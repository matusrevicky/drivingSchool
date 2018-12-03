package sk.upjs.drivingSchool;

import java.util.HashSet;
import java.util.List;

public interface AvailableTimeDao {
	
	//HashSet<AvailableTime> getAll();

	HashSet<AvailableTime> getAvailableTimesByUserId(long userId);

	void saveAvailableTimesToUserId(long userId);
}
