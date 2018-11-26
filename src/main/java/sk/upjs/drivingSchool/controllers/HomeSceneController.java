package sk.upjs.drivingSchool.controllers;

import sk.upjs.drivingSchool.App;
import sk.upjs.drivingSchool.DaoFactory;
import sk.upjs.drivingSchool.User;
import sk.upjs.drivingSchool.UserDao;
import sk.upjs.drivingSchool.login.UserSessionManager;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class HomeSceneController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button showUsersButton;
	
	@FXML
    private TextField emailTextField;

    @FXML
    private TextField passwordTextField;
    
    @FXML
    private TextField passwordAgainTextField;
    
    @FXML
    private Button saveButton;

	@FXML
	void initialize() {
		//saveButton.setDisable(true);//enabled iba po zmene v nejakom textFielde
		initializeUser();
		emailTextField.setText(loggedInUser.getEmail());
		
		showUsersButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				App.switchScene(new UserController(), "Listview.fxml");
			}
		});
	}

	private User loggedInUser;
	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();

	private void initializeUser() {
		long userId = UserSessionManager.INSTANCE.getCurrentUserSession().getUserId();
		loggedInUser = userDao.get(userId);
	}

}
