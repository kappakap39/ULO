package com.eaf.service.common.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
import com.eaf.service.common.util.DataFormat;
import com.eaf.service.common.utils.ResponseUtils;
import com.eaf.service.module.manual.CIS1044U01ServiceProxy;
import com.eaf.service.module.model.CIS1044U01RequestDataM;
import com.eaf.service.module.model.CIS1044U01ResponseDataM;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;
@WebServlet("/CIS1044U01")
public class CIS1044U01 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger =Logger.getLogger(CIS1044U01.class);
	
    public CIS1044U01() {
        super();
    }
    public static class filePath{
    	public static String properties = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")
    			+"properties"+File.separator+"CIS1044U01WS.properties";
    }
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String process = request.getParameter("process");
		logger.debug("process : "+process);
		if(process.equals("defualt")){
			File file = new File((filePath.properties));
			InputStream inStream = new FileInputStream(file);
			Properties prop = new Properties();
			prop.load(inStream);
			inStream.close();
			
			HashMap<String, Object> object = new HashMap<>();
			object.put(CIS1044U01ServiceProxy.url,prop.getProperty(CIS1044U01ServiceProxy.url));
			object.put(CIS1044U01ServiceProxy.responseConstants.cusId,prop.getProperty(CIS1044U01ServiceProxy.responseConstants.cusId));
			object.put(CIS1044U01ServiceProxy.responseConstants.status,prop.getProperty(CIS1044U01ServiceProxy.responseConstants.status));
			
			ResponseUtils.sendJsonResponse(response, object);
		}else if(process.equals("request")){
			String url = request.getParameter("url_input");
			String hub_number = request.getParameter("hub_number");
			String branch_number = request.getParameter("branch_number");
			String function_flag = request.getParameter("function_flag");
			String customer_type = request.getParameter("customer_type");
			String customer_id = request.getParameter("customer_id");
			String thai_title_name = request.getParameter("thai_title_name");
			String thai_name = request.getParameter("thai_name");
			String thai_middle_name = request.getParameter("thai_middle_name");
			String thai_last_name = request.getParameter("thai_last_name");
			String eng_title_name = request.getParameter("eng_title_name");
			String eng_name = request.getParameter("eng_name");
			String eng_middle_name = request.getParameter("eng_middle_name");
			String eng_last_name = request.getParameter("eng_last_name");
			String customer_type_code = request.getParameter("customer_type_code");
			String owner_branch = request.getParameter("owner_branch");
			String doc_type = request.getParameter("doc_type");
			String doc_id = request.getParameter("doc_id");
			String doc_expired_date = request.getParameter("doc_expired_date");
			String issued_date = request.getParameter("issued_date");
			String issue_by = request.getParameter("issue_by");
			String issue_card = request.getParameter("issue_card");
			String birth_date = request.getParameter("birth_date");
			String official_address_country = request.getParameter("official_address_country");
			String contact_address_contry = request.getParameter("contact_address_contry");
			String gender = request.getParameter("gender");
			String marital_status = request.getParameter("marital_status");
			String religion = request.getParameter("religion");
			String occupation = request.getParameter("occupation");
			String job_position = request.getParameter("job_position");
			String business_type = request.getParameter("business_type");
			String salary = request.getParameter("salary");
			String start_work_date = request.getParameter("start_work_date");
			String degree = request.getParameter("degree");
			String nationality = request.getParameter("nationality");
			String race = request.getParameter("race");
			String book_number = request.getParameter("book_number");
			String seq_number = request.getParameter("seq_number");
			String change_name_date = request.getParameter("change_name_date");
			String consent_flag = request.getParameter("consent_flag");
			String source_consent = request.getParameter("source_consent");
			String kyc_branch = request.getParameter("kyc_branch");
			String complete_doc_flag = request.getParameter("complete_doc_flag");
			String complete_kyc_doc_flag = request.getParameter("complete_kyc_doc_flag");
			String profession_code = request.getParameter("profession_code");
			String num_of_employee = request.getParameter("num_of_employee");
			String asset_exclude_land = request.getParameter("asset_exclude_land");
			String multi_contact_chanel = request.getParameter("multi_contact_chanel");
			String amt_child = request.getParameter("amt_child");
			String age_of_oldes_child = request.getParameter("age_of_oldes_child");
			String age_of_youngest_child = request.getParameter("age_of_youngest_child");
			String family_income_Range = request.getParameter("family_income_Range");
			String customer_segment = request.getParameter("customer_segment");
			String forced_save_flag = request.getParameter("forced_save_flag");
			String flag_same_address = request.getParameter("flag_same_address");
			String vip_flag = request.getParameter("vip_flag");
			String death_flag = request.getParameter("death_flag");
			String death_date = request.getParameter("death_date");
			String tax_id = request.getParameter("tax_id");
			
			
			CIS1044U01RequestDataM CIS1044U01Request = new CIS1044U01RequestDataM();	
			CIS1044U01Request.setBranchNo(branch_number);
			CIS1044U01Request.setHubNo(hub_number);
			CIS1044U01Request.setUserId(SystemConstant.getConstant("SYSTEM_USER"));
			CIS1044U01Request.setProspectFlag(function_flag); 
			CIS1044U01Request.setCustomerId(customer_id);
			CIS1044U01Request.setThTitle(thai_title_name);
			CIS1044U01Request.setThFstName(thai_name);
			CIS1044U01Request.setThMidName(thai_middle_name);
			CIS1044U01Request.setThLstName(thai_last_name );
			CIS1044U01Request.setEngTitle(eng_title_name );
			CIS1044U01Request.setEngFstName(eng_name );
			CIS1044U01Request.setEngMidName(eng_middle_name );
			CIS1044U01Request.setEngLstName(eng_last_name );
			CIS1044U01Request.setCustTypeCode(customer_type_code );
			CIS1044U01Request.setSvcBrchCode(owner_branch );
			CIS1044U01Request.setDocTypeCode(doc_type);
			CIS1044U01Request.setDocNum(doc_id );
			CIS1044U01Request.setIdCrdExpDate(DataFormat.stringToDate(doc_expired_date));
			CIS1044U01Request.setIdCrdIssuDate(DataFormat.stringToDate(issued_date));
			CIS1044U01Request.setIdCrdIssuPlaceDesc(issue_by);
			CIS1044U01Request.setIdCrdDesc(issue_card );
			CIS1044U01Request.setBirthDate(DataFormat.stringToDate(birth_date));
			CIS1044U01Request.setTaxNum(tax_id);
			CIS1044U01Request.setOfficialAddressCountry(official_address_country );
			CIS1044U01Request.setContactAddressCountry(contact_address_contry );
			CIS1044U01Request.setGender(gender);
			CIS1044U01Request.setMaritalStatus(marital_status );
			CIS1044U01Request.setReligion(religion );
			CIS1044U01Request.setOccupation(occupation );
			CIS1044U01Request.setJobPosition(job_position );
			CIS1044U01Request.setBusinessTypeCode(business_type );
			CIS1044U01Request.setSalary(salary);
			CIS1044U01Request.setStartWorkDate(DataFormat.stringToDate(start_work_date));
			CIS1044U01Request.setDegree(degree);
			CIS1044U01Request.setNationality(nationality);
			CIS1044U01Request.setRace(race);
			CIS1044U01Request.setBookNumberForChangeName(book_number );
			CIS1044U01Request.setSequenceNumberForChangeName(seq_number );
			CIS1044U01Request.setTheDateChangeName(DataFormat.stringToDate(change_name_date));
			CIS1044U01Request.setConsendFlag(consent_flag);
			CIS1044U01Request.setSourceOfConsent(source_consent );
			CIS1044U01Request.setKycBranch(kyc_branch);
			CIS1044U01Request.setCompleteDocumentFlag(complete_doc_flag );
			CIS1044U01Request.setCompleteKYCDocumentFlag(complete_kyc_doc_flag) ;
			CIS1044U01Request.setProfessionCode(profession_code);
			CIS1044U01Request.setNumberOfEmployee(DataFormat.getInteger(num_of_employee));
			CIS1044U01Request.setAssetExcludeLand(null==asset_exclude_land || "".equals(asset_exclude_land)?BigDecimal.ZERO:new BigDecimal(asset_exclude_land));
			CIS1044U01Request.setMultipleContactChannel(multi_contact_chanel);
			CIS1044U01Request.setAmountOfChildren(DataFormat.getInteger(amt_child));
			CIS1044U01Request.setAgeOfOldestChild(DataFormat.getInteger(age_of_oldes_child));
			CIS1044U01Request.setAgeOfYoungestChild(DataFormat.getInteger(age_of_youngest_child));
			CIS1044U01Request.setFamilyIncomeRange(family_income_Range );
			CIS1044U01Request.setCustomerSegment(customer_segment);
			CIS1044U01Request.setTitleForcedSaveFlag(forced_save_flag);
			CIS1044U01Request.setAddrOffCntctSameCde(flag_same_address);
			CIS1044U01Request.setVipFlag(vip_flag);
			CIS1044U01Request.setDeathFlag(death_flag);
			CIS1044U01Request.setDeathDate(DataFormat.stringToDate(death_date));
			CIS1044U01Request.setCustomerType(customer_type);
			
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setEndpointUrl(url);
				serviceRequest.setServiceId( CIS1044U01ServiceProxy.serviceId);
				serviceRequest.setUserId(SystemConstant.getConstant("SYSTEM_USER"));
				serviceRequest.setObjectData(CIS1044U01Request);
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
			
			 CIS1044U01ResponseDataM  CIS1044U01Response = ( CIS1044U01ResponseDataM)resp.getObjectData();
			if(null ==  CIS1044U01Response){
				 CIS1044U01Response = new  CIS1044U01ResponseDataM();
			}
			object.put( CIS1044U01ServiceProxy.responseConstants.cusId,  CIS1044U01Response.getCustomerId());
			object.put( CIS1044U01ServiceProxy.responseConstants.status,  CIS1044U01Response.getStatus());
			
			ResponseUtils.sendJsonResponse(response, object);
			
		}else if(process.equals("save")){
			String url = request.getParameter("url_setting");
			String cusId = request.getParameter("cusId_setting");
			String staus = request.getParameter("status_setting");
			
			File file = new File((filePath.properties));
			InputStream inputStream = new FileInputStream(file);
			Properties prop = new Properties();
			PrintWriter writer = new PrintWriter(file);
			prop.load(inputStream);
			inputStream.close();
			
			prop.setProperty(CIS1044U01ServiceProxy.url, url);
			prop.setProperty(CIS1044U01ServiceProxy.responseConstants.cusId, cusId);
			prop.setProperty(CIS1044U01ServiceProxy.responseConstants.status, staus);
			prop.store(writer, String.valueOf("update date :"+new Date(System.currentTimeMillis())));
			writer.close();
			
		}
	}
}
