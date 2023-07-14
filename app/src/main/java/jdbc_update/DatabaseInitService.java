package jdbc_update;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DatabaseInitService {

    public void initDb(Database database) {
        try {
            String initDbFilename = new Settings().getString(Settings.INIT_DB_SQL_FILEPATH);
            String sql = Files.readString(Path.of(initDbFilename));
            database.executeUpdate(sql);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
