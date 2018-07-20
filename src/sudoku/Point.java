package sudoku;

import java.util.ArrayList;
import java.util.List;

public class Point {

	public List<String> values = new ArrayList<>();

	public Point() {
		for (int i = 1; i <= 9; i++) {
			values.add(String.valueOf(i));
		}
	}

	public void set(String value) {
		values.clear();
		values.add(value);
	}

	public boolean rm(String value) {
		if (values.size() > 1) {
			values.remove(value);
			return ok();
		}
		
		return false;
	}

	public boolean ok() {
		return values.size() == 1;
	}

	public String get() {
		return ok() ? values.get(0) : null;
	}

	@Override
	public String toString() {
		return values.toString().replaceAll("\\s+", "");
	}
}
