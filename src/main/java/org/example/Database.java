package org.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private static org.example.Database INSTANCE;
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5437/";
    private static final String DATABASE_USER = "postgres";
    private static final String DATABASE_PASSWORD = "Julia";

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    Database() {
        config.setJdbcUrl(DATABASE_URL);
        config.setUsername(DATABASE_USER);
        config.setPassword(DATABASE_PASSWORD);
        ds = new HikariDataSource(config);
        Flyway flyway = Flyway.configure()
                .dataSource(ds)
                .locations("db/migration")
                .load();
        flyway.migrate();
    }

    public static org.example.Database getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new org.example.Database();
        }
        return INSTANCE;
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
