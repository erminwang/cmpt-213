package ca.cmpt213.as4.shapes;

import ca.cmpt213.as4.UI.Canvas;

import java.awt.*;

/**
 * An interface of all Shape such as Rectangle, Triangle
 *
 * @author Di Wang
 */
public interface Shape {

    int getLocationX();

    int getLocationY();

    char getBorderChar();

    void setBorderChar(char borderChar);

    Color getColor();

    void setColor(Color color);

    void draw(Canvas canvas);
}
