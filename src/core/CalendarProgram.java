package core;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import db.Appointment;
import db.Notification;
import db.NotificationType;

import gui.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;

public class CalendarProgram extends JFrame {

	private JPanel contentPane;
	private AddAppointmentPanel aap;
	private LoginPanel loginPanel;
	private MenuPanel menuPanel;
	private CalendarPanel calendarPanel;
	private Appointment[] appointments;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalendarProgram frame = new CalendarProgram();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CalendarProgram() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		loginPanel = new LoginPanel(this);
		contentPane.add(loginPanel, BorderLayout.CENTER);
		
	}

	public void displayLogin() {
		menuPanel.setVisible(false);
		calendarPanel.setVisible(false);
		loginPanel = new LoginPanel(this);
		contentPane.add(loginPanel, BorderLayout.CENTER);
	}
	public void createAppointmentPanel(){
		menuPanel.setVisible(false);
		calendarPanel.setVisible(false);
		aap = new AddAppointmentPanel(this);
		contentPane.add(aap, BorderLayout.CENTER);
		aap.setBackground(Color.LIGHT_GRAY);
	}

	public boolean checkValid(String userName, String password) {
		return true;
	}
	
	public void displayMainProgram(){
		aap.setVisible(false);
		menuPanel.setVisible(true);
		calendarPanel.setVisible(true);
	}
	public void CreateMainProgram() {
		menuPanel = new MenuPanel(this);
		contentPane.add(menuPanel, BorderLayout.WEST);
		calendarPanel = new CalendarPanel();
		calendarPanel.setBackground(Color.RED);
		contentPane.add(calendarPanel, BorderLayout.CENTER);
		
	}

}
