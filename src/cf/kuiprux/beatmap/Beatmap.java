package cf.kuiprux.beatmap;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import cf.kuiprux.game.Event;
import cf.kuiprux.game.Note;

public class Beatmap {

	Image logo;
	String name;
	String description;
	String author;
	String songPath;
	int difficulty;
	int defaultBpm;
	
	double offset;
	//bpm ��ȯ, ��� ���� ������ ���� ����
	List<Event> eventList = new ArrayList<>();
	//��Ʈ ����Ʈ
	List<Note> beats = new ArrayList<>();
	
}