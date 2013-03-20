package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
		//User u = new User(1, "espen", "master@commander.net", "hunter2");
		Appointment a = new Appointment(0, 1, "title", new GregorianCalendar(), new GregorianCalendar(), "first test meeting", true);
		a.setMeetingPoint(new MeetingPoint(1, "mordi", 200));
		a.addParticipant(new User(1, "espen", "master@commander.net", "hunter2"));
		a.addParticipant(new User(8, "aleksander", "email", "passord"));
		//System.out.println(a.getMeetingPoint().getId());
		//User result = sf.login(new User("aleksander", "email", "passord"));
		Appointment result = sf.insertAppointment(a);
		System.out.println(result);
	}
	
	public User login(User u) {
		PreparedStatement prest;
		User result = null;
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
				result = new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("hashedPassword"));
			}
				
			db.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("something fucked up");
		}
		return result;
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
	
	public Appointment insertAppointment(Appointment appointment) {
		PreparedStatement prest;
		ResultSet generatedKeys;
		int appointmentId = 0;
		try {
			db.initialize();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			// create and insert new appointment into database
			prest = db.preparedStatement("INSERT INTO sids.appointment (creatorUserId, title, start, end, description, isMeeting)"+
			" VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			prest.setInt(1, appointment.getId());
			prest.setString(2, appointment.getTitle());
			prest.setTimestamp(3, new Timestamp(appointment.getStart().getTimeInMillis()));
			prest.setTimestamp(4, new Timestamp(appointment.getEnd().getTimeInMillis()));
			prest.setString(5, appointment.getDescription());
			prest.setBoolean(6, appointment.isMeeting());
			System.out.println("executing appointment insert");
			prest.executeUpdate();
			
			generatedKeys = prest.getGeneratedKeys();
			if (generatedKeys.next()) { // if successfully inserted
				appointmentId = generatedKeys.getInt(1);
				
				// add appointment to creator
				prest = db.preparedStatement("INSERT INTO sids.user_appointment (userId, appointmentId) VALUES (?, ?)");
				prest.setInt(1, appointment.getCreatorUserId());
				prest.setInt(2, appointmentId);
				prest.executeUpdate();
				
				if (appointment.isMeeting()) { 
					prest = db.preparedStatement("INSERT INTO sids.user_appointment (userId, appointmentId) VALUES (?, ?)");
					ArrayList<User> participants = appointment.getParticipants();
					
					// create user-appointment connection for all users
					for (int i = 0; i < participants.size(); i++) { 
						System.out.println("executing userAppointment nr. " + i + " " + participants.get(i).getName());
						prest.setInt(1, participants.get(i).getId());
						prest.setInt(2, appointmentId);
						prest.executeUpdate();
					}
					// create appointment - room connection
					System.out.println("executing appointmentMeetingpoint");
					prest = db.preparedStatement("INSERT INTO sids.appointment_meetingpoint (appointmentId, meetingpointId) VALUES(?, ?)");
					prest.setInt(1, appointmentId);
					prest.setInt(2, appointment.getMeetingPoint().getId());
					System.out.println("executing appointmentMeetingPoint");
					prest.executeUpdate();
					
				}
			}
			
			System.out.println("finished inserting");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			db.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (appointmentId != 0) {
			appointment.setId(appointmentId);
			return appointment;
		} else {
			return null;
		}
	}
	
}
