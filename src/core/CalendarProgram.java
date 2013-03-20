package core;

import java.awt.BorderLayout;
import java.awt.Color;
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
import core.ClientFactory;
import db.Notification;
import db.NotificationType;
import db.User;

import gui.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

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
	private ClientFactory cf;
	
	//Alarm
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
		//connectToServer();

		
		//tool for talking with server
		cf = new ClientFactory();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//TODO: possibly overide this method to also close threads
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
		aap = new AddAppointmentPanel(this,new Appointment(currentUser));
		contentPane.add(aap, BorderLayout.CENTER);
		aap.setBackground(Color.LIGHT_GRAY);
	}

	//TODO fiks en metode som fungerer ordentlig for innloggin
	public boolean checkValid(String userName, String password) {
	User temp = cf.login(new User(0,userName,"eigil@gmail.com",password));
	return true;
	}

	public boolean logIn(String userName, char[] password) {
		User temp = cf.login(new User(0,userName,"eigil@gmail.com",Arrays.toString(password)));
		if(temp != null){
			currentUser = temp;
			return true;
		}
		return false;
	}
	
	public void displayMainProgram(){
		aap.setVisible(false);
		menuPanel.setVisible(true);
		calendarPanel.setVisible(true);
	}
	//called when user logs inn
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
		
		loadAppointments();
		alarmSetup();
	}

	private void addNotification(Notification notification) {
		menuPanel.addNotification(notification);
		
	}
	//get appointments and starts to check them in a new thread, signing up for notifications from alarmHandler.
	private void alarmSetup() {
		alarmHandler = new AlarmHandler(new ArrayList<Appointment>());//TODO: make alarmhandler compatible with hashmap
		alarmHandler.addAlarmEventListener(this);
		alarmHandlerThread = new Thread(alarmHandler);
		alarmHandlerThread.start();
	}


	//when program starts it sets the appointment field to what is recieves from server
	private void loadAppointments() {
		appointments = cf.loadAppointments(currentUser);
		
	}

	public void logout(){
		cf.logout(currentUser);
	}

	private void saveDataFromSession() {
		// TODO: save stuff and things so it dont get lost before the program shuts down
		
	}
	private void logConsole(String text){
		System.out.println("CLIENT: "+ text);
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
		String timeToAlarm = ""+new Date(appointment.getStart().getTimeInMillis()-appointment.getAlarm().getAlarmTime().getTimeInMillis());
		JOptionPane.showMessageDialog(this, "You have appointment: "+appointment.getTitle()+" in: "+timeToAlarm,"Appointment alarm",JOptionPane.INFORMATION_MESSAGE);
	}
	public User getUser(){
		return currentUser;
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
