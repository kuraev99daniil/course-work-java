package sample.controllers.detail;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.Database;
import sample.database.tables.InsuranceEventDbTable;
import sample.model.InsuranceEventModel;
import sample.util.Const;
import sample.util.Converter;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The class controller that works with insurance event data
 *
 * @author kuraev_daniil
 */
public class InsuranceEventController implements Initializable {
    @FXML
    private TextField accidentNumberTextView;
    @FXML
    private TextField dateTextView;
    @FXML
    private TextField descriptionTextView;
    @FXML
    private ComboBox<String> serviceTextView;
    @FXML
    private TextField amountPaymentTextView;
    @FXML
    private Button applyButton;
    @FXML
    private Button cancelButton;

    /**
     * Policy id field
     */
    private int policyId;

    /**
     * Object of the listener
     */
    private DetailClientController.Event listener;

    /**
     * Current form state field
     */
    private State state;

    /**
     * Database field for the Insurance Event table
     */
    private InsuranceEventDbTable insuranceEventDbTable;

    /**
     * Listener for View ComboBox
     */
    ObservableList<String> observableServiceList = FXCollections.observableArrayList("ОСАГО", "КАСКО");

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
        insuranceEventDbTable = Database.getInstance().getInsuranceEvent();

        try {
            int result = insuranceEventDbTable.getMaxId();

            if (result != -1) {
                accidentNumberTextView.setText(String.valueOf(result));
            } else {
                accidentNumberTextView.setText("1");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        setHints();
        setListener();
    }

    /**
     * The method that initializes setPromptText for View TextField
     */
    private void setHints() {
        accidentNumberTextView.setPromptText(Const.accidentNumberHint);
        dateTextView.setPromptText(Const.dateAccidentNumberHint);
        descriptionTextView.setPromptText(Const.descriptionAccidentNumberHint);
        amountPaymentTextView.setPromptText(Const.amountPaymentAccidentNumberHint);

        serviceTextView.setItems(observableServiceList);
        serviceTextView.setValue("ОСАГО");
    }

    /**
     * The method that sets listeners for buttons
     */
    private void setListener() {
        applyButton.setOnMouseClicked(mouseEvent -> {
            InsuranceEventModel insuranceEventModel = new InsuranceEventModel(
                    Integer.parseInt(accidentNumberTextView.getText()),
                    dateTextView.getText(),
                    descriptionTextView.getText(),
                    Integer.parseInt(amountPaymentTextView.getText()),
                    -1,
                    policyId,
                    serviceTextView.getValue()
            );

            if (state == InsuranceEventController.State.ADD) {
                try {
                    insuranceEventDbTable.addInsuranceEvent(insuranceEventModel, policyId);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            } else {
                try {
                    insuranceEventDbTable.updateInsuranceEventById(insuranceEventModel);
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }

            listener.updateInsuranceEventTable();

            closeScreen();
        });

        cancelButton.setOnMouseClicked(mouseEvent -> {
            closeScreen();
        });
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
     * @param insuranceEventModel - data model insurance event model
     * @param policyId - policy id
     * @param listener - listener
     * @param state - screen state
     */
    public void setStartData(InsuranceEventModel insuranceEventModel, int policyId, DetailClientController.Event listener, InsuranceEventController.State state) {
        if (insuranceEventModel != null) {

            accidentNumberTextView.setText(String.valueOf(insuranceEventModel.getId()));
            dateTextView.setText(insuranceEventModel.getDate());
            descriptionTextView.setText(insuranceEventModel.getDescription());
            amountPaymentTextView.setText(String.valueOf(insuranceEventModel.getAmountPayment()));
            serviceTextView.setValue(insuranceEventModel.getService());
        }

        this.policyId = policyId;
        this.listener = listener;
        this.state = state;
    }
}
