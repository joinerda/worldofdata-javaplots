import java.util.Arrays;
import java.util.Random;

import worldofdata.javaplots.SimpleHist;
import worldofdata.javaplots.SimplePlot;

public class MCMC {
	
	public static double F(double x) {
		return (x*x*x-x);
	}
	
	public static double mean(double [] x) {
		double sum=0;
		for(int i=0;i<x.length;i++) {
			sum += x[i];
		}
		return sum/x.length;
	}

	public static void main(String[] args) {
		int n=10000;
		double [] x = new double[n];
		double [] val = new double[n];
		double [] index = new double[n];
		Random random = new Random();
		double Temp=.010;
		
		x[0]=1.0;
		val[0] = F(x[0]);
		for(int i=1;i<n;i++) {
			index[i]=i;
			double step = 1.0*random.nextGaussian();
			double probe = x[i-1]+step;
			double val1 = F(probe);
			double deltaE = val1*val1-val[i-1]*val[i-1];
			if(deltaE<0.0) {
				x[i]=probe;
				val[i]=val1;
			} else if(random.nextDouble()<Math.exp(-deltaE/
					Temp)) {
				x[i]=probe;
				val[i]=val1;
			} else {
				x[i]=x[i-1];
				val[i]=val[i-1];
			}
		}
		//x = Arrays.copyOfRange(x, n/2, n);
		//val = Arrays.copyOfRange(val, n/2, n);
		
		SimplePlot s = new SimplePlot(800,800);
		s.setData(index, x);
		s.setPlotBounds(0, n, -10, 10);
		s.repaint();
		

		
		SimpleHist hist = new SimpleHist();
		hist.setPreferredNumBins(n/50);
		hist.setPlotBounds(x);
		hist.setData(x);
		//hist.addData(val,SimpleHist.Style.HOLLOW);
		hist.repaint();
				
		
		System.out.println(mean(x)+" "+F(mean(x)));

	}

}
