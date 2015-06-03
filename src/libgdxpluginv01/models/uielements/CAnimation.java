package libgdxpluginv01.models.uielements;

import java.util.ArrayList;
import java.util.List;

import libgdxpluginv01.constant.Parameter;
import libgdxpluginv01.constant.Resources;
import libgdxpluginv01.controller.UIController;
import libgdxpluginv01.swt.custom.PlayMode;
import libgdxpluginv01.views.properties.UIElementPropertyType;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class CAnimation extends UIElement {
	private List<Image> keyFrames;
	private int count;
	private Point origin = new Point(0, 0);
	private float frameDuration;
	private int playMode = PlayMode.NORMAL;
	private int lastFrameNumber;
	private float lastStateTime;
	private float stateTime = 0;

	private Runnable runable;

	public CAnimation(Composite root, Point location,
			UIController uiController, int type) {
		super(root, location, uiController, type);
		Display.getCurrent().timerExec((int)(getFrameDuration() * 1000), getRunable());
		
		keyFrames = new ArrayList<Image>();
		
		setDefaultKeyFrames();
	}
	
	public CAnimation(Composite root, Point location,
			UIController uiController) {
		super(root, location, uiController, UIElementType.ANIMATION);
		Display.getCurrent().timerExec((int)(getFrameDuration() * 1000), getRunable());
		
		keyFrames = new ArrayList<Image>();
		
		setDefaultKeyFrames();
	}
	
	private void setDefaultKeyFrames(){
		String framesPath = Resources.getAndroidProjectPath(Resources.getCurrentProject()) + "/assets/imgs/bow";
		for (int i = 1; i < 5; i ++){
			String path = framesPath + i + ".png";
			Resources.addImage(path);
			keyFrames.add(Resources.getImageByPath(path));
		}
	}

	@Override
	public String getDefaultNamePattern() {
		return Parameter.DEFAULT_ANIMATION_NAME_PATTERN;
	}

	@Override
	public Point getDefaultSize() {
		return Parameter.DEFAULT_ANIMATION_SIZE;
	}

	@Override
	public Point getMinSize() {
		return Parameter.ANIMATION_MIN_SIZE;
	}

	@Override
	public void drawContent(PaintEvent e) {
		// draw image
		if (keyFrames == null)
			return;
		if (keyFrames.size() == 0)
			return;
		
		Image image = keyFrames.get(getKeyFrameIndex(stateTime));
		Rectangle bound = image.getBounds();
		
		e.gc.drawImage(image, 0, 0, bound.width, bound.height, 0, 0, getSize().x, getSize().y);
	}

	@Override
	public void onMouseUp() {
	}

	@Override
	public void onMouseDown() {
	}

	@Override
	public void onMouseDoubleClick() {
	}

	@Override
	public void onMouseHover() {
	}

	@Override
	public void onMouseMove() {
	}
	
	@Override
	public void onMouseExit() {
	}

	@Override
	public void onMouseEnter() {
	}

	public int getKeyFrameIndex (float stateTime) {
		if (keyFrames.size() == 1) return 0;

		int frameNumber = (int)(stateTime / frameDuration);
		switch (playMode) {
		case PlayMode.NORMAL:
			frameNumber = Math.min(keyFrames.size() - 1, frameNumber);
			break;
		case PlayMode.LOOP:
			frameNumber = frameNumber % keyFrames.size();
			break;
		case PlayMode.LOOP_PINGPONG:
			frameNumber = frameNumber % ((keyFrames.size() * 2) - 2);
			if (frameNumber >= keyFrames.size()) frameNumber = keyFrames.size() - 2 - (frameNumber - keyFrames.size());
			break;
		case PlayMode.LOOP_RANDOM:
			int lastFrameNumber = (int) ((lastStateTime) / frameDuration);
			if (lastFrameNumber != frameNumber) {
				frameNumber = ((int)(Math.random()) / keyFrames.size());
			} else {
				frameNumber = this.lastFrameNumber;
			}
			break;
		case PlayMode.REVERSED:
			frameNumber = Math.max(keyFrames.size() - frameNumber - 1, 0);
			break;
		case PlayMode.LOOP_REVERSED:
			frameNumber = frameNumber % keyFrames.size();
			frameNumber = keyFrames.size() - frameNumber - 1;
			break;
		}

		lastFrameNumber = frameNumber;
		lastStateTime = stateTime;

		return frameNumber;
	}
	
	public Image getKeyFrame (float stateTime, boolean looping) {
		// we set the play mode by overriding the previous mode based on looping
		// parameter value
		int oldPlayMode = playMode;
		if (looping && (playMode == PlayMode.NORMAL || playMode == PlayMode.REVERSED)) {
			if (playMode == PlayMode.NORMAL)
				playMode = PlayMode.LOOP;
			else
				playMode = PlayMode.LOOP_REVERSED;
		} else if (!looping && !(playMode == PlayMode.NORMAL || playMode == PlayMode.REVERSED)) {
			if (playMode == PlayMode.LOOP_REVERSED)
				playMode = PlayMode.REVERSED;
			else
				playMode = PlayMode.LOOP;
		}

		Image frame = getKeyFrame(stateTime);
		playMode = oldPlayMode;
		return frame;
	}

	
	public Image getKeyFrame(float stateTime){
		int frameNumber = getKeyFrameIndex(stateTime);
		
		return keyFrames.get(frameNumber);
	}

	@Override
	public void remove() {
		super.remove();
		removeAllFrames();
		Display.getCurrent().timerExec(-1, runable);
		
	}

	public List<Image> getKeyFrames() {
		return keyFrames;
	}

	public void setKeyFrames(List<Image> keyFrames) {
		this.keyFrames = keyFrames;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Point getOrigin() {
		return origin;
	}

	public void setOrigin(Point origin) {
		this.origin = origin;
	}

	public float getFrameDuration() {
		if (frameDuration == 0){
			frameDuration = 0.5f;
		}
		return frameDuration;
	}

	public void setFrameDuration(float frameDuration) {
		this.frameDuration = frameDuration;
	}

	public int getPlayMode() {
		return playMode;
	}

	public void setPlayMode(int playMode) {
		this.playMode = playMode;
	}

	public float getStateTime() {
		if (stateTime >= 10f)
			stateTime = 0;
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

	public Runnable getRunable() {
		if (runable == null){
			runable = new Runnable() {
				@Override
				public void run() {
					stateTime += getFrameDuration();
					redraw();
					Display.getCurrent().timerExec((int)(getFrameDuration() * 1000), this);
				}
			};
		}
		return runable;
	}

	public void setRunable(Runnable runable) {
		this.runable = runable;
	}
	
	public void removeAllFrames(){
		while (keyFrames.size() != 0){
			Image image = keyFrames.remove(0);
			image.dispose();
		}
	}

	@Override
	public Object getPropertyValue(UIElementPropertyType type) {
		Object result = super.getPropertyValue(type);
		
		if (result != null) return result;
		
		switch (type) {
		case ROTATION:
			return getRotation();
		case PLAY_MODE:
			return getPlayMode();
		case COUNT: 
			return getCount();
		case FRAME_DURATION:
			return getFrameDuration();
		default:
			return null;
		}
	}
	
	@Override
	public void setPropertyValue(UIElementPropertyType type, Object value) {
		super.setPropertyValue(type, value);
		
		switch (type) {
		case ROTATION:
			setRotation(Float.parseFloat(value.toString()));			
			break;
		case PLAY_MODE:
			setPlayMode((int)value);
			break;
		case FRAME_DURATION:
			setFrameDuration(Float.parseFloat(value.toString()));
			break;
		default:
			break;
		}
	}

	@Override
	public Element generateXml(Document doc, Element parentNode) {
		Element el = super.generateXml(doc, parentNode);
		
//		genenerateAttrXml(doc, el, UIElementPropertyType., value);
		genenerateAttrXml(doc, el, UIElementPropertyType.PLAY_MODE, playMode);
		genenerateAttrXml(doc, el, UIElementPropertyType.FRAME_DURATION, frameDuration);
		
		generateKeyFrames(doc, el);
		
		return el;
	}
	
	private void generateKeyFrames(Document doc, Element owner){
		Element keyFramesEl = doc.createElement(UIElementPropertyType.KEY_FRAMES.toString());
		
		keyFramesEl.setAttribute(UIElementPropertyType.COUNT.toString(), count + "");
		
		for (Image frame : keyFrames) {
			Element frameEl = doc.createElement("frame");
			
			frameEl.setAttribute("path", Resources.getPathOfImage(frame));
			
			keyFramesEl.appendChild(frameEl);
		}
		
		owner.appendChild(keyFramesEl);
	}

	@Override
	public void restore(Element element) {
		super.restore(element);
		
		playMode = Integer.parseInt(readValue(element, UIElementPropertyType.PLAY_MODE));
		frameDuration = Float.parseFloat(readValue(element, UIElementPropertyType.FRAME_DURATION));
		
		// read keys frame
		Element keyFramesEl = (Element)(element.getElementsByTagName(UIElementPropertyType.KEY_FRAMES.toString()).item(0));
		NodeList frames = keyFramesEl.getElementsByTagName("frame");
		
		for (int i = 0; i < frames.getLength(); i++){
			Element frame = (Element)(frames.item(i));
			
			keyFrames.add(Resources.getImageByPath(frame.getAttribute("path")));
		}
		
		count = frames.getLength();
		System.out.println("Frames length: " + count);
	}

	@Override
	public Image getImageByIndex(int imgIndex) {
		return keyFrames.get(imgIndex);
	}
}
