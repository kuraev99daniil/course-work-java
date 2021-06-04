package sample.util;

import javafx.scene.control.Alert;

public class Dialog {
    public static void showAlertDialogBase(String title, String headerText, String contentText, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
