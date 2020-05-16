package EngineeringSoftWare.labwork8;

/**
 *@author Illia Komisarov
 * Class Main has method main() which create all objects and execute program.
 */

public class Main {
    public static void main(String[] args) {
    	Coordinates coords1 = new Coordinates(1,1);
        Coordinates coords2 = new Coordinates(1,2);
        Coordinates coords3 = new Coordinates(2,4);
        Line line1 = new Line(coords1, coords2);
        Line line2 = new Line(coords2, coords3);
        Triangle triangle = new Triangle(line1, line2); //Object was created
        Rectangle rectangle = new Rectangle(line1, line2); //Object was created

        Factory factory = new Factory(line1);
        factory.makeCopy().draw();

        factory.setPrototype(triangle);
        factory.makeCopy().draw();

        factory.setPrototype(rectangle);
        factory.makeCopy().draw();
    }
}