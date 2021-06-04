package sample.database.tables;

import sample.model.PricePolicyModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PricePolicyDbTable {

    private Connection connection = null;

    private PricePolicyDbTable() {
    }

    public PricePolicyDbTable(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method that returns all price policy data
     *
     * @return list insurance price policy models
     */
    public List<PricePolicyModel> getPricePolicy() {
        ArrayList<PricePolicyModel> listResult = new ArrayList<>();

        try {
            ResultSet result = connection.createStatement().executeQuery("SELECT * FROM price_policy");

            while (result.next()) {
                Integer id = result.getInt("idPrice");
                Integer price = result.getInt("price");

                listResult.add(new PricePolicyModel(id, price));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return listResult;
    }

    /**
     * Method that insert data price policy to database
     *
     * @param id    - id price policy
     * @param price - price
     * @return result
     * @throws SQLException database request
     */
    public int getResultInsertPricePolicy(Integer id, Integer price) throws SQLException {
        return connection
                .createStatement()
                .executeUpdate(String.format("INSERT INTO price_policy(`idPrice`, `price`) " +
                        "VALUES ('%d', '%d') " +
                        "ON DUPLICATE KEY UPDATE " +
                        "`price` = '%d'", id, price, price));
    }
}
