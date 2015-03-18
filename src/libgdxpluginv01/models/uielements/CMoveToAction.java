package libgdxpluginv01.models.uielements;

import javax.rmi.CORBA.Tie;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

public class CMoveToAction extends CAction {
	private int x, y;
	private float timeDuration;
	private UIElement target;
	private Point velocity;
	private float stateTime = 0;
	private float timePerPixel;
	
	public CMoveToAction(int x, int y, float timeDuration) {
		this.x = x;
		this.y = y;
		this.timeDuration = timeDuration;
	}
	
	public CMoveToAction(int x, int y, float timeDuration, UIElement target) {
		this.x = x;
		this.y = y;
		this.timeDuration = timeDuration; 
		this.target = target;
		
		Rectangle bound = target.getBound();
		timePerPixel = timeDuration / (Math.abs(bound.x - this.x));
		
		velocity = new Point(0, 0);
		velocity.x = (int)(1f / timePerPixel);
		velocity.y = (int)(1f / timePerPixel);
		System.out.println(velocity);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public float getTimeDuration() {
		return timeDuration;
	}

	public void setTimeDuration(float timeDuration) {
		this.timeDuration = timeDuration;
	}

	public void setTarget(UIElement target) {
		this.target = target;
	}

	@Override
	public void animate(float time) {
		if (isCompleted()) return;
		
		Rectangle bound = target.getBound();

		stateTime += time;
		
		if (velocity.x != 0){
			bound.x += velocity.x * time;
		} else {
			if (stateTime > timePerPixel){
				stateTime = 0;
				bound.x += 1;
			}
		}
		
		bound.x = (bound.x > this.x) ? this.x : bound.x;
		
		if (velocity.y != 0){
			bound.y += velocity.y * time;
		}else {
			if (stateTime > timePerPixel){
				stateTime = 0;
				bound.y += 1;
			}
		}
		
		bound.y = (bound.y > this.y) ? this.y : bound.y;
		
		if (bound.x > this.x && bound.y > this.y){
			setCompleted(true);
		}
		
		System.out.println(bound);
		System.out.println(stateTime + "-" + timePerPixel);
		
		target.setBound(bound);
		target.refresh();
	}
}
