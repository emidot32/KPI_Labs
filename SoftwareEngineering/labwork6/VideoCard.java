package EngineeringSoftWare.labwork6;

/**
 * The class VideoCard as computer component
 */
public class VideoCard implements Element {
    private String name;
    private int memory;
    private int usedPower;
    private int temperature;

    /**
     * Constructor for initialize this vars:
     * @param name - video card name. For example "Nvidis geForce 950" .
     * @param memory - video card memory.
     * @param usedPower - power which processor consume for work.
     */
    public VideoCard(String name, int memory, int usedPower){
        this.name = name;
        this.memory = memory;
        this.usedPower = usedPower;
    }
    /**
     * The method burn() increase temperature of video card.
     * User usually don't understand they harm computer.
     */
    public void burn(){
        temperature = 100;
    }
    /**
     * The method cool() for visitor "Administrator".
     * It cool video card.
     * Administrator can monitor and repair computer component.
     */
    public void cool(){
        temperature = 30;
    }
    /**
     * Overridden method accept() call method visit() for VideoCard
     * @param visitor
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String getName() {
        return name;
    }

    public int getMemory() {
        return memory;
    }

    public int getUsedPower() {
        return usedPower;
    }

    public int getTemperature() {
        return temperature;
    }
}
