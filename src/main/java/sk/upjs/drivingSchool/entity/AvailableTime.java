package sk.upjs.drivingSchool.entity;

import java.time.LocalDateTime;

public class AvailableTime {
	private Long id;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Long myUserId;
	private String eventString; 
	private String eventStringUID; 

	
	
	public String getEventStringUID() {
		return eventStringUID;
	}

	public void setEventStringUID(String eventStringUID) {
		this.eventStringUID = eventStringUID;
	}

	public String getEventString() {
		return eventString;
	}

	public void setEventString(String eventString) {
		this.eventString = eventString;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long avaibleTimeId) {
		this.id = avaibleTimeId;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public void setMyUserId(Long myUserId) {
		this.myUserId = myUserId;
	}

	public Long getMyUserId() {
		return myUserId;
	}

	public void setUserId(Long userId) {
		this.myUserId = userId;
	}

	@Override
	public String toString() {
		return "AvailableTime [id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", myUserId="
				+ myUserId + ", eventString=" + eventString + ", eventStringUID=" + eventStringUID + "]";
	}

//	@Override
//	public String toString() {
//		return "AvaibleTimes [avaibleTimeId=" + id + ", startTime=" + startTime + ", endTime=" + endTime
//				+ ", userId=" + myUserId + "]";
//	}
	
	

}
