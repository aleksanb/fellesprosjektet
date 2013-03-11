package db;

public class User {
	private int id;
	private String name;
	private String password;
	private String email;
	
	public User(int id, String name, String password, String email){
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	public void setPassword(String password){
		this.password = password;
	}
	public String getPassword(){
		return password;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public String getEmail(){
		return email;
	}
	public int getId(){
		return id;
	}
}