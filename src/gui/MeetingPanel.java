package gui;

import javax.swing.JPanel;

public class MeetingPanel extends JPanel {
	
	AddAppointmentPanel aap;

	/**
	 * Create the panel.
	 * add this panel to the appointmentpanel
	 */
	public MeetingPanel(AddAppointmentPanel aap) {
		this.aap = aap;
	}

}
