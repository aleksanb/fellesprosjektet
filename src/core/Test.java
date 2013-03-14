package core;

import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import db.Factory;

public class Test {
	
	public static void main(String args[]) {
		
		Properties p = new Properties();
		
		try {
			System.out.println("initializing battlestations");
			p.load(new FileInputStream(new File("resources/database.properties")));
			System.out.println(p.toString());
			
			Factory f = new Factory(p);
			System.out.println(f.checkValid("aleksander", "passord"));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}
