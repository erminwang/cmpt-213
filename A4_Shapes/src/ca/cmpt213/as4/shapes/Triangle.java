package ca.cmpt213.as4.shapes;

/**
 * A class representing triangles that can be drawn on canvas
 *
 * @author Di Wang
 */
public class Triangle extends ShapeImpl {

    public Triangle(int locationX, int locationY, int lateral) {
        super(locationX, locationY, lateral, lateral);
    }

    @Override
    protected boolean isBorder(int x, int y) {
        return x == 0
                || y == getWidth() - 1
                || x + (getWidth() - 1 - y) == getWidth() - 1;
    }

    @Override
    protected boolean isInside(int x, int y) {
        return x > 0
                && y < getWidth() - 1
                && x + (getWidth() - 1 - y) < getWidth() - 1;
    }
}
