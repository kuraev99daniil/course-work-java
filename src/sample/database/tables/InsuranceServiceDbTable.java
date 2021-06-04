package sample.database.tables;

import sample.model.InsuranceServiceModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InsuranceServiceDbTable {

    private Connection connection = null;

    private InsuranceServiceDbTable() {
    }

    public InsuranceServiceDbTable(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method that returns all insurance service data
     *
     * @return list insurance service models
     */
    public List<InsuranceServiceModel> getInsuranceService() {
        ArrayList<InsuranceServiceModel> listResult = new ArrayList<>();

        try {
            ResultSet result = connection.createStatement().executeQuery("SELECT * FROM insurance_service");

            while (result.next()) {
                Integer id = result.getInt("idPrice");
                String service = result.getString("price");

                listResult.add(new InsuranceServiceModel(id, service));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return listResult;
    }

    /**
     * Method that insert data insurance service to database
     *
     * @param id      - id insurance service
     * @param service - insurance service
     * @return result
     * @throws SQLException database request
     */
    public int getResultInsertInsuranceService(Integer id, String service) throws SQLException {
        return connection
                .createStatement()
                .executeUpdate(String.format("INSERT INTO insurance_service(`serviceId`, `service`) " +
                        "VALUES ('%d', '%s') " +
                        "ON DUPLICATE KEY UPDATE " +
                        "`service` = '%s'", id, service, service));
    }
}
