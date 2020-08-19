package ru.job4j.dreamjob.servlet;

import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.store.psql.StoreUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * url-pattern: /reg.do
 */
public class RegServlet extends HttpServlet {
    /**
     * get reg page
     * <p>
     * goto: /reg.jsp
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("reg.jsp").forward(req, resp);
    }

    /**
     * submit reg user from reg.jps
     * <p>
     * name : String - user's name(text from input).
     * email : String - user's email(text from input).
     * password : String - user's password(text from input).
     * <p>
     * goto:
     * reg pass >>> /auth.do
     * <p>
     * * if here WILL BE validate !!!
     * * reg fail >>> /reg.do (reload page)
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // maybe need some validate to registration????

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        StoreUser.instOf().save(new User(
                0, name,
                email, password
        ));
        resp.sendRedirect(req.getContextPath() + "/auth.do");
    }
}
