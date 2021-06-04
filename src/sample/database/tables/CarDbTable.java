package sample.database.tables;

import sample.model.CarModel;
import sample.util.Converter;

import java.sql.*;

public class CarDbTable {
    private Connection connection = null;

    private CarDbTable() {
    }

    public CarDbTable(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method that insert data car to database
     *
     * @param carModel - car data model
     * @throws SQLException database request
     */
    public void insertCar(CarModel carModel) throws SQLException {
        String request = "INSERT INTO car(`vin`, `brand`, `yearRelease`, `registrationPlate`, `power`) " +
                "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(request);

        preparedStatement.setString(1, carModel.getVin());
        preparedStatement.setString(2, carModel.getBrand());
        preparedStatement.setDate(3, Converter.convertStringToSqlDate(carModel.getYearRelease()));
        preparedStatement.setString(4, carModel.getRegistrationPlate());
        preparedStatement.setInt(5, Integer.parseInt(carModel.getPower()));

        preparedStatement.executeUpdate();
    }

    public int getCarId(String vin, String registrationPlate) throws SQLException {
        Statement statement = connection.createStatement();

        String request = String.format("SELECT id FROM car WHERE `vin` = '%s' OR `registrationPlate` = '%s' LIMIT 1", vin, registrationPlate);

        ResultSet resultSet = statement.executeQuery(request);

        if (resultSet.next()) {
            return resultSet.getInt("id");
        } else {
            return -1;
        }
    }

    /**
     * Method that searches car id
     *
     * @param id - car id
     * @return data car model
     * @throws SQLException database request
     */
    public CarModel getCarById(int id) throws SQLException {
        Statement statement = connection.createStatement();

        String request = String.format("SELECT * FROM car WHERE `id` = %s LIMIT 1", id);

        ResultSet resultSet = statement.executeQuery(request);

        if (resultSet.next()) {
            return new CarModel(
                    resultSet.getInt("id"),
                    resultSet.getString("vin"),
                    resultSet.getString("brand"),
                    Converter.getFormattedSqlDate(resultSet.getString("yearRelease")),
                    resultSet.getString("registrationPlate"),
                    resultSet.getString("power")
            );
        } else {
            return null;
        }
    }

    /**
     * Method that updated car data by id
     *
     * @param carModel - car data model
     * @throws SQLException database request
     */
    public void updateCar(CarModel carModel) throws SQLException {
        String request = "UPDATE car " +
                "SET vin=?, brand=?, yearRelease=?, registrationPlate=?, power=? " +
                "WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(request);

        preparedStatement.setString(1, carModel.getVin());
        preparedStatement.setString(2, carModel.getBrand());
        preparedStatement.setDate(3, Converter.convertStringToSqlDate(carModel.getYearRelease()));
        preparedStatement.setString(4, carModel.getRegistrationPlate());
        preparedStatement.setInt(5, Integer.parseInt(carModel.getPower()));
        preparedStatement.setInt(6, carModel.getId());

        preparedStatement.executeUpdate();
    }
}
