package sk.upjs.drivingSchool.controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.stage.Stage;
import sk.upjs.drivingSchool.User;
import sk.upjs.drivingSchool.UserFxModel;
import sk.upjs.drivingSchool.UserDao;
import sk.upjs.drivingSchool.DaoFactory;
import sk.upjs.drivingSchool.Role;

public class UserEditController {

	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
	private UserFxModel userModel;
	private User user;
	
	public UserEditController(User user) {
    	this.user = user;
    	this.userModel = new UserFxModel(user);
    }
	
	@FXML
	void initialize() {
		List<String> roles = Role.STUDENT.getAllNames();
		ObservableList<String> f = FXCollections.observableList(roles);
		roleComboBox.setItems(f);
		
		roleComboBox.getSelectionModel().selectedItemProperty()
    		.addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, 
						String oldValue, String newValue) {

					userModel.setRole(newValue);
					
				}
			});

    	roleComboBox.getSelectionModel().select(userModel.getRole());
		
		fnameTextField.textProperty().bindBidirectional(userModel.fnameProperty());
		lnameTextField.textProperty().bindBidirectional(userModel.lnameProperty());
		usernameTextField.textProperty().bindBidirectional(userModel.usernameProperty());
		emailTextField.textProperty().bindBidirectional(userModel.emailProperty());
		phoneNumberTextField.textProperty().bindBidirectional(userModel.phoneNumberProperty());
		
		SpinnerValueFactory<Integer> ridesNumbersFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1);
		ridesDoneSpinner.setValueFactory(ridesNumbersFactory);
		ridesDoneSpinner.getValueFactory().valueProperty().bindBidirectional(userModel.ridesDoneProperty().asObject());//or bindBi..(spinnerValue)
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
		checkBoxActive.selectedProperty().bindBidirectional(userModel.activeProperty());
		dateCreatedTextField.textProperty().bind(new SimpleStringProperty(userModel.dateCreatedProperty().get().format(formatter)));
		lastModifiedTextField.textProperty().bind(new SimpleStringProperty(userModel.lastModifiedProperty().get().format(formatter)));
		lastLoginTextField.textProperty().bind(new SimpleStringProperty(userModel.lastLoginProperty().get().format(formatter)));
		
		saveUserButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				userModel.setLastModified(LocalDateTime.now());
				userDao.save(userModel.getUser());
				saveUserButton.getScene().getWindow().hide();
			}
		});
		
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				saveUserButton.getScene().getWindow().hide();
			}
		});
		
		deleteUserButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("TODO");
				//
			}
		});
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	//special
	
	@FXML
	private CheckBox checkBoxActive;
	
	@FXML
	private ComboBox<String> roleComboBox;
	
	//if premature garbage collectionproblem, use this:
	//private ObjectProperty<Integer> spinnerValue = userModel.ridesDoneProperty().asObject();
	@FXML
	private Spinner<Integer> ridesDoneSpinner;
	
	//textfields
	
	@FXML
	private TextField fnameTextField;

	@FXML
	private TextField lnameTextField;

	@FXML
	private TextField usernameTextField;

	@FXML
	private TextField passwordTextField;

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

	//buttons
	
	@FXML
	private Button saveUserButton;
	
	@FXML
	private Button deleteUserButton;

	@FXML
	private Button cancelButton;
}
