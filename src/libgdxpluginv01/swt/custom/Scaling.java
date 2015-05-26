package libgdxpluginv01.swt.custom;

import libgdxpluginv01.constant.Word;

public class Scaling {
	public static final int fit = 0;
	public static final int fill = 1;
	public static final int fillX = 2;
	public static final int fillY = 3;
	public static final int stretch = 4;
	public static final int stretchX = 5;
	public static final int stretchY = 6;
	public static final int none = 7;
	
	public static String[] getStrings(){
		return new String[]{
			Word.PROPERTY_FIT,
			Word.PROPERTY_FILL,
			Word.PROPERTY_FILL_X,
			Word.PROPERTY_FILL_Y,
			Word.PROPERTY_STRETCH,
			Word.PROPERTY_STRETCH_X,
			Word.PROPERTY_STRETCH_Y,
			Word.PROPERTY_NONE
		};
	}
	
	public static int getScalingIndex(int scaling){
		return scaling;
	}
}
