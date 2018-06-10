package cf.kuiprux.spbeat.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import cf.kuiprux.spbeat.gui.effect.DrawEffect;
import cf.kuiprux.spbeat.gui.effect.IAnimatable;
import cf.kuiprux.spbeat.gui.effect.IDrawEffect;
import cf.kuiprux.spbeat.gui.effect.IEffectResult;

public abstract class Drawable implements IAnimatable {

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
	private boolean visible;
	
	private Container parent;
	
	private TransformData transformData;
	
	//false �Ͻ� transformData ��ü ���ΰ�ħ
	private boolean transformValid;
	
	private boolean loaded;
	
	private Map<IDrawEffect, IEffectResult> currentEffectMap;

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
		this.visible = true;
		
		this.currentEffectMap = new HashMap<>();
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
		return getDrawHeight() * getAnchor().getYOffset();
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
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean flag) {
		this.visible = flag;
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
		
		long now = System.currentTimeMillis();
		
		for (IDrawEffect drawEffect : new ArrayList<>(currentEffectMap.keySet())) {
			if (drawEffect.isEnded(now)) {
				removeEffect(drawEffect);
			}
			else {
				drawEffect.applyAt(now, graphics);
			}
		}
	}
	
	//�Ӽ� ����
	protected void applyProperties(Graphics graphics) {
		
	}
	
	public abstract void update(int delta);
	
	public abstract void draw(Graphics graphics);
	
	//effect ���� ����
	
	protected void addEffect(IDrawEffect effect, IEffectResult result) {
		if (currentEffectMap.containsKey(effect))
			return;
		
		currentEffectMap.put(effect, result);
	}
	
	protected void removeEffect(IDrawEffect effect) {
		if (!currentEffectMap.containsKey(effect))
			return;
		
		IEffectResult result = currentEffectMap.remove(effect);
		
		//�� ������ effect �� ������ result ü�� ����
		if (!currentEffectMap.containsValue(result)) {
			result.start();
		}
	}

	public DrawableEffectResult fadeTo(float opacity, EasingType type, int duration) {
		DrawableEffectResult temp = new DrawableEffectResult();
		DrawableEffectResult result = temp.fadeTo(opacity, type, duration);
		
		temp.start();
		
		return result;
	}
	
	public DrawableEffectResult fadeIn(EasingType type, int duration) {
		return fadeTo(1, type, duration);
	}

	public DrawableEffectResult fadeOut(EasingType type, int duration) {
		return fadeTo(0, type, duration);
	}

	public DrawableEffectResult moveToRelative(float x, float y, EasingType type, int duration) {
		DrawableEffectResult temp = new DrawableEffectResult();
		DrawableEffectResult result = temp.moveToRelative(x, y, type, duration);
		
		temp.start();
		
		return result;
	}
	
	public DrawableEffectResult moveToRelativeX(float x, EasingType type, int duration) {
		return moveToRelative(x, 0, type, duration);
	}

	public DrawableEffectResult moveToRelativeY(float y, EasingType type, int duration) {
		return moveToRelative(0, y, type, duration);
	}

	public DrawableEffectResult moveTo(float x, float y, EasingType type, int duration) {
		return moveToRelative(x - getX(), y - getY(), type, duration);
	}

	public DrawableEffectResult moveToX(float x, EasingType type, int duration) {
		return moveTo(x, getY(), type, duration);
	}

	public DrawableEffectResult moveToY(float y, EasingType type, int duration) {
		return moveTo(getX(), y, type, duration);
	}

	public DrawableEffectResult rotateTo(float rotation, EasingType type, int duration) {
		DrawableEffectResult temp = new DrawableEffectResult();
		DrawableEffectResult result = temp.rotateTo(rotation, type, duration);
		
		temp.start();
		
		return result;
	}
	
	public DrawableEffectResult wait(int time) {
		DrawableEffectResult temp = new DrawableEffectResult();
		DrawableEffectResult result = temp.wait(time);
		
		temp.start();
		
		return result;
	}

	public class DrawableEffectResult implements IEffectResult {
		
		private Map<IDrawEffect, IEffectResult> effectQueueMap;
		private long lastEndTime;
		
		public DrawableEffectResult() {
			this.effectQueueMap = new HashMap<>();
			this.lastEndTime = 0;
		}

		public void expire() {
			addEffectQueue(new DrawEffect(System.currentTimeMillis(), 0) {
				@Override
				public void applyAt(long currentTime, Graphics graphics) {
					Drawable.this.expire();
				}

				@Override
				public void onStart() {
					Drawable.this.expire();
				}
			}, new DrawableEffectResult());
		}
		
		public DrawableEffectResult wait(int time) {
			DrawableEffectResult result = new DrawableEffectResult();

			addEffectQueue(new DrawEffect(time) {
				@Override
				public void applyAt(long currentTime, Graphics graphics) {
					
				}

				@Override
				public void onStart() {
					
				}
				
			}, result);
			
			return result;
		}

		public DrawableEffectResult fadeTo(float opacity, EasingType type, int duration) {
			DrawableEffectResult result = new DrawableEffectResult();
			
			addEffectQueue(new DrawEffect(duration) {
				@Override
				public void applyAt(long currentTime, Graphics graphics) {
					float progress = type.convertProgress((float) (currentTime - getStartTime()) / duration);
					
					setOpacity(getStartValue() + (opacity - getStartValue()) * progress);
				}
				
				@Override
				public void onEnded() {
					setOpacity(opacity);
				}

				@Override
				public void onStart() {
					setStartValue(getOpacity());
				}
				
			}, result);
			
			return result;
		}
		
		public DrawableEffectResult fadeIn(EasingType type, int duration) {
			return fadeTo(1, type, duration);
		}

		public DrawableEffectResult fadeOut(EasingType type, int duration) {
			return fadeTo(0, type, duration);
		}

		public DrawableEffectResult moveToRelative(float x, float y, EasingType type, int duration) {
			DrawableEffectResult result = new DrawableEffectResult();
			
			addEffectQueue(new DrawEffect(duration) {
				@Override
				public void applyAt(long currentTime, Graphics graphics) {
					float progress = type.convertProgress((float) (currentTime - getStartTime()) / duration);
					
					graphics.translate(-x * (1 - progress), -y * (1 - progress));
				}

				@Override
				public void onStart() {
					setLocation(getX() + x, getY() + y);
				}
			}, result);
			
			return result;
		}
		
		public DrawableEffectResult moveToRelativeX(float x, EasingType type, int duration) {
			return moveToRelative(x, 0, type, duration);
		}

		public DrawableEffectResult moveToRelativeY(float y, EasingType type, int duration) {
			return moveToRelative(0, y, type, duration);
		}
		
		public DrawableEffectResult moveTo(float x, float y, EasingType type, int duration) {
			return moveToRelative(x - getX(), y - getY(), type, duration);
		}

		public DrawableEffectResult moveToX(float x, EasingType type, int duration) {
			return moveTo(x, getY(), type, duration);
		}

		public DrawableEffectResult moveToY(float y, EasingType type, int duration) {
			return moveTo(getX(), y, type, duration);
		}
		
		public DrawableEffectResult rotateTo(float rotation, EasingType type, int duration) {
			DrawableEffectResult result = new DrawableEffectResult();
			
			addEffectQueue(new DrawEffect(duration) {
				@Override
				public void applyAt(long currentTime, Graphics graphics) {
					float progress = type.convertProgress((float) (currentTime - getStartTime()) / duration);

					graphics.rotate(getDrawX() + getDrawOriginX(), getDrawY() + getDrawOriginY(), (getRotation() - getStartValue()) * progress - getRotation());
				}

				@Override
				public void onStart() {
					setStartValue(getRotation());
					setRotation(rotation);
				}
			}, result);
			
			return result;
		}
		
		private void addEffectQueue(IDrawEffect effect, IEffectResult result) {
			if (effectQueueMap.containsKey(effect))
				return;
			
			effectQueueMap.put(effect, result);
			
			lastEndTime = Math.max(effect.getEndTime(), lastEndTime);
		}
		
		@Override
		public void start() {
			long now = System.currentTimeMillis();
			
			for (IDrawEffect effect : effectQueueMap.keySet()) {
				effect.setStartTime(now);
				
				effect.onStart();
				addEffect(effect, effectQueueMap.get(effect));
			}
		}
	}
}
