package ru.job4j.dreamjob.store.psql;

import org.apache.commons.io.FileUtils;
import ru.job4j.dreamjob.debug.ConslLog;
import ru.job4j.dreamjob.psql_db_connect.PsqlConnect;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

public class PsqlImgStore {

    public static int toBaseFile(byte[] file) {
        int rsl = -1;
        try (var ps = PsqlConnect.getPool().getConnection()
                .prepareStatement("INSERT INTO can_img(byte_arr_img) VALUES (?)",
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setBytes(1, file);
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    rsl = id.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    public static byte[] fromBaseById(int id) {
        byte[] rsl = null;
        try (var prepStat = PsqlConnect.getPool().getConnection()
                .prepareStatement("SELECT * FROM can_img where id=(?)")
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
                ConslLog.log("PsqlImgStore - Exception - fromBaseById(...): bytes == null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    public static void deleteById(int id) {
        try (var prepStat = PsqlConnect.getPool().getConnection()
                .prepareStatement("DELETE FROM can_img WHERE id=(?)")
        ) {
            prepStat.setInt(1, id);
            prepStat.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        deleteById(5);
//    }

    /* --- Private Things --- */

    private static byte[] bytes(File file) {
        byte[] rsl = null;
        try {
            rsl = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    /* --- commented because it should be useful --- */

//    private static File bytesToFile(String path, byte[] fileBytes) {
//        File rsl = new File(path);
//        try {
//            FileUtils.writeByteArrayToFile(rsl, fileBytes);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return rsl;
//    }
}
