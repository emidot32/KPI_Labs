package EngineeringSoftWare.labwork6;

/**
 * Creation all instances and start program.
 */
public class Main {
    public static void main(String[] args) {
        Processor processor = new Processor("Intel Core i5", 100, 300);
        RAM ram = new RAM(256, 40);
        VideoCard videoCard = new VideoCard("NVIDIA geForce 1080", 2, 260);
        User user = new User("Vasia");
        Administrator administrator = new Administrator("Sergey");
        user.visit(processor);
        user.visit(ram);
        user.visit(videoCard);
        administrator.visit(processor);
        administrator.visit(ram);
        administrator.visit(videoCard);
    }
}
