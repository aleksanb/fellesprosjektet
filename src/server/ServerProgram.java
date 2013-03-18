package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerProgram {

	private static Socket connection;
	private static ServerSocket server;

	public static void main(String[] args) {
			
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
					new Thread(new Server(connection,i)).start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			}
	}
	private static void waitForConnection()throws IOException{
		logConsole("Waiting for someone to connect...");
		connection = server.accept();
		logConsole("Now connected to "+connection.getInetAddress().getHostName());
	}
	public static void logConsole(String text){
		System.out.println("SERVER: "+text);
	}

}
