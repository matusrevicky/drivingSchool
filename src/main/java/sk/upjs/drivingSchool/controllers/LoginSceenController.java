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
import sk.upjs.drivingSchool.login.Authenticator;
import sk.upjs.drivingSchool.login.BadPasswordException;
import sk.upjs.drivingSchool.login.UserAlreadyExistsException;
import sk.upjs.drivingSchool.login.UserSession;

public class LoginSceenController {
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField emailTextField;

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

	@FXML
	void initialize() {

		registerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				App.switchScene(new RegisterSceneController(), "registerScreen.fxml");
			}
		});

		loginButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {
					Authenticator authenticator = Authenticator.INSTANCE;

					UserSession userSession = authenticator.logIn(emailTextField.getText(), passwordTextField.getText());

					App.switchScene(new HomeSceneController(), "HomeScreen.fxml");
				} catch (UserAlreadyExistsException e) {
					passwdLabel.setText("zly user");
				} catch (BadPasswordException e) {
					passwdLabel.setText("zle heslo");
				} 
			}
		});

	}
}
