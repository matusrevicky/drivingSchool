package sk.upjs.drivingSchool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Role {
	STUDENT("student"), TEACHER("teacher"), ADMIN("admin");
	
	private String name;

	private Role(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public Role getRole(String name) {
		return valueOf(name);
	}

	public List<String> getAllNames() {			
		List<String> list = new ArrayList<String>();
		for(Role r : values()) {
			list.add(r.getName());
		}
		return list;
	}

}
