package sample.model;

import sample.util.Const;
import sample.util.Converter;

public class PolicyModel {
    private int id = -1;
    private String series = "";
    private String number = "";
    private String start = "";
    private String end = "";
    private String price = "";
    private String amount = "";
    private String service = "";

    private int idService = -1;
    private int idPrice = -1;
    private int idCar = -1;

    public PolicyModel() {
    }

    public PolicyModel(int id,
                       String series,
                       String number,
                       String start,
                       String end,
                       String amount,
                       String price,
                       String service,
                       int idService,
                       int idPrice,
                       int idCar) {
        this.id = id;
        this.series = series;
        this.number = number;
        this.start = start;
        this.end = end;
        this.amount = amount;
        this.service = service;
        this.price = price;
        this.idService = idService;
        this.idPrice = idPrice;
        this.idCar = idCar;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public int getIdPrice() {
        return idPrice;
    }

    public void setIdPrice(int idPrice) {
        this.idPrice = idPrice;
    }

    public int getIdCar() {
        return idCar;
    }

    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static boolean isEmpty(PolicyModel policyModel) {
        return policyModel.series.isBlank()
                || policyModel.number.isBlank()
                || policyModel.start.isBlank()
                || policyModel.end.isBlank()
                || policyModel.amount.isBlank()
                || policyModel.service.isBlank()
                || policyModel.price.isBlank();
    }

    public static String isValid(PolicyModel policyModel) {
        String series = policyModel.getSeries();
        if (series.length() != 3) {
            return String.format(Const.incorrectFormat, Const.seriesPolicyField);
        }

        String number = policyModel.getNumber();
        if (number.length() != 10) {
            return String.format(Const.incorrectFormat, Const.numberPolicyField);
        }

        String start = policyModel.getStart();
        if (!Converter.isDate(start)) {
            return String.format(Const.incorrectFormatDate, Const.startPolicyField);
        }

        String end = policyModel.getEnd();
        if (!Converter.isDate(end)) {
            return String.format(Const.incorrectFormatDate, Const.endPolicyField);
        }

        String price = policyModel.getPrice();
        if (!(price.length() > 0 && price.length() <= 5)) {
            return String.format(Const.incorrectFormat, Const.pricePolicyField);
        }

        String amount = policyModel.getAmount();
        if (!(amount.length() > 0 && amount.length() <= 10)) {
            return String.format(Const.incorrectFormat, Const.amountPolicyField);
        }

        String service = policyModel.getService();
        if (service.length() > 13) {
            return String.format(Const.incorrectFormat, Const.servicePolicyField);
        }

        return Const.success;
    }
}
