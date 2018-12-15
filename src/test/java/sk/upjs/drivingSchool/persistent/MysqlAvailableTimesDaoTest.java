package sk.upjs.drivingSchool.persistent;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sk.upjs.drivingSchool.entity.AvailableTime;
import sk.upjs.drivingSchool.entity.Reservation;
import sk.upjs.drivingSchool.entity.User;
import sk.upjs.drivingSchool.login.Authenticator;

class MysqlAvailableTimesDaoTest {
	// predtym ako zacnem s testami treba prepnut dao.factory na testovaciu databazu
	private static UserDao dao = DaoFactory.INSTANCE.getUserDao();
	private static AvailableTimesDao rDao = DaoFactory.INSTANCE.getAvailableTimesDao();

	@BeforeEach
	void emptyDatabase() {
		dao.deleteAll();
	}

	@Test
	void getAllSave() {
		User user1 = dao.create("student1", "student1", "000032432", "std1", "s1@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		User user2 = dao.create("student2", "student2", "070322342", "std2", "s2@s.sk", Authenticator.INSTANCE.hashPassword("s"));

		user1.setRole("teacher");
		dao.save(user1);

		AvailableTime r1 = new AvailableTime();
		r1.setEventString("iba testovacie toto nemoze byt event string");
		r1.setMyUserId(user1.getId());

		AvailableTime r2 = new AvailableTime();
		r2.setEventString("22iba testovacie toto nemoze byt event string");
		r2.setMyUserId(user1.getId());

		HashSet<AvailableTime> hashSet = new HashSet<>();
		hashSet.add(r1);
		hashSet.add(r2);
		rDao.saveAvailableTimesWithUserId(hashSet, user1.getId());

		HashSet<AvailableTime> hashSet2 = rDao.getAllCalendarEvents();

		HashSet<String> hashSetUID = new HashSet<>();

		for (AvailableTime reservation : hashSet2) {
			hashSetUID.add(reservation.getEventString());
		}

		assertTrue(hashSetUID.contains(r1.getEventString()));
		assertTrue(hashSetUID.contains(r2.getEventString()));
	}
	
	@Test
	void getAllSave2() {
		User user1 = dao.create("student1", "student1", "000032432", "std1", "s1@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		User user2 = dao.create("student2", "student2", "070322342", "std2", "s2@s.sk", Authenticator.INSTANCE.hashPassword("s"));

		user1.setRole("teacher");
		dao.save(user1);

		AvailableTime r1 = new AvailableTime();
		r1.setEventString("iba testovacie toto nemoze byt event string");
		r1.setMyUserId(user1.getId());

		AvailableTime r2 = new AvailableTime();
		r2.setEventString("22iba testovacie toto nemoze byt event string");
		r2.setMyUserId(user1.getId());

		HashSet<AvailableTime> hashSet = new HashSet<>();
		hashSet.add(r1);
		hashSet.add(r2);
		rDao.saveAvailableTimesWithUserId(hashSet, user1.getId());

		HashSet<String> hashSet2 = rDao.getAllCalendarEventsUID();


		assertTrue(hashSet2.contains(r1.getEventStringUID()));
		assertTrue(hashSet2.contains(r2.getEventStringUID()));
	}
	
	@Test
	void getStudentsTeachersAll() {
		User user1 = dao.create("student1", "student1", "000032432", "std1", "s1@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		User user2 = dao.create("student2", "student2", "070322342", "std2", "s2@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		User user3 = dao.create("student3", "student3", "070322342", "std2", "s2@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		
		user1.setRole("teacher");
		dao.save(user1);

		AvailableTime r1 = new AvailableTime();
		r1.setEventString("iba testovacie toto nemoze byt event string");
		r1.setMyUserId(user1.getId());

		AvailableTime r2 = new AvailableTime();
		r2.setEventString("22iba testovacie toto nemoze byt event string");
		r2.setMyUserId(user2.getId());
		
		AvailableTime r3 = new AvailableTime();
		r3.setEventString("33iba testovacie toto nemoze byt event string");
		r3.setMyUserId(user3.getId());

		HashSet<AvailableTime> hashSet = new HashSet<>();
		hashSet.add(r1);
		hashSet.add(r2);
		hashSet.add(r3);
		rDao.saveAvailableTimesWithUserId(hashSet, user1.getId());

		HashSet<AvailableTime> hashSet2 = rDao.getAllStudentsCalendarEvents();
		HashSet<AvailableTime> hashSet3 = rDao.getAllTeachersCalendarEvents();

		HashSet<String> hashSetStudentUID = new HashSet<>();
		HashSet<String> hashSetTeacherUID = new HashSet<>();

		for (AvailableTime reservation : hashSet2) {
			hashSetStudentUID.add(reservation.getEventString());
		}
		
		for (AvailableTime reservation : hashSet3) {
			hashSetTeacherUID.add(reservation.getEventString());
		}
		
		assertEquals(2, hashSetStudentUID.size());
		assertEquals(1, hashSetTeacherUID.size());
		
		assertTrue(hashSetStudentUID.contains(r2.getEventString()));
		assertTrue(hashSetStudentUID.contains(r3.getEventString()));
		assertTrue(hashSetTeacherUID.contains(r1.getEventString()));
	}
	
	@Test
	void getById() {
		User user1 = dao.create("student1", "student1", "000032432", "std1", "s1@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		User user2 = dao.create("student2", "student2", "070322342", "std2", "s2@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		User user3 = dao.create("student3", "student3", "070322342", "std2", "s2@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		
		user1.setRole("teacher");
		dao.save(user1);

		// event string su jedinecne
		AvailableTime r1 = new AvailableTime();
		r1.setEventString("iba testovacie toto nemoze byt event string");
		r1.setMyUserId(user1.getId());

		AvailableTime r2 = new AvailableTime();
		r2.setEventString("22iba testovacie toto nemoze byt event string");
		r2.setMyUserId(user2.getId());
		
		AvailableTime r3 = new AvailableTime();
		r3.setEventString("33iba testovacie toto nemoze byt event string");
		r3.setMyUserId(user2.getId());

		HashSet<AvailableTime> hashSet = new HashSet<>();
		hashSet.add(r1);
		hashSet.add(r2);
		hashSet.add(r3);
		rDao.saveAvailableTimesWithUserId(hashSet, user1.getId());

		HashSet<AvailableTime> hashSet2 = rDao.getAvailableTimesByUserId(user2.getId());
		

		HashSet<String> hashSetStudentUID = new HashSet<>();
		

		for (AvailableTime reservation : hashSet2) {
			hashSetStudentUID.add(reservation.getEventString());
		}
		
		
		assertEquals(2, hashSetStudentUID.size());
		
		assertTrue(hashSetStudentUID.contains(r2.getEventString()));
		assertTrue(hashSetStudentUID.contains(r3.getEventString()));
		
	}

	
}
