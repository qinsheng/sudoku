package sudoku;

public class Palace {
	public Point[][] points;

	public Palace(Sudoku sudoku, int offestx, int offsety) {
		points = new Point[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				points[i][j] = sudoku.points[offestx * 3 + i][offsety * 3 + j];
			}
		}
	}

	public boolean rm(String value) {
		boolean b = false;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (points[i][j].rm(value)) {
					b = true;
				}
			}
		}
		return b;
	}
}
