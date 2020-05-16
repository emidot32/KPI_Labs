package EngineeringSoftWare.labwork5;

/**
 * Class SomeObserver sets observers our buttons and entries
 * @author Illia Komisarov
 */
public class SomeObserver implements  Observer{
    @Override
    public void getEventFromButton(Observable obj) {
        Button button = (Button) obj;
        System.out.print("The coordinates of the button (" + button.getX() +
                ";" + button.getY() + "). The text: \"" + button.getText() + "\". Command: " + button.getCommand());
        if (button.isFlag()){
            System.out.println(". The button \"" + button.getText() + "\" was pressed.");
        }
        else{
            System.out.println(". The button \"" + button.getText() + "\" haven't been pressed yet.");
        }
    }

    @Override
    public void getTextFromEntry(Observable obj) {
        Entry entry = (Entry) obj;
        System.out.print("The entry coordinates (" + entry.getX() +
                ";" + entry.getY());
        if (entry.getText().equals("")){
            System.out.println("). The entry is empty.");
        }
        else{
            System.out.println("). The text in entry: \"" + entry.getText() + "\".");
        }

    }
}
