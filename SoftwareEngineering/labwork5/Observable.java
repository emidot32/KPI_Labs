package EngineeringSoftWare.labwork5;

public interface Observable {
    /**
     * Adds new listener to observed object.
     * @param obj object observer (listener).
     */
    public void addObserver(Observer obj);
    /**
     * Removes listener from observed object.
     * @param obj listener to be removed.
     */
    public void removeObserver(Observer obj);
    /**
     * Notifies all listeners about change of objects (buttons and entries.
     */
    public void notifyAllObservers();
}
