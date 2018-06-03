package cf.kuiprux.spbeat.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

import org.newdawn.slick.Color;
import org.newdawn.slick.Game;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import javafx.scene.shape.Polygon;

public abstract class Container extends Drawable {
	
	private Game game;
	
	private List<Drawable> children;
	
	private boolean masking;
	
	public Container() {
		this.children = new ArrayList<>();
		this.masking = false;
	}
	
	protected void init(Game game) {
		this.game = game;
	}
	
	public Game getGame() {
		return game;
	}
	
	public boolean isMaskingMode() {
		return masking;
	}

	public void setMaskingMode(boolean masking) {
		this.masking = masking;
	}
	
	public List<Drawable> getChildren(){
		return new ArrayList<>(children);
	}
	
	protected List<Drawable> getChildrenInternal(){
		return new ArrayList<>(children);
	}
	
	@Override
	public void update(int delta) {
		for (Drawable child : children)
			child.update(delta);
		
		updateInternal(delta);
	}

	@Override
	public void draw(Graphics graphics) {
		drawInternal(graphics);
		
		graphics.pushTransform();
		
		//transform ����
		
		Shape box = new Rectangle(getX(), getY(), getWidth(), getHeight()).transform(computeTransform());
		
		//transform ó�� �Ǿ����Ƿ� �ּ� ��ǥ�� ��ġ ������ ���
		graphics.translate(box.getMinX(), box.getMinY());
		graphics.scale(getWidth() / box.getWidth(), getHeight() / box.getHeight());
		
		if (isMaskingMode()) {
			graphics.setWorldClip(0, 0, getWidth(), getHeight());
		}
		
		for (Drawable child : children)
			child.draw(graphics);
		
		graphics.popTransform();
	}
	
	
	/*
	 * �̺�Ʈ ���� ����
	 */
	
	@Override
	protected void onAdded(Container container) {
		super.onAdded(container);
	}
	
	@Override
	protected void onLoaded() {
		super.onLoaded();
		
		for (Drawable child : children)
			child.onLoaded();
	}
	
	@Override
	protected void onUnloaded() {
		super.onUnloaded();
		
		for (Drawable child : children)
			child.onUnloaded();
	}
	
	protected void onChildUpdate(Drawable child) {
		
	}
	
	/*
	 * �̺�Ʈ ���� ��
	 */
	
	//�ڽ� �߰��� �Ǿ����� true ��ȯ
	public boolean addChild(Drawable drawable) {
		if (containsChild(drawable))
			return false;
		
		addInternal(drawable);
		return true;
	}
	
	//�ڽ� ���� �Ǿ����� true ��ȯ
	public boolean removeChild(Drawable drawable) {
		if (!containsChild(drawable))
			return false;
		
		removeInternal(drawable);
		return true;
	}
	
	protected void addInternal(Drawable drawable) {
		children.add(drawable);
		drawable.onAdded(this);
		
		if (drawable instanceof Container) {
			((Container) drawable).init(getGame());
		}
		
		if (isLoaded())
			drawable.onLoaded();
	}
	
	protected void removeInternal(Drawable drawable) {
		children.remove(drawable);
		drawable.onRemoved();
		
		if (isLoaded())
			drawable.onUnloaded();
	}
	
	public boolean containsChild(Drawable drawable) {
		return children.contains(drawable);
	}
	
	//�ش� container update �޼���
	protected abstract void updateInternal(int delta);
	
	//�ش� container draw �޼���
	protected abstract void drawInternal(Graphics graphics);
}
