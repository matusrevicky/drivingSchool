package sk.upjs.drivingSchool.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import sk.upjs.drivingSchool.App;
import sk.upjs.drivingSchool.DaoFactory;
import sk.upjs.drivingSchool.User;
import sk.upjs.drivingSchool.UserDao;
import sk.upjs.drivingSchool.UserFxModel;
import sk.upjs.drivingSchool.login.Authenticator;
import sk.upjs.drivingSchool.login.BadPasswordException;
import sk.upjs.drivingSchool.login.EmailNotValidException;
import sk.upjs.drivingSchool.login.SomethingInUserIsNullExeption;
import sk.upjs.drivingSchool.login.UserAlreadyExistsException;
import sk.upjs.drivingSchool.login.UserSession;

import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterSceneController {

	@FXML
	private TextField emailTextField;

	@FXML
	private TextField fnameTextField;

	@FXML
	private TextField lnameTextField;

	@FXML
	private TextField phoneTextField;

	@FXML
	private TextField usernameTextField;

	@FXML
	private Label usernameLabel;

	@FXML
	private Label emailLabel;

	@FXML
	private Label passwdLabel;

	@FXML
	private Label passwdAgainLabel;

	@FXML
	private Label phoneLabel;

	@FXML
	private Label surnameLabel;

	@FXML
	private Label nameLabel;

	@FXML
	private PasswordField passwordTextField;

	@FXML
	private PasswordField passwdAgainTextField;

	@FXML
	private Button registerButton;

	@FXML
	private Button goToLoginButton;

	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();

	private UserFxModel userModel = new UserFxModel(new User());

	public static final String EMPTY = "Required";
	public static final String NOTHING_TO_REMIND = "";
	public static final String EVERYTHING_OK = "Ok";
	public static final String BAD_EMAIL_FORMAT = "Bad email";
	public static final String PASSWORDS_DO_NOT_MATCH = "Not equal";
	public static final String USER_EXISTS = "Exists already";

	@FXML
	public void initialize() {

		fnameTextField.textProperty().bindBidirectional(userModel.fnameProperty());
		lnameTextField.textProperty().bindBidirectional(userModel.lnameProperty());
		phoneTextField.textProperty().bindBidirectional(userModel.phoneNumberProperty());
		usernameTextField.textProperty().bindBidirectional(userModel.usernameProperty());
		emailTextField.textProperty().bindBidirectional(userModel.emailProperty());
		passwordTextField.textProperty().bindBidirectional(userModel.passwordProperty());
		passwdAgainTextField.textProperty().bindBidirectional(userModel.passwordAgainProperty());

		// userModel.setFname("janko");
		fnameValidate();
		lnameValidate();
		usernameValidate();
		emailValidate();
		passwordAgainValidate();
		passwordValidate();

		checkIfEmpty(userModel.getFname(), nameLabel);
		checkIfEmpty(userModel.getLname(), surnameLabel);
		checkIfEmpty(userModel.getUsername(), usernameLabel);
		checkIfEmpty(userModel.getEmail(), emailLabel);
		checkIfEmpty(userModel.getPassword(), passwdLabel);
		checkIfEmpty(userModel.getPasswordAgain(), passwdAgainLabel);

		registerButton();
		switchToLoginButton();
	}

	private void switchToLoginButton() {
		goToLoginButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				App.switchScene(new LoginSceenController(), "LoginScreen.fxml");
			}
		});
	}

	private void registerButton() {
		registerButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Authenticator authenticator = Authenticator.INSTANCE;
					UserSession userSession = authenticator.register(userModel.getFname(), userModel.getLname(),
							userModel.getPhoneNumber(), userModel.getUsername(), userModel.getEmail(),
							userModel.getPassword(), userModel.getPasswordAgain());
					//App.switchScene(new HomeSceneController(), "HomeScreen.fxml");
					App.switchScene(new ReservationController(), "ReservationScreen.fxml");
				} catch (UserAlreadyExistsException e) {
					changeColorRed(usernameTextField);
					usernameLabel.setText(USER_EXISTS);
				} catch (SomethingInUserIsNullExeption e) {
					checkIfEmptyWarning(userModel.getFname(), nameLabel, fnameTextField);
					checkIfEmptyWarning(userModel.getLname(), surnameLabel, lnameTextField);
					checkIfEmptyWarning(userModel.getUsername(), usernameLabel, usernameTextField);
					checkIfEmptyWarning(userModel.getEmail(), emailLabel, emailTextField);
					checkIfEmptyWarning(userModel.getPassword(), passwdLabel, passwordTextField);
					checkIfEmptyWarning(userModel.getPasswordAgain(), passwdAgainLabel, passwdAgainTextField);
				} catch (EmailNotValidException e) {
					changeColorRed(emailTextField);
					emailLabel.setText(BAD_EMAIL_FORMAT);
				} catch (BadPasswordException e) {

				}
			}
		});
	}

	public void changeColorRed(TextField textField) {
		String cssLayout = "-fx-border-color: red;\n" + "-fx-border-radius: 4;\n" + "-fx-border-width: 2;\n";
		textField.setStyle(cssLayout);
	}

	public void changeColorGreen(TextField textField) {
		String cssLayout = "-fx-border-color: green;\n" + "-fx-border-radius: 4;\n" + "-fx-border-width: 2;\n";
		textField.setStyle(cssLayout);
	}

	public void changeColorNoColor(TextField textField) {
		String cssLayout = "-fx-border-width: 0;\n";
		textField.setStyle(cssLayout);
	}

	public boolean checkIfEmpty(String textFieldText, Label labelName) {
		if (textFieldText == null || textFieldText.trim().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public void checkIfEmptyWarning(String textFieldText, Label labelName, TextField textField) {
		if (textFieldText == null || textFieldText.trim().isEmpty()) {
			changeColorRed(textField);
			labelName.setText(EMPTY);
		} else {
			changeColorGreen(textField);
			labelName.setText(NOTHING_TO_REMIND);
		}
	}

	public void fnameValidate() {
		EventHandler<KeyEvent> eventHandlerTextField = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (checkIfEmpty(userModel.getFname(), nameLabel)) {
					changeColorRed(fnameTextField);
					nameLabel.setText(EMPTY);
					return;
				}
				// nameLabel.setTextFill(Color.web("#0076a3"));
				changeColorGreen(fnameTextField);
				nameLabel.setText(EVERYTHING_OK);
			}
		};
		fnameTextField.addEventHandler(KeyEvent.KEY_RELEASED, eventHandlerTextField);
	}

	public void lnameValidate() {
		EventHandler<KeyEvent> eventHandlerTextField = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (checkIfEmpty(userModel.getLname(), surnameLabel)) {
					changeColorRed(lnameTextField);
					surnameLabel.setText(EMPTY);
					return;
				}
				changeColorGreen(lnameTextField);
				surnameLabel.setText(EVERYTHING_OK);
			}
		};
		lnameTextField.addEventHandler(KeyEvent.KEY_RELEASED, eventHandlerTextField);
	}

	public void usernameValidate() {
		EventHandler<KeyEvent> eventHandlerTextField = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (checkIfEmpty(userModel.getUsername(), usernameLabel)) {
					changeColorRed(usernameTextField);
					usernameLabel.setText(EMPTY);
					return;
				}
				changeColorGreen(usernameTextField);
				usernameLabel.setText(EVERYTHING_OK);
			}
		};
		usernameTextField.addEventHandler(KeyEvent.KEY_RELEASED, eventHandlerTextField);
	}

	public void emailValidate() {
		EventHandler<KeyEvent> eventHandlerTextField = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (checkIfEmpty(userModel.getEmail(), emailLabel)) {
					changeColorRed(emailTextField);
					emailLabel.setText(EMPTY);
					return;
				}
				if (Authenticator.validateEmail(userModel.getEmail())) {
					changeColorGreen(emailTextField);
					emailLabel.setText(EVERYTHING_OK);
				} else {
					changeColorRed(emailTextField);
					emailLabel.setText(BAD_EMAIL_FORMAT);
				}
			}
		};
		emailTextField.addEventHandler(KeyEvent.KEY_RELEASED, eventHandlerTextField);
	}

	public void passwordValidate() {
		EventHandler<KeyEvent> eventHandlerTextField = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (checkIfEmpty(userModel.getPassword(), passwdLabel)) {
					changeColorRed(passwordTextField);
					passwdLabel.setText(EMPTY);
					return;
				}
				changeColorGreen(passwordTextField);
				passwdLabel.setText(EVERYTHING_OK);
			}
		};
		passwordTextField.addEventHandler(KeyEvent.KEY_RELEASED, eventHandlerTextField);
	}

	public void passwordAgainValidate() {
		EventHandler<KeyEvent> eventHandlerTextField = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (checkIfEmpty(userModel.getPasswordAgain(), passwdAgainLabel)) {
					changeColorRed(passwdAgainTextField);
					passwdAgainLabel.setText(EMPTY);
					return;
				}

				if (userModel.getPassword() != null
						&& userModel.getPassword().equals(userModel.getPasswordAgain())) {
					changeColorGreen(passwdAgainTextField);
					passwdAgainLabel.setText(EVERYTHING_OK);
				} else {
					changeColorRed(passwdAgainTextField);
					passwdAgainLabel.setText(PASSWORDS_DO_NOT_MATCH);
				}
			}
		};
		passwdAgainTextField.addEventHandler(KeyEvent.KEY_RELEASED, eventHandlerTextField);
		passwordTextField.addEventHandler(KeyEvent.KEY_RELEASED, eventHandlerTextField);
	}

}
