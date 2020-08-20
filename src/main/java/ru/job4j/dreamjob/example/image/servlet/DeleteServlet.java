package ru.job4j.dreamjob.example.image.servlet;

import ru.job4j.dreamjob.store.psql.PsqlStoreImg;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * url-pattern: /image/delete
 */
public class DeleteServlet extends HttpServlet {
    /**
     * Delete img from base.
     * <p>
     * imgId : int - image's id in base.
     * <p>
     * goto: No
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int imgId = Integer.parseInt(req.getParameter("imgId"));
        PsqlStoreImg.instOf().deleteById(imgId);
        req.getRequestDispatcher("/image/upload.do").forward(req, resp);
    }
}
