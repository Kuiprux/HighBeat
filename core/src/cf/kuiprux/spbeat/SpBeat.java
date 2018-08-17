package cf.kuiprux.spbeat;

import java.util.logging.Level;

import cf.kuiprux.spbeat.game.ResourceManager;
import cf.kuiprux.spbeat.game.MainThreadExecutor;

import cf.kuiprux.spbeat.game.MapManager;
import cf.kuiprux.spbeat.game.PlayManager;
import cf.kuiprux.spbeat.game.controller.FallbackController;
import cf.kuiprux.spbeat.game.controller.GameController;
import cf.kuiprux.spbeat.game.controller.IControllerListener;
import cf.kuiprux.spbeat.game.controller.SpbeatController;
import cf.kuiprux.spbeat.game.gui.ButtonPanel;
import cf.kuiprux.spbeat.game.gui.LoadingScreen;
import cf.kuiprux.spbeat.game.gui.ScreenManager;
import com.badlogic.gdx.graphics.g2d.Batch;

public class SpBeat extends SimpleGame implements IControllerListener {
	
	public static final String TITLE = "SpBeat";
	
	private GameController controller;
	
	private MapManager mapManager;
	
	private ButtonPanel panel;
	private ScreenManager screenManager;
	private PlayManager playManager;

	private ResourceManager resourceManager;
	
	public SpBeat() {
		this(TITLE);
	}
	
	public SpBeat(String title) {
		super(title);
		
		this.panel = new ButtonPanel();
		this.controller = new SpbeatController();
		this.screenManager = new ScreenManager(this);
		this.mapManager = new MapManager(this);
		this.playManager = new PlayManager(this);
		this.resourceManager = new ResourceManager();

		getFontManager().setDefault("Arial");
	}
	
	@Override
	public void create() {
		super.create();
		
		//입력 장치 초기화
		try {
			getController().listen();
		} catch (Exception e) {
			getLogManager().log(Level.WARNING, "장치 초기화 실패, fallback 컨트롤러를 사용합니다", e);
			
			this.controller = new FallbackController();
			
			try {
				getController().listen();
			} catch (Exception e1) {
				getLogManager().log(Level.WARNING, "fallback 장치 초기화 실패 입력을 받을 수 없습니다", e);
			}
		}
		
		getController().addListener(this);
		getController().addListener(getScreenManager());
		
		addChild(getPanel());
		
		//로딩 화면 시작
		getScreenManager().setCurrentScreen(new LoadingScreen());
	}
	
	public ButtonPanel getPanel() {
		return panel;
	}
	
	public GameController getController() {
		return controller;
	}
	
	public ScreenManager getScreenManager() {
		return screenManager;
	}

	public MapManager getMapManager() {
		return mapManager;
	}

	public PlayManager getPlayManager() {
		return playManager;
	}

	public ResourceManager getResourceManager() {
		return resourceManager;
	}

	//업데이트 함수
	@Override
	public void update(int delta) {
		super.update(delta);

		MainThreadExecutor.update();
		getScreenManager().update(delta);
	}

	//그리기 함수
	@Override
	public void draw(Batch batch, float x, float y, float width, float height) {

	}

	@Override
	public void dispose () {
		controller.close();
	}

	@Override
	public void onPress(int keyIndex) {
		
	}

	@Override
	public void onRelease(int keyIndex) {
		// TODO Auto-generated method stub
		
	}
}