package libgdxpluginv01.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.eclipse.swt.graphics.Image;

import libgdxpluginv01.constant.StaticFile;
import libgdxpluginv01.constant.Utility;

public enum UIElementPaletteContentProvider {
	INSTANCE;

	public List getModel() {
		Scanner scanner = null;
		List list = new ArrayList<>();
		Group group = null;
		Element element = null;

		try {
			scanner = new Scanner(Utility.getFile(StaticFile.UI_ELEMENTS_LIST));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (!line.startsWith("\t")) {
					group = new Group(line);
					list.add(group);
				} else {
					line = line.substring(1);
					group.addChild(new Element(line, group));
				}
			}

			scanner.close();
		} catch (FileNotFoundException e) {
			System.out
					.println("File not found: " + StaticFile.UI_ELEMENTS_LIST);
		}

		return list;
	}
}
