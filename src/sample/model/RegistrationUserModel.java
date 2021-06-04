package sample.model;

import sample.controllers.HomeController;

import java.util.ArrayList;

public class RegistrationUserModel {
    public ClientModel clientModel;
    public CarModel carModel;
    public ArrayList<CarDriverModel> carDriverModels;

    public HomeController.EventHome eventHome;

    public ClientModel getClientModel() {
        return clientModel;
    }

    public void setClientModel(ClientModel clientModel) {
        this.clientModel = clientModel;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public ArrayList<CarDriverModel> getDriverModels() {
        return carDriverModels;
    }

    public void setDriverModels(ArrayList<CarDriverModel> carDriverModels) {
        this.carDriverModels = carDriverModels;
    }

    public HomeController.EventHome getEventHome() {
        return eventHome;
    }

    public void setEventHome(HomeController.EventHome eventHome) {
        this.eventHome = eventHome;
    }
}
