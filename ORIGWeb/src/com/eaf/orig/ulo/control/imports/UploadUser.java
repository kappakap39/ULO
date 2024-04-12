package com.eaf.orig.ulo.control.imports;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.file.ExcelUtil;
import com.eaf.core.ulo.common.file.ImportControl;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.ias.shared.model.RoleM;
import com.eaf.ias.shared.model.UserM;
import com.eaf.ias.shared.model.service.IASServiceRequest;
import com.eaf.ias.shared.model.service.IASServiceResponse;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.j2ee.pattern.util.JSONUtil;
import com.eaf.orig.common.excel.ExcelGenerate;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.OrigDownloadFileUtil;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.model.ServiceResponse;
import com.eaf.ulo.cache.controller.RefreshCacheController;


@SuppressWarnings("serial")
public class UploadUser extends ExcelGenerate implements ImportControl{
	private static transient Logger logger = Logger.getLogger(UploadUser.class);
	private static final String USER_NAME = "USER_NAME";
	private static final String EMPOLYEE_ID = "EMPOLYEE_ID";
	private static final String NAME_THAI = "NAME_THAI";
	private static final String SURNAME_THAI = "SURNAME_THAI";
	private static final String NAME_ENG = "NAME_ENG";
	private static final String SURNAME_ENG = "SURNAME_ENG";
	private static final String DEPARTMENT = "DEPARTMENT";
	private static final String ORGANIZE_POSITION = "ORGANIZE_POSITION";
	private static final String WORK_POSITION = "WORK_POSITION";
	private static final String TELEPHONE = "TELEPHONE";
	private static final String TELEPHONE_EXT = "TELEPHONE_EXT";
	private static final String MOBILE = "MOBILE";
	private static final String EMAIL = "EMAIL";
	private static final String DLA = "DLA_(ONLY_CA)";
	private static final String JOB_LEVEL = "JOB_LEVEL";
	private static final String SKILL = "SKILL_(ONLY_DV)";
	private static final String PROFILE = "PROFILE";
	private static final String ROLE = "ROLE";
	private static final String ACTIVE_STATUS = "ACTIVE_STATUS";
	private static final String IAS_SERVICE_CREATE_USER_TEMP_URL = SystemConfig.getProperty("IAS_SERVICE_CREATE_USER_TEMP_URL");
	String IMPORT_USER_TEMPLATE_FILE = SystemConfig.getProperty("IMPORT_USER_TEMPLATE_FILE");
	String DOWNLOAD_PATH = SystemConfig.getProperty("DOWNLOAD_PATH");
	String IMPORT_USER_ERROR_OUTPUT_NAME = SystemConfig.getProperty("IMPORT_USER_ERROR_OUTPUT_NAME");
	
	@Override
	public String getFileLocation(HttpServletRequest request, String IMPORT_ID,String FILE_NAME) throws Exception {
		return null;
	}
	@Override
	public ProcessResponse processImport(HttpServletRequest request,String IMPORT_ID, String FILE_NAME, String LOCATION) throws Exception {
		logger.debug("FILE_NAME : "+FILE_NAME);
		logger.debug("LOCATION : "+LOCATION);
		logger.debug("IMPORT_ID : "+IMPORT_ID);
		ProcessResponse responseData = new ProcessResponse();
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		String response = "";	
		JSONUtil json = new JSONUtil();
		ArrayList<Map<String, Object[]>> errorImports = new ArrayList<Map<String, Object[]>>();
		try{
			ExcelUtil excelUtil = new ExcelUtil();
			HashMap<String,ArrayList<HashMap<String,Object>>> excelUserImportData = excelUtil.read(LOCATION, MConstant.IMPORT.UPLOAD_USER, FILE_NAME);
			ArrayList<HashMap<String, Object>> excelUserImports = excelUserImportData.get("0");
			HashMap<String, Object> userImport = excelUserImports.get(0);
			if("ERROR".equals(userImport.get("ERROR"))){
				json.put("ERROR", MessageErrorUtil.getText("ERROR_EXCEL_SHEET"));
			}
			int totalRecord = 0;
			int totalError = 0;
			if(!Util.empty(excelUserImports)){
				for(int i=1;i<excelUserImports.size();i++){
					totalRecord++;
					HashMap<String,Object> excelRow = excelUserImports.get(i);
					Map<String, Object[]> errorImport = new TreeMap<String, Object[]>();
					ArrayList<String> errors = new ArrayList<String>();
					String userName = ((String) excelRow.get(USER_NAME)).trim().replace(" ", "");
					String empId = (String)excelRow.get(EMPOLYEE_ID);
					String nameTh = (String)excelRow.get(NAME_THAI);
					String surnameTh = (String)excelRow.get(SURNAME_THAI);
					String nameEng = (String)excelRow.get(NAME_ENG);
					String surnameEng = (String)excelRow.get(SURNAME_ENG);
					String department = (String)excelRow.get(DEPARTMENT);
					String organizePosition = (String)excelRow.get(ORGANIZE_POSITION);
					String workposition = (String)excelRow.get(WORK_POSITION);
					String tel = (String)excelRow.get(TELEPHONE);
					String telExt = (String)excelRow.get(TELEPHONE_EXT);
					String mobile = (String)excelRow.get(MOBILE);
					String eMail = (String)excelRow.get(EMAIL);
					String dla = (String)excelRow.get(DLA);
					String jobLevel = (String)excelRow.get(JOB_LEVEL);
					String skill = (String)excelRow.get(SKILL);
					String profile = (String)excelRow.get(PROFILE);
					String role = (String)excelRow.get(ROLE);
					String activeStatus = (String)excelRow.get(ACTIVE_STATUS);
					if (Util.empty(userName)){
						validateNull(errors,userName,USER_NAME);
					}else{
						userName = userName.trim();
						validateMaxLength(errors, userName, 20, USER_NAME);
					}
					if (Util.empty(empId)) {
						validateNull(errors,empId,EMPOLYEE_ID);
					}else{ 
						validateIsNumber(errors, empId, EMPOLYEE_ID);
						validateMaxLength(errors, empId, 8, EMPOLYEE_ID);
					}
					if(Util.empty(nameTh)){
						validateNull(errors,nameTh,NAME_THAI);
					}else{
						validateMaxLength(errors, nameTh, 120, NAME_THAI);
					}
					if(Util.empty(surnameTh)) {
						validateNull(errors,surnameTh,SURNAME_THAI);
					}else{
						validateMaxLength(errors, surnameTh, 50, SURNAME_THAI);
					}
					if(Util.empty(nameEng)){
						validateNull(errors,nameEng,NAME_ENG);
					}else{
						validateMaxLength(errors, nameEng, 120, NAME_ENG);
					}
					if(Util.empty(surnameEng)) {
						validateNull(errors,surnameEng,SURNAME_ENG);
					}else{
						validateMaxLength(errors, surnameEng, 50, SURNAME_ENG);
					}
					if(!Util.empty(tel)){
						//validatePhoneNumber(errors,tel,TELEPHONE);
						validateIsNumber(errors,tel,TELEPHONE);
					}
					if(!Util.empty(telExt)){
						validateIsNumber(errors, telExt, TELEPHONE_EXT);
					}
					if(!Util.empty(mobile)){
						validateIsNumber(errors, mobile, MOBILE);
					}
					if(Util.empty(jobLevel)) 
						validateNull(errors,jobLevel,JOB_LEVEL);
					if(Util.empty(profile)) 
						validateNull(errors,profile,PROFILE);
					if(Util.empty(role)) 
						validateNull(errors,role,ROLE);
					if(!Util.empty(eMail)){
						validateMaxLength(errors, eMail, 100, EMAIL);
					}
					if(!Util.empty(department)){
						validateMaxLength(errors, department, 50, DEPARTMENT);
					}
					if(!Util.empty(workposition)){
						validateMaxLength(errors, workposition, 50, WORK_POSITION);
					}
					if(!Util.empty(skill)){
						validateSkillSet(errors,skill,SKILL);
					}
					if(!Util.empty(dla)){
						validateMasterData(errors,dla,DLA,"DLA_ID");
					}
					if(Util.empty(errors)){
						UserM user = new UserM();
							user.setUserName(userName);
							user.setDepartment(department);
							user.setEngFirstName(nameEng);
							user.setEngLastName(surnameEng);
							user.setTelephone(tel);
							user.setTelephoneExt(telExt);
							user.setMobilePhone(mobile);
							user.setEmailAddress(eMail);
							user.setJobDescription(jobLevel);
							user.setDlaId(dla);
							user.setProfileId(profile);
							user.setSkillSetId(skill);
							user.setPosition(workposition);
							user.setDefaultOfficeCode(organizePosition);
							user.setStatus(activeStatus);
							user.setThaiFirstName(nameTh);
							user.setUserNo(empId);
							user.setThaiLastName(surnameTh);
							user.setPosition(workposition);
							Vector<RoleM> roles = new Vector<RoleM>();
							RoleM roleObj = new RoleM();
							roleObj.setRoleID(role);
							roles.add(roleObj);
							user.setRoles(roles);
							user.setCreatedBy(userM.getUserName());
							user.setUpdateBy(userM.getUserName());
						IASServiceRequest serviceRequest = new IASServiceRequest();
							serviceRequest.setUser(user);
							serviceRequest.setSystemName(OrigConstant.SYSTEM_NAME);		
							serviceRequest.setUserName(userM.getUserName());
							RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory(){
								@Override
								protected void prepareConnection(HttpURLConnection connection, String httpMethod)throws IOException {
							        if(connection instanceof HttpsURLConnection ){
							            ((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier(){									
											@Override
											public boolean verify(String arg0, SSLSession arg1) {
												return true;
											}
										});
							        }
									super.prepareConnection(connection, httpMethod);
								}
							});
						ResponseEntity<IASServiceResponse> responseEntity = restTemplate.postForEntity(IAS_SERVICE_CREATE_USER_TEMP_URL
								,serviceRequest,IASServiceResponse.class);
						IASServiceResponse serviceResponse = responseEntity.getBody();
						logger.debug("serviceResponse.getResponseCode() : "+serviceResponse.getResponseCode());
						logger.debug("serviceResponse.getResponseDesc() : "+serviceResponse.getResponseDesc());
						if(!ServiceResponse.Status.SUCCESS.equals(serviceResponse.getResponseCode())){
							errors.add(serviceResponse.getResponseDesc());
						}
					}
					logger.debug("errors : "+errors);
					if(!Util.empty(errors)){
						totalError++;
						errorImport.put(
								OrigDownloadFileUtil.EXCEL_DATA_KEY,
								new Object[] { 
										replaceNullWithSpace(userName),
										replaceNullWithSpace(empId),
										replaceNullWithSpace(nameTh),
										replaceNullWithSpace(surnameTh),
										replaceNullWithSpace(nameEng),
										replaceNullWithSpace(surnameEng),
										replaceNullWithSpace(department),
										replaceNullWithSpace(organizePosition),
										replaceNullWithSpace(workposition),
										replaceNullWithSpace(tel),
										replaceNullWithSpace(telExt),
										replaceNullWithSpace(mobile),
										replaceNullWithSpace(eMail),
										replaceNullWithSpace(dla),
										replaceNullWithSpace(jobLevel),
										replaceNullWithSpace(skill),
										replaceNullWithSpace(profile),
										replaceNullWithSpace(role),
										replaceNullWithSpace(activeStatus),
										OrigDownloadFileUtil.coString(errors)});
						errorImports.add(errorImport);
					}
				}
			}
			if(!Util.empty(errorImports)){
				String fileName = OrigDownloadFileUtil.getGenerateFileName(IMPORT_USER_ERROR_OUTPUT_NAME);
				OrigDownloadFileUtil.createExceldRecordData(errorImports,fileName,IMPORT_USER_TEMPLATE_FILE,DOWNLOAD_PATH);
			}

			json.put(OrigDownloadFileUtil.DISPLAY_UPLOAD_KEY.MF_TOTOL,FormatUtil.toString(totalRecord));
			json.put(OrigDownloadFileUtil.DISPLAY_UPLOAD_KEY.MF_INVALID_RECORD,FormatUtil.toString(totalError));
			json.put(OrigDownloadFileUtil.DISPLAY_UPLOAD_KEY.MF_VALID_RECORD,FormatUtil.toString(totalRecord-totalError));
			String downloadLink = "";
			if(totalError>0){
				downloadLink = OrigDownloadFileUtil.getLinkForDownload(request,
								MConstant.DOWNLOAD_INVALID_FILE_MANUAL.DOWNLOAD_INVALID_LINK,
								MConstant.DOWNLOAD_INVALID_FILE_MANUAL.DOWNLOAD_INVALID_USER,
								OrigDownloadFileUtil.DOWNLOAD_PROCESS_NAME.DOWNLOAD_INVALID);
			}
			json.put(OrigDownloadFileUtil.DISPLAY_UPLOAD_KEY.MF_DOWNLOAD_INVALID,downloadLink);
			RefreshCacheController.execute(SystemConstant.getConstant("CACHE_NAME_USER"));
			response = json.getJSON();
			responseData.setStatusCode(ServiceResponse.Status.SUCCESS);
			responseData.setData(response);
		}catch(Exception e){
			logger.fatal("ERROR", e);
			responseData.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			responseData.setErrorData(ErrorController.error(e));
		}
		return responseData;
	}

	@Override
	public boolean requiredEvent(){
		return false;
	}
	@Override
	public ProcessResponse processEvent(Part part, HttpServletRequest request,HttpServletResponse response) throws Exception {
		return null;
	}
	private ArrayList<String> validateNull(ArrayList<String> obj,String data, String field_name){
		if(Util.empty(data)){
			obj.add(MessageErrorUtil.getText("ERROR_NULL_FORMAT").replaceAll("\\{FIELD_NAME\\}", field_name));
		}
		return obj;
	}
	private ArrayList<String> validatePhoneNumber(ArrayList<String> obj,String data, String field_name){
		if(!Util.empty(data)){
			if(isNumeric(data)){
				if(!isTelephoneFormat(data)){
					obj.add(MessageErrorUtil.getText("ERROR_DATE_LIMIT").replaceAll("\\{FIELD_NAME\\}", field_name));
				}
			}else{
				obj.add(MessageErrorUtil.getText("ERROR_NUMBER_ONLY").replaceAll("\\{FIELD_NAME\\}", field_name));
			}
		}
		return obj;
	}
	private ArrayList<String> validateIsNumber(ArrayList<String> obj,String data, String field_name){
		if(!Util.empty(data)){
			if(!isNumeric(data)){
				obj.add(MessageErrorUtil.getText("ERROR_NUMBER_ONLY").replaceAll("\\{FIELD_NAME\\}", field_name));
			}
		}
		return obj;
	}
	public static ArrayList<String> validateMaxLength(ArrayList<String> obj,String data, int length, String field_name) {
		if (data.length() > length) {
			obj.add(MessageErrorUtil.getText("ERROR_MAX_LENGTH").replaceAll("\\{FIELD_NAME\\}", field_name).replaceAll("\\{PARAM_NUMBER\\}", ""+length));
		}
		return obj;
	}
	public static ArrayList<String> validateLdap(ArrayList<String> obj,String data, String field_name) {
			obj.add(MessageErrorUtil.getText("ERROR_LDAP").replaceAll("\\{USER_NAME\\}", data));
		return obj;
	}
	private ArrayList<String> validateSkillSet(ArrayList<String> obj,String data,String field_name){
		if(!SystemConstant.lookup("SKILL_SET_ID",data)){
			obj.add(MessageErrorUtil.getText("ERROR_SKILL_SET").replaceAll("\\{FIELD_NAME\\}", field_name).replaceAll("\\{PARAM_CODE\\}", ""+SystemConstant.getConstant("SKILL_SET_ID")));
		}
		return obj;
	}
	private ArrayList<String> validateMasterData(ArrayList<String> obj,String data,String field_name,String masterId){
		if(!SystemConstant.lookup(masterId,data)){
			obj.add(MessageErrorUtil.getText("ERROR_NOT_MATCH_MASTER").replaceAll("\\{FIELD_NAME\\}", field_name).replaceAll("\\{PARAM_CODE\\}", ""+SystemConstant.getConstant(masterId)));
		}
		return obj;
	}
	private boolean isNumeric(String str){
		if(!Util.empty(str)){
			for(char c : str.toCharArray()){
				if(c<'0' || c>'9'){return false;}
			}
		}else{
			return false;
		}
		return true;
	}
	private boolean isTelephoneFormat(String str){
		if(!Util.empty(str)){
			if(str.length()>9){
				String firstTwoDigit = str.substring(0, 2);
				String forthToNineDigit = str.substring(3,9);
				if("00".equals(firstTwoDigit) || "08".equals(firstTwoDigit) || "09".equals(firstTwoDigit)){
					return true;
				}else if("000000".equals(forthToNineDigit)){
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}
	private Object replaceNullWithSpace (Object o){
		if(Util.empty(o)){
			String str = "";
			o=str;
		}
		return o;
	}
}
