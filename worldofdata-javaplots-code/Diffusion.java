import java.util.Random;

import worldofdata.javaplots.SimpleHist;

public class Diffusion {

	public static void main(String[] args) {
		int n = 1000;
		double [] x = new double[n];
		Random random = new Random();
		for(int i=0;i<n;i++) x[i]=5.0;
		
		SimpleHist s = new SimpleHist();
		s.setPlotBounds(0, 10, 0, 1000);
		s.setBorders(50, 10, 50, 50);
		s.setPreferredNumBins(30);
		for(int iter=0;iter<10000;iter++) {
			for(int i=0;i<n;i++) {
				x[i] += 0.1*random.nextGaussian();
			}
			s.setData(x,SimpleHist.Style.HOLLOW);
			s.repaint();
			s.sleep(10);
		}

	}

}
