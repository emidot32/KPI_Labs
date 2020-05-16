package EngineeringSoftWare.labwork4;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Illia Komisarov
 * @version 4.0.0
 * Class Rectangle implements interface VectorGraphObject and composes from Lines.
 */

public class Rectangle implements VectorGraphObject {
    /**
     * List sidesOfTheTriangle stores the lines of which it consists.
     */
    public List<Line> sidesOfTheRectangle = new ArrayList<>();
    /**
     * List coordsOfNodes stores the coordinates of nodes the rectangle.
     */
    private List<Coordinates> coordsOfNodes = new ArrayList<>();
    /**
     * Constructor for creating new object. Constructor check coordinates of lines on compatibility.
     * @param side1 - the line which is the first side of the rectangle.
     * @param side2 - the line which is the second side of the rectangle.
     * Third and fourth sides are added automatically
     */
    public Rectangle(Line side1, Line side2) {
        sidesOfTheRectangle.add(side1);
        sidesOfTheRectangle.add(side2);
        Line side3;
        Line side4;
        if (side1.getCoords1().equals(side2.getCoords1()) &&
                !side1.getCoords2().equals(side2.getCoords2())) {
            Coordinates side3Coords2 = new Coordinates(side2.getCoords2().getX(), side1.getCoords2().getY());
            side3 = new Line(side2.getCoords2(), side3Coords2);
            side4 = new Line(side3.getCoords2(), side1.getCoords2());
            sidesOfTheRectangle.add(side3);
            sidesOfTheRectangle.add(side4);
            coordsOfNodes.add(side1.getCoords2());
            coordsOfNodes.add(side1.getCoords1());
            coordsOfNodes.add(side2.getCoords2());
            coordsOfNodes.add(side3Coords2);
        } else if (side1.getCoords1().equals(side2.getCoords2()) &&
                !side1.getCoords2().equals(side2.getCoords1())) {
            Coordinates side3Coords2 = new Coordinates(side2.getCoords1().getX(), side1.getCoords2().getY());
            side3 = new Line(side2.getCoords1(), side3Coords2);
            side4 = new Line(side3.getCoords2(), side1.getCoords2());
            sidesOfTheRectangle.add(side3);
            sidesOfTheRectangle.add(side4);
            coordsOfNodes.add(side1.getCoords2());
            coordsOfNodes.add(side1.getCoords1());
            coordsOfNodes.add(side2.getCoords1());
            coordsOfNodes.add(side3Coords2);
        } else if (side1.getCoords2().equals(side2.getCoords1()) &&
                !side1.getCoords1().equals(side2.getCoords2())) {
            Coordinates side3Coords2 = new Coordinates(side2.getCoords2().getX(), side1.getCoords1().getY());
            side3 = new Line(side2.getCoords2(), side3Coords2);
            side4 = new Line(side3.getCoords2(), side1.getCoords1());
            sidesOfTheRectangle.add(side3);
            sidesOfTheRectangle.add(side4);
            coordsOfNodes.add(side1.getCoords1());
            coordsOfNodes.add(side1.getCoords2());
            coordsOfNodes.add(side2.getCoords2());
            coordsOfNodes.add(side3Coords2);
        } else if (side1.getCoords2().equals(side2.getCoords2()) &&
                !side1.getCoords1().equals(side2.getCoords1()) ) {
            Coordinates side3Coords2 = new Coordinates(side2.getCoords1().getX(), side1.getCoords1().getY());
            side3 = new Line(side2.getCoords1(), side3Coords2);
            side4 = new Line(side3.getCoords2(), side1.getCoords1());
            sidesOfTheRectangle.add(side3);
            sidesOfTheRectangle.add(side4);
            coordsOfNodes.add(side1.getCoords1());
            coordsOfNodes.add(side1.getCoords2());
            coordsOfNodes.add(side2.getCoords1());
            coordsOfNodes.add(side3Coords2);
        }

    }
    /**
     * Overridden method draw() which prints coordinates of nodes of the rectangle.
     */
    @Override
    public void draw() {
        if (sidesOfTheRectangle.size() == 4) {
            System.out.println("Draw rectangle with coordinates of nodes:" + coordsOfNodes.get(0) + ", " + coordsOfNodes.get(1) +
                    ", " + coordsOfNodes.get(2) + ", " + coordsOfNodes.get(3)+".");
        } else {
            System.out.println("The rectangle can not be drawn because coordinates of lines are inappropriate.");
        }
    }
}
