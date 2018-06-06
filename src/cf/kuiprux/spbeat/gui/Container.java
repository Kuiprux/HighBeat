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
	
	private boolean masking;
	
	private List<Drawable> children;
	
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
	
	public boolean isMasking() {
		return masking;
	}
	
	public void setMasking(boolean flag) {
		this.masking = flag;
	}
	
	//���纻
	//concurrent �߻� ������ �۾��� ���
	public List<Drawable> getChildren(){
		return new ArrayList<>(children);
	}
	
	//����
	protected List<Drawable> getChildrenInternal(){
		return children;
	}
	
	@Override
	public void update(int delta) {
		for (Drawable child : getChildren())
			child.update(delta);
		
		updateInternal(delta);
	}
	
	private void drawChild(Graphics graphics, Drawable child) {
		//�Ⱥ��ϰ�� ������ x
		if (!child.isVisible())
			return;
		
		graphics.pushTransform();
		
		child.draw(graphics);
		
		graphics.popTransform();
	}

	@Override
	public void draw(Graphics graphics) {
		graphics.pushTransform();
		
		applyProperties(graphics);
		applyTransform(graphics);
		drawInternal(graphics);
		
		graphics.popTransform();
		
		graphics.pushTransform();
		
		applyTransform(graphics);
		
		if (isMasking())
			graphics.setWorldClip(getDrawX(), getDrawY(), getDrawWidth(), getDrawHeight());
		
		for (Drawable child : getChildren())
			drawChild(graphics, child);
		
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
		
		for (Drawable child : getChildren())
			child.onLoaded();
	}
	
	@Override
	protected void onUnloaded() {
		super.onUnloaded();
		
		for (Drawable child : getChildren())
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
		getChildrenInternal().add(drawable);
		drawable.onAdded(this);
		
		if (drawable instanceof Container) {
			((Container) drawable).init(getGame());
		}
		
		if (isLoaded())
			drawable.onLoaded();
	}
	
	protected void removeInternal(Drawable drawable) {
		getChildrenInternal().remove(drawable);
		drawable.onRemoved();
		
		if (isLoaded())
			drawable.onUnloaded();
	}
	
	public boolean containsChild(Drawable drawable) {
		return getChildrenInternal().contains(drawable);
	}
	
	//�ش� container update �޼���
	protected abstract void updateInternal(int delta);
	
	//�ش� container draw �޼���
	protected abstract void drawInternal(Graphics graphics);
}
