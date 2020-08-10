package ru.job4j.dreamjob.servlet.image;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        StringBuilder logString = new StringBuilder();

        String name = req.getParameter("name");
        resp.setContentType("name=" + name);
        resp.setContentType("image/png");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
        File file = new File("images" + File.separator + name);

        logString.append(file.getAbsolutePath()).append("\r\n");

        try (FileInputStream in = new FileInputStream(file)) {
            resp.getOutputStream().write(in.readAllBytes());
        }
//        logString.append("abs path: ").append(repository.getAbsolutePath()).append("\r\n")
//                .append("path: ").append(repository.getPath()).append("\r\n");
        writeLog(logString.toString());

    }

    private void writeLog(String string) {
        var log = "C:/Danik/Projects/job4j_dreamjob/src/main/java/ru/job4j/dreamjob/servlet/log.txt";
        writeListToFile(log, List.of(string), "");
    }

    public static void writeListToFile(String path, List<String> content, String sysSeparator) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (String contentLine : content) {
                writer.write(contentLine + sysSeparator);
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println("IOException: IOHelper - write List to File!");
            e.printStackTrace();
        }
    }
}