package db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Properties;

public class ClientFactory {
	//server
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket connection;
	private JsonFactory jf;
	Properties prop;
	
	public ClientFactory() {
		connectToServer();
		jf = new JsonFactory();
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
	
	public void logout(User u){
		//TODO: use this method when log out button is pushed
		System.out.println("Going down with the ship, captain!");
		String json = jf.generateJsonCommand(Action.LOGOUT, u);
		try {
			output.writeObject(json);
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
		String json = jf.generateJsonCommand(Action.LOGIN, u);
		//String callback = "false";
		User login = null;
		try {
			output.writeObject(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("waiting for response");
		try {
			String temp = (String) input.readObject();
			if (temp.equals("null")) {
				return null;
			} else {
				login = jf.generateUser(temp);				
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return login;
	}
	
	public static void main(String args[]) {
		ClientFactory fc = new ClientFactory();
		User login = fc.login(new User("aleksander", "yolo", "passord"));
		System.out.println("login returned with " + ((login != null)? login.getName() : "null") );
		fc.logout(new User("aleksander", "stuff", "passord"));
	}
	
	private void logConsole(String text){
		System.out.println("CLIENT: "+ text);
	}
}
