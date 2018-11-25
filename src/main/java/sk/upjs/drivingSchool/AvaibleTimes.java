package sk.upjs.drivingSchool;

import java.time.LocalDateTime;

public class AvaibleTimes {
	private Long avaibleTimeId;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Long userId;
	public Long getAvaibleTimeId() {
		return avaibleTimeId;
	}
	public void setAvaibleTimeId(Long avaibleTimeId) {
		this.avaibleTimeId = avaibleTimeId;
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "AvaibleTimes [avaibleTimeId=" + avaibleTimeId + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", userId=" + userId + "]";
	}
	
	
}
