import java.io.IOException;
import java.util.stream.Stream;

public class VMTranslator {
    public static void main(String[] args) throws IOException {
        String filePath = "./foo.vm";
        Parser parser = new Parser(filePath);
        CodeWriter codeWriter = new CodeWriter("./foo.asm");

        Command command = null;

        while((command = parser.getNextCommand()) != null) {
            codeWriter.encode(command);
            break;
        }

    }
}

