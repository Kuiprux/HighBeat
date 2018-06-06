package cf.kuiprux.spbeat.gui.effect;

// �ִϸ��̼� ȿ��
public abstract class DrawEffect implements IDrawEffect {
	
	private float startTime;
	private float duration;
	
	public DrawEffect() {
		
	}
	
	public float getStartTime() {
		return startTime;
	}
	
	public float getDuration() {
		return duration;
	}
	
	public float getEndTime() {
		return getStartTime() + getDuration();
	}
	
	public boolean isEnded(float currentTime) {
		return getEndTime() < currentTime;
	}
}
