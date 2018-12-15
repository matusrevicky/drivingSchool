package sk.upjs.drivingSchool.persistent;

import java.util.HashSet;
import java.util.List;

import sk.upjs.drivingSchool.entity.AvailableTime;
import sk.upjs.drivingSchool.entity.User;

public class MemoryAvailableTimesDao implements AvailableTimesDao{

	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();
	private long lastId = 0;

	@Override
	public HashSet<AvailableTime> getAvailableTimesByUserId(long userId) {
		HashSet<AvailableTime> availableTimes = new HashSet<>();
		List<User> users = userDao.getAll();
		for(User u : users) {
			if(u.getId() == userId) {
				availableTimes = u.getAvailableTimes();
				break;
			}
		}
		return availableTimes;
	}

	@Override
	public void saveAvailableTimesWithUserId(HashSet<AvailableTime> availableTimes, long userId) {
		User u = userDao.get(userId);
		for(AvailableTime a : availableTimes) {
			a.setId(++lastId);		
			u.setId(userId);
		}
		u.setAvailableTimes(availableTimes);
		userDao.save(u);
	}

	@Override
	public HashSet<AvailableTime> getAllCalendarEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashSet<String> getAllCalendarEventsUID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashSet<AvailableTime> getAllStudentsCalendarEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashSet<AvailableTime> getAllTeachersCalendarEvents() {
		// TODO Auto-generated method stub
		return null;
	}	 



//		@Override
//		public HashSet<AvailableTime> getAll() {
//			// TODO Auto-generated method stub
//			System.out.println("TODO");
//			return null;
//		}
}
