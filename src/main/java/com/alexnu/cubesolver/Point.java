package com.alexnu.cubesolver;

public class Point {

	Integer x;
	Integer y;
	Integer z;

	public Point(Integer x, Integer y, Integer z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Point point = (Point) o;

		if (!x.equals(point.x)) return false;
		if (!y.equals(point.y)) return false;
		return z.equals(point.z);

	}

	@Override
	public int hashCode() {
		int result = x.hashCode();
		result = 31 * result + y.hashCode();
		result = 31 * result + z.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + "," + z + ")";
	}
}