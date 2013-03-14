package gui;

import javax.swing.JList;

import db.Appointment;

public class CalendarDay extends JList{
	
	Appointment[] Appointments; 
	
	public CalendarDay(){
		Appointments = new Appointment();
		
	}
}
