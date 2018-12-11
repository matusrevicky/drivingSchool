package sk.upjs.drivingSchool.controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import sk.upjs.drivingSchool.App;
import sk.upjs.drivingSchool.DaoFactory;
import sk.upjs.drivingSchool.Role;
import sk.upjs.drivingSchool.User;
import sk.upjs.drivingSchool.UserDao;
import sk.upjs.drivingSchool.UserFxModel;
import sk.upjs.drivingSchool.login.UserSessionManager;

public class UserController {

	private UserDao UserDao = DaoFactory.INSTANCE.getUserDao();
	private ObservableList<User> usersModel;
	private Map<String, BooleanProperty> columnsVisibility = new LinkedHashMap<>();
	private ObjectProperty<User> selectedUser = new SimpleObjectProperty<>();

	@FXML
	private ImageView userImageView;

	@FXML
	private TableView<User> userTableView;

	@FXML
	private Button editButton;

	@FXML
	private TextField nameTextField;

	@FXML
	private Button homeButton;

	@FXML
	private Button editMyProfileButton;

	@FXML
	private Button changePasswordButton;

	@FXML
	private Button avaibleTimesButton;

	@FXML
	private Button showUsersButton;

	@FXML
	private Button signOutButton;

	@FXML
	private Button searchButton;

	@FXML
	private Label currentUserName;

	@FXML
	private Button createUserButton;

	@FXML
	private Button deleteUserButton;

	private User loggedInUser;
	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();

	private void initializeUser() {
		long userId = UserSessionManager.INSTANCE.getCurrentUserSession().getUserId();
		loggedInUser = userDao.get(userId);

		if (loggedInUser.getRole().equals(Role.STUDENT.getName())) {
			userImageView
					.setImage(returnImage("src\\main\\resources\\sk\\upjs\\drivingSchool\\pics\\Student-3-icon.png"));
		}
		if (loggedInUser.getRole().equals(Role.TEACHER.getName())) {
			userImageView.setImage(returnImage("src\\main\\resources\\sk\\upjs\\drivingSchool\\pics\\Boss-3-icon.png"));
		}
		if (loggedInUser.getRole().equals(Role.ADMIN.getName())) {
			userImageView.setImage(
					returnImage("src\\main\\resources\\sk\\upjs\\drivingSchool\\pics\\avatar-default-icon.png"));
		}
		currentUserName.setText(loggedInUser.getUsername() + " Role: " + loggedInUser.getRole());
	}

	@FXML
	void initialize() {

		editButton.setDisable(true);
		initializeUser();

		// zobrazi vsetkych
		usersModel = FXCollections.observableArrayList(UserDao.getAll());

		if (loggedInUser.getRole().equals(Role.STUDENT.getName())) {
			showUsersButton.setDisable(true);
			// showUsersButton.setVisible(false);
		}

		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// App.switchScene(new HomeSceneController(), "HomeScreen.fxml");
				App.switchScene(new ReservationController(), "ReservationScreen.fxml");
			}
		});
		editMyProfileButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				UserEditController editController = new UserEditController(loggedInUser);
				App.showModalWindow(editController, "UserEdit.fxml");
				// tento kod sa spusti az po zatvoreni okna
				usersModel.setAll(UserDao.getAll());
			}
		});

		avaibleTimesButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				App.switchScene(new AvailableTimesController(), "AvailableTimes.fxml");
			}
		});
		showUsersButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				App.switchScene(new UserController(), "Listview.fxml");
			}
		});
		signOutButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				App.switchScene(new RegisterSceneController(), "RegisterScreen.fxml");
			}
		});

		editButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				UserEditController editController = new UserEditController(selectedUser.get());
				App.showModalWindow(editController, "UserEdit.fxml");
				// tento kod sa spusti az po zatvoreni okna
				usersModel.setAll(UserDao.getAll());
			}
		});

		createUserButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				App.showModalWindow(new CreateUserController(), "CreateNewUser.fxml");
				// tento kod sa spusti az po zatvoreni okna
				usersModel.setAll(UserDao.getAll());
			}
		});

		

		makeStringColumn(new TableColumn<>("ID"), "Id", "ID");
		makeStringColumn(new TableColumn<>("Active"), "active", "Aktívny");
		makeStringColumn(new TableColumn<>("Role"), "role", "Rola");
		makeStringColumn(new TableColumn<>("Name"), "fname", "Meno");
		makeStringColumn(new TableColumn<>("Surname"), "lname", "Priezvisko");
		makeStringColumn(new TableColumn<>("Rides done"), "ridesDone", "jazdy");
		makeStringColumn(new TableColumn<>("Username"), "username", "Prihlas. meno");
		makeStringColumn(new TableColumn<>("E-mail"), "email", "E-mail");
		makeStringColumn(new TableColumn<>("Phone number"), "phoneNumber", "tel. číslo");
		makeStringColumn(new TableColumn<>("Password"), "password", "Heslo");

		makeDateCreatedColumn();
		makeLastModifiedColumn();
		makeLastLoginColumn();

		userTableView.setItems(usersModel);
		userTableView.setEditable(true);

		ContextMenu contextMenu = new ContextMenu();
		for (Entry<String, BooleanProperty> entry : columnsVisibility.entrySet()) {
			CheckMenuItem menuItem = new CheckMenuItem(entry.getKey());
			menuItem.selectedProperty().bindBidirectional(entry.getValue());
			contextMenu.getItems().add(menuItem);
		}
		userTableView.setContextMenu(contextMenu);

		userTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
			@Override
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
				if (newValue == null) {
					editButton.setDisable(true);
				} else {
					editButton.setDisable(false);
				}
				selectedUser.set(newValue);
			}
		});

		
		deleteUserButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				userDao.delete(selectedUser.get().getId());
				usersModel.setAll(UserDao.getAll());
			}
		});
		
		
		// TODO este zrefaktoruj
		searchButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				usersModel = FXCollections.observableArrayList(UserDao.search(nameTextField.getText()));

				userTableView.setItems(usersModel);
				userTableView.setEditable(true);

				ContextMenu contextMenu = new ContextMenu();
				for (Entry<String, BooleanProperty> entry : columnsVisibility.entrySet()) {
					CheckMenuItem menuItem = new CheckMenuItem(entry.getKey());
					menuItem.selectedProperty().bindBidirectional(entry.getValue());
					contextMenu.getItems().add(menuItem);
				}
				userTableView.setContextMenu(contextMenu);
			}
		});
	}

	void makeStringColumn(TableColumn<User, Long> column, String userPropertyName, String visibilityPropertyName) {
		column.setCellValueFactory(new PropertyValueFactory<>(userPropertyName));
		// column.setCellFactory(TextFieldTableCell.forTableColumn());
		column.setEditable(false);
		userTableView.getColumns().add(column);
		columnsVisibility.put(visibilityPropertyName, column.visibleProperty());
	}

	private void makeDateCreatedColumn() {
		TableColumn<User, LocalDateTime> dateCreatedCol = new TableColumn<>("Created");

		dateCreatedCol.setCellFactory((TableColumn<User, LocalDateTime> param) -> {
			return new TableCell<User, LocalDateTime>() {
				private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy H:mm");

				@Override
				protected void updateItem(LocalDateTime item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null)
						setText("");
					else
						setText(formatter.format(item));
				}
			};
		});

		dateCreatedCol.setCellValueFactory(param -> {
			return new SimpleObjectProperty<>(param.getValue().getDateCreated());
		});
		userTableView.getColumns().add(dateCreatedCol);
		columnsVisibility.put("Created", dateCreatedCol.visibleProperty());
	}

	private void makeLastModifiedColumn() {
		TableColumn<User, LocalDateTime> lastModifiedCol = new TableColumn<>("Modified");

		lastModifiedCol.setCellFactory((TableColumn<User, LocalDateTime> param) -> {
			return new TableCell<User, LocalDateTime>() {
				private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy H:mm");

				@Override
				protected void updateItem(LocalDateTime item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null)
						setText("");
					else
						setText(formatter.format(item));
				}
			};
		});

		lastModifiedCol.setCellValueFactory(param -> {
			return new SimpleObjectProperty<>(param.getValue().getLastModified());
		});
		userTableView.getColumns().add(lastModifiedCol);
		columnsVisibility.put("Modified", lastModifiedCol.visibleProperty());
	}

	private void makeLastLoginColumn() {
		TableColumn<User, LocalDateTime> lastLoginCol = new TableColumn<>("Last login");

		lastLoginCol.setCellFactory((TableColumn<User, LocalDateTime> param) -> {
			return new TableCell<User, LocalDateTime>() {
				private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy H:mm");

				@Override
				protected void updateItem(LocalDateTime item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null)
						setText("");
					else
						setText(formatter.format(item));
				}
			};
		});

		lastLoginCol.setCellValueFactory(param -> {
			return new SimpleObjectProperty<>(param.getValue().getLastLogin());
		});
		userTableView.getColumns().add(lastLoginCol);
		columnsVisibility.put("Last login", lastLoginCol.visibleProperty());
	}

	// skopirovana z
	// https://blog.idrsolutions.com/2012/11/convert-bufferedimage-to-javafx-image/
	private WritableImage returnImage(String path) {
		BufferedImage bf = null;
		try {
			bf = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println("sda");
		}
		WritableImage wr = null;
		if (bf != null) {
			wr = new WritableImage(bf.getWidth(), bf.getHeight());
			PixelWriter pw = wr.getPixelWriter();
			for (int x = 0; x < bf.getWidth(); x++) {
				for (int y = 0; y < bf.getHeight(); y++) {
					pw.setArgb(x, y, bf.getRGB(x, y));
				}
			}
		}
		return wr;
	}

}