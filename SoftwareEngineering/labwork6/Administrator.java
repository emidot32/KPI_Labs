package EngineeringSoftWare.labwork6;

/**
 * Visitor Administrator is a good visitor for computer. He repair it.
 */
public class Administrator implements Visitor {
    public String name;

    /**
     * Constructor
     * @param name - admin name
     */
    public Administrator(String name){
        this.name = name;
    }

    /**
     * The methods visit() make acts by administrator in computer and show info about condition of it.
     */
    @Override
    public void visit(Processor processor) {
        processor.optimize();
        System.out.println("Processor "+processor.getName() + " was optimized by " + name+ ". Tact speed = "+ processor.getTactSpeed() +
                ". Used power = "+processor.getUsedPower());
    }

    @Override
    public void visit(RAM memory) {
        memory.increaseFreeMemory();
        System.out.println("RAM was increased by " + name+ ". The amount of free memory = "+ memory.getFreeMemoryAmount() +
                ". Used power = " + memory.getUsedPower());
    }

    @Override
    public void visit(VideoCard videoCard) {
        videoCard.cool();
        System.out.println("Video card " + videoCard.getName() + " was cooled by " + name+ ". The temperature = "+ videoCard.getTemperature() +
                ". Used power = " + videoCard.getUsedPower());
    }
}
