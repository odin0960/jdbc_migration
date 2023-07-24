package jdbc;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Settings {
    public static final String DB_JDBC_CONNECTION_URL = "dbUrl";
    public static final String INIT_DB_SQL_FILEPATH = "initDbSql";
    public static final String POPULATE_DB_SQL_FILEPATH = "populateDbSql";
    public static final String DEFAULT_SETTINGS_FILENAME = "settings.json";
    private Map<String, Object> settings = new HashMap<>();

    public Settings() throws IOException {
        this(DEFAULT_SETTINGS_FILENAME);
    }

    public Settings(String filename) throws IOException {
            String json = String.join("\n", Utilities.readSqlFromFile(filename));

            TypeToken<?> typeToken = TypeToken.getParameterized(Map.class, String.class, Object.class);
            settings = new Gson().fromJson(json, typeToken.getType());
    }

    public String getString(String key) {
        return getSettings(key).toString();
    }
    public Object getSettings(String key) {
        return settings.get(key);
    }

}
