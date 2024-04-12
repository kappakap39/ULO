package com.eaf.orig.ulo.control.imports;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.file.ExcelUtil;
import com.eaf.core.ulo.common.file.ImportControl;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.j2ee.pattern.util.JSONUtil;
import com.eaf.orig.common.dao.OrigImportDAO;
import com.eaf.orig.common.dao.exception.ImportManualException;
import com.eaf.orig.common.db.control.DatabaseController;
import com.eaf.orig.common.excel.ExcelGenerate;
import com.eaf.orig.common.factory.ImportManualFactory;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.OrigDownloadFileUtil;
import com.eaf.orig.ulo.manager.imports.CreateRunningParamStackManager;
import com.eaf.orig.ulo.model.membership.RunningParamStackDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class UploadPTTBlueCard extends ExcelGenerate implements ImportControl {
	private static transient Logger logger = Logger.getLogger(UploadPTTBlueCard.class);
	private static final String CARD_NO = "CARD_NO";
	private static final String PARAM_TYPE = "PARAM_TYPE";
	
	String DOWNLOAD_PATH = SystemConfig.getProperty("DOWNLOAD_PATH");
	
	String IMPORT_PTT_BLUE_CARD_TEMPLATE_FILE = SystemConfig.getProperty("IMPORT_PTT_BLUE_CARD_TEMPLATE_FILE");
	String IMPORT_PTT_BLUE_CARD_ERROR_OUTPUT_NAME = SystemConfig.getProperty("IMPORT_PTT_BLUE_CARD_ERROR_OUTPUT_NAME");
	String PREFIX_CARD_NO = SystemConstant.getConstant("PREFIX_CARD_NO");
	String PARAM_TYPE_PTT = SystemConstant.getConstant("PARAM_TYPE_PTT");
	String CARD_TYPE_PTT = SystemConstant.getConstant("CARD_TYPE_PTT");
	int CARD_NO_LENGTH = Integer.parseInt(SystemConstant.getConstant("CARD_NO_LENGTH"));

	static String PTT_CARD_TYPE = SystemConstant.getConstant("PTT_CARD_TYPE");

	private static String CACHE_NAME_CARDTYPE = SystemConstant.getConstant("CACHE_NAME_CARDTYPE");
	private static String PARAM_DESC = CacheControl.getName(CACHE_NAME_CARDTYPE, "CODE", PTT_CARD_TYPE, "VALUE");
	private static String RUNNING_PARAM_STACK_STATUS_DEACTIVATE = SystemConstant.getConstant("RUNNING_PARAM_STACK_STATUS_DEACTIVATE");

	@Override
	public String getFileLocation(HttpServletRequest request, String IMPORT_ID, String FILE_NAME) throws Exception {
		return null;
	}

	@Override
	public ProcessResponse processImport(HttpServletRequest request, String IMPORT_ID, String FILE_NAME, String LOCATION) throws Exception {
		logger.debug("FILE_NAME : " + FILE_NAME);
		logger.debug("LOCATION : " + LOCATION);
		logger.debug("IMPORT_ID : " + IMPORT_ID);
		ProcessResponse responseData = new ProcessResponse();
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		String userName = userM.getUserName();
		String response = "";
		JSONUtil json = new JSONUtil();
		ArrayList<Map<String, Object[]>> errorImports = new ArrayList<Map<String, Object[]>>();
		OrigImportDAO dao = ImportManualFactory.getOrigImportDAO(userName);
		Timestamp startTime = new Timestamp(new Date().getTime());
		logger.debug("startTime >> " + startTime);
		try {
			ExcelUtil excelUtil = new ExcelUtil();
			HashMap<String, ArrayList<HashMap<String, Object>>> excelUserImportData = excelUtil.read(LOCATION, MConstant.IMPORT.UPLOAD_PTT_BLUE_CARD,
					FILE_NAME);
			ArrayList<RunningParamStackDataM> listRunningParamStackDataM = new ArrayList<RunningParamStackDataM>();
			ArrayList<HashMap<String, Object>> excelUserImports = excelUserImportData.get("0");
			HashMap<String, Object> userImport = excelUserImports.get(0);
			if ("ERROR".equals(userImport.get("ERROR"))) {
				json.put("ERROR", MessageErrorUtil.getText("ERROR_EXCEL_SHEET"));
			}
		
			HashMap<String, String> runningParamHm = dao.selectRunningParamStackList(PARAM_TYPE_PTT);
			
			int totalRecord = 0;
			int totalError = 0;
			if (!Util.empty(excelUserImports)) {
				for (int i = 1; i < excelUserImports.size(); i++) {
					totalRecord++;
					HashMap<String, Object> excelRow = excelUserImports.get(i);
					Map<String, Object[]> errorImport = new TreeMap<String, Object[]>();
					ArrayList<String> errors = new ArrayList<String>();

					String cardNo = (String) excelRow.get(CARD_NO);
					String paramType = (String) excelRow.get(PARAM_TYPE);

					if (Util.empty(cardNo)) {
						validateNull(errors, cardNo, CARD_NO);
					} else {
						cardNo = cardNo.trim();
						if (validateCardNoLength(errors, cardNo, CARD_NO_LENGTH, CARD_NO)) {
							if (validateIsNumber(errors, cardNo, CARD_NO)) {
								if (validateExistingCardNo(errors, excelUserImports,runningParamHm, i, CARD_NO, cardNo, PARAM_TYPE_PTT)) {
									validateCardType(errors, CARD_NO, cardNo, CARD_TYPE_PTT);
									validatePrefixCardNos(errors, CARD_NO, cardNo, PREFIX_CARD_NO);
								}

							}
						}

					}

					if (Util.empty(paramType)) {
						validateNull(errors, paramType, PARAM_TYPE);
					} else {
						paramType = paramType.trim();
						validateParamType(errors, PARAM_TYPE, paramType, PARAM_TYPE_PTT);
					}

					if (!Util.empty(errors)) {
						totalError++;
						errorImport.put(OrigDownloadFileUtil.EXCEL_DATA_KEY, new Object[] { replaceNullWithSpace(cardNo),
								replaceNullWithSpace(paramType), OrigDownloadFileUtil.coString(errors) });
						errorImports.add(errorImport);
					}

					if (Util.empty(errors)) {
						mapRunningParamStackData(listRunningParamStackDataM, cardNo);
					}
				}
			}
			if (!Util.empty(errorImports)) {
				String fileName = OrigDownloadFileUtil.getGenerateFileName(IMPORT_PTT_BLUE_CARD_ERROR_OUTPUT_NAME);
				OrigDownloadFileUtil.createExceldRecordData(errorImports, fileName, IMPORT_PTT_BLUE_CARD_TEMPLATE_FILE, DOWNLOAD_PATH);
			}

			if (Util.empty(errorImports)) {
				HashMap<String, Object> objectModel = new HashMap<String, Object>();
				objectModel.put("PARAM_TYPE",PARAM_TYPE_PTT);
				objectModel.put("USER_ID",userName);
				objectModel.put("PARAM_LIST",listRunningParamStackDataM);
				DatabaseController databaseCtrl = new DatabaseController();
				databaseCtrl.processControl(objectModel, new CreateRunningParamStackManager());
			}
			logger.debug("totalTime >> " + ((new Date().getTime()) - startTime.getTime()));
			json.put(OrigDownloadFileUtil.DISPLAY_UPLOAD_KEY.MF_TOTOL, FormatUtil.toString(totalRecord));
			json.put(OrigDownloadFileUtil.DISPLAY_UPLOAD_KEY.MF_INVALID_RECORD, FormatUtil.toString(totalError));
			json.put(OrigDownloadFileUtil.DISPLAY_UPLOAD_KEY.MF_VALID_RECORD, FormatUtil.toString(totalRecord - totalError));
			String downloadLink = "";
			if (totalError > 0) {
				downloadLink = OrigDownloadFileUtil.getLinkForDownload(request, MConstant.DOWNLOAD_INVALID_FILE_MANUAL.DOWNLOAD_INVALID_LINK,
						MConstant.DOWNLOAD_INVALID_FILE_MANUAL.DOWNLOAD_INVALID_PTT_BLUE_CARD,
						OrigDownloadFileUtil.DOWNLOAD_PROCESS_NAME.DOWNLOAD_INVALID);
			}

			json.put(OrigDownloadFileUtil.DISPLAY_UPLOAD_KEY.MF_DOWNLOAD_INVALID, downloadLink);
			response = json.getJSON();
			responseData.setStatusCode(ServiceResponse.Status.SUCCESS);
			responseData.setData(response);

		} catch (Exception e) {
			logger.fatal("ERROR", e);
			responseData.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			responseData.setErrorData(ErrorController.error(e));
		}
		return responseData;
	}

	private void mapRunningParamStackData(ArrayList<RunningParamStackDataM> listRunningParamStackDataM, String cardNo) {
		RunningParamStackDataM runningParamStackDataM = new RunningParamStackDataM();
		runningParamStackDataM.setParamId(PTT_CARD_TYPE);
		runningParamStackDataM.setParamType(PARAM_TYPE_PTT);
		runningParamStackDataM.setParamDesc(PARAM_DESC);
		runningParamStackDataM.setStatus(RUNNING_PARAM_STACK_STATUS_DEACTIVATE);
		runningParamStackDataM.setParamValue(cardNo);
		listRunningParamStackDataM.add(runningParamStackDataM);
	}

	@Override
	public boolean requiredEvent() {
		return false;
	}

	@Override
	public ProcessResponse processEvent(Part part, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return null;
	}

	private ArrayList<String> validateNull(ArrayList<String> obj, String data, String field_name) {
		if (Util.empty(data)) {
			obj.add(MessageErrorUtil.getText("ERROR_NULL_FORMAT").replaceAll("\\{FIELD_NAME\\}", field_name));
		}
		return obj;
	}

	private boolean validateIsNumber(ArrayList<String> obj, String data, String field_name) {
		boolean valid = true;
		if (!Util.empty(data)) {
			if (!isNumeric(data)) {
				obj.add(MessageErrorUtil.getText("ERROR_NUMBER_ONLY").replaceAll("\\{FIELD_NAME\\}", field_name));
				valid = false;
			}
		}
		return valid;
	}

	private void validateParamType(ArrayList<String> obj, String field_name, String value, String paramType) {
		if (!paramType.equals(value)) {
			obj.add(MessageErrorUtil.getText("ERROR_PARAM_TYPE").replaceAll("\\{FIELD_NAME\\}", field_name)
					.replaceAll("\\{PARAM_TYPE_PTT\\}", paramType));
		}
	}

	private boolean validateCardNoLength(ArrayList<String> obj, String data, int length, String field_name) {
		boolean valid = true;
		if (data.length() != length) {
			obj.add(MessageErrorUtil.getText("ERROR_LENGTH_OF_CARD_NO").replaceAll("\\{FIELD_NAME\\}", field_name)
					.replaceAll("\\{PARAM_NUMBER\\}", "" + length));
			valid = false;
		}
		return valid;
	}

	private boolean isNumeric(String str) {
		if (!Util.empty(str)) {
			for (char c : str.toCharArray()) {
				if (c < '0' || c > '9') {
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}

	private void validatePrefixCardNos(ArrayList<String> obj, String field_name, String value, String prefixCardNo) {
		if (!Util.empty(value)) {
			String firstFourDigit = value.substring(0, 4);
			if (!prefixCardNo.equals(firstFourDigit)) {
				obj.add(MessageErrorUtil.getText("ERROR_PREFIX_CARD_NO").replaceAll("\\{FIELD_NAME\\}", field_name)
						.replaceAll("\\{PREFIX_CARD_NO\\}", prefixCardNo));
			}
		}
	}

	private void validateCardType(ArrayList<String> obj, String field_name, String value, String cardType) {
		if (!Util.empty(value)) {
			String cardTypeDigit = value.substring(4, 6);
			if (!cardType.equals(cardTypeDigit)) {
				obj.add(MessageErrorUtil.getText("ERROR_CARD_TYPE").replaceAll("\\{FIELD_NAME\\}", field_name)
						.replaceAll("\\{CARD_TYPE\\}", cardType));
			}
		}
	}

	private boolean validateExistingCardNo(ArrayList<String> obj, ArrayList<HashMap<String, Object>> excelUserImports,HashMap<String, String> runningParamHm, int execlIndex,
			String field_name, String value, String paramType) {
		if (!Util.empty(value)) {

			if (!checkDuplicateCardNo(excelUserImports, value, execlIndex)) {
				obj.add(MessageErrorUtil.getText("ERROR_DUPLICATE_CARD_NO_IN_EXCEL_FILE").replaceAll("\\{FIELD_NAME\\}", field_name));
				return false;
			} else if (!checkExistingCardNo(runningParamHm, paramType, value)) {
				obj.add(MessageErrorUtil.getText("ERROR_DUPLICATE_CARD_NO_IN_RUNNING_PARAM_STACK").replaceAll("\\{FIELD_NAME\\}", field_name));
				return false;
			}

		}

		return true;
	}

	private Object replaceNullWithSpace(Object o) {
		if (Util.empty(o)) {
			String str = "";
			o = str;
		}
		return o;
	}

	private boolean checkExistingCardNo(HashMap<String, String>  runningParamHm, String paramType, String cardNo) {
		boolean valid = true;
		if (!Util.empty(runningParamHm.get(cardNo))) {
			valid = false;
		}
		return valid;
	}

	private boolean checkDuplicateCardNo(ArrayList<HashMap<String, Object>> excelUserImports, String value, int valueIndex) {
		boolean valid = true;
		for (int i = 1; i < excelUserImports.size(); i++) {
			HashMap<String, Object> excelRow = excelUserImports.get(i);
			String cardNo = (String) excelRow.get(CARD_NO);
			if (i != valueIndex) {
				if (value.equals(cardNo)) {
					valid = false;
				}
			}
		}
		return valid;
	}
}
