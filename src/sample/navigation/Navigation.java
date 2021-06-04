package sample.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {
    public FXMLLoader openScreen(ScreenConfig config) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(config.getPath()));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root, config.getWidth(), config.getHeight());
        stage.setTitle(config.getTitle());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(config.getModality());

        stage.show();

        return fxmlLoader;
    }
}
