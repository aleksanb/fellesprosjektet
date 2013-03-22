package db;

import java.io.Serializable;

public interface AbstractModel extends Serializable {

	public void setAction(Action action);
	public Action getAction();
	public <T> T getCopy();
	
}