package libgdxpluginv01.models;

public class Element {
	private String name;
	private Group parent;
	
	public Element(String name, Group parent){
		this.name = name;
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Group getParent() {
		return parent;
	}

	public void setParent(Group parent) {
		this.parent = parent;
	}
}
