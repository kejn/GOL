package com.capgemini.gameOfLife;

/**
 * Possible states:</p>
 * <ul>
 * <li>DEAD</li>
 * <li>ALIVE</li>
 * </ul>
 */
public enum CellState {
	DEAD(false),
	ALIVE(true);
	
	private boolean isAlive;
	
	private CellState(boolean deadOrAlive) {
		isAlive = deadOrAlive;
	}
	
	/**
	 * Cast CellState to boolean.
	 * @return <b>true</b> if <b>this</b> is CellState.ALIVE  
	 */
	public boolean isAlive() {
		return isAlive;
	}
	
}
