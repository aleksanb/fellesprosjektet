package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.sql.PreparedStatement;

import db.Appointment;
import db.User;


public class ServerFactory {
	// Database connection
	DBConnection db;
	Properties p;
	
	public ServerFactory() {
		p = new Properties();
		try {
			p.load(new FileInputStream(new File("resources/database.properties")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			db = new DBConnection(p);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
	public static void main(String args[]) {
		ServerFactory sf = new ServerFactory();
		System.out.println("created serverFactory");
//		String result = sf.login(new User("aleksander", "email", "passord"));
		ArrayList<Appointment> result = sf.allAppointments(new User(1,"","",""));
		System.out.println(result);
	}
	
	
	public String login(User u) {
		PreparedStatement prest;
		String rslt = "null";
		try {
			System.out.println("preparing to check user");
			//System.out.println(p);
			db.initialize();
			
			prest = db.preparedStatement("SELECT * FROM sids.user WHERE sids.user.name = ? AND sids.user.hashedPassword = ? LIMIT 1;");
			prest.setString(1, u.getName());
			prest.setString(2, u.getPassword());
			System.out.println(prest);
			
			ResultSet rs = prest.executeQuery();
			
			while (rs.next()) {
				rslt = String.format("{'id':'%d','name':'%s','hashedPassword':'%s','email':'%s'}", 
						rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("hashedPassword"));
			}
				
			db.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("something fucked up");
		}
		return rslt;
	}
	
	public User createUser(String name, String password, String email) throws ClassNotFoundException, SQLException {

		PreparedStatement prest;
		ResultSet generatedKeys;
		int userId = 0;
		
		db.initialize();
		
		prest = db.preparedStatement("INSERT INTO sids.user (name, hashedPassword, email) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
		prest.setString(1, name);
		prest.setString(2, password);
		prest.setString(3, email);
		prest.executeUpdate();
		
		generatedKeys = prest.getGeneratedKeys();
		if (generatedKeys.next()) {
			userId = generatedKeys.getInt(1);
		}
		
		db.close();
		
		System.out.println("finished!");
		
		if (userId != 0) {
			return new User((int)userId, name, password, email);
		} else {
			return null;
		}
		
	}

	public ArrayList<Appointment> allAppointments(User u) {
		PreparedStatement prest;
		String rslt = "null";
		ResultSet rs;
		ArrayList<Appointment> results = new ArrayList<>();
		try {
			System.out.println("preparing to check user");
			db.initialize();
			//send query to db
			prest = db.preparedStatement("SELECT * FROM sids.appointment WHERE creatorUserId=?;");
			prest.setInt(1, u.getId());
			System.out.println(prest);
			//returns query
			rs = prest.executeQuery();
			GregorianCalendar start;
			GregorianCalendar end;
			//makes query to a appointment object
			while (rs.next()) {
				start = new GregorianCalendar();
				start.setTime(rs.getTimestamp("start"));
				end = new GregorianCalendar();
				end.setTime(rs.getTimestamp("end"));
				results.add(new Appointment(rs.getInt("id"), rs.getInt("creatorUserId"), rs.getString("title"), start, end, rs.getString("description"), rs.getBoolean("isMeeting")));
				}
			db.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("something fucked up");
		}
		return results;
	}
	
}
