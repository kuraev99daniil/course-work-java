package sample.database.tables;

import sample.model.ClientModel;
import sample.util.Converter;

import java.sql.*;
import java.util.ArrayList;

public class ClientDbTable {
    private Connection connection = null;

    private ClientDbTable() {
    }

    public ClientDbTable(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method that insert data client to database
     *
     * @param clientModel - client data model
     * @throws SQLException database request
     */
    public void insertClient(ClientModel clientModel) throws SQLException {
        String request = "INSERT INTO client(`surname`, `name`, `middleName`, `birthDay`, `passportSeries`, `passportNumber`, `address`, `photo`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(request);

        preparedStatement.setString(1, clientModel.getSurname());
        preparedStatement.setString(2, clientModel.getName());
        preparedStatement.setString(3, clientModel.getMiddleName());
        preparedStatement.setDate(4, Converter.convertStringToSqlDate(clientModel.getDateOfBirth()));
        preparedStatement.setString(5, clientModel.getPasswordSeries());
        preparedStatement.setString(6, clientModel.getPasswordNumber());
        preparedStatement.setString(7, clientModel.getAddress());
        preparedStatement.setString(8, clientModel.getPhotoPath());

        preparedStatement.executeUpdate();
    }

    /**
     * Method that searches client id
     *
     * @param passportSeries - client passport series
     * @param passportNumber - client passport number
     * @return client id
     * @throws SQLException database request
     */
    public int getClientId(String passportSeries, String passportNumber) throws SQLException {
        Statement statement = connection.createStatement();

        String request = String.format("SELECT id FROM client WHERE `passportSeries` = '%s' AND `passportNumber` = '%s' LIMIT 1", passportSeries, passportNumber);

        ResultSet resultSet = statement.executeQuery(request);

        if (resultSet.next()) {
            return resultSet.getInt("id");
        } else {
            return -1;
        }
    }

    /**
     * Method that returns all client data
     *
     * @return list client models
     * @throws SQLException database request
     */
    public ArrayList<ClientModel> getAllClient() throws SQLException {
        Statement statement = connection.createStatement();

        String request = "SELECT * FROM client";

        ResultSet resultSet = statement.executeQuery(request);

        ArrayList<ClientModel> clientModel = new ArrayList<>();

        while (resultSet.next()) {
            clientModel.add(
                    new ClientModel(
                            resultSet.getInt("id"),
                            resultSet.getString("surname"),
                            resultSet.getString("name"),
                            resultSet.getString("middleName"),
                            Converter.getFormattedSqlDate(resultSet.getDate("birthDay").toString()),
                            resultSet.getString("passportSeries"),
                            resultSet.getString("passportNumber"),
                            resultSet.getString("address"),
                            resultSet.getString("photo")
                    )
            );
        }

        return clientModel;
    }

    /**
     * Method that updated client data by id
     *
     * @param clientModel - client data model
     * @throws SQLException database request
     */
    public void updateClientById(ClientModel clientModel) throws SQLException {
        String request = "UPDATE client " +
                "SET surname=?, name=?, middleName=?, birthDay=?, passportSeries=?, passportNumber=?, address=?, photo=? " +
                "WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(request);

        preparedStatement.setString(1, clientModel.getSurname());
        preparedStatement.setString(2, clientModel.getName());
        preparedStatement.setString(3, clientModel.getMiddleName());
        preparedStatement.setDate(4, Converter.convertStringToSqlDate(clientModel.getDateOfBirth()));
        preparedStatement.setString(5, clientModel.getPasswordSeries());
        preparedStatement.setString(6, clientModel.getPasswordNumber());
        preparedStatement.setString(7, clientModel.getAddress());
        preparedStatement.setString(8, clientModel.getPhotoPath());
        preparedStatement.setInt(9, clientModel.getId());

        preparedStatement.executeUpdate();
    }

    /**
     * Method that removed client by id
     *
     * @param idClient - client id
     * @throws SQLException database request
     */
    public void removeClientById(int idClient) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM client WHERE id = ?");

        preparedStatement.setInt(1, idClient);

        preparedStatement.executeUpdate();
    }
}
