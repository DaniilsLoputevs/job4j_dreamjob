package ru.job4j.dreamjob.servlet.ajax;

import ru.job4j.dreamjob.store.psql.PsqlStoreCity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * url-pattern: /city.get
 */
public class CityServlet extends HttpServlet {

    /**
     * get all cities.
     * <p>
     * No Attributes
     * <p>
     * goto: NONE - ajax script
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("text/json");

        var cityList = PsqlStoreCity.instOf().getAll();
        try (var writer = new PrintWriter(resp.getOutputStream())) {

            var jsonBuilder = new StringBuilder("[ ");
            for (var city : cityList) {
                jsonBuilder.append("\"").append(city).append("\"");
                jsonBuilder.append(", ");
            }
            jsonBuilder.append("\"").append("none").append("\"");
            jsonBuilder.append(" ]");

            writer.println(jsonBuilder.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
