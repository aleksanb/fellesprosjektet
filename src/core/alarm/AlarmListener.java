package core.alarm;

import db.Appointment;

public interface AlarmListener {
	public void alarmEvent(Appointment appointment);
}
