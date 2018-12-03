package sk.upjs.drivingSchool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MemoryAvailableTimeDao implements AvailableTimeDao{

	private UserDao userDao = DaoFactory.INSTANCE.getUserDao();	 

		@Override
		public HashSet<AvailableTime> getAvailableTimesByUserId(long userId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void saveAvailableTimesToUserId(long userId) {
			// TODO Auto-generated method stub
			
		}

//		@Override
//		public HashSet<AvailableTime> getAll() {
//			// TODO Auto-generated method stub
//			System.out.println("TODO");
//			return null;
//		}
}
