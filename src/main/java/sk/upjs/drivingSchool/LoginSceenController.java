package sk.upjs.drivingSchool;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sk.upjs.drivingSchool.login.Authenticator;
import sk.upjs.drivingSchool.login.UserSession;

public class LoginSceenController {
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField emailTextField;

	@FXML
	private TextField passwordTextField;

	@FXML
	private Button loginButton;

	@FXML
	private Button registerButton;

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
				Authenticator authenticator = Authenticator.INSTANCE;
				UserSession userSession = authenticator.logIn(emailTextField.getText(), passwordTextField.getText());

				App.switchScene(new HomeSceneController(), "HomeScreen.fxml");
			}
		});

	}
}
