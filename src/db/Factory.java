package db;

import java.sql.ResultSet;

public class Factory {
	
	DBConnection db;
	
	public Factory(Properties properties) {
		db = new DBConnection(properties);	
	}
	
	public User createUser(String name, String password, String email) {
		
		String query = String.format("INSERT INTO user " + 
		"(name, password, email) VALUES ('%s', '%s', '%s')", name, password, email);
		
		db.initialize();
		db.makeSingleUpdate(query);
		db.close();
		
		User u = new User(name, password, email);
		return u;
	}

	public boolean checkValid(String username, String password) {
		return true;
	}
	
	
}
