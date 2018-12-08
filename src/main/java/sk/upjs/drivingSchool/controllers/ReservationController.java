package sk.upjs.drivingSchool.controllers;

import java.net.URL;

import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import jfxtras.labs.icalendaragenda.scene.control.agenda.ICalendarAgenda;
import jfxtras.labs.icalendarfx.VCalendar;
import jfxtras.labs.icalendarfx.components.VEvent;
import jfxtras.scene.control.agenda.Agenda;
import sk.upjs.drivingSchool.App;
import sk.upjs.drivingSchool.AvailableTime;
import sk.upjs.drivingSchool.AvailableTimesDao;
import sk.upjs.drivingSchool.DaoFactory;
import sk.upjs.drivingSchool.Reservation;
import sk.upjs.drivingSchool.ReservationDao;
import sk.upjs.drivingSchool.Role;
import sk.upjs.drivingSchool.User;
import sk.upjs.drivingSchool.UserDao;
import sk.upjs.drivingSchool.UserFxModel;
import sk.upjs.drivingSchool.login.UserSessionManager;

@SuppressWarnings("restriction") // FIXME vymaz tento supress warning
public class ReservationController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Label currentUserName;

	@FXML
	private Button homeButton;

	@FXML
	private Button editMyProfileButton;

	@FXML
	private Button changePasswordButton;

	@FXML
	private Button avaibleTimesButton;

	@FXML
	private Button showUsersButton;

	@FXML
	private Button signOutButton;

	@FXML
	private Button weekLeftButton;

	@FXML
	private Button weekRightButton;

	@FXML
	private Button saveButton;

	@FXML
	private ComboBox<User> studentComboBox;

	@FXML
	private TextField searchStudentTextField;

	@FXML
	private TextField searchInstructorTextField;

	@FXML
	private ComboBox<User> instructorComboBox;

	@FXML
	private CheckBox showAllInstructors;

	@FXML
	private CheckBox showAllStudents;

	@FXML
	private ComboBox<User> studentForEventComboBox;

	@FXML
	private Label instructorOnlyLabel;

	@FXML
	private Agenda calendarOriginal;
	private BorderPane borderPane = null;
	private ICalendarAgenda calendarAgenda;
	private VCalendar myCalendar;
	private ArrayList<VEvent> eventsBeforeChange = new ArrayList<VEvent>();

	private ReservationDao reservationDao = DaoFactory.INSTANCE.getReservationDao();
	private AvailableTimesDao availableTimesDao = DaoFactory.INSTANCE.getAvailableTimesDao();
	private User loggedInUser;
	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
	private UserFxModel instructorModel;
	private UserFxModel studentModel;
	private UserFxModel studentForALessonModel;

	private boolean showAllStudentsChecked = false;
	private boolean showAllInstructorsChecked = false;
	private boolean showAllStudentsChanged = false;
	private boolean showAllInstructorsChanged = false;
	private String searchInstructorString = "";
	private String searchStudentString = "";

	private void initializeUser() {
		long userId = UserSessionManager.INSTANCE.getCurrentUserSession().getUserId();
		loggedInUser = userDao.get(userId);
	}

	@FXML
	void initialize() {
		{
			initializeUser();
			currentUserName.setText("Uzivatel: " + loggedInUser.getUsername() + "; rola " + loggedInUser.getRole());
			checkSaveComponentsVisibility();
			initializeLeftMenuComponents();
			refreshComboBoxes();
			if (loggedInUser.getRole().equals(Role.TEACHER.getName())) {
				showAllStudents.setSelected(true);
				showAllStudentsChecked = true;
			} else if (loggedInUser.getRole().equals(Role.STUDENT.getName())) {
				showAllInstructors.setSelected(true);
				showAllInstructorsChecked = true;
			} else {
				showAllStudents.setSelected(true);
				showAllStudentsChecked = true;
				showAllInstructors.setSelected(true);
				showAllInstructorsChecked = true;
			}
			initializeComponents();
			refreshComboBoxes();
			if (loggedInUser.getRole().equals(Role.TEACHER.getName())) {
				instructorModel = new UserFxModel(loggedInUser);
				instructorComboBox.getSelectionModel().select(loggedInUser);
			} else if (loggedInUser.getRole().equals(Role.STUDENT.getName())) {
				studentModel = new UserFxModel(loggedInUser);
				studentComboBox.getSelectionModel().select(loggedInUser);
			} else {
				// showAllInstructors.setSelected(true);
				// showAllStudentsChecked = true;
			}
		}
	}

	private void initializeLeftMenuComponents() {
		if (loggedInUser.getRole().equals(Role.STUDENT.getName())) {
			showUsersButton.setDisable(true);
		}

		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// App.switchScene(new HomeSceneController(), "HomeScreen.fxml");
				App.switchScene(new ReservationController(), "ReservationScreen.fxml");
			}
		});
		editMyProfileButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				UserEditController editController = new UserEditController(loggedInUser);
				App.showModalWindow(editController, "UserEdit.fxml");
				// tento kod sa spusti az po zatvoreni okna
				refreshComboBoxes();
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
	}

	private void initializeComponents() {

		showAllInstructors.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				showAllInstructorsChecked = newValue;
				showAllInstructorsChanged = true;
				refreshComboBoxes();
			}
		});
		showAllStudents.selectedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				showAllStudentsChecked = newValue;
				showAllStudentsChanged = true;
				refreshComboBoxes();
			}
		});

		instructorComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
			@Override
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
				if (newValue == null) {
					instructorModel = null;
				} else {
					instructorModel = new UserFxModel(newValue);
				}
				initializeCalendar();
			}
		});
		studentComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
			@Override
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
				if (newValue == null) {
					studentModel = null;
				} else {
					studentModel = new UserFxModel(newValue);
				}
				initializeCalendar();
			}
		});
		studentForEventComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
			@Override
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
				if (newValue == null) {
					studentForALessonModel = null;
				} else {
					studentForALessonModel = new UserFxModel(newValue);
				}
				// initializeCalendar();
			}
		});

		searchInstructorTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue == null) {
					searchInstructorString = "";
				} else {
					searchInstructorString = newValue;
				}
				refreshComboBoxes();
			}
		});
		searchStudentTextField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue == null) {
					searchStudentString = "";
				} else {
					searchStudentString = newValue;
				}
				refreshComboBoxes();
			}
		});

		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				HashSet<Reservation> newReservationSet = new HashSet<Reservation>();
				for (VEvent calendarEvent : myCalendar.getVEvents()) {// Reservation r :
																		// instructorModel.getReservations()
					// ak ma event rovnake id ako v modeli, skopiruj string do modelu (to je update)
					// nove eventy uz v modeli su
					// vymazane eventy sa neskopiruju
					for (Reservation modelReservation : instructorModel.getReservations()) {
						VEvent modelEvent = VEvent.parse(modelReservation.getEventString());
						if (calendarEvent.getUniqueIdentifier().getValue()
								.equals(modelEvent.getUniqueIdentifier().getValue())) {
							modelReservation.setEventString(calendarEvent.toContent());
							newReservationSet.add(modelReservation);
							break;
						}
					}
				}
				instructorModel.setReservations(newReservationSet);
				saveReservations();
				userDao.get(loggedInUser.getId()).setLastModified(LocalDateTime.now());
				userDao.get(studentForALessonModel.getUser().getId()).setLastModified(LocalDateTime.now());
				initializeCalendar();
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

		initializeCalendar();
	}

	private void refreshComboBoxes() {

		if (showAllStudentsChanged) {
			if (showAllStudentsChecked) {
				studentComboBox.setItems(null);
				studentComboBox.setDisable(true);
			} else {
				studentComboBox.setDisable(false);
				List<User> students = userDao.getAll(Role.STUDENT.getName(), true);
				studentComboBox.setItems(FXCollections.observableList(students));
				if (!students.isEmpty()) {
					if (searchStudentString == null || searchStudentString.isEmpty()) {
						studentComboBox.getSelectionModel().select(students.get(0));
					} else {
						System.out.println("TODO filter");// TODO filter podla mena
					}
				}
			}
		}

		if (showAllInstructorsChanged) {
			if (showAllInstructorsChecked) {
				instructorComboBox.setItems(null);
				instructorComboBox.setDisable(true);
			} else {
				instructorComboBox.setDisable(false);
				List<User> instructors = userDao.getAll(Role.TEACHER.getName(), true);
				instructorComboBox.setItems(FXCollections.observableList(instructors));
				if (!instructors.isEmpty()) {
					if (searchInstructorString == null || searchInstructorString.isEmpty()) {
						instructorComboBox.getSelectionModel().select(instructors.get(0));
					} else {
						System.out.println("TODO filter");// TODO filter podla mena
					}
				}
			}
		}

		List<User> students = userDao.getAll(Role.STUDENT.getName(), true);
		studentForEventComboBox.setItems(FXCollections.observableList(students));
		if (!students.isEmpty()) {
			if (searchStudentString == null || searchStudentString.isEmpty()) {
				studentForEventComboBox.getSelectionModel().select(students.get(0));
			} else {
				System.out.println("TODO filter");// TODO filter podla mena
			}
		}

		showAllInstructorsChanged = false;
		showAllStudentsChanged = false;
	}

	private void checkSaveComponentsVisibility() {

		if (loggedInUser.getRole().equals(Role.STUDENT.getName())) {
			saveButton.setVisible(false);
			instructorOnlyLabel.setVisible(false);
			studentForEventComboBox.setVisible(false);
		} else {
			saveButton.setVisible(true);
			instructorOnlyLabel.setVisible(true);
			studentForEventComboBox.setVisible(true);
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
		String s = reservationsToString();
		// System.out.println("---------------");
		// System.out.println(s);
		// System.out.println("---------------");
		myCalendar = VCalendar.parse(s);
		calendarAgenda = new ICalendarAgenda(myCalendar);
		borderPane.setCenter(calendarAgenda);
		eventsBeforeChange.removeAll(eventsBeforeChange);
		eventsBeforeChange.addAll(myCalendar.getVEvents());

		// v novom kalendari musi byt novy listener
		Collection<VEvent> vevents = myCalendar.getVEvents();
		myCalendar.getVEvents().addListener(new ListChangeListener<VEvent>() {
			@Override
			public void onChanged(Change<? extends VEvent> c) {
				for (VEvent calendarEvent : myCalendar.getVEvents()) {
					boolean jeNovy = true;
					for (Reservation modelReservation : instructorModel.getReservations()) {
						VEvent modelEvent = VEvent.parse(modelReservation.getEventString());
						String s = calendarEvent.getUniqueIdentifier().getValue();
						String s2 = modelEvent.getUniqueIdentifier().getValue();
						if (s.equals(s2)) {
							jeNovy = false;
							break;
						}
					}
					if (jeNovy) {
						// System.out.println("novy event id: " + e.getUniqueIdentifier().getValue());
						// System.out.println("novy event string: " + e.toContent());
						HashSet<Reservation> reservations = instructorModel.getReservations();
						Reservation r = new Reservation();
						calendarEvent.setSummary(instructorModel.getUser().toString() + " \n"
								+ studentForALessonModel.getUser().toString());
						r.setEventString(calendarEvent.toContent());
						r.setInstructorId(instructorModel.getUser().getId());
						r.setStudentId(studentForALessonModel.getUser().getId());
						r.setSeenByStudent(false);
						reservations.add(r);
						instructorModel.setReservations(reservations);
						break;
					}
				}
				eventsBeforeChange.removeAll(eventsBeforeChange);
				eventsBeforeChange.addAll(myCalendar.getVEvents());
			}
		});
	}

	private String reservationsToString() {
		StringBuilder sb = new StringBuilder();
		DateTimeFormatter formatter;
		sb.append("BEGIN:VCALENDAR\r\n");
		HashSet<Reservation> reservationSet = new HashSet<Reservation>();
		if (showAllInstructorsChecked && showAllStudentsChecked) {
			reservationSet = reservationDao.getAll();
			for (Reservation reservation : reservationSet) {
				sb.append(reservation.getEventString());
				sb.append("\r\n");
			}
		} else if (showAllInstructorsChecked) {
			if (studentModel != null) {
				reservationSet = reservationDao.getReservationsByStudentId(studentModel.getUser().getId());
				for (Reservation reservation : reservationSet) {
					sb.append(reservation.getEventString());
					sb.append("\r\n");
				}
			}
		} else if (showAllStudentsChecked) {
			if (instructorModel != null) {
				reservationSet = reservationDao.getReservationsByInstructorId(instructorModel.getUser().getId());
				for (Reservation reservation : reservationSet) {
					sb.append(reservation.getEventString());
					sb.append("\r\n");
				}
			}
		} else {
			if (instructorModel != null && studentModel != null) {
				reservationSet = reservationDao.getReservationsByBothId(instructorModel.getUser().getId(),
						studentModel.getUser().getId());
				for (Reservation reservation : reservationSet) {
					sb.append(reservation.getEventString());
					sb.append("\r\n");
				}
			}
		}
		if (instructorModel != null) {
			instructorModel.setReservations(reservationSet);
		}
		sb.append("END:VCALENDAR");
		String s = sb.toString();
		s.replaceAll("//", "/");
		return s;
	}

	private void saveReservations() {
		reservationDao.saveReservations(instructorModel.getReservations(), instructorModel.getUser().getId());
	}

}
