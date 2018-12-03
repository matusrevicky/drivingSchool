package sk.upjs.drivingSchool;

import java.time.LocalDateTime;

public class AvailableTime {
	private Long id;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Long myUserId;

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

	public Long getMyUserId() {
		return myUserId;
	}

	public void setUserId(Long userId) {
		this.myUserId = userId;
	}

	@Override
	public String toString() {
		return "AvaibleTimes [avaibleTimeId=" + id + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", userId=" + myUserId + "]";
	}

}
