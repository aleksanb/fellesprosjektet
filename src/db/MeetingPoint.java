package db;

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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MeetingPoint other = (MeetingPoint) obj;
		if (id != other.id)
			return false;
		return true;
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
