package sample.model.table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ContractTable {
    private final StringProperty contractNumber;
    private final StringProperty dateContract;

    private final IntegerProperty idClient;
    private final IntegerProperty idPolicy;

    public ContractTable(String contractNumber, String dateContract, int idClient, int idPolicy) {
        this.contractNumber = new SimpleStringProperty(contractNumber);
        this.dateContract = new SimpleStringProperty(dateContract);
        this.idClient = new SimpleIntegerProperty(idClient);
        this.idPolicy = new SimpleIntegerProperty(idPolicy);
    }

    public String getContractNumber() {
        return contractNumber.get();
    }

    public StringProperty contractNumberProperty() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber.set(contractNumber);
    }

    public String getDateContract() {
        return dateContract.get();
    }

    public StringProperty dateContractProperty() {
        return dateContract;
    }

    public void setDateContract(String dateContract) {
        this.dateContract.set(dateContract);
    }

    public int getIdClient() {
        return idClient.get();
    }

    public IntegerProperty idClientProperty() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient.set(idClient);
    }

    public int getIdPolicy() {
        return idPolicy.get();
    }

    public IntegerProperty idPolicyProperty() {
        return idPolicy;
    }

    public void setIdPolicy(int idPolicy) {
        this.idPolicy.set(idPolicy);
    }
}
