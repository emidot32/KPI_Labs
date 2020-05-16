package EngineeringSoftWare.labwork7;


/**
 * The class WorkSpace changes tools on the work space.
 * @author Illia Komisrov
 */
public class WorkSpace {
    public Tool tool;
    /**
     * Set field 'too'.
     * @param tool - some tool.
     */
    void setTool(Tool tool){
        this.tool = tool;
    }

    /**
     * During creation instance we can change tool.
     * @param tool - some tool.
     */
    WorkSpace(Tool tool){
        this.setTool(tool);
    }

    /**
     * Call method makeSomeEffect() in the work space.
     */
    void makeSomeEffect(){
        tool.makeSomeEffect();
    }
}
