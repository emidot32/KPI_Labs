package EngineeringSoftWare.labwork3;

import java.util.ArrayList;
import java.util.Date;

/**
 *@author Illia Komisarov
 *@version 3.0.0
 * Class Directory which can consist files and subdirectories
 * and has own info about size and date of creation and info about the saved files.
 */

public class Directory implements Component{
    /**
     * Directory name.
     */
    private String name;
    /**
     * List of files and subdirectories.
     */
    private ArrayList<Component> components;
    /**
     * Directory size.
     */
    private int size;
    /**
     * Date of creation. DD/MM/YYYY
     */
    private final Date date;
    /**
     * Directory constructor.
     */
    Directory(String name, int day, int mounth, int year) {
        this.name = name;
        date = new Date(day, mounth, year);
        components = new ArrayList<Component>();
    }
    /**
     * Method add() adds file or subdirectory to list "components".
     */

    public void add(Component component) {
        components.add(component);
    }
    /**
     * Method remove() removes file or subdirectory from list "components".
     */
    public void remove(Component component) {
        components.remove(component);
    }

    /**
     * Method ls() like command in Linux OS it shows info about directory.
     * Namely what files and subdirectories this directory has.
     */
    public void ls() {
        for (Component element: components) {
            System.out.println(element);
        }

    }
    /**
     * Calculates and returns directory size.
     */
    @Override
    public int getSize() {
        size = 0;
        if (!components.isEmpty()) {
            for (Component element: components) {
                size += element.getSize();
            }
        }
        return size;
    }
    @Override
    public String toString(){
        return "Directory: name = " + name + ", size = " + getSize() + ", date of creations = " + date;
    }

    @Override
    public Date getDateOfCreation() {
        return date;
    }

    @Override
    public String getName() {
        return name;
    }
}
