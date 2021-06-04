package sample.database.tables;

import sample.model.ContractModel;
import sample.util.Converter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContractDbTable {
    private Connection connection = null;

    private ContractDbTable() {
    }

    public ContractDbTable(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method that insert data contract to database
     *
     * @param contractModel - contract data model
     * @throws SQLException database request
     */
    public void insertContract(ContractModel contractModel) throws SQLException {
        String request = "INSERT INTO contract(`dateContract`, `idClient`, `idPolicy`) " +
                "VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(request);

        preparedStatement.setDate(1, Converter.convertStringToSqlDate(contractModel.getDateContract()));
        preparedStatement.setInt(2, contractModel.getIdClient());
        preparedStatement.setInt(3, contractModel.getIdPolicy());

        preparedStatement.executeUpdate();
    }

    /**
     * Method that searches contract by id
     *
     * @param clientId - client id
     * @return list client models
     * @throws SQLException database request
     */
    public List<ContractModel> getListContractsByClientId(int clientId) throws SQLException {
        Statement statement = connection.createStatement();

        String request = String.format("SELECT * FROM contract WHERE `idClient` = %d", clientId);

        ResultSet resultSet = statement.executeQuery(request);

        ArrayList<ContractModel> contractModels = new ArrayList<>();

        while (resultSet.next()) {
            contractModels.add(
                    new ContractModel(
                            String.valueOf(resultSet.getInt("contractNumber")),
                            Converter.getFormattedSqlDate(resultSet.getDate("dateContract").toString()),
                            resultSet.getInt("idClient"),
                            resultSet.getInt("idPolicy")
                    )
            );
        }

        return contractModels;
    }

    public int getMaxId() throws SQLException {
        Statement statement = connection.createStatement();

        String request = "SELECT * FROM contract ORDER BY contractNumber DESC LIMIT 1";

        ResultSet resultSet = statement.executeQuery(request);

        if (resultSet.next()) {
            return resultSet.getInt("contractNumber");
        }

        return -1;
    }
}
