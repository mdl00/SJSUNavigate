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
	
	JButton[][] gridButtons = new JButton[128][128];
	
	int xStart = -1, yStart = -1;
	int xEnd = -1, yEnd = -1;
	
	public JButton[][] getButtons() {
		return gridButtons;
	}
	
	public void setStart(int x, int y) {
		xStart = x;
		yStart = y;
	}
	
	public void setEnd(int x, int y) {
		xEnd = x;
		yEnd = y;
	}
	
	public boolean hasStart() {
		if (xStart != -1) return true;
		return false;
	}
	
	public boolean hasEnd() {
		if (xEnd != -1) return true;
		return false;
	}
	
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
	        		gridButtons[i][j] = null;
	        	}
			}
        	while (scan.hasNextLine()) {
        		String line = scan.nextLine();
        		String[] info = line.split("; ");
        		if (info[0].equals("New")) {
        			xCoord = Integer.parseInt(info[2]);
        			yCoord = Integer.parseInt(info[3]);
        			if (xCoord == xStart && yCoord == yStart) {
        				g.setColor(Color.GREEN);
        			} else if (xCoord == xEnd && yCoord == yEnd) {
        				g.setColor(Color.RED);
        			} else {
        				g.setColor(Color.MAGENTA);
        			}
        			g.fillRect(xCoord*5 + 32, (128-yCoord)*4 + 174, 5, 4);
        			g.setColor(Color.BLACK);
        			g.drawRect(xCoord*5 + 32, (128-yCoord)*4 + 174, 5, 4);
        			JButton button = new JButton(ticon);
        			button.setBounds(xCoord*5, (128-yCoord)*4, 5, 4);
        			button.setVisible(false);
	        		gridButtons[xCoord][yCoord] = button;
        		}
        	}
        	scan.close();
        } catch (Exception exception) {
        	System.out.print("Stack exception: ");
            exception.printStackTrace();
        }
        
        for (int i = 0; i < 128; i++) {
        	for (int j = 0; j < 128; j++) {
        		if (gridButtons[i][j] != null) {
        			System.out.println(i + ", " + j);
        		}
        	}
        }
    }
}

public class SJSUNav {
	
	public enum Direction{LEFT, RIGHT, UP, DOWN};
	
	int startX = -1;
	int endX = -1;
	int startY = -1;
	int endY = -1;
	
	public static void main(String args[]) {
		Direction d = Direction.LEFT;
		
		JFrame mainWindow = new JFrame();
		
		try {
			MapImage mi = new MapImage();
			
			for (int i = 0; i < 128; i++) {
				for (int j = 0; j < 128; j++) {
					if (mi.getButtons()[i][j] != null) mainWindow.add(mi.getButtons()[i][j]);
				}
			}
						
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
		        	if (x < 128 && y < 128) {
		        		if (mi.getButtons()[x][y] != null) {
		        			if (mi.hasStart() && mi.hasEnd()) {
		        				mi.setStart(x, y);
		        				mi.setEnd(-1, -1);
		        			} else if (!mi.hasStart()) {
		        				mi.setStart(x, y);
		        			} else if (!mi.hasEnd()) {
		        				mi.setEnd(x, y);
		        			}
						} else {
							mi.setStart(-1, -1);
							mi.setEnd(-1, -1);
						}
		        		mi.repaint();
		        	}
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
