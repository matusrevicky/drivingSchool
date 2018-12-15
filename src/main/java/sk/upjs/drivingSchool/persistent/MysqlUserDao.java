package sk.upjs.drivingSchool.persistent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import sk.upjs.drivingSchool.entity.Role;
import sk.upjs.drivingSchool.entity.User;
import sk.upjs.drivingSchool.login.Authenticator;

public class MysqlUserDao implements UserDao {

	private JdbcTemplate jdbcTemplate;

	public MysqlUserDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void deleteAll() {
		jdbcTemplate.update("SET SQL_SAFE_UPDATES = 0;");;
		jdbcTemplate.update("DELETE FROM reservation");
		jdbcTemplate.update("DELETE FROM availabletime");
		jdbcTemplate.update("DELETE FROM user");
	}

	@Override
	public void add(User user) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName("User");
		simpleJdbcInsert.usingGeneratedKeyColumns("id");
		simpleJdbcInsert.usingColumns("fname", "lname", "username", "email", "password", "phoneNumber", "dateCreated",
				"lastModified", "lastLogin", "active", "ridesDone", "role");
		Map<String, Object> hodnoty = new HashMap<>();
		hodnoty.put("fname", user.getFname());
		hodnoty.put("lname", user.getLname());
		hodnoty.put("username", user.getUsername());
		hodnoty.put("email", user.getEmail());
		hodnoty.put("password", user.getPassword());
		hodnoty.put("phoneNumber", user.getPhoneNumber());
		hodnoty.put("dateCreated", user.getDateCreated());
		hodnoty.put("lastModified", user.getLastModified());
		hodnoty.put("lastLogin", user.getLastLogin());
		hodnoty.put("active", user.isActive());
		hodnoty.put("ridesDone", user.getRidesDone());
		hodnoty.put("role", user.getRole());
		Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
		user.setId(id);
	}

	@Override
	public List<User> getAll() {
		String sql = "SELECT id, fname, lname, username, email, password, phoneNumber, dateCreated, "
				+ "lastModified, lastLogin, active, ridesDone, role FROM User";
		List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
		AvailableTimesDao availableTimeDao = DaoFactory.INSTANCE.getAvailableTimesDao();
		ReservationDao reservationDao = DaoFactory.INSTANCE.getReservationDao();
		for (User u : users) {
			u.setAvailableTimes(availableTimeDao.getAvailableTimesByUserId(u.getId()));
			if (u.getRole().equals(Role.TEACHER.toString())) {
				u.setReservations(reservationDao.getReservationsByInstructorId(u.getId()));
			} else if (u.getRole().equals(Role.TEACHER.toString())) {
				u.setReservations(reservationDao.getReservationsByStudentId(u.getId()));
			}
		}
		return users;
	}

	@Override
	public void save(User u) throws NullPointerException {
		if (u == null)
			throw new NullPointerException("User cannot be null");
		if (u.getId() == null) {
			add(u);
		} else {
			if ((u.getPassword() == null || u.getPassword().isEmpty())) {
				String sql = "UPDATE User SET fname = ?, lname = ?, username = ?, email = ?,"
						+ " phoneNumber = ?, dateCreated = ?, lastModified = ?,"
						+ " lastLogin = ?, active = ?, ridesDone = ?, role = ?" + "WHERE id = ?";
				jdbcTemplate.update(sql, u.getFname(), u.getLname(), u.getUsername(), u.getEmail(), u.getPhoneNumber(),
						u.getDateCreated(), u.getLastModified(), u.getLastLogin(), u.isActive(), u.getRidesDone(),
						u.getRole(), u.getId());
			} else {
				String sql = "UPDATE User SET fname = ?, lname = ?, username = ?, email = ?,"
						+ " password = ?, phoneNumber = ?, dateCreated = ?, lastModified = ?,"
						+ " lastLogin = ?, active = ?, ridesDone = ?, role = ?" + "WHERE id = ?";
				jdbcTemplate.update(sql, u.getFname(), u.getLname(), u.getUsername(), u.getEmail(),
						Authenticator.INSTANCE.hashPassword(u.getPassword()), u.getPhoneNumber(), u.getDateCreated(),
						u.getLastModified(), u.getLastLogin(), u.isActive(), u.getRidesDone(), u.getRole(), u.getId());
			}
		}
	}

	@Override
	public void delete(long id) {
		int deleted = jdbcTemplate.update("DELETE FROM User WHERE id = ?", id);
		if (deleted == 0) {
			// throw new UserNotFoundException(id);
			System.out.println("UserNotFoundException, id:" + id);
		}
	}

	@Override
	public List<User> getAll(String role, boolean active) {
		List<User> originalList = getAll();
		List<User> returnList = new ArrayList<User>();
		for (User u : originalList) {
			if (u.getRole().equals(role) && u.isActive() == active) {
				returnList.add(u);
			}
		}
		return returnList;
	}

	/*
	 * @Override public User create(String email, String password) throws
	 * DuplicateKeyException { // TODO Auto-generated method stub return null; }
	 */

	@Override
	public User get(String username) {
		// FIXME prerobit
		User thisUser = null;
		for (User u : getAll()) {
			String u1 = u.getUsername();
			if (u1.equals(username)) {
				thisUser = u;
				break;
			}
		}
		return thisUser;
	}

	@Override
	public User get(long id)  {
		// FIXME prerobit
		User thisUser = new User();
		for (User u : getAll()) {
			if (u.getId() == id) {
				thisUser = u;
				break;
			}
		}
		return thisUser;
	}

	public List<User> search(String name) {
		String sql = "SELECT id, fname, lname, username, email, password, phoneNumber, dateCreated, "
				+ "lastModified, lastLogin, active, ridesDone, role FROM User where fname LIKE '%" + name + "%'";
		List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
		AvailableTimesDao availableTimeDao = DaoFactory.INSTANCE.getAvailableTimesDao();
		ReservationDao reservationDao = DaoFactory.INSTANCE.getReservationDao();
		for (User u : users) {
			u.setAvailableTimes(availableTimeDao.getAvailableTimesByUserId(u.getId()));
			if (u.getRole().equals(Role.TEACHER.toString())) {
				u.setReservations(reservationDao.getReservationsByInstructorId(u.getId()));
			} else if (u.getRole().equals(Role.TEACHER.toString())) {
				u.setReservations(reservationDao.getReservationsByStudentId(u.getId()));
			}
		}
		return users;
	}

	@Override
	public User create(String name, String surname, String phone, String username, String email, String password) {
		User u = new User();
		u.setFname(name);
		u.setLname(surname);
		u.setPhoneNumber(phone);
		u.setUsername(username);
		u.setEmail(email);
		u.setPassword(password);
		this.add(u);
		return u;
	}

}
