package ru.job4j.dreamjob.servlet.can;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.dreamjob.debug.ConslLog;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.psql.PsqlStoreCandidate;
import ru.job4j.dreamjob.store.psql.PsqlStoreImg;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * url-pattern: /candidate/candidates.do
 */
public class CandidatesServlet extends HttpServlet {
    /**
     * show all candidates.
     * <p>
     * No Attributes
     * <p>
     * goto: candidates.jsp
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ConslLog.log("Can Servlet - GET Start");
        req.setAttribute("candidates", PsqlStoreCandidate.instOf().findAll());

        ConslLog.log("Can Servlet - GET Finish");
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

    /**
     * submit edit one candidate.
     * <p>
     * id : int - candidate's id.
     * name : String - text from input, new candidate's name.
     * <p>
     * goto: doGet -> candidates.jsp
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ConslLog.log("Can Servlet - Start");
        ConslLog.log("canImgId", Integer.parseInt(req.getParameter("id")));
        ConslLog.log("CandidatesServlet");
        ConslLog.log("req Param - id", req.getParameter("id"));
        ConslLog.log("parse req Param - id", Integer.parseInt(req.getParameter("id")));
        ConslLog.log("req Param - name", req.getParameter("name"));
        ConslLog.log("Can Servlet");

        ServletFileUpload upload = createDefaultFactory();

        int canId = Integer.parseInt(req.getParameter("id"));
        String newName = req.getParameter("name");
        int imgId = -1;
        // if >>> jsp - form enctype="multipart/form-data"
        if (newName == null) {
            imgId = loadImg(req, upload);
        }
        updCandidate(req, canId, newName, imgId);

        ConslLog.log("Can Servlet - Finish");

        doGet(req, resp);
    }

    private ServletFileUpload createDefaultFactory() {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        return new ServletFileUpload(factory);
    }

    private int loadImg(HttpServletRequest req, ServletFileUpload upload)
            throws IOException {
        int rsl = -1;
        try {
            List<FileItem> items = upload.parseRequest(req);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    rsl = PsqlStoreImg.toBaseFile(item.getInputStream().readAllBytes());
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    private void updCandidate(HttpServletRequest req, int canId, String newName, int imgId) {
        Candidate temp = PsqlStoreCandidate.instOf().findById(canId);

        ConslLog.log("UPD");
        ConslLog.log("BEFORE");
        ConslLog.log("temp id: ", temp.getId());
        ConslLog.log("temp name: ", temp.getName());
        ConslLog.log("temp imgId: ", temp.getImgId());
        ConslLog.log("new imgId: ", imgId);
        ConslLog.log("new name: ", newName);

        boolean change = false;
        if (newName != null) {
            temp.setName(newName);
            change = true;
        }
        if (imgId != -1) {
            temp.setImgId(imgId);
            change = true;
        }
        if (change) {
            PsqlStoreCandidate.instOf().save(temp);
        }

        ConslLog.log("AFTER");
        ConslLog.log("temp id: ", temp.getId());
        ConslLog.log("temp name: ", temp.getName());
        ConslLog.log("temp imgId: ", temp.getImgId());
        ConslLog.log("new imgId: ", imgId);
        ConslLog.log("new name: ", newName);
    }
}
