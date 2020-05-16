package EngineeringSoftWare.labwork3;

import java.util.Date;
/**
 *@author Illia Komisarov
 *@version 3.0.0
 */

public interface Component {
    /**
     * Returns size of file or directory.
     * @return size of component
     */
    int getSize();
    /**
     * Method which return date of creation
     * @return date of creation
     */
    Date getDateOfCreation();
    /**
     * Returns name.
     */
    String getName();
}
