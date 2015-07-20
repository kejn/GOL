package com.capgemini.gameOfLife;

import java.util.HashMap;
import java.util.Map;

public class GameBoard {
	protected Map<Coordinate, Cell> cellMap;
	public final Coordinate DIMENSIONS;

	public GameBoard(Coordinate newDimensions) {
		DIMENSIONS = new Coordinate(newDimensions);
		Coordinate.setUpperLimit(DIMENSIONS);
		this.cellMap = new HashMap<Coordinate, Cell>();
		initCells();
	}

	/**
	 * Creates all cells to fill the GameBoard by default initializing them to
	 * be dead.
	 */
	protected void initCells() {
		for (int i = 0; i < DIMENSIONS.getX(); ++i) {
			for (int j = 0; j < DIMENSIONS.getY(); ++j) {
				Coordinate coord = new Coordinate(i, j);
				cellMap.put(coord, new Cell(coord));
			}
		}
	}

	/**
	 * @return Number of alive <b>cell</b>'s neighbors.
	 */
	protected int countNeighborsAlive(Cell cell) {
		int alive = 0;
		for (Coordinate neighbor : cell.getNeighborCells()) {
			if (getCell(neighbor).isAlive()) {
				++alive;
			}
		}
		return alive;
	}

	/**
	 * @return <b>Cell</b> at given <b>position</b> in GameBoard.
	 */
	public Cell getCell(Coordinate position) {
		return cellMap.get(position);
	}

	/**
	 * @return <b>Cell</b> at position in GameBoard given by <b>posX</b>
	 *         (horizontal coordinate) and <b>posY</b> (vertical coordinate).
	 */
	public Cell getCell(Integer posX, Integer posY) {
		return cellMap.get(new Coordinate(posX, posY));
	}

	/**
	 * @param position
	 *            target Cell position on GameBoard
	 * @param state
	 *            to be set; possible states are described by CellState enum.
	 * @param inNextGeneration
	 *            if set to <b>true</b> the change of state won't be visible
	 *            until next generation; otherwise the change is applied
	 *            immediately.
	 */
	public void setCellState(Coordinate position, CellState state, boolean inNextGeneration) {
		Cell cell = getCell(position);
		if (cell.getState().equals(state)) {
			return;
		}
		cell.setNextState(state);
		if (!inNextGeneration) {
			cell.nextGeneration();
		}
		cellMap.put(position, cell);
	}

	/**
	 * Determines the next state of cells and proceeds to next generation.
	 */
	public void nextGeneration() {
		for (Map.Entry<Coordinate, Cell> entry : cellMap.entrySet()) {
			determineStateInNextGeneration(entry.getValue());
		}

		for (Map.Entry<Coordinate, Cell> entry : cellMap.entrySet()) {
			setCellState(entry.getKey(), entry.getValue().getNextState(), false);
		}
	}

	/**
	 * Determines the cell state in next generation.
	 * </p>
	 * RULE 1: If <b>cell</b> is alive and has lees than 2 or more than 3 living
	 * neighbors it will be killed.
	 * </p>
	 * RULE 2: If <b>cell</b> is dead and has exactly 3 living neighbors it will
	 * be revived.
	 * 
	 * @param cell
	 */
	protected void determineStateInNextGeneration(Cell cell) {
		Coordinate coord = cell.getPosition();
		int alive = countNeighborsAlive(cell);

		if (cell.isAlive() && (alive < 2 || alive > 3)) {
			setCellState(coord, CellState.DEAD, true);
		} else if (!cell.isAlive() && alive == 3) {
			setCellState(coord, CellState.ALIVE, true);
		}
	}

}
