package sample.controllers.registration;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.base.BaseRegistrationController;
import sample.controllers.HomeController;
import sample.model.ClientModel;
import sample.model.RegistrationUserModel;
import sample.navigation.SceneConfig;
import sample.navigation.Switcher;
import sample.util.*;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The class controller that works with clients data
 *
 * @author kuraev_daniil
 */
public class ClientController extends BaseRegistrationController implements Initializable {
    @FXML
    private Button nextButton;
    @FXML
    private ImageView photoImageView;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField middleNameTextView;
    @FXML
    private TextField dateOfBirthTextView;
    @FXML
    private TextField passportSeriesTextView;
    @FXML
    private TextField numberPassportTextField;
    @FXML
    private TextField addressTextView;

    /**
     * Database field for the Client table
     */
    private ClientModel clientModel = new ClientModel();

    /**
     * Class-model for the package registration that saved all info about client
     */
    private RegistrationUserModel registrationUserModel = new RegistrationUserModel();

    /**
     * JavaFX library method for view initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setHints();
        setListeners();
        setDefaultImage();
    }

    /**
     * The method that initializes setPromptText for View TextField
     */
    private void setHints() {
        surnameTextField.setPromptText(Const.surnameHint);
        nameTextField.setPromptText(Const.nameHint);
        middleNameTextView.setPromptText(Const.middleNameHint);
        dateOfBirthTextView.setPromptText(Const.dateOfBirthHint);
        passportSeriesTextView.setPromptText(Const.passportSeriesHint);
        numberPassportTextField.setPromptText(Const.passportNumberHint);
        addressTextView.setPromptText(Const.addressHint);
    }

    /**
     * The method that sets listeners for buttons
     */
    private void setListeners() {
        photoImageView.setOnMouseClicked(mouseEvent -> {
            File resultFile = ImagesHandler.CopyAbsoluteFilesToRelativeFiles(FileManager.getImageFile(photoImageView.getScene().getWindow()));

            Image img = new Image(resultFile.toURI().toString());
            clientModel.setPhotoPath(resultFile.toURI().toString());
            photoImageView.setImage(img);
        });

        nextButton.setOnMouseClicked(mouseEvent -> {
            clientModel.setSurname(surnameTextField.getText());
            clientModel.setName(nameTextField.getText());
            clientModel.setMiddleName(middleNameTextView.getText());
            clientModel.setDateOfBirth(dateOfBirthTextView.getText());
            clientModel.setPasswordNumber(numberPassportTextField.getText());
            clientModel.setPasswordSeries(passportSeriesTextView.getText());
            clientModel.setAddress(addressTextView.getText());

            if (ClientModel.isEmpty(clientModel)) {
                Dialog.showAlertDialogBase(
                        Const.dialogError,
                        Const.dialogEmptyFields,
                        Const.EMPTY_FIELD,
                        Alert.AlertType.WARNING
                );

                return;
            }

            String resultValidity = ClientModel.isValidFields(clientModel);
            if (!resultValidity.equals(Const.success)) {
                Dialog.showAlertDialogBase(
                        Const.dialogError,
                        resultValidity,
                        Const.EMPTY_FIELD,
                        Alert.AlertType.WARNING
                );

                return;
            }

            registrationUserModel.setClientModel(clientModel);

            openScene(
                    new SceneConfig(
                            Const.pathSceneRegistrationCar,
                            Const.registrationWindowWidth,
                            Const.registrationWindowHeight,
                            Switcher.NEXT
                    ),
                    mouseEvent.getSource(),
                    registrationUserModel
            );
        });
    }

    /**
     * The method that sets start values for text fields
     */
    private void setStartValues() {
        if (registrationUserModel == null) {
            return;
        }

        clientModel = registrationUserModel.getClientModel();

        if (ClientModel.isEmpty(clientModel)) {
            return;
        }

        surnameTextField.setText(clientModel.getSurname());
        nameTextField.setText(clientModel.getName());
        middleNameTextView.setText(clientModel.getMiddleName());
        dateOfBirthTextView.setText(clientModel.getDateOfBirth());
        passportSeriesTextView.setText(clientModel.getPasswordSeries());
        numberPassportTextField.setText(clientModel.getPasswordNumber());
        addressTextView.setText(clientModel.getAddress());
        photoImageView.setImage(new Image(clientModel.getPhotoPath()));
    }

    /**
     * The method that sets a standard photo for ImageView
     */
    private void setDefaultImage() {
        Image img = new Image(Const.pathDefaultImage);
        photoImageView.setImage(img);
    }

    /**
     * The method that sets the necessary parameters for the current fields of the class
     *
     * @param registrationUserModel - all client data
     * @param switcher              - screen status
     */
    @Override
    public void setFullUserModel(RegistrationUserModel registrationUserModel, Switcher switcher) {
        this.registrationUserModel = registrationUserModel;

        if (switcher.equals(Switcher.BACK)) {
            setStartValues();
        }
    }

    /**
     * The method that stores the listener in the memory of another object
     *
     * @param eventHome - HomeController screen listener
     */
    public void setEventHome(HomeController.EventHome eventHome) {
        registrationUserModel.setEventHome(eventHome);
    }
}
