package sample.model.table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClientTable {
    private final IntegerProperty id;
    private final StringProperty surname;
    private final StringProperty name;
    private final StringProperty middleName;
    private final StringProperty series;
    private final StringProperty number;
    private final StringProperty dateOfBirth;
    private final StringProperty address;
    private StringProperty photoPath;

    public ClientTable(int id,
                       String surname,
                       String name,
                       String middleName,
                       String series,
                       String number,
                       String dateOfBirth,
                       String address,
                       String photoPath) {
        this.id = new SimpleIntegerProperty(id);
        this.surname = new SimpleStringProperty(surname);
        this.name = new SimpleStringProperty(name);
        this.middleName = new SimpleStringProperty(middleName);
        this.series = new SimpleStringProperty(series);
        this.number = new SimpleStringProperty(number);
        this.dateOfBirth = new SimpleStringProperty(dateOfBirth);
        this.address = new SimpleStringProperty(address);
        this.photoPath = new SimpleStringProperty(photoPath);
    }

    public ClientTable() {
        this(-1, "", "", "", "", "", "", "", "");
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getMiddleName() {
        return middleName.get();
    }

    public StringProperty middleNameProperty() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName.set(middleName);
    }

    public String getSeries() {
        return series.get();
    }

    public StringProperty seriesProperty() {
        return series;
    }

    public void setSeries(String series) {
        this.series.set(series);
    }

    public String getNumber() {
        return number.get();
    }

    public StringProperty numberProperty() {
        return number;
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    public String getDateOfBirth() {
        return dateOfBirth.get();
    }

    public StringProperty dateOfBirthProperty() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
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

    public String getPhotoPath() {
        return photoPath.get();
    }

    public StringProperty photoPathProperty() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath.set(photoPath);
    }
}
