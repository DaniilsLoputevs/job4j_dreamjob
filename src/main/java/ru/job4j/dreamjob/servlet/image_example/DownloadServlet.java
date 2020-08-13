package ru.job4j.dreamjob.servlet.image_example;


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

        String name = req.getParameter("name");
        resp.setContentType("name=" + name);
        resp.setContentType("image/png");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
        File file = new File("images" + File.separator + name);

        try (FileInputStream in = new FileInputStream(file)) {
            resp.getOutputStream().write(in.readAllBytes());
        }

//        byte[] bytes = Exp.fromBase(1);
//        resp.getOutputStream().write(bytes);
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