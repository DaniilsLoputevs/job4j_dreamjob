package ru.job4j.dreamjob.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.dreamjob.debug.ConslLog;
import ru.job4j.dreamjob.image.ImageControl;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class CandidateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("candidates", PsqlStore.instOf().findAllCandidates());
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

    /**
     * String : id
     * String : name
     * File   : img
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ConslLog.log("req Param - id", req.getParameter("id"));
        ConslLog.log("parse req Param - id", Integer.parseInt(req.getParameter("id")));
        ConslLog.log("req Param - name", req.getParameter("name"));

        int imgId = uploadImg(req);
        ConslLog.log("ImgId", imgId);

//        req.setCharacterEncoding("UTF-8");
        PsqlStore.instOf().save(
                new Candidate(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("name")
                        ,
                        imgId
//                        req.getParameter("img_id")
                )
        );
        resp.sendRedirect(req.getContextPath() + "/candidate/candidates.do");
    }

    private int uploadImg(HttpServletRequest req) {
        int rsl = -1;
        var factory = new DiskFileItemFactory();
        var servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        var upload = new ServletFileUpload(factory);
        ConslLog.log("before try");
        try {
            ConslLog.log("start try");
            List<FileItem> items = upload.parseRequest(req);
            File folder = new File("images");
            if (!folder.exists()) {
                folder.mkdir();
            }
            ConslLog.log("before forEach");
            for (FileItem item : items) {
                ConslLog.log("before if");
                if (!item.isFormField()) {
                    ConslLog.log("start if");
                    var imgName = item.getName();
                    ConslLog.log("imgName", imgName);
                    String imgSavePath = folder + File.separator + imgName;
                    ConslLog.log("imgSavePath", imgSavePath);

                    ImageControl.uploadImgToServ(imgSavePath, item);
                    rsl = ImageControl.uploadImgToPsql(imgName);
                    ConslLog.log("rsl", rsl);
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        return rsl;
    }

}
