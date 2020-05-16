package EngineeringSoftWare.labwork6;

/**
 * Visitor User is a bad visitor for computer. He destroys it.
 */
public class User implements Visitor {
    public String name;

    /**
     * Constructor
     * @param name - user name
     */
    public User(String name){
        this.name = name;
    }

    /**
     * The methods visit() make acts by user in computer and show info about condition of it.
     */
    @Override
    public void visit(Processor processor) {
        processor.load();
        System.out.println("Processor "+processor.getName() + " was loaded by " + name+ ". Tact speed = "+ processor.getTactSpeed() +
                ". Used power = "+processor.getUsedPower());
    }

    @Override
    public void visit(RAM memory) {
        memory.decreaseFreeMemory();
        System.out.println("RAM was wasted by " + name+ ". Amount of free memory = "+ memory.getFreeMemoryAmount() +
                ". Used power = " + memory.getUsedPower());
    }

    @Override
    public void visit(VideoCard videoCard) {
        videoCard.burn();
        System.out.println("Video card " + videoCard.getName() + " was burned by " + name+ ". The temperature = "+ videoCard.getTemperature() +
                ". Used power = " + videoCard.getUsedPower());
    }
}
