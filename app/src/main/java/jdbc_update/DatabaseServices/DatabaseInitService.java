package jdbc_update.DatabaseServices;

import jdbc_update.Database;
import jdbc_update.Settings;
import jdbc_update.Utilities;

import java.io.IOException;

public class DatabaseInitService {

    public void initDb(Database database) throws IOException {
        String initDbFilename = new Settings().getString(Settings.INIT_DB_SQL_FILEPATH);
        database.executeUpdate(Utilities.readSqlFromFile(initDbFilename));
    }
}
