package ru.job4j.dreamjob.servlet.can;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.psql.PsqlStoreCandidate;
import ru.job4j.dreamjob.store.psql.PsqlStoreImg;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * url-pattern: /candidate/delete.do
 */
public class CandidateDeleteServlet extends HttpServlet {
    /**
     * del can with img
     * <p>
     * id : int - candidate's id in base.
     * <p>
     * goto: /candidate/candidates.do -> candidates.jsp.jsp
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Candidate can = PsqlStoreCandidate.instOf().findById(id);

        PsqlStoreImg.deleteById(can.getImgId());
        PsqlStoreCandidate.instOf().deleteById(id);

        resp.sendRedirect(req.getContextPath() + "/candidate/candidates.do");
    }
}
