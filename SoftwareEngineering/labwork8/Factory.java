package EngineeringSoftWare.labwork8;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Illia Komisarov
 * Class Factory automates the process of making copies.
 */
public class Factory {
    private Line line;
    private Rectangle rectangle;
    private Triangle triangle;
    private int check = 0;

    /**
     * Overwritten constructors with other elements of the vector graphic.
     */
    public Factory(Line line){
        this.setPrototype(line);
    }
    public Factory(Rectangle rectangle){
        this.setPrototype(rectangle);
    }
    public Factory(Triangle triangle) {
        this.setPrototype(triangle);
    }

    /**
     * The methos setPrototype() changes type of the original of the vector graphic.
     * @param object
     */
    void setPrototype(VectorGraphObject object){
        if (object instanceof Line){
            this.line = (Line) object;
            check = 1;
        }
        else if (object instanceof Rectangle){
            this.rectangle = (Rectangle) object;
            check = 2;
        }
        else if (object instanceof Triangle){
            this.triangle = (Triangle) object;
            check = 3;
        }
    }

    /**
     * The method makeCopy() make copy the current original element.
     * @return copy of current element.
     */
    VectorGraphObject makeCopy(){
        if (check == 1){
            return line.copy();
        }
        else if(check == 2){
            return rectangle.copy();
        }
        return triangle.copy();
    }

}

