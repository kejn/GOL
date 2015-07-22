package com.capgemini.gameOfLife;

import java.util.ArrayList;
import java.util.List;

public class Cell {
	/**
	 * Current state.
	 */
	private CellState state;

	/**
	 * Determined state in next generation.
	 */
	private CellState nextState;

	/**
	 * Cell's position in GameBoard.
	 */
	private Coordinate position;

	/**
	 * List of neighbor Cells in GameBoard. Depending on {@link #position} and
	 * GameBoard dimensions can be empty (if GameBoard consists of 1 Cell) or
	 * contain from 3, 5 or 8 neighbor coordinates (in 2D GameBoard).
	 */
	private final List<Coordinate> neighborCells;

	public Cell(Coordinate newPosition) {
		state = CellState.DEAD;
		nextState = CellState.DEAD;
		position = new Coordinate(newPosition);
		neighborCells = new ArrayList<Coordinate>();
		createNeighborsList();
	}

	public Coordinate getPosition() {
		return position;
	}

	public final List<Coordinate> getNeighborCells() {
		return neighborCells;
	}

	public CellState getState() {
		return state;
	}

	public CellState getNextState() {
		return nextState;
	}

	/**
	 * @return <b>true</b> if cell's state is CellState.ALIVE.
	 */
	public boolean isAlive() {
		return state.isAlive();
	}

	/**
	 * @return Size of neighbor list.
	 */
	public int countNeighbors() {
		return neighborCells.size();
	}

	/**
	 * Assigns state that should be assigned to Cell in next generation.
	 * 
	 * @param cellState
	 *            desired state in next generation.
	 * @return <b>this</b> after changes.
	 */
	public Cell setNextState(CellState cellState) {
		nextState = cellState;
		return this;
	}

	/**
	 * Proceeds to next generation assigning {@link #nextState} as current
	 * {@link #state}.
	 * 
	 * @return <b>this</b> after changes.
	 */
	public Cell nextGeneration() {
		state = nextState;
		return this;
	}

	/**
	 * [!!! 2D GameBoard]
	 * </p>
	 * Adds all valid coordinates of adjacent cells to the neighbor list.
	 */
	private void createNeighborsList() {
		for (int i = -1; i < 2; ++i) {
			for (int j = -1; j < 2; ++j) {
				if (i == 0 && j == 0) {
					continue;
				}
				addNeighborIfHasValidCoordinates(i, j);
			}
		}
	}

	/**
	 * Removes coordinates invalid to GameBoard limits from neighbor list.
	 */
	private void addNeighborIfHasValidCoordinates(int shiftX, int shiftY) {
		try {
			neighborCells.add(position.newShifted(shiftX, shiftY).validateToLimits());
		} catch (NullPointerException e) { // invalid coordinates
		} catch (Exception e) { 		   // uninitialized Coordinate.upperLimit 
			System.err.println(e.getMessage());
		} 
	}
}
