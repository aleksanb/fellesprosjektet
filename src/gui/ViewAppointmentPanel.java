package gui;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import core.CalendarProgram;
import db.Appointment;
import db.User;


public class ViewAppointmentPanel extends AddAppointmentPanel {

	public ViewAppointmentPanel(CalendarProgram cp, Appointment appointment) {
		super(cp,appointment);
		startPick.setEditable(false);
		endPick.setEditable(false);
		descriptionArea.setEditable(false);
		titleField.setEditable(false);
		startField.setEditable(false);
		endField.setEditable(false);
		meetingBox.setVisible(false);
		addAppButton.setVisible(false);
		cancelButton.setText("Close");
		alarmBox.setVisible(false);
		if(appointment.isMeeting())
			meetingPanel.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent event){
		
		if(event.getActionCommand().equals("Cancel"))
			this.setVisible(false);
	}
	public static void main(String[] args){
		JFrame frame = new JFrame();
		frame.getContentPane().add(new ViewAppointmentPanel(new CalendarProgram(),new Appointment(new User())));
		frame.pack();
        frame.setSize (800,500);
        frame.setVisible(true);
	}

}
