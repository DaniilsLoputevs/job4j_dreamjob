package ru.job4j.dreamjob.psql;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class PsqlPoolConnect implements AutoCloseable {
    private final BasicDataSource pool = new BasicDataSource();

    private PsqlPoolConnect() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties")
        )) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    @Override
    public void close() throws IOException {
        try {
            pool.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static final class Lazy {
        private static final PsqlPoolConnect INST = new PsqlPoolConnect();
    }

    public static BasicDataSource getPool() {
        return Lazy.INST.pool;
    }

}
