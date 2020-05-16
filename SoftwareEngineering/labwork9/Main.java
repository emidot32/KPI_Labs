package EngineeringSoftWare.labwork9;

/**
 *@author Illia Komisarov
 * Class Main has method main() which create all objects and execute program.
 */
public class Main {
    public static void main(String[] args) {
        Java java = new Java();
        C c = new C();
        java.getCompilation("public class Main { " +
                "public static void main (String[] args){" +
                "System.out.println(2+2);}}").doSomething();
        c.getCompilation("int main(){" +
                "char a;" +
                "printf(a);}").doSomething();
        java.getDebugging("public class Main {" +
                "public static void main (String[] args){" +
                "for (int i=0;i<5;i++){" +
                "System.out.println(i/5);}}}").doSomething();
        c.getValidation("int main(){" +
                "boolean flag;" +
                "flag++;}").doSomething();
    }
}