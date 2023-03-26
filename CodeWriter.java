import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CodeWriter {
    BufferedWriter writer;

    public CodeWriter(String outputFile) throws IOException {
        writer = new BufferedWriter(new FileWriter(outputFile));
    }

    public void writeArithmetic(String arg1) throws IOException {
        writer.write("// " + arg1 + "\n");
    }

    public void writePushPop(C commandType, String arg1, String arg2, String commandForComment) throws IOException {
        writer.write("// " + commandForComment + "\n");
    }

    public void close() throws IOException {
        writer.close();
    }
}
