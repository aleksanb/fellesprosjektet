package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import db.AbstractModel;
import db.Action;
import db.Appointment;
import db.User;


public class Server implements Runnable{
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket connection;
	
	boolean closeConnection;
	private ServerFactory sf;
	int connectionID;

	public Server(Socket connection, int connectionID) {
		this.connection=connection;
		this.connectionID = connectionID;
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

		AbstractModel am = (AbstractModel) input.readObject();
		Class<? extends AbstractModel> cl = am.getClass();
		Action action = am.getAction();
		System.out.println("Read object!"+am.toString());
		
		switch(action) {
		case LOGIN:
			System.out.println("WE HAVE RECIEVED LOGIN REQUEST");
			User l_callback = sf.login((User) am);
			output.writeObject(l_callback);
			System.out.println("wrote " + l_callback.getName() + " back to client");
			/*User lookup = gson.fromJson(alo.get(0).toString(), User.class);
			User l_u_callback = sf.login(lookup);
			// add event listener
			System.out.println("sending user " + l_u_callback.getName() + " back to login");
			output.writeObject(l_u_callback);*/
			break;
		case LOGOUT:
			System.out.println("logging out. Going down with the ship cap'n");
			//System.out.println("Logging out " + (alo.get(0)));
			// remove event listeners
			closeConnection();
			break;
			
		case DELETE:
			/*for (int i = 0; i < alu.size(); i++) {
				System.out.println("DELETE FROM sids." + cl + "where sids." + cl + "==" + alu.get(i));
			}*/
			break;
		case GET:
			/*if ( cl.equals(Appointment.class)) {
				System.out.println("Vi har fatt get request for en appointment!");
			}*/
			break;
		case GET_ALL_APPOINTMENTS:
			System.out.println("WE HAVE RECIEVED GET ALL APPOINTMENTS REQUEST");
			//ArrayList<Appointment> sqld_apps = sf.allAppointments(appslookup);
			//TODO: add event listener
			//System.out.println("sending appointments " + sqld_apps + " back to login");
			//output.writeObject(sqld_apps);
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
			break;
		case UPDATE:
			break;
		default:
			System.out.println("action did not match any enum");
			break;
		}
	}
}