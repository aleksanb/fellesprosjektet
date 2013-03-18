package core.alarm;

import java.util.GregorianCalendar;

public class Alarm {

	private GregorianCalendar time;
	private int id;
	
	public Alarm(GregorianCalendar alarmTime, int id) {
		this.time=alarmTime;
		this.id=id;
	}
	public int getId(){
		return id;
	}
	public GregorianCalendar getAlarmTime(){
		return time;
	}
	public long getMillis(){
		return time.getTimeInMillis();
	}

}
