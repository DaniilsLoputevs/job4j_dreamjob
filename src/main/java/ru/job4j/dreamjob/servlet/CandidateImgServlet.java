package ru.job4j.dreamjob.servlet;

import ru.job4j.dreamjob.psql_db_connect.PsqlImg;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CandidateImgServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
//        ConslLog.log("canImgId", id);

        resp.setContentType("image/png");
        resp.setHeader("Content-Disposition", "attachment; filename=\"byte[]\"");

        byte[] bytes = PsqlImg.fromBaseById(1);
        resp.getOutputStream().write(bytes);
    }
}
