package db;

import java.io.Serializable;

public class MeetingPoint implements AbstractModel {
	private Action action;
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private int capacity;

	public MeetingPoint(int id, String name, int capacity) {
		this.id = id;
		this.name = name;
		this.capacity = capacity;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	@Override
	public String toString() {
		return "MeetingPoint [id=" + id + ", name=" + name + ", capacity="
				+ capacity + "]";
	}
	@Override
	public void setAction(Action action) {
		this.action=action;
	}
	@Override
	public Action getAction() {
		return action;
	}
	@Override
	public <T> T getCopy() {
		return (T) new MeetingPoint(this.id, this.name, this.capacity);
	}
	
}
