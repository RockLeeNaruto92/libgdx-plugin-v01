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
	
	public static int getAlignIndex(int align){
		switch (align) {
		case left:
			return 0;
		case right:
			return 1;
		case top:
			return 2;
		case bottom:
			return 3;
		case topLeft:
			return 4;
		case topRight:
			return 5;
		case bottomLeft:
			return 6;
		case bottomRight:
			return 7;
		default:
			return -1;
		}
	}
	
	public static int getAlign(int index){
		switch (index) {
		case 0:
			return left;
		case 1:
			return right;
		case 2:
			return top;
		case 3:
			return bottom;
		case 4:
			return topLeft;
		case 5:
			return topRight;
		case 6:
			return bottomLeft;
		case 7:
			return bottomRight;
		default:
			break;
		}
		return -1;
	}
}
