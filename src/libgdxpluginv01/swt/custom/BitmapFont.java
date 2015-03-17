package libgdxpluginv01.swt.custom;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;


public class BitmapFont {
	private Display display;
	private String fontFileName;
	private String path;
	private List<String> imgFileNames;
	private List<Image> images;
	private int size;
	private int bold, italic, unicode, stretchH, aa, smooth;
	private int padLeft, padRight, padTop, padBottom;
	private int spacingX, spacingY;
	int lineHeight, base, scaleH, scaleW, pages, packed;
	private String charset;
	
	private List<CCharacter> characters;
	
	public BitmapFont(Display display, String fontFilePath){
		this.display = display;
		this.fontFileName = getName(fontFilePath);
		this.imgFileNames = new ArrayList<String>();
		this.images = new ArrayList<Image>();
		this.characters = new ArrayList<CCharacter>();
		this.path = getPath(fontFilePath);
		
		readCharacters();
	}
	
	private String getName(String path){
		
		return path.substring(path.lastIndexOf('\\') + 1);
	}
	
	private String getPath(String path){
		return path.substring(0, path.lastIndexOf('\\') + 1);
	}
	
	public String getPath(){
		return this.path;
	}
	
	private void readCharacters(){
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("datas/default.fnt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		readIntegerInforTag(scanner.nextLine());
		readCommonTag(scanner.nextLine());
		readPageTag(scanner);
		readCharsTag(scanner);
		openImages();
	}
	
	private void openImages(){
		for (int i = 0; i < pages; i++){
			images.add(new Image(display, "D:/default.png"));
		}
	}

	private void readPageTag(Scanner scanner) {
		for (int i = 0; i < pages; i++){
			String line = scanner.nextLine();
			
			String str = line.substring(line.indexOf("id="), line.indexOf("file="));
			int id = readIntegerInfor(1, str, 3)[0];
			
			str = line.substring(line.indexOf("file="));
			String fileName = readStringInfor(str);
			
			imgFileNames.add(id, fileName);
		}
	}

	private void readCharsTag(Scanner scanner) {
		String line = scanner.nextLine();
		String str = line.substring(line.indexOf("count="), line.length());
		int count = readIntegerInfor(1, str, 6)[0];
		
		
		for (int i = 0; i < count; i++){
			line = scanner.nextLine();
			str = line.substring(line.indexOf("id="), line.indexOf("x="));
			int id = readIntegerInfor(1, str, 3)[0];
			
			str = line.substring(line.indexOf("x="), line.indexOf("y="));
			int x = readIntegerInfor(1, str, 2)[0];
			
			str = line.substring(line.indexOf("y="), line.indexOf("width="));
			int y = readIntegerInfor(1, str, 2)[0];
			
			str = line.substring(line.indexOf("width="), line.indexOf("height="));
			int width = readIntegerInfor(1, str, 6)[0];
			
			str = line.substring(line.indexOf("height="), line.indexOf("xoffset="));
			int height = readIntegerInfor(1, str, 7)[0];
			
			str = line.substring(line.indexOf("xoffset="), line.indexOf("yoffset="));
			int xoffset = readIntegerInfor(1, str, 8)[0];
			
			str = line.substring(line.indexOf("yoffset="), line.indexOf("xadvance="));
			int yoffset = readIntegerInfor(1, str, 8)[0];
			
			str = line.substring(line.indexOf("xadvance="), line.indexOf("page="));
			int xadvance = readIntegerInfor(1, str, 9)[0];
			
			str = line.substring(line.indexOf("page="), line.indexOf("chnl="));
			int page = readIntegerInfor(1, str, 5)[0];
			
			str = line.substring(line.indexOf("chnl="));
			int chnl = readIntegerInfor(1, str, 5)[0];
			
			characters.add(new CCharacter(id, x, y, width, height, xoffset, yoffset, xadvance, page, chnl));
		}
	}

	private void readCommonTag(String line) {
		String str = line.substring(line.indexOf("lineHeight="), line.indexOf("base="));
		lineHeight = readIntegerInfor(1, str, 11)[0];
		
		str = line.substring(line.indexOf("base="), line.indexOf("scaleW="));
		base = readIntegerInfor(1, str, 5)[0];
		
		str = line.substring(line.indexOf("scaleW="), line.indexOf("scaleH="));
		scaleW = readIntegerInfor(1, str, 7)[0];
		
		str = line.substring(line.indexOf("scaleH="), line.indexOf("pages="));
		scaleH = readIntegerInfor(1, str, 7)[0];
		
		str = line.substring(line.indexOf("pages="), line.indexOf("packed="));
		pages = readIntegerInfor(1, str, 6)[0];
		
		str = line.substring(line.indexOf("packed="));
		base = readIntegerInfor(1, str, 7)[0];
	}

	private void readIntegerInforTag(String line) {
		System.out.println(line.indexOf("bound="));
		String str = line.substring(line.indexOf("size="), line.indexOf("bold=") - 1);
		size = readIntegerInfor(1, str, 5)[0];
		
		str = line.substring(line.indexOf("bold="), line.indexOf("italic=") - 1);
		bold = readIntegerInfor(1, str, 5)[0];
		
		str = line.substring(line.indexOf("italic="), line.indexOf("charset=") - 1);
		italic = readIntegerInfor(1, str, 7)[0];
		
		str = line.substring(line.indexOf("charset="), line.indexOf("unicode=") - 1);
		charset = readStringInfor(str);
		
		str = line.substring(line.indexOf("unicode="), line.indexOf("stretchH=") - 1);
		unicode = readIntegerInfor(1, str, 8)[0];
		
		str = line.substring(line.indexOf("stretchH="), line.indexOf("smooth=") - 1);
		stretchH = readIntegerInfor(1, str, 9)[0];
		
		str = line.substring(line.indexOf("smooth="), line.indexOf("aa=") - 1);
		smooth = readIntegerInfor(1, str, 7)[0];
		
		str = line.substring(line.indexOf("aa="), line.indexOf("padding=") - 1);
		aa = readIntegerInfor(1, str, 3)[0];
		
		str = line.substring(line.indexOf("padding="), line.indexOf("spacing=") - 1);
		int[] pad = readIntegerInfor(4, str, 8);
		padTop = pad[0];
		padLeft = pad[1];
		padBottom = pad[2];
		padRight = pad[3];
		
		str = line.substring(line.indexOf("spacing="));
		int[] spacing = readIntegerInfor(2, str, 8);
		spacingX = spacing[0];
		spacingY = spacing[1];
	}
	
	private int[] readIntegerInfor(int count, String str, int fIndex){
		int infor[] = new int[count];
		int cIndex = -2, index = 0;
		
		while (cIndex != -1){
			cIndex = str.indexOf(',', fIndex);
			if (cIndex == -1){
				int spaceIndex = str.indexOf(' ');
				infor[index++] = Integer.parseInt(str.substring(fIndex, (spaceIndex == -1 ) ? str.length(): spaceIndex));
			}
			else
				infor[index++] = Integer.parseInt(str.substring(fIndex, cIndex));
			
			fIndex = cIndex + 1;
		}
		
		return infor;
	}
	
	private String readStringInfor(String str){
		int fIndex = str.indexOf('"') + 1;
		int eIndex = str.lastIndexOf('"');
		
		return str.substring(fIndex, eIndex);
	}
	
	public void dispose(){
		for (Image image : images) {
			image.dispose();
		}
	}
	
	private void draw(GC gc, CCharacter cchar, int x, int y, float scaleX, float scaleY){
		gc.drawImage(images.get(cchar.page), cchar.x, cchar.y, cchar.width, cchar.height, x + cchar.xOffset, y + cchar.yOffset, (int)(cchar.width * scaleX), (int)(cchar.height * scaleY));
	}
	
	public void drawString(GC gc, String str, int x, int y, float scaleX, float scaleY){
		int nextX = x;
		
		for (int i = 0; i < str.length(); i++){
			CCharacter cchar = findCharacter(str.charAt(i));
			draw(gc, cchar, nextX, y, scaleX, scaleY);
			nextX += cchar.xAdvance * scaleX;
		}
	}
	
	private CCharacter findCharacter(char character){
		for (CCharacter index : characters) {
			if (character == index.character){
				return index;
			}
		}
		
		return null;
	}
	
	public Point getActualSize(String str, float scaleX, float scaleY){
		int maxHeight = 0;
		int sumWidth = 0;
		
		for (int i = 0; i < str.length(); i++){
			CCharacter cchar = findCharacter(str.charAt(i));
			
			sumWidth += (int)(cchar.xAdvance * scaleX);
			int height = (int)(cchar.height * scaleY);
			
			if (maxHeight < height)
				maxHeight = height;
		}
		
		return new Point(sumWidth, maxHeight);
	}
	
	class CCharacter {
		private char character;
		private int x, y, width, height;
		private int xOffset, yOffset, xAdvance;
		private int page, chnl;
		
		public CCharacter(int character, int x, int y, int width, int height,
				int xOffset, int yOffset, int xAdvance, int page, int chnl) {
			super();
			this.character = Character.toChars(character)[0];
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.xOffset = xOffset;
			this.yOffset = yOffset;
			this.xAdvance = xAdvance;
			this.page = page;
			this.chnl = chnl;
		}
	}
}
