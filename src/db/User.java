package db;

public class User {
	private int id;
	private String name;
	private String email;
	private String hashedPassword;
	
	public User() {
		this.id = 1337;
		this.name = "leeroy jenkins";
		this.hashedPassword = "hunter2";
		this.email = "too.leqit@to.quit";
	}
	
	public User(int id, String name, String email, String hashedPassword){
		this.id = id;
		this.name = name;
		this.hashedPassword = hashedPassword;
		this.email = email;
	}
	public User(String name, String email, String HashedPassword) {
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
	public void setId(int id){
		this.id = id;
	}
	public int getId(){
		return id;
	}

}
