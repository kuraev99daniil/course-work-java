package sample.database.tables;

import sample.enums.InsuranceServiceValues;
import sample.enums.PriceValues;
import sample.model.PolicyModel;
import sample.util.Converter;

import java.sql.*;
import java.util.Locale;

public class PolicyDbTable {
    private Connection connection = null;

    private PolicyDbTable() {
    }

    public PolicyDbTable(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method that insert data policy to database
     *
     * @param policyModel - policy data model
     * @throws SQLException database request
     */
    public void insertPolicy(PolicyModel policyModel) throws SQLException {
        String request = "INSERT INTO policy(`policySeries`, `policyNumber`, `insuranceSum`, `beginning`, `ending`, `idService`, `idPrice`, `idCar`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        policyModel.setIdService(InsuranceServiceValues.getDbKey(policyModel.getService().toUpperCase(Locale.ROOT)));
        policyModel.setIdPrice(PriceValues.getDbKey(Integer.parseInt(policyModel.getPrice())));

        PreparedStatement preparedStatement = connection.prepareStatement(request);

        preparedStatement.setString(1, policyModel.getSeries());
        preparedStatement.setString(2, policyModel.getNumber());
        preparedStatement.setInt(3, Integer.parseInt(policyModel.getAmount()));
        preparedStatement.setDate(4, Converter.convertStringToSqlDate(policyModel.getStart()));
        preparedStatement.setDate(5, Converter.convertStringToSqlDate(policyModel.getEnd()));
        preparedStatement.setInt(6, policyModel.getIdService());
        preparedStatement.setInt(7, policyModel.getIdPrice());
        preparedStatement.setInt(8, policyModel.getIdCar());

        preparedStatement.executeUpdate();
    }

    /**
     * Method that searches policy id
     *
     * @param policySeries - policy data series
     * @param policyNumber - policy data number
     * @return policy id
     * @throws SQLException database request
     */
    public int getPolicyId(String policySeries, String policyNumber) throws SQLException {
        Statement statement = connection.createStatement();

        String request = String.format("SELECT id FROM policy WHERE `policySeries` = '%s' AND `policyNumber` = '%s' LIMIT 1", policySeries, policyNumber);

        ResultSet resultSet = statement.executeQuery(request);

        if (resultSet.next()) {
            return resultSet.getInt("id");
        } else {
            return -1;
        }
    }

    /**
     * Method that searches policy model by id
     *
     * @param idPolicy - client policy series
     * @return policy model
     * @throws SQLException database request
     */
    public PolicyModel getPolicyById(int idPolicy) throws SQLException {
        Statement statement = connection.createStatement();

        String request = String.format("SELECT * FROM policy WHERE `id` = %s LIMIT 1", idPolicy);

        ResultSet resultSet = statement.executeQuery(request);

        if (resultSet.next()) {
            return new PolicyModel(
                    resultSet.getInt("id"),
                    resultSet.getString("policySeries"),
                    resultSet.getString("policyNumber"),
                    Converter.getFormattedSqlDate(resultSet.getString("beginning")),
                    Converter.getFormattedSqlDate(resultSet.getString("ending")),
                    resultSet.getString("insuranceSum"),
                    PriceValues.getDbValue(resultSet.getInt("idPrice")),
                    InsuranceServiceValues.getDbValue(resultSet.getInt("idService")).toUpperCase(Locale.ROOT),
                    resultSet.getInt("idService"),
                    resultSet.getInt("idPrice"),
                    resultSet.getInt("idCar")
            );
        } else {
            return null;
        }
    }

    /**
     * Method that removed policy by id
     *
     * @param idPolicy - policy id
     * @throws SQLException database request
     */
    public void removePolicyById(int idPolicy) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM policy WHERE id = ?");

        preparedStatement.setInt(1, idPolicy);

        preparedStatement.executeUpdate();
    }

    /**
     * Method that updated policy data by id
     *
     * @param policyModel - policy data model
     * @throws SQLException database request
     */
    public void updatePolicyById(PolicyModel policyModel) throws SQLException {
        String request = "UPDATE policy " +
                "SET policySeries=?, policyNumber=?, beginning=?, ending=?, insuranceSum=?, idService=?, idPrice=? " +
                "WHERE id=?";

        policyModel.setIdService(InsuranceServiceValues.getDbKey(policyModel.getService().toUpperCase(Locale.ROOT)));
        policyModel.setIdPrice(PriceValues.getDbKey(Integer.parseInt(policyModel.getPrice())));

        PreparedStatement preparedStatement = connection.prepareStatement(request);

        preparedStatement.setString(1, policyModel.getSeries());
        preparedStatement.setString(2, policyModel.getNumber());
        preparedStatement.setDate(3, Converter.convertStringToSqlDate(policyModel.getStart()));
        preparedStatement.setDate(4, Converter.convertStringToSqlDate(policyModel.getEnd()));
        preparedStatement.setInt(5, Integer.parseInt(policyModel.getAmount()));
        preparedStatement.setInt(6, policyModel.getIdService());
        preparedStatement.setInt(7, policyModel.getIdPrice());
        preparedStatement.setInt(8, policyModel.getId());

        preparedStatement.executeUpdate();
    }

    /**
     * Method that returns statistic data
     *
     * @param start   - beginning data
     * @param end     - ending data
     * @param service - type auto insurance
     * @throws SQLException database request
     * @return insurance sum by period
     */
    public int getInsuranceSumByPeriod(Date start, Date end, String service) throws SQLException {
        PreparedStatement preparedStatement
                = connection.prepareStatement("SELECT SUM(insuranceSum) " +
                "FROM policy " +
                "WHERE ((? >= beginning AND ? < ending) OR ? > beginning) AND ? > ? AND idService = ?");

        preparedStatement.setDate(1, start);
        preparedStatement.setDate(2, start);
        preparedStatement.setDate(3, end);
        preparedStatement.setDate(4, end);
        preparedStatement.setDate(5, start);
        preparedStatement.setInt(6, InsuranceServiceValues.getDbKey(service));

        ResultSet result = preparedStatement.executeQuery();

        result.next();

        return result.getInt(1);
    }
}
