package worldofdata.javaplots;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import javax.swing.*;
 
public class SimpleGraphComponent extends Component {
	int borderLeft=80;
	int borderRight=20;
	int borderTop=60;
	int borderBottom=80;
	int thick=5;
	int marker=15;
	int fontSize=24;

	double xmin=0.0;
	double xmax=10.0;
	double ymin=0.0;
	double ymax=10.0;
	int prefW=400;
	int prefH=400;
	boolean exitOnClose = true;
	boolean halfAxis = true;
	Color [] defaultColors = {Color.BLUE, Color.RED,
			Color.GREEN,Color.BLACK};
	String title = null;
	String xLabel = null;
	String yLabel = null;
	Color background = Color.WHITE;
	Color foreground = Color.BLACK;
    JFrame frame=null;
    boolean drawXLabels = true;
    boolean drawYLabels = true;
    boolean drawYMaxOnRight = false;
    
    boolean showAxis=true;
    
    public void setShowAxis(boolean showAxis) {
    	this.showAxis=showAxis;
    }
    
    public void setDrawYMaxOnRight(boolean drawYMaxOnRight) {
    	this.drawYMaxOnRight=drawYMaxOnRight;
    }
    
    public void setFontSize(int fontSize) {
    	this.fontSize=fontSize;
    }
    
    public void setBorders(int borderLeft,
    		int borderRight,int borderBottom,
    		int borderTop) {
    	this.borderLeft=borderLeft;
    	this.borderRight=borderRight;
    	this.borderBottom=borderBottom;
    	this.borderTop=borderTop;
    }
    
    public void setDrawXLabels(boolean drawXLabels) {
    	this.drawXLabels=drawXLabels;
    }
    
    public void setDrawYLabels(boolean drawYLabels) {
    	this.drawYLabels=drawYLabels;
    }
    
    public void setHalfAxis(boolean halfAxis) {
    	this.halfAxis = halfAxis;
    }
	
	public void setExitOnClose(boolean exitOnClose) {
		this.exitOnClose = exitOnClose;
	}
	
	public void setPlotBounds(double xmin, double xmax, 
			double ymin, double ymax) {
		this.xmin=xmin;
		this.xmax=xmax;
		this.ymin=ymin;
		this.ymax=ymax;
	}
	
	public void setPlotBoundsX(double xmin, double xmax) {
		this.xmin=xmin;
		this.xmax=xmax;
	}
	
	public void setPlotBoundsY(double ymin, double ymax) {
		this.ymin=ymin;
		this.ymax=ymax;
	}
	
	public void setMarkerSize(int marker) {
		this.marker = marker+1;
	}
	public void setThickness(int thick) {
		this.thick=thick+1;
	}
	public void setForeground(Color fg) {
		foreground = fg;
	}
	
	public void setBackground(Color fg) {
		background = fg;
	}
	
	public void setTitle(String title) {
		this.title = title;
		/*
		if(title==null) {
			borderTop = 10;
		} else {
			borderTop = 40;
		}
		*/
	}
	
	public void setXLabel(String xLabel) {
		this.xLabel = xLabel;
	}
	public void setYLabel(String yLabel) {
		this.yLabel = yLabel;
	}
	

	
	int real2Int(double x, double rmin, double rmax,
			int imin, int imax) {
		return imin+(int)((double)(imax-imin)*
				(x-rmin)/(rmax-rmin));
	}
	
	int real2PixelX(double x) {
		int width = getWidth();
		if(showAxis)
			return real2Int(x,xmin,xmax,borderLeft,
				width-borderRight);
		else return real2Int(x,xmin,xmax,0,
				width);
	}
	int real2PixelY(double y) {
		int height = getHeight();
		if(showAxis)
			return real2Int(y,ymax,ymin,borderTop,
				height-borderBottom);
		else 
			return real2Int(y,ymax,ymin,0,
					height);
	}
	int [] real2Pixel(double [] x) {
		return real2Pixel(x[0],x[1]);
	}
	int [] real2Pixel(double x,double y) {
		int [] retval = new int[2];
		retval[0] = real2PixelX(x);
		retval[1] = real2PixelY(y);
		return retval;
	}

	
    public Dimension getPreferredSize() {
    	return new Dimension(prefW,prefH);
    }
    
    public void drawLine(Graphics g, int x1, int y1,int x2, int y2) {
    	for(int j=-thick/2;j<thick/2;j++) {
    		g.drawLine(x1+j, y1, x2+j, y2);
    		g.drawLine(x1, y1+j, x2, y2+j);
    	}	
    }
    
    public void drawAxis(Graphics g) {
		int height = getHeight();
		int width = getWidth();
		g.setFont(new Font("TimesRoman", Font.BOLD, fontSize)); 

		String xmaxS = String.format("%.3g", xmax);
		String xminS = String.format("%.3g", xmin);
		String ymaxS = String.format("%.3g", ymax);
		String yminS = String.format("%.3g", ymin);
		if(title!=null) {
			Rectangle2D bounds = g.getFontMetrics().getStringBounds(""+title,g);
			int offsetX = (int)bounds.getWidth();
			int offsetY = (int)bounds.getHeight();
			g.drawString(title, width/2-offsetX/2, borderTop/2+offsetY/2);
		}
		
		if(yLabel!=null) {
			Rectangle2D bounds = g.getFontMetrics().getStringBounds(""+yLabel,g);
			int offsetX = (int)bounds.getWidth();
			int offsetY = (int)bounds.getHeight();
			
			Graphics2D g2d = (Graphics2D) g.create();
	        g2d.setTransform(AffineTransform.getRotateInstance(Math.toRadians(-90), borderLeft/2+offsetY/2,height/2));
	        g2d.drawString(yLabel, borderLeft/2+offsetY/2,height/2);
	        g2d.dispose();
		}
		
		if(xLabel!=null) {
			Rectangle2D bounds = g.getFontMetrics().getStringBounds(""+xLabel,g);
			int offsetX = (int)bounds.getWidth();
			int offsetY = (int)bounds.getHeight();
			g.drawString(xLabel, width/2-offsetX/2,height-borderBottom/2+offsetY/4);
		}
		
		if(drawXLabels) {
			Rectangle2D bounds = g.getFontMetrics().getStringBounds(xmaxS,g);
			int offset = (int)bounds.getWidth();
			
			g.drawString(xminS,borderLeft,height-borderBottom/2);
			g.drawString(xmaxS,width-borderRight-offset,height-borderBottom/2);
		}
		if(drawYLabels) {
			Rectangle2D bounds = g.getFontMetrics().getStringBounds(ymaxS,g);
			int offset = (int)bounds.getHeight();
			
			g.drawString(yminS,borderLeft/4,height-borderBottom);
			
			int ymaxpos = 5*borderLeft/4;
			if(drawYMaxOnRight) ymaxpos = borderLeft/4;
			g.drawString(ymaxS,ymaxpos,borderTop+offset);
		}
		drawLine(g,borderLeft, borderTop, borderLeft,
				height-borderBottom);
		drawLine(g,borderLeft, height-borderBottom, width-borderRight,
				height-borderBottom);
		if(!halfAxis) {
			drawLine(g,width-borderRight, borderTop, width-borderRight,
				height-borderBottom);
			drawLine(g,borderLeft, borderTop, width-borderRight,
				borderTop);
		}

		g.drawLine(borderLeft, real2PixelY(0), width-borderRight, real2PixelY(0));

    }
    
    public void sleep(int ms) {
    	try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
            
    public void paint(Graphics g) {
    	g.setColor(background);
    	g.fillRect(0, 0, getWidth(), getHeight());    	
    	g.setColor(foreground);
    	if(showAxis) {
    		drawAxis(g);
    		g.setClip(borderLeft,borderTop,
    			getWidth()-borderRight-borderLeft,
    			getHeight()-borderBottom-borderTop);
    	}
    }
    
    public static double [] linspace(double min, double max, int n) {
		double [] rv = new double[n];
		rv[0]=min;
		rv[n-1]=max;
		double h=(max-min)/(n-1);
		for(int i=1;i<n-1;i++) rv[i]=min+i*h;
		return rv;
	}
    
    public SimpleGraphComponent() {
    	this(500,500,true);
    }
    
    public SimpleGraphComponent(int w, int h) {
    	this(w,h,true);
    }
    
    public SimpleGraphComponent(boolean addFrame) {
    	this(500,500,addFrame);
    }
    
    public SimpleGraphComponent(int w, int h, boolean addFrame) {
    	prefW=w;
    	prefH=h;
    	
    	if(addFrame) {
	    	frame = new JFrame("Simple Plotting Window");
	    	frame.addWindowListener(new WindowAdapter(){
	            public void windowClosing(WindowEvent e) {
	            	if(exitOnClose) {
	            		System.exit(0);
	            	} else {
	            		frame.setVisible(false);
	            	}
	            }
	        });
	
	    	frame.add(this);
	    	frame.pack();
	    	frame.setVisible(true);
    	}
    }
 
    public static void main(String[] args) {
    	SimpleGraphComponent s = new SimpleGraphComponent(800,800);
        
        s.setForeground(Color.DARK_GRAY);
        s.setBackground(new Color(0.95f,0.95f,0.95f));
        s.setThickness(4);
        s.setMarkerSize(20);
        s.setHalfAxis(false);
        s.setTitle("Title");
        s.setYLabel("Y Label");
        s.setXLabel("X Label");
        s.repaint();
        
    }
}