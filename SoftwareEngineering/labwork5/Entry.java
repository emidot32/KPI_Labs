package EngineeringSoftWare.labwork5;

import java.util.ArrayList;

/**
 * Class Entry - single line input field.
 * @author Illia Komisarov
 */
public class Entry implements Observable {
    /**
     * Class fields. Their description is below.
     */
    private int x, y;
    private int height;
    private int width;
    /**
     * TextL5 which is entered in entry.
     */
    private String text;
    /**
     * List of observers (listeners).
     */
    private ArrayList<Observer> observers = new ArrayList<>();

    /**
     * Constructor of the class which creates instance of Entry and initializes the fields.
     * @param x - coordinate by x
     * @param y - coordinate by y
     * @param height - entry height
     * @param width - entry width
     */
    public Entry(int x, int y, int height, int width){
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.text = "";
    }
    public void inputText(String input){
        text = input;
        notifyAllObservers();
    }
    @Override
    public void addObserver(Observer obj) {
        observers.add(obj);
    }

    @Override
    public void removeObserver(Observer obj) {
        observers.remove(obj);
    }

    @Override
    public void notifyAllObservers() {
        for (Observer o: observers){
            o.getTextFromEntry(this);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getText() {
        return text;
    }
}
