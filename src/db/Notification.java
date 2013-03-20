package db;

public class Notification {
	
	private int userId;
	private Appointment appointment;
	private NotificationType notificationType;
	private String message;
	

	public Notification(int userId, Appointment appointment, NotificationType notificationType){
		this.userId = userId;
		this.appointment = appointment;
		this.notificationType = notificationType;	
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointment = appointment;
	}

	public NotificationType getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}
	@Override
	public String toString(){
		return message;
	}
	
}
