package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import db.Notification;
import db.User;

public class ServerProgram {
	//network
	private Socket connection;
	private ServerSocket server;
	//lists
	HashMap<Integer,User> onlineUsers= new HashMap<Integer,User>();
	HashMap<Integer,ArrayList<Notification>> notifications = new HashMap<Integer,ArrayList<Notification>>();
	
	public ServerProgram(){
	}

	public static void main(String[] args) {
		ServerProgram sp = new ServerProgram();
		sp.run();
	}
	public void run(){
		try {
			server = new ServerSocket(6789,100);
		} catch (IOException e1) {
			logConsole("problem setting up ports and sockets");
			e1.printStackTrace();
		}
		int i = 0;
		while(true){
			try {
				waitForConnection();
				new Thread(new Server(connection,i,this)).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
			i++;
		}
	}
	private void waitForConnection()throws IOException{
		logConsole("Waiting for someone to connect...");
		connection = server.accept();
		logConsole("Now connected to "+connection.getInetAddress().getHostName());
	}
	public static void logConsole(String text){
		System.out.println("SERVER: "+text);
	}
//	public void addOnlineUserConnection(User user, Socket connection){
//		onlineUsers.put(user.getId(), user);
//		userConnection.put(user, connection);
//		System.out.println("ONLINE USERS: "+onlineUsers);
//		
//	}

//	public void removeOnlineUsers(int id) {
//		User user = onlineUsers.remove(id);
//		userConnection.remove(user);
//	}

	public void saveNotifications(Notification n_callback, ArrayList<User> users) throws IOException {
		for (User user : users) {
			try{
			notifications.get(user.getId()).add(n_callback);
			}catch(NullPointerException e){
				notifications.put(user.getId(), new ArrayList<Notification>());
				notifications.get(user.getId()).add(n_callback);
				System.out.println("added new user to list of users");
			}
							
		}
	}

	public ArrayList<Notification> fetchAppointments(User am) {
		if(notifications.get(am.getId())==null)
			notifications.put(am.getId(), new ArrayList<Notification>());//gets a new lsit if not registred
		ArrayList<Notification> retmsg = notifications.get(am.getId());//saves the list temp..
		notifications.put(am.getId(), new ArrayList<Notification>());//cleans out the list
		return retmsg;//returns the temp
	}

}
