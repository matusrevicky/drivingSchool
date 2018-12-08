package sk.upjs.drivingSchool.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import sk.upjs.drivingSchool.App;
import sk.upjs.drivingSchool.DaoFactory;
import sk.upjs.drivingSchool.Role;
import sk.upjs.drivingSchool.User;
import sk.upjs.drivingSchool.UserDao;
import sk.upjs.drivingSchool.login.UserSessionManager;

public class UserController {

	private UserDao UserDao = DaoFactory.INSTANCE.getUserDao();
	private ObservableList<User> usersModel;
	private Map<String, BooleanProperty> columnsVisibility = new LinkedHashMap<>();
	private ObjectProperty<User> selectedUser = new SimpleObjectProperty<>();

	@FXML
	private TableView<User> userTableView;

	@FXML
	private Button editButton;

	@FXML
	private TextField surnameTextField;

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
	}

	@FXML
	void initialize() {
		initializeUser();
		currentUserName.setText("Uzivatel: " + loggedInUser.getUsername() + "; rola " + loggedInUser.getRole());

		usersModel = FXCollections.observableArrayList(UserDao.getAll());

		if (loggedInUser.getRole().equals(Role.STUDENT.getName())) {
			showUsersButton.setDisable(true);
			// showUsersButton.setVisible(false);
		}

		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//App.switchScene(new HomeSceneController(), "HomeScreen.fxml");
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
		changePasswordButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO
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
		
		searchButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
		
		createUserButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
		
		deleteUserButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
			}
		});

		makeStringColumn(new TableColumn<>("ID"), "userId", "ID");
		makeStringColumn(new TableColumn<>("Aktívny"), "active", "Aktívny");
		makeStringColumn(new TableColumn<>("Rola"), "role", "Rola");
		makeStringColumn(new TableColumn<>("Meno"), "fname", "Meno");
		makeStringColumn(new TableColumn<>("Priezvisko"), "lname", "Priezvisko");
		makeStringColumn(new TableColumn<>("Pocet jazd"), "ridesDone", "jazdy");
		makeStringColumn(new TableColumn<>("Prihlasovacie meno"), "username", "Prihlas. meno");
		makeStringColumn(new TableColumn<>("E-mail"), "email", "E-mail");
		makeStringColumn(new TableColumn<>("Telefónne číslo"), "phoneNumber", "tel. číslo");
		makeStringColumn(new TableColumn<>("Heslo"), "password", "Heslo");

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
	}

	void makeStringColumn(TableColumn<User, Long> column, String userPropertyName, String visibilityPropertyName) {
		column.setCellValueFactory(new PropertyValueFactory<>(userPropertyName));
		// column.setCellFactory(TextFieldTableCell.forTableColumn());
		column.setEditable(false);
		userTableView.getColumns().add(column);
		columnsVisibility.put(visibilityPropertyName, column.visibleProperty());
	}

	private void makeDateCreatedColumn() {
		TableColumn<User, LocalDateTime> dateCreatedCol = new TableColumn<>("Vytvorený");

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
		columnsVisibility.put("Vytvorený", dateCreatedCol.visibleProperty());
	}

	private void makeLastModifiedColumn() {
		TableColumn<User, LocalDateTime> lastModifiedCol = new TableColumn<>("Upravený");

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
		columnsVisibility.put("Upravený", lastModifiedCol.visibleProperty());
	}

	private void makeLastLoginColumn() {
		TableColumn<User, LocalDateTime> lastLoginCol = new TableColumn<>("Posledné prihlásenie");

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
		columnsVisibility.put("Posled. prihl.", lastLoginCol.visibleProperty());
	}

}