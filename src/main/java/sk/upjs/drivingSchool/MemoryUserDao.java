package sk.upjs.drivingSchool;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCrypt;

import sk.upjs.drivingSchool.login.Authenticator;

public class MemoryUserDao implements UserDao {

	private List<User> users = new ArrayList<>();
	private long lastId = 0;

	@Override
	public String toString() {
		return "MemoryUserDao [users=" + users + ", lastId=" + lastId + "]";
	}

	public MemoryUserDao() {
		// TODO pre testovacie ucely - potom zmazat
		// FIXME user nema vsetko vyplnene
		User u = new User();
		
		u.setFname("Andrej");
		u.setLname("Kiska");
		u.setUsername("o");
		u.setPassword(hashPassword("o"));
		u.setEmail("prezident@prezident.sk");
		u.setPhoneNumber("0900 000 000");
		u.setRole(Role.ADMIN.getName());
		u.setRidesDone(100);
		u.setDateCreated(LocalDateTime.now());
		u.setLastModified(u.getDateCreated());
		u.setActive(true);		
		this.add(u);
		AvailableTime time = new AvailableTime();
		time.setStartTime(LocalDateTime.now().withHour(3));
		time.setEndTime(LocalDateTime.now().withHour(12));
		time.setUserId(u.getId());
		HashSet<AvailableTime> times = new HashSet<>();
		times.add(time);
		u.setAvailableTimes(times);
		

		User u2 = new User();
		u2.setFname("Mária");
		u2.setLname("Trošková");
		u2.setUsername("i");
		u2.setEmail("i");
		u2.setPassword(hashPassword("i"));
		u2.setPhoneNumber("0900 111 111");
		u2.setDateCreated(LocalDateTime.now());
		u2.setLastModified(u.getDateCreated());
		u2.setActive(true);
		this.add(u2);		
		time = new AvailableTime();
		times = new HashSet<>();
		time.setStartTime(LocalDateTime.now().withHour(6));
		time.setEndTime(LocalDateTime.now().withHour(11));
		time.setUserId(u2.getId());
		times.add(time);
		time = new AvailableTime();
		time.setStartTime(LocalDateTime.now().withHour(12));
		time.setEndTime(LocalDateTime.now().withHour(15));
		time.setUserId(u2.getId());
		times.add(time);
		u2.setAvailableTimes(times);
		
		
		User u3 = new User();
		u3.setFname("Neaktivny");
		u3.setLname("Student");
		u3.setUsername("i");
		u3.setEmail("i");
		u3.setPassword(hashPassword("i"));
		u3.setDateCreated(LocalDateTime.now());
		u3.setLastModified(u.getDateCreated());
		u3.setRole(Role.STUDENT.getName());
		u3.setActive(false);
		
		this.add(u3);
	}
	// TODO pre testovacie ucely - potom zmazat
	public String hashPassword(String plainText) {
		String salt = BCrypt.gensalt();
		return BCrypt.hashpw(plainText, salt);
	}
	
	@Override
	public void add(User user) {
		user.setId(++lastId);
		users.add(user);
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return users;
	}

	@Override
	public void save(User user) {
		if (user != null) {
			if (user.getId() == null) {
				add(user);
			} else {
				for (int i = 0; i < users.size(); i++) {
					if (users.get(i).getId().equals(user.getId())) {
						users.set(i, user);
						break;
					}
				}
			}
		}
	}

	@Override
	public void delete(long id) {
		Iterator<User> it = users.iterator();
		while (it.hasNext()) {
			User p = it.next();
			if (p.getId().equals(id)) {
				it.remove();
				return;
			}
		}
	}

	/*@Override
	public User create(String email, String password) throws DuplicateKeyException {
		User u = new User();
		u.setPassword(password);
		u.setEmail(email);
		this.add(u);
		return u;
	}*/
	
	@Override
	public User create(String name, String surname, String phone, String username, String email, String password)  {
		User u = new User();
		u.setFname(name);
		u.setLname(surname);
		u.setPhoneNumber(phone);
		u.setUsername(username);
		u.setPassword(password);
		u.setEmail(email);
		this.add(u);
		return u;
	}

	@Override
	public User get(String username) throws EmptyResultDataAccessException {
		Iterator<User> it = users.iterator();
		while (it.hasNext()) {
			User p = it.next();
			if (p.getUsername().equals(username)) {
				return p;
			}
		}
		return null;
	}
	
	@Override
	public User get(long id) throws EmptyResultDataAccessException {
		Iterator<User> it = users.iterator();
		while (it.hasNext()) {
			User p = it.next();
			if (p.getId()==id) {
				return p;
			}
		}
		return null;
	}

	@Override
	public List<User> getAll(String role, boolean active) {
		List<User> list = new ArrayList<User>();
		for (User u : getAll()) {
			if (u.getRole().equals(role) && u.isActive() == active) {
				list.add(u);
			}
		}
		return list;
	}
}
