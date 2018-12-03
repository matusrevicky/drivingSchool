package sk.upjs.drivingSchool;

import java.util.HashSet;
import java.util.List;

public interface AvailableTimesDao {
	
	//HashSet<AvailableTime> getAll();

	HashSet<AvailableTime> getAvailableTimesByUserId(long userId);

	void saveAvailableTimesWithUserId(HashSet<AvailableTime> availableTimes, long userId);
}
