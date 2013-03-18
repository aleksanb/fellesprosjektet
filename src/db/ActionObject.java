package db;

import java.util.ArrayList;

public class ActionObject {
	private Action action;
	private String cl;
	private ArrayList<Object> alObject;
	
	public ActionObject(Action action, Object object) {
		this.action = action;
		this.alObject = new ArrayList<Object>();
		this.cl = generateClassName(object);
		this.alObject.add(object);
	}
	
	private String generateClassName(Object o) {
		String[] s = o.getClass().toString().toLowerCase().split(" ")[1].split("\\.");
		return s[0] + "." + s[1].substring(0,1).toUpperCase() + s[1].substring(1);
	}
	
	public void addObject(Object object) {
		alObject.add(object);
	}

	public Action getAction() {
		return action;
	}

	public String getCl() {
		return cl;
	}

	public ArrayList<Object> getAlObject() {
		return alObject;
	}
	
}
