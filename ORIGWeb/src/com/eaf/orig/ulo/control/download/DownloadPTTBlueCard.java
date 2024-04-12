package com.eaf.orig.ulo.control.download;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.file.DownloadControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.orig.common.dao.OrigImportDAO;
import com.eaf.orig.common.factory.ImportManualFactory;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.control.util.OrigDownloadFileUtil;
import com.eaf.orig.ulo.model.download.DownLoadM;
import com.eaf.orig.ulo.model.membership.RunningParamStackDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class DownloadPTTBlueCard implements DownloadControl {
	private static Logger logger = Logger.getLogger(DownloadPTTBlueCard.class);
	String IMPORT_PTT_BLUE_CARD_OUTPUT_NAME = SystemConfig.getProperty("IMPORT_PTT_BLUE_CARD_OUTPUT_NAME");
	String IMPORT_PTT_BLUE_CARD_EXISTING_TEMPLATE_FILE = SystemConfig.getProperty("IMPORT_PTT_BLUE_CARD_EXISTING_TEMPLATE_FILE");
	String IMPORT_PTT_BLUE_CARD_ERROR_OUTPUT_NAME = SystemConfig.getProperty("IMPORT_PTT_BLUE_CARD_ERROR_OUTPUT_NAME");
	String DOWNLOAD_PATH = SystemConfig.getProperty("DOWNLOAD_PATH");

	@Override
	public boolean requiredEvent() {
		return false;
	}

	@Override
	public ProcessResponse processDownload(HttpServletRequest request) throws Exception {
		ProcessResponse processResponse = new ProcessResponse();
		try {
			DownLoadM download = new DownLoadM();
			String processName = request.getParameter("PROCESS_NAME");
			String PARAM_TYPE_PTT = SystemConstant.getConstant("PARAM_TYPE_PTT");
			logger.debug("processName : " + processName);
			if (OrigDownloadFileUtil.DOWNLOAD_PROCESS_NAME.DOWNLOAD_INVALID.equals(processName)) {
				String fileName = OrigDownloadFileUtil.getGenerateFileName(IMPORT_PTT_BLUE_CARD_ERROR_OUTPUT_NAME);
				download.setFileName(fileName);
				download.setFilePath(DOWNLOAD_PATH);
				download.setMimeType("multipart/form-data");
				processResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
				processResponse.setData(new Gson().toJson(download));
			} else if (OrigDownloadFileUtil.DOWNLOAD_PROCESS_NAME.DOWNLOAD_EXISTING.equals(processName)) {
				OrigImportDAO dao = ImportManualFactory.getOrigImportDAO();
				ArrayList<RunningParamStackDataM> listRunningParamStackDataM = dao.selectRunningParamStack(PARAM_TYPE_PTT);
				ArrayList<Map<String, Object[]>> dataList = new ArrayList<Map<String, Object[]>>();
				if (!Util.empty(listRunningParamStackDataM)) {
					for (RunningParamStackDataM data : listRunningParamStackDataM) {
						Map<String, Object[]> hData = new TreeMap<String, Object[]>();
						hData.put(
								OrigDownloadFileUtil.EXCEL_DATA_KEY,
								new Object[] {

								replaceNullWithSpace(data.getParamValue()), replaceNullWithSpace(data.getParamType()),
										replaceNullWithSpace(data.getStatus()),
										replaceNullWithSpace(convertDateToString(data.getUpdateDate().getTime(), FormatUtil.Format.DDMMYYYY)),

								});
						dataList.add(hData);
					}
				}
				String fileName = OrigDownloadFileUtil.getGenerateFileName(IMPORT_PTT_BLUE_CARD_OUTPUT_NAME);
				OrigDownloadFileUtil.createExceldRecordData(dataList, fileName, IMPORT_PTT_BLUE_CARD_EXISTING_TEMPLATE_FILE, DOWNLOAD_PATH);
				download.setFileName(fileName);
				download.setFilePath(DOWNLOAD_PATH);
				download.setMimeType("multipart/form-data");
				processResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
				processResponse.setData(new Gson().toJson(download));
			}
		} catch (Exception e) {
			logger.fatal("ERROR", e);
			processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processResponse.setErrorData(ErrorController.error(e));
		}
		return processResponse;
	}

	@Override
	public ProcessResponse processEvent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}

	private Object replaceNullWithSpace(Object o) {
		if (Util.empty(o)) {
			String str = "";
			o = str;
		}
		return o;
	}

	public static String convertDateToString(long date, String format) {
		//
		return FormatUtil.display(new java.sql.Date(date), FormatUtil.EN, format);
	}
}
