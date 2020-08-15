package ru.job4j.dreamjob.servlet.candidate;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.dreamjob.debug.ConslLog;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.psql.PsqlCandidateStore;
import ru.job4j.dreamjob.store.psql.PsqlImgStore;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class CandidateImgServlet extends HttpServlet {
    /* Download IMG*/
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        try {
//            int id = Integer.parseInt(req.getParameter("id"));
//        } catch (NumberFormatException e) {
//            ConslLog.log("Exception - id", req.getParameter("id"));
//        }

//        ConslLog.log("Img Servlet");
//        int id = Integer.parseInt(req.getParameter("id"));
//        ConslLog.log("canImgId", id);
//        ConslLog.log("CandidatesServlet");
//        ConslLog.log("req Param - id", req.getParameter("id"));
//        ConslLog.log("parse req Param - id", Integer.parseInt(req.getParameter("id")));
//        ConslLog.log("req Param - name", req.getParameter("name"));
//        ConslLog.log("Img Servlet");

        resp.setContentType("image/png");
        resp.setHeader("Content-Disposition", "attachment; filename=\"byte[]\"");

        byte[] bytes = PsqlImgStore.fromBaseById(3);
//        byte[] bytes = PsqlImgStore.fromBaseById(id);
        resp.getOutputStream().write(bytes);
    }

    /* Upload IMG */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ConslLog.log("Img Servlet");
        int id = Integer.parseInt(req.getParameter("id"));
        ConslLog.log("canImgId", id);
        ConslLog.log("CandidatesServlet");
        ConslLog.log("req Param - id", req.getParameter("id"));
        ConslLog.log("parse req Param - id", Integer.parseInt(req.getParameter("id")));
        ConslLog.log("req Param - name", req.getParameter("name"));
        ConslLog.log("Img Servlet");

        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
//        try {
//            List<FileItem> items = upload.parseRequest(req);
//            for (FileItem item : items) {
//                if (!item.isFormField()) {
//                    PsqlImgStore.toBaseFile(item.getInputStream().readAllBytes());
//                }
//            }
//        } catch (FileUploadException e) {
//            e.printStackTrace();
//        }
        int imgId = loadImg(req, upload);
        updCandidate(req, imgId);

        ConslLog.log("Img Servlet");

        resp.sendRedirect(req.getContextPath() + "/candidate/candidates.do");
    }
    private int loadImg(HttpServletRequest req, ServletFileUpload upload)
            throws IOException {
        int rsl = -1;
        try {
            List<FileItem> items = upload.parseRequest(req);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    rsl = PsqlImgStore.toBaseFile(item.getInputStream().readAllBytes());
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    private void updCandidate(HttpServletRequest req, int imgId) {
//        String candidateName = req.getParameter("name");
        String candidateName = "test";
        int candidateId = Integer.parseInt(req.getParameter("id"));
        Candidate temp = PsqlCandidateStore.findByIdCandidate(candidateId);
        ConslLog.log("UPD");
        ConslLog.log("BEFORE");
        ConslLog.log("id: ", temp.getId());
        ConslLog.log("name: ", temp.getName());
        ConslLog.log("imgId: ", temp.getImgId());
        temp.setName(candidateName);
        temp.setImgId(imgId);
        PsqlCandidateStore.save(temp);
        ConslLog.log("BEFORE");
        ConslLog.log("id: ", temp.getId());
        ConslLog.log("name: ", temp.getName());
        ConslLog.log("imgId: ", temp.getImgId());
    }
}
