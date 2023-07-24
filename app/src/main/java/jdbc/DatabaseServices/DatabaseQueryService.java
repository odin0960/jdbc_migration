package jdbc.DatabaseServices;

import jdbc.Database;
import jdbc.Utilities;
import jdbc.instances.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryService {

    public static final String FIND_MAX_SALARY_WORKER = "./sql/find_max_salary_worker.sql";
    public static final String FIND_MAX_PROJECTS_CLIENT = "./sql/find_max_projects_client.sql";
    public static final String FIND_LONGEST_PROJECT = "./sql/find_longest_project.sql";
    public static final String FIND_YOUNGEST_ELDEST_WORKER = "./sql/find_youngest_eldest_worker.sql";
    public static final String PRINT_PROJECT_PRICES = "./sql/print_project_prices.sql";

    Statement st = Database.getInstance().getConnection().createStatement();

    public DatabaseQueryService() throws SQLException {
    }

    public List<MaxSalaryWorker> findMaxSalaryWorker() throws IOException {

        List<MaxSalaryWorker> result = new ArrayList<>();

            try (ResultSet rs = st.executeQuery(Utilities.readSqlFromFile(FIND_MAX_SALARY_WORKER))) {
                while (rs.next()) {
                    MaxSalaryWorker maxSalaryWorker = new MaxSalaryWorker();
                    maxSalaryWorker.setName(rs.getString("name"));
                    maxSalaryWorker.setSalary(rs.getInt("salary"));
                    result.add(maxSalaryWorker);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return result;
    }

    public List<MaxProjectCountClient> findMaxProjectsClient() throws IOException {

        List<MaxProjectCountClient> result = new ArrayList<>();

            try (ResultSet rs = st.executeQuery(Utilities.readSqlFromFile(FIND_MAX_PROJECTS_CLIENT))) {
                while (rs.next()) {
                    MaxProjectCountClient maxProjectsClient = new MaxProjectCountClient();
                    maxProjectsClient.setName(rs.getString("name"));
                    maxProjectsClient.setProjectCount(rs.getInt("project_count"));
                    result.add(maxProjectsClient);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return result;
    }

    public List<LongestProject> findLongestProject() throws IOException {

        List<LongestProject> result = new ArrayList<>();

            try (ResultSet rs = st.executeQuery(Utilities.readSqlFromFile(FIND_LONGEST_PROJECT))) {
                while (rs.next()) {
                    LongestProject longestProject = new LongestProject();
                    longestProject.setName(rs.getString("name"));
                    longestProject.setMonthCount(rs.getInt("month_count"));
                    result.add(longestProject);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return result;
    }

    public List<YoungestEldestWorker> findYoungestEldestWorker() throws IOException {

        List<YoungestEldestWorker> result = new ArrayList<>();

            try (ResultSet rs = st.executeQuery(Utilities.readSqlFromFile(FIND_YOUNGEST_ELDEST_WORKER))) {
                while (rs.next()) {
                    YoungestEldestWorker youngestEldestWorker = new YoungestEldestWorker();
                    youngestEldestWorker.setType(rs.getString("type"));
                    youngestEldestWorker.setName(rs.getString("name"));
                    youngestEldestWorker.setBirthday(rs.getString("birthday"));
                    result.add(youngestEldestWorker);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return result;
    }

    public List<ProjectPrices> printProjectPrices() throws SQLException, IOException {

        List<ProjectPrices> result = new ArrayList<>();

            try (ResultSet rs = st.executeQuery(Utilities.readSqlFromFile(PRINT_PROJECT_PRICES))) {
                while (rs.next()) {
                    ProjectPrices projectPrices = new ProjectPrices();
                    projectPrices.setName(rs.getString("name"));
                    projectPrices.setPrice(rs.getInt("price"));
                    result.add(projectPrices);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return result;
    }

}
