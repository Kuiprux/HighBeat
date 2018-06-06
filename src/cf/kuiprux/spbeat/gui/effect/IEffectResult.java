package cf.kuiprux.spbeat.gui.effect;

import cf.kuiprux.spbeat.gui.EasingType;

/*
 * drawable effect ü�� ȣ�� Ŭ����
 * ���� �������� effect�� ������ ü�� ȣ���
 */
public interface IEffectResult<T extends IEffectResult<?>> {
	
	void expire();
	
	default T fadeIn(EasingType type, int duration) {
		return fadeTo(1, type, duration);
	}
	
	default T fadeOut(EasingType type, int duration) {
		return fadeTo(0, type, duration);
	}
	
	T fadeTo(float opacity, EasingType type, int duration);
	
	default T moveToRelativeX(float x, EasingType type, int duration) {
		return moveToRelative(x, 0, type, duration);
	}
	
	default T moveToRelativeY(float y, EasingType type, int duration) {
		return moveToRelative(0, y, type, duration);
	}
	
	T moveToRelative(float x, float y, EasingType type, int duration);
	
	T rotateTo(float rotation, EasingType type, int duration);

	//ü�� ���� ��� ���� �׳� ����
	void start();
	
}
