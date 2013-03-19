package server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBConnection {
	private Properties properties; // file containing the connection properties
	private String jdbcDriver; // String containing the driver Class name
	private String url; // Address to the database
	private Connection conn;
	
	public DBConnection(String propertiesFilename) throws IOException, ClassNotFoundException, SQLException {
		File f = new File(propertiesFilename);
		properties = new Properties();
		properties.load(new FileInputStream(f));
		jdbcDriver = properties.getProperty("jdbcDriver");
		url = properties.getProperty("url");
	}

	public DBConnection(Properties properties) throws ClassNotFoundException,
			SQLException {
		this.properties = properties;
		jdbcDriver = properties.getProperty("jdbcDriver");
		url = properties.getProperty("url");

	}

	public void initialize() throws ClassNotFoundException, SQLException {
		Class.forName(jdbcDriver);
		Properties info = new Properties();

		info.setProperty("user", properties.getProperty("user"));
		info.setProperty("password", properties.getProperty("password"));
		conn = DriverManager.getConnection(url, info);
	}

	public ResultSet makeSingleQuery(String sql) throws SQLException {
		Statement st = conn.createStatement();
		return st.executeQuery(sql);
	}

	public void makeSingleUpdate(String sql) throws SQLException {
		Statement st = conn.createStatement();
		st.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
	}

	public PreparedStatement preparedStatement(String sql) throws SQLException {
		return conn.prepareStatement(sql);
	}
	
	public PreparedStatement preparedStatement(String sql, int args) throws SQLException {
		return conn.prepareStatement(sql, args);
	}

	public void close() throws SQLException {
		conn.close();
	}

}