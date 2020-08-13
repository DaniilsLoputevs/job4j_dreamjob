package ru.job4j.dreamjob.servlet.image_example;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.dreamjob.image.ImageControl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UploadServlet extends HttpServlet {
    /* show imgs on swb-page IMG */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<String> images = new ArrayList<>();
        for (File name : new File("images").listFiles()) {
            images.add(name.getName());
        }
        req.setAttribute("images", images);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/image/upload.jsp");
        dispatcher.forward(req, resp);
    }

    /* Upload IMG */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
//        StringBuilder logString = new StringBuilder();

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> items = upload.parseRequest(req);
            File folder = new File("images");
            if (!folder.exists()) {
                folder.mkdir();
            }
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    String imgSavePath = folder + File.separator + item.getName();
                    ImageControl.uploadImgToServ(imgSavePath, item);
                    ImageControl.uploadImgToPsql(item.getName());
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

//        logString.append("abs path: ").append(repository.getAbsolutePath()).append("\r\n")
//                .append("path: ").append(repository.getPath()).append("\r\n");
//        writeLog(logString.toString());

        doGet(req, resp);
    }

    private void doClean() {
        String imgDeleteName = "img6";

        List<String> images = new ArrayList<>();
        for (File name : new File("images").listFiles()) {
            images.add(name.getName());
        }

        File file = new File("images" + File.separator + imgDeleteName);
        System.out.println(file.delete());
    }


//    private void writeLog(String string) {
//        var log = "C:/Danik/Projects/job4j_dreamjob/src/main/java/ru/job4j/dreamjob/servlet/log.txt";
//        writeListToFile(log, List.of(string), "");
//    }
//
//    public static void writeListToFile(String path, List<String> content, String sysSeparator) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
//            for (String contentLine : content) {
//                writer.write(contentLine + sysSeparator);
//            }
//            writer.flush();
//        } catch (IOException e) {
//            System.out.println("IOException: IOHelper - write List to File!");
//            e.printStackTrace();
//        }
//    }
}