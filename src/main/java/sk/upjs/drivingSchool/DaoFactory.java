package sk.upjs.drivingSchool;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;

public enum DaoFactory {
	INSTANCE;
	
	private JdbcTemplate jdbcTemplate;
	private UserDao userDao;
	private AvailableTimesDao availableTimesDao;
	private ReservationDao reservationDao;
	
	
	public UserDao getUserDao() {
		if (userDao == null) {
			//userDao = new MemoryUserDao();
			userDao = new MysqlUserDao(getJdbcTemplate());
		}
		return userDao;
	}
	public AvailableTimesDao getAvailableTimesDao() {
		if (availableTimesDao == null) {
			//availableTimesDao = new MemoryAvailableTimesDao();
			availableTimesDao = new MysqlAvailableTimesDao(getJdbcTemplate());
		}
		return availableTimesDao;
	}
	public ReservationDao getReservationDao() {
		if (reservationDao == null) {
			//memoryDao nieje
			reservationDao = new MysqlReservationDao(getJdbcTemplate());
		}
		return reservationDao;
	}
	
	private JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUser("root");
			dataSource.setPassword("databazy");
			dataSource.setDatabaseName("mydb");
			dataSource.setUrl("jdbc:mysql://localhost/mydb?serverTimezone=Europe/Bratislava");//driving_school
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		return jdbcTemplate;
	}
}
