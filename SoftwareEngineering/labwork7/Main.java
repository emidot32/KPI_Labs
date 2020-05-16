package EngineeringSoftWare.labwork7;


/**
 * The class Main has all instances.
 * @author Illia Komisarov
 */
public class Main {
    public static void main(String[] args) {
        Pouring pouring = new Pouring();
        Retouch retouch = new Retouch();
        GraphicObject graphicObject = new GraphicObject();
        WorkSpace workSpace = new WorkSpace(graphicObject);
        workSpace.makeSomeEffect();

        workSpace.setTool(pouring);
        workSpace.makeSomeEffect();

        workSpace.setTool(retouch);
        workSpace.makeSomeEffect();
    }
}
