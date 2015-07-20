package com.capgemini.gameOfLife;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameBoardTest {

	@Test
	public void cellHasNoNeighborsIn1CellGameBoard() {
		GameBoard gameBoard = new GameBoard(new Coordinate(1, 1));
		assertEquals(0, gameBoard.getCell(new Coordinate(0, 0)).countNeighbors());
	}

	@Test
	public void cellIsRevivedAndKilled() {
		GameBoard gameBoard = new GameBoard(new Coordinate(1, 1));
		gameBoard.setCellState(new Coordinate(0, 0), CellState.ALIVE, false);
		gameBoard.nextGeneration();
		assertFalse(gameBoard.getCell(new Coordinate(0, 0)).isAlive());
	}
	
	@Test
	public void cellHavingLessThanOneLivingNeighborDies() {
		GameBoard gameBoard = new GameBoard(new Coordinate(3, 3));
		gameBoard.setCellState(new Coordinate(1, 1), CellState.ALIVE, false);

		gameBoard.nextGeneration();
		assertFalse(gameBoard.getCell(1, 1).isAlive());
	}


	@Test
	public void cellHaving2Or3LivingNeighborLives() {
		GameBoard gameBoard = new GameBoard(new Coordinate(3, 3));
		gameBoard.setCellState(new Coordinate(1, 1), CellState.ALIVE, false);
		gameBoard.setCellState(new Coordinate(1, 2), CellState.ALIVE, false);
		gameBoard.setCellState(new Coordinate(2, 2), CellState.ALIVE, false);
		gameBoard.nextGeneration();
		assertTrue(gameBoard.getCell(1, 1).isAlive());
		assertTrue(gameBoard.getCell(1, 2).isAlive());
		assertTrue(gameBoard.getCell(2, 2).isAlive());
	}

	@Test
	public void cellHavingMoreThan3NeighborsDies() {
		GameBoard gameBoard = new GameBoard(new Coordinate(3, 3));
		gameBoard.setCellState(new Coordinate(0, 2), CellState.ALIVE, false);
		gameBoard.setCellState(new Coordinate(1, 1), CellState.ALIVE, false);
		gameBoard.setCellState(new Coordinate(1, 2), CellState.ALIVE, false);
		gameBoard.setCellState(new Coordinate(2, 1), CellState.ALIVE, false);
		gameBoard.setCellState(new Coordinate(2, 2), CellState.ALIVE, false);
		gameBoard.nextGeneration();
		assertTrue(gameBoard.getCell(0, 2).isAlive());
		assertFalse(gameBoard.getCell(1, 1).isAlive());
		assertFalse(gameBoard.getCell(1, 2).isAlive());
		assertTrue(gameBoard.getCell(2, 1).isAlive());
		assertTrue(gameBoard.getCell(2, 2).isAlive());
	}
	
	@Test
	public void deadCellHaving3NeighborsRevieves() {
		GameBoard gameBoard = new GameBoard(new Coordinate(3, 3));
		gameBoard.setCellState(new Coordinate(0, 2), CellState.ALIVE, false);
		gameBoard.setCellState(new Coordinate(1, 2), CellState.ALIVE, false);
		gameBoard.setCellState(new Coordinate(2, 2), CellState.ALIVE, false);
		gameBoard.nextGeneration();
		assertFalse(gameBoard.getCell(0, 2).isAlive());
		assertTrue(gameBoard.getCell(1, 1).isAlive());
		assertTrue(gameBoard.getCell(1, 2).isAlive());
		assertFalse(gameBoard.getCell(2, 2).isAlive());
	}

	@Test
	public void loopTest2Generations() {
		GameBoard gameBoard = new GameBoard(new Coordinate(3, 3));
		gameBoard.setCellState(new Coordinate(0, 1), CellState.ALIVE, false);
		gameBoard.setCellState(new Coordinate(1, 1), CellState.ALIVE, false);
		gameBoard.setCellState(new Coordinate(2, 1), CellState.ALIVE, false);
		gameBoard.nextGeneration();
		assertFalse(gameBoard.getCell(0, 0).isAlive());
		assertFalse(gameBoard.getCell(0, 1).isAlive());
		assertFalse(gameBoard.getCell(0, 2).isAlive());
		assertFalse(gameBoard.getCell(2, 0).isAlive());
		assertFalse(gameBoard.getCell(2, 1).isAlive());
		assertFalse(gameBoard.getCell(2, 2).isAlive());
		assertTrue(gameBoard.getCell(1, 0).isAlive());
		assertTrue(gameBoard.getCell(1, 1).isAlive());
		assertTrue(gameBoard.getCell(1, 2).isAlive());
		gameBoard.nextGeneration();
		assertFalse(gameBoard.getCell(0, 0).isAlive());
		assertFalse(gameBoard.getCell(1, 0).isAlive());
		assertFalse(gameBoard.getCell(2, 0).isAlive());
		assertFalse(gameBoard.getCell(0, 2).isAlive());
		assertFalse(gameBoard.getCell(1, 2).isAlive());
		assertFalse(gameBoard.getCell(2, 2).isAlive());
		assertTrue(gameBoard.getCell(0, 1).isAlive());
		assertTrue(gameBoard.getCell(1, 1).isAlive());
		assertTrue(gameBoard.getCell(2, 1).isAlive());
	}

}
