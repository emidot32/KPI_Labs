package EngineeringSoftWare.labwork9;
/**

 * @author Illia Komisarov
 * The interface Language sets programming language in IDE. It is "factory" for IDE's tools.
 */
public interface Language {
    /**
     * The method creates instance of Compiler. That is 'compile' some code.
     * @param code - code which will be compiled.
     * @return - instance of Compiler.
     */
    Compiler getCompilation(String code);
    /**
     * The method creates instance of Debugger. That is 'debug' some code.
     * @param code - code which will be debugged.
     * @return - instance of Debugger.
     */
    Debugger getDebugging(String code);
    /**
     * The method creates instance of Validator. That is 'valid' some code.
     * @param code - code which will be valid.
     * @return - instance of Validator.
     */
    Validator getValidation(String code);
}
