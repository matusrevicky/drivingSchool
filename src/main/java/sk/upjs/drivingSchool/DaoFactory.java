package sk.upjs.drivingSchool;

import org.springframework.jdbc.core.JdbcTemplate;

import com.mysql.cj.jdbc.MysqlDataSource;

public enum DaoFactory {
	INSTANCE;
	
	private JdbcTemplate jdbcTemplate;
	private UserDao userDao;
	private AvailableTimesDao availableTimeDao;
	
	
	public UserDao getUserDao() {
		if (userDao == null)
			userDao = new MemoryUserDao();
		return userDao;
	}
	public AvailableTimesDao getAvailableTimesDao() {
		if (availableTimeDao == null)
			availableTimeDao = new MemoryAvailableTimesDao();
		return availableTimeDao;
	}
	
//	public WorkshopDao getWorkshopDao() {
//		if (workshopDao == null) {
//			workshopDao = new MysqlWorkshopDao(getJdbcTemplate());
//		}
//		return workshopDao;
//	}
	
	private JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUser("root");
			dataSource.setPassword("databazy");
//			dataSource.setDatabaseName("registracia_itat");
			dataSource.setUrl("jdbc:mysql://localhost/driving_school?serverTimezone=Europe/Bratislava");
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		return jdbcTemplate;
	}
}
