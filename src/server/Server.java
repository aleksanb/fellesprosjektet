package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;


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
		boolean closeConnection = false;
		do{
			try{
				;
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
	
	
	public void logConsole(String text){
		System.out.println("SERVER: "+text);
	}
}