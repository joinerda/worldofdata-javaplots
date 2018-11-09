import worldofdata.javaplots.SimpleGrid;

public class SimpleModel2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n=25;
		int [][] cells = new int[n][n];
		int [][] swap = new int[n][n];
		
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				cells[i][j] = (int)(100.0*Math.random());
			}
		}
		SimpleGrid s = new SimpleGrid();
		s.setCmapRange(0, 100);
		
		for(int iter=0;iter<100;iter++) {
			for(int i=1;i<n-1;i++) {
				for(int j=1;j<n-1;j++) {
					swap[i][j] = (int)(0.25*(double)(
							cells[i+1][j]+
							cells[i-1][j]+
							cells[i][j+1]+
							cells[i][j-1]));
				}
			}
			for(int i=1;i<n-1;i++) {
				for(int j=1;j<n-1;j++) {
					cells[i][j]=swap[i][j];
				}
			}
			s.setData(cells);
			s.repaint();
			s.sleep(100);
		}
	}
}
