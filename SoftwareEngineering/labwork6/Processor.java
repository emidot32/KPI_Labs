package EngineeringSoftWare.labwork6;

/**
 * The class Processor as computer component
 */
public class Processor implements Element{
    private String name;
    private int tactSpeed;
    private int usedPower;

    /**
     * Constructor for initialize this vars:
     * @param name - processor name. For example "Inter Core i3"
     * @param tactSpeed - processor tact speed.
     * @param usedPower - power which processor consume for work.
     */
    public Processor(String name, int tactSpeed, int usedPower){
        this.name = name;
        this.tactSpeed = tactSpeed;
        this.usedPower = usedPower;
    }

    /**
     * The method optimize() for visitor "Administrator".
     * Administrator can monitor and repair computer component.
     */
    public void optimize(){
        tactSpeed++;
        usedPower--;
    }

    /**
     * The method load() worsens processor work.
     * User usually don't understand they harm computer.
     */
    public void load(){
        tactSpeed--;
        usedPower++;
    }

    /**
     * Overridden method accept() call method visit() for Processor
     * @param visitor
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String getName() {
        return name;
    }

    public int getTactSpeed() {
        return tactSpeed;
    }

    public int getUsedPower() {
        return usedPower;
    }
}
