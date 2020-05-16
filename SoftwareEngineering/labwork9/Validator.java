package EngineeringSoftWare.labwork9;

/**
 * @author Illia Komisarov
 * Class Compile can valid some code. Concrete tool for lang.
 */
public class Validator implements Tool {
    private String code;

    public Validator(String code){
        this.code = code;
    }
    /**
     * The method valid code.
     */
    @Override
    public void doSomething() {
        System.out.println("Valid the code: "+code);
    }
}
