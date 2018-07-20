package sudoku;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Sudoku {
	public Point[][] points;
	public Palace[][] palaces;

	public Sudoku(int[][] mat) {
		points = new Point[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				points[i][j] = new Point();
				if (mat[i][j] != 0) {
					points[i][j].set(String.valueOf(mat[i][j]));
				}
			}
		}

		palaces = new Palace[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				palaces[i][j] = new Palace(this, i, j);
			}
		}
	}

	public Sudoku(Sudoku sudoku) {
		points = new Point[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				points[i][j] = new Point();
				String v = sudoku.points[i][j].get();
				if (v != null) {
					points[i][j].set(v);
				}
			}
		}

		palaces = new Palace[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				palaces[i][j] = new Palace(this, i, j);
			}
		}
	}

	public void go() {
		refresh0();

		for (int i = 0; i < 9 * 9 * 9; i++) {
			while (play11()) {
				refresh0();
			}
			while (play12()) {
				refresh0();
			}
			while (play2()) {
				refresh0();
			}
		}
	}

	public void refresh0() {
		while (true) {
			boolean b = false;
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					String value = points[i][j].get();
					if (value != null) {
						// in same line&column
						for (int k = 0; k < 9; k++) {
							if (points[k][j].rm(value)) {
								b = true;
							}
							if (points[i][k].rm(value)) {
								b = true;
							}
						}

						// in same palace
						if (palaces[i / 3][j / 3].rm(value)) {
							b = true;
						}
					}
				}
			}
			if (!b) {
				break;
			}
		}
	}

	// line-palace check
	public boolean play11() {
		for (int i = 0; i < 9; i++) {
			for (int kk = 0; kk < 9; kk++) {
				List<Integer> yy = new ArrayList<>();
				String v = String.valueOf(kk + 1);
				for (int y = 0; y < 9; y++) {
					if (!points[i][y].ok() && points[i][y].values.contains(v)) {
						yy.add(y);
					}
				}

				if (yy.size() == 1) {
					points[i][yy.get(0)].set(v);
					return true;
				} else if (yy.size() > 1) {
					int min = yy.get(0);
					int max = yy.get(yy.size() - 1);
					if ((max < 3) || (min >= 3 && max < 6) || (min >= 6)) {
						Palace palace = palaces[i / 3][min / 3];
						int tx = i % 3;
						for (int p = 0; p < 3; p++) {
							for (int q = 0; q < 3; q++) {
								if (p != tx && palace.points[p][q].rm(v)) {
									return true;
								}
							}
						}
					}
				}
			}
		}

		return false;
	}

	// column-palace check
	public boolean play12() {
		for (int j = 0; j < 9; j++) {
			for (int kk = 0; kk < 9; kk++) {
				List<Integer> xx = new ArrayList<>();
				String v = String.valueOf(kk + 1);
				for (int x = 0; x < 9; x++) {
					if (!points[x][j].ok() && points[x][j].values.contains(v)) {
						xx.add(x);
					}
				}

				if (xx.size() == 1) {
					points[xx.get(0)][j].set(v);
					return true;
				} else if (xx.size() > 1) {
					int min = xx.get(0);
					int max = xx.get(xx.size() - 1);
					Palace palace = palaces[min / 3][j / 3];
					if ((max < 3) || (min >= 3 && max < 6) || (min >= 6)) {
						int ty = j % 3;
						for (int p = 0; p < 3; p++) {
							for (int q = 0; q < 3; q++) {
								if (q != ty && palace.points[p][q].rm(v)) {
									return true;
								}
							}
						}
					}
				}
			}
		}

		return false;
	}

	// palace-line check
	public boolean play2() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Palace palace = palaces[i][j];
				for (int vv = 0; vv < 9; vv++) {
					String value = String.valueOf(vv);
					List<Integer> xx = new ArrayList<>();
					List<Integer> yy = new ArrayList<>();
					for (int p = 0; p < 3; p++) {
						for (int q = 0; q < 3; q++) {
							Point point = palace.points[p][q];
							if (!point.ok() && point.values.contains(value)) {
								xx.add(p);
								yy.add(q);
							}
						}
					}

					int size = xx.size();
					if (size == 1) {
						palace.points[xx.get(0)][yy.get(0)].set(value);
						return true;
					} else if (size == 2 || size == 3) {
						if (allSame(xx)) {
							int ii = 3 * i + xx.get(0);
							for (int jj = 0; jj < 9; jj++) {
								if ((jj < 3 * j || jj > 3 * j + 2) && points[ii][jj].rm(value)) {
									return true;
								}
							}
						} else if (allSame(yy)) {
							int jj = 3 * j + yy.get(0);
							for (int ii = 0; ii < 9; ii++) {
								if ((ii < 3 * i || ii > 3 * i + 2) && points[ii][jj].rm(value)) {
									return true;
								}
							}
						}
					}
				}
			}
		}

		return false;
	}

	private static boolean allSame(List<Integer> xx) {
		int p = xx.get(0);
		for (int i = 1; i < xx.size(); i++) {
			if (xx.get(i) != p) {
				return false;
			}
		}
		return true;
	}

	public void go2() {
		if (ok()) {
			print();
			return;
		}

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (points[i][j].values.size() == 2) {
					Sudoku sudoku1 = new Sudoku(this);
					sudoku1.points[i][j].set(points[i][j].values.get(0));
					sudoku1.go();
					if (sudoku1.ok()) {
						sudoku1.print();
						return;
					}

					Sudoku sudoku2 = new Sudoku(this);
					sudoku2.points[i][j].set(points[i][j].values.get(1));
					sudoku2.go();
					if (sudoku2.ok()) {
						sudoku2.print();
						return;
					}
				}
			}
		}

		print();
		print0();
	}

	public boolean ok() {
		for (int i = 0; i < 9; i++) {
			Set<String> set = new HashSet<>();
			for (int j = 0; j < 9; j++) {
				if (!points[i][j].ok()) {
					return false;
				}
				set.add(points[i][j].get());
			}
			if (set.size() < 9) {
				return false;
			}

			set.clear();
			for (int j = 0; j < 9; j++) {
				if (!points[j][i].ok()) {
					return false;
				}
				set.add(points[j][i].get());
			}
			if (set.size() < 9) {
				return false;
			}
		}
		return true;
	}

	public void print() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				String v = points[i][j].get();
				System.out.print((v == null ? "-" : v) + " ");
			}
			System.out.println();
		}
	}

	public void print0() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.println((i + 1) + "," + (j + 1) + ": " + points[i][j]);
			}
		}
	}
}
