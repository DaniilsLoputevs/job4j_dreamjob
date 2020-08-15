package ru.job4j.dreamjob.servlet.candidate;

import ru.job4j.dreamjob.store.psql.PsqlImgStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CandidateImgDelServlet extends HttpServlet {
    /* Delete candidate IMG */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        PsqlImgStore.deleteById(4);
//        PsqlImgStore.deleteById(id);

        resp.sendRedirect(req.getContextPath() + "/candidate/candidates.do");
    }
}
