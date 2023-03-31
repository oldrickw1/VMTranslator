import java.io.IOException;

public class VMTranslator {
    public static void main(String[] args) throws IOException {
        String filePath = "./StackTest.vm";
        String fileName = filePath.substring(0, filePath.length()-3);
        Parser parser = new Parser(filePath);
        CodeWriter codeWriter = new CodeWriter(fileName + ".asm");


        while(parser.hasMoreLines()) {
            parser.advance();
            switch (parser.getCommandType()) {
                case ARITHMETIC -> codeWriter.writeArithmetic(parser.getArg1());
                case PUSH, POP -> codeWriter.writePushPop(parser.getCommandType(), parser.getArg1(), parser.getArg2(), parser.getCommand(), filePath.split("\\.")[0]);
            }
        }
        codeWriter.close();

    }
}

