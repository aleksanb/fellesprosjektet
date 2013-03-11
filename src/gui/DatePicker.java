package gui;

import net.sourceforge.jdatepicker.*;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.UtilCalendarModel;

public class DatePicker {
	
	
	public static JDatePanelImpl getDefaultCalendarModel(){
		
		UtilCalendarModel model = new UtilCalendarModel();
		JDatePanelImpl datePan = new JDatePanelImpl(model);
		
		return datePan;
	}

}
