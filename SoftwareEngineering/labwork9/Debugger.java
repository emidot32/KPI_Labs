package EngineeringSoftWare.labwork9;
/**
 * @author Illia Komisarov
 * Class Debugger can debugg some code. Concrete tool for lang.
 */
public class Debugger implements Tool {
    private String code;

    public Debugger(String code){
        this.code = code;
    }
    /**
     * The method debug code.
     */
    @Override
    public void doSomething() {
        System.out.println("Start debugging code: "+code);
    }
}
