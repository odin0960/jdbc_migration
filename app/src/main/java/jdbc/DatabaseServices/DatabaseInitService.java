package jdbc.DatabaseServices;

import org.flywaydb.core.Flyway;

public class DatabaseInitService {

    public void initDb(String dbUrl) {
        // Create the Flyway instance and point it to the database
        Flyway flyway = Flyway
                .configure()
                .dataSource(dbUrl, null, null)
                .load();

        // Start the migration
        flyway.migrate();
    }
}
