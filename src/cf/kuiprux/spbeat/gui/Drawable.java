package cf.kuiprux.spbeat.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;

public abstract class Drawable {
	
	private float x;
	private float y;
	
	//0 ~ 1
	private float opacity;
	
	private Container parent;
	
	private boolean loaded;
	
	public Drawable() {
		this.loaded = false;
		
		this.x = 0;
		this.y = 0;
		
		this.opacity = 1;
	}
	
	/*
	 * getter / setter ���� ����
	 */
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getOpacity() {
		return opacity;
	}
	
	public void setX(float x) {
		this.x = x;
		
		sendParentUpdate();
	}
	
	public void setY(float y) {
		this.y = y;
		
		sendParentUpdate();
	}
	
	public void setLocation(float x, float y) {
		this.x = x;
		this.y = y;
		
		sendParentUpdate();
	}
	
	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}
	
	public abstract float getWidth();
	public abstract float getHeight();
	/*
	 * �׸���� �̹Ƿ� width / height setter�� ���� ������ �ʿ䰡 ������
	 * �ʿ� �� ��� ���� ����
	 */
	
	public abstract Rectangle getBoundingBox();
	
	public void sendParentUpdate() {
		if (isLoaded())
			getParent().onChildUpdate(this);
	}
	
	public Container getParent() {
		return parent;
	}
	
	public boolean isLoaded() {
		return loaded;
	}
	
	//Parent Container���� ����
	public void expire() {
		if (getParent() == null)
			return;
		
		getParent().removeChild(this);
	}

	//TODO:: ����
	public Transform computeTransform() {
		return Transform.createTranslateTransform(0, 0);
	}
	
	/*
	 * getter ���� ��
	 */
	
	/*
	 * �̺�Ʈ ���� ����
	 */
	
	//container�� �ڽ����� �־������� ȣ��
	protected void onAdded(Container container) {
		//�ٸ� parent�� �ҼӵǾ� ������ ����
		if (parent != null)
			expire();
		
		parent = container;
	}
	
	//�ش� Drawable�� �ε�(update�� draw�� ȣ�� �� �� �ִ� ����) �ɶ�
	protected void onLoaded() {
		loaded = true;
	}
	
	//�ش� Drawable�� �� �ε�(update�� draw�� ȣ�� �� �� ���� ����) �ɶ�
	protected void onUnloaded() {
		loaded = false;
	}
	
	//container���� ���� �Ǿ��� �� ȣ��
	protected void onRemoved() {
		parent = null;
	}
	
	/*
	 * �̺�Ʈ ���� ��
	 */
	
	public abstract void update(int delta);
	
	public abstract void draw(Graphics graphics);
}
