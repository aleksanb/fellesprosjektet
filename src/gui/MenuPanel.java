package gui;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Component;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Color;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JList;

import db.Appointment;
import db.Factory;
import db.Notification;
import core.CalendarProgram;
import javax.swing.JComboBox;

public class MenuPanel extends JPanel {
	private JComboBox<Notification> notificationList;
	JLabel lblNotifications;
	/**
	 * Create the panel.
	 */
	public MenuPanel(CalendarProgram cp) {
		setBackground(new Color(51, 204, 204));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		

		
		JLabel lblHello = new JLabel("Hello");
		lblHello.setForeground(new Color(0, 0, 0));
		lblHello.setBackground(new Color(0, 0, 255));
		GridBagConstraints gbc_lblHello = new GridBagConstraints();
		gbc_lblHello.insets = new Insets(0, 0, 5, 5);
		gbc_lblHello.gridx = 2;
		gbc_lblHello.gridy = 1;
		add(lblHello, gbc_lblHello);
		
		JButton btnNewA = new JButton("New appointment");
		btnNewA.addActionListener(new AddAppointmentListener(cp));
		GridBagConstraints gbc_btnNewA = new GridBagConstraints();
		gbc_btnNewA.gridwidth = 5;
		gbc_btnNewA.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewA.gridx = 0;
		gbc_btnNewA.gridy = 2;
		add(btnNewA, gbc_btnNewA);
		
		JButton btnShowCalendars = new JButton("Show calendars");
		GridBagConstraints gbc_btnShowCalendars = new GridBagConstraints();
		gbc_btnShowCalendars.gridwidth = 5;
		gbc_btnShowCalendars.insets = new Insets(0, 0, 5, 0);
		gbc_btnShowCalendars.gridx = 0;
		gbc_btnShowCalendars.gridy = 3;
		add(btnShowCalendars, gbc_btnShowCalendars);
		
		JButton btnLog = new JButton("Logout");
		btnLog.addActionListener(new logoutListener(cp));
		
		lblNotifications = new JLabel("you do not have any notifications");
		GridBagConstraints gbc_lblNotifications = new GridBagConstraints();
		gbc_lblNotifications.insets = new Insets(0, 0, 5, 5);
		gbc_lblNotifications.gridx = 2;
		gbc_lblNotifications.gridy = 5;
		add(lblNotifications, gbc_lblNotifications);
		
		notificationList = new JComboBox<Notification>();
		notificationList.setMaximumRowCount(5);
		notificationList.addActionListener(new NotificationListListener(notificationList));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.anchor = GridBagConstraints.NORTH;
		gbc_comboBox.gridwidth = 8;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 6;
		add(notificationList, gbc_comboBox);
		GridBagConstraints gbc_btnLog = new GridBagConstraints();
		gbc_btnLog.gridwidth = 3;
		gbc_btnLog.insets = new Insets(0, 0, 0, 5);
		gbc_btnLog.gridx = 1;
		gbc_btnLog.gridy = 13;
		add(btnLog, gbc_btnLog);

	}
	public void addNotification(Notification notification){
		notification.setMessage("Please attend meeting");
		notificationList.addItem(notification);
		notificationList.setSelectedIndex(-1);
		update();
	}
	private void update() {
		int antall = notificationList.getItemCount();
		if(antall == 1){
			lblNotifications.setText("you  have 1 notification");
		}
		else{
			lblNotifications.setText("you have: " + Integer.toString(antall) + " notifications");
		}
	}
	class logoutListener implements ActionListener{
		CalendarProgram cp;
		public logoutListener(CalendarProgram cp){
			this.cp = cp;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			//cp.logout();
			cp.displayLogin();
		}
		
	}
	class AddAppointmentListener implements ActionListener{
		CalendarProgram cp;
		public AddAppointmentListener(CalendarProgram cp){
			this.cp = cp;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			cp.createAppointmentPanel();
		}
		
	}
	class NotificationListListener implements ActionListener{
		JComboBox<Notification> notificationList;
		
		public NotificationListListener(JComboBox<Notification> notificationList) {
			this.notificationList = notificationList;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int index = notificationList.getSelectedIndex();
			Notification note = (Notification) notificationList.getSelectedItem();
			if(index != -1 && index != 0){
			notificationList.removeItem(note);
			}
		}
		
	}
	 public static void main(String args[]) {
			JFrame frame = new JFrame("...");
			CalendarProgram cp1 = new CalendarProgram();
			MenuPanel mp = new MenuPanel(cp1);
			frame.getContentPane().add(mp);
			frame.pack();
			frame.setSize(200, 400);
			frame.setVisible(true);
		}  

}
