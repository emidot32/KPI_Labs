package EngineeringSoftWare.labwork8;

/**
 * @author Illia Komisarov
 * Class Coordinates sets coordinates for some dot in Cartesian plane of coordinates.
 */

public class Coordinates {
    /**
     * Fields for setting coordinates of the dot - (x;y).
     */
    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Overridden method equals() compares equality of two coordinates.
     * @param otherObject - object of comparison.
     * @return boolean value - true if objects is equals.
     */
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        Coordinates other = (Coordinates) otherObject;
        return (other.getX() == this.getX() &&
                other.getY() == this.getY());

    }

    /**
     * Second overridden method for comfortable representation of coordinates.
     * @return (x;y)
     */
    public String toString() {
        return "(" + this.getX() + ";" + this.getY() + ")";
    }

    /**
     *
     * @return y coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @return x coordinate.
     */
    public int getX() {
        return x;
    }
}
