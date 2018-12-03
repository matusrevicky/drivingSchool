package sk.upjs.drivingSchool.controllers;

import sk.upjs.drivingSchool.App;
import sk.upjs.drivingSchool.DaoFactory;
import sk.upjs.drivingSchool.Role;
import sk.upjs.drivingSchool.User;
import sk.upjs.drivingSchool.UserDao;
import sk.upjs.drivingSchool.login.UserSessionManager;
import jfxtras.scene.control.agenda.Agenda;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HomeSceneController {

	@FXML
	private Button editMyProfileButton;

	@FXML
	private Button changePasswordButton;

	@FXML
	private Button avaibleTimesButton;

	@FXML
	private Button homeButton;

	@FXML
	private Button showUsersButton;
	
	@FXML
	private Button signOutButton;
	
	@FXML
	private TextField emailTextField;

	@FXML
	private TextField passwordTextField;

	@FXML
	private TextField passwordAgainTextField;

	@FXML
	private Button saveButton;

	@FXML
	private Label currentUserName;

	private User loggedInUser;
	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();

	private void initializeUser() {
		long userId = UserSessionManager.INSTANCE.getCurrentUserSession().getUserId();
		loggedInUser = userDao.get(userId);
	}

	@FXML
	void initialize() {
		// saveButton.setDisable(true);//enabled iba po zmene v nejakom textFielde
		initializeUser();
		currentUserName.setText("Uzivatel: " + loggedInUser.getUsername() + "; rola " + loggedInUser.getRole());

	//	emailTextField.setText(loggedInUser.getEmail());

		if (loggedInUser.getRole().equals(Role.STUDENT.getName())) {
			showUsersButton.setDisable(true);
			// showUsersButton.setVisible(false);
		}

		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				App.switchScene(new HomeSceneController(), "HomeScreen.fxml");
			}
		});
		editMyProfileButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				UserEditController editController = new UserEditController(loggedInUser);
				App.showModalWindow(editController, "UserEdit.fxml");
				// tento kod sa spusti az po zatvoreni okna
			}
		});
		changePasswordButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//TODO
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
		
	}
}
