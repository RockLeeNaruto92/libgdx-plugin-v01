package libgdxpluginv01.models;

import java.util.ArrayList;
import java.util.List;

public class Group {
	private String name;
	private List childrens;
	
	public Group(String name){
		this.name = name;
		this.childrens = new ArrayList<>(); 
	}
	
	public void addChild(Object child){
		childrens.add(child);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List getChildrens() {
		return childrens;
	}

	public void setChildrens(List childrens) {
		this.childrens = childrens;
	}
}
