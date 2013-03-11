package gui;

import javax.swing.JComponent;
import javax.swing.JPanel;

import net.sourceforge.jdatepicker.JDatePicker;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;

import db.Appointment;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import net.sourceforge.jdatepicker.util.JDatePickerUtil;
import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import javax.swing.SwingConstants;

public class AddAppointmentPanel extends JPanel implements ActionListener {

	Appointment appointment = null;
	
	
	/**
	 * Create the panel.
	 */
	public AddAppointmentPanel() {
		
		//GridBagLayout
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		//dateFrom label
		JLabel dateFromLabel = DefaultComponentFactory.getInstance().createLabel("Date from");
		dateFromLabel.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints gbc_dateFromLabel = new GridBagConstraints();
		gbc_dateFromLabel.insets = new Insets(0, 0, 5, 5);
		gbc_dateFromLabel.gridx = 0;
		gbc_dateFromLabel.gridy = 0;
		add(dateFromLabel, gbc_dateFromLabel);
		
		//pick from date
		JDatePickerImpl dateFromPick = new JDatePickerImpl(DatePicker.getDefaultCalendarModel());
		GridBagConstraints gbc_dateFromPick = new GridBagConstraints();
		gbc_dateFromPick.insets = new Insets(0, 0, 5, 0);
		gbc_dateFromPick.fill = GridBagConstraints.BOTH;
		gbc_dateFromPick.gridx = 2;
		gbc_dateFromPick.gridy = 0;
		add(dateFromPick, gbc_dateFromPick);
		
		//dateTo label
		JLabel dateToLabel = DefaultComponentFactory.getInstance().createLabel("Date to");
		dateToLabel.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints gbc_dateToLabel = new GridBagConstraints();
		gbc_dateToLabel.anchor = GridBagConstraints.NORTH;
		gbc_dateToLabel.insets = new Insets(0, 0, 0, 5);
		gbc_dateToLabel.gridx = 0;
		gbc_dateToLabel.gridy = 1;
		add(dateToLabel, gbc_dateToLabel);
		
		//pick to date
		JDatePickerImpl dateToPick = new JDatePickerImpl(DatePicker.getDefaultCalendarModel());
		dateToPick.addActionListener(this);
//		dateToPick.setActionCommand("To date");
		GridBagConstraints gbc_dateToPick = new GridBagConstraints();
		gbc_dateToPick.fill = GridBagConstraints.BOTH;
		gbc_dateToPick.gridx = 2;
		gbc_dateToPick.gridy = 1;
		add(dateToPick, gbc_dateToPick);
		
	}


	@Override
	public void actionPerformed(ActionEvent event) {
		JDatePickerImpl date = (JDatePickerImpl) event.getSource();
//		if(event.getActionCommand().equals(""))
		
	}
	

}
