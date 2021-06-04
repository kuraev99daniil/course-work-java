package sample.database.tables;

import sample.enums.InsuranceServiceValues;
import sample.model.InsuranceEventModel;
import sample.util.Converter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InsuranceEventDbTable {
    private Connection connection = null;

    private InsuranceEventDbTable() {
    }

    public InsuranceEventDbTable(Connection connection) {
        this.connection = connection;
    }

    /**
     * Method that returns insurance event by policy id
     *
     * @param idPolicy - policy id
     * @return list insurance event models
     * @throws SQLException database request
     */
    public List<InsuranceEventModel> getAllInsuranceEventsByIdPolicy(int idPolicy) throws SQLException {
        Statement statement = connection.createStatement();

        String request = String.format("SELECT * FROM insurance_event WHERE `idPolicy` = %s", idPolicy);

        ResultSet resultSet = statement.executeQuery(request);

        ArrayList<InsuranceEventModel> insuranceEventModels = new ArrayList<>();

        while (resultSet.next()) {
            insuranceEventModels.add(
                    new InsuranceEventModel(
                            resultSet.getInt("accidentId"),
                            Converter.getFormattedSqlDate(resultSet.getDate("date").toString()),
                            resultSet.getString("description"),
                            resultSet.getInt("amountPayment"),
                            resultSet.getInt("idService"),
                            resultSet.getInt("idPolicy"),
                            InsuranceServiceValues.getDbValue(resultSet.getInt("idService")).toUpperCase(Locale.ROOT)
                    )
            );
        }

        return insuranceEventModels;
    }

    /**
     * Method that updated insurance event data by id
     *
     * @param insuranceEventModel - insurance event data model
     * @throws SQLException database request
     */
    public void updateInsuranceEventById(InsuranceEventModel insuranceEventModel) throws SQLException {
        String request = "UPDATE insurance_event " +
                "SET date=?, description=?, amountPayment=?, idService=? " +
                "WHERE accidentId=?";

        PreparedStatement preparedStatement = connection.prepareStatement(request);

        preparedStatement.setDate(1, Converter.convertStringToSqlDate(insuranceEventModel.getDate()));
        preparedStatement.setString(2, insuranceEventModel.getDescription());
        preparedStatement.setInt(3, insuranceEventModel.getAmountPayment());
        preparedStatement.setInt(4, InsuranceServiceValues.getDbKey(insuranceEventModel.getService().toUpperCase(Locale.ROOT)));
        preparedStatement.setInt(5, insuranceEventModel.getId());

        preparedStatement.executeUpdate();
    }

    /**
     * Method that insert data insurance event to database
     *
     * @param insuranceEventModel - insurance event data model
     * @param idPolicy            - policy id
     * @throws SQLException database request
     */
    public void addInsuranceEvent(InsuranceEventModel insuranceEventModel, int idPolicy) throws SQLException {
        String request = "INSERT INTO insurance_event(`date`, `description`, `amountPayment`, `idPolicy`, `idService`) " +
                "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(request);

        preparedStatement.setDate(1, Converter.convertStringToSqlDate(insuranceEventModel.getDate()));
        preparedStatement.setString(2, insuranceEventModel.getDescription());
        preparedStatement.setInt(3, insuranceEventModel.getAmountPayment());
        preparedStatement.setInt(4, idPolicy);
        preparedStatement.setInt(5, InsuranceServiceValues.getDbKey(insuranceEventModel.getService().toUpperCase(Locale.ROOT)));

        preparedStatement.executeUpdate();
    }

    /**
     * Method that removed insurance event by id
     *
     * @param accidentId - accident id
     * @throws SQLException database request
     */
    public void removeInsuranceEventById(int accidentId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM insurance_event WHERE accidentId = ?");

        preparedStatement.setInt(1, accidentId);

        preparedStatement.executeUpdate();
    }

    /**
     * Method that returns statistic data
     *
     * @param start   - beginning data
     * @param end     - ending data
     * @param service - type auto insurance
     * @return amount payment by period
     * @throws SQLException database request
     */
    public int getAmountPaymentByPeriod(Date start, Date end, String service) throws SQLException {
        PreparedStatement preparedStatement
                = connection.prepareStatement("SELECT SUM(amountPayment) " +
                "FROM insurance_event " +
                "WHERE (? < date AND ? > date) AND ? > ? AND idService = ?");

        preparedStatement.setDate(1, start);
        preparedStatement.setDate(2, end);
        preparedStatement.setDate(3, end);
        preparedStatement.setDate(4, start);
        preparedStatement.setInt(5, InsuranceServiceValues.getDbKey(service));

        ResultSet result = preparedStatement.executeQuery();

        result.next();

        return result.getInt(1);
    }

    public int getMaxId() throws SQLException {
        Statement statement = connection.createStatement();

        String request = "SELECT * FROM insurance_event ORDER BY accidentId DESC LIMIT 1";

        ResultSet resultSet = statement.executeQuery(request);

        if (resultSet.next()) {
            return resultSet.getInt("accidentId");
        }

        return -1;
    }
}
