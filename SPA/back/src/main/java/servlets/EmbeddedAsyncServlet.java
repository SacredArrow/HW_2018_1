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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

//@WebServlet (name = "EmbeddedAsyncServlet", urlPatterns = {"/file"})
public class EmbeddedAsyncServlet extends HttpServlet {
    public static ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();


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
                    String filter_name = req.getParameter("filter");
                    String id = req.getParameter("id");
                    String format = req.getParameter("format");
                    map.put(id, 0);
                    System.out.println(filter_name);
                    String file_name = "res/" +id+"."+ format;
                    System.out.println(file_name);
                    File file = new File(file_name);
                    Filter filter = null;
                    switch (filter_name) {
                        case "Negative filter" : filter = new NegativeFilter(file, id, format);
                        break;
                        case "White/Black filter" : filter = new BlackWhiteFilter(file, id, format);
                        break;
                        case "Blur filter" : filter = new BlurFilter(file, id, format);
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
        final AsyncContext ctxt = req.startAsync();

        ctxt.start(() -> {
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
            String sessionID = "";
            while (iter.hasNext()) {
                FileItem item = iter.next();

                if (! item.isFormField()) {
                    String name = item.getName();
                    String format = name.substring(name.indexOf('.') + 1);
                    sessionID = req.getSession().getId();
                    String file_name = "res/" + sessionID + "." + format;
                    System.out.println(file_name);
                    File uploadedFile = new File(file_name);
                    try {
                        item.write(uploadedFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                resp.getWriter().append(sessionID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ctxt.complete();

        });

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