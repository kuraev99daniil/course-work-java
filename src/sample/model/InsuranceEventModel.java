package sample.model;

public class InsuranceEventModel {
    private int id;
    private String date;
    private String description;
    private int amountPayment;
    private int idService;
    private int idPolicy;

    private String service;

    public InsuranceEventModel() {
        this(-1, "", "", -1, -1, -1, "");
    }

    public InsuranceEventModel(int id, String date, String description, int amountPayment, int idService, int idPolicy, String service) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.amountPayment = amountPayment;
        this.idService = idService;
        this.idPolicy = idPolicy;
        this.service = service;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmountPayment() {
        return amountPayment;
    }

    public void setAmountPayment(int amountPayment) {
        this.amountPayment = amountPayment;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public int getIdPolicy() {
        return idPolicy;
    }

    public void setIdPolicy(int idPolicy) {
        this.idPolicy = idPolicy;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
