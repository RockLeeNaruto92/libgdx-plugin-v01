package libgdxpluginv01.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import libgdxpluginv01.constant.StaticFile;

public enum UIElementPaletteContentProvider {
	INSTANCE;
	
	public List getModel(){
		Scanner scanner = null;
		List list = new ArrayList<>();
		List group = null;
		
		try {
			scanner = new Scanner(new File(StaticFile.UI_ELEMENTS_LIST));
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + StaticFile.UI_ELEMENTS_LIST);
		}
		
		while (scanner.hasNextLine()){
			String line = scanner.nextLine();
			if (!line.startsWith("\t")){
				group = new ArrayList<>();
				list.add(group);
			} else {
				line = line.substring(1);
			}
			
			group.add(line);
		}
		scanner.close();
		
		return list;
	}
}
