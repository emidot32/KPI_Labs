package EngineeringSoftWare.labwork4;

import java.util.ArrayList;
import java.util.List;

/**
 *@author Illia Komisarov
 *@version 4.0.0
 * Class Main has method main() which creates all objects and executes program.
 */

public class Main {
    public static void main(String[] args) {
        Coordinates coords1 = new Coordinates(1,1);
        Coordinates coords2 = new Coordinates(1,2);
        Coordinates coords3 = new Coordinates(2,4);
        Factory factory = new Factory();
        List<VectorGraphObject> listOfObjects = new ArrayList<>();
        listOfObjects.add(factory.getObject("Line", coords1, coords2, coords3)); //Object was created
        listOfObjects.add(factory.getObject("Line", coords1, coords2, coords3)); //Object was not created
        listOfObjects.add(factory.getObject("Line", coords1, coords1, coords3)); //Object was created
        listOfObjects.add(factory.getObject("Triangle", coords1, coords2, coords3)); //Object was created
        listOfObjects.add(factory.getObject("Triangle", coords1, coords2, coords3)); //Object was not created
        listOfObjects.add(factory.getObject("Rectangle", coords1, coords2, coords3)); //Object was created
        for (VectorGraphObject object : listOfObjects){
            object.draw();
        }
    }
}
