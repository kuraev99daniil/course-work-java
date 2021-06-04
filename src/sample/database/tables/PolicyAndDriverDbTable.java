package sample.database.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PolicyAndDriverDbTable {
    private Connection connection = null;

    private PolicyAndDriverDbTable() {
    }

    public PolicyAndDriverDbTable(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method that insert data policy and driver to database
     *
     * @param idPolicy    - policy id
     * @param idCarDriver - car driver id
     * @throws SQLException database request
     */
    public void insertPolicyAndDriver(int idPolicy, int idCarDriver) throws SQLException {
        String request = "INSERT INTO policy_driver(`idPolicyLink`, `idCarDriverLink`) " +
                "VALUES (?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(request);

        preparedStatement.setInt(1, idPolicy);
        preparedStatement.setInt(2, idCarDriver);

        preparedStatement.executeUpdate();
    }

    /**
     * Method that searches car driver ids
     *
     * @param idPolicy - policy id
     * @return list car driver ids
     */
    public List<Integer> getCarDriverIdsByPolicyId(int idPolicy) {
        try {
            ResultSet result = connection
                    .createStatement()
                    .executeQuery(
                            String.format("SELECT idCarDriverLink FROM policy_driver WHERE `idPolicyLink` = %s", idPolicy)
                    );

            ArrayList<Integer> listIdDrivers = new ArrayList<Integer>();

            while (result.next()) {
                listIdDrivers.add(result.getInt("idCarDriverLink"));
            }

            return listIdDrivers;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return null;
    }
}
