package cf.kuiprux.spbeat.gui;

//���൵ �׷���
public abstract class EasingType {
	
	public static final EasingType LINEAR = new EasingType() {
		@Override
		public float convertProgress(float progress) {
			return progress;
		}
	};
	
	//progress = 0 ~ 1 ��ȯ�� progress �� ��ȯ
	public abstract float convertProgress(float progress);
}
