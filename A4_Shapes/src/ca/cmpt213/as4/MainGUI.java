package ca.cmpt213.as4;

import ca.cmpt213.as4.UI.Canvas;
import ca.cmpt213.as4.UI.CanvasIcon;
import ca.cmpt213.as4.UI.PicturePanel;
import ca.cmpt213.as4.shapes.Rectangle;
import ca.cmpt213.as4.shapes.TextBox;
import ca.cmpt213.as4.shapes.Triangle;

import java.awt.Color;

import javax.swing.*;


/**
 * Test program showing a number of different shapes inside an icon.
 */
public class MainGUI {
	// Default size of the panels
	private static final int SIZE_X = 79;
	private static final int SIZE_Y = 24;
	
	
	/**
	 * Application to display the test "pictures" to the UI.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));

		// Sample function showing use of Canvas, which will be needed when writing your Shapes.
		// Comment this out when you have later tests working.
        // You may ignore the details about what the JLabel and CanvasIcon are.
//	    frame.add(new JLabel(new CanvasIcon(makeExampleCanvas())));

		// Test routines to exercise Picture and the Shape classes.
		frame.add(makeRectanglesPicture());
		frame.add(makeTrianglesPicture());
		frame.add(makeTextBoxesPicture());
//		frame.add(makeMixedPicture());
		frame.add(makeFrontAndBackPicture());

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	/**
	 * Demonstrate the Canvas class (useful because your Shapes must draw themselves onto a canvas).
	 *  - Use a Canvas to "draw" some items.
	 * @return The Canvas we have drawn on.
	 */
	private static Canvas makeExampleCanvas() {
        // Demonstrate drawing to the canvas
		Canvas canvas = new Canvas(12, 15);

		// Draw characters onto the canvas
		canvas.setCellText(0, 0, 'S');
		canvas.setCellText(1, 1, '!');
		canvas.setCellText(canvas.getSizeX()-1, canvas.getSizeY()-1, 'B');

		// Setting colours in the canvas
		for (int i = 0; i < 5; i++) {
			canvas.setCellColor(i + 3, 8, Color.RED);
			canvas.setCellColor(5, i + 6, Color.GREEN);
		}

		// Setting colour and text:
		for (int i = 0; i < 5; i++) {
			int pos = i + 9;
			canvas.setCellColor(pos, pos, Color.YELLOW);
			canvas.setCellText(pos, pos, (char)('A' + i));
		}

		// Trying to draw off the canvas is just ignored
		// (useful when drawing a shape beyond the bounds of the canvas; you
		//  can ignore the fact that it's off the page!)
		canvas.setCellText(canvas.getSizeX(), canvas.getSizeY(), 'E');

		return canvas;
	}



	// //////////////////////////////////////////////////////////////////////////
	// Rectangle Test
	// //////////////////////////////////////////////////////////////////////////
	private static PicturePanel makeRectanglesPicture() {
		PicturePanel picture = new PicturePanel("Rectangle Picture Test:", 60, 19);
		testRectanglesRow1(picture);
		testRectangleRow2(picture);
		return picture;
	}

	private static void testRectanglesRow1(PicturePanel picture) {
		Rectangle r1 = new Rectangle(0, 0, 1, 1);
		r1.setColor(Color.MAGENTA);
		picture.addFront(r1);

		Rectangle r2 = new Rectangle(5, 0, 2, 2);
		r2.setBorderChar('*');
		r2.setColor(Color.GREEN);
		picture.addFront(r2);

		Rectangle r3 = new Rectangle(10, 0, 3, 3);
		r3.setColor(Color.RED);
		picture.addFront(r3);

		Rectangle r4 = new Rectangle(15, 0, 4, 4);
		picture.addFront(r4);

		Rectangle r5 = new Rectangle(27, 0, 35, 18);
		r5.setColor(Color.ORANGE);
		picture.addFront(r5);
	}

	private static void testRectangleRow2(PicturePanel picture) {
		final int ROW = 5;
		Rectangle r1 = new Rectangle(0, ROW, 5, 10);
		r1.setColor(Color.PINK);
		picture.addFront(r1);

		r1 = new Rectangle(10, ROW, 5, 10);
		r1.setColor(Color.LIGHT_GRAY);
		r1.setBorderChar('#');
		picture.addFront(r1);

		r1 = new Rectangle(20, ROW, 5, 10);
		r1.setColor(Color.RED);
		r1.setBorderChar('%');
		picture.addFront(r1);

		r1 = new Rectangle(30, ROW, 14, 49);
		r1.setColor(Color.GREEN);
		r1.setBorderChar('@');
		picture.addFront(r1);
	}

	// //////////////////////////////////////////////////////////////////////////
	// Triangle Test
	// //////////////////////////////////////////////////////////////////////////
	private static PicturePanel makeTrianglesPicture() {
		PicturePanel picture = new PicturePanel("Triangle Picture Test:", 50, 26);
		testTriangleRow1(picture);
		testTriangleRow2(picture);
		return picture;
	}

	private static void testTriangleRow1(PicturePanel picture) {
		Triangle t1 = new Triangle(0, 0, 1);
		t1.setColor(Color.CYAN);
		picture.addFront(t1);

		Triangle t2 = new Triangle(5, 0, 2);
		t2.setColor(Color.RED);
		picture.addFront(t2);

		Triangle t3 = new Triangle(10, 0, 3);
		t3.setColor(Color.MAGENTA);
		picture.addFront(t3);

		Triangle t4 = new Triangle(15, 0, 4);
		t4.setColor(Color.GREEN);
		picture.addFront(t4);

		Triangle t5 = new Triangle(27, 0, 18);
		t5.setColor(Color.LIGHT_GRAY);
		picture.addFront(t5);

		Triangle t6 = new Triangle(37, 2, 7);
		t6.setColor(Color.BLACK);
		picture.addFront(t6);
	}

	private static void testTriangleRow2(PicturePanel picture) {
		final int ROW = 10;
		Triangle t1 = new Triangle(0, ROW, 5);
		t1.setColor(Color.RED);
		picture.addFront(t1);

		t1 = new Triangle(10, ROW, 5);
		t1.setColor(Color.YELLOW);
		t1.setBorderChar('#');
		picture.addFront(t1);

		t1 = new Triangle(20, ROW, 5);
		t1.setColor(Color.GREEN);
		t1.setBorderChar('%');
		picture.addFront(t1);

		t1 = new Triangle(35, ROW, 5);
		t1.setColor(Color.RED);
		t1.setBorderChar('X');
		picture.addFront(t1);

		t1 = new Triangle(30, ROW, 16);
		t1.setBorderChar('@');
		picture.addFront(t1);

	}

	// //////////////////////////////////////////////////////////////////////////
	// TextBox Test
	// //////////////////////////////////////////////////////////////////////////
	private static PicturePanel makeTextBoxesPicture() {
		PicturePanel picture = new PicturePanel("Text Box Test:", SIZE_X, SIZE_Y);
		testTextBoxRow1(picture);
		testTextBoxRow2(picture);
		return picture;
	}

	private static void testTextBoxRow1(PicturePanel picture) {
		TextBox tb1 = new TextBox(0, 0, 14, 3, "Hello world!");
		tb1.setColor(Color.CYAN);
		picture.addFront(tb1);

		tb1 = new TextBox(0, 5, 16, 3, "Hello world!");
		tb1.setColor(Color.RED);
		picture.addFront(tb1);

		TextBox tb2 = new TextBox(20, 0, 9, 9,
				"And so it began, the great debugging!");
		tb2.setColor(Color.GREEN);
		picture.addFront(tb2);

		// Code Monkey Lyrics, by Jonathan Coulton
		// See: http://www.youtube.com/watch?v=qYodWEKCuGg
		TextBox tb3 = new TextBox(
				30,
				0,
				30,
				15,
				"Code Monkey get up get coffee. "
						+ "Code Monkey go to job. "
						+ "Code Monkey have boring meeting with boring manager Rob. "
						+ "Rob say Code Monkey very diligent, "
						+ "but his output stink. "
						+ "His code not functional or elegant. "
						+ "What do Code Monkey think? "
						+ "Code Monkey think maybe manager want to write goddamn login page himself. "
						+ "Code Monkey not say it out loud. "
						+ "Code Monkey not crazy just proud.");
		picture.addFront(tb3);

		TextBox tb4 = new TextBox(65, 5, 13, 10,
				"And there was much rejoicing!");
		tb4.setColor(Color.ORANGE);
		picture.addFront(tb4);
	}

	private static void testTextBoxRow2(PicturePanel picture) {
		// Test how well the text wraps
		final int ROW = 16;
		String alphabet = " a b c d e f g h i j k l m n o p q r s t u v w x y z"
				+ " A B C D E F G H I J K L M O P Q R S T U V W X Y Z ";
		TextBox tb1 = new TextBox(0, ROW, 8, 8, alphabet);
		tb1.setColor(Color.CYAN);
		picture.addFront(tb1);

		tb1 = new TextBox(9, ROW, 8, 8, alphabet);
		tb1.setColor(Color.GREEN);
		picture.addFront(tb1);

		tb1 = new TextBox(20, ROW, 7, 8, alphabet);
		tb1.setColor(Color.MAGENTA);
		picture.addFront(tb1);

		tb1 = new TextBox(30, ROW, 9, 8, alphabet);
		tb1.setColor(Color.LIGHT_GRAY);
		picture.addFront(tb1);

		tb1 = new TextBox(40, ROW, 9, 8, alphabet);
		tb1.setColor(Color.CYAN);
		tb1.setMessage("Hi yal!");
		tb1.setBorderChar('#');
		picture.addFront(tb1);

		tb1 = new TextBox(50, ROW, 9, 3, alphabet);
		tb1.setColor(Color.GREEN);
		tb1.setMessage("Hi yal!");
		tb1.setBorderChar('&');
		picture.addFront(tb1);

		tb1 = new TextBox(60, ROW, 20, 5, alphabet);
		tb1.setColor(Color.RED);
		tb1.setMessage("Hi yal!");
		tb1.setBorderChar('*');
		picture.addFront(tb1);
	}

	// //////////////////////////////////////////////////////////////////////////
	// Mixed Picture Test
	// //////////////////////////////////////////////////////////////////////////
	private static PicturePanel makeMixedPicture() {
		PicturePanel pic = new PicturePanel("Mixed Element Picture Test:", SIZE_X, SIZE_Y);

		// Add border
		Rectangle rect = new Rectangle(0, 0, SIZE_X, SIZE_Y);
		rect.setColor(Color.ORANGE);
		rect.setBorderChar('#');
		pic.addFront(rect);

		// Text box with styling:
		rect = new TextBox(45, 10, 30, 4, "A box!");
		rect.setColor(Color.RED);
		rect.setBorderChar('%');
		pic.addFront(rect);

		// Bunch of other shapes:
		Triangle shape2 = new Triangle(15, 2, 20);
		shape2.setColor(Color.CYAN);
		pic.addFront(shape2);

		Rectangle shape3 = new Rectangle(25, 8, 15, 8);
		shape3.setColor(Color.GREEN);
		pic.addFront(shape3);

		Triangle shape4 = new Triangle(54, 1, 5);
		shape4.setColor(Color.DARK_GRAY);
		pic.addFront(shape4);

		TextBox shape5 = new TextBox(2, 2, 3, 20, "Thin text in a box!");
		shape5.setColor(Color.YELLOW);
		pic.addFront(shape5);

		Rectangle shape6 = new Rectangle(72, 18, 5, 4);
		shape6.setColor(Color.MAGENTA);
		pic.addFront(shape6);

		return pic;
	}

	// //////////////////////////////////////////////////////////////////////////
	// Test picture by adding shapes to the front and back.
	// //////////////////////////////////////////////////////////////////////////
	private static PicturePanel makeFrontAndBackPicture() {
		PicturePanel pic = new PicturePanel("Front And Back Picture Test:", SIZE_X, SIZE_Y);

		// Box around text
		Rectangle rect = new Rectangle(30, 7, 20, 12);
		rect.setColor(Color.RED);
		rect.setBorderChar('B');
		pic.addBack(rect);

		// Triangle in background-left
		Triangle triLeft = new Triangle(25, 3, 18);
		triLeft.setColor(Color.CYAN);
		triLeft.setBorderChar('L');
		pic.addBack(triLeft);

		// Text on top:
		TextBox textTop = new TextBox(35, 9, 10, 8, "I'm on top! WOOO HOO!");
		textTop.setColor(Color.LIGHT_GRAY);
		textTop.setBorderChar('T');
		pic.addFront(textTop);

		// Triangle in background-right
		Triangle triRight = new Triangle(45, 3, 18);
		triRight.setColor(Color.BLUE);
		triRight.setBorderChar('R');
		pic.addBack(triRight);

		// Text in very back:
		TextBox textBack = new TextBox(10, 1, SIZE_X-20, SIZE_Y-2, "Aww drat, I'm at the back.");
		textBack.setColor(Color.YELLOW);
		textBack.setBorderChar('O');
		pic.addBack(textBack);

		return pic;
	}

}

