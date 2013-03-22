package db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import core.alarm.Alarm;

public class Appointment implements AbstractModel, Serializable {
	private Action action;
	private static final long serialVersionUID = 1L;
	private int id;
	private int creatorUserId ;
	private String title;
	private GregorianCalendar start;
	private GregorianCalendar end;
	private String description;
	private boolean isMeeting;
	private Alarm alarm;
	private ArrayList<User> participants;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Appointment other = (Appointment) obj;
		if (id != other.id)
			return false;
		return true;
	}

	private MeetingPoint place;
	private AppointmentType appointmentType;
	private MeetingPoint meetingPoint;
	
	public Appointment(User u){
		this.title="";
		this.creatorUserId = u.getId();
		this.start=new GregorianCalendar();
		this.end=new GregorianCalendar(); end.set(Calendar.HOUR, start.get(Calendar.HOUR)+1);
		this.description="";
		this.isMeeting=false;
		this.participants = new ArrayList<User>();
		this.participants.add(u);
		this.place = null;
		this.appointmentType = AppointmentType.OK;
		this.meetingPoint = null;
	}

	public Appointment(int id, int creatorUserId, String title, GregorianCalendar start,
			GregorianCalendar end, String description, boolean isMeeting) {
		this.action = null;
		this.id = id;
		this.creatorUserId = creatorUserId;
		this.title = title;
		this.start = start;
		this.end = end;
		this.description = description;
		this.isMeeting = isMeeting;
		this.participants = new ArrayList<User>();
		this.place = null;
		this.appointmentType = AppointmentType.OK;
		this.meetingPoint = null;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public AppointmentType getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(AppointmentType appointmentType) {
		this.appointmentType = appointmentType;
	}

	public int getId() {
		return id;
	}
	public int getCreatorUserId() {
		return creatorUserId;
	}
	public void setCreatorUserId(int creatorUserId) {
		this.creatorUserId = creatorUserId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public GregorianCalendar getStart() {
		return start;
	}
	//overload
	public void setStart(GregorianCalendar start) {
		this.start = start;
	}
	public GregorianCalendar getEnd() {
		return end;
	}
	//overload
	public void setEnd(GregorianCalendar end) {
		this.end = end;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isMeeting() {
		return isMeeting;
	}
	public void setMeeting(boolean isMeeting) {
		this.isMeeting = isMeeting;
	}
	public Date getStartAsDate(){
		return start.getTime();
	}
	public Date getEndAsDate(){
		return end.getTime();
	}
	//overload
	public void setStart(Date date){
		start.setTime(date);
	}
	//overload
	public void setEnd(Date date){
		end.setTime(date);
	}
	public int getWeek(){
		return start.get(GregorianCalendar.WEEK_OF_YEAR);
	}
	public int getYear(){
		return start.get(GregorianCalendar.YEAR);
	}
	public Alarm getAlarm() {
		return alarm;
	}

	public void setAlarm(GregorianCalendar alarmTime) {
		this.alarm = new Alarm(alarmTime,id);
	}
	
	public ArrayList<User> getParticipants(){
		return this.participants;
	}
	
	public void addParticipant(User user){
		this.participants.add(user);
	}
	public void setParticipants(ArrayList<User> participants){
		this.participants=participants;
	}
	
	public MeetingPoint getMeetingPoint(){
		return this.meetingPoint;
	}
	
	public void setMeetingPoint(MeetingPoint meetingPoint){
		this.meetingPoint = meetingPoint;
	}
	
	@Override
	public String toString() {
		String out = "Appointment:\n id: " + id + "\n creatorUserId: " + creatorUserId
				+ "\n title: " + title + "\n start: " + start.getTime() + "\n end: " + end.getTime()
				+ "\n description: " + description + "\n isMeeting: " + isMeeting;
		if(alarm!=null) 
			out+="\n Alarm: " + alarm.getAlarmTime().getTime();
		return out;
	}

	public boolean hasAlarm() {
		return alarm!=null;
	}
	@Override
	public void setAction(Action action) {
		this.action = action;
		
	}
	@Override
	public Action getAction() {
		return this.action;
	}

	@Override
	public <T> T getCopy() {
		Appointment app = new Appointment(this.id,this.creatorUserId,this.title,this.start,this.end,this.description,this.isMeeting);
		if(this.hasAlarm())
			app.setAlarm((GregorianCalendar)this.alarm.getAlarmTime().clone());
		app.setParticipants(new ArrayList<User>());
		for (int i = 0; i < this.participants.size(); i++) {
			app.addParticipant(this.getParticipants().get(i));
		}
		if(this.isMeeting && this.meetingPoint != null){
			app.setMeetingPoint(this.meetingPoint);
		}
		//TODO: bruke this.clone() ?
		return (T) app; 
	}
}
