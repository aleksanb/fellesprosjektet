package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import db.User;

public class ServerProgram {
	//network
	private Socket connection;
	private ServerSocket server;
	//lists
	HashMap<Integer,User> onlineUsers= new HashMap<Integer,User>();
	HashMap<User,Socket> userConnection = new HashMap<User, Socket>();
	
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
				// TODO Auto-generated catch block
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
	public void addOnlineUserConnection(User user, Socket connection){
		onlineUsers.put(user.getId(), user);
		userConnection.put(user, connection);
		
	}

	public void removeOnlineUsers(int id) {
		User user = onlineUsers.remove(id);
		userConnection.remove(user);
		
	}

}
