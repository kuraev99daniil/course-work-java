package sample.controllers.detail;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.Database;
import sample.database.tables.CarDriverDbTable;
import sample.database.tables.PolicyAndDriverDbTable;
import sample.model.CarDriverModel;
import sample.util.Const;
import sample.util.Dialog;
import sample.util.Logger;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The class controller that works with car driver data
 *
 * @author kuraev_daniil
 */
public class CarDriverController implements Initializable {
    @FXML
    private TextField surnameTextView;
    @FXML
    private TextField nameTextView;
    @FXML
    private TextField middleNameTextView;
    @FXML
    private TextField seriesTextView;
    @FXML
    private TextField numberTextView;

    @FXML
    private Button applyButton;
    @FXML
    private Button cancelButton;

    /**
     * Policy id field
     */
    private int idPolicy;

    /**
     * Current form state field
     */
    private State state;

    /**
     * Class-model CarDriverModel
     */
    private CarDriverModel selectedDriver;

    /**
     * Object of the listener
     */
    private DetailClientController.Event listener;

    /**
     * Database field for the car driver table
     */
    private CarDriverDbTable carDriverDbTable;

    /**
     * Database field for the PolicyAndDriver table
     */
    private PolicyAndDriverDbTable policyAndDriverDbTable;

    /**
     * State form
     */
    public enum State {
        ADD, UPDATE;
    }

    /**
     * JavaFX library method for view initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carDriverDbTable = Database.getInstance().getCarDriver();
        policyAndDriverDbTable = Database.getInstance().getPolicyAndDriver();

        setHints();
        setListeners();
    }

    /**
     * The method that initializes setPromptText for View TextField
     */
    private void setHints() {
        surnameTextView.setPromptText(Const.driverSurnameHint);
        nameTextView.setPromptText(Const.driverNameHint);
        middleNameTextView.setPromptText(Const.driverMiddleNameHint);
        seriesTextView.setPromptText(Const.driverSeriesHint);
        numberTextView.setPromptText(Const.driverNumberHint);
    }

    /**
     * The method that sets listeners for buttons
     */
    private void setListeners() {
        applyButton.setOnMouseClicked(mouseEvent -> {
            CarDriverModel carDriverModel = new CarDriverModel(
                    -1,
                    surnameTextView.getText(),
                    nameTextView.getText(),
                    middleNameTextView.getText(),
                    seriesTextView.getText(),
                    numberTextView.getText()
            );

            if (CarDriverModel.isEmpty(carDriverModel)) {
                Dialog.showAlertDialogBase(
                        Const.dialogError,
                        Const.dialogEmptyFields,
                        Const.EMPTY_FIELD,
                        Alert.AlertType.WARNING
                );

                return;
            }

            String resultValidity = CarDriverModel.isValidFields(carDriverModel);
            if (!resultValidity.equals(Const.success)) {
                Dialog.showAlertDialogBase(
                        Const.dialogError,
                        resultValidity,
                        Const.EMPTY_FIELD,
                        Alert.AlertType.WARNING
                );

                return;
            }

            if (state == State.ADD) {
                try {
                    carDriverDbTable.insertCarDriver(carDriverModel);
                    Logger.Log(carDriverModel.getSeries());
                    int idCarDriver = carDriverDbTable.getCarDriverId(carDriverModel.getSeries(), carDriverModel.getNumber());
                    policyAndDriverDbTable.insertPolicyAndDriver(idPolicy, idCarDriver);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            } else {
                carDriverModel.setId(selectedDriver.getId());

                try {
                    carDriverDbTable.updateCarDriverById(carDriverModel);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }

            listener.updateCarDriverTable();

            closeScreen();
        });

        cancelButton.setOnMouseClicked(mouseEvent -> closeScreen());
    }

    /**
     * A method that closes the current program screen
     */
    private void closeScreen() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * The method that sets start values for text fields
     *
     * @param carDriverModel - model database car driver
     * @param listener - listener
     * @param idPolicy - policy id
     * @param state = screen state
     */
    public void setStartValues(CarDriverModel carDriverModel, DetailClientController.Event listener, State state, int idPolicy) {
        if (carDriverModel != null) {
            selectedDriver = carDriverModel;

            surnameTextView.setText(carDriverModel.getSurname());
            nameTextView.setText(carDriverModel.getName());
            middleNameTextView.setText(carDriverModel.getMiddleName());
            seriesTextView.setText(carDriverModel.getSeries());
            numberTextView.setText(carDriverModel.getNumber());
        }

        this.listener = listener;
        this.state = state;
        this.idPolicy = idPolicy;
    }
}
