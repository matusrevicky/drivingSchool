package sk.upjs.drivingSchool.persistent;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;

public enum DaoFactory {
	INSTANCE;
	
	private JdbcTemplate jdbcTemplate;
	private UserDao userDao;
	private AvailableTimesDao availableTimesDao;
	private ReservationDao reservationDao;
	private boolean isTesting = false;
	
	public boolean isTesting() {
		return isTesting;
	}
	public void setTesting(boolean isTesting) {
		this.isTesting = isTesting;
	}
	
	
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
	
	// jdbc:mysql://localhost/mydbtest?server pri beznom uzivani treba jdbc:mysql://localhost/mydb?server
	private JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUser("root");
			dataSource.setPassword("databazy");
			if (isTesting == true) {
				dataSource.setDatabaseName("mydbtest"); //mydbtest ak testujem
				dataSource.setUrl("jdbc:mysql://localhost/mydbtest?serverTimezone=Europe/Bratislava");//driving_school
			} else {
				dataSource.setDatabaseName("mydb"); //mydbtest ak testujem
				dataSource.setUrl("jdbc:mysql://localhost/mydb?serverTimezone=Europe/Bratislava");//driving_school
			}
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		return jdbcTemplate;
	}
}
