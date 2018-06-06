package cf.kuiprux.spbeat.game.gui;

import cf.kuiprux.spbeat.SpBeAt;
import cf.kuiprux.spbeat.game.controller.IControllerListener;

public abstract class ScreenPreset implements IControllerListener {
	
	private ScreenManager screenManager;
	
	private boolean loaded;
	
	public ScreenPreset() {
		this.loaded = false;
	}
	
	public boolean isLoaded() {
		return loaded;
	}
	
	//isLoaded�� false �Ͻ� null
	public ScreenManager getScreenManager() {
		return screenManager;
	}
	
	public SpBeAt getGame() {
		return getScreenManager().getGame();
	}
	
	public ButtonPanel getButtonPanel() {
		return getGame().getPanel();
	}
	
	public void load(ScreenManager screenManager) {
		if (isLoaded())
			return;
		this.loaded = true;
		
		this.screenManager = screenManager;
		this.onLoad();
	}
	
	public void unload() {
		if (!isLoaded())
			return;
		this.loaded = false;
		
		this.screenManager = null;
		this.onUnload();
	}
	
	protected abstract void onLoad();
	protected abstract void onUnload();
}