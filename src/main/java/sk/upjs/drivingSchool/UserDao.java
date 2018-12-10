package sk.upjs.drivingSchool;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;

public interface UserDao {
	
	void add(User user);

	List<User> getAll();
	
	List<User> getAll(String role, boolean active);
	
	void save(User user);

	void delete(long id);

	//User create(String email, String password) throws DuplicateKeyException;

	User get(String username) throws EmptyResultDataAccessException;

	User get(long id) throws EmptyResultDataAccessException;

	User create(String name, String surname,String phone, String username, String email, String password);

	List<User> search(String name);

}
