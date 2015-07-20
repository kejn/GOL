package com.capgemini.gameOfLife;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class GameOfLife extends GameBoard {
	private final String shellTitle;
	private final Color colorAlive;
	private Display display;
	private Shell shell;

	private GridLayout shellLayout;
	private RowLayout navLayout;
	private GridLayout cellLayout;

	private Composite navBar;
	private Composite cellArea;

	private Button bRandRevive;
	private Button bRandKill;
	private Button bNext;
	
	private Label separator;
	
	private Map<Coordinate, Label> cellLabelMap;

	public GameOfLife(Coordinate newDimensions) {
		super(newDimensions);
		shellTitle = "Game of Life";
		initializeDisplayAndShell();
		initializeNavBar();
		addHorizontalSeparator();
		colorAlive = new Color(display, new RGB(0,200,100));
		initializeCellArea();
	}

	private void initializeCellArea() {
		cellArea = new Composite(shell, SWT.BORDER | SWT.FILL);
		cellLayout = new GridLayout(DIMENSIONS.getX(), true);
		cellLayout.horizontalSpacing = 0;
		cellLayout.verticalSpacing = 0;
		cellLabelMap = new HashMap<Coordinate, Label>();
		for (final Map.Entry<Coordinate, Cell> entry : cellMap.entrySet()) {
			Label label = new Label(cellArea, SWT.BORDER);
			label.setText("     ");
			label.setBackground(new Color(display, new RGB(0,0,0)));
			label.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseUp(MouseEvent arg0) {
				}
				
				@Override
				public void mouseDown(MouseEvent arg0) {
					Label label = (Label) arg0.getSource();
					Cell cell = entry.getValue();
					Coordinate coord = entry.getKey();
					if(cell.isAlive()) {
						setCellState(coord, CellState.DEAD, false);
						label.setBackground(null);
					} else {
						setCellState(coord, CellState.ALIVE, false);
						label.setBackground(colorAlive);
					}
					cellLabelMap.put(coord, label);
				}
				
				@Override
				public void mouseDoubleClick(MouseEvent arg0) {
				}
			});
			cellLabelMap.put(entry.getKey(), label);
		}
		cellArea.setLayout(cellLayout);
	}

	@Override
	public void nextGeneration() {
		super.nextGeneration();
		for (Map.Entry<Coordinate, Cell> entry : cellMap.entrySet()) {
			Coordinate coord = entry.getKey();
			Cell cell = entry.getValue();
			Label label = cellLabelMap.get(coord);
			if (cell.isAlive()) {
				label.setBackground(colorAlive);
			} else {
				label.setBackground(new Color(display, new RGB(0,0,0)));
			}
		}
	}
	
	private void initializeDisplayAndShell() {
		display = new Display();
		shell = new Shell(display);
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

		bRandRevive = new Button(navBar, SWT.PUSH);
		bRandRevive.setText("Random revive");

		bRandKill = new Button(navBar, SWT.PUSH);
		bRandKill.setText("Random kill");
		
		bNext = new Button(navBar, SWT.PUSH);
		bNext.setText("Next generation");
		bNext.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseUp(MouseEvent arg0) {
			}
			
			@Override
			public void mouseDown(MouseEvent arg0) {
				nextGeneration();
			}
			
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
			}
		});
	}

	private void addHorizontalSeparator() {
		separator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		separator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	}
	
	private void startEventLoop() {
		shell.pack();
		shell.setMinimumSize(shell.getSize());
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	
	
	public static void main(String[] args) {
		GameOfLife gol = new GameOfLife(new Coordinate(6, 3));
		gol.startEventLoop();

//		Composite cellArea = new Composite(shell, SWT.BORDER | SWT.FILL);
//		GridLayout cellLayout = new GridLayout(4, true);
//		cellArea.setLayout(cellLayout);
//		Label cellLabel;
//		for (int i = 0; i < 4; i++) {
//			for (int j = 0; j < 4; j++) {
//				cellLabel = new Label(cellArea, SWT.BORDER);
//				cellLabel.setText("     ");
//				// cellLabel.setBackground(new Color(display, new
//				// RGB(0,200,100)));
//			}
//		}

		// label = new Label(shell, SWT.BORDER);
		// label.setText("This is a label");
		// GridData data = new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1);
		// label.setLayoutData(data);
		//
		// label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		//
		// data = new GridData(SWT.FILL, SWT.TOP, true, false);
		// data.horizontalSpan = 2;
		// label.setLayoutData(data);
		//
		// Button b = new Button(shell, SWT.PUSH);
		// b.setText("New Button");
		//
		// data = new GridData(SWT.LEFT, SWT.TOP, false, false, 2, 1);
		// b.setLayoutData(data);
		//
		// Spinner spinner = new Spinner(shell, SWT.READ_ONLY);
		// spinner.setMinimum(0);
		// spinner.setMaximum(1000);
		// spinner.setSelection(500);
		// spinner.setIncrement(1);
		// spinner.setPageIncrement(100);
		// GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		// gridData.widthHint = SWT.DEFAULT;
		// gridData.heightHint = SWT.DEFAULT;
		// gridData.horizontalSpan = 2;
		// spinner.setLayoutData(gridData);
		//
		// Composite composite = new Composite(shell, SWT.BORDER);
		// gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		// gridData.horizontalSpan = 2;
		// composite.setLayoutData(gridData);
		// composite.setLayout(new GridLayout(1, false));
		//
		// Text txtTest = new Text(composite, SWT.NONE);
		// txtTest.setText("Testing");
		// gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		// txtTest.setLayoutData(gridData);
		//
		// Text txtMoreTests = new Text(composite, SWT.NONE);
		// txtMoreTests.setText("Another test");
		//
		// Group group = new Group(shell, SWT.NONE);
		// group.setText("This is my group");
		// gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		// gridData.horizontalSpan = 2;
		// group.setLayoutData(gridData);
		// group.setLayout(new RowLayout(SWT.VERTICAL));
		// Text txtAnotherTest = new Text(group, SWT.NONE);
		// txtAnotherTest.setText("Another test");

		
	}

	

}
