package cf.kuiprux.spbeat.game.gui;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.BiConsumer;

import cf.kuiprux.spbeat.game.beatmap.Beatmap;
import cf.kuiprux.spbeat.game.loader.BeatmapLoader;
import org.newdawn.slick.Color;

import cf.kuiprux.spbeat.game.gui.ButtonPanel.ButtonArea;
import cf.kuiprux.spbeat.gui.AlignMode;
import cf.kuiprux.spbeat.gui.EasingType;
import cf.kuiprux.spbeat.gui.element.Square;

public class LoadingScreen extends ScreenPreset {

	private static final Path SONG_PATH;

	static {
		SONG_PATH = Paths.get("fumens");
	}

	@Override
	public void onPress(int keyIndex) {
		
	}

	@Override
	public void onUp(int keyIndex) {
		
	}

	@Override
	protected void onLoad() {
		getButtonPanel().getBackground().setColor(Color.green);

		BeatmapLoader loader = new BeatmapLoader();

		try {
			//��!
			List<Beatmap> beatmapList = loader.loadAll(SONG_PATH).run().get();

            for (Beatmap map : beatmapList){
                System.out.println("ä�� " + map.getTitle() + " �� �߰� �Ǿ����ϴ�.");
                getGame().getMapManager().addBeatmap(map);
            }

            getScreenManager().setCurrentScreen(new BeatmapSelectScreen());
		} catch (Exception e) {
			System.out.println("��Ʈ�� ������ ���� ���� �ʰų� �ջ� �Ǿ����ϴ�. " + e.getLocalizedMessage());
		}
	}

	@Override
	protected void onUnload() {
		
	}

	@Override
	protected void update(int delta) {
		
	}

}
