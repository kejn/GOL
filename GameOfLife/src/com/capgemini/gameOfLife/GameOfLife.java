package com.capgemini.gameOfLife;

import java.awt.Font;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

public class GameOfLife {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Game Of Life");
		shell.setLayout(new GridLayout(1, false));

		Composite navBar = new Composite(shell, SWT.NONE);
		RowLayout buttonLayout = new RowLayout();
		buttonLayout.wrap = true;
		buttonLayout.pack = false;
		navBar.setLayout(buttonLayout);
		
		Button bRevieve = new Button(navBar, SWT.PUSH);
		bRevieve.setText("Random revive");
		
		Button bKill = new Button(navBar, SWT.PUSH);
		bKill.setText("Random kill");

		new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL).setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));;
		
		Composite cellArea = new Composite(shell, SWT.BORDER | SWT.FILL);
		GridLayout cellLayout = new GridLayout(4, true);
		cellArea.setLayout(cellLayout);
		Label cellLabel; 
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				cellLabel = new Label(cellArea, SWT.BORDER);
				cellLabel.setText("     ");
//				cellLabel.setBackground(new Color(display, new RGB(0,200,100)));
			}
		}
		

//		label = new Label(shell, SWT.BORDER);
//		label.setText("This is a label");
//		GridData data = new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1);
//		label.setLayoutData(data);
//
//		label = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
//
//		data = new GridData(SWT.FILL, SWT.TOP, true, false);
//		data.horizontalSpan = 2;
//		label.setLayoutData(data);
//
//		Button b = new Button(shell, SWT.PUSH);
//		b.setText("New Button");
//
//		data = new GridData(SWT.LEFT, SWT.TOP, false, false, 2, 1);
//		b.setLayoutData(data);
//
//		Spinner spinner = new Spinner(shell, SWT.READ_ONLY);
//		spinner.setMinimum(0);
//		spinner.setMaximum(1000);
//		spinner.setSelection(500);
//		spinner.setIncrement(1);
//		spinner.setPageIncrement(100);
//		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
//		gridData.widthHint = SWT.DEFAULT;
//		gridData.heightHint = SWT.DEFAULT;
//		gridData.horizontalSpan = 2;
//		spinner.setLayoutData(gridData);
//
//		Composite composite = new Composite(shell, SWT.BORDER);
//		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
//		gridData.horizontalSpan = 2;
//		composite.setLayoutData(gridData);
//		composite.setLayout(new GridLayout(1, false));
//
//		Text txtTest = new Text(composite, SWT.NONE);
//		txtTest.setText("Testing");
//		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
//		txtTest.setLayoutData(gridData);
//
//		Text txtMoreTests = new Text(composite, SWT.NONE);
//		txtMoreTests.setText("Another test");
//
//		Group group = new Group(shell, SWT.NONE);
//		group.setText("This is my group");
//		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
//		gridData.horizontalSpan = 2;
//		group.setLayoutData(gridData);
//		group.setLayout(new RowLayout(SWT.VERTICAL));
//		Text txtAnotherTest = new Text(group, SWT.NONE);
//		txtAnotherTest.setText("Another test");

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

}
