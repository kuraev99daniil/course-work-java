package sample.util;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FileManager {
    public static File getImageFile(Window window) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Open Image File");

        FileChooser.ExtensionFilter filter1 =
                new FileChooser.ExtensionFilter("All image files", "*.png", "*.jpg");

        fileChooser.getExtensionFilters().add(filter1);

        return fileChooser.showOpenDialog(window);
    }

    public static void copyFile(File source, Path dest) {
        try {
            Files.copy(source.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
