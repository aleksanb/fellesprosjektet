package core.alarm;

import java.util.GregorianCalendar;

import db.AbstractModel;
import db.Action;

public class Alarm implements AbstractModel {
	private static final long serialVersionUID = 1L;
	private GregorianCalendar time;
	private int id;
	private Action action;
	
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
		return (T) new Alarm(this.time, this.id);
	}

}
