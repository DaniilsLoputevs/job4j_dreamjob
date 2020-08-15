//package ru.job4j.dreamjob.servlet.candidate;
//
//import ru.job4j.dreamjob.debug.ConslLog;
//import ru.job4j.dreamjob.store.psql.PsqlStoreImg;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class CandidateImgServlet extends HttpServlet {
//    /**
//     * Download || show IMG
//     * <p>
//     * imgId : int - image id in base.
//     */
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//            throws ServletException, IOException {
//
////        ConslLog.log("Img Servlet");
//        int imgId = Integer.parseInt(req.getParameter("imgId"));
////        ConslLog.log("canImgId", imgId);
////        ConslLog.log("CandidatesServlet");
////        ConslLog.log("req Param - id", req.getParameter("id"));
////        ConslLog.log("parse req Param - id", Integer.parseInt(req.getParameter("id")));
////        ConslLog.log("req Param - name", req.getParameter("name"));
////        ConslLog.log("Img Servlet");
//
//        resp.setContentType("image/png");
//        resp.setHeader("Content-Disposition", "attachment; filename=\"byte[]\"");
//
////        byte[] bytes = PsqlStoreImg.fromBaseById(3);
//        byte[] bytes = PsqlStoreImg.fromBaseById(imgId);
//        resp.getOutputStream().write(bytes);
//    }
//
////    /* Upload IMG & upd Candidate*/
////    @Override
////    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
////            throws ServletException, IOException {
////
////        ConslLog.log("Img Servlet");
////        int id = Integer.parseInt(req.getParameter("id"));
////        ConslLog.log("canImgId", id);
////        ConslLog.log("CandidatesServlet");
////        ConslLog.log("req Param - id", req.getParameter("id"));
////        ConslLog.log("parse req Param - id", Integer.parseInt(req.getParameter("id")));
////        ConslLog.log("req Param - name", req.getParameter("name"));
////        ConslLog.log("Img Servlet");
////
////        DiskFileItemFactory factory = new DiskFileItemFactory();
////        ServletContext servletContext = this.getServletConfig().getServletContext();
////        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
////        factory.setRepository(repository);
////        ServletFileUpload upload = new ServletFileUpload(factory);
//////        try {
//////            List<FileItem> items = upload.parseRequest(req);
//////            for (FileItem item : items) {
//////                if (!item.isFormField()) {
//////                    PsqlStoreImg.toBaseFile(item.getInputStream().readAllBytes());
//////                }
//////            }
//////        } catch (FileUploadException e) {
//////            e.printStackTrace();
//////        }
////        int imgId = loadImg(req, upload);
////        updCandidate(req, imgId);
////
////        ConslLog.log("Img Servlet");
////
////        resp.sendRedirect(req.getContextPath() + "/candidate/candidates.do");
////    }
////
////    private int loadImg(HttpServletRequest req, ServletFileUpload upload)
////            throws IOException {
////        int rsl = -1;
////        try {
////            List<FileItem> items = upload.parseRequest(req);
////            for (FileItem item : items) {
////                if (!item.isFormField()) {
////                    rsl = PsqlStoreImg.toBaseFile(item.getInputStream().readAllBytes());
////                }
////            }
////        } catch (FileUploadException e) {
////            e.printStackTrace();
////        }
////        return rsl;
////    }
////
////    private void updCandidate(HttpServletRequest req, int imgId) {
//////        String candidateName = req.getParameter("name");
////        String candidateName = "test";
////        int candidateId = Integer.parseInt(req.getParameter("id"));
////        Candidate temp = PsqlStoreCandidate.findByIdCandidate(candidateId);
////        ConslLog.log("UPD");
////        ConslLog.log("BEFORE");
////        ConslLog.log("id: ", temp.getId());
////        ConslLog.log("name: ", temp.getName());
////        ConslLog.log("imgId: ", temp.getImgId());
////        temp.setName(candidateName);
////        temp.setImgId(imgId);
////        PsqlStoreCandidate.save(temp);
////        ConslLog.log("BEFORE");
////        ConslLog.log("id: ", temp.getId());
////        ConslLog.log("name: ", temp.getName());
////        ConslLog.log("imgId: ", temp.getImgId());
////    }
//}
