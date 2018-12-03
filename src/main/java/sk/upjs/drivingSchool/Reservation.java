package sk.upjs.drivingSchool;

import java.time.LocalDateTime;

public class Reservation {
	private Long reservationId;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private Long instructorId;
	private Long stundetId;
	private boolean seenByStudent;
	private LocalDateTime dateCreated;
	public Long getReservationId() {
		return reservationId;
	}
	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	public Long getInstructorId() {
		return instructorId;
	}
	public void setInstructorId(Long instructorId) {
		this.instructorId = instructorId;
	}
	public Long getStundetId() {
		return stundetId;
	}
	public void setStundetId(Long stundetId) {
		this.stundetId = stundetId;
	}
	public boolean isSeenByStudent() {
		return seenByStudent;
	}
	public void setSeenByStudent(boolean seenByStudent) {
		this.seenByStudent = seenByStudent;
	}
	public LocalDateTime getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}
	@Override
	public String toString() {
		return "Reservation [reservationId=" + reservationId + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", instructorId=" + instructorId + ", stundetId=" + stundetId + ", seenByStudent=" + seenByStudent
				+ ", dateCreated=" + dateCreated + "]";
	}
	
	
	
}
