package core.alarm;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


import db.Appointment;

public class AlarmHandler implements Runnable{

	private AlarmEventSupport aes;
	private ArrayList<GregorianCalendar> alarmList;
	private ArrayList<Appointment> appointments;
	
	public AlarmHandler(ArrayList<Appointment> appointments){
		this.appointments=appointments;
		extractAlarms();
		aes = new AlarmEventSupport(appointments);
	}
	private void extractAlarms() {
		// TODO Auto-generated method stub
		
	}
	//let client listen for alarm events
	public void addPropertyChangeListener(PropertyChangeListener listener){
		aes.addPropertyChangeListener(listener);
	}
	public void addAlarm(GregorianCalendar alarm){
		alarmList.add(alarm);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
	private void soundAlarm(Alarm alarm){
		aes.fireAlarmEvent(alarm);
	}

}
