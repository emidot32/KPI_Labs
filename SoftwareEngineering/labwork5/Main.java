package EngineeringSoftWare.labwork5;

public class Main {
    public static void main(String[] args) {
        Entry entry = new Entry(1, 2, 3, 10);
        Button button = new Button(1, 1, "Button 1", "Show something");
        SomeObserver observer1 = new SomeObserver();
        SomeObserver observer2 = new SomeObserver();
        entry.addObserver(observer1);
        entry.addObserver(observer2);
        button.addObserver(observer1);
        button.addObserver(observer2);
        entry.notifyAllObservers();
        button.notifyAllObservers();
        entry.inputText("TextL5 in Entry");
        button.pushButton();
    }



}
