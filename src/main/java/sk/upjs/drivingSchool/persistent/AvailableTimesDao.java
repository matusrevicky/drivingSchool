package sk.upjs.drivingSchool.persistent;

import java.util.HashSet;


import sk.upjs.drivingSchool.entity.AvailableTime;

public interface AvailableTimesDao {

	HashSet<AvailableTime> getAvailableTimesByUserId(long userId);

	void saveAvailableTimesWithUserId(HashSet<AvailableTime> availableTimes, long userId);

	HashSet<AvailableTime> getAllCalendarEvents();

	HashSet<String> getAllCalendarEventsUID();

	HashSet<AvailableTime> getAllStudentsCalendarEvents();

	HashSet<AvailableTime> getAllTeachersCalendarEvents();
}
