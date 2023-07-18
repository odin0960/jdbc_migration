package jdbc_update.DatabaseServices;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jdbc_update.Database;
import jdbc_update.entities.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DatabasePopulateService {

    public static final String WORKERS = "./sql/workers.json";
    public static final String CLIENTS = "./sql/clients.json";
    public static final String PROJECTS = "./sql/projects.json";
    public static final String PROJECT_WORKER = "./sql/project_worker.json";


    public void populateWorkers(Database database) throws SQLException, IOException {
        String prSql = "INSERT INTO worker (name, birthday, level, salary) VALUES (?, ?, ?, ?)";
        try (PreparedStatement prSt = database.getConnection().prepareStatement(prSql)){

            List<Worker> workers;

            String json = String.join("\n", Files.readString(Path.of(WORKERS)));

            TypeToken<?> typeToken = TypeToken.getParameterized(List.class, Worker.class);
            workers = new Gson().fromJson(json, typeToken.getType());

            for (Worker worker : workers) {
                prSt.setString(1, worker.getName());
                prSt.setString(2, worker.getBirthday());
                prSt.setString(3, String.valueOf(worker.getLevel()));
                prSt.setInt(4, worker.getSalary());
                prSt.addBatch();
            }
            prSt.executeBatch();
        }
    }

    public void populateClients(Database database) throws SQLException, IOException {
        String prSql = "INSERT INTO client (name) VALUES (?)";
        try (PreparedStatement prSt = database.getConnection().prepareStatement(prSql)) {

            List<Client> clients;

            String json = String.join("\n", Files.readString(Path.of(CLIENTS)));

            TypeToken<?> typeToken = TypeToken.getParameterized(List.class, Client.class);
            clients = new Gson().fromJson(json, typeToken.getType());

            for (Client client : clients) {
                prSt.setString(1, client.getName());
                prSt.addBatch();
            }
            prSt.executeBatch();
        }
    }

    public void populateProjects(Database database) throws SQLException, IOException {
        String prSql = "INSERT INTO project (client_id, start_date, finish_date) VALUES (?, ?, ?)";
        try (PreparedStatement prSt = database.getConnection().prepareStatement(prSql)) {

            List<Project> projects;

            String json = String.join("\n", Files.readString(Path.of(PROJECTS)));

            TypeToken<?> typeToken = TypeToken.getParameterized(List.class, Project.class);
            projects = new Gson().fromJson(json, typeToken.getType());

            for (Project project : projects) {
                prSt.setInt(1, project.getClientId());
                prSt.setString(2, project.getStartDate());
                prSt.setString(3, project.getFinishDate());
//                prSt.executeUpdate();
                prSt.addBatch();
            }
            prSt.executeBatch();
        }
    }

    public void populateProjectWorker(Database database) throws SQLException, IOException {
        String prSql = "INSERT INTO project_worker (project_id, worker_id) VALUES (?, ?)";
        try (PreparedStatement prSt = database.getConnection().prepareStatement(prSql)) {

            List<ProjectWorker> projectWorkers;

            String json = String.join("\n", Files.readString(Path.of(PROJECT_WORKER)));

            TypeToken<?> typeToken = TypeToken.getParameterized(List.class, ProjectWorker.class);
            projectWorkers = new Gson().fromJson(json, typeToken.getType());

            for (ProjectWorker projectWorker : projectWorkers) {
                prSt.setInt(1, projectWorker.getProjectId());
                prSt.setInt(2, projectWorker.getWorkerId());
//                prSt.executeUpdate();
                prSt.addBatch();
            }
            prSt.executeBatch();
        }
    }
}
