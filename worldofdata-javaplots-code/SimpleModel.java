import java.awt.Color;

import worldofdata.javaplots.SimplePlot;
import worldofdata.javaplots.SimplePlot.Style;

public class SimpleModel {
	
	public static double [] linspace(double min, double max, int n) {
		double [] retval = new double[n];
		retval[0]=min;
		retval[n-1]=max;
		double step = (max-min)/(n-1);
		for(int i=1;i<n-1;i++) retval[i] = min+i*step;
		return retval;
	}

	public static void main(String[] args) {
		int n=40;
		double [] t = linspace(0,10,n);
		double [] x = new double[n];
		double [] y = new double[n];
		for(int i=0;i<n;i++) x[i] = Math.sin(t[i]);
		for(int i=0;i<n;i++) y[i] = Math.cos(t[i]);
		SimplePlot s = new SimplePlot(800,800);
		s.setPlotBounds(0,10,-5,5);
		s.setTitle("Waves In Motion");
		for(double phase=0.0;phase<100.0;phase+=0.1) {
			for(int i=0;i<n;i++) x[i] = Math.sin(t[i]-phase);
			for(int i=0;i<n;i++) y[i] = Math.cos(t[i]+phase);
			s.setData(t, x, SimplePlot.Style.BOTH);
			s.addData(t, y, Color.GREEN, SimplePlot.Style.MARKER);
			s.repaint();
			s.sleep(25);
		}
		
	}

}
