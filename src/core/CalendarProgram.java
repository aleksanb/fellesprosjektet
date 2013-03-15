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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

public class CalendarProgram extends JFrame {
	
	//gui
	private JPanel contentPane;
	private AddAppointmentPanel aap;
	private LoginPanel loginPanel;
	private MenuPanel menuPanel;
	private CalendarPanel calendarPanel;
	
	//model
	private Appointment[] appointments;
	
	//server
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket connection;
	Properties prop;

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
		//sets up a connection to the server
		connectToServer();
		
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
		menuPanel.addNotification(new Notification(1, 2, NotificationType.CANCELLED));
		menuPanel.addNotification(new Notification(1, 2, NotificationType.CANCELLED));
		menuPanel.addNotification(new Notification(1, 2, NotificationType.CANCELLED));
		menuPanel.addNotification(new Notification(1, 2, NotificationType.CANCELLED));
		menuPanel.addNotification(new Notification(1, 2, NotificationType.CANCELLED));
		calendarPanel = new CalendarPanel();
		calendarPanel.setBackground(Color.RED);
		contentPane.add(calendarPanel, BorderLayout.CENTER);
		
	}

	private void connectToServer() {
		File file = new File("resources/server.properties");
		prop = new Properties();
		//load in adress and port from server.properties
		try { prop.load(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			logConsole("Could not find file");
			e.printStackTrace();
		} catch (IOException e) {
			logConsole("Could not read from file");
			e.printStackTrace();}
		//connect to server
		try { 
			createConnection();
			setupStreams();
		} catch (IOException e) {
			logConsole("could not setup a connection.");
			e.printStackTrace();}
	}

	//create the connection to the server
	private void createConnection() throws IOException {
		logConsole("Attempting connection...");
		connection = new Socket(InetAddress.getByName(prop.getProperty("ip")),Integer.parseInt(prop.getProperty("port")));
		logConsole("Connected to "+ connection.getInetAddress().getHostName());
	}
	//set up streams to send and receive data
	private void setupStreams()throws IOException{
		//TODO: rewrite this method to handle gson
		output= new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		logConsole("connection established");
	}
	
	public void logout(){
		saveDataFromSession();
		//TODO: use this method when log out button is pushed
		try {
			output.close();
			input.close();
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logConsole("connection closed");
	}

	private void saveDataFromSession() {
		// TODO: save stuff and things so it dont get lost before the program shuts down
		
	}
	private void logConsole(String text){
		System.out.println("CLIENT: "+ text);
	}
}
