package sk.upjs.drivingSchool.persistent;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sk.upjs.drivingSchool.entity.User;
import sk.upjs.drivingSchool.login.Authenticator;

class MysqlUserDaoTest {

	// predtym ako zacnem s testami treba prepnut dao.factory na testovaciu databazu
	private static UserDao dao = DaoFactory.INSTANCE.getUserDao();

	@BeforeEach
	void emptyDatabase() {
		dao.deleteAll();
	}
	

	@Test
	void create() { // zaroven otestovalo aj add, getAll
		User user1 = dao.create("student1", "student1", "000032432", "std1", "s1@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		User user2 = dao.create("student2", "student2", "070322342", "std2", "s2@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		List<User> list = new ArrayList<>();
		list.add(user1);
		list.add(user2);
		for (int i = 0; i < list.size(); i++) {
			assertEquals(list.get(i).getId(), dao.getAll().get(i).getId());
		}
	}

	@Test
	void search() {
		User user1 = dao.create("student1", "student1", "000032432", "std1", "s1@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		User user2 = dao.create("student2", "student2", "070322342", "std2", "s2@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		List<User> list = new ArrayList<>();
		List<User> list2 = dao.search("ent1");

		list.add(user1);
		list.add(user2);

		assertEquals(1, list2.size());
		assertEquals(list2.get(0).getId(), user1.getId());

	}
	
	@Test
	void getUsingUserName() {
		User user1 = dao.create("student1", "student1", "000032432", "std1", "s1@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		User user2 = dao.create("student2", "student2", "070322342", "std2", "s2@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		User user3 = dao.get(user1.getUsername());
		
		assertEquals(user1.getId(), user3.getId());
	}
	
	@Test
	void getUsingId() {
		User user1 = dao.create("student1", "student1", "000032432", "std1", "s1@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		User user2 = dao.create("student2", "student2", "070322342", "std2", "s2@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		User user3 = dao.get(user1.getId());
		
		assertEquals(user1.getId(), user3.getId());
	}
	
	@Test
	void deleteUsingId() {
		User user1 = dao.create("student1", "student1", "000032432", "std1", "s1@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		User user2 = dao.create("student2", "student2", "070322342", "std2", "s2@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		dao.delete(user1.getId());
		List<User> list = new ArrayList<>();
		List<User> list2 = dao.getAll();
		list.add(user1);
		list.add(user2);
		
		assertEquals(1, list2.size());
		assertEquals(list2.get(0).getId(), user2.getId());
	}
	
	@Test
	void save() {
		User user1 = dao.create("student1", "student1", "000032432", "std1", "s1@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		User user2 = dao.create("student2", "student2", "070322342", "std2", "s2@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		
		user1.setFname("jano");
		dao.save(user1);
		
		List<User> list2 = dao.getAll();
		assertEquals(2, list2.size());
		assertEquals(list2.get(0).getFname(), user1.getFname());
		
	}
	
}
