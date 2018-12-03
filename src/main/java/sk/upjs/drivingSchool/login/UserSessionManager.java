package sk.upjs.drivingSchool.login;

public enum UserSessionManager {
    INSTANCE;

    private UserSession currentUserSession;

    public UserSession getCurrentUserSession() {
        return currentUserSession;
    }

    public void setCurrentUserSession(final UserSession currentUserSession) {
        this.currentUserSession = currentUserSession;
    }
}
