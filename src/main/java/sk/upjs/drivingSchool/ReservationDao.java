package sk.upjs.drivingSchool;

import java.util.HashSet;

public interface ReservationDao {
	
	HashSet<Reservation> getAll();
	
	HashSet<Reservation> getReservationsByStudentId(long studentId);
	
	HashSet<Reservation> getReservationsByInstructorId(long instructorId);
	
	HashSet<Reservation> getReservationsByBothId(long instructorId, long studentId);

	void saveReservations(HashSet<Reservation> reservations, long instructorId);
}
