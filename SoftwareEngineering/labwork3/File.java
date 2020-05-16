package EngineeringSoftWare.labwork3;

import java.util.Date;

/**
 * @author Illia Komisarov
 * @version 3.0.0
 * Class File which has info about size and date of creation the file.
 */
class File implements Component {
    /**
     * File size.
     */
    private int size;
    /**
     * File name.
     */
    private String name;
    /**
     * Date of creation. DD/MM/YYYY
     */
    private Date date;

    /**
     * File constructor.
     */

     File(int size, String name, int day, int mounth, int year) {
        this.size = size;
        this.name = name;
        this.date = new Date(day, mounth, year);
    }

    public File(String pathFrom) {
    }

    @Override
    public String toString() {
        return "File: name = " + name + ", size = " + size + ", date of creation = " + date;
    }

    @Override
    public Date getDateOfCreation() {
        return date;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String getName() {
        return name;
    }
}
