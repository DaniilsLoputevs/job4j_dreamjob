package ru.job4j.dreamjob.ahelptools;

import org.apache.commons.io.FileUtils;
import ru.job4j.dreamjob.store.psql.PsqlStoreImg;

import java.io.File;
import java.io.IOException;


public class ManualExperiment {

    public static void main(String[] args) {
//        var sourcePath = "C:\\Users\\Admin\\Desktop\\job4j\\null_ID_img.png";
//        var sourcePath = "C:\\Danik\\Programming_Tools\\apache-tomcat-9.0.37\\apache-tomcat-9.0.37\\bin\\images\\0tB82-LbU8U.jpg";
//        var targetPath = "C:\\Danik\\Projects\\job4j_dreamjob\\src\\main\\java\\ru\\job4j\\dreamjob\\target.jpg";
//        var imgFile = new File(sourcePath);

//        int id = toBase(imgFile);
//        ConslLog.log("id", id);

//        byte[] fileBytes = fromBase(1);

//        var file = bytesToFile(targetPath, fileBytes);

        /* self work */

//        var bytes = bytes(new File(sourcePath));
//
//        try (var ps = PsqlPoolConnect.getPool().getConnection()
//                .prepareStatement("INSERT INTO can_img(byte_arr_img) VALUES (?)")
//        ) {
////            ps.setInt(1, 0);
////            ps.setBytes(2, bytes);
//            ps.setBytes(1, bytes);
//            ps.execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        var candidate = new Candidate(24, "UPD", 111);
//
//
//        try (var prepStat = PsqlPoolConnect.getPool().getConnection()
//                .prepareStatement("UPDATE candidate SET name=(?), img_id=(?) WHERE id=(?)")
//        ) {
//            prepStat.setString(1, candidate.getName());
//            prepStat.setInt(2, candidate.getImgId());
//            prepStat.setInt(3, candidate.getId());
//            prepStat.execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    public static int toBase(File file) {
        return PsqlStoreImg.toBaseFile(bytes(file));
//        int rsl = -1;
//        try (var ps = PsqlPoolConnect.getPool().getConnection()
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
        return PsqlStoreImg.fromBaseById(id);
//        byte[] rsl = null;
//        try (var prepStat = PsqlPoolConnect.getPool().getConnection()
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
//                ConslLog.log("ManualExperiment - fromBase(): bytes == null");
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
