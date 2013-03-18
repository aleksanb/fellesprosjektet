package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server implements Runnable{
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket connection;
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
		boolean closeConnection = false;
		do{
			try{
				logConsole((String)input.readObject());
			}catch(Exception e){
				logConsole("Error reading data");
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
	
	
	public void logConsole(String text){
		System.out.println("SERVER "+connectionID+": " +text);
	}
}