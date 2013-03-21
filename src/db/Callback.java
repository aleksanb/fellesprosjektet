package db;

public class Callback implements AbstractModel {
	private Action action;

	public Callback(Action action) {
		this.action = action;
	}
	
	@Override
	public void setAction(Action action) {
		this.action = action;
	}

	@Override
	public Action getAction() {
		return this.action;
	}

	@Override
	public <T> T getCopy() {
		return (T) new Callback(this.action);
	}

}
