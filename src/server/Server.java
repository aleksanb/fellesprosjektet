package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import db.Action;
import db.ActionObject;
import db.ServerFactory;
import db.User;


public class Server {
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	private Gson gson;
	boolean closeConnection;
	private ServerFactory sf;


	//set up and run the server
	public void startRunning(){
		try{
			server = new ServerSocket(6789,100);
			while(true){
				try{
					waitForConnection();
					setupStreams();
					new Thread(new Runnable() {
						@Override
						public void run(){
							try {
								whileRunning();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}).start();
				//when ending connection
				}catch(EOFException e){
					logConsole("Server ended the connection");
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	//wait for connection then display connection info
	private void waitForConnection()throws IOException{
		logConsole("Waiting for someone to connect...");
		connection = server.accept();
		logConsole("Now connected to "+connection.getInetAddress().getHostName());
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
				;
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
		}
	}
	
	private void closeConnection() {
		closeConnection = false;
	}
	
	
	public void logConsole(String text){
		System.out.println("SERVER: "+text);
	}
	
	private void handleShit() throws ClassNotFoundException, JsonSyntaxException, IOException {
		//System.out.println("vi er i handleShit");
		String incoming = (String) input.readObject();
		System.out.println("incoming is " + incoming);
		gson = new Gson();
		ActionObject ao = gson.fromJson(incoming, ActionObject.class);
		Action action = ao.getAction();
		Class<?> cl = Class.forName(ao.getCl());
		//System.out.println("stage 2");
		//System.out.println(cl);
		
		ArrayList<Object> alo = ao.getAlObject();
		for ( int i = 0; i < alo.size(); i++ ) {
			System.out.println(alo.get(i));
		}
		ArrayList<Object> alu = new ArrayList<Object>();
		/*System.out.println(("created arrayLists"));
		for (int i = 0; i < alo.size(); i++) {
			//System.out.println(Integer.toString(i) + "th iteration");
			System.out.println("attempting to add" + alo.get(i));
			System.out.println(gson.fromJson(alo.get(i).toString(), cl).toString());
			alu.add(gson.fromJson(alo.get(i).toString(), cl));
			System.out.println("added " + alo.get(i).toString());
		}*/
		//System.out.println("stage 3");
		//System.out.println(cl.toString().split("\\.")[1]);
		String sql;
		System.out.println("switching");
		switch(action) {
		case LOGIN:
			System.out.println("WE HAVE RECIEVED LOGIN REQUEST");
			User lookup = gson.fromJson(alo.get(0).toString(), User.class);
			String sqld_user = sf.login(lookup);
			// add event listener
			System.out.println("sending user " + sqld_user + " back to login");
			output.writeObject(sqld_user);
			break;
		case LOGOUT:
			System.out.println("logging out. Going down with the ship cap'n");
			System.out.println("Logging out " + (alo.get(0)));
			// remove event listeners
			closeConnection();
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
			System.out.println("action did not match any enum");
			break;
		}
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		server.startRunning();
	}
	
}