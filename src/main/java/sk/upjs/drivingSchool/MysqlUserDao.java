package sk.upjs.drivingSchool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class MysqlUserDao implements UserDao{
	
private JdbcTemplate jdbcTemplate;
	
	public MysqlUserDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void add(User user) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName("User");
		simpleJdbcInsert.usingGeneratedKeyColumns("userId");
		simpleJdbcInsert.usingColumns("fname", "lname", "username", "email", "password", "phoneNumber", "dateCreated", 
				"lastModified", "lastLogin", "active", "ridesDone", "role");
		Map<String,Object> hodnoty = new HashMap<>();
		hodnoty.put("fname",user.getFname());
		hodnoty.put("lname",user.getLname());
		hodnoty.put("username",user.getUsername());
		hodnoty.put("email",user.getEmail());
		hodnoty.put("password",user.getPassword());
		hodnoty.put("phoneNumber",user.getPhoneNumber());
		hodnoty.put("dateCreated",user.getDateCreated());
		hodnoty.put("lastModified",user.getLastModified());
		hodnoty.put("lastLogin",user.getLastLogin());
		hodnoty.put("active",user.isActive());
		hodnoty.put("ridesDone",user.getRidesDone());
		hodnoty.put("role", user.getRole());
		Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
		user.setUserId(id);
	}

	@Override
	public List<User> getAll() {
		String sql = "SELECT fname, lname, username, email, password, phoneNumber, dateCreated, lastModified, lastLogin, active, ridesDone, role FROM User";
		List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
		AvailableTimesDao availableTimeDao = DaoFactory.INSTANCE.getAvailableTimesDao();
		for (User p : users) {
			p.setAvailableTimes(availableTimeDao.getAvailableTimesByUserId(p.getUserId()));
			//TODO p.setReservation
		}
		return users;
	}

	@Override
	public void save(User u) throws NullPointerException {
		if (u == null) 
			throw new NullPointerException("User cannot be null");
		if (u.getUserId() == null) {
			add(u);
		} else {
			String sql = "UPDATE User SET fname = ?, lname = ?, username = ?, email = ?,"
					+ " password = ?, phoneNumber = ?, dateCreated = ?, lastModified = ?,"
					+ " lastLogin = ?, active = ?, ridesDone = ?, role "
					+ "WHERE id = ?";
			jdbcTemplate.update(sql, u.getFname(), u.getLname(), u.getUsername(), u.getEmail(),
					u.getPassword(), u.getPhoneNumber(), u.getDateCreated(), u.getLastModified(),
					u.getLastLogin(), u.isActive(),	u.getRidesDone(), u.getRole(),
					u.getUserId());
		}
	}

	@Override
	public void delete(long id){
		int deleted = jdbcTemplate.update("DELETE FROM User WHERE id = ?", id);
		if (deleted == 0) {
			//throw new UserNotFoundException(id);
			System.out.println("UserNotFoundException, id:" + id);
		}
	}

	@Override
	public List<User> getAll(String role, boolean active) {
		List<User> list = getAll();
		for(User u : list) {
			if(u.getRole() != role || u.isActive() != active) {
				list.remove(u);
			}
		}
		return list;
	}

	/*@Override
	public User create(String email, String password) throws DuplicateKeyException {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public User get(String username) throws EmptyResultDataAccessException {
		//FIXME prerobit
		User thisUser = new User();
		for(User u : getAll()) {
			if(u.getUsername() == username) {
				thisUser = u;
				break;
			}
		}
		return thisUser;
	}

	@Override
	public User get(long id) throws EmptyResultDataAccessException {
		//FIXME prerobit
				User thisUser = new User();
				for(User u : getAll()) {
					if(u.getUserId() == id) {
						thisUser = u;
						break;
					}
				}
				return thisUser;
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
