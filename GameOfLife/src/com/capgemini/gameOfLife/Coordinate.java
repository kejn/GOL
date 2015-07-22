package com.capgemini.gameOfLife;

public class Coordinate {
	/**
	 * Global limit for all Coordinates. Initialized lets to check if
	 * Coordinates are within rectangle area including Coordinate(0,0) and
	 * excluding upperLimit.
	 */
	private static Coordinate upperLimit = null;

	/**
	 * Row.
	 */
	protected Integer x;

	/**
	 * Column.
	 */
	protected Integer y;

	public Coordinate(Integer newX, Integer newY) {
		x = newX;
		y = newY;
	}

	public Coordinate(Coordinate newPosition) {
		x = newPosition.getX();
		y = newPosition.getY();
	}

	/**
	 * @return Row.
	 */
	public Integer getX() {
		return x;
	}

	/**
	 * @return Column.
	 */
	public Integer getY() {
		return y;
	}

	/**
	 * Assigns <b>this</b> position <b>another</b> position.
	 */
	public static void setUpperLimit(Coordinate another) {
		upperLimit = new Coordinate(another);
	}

	public static Coordinate random() {
		int x = (int) (Math.random() * upperLimit.getX());
		int y = (int) (Math.random() * upperLimit.getY());
		return new Coordinate(x, y);
	}

	/**
	 * @return Coordinate shifted by <b>shiftX</b> rows and by <b>shiftY</b>
	 *         columns.
	 */
	public Coordinate newShifted(Integer shiftX, Integer shiftY) {
		return new Coordinate(x + shiftX, y + shiftY);
	}

	/**
	 * @return <b>this</b> if both {@link #x} and {@link #y} are positive values
	 *         smaller than specified by {@link #upperLimit}.
	 * @throws Exception
	 *             if upper limit was not initialized (e.g. in GameBoard
	 *             constructor).
	 * @throws NullPointerException
	 *             if <b>this</b> {@link #isNegative()} or
	 *             {@link #hasEqualXOrY(Coordinate)} to {@link #upperLimit}.
	 */
	public Coordinate validateToLimits() throws Exception, NullPointerException {
		if (Coordinate.upperLimit == null) {
			throw new Exception("Upper limit was not initialised.");
		}
		if (isNegative() || hasEqualXOrY(Coordinate.upperLimit)) {
			throw new NullPointerException("Invalid to limits.");
		}
		return this;
	}

	public boolean isNegative() {
		if (x < 0 || y < 0) {
			return true;
		}
		return false;
	}

	/**
	 * @return true if this Coordinate is in the same row or column that another
	 *         Coordinate is.
	 */
	public boolean hasEqualXOrY(Coordinate another) {
		if (x == another.getX() || y == another.getY()) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "(" + x + "; " + y + ")";
	}

	@Override
	public boolean equals(Object object) {
		Coordinate pos = (Coordinate) object;
		if (x.equals(pos.getX()) && y.equals(pos.getY())) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return x * upperLimit.getX() + y;
	}
}
