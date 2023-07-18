package jdbc_update;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Utilities {

    public static String readSqlFromFile (String path) throws IOException {
        return Files.readString(Path.of(path));
    }
}
