import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private String currentCommand;
    private CommandType currCommandType;
    private String currArg1;
    private String currArg2;


    private List<String> commands = new ArrayList<>();

    public Parser(String vmFilePath) throws IOException {
        if (!vmFilePath.split("\\.")[1].equals("vm")) {
            throw new IllegalArgumentException("Must be a .vm file");
        }
        parse(new BufferedReader(new FileReader(vmFilePath)));
    }

    private void parse(BufferedReader bufferedReader) throws IOException {
        String s = bufferedReader.readLine();
        if (isWhitespaceOrCommand(s)) return;
        currentCommand = s;
        if (isArithmeticInstruction(s)) {
            currArg1 = s;
            currArg2 = null;
            currCommandType = CommandType.C_ARITHMETIC;
        }
        if (isPushInstruction(s)) {
            // ....
        }

    }

    private boolean isArithmeticInstruction(String s) {
        return (s.contains("add") ||
                s.contains("sub") ||
                s.contains("neg") ||
                s.contains("eq")  ||
                s.contains("gt")  ||
                s.contains("lt")  ||
                s.contains("and") ||
                s.contains("or")  ||
                s.contains("not"));
    }

    private boolean isWhitespaceOrCommand(String s) {
        s = s.trim();
        return s.startsWith("//") || s.isBlank();
    }


}
