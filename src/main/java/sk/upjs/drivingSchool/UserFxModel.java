package sk.upjs.drivingSchool;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sk.upjs.drivingSchool.User;

public class UserFxModel {
	
	private User user;
	private StringProperty fname = new SimpleStringProperty();
	private StringProperty lname = new SimpleStringProperty();
	private StringProperty username = new SimpleStringProperty();
	private StringProperty email = new SimpleStringProperty();
	private StringProperty password = new SimpleStringProperty();
	private StringProperty passwordAgain = new SimpleStringProperty();
	private StringProperty phoneNumber = new SimpleStringProperty();
	private ObjectProperty<LocalDateTime> dateCreated = new SimpleObjectProperty<>();
	private ObjectProperty<LocalDateTime> lastModified = new SimpleObjectProperty<>();
	private ObjectProperty<LocalDateTime> lastLogin = new SimpleObjectProperty<>();
	private BooleanProperty active = new SimpleBooleanProperty();
	private IntegerProperty ridesDone = new SimpleIntegerProperty();
	private StringProperty role = new SimpleStringProperty();
	private HashSet<AvailableTime> availableTimes = new HashSet<AvailableTime>();
	private HashSet<Reservation> reservations = new HashSet<Reservation>();

	public UserFxModel(User user) {
		this.user = user;
		setFname(user.getFname());
		setLname(user.getLname());
		setUsername(user.getUsername());
		setEmail(user.getEmail());
		setPhoneNumber(user.getPhoneNumber());
		setDateCreated(user.getDateCreated());
		setLastModified(user.getLastModified());
		setLastLogin(user.getLastLogin());
		setRole(user.getRole());
		setRidesDone(user.getRidesDone());
		setActive(user.isActive());
		setAvailableTimes(user.getAvailableTimes());
		setReservations(user.getReservations());
	}
	public User getUser() {
		user.setFname(getFname());
		user.setLname(getLname());
		user.setUsername(getUsername());
		user.setEmail(getEmail());
		user.setPhoneNumber(getPhoneNumber());
		user.setDateCreated(getDateCreated());
		user.setLastModified(getLastModified());
		user.setLastLogin(getLastLogin());
		user.setActive(getActive());
		user.setRole(getRole());
		user.setRidesDone(getRidesDone());
		user.setAvailableTimes(getAvailableTimes());
		user.setReservations(getReservations());
		return user;
	}	
	
	//TO-DO get, set a StringProperty pre vsetko ostatne
	
	public String getFname() {
		return fname.get();
	}
	public void setFname(String fname) {
		this.fname.set(fname);
	}
	public StringProperty fnameProperty() {
		return fname;
	}
	
	public String getLname() {
		return lname.get();
	}
	public void setLname(String lname) {
		this.lname.set(lname);
	}
	public StringProperty lnameProperty() {
		return lname;
	}	
	
	public String getUsername() {
		return username.get();
	}
	public void setUsername(String username) {
		this.username.set(username);
	}
	public StringProperty usernameProperty() {
		return this.username;
	}
	
	public String getEmail() {
		return email.get();
	}
	public void setEmail(String email) {
		this.email.set(email);
	}
	public StringProperty emailProperty() {
		return this.email;
	}
	
	public String getPassword() {
		return password.get();
	}
	public void setPassword(String password) {
		this.password.set(password);
	}
	public StringProperty passwordProperty() {
		return this.password;
	}
	
	public String getPasswordAgain() {
		return passwordAgain.get();
	}
	public void setPasswordAgain(String passwordAgain) {
		this.passwordAgain.set(passwordAgain);
	}
	public StringProperty passwordAgainProperty() {
		return this.passwordAgain;
	}
	
	public String getPhoneNumber() {
		return phoneNumber.get();
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber.set(phoneNumber);
	}
	public StringProperty phoneNumberProperty() {
		return this.phoneNumber;
	}
	
	public String getRole() {
		return role.get();
	}
	public void setRole(String role) {
		this.role.setValue(role);
	}
	public StringProperty roleProperty() {
		return this.role;
	}
	
	public Integer getRidesDone() {
		return ridesDone.get();
	}
	public void setRidesDone(Integer ridesDone) {
		this.ridesDone.setValue(ridesDone);
	}
	public IntegerProperty ridesDoneProperty() {
		return this.ridesDone;
	}
	
	public Boolean getActive() {
		return active.get();
	}
	public void setActive(Boolean active) {
		this.active.set(active);
	}
	public BooleanProperty activeProperty() {
		return this.active;
	}
	
	public LocalDateTime getDateCreated() {
		return dateCreated.get();
	}
	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated.set(dateCreated);
	}
	public ObjectProperty<LocalDateTime> dateCreatedProperty() {
		return dateCreated;
	}
	
	public LocalDateTime getLastModified() {
		return lastModified.get();
	}
	public void setLastModified(LocalDateTime astModified) {
		this.lastModified.set(astModified);
	}
	public ObjectProperty<LocalDateTime> lastModifiedProperty() {
		return lastModified;
	}
	
	public LocalDateTime getLastLogin() {
		return lastLogin.get();
	}
	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin.set(lastLogin);
	}
	public ObjectProperty<LocalDateTime> lastLoginProperty() {
		return lastLogin;
	}
	
	public HashSet<AvailableTime> getAvailableTimes() {
		return availableTimes;
	}
	public void setAvailableTimes(HashSet availableTimes) {
		this.availableTimes = availableTimes;
	}
	
	public HashSet<Reservation> getReservations() {
		return reservations;
	}
	public void setReservations(HashSet reservations) {
		this.reservations = reservations;
	}
}
