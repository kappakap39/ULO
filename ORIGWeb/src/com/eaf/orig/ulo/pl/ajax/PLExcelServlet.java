package com.eaf.orig.ulo.pl.ajax;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.utility.AttachmentUtility;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil;
import com.eaf.orig.ulo.pl.app.utility.RenderExcelFileUtility;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;
import com.eaf.orig.ulo.pl.model.app.PLAttachmentHistoryDataM;
import com.eaf.orig.ulo.pl.model.app.PLImportCreditLineDataM;
import com.eaf.orig.ulo.pl.model.app.PLImportSpecialPointDataM;
import com.eaf.orig.ulo.pl.model.app.PLResponseImportSpecialPointDataM;

public class PLExcelServlet extends HttpServlet implements Servlet {
	
	private Logger logger = Logger.getLogger(this.getClass());

	public PLExcelServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String requestType = request.getParameter("requestType");
		request.getSession().removeAttribute("SEARCH_DATAM");

		DataFormatUtility dataUtil = new DataFormatUtility();
		int maxMemorySize = 10 * 1024 * 1024;
		AttachmentUtility attachmentUtil = AttachmentUtility.getInstance();
		double maxFileSize = attachmentUtil.getMaxFileSize() * 1024;

		String tempDir = attachmentUtil.getAttachmentTempPath();

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Set factory constraints
		factory.setSizeThreshold(maxMemorySize);
		factory.setRepository(new File(tempDir));

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// String appString

		PLAttachmentHistoryDataM attachmentDataM = null;
		String session_id = String.valueOf(Math.round(Math.random() * Math.pow(10, 16))) + String.valueOf(dataUtil.getSysTimeStamp().getTime());
		logger.debug("sessionId:" + session_id);

		String dataDate = null;
		boolean hasSizeError = false;

		try {
			// Parse the request
			List /* FileItem */items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			FileItem importFile = null;

			while (iter.hasNext()) {

				FileItem item = (FileItem) iter.next();
				String name = item.getFieldName();
				if (item.isFormField()) {

					String value = item.getString();
					logger.debug("Form field " + name + " with value " + value + " detected.");
					if (!OrigUtil.isEmptyString(name) && "dataDate".equalsIgnoreCase(name)) {
						dataDate = item.getString();
						logger.debug("dataDate = " + dataDate);
					}

				} else {

					logger.debug("File field " + name + " with file name " + item.getName() + " detected.");
					logger.debug("Content Type=" + item.getContentType());
					// Process the input stream
					if (item.getSize() < maxFileSize) {
						if ("importFile".equalsIgnoreCase(name)) {
							attachmentDataM = AttachmentUtility.getInstance().createFile(item);
							importFile = item;
						}

					} else {
						SearchHandler handler = (SearchHandler) request.getSession().getAttribute("SEARCH_DATAM");
						if (handler == null) { handler = new SearchHandler(); }

						SearchHandler.SearchDataM searchM = handler.getSearchM();
						if (searchM == null) { searchM = new SearchHandler.SearchDataM(); }

						searchM.setPrefixErrorMsg(PLMessageResourceUtil.getTextDescription(request, "ERROR_FILE_SIZE"));
						handler.PushErrorMessage("error", " ");
						handler.setSearchM(searchM);
						request.getSession().setAttribute("SEARCH_DATAM", handler);

						String redirectUrl = "FrontController?page=IMPORT_SPECIAL_POINT";
						logger.debug("@@@@@ redirectUrl:" + redirectUrl);
						try {
							response.sendRedirect(redirectUrl);
						} catch (IOException e) {
							logger.fatal(e.getMessage());
						}

						break;
					}

				}

			}

			if (!hasSizeError && attachmentDataM != null) {
				File fileCreditCreate = new File(attachmentDataM.getFilePath() + File.separator + attachmentDataM.getFileName());
				importFile.write(fileCreditCreate);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Error:" + e);
			String errorMessage = "<span class='BigtodotextRedLeft'>System Error, please contact Administrator</span><script>parent.cancelBlockScreen();</script>";
			request.getSession().setAttribute("IMPORT_RESPONSE", errorMessage);
			// this.removeAllFile(attachFileVT);
		}

		if (requestType != null && OrigConstant.ExcelType.IMPORT_SPECIAL_POINT.equals(requestType)) {
			request.getSession().removeAttribute("respone");
			IMPORT_SPECIAL_POINT(request, response, attachmentDataM, dataDate);

		} else {

			String fileName = "report.xls";
			String paramFilesName = request.getParameter("fileName");
			if (!OrigUtil.isEmptyString(paramFilesName)) {
				fileName = paramFilesName;
			}

			response.setDateHeader("Last-Modified", System.currentTimeMillis());
			// response.setHeader("Pragma", "no-cache");
			response.setHeader("Content-disposition", "attachment;filename=" + fileName);
			response.setContentType("application/vnd.ms-excel");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String requestType = request.getParameter("requestType");
		logger.debug("requestType = " + requestType);
		if (ServletFileUpload.isMultipartContent(request)) {
			doGet(request, response);
		} else {
			if (requestType != null && OrigConstant.ExcelType.EXPORT_REJECT_REPORT_SPECIAL.equals(requestType)) {
				
				RenderExcelFileUtility xlsRender = new RenderExcelFileUtility();
				String fileName = "RejectReport.xls";

				response.setDateHeader("Last-Modified", System.currentTimeMillis());
				// response.setHeader("Pragma", "no-cache");
				response.setHeader("Content-disposition", "attachment;filename=" + fileName);
				response.setContentType("application/vnd.ms-excel");

				Vector<PLImportSpecialPointDataM> errImportVect = (Vector<PLImportSpecialPointDataM>) request.getSession().getAttribute("importReport");
				logger.debug("errImportVect = " + errImportVect);
				xlsRender.renderRejectReportXLS(request, response, errImportVect);

			} else if (requestType != null && OrigConstant.ExcelType.EXPORT_REJECT_REPORT_CREDIT_LINE.equals(requestType)) {
				RenderExcelFileUtility xlsRender = new RenderExcelFileUtility();
				String fileName = "RejectReportCreditLine.xls";

				String paramFilesName = request.getParameter("fileName");
				if (!OrigUtil.isEmptyString(paramFilesName)) {
					fileName = paramFilesName;
				}
				response.setDateHeader("Last-Modified", System.currentTimeMillis());
				// response.setHeader("Pragma", "no-cache");
				response.setHeader("Content-disposition", "attachment;filename=" + fileName);
				response.setContentType("application/vnd.ms-excel");

				Vector<PLImportCreditLineDataM> errImportVect = (Vector<PLImportCreditLineDataM>) request.getSession().getAttribute("IMPORT_REJECT");
				logger.debug("errImportVect = " + errImportVect);
				xlsRender.renderRejectReportCreditLineXLS(request, response, errImportVect);
				
			} else if (requestType != null && OrigConstant.ExcelType.EXPORT_CONSENT.equals(requestType)) {
				
				RenderExcelFileUtility xlsRender = new RenderExcelFileUtility();
				String fileName = "report.xls";

				String paramFilesName = request.getParameter("fileName");
				if (!OrigUtil.isEmptyString(paramFilesName)) {
					fileName = paramFilesName;
				}

				response.setDateHeader("Last-Modified", System.currentTimeMillis());
				// response.setHeader("Pragma", "no-cache");
				response.setHeader("Content-disposition", "attachment;filename=" + fileName);
				response.setContentType("application/vnd.ms-excel");
				xlsRender.renderXLS(request, response);
				
			}
		}

	}

	private void removeAllFile(Vector<PLAttachmentHistoryDataM> attachVT) {
		if (attachVT != null && attachVT.size() > 0) {
			for (int i = 0; i < attachVT.size(); i++) {
				PLAttachmentHistoryDataM attachM = attachVT.get(i);
				this.removeFile(attachM);
			}
		}
	}

	private void removeFile(PLAttachmentHistoryDataM attachM) {
		if (!OrigUtil.isEmptyObject(attachM)) {
			logger.debug("@@@@@ remove directory:" + attachM.getFilePath());
			File directory = new File(attachM.getFilePath());
			// make sure directory exists
			if (!directory.exists()) {
				logger.debug("@@@@@ Directory does not exist.");
				System.exit(0);
			} else {
				try {
					delete(directory);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(0);
				}
			}
		}
	}

	private void delete(File file) throws IOException {
		if (file.isDirectory()) {
			// directory is empty, then delete it
			if (file.list().length == 0) {
				file.delete();
				logger.debug("@@@@@ Directory is deleted : " + file.getAbsolutePath());
			} else {
				// list all the directory contents
				String files[] = file.list();
				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);
					// recursive delete
					delete(fileDelete);
				}
				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
					logger.debug("@@@@@ Directory is deleted : " + file.getAbsolutePath());
				}
			}
		} else {
			// if file, then delete it
			file.delete();
			logger.debug("@@@@@ File is deleted : " + file.getAbsolutePath());
		}
	}

	private void IMPORT_SPECIAL_POINT(HttpServletRequest request, HttpServletResponse response, PLAttachmentHistoryDataM attachmentDataM, String dataDate) {

		RenderExcelFileUtility xlsRender = new RenderExcelFileUtility();
		PLResponseImportSpecialPointDataM resImportM;
		try {
			resImportM = xlsRender.importExcelFile(request, response, attachmentDataM, dataDate);

			if (!OrigUtil.isEmptyObject(resImportM) && resImportM.getTotalRecord() != 0) {
				request.getSession().setAttribute("importReport", resImportM.getErrorImportReportVect());

				int sucessRecord = resImportM.getTotalRecord() - resImportM.getErrorImportReportVect().size();
				StringBuilder renderResult = new StringBuilder("");
				renderResult.append("<div class='PanelThird'><table class='TableFrame'><tr align='center'>");
				renderResult.append("<td class='textR' width='50%'><b>Total</b>&nbsp;</td>");
				renderResult.append("<td class='textL' width='50%'><b>" + resImportM.getTotalRecord() + "</b></td>");
				renderResult.append("</tr>");
				renderResult.append("<tr>");
				renderResult.append("<td class='textR' width='50%'>Success&nbsp;</td>");
				renderResult.append("<td class='textL' width='50%'>" + sucessRecord + "</td>");
				renderResult.append("</tr>");
				renderResult.append("<tr>");
				renderResult.append("<td class='textR' width='50%'>Reject&nbsp;</td>");
				if (resImportM != null && resImportM.getErrorImportReportVect().size() > 0) {
					renderResult.append("<td class='textL' width='50%'>");
					renderResult.append("<table width='100%' align='center' border='0' cellspacing='0' cellpadding='0' >");
					renderResult.append("<tr>");
					renderResult.append("<td class='textL' width='50%'>" + resImportM.getErrorImportReportVect().size() + "</td>");
					renderResult.append("<td align='textR'><input type='button' value='Export Reject Report' onclick='exportExcel()' class='button'></td>");
					renderResult.append("</tr></table></td>");
				} else {
					renderResult.append("<td class='textL' width='50%'>" + resImportM.getErrorImportReportVect().size() + "</td>");
				}
				renderResult.append("</tr></table></div>");
				request.getSession().setAttribute("respone", renderResult.toString());

			} else {
				SearchHandler handler = new SearchHandler();// (SearchHandler)request.getSession().getAttribute("SEARCH_DATAM");
				// if(handler==null){handler = new SearchHandler();}

				SearchHandler.SearchDataM searchM = handler.getSearchM();
				if (searchM == null) {
					searchM = new SearchHandler.SearchDataM();
				}

				searchM.setPrefixErrorMsg(PLMessageResourceUtil.getTextDescription(request, "ERROR_FORMAT_FILE"));
				handler.PushErrorMessage("error", " ");
				handler.setSearchM(searchM);
				request.getSession().setAttribute("SEARCH_DATAM", handler);
			}
		} catch (Exception e) {
			logger.fatal(e.getMessage());
		} finally {
			String redirectUrl = "FrontController?page=IMPORT_SPECIAL_POINT";
			logger.debug("@@@@@ redirectUrl:" + redirectUrl);
			try {
				response.sendRedirect(redirectUrl);
			} catch (IOException e) {
				logger.fatal(e.getMessage());
			}
		}

	}
}
