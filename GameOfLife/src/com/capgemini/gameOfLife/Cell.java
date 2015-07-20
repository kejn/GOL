package com.capgemini.gameOfLife;

import java.util.ArrayList;
import java.util.List;

public class Cell {
	private CellState state;
	private CellState nextState;
	private Coordinate position;
	private final List<Coordinate> neighborCells;

	public Cell(Coordinate newPosition) {
		state = CellState.DEAD;
		nextState = CellState.DEAD;
		position = new Coordinate(newPosition);
		neighborCells = new ArrayList<Coordinate>();
		createNeighborsList();
		validateNeighborsList();
	}
	
	public Coordinate getPosition() {
		return position;
	}
	
	public final List<Coordinate> getNeighborCells() {
		return neighborCells;
	}

	public boolean isAlive() {
		return state.isAlive();
	}
	
	public CellState getState() {
		return state;
	}
	public CellState getNextState() {
		return nextState;
	}

	public int countNeighbors() {
		return neighborCells.size();
	}

	public Cell setNextState(CellState cellState) {
		nextState = cellState;
		return this;
	}
	
	public Cell nextGeneration() {
		state = nextState;
		return this;
	}

	private void createNeighborsList() {
		for (int i = -1; i < 2; ++i) {
			for (int j = -1; j < 2; ++j) {
				if (i == 0 && j == 0) {
					continue;
				}
				neighborCells.add(position.newShifted(i, j));
			}
		}
	}

	private void validateNeighborsList() {
		try {
			for (int i = 0; i < neighborCells.size(); ++i) {
				if (!neighborCells.get(i).isValidToLimits()) {
					neighborCells.remove(i);
					--i;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
