package sk.upjs.drivingSchool.controllers;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import sk.upjs.drivingSchool.UserFxModel;
import sk.upjs.drivingSchool.entity.Role;
import sk.upjs.drivingSchool.entity.User;
import sk.upjs.drivingSchool.login.UserSessionManager;
import sk.upjs.drivingSchool.persistent.DaoFactory;
import sk.upjs.drivingSchool.persistent.UserDao;

@SuppressWarnings("restriction")
public class UserEditController {

	@FXML
	private CheckBox checkBoxActive;

	@FXML
	private ComboBox<String> roleComboBox;

	// if premature garbage collectionproblem, use this:
	// private ObjectProperty<Integer> spinnerValue =
	// userModel.ridesDoneProperty().asObject();
	@FXML
	private Spinner<Integer> ridesDoneSpinner;

	@FXML
	private TextField fnameTextField;

	@FXML
	private TextField lnameTextField;

	@FXML
	private TextField usernameTextField;

	@FXML
	private TextField passwordTextField;

	@FXML
	private TextField passwordAgainTextField;

	@FXML
	private TextField emailTextField;

	@FXML
	private TextField phoneNumberTextField;

	@FXML
	private TextField dateCreatedTextField;

	@FXML
	private TextField lastModifiedTextField;

	@FXML
	private TextField lastLoginTextField;

	@FXML
	private Button saveUserButton;

	@FXML
	private Button deleteUserButton;

	@FXML
	private Button cancelButton;

	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
	private UserFxModel userModel;
	private User user;
	private User loggedInUser;

	public UserEditController(User user) {
		this.user = user;
		this.userModel = new UserFxModel(user);
	}

	private void initializeUser() {
		long userId = UserSessionManager.INSTANCE.getCurrentUserSession().getUserId();
		loggedInUser = userDao.get(userId);

		// zabezpeci ze student ani ucitel si nemoze zmenit rolu, pocet jazd, a aktivitu
		if (loggedInUser.getRole().equals(Role.STUDENT.getName())
				|| loggedInUser.getRole().equals(Role.TEACHER.getName())) {
			roleComboBox.setDisable(true);
			//ridesDoneSpinner.setDisable(true);
			checkBoxActive.setDisable(true);
		} else {
			roleComboBox.setDisable(false);
			//ridesDoneSpinner.setDisable(false);
			checkBoxActive.setDisable(false);
		}
		
		// zabezpeci ze ucitel si nemoze zmenit rolu, a aktivitu, 
		if (loggedInUser.getRole().equals(Role.TEACHER.getName())) {
			roleComboBox.setDisable(true);
			checkBoxActive.setDisable(true);
		} else {
			roleComboBox.setDisable(false);
			checkBoxActive.setDisable(false);
		}
		
		// ucitel nema pravo menit uzivatelom hesla
		if ( user.getId()==loggedInUser.getId()) {
			passwordTextField.setDisable(false);
			passwordAgainTextField.setDisable(false);
		} else {
			passwordTextField.setDisable(true);
			passwordAgainTextField.setDisable(true);
		}
	}

	@FXML
	void initialize() {
		initializeUser();

		roleComboBoxInitialize();

		userBind();
		spinnerInitialize();// bindBi..(spinnerValue)
		dateBinding();

		passwordTextFieldAction();
		passwordAgainTextFieldAction();

		saveUserAction();
		cancelAction();
	}

	private void roleComboBoxInitialize() {
		List<String> roles = Role.STUDENT.getAllNames();
		ObservableList<String> f = FXCollections.observableList(roles);
		roleComboBox.setItems(f);

		roleComboBoxAction();
		roleComboBox.getSelectionModel().select(userModel.getRole());
	}

	private void userBind() {
		// iba jednosmerny bind !!!
		userModel.passwordAgainProperty().bind(passwordAgainTextField.textProperty());
		userModel.passwordProperty().bind(passwordTextField.textProperty());
		// obojsmerne bindy
		fnameTextField.textProperty().bindBidirectional(userModel.fnameProperty());
		lnameTextField.textProperty().bindBidirectional(userModel.lnameProperty());
		usernameTextField.textProperty().bindBidirectional(userModel.usernameProperty());
		emailTextField.textProperty().bindBidirectional(userModel.emailProperty());
		phoneNumberTextField.textProperty().bindBidirectional(userModel.phoneNumberProperty());
	}

	private void dateBinding() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
		checkBoxActive.selectedProperty().bindBidirectional(userModel.activeProperty());
		dateCreatedTextField.textProperty()
				.bind(new SimpleStringProperty(userModel.dateCreatedProperty().get().format(formatter)));
		lastModifiedTextField.textProperty()
				.bind(new SimpleStringProperty(userModel.lastModifiedProperty().get().format(formatter)));
		lastLoginTextField.textProperty()
				.bind(new SimpleStringProperty(userModel.lastLoginProperty().get().format(formatter)));
	}

	private void spinnerInitialize() {
		SpinnerValueFactory<Integer> ridesNumbersFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0,
				1);
		ridesDoneSpinner.setValueFactory(ridesNumbersFactory);
		ridesDoneSpinner.getValueFactory().valueProperty().bindBidirectional(userModel.ridesDoneProperty().asObject());// or
	}

	private void cancelAction() {
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				saveUserButton.getScene().getWindow().hide();
			}
		});
	}

	private void saveUserAction() {
		saveUserButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				userModel.setLastModified(LocalDateTime.now());
				userDao.save(userModel.getUser());
				saveUserButton.getScene().getWindow().hide();
			}
		});
	}

	private void passwordAgainTextFieldAction() {
		passwordAgainTextField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!userModel.getPassword().equals(userModel.getPasswordAgain())) {
					saveUserButton.setDisable(true);
				} else {
					saveUserButton.setDisable(false);
				}
			}
		});
	}

	private void passwordTextFieldAction() {
		passwordTextField.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!userModel.getPassword().equals(userModel.getPasswordAgain())) {
					saveUserButton.setDisable(true);
				} else {
					saveUserButton.setDisable(false);
				}
			}
		});
	}

	private void roleComboBoxAction() {
		roleComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				userModel.setRole(newValue);

			}
		});
	}

}
