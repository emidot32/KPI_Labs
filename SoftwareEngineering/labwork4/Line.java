package EngineeringSoftWare.labwork4;

/**
 * @author Illia Komisarov
 * @version 4.0.0
 * Class Line implements interface VectorGraphObject and it is primitive for creating objects of vector graphic.
 */

public class Line implements VectorGraphObject {
    /**
     * This fields set coordinates of two dots of the line.
     */
    private Coordinates coords1;
    private Coordinates coords2;


    public Line(Coordinates coords1, Coordinates cooeds2) {
        this.coords1 = coords1;
        this.coords2 = cooeds2;
    }

    /**
     * Overridden method draw() for Line.
     */
    @Override
    public void draw() {
        System.out.println("Draw the line with coordinates " + coords1 + " and " + coords2 + ".");
    }

    /**
     *
     * @return coordinates of first dot line.
     */
    public Coordinates getCoords1() {
        return coords1;
    }
    /**
     *
     * @return coordinates of second dot line.
     */
    public Coordinates getCoords2() {
        return coords2;
    }

}
