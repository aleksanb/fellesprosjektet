package db;

import gui.AppointmentView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GregorianCalendar currentDate;
	private int year;
	private int week;
	
	private ArrayList<AppointmentView> appointments;
	
	public CalendarModel() {
		init();
	}
	
	public void init() {

		currentDate = new GregorianCalendar();
		year = currentDate.get(GregorianCalendar.YEAR);
		week = currentDate.get(GregorianCalendar.WEEK_OF_YEAR);
		
		appointments = new ArrayList<AppointmentView>();
		
	}

	public GregorianCalendar getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(GregorianCalendar currentDate) {
		this.currentDate = currentDate;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
		currentDate.set(GregorianCalendar.YEAR, year);
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
		currentDate.set(GregorianCalendar.WEEK_OF_YEAR, week);
	}

	public ArrayList<AppointmentView> getEvents() {
		return appointments;
	}

	public void setAppointments(ArrayList<AppointmentView> appointments) {
		this.appointments = appointments;
	}
	
	public void addAppointment(Appointment appointment) {
		appointments.add(new AppointmentView(appointment));
	}
	public void addAppointmentView(AppointmentView appointmentView) {
		appointments.add(appointmentView);
	}
	public void removeAppointment(Appointment appointment) {
		for(int i= 0; i< appointments.size(); i++){
			System.out.println("hore");
			AppointmentView appointmentView = appointments.get(i);
			Appointment a = appointmentView.getModel();
			System.out.println(a);
			System.out.println(appointment);
			if(a.equals(appointment)){
				System.out.println("DEleted for faen");
				appointments.remove(appointmentView);
			}
		}
	}

	public void setAppointmentFromList(ArrayList<Appointment> appointments) {
		for(Appointment appointment : appointments){
			System.out.println("appointment");
			System.out.println(appointment.getWeek());
			System.out.println(appointment.getYear());
			System.out.println(appointment.getStart().getTime());
			System.out.println(appointment.getEnd().getTime());
			addAppointment(appointment);
		}
		
	}
}
