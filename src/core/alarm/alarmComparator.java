package core.alarm;

import java.util.Comparator;

import db.Appointment;

public class alarmComparator implements Comparator<Appointment> {

	@Override
	public int compare(Appointment arg0, Appointment arg1) {
//		return Long.signum(arg0.getAlarm().getAlarmTime().getTimeInMillis()-arg1.getAlarm().getAlarmTime().getTimeInMillis());
		return Long.signum(arg1.getAlarm().getAlarmTime().getTimeInMillis()-arg0.getAlarm().getAlarmTime().getTimeInMillis());
	}
}
