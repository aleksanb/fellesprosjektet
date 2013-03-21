package gui;

import javax.swing.JPanel;

import core.CalendarProgram;
import db.Appointment;

public class EditAppointmentPanel extends AddAppointmentPanel{
	//TODO make the panel work properly... Esp?
	public EditAppointmentPanel(CalendarProgram calendarProgram, Appointment appointment) {
		super(calendarProgram, appointment);
		System.out.println("holla mamma");
		
	}

	public void setAppoitment(Appointment appointment) {
		System.out.println(appointment.getTitle());
		
	}

}
