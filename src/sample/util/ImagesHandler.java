package sample.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ImagesHandler {
    public static File CopyAbsoluteFilesToRelativeFiles(File absoluteFile) {
        try {
            Files.copy(absoluteFile.toPath(), Path.of("./resources/" + absoluteFile.getName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new File("./resources/" + absoluteFile.getName());
    }

    public static String EscapeBackSlashes(String inputStr) {
        char[]  inputStrAsChars = inputStr.toCharArray();

        StringBuilder resultStr = new StringBuilder();

        for (char inputStrAsChar : inputStrAsChars) {
            if (inputStrAsChar == '\\') {
                resultStr.append('/');
            }
            resultStr.append(inputStrAsChar);
        }
        return resultStr.toString();
    }
}
