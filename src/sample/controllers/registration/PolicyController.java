package sample.controllers.registration;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.base.BaseRegistrationController;
import sample.database.Database;
import sample.database.tables.*;
import sample.model.*;
import sample.navigation.SceneConfig;
import sample.navigation.Switcher;
import sample.util.Const;
import sample.util.Converter;
import sample.util.Dialog;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The class controller that works with policies data
 *
 * @author kuraev_daniil
 */
public class PolicyController extends BaseRegistrationController implements Initializable {

    @FXML
    private TextField numberContractTextField;
    @FXML
    private TextField dateContractTextField;

    @FXML
    private TextField seriesPolicyTextField;
    @FXML
    private TextField numberPolicyTextField;
    @FXML
    private TextField startPolicyTextField;
    @FXML
    private TextField endPolicyTextField;
    @FXML
    private ComboBox<String> pricePolicyComboBox;
    @FXML
    private TextField amountPolicyTextField;
    @FXML
    private ComboBox<String> servicePolicyComboBox;

    @FXML
    private Button completeButton;
    @FXML
    private Button backButton;

    /**
     * Class-model ContractModel
     */
    private final ContractModel contractModel = new ContractModel();

    /**
     * Class-model PolicyModel
     */
    private final PolicyModel policyModel = new PolicyModel();

    /**
     * Class-model for the package registration that saved all info about client
     */
    private RegistrationUserModel registrationUserModel;

    /**
     * Database Field
     */
    private Database database = null;

    /**
     * Listener for View ComboBox
     */
    ObservableList<String> observablePriceList = FXCollections.observableArrayList("5000", "7500", "10000");

    /**
     * Listener for View ComboBox
     */
    ObservableList<String> observableServiceList = FXCollections.observableArrayList("ОСАГО", "КАСКО", "ОСАГО И КАСКО");

    /**
     * JavaFX library method for view initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        database = Database.getInstance();

        try {
            int result = database.getContract().getMaxId();

            if (result != -1) {
                numberContractTextField.setText(String.valueOf(result));
            } else {
                numberContractTextField.setText("AUTO GENERATE");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        dateContractTextField.setText(Converter.getCurrentDate());

        setHints();
        setListeners();
        setComboBoxes();
    }

    /**
     * The method that initializes setPromptText for View TextField
     */
    private void setHints() {
        numberContractTextField.setPromptText(Const.contractHint);
        dateContractTextField.setPromptText(Const.dateContractHint);

        seriesPolicyTextField.setPromptText(Const.seriesPolicyHint);
        numberPolicyTextField.setPromptText(Const.numberPolicyHint);
        startPolicyTextField.setPromptText(Const.startPolicyHint);
        endPolicyTextField.setPromptText(Const.endPolicyHint);
        amountPolicyTextField.setPromptText(Const.amountPolicy);
    }

    /**
     * The method that sets the listener for a ComboBox and sets the initial value
     */
    private void setComboBoxes() {
        pricePolicyComboBox.setItems(observablePriceList);
        pricePolicyComboBox.setValue("5000");

        servicePolicyComboBox.setItems(observableServiceList);
        servicePolicyComboBox.setValue("ОСАГО");
    }

    /**
     * The method that sets listeners for buttons
     */
    private void setListeners() {
        completeButton.setOnMouseClicked(mouseEvent -> {
            contractModel.setContractNumber(numberContractTextField.getText());
            contractModel.setDateContract(dateContractTextField.getText());

            policyModel.setSeries(seriesPolicyTextField.getText());
            policyModel.setNumber(numberPolicyTextField.getText());
            policyModel.setStart(startPolicyTextField.getText());
            policyModel.setEnd(endPolicyTextField.getText());
            policyModel.setAmount(amountPolicyTextField.getText());
            policyModel.setService(servicePolicyComboBox.getValue());
            policyModel.setPrice(pricePolicyComboBox.getValue());

            if (ContractModel.isEmpty(contractModel) || PolicyModel.isEmpty(policyModel)) {
                Dialog.showAlertDialogBase(
                        Const.dialogError,
                        Const.dialogEmptyFields,
                        Const.EMPTY_FIELD,
                        Alert.AlertType.WARNING
                );

                return;
            }

            String resultContractValidity = ContractModel.isValid(contractModel);
            if (!resultContractValidity.equals(Const.success)) {
                Dialog.showAlertDialogBase(
                        Const.dialogError,
                        resultContractValidity,
                        Const.EMPTY_FIELD,
                        Alert.AlertType.WARNING
                );

                return;
            }

            String resultPolicyValidity = PolicyModel.isValid(policyModel);
            if (!resultPolicyValidity.equals(Const.success)) {
                Dialog.showAlertDialogBase(
                        Const.dialogError,
                        resultPolicyValidity,
                        Const.EMPTY_FIELD,
                        Alert.AlertType.WARNING
                );

                return;
            }

            addDataToDatabase();

            registrationUserModel.getEventHome().updateTableView();

            closeWindow(completeButton.getScene().getWindow());
        });

        backButton.setOnMouseClicked(mouseEvent -> {
            openScene(
                    new SceneConfig(
                            Const.pathSceneRegistrationCar,
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
     * The method that adds all customer data to the database
     */
    private void addDataToDatabase() {
        int idClient = getClientId();
        int idCar = getCarId();

        policyModel.setIdCar(idCar);

        int idPolicy = getPolicyId();

        contractModel.setIdPolicy(idPolicy);
        contractModel.setIdClient(idClient);

        ArrayList<Integer> idCarDriver = getCarDriverId();

        addPolicyAndCarDriver(idCarDriver, idPolicy);
        addContract();
    }

    /**
     * The method that returns the client id
     *
     * @return - id client
     */
    private int getClientId() {
        ClientDbTable clientDbTable = database.getClient();

        addClientToDatabase(clientDbTable);

        try {
            return clientDbTable.getClientId(
                    registrationUserModel.getClientModel().getPasswordSeries(),
                    registrationUserModel.getClientModel().getPasswordNumber()
            );
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return -1;
    }

    /**
     * The method that adds a client to the database
     *
     * @param clientDbTable - model with customer data
     */
    private void addClientToDatabase(ClientDbTable clientDbTable) {
        try {
            int result = clientDbTable.getClientId(
                    registrationUserModel.clientModel.getPasswordSeries(),
                    registrationUserModel.clientModel.getPasswordNumber()
            );

            if (result == -1) {
                clientDbTable.insertClient(registrationUserModel.clientModel);
            } else {
                return;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Method that returns car id
     *
     * @return car id
     */
    private int getCarId() {
        CarDbTable carDbTable = database.getCar();

        addCarToDatabase(carDbTable);

        try {
            return carDbTable.getCarId(
                    registrationUserModel.getCarModel().getVin(),
                    registrationUserModel.getCarModel().getRegistrationPlate()
            );
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return -1;
    }

    /**
     * The method that adds a car to the database
     *
     * @param carDbTable - model with vehicle data
     */
    private void addCarToDatabase(CarDbTable carDbTable) {
        try {
            carDbTable.insertCar(registrationUserModel.getCarModel());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Method that returns car driver id
     *
     * @return list car ids
     */
    private ArrayList<Integer> getCarDriverId() {
        CarDriverDbTable carDriverDbTable = database.getCarDriver();

        addCarDriverToDatabase(carDriverDbTable);

        try {
            ArrayList<Integer> listCarDriverId = new ArrayList<>();
            for (CarDriverModel carDriver : registrationUserModel.getDriverModels()) {
                listCarDriverId.add(carDriverDbTable.getCarDriverId(
                        carDriver.getSeries(),
                        carDriver.getNumber()));
            }

            return listCarDriverId;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return new ArrayList<>();
    }

    /**
     * The method that adds a driver to the database
     *
     * @param carDriverDbTable - driver data model
     */
    private void addCarDriverToDatabase(CarDriverDbTable carDriverDbTable) {
        try {
            for (CarDriverModel carDriver : registrationUserModel.getDriverModels()) {
                carDriverDbTable.insertCarDriver(carDriver);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Method that returns policy id
     *
     * @return policy id
     */
    private int getPolicyId() {
        PolicyDbTable policyDbTable = database.getPolicy();

        addPolicyToDatabase(policyDbTable);

        try {
            return policyDbTable.getPolicyId(
                    policyModel.getSeries(),
                    policyModel.getNumber()
            );
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return -1;
    }

    /**
     * Method that adds the policy to the database
     *
     * @param policyDbTable - model with policy data
     */
    private void addPolicyToDatabase(PolicyDbTable policyDbTable) {
        try {
            policyDbTable.insertPolicy(policyModel);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * A method that adds a client to the database
     *
     * @param listCarDriverIds - driver id list
     * @param idPolicy         - policy id
     */
    private void addPolicyAndCarDriver(ArrayList<Integer> listCarDriverIds, int idPolicy) {
        PolicyAndDriverDbTable policyAndDriverDbTable = database.getPolicyAndDriver();

        for (int idCarDriver : listCarDriverIds) {
            try {
                policyAndDriverDbTable.insertPolicyAndDriver(idPolicy, idCarDriver);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * A method that adds a contract to the database
     */
    private void addContract() {
        ContractDbTable contractDbTable = database.getContract();

        try {
            contractDbTable.insertContract(contractModel);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
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
    }

    /**
     * A method that closes the current program window
     *
     * @param window - current window object
     */
    public void closeWindow(Window window) {
        Stage stage = (Stage) window;
        stage.close();
    }
}
