package db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Properties;

import com.google.gson.Gson;
import com.sun.jndi.url.corbaname.corbanameURLContextFactory;

public class JsonFactory {
	Gson gson;
	private Properties prop;
	
	public JsonFactory() {
		gson = new Gson();
	}
	
	private void logConsole(String text){
		System.out.println("CLIENT: "+ text);
	}

	public String generateJsonCommand(Action action, Object o) {
		ActionObject ao = new ActionObject(action, o);
		return gson.toJson(ao);
	}
	
	public User generateUser(String json) {
		return gson.fromJson(json, User.class);
	}
	
}
