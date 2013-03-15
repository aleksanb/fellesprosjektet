package core;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class AlarmHandler {

	private PropertyChangeSupport pcs;
	private ArrayList<GregorianCalendar> alarmList;
	
	public AlarmHandler(ArrayList<GregorianCalendar> alarmList){
		pcs = new PropertyChangeSupport(this);
		this.alarmList = alarmList;
	}
	//let client listen for alarm events
	public void addPropertyChangeListener(PropertyChangeListener listener){
		pcs.addPropertyChangeListener(listener);
	}
	public void addAlarm(GregorianCalendar alarm){
		alarmList.add(alarm);
	}

}
