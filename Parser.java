import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Parser {
    private List<String> commands = new ArrayList<>();
    private int numOfCommands;
    private int currentCommandNumber;



    private String currentCommand;
    private C currCommandType;
    private String currArg1;
    private String currArg2;



    public Parser(String vmFilePath) throws IOException {
        if (!vmFilePath.endsWith(".vm")) {
            System.out.println(vmFilePath);
            throw new IllegalArgumentException("Must be a .vm file");
        }
        extractCommands(new BufferedReader(new FileReader(vmFilePath)));
        numOfCommands = commands.size();
        currentCommandNumber = 0;
    }

    public void advance() {
        currentCommand = commands.get(currentCommandNumber++);
        determineCommandType();
        switch (currCommandType) {
            case ARITHMETIC -> {
                currArg1 = currentCommand;
                currArg2 = null;
            }
            case PUSH, POP -> {
                currArg1 = currentCommand.split(" ")[1];
                currArg2 = currentCommand.split(" ")[2];
            }
        }
        if (isArithmeticInstruction()) {
            currArg1 = currentCommand;
            currArg2 = null;
            currCommandType = C.ARITHMETIC;
        }
    }

    private void determineCommandType() {
        if (isArithmeticInstruction()) {
            currCommandType = C.ARITHMETIC;
        } else if (isPushInstruction()) {
            currCommandType = C.PUSH;
        } else if (isPopInstruction()) {
            currCommandType = C.POP;
        }
    }

    private boolean isPopInstruction() {
        return currentCommand.contains("pop");
    }

    private boolean isPushInstruction() {
        return currentCommand.contains("push");
    }

    private void extractCommands(BufferedReader bufferedReader) throws IOException {
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            if (isWhitespaceOrCommand(s)) continue;
            commands.add(s);
        }
    }

    private boolean isArithmeticInstruction() {
        return Set.of("add", "sub", "neg", "eq", "gt", "lt", "and", "or", "not").stream().anyMatch(currentCommand::contains);
    }

    private boolean isWhitespaceOrCommand(String s) {
        s = s.trim();
        return s.startsWith("//") || s.isBlank();
    }


    public boolean hasMoreLines() {
        return currentCommandNumber < numOfCommands;
    }

    public C getCommandType() {
        return currCommandType;
    }

    public String getCommand() {
        return currentCommand;
    }


    public String getArg1() {
        return currArg1;
    }

    public String getArg2() {
        return currArg2;
    }
}
