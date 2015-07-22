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
	/**
	 * Display the app will run at.
	 */
	public static final Display DISPLAY = new Display();
	public static final Monitor PRIMARY_MONITOR = DISPLAY.getPrimaryMonitor();

	/**
	 * Time in milliseconds after which the the {@link #nextGeneration()} method
	 * will be repeatedaly called in loop executed by pressing {@link #bLoop}
	 * Button.
	 */
	private static final int refreshTimeMs = 50;

	/**
	 * Root app window.
	 */
	private static Shell shell = new Shell(DISPLAY);

	/**
	 * Root app window title.
	 */
	private final String shellTitle;

	/**
	 * Number representing 20% of all Cells in GameBoard.
	 */
	private final int _20PercentOfGameBoard;

	/**
	 * Window displaying "Loading" Label until the {@link #shell} window is
	 * fully loaded.
	 */
	private Shell dialogShell;

	/**
	 * Root window layout.
	 */
	private GridLayout shellLayout;

	/**
	 * Layout for {@link #navBar}.
	 */
	private RowLayout navLayout;

	/**
	 * Layout for cell labels.
	 */
	private GridLayout cellLayout;

	/**
	 * Composite containing navigation buttons.
	 */
	private Composite navBar;

	/**
	 * Composite containing all cell labels.
	 */
	private Composite cellArea;

	/**
	 * Button simulating {@link #_20PercentOfGameBoard} mouse clicks in
	 * {@link #cellArea}.
	 */
	private Button bRandom;

	/**
	 * Button calling {@link #nextGeneration()} method.
	 */
	private Button bNext;

	/**
	 * Button which starts/stops calling {@link #nextGeneration()} method every
	 * {@link #refreshTimeMs} milliseconds.
	 */
	private Button bLoop;

	/**
	 * Separates {@link #navBar} and {@link #cellArea}.
	 */
	private Label separator;

	/**
	 * Map of cell labels corresponding to cellMap from GameBoard.
	 */
	private Map<Coordinate, CellLabel> cellLabelMap;

	public static Device getDisplay() {
		return DISPLAY;
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

	/**
	 * Initializes related fields and fills {@link #cellLabelMap} with DEAD cell
	 * labels. Each label is added a listener which lets user change its state
	 * by mouse click.
	 */
	private void initializeCellArea() {
		cellArea = new Composite(shell, SWT.NONE);// SWT.BORDER | SWT.FILL);
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
					}
				});
				cellLabelMap.put(label.getPosition(), label);
			}
		}
		cellArea.setLayout(cellLayout);
	}

	/**
	 * Applies changes calculated by overrided method to UI.
	 */
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

	/**
	 * Initializes {@link #navBar} containing buttons: {@link #bRandom},
	 * {@link #bNext} and {@link #bLoop}.
	 */
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
				DISPLAY.timerExec(refreshTimeMs, this);
			}
		};
		bLoop.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				if (bLoop.getText().equals("Start loop")) {
					bLoop.setText("Stop loop");
					DISPLAY.timerExec(100, loopNextGeneration);
				} else {
					bLoop.setText("Start loop");
					DISPLAY.timerExec(-1, loopNextGeneration);
				}
			}
		});
	}

	/**
	 * Simulates {@link #_20PercentOfGameBoard} mouse clicks in
	 * {@link #cellArea}.
	 */
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

	void start() {
		centerShell();
		initLoadingShell();
		openShell();
		while (!shell.isDisposed()) {
			if (!DISPLAY.readAndDispatch()) {
				DISPLAY.sleep();
			}
		}
		DISPLAY.dispose();
	}

	/**
	 * Sets {@link #shell} location so that it is displayed in the center of
	 * {@link #PRIMARY_MONITOR}.
	 */
	private void centerShell() {
		shell.pack();
		shell.setMinimumSize(shell.getSize());

		Rectangle bounds = PRIMARY_MONITOR.getBounds();
		Rectangle rect = shell.getBounds();

		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;

		shell.setLocation(x, y);
	}

	/**
	 * Initializes and centers {@link #dialogShell}.
	 */
	private void initLoadingShell() {
		dialogShell = new Shell(shell, SWT.RESIZE | SWT.ON_TOP);
		dialogShell.setLayout(new GridLayout(1, false));
		dialogShell.setSize(280, 110);

		Label loadingLabel = new Label(dialogShell, SWT.NONE);
		loadingLabel.setText("\n\tLoading...");

		FontData[] fontData = loadingLabel.getFont().getFontData();
		fontData[0].setHeight(16);
		loadingLabel.setFont(new Font(DISPLAY, fontData[0]));

		int x = shell.getLocation().x - dialogShell.getSize().x / 2 + shell.getSize().x / 2;
		int y = shell.getLocation().y - dialogShell.getSize().y / 2 + shell.getSize().y / 2;

		dialogShell.setLocation(new Point(x, y));
	}

	private void openShell() {
		dialogShell.open();
		shell.open();
		dialogShell.dispose();
	}

	/**
	 * If user exited the AskShell with X button it is set to true, so that
	 * GameOfLife is not run.
	 */
	public static boolean CANCELLED = false;
	
	/**
	 * GameBoard dimensions desired by user.
	 */
	public static Coordinate USER_COORDINATES;

	public static void main(String[] args) {
		AskShell.display();
		if (GameOfLife.CANCELLED) {
			return;
		}
		GameOfLife gol = new GameOfLife(USER_COORDINATES);
		gol.start();
	}

}
