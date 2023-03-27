import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.SwitchPoint;

public class CodeWriter {
    BufferedWriter writer;


    public CodeWriter(String outputFile) throws IOException {
        writer = new BufferedWriter(new FileWriter(outputFile));
        System.out.println(EQ);
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

    public void writePushPop(C commandType, String segment, String index, String commandForComment) throws IOException {
        writer.write("// " + commandForComment + "\n");
        switch (commandType) {
            case C.PUSH -> writer.write(make);
        }

    }

    public void close() throws IOException {
        writer.close();
    }

    String POP_LAST = """
            @SP
            M=M-1
            A=M
            D=M
            """;

    String PUSH_LAST = """
            @SP
            A=M
            M=D
            @SP
            M=M+1
            """;

    String AND  = POP_LAST + """
            @a
            M=D
            """ + POP_LAST + """
            D=M&D
            """
            + PUSH_LAST;

    String OR = POP_LAST + """
            @a
            M=D
            """ + POP_LAST + """
            D=M|D
            """
            + PUSH_LAST;

    String NOT = POP_LAST + """
            D=!D
            """
            + PUSH_LAST;

    String GT = POP_LAST +
            """
            @SP
            M=M-1
            A=M
            D=D-M
            @GREATER
            D;JGT
            D=0
            """ +
            PUSH_LAST +
            """
            @END
            0;JMP
            (GREATER)
            D=1
            """ +
            PUSH_LAST +
            """
            (END)
            """;


    String LT = POP_LAST +
            """
            @SP
            M=M-1
            A=M
            D=M-D
            @LESS
            D;JLE
            D=-1
            """ +
            PUSH_LAST +
            """
            @END
            0;JMP
            (LESS)
            D=0
            """ +
            PUSH_LAST +
            """
            (END)
            """;


    String EQ = POP_LAST +
            """
            @SP
            M=M-1
            A=M
            D=D-M
            @NOT_EQUAL
            D;JNE
            """ +
            PUSH_LAST +
            """
            @END
            0;JMP
            (NOT_EQUAL)
            D=-1
            """ +
            PUSH_LAST +
            """ 
            (END) 
            """;

    String NEG = POP_LAST+
            """
            D=-D
            """ +
            PUSH_LAST;


    String ADD = POP_LAST+
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


    String SUB = POP_LAST +
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
