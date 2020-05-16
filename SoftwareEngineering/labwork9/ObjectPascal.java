package EngineeringSoftWare.labwork9;

/**
 * @author Illia Komisarov
 * Class for client extension.
 */
public class ObjectPascal implements Language {
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
