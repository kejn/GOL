package com.capgemini.gameOfLife;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

public class GameOfLife extends GameBoard {
	protected static final int refreshTimeMs = 50;
	private final String shellTitle;
	private final int _20PercentOfGameBoard;
	private static Display display = new Display();
	private static Shell shell = new Shell(display);
	private static Monitor primaryMonitor = display.getPrimaryMonitor();

	private Shell dialogShell;

	private GridLayout shellLayout;
	private RowLayout navLayout;
	private GridLayout cellLayout;

	private Composite navBar;
	private Composite cellArea;

	private Button bRandom;
	private Button bNext;
	private Button bLoop;

	private Label separator;

	private Map<Coordinate, CellLabel> cellLabelMap;

	public static Device getDisplay() {
		return display;
	}

	public GameOfLife(Coordinate newDimensions) {
		super(newDimensions);
		_20PercentOfGameBoard = DIMENSIONS.getX() * DIMENSIONS.getY() / 5;
		shellTitle = "Game of Life";
		initializeDisplayAndShell();
		initializeNavBar();
		addHorizontalSeparator();
		initializeCellArea();
	}

	private void initializeCellArea() {
		cellArea = new Composite(shell, SWT.BORDER | SWT.FILL);
		cellLayout = new GridLayout(DIMENSIONS.getY(), true);
		cellLayout.horizontalSpacing = 0;
		cellLayout.verticalSpacing = 0;
		cellLabelMap = new HashMap<Coordinate, CellLabel>();
		for (int i = 0; i < DIMENSIONS.getX(); i++) {
			for (int j = 0; j < DIMENSIONS.getY(); j++) {
				Coordinate coord = new Coordinate(i, j);
				final CellLabel label = new CellLabel(cellArea, SWT.BORDER, coord);
				label.addListener(SWT.MouseDown, new Listener() {
					@Override
					public void handleEvent(Event e) {
						Coordinate coord = label.getPosition();
						Cell cell = cellMap.get(coord);
						if (cell.isAlive()) {
							setCellState(coord, CellState.DEAD, false);
							label.setBackground(CellLabel.colorDead());
						} else {
							setCellState(coord, CellState.ALIVE, false);
							label.setBackground(CellLabel.colorAlive());
						}
						cellLabelMap.put(coord, label);
					}
				});
				cellLabelMap.put(label.getPosition(), label);
			}
		}
		cellArea.setLayout(cellLayout);
	}

	@Override
	public void nextGeneration() {
		super.nextGeneration();
		for (Map.Entry<Coordinate, Cell> entry : cellMap.entrySet()) {
			Coordinate coord = entry.getKey();
			Cell cell = entry.getValue();
			CellLabel label = cellLabelMap.get(coord);
			if (cell.isAlive()) {
				label.setBackground(CellLabel.colorAlive());
			} else {
				label.setBackground(CellLabel.colorDead());
			}
		}
	}

	private void initializeDisplayAndShell() {
		shellLayout = new GridLayout(1, true);
		shell.setText(shellTitle);
		shell.setLayout(shellLayout);
	}

	private void initializeNavBar() {
		navBar = new Composite(shell, SWT.NONE);
		navLayout = new RowLayout();
		navLayout.wrap = true;
		navLayout.pack = false;
		navBar.setLayout(navLayout);

		bRandom = new Button(navBar, SWT.PUSH);
		bRandom.setText(_20PercentOfGameBoard + " random clicks");
		bRandom.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				random20PercentOfGameBoardClicks();
			}
		});

		bNext = new Button(navBar, SWT.PUSH);
		bNext.setText("Next generation");
		bNext.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				nextGeneration();
			}
		});

		bLoop = new Button(navBar, SWT.PUSH);
		bLoop.setText("Start loop");
		final Runnable loopNextGeneration = new Runnable() {
			@Override
			public void run() {
				if (shell.isDisposed()) {
					return;
				}
				nextGeneration();
				display.timerExec(refreshTimeMs, this);
			}
		};
		bLoop.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				if (bLoop.getText().equals("Start loop")) {
					bLoop.setText("Stop loop");
					display.timerExec(100, loopNextGeneration);
				} else {
					bLoop.setText("Start loop");
					display.timerExec(-1, loopNextGeneration);
				}
			}
		});
	}

	private void random20PercentOfGameBoardClicks() {
		int cellsToRevive = _20PercentOfGameBoard;
		while (cellsToRevive-- > 0) {
			cellLabelMap.get(Coordinate.random()).getLabel().notifyListeners(SWT.MouseDown, null);
		}
	}

	private void addHorizontalSeparator() {
		separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		separator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}

	private void start() {
		centerShell();
		showLoadingShell();
		openShell();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
	
	private void centerShell() {
		shell.pack();
		shell.setMinimumSize(shell.getSize());
		
		Rectangle bounds = primaryMonitor.getBounds();
		Rectangle rect = shell.getBounds();
		
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		
		shell.setLocation(x, y);
	}

	private void showLoadingShell() {
		dialogShell = new Shell(shell, SWT.RESIZE | SWT.ON_TOP);
		dialogShell.setLayout(new GridLayout(1, false));
		dialogShell.setSize(280, 110);

		Label loadingLabel = new Label(dialogShell, SWT.NONE);
		loadingLabel.setText("\n\tLoading...");

		FontData[] fontData = loadingLabel.getFont().getFontData();
		fontData[0].setHeight(16);
		loadingLabel.setFont(new Font(display, fontData[0]));

		int x = shell.getLocation().x - dialogShell.getSize().x / 2 + shell.getSize().x / 2;
		int y = shell.getLocation().y - dialogShell.getSize().y / 2 + shell.getSize().y / 2;

		dialogShell.setLocation(new Point(x, y));
	}

	private void openShell() {
		dialogShell.open();
		shell.open();
		dialogShell.dispose();
	}

	public static void main(String[] args) {
		GameOfLife gol = new GameOfLife(new Coordinate(40, 60));
		gol.start();
	}

}
