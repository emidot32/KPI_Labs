package EngineeringSoftWare.labwork9;
/**
 * @author Illia Komisarov
 * Class Compile can compile some code. Concrete tool for lang.
 */
public class Compiler implements Tool {
    private String code;

    public Compiler(String code){
        this.code = code;
    }
    /**
     * The method compile code.
     */
    @Override
    public void doSomething() {
        System.out.println("Build and run the code: "+code);
    }
}
