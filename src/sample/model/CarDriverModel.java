package sample.model;

import sample.util.Const;

public class CarDriverModel {
    private int id;
    private String surname;
    private String name;
    private String middleName;
    private String series;
    private String number;

    public CarDriverModel() {
        this(-1, "", "", "", "", "");
    }

    public CarDriverModel(
            int id,
            String surname,
            String name,
            String middleName,
            String series,
            String number
    ) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
        this.series = series;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
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

    public static Boolean isEmpty(CarDriverModel carDriverModel) {
        return carDriverModel.getSurname().isBlank() ||
                carDriverModel.getName().isBlank() ||
                carDriverModel.getMiddleName().isBlank() ||
                carDriverModel.getSeries().isBlank() ||
                carDriverModel.getNumber().isBlank();
    }

    public static String isValidFields(CarDriverModel carDriverModel) {
        String surname = carDriverModel.getSurname();
        if (!(surname.length() > 0 && surname.length() < 50)) {
            return String.format(Const.characterLimitExceeded, Const.surnameField);
        }

        String name = carDriverModel.getName();
        if (!(name.length() > 0 && name.length() < 50)) {
            return String.format(Const.characterLimitExceeded, Const.nameField);
        }

        String middleName = carDriverModel.getMiddleName();
        if (!(middleName.length() > 0 && middleName.length() < 50)) {
            return String.format(Const.characterLimitExceeded, Const.middleNameField);
        }

        String series = carDriverModel.getSeries();
        if (series.length() != 4) {
            return String.format(Const.characterLimitExceeded, Const.passportSeriesField);
        }

        String number = carDriverModel.getNumber();
        if (number.length() != 6) {
            return String.format(Const.characterLimitExceeded, Const.passportNumberField);
        }

        return Const.success;
    }
}
