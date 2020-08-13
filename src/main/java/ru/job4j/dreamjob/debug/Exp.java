package ru.job4j.dreamjob.debug;

import org.apache.commons.io.FileUtils;
import ru.job4j.dreamjob.psql_db_connect.PsqlImg;

import java.io.File;
import java.io.IOException;


public class Exp {

    public static void main(String[] args) {
        var sourcePath = "C:\\Danik\\Programming_Tools\\apache-tomcat-9.0.37\\apache-tomcat-9.0.37\\bin\\images\\0tB82-LbU8U.jpg";
        var targetPath = "C:\\Danik\\Projects\\job4j_dreamjob\\src\\main\\java\\ru\\job4j\\dreamjob\\target.jpg";
        var imgFile = new File(sourcePath);

        int id = toBase(imgFile);
//        ConslLog.log("id", id);

        byte[] fileBytes = fromBase(1);

        var file = bytesToFile(targetPath, fileBytes);
    }

    public static int toBase(File file) {
        return PsqlImg.toBaseFile(file);
//        int rsl = -1;
//        try (var ps = PsqlConnect.getPool().getConnection()
//                .prepareStatement("INSERT INTO byte(byte_arr_img) VALUES (?)",
//                        Statement.RETURN_GENERATED_KEYS)
//        ) {
//            ps.setBytes(1, bytes(file));
//            ps.execute();
//            try (ResultSet id = ps.getGeneratedKeys()) {
//                if (id.next()) {
//                    rsl = id.getInt(1);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return rsl;
    }

    public static byte[] fromBase(int id) {
        return PsqlImg.fromBaseById(id);
//        byte[] rsl = null;
//        try (var prepStat = PsqlConnect.getPool().getConnection()
//                .prepareStatement("SELECT * FROM byte where id=(?)")
//        ) {
//            prepStat.setInt(1, id);
//            ResultSet resultSet = prepStat.executeQuery();
//            byte[] bytes = null;
//            while (resultSet.next()) {
//                bytes = resultSet.getBytes("byte_arr_img");
////                rsl.setId(resultSet.getInt("id"));
////                rsl.setName(resultSet.getString("name"));
//            }
//            if (bytes != null) {
////                FileUtils.writeByteArrayToFile(new File("pathname"), bytes);
//                rsl = bytes;
//            } else {
//                ConslLog.log("Exp - fromBase(): bytes == null");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return rsl;
    }

    public static File bytesToFile(String path, byte[] fileBytes) {
        File rsl = new File(path);
        try {
            FileUtils.writeByteArrayToFile(rsl, fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    public static byte[] bytes(File file) {
        byte[] rsl = null;
        try {
            rsl = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rsl;
    }
}
