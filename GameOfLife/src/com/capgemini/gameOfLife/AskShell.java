package com.capgemini.gameOfLife;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

public class AskShell {
	private final static Shell askShell = new Shell(GameOfLife.display, SWT.CLOSE);
	private static Group group = new Group(askShell, SWT.NONE);
	private static GridData spinnerGridData = new GridData(SWT.FILL, SWT.WRAP, false, false, 1, 1);
	private static Spinner[] dimensions = null;

	public static void display() {
		initShell();
		initGroup();
		newLabel("Number of rows");
		newSpinner();
		newLabel("Number of columns");
		newSpinner();
		
		new Label(askShell, SWT.NONE);
		
		Button button = new Button(askShell, SWT.PUSH);
		button.setText("OK");
		button.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, false, false, 1, 1));
		button.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event e) {
				GameOfLife.USER_COORDINATES = new Coordinate(dimensions[0].getSelection(), dimensions[1].getSelection());
				askShell.notifyListeners(SWT.OK, null);
			}
		});
		
		askShell.pack();
		askShell.setMinimumSize(askShell.getSize());
		
		center();
		
		askShell.open();
		while (!askShell.isDisposed() && !GameOfLife.CANCELLED) {
			if (!GameOfLife.display.readAndDispatch()) {
				GameOfLife.display.sleep();
			}
		}
	}

	private static void newLabel(String text) {
		Label label = new Label(group, SWT.NONE);
		label.setText(text);
	}
	
	private static int i = 0;
	private static void newSpinner() {
		if(dimensions == null) {
			dimensions =  new Spinner[2];
		}
		dimensions[i] = new Spinner(group, SWT.FILL);
		dimensions[i].setMinimum(1);
		dimensions[i].setMaximum(70);
		dimensions[i].setSelection(40);
		dimensions[i].setLayoutData(spinnerGridData);
		++i;
	}
	
	
	private static void center() {
		Rectangle bounds = GameOfLife.primaryMonitor.getBounds();
		Rectangle rect = askShell.getBounds();
	
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
	
		askShell.setLocation(x, y);
	}

	private static void initGroup() {
		group.setText("Specify the game board dimensions");
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		group.setLayout(new GridLayout(2,true));		
	}

	private static void initShell() {
		askShell.setText("New Game of Life");
		askShell.setLayout(new GridLayout(2, true));
		askShell.addShellListener(new ShellListener() {
			@Override
			public void shellIconified(ShellEvent arg0) {
			}
	
			@Override
			public void shellDeiconified(ShellEvent arg0) {
			}
	
			@Override
			public void shellDeactivated(ShellEvent arg0) {
			}
	
			@Override
			public void shellClosed(ShellEvent arg0) {
				GameOfLife.CANCELLED = true;
			}
	
			@Override
			public void shellActivated(ShellEvent arg0) {
			}
		});
		askShell.addListener(SWT.OK, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				askShell.dispose();
			}
		});
	
	}

}
