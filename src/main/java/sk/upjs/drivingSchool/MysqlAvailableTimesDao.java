package sk.upjs.drivingSchool;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class MysqlAvailableTimesDao implements AvailableTimesDao {
	
	private JdbcTemplate jdbcTemplate;

	public MysqlAvailableTimesDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public HashSet<AvailableTime> getAllCalendarEvents() {
		String sql = "SELECT availabletime.eventString FROM AvailableTime ";
		List<AvailableTime> reservationsList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AvailableTime.class));
		return new HashSet<AvailableTime>(reservationsList);
	}
	
	@Override
	public HashSet<AvailableTime> getAvailableTimesByUserId(long userId) {
		String sql = "SELECT time.id, time.myUserId, time.startTime, time.endTime, time.eventString " +
				"FROM availableTime AS time JOIN user ON user.id = time.myUserId " +
				"WHERE user.id = " + userId; 
		List<AvailableTime> availableTimesList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AvailableTime.class));
		return new HashSet<AvailableTime>(availableTimesList);
	}

	@Override
	public void saveAvailableTimesWithUserId(HashSet<AvailableTime> availableTimes, long userId) {
		jdbcTemplate.update("DELETE FROM availableTime WHERE myUserId = ?", userId);	
		for(AvailableTime a : availableTimes) {
			add(a);
		}	
	}
	
	private void add(AvailableTime availableTime) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName("availableTime");
		simpleJdbcInsert.usingGeneratedKeyColumns("id");
		simpleJdbcInsert.usingColumns("startTime", "endTime", "myUserId", "eventString");
		
		Map<String,Object> hodnoty = new HashMap<>();
		hodnoty.put("startTime",availableTime.getStartTime());
		hodnoty.put("endTime",availableTime.getEndTime());
		hodnoty.put("myUserId",availableTime.getMyUserId());
		hodnoty.put("eventString", availableTime.getEventString());
		
		Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
		availableTime.setId(id);
	}
}
