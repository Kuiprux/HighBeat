package cf.kuiprux.spbeat.game.gui;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import cf.kuiprux.spbeat.gui.AlignMode;
import cf.kuiprux.spbeat.gui.Container;
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
	private List<ButtonArea> buttonList;
	
	public ButtonPanel() {
		this.buttonList = new ArrayList<>();
		initPanel();
	}
	
	private void initPanel() {
		addInternal(background = new Square(0, 0, getButtonPosX(getColumnCount() - 1) + getButtonWidth(), getButtonPosY(getRowCount() - 1) + getButtonHeight()));
		
		setLocation(85.5f, 285.5f);
		setMasking(true);
		
		background.setColor(Color.green);
		
		for (int y = 0; y < getRowCount(); y++) {
			for (int x = 0; x < getColumnCount(); x++) {
				//float ������
				ButtonArea button = new ButtonArea(getButtonPosX(x), getButtonPosY(y));

				addInternal(button);
				buttonList.add(button);
			}
		}
	}
	
	public Square getBackground() {
		return background;
	}
	
	//�⺻ �ڽ� ���� ���� ó��
	@Override
	public boolean removeChild(Drawable child) {
		if (child == background || buttonList.contains(child))
			return false;
		
		return removeChild(child);
	}
	
	//�ش� x y ��° �� �ִ� Button������Ʈ ��ȯ
	public ButtonArea getButtonAreaAt(int x, int y) {
		return getButtonAreaAt(y * getColumnCount() + x);
	}

	public ButtonArea getButtonAreaAt(int index) {
		return buttonList.get(index);
	}
	
	public List<ButtonArea> getButtonAreaList() {
		return new ArrayList<ButtonArea>(buttonList);
	}

	@Override
	public void draw(Graphics graphics) {
		super.draw(graphics);
		
		graphics.pushTransform();
		
		applyTransform(graphics);
		applyProperties(graphics);
		
		// !! TRICK !! :(
		graphics.setColor(Color.black);
		
		float drawX = getDrawX();
		float drawY = getDrawY();
		
		//���� ���м�
		for (int x = 1; x < getRowCount(); x++) {
			graphics.fillRect(drawX + getButtonPosX(x) - getButtonGapColumn(), drawY, getButtonGapColumn(), getDrawHeight());
		}
		
		//���� ���м�
		for (int y = 1; y < getColumnCount(); y++) {
			graphics.fillRect(drawX, drawY + getButtonPosY(y) - getButtonGapRow(), getDrawWidth(), getButtonGapRow());
		}
		
		graphics.popTransform();
	}
	
	@Override
	protected void updateInternal(int delta) {
		
	}
	
	//���μ� x ��° ��ư x ��ǥ
	public float getButtonPosX(int x) {
		return (getButtonWidth() + getButtonGapColumn()) * x;
	}

	//���μ� x ��° ��ư y ��ǥ
	public float getButtonPosY(int y) {
		return (getButtonHeight() + getButtonGapRow()) * y;
	}
	
	public float getButtonWidth() {
		return BUTTON_WIDTH;
	}

	public float getButtonHeight() {
		return BUTTON_HEIGHT;
	}
	
	public float getButtonGapColumn() {
		return BUTTON_GAP_X;
	}
	
	public float getButtonGapRow() {
		return BUTTON_GAP_Y;
	}
	
	public int getRowCount() {
		return ROW;
	}
	
	public int getColumnCount() {
		return COLUMN;
	}
	
	public class ButtonArea extends Container {
		
		public ButtonArea(float x, float y) {
			super();
			
			setX(x);
			setY(y);
			
			setOrigin(AlignMode.CENTRE);
		}
		
		@Override
		public float getWidth() {
			return getButtonWidth();
		}

		@Override
		public float getHeight() {
			return getButtonHeight();
		}
		
		//���� �� ����
		@Override
		public float getDrawWidth() {
			return super.getDrawWidth()  +1;
		}

		@Override
		public float getDrawHeight() {
			return super.getDrawHeight() + 1;
		}

		@Override
		protected void updateInternal(int delta) {
			
		}

		@Override
		protected void drawInternal(Graphics graphics) {
			
		}

		@Override
		public Rectangle getBoundingBox() {
			return new Rectangle(getDrawX(), getDrawY(), getDrawWidth(), getDrawHeight());
		}
	}
}
