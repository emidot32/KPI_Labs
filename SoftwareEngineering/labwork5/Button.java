package EngineeringSoftWare.labwork5;

import java.util.ArrayList;

/**
 * Concrete observable class.
 * @author Illia Komisarov
 *
 */
public class Button implements Observable {
    /**
     * Class fields. Their description is below.
     */
    private int x, y;
    private String text;
    private String command;
    /**
     * This flag sets press the button. If flag == true, button was pressed.
     */
    private boolean flag;
    /**
     * List of observers (listeners).
     */
    private ArrayList<Observer> observers = new ArrayList<>();

    /**
     * Constructor of the class which creates instance of Button and initializes the fields.
     * @param x - coordinate by x
     * @param y - coordinate by y
     * @param text - text on the button
     * @param command - the command which button does
     */
    public Button(int x, int y, String text, String command) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.command = command;
        flag = false;
    }
    public void pushButton(){
        flag = true;
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
        for (Observer o: observers) {
            o.getEventFromButton(this);
        }
    }

    public String getCommand() {
        return command;
    }

    public String getText() {
        return text;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public boolean isFlag() {
        return flag;
    }
}