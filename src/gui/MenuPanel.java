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
import core.CalendarProgram;

public class MenuPanel extends JPanel {
	JList notificationList;
	private DefaultListModel listModel;
	/**
	 * Create the panel.
	 */
	public MenuPanel() {
		listModel = new DefaultListModel();
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
		btnNewA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
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
		
		JLabel lblNotifications = new JLabel("Notifications");
		GridBagConstraints gbc_lblNotifications = new GridBagConstraints();
		gbc_lblNotifications.gridwidth = 3;
		gbc_lblNotifications.insets = new Insets(0, 0, 5, 5);
		gbc_lblNotifications.gridx = 1;
		gbc_lblNotifications.gridy = 5;
		add(lblNotifications, gbc_lblNotifications);
		
		notificationList = new JList();
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 3;
		gbc_list.gridheight = 2;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 1;
		gbc_list.gridy = 6;
		add(notificationList, gbc_list);
		
		
		JButton btnLog = new JButton("Logout");
		btnLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnLog = new GridBagConstraints();
		gbc_btnLog.gridwidth = 3;
		gbc_btnLog.insets = new Insets(0, 0, 0, 5);
		gbc_btnLog.gridx = 1;
		gbc_btnLog.gridy = 13;
		add(btnLog, gbc_btnLog);

	}
	public void addNotification(String description){
		listModel.addElement(description);
		update();
	}
	private void update() {
		notificationList.setListData(listModel.toArray());
	}
	 public static void main(String args[]) {
			JFrame frame = new JFrame("...");
			MenuPanel mp = new MenuPanel();
			frame.add(mp);
			frame.pack();
			frame.setSize(200, 400);
			frame.setVisible(true);
			mp.addNotification("Holla holla");
		}  

}
