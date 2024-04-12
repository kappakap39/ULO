package com.eaf.orig.ulo.pl.ajax;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.model.app.PLAttachmentHistoryDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class PLDownLoadAttachmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(PLDownLoadAttachmentServlet.class);

	public PLDownLoadAttachmentServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("download Attachment Servlet");

		if (request.getParameter("attachmentId") != null) {
			String attachmentId = request.getParameter("attachmentId");
			logger.debug("attachmentId=" + attachmentId);
			PLAttachmentHistoryDataM attachmentM = null;
			try{
				attachmentM = PLORIGEJBService.getORIGDAOUtilLocal().loadModelOrigAttachmentHistoryMFromAttachId(attachmentId);
			}catch(Exception e){
				logger.error("Error ", e);
			}
			if(null != attachmentM) {
				logger.debug("file Name=" + attachmentM.getFileName());
				response.setDateHeader("Last-Modified", System.currentTimeMillis());
				//response.setHeader("Pragma", "no-cache");
				response.setDateHeader("Expires", 0);
				response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode(attachmentM.getFileName(),"UTF-8"));
				response.setContentType(attachmentM.getMimeType());
				String filePath = attachmentM.getFilePath() + File.separator + attachmentM.getFileName();
				logger.debug("File Path=" + filePath);
				File downloadFile = new File(filePath);
				logger.debug("File exist =" + downloadFile.exists());
				logger.debug("File can read =" + downloadFile.canRead());
				logger.debug("File can write =" + downloadFile.canWrite());
				logger.debug("File can Excute =" + downloadFile.canExecute());
				if (downloadFile.exists()) {
					FileInputStream is = new FileInputStream(downloadFile);
					OutputStream output = response.getOutputStream();
					logger.debug("Input Stream=" + is);
					if (is != null) {
						int read = 0;
						byte[] bytes = new byte[1024];
						while ((read = is.read(bytes)) != -1) {
							output.write(bytes, 0, read);
						}
						output.flush();
						output.close();
					}
				}
			}
		} else if (request.getParameter("fileName") != null) {
			try {
				String fileName = request.getParameter("fileName");
				String interFaceType = request.getParameter("interface_type");

				ORIGCacheDataM cacheM = ORIGCacheUtil.getInstance().GetListboxCacheDataM(OrigConstant.fieldId.INTERFACE_TYPE,
						OrigConstant.BusClass.FCP_ALL_ALL, interFaceType);

				String filePath = cacheM.getSystemID11()+ File.separator + OrigConstant.BACKUP + File.separator + DataFormatUtility.dateToStringYYYYMMDD(new Date());

				if (OrigConstant.InterfaceType.INCREASE_CREDIT_LINE.equals(interFaceType)) {
					String attachmentId = "CL" + DataFormatUtility.dateToStringYYYYMMDD(new Date());
					PLAttachmentHistoryDataM attachM = PLORIGEJBService.getORIGDAOUtilLocal().loadModelOrigAttachmentHistoryMFromAttachId(attachmentId);
					filePath = attachM.getFilePath();
				}

				response.setDateHeader("Last-Modified", System.currentTimeMillis());
				//response.setHeader("Pragma", "no-cache");
				response.setDateHeader("Expires", 0);
				response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode(fileName,"UTF-8"));
				response.setContentType(this.getManualMimeType(fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length())));

				fileName = filePath + File.separator + fileName;

				logger.debug("File Path=" + fileName);
				FileInputStream is = new FileInputStream(new File(fileName));
				OutputStream output = response.getOutputStream();
				logger.debug("is=" + is);
				if (is != null) {
					int read = 0;
					byte[] bytes = new byte[1024];
					while ((read = is.read(bytes)) != -1) {
						output.write(bytes, 0, read);
					}
					output.flush();
					output.close();
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private String getManualMimeType(String fileType) {
		String resultMineType = null;
		if ("xlsx".equalsIgnoreCase(fileType)) {
			resultMineType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		} else if ("xls".equalsIgnoreCase(fileType)) {
			resultMineType = "application/vnd.ms-excel";
		} else if ("jpg".equalsIgnoreCase(fileType) || "jpeg".equalsIgnoreCase(fileType)) {
			resultMineType = "image/jpeg";
		} else if ("gif".equalsIgnoreCase(fileType)) {
			resultMineType = "image/gif";
		} else if ("doc".equalsIgnoreCase(fileType)) {
			resultMineType = "application/msword";
		} else if ("docx".equalsIgnoreCase(fileType)) {
			resultMineType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
		} else if ("txt".equalsIgnoreCase(fileType)) {
			resultMineType = "text/plain";
		} else if ("ppt".equalsIgnoreCase(fileType)) {
			resultMineType = "application/vnd.ms-powerpoint";
		} else if ("pptx".equalsIgnoreCase(fileType)) {
			resultMineType = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
		} else if ("xps".equalsIgnoreCase(fileType)) {
			resultMineType = "application/vnd.ms-xpsdocument";
		} else if ("pdf".equalsIgnoreCase(fileType)) {
			resultMineType = "application/pdf";
		} else {
			resultMineType = "content/unknown";
		}
		return resultMineType;
	}
}
