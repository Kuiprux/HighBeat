package cf.kuiprux.spbeat.game.beatmap;

public class Note implements INote {
	
	//��Ʈ�� ��Ÿ�� ��ġ
	private int noteIndex;
	//��Ʈ�� Ŭ���Ǿ� �Ǵ� ��Ȯ�� �ð�
	private float exactTime;
	
	public Note(int noteIndex, float exactTime) {
		this.noteIndex = noteIndex;
		this.exactTime = exactTime;
	}
	
	public int getNoteIndex() {
		return noteIndex;
	}
	
	public float getExactTime() {
		return exactTime;
	}

	@Override
	public String toString(){
		return "Note index: " + getNoteIndex() + " time: " + getExactTime();
	}
}
