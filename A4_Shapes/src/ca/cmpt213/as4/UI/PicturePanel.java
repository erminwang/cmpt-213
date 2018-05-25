package ca.cmpt213.as4.UI;

import ca.cmpt213.as4.shapes.Shape;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PicturePanel extends JPanel{
	
	private static final int BORDER_WIDTH = 2; 
	
	private Canvas canvas;
	private List<Shape> shapes = new ArrayList<>();
	
	public PicturePanel(String title, int sizeX, int sizeY) {
		canvas = new Canvas(sizeX, sizeY);
		setBorder(BorderFactory.createLineBorder(Color.BLUE, BORDER_WIDTH));
		setLayout(new BorderLayout());
		
		add(new JLabel(title), BorderLayout.NORTH);
		
		CanvasIcon icon = new CanvasIcon(canvas);
		add(new JLabel(icon), BorderLayout.CENTER);
	}
	
	public void addFront(Shape shape) {
		// Add the shape at the end of the list so it's drawn last (top/front)
		shapes.add(shape);
		redraw();
	}

	public void addBack(Shape shape) {
		// Place shape at start of list so it's drawn first (bottom/back).
		shapes.add(0, shape);
		redraw();
	}

	private void redraw() {
		for (Shape shape : shapes) {
			shape.draw(canvas);
		}
	}
}
