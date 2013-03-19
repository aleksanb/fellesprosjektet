package db;

import java.util.Date;
import java.util.GregorianCalendar;

import core.alarm.Alarm;

/**
 * 
 * @author Espen
 *
 *Kan vi bruke klassen GregorianCalendar istedet for "Date" til � lagre dato-objekter?
 *"Date" brukes mest til � sette dato inn i en database tror jeg?
 *n�r vi gj�r det kan vi istedet bruke gregorianCalendar.getTime()
 */



public class Appointment {

	private int id;
	private int creatorUserId ;
	private String title;
	private GregorianCalendar start;
	private GregorianCalendar end;
	private String description;
	private boolean isMeeting;
	private Alarm alarm;
	

	public Appointment(int id, int creatorUserId, String title, GregorianCalendar start,
			GregorianCalendar end, String description, boolean isMeeting) {
		this.id = id;
		this.creatorUserId = creatorUserId;
		this.title = title;
		this.start = start;
		this.end = end;
		this.description = description;
		this.isMeeting = isMeeting;
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
	public Alarm getAlarm() {
		return alarm;
	}

	public void setAlarm(GregorianCalendar alarmTime) {
		this.alarm = new Alarm(alarmTime,id);
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
	
	
	
}
