package sk.upjs.drivingSchool.entity;

public class Reservation {
	
	// vsetky zakomentovane veci tam sice byt mozu, ale po pouziti kniznice jxtras, kde 
	// kalendar bere ako vstup cely string(string uz obsahuje start, koniec, farbu, opakokvanie...), 
	// uz neboli dolezite
	//private Long reservationId;
	//private LocalDateTime startDate;
	//private LocalDateTime endDate;
	//private LocalDateTime dateCreated;
	private String eventString;
	private Long instructorId;
	private Long studentId;
	private boolean seenByStudent;

	public Long getInstructorId() {
		return instructorId;
	}
	public void setInstructorId(Long instructorId) {
		this.instructorId = instructorId;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long stundetId) {
		this.studentId = stundetId;
	}
	public boolean getSeenByStudent() {
		return seenByStudent;
	}
	public void setSeenByStudent(boolean seenByStudent) {
		this.seenByStudent = seenByStudent;
	}	
	public String getEventString() {
		return eventString;
	}
	public void setEventString(String eventString) {
		this.eventString = eventString;
	}	
	
	
	/*public Long getReservationId() {
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
	public LocalDateTime getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
	}*/
	
}
