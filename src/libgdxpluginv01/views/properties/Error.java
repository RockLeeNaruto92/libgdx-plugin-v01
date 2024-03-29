package libgdxpluginv01.views.properties;

import org.eclipse.swt.graphics.Point;

public enum Error {
	VALID (""),
	LOCATION_X_IS_STRING ("Location x value must be integer number!"),
	LOCATION_X_OUT_RANGE ("Location x is out of range: "),
	LOCATION_Y_IS_STRING ("Location y value must be integer number!"),
	LOCATION_Y_OUT_RANGE ("Location y is out of range: "),
	FONT_SCALE_IS_EMPTY ("Font scale must be float number"),
	MAX_MIN_STEP_IS_NOT_FLOAT_NUM ("Max, min, step size, value must be float number");
	
	private String name;
	private Error (String str){
		name = str;
	}
	
	public String toString(Point range){
		return name + range;
	}
}

