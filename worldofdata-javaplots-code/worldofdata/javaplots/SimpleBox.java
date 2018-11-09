package worldofdata.javaplots;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.Random;

import javax.swing.*;
 
public class SimpleBox extends SimpleGraphComponent {

	double [][] x= {{1,2,3,4,5,6}};
	Color [] colors = null;
	public enum Style {FILLED, HOLLOW};
	Style [] style = null;


    
	
	
	
	
	

	
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
	



	
	public void setData(double [] x, Color col) {
		setData(x,col,Style.FILLED);
	}
	public void setData(double [] x) {
		setData(x,defaultColors[0],Style.FILLED);
	}
	


	public void setData(double [] x, Style style) {
		setData(x,defaultColors[0],style);

	}
	
	
	public void setData(double [] x, Color col, Style style) {
		this.x = new double[1][];
		this.x[0]=x;
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
	

	


	
	public void addData(int [] x, 
			Color col, Style style) {
		
		double [] xd = new double[x.length];
		for(int i=0;i<x.length;i++) {
			xd[i] = (double)x[i];
		}
		addData(xd,col,style);
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
		int nplots= this.x.length;
		double [][] newX = new double[nplots+1][];
		Color [] newC = new Color[nplots+1];
		Style [] newS = new Style[nplots+1];
		for(int i=0;i<nplots;i++) {
			newX[i]=this.x[i];
			newC[i]=this.colors[i];
			newS[i]=this.style[i];
		}
		this.x = newX;
		this.x[nplots]=x;
		this.colors = newC;
		this.colors[nplots]=col;
		this.style = newS;
		this.style[nplots]=style;
        repaint();
	}
	
    
    
    
    
    public void drawBoxPlot(Graphics g,  
    		double [] x, int px,
    		Style style) {
    	
    	int boxWidth=30;
    	Arrays.sort(x);
    	double min = x[0];
    	double q1 = x[x.length/4];
    	double med = x[x.length/2];
    	double q3 = x[3*x.length/4];
    	double max = x[x.length-1];
    	int ol1=-1;
    	int sl1=-1;

    	int ol3=x.length;
    	int sl3=x.length;
    	
    	for(int i=0;i<x.length/4;i++) {
    		if(x[i]<q1-3.0*(q3-q1)) {
    			min=x[i+1];
    			ol1=i;
    		} else if(x[i]<q1-1.5*(q3-q1)) {
    			min=x[i+1];
    			sl1=i;
    		}
    	}
    	for(int i=x.length-1;i>3*x.length/4;i--) {
    		if(x[i]>q3+3.0*(q3-q1)) {
    			max=x[i-1];
    			ol3=i;
    		}else if(x[i]>q3+1.5*(q3-q1)) {
    			max=x[i-1];
    			sl3=i;
    		}
    	}
    	
    	int pymin,py1,pymed,py3,pymax;
    	pymin = real2PixelY(min);
    	py1 = real2PixelY(q1);
    	pymed = real2PixelY(med);
    	py3 = real2PixelY(q3);
    	pymax = real2PixelY(max);
    	
    	Color fg  = g.getColor();
    	if(style==Style.HOLLOW) {
    		g.setColor(background);
    		g.fillRect(px-boxWidth/2, py3,boxWidth , py1-py3);
    		g.setColor(fg);
    		drawLine(g,px-boxWidth/2, py1, px+boxWidth/2, py1);
    		drawLine(g,px-boxWidth/2, py3, px+boxWidth/2, py3);
    		drawLine(g,px-boxWidth/2, py1, px-boxWidth/2, py3);
    		drawLine(g,px+boxWidth/2, py1, px+boxWidth/2, py3); 
    	}
    	if(style==Style.FILLED) {
    		g.fillRect(px-boxWidth/2, py3,boxWidth , py1-py3);
    	}
    	g.setColor(foreground);
		drawLine(g,px, py3, px, pymax);
		drawLine(g,px, py1, px, pymin);
		drawLine(g,px-boxWidth/2, pymin, px+boxWidth/2, pymin);
		drawLine(g,px-boxWidth/2, pymax, px+boxWidth/2, pymax);
		
		for(int i=0;i<=ol1;i++) {
			g.fillOval(px-boxWidth/4,real2PixelY(x[i])-boxWidth/4,
					boxWidth/2,boxWidth/2);
		}
		for(int i=x.length-1;i>=ol3;i--) {
			g.fillOval(px-boxWidth/4,real2PixelY(x[i])-boxWidth/4,
					boxWidth/2,boxWidth/2);
		}
		for(int i=ol1+1;i<=sl1;i++) {
			g.drawOval(px-boxWidth/4,real2PixelY(x[i])-boxWidth/4,
					boxWidth/2,boxWidth/2);
		}
		for(int i=ol3-1;i>=sl3;i--) {
			g.drawOval(px-boxWidth/4,real2PixelY(x[i])-boxWidth/4,
					boxWidth/2,boxWidth/2);
		}
		
		g.setColor(fg);
    }
            
    public void paint(Graphics g) {
    	super.paint(g);

    	int space = (getWidth()-borderRight-borderLeft)/x.length;
    	for(int i=0;i<x.length;i++) {
    		int px = borderLeft + space/2 + i*space;
    		if(colors==null) {
    			g.setColor(defaultColors[i%defaultColors.length]);
    		} else if (i>=colors.length ){
    			g.setColor(defaultColors[i%defaultColors.length]);
    		} else {
    			g.setColor(colors[i]);
    		}
    		if(style==null) {
        		drawBoxPlot(g,x[i],px,Style.FILLED);
    		} else if(i>=style.length) {
    			drawBoxPlot(g,x[i],px,Style.FILLED);
    		}else {
    			drawBoxPlot(g,x[i],px,style[i]);
    		}
    	}
        g.setClip(null);

    }
    
    
    public SimpleBox() {
    	super();
    	drawXLabels=false;
    }
    
    public SimpleBox(int w, int h) {
    	super(w,h); 
    	drawXLabels=false;

    }
    
    public SimpleBox(boolean addFrame) {
    	super(addFrame);
    	drawXLabels=false;

    }
    
    public SimpleBox(int w, int h, boolean addFrame) {
    	super(w,h,addFrame);
    	drawXLabels=false;
    }
 
    public static void main(String[] args) {
 
    	int n=1000;
        double [] x = new double[n];
        double [] y = new double[n];
        double [] z = new double[2*n];
        Random random = new Random();

        SimpleBox s = new SimpleBox(800,800);
        
        s.setTitle("Boxplot");
        s.setPlotBoundsY(-10, 10);
        
        for(int i=0;i<n;i++) {
        	x[i]=random.nextGaussian();
        	y[i]=0.5*random.nextGaussian()+5.5;
        	z[2*i]=3*random.nextGaussian()-0.5;
        	z[2*i+1]=3*random.nextGaussian()-0.5;

        }
        x[0]=-6.0;
        
        s.setData(x,Style.HOLLOW);
        s.addData(y,Color.BLACK,Style.HOLLOW);
        s.addData(z,Style.FILLED);
        s.repaint();
        
    }
}