package worldofdata.javaplots;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.Random;

import javax.swing.*;
 
public class SimpleHist extends SimpleGraphComponent {

	double [][] x= {{1,2,3,4,5,6}};
	double [][] bins;
	double [][] frequency;
	Color [] colors = null;
	public enum Style {FILLED, HOLLOW, BORDERED};
	Style [] style = null;
	
	int prefN=10;


    boolean plotDensity=false;
    
    public void setPreferredNumBins(int prefN) {
    	this.prefN=prefN;
    }
   
    public void setPlotDensity(boolean plotDensity) {
    	this.plotDensity=plotDensity;
    }
	
	

	
	public void setData(int [] x, Color col, Style style) {
		double [] xd = new double[x.length];
		for(int i=0;i<x.length;i++) {
			xd[i] = (double)x[i];
		}
		setData(xd,col,style);
	}
	
	public void setData(int [] x) {
		setData(x,defaultColors[0],Style.FILLED);
	}
	
	public void setData(int [] x, Style style) {
		setData(x,defaultColors[0],style);
	}
	
	public void setData(int [] x, Color col) {
		setData(x,col,Style.FILLED);
	}
	
	
	public void setData(double [] x, Style style) {
		double [] bins = linspace(xmin,xmax,prefN);
		setData(x,bins,defaultColors[0],style);
	}
	
	public void setData(double [] x, Color col,Style style) {
		double [] bins = linspace(xmin,xmax,prefN);
		setData(x,bins,col,style);
	}
	
	public void setData(double [] x, Color col) {
		setData(x,col,Style.FILLED);
	}
	public void setData(double [] x) {
		setData(x,defaultColors[0],Style.FILLED);
	}
	
	public void setData(double [] x, double [] bins) {
		setData(x,bins,defaultColors[0],Style.FILLED);
	}
	
	public void setData(double [] x, double [] bins, Style style) {
		setData(x,bins, defaultColors[0],style);
	}
	
	public void setData(double [] x,  double [] bins, Color col) {
		setData(x,bins,col,Style.FILLED);
	}
	
	double [] count(double [] x, double [] bins) {
		double [] rv = new double[bins.length-1];
		for(int i=0;i<x.length;i++) {
			for(int j=0;j<bins.length-1;j++) {
				// this would be more efficient as
				//   a binary search
				if(x[i]>bins[j]&&x[i]<=bins[j+1]) {
					rv[j]+=1.0;
					break;
				}
			}
		}
		return rv;
	}
	
	public double [] dataRange(double [] x) {
		double [] range = new double[2];
		range[0]=range[1]=x[0];
		for(int i=1;i<x.length;i++) {
			range[0]=Math.min(range[0], x[i]);
			range[1]=Math.max(range[1], x[i]);
		}
		return range;
	}
	
	public void setData(double [] x, double [] bins, Color col, Style style) {
		
		this.x = new double[1][];
		this.x[0]=x;
		this.bins = new double[1][];
		this.bins[0]=bins;
		this.frequency = new double[1][];
		this.frequency[0] = count(x,bins);
		
		//double [] rangeX = dataRange(x);
		//double [] rangeF = dataRange(this.frequency[0]);
		//setPlotBounds(rangeX[0],rangeX[1],0,rangeF[1]);
		
		this.colors = new Color[1];
		this.colors[0] = col;
		this.style = new Style[1];
		this.style[0] = style;
        repaint();
	}
	
	public void addData(int [] x ) {
		addData(x,
				defaultColors[this.x.length%defaultColors.length],
				Style.FILLED);
	}
	public void addData(int [] x,  Color col) {
		addData(x,
				col,
				Style.FILLED);
	}
	public void addData(int [] x,  Style style) {
		addData(x,
				defaultColors[this.x.length%defaultColors.length],
				style);
	}
	
	public void addData(int [] x, double [] bins, Color col) {
		addData(x,bins,
				col,
				Style.FILLED);
	}
	
	public void addData(int [] x, Color col, Style style) {
		double [] bins = linspace(xmin,xmax,prefN);
		addData(x,bins,
				col,
				style);
	}
	
	public void addData(int [] x, double []bins, Style style) {
		addData(x,bins,
				defaultColors[this.x.length%defaultColors.length],
				style);
	}
	
	public void addData(int [] x, double [] bins,
			Color col, Style style) {
		
		double [] xd = new double[x.length];
		for(int i=0;i<x.length;i++) {
			xd[i] = (double)x[i];
		}
		addData(xd,bins,col,style);
	}
	
	public void addData(double [] x) {
		addData(x,
				defaultColors[this.x.length%defaultColors.length],
				Style.FILLED);
	}
	public void addData(double [] x,  Color col) {
		addData(x,
				col,
				Style.FILLED);
	}
	public void addData(double [] x, Style style) {
		addData(x,
				defaultColors[this.x.length%defaultColors.length],
				style);
	}
	
	public void addData(double [] x,  
			 Color col, Style style) {
		double [] bins = linspace(xmin,xmax,prefN);
		addData(x,bins,col,style);
	}
	
	public void addData(double [] x, double[] bins, Style style) {
		addData(x,bins,
				defaultColors[this.x.length%defaultColors.length],
				style);
	}
	public void addData(double [] x, double [] bins,Color col) {
		addData(x,bins,
				col,
				Style.FILLED);
	}
	

	public void addData(double [] x, double [] bins,
			Color col, Style style) {
		if(this.x==null) {
			setData(x,bins,col,style);
		} else if(this.x.length==0) {
			setData(x,bins,col,style);
		}
		int nplots= this.x.length;
		double [][] newX = new double[nplots+1][];
		double [][] newB = new double[nplots+1][];
		double [][] newF = new double[nplots+1][];
		Color [] newC = new Color[nplots+1];
		Style [] newS = new Style[nplots+1];
		for(int i=0;i<nplots;i++) {
			newX[i]=this.x[i];
			newB[i]=this.bins[i];
			newF[i]=this.frequency[i];
			newC[i]=this.colors[i];
			newS[i]=this.style[i];
		}
		this.x = newX;
		this.x[nplots]=x;
		this.bins = newB;
		this.bins[nplots]=bins;
		this.frequency = newF;
		this.frequency[nplots]=count(x,bins);
		this.colors = newC;
		this.colors[nplots]=col;
		this.style = newS;
		this.style[nplots]=style;
        repaint();
	}
	
   
    
    public void drawBox(Graphics g, int nData, 
    		double [] bins, double [] frequency,
    		Style style) {
    	int px1, py, px2;
    	int py0 = real2PixelY(0.0);
    	if(style==Style.FILLED||style==Style.BORDERED) {
	        px1 = real2PixelX(bins[0]);
	        for(int i=1;i<bins.length;i++) {
	        	px2 = real2PixelX(bins[i]);
	        	if(plotDensity) {
	        		py = real2PixelY (frequency[i-1]/nData);
	        	} else {
	        		py = real2PixelY (frequency[i-1]);
	        	}
	        	g.fillRect(px1, py, px2-px1,py0-py);
	        	px1=px2;
	        }
    	}
    	if(style==Style.HOLLOW||style==Style.BORDERED) {
        	px1 = real2PixelX(bins[0]);
        	Color temp = g.getColor();
        	if(style==Style.BORDERED) g.setColor(foreground);
	        for(int i=1;i<bins.length;i++) {
	        	px2 = real2PixelX(bins[i]);
	        	if(plotDensity) {
	        		py = real2PixelY (frequency[i-1]/nData);
	        	} else {
	        		py = real2PixelY (frequency[i-1]);
	        	}	
	        	drawLine(g,px1, py, px2, py);
	        	drawLine(g,px1, py0, px2, py0);
	        	drawLine(g,px1, py0, px1, py);
	        	drawLine(g,px2, py0, px2, py);
	        	px1=px2;
	        }
	        g.setColor(temp);
    	}
    	
    }
    
    public void setPlotBounds(double [] x) {
    	double [] rangeX = dataRange(x);
    	double [] bins = linspace(rangeX[0],rangeX[1],prefN);
    	double [] frequency = count(x,bins);
    	
    	double [] rangeF = dataRange(frequency);
    	setPlotBounds(rangeX[0],rangeX[1],0,rangeF[1]);	
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
        		drawBox(g,x[i].length,bins[i],frequency[i],Style.FILLED);
    		} else if(i>=style.length) {
    			drawBox(g,x[i].length,bins[i],frequency[i],Style.FILLED);

    		}else {
    			drawBox(g,x[i].length,bins[i],frequency[i],style[i]);
    		}
    	}
        g.setClip(null);
    	
    }
    
    
    public SimpleHist() {
    	super();
    }
    
    public SimpleHist(int w, int h) {
    	super(w,h);
    }
    
    public SimpleHist(boolean addFrame) {
    	super(addFrame);
    }
    
    public SimpleHist(int w, int h, boolean addFrame) {
    	super(w,h,addFrame);
    }
 
    public static void main(String[] args) {
 
    	int n=1000;
        double [] x = new double[n];
        double [] x2 = new double[n];
        double [] x3 = new double[2*n];
        Random random = new Random();

        SimpleHist s = new SimpleHist(800,800);
        double [] bins = linspace(-10,10,41);
        
        s.setTitle("Histogram");
        s.setYLabel("Frequency");
        s.setXLabel("Bins");
        
        for(int iter=0;iter<1000;iter++) {
        	for(int i=0;i<n;i++) {
            	x[i] = 2.0*random.nextGaussian();
            	x2[i] = 3.0*random.nextGaussian()+5.0;
            	x3[2*i] = 1.0*random.nextGaussian()-2.0;
            	x3[2*i+1] = 1.0*random.nextGaussian()-2.0;
            }
        	s.setData(x);
            s.addData(x2,Style.HOLLOW);
            s.addData(x3,bins,new Color(0,190,0,200),Style.BORDERED);
            s.setPlotBounds(-10.0, 10.0, 0.0, 800);
            s.repaint();
            s.sleep(100);
        }
        
    }
}