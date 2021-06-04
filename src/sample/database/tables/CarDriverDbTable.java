package sample.database.tables;

import sample.model.CarDriverModel;

import java.sql.*;

public class CarDriverDbTable {
    private Connection connection = null;

    private CarDriverDbTable() {
    }

    public CarDriverDbTable(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method that insert data car driver to database
     *
     * @param carDriverModel - car driver data model
     * @throws SQLException database request
     */
    public void insertCarDriver(CarDriverModel carDriverModel) throws SQLException {
        String request = "INSERT INTO car_driver(`surname`, `name`, `middleName`, `licenseSeries`, `licenseNumber`) " +
                "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(request);

        preparedStatement.setString(1, carDriverModel.getSurname());
        preparedStatement.setString(2, carDriverModel.getName());
        preparedStatement.setString(3, carDriverModel.getMiddleName());
        preparedStatement.setString(4, carDriverModel.getSeries());
        preparedStatement.setString(5, carDriverModel.getNumber());

        preparedStatement.executeUpdate();
    }

    /**
     * Method that searches car driver id
     *
     * @param licenseSeries - car driver data series
     * @param licenseNumber - car driver data number
     * @return car driver id
     * @throws SQLException database request
     */
    public int getCarDriverId(String licenseSeries, String licenseNumber) throws SQLException {
        Statement statement = connection.createStatement();

        String request = String.format("SELECT id FROM car_driver WHERE `licenseSeries` = '%s' AND `licenseNumber` = '%s' LIMIT 1", licenseSeries, licenseNumber);

        ResultSet resultSet = statement.executeQuery(request);

        if (resultSet.next()) {
            return resultSet.getInt("id");
        } else {
            return -1;
        }
    }

    /**
     * Method that searches car driver model by id
     *
     * @param id - car driver policy series
     * @return car driver model
     * @throws SQLException database request
     */
    public CarDriverModel getCarDriverById(int id) throws SQLException {
        Statement statement = connection.createStatement();

        String request = String.format("SELECT * FROM car_driver WHERE `id` = '%s' LIMIT 1", id);

        ResultSet resultSet = statement.executeQuery(request);

        if (resultSet.next()) {
            return new CarDriverModel(
                    resultSet.getInt("id"),
                    resultSet.getString("surname"),
                    resultSet.getString("name"),
                    resultSet.getString("middleName"),
                    resultSet.getString("licenseSeries"),
                    resultSet.getString("licenseNumber")
            );
        } else {
            return null;
        }
    }

    /**
     * Method that updated car driver data by id
     *
     * @param carDriverModel - car driver data model
     * @throws SQLException database request
     */
    public void updateCarDriverById(CarDriverModel carDriverModel) throws SQLException {
        String request = "UPDATE car_driver " +
                "SET surname=?, name=?, middleName=?, licenseSeries=?, licenseNumber=? " +
                "WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(request);

        preparedStatement.setString(1, carDriverModel.getSurname());
        preparedStatement.setString(2, carDriverModel.getName());
        preparedStatement.setString(3, carDriverModel.getMiddleName());
        preparedStatement.setString(4, carDriverModel.getSeries());
        preparedStatement.setString(5, carDriverModel.getNumber());
        preparedStatement.setInt(6, carDriverModel.getId());

        preparedStatement.executeUpdate();
    }

    /**
     * Method that removed car driver by id
     *
     * @param idCarDriver - car driver id
     * @throws SQLException database request
     */
    public void removeCarDriverById(int idCarDriver) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM car_driver WHERE id = ?");

        preparedStatement.setInt(1, idCarDriver);

        preparedStatement.executeUpdate();
    }
}
