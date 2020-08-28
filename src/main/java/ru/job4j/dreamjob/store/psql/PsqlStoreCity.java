package ru.job4j.dreamjob.store.psql;

import ru.job4j.dreamjob.psql.PsqlPoolConnect;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PsqlStoreCity {
    private static final class Lazy {
        private static final PsqlStoreCity INST = new PsqlStoreCity();
    }

    public static PsqlStoreCity instOf() {
        return PsqlStoreCity.Lazy.INST;
    }

    public List<String> getAll() {
        List<String> rsl = new ArrayList<>();
        var sql = "SELECT * FROM city";
        try (var connect = PsqlPoolConnect.getPool().getConnection();
             var prepStat = connect.prepareStatement(sql)
        ) {
            try (ResultSet it = prepStat.executeQuery()) {
                while (it.next()) {
                    rsl.add(it.getString("name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }
}
