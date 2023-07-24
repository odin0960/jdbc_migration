package jdbc;

import jdbc.entities.Client;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class ClientService {
    private static final Logger LOGGER = LoggerFactory.getLogger(Database.class);
    private PreparedStatement createSt;
    private PreparedStatement readSt;
    private PreparedStatement setSt;
    private PreparedStatement deleteSt;
    private PreparedStatement readAllSt;
    private PreparedStatement clearSt;
    private static final String CREATE_CLIENT = "INSERT INTO client (name) VALUES (?)";
    private static final String GET_BY_ID = "SELECT name FROM client WHERE id = ?";
    private static final String SET_NEW_NAME = "UPDATE client SET name = ? WHERE id = ?";
    private static final String DELETE_CLIENT = "DELETE FROM client WHERE id = ?";
    private static final String READ_ALL = "SELECT id, name FROM client";
    private static final String CLEAR_ALL = "DELETE FROM project_worker; DELETE FROM project; DELETE FROM client";


    public ClientService(Connection connection) throws SQLException {
        createSt = connection.prepareStatement(CREATE_CLIENT, Statement.RETURN_GENERATED_KEYS);
        readSt = connection.prepareStatement(GET_BY_ID);
        setSt = connection.prepareStatement(SET_NEW_NAME);
        deleteSt = connection.prepareStatement(DELETE_CLIENT);
        readAllSt = connection.prepareStatement(READ_ALL);
        clearSt = connection.prepareStatement(CLEAR_ALL);
    }

    public void clear() throws SQLException {
        clearSt.executeUpdate();
    }

    public long create(String name) {
        long id = -1L;
        try {
            if (name == null || name.length() > 1000 || name.length() < 2) {
                throw new IllegalArgumentException();
            } else {
                createSt.setString(1, name);
                createSt.executeUpdate();
                ResultSet rs = createSt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("The name length must be greater than 1 character but less than 1000 characters", ex);
        }
        return id;
    }

    public String getById(long id) {
        try {
            if (id < 1) {
                throw new IllegalArgumentException();
            } else {
                readSt.setLong(1, id);
                ResultSet rs = readSt.executeQuery();
                if (!rs.next()) {
                    return null;
                }
                return rs.getString("name");
            }
        } catch (Exception ex) {
            LOGGER.error("The id must be greater than 0", ex);
        }
        return null;
    }

    void setName(long id, String name) {
        try {
            if (id < 1 || name == null || name.length() > 1000 || name.length() < 2) {
                throw new IllegalArgumentException();
            } else {
                setSt.setString(1, name);
                setSt.setLong(2, id);
                setSt.executeUpdate();
            }
        } catch (Exception ex) {
            LOGGER.error("The name length must be 2-1000 characters and id must be greater than 0", ex);
        }

    }

    void deleteById(long id) {
        try {
            if (getById(id) != null) {
                deleteSt.setLong(1, id);
                deleteSt.executeUpdate();

            }
        } catch (Exception ex) {
            LOGGER.error("The name length must be 2-1000 characters and id must be greater than 0", ex);
        }

    }

    List<Client> listAll() {

        List<Client> result = new ArrayList<>();

        try (ResultSet rs = readAllSt.executeQuery()) {
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getLong("id"));
                client.setName(rs.getString("name"));
                result.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
