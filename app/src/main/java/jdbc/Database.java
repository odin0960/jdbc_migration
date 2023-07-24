package jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.BasicConfigurator;

import java.sql.*;

public class Database {
    private static final Logger LOGGER = LoggerFactory.getLogger(Database.class);
    public static final Database INSTANCE = new Database();
    private Connection connection;

    private Database() {
        BasicConfigurator.configure();
        try {
            String dbUrl = new Settings().getString(Settings.DB_JDBC_CONNECTION_URL);
            connection = DriverManager.getConnection(dbUrl);
            LOGGER.info("Connection is successful");
        } catch (Exception ex) {
            LOGGER.error("Connection problem...", ex);
        }
    }

    public static Database getInstance() {
        return INSTANCE;
    }

    public int executeUpdate(String sql) {
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(sql);
        } catch (Exception ex) {
            LOGGER.error("Problem with update...", ex);

            return -1;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            LOGGER.error("Connection is not closed...", ex);
        }
    }
}
