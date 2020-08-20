package ru.job4j.dreamjob.example.image.servlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.job4j.dreamjob.store.psql.PsqlStoreImg;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * url-pattern: /image/upload.do
 */
public class UploadServlet extends HttpServlet {
    /**
     * load: upload.jsp
     * get image's id on upload.jsp - for next show it
     * <p>
     * VAR TO JSP
     * images : List<String> - image's ids from base. (for )
     * <p>
     * goto: /image/upload.jsp
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        var images = PsqlStoreImg.instOf().getAllIIds();
        req.setAttribute("imagesIds", images);
        req.getRequestDispatcher("/image_example/upload.jsp").forward(req, resp);
    }

    /**
     * save img in base.
     * <p>
     * items : List<FileItem> - image(s) from request tempDir.(from input on upload.jsp)
     * <p>
     * goto: doGet(req, resp) -> /image/upload.jsp
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        loadImg(req, createDefaultFactory());
        doGet(req, resp);
    }

    private ServletFileUpload createDefaultFactory() {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        return new ServletFileUpload(factory);
    }

    private int loadImg(HttpServletRequest req, ServletFileUpload upload)
            throws IOException {
        int rsl = -1;
        try {
            List<FileItem> items = upload.parseRequest(req);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    rsl = PsqlStoreImg.instOf().toBaseFile(item.getInputStream().readAllBytes());
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        return rsl;
    }
}