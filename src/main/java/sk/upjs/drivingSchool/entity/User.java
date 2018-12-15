package sk.upjs.drivingSchool.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class User {
	private Long id;
	private String fname;
	private String lname;
	private String username;
	private String email;
	private String password;
	private String phoneNumber;
	private LocalDateTime dateCreated = LocalDateTime.now();
	private LocalDateTime lastModified = LocalDateTime.now();
	private LocalDateTime lastLogin = LocalDateTime.now();
	private boolean active = true;
	private Integer ridesDone = 0;
	private String role = Role.STUDENT.getName();
	private HashSet<AvailableTime> availableTimes = new HashSet<AvailableTime>();
	private HashSet<Reservation> reservations = new HashSet<Reservation>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String string) {
		this.phoneNumber = string;
	}

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Integer getRidesDone() {
		return ridesDone;
	}

	public void setRidesDone(Integer ridesDone) {
		this.ridesDone = ridesDone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	@Override
	public String toString() {
		return getFname() + " " + getLname();
	}

}
