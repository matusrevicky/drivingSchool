package sk.upjs.drivingSchool.login;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCrypt;

import sk.upjs.drivingSchool.entity.User;
import sk.upjs.drivingSchool.persistent.DaoFactory;
import sk.upjs.drivingSchool.persistent.UserDao;

public enum Authenticator {

	INSTANCE;

	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();

	public UserSession logIn(String username, String password) {
		User user = userDao.get(username);

		if (user == null) {
			throw new UserDoesNotExistException();
		}

		if (BCrypt.checkpw(password, user.getPassword()) == false) {
			throw new BadPasswordException();
		}

		if (!user.isActive()) {
			throw new UserNotActiveException();
		}
		user.setLastLogin(LocalDateTime.now());
		UserSession userSession = new UserSession(user, user.getId(), user.getRole());
		UserSessionManager.INSTANCE.setCurrentUserSession(userSession);

		return userSession;
	}
	
	// podobne ako registracia, ale bez prihlasenia vytvoreneho uzivatela
	public UserSession create(String name, String surname, String phone, String username, String email,
			String password, String passwdAgain) throws UserAlreadyExistsException {
		if (name == null || surname == null  || username == null || email == null || password == null
				|| passwdAgain == null) {
			throw new SomethingInUserIsNullExeption();
		}

		if (name.trim().isEmpty() || surname.trim().isEmpty() || username.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()
				|| passwdAgain.trim().isEmpty()) {
			throw new SomethingInUserIsNullExeption();
		}
		
		if (!validateEmail(email)) {
			throw new EmailNotValidException();
		}

		if (userExists(username)) {
			throw new UserAlreadyExistsException();
		}
		
		if (!password.equals(passwdAgain)) {
			throw new BadPasswordException();
		}

		String hashedPassword = hashPassword(password);

		if (phone == null) {
			phone = "";
		}
		User createdUser = userDao.create(name, surname, phone, username, email, hashedPassword);
		UserSession userSession = new UserSession(createdUser, createdUser.getId(), createdUser.getRole());
		
		return userSession;
	}

	//  po regisracii sa hned aj prihlasi
	public UserSession register(String name, String surname, String phone, String username, String email,
			String password, String passwdAgain) throws UserAlreadyExistsException {
		if (name == null || surname == null  || username == null || email == null || password == null
				|| passwdAgain == null) {
			throw new SomethingInUserIsNullExeption();
		}

		if (name.trim().isEmpty() || surname.trim().isEmpty() || username.trim().isEmpty() || email.trim().isEmpty() || password.trim().isEmpty()
				|| passwdAgain.trim().isEmpty()) {
			throw new SomethingInUserIsNullExeption();
		}
		
		if (!validateEmail(email)) {
			throw new EmailNotValidException();
		}

		if (userExists(username)) {
			throw new UserAlreadyExistsException();
		}
		
		if (!password.equals(passwdAgain)) {
			throw new BadPasswordException();
		}

		String hashedPassword = hashPassword(password);

		if (phone == null) {
			phone = "";
		}
		User createdUser = userDao.create(name, surname, phone, username, email, hashedPassword);
		UserSession userSession = new UserSession(createdUser, createdUser.getId(), createdUser.getRole());
		UserSessionManager.INSTANCE.setCurrentUserSession(userSession);

		return userSession;
	}

	// pouzite pomale hashovanie
	public String hashPassword(String plainText) {
		String salt = BCrypt.gensalt();
		return BCrypt.hashpw(plainText, salt);
	}

	private boolean userExists(String username) {
		try {
			if (userDao.get(username) != null) {
				return true;
			}
		} catch (EmptyResultDataAccessException ignored) {
			return false;
		}
		return false;
	}

	// regularny vyraz inspirovany https://www.regular-expressions.info/email.html
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public static boolean validateEmail(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}

}
