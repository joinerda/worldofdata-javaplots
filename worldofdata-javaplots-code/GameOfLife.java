import java.awt.Color;

import worldofdata.javaplots.SimpleGrid;

public class GameOfLife {
	
	public static int countNeighbors(int [][]cells,
			int i,int j) {
		int sum=0;
		int ncols = cells.length;
		int nrows = cells[0].length;
		
		for(int l=-1;l<2;l++) {
			for(int m=-1;m<2;m++) {
				if(m!=0||l!=0) {
					if(cells[(i+l+ncols)%ncols]
							[(j+m+nrows)%nrows]>0) 
						sum++;
				}
			}
		}
		return sum;
	}

	public static void main(String[] args) {
		int n=100;
		int [][] cells =new int[n][n];
		int [][] swap = new int[n][n];
		
		//set up initial grid
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				if(Math.random()<0.25) {
					cells[i][j]=1;
				}
			}
		}
		SimpleGrid s = new SimpleGrid(801,801);
		s.setBelowRange(Color.BLACK);
		s.setMinColor(Color.GREEN);
		s.setMaxColor(Color.WHITE);
		s.setCmapRange(1, 10);

		
		for(int iter=0;iter<100000;iter++) {
			//life rules
			for(int i=0;i<n;i++) {
				for(int j=0;j<n;j++) {
					int count = countNeighbors(cells,i,j);
					if(cells[i][j]==0) {
						if(count==3) {
							swap[i][j]=1;
						} else {
							swap[i][j]=0;
						}
					} else {
						if(count==2||count==3) {
							swap[i][j]=cells[i][j]+1;
						} else {
							swap[i][j]=0;
						}
					}
				}
			}
			for(int i=0;i<n;i++) {
				for(int j=0;j<n;j++) {
					cells[i][j]=swap[i][j];
				}
			}
			s.setData(cells);
			s.repaint();
			s.sleep(100);
			int [] coords = s.getClick();
			if(coords!=null) {
				System.out.println("Click "+coords[0]+" "+coords[1]);
			}
		}
		

	}

}
