package db;

import java.io.Serializable;

public class Group implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;

	public Group(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
}
