package libgdxpluginv01.swt.custom;

import libgdxpluginv01.constant.Word;

public class Align {
	static public final int center = 1 << 0;
	static public final int top = 1 << 1;
	static public final int bottom = 1 << 2;
	static public final int left = 1 << 3;
	static public final int right = 1 << 4;

	static public final int topLeft = top | left;
	static public final int topRight = top | right;
	static public final int bottomLeft = bottom | left;
	static public final int bottomRight = bottom | right;
	
	public static String[] getStrings(){
		return new String[] {
			Word.ALIGN_LEFT,
			Word.ALIGN_RIGHT,
			Word.ALIGN_TOP,
			Word.ALIGN_BOTTOM,
			Word.ALIGN_TOP_LEFT,
			Word.ALIGN_TOP_RIGHT,
			Word.ALIGN_BOTTOM_LEFT,
			Word.ALIGN_BOTTOM_RIGHT
		};
	}
}
