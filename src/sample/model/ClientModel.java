package sample.model;

import sample.util.Const;
import sample.util.Converter;

public class ClientModel {
    private int id = -1;
    private String surname = "";
    private String name = "";
    private String middleName = "";
    private String dateOfBirth = "";
    private String passwordSeries = "";
    private String passwordNumber = "";
    private String address = "";
    private String photoPath = "";

    public ClientModel() {
    }

    public ClientModel(
            int id,
            String surname,
            String name,
            String middleName,
            String dateOfBirth,
            String passwordSeries,
            String passwordNumber,
            String address,
            String photoPath) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
        this.passwordSeries = passwordSeries;
        this.passwordNumber = passwordNumber;
        this.address = address;
        this.photoPath = photoPath;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPasswordSeries() {
        return passwordSeries;
    }

    public void setPasswordSeries(String passwordSeries) {
        this.passwordSeries = passwordSeries;
    }

    public String getPasswordNumber() {
        return passwordNumber;
    }

    public void setPasswordNumber(String passwordNumber) {
        this.passwordNumber = passwordNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Boolean isEmpty(ClientModel clientModel) {
        return clientModel.getSurname().isBlank() ||
                clientModel.getName().isBlank() ||
                clientModel.getMiddleName().isBlank() ||
                clientModel.getDateOfBirth().isBlank() ||
                clientModel.getPasswordNumber().isBlank() ||
                clientModel.getPasswordSeries().isBlank() ||
                clientModel.getAddress().isBlank() ||
                clientModel.getPhotoPath().isBlank();
    }

    public static String isValidFields(ClientModel clientModel) {
        String surname = clientModel.getSurname();
        if (!(surname.length() > 0 && surname.length() < 50)) {
            return String.format(Const.characterLimitExceeded, Const.surnameField);
        }

        String name = clientModel.getName();
        if (!(name.length() > 0 && name.length() < 50)) {
            return String.format(Const.characterLimitExceeded, Const.nameField);
        }

        String middleName = clientModel.getMiddleName();
        if (!(middleName.length() > 0 && middleName.length() < 50)) {
            return String.format(Const.characterLimitExceeded, Const.middleNameField);
        }

        String dateOfBirth = clientModel.getDateOfBirth();
        if (!Converter.isDate(dateOfBirth)) {
            return String.format(Const.incorrectFormatDate, Const.dateOfBirthField);
        }

        String passportSeries = clientModel.getPasswordSeries();
        if (passportSeries.length() != 4) {
            return String.format(Const.incorrectFormat, Const.passportSeriesField);
        }

        String passportNumber = clientModel.getPasswordNumber();
        if (passportNumber.length() != 6) {
            return String.format(Const.incorrectFormat, Const.passportNumberField);
        }

        String address = clientModel.getAddress();
        if (!(address.length() > 0 && address.length() < 150)) {
            return String.format(Const.characterLimitExceeded, Const.addressField);
        }

        return Const.success;
    }
}
