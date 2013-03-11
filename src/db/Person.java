package db;

public class Person {
	private int id;
	private String name;
	private String password;
	private String email;
	
	public Person(int id){
		this.id = id;
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
