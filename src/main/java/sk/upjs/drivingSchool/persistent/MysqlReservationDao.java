package sk.upjs.drivingSchool.persistent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import sk.upjs.drivingSchool.entity.Reservation;

public class MysqlReservationDao implements ReservationDao{
	
	private JdbcTemplate jdbcTemplate;

	public MysqlReservationDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public HashSet<Reservation> getAll() {
		String sql = "SELECT reservation.eventString, reservation.instructorId, reservation.studentId, "
				+ "reservation.seenByStudent FROM Reservation ";
		List<Reservation> reservationsList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Reservation.class));
		return new HashSet<Reservation>(reservationsList);
	}

	@Override
	public HashSet<Reservation> getReservationsByStudentId(long studentId) {
		String sql = "SELECT reservation.eventString, reservation.instructorId, reservation.studentId, "
				+ "reservation.seenByStudent FROM Reservation "
				+ "WHERE reservation.studentId = " + studentId; 
		List<Reservation> reservationsList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Reservation.class));
		return new HashSet<Reservation>(reservationsList);
	}
	
	@Override
	public HashSet<Reservation> getReservationsByInstructorId(long instructorId) {
		String sql = "SELECT reservation.eventString, reservation.instructorId, reservation.studentId, "
				+ "reservation.seenByStudent FROM Reservation "
				+ "WHERE reservation.instructorId = " + instructorId; 
		List<Reservation> reservationsList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Reservation.class));
		return new HashSet<Reservation>(reservationsList);
	}
	
	@Override
	public HashSet<Reservation> getReservationsByBothId(long instructorId, long studentId) {
		String sql = "SELECT reservation.eventString, reservation.instructorId, reservation.studentId, "
				+ "reservation.seenByStudent FROM Reservation "
				+ "WHERE reservation.instructorId = " + instructorId + " AND "
				+ "reservation.studentId = " + studentId; 
		List<Reservation> reservationsList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Reservation.class));
		return new HashSet<Reservation>(reservationsList);
	}

	@Override
	public void saveReservations(HashSet<Reservation> reservations, long instructorId) {
		jdbcTemplate.update("DELETE FROM Reservation WHERE instructorId = ?", instructorId);	
		for(Reservation r : reservations) {
			add(r);
		}
	}
	
	@Override
	public void saveReservations(long studentId, HashSet<Reservation> reservations) {
		jdbcTemplate.update("DELETE FROM Reservation WHERE studentId = ?", studentId);	
		for(Reservation r : reservations) {
			add(r);
		}
	}
	
	private void add(Reservation reservation) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName("Reservation");
		//simpleJdbcInsert.usingGeneratedKeyColumns("id");
		simpleJdbcInsert.usingColumns("eventString", "instructorId", "studentId", "seenByStudent");
		
		Map<String,Object> hodnoty = new HashMap<>();
		hodnoty.put("eventString",reservation.getEventString());
		hodnoty.put("instructorId",reservation.getInstructorId());
		hodnoty.put("studentId",reservation.getStudentId());
		hodnoty.put("seenByStudent",reservation.getSeenByStudent());
		
		simpleJdbcInsert.execute(hodnoty);
	}	

}
