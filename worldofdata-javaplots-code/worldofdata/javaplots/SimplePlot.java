package worldofdata.javaplots;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import javax.swing.*;
 
public class SimplePlot extends SimpleGraphComponent {
	
	double [][] x= {{1,2,3,4,5,6}};
	double [][] y= {{1,2,3,2,1,2}};
	Color [] colors = null;
	public enum Style {LINE, MARKER, BOTH};
	Style [] style = null;
	
	public void setData(int [] x, int [] y, Color col, Style style) {
		double [] xd = new double[x.length];

		double [] yd = new double[y.length];

		for(int i=0;i<x.length;i++) {
			xd[i] = (double)x[i];
			yd[i] = (double)y[i];
		}
		setData(xd,yd,col,style);
	}
	
	public void setData(int [] x, int [] y) {
		setData(x,y,defaultColors[0],Style.LINE);
	}
	
	public void setData(int [] x, int [] y, Style style) {
		setData(x,y,defaultColors[0],style);
	}
	
	public void setData(int [] x, int [] y, Color col) {
		setData(x,y,col,Style.LINE);
	}
	
	public void setData(double [] x, double [] y) {
		setData(x,y,defaultColors[0],Style.LINE);
	}
	
	public void setData(double [] x, double [] y, Style style) {
		setData(x,y,defaultColors[0],style);
	}
	
	public void setData(double [] x, double [] y, Color col) {
		setData(x,y,col,Style.LINE);
	}
	
	public void setData(double [] x, double [] y, Color col, Style style) {
		this.x = new double[1][];
		this.x[0]=x;
		this.y = new double[1][];
		this.y[0]=y;
		this.colors = new Color[1];
		this.colors[0] = col;
		this.style = new Style[1];
		this.style[0] = style;
        repaint();
	}
	
	public void addData(int [] x, int [] y) {
		addData(x,y,
				defaultColors[this.x.length%defaultColors.length],
				Style.LINE);
	}
	public void addData(int [] x, int [] y, Color col) {
		addData(x,y,
				col,
				Style.LINE);
	}
	public void addData(int [] x, int [] y, Style style) {
		addData(x,y,
				defaultColors[this.x.length%defaultColors.length],
				style);
	}
	
	public void addData(double [] x, double [] y) {
		addData(x,y,
				defaultColors[this.x.length%defaultColors.length],
				Style.LINE);
	}
	public void addData(double [] x, double [] y, Color col) {
		addData(x,y,
				col,
				Style.LINE);
	}
	public void addData(double [] x, double [] y, Style style) {
		addData(x,y,
				defaultColors[this.x.length%defaultColors.length],
				style);
	}
	
	public void addData(int [] x,
			int [] y, 
			Color col, Style style) {
		
		double [] xd = new double[x.length];

		double [] yd = new double[y.length];

		for(int i=0;i<x.length;i++) {
			xd[i] = (double)x[i];
			yd[i] = (double)y[i];
		}
		addData(xd,yd,col,style);
	}
	
	public void addData(double [] x, double [] y, Color col, Style style) {
		int nplots= this.x.length;
		double [][] newX = new double[nplots+1][];
		double [][] newY = new double[nplots+1][];
		Color [] newC = new Color[nplots+1];
		Style [] newS = new Style[nplots+1];
		for(int i=0;i<nplots;i++) {
			newX[i]=this.x[i];
			newY[i]=this.y[i];
			newC[i]=this.colors[i];
			newS[i]=this.style[i];
		}
		this.x = newX;
		this.x[nplots]=x;
		this.y = newY;
		this.y[nplots]=y;
		this.colors = newC;
		this.colors[nplots]=col;
		this.style = newS;
		this.style[nplots]=style;
        repaint();
	}
	
	public void drawPlot(Graphics g, double [] x, double [] y,
    		Style style) {
    	int px1, py1, px2,py2;
        if(x!=null) {
        	if(style==Style.LINE||style==Style.BOTH) {
	        	px1 = real2PixelX(x[0]);
	        	py1 = real2PixelY (y[0]);
		        for(int i=1;i<x.length;i++) {
		        	px2 = real2PixelX(x[i]);
		        	py2 = real2PixelY(y[i]);
		        	drawLine(g,px1, py1, px2, py2);
		        	px1=px2;
		        	py1=py2;
		        }
        	}
        	if(style==Style.MARKER||style==Style.BOTH) {
		        for(int i=0;i<x.length;i++) {
		        	px2 = real2PixelX(x[i]);
		        	py2 = real2PixelY(y[i]);
		        	g.fillOval(px2-marker/2, py2-marker/2,marker,marker);
		        	Color last = g.getColor();
		        	g.setColor(foreground);
		        	g.drawOval(px2-marker/2, py2-marker/2,marker,marker);
		        	g.setColor(last);
		        }
        	}
        }
    }
	
	public void paint(Graphics g) {
    	super.paint(g);

    	for(int i=0;i<x.length;i++) {
    		if(colors==null) {
    			g.setColor(defaultColors[i%defaultColors.length]);
    		} else if (i>=colors.length ){
    			g.setColor(defaultColors[i%defaultColors.length]);
    		} else {
    			g.setColor(colors[i]);
    		}
    		if(style==null) {
        		drawPlot(g,x[i],y[i],Style.BOTH);
    		} else if(i>=style.length) {
        		drawPlot(g,x[i],y[i],Style.BOTH);

    		}else {
        		drawPlot(g,x[i],y[i],style[i]);

    		}
    	}
        g.setClip(null);
    }
	
	public SimplePlot() {
		super();
	}
	
	public SimplePlot(int w, int h) {
		super(w,h);
	}
	
	public SimplePlot(boolean addFrame) {
		super(addFrame);
	}
	
	public SimplePlot(int w, int h,boolean addFrame) {
		super(w,h,addFrame);
	}
 
    public static void main(String[] args) {
 
        double [] x = {1,2,3,4,5,6,7,8,9};
        double [] y = { 5,4,3,2,1,2,3,4,5};
        
        double [] y2 = { 9.5,9,8,7,4,5,6,2,3};
        
        SimplePlot s = new SimplePlot(800,800);
        
        s.setForeground(Color.DARK_GRAY);
        s.setBackground(new Color(0.95f,0.95f,0.95f));
        s.setData(x,y);
        s.setThickness(4);
        s.setMarkerSize(20);
        s.setTitle("Title");
        s.setYLabel("Y Label");
        s.setXLabel("X Label");
        s.addData(x, y2,new Color(0.5f,0,0), Style.BOTH);
        s.repaint();
        
    }
}