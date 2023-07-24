package jdbc;

import jdbc.DatabaseServices.*;
import jdbc.instances.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException, SQLException {

        Database database = Database.getInstance();
        Connection connection = database.getConnection();

        String dbUrl = new Settings().getString(Settings.DB_JDBC_CONNECTION_URL);

        new DatabaseInitService().initDb(dbUrl);

        ClientService clientService = new ClientService(connection);

        long id = clientService.create("Epsilon");
        System.out.println(id);
        System.out.println(clientService.getById(id)); //Epsilon
        String newName = "Upsilon";
        clientService.setName(id, newName);
        System.out.println(clientService.getById(id)); //Upsilon
        clientService.deleteById(id);
        System.out.println(clientService.getById(id)); //null
        System.out.println(clientService.listAll());

        List<MaxSalaryWorker> maxSalaryWorker = new DatabaseQueryService().findMaxSalaryWorker();
        List<MaxProjectCountClient> maxProjectCountClients = new DatabaseQueryService().findMaxProjectsClient();
        List<LongestProject> longestProject = new DatabaseQueryService().findLongestProject();
        List<YoungestEldestWorker> youngestEldestWorker = new DatabaseQueryService().findYoungestEldestWorker();
        List<ProjectPrices> projectPrices = new DatabaseQueryService().printProjectPrices();

        maxSalaryWorker.forEach(System.out::println);
        maxProjectCountClients.forEach(System.out::println);
        longestProject.forEach(System.out::println);
        youngestEldestWorker.forEach(System.out::println);
        projectPrices.forEach(System.out::println);
    }
}
