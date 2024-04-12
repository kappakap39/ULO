package com.eaf.orig.shared.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.eaf.orig.shared.utility.CMConstants;
import com.eaf.orig.shared.utility.CacheParam;
import com.eaf.orig.shared.utility.FTPUtil;

public class UploadImageServlet extends HttpServlet implements Servlet {
    Logger log = Logger.getLogger(UploadImageServlet.class);

    /*
     * (non-Java-doc)
     * 
     * @see javax.servlet.http.HttpServlet#HttpServlet()
     */
    public UploadImageServlet() {
        super();
    }

    /*
     * (non-Java-doc)
     * 
     * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest arg0,
     *      HttpServletResponse arg1)
     */
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    /*
     * (non-Java-doc)
     * 
     * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest arg0,
     *      HttpServletResponse arg1)
     */
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String resultStr = "";

        final long maxSize = 10000000;
        final String config = getServletContext().getRealPath("/WEB-INF/CM.conf");
        final String mimeConfigFile = getServletContext().getRealPath("/WEB-INF/mimeType.conf");

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        //Check that we have a file upload request
        log.debug("");
        log.debug("##### Begin to UploadFaxFile Service for Kleasing");

        // Check that we have a file upload request

        log.debug("req2=" + req);
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        log.debug("isMultipart=" + isMultipart);

        log.debug("req3=" + req);
        boolean success = false;
        String redirectUrl = "";

        //CMUtils util = new CMUtils();

        CacheParam.loadConfiguration(config);

        try {

            log.debug("req4=" + req);
            //util.setSessionCMBConnection(req);

            log.debug("req5=" + req);

            if (isMultipart) { // if upload

                //Get Parameter From Request
                String fileType = null;
                String fileName = null;
                String mimeType = null;
                long size = 0;

                // Create a factory for disk-based file items
                DiskFileItemFactory factory = new DiskFileItemFactory();

                // Create a new file upload handler
                //ServletFileUpload upload = new ServletFileUpload(factory);
                DiskFileUpload upload = new DiskFileUpload();

                log.debug("req6=" + req);
                // Parse the request

                List /* FileItem */items = upload.parseRequest(req);

                // Prepare variable for uploaded file
                Iterator iter = items.iterator();

                FileItem fItem = null;

                // Process the uploaded items
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();

                    if (item.isFormField()) {

                        String name = item.getFieldName();
                        String value = item.getString();

                        if (CMConstants.UPLOAD_REDIRECT_URL.equals(name)) {
                            redirectUrl = value;
                        }
                    } else {

                        fileName = item.getName();
                        fileName = getFileName(fileName);
                        size = item.getSize();
                        if (size > maxSize) {
                            out.println("size=" + size + " bytes is over maximum (" + maxSize + " bytes)");
                            return;
                        }
                        fItem = item;
                    }
                }

                log.debug("redirectUrl=" + redirectUrl);

                /** if parameters ready */
                if (fileName != null && !fileName.equals("") && fItem != null) {

                    /** Prepare path to write */
                    String serverName = CacheParam.getFaxFileUploadServer();
                    String serverFolder = CacheParam.getFaxFileUploadFolder();
                    String serverUser = CacheParam.getFaxFileUploadUser();
                    String serverPassword = CacheParam.getFaxFileUploadPassword();
                    String serverTempPath = CacheParam.getFaxFileUploadTempPath();

                    File f = new File(serverTempPath);
                    if (!f.exists())
                        f.mkdirs();

                    /** write file */
                    String uploadTempFolder = serverTempPath + File.separator + fileName;
                    log.debug("uploadFile=" + uploadTempFolder);
                    File uploadedFile = new File(uploadTempFolder);
                    log.debug("uploadedFile=" + uploadedFile);
                    log.debug("fItem=" + fItem);
                    fItem.write(uploadedFile);

                    log.debug("serverName=" + serverName);
                    log.debug("serverUser=" + serverUser);
                    log.debug("serverPassword=" + serverPassword);
                    FTPUtil ftp = new FTPUtil(serverName, serverUser, serverPassword);

                    if (!ftp.ftpToFolder(uploadTempFolder, fileName, serverFolder)) {
                        log.debug("cannot ftp " + fileName + " to real folder");
                        resultStr = "&result=failed";
                    } else {
                        resultStr = "&result=success";
                    }

                    //delete file in temp folder
                    deleteFile(uploadTempFolder);

                }
            }
            if (redirectUrl != null && !redirectUrl.equals("")) {
                log.debug("sendredirect url=" + redirectUrl + resultStr);
                resp.sendRedirect(redirectUrl + resultStr);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("found exception:" + e.getMessage());
        } finally {
            //			try{
            //				util.disconnectCMBConn(req);
            //			}catch(Exception e){
            //				e.printStackTrace();
            //			}
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getFileName(String path) {

        String fileName = null;
        String separator = "\\";

        //int pos = path.lastIndexOf(separator);
        //fileName =path.substring(pos+1);
        Calendar cl = Calendar.getInstance();
        DecimalFormat dfYear = new DecimalFormat("0000");
        DecimalFormat dfMonth = new DecimalFormat("00");
        DecimalFormat dfDay = new DecimalFormat("00");
        DecimalFormat dfHour = new DecimalFormat("00");
        DecimalFormat dfMin = new DecimalFormat("00");
        DecimalFormat dfSec = new DecimalFormat("00");
        DecimalFormat dfMilisec = new DecimalFormat("000");
        fileName = "SCN_" + dfYear.format(cl.get(Calendar.YEAR)) + dfMonth.format(cl.get(Calendar.MONTH )+1) + dfDay.format(cl.get(Calendar.DAY_OF_MONTH))
                + dfHour.format(cl.get(Calendar.HOUR_OF_DAY)) +dfMin.format(cl.get(Calendar.MINUTE))+dfSec.format(cl.get(Calendar.SECOND))+"_"+
                dfMilisec.format( cl.get(Calendar.MILLISECOND)%1000d)+
                ".TIF";
        return fileName;
    }

    private boolean deleteFile(String fileName) throws Exception {
        boolean deleteResult = false;
        try {
            File f = new File(fileName);
            deleteResult = f.delete();
            if (!deleteResult) {
                log.debug("Cannot delete " + fileName);
            } else {
                log.debug("Delete File " + fileName + "  successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deleteResult;
    }

}