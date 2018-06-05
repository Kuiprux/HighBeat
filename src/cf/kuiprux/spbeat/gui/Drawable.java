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
	
	private TransformData transformData;
	
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
		return getX() - getDrawAnchorX() + (getParent() != null ? getParent().getDrawX() : 0);
	}
	
	
	public float getDrawY() {
		return getY() - getDrawAnchorY() + (getParent() != null ? getParent().getDrawY() : 0);
	}
	
	public float getDrawOriginX() {
		return getWidth() * getOrigin().getXOffset();
	}
	
	public float getDrawOriginY() {
		return getHeight() * getOrigin().getYOffset();
	}
	
	public float getDrawAnchorX() {
		return getDrawWidth() * getAnchor().getXOffset();
	}
	
	public float getDrawAnchorY() {
		return getDrawHeight() * getOrigin().getYOffset();
	}
	
	public float getOpacity() {
		return opacity;
	}
	
	public TransformData getTransformData() {
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
		if (isLoaded() && getParent() != null)
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

	//Transform ��� ���
	public TransformData computeTransform() {
		float originX = getDrawX() + getDrawOriginX();
		float originY = getDrawY() + getDrawOriginY();
		
		TransformData transformData = TransformData.applyTransform(originX, originY, 0f, 0f, getScaleX(), getScaleY(), (float) Math.toRadians(getRotation()));

		return this.transformData = transformData;
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
	
	//graphics�� TransformData ����
	protected void applyTransform(Graphics graphics) {
		TransformData transformData = getTransformData();
		
		transformData.applyTransform(graphics);
	}
	
	//�Ӽ� ����
	protected void applyProperties(Graphics graphics) {
		
	}
	
	public abstract void update(int delta);
	
	public abstract void draw(Graphics graphics);
}
