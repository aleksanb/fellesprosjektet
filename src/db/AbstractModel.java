package db;

public interface AbstractModel {

	public void setAction(Action action);
	public Action getAction();
	public <T> T getCopy();
	
}