package libgdxpluginv01.models.uielements;

public abstract class CAction {
	private boolean completed;

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	public abstract void animate(float time);
	
}
