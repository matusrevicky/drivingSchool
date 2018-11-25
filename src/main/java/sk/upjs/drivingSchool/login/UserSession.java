package sk.upjs.drivingSchool.login;


import java.time.LocalDateTime;

import sk.upjs.drivingSchool.User;

public class UserSession {

    private long userId;
    private User loggedUser;
    private LocalDateTime logInDate;

    UserSession(final User loggedUser, final long userId) {
        this.loggedUser = loggedUser;
        this.userId = userId;
        this.logInDate = LocalDateTime.now();
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

    public void setUserId(final int userId) {
        this.userId = userId;
    }

}
