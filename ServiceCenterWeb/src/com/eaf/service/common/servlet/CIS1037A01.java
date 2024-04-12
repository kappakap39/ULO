package com.eaf.service.common.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.common.utils.ResponseUtils;
import com.eaf.service.module.manual.CIS1037A01ServiceProxy;
import com.eaf.service.module.model.CIS1037A01RequestDataM;
import com.eaf.service.module.model.CIS1037A01ResponseDataM;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;

@WebServlet("/CIS1037A01")
public class CIS1037A01 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(CIS1037A01.class);
	
    public CIS1037A01() {
        super();
    }

    public static class filePath{
    	public static final String file = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")+"properties"+File.separator+"CIS1037A01WS.properties";
    	public static final String text = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")+"properties"+File.separator+"regulationType.txt";
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String process = request.getParameter("process");
		logger.debug("process : "+process);
		if(process.equals("defualt")){
			File file = new File((filePath.file));
			InputStream inStream = new FileInputStream(file);
			Properties prop = new Properties();
			prop.load(inStream);
			inStream.close();
			
			file = new File((filePath.text));
			FileReader reader = new FileReader(file);
			LineNumberReader lineNumberReader = new LineNumberReader(reader);
			lineNumberReader.skip(Long.MAX_VALUE);
			
			int data = lineNumberReader.getLineNumber();
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String line = null;
			String[][] value = new String[data][2];
			int i=0;
			logger.debug("data : "+data);
			while((line=bufferedReader.readLine())!=null){
				logger.debug("i "+i);
				logger.debug("line : "+line);
				value[i] = line.split("\\|");
				i++;
			}
			bufferedReader.close();
			
			String[] regSubType = new String[data];
			String[] regType = new String[data];
			for(int j=0;j<data;j++){
				logger.debug("value : "+value[j][0]);
				regSubType[j] = value[j][0];
				regType[j] = value[j][1];
			}
			
			HashMap<String, Object> object = new HashMap<>();
			object.put(CIS1037A01ServiceProxy.url, prop.getProperty(CIS1037A01ServiceProxy.url));
			object.put(CIS1037A01ServiceProxy.responseConstants.addrId, prop.getProperty(CIS1037A01ServiceProxy.responseConstants.addrId));
			object.put(CIS1037A01ServiceProxy.responseConstants.CISContact.phoneId, prop.getProperty(CIS1037A01ServiceProxy.responseConstants.CISContact.phoneId));
			object.put(CIS1037A01ServiceProxy.responseConstants.CISContact.eAddressId, prop.getProperty(CIS1037A01ServiceProxy.responseConstants.CISContact.eAddressId));
			object.put(CIS1037A01ServiceProxy.responseConstants.regulationAmt, regSubType.length);
			object.put(CIS1037A01ServiceProxy.responseConstants.riskCode, prop.getProperty(CIS1037A01ServiceProxy.responseConstants.riskCode));
			object.put(CIS1037A01ServiceProxy.responseConstants.cusId, prop.getProperty(CIS1037A01ServiceProxy.responseConstants.cusId));
			object.put(CIS1037A01ServiceProxy.responseConstants.offcAddrId, prop.getProperty(CIS1037A01ServiceProxy.responseConstants.offcAddrId));
			
			object.put(CIS1037A01ServiceProxy.responseConstants.customerRegulationInformation.regSubType, regSubType);
			object.put(CIS1037A01ServiceProxy.responseConstants.customerRegulationInformation.regType, regType);
			
			ResponseUtils.sendJsonResponse(response, object);
		}else if(process.equals("request")){
			String url = request.getParameter("url_input");

			CIS1037A01RequestDataM CIS1037A01Request = new CIS1037A01RequestDataM();
			mapCIS1037A01Request(request,CIS1037A01Request);
			
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setEndpointUrl(url);
				serviceRequest.setServiceId(CIS1037A01ServiceProxy.serviceId);
				serviceRequest.setIgnoreServiceLog(true);
				serviceRequest.setUserId(SystemConstant.getConstant("SYSTEM_USER"));
				serviceRequest.setObjectData(CIS1037A01Request);
			ServiceResponseDataM resp = null;	
			try{
				ServiceCenterProxy proxy = new ServiceCenterProxy();
					resp = proxy.requestService(serviceRequest);
			}catch(Exception e){
				e.printStackTrace();
			}
			HashMap<String,Object> object = new HashMap<String,Object>();
			Gson gson = new Gson();
			object.put("jsonRq", gson.toJson(resp.getRequestObjectData()));
			object.put("jsonRs", gson.toJson(resp.getResponseObjectData()));
			
			CIS1037A01ResponseDataM CIS1037A01Response = (CIS1037A01ResponseDataM)resp.getObjectData();
			if(null == CIS1037A01Response){
				CIS1037A01Response = new CIS1037A01ResponseDataM();
			}
			object.put(CIS1037A01ServiceProxy.responseConstants.addrId, CIS1037A01Response.getAddressId());
			object.put(CIS1037A01ServiceProxy.responseConstants.CISContact.phoneId, CIS1037A01Response.getPhoneId());
			object.put(CIS1037A01ServiceProxy.responseConstants.CISContact.eAddressId, CIS1037A01Response.getMailId());
			object.put(CIS1037A01ServiceProxy.responseConstants.regulationAmt, CIS1037A01Response.getTotalRegulation());
			object.put(CIS1037A01ServiceProxy.responseConstants.riskCode, CIS1037A01Response.getRiskCode());
			object.put(CIS1037A01ServiceProxy.responseConstants.cusId, CIS1037A01Response.getCustomerId());
			object.put(CIS1037A01ServiceProxy.responseConstants.offcAddrId, CIS1037A01Response.getOfficialAddressId());
			object.put(CIS1037A01ServiceProxy.responseConstants.customerRegulationInformation.class.getSimpleName(), CIS1037A01Response.getRegulations());
			
			logger.debug("resp : "+gson.toJson(resp));
			ResponseUtils.sendJsonResponse(response, object);
			
		}else if(process.equals("save")){
			String url = request.getParameter("url_setting");
			String contactAddrId = request.getParameter("contactAddrId_setting");
			String phoneId = request.getParameter("phoneId_setting");
			String eAddrId = request.getParameter("eAddrId_setting");
			String totalNumReg = request.getParameter("totalNumReg_setting");
			String riskCode = request.getParameter("riskCode_setting");
			String cusId = request.getParameter("cusId_setting");
			String offiAddrId = request.getParameter("offiAddrId_setting");
			
			File file = new File((filePath.file));
			InputStream inStream = new FileInputStream(file);
			PrintWriter writer = new PrintWriter(file);
			Properties prop = new Properties();
			prop.load(inStream);
			inStream.close();
			
			prop.setProperty(CIS1037A01ServiceProxy.url, url);
			prop.setProperty(CIS1037A01ServiceProxy.responseConstants.addrId, contactAddrId);
			prop.setProperty(CIS1037A01ServiceProxy.responseConstants.CISContact.phoneId, phoneId);
			prop.setProperty(CIS1037A01ServiceProxy.responseConstants.CISContact.eAddressId, eAddrId);
			prop.setProperty(CIS1037A01ServiceProxy.responseConstants.regulationAmt, totalNumReg);
			prop.setProperty(CIS1037A01ServiceProxy.responseConstants.riskCode, riskCode);
			prop.setProperty(CIS1037A01ServiceProxy.responseConstants.cusId, cusId);
			prop.setProperty(CIS1037A01ServiceProxy.responseConstants.offcAddrId, offiAddrId);
			prop.store(writer, "data record in regulationType.txt");
			writer.close();
		}else if(process.equals("add")){
			String regSubType = request.getParameter("regSubType_add");
			String regType = request.getParameter("regType_add");
			
			String addLine = regSubType+"|"+regType+"\n";
			File file = new File((filePath.text));
			FileWriter writer = new FileWriter(file, true);
			writer.write(addLine);
			writer.close();
			
		}else if(process.equals("edit")){
			String regSubType = request.getParameter("regSubType_add");
			String regType = request.getParameter("regType_add");
			String row = request.getParameter("row_hidden");
			
			String editLine = regSubType+"|"+regType;
			logger.debug("edit line : "+editLine);
			File file = new File((filePath.text));
			File temp = new File(file.getAbsoluteFile()+".tmp");
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			PrintWriter writer = new PrintWriter(temp);
			
			String line = null;
			int rowMatching = 1;
			while((line = reader.readLine())!=null){
				if(rowMatching == Integer.parseInt(row)){
					logger.debug("row edit : "+editLine);
					writer.println(editLine);
				}else{
					logger.debug("row : "+line);
					writer.println(line);
				}
				rowMatching++;
			}
			writer.close();
			reader.close();
			System.gc();
			if(!file.delete()){logger.debug("cannot delete this file."); return;}
			if(!temp.renameTo(file)){logger.debug("cannot rename to file."); return;}
		}else if(process.equals("delete")){
			String row = request.getParameter("row");

			File file = new File((filePath.text));
			File temp = new File(file.getAbsoluteFile()+".tmp");
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			PrintWriter writer = new PrintWriter(temp);
			
			String line = null;
			int rowMatching = 1;
			while((line = reader.readLine())!=null){
				if(rowMatching == Integer.parseInt(row)){

				}else{
					logger.debug("row : "+line);
					writer.println(line);
				}
				rowMatching++;
			}
			writer.close();
			reader.close();
			System.gc();
			if(!file.delete()){logger.debug("cannot delete this file."); return;}
			if(!temp.renameTo(file)){logger.debug("cannot rename to file."); return;}
		}
	}
	
	public void mapCIS1037A01Request(HttpServletRequest request,CIS1037A01RequestDataM CIS1037A01Request){
		//head
		String branchNo = request.getParameter("branchNo_input");
		String confirmFlag = request.getParameter("confirmFlag_input");
		String hubNo = request.getParameter("hubNo_input");
		String userId = request.getParameter("userId_input");
		
		CIS1037A01Request.setBranchNo(branchNo);
		CIS1037A01Request.setConfirmFlag(confirmFlag);
		CIS1037A01Request.setHubNo(hubNo);
		CIS1037A01Request.setUserId(userId);
		
		//CISIndivCust
		String addrFlag = request.getParameter("addrFlag_input");
		String assetExcludeLand = request.getParameter("assetExcludeLand_input");
		String birthDate = request.getParameter("birthDate_input");
		String childrenAmount = request.getParameter("childrenAmount_input");
		String consendFlag = request.getParameter("consendFlag_input");
		String consentSource = request.getParameter("consentSource_input");
		String multipleContactChannel = request.getParameter("multipleContactChannel_input");
		String customerSegment = request.getParameter("customerSegment_input");
		String customerType = request.getParameter("customerType_input");
		String depositCode = request.getParameter("depositCode_input");
		String documentId = request.getParameter("documentId_input");
		String documentType = request.getParameter("documentType_input");
		String degree = request.getParameter("degree_input");
		String employeeAmount = request.getParameter("employeeAmount_input");
		String englishName = request.getParameter("englishName_input");
		String englishLastName = request.getParameter("englishLastName_input");
		String englishMiddleName = request.getParameter("englishMiddleName_input");
		String englishTitleName = request.getParameter("englishTitleName_input");
		String familyIncome = request.getParameter("familyIncome_input");
		String reviewFlag = request.getParameter("reviewFlag_input");
		String cardDescription = request.getParameter("cardDescription_input");
		String cardExpiryDate = request.getParameter("cardExpiryDate_input");
		String issueDate = request.getParameter("issueDate_input");
		String issueBy = request.getParameter("issueBy_input");
		String salary = request.getParameter("salary_input");
		String maritalStatus = request.getParameter("maritalStatus_input");
		String menuAddFlag = request.getParameter("menuAddFlag_input");
		String nationalityCode = request.getParameter("nationalityCode_input");
		String changeBookNumber = request.getParameter("changeBookNumber_input");
		String changeDate = request.getParameter("changeDate_input");
		String customerId = request.getParameter("customerId_input");
		String contactAddressLine1 = request.getParameter("contactAddressLine1_input");
		String contactAddressLine2 = request.getParameter("contactAddressLine2_input");
		String officialAddressLine1 = request.getParameter("officialAddressLine1_input");
		String officialAddressLine2 = request.getParameter("officialAddressLine2_input");
		String occupation = request.getParameter("occupation_input");
		String oldestChildAge = request.getParameter("oldestChildAge_input");
		String positionCode = request.getParameter("positionCode_input");
		String professionCode = request.getParameter("professionCode_input");
		String prospectFlag = request.getParameter("prospectFlag_input");
		String raceCode = request.getParameter("raceCode_input");
		String religionCode = request.getParameter("religionCode_input");
		String reverseFlag = request.getParameter("reverseFlag_input");
		String gender = request.getParameter("gender_input");
		String ownerBrNo = request.getParameter("ownerBrNo_input");
		String taxId = request.getParameter("taxId_input");
		String businessType = request.getParameter("businessType_input");
		String thaiName = request.getParameter("thaiName_input");
		String thaiLastName = request.getParameter("thaiLastName_input");
		String thaiMiddleName = request.getParameter("thaiMiddleName_input");
		String thaiTitleName = request.getParameter("thaiTitleName_input");
		String titleNameCode = request.getParameter("titleNameCode_input");
		String customerTypeCode = request.getParameter("customerTypeCode_input");
		String validateFlag = request.getParameter("validateFlag_input");
		String vipFlag = request.getParameter("vipFlag_input");
		String workStartDate = request.getParameter("workStartDate_input");
		String youngestChildAge = request.getParameter("youngestChildAge_input");
		
		CIS1037A01Request.setAddrFlag(addrFlag);
		CIS1037A01Request.setAssetExcludeLand(new BigDecimal(assetExcludeLand.equals("") ? "0.0" : assetExcludeLand));
		CIS1037A01Request.setBirthDate(birthDate.equals("") ? null : Date.valueOf(birthDate));
		CIS1037A01Request.setChildrenAmount(childrenAmount.equals("") ? 0 : Integer.valueOf(childrenAmount));
		CIS1037A01Request.setConsendFlag(consendFlag);
		CIS1037A01Request.setConsentSource(consentSource);
		CIS1037A01Request.setMultipleContactChannel(multipleContactChannel);
		CIS1037A01Request.setCustomerSegment(customerSegment);
		CIS1037A01Request.setCustomerType(customerType);
		CIS1037A01Request.setDepositCode(depositCode);
		CIS1037A01Request.setDocumentId(documentId);
		CIS1037A01Request.setDocumentType(documentType);
		CIS1037A01Request.setDegree(degree);
		CIS1037A01Request.setEmployeeAmount(employeeAmount.equals("") ? 0 : Integer.valueOf(employeeAmount));
		CIS1037A01Request.setEnglishName(englishName);
		CIS1037A01Request.setEnglishLastName(englishLastName);
		CIS1037A01Request.setEnglishMiddleName(englishMiddleName);
		CIS1037A01Request.setEnglishTitleName(englishTitleName);
		CIS1037A01Request.setFamilyIncome(familyIncome);
		CIS1037A01Request.setReviewFlag(reviewFlag);
		CIS1037A01Request.setCardDescription(cardDescription);
		CIS1037A01Request.setCardExpiryDate(cardExpiryDate.equals("") ? null : Date.valueOf(cardExpiryDate));
		CIS1037A01Request.setIssueDate(issueDate.equals("") ? null : Date.valueOf(issueDate));
		CIS1037A01Request.setIssueBy(issueBy);
		CIS1037A01Request.setSalary(salary);
		CIS1037A01Request.setMaritalStatus(maritalStatus);
		CIS1037A01Request.setMenuAddFlag(menuAddFlag);
		CIS1037A01Request.setNationalityCode(nationalityCode);
		CIS1037A01Request.setChangeBookNumber(changeBookNumber);
		CIS1037A01Request.setChangeDate(changeDate.equals("") ? null : Date.valueOf(changeDate));
		CIS1037A01Request.setCustomerId(customerId);
		CIS1037A01Request.setContactAddressLine1(contactAddressLine1);
		CIS1037A01Request.setContactAddressLine2(contactAddressLine2);
		CIS1037A01Request.setOfficialAddressLine1(officialAddressLine1);
		CIS1037A01Request.setOfficialAddressLine2(officialAddressLine2);
		CIS1037A01Request.setOccupation(occupation);
		CIS1037A01Request.setOldestChildAge(oldestChildAge.equals("") ? 0 : Integer.parseInt(oldestChildAge));
		CIS1037A01Request.setPositionCode(positionCode);
		CIS1037A01Request.setProfessionCode(professionCode);
		CIS1037A01Request.setProspectFlag(prospectFlag);
		CIS1037A01Request.setRaceCode(raceCode);
		CIS1037A01Request.setReligionCode(religionCode);
		CIS1037A01Request.setReverseFlag(reverseFlag);
		CIS1037A01Request.setOwnerBrNo(ownerBrNo);
		CIS1037A01Request.setGender(gender);
		CIS1037A01Request.setTaxId(taxId);
		CIS1037A01Request.setBusinessType(businessType);
		CIS1037A01Request.setThaiName(thaiName);
		CIS1037A01Request.setThaiLastName(thaiLastName);
		CIS1037A01Request.setThaiMiddleName(thaiMiddleName);
		CIS1037A01Request.setThaiTitleName(thaiTitleName);
		CIS1037A01Request.setTitleNameCode(titleNameCode);
		CIS1037A01Request.setCustomerTypeCode(customerTypeCode);
		CIS1037A01Request.setValidateFlag(validateFlag);
		CIS1037A01Request.setVipFlag(vipFlag);
		CIS1037A01Request.setWorkStartDate(workStartDate.equals("") ? null : Date.valueOf(workStartDate));
		CIS1037A01Request.setYoungestChildAge(youngestChildAge.equals("") ? 0 : Integer.parseInt(youngestChildAge));
		
		//ContactAddrObj
		String contactAddressAmphur = request.getParameter("contactAddressAmphur_input");
		String contactAddressBuilding = request.getParameter("contactAddressBuilding_input");
		String contactAddressCountry = request.getParameter("contactAddressCountry_input");
		String contactAddressFloor = request.getParameter("contactAddressFloor_input");
		String contactAddressNumber = request.getParameter("contactAddressNumber_input");
		String contactAddressMoo = request.getParameter("contactAddressMoo_input");
		String contactAddressName = request.getParameter("contactAddressName_input");
		String contactAddressBoxNo = request.getParameter("contactAddressBoxNo_input");
		String contactAddressPostCode = request.getParameter("contactAddressPostCode_input");
		String contactAddressPreferedFlag = request.getParameter("contactAddressPreferedFlag_input");
		String contactAddressProvince = request.getParameter("contactAddressProvince_input");
		String contactAddressRoad = request.getParameter("contactAddressRoad_input");
		String contactAddressRoom = request.getParameter("contactAddressRoom_input");
		String contactAddressSoi = request.getParameter("contactAddressSoi_input");
		String contactAddressTumbol = request.getParameter("contactAddressTumbol_input");
		String contactAddressMooban = request.getParameter("contactAddressMooban_input");
		
		CIS1037A01Request.setContactAddressAmphur(contactAddressAmphur);
		CIS1037A01Request.setContactAddressBuilding(contactAddressBuilding);
		CIS1037A01Request.setContactAddressCountry(contactAddressCountry);
		CIS1037A01Request.setContactAddressFloor(contactAddressFloor);
		CIS1037A01Request.setContactAddressNumber(contactAddressNumber);
		CIS1037A01Request.setContactAddressMoo(contactAddressMoo);
		CIS1037A01Request.setContactAddressName(contactAddressName);
		CIS1037A01Request.setContactAddressBoxNo(contactAddressBoxNo);
		CIS1037A01Request.setContactAddressPostCode(contactAddressPostCode);
		CIS1037A01Request.setContactAddressPreferedFlag(contactAddressPreferedFlag);
		CIS1037A01Request.setContactAddressProvince(contactAddressProvince);
		CIS1037A01Request.setContactAddressRoad(contactAddressRoad);
		CIS1037A01Request.setContactAddressRoom(contactAddressRoom);
		CIS1037A01Request.setContactAddressSoi(contactAddressSoi);
		CIS1037A01Request.setContactAddressTumbol(contactAddressTumbol);
		CIS1037A01Request.setContactAddressMooban(contactAddressMooban);
		
		//ContactVect
		String[] location = request.getParameterValues("locationCode_input");
		String telephoneAvailability = request.getParameter("availabilityTime_input");
		String telephoneExtNumber = request.getParameter("phoneExtensionNumber_input");
		String telephoneNumber = request.getParameter("phoneNumber_input");
		String[] type = request.getParameterValues("typeCode_input");
		String mailName = request.getParameter("name_input");

		CIS1037A01Request.setTelephoneLocation(location[0]);
		CIS1037A01Request.setTelephoneAvailability(telephoneAvailability);
		CIS1037A01Request.setTelephoneExtNumber(telephoneExtNumber);
		CIS1037A01Request.setTelephoneNumber(telephoneNumber);
		CIS1037A01Request.setTelephoneType(type[0]);
		CIS1037A01Request.setMailLocation(location[1]);
		CIS1037A01Request.setMailName(mailName);
		CIS1037A01Request.setMailType(type[1]);
		
		//KYCObj
		String completeDocumentFlag = request.getParameter("completeDocumentFlag_input");
		String kycCompleteDocumentFlag = request.getParameter("kycCompleteDocumentFlag_input");
		String kycBranchCode = request.getParameter("kycBranchCode_input");
		
		CIS1037A01Request.setCompleteDocumentFlag(completeDocumentFlag);
		CIS1037A01Request.setKycCompleteDocumentFlag(kycCompleteDocumentFlag);
		CIS1037A01Request.setKycBranchCode(kycBranchCode);
		
		//OfficialAddress
		String officialAddressAmphur = request.getParameter("officialAddressAmphur_input");
		String officialAddressBuilding = request.getParameter("officialAddressBuilding_input");
		String officialAddressCountry = request.getParameter("officialAddressCountry_input");
		String officialAddressFloor = request.getParameter("officialAddressFloor_input");
		String officialAddressNumber = request.getParameter("officialAddressNumber_input");
		String officialAddressMoo = request.getParameter("officialAddressMoo_input");
		String officialAddressName = request.getParameter("officialAddressName_input");
		String officialAddressBoxNo = request.getParameter("officialAddressBoxNo_input");
		String officialAddressPostCode = request.getParameter("officialAddressPostCode_input");
		String officialAddressPreferedFlag = request.getParameter("officialAddressPreferedFlag_input");
		String officialAddressProvince = request.getParameter("officialAddressProvince_input");
		String officialAddressRoad = request.getParameter("officialAddressRoad_input");
		String officialAddressRoom = request.getParameter("officialAddressRoom_input");
		String officialAddressSoi = request.getParameter("officialAddressSoi_input");
		String officialAddressTumbol = request.getParameter("officialAddressTumbol_input");
		String officialAddressMooban = request.getParameter("officialAddressMooban_input");
		
		CIS1037A01Request.setOfficialAddressAmphur(officialAddressAmphur);
		CIS1037A01Request.setOfficialAddressBuilding(officialAddressBuilding);
		CIS1037A01Request.setOfficialAddressCountry(officialAddressCountry);
		CIS1037A01Request.setOfficialAddressFloor(officialAddressFloor);
		CIS1037A01Request.setOfficialAddressNumber(officialAddressNumber);
		CIS1037A01Request.setOfficialAddressMoo(officialAddressMoo);
		CIS1037A01Request.setOfficialAddressName(officialAddressName);
		CIS1037A01Request.setOfficialAddressBoxNo(officialAddressBoxNo);
		CIS1037A01Request.setOfficialAddressPostCode(officialAddressPostCode);
		CIS1037A01Request.setOfficialAddressPreferedFlag(officialAddressPreferedFlag);
		CIS1037A01Request.setOfficialAddressProvince(officialAddressProvince);
		CIS1037A01Request.setOfficialAddressRoad(officialAddressRoad);
		CIS1037A01Request.setOfficialAddressRoom(officialAddressRoom);
		CIS1037A01Request.setOfficialAddressSoi(officialAddressSoi);
		CIS1037A01Request.setOfficialAddressTumbol(officialAddressTumbol);
		CIS1037A01Request.setOfficialAddressMooban(officialAddressMooban);
		
	} 
}
