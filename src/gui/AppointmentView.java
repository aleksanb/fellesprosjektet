package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.border.StrokeBorder;

import core.CalendarProgram;

import db.Appointment;

public class AppointmentView extends JComponent {

	private Appointment model;
	private Color color;
	private boolean selected;

	public AppointmentView() {
		color = Color.LIGHT_GRAY;
		selected = false;
	}
	
	public AppointmentView(Appointment app) {
		this.model = app;
		color = Color.green;
		selected = false;
	}
	
	public Appointment getModel() {
		return model;
	}

	public void setModel(Appointment model) {
		this.model = model;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public void paint(Graphics gr) {
		Graphics2D g = (Graphics2D)gr;

		g.setColor(color);
		g.fillRoundRect(2, 1, getBounds().width - 3, getBounds().height - 1, 10, 10);
		g.setColor(Color.BLACK);
		if(selected) {
			g.drawRoundRect(0, 0, getBounds().width, getBounds().height - 1, 10, 10);
			g.drawRoundRect(1, 1, getBounds().width - 2, getBounds().height - 3, 10, 10);
		}
		g.drawString("møte", 4, g.getFont().getSize() + 4);
		g.drawString("alfred", 4, g.getFont().getSize() * 2 + 4);
	}
	 public static void main(String args[]) {
			JFrame frame = new JFrame("...");
			AppointmentView ev = new AppointmentView();
			ev.setSelected(true);
			frame.getContentPane().add(ev);
			frame.pack();
			frame.setVisible(true);
		}  
}
