package cf.kuiprux.spbeat.gui.effect;

/*
 * drawable effect ü�� ȣ�� Ŭ����
 * ���� �������� effect�� ������ ü�� ȣ���
 */
public interface IEffectResult<T extends IEffectResult<?>> extends IAnimatable<T> {
	
	//ü�� �����ϰ� ����
	void start();
}
