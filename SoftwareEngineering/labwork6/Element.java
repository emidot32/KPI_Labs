package EngineeringSoftWare.labwork6;

/**
 * Interface Element summarise all computer component
 */
public interface Element {
    /**
     * The method accept() "accepts" all visitors
     * @param visitor
     */
    void accept(Visitor visitor);
}
