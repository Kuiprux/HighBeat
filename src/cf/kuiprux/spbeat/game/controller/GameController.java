package cf.kuiprux.spbeat.game.controller;

import java.util.ArrayList;
import java.util.List;

public abstract class GameController implements IGameController {

	private Thread keyListenThread;
	
	private List<IControllerListener> listenerList;
	private List<Integer> pressedKeyList;
	
	private boolean listening;
	
	//index ���� 0 ~ 15
	public GameController() {
		this.listenerList = new ArrayList<>();
		this.pressedKeyList = new ArrayList<>();
		
		this.listening = false;
	}
	
	@Override
	public void listen() throws Exception {
		if (isListening())
			throw new Exception("already listening");
		this.listening = true;
		
		this.keyListenThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(isListening()){
					updateLoop();
				}
			}
		});
		
		updateInit();
		this.keyListenThread.start();
	}
	
	@Override
	public void close() {
		this.listening = false;
		keyListenThread.interrupt();
	}
	
	@Override
	public boolean isListening() {
		return listening;
	}

	@Override
	public boolean isPressed(int index) {
		return pressedKeyList.contains(index);
	}

	@Override
	public void addListener(IControllerListener listener) {
		if (containsListener(listener))
			return;
		
		listenerList.add(listener);
	}

	@Override
	public void removeListener(IControllerListener listener) {
		if (!containsListener(listener))
			return;
		
		listenerList.remove(listener);
	}

	@Override
	public boolean containsListener(IControllerListener listener) {
		return listenerList.contains(listener);
	}
	
	protected void setKeyPressed(int index) {
		if (pressedKeyList.contains(index))
			return;
		
		pressedKeyList.add(index);
	}
	
	protected void setKeyUnpressed(int index) {
		if (!pressedKeyList.contains(index))
			return;
		
		pressedKeyList.remove(index);
	}
	
	protected void callPressEvent(int index) {
		//concurrent ����
		for (IControllerListener listener : new ArrayList<>(listenerList)) {
			listener.onPress(index);
		}
	}

	protected void callUpEvent(int index) {
		//concurrent ����
		for (IControllerListener listener : new ArrayList<>(listenerList)) {
			listener.onUp(index);
		}
	}
	
	//update ���� �� ȣ���
	protected void updateInit() {
		
	}
	protected abstract void updateLoop();
}
