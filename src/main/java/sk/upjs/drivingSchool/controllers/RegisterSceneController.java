package sk.upjs.drivingSchool.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import sk.upjs.drivingSchool.App;
import sk.upjs.drivingSchool.DaoFactory;
import sk.upjs.drivingSchool.UserDao;
import sk.upjs.drivingSchool.login.Authenticator;
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

	private boolean emailValid;

	private boolean passwordValid;

	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();

	public static final String EMPTY = "Musí byť vyplnené";

	@FXML
	public void initialize() {

		fnameValidate();
		lnameValidate();
		usernameValidate();
		emailValidate();
		passwordAgainValidate();
		passwordValidate();
		

		checkIfEmpty(fnameTextField.getText(), nameLabel);
		checkIfEmpty(lnameTextField.getText(), surnameLabel);
	
		checkIfEmpty(usernameTextField.getText(), usernameLabel);
		checkIfEmpty(emailTextField.getText(), emailLabel);
		checkIfEmpty(passwordTextField.getText(), passwdLabel);
		checkIfEmpty(passwdAgainTextField.getText(), passwdAgainLabel);
		

		registerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				
				Authenticator authenticator = Authenticator.INSTANCE;
				UserSession userSession = authenticator.register(fnameTextField.getText(), lnameTextField.getText(),
						usernameTextField.getText(), emailTextField.getText(), passwordTextField.getText(),
						passwdAgainTextField.getText());

				App.switchScene(new HomeSceneController(), "HomeScreen.fxml");
			}
		});

		goToLoginButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				App.switchScene(new LoginSceenController(), "LoginScreen.fxml");
			}
		});
	}

	public void checkIfEmpty(String textFieldText, Label labelName) {
		if (textFieldText.trim().isEmpty()) {
			labelName.setText(EMPTY);
		} else {
			labelName.setText("");
		}
	}

	boolean isPasswordValid(String textFieldText, Label labelName) {
		if (textFieldText.trim().isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public void fnameValidate() {
		EventHandler<KeyEvent> eventHandlerTextField = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				nameLabel.setText("ok");
				if (fnameTextField.getText().trim().isEmpty()) {
					nameLabel.setText("");
				}

			}
		};
		fnameTextField.addEventHandler(KeyEvent.KEY_RELEASED, eventHandlerTextField);
	}

	public void lnameValidate() {
		EventHandler<KeyEvent> eventHandlerTextField = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				surnameLabel.setText("ok");
				if (lnameTextField.getText().trim().isEmpty()) {
					surnameLabel.setText("");
				}
			}
		};
		lnameTextField.addEventHandler(KeyEvent.KEY_RELEASED, eventHandlerTextField);
	}

	public void usernameValidate() {
		EventHandler<KeyEvent> eventHandlerTextField = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				usernameLabel.setText("ok");
				if (usernameTextField.getText().trim().isEmpty()) {
					usernameLabel.setText("");
				}
			}
		};
		usernameTextField.addEventHandler(KeyEvent.KEY_RELEASED, eventHandlerTextField);
	}

	public void emailValidate() {
		EventHandler<KeyEvent> eventHandlerTextField = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (validate(emailTextField.getText())) {
					emailLabel.setText("ok");
				} else {
					emailLabel.setText("false");
				}
			}
		};
		emailTextField.addEventHandler(KeyEvent.KEY_RELEASED, eventHandlerTextField);
	}

	public void passwordAgainValidate() {
		EventHandler<KeyEvent> eventHandlerTextField = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (passwordTextField.getText().equals(passwdAgainTextField.getText())
						&& !passwordTextField.getText().isEmpty() && !passwdAgainTextField.getText().isEmpty()) {
					passwdAgainLabel.setText("ok");
				} else {
					passwdAgainLabel.setText("nerovnaju sa");
				}
			}
		};
		passwdAgainTextField.addEventHandler(KeyEvent.KEY_RELEASED, eventHandlerTextField);
		passwordTextField.addEventHandler(KeyEvent.KEY_RELEASED, eventHandlerTextField);
	}
	
	public void passwordValidate() {
		EventHandler<KeyEvent> eventHandlerTextField = new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				passwdLabel.setText("ok");
				if (passwordTextField.getText().trim().isEmpty()) {
					passwdLabel.setText("");
				}
			}
		};
		passwordTextField.addEventHandler(KeyEvent.KEY_RELEASED, eventHandlerTextField);
	}

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public static boolean validate(String emailStr) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}
}
