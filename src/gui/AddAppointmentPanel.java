package gui;

import javax.swing.JPanel;

import net.sourceforge.jdatepicker.JDatePicker;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;

import db.Appointment;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import net.sourceforge.jdatepicker.util.JDatePickerUtil;
import net.sourceforge.jdatepicker.JDateComponentFactory;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import javax.swing.SwingConstants;

public class AddAppointmentPanel extends JPanel {

	Appointment appointment = null;
	
	
	/**
	 * Create the panel.
	 */
	public AddAppointmentPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("Date from");
		lblNewJgoodiesLabel.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints gbc_lblNewJgoodiesLabel = new GridBagConstraints();
		gbc_lblNewJgoodiesLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewJgoodiesLabel.gridx = 0;
		gbc_lblNewJgoodiesLabel.gridy = 0;
		add(lblNewJgoodiesLabel, gbc_lblNewJgoodiesLabel);
		
		JDatePickerImpl datePickerImpl = new JDatePickerImpl(DatePicker.getDefaultCalendarModel());
		GridBagConstraints gbc_datePickerImpl = new GridBagConstraints();
		gbc_datePickerImpl.insets = new Insets(0, 0, 5, 0);
		gbc_datePickerImpl.fill = GridBagConstraints.BOTH;
		gbc_datePickerImpl.gridx = 2;
		gbc_datePickerImpl.gridy = 0;
		add(datePickerImpl, gbc_datePickerImpl);
		
		JLabel lblNewJgoodiesLabel_1 = DefaultComponentFactory.getInstance().createLabel("Date to");
		lblNewJgoodiesLabel_1.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints gbc_lblNewJgoodiesLabel_1 = new GridBagConstraints();
		gbc_lblNewJgoodiesLabel_1.anchor = GridBagConstraints.NORTH;
		gbc_lblNewJgoodiesLabel_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewJgoodiesLabel_1.gridx = 0;
		gbc_lblNewJgoodiesLabel_1.gridy = 1;
		add(lblNewJgoodiesLabel_1, gbc_lblNewJgoodiesLabel_1);
		
		JDatePickerImpl datePickerImpl_1 = new JDatePickerImpl(DatePicker.getDefaultCalendarModel());
		GridBagConstraints gbc_datePickerImpl_1 = new GridBagConstraints();
		gbc_datePickerImpl_1.fill = GridBagConstraints.BOTH;
		gbc_datePickerImpl_1.gridx = 2;
		gbc_datePickerImpl_1.gridy = 1;
		add(datePickerImpl_1, gbc_datePickerImpl_1);
		
	}

}
