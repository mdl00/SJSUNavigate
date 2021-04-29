import java.util.Random;
import java.util.Scanner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class MapImage extends Canvas {
	
	static JButton[][] gridButtons = new JButton[128][128];
	
	public void paint(Graphics g) {  
		
		final String GREEN_PATH = "/Users/matthew/Downloads/greenblock.png";
		final String TGREEN_PATH = "/Users/matthew/Downloads/transparentgreenblock.png";
		final String RED_PATH = "/Users/matthew/Downloads/redblock.png";
		final String TRED_PATH = "/Users/matthew/Downloads/transparentredblock.png";
		final String TBLUE_PATH = "/Users/matthew/Downloads/transparentblueblock.png";
		final String T_PATH = "/Users/matthew/Downloads/transparentblock.png";
		
        setBackground(Color.WHITE);   
		
		Toolkit t=Toolkit.getDefaultToolkit();  
        Image img=t.getImage("/Users/matthew/Downloads/111919_NorthCampus_WEB_07.jpg");
        Image logo=t.getImage("/Users/matthew/Downloads/Applogo131.png");
        g.drawImage(img,0,0,this);
        
        g.fillRect(0,0,500,110); 
        setForeground(Color.WHITE); 
        g.drawImage(logo,0,0,this);
        
        try {
        	BufferedImage bimg = ImageIO.read(new File(GREEN_PATH));
    		final ImageIcon greenicon = new ImageIcon(bimg);
    		bimg = ImageIO.read(new File(TGREEN_PATH));
    		final ImageIcon tgreenicon = new ImageIcon(bimg);
    		bimg = ImageIO.read(new File(RED_PATH));
    		final ImageIcon redicon = new ImageIcon(bimg);
    		bimg = ImageIO.read(new File(TRED_PATH));
    		final ImageIcon tredicon = new ImageIcon(bimg);
    		bimg = ImageIO.read(new File(T_PATH));
    		final ImageIcon ticon = new ImageIcon(bimg);
    		bimg = ImageIO.read(new File(TBLUE_PATH));
    		final ImageIcon tblueicon = new ImageIcon(bimg);
        	
        	File input = new File("/Users/matthew/Downloads/mapfile.txt");
        	Scanner scan = new Scanner(input);
        	int xCoord, yCoord;
        	
			for (int i = 0; i < 128; i++) {
	        	for (int j = 0; j < 128; j++) {
	        		JButton button = new JButton();
        			button.setBounds(i*5, (128-j)*4, 5, 4);
        			button.setVisible(false);
	        		gridButtons[i][j] = button;
	        	}
			}
        	while (scan.hasNextLine()) {
        		String line = scan.nextLine();
        		String[] info = line.split("; ");
        		if (info[0].equals("New")) {
        			xCoord = Integer.parseInt(info[2]);
        			yCoord = Integer.parseInt(info[3]);
        			g.setColor(Color.MAGENTA);
        			g.fillRect(xCoord*5 + 32, (128-yCoord)*4 + 174, 5, 4);
        			g.setColor(Color.BLACK);
        			g.drawRect(xCoord*5 + 32, (128-yCoord)*4 + 174, 5, 4);
        			gridButtons[xCoord][yCoord].setIcon(ticon);
        		}
        	}
        	scan.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

public class SJSUNav {
	
	public enum Direction{LEFT, RIGHT, UP, DOWN};
	
	static int startX = -1;
	static int endX = -1;
	static int startY = -1;
	static int endY = -1;
	
	final static String GREEN_PATH = "/Users/matthew/Downloads/greenblock.png";
	final static String TGREEN_PATH = "/Users/matthew/Downloads/transparentgreenblock.png";
	final static String RED_PATH = "/Users/matthew/Downloads/redblock.png";
	final static String TRED_PATH = "/Users/matthew/Downloads/transparentredblock.png";
	final static String TBLUE_PATH = "/Users/matthew/Downloads/transparentblueblock.png";
	final static String T_PATH = "/Users/matthew/Downloads/transparentblock.png";
	
	public static void main(String args[]) {
		Direction d = Direction.LEFT;
		
		try {
		BufferedImage bimg = ImageIO.read(new File(GREEN_PATH));
		final ImageIcon greenicon = new ImageIcon(bimg);
		bimg = ImageIO.read(new File(TGREEN_PATH));
		final ImageIcon tgreenicon = new ImageIcon(bimg);
		bimg = ImageIO.read(new File(RED_PATH));
		final ImageIcon redicon = new ImageIcon(bimg);
		bimg = ImageIO.read(new File(TRED_PATH));
		final ImageIcon tredicon = new ImageIcon(bimg);
		bimg = ImageIO.read(new File(T_PATH));
		final ImageIcon ticon = new ImageIcon(bimg);
		bimg = ImageIO.read(new File(TBLUE_PATH));
		final ImageIcon tblueicon = new ImageIcon(bimg);
		} catch (Exception exception) {
            exception.printStackTrace();
        }
		
		JFrame mainWindow = new JFrame();
		
		try {
			MapImage mi = new MapImage();
			
			/*JButton[][] gridButtons = new JButton[128][128];
			for (int i = 0; i < 128; i++) {
	        	for (int j = 0; j < 128; j++) {
	        		
	        		gridButtons[i][j] = button;
	        	}
	        }
			
			for (int i = 0; i < 128; i++) {
				for (int j = 0; j < 128; j++) {
					mainWindow.add(gridButtons[i][j]);
				}
			}*/
						
			mi.addMouseListener(new MouseAdapter() {
				
	            @Override
	            public void mouseClicked(MouseEvent e) {
	            	PointerInfo a;
	    	    	Point b;
	    			int x, y;
	            	a = MouseInfo.getPointerInfo();
					b = a.getLocation();
		        	x = (int) b.getX();
		        	y = (int) b.getY();
		        	x = (x-32)/5;
		        	y = 183 - y/4;
		        	System.out.println(x + ", " + y);
					if (MapImage.gridButtons[x][y].getIcon() == ticon);
	            }
	        });
		
			mainWindow.add(mi);		
			mainWindow.setSize(800,800);  
		    mainWindow.setVisible(true); 
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}
}
