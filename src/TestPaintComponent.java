import java.awt.*;
import java.util.*;
import java.awt.event.*;

import javax.swing.*;
import java.awt.geom.*;
import java.awt.font.*;
@SuppressWarnings("serial")
public class TestPaintComponent extends JPanel implements MouseMotionListener, MouseListener{
    
	private int x, y, distance = 25;
	private float fontSize;
	private int ClassNum ;  //Number of class
	private int px[];
	private int py[];
	private ArrayList<Integer> numberPerRow;
	private ArrayList<String> className;
	private ArrayList<ArrayList<String>> attributeName = new ArrayList<ArrayList<String>>(); 
    private ArrayList<ArrayList<String>> methodName = new ArrayList<ArrayList<String>>();  
    
    public void setData(ArrayList<String> c, ArrayList<ArrayList<String>> a, ArrayList<ArrayList<String>> m, int nc,ArrayList<Integer> number) {
    	className = c;
    	attributeName = a;
    	methodName = m;
    	px = new int[nc];
    	py = new int[nc];
    	ClassNum = nc; yD = new int[nc];
    	numberPerRow = number;
    }
    private int xD = 200, h = 40;
    private int[] yD;
    public String getClass(String s) {
    	String[] tmp = s.split(" ");
    	for(int i = 0; i < tmp.length; i++) {
    		if(tmp[i].equals("class")){
    			return tmp[i+1];
    		}
    	}
    	return "";
    }
    //@Override
    
    
    public void paintComponent(Graphics g) {
    	
    	super.paintComponent(g);
        Graphics2D g1 = (Graphics2D)g;
        g1.setPaint(Color.black);
        Font font = g1.getFont().deriveFont(fontSize);
        g1.setFont(font);FontRenderContext frc = g1.getFontRenderContext();
        //int x
        //int[] yD = new int[getClassNum()]; 
        for(int i = 0;i < ClassNum; i++) {
 
        	for(int j = 0; j < ClassNum; j++) {
        		String tmp = className.get(j).replaceAll(",", " extends"); 
        		String name = getClass(tmp);
        		
        		String tmp1 = className.get(i);
        		if(tmp1.contains("extends " + name)){
        			
        			g1.draw(new Line2D.Double(px[i] + xD/2, py[i], px[j] + xD/3, py[j] + yD[j] + 10));
        			g1.draw(new Line2D.Double(px[j] + xD/3, py[j] + yD[j], px[j] + xD/3 - 10, py[j] + yD[j] + 10));
        			g1.draw(new Line2D.Double(px[j] + xD/3, py[j] + yD[j], px[j] + xD/3 + 10, py[j] + yD[j] + 10));
        			g1.draw(new Line2D.Double(px[j] + xD/3 - 10, py[j] + yD[j] + 10, px[j] + xD/3 + 10,py[j] + yD[j] + 10));
        		}
        		if(tmp1.contains("implements " + name)) {
        			int _x1 = (px[j]+xD/2+xD/6), _y1 = py[j] + yD[j];
        			g1.draw(new Line2D.Double(px[i] + xD/2, py[i], _x1, _y1 + 30));
        			g1.draw(new Line2D.Double(_x1, _y1, _x1 - 10, _y1 + 20));
        			g1.draw(new Line2D.Double(_x1, _y1, _x1 + 10, _y1 + 20)); g1.draw(new Line2D.Double(_x1 + 10, _y1 + 20, _x1, _y1 + 30)); g1.draw(new Line2D.Double(_x1 - 10, _y1 + 20, _x1, _y1 + 30));
        		}
        	}
        }
    
        for(int i = 0; i < ClassNum; i++) {
        	h = (int)font.getStringBounds("A", frc).getHeight() + 15;
        	int x1 = px[i], y1 = py[i], x2 = x1 + xD, y2 = y1 + yD[i]; 
        	g1.setColor(Color.WHITE); g1.fillRect(x1, y1, xD, yD[i]);
        	g1.setColor(Color.BLACK); g1.setStroke(new BasicStroke(3));
        	g1.draw(new Line2D.Double(x1, y1, x2, y1));
        	g1.draw(new Line2D.Double(x1, y2, x2, y2));
        	g1.draw(new Line2D.Double(x1, y1, x1, y2));
        	g1.draw(new Line2D.Double(x2, y1, x2 , y2));
        	g1.setStroke(new BasicStroke(1));
        	
        	String name = getClass(className.get(i));
        	
        	int width = (int)font.getStringBounds(name, frc).getWidth();
        	int height = (int)font.getStringBounds(name, frc).getHeight();
        	int sx = x1 + (xD - width)/2;
        	int sy = y1 + (h + height/2)/2;
        	
        	g1.drawString(name, sx, sy);
        	y1 += h; sy += h; sx = x1 + 10;
        	if(attributeName.get(i).size() != 0 || methodName.get(i).size() != 0){
        		g1.draw(new Line2D.Double(x1, y1, x2, y1));}
        	for(String s: attributeName.get(i)) {
        		
        		if(s.contains("public")) {
        			s = s.replaceAll("public", ""); s = "+ " + s;
        		}
        		else if(s.contains("private")) {s = s.replaceAll("private", ""); s = "- " + s;}
        		else if(s.contains("protected")) {s = s.replaceAll("protected", ""); s =" # " + s;}
        		else s = "~ " + s;
        		int w = (int)font.getStringBounds(s, frc).getWidth();
        		
        		if(w + 10 < xD){
        			g1.drawString(s, sx, sy);
        		}
        		else{
        			String str = s.substring(0, 14) + "... ";
        			g1.drawString(str, sx, sy);
        		}
        		sy += h; y1 += h;
        	} 
        	sy += h; y1 += h;
        	//if(attributeName.get(i).size() != 0 || methodName.get(i).size() != 0) {
        		g1.draw(new Line2D.Double(x1, y1, x2, y1));
        	//}
        	for(String s: methodName.get(i)) {
        		
        		if(s.contains("public")) {
        			s = s.replaceAll("public", ""); s = "+ " + s;
        		}
        		else if(s.contains("private")) {s = s.replaceAll("private", ""); s = "- " + s;}
        		else if(s.contains("protected")) {s = s.replaceAll("protected", ""); s = "# " + s;
        		}
        		else s = "~ " + s;
        		int w = (int)font.getStringBounds(s, frc).getWidth();
        		if(w + 10 < xD) {
        			g1.drawString(s, sx, sy);
        		}
        		else {
        			String str = s.substring(0, 21) + "...";
        			g1.drawString(str, sx, sy);
        		}
        		
        		sy += h;
        		y1 += h;
        	}
       
        }
 
    }
    
    public int getClassNum() {
    	return ClassNum;
    }
    public void setFontSize(int s) {
    	fontSize = s;
    }
    public void setYD(){
    	for(int i = 0; i < ClassNum; i++) {
    		yD[i] = h*(attributeName.get(i).size() + methodName.get(i).size() + 1);
    	}
    }
    public int max(int[] a, int s, int e) {
    	int maxx = a[s];
    	for(int i = s+1; i <= e; i++) {
    		if(a[i]>maxx) maxx= a[i];
    	}
    	return maxx;
    }
    public void setyD() {
    	for(int i = 0; i < ClassNum; i++) {
    		int num1 = attributeName.get(i).size(), num2 = methodName.get(i).size();
    		if(num1 == 0) num1 = 1;
    		if(num2 == 0) num2 = 1;
    		yD[i] = h*(num1 + num2 + 1);
    	}
    }
    public void setpXpY(int distance) { Integer tmp = 0, tmp1 = 0;	
    	for(int i = 0; i < numberPerRow.size(); i++) {
    		int k = 0;
    		for(Integer j = tmp ; j < numberPerRow.get(i) + tmp; j++) {
    			px[j] = 50 + (2*distance + xD)*k;
    			k++;
    			if(i==0) {
    				py[j] = 50;
    			}
    			else {
    				py[j] = 50 + (max(yD, tmp1, tmp+tmp1-1)+distance*4)*i;
    			}
    		}
    		tmp1 = tmp1 +tmp;
    		tmp = numberPerRow.get(i) + tmp1;
    	}
    	
    }
    public void setClassNum(int c) {
    	ClassNum = c;
    }
    @Override
    public void mousePressed(MouseEvent a) {
    	x = a.getX();
    	y = a.getY();
    } 
    @Override
	public void mouseDragged(MouseEvent a){
		//System.out.println(x + "  " + y);
		//System.out.println("Mouse:" + a.getX() + "  " + a.getY());
		
		for(int i = 0; i < ClassNum; i++) {
			if(x >= px[i] && x <= px[i] + 200 && y <= py[i] + yD[i] && y >=py[i]) {
				x = a.getX(); y =  a.getY();
				px[i] = a.getX() - 70;
				py[i] = a.getY() - yD[i]/2;
				repaint();
			}
		}
	}
	@Override
	public void mouseMoved(MouseEvent a){};
	@Override
	public void mouseClicked(MouseEvent a){};
	@Override
	public void mouseReleased(MouseEvent a){};
	@Override
	public void mouseExited(MouseEvent a){};
	@Override
	public void mouseEntered(MouseEvent a){};
	
	public TestPaintComponent() {
		//setBackground(Color.GREEN.darker().darker());
		addMouseMotionListener(this);
		addMouseListener(this);
		addMouseWheelListener(new MouseWheelListener(){
			@Override
			public void mouseWheelMoved(MouseWheelEvent a) {
				float f = a.getWheelRotation(), d;
				d = 0.05f * f; 
				fontSize += d;
				xD += (int) (f * 0.5f); h += (int)(f * 0.5f);
				//distance += (int) (f * 0.05f); setpXpY(distance);
				//for(int i = 0; i < ClassNum; i++) {
				//	px[i] += (int) (4f * f); py[i] += (int) (4f * f);
				//}
				for(int i = 0; i < ClassNum; i++) {
					if(f < 0) {
						if(yD[i] > 25){
							yD[i] += (int) (f * 0.35f);
						}
						else yD[i] = 25;
					}
					else yD[i] += (int)(f * 0.35f);
				}
				repaint();
			}
		});
	}
	public Dimension getPreferredSize(){
		return new Dimension(1920*4, 1080*4);
	}
	
	public static void pause(int t) {
		try {
			Thread.sleep(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}