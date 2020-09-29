package ru.job4j.dreamjob.servlet;

import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.store.psql.StoreUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * url-pattern: /auth.do
 */
public class AuthServlet extends HttpServlet {

    /**
     * submit auth user from login.jps
     * <p>
     * email : String - user email(text from input)
     * password : String - user password(text from input).
     * <p>
     * goto:
     * auth pass >>> /post/posts.do
     * auth fail >>> login.jsp (reload page)
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (authCheck(req)) {
            resp.sendRedirect(req.getContextPath() + "/post/posts.do");
        } else {
            req.setAttribute("error", "Не верный email или пароль");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }

    private boolean authCheck(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if ("root@local".equals(email) && "root".equals(password)) {
            sessionSetUser(session, new User(0, "Admin", email, password));
            return true;
        } else {
            var user = StoreUser.instOf().getByEmail(email);
            if (checkUser(user)) {
                sessionSetUser(session, user);
                return true;
            }
        }
        return false;
    }

    private boolean checkUser(User user) {
        return !user.getEmail().equalsIgnoreCase("")
                && !user.getPassword().equalsIgnoreCase("");
    }

    private void sessionSetUser(HttpSession sc, User user) {
        sc.setAttribute("user", user);
//        var logTemp = sc.getAttribute("user"); // in mock test == null
//        ConslLog.log("Auth user", logTtemp);
    }
}