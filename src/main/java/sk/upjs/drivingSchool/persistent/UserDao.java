package sk.upjs.drivingSchool.persistent;

import java.util.List;

import sk.upjs.drivingSchool.entity.User;

public interface UserDao {
	
	void add(User user);

	List<User> getAll();
	
	List<User> getAll(String role, boolean active);
	
	void save(User user);

	void delete(long id);

	User get(String username);

	User get(long id);

	User create(String name, String surname,String phone, String username, String email, String password);

	List<User> search(String name);

	void deleteAll();

}
