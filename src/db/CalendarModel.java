package db;

import gui.AppointmentView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalendarModel {
	
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
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
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
}
