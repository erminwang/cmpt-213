package ca.cmpt213.as4.shapes;

import ca.cmpt213.as4.UI.Canvas;

import java.awt.*;

/**
 * An implementation of Shape interface
 *
 * @author Di Wang
 */
public abstract class ShapeImpl implements Shape {
    private int locationX;
    private int locationY;
    private int width;
    private int height;
    private char borderChar;
    private Color color;

    ShapeImpl(int locationX, int locationY, int width, int height) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.width = width;
        this.height = height;
        this.borderChar = '*';
        this.color = Color.YELLOW;
    }

    @Override
    public char getBorderChar() {
        return borderChar;
    }

    @Override
    public void setBorderChar(char borderChar) {
        this.borderChar = borderChar;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public int getLocationX() {
        return locationX;
    }

    @Override
    public int getLocationY() {
        return locationY;
    }

    protected int getWidth() {
        return width;
    }

    protected int getheight() {
        return height;
    }

    @Override
    public void draw(Canvas canvas) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int canvasX = locationX + i;
                int canvasY = locationY + j;
                if (isBorder(i, j)) {
                    canvas.setCellColor(canvasX, canvasY, color);
                    canvas.setCellText(canvasX, canvasY, borderChar);
                } else if (isInside(i, j)) {
                    canvas.setCellColor(canvasX, canvasY, color);
                    canvas.setCellText(canvasX, canvasY, ' ');
                }
            }
        }
    }

    protected abstract boolean isBorder(int x, int y);

    protected abstract boolean isInside(int x, int y);
}
