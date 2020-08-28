package ru.job4j.dreamjob.servlet.ajax;

import ru.job4j.dreamjob.ahelptools.ConslLog;
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
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
////        resp.setContentType("application/json");
//        resp.setContentType("text/json");
//        resp.setCharacterEncoding("UTF-8");
//        resp.setHeader("Access-Control-Allow-Origin", "*");
//
//        var cityList = PsqlStoreCity.instOf().getAll();
//
//        try (var writer = new PrintWriter(resp.getOutputStream())) {
//
//            ConslLog.log("LIST");
////        cityList.forEach(ConslLog::log);
//            ConslLog.log("LIST");
//
//            var jsonBuilder = new StringBuilder("[ ");
//            for (var city : cityList) {
//                jsonBuilder.append("\"").append(city).append("\"");
//                jsonBuilder.append(", ");
//            }
//            jsonBuilder.append("\"").append("none").append("\"");
//            jsonBuilder.append(" ]");
//
//            var rsl = "{ \"value\" : " + jsonBuilder.toString() + " }";
//            writer.println(rsl);
////        writer.println(jsonBuilder.toString());
//
//
////        cityList.forEach(writer::println);
////        writer.println(jsonBuilder.toString());
////        writer.println("Nice to meet you, " + 000);
////        writer.println("Nice to meet you, " + 111);
//            writer.flush();
//
//            ConslLog.log("LIST");
//            ConslLog.log(rsl);
////        ConslLog.log(jsonBuilder.toString());
//            ConslLog.log("LIST");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("application/json");
//        resp.setContentType("text/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Access-Control-Allow-Origin", "*");

        var cityList = PsqlStoreCity.instOf().getAll();

        try (var writer = new PrintWriter(resp.getOutputStream())) {

            var jsonBuilder = new StringBuilder("[ ");
            for (var city : cityList) {
                jsonBuilder.append("\"").append(city).append("\"");
                jsonBuilder.append(", ");
            }
            jsonBuilder.append("\"").append("none").append("\"");
            jsonBuilder.append(" ]");

            var rsl = "{ \"value\" : " + jsonBuilder.toString() + " }";
            writer.println(jsonBuilder.toString());
//        writer.println(jsonBuilder.toString());


//        cityList.forEach(writer::println);
//        writer.println(jsonBuilder.toString());
//        writer.println("Nice to meet you, " + 000);
//        writer.println("Nice to meet you, " + 111);
            writer.flush();

            ConslLog.log("LIST");
            ConslLog.log(rsl);
//        ConslLog.log(jsonBuilder.toString());
            ConslLog.log("LIST");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
