package jdbc;

import jdbc.DatabaseServices.*;
import jdbc.instances.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;


public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(Database.class);

    public static void main(String[] args) throws SQLException, IOException {

        Connection connection = Database.getInstance().getConnection();

        String dbUrl = new Settings().getString(Settings.DB_JDBC_CONNECTION_URL);

        new DatabaseInitService().initDb(dbUrl);

        ClientService clientService = new ClientService(connection);

//        String name = "I";
//        String name = "BiggestName".repeat(100);
        String name = "Epsilon";
        long id = clientService.create(name);
        LOGGER.info(String.valueOf(id));
        LOGGER.info(clientService.getById(id)); //Epsilon
        String newName = "Upsilon";
        clientService.setName(id, newName);
        LOGGER.info(clientService.getById(id)); //Upsilon
        clientService.deleteById(id);
        if (clientService.getById(id) == null)
            LOGGER.info("Deleted"); //null
        clientService.listAll().stream().map(String::valueOf).forEach(LOGGER::info);

        List<MaxSalaryWorker> maxSalaryWorker = new DatabaseQueryService().findMaxSalaryWorker();
        List<MaxProjectCountClient> maxProjectCountClients = new DatabaseQueryService().findMaxProjectsClient();
        List<LongestProject> longestProject = new DatabaseQueryService().findLongestProject();
        List<YoungestEldestWorker> youngestEldestWorker = new DatabaseQueryService().findYoungestEldestWorker();
        List<ProjectPrices> projectPrices = new DatabaseQueryService().printProjectPrices();

        maxSalaryWorker.forEach(x -> LOGGER.info(String.valueOf(x)));
        maxProjectCountClients.forEach(x -> LOGGER.info(String.valueOf(x)));
        longestProject.forEach(x -> LOGGER.info(String.valueOf(x)));
        youngestEldestWorker.forEach(x -> LOGGER.info(String.valueOf(x)));
        projectPrices.forEach(x -> LOGGER.info(String.valueOf(x)));
    }
}
