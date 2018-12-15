package sk.upjs.drivingSchool.persistent;

import java.util.HashSet;

import sk.upjs.drivingSchool.entity.Reservation;

public interface ReservationDao {
	
	HashSet<Reservation> getAll();
	
	HashSet<Reservation> getReservationsByStudentId(long studentId);
	
	HashSet<Reservation> getReservationsByInstructorId(long instructorId);
	
	HashSet<Reservation> getReservationsByBothId(long instructorId, long studentId);

	void saveReservations(HashSet<Reservation> reservations, long instructorId);

	void saveReservations(long studentId, HashSet<Reservation> reservations);
}
