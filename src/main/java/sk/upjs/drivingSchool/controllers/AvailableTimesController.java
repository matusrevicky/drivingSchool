package sk.upjs.drivingSchool.controllers;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import jfxtras.labs.icalendaragenda.scene.control.agenda.ICalendarAgenda;
import jfxtras.labs.icalendarfx.VCalendar;
import jfxtras.labs.icalendarfx.components.VEvent;
import jfxtras.scene.control.agenda.Agenda;
import sk.upjs.drivingSchool.App;
import sk.upjs.drivingSchool.AvailableTime;
import sk.upjs.drivingSchool.AvailableTimesDao;
import sk.upjs.drivingSchool.DaoFactory;
import sk.upjs.drivingSchool.Role;
import sk.upjs.drivingSchool.User;
import sk.upjs.drivingSchool.UserDao;
import sk.upjs.drivingSchool.UserFxModel;
import sk.upjs.drivingSchool.login.UserSessionManager;

public class AvailableTimesController {

	@FXML
	private Label currentUserName;

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
	private Button weekLeftButton;

	@FXML
	private Button weekRightButton;

	@FXML
	private Agenda calendarOriginal;
	// musi byt v strede BorderPane,
	// kalendar sa da nastavit iba cez konstruktor
	// preto sa zapamata BorderPane
	// a vzdy sa v nom vytvori novy calendar
	// do ktoreho sa nahadzu hodnoty
	private BorderPane borderPane = null;
	private ICalendarAgenda calendarAgenda;
	private VCalendar myCalendar;

	@FXML
	private Button saveButton;

	@FXML
	private CheckBox activeCheckBox;

	@FXML
	private ComboBox<String> roleComboBox;

	@FXML
	private ComboBox<User> nameComboBox;

	private AvailableTimesDao availableTimesDao = DaoFactory.INSTANCE.getAvailableTimesDao();
	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
	private UserFxModel userModel;
	private String selectedRole;
	private boolean selectedActive;
	private User loggedInUser;

	private void initializeUser() {
		long userId = UserSessionManager.INSTANCE.getCurrentUserSession().getUserId();
		loggedInUser = userDao.get(userId);
	}

	@FXML
	void initialize() {

		initializeUser();
		currentUserName.setText("Uzivatel: " + loggedInUser.getUsername() + "; rola " + loggedInUser.getRole());
		this.userModel = new UserFxModel(loggedInUser);
		selectedRole = userModel.getRole();
		selectedActive = userModel.getActive();
		initializeComponentsWithCurrectUserModel();
		initializeCalendar();

		if (userModel.getUser().getRole().equals(Role.STUDENT.getName())) {
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
				refreshNameComboBox();
			}
		});
		changePasswordButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO
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

		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				saveAvailableTimes();
				userDao.get(loggedInUser.getUserId()).setLastModified(LocalDateTime.now());

			}
		});

		weekLeftButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LocalDateTime l = calendarAgenda.getDisplayedLocalDateTime().minus(Period.ofWeeks(1));
				calendarAgenda.setDisplayedLocalDateTime(l);
			}
		});
		weekRightButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LocalDateTime l = calendarAgenda.getDisplayedLocalDateTime().plus(Period.ofWeeks(1));
				calendarAgenda.setDisplayedLocalDateTime(l);
			}
		});
	}

	private void initializeComponentsWithCurrectUserModel() {

		List<String> roles = Role.STUDENT.getAllNames();
		ObservableList<String> f = FXCollections.observableList(roles);
		roleComboBox.setItems(f);
		roleComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				selectedRole = newValue;
				refreshNameComboBox();
			}
		});
		roleComboBox.getSelectionModel().select(userModel.getRole());

		activeCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				selectedActive = newValue;
				refreshNameComboBox();
			}
		});
		activeCheckBox.selectedProperty().setValue(userModel.getActive());

		List<User> users = userDao.getAll(userModel.getRole(), userModel.getActive());
		nameComboBox.setItems(FXCollections.observableList(users));
		nameComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {

			@Override
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
				if (newValue == null) {
					userModel = null;
				} else {
					userModel = new UserFxModel(newValue);
				}
				initializeCalendar();
				checkSaveButtonVisibility();
			}
		});
		nameComboBox.getSelectionModel().select(userModel.getUser());		
	}

	private void refreshNameComboBox() {

		List<User> users = userDao.getAll(selectedRole, selectedActive);
		nameComboBox.setItems(FXCollections.observableList(users));
		if (!users.isEmpty()) {
			nameComboBox.getSelectionModel().select(users.get(0));
		}
	}

	private void checkSaveButtonVisibility() {

		if (userModel != null && loggedInUser == userModel.getUser()) {
			// saveButton.setDisable(false);
			saveButton.setVisible(true);
		} else {
			// saveButton.setDisable(true);
			saveButton.setVisible(false);
		}
	}

	private void initializeCalendar() {

		if (borderPane == null)
			borderPane = (BorderPane) calendarOriginal.getParent();
		if (borderPane.getChildren() == null || borderPane.getChildren().size() == 0) {

		} else if (borderPane.getChildren().size() == 1) {
			borderPane.getChildren().remove(0);
		} else {
			System.out.println("Viac kalendarov naraz exception!");
		}
		if (userModel != null) {
			myCalendar = VCalendar.parse(availableTimesToString());
			calendarAgenda = new ICalendarAgenda(myCalendar);
			borderPane.setCenter(calendarAgenda);
		}
	}

	private String availableTimesToString() {
		StringBuilder sb = new StringBuilder();
		DateTimeFormatter formatter;
		sb.append("BEGIN:VCALENDAR\r\n");
		for (AvailableTime availableTime : availableTimesDao.getAvailableTimesByUserId(userModel.getUser().getUserId())) {//userModel.getAvailableTimes()
			sb.append("BEGIN:VEVENT\r\n" + "SUMMARY:Voľný čas\r\n" + "CATEGORIES:group00\r\n"
					+ "DTSTART;TZID=Europe/Prague:");

			formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			sb.append(availableTime.getStartTime().format(formatter));
			sb.append("T");
			formatter = DateTimeFormatter.ofPattern("HHmmss");
			sb.append(availableTime.getStartTime().format(formatter));
			sb.append("\r\n");

			sb.append("DTEND;TZID=Europe/Prague:");
			formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			sb.append(availableTime.getEndTime().format(formatter));
			sb.append("T");
			formatter = DateTimeFormatter.ofPattern("HHmmss");
			sb.append(availableTime.getEndTime().format(formatter));
			sb.append("\r\n");

			sb.append("DESCRIPTION:\r\n" + "CREATED:20181201T093806Z\r\n" + "DTSTAMP:20181201T093806Z\r\n"
					+ "UID:20181201T103807-0jfxtras.org\r\n" + "END:VEVENT\r\n");
		}
		sb.append("END:VCALENDAR");
		return sb.toString();
	}

	private void saveAvailableTimes() {
		HashSet<AvailableTime> hashSetOfAvailableTimes = new HashSet<AvailableTime>();

		for (VEvent event : myCalendar.getVEvents()) {
			AvailableTime newAvailableTime = new AvailableTime();
			newAvailableTime.setUserId(loggedInUser.getUserId());// TODO generovanie id, treba vobec?

			String str = event.getDateTimeStart().getValue().toString();
			LocalDateTime l = LocalDateTime.parse(str.substring(0, str.indexOf('+')));
			newAvailableTime.setStartTime(l);

			str = event.getDateTimeEnd().getValue().toString();
			l = LocalDateTime.parse(str.subSequence(0, str.indexOf('+')));
			newAvailableTime.setEndTime(l);

			hashSetOfAvailableTimes.add(newAvailableTime);
		}
		
		availableTimesDao.saveAvailableTimesWithUserId(hashSetOfAvailableTimes, loggedInUser.getUserId());
		//userDao.get(loggedInUser.getUserId()).setAvailableTimes(hashSetOfAvailableTimes);
	}

}
