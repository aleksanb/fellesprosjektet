package db;

public class Notification implements AbstractModel{
	private static final long serialVersionUID = 1L;
	private int userId;
	private Appointment appointment;
	private NotificationType notificationType;
	private String message;
	private Action action;
	

	public Notification(int userId, Appointment appointment, NotificationType notificationType){
		this.userId = userId;
		this.appointment = appointment;
		this.notificationType = notificationType;	
	}
	public Notification(int userId, Appointment appointment, NotificationType notificationType,String message){
		this.userId = userId;
		this.appointment = appointment;
		this.notificationType = notificationType;
		this.message=message;
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

	@Override
	public void setAction(Action action) {
		this.action=action;
		
	}

	@Override
	public Action getAction() {
		return action;
	}

	@Override
	public <T> T getCopy() {
		return (T) new Notification(this.userId, (Appointment) this.appointment.getCopy(), this.notificationType,this.message);
	}
	
}
