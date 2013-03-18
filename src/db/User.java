package db;

public class User {
	private int id;
	private String name;
	private String hashedPassword;
	private String email;
	
	public User(int id, String name, String hashedPassword, String email){
		this.id = id;
		this.name = name;
		this.hashedPassword = hashedPassword;
		this.email = email;
	}
	public User(String name, String HashedPassword, String email) {
		this.name = name;
		this.hashedPassword = HashedPassword;
		this.email = email;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}
	public void setHashedPassword(String hashedPassword){
		this.hashedPassword = hashedPassword;
	}
	public String getPassword(){
		return hashedPassword;
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
