package com.capgemini.gameOfLife;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

public class CellLabel {
	private Coordinate position;
	private Label label;

	public CellLabel(Composite parent, int style, Coordinate position) {
		label = new Label(parent, style);
		label.setData(this);
		label.setText("     ");
		this.position = position;
	}

	public CellLabel(Label label, Coordinate position) {
		this.label = label;
		this.position = position;
	}

	public Coordinate getPosition() {
		return position;
	}
	
	public Label getLabel() {
		return label;
	}
	
	public void setText(String text) {
		label.setText(text);
	}
	
	public static Color colorDead() {
		return null;
	}

	public static Color colorAlive() {
		return new Color(GameOfLife.getDisplay(), new RGB(0,200,100));
	}

	public void setBackground(Color color) {
		label.setBackground(color);
	}

	public void addListener(int eventType, Listener listener) {
		label.addListener(eventType, listener);
		label.setToolTipText(position.toString());
	}

	public void setLabel(Label label) {
		this.label = label;		
	}
}
