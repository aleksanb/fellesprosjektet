package gui;

import javax.swing.JPanel;

import net.sourceforge.jdatepicker.JDatePicker;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import net.sourceforge.jdatepicker.util.JDatePickerUtil;
import net.sourceforge.jdatepicker.JDateComponentFactory;

public class AddAppointmentPanel extends JPanel {

	/**
	 * Create the panel.
	 */

	public AddAppointmentPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("Date from");
		GridBagConstraints gbc_lblNewJgoodiesLabel = new GridBagConstraints();
		gbc_lblNewJgoodiesLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewJgoodiesLabel.gridx = 0;
		gbc_lblNewJgoodiesLabel.gridy = 0;
		add(lblNewJgoodiesLabel, gbc_lblNewJgoodiesLabel);
		
		JLabel lblNewJgoodiesLabel_1 = DefaultComponentFactory.getInstance().createLabel("Date to");
		GridBagConstraints gbc_lblNewJgoodiesLabel_1 = new GridBagConstraints();
		gbc_lblNewJgoodiesLabel_1.gridx = 0;
		gbc_lblNewJgoodiesLabel_1.gridy = 1;
		add(lblNewJgoodiesLabel_1, gbc_lblNewJgoodiesLabel_1);
		
	}

}
