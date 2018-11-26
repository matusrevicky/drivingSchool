package sk.upjs.drivingSchool.login;

import java.time.LocalDateTime;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCrypt;

import sk.upjs.drivingSchool.DaoFactory;
import sk.upjs.drivingSchool.User;
import sk.upjs.drivingSchool.UserDao;

public enum Authenticator {

	INSTANCE;

	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();

	public UserSession logIn(String email, String password) {
		User user = userDao.get(email);

		if (user == null) {
			throw new UserAlreadyExistsException();
		}

		if (BCrypt.checkpw(password, user.getPassword()) == false) {
			throw new BadPasswordException();
		}
		
		user.setLastLogin(LocalDateTime.now());

		UserSession userSession = new UserSession(user, user.getUserId());
		UserSessionManager.INSTANCE.setCurrentUserSession(userSession);

		return userSession;
	}

	public UserSession register(String name, String surname, String username, String email, String password,
			String passwdAgain) throws UserAlreadyExistsException {
		if (name.isEmpty() || surname.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()
				|| passwdAgain.isEmpty()) {
			throw new UserAlreadyExistsException();
		}

		if (userExists(email) || !password.equals(passwdAgain)) {
			throw new UserAlreadyExistsException();
		}

		String hashedPassword = hashPassword(password);

		User createdUser = userDao.create(name, surname, username, email, hashedPassword);
		UserSession userSession = new UserSession(createdUser, createdUser.getUserId());
		UserSessionManager.INSTANCE.setCurrentUserSession(userSession);

		return userSession;
	}

	public String hashPassword(String plainText) {
		String salt = BCrypt.gensalt();
		return BCrypt.hashpw(plainText, salt);
	}

	private boolean userExists(String email) {
		try {
			if (userDao.get(email) != null) {
				return true;
			}
		} catch (EmptyResultDataAccessException ignored) {
			return false;
		}
		return false;
	}

}
