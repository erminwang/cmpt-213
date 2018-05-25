package ca.cmpt213.as4.shapes;

/**
 * A class representing rectangles that can be drawn on canvas
 *
 * @author Di Wang
 */
public class Rectangle extends ShapeImpl {

    public Rectangle(int locationX, int locationY, int width, int height) {
        super(locationX, locationY, width, height);
    }


    @Override
    protected boolean isBorder(int x, int y) {
        boolean isOnXBorder = x == 0 || x == getWidth() - 1;
        boolean isOnYBorder = y == 0 || y == getheight() - 1;

        return isOnXBorder || isOnYBorder;
    }

    @Override
    protected boolean isInside(int x, int y) {
        boolean isInsideXRange = x > 0 && x < getWidth() - 1;
        boolean isInsideYRange = y > 0 && y < getheight() - 1;

        return isInsideXRange && isInsideYRange;
    }
}
