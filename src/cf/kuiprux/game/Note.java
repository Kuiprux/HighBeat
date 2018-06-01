package cf.kuiprux.game;

public class Note {
	//��Ʈ�� ���� ��ư ��ġ
	private int buttonIndex;
	//Ŭ���ؾ� �Ǵ� �ð�
	private int exactTime;
	//Ŭ���� �ð�
	private int pressedTime;
	
	public Note(int buttonIndex, int exactTime) {
		this.buttonIndex = buttonIndex;
		this.exactTime = exactTime;
	}

	public int getPressedTime() {
		return pressedTime;
	}

	public int getExactTime() {
		return exactTime;
	}

	public int getButtonIndex() {
		return buttonIndex;
	}
}
