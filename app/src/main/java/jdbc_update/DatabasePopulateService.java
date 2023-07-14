package jdbc_update;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DatabasePopulateService {

    public void populateDb(Database database) {
        try {
            String populateDbFilename = new Settings().getString(Settings.POPULATE_DB_SQL_FILEPATH);
            String sql = Files.readString(Path.of(populateDbFilename));
            database.executeUpdate(sql);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
