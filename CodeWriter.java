import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CodeWriter {
    BufferedWriter writer;


    public CodeWriter(String outputFile) throws IOException {
        writer = new BufferedWriter(new FileWriter(outputFile));
        System.out.println(generatePushAssembly("static", "4", "foo"));
    }

    public void writeArithmetic(String arg1) throws IOException {
        writer.write("// " + arg1 + "\n");
        switch (arg1) {
            case "add" -> writer.write(ADD);
            case "sub" -> writer.write(SUB);
            case "neg" -> writer.write(NEG);
            case "eq" -> writer.write(EQ);
            case "gt" -> writer.write(GT);
            case "lt" -> writer.write(LT);
            case "and" -> writer.write(AND);
            case "or" -> writer.write(OR);
            case "not" -> writer.write(NOT);
        }
    }

    public void writePushPop(C commandType, String segment, String index, String commandForComment, String filePath) throws IOException {
        writer.write("// " + commandForComment + "\n");
        switch (commandType) {
            case PUSH -> writer.write(generatePushAssembly(segment, index, filePath));
            case POP  -> writer.write(generatePopAssembly(segment, index, filePath));
        }
    }

    private String generatePopAssembly(String segment, String index, String filePath) {
        switch (segment) {
            case "argument" -> { // passed
                return  """
                        @ARG
                        D=M 
                        @"""+index+
                        """
                        \nD=D+A 
                        @a
                        M=D
                        @SP
                        M=M-1
                        A=M
                        D=M
                        @a
                        A=M
                        M=D
                        """;
            }
            case "local" -> {
                return """
                        @LCL
                        D=M 
                        @"""+index+
                        """
                        \nD=D+A 
                        @a
                        M=D
                        @SP
                        M=M-1
                        A=M
                        D=M
                        @a
                        A=M
                        M=D
                        """;
            }
            case "this" -> {
                return """
                        @THIS
                        D=M 
                        @"""+index+
                        """
                        \nD=D+A 
                        @a
                        M=D
                        @SP
                        M=M-1
                        A=M
                        D=M
                        @a
                        A=M
                        M=D
                        """;
            }
            case "that" -> {
                return """
                        @THAT
                        D=M 
                        @"""+index+
                        """
                        \nD=D+A 
                        @a
                        M=D
                        @SP
                        M=M-1
                        A=M
                        D=M
                        @a
                        A=M
                        M=D
                        """;
            }
            case "static" -> { // passed
                return
                        """
                        @SP
                        M=M-1
                        A=M
                        D=M
                        """
                        +"@" +
                        filePath + "." + index + "\n" +
                        """
                        M=D
                        """;
            }
            case "pointer" -> {
                return """
                        @3
                        D=A
                        @"""+index+
                        """
                        \nD=D+A
                        @a
                        M=D
                        @SP
                        M=M-1
                        A=M
                        D=M
                        @a
                        A=M
                        M=D
                        """;
            }
            case "temp" -> { // passed
                return """
                        @5
                        D=A
                        @"""+index+
                        """
                        \nD=D+A
                        @a
                        M=D
                        @SP
                        M=M-1
                        A=M
                        D=M
                        @a
                        A=M
                        M=D
                        """;
            }
        }
        return "";
    }

    private String generatePushAssembly(String segment, String index, String filePath) {
        switch (segment) {
            case "argument" -> { // passed
                return """
                        @ARG
                        D=M
                        @"""+index+
                        """
                        \nA=D+A
                        D=M
                        """ + PUSH_LAST;
            }
            case "local" -> {
                return """
                        @LCL
                        D=M
                        @"""+index+
                        """                        
                        \nD=D+A
                        D=M
                        """ + PUSH_LAST;
            }
            case "static" -> { // passed
                return  "@" + filePath + "." + index + "\n" +
                        """
                        D=M
                        """ + PUSH_LAST;
            }
            case "constant" -> {
                return """
                        @"""+index+
                        """
                        \nD=A
                        """ + PUSH_LAST;
            }
            case "this" -> {
                return """
                        @THIS
                        D=M
                        @"""+index+
                        """
                        \nA=D+A
                        D=M
                        """ + PUSH_LAST;
            }
            case "that" -> {
                return """
                        @THAT
                        D=M
                        @"""+index+
                        """
                        \nA=D+A
                        D=M
                        """ + PUSH_LAST;
            }
            case "pointer" -> {
                return """
                        @3
                        D=A
                        @"""+index+
                        """
                        \nA=D+A
                        D=M
                        """ + PUSH_LAST;
            }
            case "temp" -> {
                return """
                        @5
                        D=A
                        @"""+index+
                        """
                        \nA=D+A
                        A=M
                        D=M
                        """ + PUSH_LAST;
            }
        }
        return "ERROR";
    }

    public void close() throws IOException {
        writer.write(ENDLOOP);
        writer.close();
    }

    String POP_LAST = // passed
            """
            @SP
            M=M-1
            A=M
            D=M
            """;

    String PUSH_LAST = // passed
            """
            @SP
            A=M
            M=D
            @SP
            M=M+1
            """;

    String AND  = POP_LAST + // passed
            """
            @a
            M=D
            """ + POP_LAST +
            """
            @a
            D=M&D
            """
            + PUSH_LAST;

    String OR = POP_LAST + // passed
            """
            @a
            M=D
            """ + POP_LAST +
            """
            @a
            D=M|D
            """
            + PUSH_LAST;

    String NOT = POP_LAST + // passed
            """
            D=!D
            """
            + PUSH_LAST;

    String GT = POP_LAST + // passed
            """
            @SP
            M=M-1
            A=M
            D=M-D
            @GREATER
            D;JGT
            D=0
            """ +
            PUSH_LAST +
            """
            @END
            0;JMP
            (GREATER)
            D=-1
            """ +
            PUSH_LAST +
            """
            (END)
            """;


    String LT = POP_LAST + // passed
            """
            @SP
            M=M-1
            A=M
            D=D-M
            @LESS
            D;JLE
            D=0
            """ +
            PUSH_LAST +
            """
            @END
            0;JMP
            (LESS)
            D=-1
            """ +
            PUSH_LAST +
            """
            (END)
            """;


    String EQ = POP_LAST + // passed
            """
            @SP
            M=M-1
            A=M
            D=D-M
            @NOT_EQUAL
            D;JNE
            D=-1
            """ +
            PUSH_LAST +
            """
            @END
            0;JMP
            (NOT_EQUAL)
            D=0
            """ +
            PUSH_LAST +
            """ 
            (END) 
            """;

    String NEG = POP_LAST+ // passed
            """
            D=-D
            """ +
            PUSH_LAST;


    String ADD = POP_LAST+ // passed
            """
            @a
            M=D
            """ +
            POP_LAST +
            """
            @a
            D=D+M
            """ +
            PUSH_LAST;


    String SUB = POP_LAST + // passed
            """
            @a
            M=D
            """ +
            POP_LAST +
            """
            @a
            D=D-M
            """ +
            PUSH_LAST;

    String ENDLOOP = """
            (END)
            @END
            0;JMP
            """;
}
