package com.capgemini.gameOfLife;

public class Coordinate {
	/**
	 * Global limit for all Coordinates. Initialized lets to check if
	 * Coordinates are within rectangle area including Coordinate(0,0) and
	 * excluding upperLimit.
	 */
	private static Coordinate upperLimit = null;

	/**
	 * Horizontal position.
	 */
	protected Integer x;

	/**
	 * Vertical position.
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

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	/**
	 * Assigns <b>this</b> position <b>another</b> position.
	 * 
	 * @param another
	 */
	public static void setUpperLimit(Coordinate another) {
		upperLimit = new Coordinate(another);
	}

	/**
	 * @return Coordinate shifted by <b>shiftX</b> horizontally and by
	 *         <b>shiftY</b> vertically.
	 */
	public Coordinate newShifted(Integer shiftX, Integer shiftY) {
		return new Coordinate(x + shiftX, y + shiftY);
	}

	/**
	 * @return true if both {@link #x} and {@link #y} are positive values
	 *         smaller than specified by {@link #upperLimit}.
	 * @throws Exception
	 *             if upper limit was not initialized (e.g. in GameBoard
	 *             constructor).
	 */
	public boolean isValidToLimits() throws Exception {
		if (Coordinate.upperLimit == null) {
			throw new Exception("Upper limit was not initialised.");
		}
		if (isNegative() || hasEqualXOrY(Coordinate.upperLimit)) {
			return false;
		}
		return true;
	}

	public boolean isNegative() {
		if (x < 0 || y < 0) {
			return true;
		}
		return false;
	}

	/**
	 * @return true if this Coordinate is in the same row or column that another
	 *         is.
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
