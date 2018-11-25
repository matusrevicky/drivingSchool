package sk.upjs.drivingSchool.login;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCrypt;

import sk.upjs.drivingSchool.DaoFactory;
import sk.upjs.drivingSchool.User;
import sk.upjs.drivingSchool.UserDao;

public enum Authenticator {

	INSTANCE;

	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();

	public UserSession logIn(String email, String password)
			throws BadPasswordException, EmptyResultDataAccessException {
		User user = userDao.get(email);

		if (!BCrypt.checkpw(password, user.getPassword())) {
			throw new BadPasswordException();
		}

		UserSession userSession = new UserSession(user, user.getUserId());
		UserSessionManager.INSTANCE.setCurrentUserSession(userSession);

		return userSession;
	}

	public UserSession register(String email, String password) throws UserAlreadyExistsException {
		if (userExists(email)) {
			throw new UserAlreadyExistsException();
		}

		String hashedPassword = hashPassword(password);

		User createdUser = userDao.create(email, hashedPassword);
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
