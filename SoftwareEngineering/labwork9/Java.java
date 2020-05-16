package EngineeringSoftWare.labwork9;

/**
 * @author Illia komisarov
 * Set some tools for Java lang. Concrete factory for tools.
 */
public class Java implements Language {
    @Override
    public Compiler getCompilation(String code) {
        return new Compiler(code);
    }

    @Override
    public Debugger getDebugging(String code) {
        return new Debugger(code);
    }

    @Override
    public Validator getValidation(String code) {
        return new Validator(code);
    }
}
