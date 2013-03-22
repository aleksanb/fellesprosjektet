package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import db.AbstractModel;
import db.Action;
import db.Appointment;
import db.Callback;
import db.MeetingPoint;
import db.Notification;
import db.Status;
import db.User;

public class Server implements Runnable{
	
	//Network
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket connection;
	
	//Recieve, send, local vairables
	private AbstractModel am;
	private User currentUser;
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
		
		closeConnection = false;
		sf = new ServerFactory();
		do {
			handleShit();
		} while(!closeConnection); 
		closeApp();
		
	}

	//get stream to send and receive data
	private void setupStreams()throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		logConsole("Streams are now set up!");	
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
	
	private void handleShit() {

		close = false;
		try {
			am = (AbstractModel) input.readObject();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			am = new Callback(Action.MASSIVE_FAILURE);
			closeConnection();
		} catch (IOException e1) {
			e1.printStackTrace();
			am = new Callback(Action.MASSIVE_FAILURE);
			closeConnection();
		}
		Class<? extends AbstractModel> cl = am.getClass();
		Action action = am.getAction();
		System.out.println("Read object!"+ am.toString());
		System.out.println("And action is:"+action);
		
		switch(action) {
		case LOGIN:
			//System.out.println("WE HAVE RECIEVED LOGIN REQUEST");
			User l_callback;
			try {
				l_callback = sf.login((User) am);
				l_callback.setAction(Action.SUCCESS);
			} catch (Exception e) {
				System.out.println("no user found :(");
				l_callback = am.getCopy();
				l_callback.setAction(Action.MASSIVE_FAILURE);
				e.printStackTrace();
			}
			//adds user to servers list of online users
			if(l_callback.getAction().equals(Action.SUCCESS)) {
				currentUser = l_callback;
				System.out.println("*** current user is set to " + currentUser.getName() + " with id " + currentUser.getId() + " ***");
			}
			try {
				output.writeObject(l_callback);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("sent back " + action + " with status " + l_callback.getAction());
			break;
		case DISCONNECT:
			//System.out.println("User " + ((User) am).getName() + "Wishes to disconnect");
			System.out.println("logging out. Going down with the ship cap'n");
			
			//remove user from list of online users
//			sp.removeOnlineUsers(((User) am).getId());
			currentUser = null;
			close = true;
			break;
			
		case DELETE:
			Callback d_a_callback = new Callback();
			if (cl.equals(Appointment.class)){
				System.out.println("deleting appointment");
				Boolean b = sf.deleteAppointment((Appointment) am);
				if (b) {
					d_a_callback.setAction(Action.SUCCESS);
				} else {
					d_a_callback.setAction(Action.MASSIVE_FAILURE);
				}
				try {
					output.writeObject(d_a_callback);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("fucked up deleting");
				}
			}
			System.out.println("sent back " + action + " with status " + d_a_callback.getAction());
			break;
		case GET:
			break;
		case GET_ALL_USERS_ALL_APPOINTMENTS:
			System.out.println("get ALL the users and ALL the appointments!");
			HashMap<User, ArrayList<Appointment>> g_a_u_a_a_callback;
			try {
				g_a_u_a_a_callback = sf.getAllUsersAllAppointments();
			} catch (Exception e) {
				g_a_u_a_a_callback = new HashMap<User, ArrayList<Appointment>>();
			}
			try {
				output.writeObject(g_a_u_a_a_callback);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println("sent back " + g_a_u_a_a_callback.size() + "users with respective appointments");
			break;
		case GET_ALL_APPOINTMENTS:
			System.out.println("WE HAVE RECIEVED GET ALL APPOINTMENTS REQUEST");
			ArrayList<Appointment> g_a_a_callback;
			try {
				g_a_a_callback = sf.getAllAppointments(currentUser);
			} catch (Exception e) {
				g_a_a_callback = new ArrayList<Appointment>();
			}
			try {
				output.writeObject(g_a_a_callback);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("sent back " + g_a_a_callback.size() + "appointments");
			break;
		case GET_ALL_APPOINTMENT_USERS:
			System.out.println("getting all users with status for appointment y");
			HashMap<User, Status> g_a_a_u_callback;
			try {
				g_a_a_u_callback = sf.getParticipants((Appointment) am );
			} catch (Exception e) {
				g_a_a_u_callback = new HashMap<User, Status>();
			}
			try {
				output.writeObject(g_a_a_u_callback);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("sent back users with statuses for " + ((Appointment) am).getId());
			break;
		case GET_ALL_USERS:
			System.out.println("Vi vil ha alle brukerne!");
			ArrayList<User> g_a_u_callback;
			try {
				g_a_u_callback = sf.getAllUsers();
			} catch (Exception e) { 
				g_a_u_callback = new ArrayList<User>();
			}
			try {
				output.writeObject(g_a_u_callback);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("sent back " + g_a_u_callback.size() + " users");
			break;
		case INSERT:
			if ( cl.equals(Appointment.class)) {
				System.out.println("Vi har fatt insert request for en appointment!");
				Appointment i_u_callback;
				try {
					i_u_callback = sf.insertAppointment( (Appointment) am);
					i_u_callback.setAction(Action.SUCCESS);
				} catch (Exception e) {
					i_u_callback = am.getCopy();
					i_u_callback.setAction(Action.MASSIVE_FAILURE);
				}
				try {
					output.writeObject(i_u_callback);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("sent back " + action + " with status " + i_u_callback.getAction());
			}
			break;
		case GET_MEETINGPOINT:
			ArrayList<MeetingPoint> g_m_callback = new ArrayList<MeetingPoint>();
			g_m_callback.add(new MeetingPoint(10, "yolo", 40));
			g_m_callback.add(new MeetingPoint(13, "holo", 30));
			try {
				output.writeObject(g_m_callback);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;
		case SET_STATUS_ATTENDING:
		case SET_STATUS_NOT_ATTENDING:
			Callback callback;
			try {
				sf.setStatus((Appointment) am, currentUser, action);
				callback = new Callback(Action.SUCCESS);
			} catch (Exception e) {
				callback = new Callback(Action.MASSIVE_FAILURE);
				e.printStackTrace();
			}
			System.out.println("sat status " + action);
			try {
				output.writeObject(callback);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("sent back " + action + " with status " + callback.getAction());
			break;
		case NOTIFICATION:
			logConsole("received notification");
			//receive notification
			Notification n_callback = (Notification) am;
			//extract users
			ArrayList<User> users = n_callback.getAppointment().getParticipants();
			//call sp, and save notification for all users that match
			try {
				sp.saveNotifications(n_callback, users);
				n_callback.setAction(Action.SUCCESS);
			} catch (Exception e) {
				n_callback.setAction(Action.MASSIVE_FAILURE);
				e.printStackTrace();
			}
			//write back to client
			try {
				output.writeObject(n_callback);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("sent back " + action + " with status " + n_callback.getAction());
			break;
			//getting all the notifications for this specific user.
		case GET_NOTIFICATION:
			logConsole("fetching notification");
			ArrayList<Notification> al_n_callback;
			try {
				al_n_callback = sp.fetchAppointments((User) am);
			} catch (Exception e) {
				al_n_callback = new ArrayList<Notification>();
				e.printStackTrace();
			}
			try {
				output.writeObject(al_n_callback);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("sent back " + action + " with size " + al_n_callback.size());
			break;
		case UPDATE:
			if ( cl.equals(Appointment.class)) {
				System.out.println("Vi har fatt update request for en appointment!");
				Appointment u_u_callback;
				try {
					u_u_callback = sf.updateAppointment( (Appointment) am);
					u_u_callback.setAction(Action.SUCCESS);
				} catch (Exception e) {
					u_u_callback = am.getCopy();
					u_u_callback.setAction(Action.MASSIVE_FAILURE);
				}
				try {
					output.writeObject(u_u_callback);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("sent back " + action + " with status " + u_u_callback.getAction());
			}
			break;
		case MASSIVE_FAILURE:
			System.out.println("could not read input properly");
		default:
			System.out.println("action did not match any enum");
			break;
		}
		
		if (close) {
			closeConnection();
		}
		
	}
}