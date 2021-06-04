package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.database.Database;
import sample.database.tables.InsuranceEventDbTable;
import sample.database.tables.PolicyDbTable;
import sample.util.Converter;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The class controller that works with statistics data
 *
 * @author kuraev_daniil
 */
public class StatisticController implements Initializable {
    @FXML
    private TextField startTextField;
    @FXML
    private TextField endTextField;
    @FXML
    private ComboBox<String> serviceComboBox;
    @FXML
    private Button resultButton;
    @FXML
    private Label amountPaymentLabel;
    @FXML
    private Label sumContractsLabel;

    /**
     * Database field for the Policies table
     */
    private PolicyDbTable policyDbTable;

    /**
     * Database field for the Insurance case table
     */
    private InsuranceEventDbTable insuranceEventDbTable;

    /**
     * Listener for View ComboBox
     */
    ObservableList<String> observableServiceList = FXCollections.observableArrayList("ОСАГО", "КАСКО", "ОСАГО И КАСКО");

    /**
     * JavaFX library method for view initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        policyDbTable = Database.getInstance().getPolicy();
        insuranceEventDbTable = Database.getInstance().getInsuranceEvent();

        setHints();
        setListeners();
        setComboBoxes();
    }

    /**
     * The method that initializes setPromptText for View TextField
     */
    private void setHints() {
        startTextField.setPromptText("Дата начала");
        endTextField.setPromptText("Дата конца");
    }

    /**
     * The method that sets listeners for buttons
     */
    private void setListeners() {
        resultButton.setOnMouseClicked(mouseEvent -> {
            try {
                Date start = Converter.convertStringToSqlDate(startTextField.getText());
                Date end = Converter.convertStringToSqlDate(endTextField.getText());
                String service = serviceComboBox.getValue();

                int resultInsuranceSum = policyDbTable.getInsuranceSumByPeriod(
                        start,
                        end,
                        serviceComboBox.getValue()
                );

                amountPaymentLabel.setText(String.format("Сумма общих страх. выплат: %s", resultInsuranceSum));

                int resultAmountPayment = insuranceEventDbTable.getAmountPaymentByPeriod(
                        start,
                        end,
                        service
                );

                sumContractsLabel.setText(String.format("Сумма выплат за ДТП: %s", resultAmountPayment));
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        });
    }

    /**
     * The method that sets the listener for a ComboBox and sets the initial value
     */
    private void setComboBoxes() {
        serviceComboBox.setItems(observableServiceList);
        serviceComboBox.setValue("ОСАГО");
    }
}
