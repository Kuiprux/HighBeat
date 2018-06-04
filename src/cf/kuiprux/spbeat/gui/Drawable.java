package cf.kuiprux.spbeat.gui;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Transform;

public abstract class Drawable {
	
	private float x;
	private float y;
	
	//��ġ ������
	private AlignMode anchor;
	
	//ȸ�� / Ȯ��� ������
	private AlignMode origin;
	
	private float rotation;
	private float scaleX;
	private float scaleY;
	
	//0 ~ 1
	private float opacity;
	
	private Container parent;
	
	private Transform transformData;
	
	//false �Ͻ� transformData ��ü ���ΰ�ħ
	private boolean transformValid;
	
	private boolean loaded;
	
	public Drawable() {
		this.loaded = false;
		
		this.x = 0;
		this.y = 0;
		
		this.opacity = 1;
		
		this.anchor = AlignMode.DEFAULT;
		this.origin = AlignMode.DEFAULT;
		
		this.rotation = 0;
		this.scaleX = 1;
		this.scaleY = 1;
		
		this.transformValid = false;
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
	
	//�׸���� ��ġ (transform �Ӽ� �� ����)
	public float getDrawX() {
		return getX() - getAnchor().getXOffset() * getDrawWidth() + (getParent() != null ? getParent().getDrawX() : 0);
	}
	
	
	public float getDrawY() {
		return getY() -getAnchor().getYOffset() * getDrawHeight() + (getParent() != null ? getParent().getDrawY() : 0);
	}
	
	public float getOriginX() {
		return getDrawX() + getWidth() * getOrigin().getXOffset();
	}
	
	public float getOriginY() {
		return getDrawY() + getHeight() * getOrigin().getYOffset();
	}
	
	public float getOpacity() {
		return opacity;
	}
	
	public Transform getTransformData() {
		if (transformData == null || !transformValid)
			return transformData = computeTransform();
		
		return transformData;
	}
	
	public AlignMode getAnchor() {
		return anchor;
	}
	
	public AlignMode getOrigin() {
		return origin;
	}
	
	public float getRotation() {
		return rotation;
	}
	
	public float getScaleX() {
		return scaleX;
	}
	
	public float getScaleY() {
		return scaleY;
	}
	
	public void setX(float x) {
		if (this.x == x)
			return;
		this.x = x;
		
		sendParentUpdate();
	}
	
	public void setY(float y) {
		if (this.y == y)
			return;
		this.y = y;
		
		sendParentUpdate();
	}
	
	public void setLocation(float x, float y) {
		if (this.x == x && this.y == y)
			return;
		
		this.x = x;
		this.y = y;
		
		sendParentUpdate();
	}
	
	public void setOpacity(float opacity) {
		this.opacity = opacity;
	}
	
	public void setOrigin(AlignMode mode) {
		if (mode == this.origin)
			return;
		
		if (mode == null)
			mode = AlignMode.DEFAULT;
		
		this.origin = mode;
	}
	
	public void setAnchor(AlignMode mode) {
		if (mode == this.anchor)
			return;
		
		if (mode == null)
			mode = AlignMode.DEFAULT;
		
		this.anchor = mode;
		sendParentUpdate();
	}
	
	public void setRotation(float angle) {
		if (angle == this.rotation)
			return;
		
		this.rotation = angle;
		sendParentUpdate();
		transformValid = false;
	}
	
	public void setScaleX(float scaleX) {
		if (scaleX == this.scaleX)
			return;
		
		this.scaleX = scaleX;
		sendParentUpdate();
		transformValid = false;
	}
	
	public void setScaleY(float scaleY) {
		if (scaleY == this.scaleY)
			return;
		
		this.scaleY = scaleY;
		transformValid = false;
	}
	
	public void setScale(float scaleX, float scaleY) {
		if (scaleX == this.scaleX && scaleY == this.scaleY)
			return;
		
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		sendParentUpdate();
		transformValid = false;
	}
	
	public abstract float getWidth();
	public abstract float getHeight();
	/*
	 * �̹Ƿ� width / height setter�� ���� ������ �ʿ䰡 ������
	 * �ʿ� �� ��� ���� ����
	 */
	
	
	//�׸���� ũ�� (transform �Ӽ� �� ����)
	public float getDrawWidth() {
		return getWidth();
	}
	
	
	public float getDrawHeight() {
		return getHeight();
	}
	
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
		Transform transform = Transform.createTranslateTransform(0, 0);
		
		float originX = getOriginX();
		float originY = getOriginY();
		
		if (getScaleX() != 1 || getScaleY() != 1)
			transform = transform.concatenate(Transform.createScaleTransform(getScaleX(), getScaleY()));
		if (getRotation() % 360 != 0)
			transform = transform.concatenate(Transform.createRotateTransform((float) Math.toRadians(getRotation()), originX, originY));
		
		return transformData = transform;
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
	
	//transform ����
	protected void applyTransform(Graphics graphics) {
		Transform transform = getTransformData();
		
		float originX = getOriginX();
		float originY = getOriginY();
		
		if (getRotation() % 360 != 0)
			graphics.rotate(originX, originY, getRotation());
		
		if (getScaleX() != 1 && getScaleY() != 1) {
			graphics.translate(originX, originY);
			graphics.scale(getScaleX(), getScaleY());
			graphics.translate(-originX, -originY);
		}
		
	}
	
	//�Ӽ� ����
	protected void applyProperties(Graphics graphics) {
		
	}
	
	public abstract void update(int delta);
	
	public abstract void draw(Graphics graphics);
}
