import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CodeWriter {
    BufferedWriter writer;

    public CodeWriter(String outputFile) throws IOException {
        writer = new BufferedWriter(new FileWriter(outputFile));
    }

    public void encode(Command command) {
        switch (command.getType()) {
            case C_ARITHMETIC -> System.out.println("Arithmetic");
            case C_PUSH -> System.out.println("Push");
            case C_POP -> System.out.println("Pop");
            case C_IF -> System.out.println("If");
            case C_CALL -> System.out.println("Call");
            case C_FUNCTION -> System.out.println("Function");
            case C_GOTO -> System.out.println("Goto");
            case C_LABEL -> System.out.println("Label");
            case C_RETURN -> System.out.println("Return");
        }

    }
}
