package servlets;

import filter.BlackWhiteFilter;
import filter.BlurFilter;
import filter.Filter;
import filter.NegativeFilter;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;

//@WebServlet (name = "EmbeddedAsyncServlet", urlPatterns = {"/file"})
public class EmbeddedAsyncServlet extends HttpServlet {
    public static volatile double progress;
    private String filter_name;
    public static String format;

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        return o.readObject();
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(obj);
        return b.toByteArray();
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final AsyncContext ctxt = req.startAsync();
        ctxt.start(() -> {
            System.err.println("Do get");
            String get = req.getParameter("UUID");
            try {
                try {
                    progress = 0;
                    filter_name = req.getParameter("filter");
                    System.out.println(filter_name);
                    File file = new File("res/image." + format);
                    Filter filter = null;
                    switch (filter_name) {
                        case "Negative filter" : filter = new NegativeFilter(file);
                        break;
                        case "White/Black filter" : filter = new BlackWhiteFilter(file);
                        break;
                        case "Blur filter" : filter = new BlurFilter(file);
                        break;
                    }
                    filter.process();
                    FileInputStream fileInputStreamReader = new FileInputStream(file);
                    byte[] bytes = new byte[(int) file.length()];
                    fileInputStreamReader.read(bytes);
                    String encodedfile = new String(Base64.encodeBase64(bytes), "UTF-8");
                    encodedfile = "data:image/" + format + ";base64," + encodedfile;
                    resp.setContentType("text/plain");
                    resp.setHeader("Access-Control-Allow-Origin", "*");
//                                resp.setHeader("Content-Type", "text/plain");

                    resp.getWriter().append(encodedfile);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            ctxt.complete();
        });
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

        System.err.println("Do post");
//                    try {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();

// Configure a repository (to ensure a secure temp location is used)
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);

// Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

// Parse the request
        List<FileItem> items = null;
        try {
            items = upload.parseRequest(req);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        Iterator<FileItem> iter = items.iterator();
        while (iter.hasNext()) {
            FileItem item = iter.next();

            if (! item.isFormField()) {
                String name = item.getName();
                format = name.substring(name.indexOf('.') + 1);
                File uploadedFile = new File("res/image." + format);
                try {
                    item.write(uploadedFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    protected void doOptions(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final AsyncContext ctxt = req.startAsync();

        ctxt.start(() -> {
            System.err.println("Do opt");
//                    try {
            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
            resp.setContentType("text/plain");
            ctxt.complete();
        });
    }
}