package sample.base;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.RegistrationUserModel;
import sample.navigation.SceneConfig;
import sample.navigation.Switcher;

import java.io.IOException;

/**
 * An abstract class that defines general parameters for classes in the registration package
 *
 * @author kuraev_daniil
 */
public abstract class BaseRegistrationController {
    /**
     * he method that sets the necessary parameters for the current fields of the class
     *
     * @param registrationUserModel - model registration package
     * @param switcher screen state
     */
    public abstract void setFullUserModel(RegistrationUserModel registrationUserModel, Switcher switcher);

    /**
     * The method that opens the other screen
     *
     * @param config - param screen
     * @param source - some object
     * @param data - model data registration
     */
    public void openScene(SceneConfig config, Object source, RegistrationUserModel data) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(config.getPath()));
            Parent root = loader.load();
            Scene sceneCar = new Scene(root, config.getWidth(), config.getHeight());
            Stage stage = (Stage) ((Node) source).getScene().getWindow();
            stage.setScene(sceneCar);
            setInitialDataController(loader, data, config.getSwitcher());
            stage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * A method that passes parameters to another controller
     *
     * @param loader - FXMLoader
     * @param data - data model
     * @param switcher - state screen
     */
    private void setInitialDataController(FXMLLoader loader, RegistrationUserModel data, Switcher switcher) {
        BaseRegistrationController controller = loader.getController();
        controller.setFullUserModel(data, switcher);
    }
}
