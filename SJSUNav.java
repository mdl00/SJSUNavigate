import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SJSUNav extends Canvas {
	
	public enum Direction{LEFT, RIGHT, UP, DOWN};
	
	private static final String GREEN_PATH = "/Users/matthew/Downloads/greenblock.png";
	private static final String TGREEN_PATH = "/Users/matthew/Downloads/transparentgreenblock.png";
	private static final String RED_PATH = "/Users/matthew/Downloads/redblock.png";
	private static final String TRED_PATH = "/Users/matthew/Downloads/transparentredblock.png";
	private static final String T_PATH = "/Users/matthew/Downloads/transparentblock.png";
	
	public static boolean continuing = true;
	
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
		
		ImageIcon greenicon = null, tgreenicon = null, redicon = null, tredicon = null, ticon = null;
		
		JFrame mainWindow = new JFrame();
		
		try {
			BufferedImage img = ImageIO.read(new File(GREEN_PATH));
			greenicon = new ImageIcon(img);
			img = ImageIO.read(new File(TGREEN_PATH));
			tgreenicon = new ImageIcon(img);
			img = ImageIO.read(new File(RED_PATH));
			redicon = new ImageIcon(img);
			img = ImageIO.read(new File(TRED_PATH));
			tredicon = new ImageIcon(img);
			img = ImageIO.read(new File(T_PATH));
			ticon = new ImageIcon(img);
			SJSUNav sn = new SJSUNav();
			
			JButton button = new JButton("Close");
			button.setBounds(350, 550, 100, 20);
			button.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                	continuing = false;
                }
            });
			mainWindow.add(button);
			mainWindow.add(sn);
			
			mainWindow.setSize(800,800);  
		    mainWindow.setVisible(true); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		PointerInfo a;
    	Point b;
		int x, y;
		
		JButton jb = new JButton(tgreenicon);
		jb.setBounds(0,0,0,0);
		mainWindow.add(jb);
		
		while(continuing) {
			a = MouseInfo.getPointerInfo();
			b = a.getLocation();
        	x = (int) b.getX();
        	y = (int) b.getY();
        	x = x/16;
        	y = y/16 - 3;
        	
    		jb.setBounds(x*16, y*16, 16, 16);
    		jb.repaint();
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
