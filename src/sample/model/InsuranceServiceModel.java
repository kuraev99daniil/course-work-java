package sample.model;

public class InsuranceServiceModel {
    private Integer id = -1;
    private String service = "";

    public InsuranceServiceModel(Integer id, String service) {
        this.id = id;
        this.service = service;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
