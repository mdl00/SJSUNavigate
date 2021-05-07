import java.util.HashMap;
import java.util.LinkedList;
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
	boolean[][] onRoute = new boolean[128][128];
	LinkedList<Node> nodes = new LinkedList<Node>();
	
	int xStart = -1, yStart = -1;
	int xEnd = -1, yEnd = -1;
	boolean showRoute = false;
	
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
	
	public int[] getStart() {
		int[] result = {xStart, yStart};
		return result;
	}
	
	public int[] getEnd() {
		int[] result = {xEnd, yEnd};
		return result;
	}
	
	public boolean hasStart() {
		if (xStart != -1) return true;
		return false;
	}
	
	public boolean hasEnd() {
		if (xEnd != -1) return true;
		return false;
	}
	
	public void showRoute(boolean show) {
		showRoute = show;
		if (!show) {
			for (int i = 0; i < 128; i++) {
				for (int j = 0; j < 128; j++) {
					onRoute[i][j] = false;
				}
			}
		}
	}
	
	public LinkedList<Node> getNodes() {
		return nodes;
	}
	
	public void includeOnRoute(boolean isOn, int x, int y) {
		onRoute[x][y] = isOn;
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
        	
        	File input = new File("/Users/matthew/Downloads/MapInfo.txt");
        	Scanner scan = new Scanner(input);
        	int xCoord, yCoord;
        	
			for (int i = 0; i < 128; i++) {
	        	for (int j = 0; j < 128; j++) {
	        		gridButtons[i][j] = null;
	        	}
			}
			
			if (showRoute) {
				for (int i = 0; i < 128; i++) {
					for (int j = 0; j < 128; j++) {
						if (onRoute[i][j]) {
							g.setColor(Color.CYAN);
							g.fillRect(i*5 + 32, (128-j)*4 + 174, 5, 4);
						}
					}
				}
			}
			
        	while (scan.hasNextLine()) {
        		String line = scan.nextLine();
        		String[] info = line.split("; ");
        		if (info[0].equals("New")) {
        			xCoord = Integer.parseInt(info[2]);
        			yCoord = Integer.parseInt(info[3]);
        			Node node = new Node(info[1], xCoord, yCoord);
                    nodes.add(node);
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
		
		Login login = new Login();
		login.start();
		
		JFrame mainWindow = new JFrame();
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextField startField = new JTextField();
		startField.setBounds(20, 720, 200, 20);
		startField.setVisible(true);
		mainWindow.add(startField);
		
		JLabel startLabel = new JLabel();
		startLabel.setText("Starting point");
		startLabel.setBounds(20, 750, 200, 20);
		mainWindow.add(startLabel);
		
		JTextField endField = new JTextField();
		endField.setBounds(270, 720, 200, 20);
		endField.setVisible(true);
		mainWindow.add(endField);
		
		JLabel endLabel = new JLabel();
		endLabel.setText("Destination");
		endLabel.setBounds(270, 750, 200, 20);
		mainWindow.add(endLabel);
		
		JButton confirm = new JButton();
		confirm.setText("Find Route");
		confirm.setBounds(500, 720, 80, 20);
		confirm.setVisible(true);
		mainWindow.add(confirm);
		
		try {
			MapImage mi = new MapImage();
			
			for (int i = 0; i < 128; i++) {
				for (int j = 0; j < 128; j++) {
					if (mi.getButtons()[i][j] != null) mainWindow.add(mi.getButtons()[i][j]);
				}
			}
			
			confirm.addMouseListener(new MouseAdapter() 
			{
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					mi.showRoute(true);
    				LinkedList<Node> path = Path.findShortestPathByName("/Users/matthew/Downloads/MapInfo.txt", startField.getText(), endField.getText()).getPath();
    				System.out.println("LIST OUTPUT: ");
    				System.out.println(path.toString());
    				for (int i = 0; i < path.size(); i++) {
    					if (!(path.get(i).toString().contains("-"))) path.remove(i);
    				}
    				for (int i = 0; i < path.size() - 1; i++) {
    					int startX = path.get(i).getX();
    					int startY = path.get(i).getY();
    					int endX = path.get(i+1).getX();
    					int endY = path.get(i+1).getY();
    					
    					System.out.println("CONNECTING NODES " + startX + "-" + startY + " TO " + endX + "-" + endY);
    					
    					if (endX > startX) {
    						for (int j = startX + 1; j <= endX; j++) {
    							mi.includeOnRoute(true, j, startY);
    						}
    					} else {
    						for (int j = startX - 1; j >= endX; j--) {
    							mi.includeOnRoute(true, j, startY);
    						}
    					}
    					if (endY > startY) {
    						for (int j = startY + 1; j < endY; j++) {
    							mi.includeOnRoute(true, endX, j);
    						}
    					} else {
    						for (int j = startY - 1; j > endY; j--) {
    							mi.includeOnRoute(true, endX, j);
    						}
    					}
    				}
    				mi.repaint();
				}
			});
						
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
		        	if (x < 128 && y < 128) {
		        		if (mi.getButtons()[x][y] != null) {
		        			if (mi.hasStart() && mi.hasEnd()) {
		        				mi.setStart(x, y);
		        				mi.setEnd(-1, -1);
		        				mi.showRoute(false);
		        			} else if (!mi.hasStart()) {
		        				mi.setStart(x, y);
		        				mi.showRoute(false);
		        			} else if (!mi.hasEnd()) {
		        				mi.setEnd(x, y);
		        				int[] startPoint = mi.getStart();
		        				int[] endPoint = mi.getEnd();
		        				String startName = "", endName = "";
		        				for (int i = 0; i < mi.getNodes().size(); i++) {
		        					if (mi.getNodes().get(i).getX() == startPoint[0] && mi.getNodes().get(i).getY() == startPoint[1]) {
		        						startName = mi.getNodes().get(i).getName();
		        					}
		        					if (mi.getNodes().get(i).getX() == endPoint[0] && mi.getNodes().get(i).getY() == endPoint[1]) {
		        						endName = mi.getNodes().get(i).getName();
		        					}
		        				}
		        				mi.showRoute(true);
		        				String startCoord = mi.getStart()[0] + "-" + mi.getStart()[1];
		        				String endCoord = mi.getEnd()[0] + "-" + mi.getEnd()[1];
		        				System.out.println("PATH FROM " + startCoord + " TO " + endCoord);
		        				LinkedList<Node> path = Path.findShortestPathByName("/Users/matthew/Downloads/MapInfo.txt", startName, endName).getPath();
		        				System.out.println("LIST OUTPUT: ");
		        				System.out.println(path.toString());
		        				for (int i = 0; i < path.size(); i++) {
		        					if (!(path.get(i).toString().contains("-"))) path.remove(i);
		        				}
		        				for (int i = 0; i < path.size() - 1; i++) {
		        					int startX = path.get(i).getX();
		        					int startY = path.get(i).getY();
		        					int endX = path.get(i+1).getX();
		        					int endY = path.get(i+1).getY();
		        					
		        					System.out.println("CONNECTING NODES " + startX + "-" + startY + " TO " + endX + "-" + endY);
		        					
		        					if (endX > startX) {
		        						for (int j = startX + 1; j <= endX; j++) {
		        							mi.includeOnRoute(true, j, startY);
		        						}
		        					} else {
		        						for (int j = startX - 1; j >= endX; j--) {
		        							mi.includeOnRoute(true, j, startY);
		        						}
		        					}
		        					if (endY > startY) {
		        						for (int j = startY + 1; j < endY; j++) {
		        							mi.includeOnRoute(true, endX, j);
		        						}
		        					} else {
		        						for (int j = startY - 1; j > endY; j--) {
		        							mi.includeOnRoute(true, endX, j);
		        						}
		        					}
		        				}
		        			}
						} else {
							mi.setStart(-1, -1);
							mi.setEnd(-1, -1);
							mi.showRoute(false);
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
