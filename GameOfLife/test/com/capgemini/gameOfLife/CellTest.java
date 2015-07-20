package com.capgemini.gameOfLife;

import static org.junit.Assert.*;

import org.junit.Test;

public class CellTest {

	@Test
	public void cellHas0Neighbors() {
		Coordinate.setUpperLimit(new Coordinate(1, 1));
		Cell cell = new Cell(new Coordinate(0, 0));
		assertEquals(0, cell.countNeighbors());
	}
	
	@Test
	public void cellHas3Neighbors() {
		Coordinate.setUpperLimit(new Coordinate(2, 2));
		Cell cell00 = new Cell(new Coordinate(0, 0));
		Cell cell01 = new Cell(new Coordinate(0, 1));
		Cell cell10 = new Cell(new Coordinate(1, 0));
		Cell cell11 = new Cell(new Coordinate(1, 1));
		assertEquals(3, cell00.countNeighbors());
		assertEquals(3, cell01.countNeighbors());
		assertEquals(3, cell10.countNeighbors());
		assertEquals(3, cell11.countNeighbors());
	}

	@Test
	public void cellHas5Neighbors() {
		Coordinate.setUpperLimit(new Coordinate(2, 3));
		Cell cell01 = new Cell(new Coordinate(0, 1));
		Cell cell11 = new Cell(new Coordinate(1, 1));
		assertEquals(5, cell01.countNeighbors());
		assertEquals(5, cell11.countNeighbors());
	}
	
	@Test
	public void cellHas8Neighbors() {
		Coordinate.setUpperLimit(new Coordinate(3, 3));
		Cell cell11 = new Cell(new Coordinate(1, 1));
		assertEquals(8, cell11.countNeighbors());
	}

	@Test
	public void cellIsRevived() {
		Coordinate.setUpperLimit(new Coordinate(1, 1));
		Cell cell = new Cell(new Coordinate(0, 0));
		cell.setNextState(CellState.ALIVE);
		cell.nextGeneration();
		assertTrue(cell.isAlive());
	}

	@Test
	public void cellIsKilled() {
		Coordinate.setUpperLimit(new Coordinate(1, 1));
		Cell cell = new Cell(new Coordinate(0, 0));
		cell.setNextState(CellState.ALIVE);
		cell.nextGeneration();
		cell.setNextState(CellState.DEAD);
		cell.nextGeneration();
		assertFalse(cell.isAlive());
	}
}
