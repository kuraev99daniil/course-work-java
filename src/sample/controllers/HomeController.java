package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.controllers.detail.DetailClientController;
import sample.controllers.registration.ClientController;
import sample.database.Database;
import sample.database.tables.ClientDbTable;
import sample.database.tables.InsuranceServiceDbTable;
import sample.database.tables.PricePolicyDbTable;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Modality;
import sample.enums.InsuranceServiceValues;
import sample.enums.PriceValues;
import sample.model.ClientModel;
import sample.model.converter.ConverterModel;
import sample.model.table.ClientTable;
import sample.navigation.Navigation;
import sample.navigation.ScreenConfig;
import sample.util.Const;
import sample.util.FileManager;
import sample.util.ImagesHandler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The Class-Controller of the start page at program startup
 *
 * @author kuraev_daniil
 */
public class HomeController implements Initializable {

    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField middleNameTextField;
    @FXML
    private TextField seriesTextField;
    @FXML
    private TextField numberTextField;
    @FXML
    private TextField dateOfBirthTextView;
    @FXML
    private TextField addressTextField;
    @FXML
    private ImageView clientImageView;

    @FXML
    private Button addingClientButton;
    @FXML
    private Button openingClientButton;
    @FXML
    private Button statisticButton;
    @FXML
    private Button savingButton;

    @FXML
    private TableView<ClientTable> clientTable;

    @FXML
    private TableColumn<ClientTable, String> columnSurname;
    @FXML
    private TableColumn<ClientTable, String> columnName;
    @FXML
    private TableColumn<ClientTable, String> columnMiddleName;
    @FXML
    private TableColumn<ClientTable, String> columnSeries;
    @FXML
    private TableColumn<ClientTable, String> columnNumber;
    @FXML
    private TableColumn<ClientTable, String> columnDateOfBirth;
    @FXML
    private TableColumn<ClientTable, String> columnAddress;

    /**
     * Filed-listener for the table of the client
     */
    private final ObservableList<ClientTable> clientTableObservableList = FXCollections.observableArrayList();

    /**
     * Database Field
     */
    private Database database = null;

    /**
     * Field with the data of the selected client in the table
     */
    private ClientTable selectedClient = null;

    /**
     * Interface to perform an action in the current view from another view
     */
    public interface EventHome {
        /**
         * The method that updates the clients table
         */
        void updateTableView();
    }

    /**
     * EventHome interface implementation
     */
    private final EventHome eventHome = this::showClientTable;

    /**
     * JavaFX library method for view initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        database = Database.getInstance();

        doRequestDatabase();

        setHints();
        setViewListeners();
        setClientColumns();
    }

    /**
     * The method that performs the necessary queries in the database for this view
     */
    private void doRequestDatabase() {
        insertDefaultDataToPricePolicy();
        insertDefaultDataToInsuranceService();

        showClientTable();
    }

    /**
     * The method that loads all clients from the database and displays them in the clients table
     */
    private void showClientTable() {
        if (!clientTableObservableList.isEmpty()) {
            clientTableObservableList.clear();
        }

        ArrayList<ClientModel> listClientModels = getAllUser();

        if (listClientModels != null) {
            for (ClientModel model : listClientModels) {
                clientTableObservableList.add(ConverterModel.convertClientModelToClientTable(model));
            }
        }
    }

    /**
     * The method that initializes setPromptText for View TextField
     */
    private void setHints() {
        clientTable.setPlaceholder(new Label(Const.tableHint));

        surnameTextField.setPromptText(Const.surnameHint);
        nameTextField.setPromptText(Const.nameHint);
        middleNameTextField.setPromptText(Const.middleNameHint);
        seriesTextField.setPromptText(Const.passportSeriesHint);
        numberTextField.setPromptText(Const.passportNumberHint);
        dateOfBirthTextView.setPromptText(Const.dateOfBirthHint);
        addressTextField.setPromptText(Const.addressHint);

        Image img = new Image(Const.pathDefaultImage);
        clientImageView.setImage(img);
    }

    /**
     * The method that sets listeners for buttons
     */
    private void setViewListeners() {
        addingClientButton.setOnMouseClicked(event -> {
            try {
                FXMLLoader fxmlLoader = new Navigation().openScreen(
                        new ScreenConfig(
                                Const.pathSceneRegistrationClient,
                                Const.registration,
                                Const.registrationWindowWidth,
                                Const.registrationWindowHeight,
                                Modality.APPLICATION_MODAL
                        )
                );

                ClientController clientController = fxmlLoader.getController();
                clientController.setEventHome(eventHome);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        openingClientButton.setOnMouseClicked(mouseEvent -> {
            if (selectedClient == null) {
                return;
            }

            ClientModel clientModel = new ClientModel(
                    selectedClient.getId(),
                    surnameTextField.getText(),
                    nameTextField.getText(),
                    middleNameTextField.getText(),
                    dateOfBirthTextView.getText(),
                    seriesTextField.getText(),
                    numberTextField.getText(),
                    addressTextField.getText(),
                    selectedClient.getPhotoPath()
            );

            if (ClientModel.isEmpty(clientModel)) {
                return;
            }

            try {
                FXMLLoader fxmlLoader = new Navigation().openScreen(
                        new ScreenConfig(
                                Const.pathSceneDetailClient,
                                Const.titleDetailClient,
                                Const.detailClientWidth,
                                Const.detailClientHeight,
                                Modality.APPLICATION_MODAL
                        )
                );

                DetailClientController detailClientController = fxmlLoader.getController();
                detailClientController.setClient(clientModel, eventHome);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        statisticButton.setOnMouseClicked(mouseEvent -> {
            try {
                new Navigation().openScreen(
                        new ScreenConfig(
                                Const.pathSceneStatistic,
                                Const.titleStatistic,
                                Const.statisticWidth,
                                Const.statisticHeight,
                                Modality.APPLICATION_MODAL
                        )
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        savingButton.setOnMouseClicked(mouseEvent -> {
            updateClientInfo();
        });

        clientImageView.setOnMouseClicked(mouseEvent -> {
            File resultFile = ImagesHandler.CopyAbsoluteFilesToRelativeFiles(FileManager.getImageFile(clientImageView.getScene().getWindow()));

            Image img = new Image(resultFile.toURI().toString());
            selectedClient.setPhotoPath(resultFile.toURI().toString());
            clientImageView.setImage(img);
        });
    }

    /**
     * The method that initializes the columns of the TableClient table
     */
    private void setClientColumns() {
        columnSurname.setCellValueFactory(column -> column.getValue().surnameProperty());
        columnName.setCellValueFactory(column -> column.getValue().nameProperty());
        columnMiddleName.setCellValueFactory(column -> column.getValue().middleNameProperty());
        columnSeries.setCellValueFactory(column -> column.getValue().seriesProperty());
        columnNumber.setCellValueFactory(column -> column.getValue().numberProperty());
        columnDateOfBirth.setCellValueFactory(column -> column.getValue().dateOfBirthProperty());
        columnAddress.setCellValueFactory(column -> column.getValue().addressProperty());

        clientTable.setItems(clientTableObservableList);

        clientTable.getSelectionModel().selectedItemProperty().addListener((observableValue, clientTable, t1) -> {
            if (t1 != null) {
                selectedClient = t1;

                surnameTextField.setText(t1.getSurname());
                nameTextField.setText(t1.getName());
                middleNameTextField.setText(t1.getMiddleName());
                seriesTextField.setText(t1.getSeries());
                numberTextField.setText(t1.getNumber());
                dateOfBirthTextView.setText(t1.getDateOfBirth());
                addressTextField.setText(t1.getAddress());

                Image img = new Image(t1.getPhotoPath());
                clientImageView.setImage(img);
            }
        });
    }

    /**
     * Filling the PricePolicy table with standard values
     */
    private void insertDefaultDataToPricePolicy() {
        PricePolicyDbTable pricePolicyDbTable = database.getPricePolicy();

        try {
            pricePolicyDbTable.getResultInsertPricePolicy(1, PriceValues.getValue(PriceValues.PRICE_5000));
            pricePolicyDbTable.getResultInsertPricePolicy(2, PriceValues.getValue(PriceValues.PRICE_7500));
            pricePolicyDbTable.getResultInsertPricePolicy(3, PriceValues.getValue(PriceValues.PRICE_10000));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Filling the InsuranceService table with standard values
     */
    private void insertDefaultDataToInsuranceService() {
        InsuranceServiceDbTable insuranceServiceDbTable = database.getInsuranceService();

        try {
            insuranceServiceDbTable.getResultInsertInsuranceService(1, InsuranceServiceValues.getValue(InsuranceServiceValues.OSAGO).toUpperCase(Locale.ROOT));
            insuranceServiceDbTable.getResultInsertInsuranceService(2, InsuranceServiceValues.getValue(InsuranceServiceValues.CASCO).toUpperCase(Locale.ROOT));
            insuranceServiceDbTable.getResultInsertInsuranceService(3, InsuranceServiceValues.getValue(InsuranceServiceValues.OSAGO_CASKO).toUpperCase(Locale.ROOT));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * The method that gets the whole list of users
     *
     * @return Returns the list of all clients in the database
     */
    private ArrayList<ClientModel> getAllUser() {
        try {
            return database.getClient().getAllClient();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }

    /**
     * The method that updates the data of the selected client in the database
     */
    private void updateClientInfo() {
        if (selectedClient == null) {
            return;
        }

        ClientDbTable clientDbTable = Database.getInstance().getClient();

        ClientModel clientModel = new ClientModel(
                selectedClient.getId(),
                surnameTextField.getText(),
                nameTextField.getText(),
                middleNameTextField.getText(),
                dateOfBirthTextView.getText(),
                seriesTextField.getText(),
                numberTextField.getText(),
                addressTextField.getText(),
                selectedClient.getPhotoPath()
        );

        if (ClientModel.isEmpty(clientModel)) {
            return;
        }

        try {
            clientDbTable.updateClientById(clientModel);

            showClientTable();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
