package cf.kuiprux.spbeat.game.beatmap;

public class Note {
	
	//��Ʈ�� ��Ÿ�� ��ġ
	private int noteIndex;
	//��Ʈ�� Ŭ���Ǿ� �Ǵ� ��Ȯ�� �ð�
	private int exactTime;
	
	public Note(int noteIndex, int exactTime) {
		this.noteIndex = noteIndex;
		this.exactTime = exactTime;
	}
	
	public int getNoteIndex() {
		return noteIndex;
	}
	
	public int getExactTime() {
		return exactTime;
	}
}
