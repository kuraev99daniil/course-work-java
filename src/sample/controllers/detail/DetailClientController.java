package sample.controllers.detail;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.controllers.HomeController;
import sample.database.Database;
import sample.database.tables.*;
import sample.model.*;
import sample.model.converter.ConverterModel;
import sample.model.table.CarDriverTable;
import sample.model.table.ContractTable;
import sample.model.table.InsuranceEventTable;
import sample.navigation.Navigation;
import sample.navigation.ScreenConfig;
import sample.util.Const;
import sample.util.Dialog;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The class controller that works with detail client data
 *
 * @author kuraev_daniil
 */
public class DetailClientController implements Initializable {

    @FXML
    private Button saveChangeButton;
    @FXML
    private Button removeContractButton;

    @FXML
    private TableView<InsuranceEventTable> insuranceEventTable;
    @FXML
    private TableColumn<InsuranceEventTable, String> dateInsuranceEventColumn;
    @FXML
    private TableColumn<InsuranceEventTable, String> descriptionInsuranceEventColumn;
    @FXML
    private TableColumn<InsuranceEventTable, Integer> amountInsuranceEventColumn;
    @FXML
    private TableColumn<InsuranceEventTable, String> serviceInsuranceEventColumn;

    @FXML
    private Button addInsuranceEventButton;
    @FXML
    private Button editInsuranceEventButton;
    @FXML
    private Button removeInsuranceEventButton;

    @FXML
    private Button addCarDriverButton;
    @FXML
    private Button editCarDriverButton;
    @FXML
    private Button removeCarDriverButton;

    @FXML
    private TableView<CarDriverTable> carDriverTable;
    @FXML
    private TableColumn<CarDriverTable, String> surnameCarDriverColumn;
    @FXML
    private TableColumn<CarDriverTable, String> nameCarDriverColumn;
    @FXML
    private TableColumn<CarDriverTable, String> middleNameCarDriverColumn;
    @FXML
    private TableColumn<CarDriverTable, String> seriesCarDriverColumn;
    @FXML
    private TableColumn<CarDriverTable, String> numberCarDriverColumn;

    @FXML
    private TableView<ContractTable> contractTableView;

    @FXML
    private TableColumn<ContractTable, String> numberContractColumn;
    @FXML
    private TableColumn<ContractTable, String> dateContractColumn;

    @FXML
    private TextField vinTextView;
    @FXML
    private TextField brandTextView;
    @FXML
    private TextField yearReleaseTextView;
    @FXML
    private TextField registrationPlateTextView;
    @FXML
    private TextField powerTextView;

    @FXML
    private TextField policySeriesTextView;
    @FXML
    private TextField policyNumberTextView;
    @FXML
    private TextField beginningTextView;
    @FXML
    private TextField endingTextView;
    @FXML
    private TextField insuranceSumTextView;
    @FXML
    private ComboBox<String> serviceComboBox;
    @FXML
    private ComboBox<String> priceComboBox;

    @FXML
    private ImageView clientImageView;

    /**
     * Class-model ContractModel
     */
    private ContractModel contractModel = null;

    /**
     * Class-model Client Model for selected from client table
     */
    private ClientModel selectedClientModel = null;

    /**
     * Class-model Policy Model for selected from policy table
     */
    private PolicyModel currentPolicyModel = null;

    /**
     * Class-model CarModel
     */
    private CarModel currentCarModel = null;

    /**
     * Listener for View ComboBox
     */
    private final ObservableList<ContractTable> listObservableContracts = FXCollections.observableArrayList();

    /**
     * Listener for View ComboBox
     */
    private final ObservableList<CarDriverTable> listObservableCarDriver = FXCollections.observableArrayList();

    /**
     * Listener for View ComboBox
     */
    private final ObservableList<InsuranceEventTable> listObservableInsuranceEvent = FXCollections.observableArrayList();

    /**
     * Database field for the List Contract table
     */
    private List<ContractModel> listContractModel;

    /**
     * Database field for the contract table
     */
    private ContractDbTable contractDbTable = null;

    /**
     * Database field for the policy table
     */
    private PolicyDbTable policyDbTable = null;

    /**
     * Database field for the car table
     */
    private CarDbTable carDbTable = null;

    /**
     * Database field for the PolicyAndCarDriver table
     */
    private PolicyAndDriverDbTable policyAndDriverDbTable = null;

    /**
     * Database field for the CarDriver table
     */
    private CarDriverDbTable carDriverDbTable = null;

    /**
     * Database field for the car InsuranceEvent table
     */
    private InsuranceEventDbTable insuranceEventDbTable = null;

    /**
     * Database field for the car Client table
     */
    private ClientDbTable clientDbTable = null;

    /**
     * Class-model CarDriver Model for selected from CarDriver table
     */
    private CarDriverModel selectedDriverCar;

    /**
     * Class-model InsuranceEventModel Model for selected from InsuranceEventModel table
     */
    private InsuranceEventModel selectedInsuranceEvent;

    /**
     * Object of the listener
     */
    private HomeController.EventHome listenerEventHome;

    /**
     * Interface to perform an action in the current view from another view
     */
    public interface Event {
        /**
         * The method that updates the car driver table
         */
        void updateCarDriverTable();

        /**
         * The method that updates the insurance event table
         */
        void updateInsuranceEventTable();
    }

    /**
     * Object of the listener
     */
    public Event listener = new Event() {
        /**
         * interface implementation
         * */
        @Override
        public void updateCarDriverTable() {
            addDataToCarDriverTableView();
        }

        /**
         * interface implementation
         * */
        @Override
        public void updateInsuranceEventTable() {
            addDataToInsuranceEventTableView();
        }
    };

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
        contractDbTable = Database.getInstance().getContract();
        policyDbTable = Database.getInstance().getPolicy();
        carDbTable = Database.getInstance().getCar();
        policyAndDriverDbTable = Database.getInstance().getPolicyAndDriver();
        carDriverDbTable = Database.getInstance().getCarDriver();
        insuranceEventDbTable = Database.getInstance().getInsuranceEvent();
        clientDbTable = Database.getInstance().getClient();

        setHints();
        setListeners();
        setComboBoxes();
        setContractTable();
        setCarDriverTable();
        setInsuranceEventTable();
    }

    /**
     * The method that initializes setPromptText for View TextField
     */
    private void setHints() {
        contractTableView.setPlaceholder(new Label(Const.tableHint));
        carDriverTable.setPlaceholder(new Label(Const.tableHint));
        insuranceEventTable.setPlaceholder(new Label(Const.tableHint));

        policySeriesTextView.setPromptText(Const.seriesPolicyHint);
        policyNumberTextView.setPromptText(Const.numberPolicyHint);
        beginningTextView.setPromptText(Const.startPolicyHint);
        endingTextView.setPromptText(Const.endPolicyHint);
        insuranceSumTextView.setPromptText(Const.amountPolicy);

        vinTextView.setPromptText(Const.vinHint);
        brandTextView.setPromptText(Const.brandHint);
        yearReleaseTextView.setPromptText(Const.yearReleaseHint);
        registrationPlateTextView.setPromptText(Const.registrationPlateHint);
        powerTextView.setPromptText(Const.powerHint);
    }

    /**
     * The method that sets listeners for buttons
     */
    private void setListeners() {
        addCarDriverButton.setOnMouseClicked(mouseEvent -> {
            if (listObservableCarDriver.size() > 2) {
                Dialog.showAlertDialogBase(
                        Const.dialogError,
                        Const.dialogLimitCarDriver,
                        Const.EMPTY_FIELD,
                        Alert.AlertType.WARNING
                );

                return;
            }

            try {
                FXMLLoader fxmlLoader = new Navigation().openScreen(
                        new ScreenConfig(
                                Const.pathSceneDetailCarDriver,
                                Const.titleCarDriver,
                                Const.carDriverWidth,
                                Const.carDriverHeight,
                                Modality.APPLICATION_MODAL
                        )
                );

                CarDriverController carDriverController = fxmlLoader.getController();
                carDriverController.setStartValues(null, listener, CarDriverController.State.ADD, currentPolicyModel.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        editCarDriverButton.setOnMouseClicked(mouseEvent -> {
            if (selectedDriverCar == null) {
                return;
            }

            try {
                FXMLLoader fxmlLoader = new Navigation().openScreen(
                        new ScreenConfig(
                                Const.pathSceneDetailCarDriver,
                                Const.titleCarDriver,
                                Const.carDriverWidth,
                                Const.carDriverHeight,
                                Modality.APPLICATION_MODAL
                        )
                );

                CarDriverController carDriverController = fxmlLoader.getController();
                carDriverController.setStartValues(selectedDriverCar, listener, CarDriverController.State.UPDATE, currentPolicyModel.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        removeCarDriverButton.setOnMouseClicked(mouseEvent -> {
                    if (listObservableCarDriver.size() != 2 && selectedDriverCar != null) {
                        return;
                    }

                    try {
                        carDriverDbTable.removeCarDriverById(selectedDriverCar.getId());

                        addDataToCarDriverTableView();
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }
        );

        removeContractButton.setOnMouseClicked(mouseEvent -> {
            try {
                policyDbTable.removePolicyById(currentPolicyModel.getId());

                if (listObservableContracts.size() == 1) {
                    List<ContractModel> listContractModel = contractDbTable.getListContractsByClientId(currentPolicyModel.getId());

                    if (listContractModel.size() == 0) {
                        clientDbTable.removeClientById(selectedClientModel.getId());
                    }

                    listenerEventHome.updateTableView();

                    closeScreen();
                }

                addDataToContractTableView();
                clearAllTextField();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });

        saveChangeButton.setOnMouseClicked(mouseEvent -> {
            PolicyModel policyModel = new PolicyModel(
                    currentPolicyModel.getId(),
                    policySeriesTextView.getText(),
                    policyNumberTextView.getText(),
                    beginningTextView.getText(),
                    endingTextView.getText(),
                    insuranceSumTextView.getText(),
                    priceComboBox.getValue(),
                    serviceComboBox.getValue(),
                    -1,
                    -1,
                    currentPolicyModel.getIdCar()
            );

            CarModel carModel = new CarModel(
                    currentCarModel.getId(),
                    vinTextView.getText(),
                    brandTextView.getText(),
                    yearReleaseTextView.getText(),
                    registrationPlateTextView.getText(),
                    powerTextView.getText()
            );

            if (PolicyModel.isEmpty(policyModel) || CarModel.isEmpty(carModel)) {
                Dialog.showAlertDialogBase(
                        Const.dialogError,
                        Const.dialogEmptyFields,
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

            try {
                policyDbTable.updatePolicyById(policyModel);
                carDbTable.updateCar(carModel);
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });

        addInsuranceEventButton.setOnMouseClicked(mouseEvent -> {
            try {
                FXMLLoader fxmlLoader = new Navigation().openScreen(
                        new ScreenConfig(
                                Const.pathSceneDetailInsuranceEvent,
                                Const.titleInsuranceEvent,
                                Const.insuranceEventWidth,
                                Const.insuranceEventHeight,
                                Modality.APPLICATION_MODAL
                        )
                );

                InsuranceEventController carDriverController = fxmlLoader.getController();
                carDriverController.setStartData(null, currentPolicyModel.getId(), listener, InsuranceEventController.State.ADD);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        editInsuranceEventButton.setOnMouseClicked(mouseEvent -> {
                    try {
                        FXMLLoader fxmlLoader = new Navigation().openScreen(
                                new ScreenConfig(
                                        Const.pathSceneDetailInsuranceEvent,
                                        Const.titleInsuranceEvent,
                                        Const.insuranceEventWidth,
                                        Const.insuranceEventHeight,
                                        Modality.APPLICATION_MODAL
                                )
                        );

                        InsuranceEventController carDriverController = fxmlLoader.getController();
                        carDriverController.setStartData(selectedInsuranceEvent, currentPolicyModel.getId(), listener, InsuranceEventController.State.UPDATE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );

        removeInsuranceEventButton.setOnMouseClicked(mouseEvent -> {
                    try {
                        insuranceEventDbTable.removeInsuranceEventById(selectedInsuranceEvent.getId());

                        addDataToInsuranceEventTableView();
                    } catch (SQLException exception) {
                        exception.printStackTrace();
                    }
                }
        );
    }

    /**
     * The method that sets the listener for a ComboBox and sets the initial value
     */
    private void setComboBoxes() {
        priceComboBox.setItems(observablePriceList);
        serviceComboBox.setItems(observableServiceList);
    }

    /**
     * A method that init view Contract Table
     */
    private void setContractTable() {
        numberContractColumn.setCellValueFactory(column -> column.getValue().contractNumberProperty());
        dateContractColumn.setCellValueFactory(column -> column.getValue().dateContractProperty());

        contractTableView.getSelectionModel().selectedItemProperty().addListener((observableValue, clientTable, t1) -> {
            if (t1 != null) {
                contractModel = ConverterModel.convertContractTableToContractModel(t1);

                int idCar = 0;
                try {
                    currentPolicyModel = policyDbTable.getPolicyById(t1.getIdPolicy());

                    idCar = currentPolicyModel.getIdCar();

                    policySeriesTextView.setText(currentPolicyModel.getSeries());
                    policyNumberTextView.setText(currentPolicyModel.getNumber());
                    beginningTextView.setText(currentPolicyModel.getStart());
                    endingTextView.setText(currentPolicyModel.getEnd());
                    insuranceSumTextView.setText(currentPolicyModel.getAmount());
                    serviceComboBox.setValue(currentPolicyModel.getService());
                    priceComboBox.setValue(currentPolicyModel.getPrice());

                    addDataToCarDriverTableView();
                    addDataToInsuranceEventTableView();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }

                try {
                    currentCarModel = carDbTable.getCarById(idCar);

                    vinTextView.setText(currentCarModel.getVin());
                    brandTextView.setText(currentCarModel.getBrand());
                    yearReleaseTextView.setText(currentCarModel.getYearRelease());
                    registrationPlateTextView.setText(currentCarModel.getRegistrationPlate());
                    powerTextView.setText(currentCarModel.getPower());
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        });

        contractTableView.setItems(listObservableContracts);
    }

    /**
     * A method that init view Car Driver Table
     */
    private void setCarDriverTable() {
        surnameCarDriverColumn.setCellValueFactory(column -> column.getValue().surnameProperty());
        nameCarDriverColumn.setCellValueFactory(column -> column.getValue().nameProperty());
        middleNameCarDriverColumn.setCellValueFactory(column -> column.getValue().middleNameProperty());
        seriesCarDriverColumn.setCellValueFactory(column -> column.getValue().seriesProperty());
        numberCarDriverColumn.setCellValueFactory(column -> column.getValue().numberProperty());

        carDriverTable.getSelectionModel().selectedItemProperty().addListener((observableValue, clientTable, t1) -> {
            if (t1 != null) {
                selectedDriverCar = ConverterModel.convertCarDriverTableToCarDriverModel(t1);
            }
        });

        carDriverTable.setItems(listObservableCarDriver);
    }

    /**
     * A method that init view Insurance Event Table
     */
    private void setInsuranceEventTable() {
        dateInsuranceEventColumn.setCellValueFactory(column -> column.getValue().dateProperty());
        descriptionInsuranceEventColumn.setCellValueFactory(column -> column.getValue().descriptionProperty());
        amountInsuranceEventColumn.setCellValueFactory(column -> column.getValue().amountPaymentProperty().asObject());
        serviceInsuranceEventColumn.setCellValueFactory(column -> column.getValue().serviceProperty());

        insuranceEventTable.getSelectionModel().selectedItemProperty().addListener((observableValue, clientTable, t1) -> {
            if (t1 != null) {
                selectedInsuranceEvent = ConverterModel.convertInsuranceTableToInsuranceModel(t1);
            }
        });

        insuranceEventTable.setItems(listObservableInsuranceEvent);
    }

    /**
     * A method that set current Client
     *
     * @param clientModel - model with data client
     * @param listener - listener
     */
    public void setClient(ClientModel clientModel, HomeController.EventHome listener) {
        this.selectedClientModel = clientModel;
        listenerEventHome = listener;

        setClientImageView(clientModel.getPhotoPath());

        addDataToContractTableView();
    }

    /**
     * A method that adds a Contract to the database
     */
    private void addDataToContractTableView() {
        if (!listObservableContracts.isEmpty()) {
            listObservableContracts.clear();
        }

        try {
            listContractModel = contractDbTable.getListContractsByClientId(selectedClientModel.getId());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        for (ContractModel model : listContractModel) {
            listObservableContracts.add(ConverterModel.convertContractModelToContractTable(model));
        }
    }

    /**
     * A method that adds a Car Driver to the database
     */
    private void addDataToCarDriverTableView() {
        selectedDriverCar = null;

        if (!listObservableCarDriver.isEmpty()) {
            listObservableCarDriver.clear();
        }

        List<Integer> listCarDriverIds = policyAndDriverDbTable.getCarDriverIdsByPolicyId(currentPolicyModel.getId());

        for (int id : listCarDriverIds) {
            try {
                CarDriverModel carDriverModel = carDriverDbTable.getCarDriverById(id);

                listObservableCarDriver.add(ConverterModel.convertCarDriverModelToCarDriverTable(carDriverModel));
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * A method that adds a Insurance Event to the database
     */
    private void addDataToInsuranceEventTableView() {
        selectedInsuranceEvent = null;

        if (!listObservableInsuranceEvent.isEmpty()) {
            listObservableInsuranceEvent.clear();
        }

        try {
            List<InsuranceEventModel> listInsuranceEvents = insuranceEventDbTable.getAllInsuranceEventsByIdPolicy(currentPolicyModel.getId());

            for (InsuranceEventModel model : listInsuranceEvents) {
                listObservableInsuranceEvent.add(ConverterModel.convertInsuranceEventModelToInsuranceEventTable(model));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * A method that set default background ImageView
     *
     * @param pathImage - path to selected image
     */
    private void setClientImageView(String pathImage) {
        Image img = new Image(pathImage);
        clientImageView.setImage(img);
    }

    /**
     * A method that closes the current program screen
     */
    private void closeScreen() {
        Stage stage = (Stage) removeContractButton.getScene().getWindow();
        stage.close();
    }

    /**
     * A method that cleared all TextFields
     */
    private void clearAllTextField() {
        vinTextView.setText("");
        brandTextView.setText("");
        yearReleaseTextView.setText("");
        registrationPlateTextView.setText("");
        powerTextView.setText("");

        policySeriesTextView.setText("");
        policyNumberTextView.setText("");
        beginningTextView.setText("");
        endingTextView.setText("");
        insuranceSumTextView.setText("");

        listObservableCarDriver.clear();
        listObservableInsuranceEvent.clear();

        observablePriceList.clear();
        observableServiceList.clear();
    }
}
