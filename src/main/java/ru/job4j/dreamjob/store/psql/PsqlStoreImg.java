package ru.job4j.dreamjob.store.psql;

import ru.job4j.dreamjob.ahelptools.ConslLog;
import ru.job4j.dreamjob.psql.PsqlPoolConnect;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PsqlStoreImg {

    private static final class Lazy {
        private static final PsqlStoreImg INST = new PsqlStoreImg();
    }

    public static PsqlStoreImg instOf() {
        return PsqlStoreImg.Lazy.INST;
    }

    /**
     * @param file - img file to bytes
     * @return - id in base.
     */
    public int toBaseFile(byte[] file) {
        int rsl = -1;
        var sql = "INSERT INTO can_img(byte_arr_img) VALUES (?)";
        try (var connect = PsqlPoolConnect.getPool().getConnection();
             var prepStat = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            prepStat.setBytes(1, file);
            prepStat.execute();
            try (ResultSet id = prepStat.getGeneratedKeys()) {
                if (id.next()) {
                    rsl = id.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    public byte[] fromBaseById(int id) {
        byte[] rsl = null;
        var sql = "SELECT * FROM can_img where id=(?)";
        try (var connect = PsqlPoolConnect.getPool().getConnection();
             var prepStat = connect.prepareStatement(sql)
        ) {
            prepStat.setInt(1, id);
            ResultSet resultSet = prepStat.executeQuery();
            byte[] bytes = null;
            while (resultSet.next()) {
                bytes = resultSet.getBytes("byte_arr_img");
            }
            if (bytes != null) {
                rsl = bytes;
            } else {
                ConslLog.log("PsqlStoreImg - Exception - fromBaseById(...): bytes == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    public void deleteById(int id) {
        var sql = "DELETE FROM can_img WHERE id=(?)";
        try (var connect = PsqlPoolConnect.getPool().getConnection();
             var prepStat = connect.prepareStatement(sql)
        ) {
            prepStat.setInt(1, id);
            prepStat.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Collection<Integer> getAllIIds() {
        List<Integer> rsl = new ArrayList<>();
        var sql = "SELECT  id FROM can_img";
        try (var connect = PsqlPoolConnect.getPool().getConnection();
             var prepStat = connect.prepareStatement(sql);
        ) {
            ResultSet resultSet = prepStat.executeQuery();
            while (resultSet.next()) {
                rsl.add(resultSet.getInt("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }
}
