package EngineeringSoftWare.labwork6;

/**
 * Interface Visitor summarizes all visitors of computer components
 */
public interface Visitor {
    /**
     * The method visit() for Processor
     * @param processor
     */
    void visit(Processor processor);
    /**
     * The method visit() for RAM
     * @param memory
     */
    void visit(RAM memory);
    /**
     * The method visit() for VideoCard
     * @param videoCard
     */
    void visit(VideoCard videoCard);
}
