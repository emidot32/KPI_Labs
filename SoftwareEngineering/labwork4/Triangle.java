package EngineeringSoftWare.labwork4;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Illia Komisarov
 * @version 4.0.0
 * Class Triangle implements interface VectorGraphObject and composes from Lines.
 */

public class Triangle implements VectorGraphObject {
    /**
     * List sidesOfTheTriangle stores the lines of which it consists.
     */
    public List<Line> sidesOfTheTriangle = new ArrayList<>();
    /**
     * List coordsOfNodes stores the coordinates of nodes the triangle.
     */
    private List<Coordinates> coordsOfNodes = new ArrayList<>();

    /**
     * Constructor for creating new object. Constructor check coordinates of lines on compatibility.
     * @param side1 - the line which is the first side of the triangle.
     * @param side2 - the line which is the second side of the triangle.
     * Third side is added automatically
     */
    public Triangle(Line side1, Line side2){
        sidesOfTheTriangle.add(side1);
        sidesOfTheTriangle.add(side2);
        Line side3;
        if (side1.getCoords1().equals(side2.getCoords1()) &&
                !side1.getCoords2().equals(side2.getCoords2())) {
            side3 = new Line(side1.getCoords2(), side2.getCoords2());
            sidesOfTheTriangle.add(side3);
            coordsOfNodes.add(side1.getCoords1());
            coordsOfNodes.add(side1.getCoords2());
            coordsOfNodes.add(side2.getCoords2());
        }
        else if (side1.getCoords1().equals(side2.getCoords2()) &&
                !side1.getCoords2().equals(side2.getCoords1())) {
            side3 = new Line(side1.getCoords2(), side2.getCoords1());
            sidesOfTheTriangle.add(side3);
            coordsOfNodes.add(side1.getCoords1());
            coordsOfNodes.add(side1.getCoords2());
            coordsOfNodes.add(side2.getCoords1());
        }
        else if (side1.getCoords2().equals(side2.getCoords1()) &&
                !side1.getCoords1().equals(side2.getCoords2())) {
            side3 = new Line(side1.getCoords1(), side2.getCoords2());
            sidesOfTheTriangle.add(side3);
            coordsOfNodes.add(side1.getCoords2());
            coordsOfNodes.add(side1.getCoords1());
            coordsOfNodes.add(side2.getCoords2());
        }
        else if (side1.getCoords2().equals(side2.getCoords2()) &&
                !side1.getCoords1().equals(side2.getCoords1())) {
            side3 = new Line(side1.getCoords1(), side2.getCoords1());
            sidesOfTheTriangle.add(side3);
            coordsOfNodes.add(side1.getCoords2());
            coordsOfNodes.add(side1.getCoords1());
            coordsOfNodes.add(side2.getCoords1());
        }

    }

    /**
     * Overridden method draw() which prints coordinates of nodes of the triangle.
     */
    @Override
    public void draw() {
        if (sidesOfTheTriangle.size() == 3){
            System.out.println("Draw triangle with coordinates of nodes:" + coordsOfNodes.get(0)+", "+ coordsOfNodes.get(1)+
                    ", "+ coordsOfNodes.get(2)+".");
        }
        else{
            System.out.println("The triangle can not be drawn because coordinates of lines are inappropriate.");
        }
    }
}
