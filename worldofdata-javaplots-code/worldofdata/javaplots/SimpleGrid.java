package worldofdata.javaplots;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class SimpleGrid extends SimpleGraphComponent implements MouseListener {
	double [][] cells;
	Color [][] colors;
	double cmax=10.0;
	double cmin=0.0;
	Color belowRange=null;
	Color aboveRange=null;
	Color minColor=Color.BLACK;
	Color maxColor=Color.WHITE;
	Color foreground=Color.BLACK;
	boolean cellClicked = false;
	int clickI = 0;
	int clickJ = 0;
    boolean drawGrid=true;
    
    public void setBlank(int nx, int ny) {
    	cells = new double[nx][ny];
    	colors = new Color[nx][ny];
    }
    
    public void setDrawGrid(boolean drawGrid) {
    	this.drawGrid = drawGrid;
    }
    

	
	
	public int [] getClick() {
		if(cellClicked) {
			cellClicked = false;
			int [] retval = {clickI, clickJ};
			return retval;
		} else return null;
	}
	
	@Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        cellClicked=true;
        clickI=(int)((double)x/getWidth()*cells.length);
        clickJ=(int)((double)y/getHeight()*cells[0].length);
    }
 
    @Override
    public void mouseEntered(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
    }
 
    @Override
    public void mouseExited(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
    }
 
    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
    }
 
    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
    }
	public void setMinColor(Color minColor) {
		this.minColor = minColor;
	}
	public void setMaxColor(Color maxColor) {
		this.maxColor = maxColor;
	}
	public void setBelowRange(Color belowRange) {
		this.belowRange = belowRange;
	}
	public void setAboveRange(Color aboveRange) {
		this.aboveRange = aboveRange;
	}
	public void setCmapRange(double min, double max) {
		cmin=min;
		cmax=max;
	}
	
    
    
    public void addData(int i, int j, Color col) {
    	colors[i][j]=col;
    }
    
    public void setData(int [][] cells) {
    	int ncols = cells.length;
    	int nrows = cells[0].length;
    	this.cells = new double[ncols][nrows];
    	colors = new Color[ncols][nrows];
    	for(int i=0;i<ncols;i++) {
    		for(int j=0;j<nrows;j++) {
    			this.cells[i][j]=(double)cells[i][j];
    		}
    	}
    }
    
    public void setData(double [][]cells) {
    	this.cells=cells;
    }

            
    public void paint(Graphics g) {
    	// divide space up into cells, draw borders, fill cells
    	
    	super.paint(g);
    	int width = getWidth();
    	int height = getHeight();
    	
    	int offsetX=0;
    	int offsetY=0;
    	if(showAxis) {
    		offsetX=borderLeft;
    		offsetY=borderTop;
    		width-=borderRight+borderLeft+1;
    		height-=borderBottom+borderTop+1;
    	}
    	
    	int ncols = cells.length;
    	int nrows = cells[0].length;
    	int ix = offsetX+(int)((double)(0)/ncols*width);
    	for(int i=0;i<ncols;i++) {
			int ixp1 = offsetX+(int)((double)(i+1)/ncols*width);
    		int jy = offsetY+(int)((double)(0)/nrows*height);
    		for(int j=0;j<nrows;j++) {
    			int jyp1 = offsetY+(int)((double)(j+1)/nrows*height);

    			if(colors==null) {
    				g.setColor(cMap(cells[i][j]));
    			} else if(colors[i][j]==null) {
    				g.setColor(cMap(cells[i][j]));
    			} else {
    				g.setColor(colors[i][j]);
    			}
    			
    			g.fillRect(ix,jy, ixp1-ix,
    					jyp1-jy);
    			if(drawGrid) {
	    			g.setColor(foreground);
	    			g.drawRect(ix,jy, ixp1-ix,
	    					jyp1-jy);
    			}
    			jy=jyp1;
    		}
    		ix=ixp1;
    	}
    	g.setClip(null);

    }
    
    Color cMap(double x) {
    	float cval=0.0f;
    	if(x<cmin) cval= -1.0f;
    	else if(x>cmax) cval =2.0f;
    	else {
    		cval=(float)(x-cmin)/(float)(cmax-cmin);
    	}
    	

    	if(cval<0.0) if( belowRange!=null) {
    		return belowRange;
    	} else {
    		cval=0.0f;
    	}
    	if(cval>1.0) if (aboveRange!=null) {
    		return aboveRange;
    	}else {
    		cval=1.0f;
    	}
    	cval = Math.min(1.0f, cval);
    	cval = Math.max(0.0f, cval);
    	float red = minColor.getRed()/255.0f+cval*(maxColor.getRed()-minColor.getRed())/255.f;
    	float green = minColor.getGreen()/255.0f+cval*(maxColor.getGreen()-minColor.getGreen())/255.f;
    	float blue = minColor.getBlue()/255.0f+cval*(maxColor.getBlue()-minColor.getBlue())/255.f;
    	return new Color(red,green,blue);
    }
    
    
    
    public SimpleGrid(boolean addFrame) {
    	super(addFrame);
    	addMouseListener(this);
    	showAxis = false;

    }
    
    public SimpleGrid() {
    	super();
    	addMouseListener(this);
    	showAxis = false;

    }
    public SimpleGrid(int w, int h) {
    	super(w,h);
    	addMouseListener(this);
    	showAxis = false;

    }
      
    public SimpleGrid(int w,int h,boolean addFrame) {
    	super(w,h,addFrame);
    	addMouseListener(this);
    	showAxis = false;
    }
 
    public static void main(String[] args) {
        int [][]x = {{0,1,2},{3,4,5},{6,7,8}};
        SimpleGrid s = new SimpleGrid();
        s.setCmapRange(0, 8);
        s.setData(x);
        while(true) {
            s.repaint(); 
            s.sleep(100);
            int [] coords = s.getClick();
			if(coords!=null) {
				System.out.println("Click "+coords[0]+" "+coords[1]);
			}
        }
    }
}