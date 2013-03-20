package core;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import core.alarm.AlarmHandler;
import core.alarm.AlarmListener;

import db.Appointment;
import db.AppointmentType;
import db.CalendarModel;
import db.ClientFactory;
import db.Notification;
import db.NotificationType;
import db.User;

import gui.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Properties;

public class CalendarProgram extends JFrame implements AlarmListener {
	
	//gui
	private JPanel contentPane;
	private AddAppointmentPanel aap;
	private EditAppointmentPanel eap;
	private LoginPanel loginPanel;
	private MenuPanel menuPanel;
	private CalendarPanel calendarPanel;
	
	//model
	private HashMap<Integer, Appointment> appointments;
	private User currentUser;
	
	//tools
	private Thread alarmHandlerThread;
	private ClientFactory clientFactory = new ClientFactory();
	
	//server
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket connection;
	private Properties prop;
	private AlarmHandler alarmHandler;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CalendarProgram frame = new CalendarProgram();
					frame.setSize(1200, 600);
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
		appointments = new HashMap<Integer, Appointment>();
		//sets up a connection to the server
		connectToServer();
		
		//TODO: load in appointments, look at the empty method
		
		//get appointments and starts to check them in a new thread, signing up for notifications from alarmHandler.
		alarmHandler = new AlarmHandler(new ArrayList<Appointment>());
		alarmHandler.addAlarmEventListener(this);
		alarmHandlerThread = new Thread(alarmHandler);
		alarmHandlerThread.start();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//TODO: possibly overide this method to also close threads
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		loginPanel = new LoginPanel(this);
		contentPane.add(loginPanel, BorderLayout.CENTER);
		
	}

	//private ArrayList<Appointment> getAppointmentList() {
		//TODO: make server fetch appointments
		//appointments = new ArrayList<Integer, Appointment>();
		//return appointments;
		
	//}

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
		/*
		User temp = clientFactory.login(new User(0,userName,"eigil@gmail.com",password));
		if(temp != null){
			currentUser = temp;
			return true;
		}
		return false;*/
	}
	
	public void displayMainProgram(){
		aap.setVisible(false);
		menuPanel.setVisible(true);
		calendarPanel.setVisible(true);
	}
	public void CreateMainProgram() {
		menuPanel = new MenuPanel(this);
		
		GregorianCalendar gc = new GregorianCalendar();
		GregorianCalendar gc1 = new GregorianCalendar();
		gc1.set(GregorianCalendar.HOUR_OF_DAY, 6);
		gc.set(GregorianCalendar.WEEK_OF_YEAR, 5);
		gc1.set(GregorianCalendar.WEEK_OF_YEAR, 5);
		Appointment app = new Appointment(1, 2, "hallais", gc, gc1, "halla", true);
		app.setAppointmentType(AppointmentType.NEEDSATTENTION);
		addNotification(new Notification(1, app, NotificationType.CANCELLED));
		
		GregorianCalendar gc2 = new GregorianCalendar();
		GregorianCalendar gc3 = new GregorianCalendar();
		gc2.set(GregorianCalendar.HOUR_OF_DAY, 7);
		gc2.set(GregorianCalendar.WEEK_OF_YEAR, 5);
		gc3.set(GregorianCalendar.WEEK_OF_YEAR, 5);
		gc2.set(GregorianCalendar.DAY_OF_WEEK, 5);
		gc3.set(GregorianCalendar.DAY_OF_WEEK, 5);
		Appointment app1 = new Appointment(2, 3, "halla", gc3, gc2, "halla", true);
		app1.setAppointmentType(AppointmentType.DELETED);
		addNotification(new Notification(2, app1, NotificationType.CANCELLED));
		
		contentPane.add(menuPanel, BorderLayout.WEST);

		calendarPanel = new CalendarPanel(this);
		calendarPanel.setBackground(Color.RED);
		calendarPanel.addAppointmentToModel(app);
		calendarPanel.addAppointmentToModel(app1);
		contentPane.add(calendarPanel, BorderLayout.CENTER);
		
	}

	private void addNotification(Notification notification) {
		menuPanel.addNotification(notification);
		
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
			try {
				logConsole("could not connect to server: "+InetAddress.getByName(prop.getProperty("ip")));
			} catch (UnknownHostException e1) {
				logConsole("Could not find server");
				e1.printStackTrace();
			}
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
	public void sendDebug(String text) {
		try {
			output.writeObject(text);
		} catch (IOException e) {
			logConsole("Error sending data");
			e.printStackTrace();
		}
	}
	
	public void addAppointment(Appointment app){
		//userf.create
		calendarPanel.addAppointmentToModel(app);
		appointments.put(app.getId(), app);
		if(app.hasAlarm()){
			alarmHandler.addAppointment(app);
			alarmHandlerThread.interrupt();
			System.out.println("Thread: "+alarmHandlerThread.interrupted());
		}		
	}
		
	@Override
	public void alarmEvent(Appointment appointment) {
		//TODO: format the message on the alarm
		JOptionPane.showMessageDialog(this, "title and shit","Appointment alarm",JOptionPane.INFORMATION_MESSAGE);
	}

	public void setFocusInCalendar(Notification note) {
		calendarPanel.setFocusToAppointment(note.getAppointment());
	}

	public void createEditAppointmentPanel(Appointment appointment) {
		menuPanel.setVisible(false);
		calendarPanel.setVisible(false);
		eap = new EditAppointmentPanel(this);
		eap.setAppoitment(appointment);
		contentPane.add(eap, BorderLayout.CENTER);
		eap.setBackground(Color.LIGHT_GRAY);
	}

	public void setEditButtonEnabled() {
		menuPanel.setEditButtonEnabled();
		
	}
	public void setEditButtonDisabled() {
		menuPanel.setEditButtonDisabled();
		
	}

	public Appointment getSelectedAppointment() {
		return calendarPanel.getSelectedEvent().getModel();
	}

}
