package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import db.AbstractModel;
import db.Action;
import db.Appointment;
import db.Notification;
import db.User;

public class Server implements Runnable{
	
	//Network
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket connection;
	
	//Recieve, send, local vairables
	private AbstractModel am;
	private ServerFactory sf;
	int connectionID;
	private ServerProgram sp;
	
	//Flow logic
	boolean closeConnection;
	boolean close;

	public Server(Socket connection, int connectionID,ServerProgram sp) {
		this.connection=connection;
		this.connectionID = connectionID;
		this.sp=sp;
	}
	//set up and run the server
	public void run(){
		try {
			setupStreams();
		} catch (IOException e1) {
			logConsole("Could not establish connection");
			e1.printStackTrace();
		}
		try{
			whileRunning();
		}catch(IOException e){
			logConsole("Server ended the connection");
		}
	}

	//get stream to send and receive data
	private void setupStreams()throws IOException{
		//TODO: rewrite this method to handle gson
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		logConsole("Streams are now set up!");	
	}
	//after connection is setup 
	private void whileRunning()throws IOException{
		closeConnection = false;
		sf = new ServerFactory();
		do{
			try{
				handleShit();
			}catch(Exception e){
				e.printStackTrace();
				logConsole(e.getMessage());
				closeConnection();
			}
		}while(!closeConnection); 
		closeApp();
	}
	private void closeApp(){
		logConsole("Closing connections" );
		try{
			output.close();
			input.close();
			connection.close();
		}catch(IOException e){
			e.printStackTrace();
			closeConnection();
		}
	}
	
	private void closeConnection() {
		closeConnection = true;
	}
	
	public void logConsole(String text){
		System.out.println("SERVER "+connectionID+": " +text);
	}
	
	private void handleShit() throws ClassNotFoundException, IOException {

		close = false;
		am = (AbstractModel) input.readObject();
		Class<? extends AbstractModel> cl = am.getClass();
		Action action = am.getAction();
		System.out.println("Read object!"+ am.toString());
		System.out.println("And action is:"+action);
		
		switch(action) {
		case LOGIN:
			//System.out.println("WE HAVE RECIEVED LOGIN REQUEST");
			User l_callback = null;
			try {
				l_callback = sf.login((User) am);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//adds user to servers list of online users
			if(l_callback != null)
//				sp.addOnlineUserConnection(l_callback,connection);
			
			try {
				output.writeObject(l_callback);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("wrote " + l_callback + " back to client");
			break;
		case DISCONNECT:
			//System.out.println("User " + ((User) am).getName() + "Wishes to disconnect");
			System.out.println("logging out. Going down with the ship cap'n");
			
			//remove user from list of online users
//			sp.removeOnlineUsers(((User) am).getId());
			close = true;
			break;
			
		case DELETE:
			break;
		case GET:
			break;
		case GET_ALL_APPOINTMENTS:
			System.out.println("WE HAVE RECIEVED GET ALL APPOINTMENTS REQUEST");
			ArrayList<Appointment> g_a_a_callback = sf.getAllAppointments((User) am);
			output.writeObject(g_a_a_callback);
			System.out.println("sent back appointments");
			break;
		case GET_ALL_USERS:
			System.out.println("Vi vil ha alle brukerne!");
			ArrayList<User> g_a_u_callback = sf.getAllUsers();
			output.writeObject(g_a_u_callback);
			System.out.println("Sending back users" + g_a_u_callback);
			break;
		case INSERT:
			if ( cl.equals(Appointment.class)) {
				System.out.println("Vi har fatt insert request for en appointment!");
				Appointment i_u_callback = sf.insertAppointment( (Appointment) am);
				output.writeObject(i_u_callback);
				System.out.println("sent back appointment");
			}
			break;
		case NOTIFICATION:
			logConsole("received notification");
			//receive notification
			Notification n_callback = (Notification)am;
			//extract users
			ArrayList<User> users = n_callback.getAppointment().getParticipants();
			//call sp, and save notification for all users that match
			sp.saveNotifications(n_callback,users);
			
			//write back to client
			output.writeObject(n_callback);
			break;
			//getting all the notifications for this specific user.
		case GET_NOTIFICATION:
			logConsole("fetching notification");
			ArrayList<Notification> al_n_callback = sp.fetchAppointments((User) am);
			output.writeObject(al_n_callback);
			break;
		case UPDATE:
			if ( cl.equals(Appointment.class)) {
				System.out.println("Vi har fatt update request for en appointment!");
				Appointment u_u_callback = sf.updateAppointment( (Appointment) am);
				output.writeObject(u_u_callback);
				System.out.println("sent back appointment");
			}
			break;
		default:
			System.out.println("action did not match any enum");
			break;
		}
		
		if (close) {
			closeConnection();
		}
		
	}
}