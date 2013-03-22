package db;

public class Callback implements AbstractModel {
	private static final long serialVersionUID = 1L;
	private Action action;

	public Callback(Action action) {
		this.action = action;
	}
	
	public Callback() {
		this.action = null;
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
