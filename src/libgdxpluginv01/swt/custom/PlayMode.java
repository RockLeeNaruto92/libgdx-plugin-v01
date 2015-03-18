package libgdxpluginv01.swt.custom;

import libgdxpluginv01.constant.Word;

public class PlayMode {
	public static final int NORMAL = 0;
	public static final int REVERSED = 1;
	public static final int LOOP = 2;
	public static final int LOOP_REVERSED = 3;
	public static final int LOOP_PINGPONG = 4;
	public static final int LOOP_RANDOM = 5;
	
	public static String[] getStrings(){
		return new String[]{
				Word.PLAY_MODE_NORMAL,
				Word.PLAY_MODE_REVERSE,
				Word.PLAY_MODE_LOOP,
				Word.PLAY_MODE_LOOP_REVERSE,
				Word.PLAY_MODE_PING_PONG,
				Word.PLAY_MODE_RANDOM
		};
	}
}
