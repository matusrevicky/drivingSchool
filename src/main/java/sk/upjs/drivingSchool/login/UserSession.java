package sk.upjs.drivingSchool.login;

import java.time.LocalDateTime;

import sk.upjs.drivingSchool.entity.User;

public class UserSession {

	private long userId;
	private User loggedUser;
	private String role;
	private LocalDateTime logInDate;

	UserSession(final User loggedUser, final long userId, final String role) {
		this.loggedUser = loggedUser;
		this.userId = userId;
		this.logInDate = LocalDateTime.now();
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(final String role) {
		this.role = role;
	}

	public LocalDateTime getLogInDate() {
		return logInDate;
	}

	public void setLogInDate(final LocalDateTime logInDate) {
		this.logInDate = logInDate;
	}

	public User getLoggedUser() {
		return loggedUser;
	}

	public void setLoggedUser(final User loggedUser) {
		this.loggedUser = loggedUser;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(final long userId) {
		this.userId = userId;
	}

}
