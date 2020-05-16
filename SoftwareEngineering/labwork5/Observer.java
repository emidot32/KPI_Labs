package EngineeringSoftWare.labwork5;

public interface Observer {
    /**
     * Show info about button.
     * @param obj caller.
     */
    public void getEventFromButton(Observable obj);

    /**
     * Show info about entry.
     * @param obj caller.
     */
    public void getTextFromEntry(Observable obj);
}
