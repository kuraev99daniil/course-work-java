package sample.model;

import sample.util.Const;
import sample.util.Converter;

public class CarModel {
    private int id = -1;
    private String vin = "";
    private String brand = "";
    private String yearRelease = "";
    private String registrationPlate = "";
    private String power = "";

    public CarModel() {
    }

    public CarModel(int id,
                    String vin,
                    String brand,
                    String yearRelease,
                    String registrationPlate,
                    String power) {
        this.id = id;
        this.vin = vin;
        this.brand = brand;
        this.yearRelease = yearRelease;
        this.registrationPlate = registrationPlate;
        this.power = power;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getYearRelease() {
        return yearRelease;
    }

    public void setYearRelease(String yearRelease) {
        this.yearRelease = yearRelease;
    }

    public String getRegistrationPlate() {
        return registrationPlate;
    }

    public void setRegistrationPlate(String registrationPlate) {
        this.registrationPlate = registrationPlate;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Boolean isEmpty(CarModel carModel) {
        return carModel.getBrand().isBlank() ||
                carModel.getYearRelease().isBlank() ||
                carModel.getRegistrationPlate().isBlank() ||
                carModel.getPower().isBlank() ||
                carModel.getVin().isBlank();
    }

    public static String isValidFields(CarModel carModel) {
        String vin = carModel.getVin();
        if (vin.length() != 17) {
            return String.format(Const.incorrectFormat, Const.vinField);
        }

        String brand = carModel.getBrand();
        if (!(brand.length() > 0 && brand.length() < 50)) {
            return String.format(Const.characterLimitExceeded, Const.brandField);
        }

        String yearRelease = carModel.getYearRelease();
        if (!Converter.isDate(yearRelease)) {
            return String.format(Const.incorrectFormatDate, Const.yearReleaseField);
        }

        String registrationPlate = carModel.getRegistrationPlate();
        if (!(registrationPlate.length() > 0 && registrationPlate.length() < 10)) {
            return String.format(Const.characterLimitExceeded, Const.registrationPlateField);
        }

        String power = carModel.getPower();
        if (!(power.length() > 0 && power.length() < 5)) {
            return String.format(Const.characterLimitExceeded, Const.powerField);
        }

        return Const.success;
    }
}
