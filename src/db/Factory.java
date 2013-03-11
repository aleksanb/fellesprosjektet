package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.sql.PreparedStatement;

public class Factory {
	
	DBConnection db;
	
	public Factory(Properties properties) throws ClassNotFoundException, SQLException {
		db = new DBConnection(properties);	
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
	
}
