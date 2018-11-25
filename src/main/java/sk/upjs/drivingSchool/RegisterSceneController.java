package sk.upjs.drivingSchool;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sk.upjs.drivingSchool.login.Authenticator;
import sk.upjs.drivingSchool.login.UserSession;

import java.util.Locale;
import java.util.Optional;

public class RegisterSceneController {

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private TextField emailTextField;

    @FXML
    private TextField passwordTextField;
    
    @FXML
    private Button registerButton;
    
    @FXML
    private Button goToLoginButton;
    
    private boolean emailValid;

    private boolean passwordValid;

    private UserDao userDao = DaoFactory.INSTANCE.getUserDao();

    @FXML
    public void initialize() {
    	registerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Authenticator authenticator = Authenticator.INSTANCE;
	            UserSession userSession = authenticator.register(emailTextField.getText(), passwordTextField.getText());
	            
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

}
