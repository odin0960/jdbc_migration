package jdbc_update.entities;

import lombok.Data;

import java.sql.Date;

@Data
public class Worker {
    private String name;
    private String birthday;
    private Level level;
    private int salary;

    public enum Level {
        Trainee,
        Junior,
        Middle,
        Senior
    }
}

