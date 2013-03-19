package core.alarm;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


import db.Appointment;

public class AlarmEventSupport {
//	ArrayList<Appointment> appointments;
	Set<AlarmListener> listeners = new HashSet<AlarmListener>();
	
//	//adds appointsments to the list so it can return the appointment directly
//	public AlarmEventSupport(ArrayList<Appointment> appointments) {
//		this.appointments=appointments;
//	}
	
	//send the appointment to the instances that listen to it
	public void fireAlarmEvent(Appointment alarm) {
		for (AlarmListener listener : listeners) {
			listener.alarmEvent(alarm);
		}
		
	}
//	//get an alarm takes the id and find the corresponding id in appointment, return that appointment
//	private Appointment getAppointmentByAlarmId(Alarm alarm) {
//		Appointment appointment = null;
//		for (Appointment app : appointments) {
//			if(alarm.getId()==app.getId())
//				appointment=app;
//		}
//		return appointment;
//	}

	//adds new listeners
	public void addAlarmEventListener(AlarmListener listener) {
		listeners.add(listener);
		
	}
//	//adds new alarms
//	public void addAlarm(Appointment appointment){
//		appointments.add(appointment);
//	}
	//remove listeners
	public void removeListener(AlarmListener listener){
		listeners.remove(listener);
	}
//	//deletes alarms
//	public void removeAlarm(Alarm alarm){
//		for (Appointment app : appointments) {
//			if(alarm.getId()==app.getId())
//				appointments.remove(app);
//		}
//	}
}
