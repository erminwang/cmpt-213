package ca.cmpt213.as4.UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;

/**
 * An Icon which renders a Canvas into a graphical form. - Pass the constructor
 * the Canvas you want to render. - Place the CanvasIcon in a JLabel for
 * displaying on the screen.
 */
public class CanvasIcon implements Icon {
	// Size of individual character blocks on the screen.
	private static final int BLOCK_SIZE_X = 15;
	private static final int BLOCK_SIZE_Y = 18;

	// Where in the block to start writing text
	private static final int TEXT_OFFSET_X = 2;
	private static final int TEXT_OFFSET_Y = (int) (BLOCK_SIZE_Y * .5) + 3;

	private Canvas canvas;

	/**
	 * Create the CanvasIcon from an existing Canvas with characters and
	 * coloured blocks in it.
	 */
	public CanvasIcon(Canvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public int getIconHeight() {
		return BLOCK_SIZE_Y * canvas.getSizeY();
	}

	@Override
	public int getIconWidth() {
		return BLOCK_SIZE_X * canvas.getSizeX();
	}

	@Override
	public void paintIcon(Component c, Graphics g, int xPos, int yPos) {
		// Take the basic "Graphics" context and convert to
		// more powerful Graphics2D context.
		Graphics2D g2d = (Graphics2D) g;

		// For each square in the canvas, render it to the graphics context.
		for (int y = 0; y < canvas.getSizeY(); y++) {
			for (int x = 0; x < canvas.getSizeX(); x++) {
				int left = x * BLOCK_SIZE_X;
				int top = y * BLOCK_SIZE_Y;

				// Draw the box (leaving a 1 pixel boarder
				Color boxColor = canvas.getPointColor(x, y);
				g2d.setColor(boxColor);
				g2d.setBackground(boxColor);
				g2d.fillRect(left, top, BLOCK_SIZE_X - 1, BLOCK_SIZE_Y - 1);

				// Draw the text
				g2d.setColor(Color.BLACK);
				g2d.drawString("" + canvas.getPointText(x, y), left
						+ TEXT_OFFSET_X, top + TEXT_OFFSET_Y);
			}
		}
	}

}
