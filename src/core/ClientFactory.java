package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Properties;

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
	
	// :D :D :D :D :D :D
	// Here come the class methods
	// :D :D :D :D :D :D 
	
	public Appointment addAppointment(Appointment appointment) {
		
		System.out.println("sending appointmnent to server");
		//String json = jf.generateJsonCommand(Action.INSERT, appointment);
		Appointment callback = null;
		try {
			output.writeObject(appointment);
			System.out.println("wrote object");
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("waiting for response");
		try {
			callback = (Appointment) input.readObject();
			System.out.println("we have read callback");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return callback;
	}
	
	public void logout(User u){
		//TODO: use this method when log out button is pushed
		try {
			output.writeObject(u);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			output.close();
			input.close();
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("connection closed");
	}
	
	public User login(User u) {
		User callback = null;
		try {
			output.writeObject(u);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("waiting for response");
		try {
			callback = (User) input.readObject();	
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return callback;
	}
	
	public static void main(String args[]) {
		ClientFactory fc = new ClientFactory();
		User u = new User(0, "aleksander", "yolo", "passord");
		u.setAction(Action.LOGIN);
		User login = fc.login(u);
		System.out.println("login returned with " + ((login != null)? login.getName() : "null") );
		fc.logout(u);
	}
	
	private void logConsole(String text){
		System.out.println("CLIENT: "+ text);
	}
}
