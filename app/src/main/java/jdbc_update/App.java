package jdbc_update;

import jdbc_update.DatabaseServices.*;
import jdbc_update.entities.*;
import jdbc_update.instances.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException, SQLException {

        Database database = Database.getInstance();
        Connection connection = database.getConnection();

        new DatabaseInitService().initDb(database);

        DatabasePopulateService populate = new DatabasePopulateService();

//        worker.getClass().getSimpleName()
        List<Worker> workers = Utilities.getListFromJson(DatabasePopulateService.WORKERS, Worker.class);
        connection.setAutoCommit(false);
        try {
            for (Worker worker : workers) {
                populate.populateWorkers(database, worker);
            }
            connection.commit();
        } catch (Exception ex) {
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
        }

        List<Client> clients = Utilities.getListFromJson(DatabasePopulateService.CLIENTS, Client.class);
        for (Client client : clients) {
            populate.populateClients(database, client);
        }

        List<Project> projects = Utilities.getListFromJson(DatabasePopulateService.PROJECTS, Project.class);
        for (Project project : projects) {
            populate.populateProjects(database, project);
        }

        List<ProjectWorker> projectWorkers = Utilities.getListFromJson(DatabasePopulateService.PROJECT_WORKER, ProjectWorker.class);
        for (ProjectWorker projectWorker : projectWorkers) {
            populate.populateProjectWorker(database, projectWorker);
        }


//        new DatabasePopulateService().populateWorkers(database);
//        new DatabasePopulateService().populateClients(database);
//        new DatabasePopulateService().populateProjects(database);
//        new DatabasePopulateService().populateProjectWorker(database);


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
