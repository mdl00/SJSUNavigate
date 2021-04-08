import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class MapImage extends Canvas {
	public void paint(Graphics g) {  
        setBackground(Color.WHITE);   
		
		Toolkit t=Toolkit.getDefaultToolkit();  
        Image img=t.getImage("/Users/matthew/Downloads/111919_NorthCampus_WEB_07.jpg");
        Image logo=t.getImage("/Users/matthew/Downloads/Applogo131.png");
        g.drawImage(img,0,0,this);
        
        g.fillRect(0,0,500,110); 
        setForeground(Color.WHITE); 
        g.drawImage(logo,0,0,this);
        g.setColor(Color.BLACK);
        for (int i = 4; i < 40; i++) {
        	for (int j = 11; j < 42; j++) {
        		g.drawRect(i*16, j*16, 16, 16);
        	}
        }
    }
}

public class SJSUNav {
	
	public enum Direction{LEFT, RIGHT, UP, DOWN};
	
	private static final String GREEN_PATH = "/Users/matthew/Downloads/greenblock.png";
	private static final String TGREEN_PATH = "/Users/matthew/Downloads/transparentgreenblock.png";
	private static final String RED_PATH = "/Users/matthew/Downloads/redblock.png";
	private static final String TRED_PATH = "/Users/matthew/Downloads/transparentredblock.png";
	private static final String TBLUE_PATH = "/Users/matthew/Downloads/transparentblueblock.png";
	private static final String T_PATH = "/Users/matthew/Downloads/transparentblock.png";
	
	static int startX = -1;
	static int endX = -1;
	static int startY = -1;
	static int endY = -1;
	
	public static void main(String args[]) {
		String[][] locs = loadMap();
		Direction d = Direction.LEFT;
		
		for (int i = 0; i < 20; i++) {
			System.out.print((i+1) + ": ");
			for (int j = 0; j < 19; j++) {
				System.out.print(locs[i][j] + " ");
			}
			System.out.println();
			System.out.println();
		}
		
		JFrame mainWindow = new JFrame();
		
		try {
			BufferedImage img = ImageIO.read(new File(GREEN_PATH));
			final ImageIcon greenicon = new ImageIcon(img);
			img = ImageIO.read(new File(TGREEN_PATH));
			final ImageIcon tgreenicon = new ImageIcon(img);
			img = ImageIO.read(new File(RED_PATH));
			final ImageIcon redicon = new ImageIcon(img);
			img = ImageIO.read(new File(TRED_PATH));
			final ImageIcon tredicon = new ImageIcon(img);
			img = ImageIO.read(new File(T_PATH));
			final ImageIcon ticon = new ImageIcon(img);
			img = ImageIO.read(new File(TBLUE_PATH));
			final ImageIcon tblueicon = new ImageIcon(img);
			MapImage mi = new MapImage();
			
			JButton[][] gridButtons = new JButton[36][31];
			for (int i = 4; i < 40; i++) {
	        	for (int j = 11; j < 42; j++) {
	        		JButton button = new JButton(ticon);
	        		button.setBounds(i*16, j*16, 16, 16);
	        		button.setVisible(false);
	        		gridButtons[i-4][j-11] = button;
	        	}
	        }
			
			for (int i = 0; i < 36; i++) {
				for (int j = 0; j < 31; j++) {
					mainWindow.add(gridButtons[i][j]);
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
		        	x = x/16 - 4;
		        	y = y/16 - 14;
		        	System.out.println(x + ", " + y);
		        	if (x >= 0 && x < 36 && y >= 0 && y < 31) {
		        		if (startX == -1 || endX != -1) {
		        			if (endX != -1) {
		        				for (int i = 0; i < 36; i++) {
		        					for (int j = 0; j < 31; j++) {
		        						gridButtons[i][j].setVisible(false);
		        					}
		        				}
		        				endX = -1;
		        				endY = -1;
		        			}
		        			gridButtons[x][y].setIcon(greenicon);
		        			gridButtons[x][y].setVisible(true);
		        			startX = x;
			        		startY = y;
			        		
		        		} else {
		        			gridButtons[x][y].setIcon(redicon);
		        			gridButtons[x][y].setVisible(true);
		        			endX = x;
			        		endY = y;
			        		try{Thread.sleep(1000);}catch(Exception err){}    
			        		for (int i = startX + 1; i <= endX; i++) {
			        			gridButtons[i][startY].setIcon(tblueicon);
			        			gridButtons[i][startY].setVisible(true);
			        		}
			        		for (int i = startY + 1; i < endY; i++) {
			        			gridButtons[endX][i].setIcon(tblueicon);
			        			gridButtons[endX][i].setVisible(true);
			        		}
			        	}
		        	}
	            }
	        });
		
			mainWindow.add(mi);		
			mainWindow.setSize(800,800);  
		    mainWindow.setVisible(true); 
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static String[][] loadMap() {
		String[][] map = {{"KNG","KNG","[ ]","U.T","[ ]","DMH","[ ]","CAR","[ ]","ENG","ENG","ENG","[ ]","I.S","I.S","[ ]","CYA","[ ]","[ ]"},
				  {"KNG","KNG","[ ]","HGH","[ ]","IRC","[ ]","ADM","[ ]","ENG","ENG","ENG","[ ]","I.S","I.S","[ ]","CYB","CYB","[ ]"},
				  {"KNG","KNG","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","ENG","ENG","ENG","[ ]","I.S","I.S","[ ]","[ ]","[ ]","[ ]"},						
				  {"[ ]","[ ]","[ ]","[ ]","C.C","[ ]","C.L","C.L","[ ]","ENG","ENG","ENG","[ ]","I.S","I.S","[ ]","[ ]","[ ]","[ ]"},
				  {"SCI","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"},
				  {"SCI","SCI","[ ]","T.H","M.D","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","ATM","[ ]","[ ]"},
				  {"WSQ","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","CCB","[ ]","S.U","S.U","S.U","ASP","B.K","[ ]","[ ]","[ ]","[ ]","[ ]"},
				  {"WSQ","WSQ","[ ]","[ ]","DBH","DBH","[ ]","CCB","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"},
				  {"[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","B.T","[ ]"},
				  {"[ ]","[ ]","[ ]","SPM","[ ]","FOB","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","BBC","BBC","[ ]"},
			          {"YUH","YUH","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"},
				  {"YUH","YUH","[ ]","SPC","SPC","SPE","[ ]","SWC","[ ]","E.C","E.C","E.C","E.C","[ ]","H.B","[ ]","C.P","C.P","[ ]"},
				  {"[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","E.C","E.C","E.C","E.C","[ ]","[ ]","[ ]","C.P","C.P","[ ]"},
				  {"[ ]","[ ]","[ ]","[ ]","M.H","[ ]","S.H","S.H","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"},
				  {"WPG","[ ]","[ ]","[ ]","M.H","[ ]","S.H","S.H","[ ]","SAC","SAC","SAC","SAC","[ ]","[ ]","[ ]","CVC","[ ]","CVB"},
				  {"WPG","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","SAC","SAC","SAC","SAC","[ ]","[ ]","[ ]","CVC","[ ]","CVB"},
				  {"WPG","[ ]","D.H","D.H","[ ]","SPG","SPG","SPG","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","CVB"},
				  {"WPG","[ ]","[ ]","D.H","[ ]","SPG","SPG","SPG","[ ]","WSH","WSH","[ ]","D.C","[ ]","JWH","[ ]","CVA","[ ]","CVB"},
				  {"WPG","[ ]","D.H","D.H","[ ]","SPG","SPG","UPD","[ ]","WSH","WSH","[ ]","D.C","[ ]","JWH","[ ]","CVA","[ ]","CVB"},
				  {"[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"},
				  {"[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]","[ ]"}};
		return map;
	}
}
