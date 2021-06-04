package sample.database;

import sample.database.tables.*;

import java.sql.*;

/**
 * Host Name: 127.0.0.1
 * Port: 3306
 * Connection Name: Local Instance MySQL80
 **/

public class Database {
    private static Database instance;

    private final static String DB_URL = "jdbc:mysql://localhost:3306/auto_insurance";
    private final static String user = "root";
//    private final static String password = "password";
    private final static String password = "12345qwert";

    private Database() {
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    private Connection connection;

    /*   CONNECTION   */

    private void createConnection() {
        if (connection == null) {
            doConnect();

            return;
        }

        try {
            if (connection.isClosed()) {
                doConnect();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void doConnect() {
        try {
            connection = DriverManager.getConnection(DB_URL, user, password);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /*   INSTANCE CLIENT TABLE   */

    public ClientDbTable getClient() {
        createConnection();

        return new ClientDbTable(connection);
    }

    /*   TABLE CONTRACT   */

    public ContractDbTable getContract() {
        createConnection();

        return new ContractDbTable(connection);
    }

    /*   TABLE POLICY   */

    public PolicyDbTable getPolicy() {
        createConnection();

        return new PolicyDbTable(connection);
    }

    /*   TABLE CAR   */

    public CarDbTable getCar() {
        createConnection();

        return new CarDbTable(connection);
    }

    /*   TABLE INSURANCE EVENT   */

    public InsuranceEventDbTable getInsuranceEvent() {
        createConnection();

        return new InsuranceEventDbTable(connection);
    }

    /*   TABLE CAR DRIVER   */

    public CarDriverDbTable getCarDriver() {
        createConnection();

        return new CarDriverDbTable(connection);
    }

    /*   TABLE POLICY_DRIVER   */

    public PolicyAndDriverDbTable getPolicyAndDriver() {
        createConnection();

        return new PolicyAndDriverDbTable(connection);
    }

    /*   INSTANCE INSURANCE SERVICE TABLE   */

    public InsuranceServiceDbTable getInsuranceService() {
        createConnection();

        return new InsuranceServiceDbTable(connection);
    }

    /*   INSTANCE PRICE POLICY TABLE   */

    public PricePolicyDbTable getPricePolicy() {
        createConnection();

        return new PricePolicyDbTable(connection);
    }
}
