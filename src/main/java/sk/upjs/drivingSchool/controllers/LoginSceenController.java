package sk.upjs.drivingSchool.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.security.crypto.bcrypt.BCrypt;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sk.upjs.drivingSchool.App;
import sk.upjs.drivingSchool.User;
import sk.upjs.drivingSchool.UserFxModel;
import sk.upjs.drivingSchool.login.Authenticator;
import sk.upjs.drivingSchool.login.BadPasswordException;
import sk.upjs.drivingSchool.login.UserAlreadyExistsException;
import sk.upjs.drivingSchool.login.UserDoesNotExistException;
import sk.upjs.drivingSchool.login.UserNotActiveException;
import sk.upjs.drivingSchool.login.UserSession;

public class LoginSceenController {
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField usernameTextField;

	@FXML
	private PasswordField passwordTextField;

	@FXML
	private Button loginButton;

	@FXML
	private Button registerButton;

	@FXML
	private Label passwdLabel;

	@FXML
	private Label emailLabel;
	
	private UserFxModel userModel = new UserFxModel(new User());

	public static final String BAD_PASSWORD = "Bad password";
	public static final String BAD_USER = "Bad user";
	public static final String NOT_ACTIVE = "You are no longer a member";
	
	
	@FXML
	void initialize() {

		usernameTextField.textProperty().bindBidirectional(userModel.usernameProperty());
		passwordTextField.textProperty().bindBidirectional(userModel.passwordProperty());
		
		registerButtonPressed();
		loginButtonPressed();
	}

	private void loginButtonPressed() {
		loginButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {
					Authenticator authenticator = Authenticator.INSTANCE;

					UserSession userSession = authenticator.logIn(userModel.getUsername(), userModel.getPassword());

					App.switchScene(new HomeSceneController(), "HomeScreen.fxml");
				} catch (UserDoesNotExistException e) {
					passwdLabel.setText(BAD_USER);
				} catch (BadPasswordException e) {
					passwdLabel.setText(BAD_PASSWORD);
				}  catch (UserNotActiveException e) {
					passwdLabel.setText(NOT_ACTIVE);
				} 
			}
		});
	}

	private void registerButtonPressed() {
		registerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				App.switchScene(new RegisterSceneController(), "registerScreen.fxml");
			}
		});
	}
}
