package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * It might just allow one connection atm
 * @author espen
 *
 */

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
					whileRunning();
				//when ending connection
				}catch(EOFException e){
					showMessage("\n Server ended the connection");
				}finally{
					closeApp();
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	//wait for connection then display connection info
	private void waitForConnection()throws IOException{
		showMessage(" Waiting for someone to connect...\n");
		connection = server.accept();
		showMessage(" Now connected to"+connection.getInetAddress().getHostName());
	}
	//get stream to send and receive data
	private void setupStreams()throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n Streams are now set up!\n");	
	}
	//after connection is setup 
	private void whileRunning()throws IOException{
		
		do{
			try{
				;
			}catch(Exception e){
				;
			}
		}while(true/*end condition*/); 
	}
	private void closeApp(){
		showMessage("\n Closing connections" );
		try{
			output.close();
			input.close();
			connection.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	
	public void showMessage(final String text){
		System.out.println(text);
	}
}