package EngineeringSoftWare.labwork4;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Illia Komisarov
 * @version 4.0.0
 * Class Factory decides to create new object or use old.
 */
public class Factory {
    private static final Map<String, Object[]> objects = new HashMap<>();

    /**
     * Method getObject() creates new objects and adds in the map.
     * @param className - key for creating new object.
     * @param coords1 - coordinate1 for line.
     * @param coords2 - coordinates2 for line.
     * @param coords3 - coordinates3 for second line.
     * @return new object.
     */
    public VectorGraphObject getObject(String className, Coordinates coords1, Coordinates coords2, Coordinates coords3){
        Object[] arrayOfParam = objects.get(className);
        if (arrayOfParam == null || arrayOfParam[1] != coords1 || arrayOfParam[2] != coords2 || arrayOfParam[3] != coords3){
            arrayOfParam = new Object[4];
            VectorGraphObject object;
            arrayOfParam[1] = coords1;
            arrayOfParam[2] = coords2;
            arrayOfParam[3] = coords3;
            switch (className){
                case ("Rectangle"):
                    object = new Rectangle(new Line(coords1, coords2), new Line(coords2, coords3));
                    arrayOfParam[0] = object;
                    break;
                case ("Triangle"):
                    object = new Triangle(new Line(coords1, coords2), new Line(coords2, coords3));
                    arrayOfParam[0] = object;
                    break;
                case ("Line"):
                    object = new Line(coords1, coords2);
                    arrayOfParam[0] = object;
                    break;
            }
            objects.put(className, arrayOfParam);
        }
        return (VectorGraphObject)arrayOfParam[0];
    }
}
