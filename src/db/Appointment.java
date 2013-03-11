package db;

import java.util.Date;

/**
 * 
 * @author Espen
 *
 *Kan vi bruke klassen GregorianCalendar istedet for "Date" til � lagre dato-objekter?
 *"Date" brukes mest til � sette dato inn i en database tror jeg?
 *n�r vi gj�r det kan vi istedet bruke gregorianCalendar.getTime()
 */



public class Appointment {

	int id;
	int creatorUserId ;
	String title;
	Date start;
	Date end;
	String description;
	boolean isMeeting;
	
	public Appointment(int id, int creatorUserId, String title, Date start,
			Date end, String description, boolean isMeeting) {
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
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
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
	
	
}
