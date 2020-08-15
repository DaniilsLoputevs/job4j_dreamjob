package ru.job4j.dreamjob.servlet.can;

import ru.job4j.dreamjob.store.psql.PsqlStoreImg;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * url-pattern: /candidate/image.get
 */
public class CandidateImgServlet extends HttpServlet {

    /**
     * Download || show IMG
     * <p>
     * imgId : int - image's id in base.
     * <p>
     * goto: No
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int imgId = Integer.parseInt(req.getParameter("imgId"));

//        ConslLog.log("Img Servlet");
//        ConslLog.log("canImgId", imgId);
//        ConslLog.log("CandidatesServlet");
//        ConslLog.log("req Param - id", req.getParameter("id"));
//        ConslLog.log("parse req Param - id", Integer.parseInt(req.getParameter("id")));
//        ConslLog.log("req Param - name", req.getParameter("name"));
//        ConslLog.log("Img Servlet");

        resp.setContentType("image/png");
        resp.setHeader("Content-Disposition", "attachment; filename=\"byte[]\"");

        byte[] bytes = PsqlStoreImg.fromBaseById(imgId);
        resp.getOutputStream().write(bytes);
    }

}
