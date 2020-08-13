package ru.job4j.dreamjob.image;

import org.apache.commons.fileupload.FileItem;
import ru.job4j.dreamjob.psql_db_connect.PsqlConnect;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

public class ImageControl {
//    public static void psqlDeleteImg(String imgName) {
//
//    }
//    public static void servDeleteImg(String imgName) {
//
//    }
//    public static void psqlAddImgTo(String imgName) {
//
//    }
//    public static void servAddImgTo(String imgName) {
//
//    }

    /**
     * @param imgName -
     * @return img id in PSQL after insert.
     */
    public static int uploadImgToPsql(String imgName) {
        var rsl = -1;
        try (var ps = PsqlConnect.getPool().getConnection()
                .prepareStatement("INSERT INTO image(img_name) VALUES (?)",
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setString(1, imgName);
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

//    private Candidate create(Candidate candidate) {
//        try (var ps = PsqlConnect.get().getConnection()
//                .prepareStatement("INSERT INTO candidate(name) VALUES (?)")
//        ) {
//            ps.setString(1, candidate.getName());
//            ps.execute();
//            try (ResultSet id = ps.getGeneratedKeys()) {
//                if (id.next()) {
//                    candidate.setId(id.getInt(1));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return candidate;
//    }

    /**
     * @param imgSavePath -
     * @param requestFile - img src from request, - need for save on server side.
     */
    public static void uploadImgToServ(String imgSavePath, FileItem requestFile) {
        File file = new File(imgSavePath);
        try (FileOutputStream out = new FileOutputStream(file)) {
            out.write(requestFile.getInputStream().readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static String getImgSrcById(int id) {
//        String rsl = "";
//        try (var prepStat = PsqlConnect.get().getConnection()
//                .prepareStatement("SELECT * FROM image where id=(?)")
//        ) {
//            prepStat.setInt(1, id);
//            ResultSet resultSet = prepStat.executeQuery();
//            while (resultSet.next()) {
//                rsl = resultSet.getString("img_name");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return rsl;
//    }


}
