package core.alarm;

import java.util.Comparator;

public class alarmComparator implements Comparator<Alarm> {

	@Override
	public int compare(Alarm arg0, Alarm arg1) {
		return Long.signum(arg0.getAlarmTime().getTimeInMillis()-arg1.getAlarmTime().getTimeInMillis());
	}
}
