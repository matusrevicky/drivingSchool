package sk.upjs.drivingSchool.persistent;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sk.upjs.drivingSchool.entity.Reservation;
import sk.upjs.drivingSchool.entity.User;
import sk.upjs.drivingSchool.login.Authenticator;

class MysqlReservationDaoTest {
	// predtym ako zacnem s testami treba prepnut dao.factory na testovaciu databazu

	private  UserDao dao = DaoFactory.INSTANCE.getUserDao();
	private  ReservationDao rDao = DaoFactory.INSTANCE.getReservationDao();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		DaoFactory.INSTANCE.setTesting(true);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		DaoFactory.INSTANCE.setTesting(false);
	}
	
	@BeforeEach
	void emptyDatabase() {
		dao.deleteAll();
	}

	@Test
	void getAllSaveAdd() { // zaroven otestovalo aj add, getAll
		User user1 = dao.create("student1", "student1", "000032432", "std1", "s1@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		User user2 = dao.create("student2", "student2", "070322342", "std2", "s2@s.sk", Authenticator.INSTANCE.hashPassword("s"));

		user1.setRole("teacher");
		dao.save(user1);

		Reservation r1 = new Reservation();
		r1.setEventString("iba testovacie toto nemoze byt event string");
		r1.setInstructorId(user1.getId());
		r1.setStudentId(user2.getId());
		r1.setSeenByStudent(false);

		Reservation r2 = new Reservation();
		r2.setEventString("22iba testovacie toto nemoze byt event string");
		r2.setInstructorId(user1.getId());
		r2.setStudentId(user2.getId());
		r2.setSeenByStudent(false);

		HashSet<Reservation> hashSet = new HashSet<>();
		hashSet.add(r1);
		hashSet.add(r2);
		rDao.saveReservations(hashSet, user1.getId());

		HashSet<Reservation> hashSet2 = rDao.getAll();

		HashSet<String> hashSetUID = new HashSet<>();

		for (Reservation reservation : hashSet2) {
			hashSetUID.add(reservation.getEventString());
		}

		assertTrue(hashSetUID.contains(r1.getEventString()));
		assertTrue(hashSetUID.contains(r2.getEventString()));

	}

	@Test
	void getReservationByStudentId() { // zaroven otestovalo aj add, getAll
		User user1 = dao.create("student1", "student1", "000032432", "std1", "s1@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		User user2 = dao.create("student2", "student2", "070322342", "std2", "s2@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		User user3 = dao.create("student3", "student3", "070322342", "std2", "s2@s.sk", Authenticator.INSTANCE.hashPassword("s"));

		user1.setRole("teacher");
		dao.save(user1);

		Reservation r1 = new Reservation();
		r1.setEventString("iba testovacie toto nemoze byt event string");
		r1.setInstructorId(user1.getId());
		r1.setStudentId(user2.getId());
		r1.setSeenByStudent(false);

		Reservation r2 = new Reservation();
		r2.setEventString("22iba testovacie toto nemoze byt event string");
		r2.setInstructorId(user1.getId());
		r2.setStudentId(user2.getId());
		r2.setSeenByStudent(false);

		Reservation r3 = new Reservation();
		r3.setEventString("33iba testovacie toto nemoze byt event string");
		r3.setInstructorId(user1.getId());
		r3.setStudentId(user3.getId());
		r3.setSeenByStudent(false);

		HashSet<Reservation> hashSet = new HashSet<>();
		hashSet.add(r1);
		hashSet.add(r2);
		hashSet.add(r3);
		rDao.saveReservations(hashSet, user1.getId());

		rDao.getReservationsByInstructorId(user2.getId());

		HashSet<Reservation> hashSetStudent = rDao.getReservationsByStudentId(user2.getId());
		HashSet<Reservation> hashSetInstructor = rDao.getReservationsByInstructorId(user1.getId());

		HashSet<String> hashSetStudentUID = new HashSet<>();
		HashSet<String> hashSetTeacherUID = new HashSet<>();

		for (Reservation reservation : hashSetStudent) {
			hashSetStudentUID.add(reservation.getEventString());
		}

		for (Reservation reservation : hashSetInstructor) {
			hashSetTeacherUID.add(reservation.getEventString());
		}

		assertEquals(2, hashSetStudent.size());
		assertEquals(3, hashSetInstructor.size());

		// student2 ma 2 rezervacie
		assertTrue(hashSetStudentUID.contains(r1.getEventString()));
		assertTrue(hashSetStudentUID.contains(r2.getEventString()));

		// instructor 1 ma 3 rezervacie
		assertTrue(hashSetTeacherUID.contains(r1.getEventString()));
		assertTrue(hashSetTeacherUID.contains(r2.getEventString()));
		assertTrue(hashSetTeacherUID.contains(r3.getEventString()));

	}

	@Test
	void getReservationByBothId() { // zaroven otestovalo aj add, getAll
		User user1 = dao.create("student1", "student1", "000032432", "std1", "s1@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		User user2 = dao.create("student2", "student2", "070322342", "std2", "s2@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		User user3 = dao.create("student3", "student3", "070322342", "std2", "s2@s.sk", Authenticator.INSTANCE.hashPassword("s"));
		User user4 = dao.create("student4", "student4", "070322342", "std2", "s2@s.sk", Authenticator.INSTANCE.hashPassword("s"));

		user1.setRole("teacher");
		user4.setRole("teacher");
		dao.save(user1);

		Reservation r1 = new Reservation();
		r1.setEventString("iba testovacie toto nemoze byt event string");
		r1.setInstructorId(user1.getId());
		r1.setStudentId(user2.getId());
		r1.setSeenByStudent(false);

		Reservation r2 = new Reservation();
		r2.setEventString("22iba testovacie toto nemoze byt event string");
		r2.setInstructorId(user1.getId());
		r2.setStudentId(user2.getId());
		r2.setSeenByStudent(false);

		Reservation r3 = new Reservation();
		r3.setEventString("33iba testovacie toto nemoze byt event string");
		r3.setInstructorId(user1.getId());
		r3.setStudentId(user3.getId());
		r3.setSeenByStudent(false);

		Reservation r4 = new Reservation();
		r4.setEventString("44iba testovacie toto nemoze byt event string");
		r4.setInstructorId(user4.getId());
		r4.setStudentId(user2.getId());
		r4.setSeenByStudent(false);

		HashSet<Reservation> hashSet = new HashSet<>();
		hashSet.add(r1);
		hashSet.add(r2);
		hashSet.add(r3);
		hashSet.add(r4);
		rDao.saveReservations(hashSet, user1.getId());

		rDao.getReservationsByInstructorId(user2.getId());

		HashSet<Reservation> hashSetStudentInst = rDao.getReservationsByBothId(user1.getId(), user2.getId());

		HashSet<String> hashSetStudentInstUID = new HashSet<>();

		for (Reservation reservation : hashSetStudentInst) {
			hashSetStudentInstUID.add(reservation.getEventString());
		}

		assertEquals(2, hashSetStudentInst.size());

		// student2 ma 2 rezervacie
		assertTrue(hashSetStudentInstUID.contains(r1.getEventString()));
		assertTrue(hashSetStudentInstUID.contains(r2.getEventString()));

	}
}
