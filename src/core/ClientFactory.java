package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import db.AbstractModel;
import db.Action;
import db.Appointment;
import db.User;

public class ClientFactory {
	
	//server
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket connection;
	Properties prop;
	
	public ClientFactory() {
		connectToServer();
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
	
	private void closeConnection() {
		try {
			output.close();
			input.close();
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("connection closed");
	}
	
	// :D :D :D :D :D :D
	// Here come the class methods
	// :D :D :D :D :D :D 
	
	public  <T extends AbstractModel, R> R sendAction(T t, Action action) {
		R callback = null;
		T clone = t.getCopy();
		clone.setAction(action);
		
		// Send object
		try {
			output.writeObject(clone);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Recieve object if nessecary
		if (!action.equals(Action.DISCONNECT)) {
			try {
				callback = (R) input.readObject();	
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return callback;
	}
	
	private void logConsole(String text){
		System.out.println("CLIENT: "+ text);
	}
	
	public static void main(String args[]) {
		
		// Alle verdier returnerer enten null eller et gyldig objekt hvis ting gikk bra
		//
		// login user
		// User u = new User(0, "aleksander", "yolo", "passord");
		//
		// logout user
		// cf.sendAction(u, Action.DISCONNECT);
		//
		// get all user appointments
		// ArrayList<Appointment> appointments = cf.sendAction(u, Action.GET_ALL_APPOINTMENTS);
		// 
		// create appointment
		// Appointment a = new Appointment(0, 1, "test2", new GregorianCalendar(), new GregorianCalendar(), "beskrivelse", true);
		// a.setMeetingPoint(new MeetingPoint(1, "redhead", 200));
		// Appointment success = cf.sendAction(a, Action.INSERT);
		
		ClientFactory cf = new ClientFactory();
		
		//test insert appointment
		
//		Appointment a = new Appointment(0, 1, "test2", new GregorianCalendar(), new GregorianCalendar(), "beskrivelse", true);
//		a.setMeetingPoint(new MeetingPoint(1, "redhead", 200));
		
		// Test login+logout
		User u = new User(0, "aleksander", "yolo", "passord");
		
		//System.out.println(login);
//		Appointment ap = cf.sendAction(a, Action.INSERT);
//		System.out.println(ap);
		User login = cf.sendAction(u, Action.LOGIN);
		System.out.println("returned with " + login.getName());
		
		System.out.println("get appointments");
		ArrayList<Appointment> appointments = cf.sendAction(u, Action.GET_ALL_APPOINTMENTS);
		System.out.println(appointments.size());
		
		cf.sendAction(u, Action.DISCONNECT);
		System.out.println("sending logout");
		//System.out.println("logged out");
		//System.out.println("login returned with " + ((login != null)? login.getName() : "null") );

		//System.out.println(cf.output);
		/*Appointment a = new Appointment(0, 1, "test2", new GregorianCalendar(), new GregorianCalendar(), "beskrivelse", true);
		a.setMeetingPoint(new MeetingPoint(1, "redhead", 200));
		Appointment success = cf.addAppointment(a);
		System.out.println(success.getId());*/
		
		
		//Action a = cf.sendAction(u, Action.LOGOUT);
		//cf.sendAction(u, Action.LOGOUT);
		//System.out.println("logout returned ");
		//cf.closeConnection();

	}
}
