package core;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gui.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;

public class CalendarProgram extends JFrame {

	private JPanel contentPane;
	private AddAppointmentPanel aap;
	private LoginPanel loginPanel;
	private JLayeredPane layeredPane;

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
		layeredPane = new JLayeredPane();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		CalendarPanel calendarPanel = new CalendarPanel();
		calendarPanel.setBackground(Color.RED);
		contentPane.add(calendarPanel, BorderLayout.CENTER);
		
		MenuPanel menuPanel = new MenuPanel(this);
		contentPane.add(menuPanel, BorderLayout.WEST);
		menuPanel.setBackground(Color.GREEN);
		
		loginPanel = new LoginPanel(this);
		
	}

	public void displayLogin() {
		contentPane.add(loginPanel, BorderLayout.CENTER);
		loginPanel.setBackground(Color.BLUE);
	}
	public void createAppointmentPanel(){
		aap = new AddAppointmentPanel();
		contentPane.add(aap, BorderLayout.CENTER);
		aap.setBackground(Color.BLUE);
	}

	public boolean checkValid(String userName, String password) {
		return true;
	}

}
