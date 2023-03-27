import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CodeWriter {


    public CodeWriter(String outputFile) throws IOException {
        writer = new BufferedWriter(new FileWriter(outputFile));
    }

    public void writeArithmetic(String arg1) throws IOException {
        writer.write("// " + arg1 + "\n");
        if (arg1.equals("add")) {
            writer.write(ADD);
        }

    }

    public void writePushPop(C commandType, String arg1, String arg2, String commandForComment) throws IOException {
        writer.write("// " + commandForComment + "\n");
    }

    public void close() throws IOException {
        writer.close();
    }

    BufferedWriter writer;

    String EQ = """
            
            """;

    String NEG = """
            @SP
            M=M-1          
            @SP
            A=M
            D=M    
            @a
            M=D
            @0
            D=A
            @a
            D=D-M
            @SP
            A=M
            M=D
            """;


    String ADD = """
            @SP
            M=M-1
            @SP
            A=M
            D=M
            @a
            M=D
            @SP
            M=M-1
            @SP
            A=M
            D=M
            @b
            M=D
            @a
            D=M
            @b
            D=D+M
            @SP
            A=M
            M=D
            @SP
            M=M+1      
            """;

    String SUB = """
            @SP
            M=M-1
            @SP
            A=M
            D=M
            @a
            M=D
            @SP
            M=M-1
            @SP
            A=M
            D=M
            @b
            M=D
            @a
            D=M
            @b
            D=M-D
            @SP
            A=M
            M=D
            @SP
            M=M+1      
            """;


}
