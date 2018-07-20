package sudoku;

public class App {

	public static void main(String[] args) {
		int[][] matd = new int[][] {
			{ 0, 0, 0, 1, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 2, 7, 0, 0, 0 },
			{ 4, 0, 6, 0, 0, 0, 1, 0, 9 },
			{ 0, 0, 0, 0, 0, 0, 9, 2, 5 },
			{ 6, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 3, 0, 0, 8, 1, 0, 0, 0, 0 },
			{ 7, 0, 0, 0, 4, 0, 3, 0, 0 },
			{ 0, 0, 4, 0, 0, 0, 0, 0, 7 },
			{ 0, 8, 5, 0, 9, 0, 0, 0, 4 }
		};
		Sudoku sudoku = new Sudoku(matd);
		sudoku.go();
		sudoku.go2();
	}

}
