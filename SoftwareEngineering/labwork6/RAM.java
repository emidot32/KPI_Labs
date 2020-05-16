package EngineeringSoftWare.labwork6;

/**
 * The class RAM as computer component
 */
public class RAM implements Element{
    private int freeMemoryAmount;
    private int usedPower;

    /**
     * Constructor for initialize this vars:
     * @param memoryAmount - the amount of RAM.
     * @param usedPower - power which RAM consume for work.
     */
    public RAM(int memoryAmount, int usedPower){
        this.freeMemoryAmount = memoryAmount;
        this.usedPower = usedPower;
    }
    /**
     * The method decreaseFreeMemory() hammers memory.
     * User usually don't understand they harm computer.
     */
    public void decreaseFreeMemory(){
        freeMemoryAmount--;
    }
    /**
     * The method increaseFreeMemory() for visitor "Administrator".
     * It free memory.
     * Administrator can monitor and repair computer component.
     */
    public void increaseFreeMemory(){
        freeMemoryAmount++;
    }
    /**
     * Overridden method accept() call method visit() for RAM
     * @param visitor
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getFreeMemoryAmount() {
        return freeMemoryAmount;
    }

    public int getUsedPower() {
        return usedPower;
    }
}
