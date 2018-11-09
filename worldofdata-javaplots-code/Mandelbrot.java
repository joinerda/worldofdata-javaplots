import java.awt.Color;

import worldofdata.javaplots.SimpleGrid;

public class Mandelbrot {

	public static double [] linspace(double min, double max,
			int n) {
		double [] rv = new double[n];
		double h = (max-min)/(n-1);
		rv[0]=min;
		for(int i=1;i<n-1;i++) 
			rv[i]=min+i*h;
		rv[n-1]=max;
		return rv;
	}
	
	public static double [][]calcPixels(double [] px,
			double [] py,int depth) {
		double [][] pixels = new double[px.length][py.length];
		for(int i=0;i<px.length;i++) {
			for(int j=0;j<py.length;j++) {
				double cx = px[i];
				double cy = py[j];
				double x = 0.0;
				double y = 0.0;
				int k=0;
				for(k=0;k<depth;k++) {
					double t = x*x-y*y+cx;
					y = 2*x*y + cy;
					x = t;
					if(x*x+y*y>5.0) {
						break;
					}
				}
				pixels[i][j]=Math.log((double)k);
			}
		}
		return pixels;
	}
	
	public static void main(String[] args) {
		int depth=1000;
		int n=400;
		double cx = -0.5;
		double cy = 0.0;
		double size = 3;
		double [] px = linspace(cx-size/2,cx+size/2,n);
		double [] py = linspace(cy+size/2,cy-size/2,n);
		
		double [][] pixels = calcPixels(px,py,depth);

		SimpleGrid s = new SimpleGrid(n,n);
		s.setCmapRange(0, Math.log(depth-1.0));
		s.setDrawGrid(false);
		s.setMinColor(Color.GREEN);
		s.setMaxColor(Color.BLUE);
		s.setAboveRange(Color.BLACK);
		s.setData(pixels);
		s.repaint();

		while(true) {
			s.sleep(100);
			int [] clicked = s.getClick();
			if(clicked!=null) {
				System.out.println("CLICK ON "+px[clicked[0]]+
						" "+py[clicked[1]]);
				cx = px[clicked[0]];
				cy = py[clicked[1]];
				int nx = s.getWidth();
				int ny = s.getHeight();
				size=size/2;
				
				px = linspace(cx-size/2,cx+size/2,nx);
				py = linspace(cy+size/2,cy-size/2,ny);
				
				pixels = calcPixels(px,py,depth);
				
				s.setData(pixels);
				s.repaint();			
			}	
		}	
	}
}
