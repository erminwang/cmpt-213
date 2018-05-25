package ca.cmpt213.as4.shapes;

import ca.cmpt213.as4.UI.Canvas;

import java.util.ArrayList;
import java.util.List;

/**
 * TextBox that contains lines of horizontally centred text
 *
 * @author Di Wang
 */
public class TextBox extends Rectangle {
    private String message;

    public TextBox(int locationX, int locationY, int width, int height, String message) {
        super(locationX, locationY, width, height);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void draw(Canvas canvas) {
        int lineWidth = getWidth() - 2;
        String[] messageArray = message.split("\\s+");
        List<String> messageList = new ArrayList<>();
        String lineBuffer = "";
        for (int i = 0; i < messageArray.length; i++) {
            String word = messageArray[i].trim();
            if (word.length() == 0) {
                continue;
            }
            if (lineBuffer.length() > lineWidth) {
                messageList.add(lineBuffer.substring(0, lineWidth));
                lineBuffer = lineBuffer.substring(lineWidth);
                i--;
            } else if (lineBuffer.equals("")) {
                lineBuffer = word;
            } else if (lineBuffer.length() + word.length() + 1 > lineWidth) { //1 for a space between two words
                messageList.add(lineBuffer);
                lineBuffer = "";
                i--;
            } else {
                lineBuffer += " " + word;
            }
        }
        while (lineBuffer.length() > lineWidth) {
            messageList.add(lineBuffer.substring(0, lineWidth));
            lineBuffer = lineBuffer.substring(lineWidth);
        }
        messageList.add(lineBuffer);
        super.draw(canvas);
        for (int j = 0; j < messageList.size() && j < getheight() - 2; j++) {
            String line = messageList.get(j);
            int spacesOnTheLeft = (lineWidth - line.length()) / 2;
            for (int i = spacesOnTheLeft + 1; i < spacesOnTheLeft + line.length() + 1; i++) {
                canvas.setCellText(getLocationX() + i, getLocationY() + j + 1,
                        line.charAt(i - spacesOnTheLeft - 1));
            }
        }
    }
}
