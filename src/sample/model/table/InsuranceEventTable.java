package sample.model.table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InsuranceEventTable {
    private IntegerProperty id;
    private StringProperty date;
    private StringProperty description;
    private IntegerProperty amountPayment;
    private IntegerProperty idService;
    private IntegerProperty idPolicy;

    private StringProperty service;

    public InsuranceEventTable() {
        this(-1, "", "", -1, -1, -1, "");
    }

    public InsuranceEventTable(
            int id,
            String date,
            String description,
            int amountPayment,
            int idService,
            int idPolicy,
            String service
    ) {
        this.id = new SimpleIntegerProperty(id);
        this.date = new SimpleStringProperty(date);
        this.description = new SimpleStringProperty(description);
        this.amountPayment = new SimpleIntegerProperty(amountPayment);
        this.idService = new SimpleIntegerProperty(idService);
        this.idPolicy = new SimpleIntegerProperty(idPolicy);
        this.service = new SimpleStringProperty(service);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public int getAmountPayment() {
        return amountPayment.get();
    }

    public IntegerProperty amountPaymentProperty() {
        return amountPayment;
    }

    public void setAmountPayment(int amountPayment) {
        this.amountPayment.set(amountPayment);
    }

    public int getIdService() {
        return idService.get();
    }

    public IntegerProperty idServiceProperty() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService.set(idService);
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

    public String getService() {
        return service.get();
    }

    public StringProperty serviceProperty() {
        return service;
    }

    public void setService(String service) {
        this.service.set(service);
    }
}
