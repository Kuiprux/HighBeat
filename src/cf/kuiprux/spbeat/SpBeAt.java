package cf.kuiprux.spbeat;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import cf.kuiprux.spbeat.game.ButtonPanel;
import cf.kuiprux.spbeat.game.PlayManager;

public class SpBeAt extends SimpleGame {
	
	public static final String TITLE = "SpBeAt";
	
	private ButtonPanel panel;
	private PlayManager playManager;
	
	public SpBeAt() {
		this(TITLE);
	}
	
	public SpBeAt(String title) {
		super(title);
		
		this.panel = new ButtonPanel();
	}
	
	@Override
	public void init(GameContainer container) throws SlickException {
		super.init(container);
		
		addChild(getPanel());
	}
	
	public ButtonPanel getPanel() {
		return panel;
	}

	//������Ʈ �Լ�
	@Override
	protected void updateInternal(int delta) {
		
	}

	//�׸��� �Լ�
	@Override
	protected void drawInternal(Graphics graphics) {
		graphics.setAntiAlias(true);
	}
}
