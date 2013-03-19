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
import db.MeetingPoint;
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
//		System.out.println(result.get(1).getParticipants());
//		System.out.println(result.get(1).getPlace());
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
		ResultSet apps;
		ArrayList<Appointment> results = new ArrayList<Appointment>();
		try {
			System.out.println("preparing to check user");
			//send query to db
			db.initialize();
			prest = db.preparedStatement("SELECT * FROM sids.appointment WHERE creatorUserId=?;");
			prest.setInt(1, u.getId());
			System.out.println(prest);
			//returns query
			apps = prest.executeQuery();
			GregorianCalendar start;
			GregorianCalendar end;
			//makes query to a appointment object
			while (apps.next()) {
				start = new GregorianCalendar();
				start.setTime(apps.getTimestamp("start"));
				end = new GregorianCalendar();
				end.setTime(apps.getTimestamp("end"));
				Appointment temp = new Appointment(apps.getInt("id"), apps.getInt("creatorUserId"), apps.getString("title"), start, end, apps.getString("description"), apps.getBoolean("isMeeting"));
				if(temp.isMeeting()){
					temp.setParticipants(getParticipants(temp));
					temp.setPlace(getMeetingPoint(temp));
				}
				results.add(temp);
			}
			db.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("something fucked up while getting appointments");
		}
		return results;
	}
	private MeetingPoint getMeetingPoint(Appointment app) throws ClassNotFoundException, SQLException {
		DBConnection conn = new DBConnection(p);
		PreparedStatement prest;
		ResultSet mPoint;
		MeetingPoint results=null;
		try {
			System.out.println("preparing to check appointment for the meeting place");
			//send query to db
			conn.initialize();
			prest = conn.preparedStatement("SELECT meetingpoint.id,name,capacity FROM " +
					"((sids.appointment JOIN sids.appointment_meetingpoint ON appointment.id=?)JOIN sids.meetingpoint ON meetingpoint.id=meetingpointId);");
			prest.setInt(1, app.getId());
			System.out.println(prest);
			//returns query
			mPoint = prest.executeQuery();
			//makes query to a MeetingPoint object
			while (mPoint.next()) {
				results = new MeetingPoint(mPoint.getInt("id"), mPoint.getString("name"), mPoint.getInt("capacity"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("something fucked up while getting meeting place");
		}
		System.out.println(results);
		conn.close();
		return results;
	}
	private ArrayList<User> getParticipants(Appointment app) throws ClassNotFoundException, SQLException{
		DBConnection conn = new DBConnection(p);
		PreparedStatement prest;
		ResultSet ppants;
		ArrayList<User> results = new ArrayList<User>();
		try {
			System.out.println("preparing to check appointment for participants");
			//send query to db
			conn.initialize();
			prest = conn.preparedStatement("SELECT user.id, name, email, hashedPassword " +
					"FROM ((sids.appointment JOIN sids.user_appointment ON appointment.id=?)JOIN sids.user ON user.id=userId);");
			prest.setInt(1, app.getId());
			System.out.println(prest);
			//returns query
			ppants = prest.executeQuery();
			//makes query to a list of User
			while (ppants.next()) {
				results.add(new User(ppants.getInt("id"), ppants.getString("name"), ppants.getString("email"), ppants.getString("hashedPassword")));
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("something fucked up while getting participants");
		}
		System.out.println(results);
		conn.close();
		return results;		
	}
	
}
