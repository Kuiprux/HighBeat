package cf.kuiprux.spbeat.game.gui;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import cf.kuiprux.spbeat.gui.AlignMode;
import cf.kuiprux.spbeat.gui.Drawable;
import cf.kuiprux.spbeat.gui.containers.SimpleContainer;
import cf.kuiprux.spbeat.gui.element.Square;

// 4x4 ũ���� �簢�� ��ư ��
public class ButtonPanel extends SimpleContainer {
	
	public static final int BUTTON_WIDTH = 100;
	public static final int BUTTON_HEIGHT = 100;
	
	public static final int BUTTON_GAP_X = 10;
	public static final int BUTTON_GAP_Y = 10;
	
	public static final int ROW = 4;
	public static final int COLUMN = 4;
	
	private Square background;
	
	public ButtonPanel() {
		initPanel();
	}
	
	private void initPanel() {
		addInternal(background = new Square(0, 0, getButtonPosX(COLUMN - 1) + BUTTON_WIDTH, getButtonPosY(ROW - 1) + BUTTON_HEIGHT));
		
		setLocation(85.5f, 285.5f);
		setMasking(true);
		
		background.setColor(Color.green);
	}
	
	public Square getBackground() {
		return background;
	}

	@Override
	public void draw(Graphics graphics) {
		super.draw(graphics);
		
		graphics.pushTransform();
		
		applyTransform(graphics);
		applyProperties(graphics);
		
		// !! TRICK !! I don't like it
		graphics.setColor(Color.black);
		
		float drawX = getDrawX();
		float drawY = getDrawY();
		
		//���� ���м�
		for (int x = 1; x < ROW; x++) {
			graphics.fillRect(drawX + getButtonPosX(x) - BUTTON_GAP_X, drawY, BUTTON_GAP_X, getDrawHeight());
		}
		
		//���� ���м�
		for (int y = 1; y < COLUMN; y++) {
			graphics.fillRect(drawX, drawY + getButtonPosY(y) - BUTTON_GAP_Y, getDrawWidth(), BUTTON_GAP_Y);
		}
		
		graphics.popTransform();
	}
	
	@Override
	protected void updateInternal(int delta) {
		
	}
	
	//���μ� x ��° ��ư x ��ǥ
	public float getButtonPosX(int x) {
		return (BUTTON_WIDTH + BUTTON_GAP_X) * x;
	}

	//���μ� x ��° ��ư y ��ǥ
	public float getButtonPosY(int y) {
		return (BUTTON_HEIGHT + BUTTON_GAP_Y) * y;
	}
}
