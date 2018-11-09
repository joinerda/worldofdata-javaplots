import java.awt.Color;

import worldofdata.javaplots.SimpleGrid;

public class EightQueens {
	
	public static int totalFound=0;
	public static SimpleGrid s;
	
	public static int [] getClears(int [] board, int level) {
		int n = board.length;
		int [] storage = new int[n];
		if(level==1) {
			for(int i=0;i<n;i++) {
				storage[i]=i;
			}
			return storage;
		}
		else {
			int sum=0;
			for(int i=0;i<n;i++) {
				boolean clear=true;
				for(int l=level-1;l>=1;l--) {
					if(i==board[l-1]) {
						clear=false;
					}else if(i==board[l-1]+(l-level)) {
						clear=false;
					}else if(i==board[l-1]-(l-level)) {
						clear=false;
					}
					if(!clear) break;
				}
				if(clear) {
					storage[sum++]=i;
				}
			}
			if(sum==0) return null;
			int [] returnValues = new int[sum];
			for(int i=0;i<sum;i++) {
				returnValues[i]=storage[i];
			}
			return returnValues;
		}
	}
	
	public static void drawBoard(int [] a) {
		if(a==null) {
			System.out.println("NULL");
		} else {
			s.setBlank(a.length, a.length);
			for(int i=0;i<a.length;i++) {
				for(int j=0;j<a.length;j++) {
					if((i+j)%2==0) {
						s.addData(i, j, Color.GRAY);
					} else {
						s.addData(i, j, Color.WHITE);
					}
				}
			}
			for(int i=0;i<a.length;i++) {
				s.addData(a[i], i, Color.GREEN);
			}
			s.repaint();
			s.sleep(pause);

		}
	}
	
	public static void printArray(int [] a) {
		if(a==null) {
			System.out.println("NULL");
		} else {
			int n=a.length;
			for(int i=0;i<n;i++) {
				System.out.print(a[i]+ " ");
			}
			System.out.println();			
		}
	}
	
	public static void addQueen(int [] board, int level) {
		// determine clear spaces on this level.
		// if last level, for each clear space on the
		//   level post a solution.
		// if if not last level, loop over clear spaces
		//   recurse one level down
		
	
		int n=board.length;
		for(int i=level-1;i<n;i++) {
			board[i]=-1;
		}
		int [] clears = getClears(board,level);

		if(clears==null) {
			return;
		}
		
		for(int i=0;i<clears.length;i++) {
			board[level-1]=clears[i];
			if(level==n) {
				//printArray(board);
				totalFound++;
				drawBoard(board);
			} else {
				addQueen(board,level+1);
			}
		}
	}
	
	public static int pause=1000;
	public static void main(String[] args) {
		int n=8;
		int [] board = new int[n];
		s = new SimpleGrid();
		
		addQueen(board,1);
		System.out.println("Total Found "+totalFound);
	}

}
