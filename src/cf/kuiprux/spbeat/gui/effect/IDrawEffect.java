package cf.kuiprux.spbeat.gui.effect;

import org.newdawn.slick.Graphics;

public interface IDrawEffect {

	// �ý��� �ð� ���
	long getStartTime();
	
	int getDuration();
	
	long getEndTime();
	
	boolean isEnded(long currentTime);
	
	//effect ���۽� �ߵ�
	void onStart();
	void applyAt(long currentTime, Graphics graphics);

	void setStartTime(long startTime);
}
