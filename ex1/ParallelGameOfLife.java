package ex1;

public class ParallelGameOfLife implements GameOfLife {

	public boolean[][][] invoke(boolean[][] initalField, int hSplit, int vSplit,
			int generations) {
		// TODO Auto-generated method stub
		// testing git
		return null;
	}


	private int numNeighbors(int x,int y, boolean[][] field ){
		int counter=(field[x][y]?-1:0);
		for (int i=x-1; i<=x+1;i++ ){
			if (i<0||i>=field.length){ continue ; }
			for (int j=y-1; j<=y+1;j++){
				if (j<0||j>=field[0].length){ continue ; }
				counter+=(field[i][j]?1:0);
			}
		}
		return counter;
	}
}
