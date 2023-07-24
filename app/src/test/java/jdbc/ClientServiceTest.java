package jdbc;

import jdbc.DatabaseServices.DatabaseInitService;
import jdbc.entities.Client;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ClientServiceTest {
    private Connection connection;
    private ClientService clientService;

    @BeforeEach
    public void beforeEach() throws SQLException {
        final String dbUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
        new DatabaseInitService().initDb(dbUrl);
        connection = DriverManager.getConnection(dbUrl);
        clientService = new ClientService(connection);
        clientService.clear();
    }

    @AfterEach
    public void afterEach() throws SQLException {
        connection.close();
    }

    @Test
    void testThatClientCreatedCorrectly() {

        //Setup
        //        Client client = new Client();
        //        client.setName("TestName");
        String name = "TestName";

        //Action
        long id = clientService.create(name);
        String savedName = clientService.getById(id);

        //Assert
        Assertions.assertEquals(name, savedName);
    }

    @Test
    void testThatCreateNewClientThrowsExceptionForWrongName() {
        List<String> names = new ArrayList<>();
        String smallName = "I";
        String bigName = "BiggestName".repeat(100);
        names.add(null);
        names.add(smallName);
        names.add(bigName);

        for (String name : names) {
//            Exception ex =
            Assertions.assertThrows(Exception.class,
                    () -> clientService.create(name));
//            Assertions.assertTrue(ex.getMessage().contains("Error"));
        }
    }

    @Test
    void testThatSetNameCorrectly() {
        //Setup
        String name = "TestName";
        long id = clientService.create(name);
        String newName = "NewName";

        //Action
        clientService.setName(id, newName);

        //Assert
        Assertions.assertEquals(newName, clientService.getById(id));
    }

    @Test
    void testThatSetNewNameThrowsExceptionForWrongName() {
        long id = clientService.create("TestName");
        List<String> names = new ArrayList<>();
        String smallName = "I";
        String bigName = "BiggestName".repeat(100);
        names.add(null);
        names.add(smallName);
        names.add(bigName);

        for (String name : names) {
            Assertions.assertThrows(IllegalArgumentException.class,
                    () -> clientService.setName(id, name));
        }
    }

    @Test
    void testThatClientDeleteCorrectly() {
        String name = "TestName";
        long id = clientService.create(name);

        clientService.deleteById(id);

        Assertions.assertNull(clientService.getById(id));
    }

    @Test
    void listAllTest() {
        Client client = new Client();
        String name = "TestName";
        client.setName(name);

        long id = clientService.create(name);
        client.setId(id);

        List<Client> expectedClients = Collections.singletonList(client);
        List<Client> actualClients = clientService.listAll();

        Assertions.assertEquals(expectedClients, actualClients);
    }
}