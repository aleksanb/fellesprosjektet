package db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Properties;

import com.google.gson.Gson;
import com.sun.jndi.url.corbaname.corbanameURLContextFactory;

public class JsonFactory {
	Gson gson;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket connection;
	private Properties prop;
	
	public JsonFactory() {
		gson = new Gson();
		//connectToServer();
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
	
	private void logConsole(String text){
		System.out.println("CLIENT: "+ text);
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

	public String generateJsonCommand(Action action, Object o) {
		ActionObject ao = new ActionObject(action, o);
		return gson.toJson(ao);
	}
	
	public User generateUser(String json) {
		return gson.fromJson(json, User.class);
	}
	
	public void parseJson(String json) throws ClassNotFoundException {
		ActionObject ao = gson.fromJson(json, ActionObject.class);
		Action action = ao.getAction();
		Class<?> cl = Class.forName(ao.getCl());
		
		ArrayList<Object> alo = ao.getAlObject();
		ArrayList<Object> alu = new ArrayList<Object>();

		for (int i = 0; i < alo.size(); i++) {
			alu.add(gson.fromJson(alo.get(i).toString(), cl));
		}
		System.out.println(cl.toString().split("\\.")[1]);
		String sql;
		switch(action) {
		case LOGIN:
			sql = "SELECT * FROM sids.user WHERE sids.user.name ='stuff' AND sids.user.hashedPassword = 'hunter2' LIMIT 1";
			break;
			
		case DELETE:
			for (int i = 0; i < alu.size(); i++) {
				sql = "DELETE FROM sids." + cl + "where sids." + cl + "==" + alu.get(i);
			}
			break;
		case GET:
			break;
		case INSERT:
			break;
		case NOTIFICATION:
			break;
		case UPDATE:
			break;
		default:
			break;
		}

	}
	
	public static void main(String args[]) {
		JsonFactory jsf = new JsonFactory();
		System.out.println(jsf.generateJsonCommand(Action.LOGOUT, new User()));
		/*
		User u = new User(10, "aleksander", "burkow", "aleksanderburkow@gmail.com");
		Appointment a = new Appointment(10, 5, "yolo", new GregorianCalendar(), new GregorianCalendar() , "descr", true);
		String json = jsf.generateJsonCommand(Action.DELETE, a);
		System.out.println(json);
		try {
			jsf.parseJson(json);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}*/
	}
	
}
