package sample.controllers.registration;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.base.BaseRegistrationController;
import sample.model.CarModel;
import sample.model.CarDriverModel;
import sample.model.RegistrationUserModel;
import sample.navigation.SceneConfig;
import sample.navigation.Switcher;
import sample.util.Const;
import sample.util.Dialog;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * The class controller that works with Car data
 *
 * @author kuraev_daniil
 */
public class CarController extends BaseRegistrationController implements Initializable {
    @FXML
    private TextField vinTextField;
    @FXML
    private TextField brandTextView;
    @FXML
    private TextField yearReleaseTextView;
    @FXML
    private TextField registrationPlateTextView;
    @FXML
    private TextField powerTextView;

    @FXML
    private TextField nameOneTextView;
    @FXML
    private TextField surnameOneTextView;
    @FXML
    private TextField middleNameOneTextView;
    @FXML
    private TextField seriesOneTextView;
    @FXML
    private TextField numberOneTextView;

    @FXML
    private TextField nameTwoTextView;
    @FXML
    private TextField surnameTwoTextView;
    @FXML
    private TextField middleNameTwoTextView;
    @FXML
    private TextField seriesTwoTextView;
    @FXML
    private TextField numberTwoTextView;

    @FXML
    private Button nextButton;
    @FXML
    private Button backButton;

    /**
     * Database field for the Car table
     */
    private final CarModel carModel = new CarModel();

    /**
     * Database field for the CarDriver table
     */
    private final CarDriverModel driverOneModel = new CarDriverModel();

    /**
     * Database field for the CarDriver table
     */
    private final CarDriverModel driverTwoModel = new CarDriverModel();

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
    }

    /**
     * The method that initializes setPromptText for View TextField
     */
    private void setHints() {
        vinTextField.setPromptText(Const.vinHint);
        brandTextView.setPromptText(Const.brandHint);
        yearReleaseTextView.setPromptText(Const.yearReleaseHint);
        registrationPlateTextView.setPromptText(Const.registrationPlateHint);
        powerTextView.setPromptText(Const.powerHint);

        surnameOneTextView.setPromptText(Const.driverSurnameHint);
        nameOneTextView.setPromptText(Const.driverNameHint);
        middleNameOneTextView.setPromptText(Const.driverMiddleNameHint);
        seriesOneTextView.setPromptText(Const.driverSeriesHint);
        numberOneTextView.setPromptText(Const.driverNumberHint);

        surnameTwoTextView.setPromptText(Const.driverSurnameHint);
        nameTwoTextView.setPromptText(Const.driverNameHint);
        middleNameTwoTextView.setPromptText(Const.driverMiddleNameHint);
        seriesTwoTextView.setPromptText(Const.driverSeriesHint);
        numberTwoTextView.setPromptText(Const.driverNumberHint);
    }

    /**
     * The method that sets listeners for buttons
     */
    private void setListeners() {
        nextButton.setOnMouseClicked(mouseEvent -> {
            carModel.setVin(vinTextField.getText());
            carModel.setBrand(brandTextView.getText());
            carModel.setYearRelease(yearReleaseTextView.getText());
            carModel.setRegistrationPlate(registrationPlateTextView.getText());
            carModel.setPower(powerTextView.getText());

            driverOneModel.setSurname(surnameOneTextView.getText());
            driverOneModel.setName(nameOneTextView.getText());
            driverOneModel.setMiddleName(middleNameOneTextView.getText());
            driverOneModel.setSeries(seriesOneTextView.getText());
            driverOneModel.setNumber(numberOneTextView.getText());

            driverTwoModel.setSurname(surnameTwoTextView.getText());
            driverTwoModel.setName(nameTwoTextView.getText());
            driverTwoModel.setMiddleName(middleNameTwoTextView.getText());
            driverTwoModel.setSeries(seriesTwoTextView.getText());
            driverTwoModel.setNumber(numberTwoTextView.getText());

            if (CarModel.isEmpty(carModel) || CarDriverModel.isEmpty(driverOneModel)) {
                Dialog.showAlertDialogBase(
                        Const.dialogError,
                        Const.dialogEmptyFields,
                        Const.EMPTY_FIELD,
                        Alert.AlertType.WARNING
                );

                return;
            }

            String resultCarValidity = CarModel.isValidFields(carModel);
            if (!resultCarValidity.equals(Const.success)) {
                Dialog.showAlertDialogBase(
                        Const.dialogError,
                        resultCarValidity,
                        Const.EMPTY_FIELD,
                        Alert.AlertType.WARNING
                );

                return;
            }

            String resultDriverOneValidity = CarDriverModel.isValidFields(driverOneModel);
            if (!resultDriverOneValidity.equals(Const.success)) {
                Dialog.showAlertDialogBase(
                        Const.dialogError,
                        resultDriverOneValidity,
                        Const.EMPTY_FIELD,
                        Alert.AlertType.WARNING
                );

                return;
            }

            ArrayList<CarDriverModel> listDrivers = new ArrayList<>();

            listDrivers.add(driverOneModel);

            registrationUserModel.setCarModel(carModel);

            String resultDriverTwoValidity = CarDriverModel.isValidFields(driverTwoModel);
            if (!CarDriverModel.isEmpty(driverTwoModel) && resultDriverTwoValidity.equals(Const.success)) {
                listDrivers.add(driverTwoModel);
            } else {
                listDrivers.add(new CarDriverModel());
            }

            registrationUserModel.setDriverModels(listDrivers);

            openScene(
                    new SceneConfig(
                            Const.pathSceneRegistrationPolicy,
                            Const.registrationWindowWidth,
                            Const.registrationWindowHeight,
                            Switcher.NEXT
                    ),
                    mouseEvent.getSource(),
                    registrationUserModel
            );
        });

        backButton.setOnMouseClicked(mouseEvent -> {
            openScene(
                    new SceneConfig(
                            Const.pathSceneRegistrationClient,
                            Const.registrationWindowWidth,
                            Const.registrationWindowHeight,
                            Switcher.BACK
                    ),
                    mouseEvent.getSource(),
                    registrationUserModel
            );
        });
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
     * The method that sets start values for text fields
     */
    private void setStartValues() {
        if (registrationUserModel == null) {
            return;
        }

        CarModel carModel = registrationUserModel.getCarModel();
        vinTextField.setText(carModel.getVin());
        brandTextView.setText(carModel.getBrand());
        yearReleaseTextView.setText(carModel.getYearRelease());
        registrationPlateTextView.setText(carModel.getRegistrationPlate());
        powerTextView.setText(carModel.getPower());

        ArrayList<CarDriverModel> carDriverModel = registrationUserModel.getDriverModels();
        surnameOneTextView.setText(carDriverModel.get(0).getSurname());
        nameOneTextView.setText(carDriverModel.get(0).getName());
        middleNameOneTextView.setText(carDriverModel.get(0).getMiddleName());
        seriesOneTextView.setText(carDriverModel.get(0).getSeries());
        numberOneTextView.setText(carDriverModel.get(0).getNumber());

        surnameTwoTextView.setText(carDriverModel.get(1).getSurname());
        nameTwoTextView.setText(carDriverModel.get(1).getName());
        middleNameTwoTextView.setText(carDriverModel.get(1).getMiddleName());
        seriesTwoTextView.setText(carDriverModel.get(1).getSeries());
        numberTwoTextView.setText(carDriverModel.get(1).getNumber());
    }
}
