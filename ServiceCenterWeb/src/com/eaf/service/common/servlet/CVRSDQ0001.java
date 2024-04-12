package com.eaf.service.common.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.model.app.CVRSValidationResultDataM;
import com.eaf.orig.ulo.service.cvrsdq0001.dao.CVRSDQ0001ServiceManager;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.common.util.DataFormat;
import com.eaf.service.common.utils.ResponseUtils;
import com.eaf.service.module.manual.CIS1044U01ServiceProxy;
import com.eaf.service.module.manual.CVRSDQ0001ServiceProxy;
import com.eaf.service.module.model.CVRSDQ0001AddressDataM;
import com.eaf.service.module.model.CVRSDQ0001ContactDataM;
import com.eaf.service.module.model.CVRSDQ0001RequestDataM;
import com.eaf.service.module.model.CVRSDQ0001ResponseDataM;
import com.eaf.service.module.model.ResponseValidateResultDataM;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;
@WebServlet("/CVRSDQ0001")
public class CVRSDQ0001 extends HttpServlet{
	private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(CVRSDQ0001.class);
	
    public CVRSDQ0001() {
        super();
    }
	 public static class filePath{
	    	public static final String properties = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")+"properties"+File.separator+"CVRSDQ0001WS.properties";
	    	public static final String json = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")+"properties"+File.separator+"DataQuality.json";
	  }
	 protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String process = request.getParameter("process");
			logger.debug("process : "+process);
			if(process.equals("defualt")){
				File fileproperties = new File((filePath.properties));
				InputStream inStream = new FileInputStream(fileproperties);
				Properties prop = new Properties();
				prop.load(inStream);
				inStream.close();
				
				HashMap<String, Object> object = new HashMap<>();
				object.put(CVRSDQ0001ServiceProxy.url,prop.getProperty(CVRSDQ0001ServiceProxy.url));
					
				ResponseUtils.sendJsonResponse(response, object);
			}else if(process.equals("request")){
				String url = request.getParameter("url_input");

				CVRSDQ0001RequestDataM CVRSDQ0001Request = new CVRSDQ0001RequestDataM();
				mapCVRSDQ0001Request(request,CVRSDQ0001Request);
				
				ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
					serviceRequest.setEndpointUrl(url);
					serviceRequest.setServiceId(CVRSDQ0001ServiceProxy.serviceId);
					serviceRequest.setIgnoreServiceLog(true);
					serviceRequest.setUserId(SystemConstant.getConstant("SYSTEM_USER"));
					serviceRequest.setObjectData(CVRSDQ0001Request);
				ServiceResponseDataM resp = null;	
				try{
					ServiceCenterProxy proxy = new ServiceCenterProxy();
						resp = proxy.requestService(serviceRequest);
				}catch(Exception e){
					e.printStackTrace();
				}
				CVRSDQ0001ResponseDataM CIS1037A01Response = (CVRSDQ0001ResponseDataM)resp.getObjectData();
				if(null != CIS1037A01Response){
					try {
						createValidateResultTable(CIS1037A01Response,SystemConstant.getConstant("SYSTEM_USER"));
					} catch (Exception e) {
						logger.fatal("ERROR",e);
					}
				}
				
				HashMap<String,Object> object = new HashMap<String,Object>();
				Gson gson = new Gson();
				object.put("jsonRq", gson.toJson(resp.getRequestObjectData()));
				object.put("jsonRs", gson.toJson(resp.getResponseObjectData()));
				object.put("response", gson.toJson(resp.getResponseObjectData()));
				logger.debug("resp : "+gson.toJson(resp));
				ResponseUtils.sendJsonResponse(response, object);
				
			}else if(process.equals("save")){
				String url = request.getParameter("url_setting");
				
				File file = new File((filePath.properties));
				InputStream inputStream = new FileInputStream(file);
				Properties prop = new Properties();
				PrintWriter writer = new PrintWriter(file);
				prop.load(inputStream);
				inputStream.close();				
				prop.setProperty(CIS1044U01ServiceProxy.url, url);
				prop.store(writer, String.valueOf("update date :"+new Date(System.currentTimeMillis())));
				writer.close();
			}else if(process.equals("add")){
				 
				
			}else if(process.equals("edit")){
				 
			}else if(process.equals("delete")){

			}
		}
		public void createValidateResultTable(CVRSDQ0001ResponseDataM cvrsdq0001ResponseDataM,String userId) throws Exception{
			Date sysDate = new Date(System.currentTimeMillis());
			ArrayList<ResponseValidateResultDataM>  validateFieldResults = cvrsdq0001ResponseDataM.getResponseValidateFields();
			if(null!=validateFieldResults && validateFieldResults.size()>0){
				for(ResponseValidateResultDataM fieldResultValidate : validateFieldResults){
					CVRSValidationResultDataM cvrsValidationResultDataM = new CVRSValidationResultDataM();
					cvrsValidationResultDataM.setFieldId(fieldResultValidate.getFieldId());
					cvrsValidationResultDataM.setFieldName(fieldResultValidate.getFieldName());
					cvrsValidationResultDataM.setPersonalId(fieldResultValidate.getPersonalId());
					cvrsValidationResultDataM.setErrorCode(fieldResultValidate.getErrorCode());
					cvrsValidationResultDataM.setErrorDesc(fieldResultValidate.getErrorDesc());
					cvrsValidationResultDataM.setFieldGroup(fieldResultValidate.getFildGroup());
					cvrsValidationResultDataM.setCreateDate(sysDate);
					cvrsValidationResultDataM.setCreateBy(userId);
					try {
						CVRSDQ0001ServiceManager.createCVRSValidateResult(cvrsValidationResultDataM);
					} catch (Exception e) {
						logger.fatal("ERROR",e);
						throw new Exception(e);
					}
					
				}
			}
			
			
		}
		public void mapCVRSDQ0001Request(HttpServletRequest request,CVRSDQ0001RequestDataM CVRSDQ0001Request){
			//user profile
			String th_first_name = request.getParameter("th_first_name");
			String personal_id = request.getParameter("personal_id");
			String th_title_name = request.getParameter("th_title_name");
			String th_last_name = request.getParameter("th_last_name");
			String th_middle_name = request.getParameter("th_middle_name");
			String eng_first_name = request.getParameter("eng_first_name");
			String eng_title_name = request.getParameter("eng_title_name");
			String eng_last_name = request.getParameter("eng_last_name");
			String eng_middle_name = request.getParameter("eng_middle_name");
			String gender = request.getParameter("gender");
			String marriage_status = request.getParameter("marriage_status");
			String customer_type = request.getParameter("customer_type");
			String customer_type_code = request.getParameter("customer_type_code");
			String cid_type = request.getParameter("cid_type");
			String date_of_birth = request.getParameter("date_of_birth");
			String id_no = request.getParameter("id_no");
			String issue_by = request.getParameter("issue_by");
			String issue_date = request.getParameter("issue_date");
			String expire_date = request.getParameter("expire_date");
			String nationality = request.getParameter("nationality");
			String race = request.getParameter("race");
			String occupation = request.getParameter("occupation");
			String profession = request.getParameter("profession");
			String position = request.getParameter("position");
			String education = request.getParameter("education");
			String salary = request.getParameter("salary");
			String no_of_employee = request.getParameter("no_of_employee");
			String consent_flag = request.getParameter("consent_flag");
			String asset = request.getParameter("asset");
			
			CVRSDQ0001Request.setPersonalId(personal_id);
			CVRSDQ0001Request.setAsset(asset);
			CVRSDQ0001Request.setDocumentType(cid_type);
			CVRSDQ0001Request.setConsentFlag(consent_flag);
			CVRSDQ0001Request.setCustomerType(customer_type);
			CVRSDQ0001Request.setCustomerTypeCode(customer_type_code);
			CVRSDQ0001Request.setDateOfBirth(DataFormat.stringToDate(date_of_birth));
			CVRSDQ0001Request.setEducation(education);
			CVRSDQ0001Request.setEngFirstName(eng_first_name);
			CVRSDQ0001Request.setEngLastName(eng_last_name);
			CVRSDQ0001Request.setEngMiddleName(eng_middle_name);
			CVRSDQ0001Request.setEngTitleName(eng_title_name);
			CVRSDQ0001Request.setExpireDate(DataFormat.stringToDate(expire_date));
			CVRSDQ0001Request.setGender(gender);
			CVRSDQ0001Request.setIdNo(id_no);
			CVRSDQ0001Request.setIssueBy(issue_by);
			CVRSDQ0001Request.setIssueDate(DataFormat.stringToDate(issue_date));
			CVRSDQ0001Request.setMarriageStatus(marriage_status);
			CVRSDQ0001Request.setNationality(nationality);
			CVRSDQ0001Request.setNoOfEmployee(no_of_employee);
			CVRSDQ0001Request.setOccupation(occupation);
			CVRSDQ0001Request.setPosition(position);
			CVRSDQ0001Request.setProfession(profession);
			CVRSDQ0001Request.setRace(race);
			CVRSDQ0001Request.setSalary(salary);
			CVRSDQ0001Request.setThaiFirstName(th_first_name);
			CVRSDQ0001Request.setThaiLastName(th_last_name);
			CVRSDQ0001Request.setThaiMiddleName(th_middle_name);
			CVRSDQ0001Request.setThaiTitleName(th_title_name);
					 
			//regis address
			String regis_address_type = request.getParameter("regis_address_type");
			String regis_country_code = request.getParameter("regis_country_code");
			String regis_district = request.getParameter("regis_district");
			String regis_amphur = request.getParameter("regis_amphur");
			String regis_building = request.getParameter("regis_building");
			String regis_floor = request.getParameter("regis_floor");
			String regis_moo = request.getParameter("regis_moo");
			String regis_address_no = request.getParameter("regis_address_no");
			String regis_address_name = request.getParameter("regis_address_name");
			String regis_po_box = request.getParameter("regis_po_box");
			String regis_post_code = request.getParameter("regis_post_code");
			String regis_province = request.getParameter("regis_province");
			String regis_road = request.getParameter("regis_road");
			String regis_room = request.getParameter("regis_room");
			String regis_soi = request.getParameter("regis_soi");
			String regis_village = request.getParameter("regis_village");
			
			CVRSDQ0001AddressDataM rigistrationAddress = new CVRSDQ0001AddressDataM();
			rigistrationAddress.setAddressType(regis_address_type);
			rigistrationAddress.setCountryCode(regis_country_code);
			rigistrationAddress.setDistrict(regis_district);
			rigistrationAddress.setAmphurCode(regis_amphur);
			rigistrationAddress.setBuilding(regis_building);
			rigistrationAddress.setFloor(regis_floor);
			rigistrationAddress.setMoo(regis_moo);
			rigistrationAddress.setAddressNo(regis_address_no);
			rigistrationAddress.setAddressName(regis_address_name);
			rigistrationAddress.setPoBox(regis_po_box);
			rigistrationAddress.setPostcode(regis_post_code);
			rigistrationAddress.setProvince(regis_province);
			rigistrationAddress.setRoad(regis_road);
			rigistrationAddress.setRoom(regis_room);
			rigistrationAddress.setRoom(regis_room);
			rigistrationAddress.setSoi(regis_soi);
			rigistrationAddress.setVillage(regis_village);
			CVRSDQ0001Request.setRegistrationAddress(rigistrationAddress);
						
			 //Contact address
			String contact_address_type = request.getParameter("contact_address_type");
			String contact_country_code = request.getParameter("contact_country_code");
			String contact_district = request.getParameter("contact_district");
			String contact_amphur = request.getParameter("contact_amphur");
			String contact_building = request.getParameter("contact_building");
			String contact_floor = request.getParameter("contact_floor");
			String contact_moo = request.getParameter("contact_moo");
			String contact_address_no = request.getParameter("contact_address_no");
			String contact_address_name = request.getParameter("contact_address_name");
			String contact_po_box = request.getParameter("contact_po_box");
			String contact_post_code = request.getParameter("contact_post_code");
			String contact_province = request.getParameter("contact_province");
			String contact_road = request.getParameter("contact_road");
			String contact_room = request.getParameter("contact_room");
			String contact_soi = request.getParameter("contact_soi");
			String contact_village = request.getParameter("contact_village");
			
			CVRSDQ0001AddressDataM contactAddress = new CVRSDQ0001AddressDataM();
			contactAddress.setAddressType(contact_address_type);
			contactAddress.setCountryCode(contact_country_code);
			contactAddress.setDistrict(contact_district);
			contactAddress.setAmphurCode(contact_amphur);
			contactAddress.setBuilding(contact_building);
			contactAddress.setFloor(contact_floor);
			contactAddress.setMoo(contact_moo);
			contactAddress.setAddressNo(contact_address_no);
			contactAddress.setAddressName(contact_address_name);
			contactAddress.setPoBox(contact_po_box);
			contactAddress.setPostcode(contact_post_code);
			contactAddress.setProvince(contact_province);
			contactAddress.setRoad(contact_road);
			contactAddress.setRoom(contact_room);
			contactAddress.setRoom(contact_room);
			contactAddress.setSoi(contact_soi);
			contactAddress.setVillage(contact_village);
			CVRSDQ0001Request.setContactAddress(contactAddress);
			
			//Work Address
			String work_address_type = request.getParameter("work_address_type");
			String work_country_code = request.getParameter("work_country_code");
			String work_district = request.getParameter("work_district");
			String work_amphur = request.getParameter("work_amphur");
			String work_building = request.getParameter("work_building");
			String work_floor = request.getParameter("work_floor");
			String work_moo = request.getParameter("work_moo");
			String work_address_no = request.getParameter("work_address_no");
			String work_address_name = request.getParameter("work_address_name");
			String work_po_box = request.getParameter("work_po_box");
			String work_post_code = request.getParameter("work_post_code");
			String work_province = request.getParameter("work_province");
			String work_road = request.getParameter("work_road");
			String work_room = request.getParameter("work_room");
			String work_soi = request.getParameter("work_soi");
			String work_village = request.getParameter("work_village");

			CVRSDQ0001AddressDataM workAddress = new CVRSDQ0001AddressDataM();
			workAddress.setAddressType(work_address_type);
			workAddress.setCountryCode(work_country_code);
			workAddress.setDistrict(work_district);
			workAddress.setAmphurCode(work_amphur);
			workAddress.setBuilding(work_building);
			workAddress.setFloor(work_floor);
			workAddress.setMoo(work_moo);
			workAddress.setAddressNo(work_address_no);
			workAddress.setAddressName(work_address_name);
			workAddress.setPoBox(work_po_box);
			workAddress.setPostcode(work_post_code);
			workAddress.setProvince(work_province);
			workAddress.setRoad(work_road);
			workAddress.setRoom(work_room);
			workAddress.setRoom(work_room);
			workAddress.setSoi(work_soi);
			workAddress.setVillage(work_village);
			CVRSDQ0001Request.setWorkAddress(workAddress);
						
			//other address			
			String size_of_other_address = request.getParameter("size_of_other_address");
			int sizeOthAddress = Integer.valueOf(size_of_other_address);
			ArrayList<CVRSDQ0001AddressDataM> otherAddresses = new ArrayList<CVRSDQ0001AddressDataM>();
			for(int i=0;i<sizeOthAddress;i++){
				String oth_address_type = request.getParameter("oth_address_type_"+i);
				String oth_country_code = request.getParameter("oth_country_code_"+i);
				String oth_district = request.getParameter("oth_district_"+i);
				String oth_amphur = request.getParameter("oth_amphur_"+i);
				String oth_building = request.getParameter("oth_building_"+i);
				String oth_floor = request.getParameter("oth_floor_"+i);
				String oth_moo = request.getParameter("oth_moo_"+i);
				String oth_address_no = request.getParameter("oth_address_no_"+i);
				String oth_address_name = request.getParameter("oth_address_name_"+i);
				String oth_po_box = request.getParameter("oth_po_box_"+i);
				String oth_post_code = request.getParameter("oth_post_code_"+i);
				String oth_province = request.getParameter("oth_province_"+i);
				String oth_road = request.getParameter("oth_road_"+i);
				String oth_room = request.getParameter("oth_room_"+i);
				String oth_soi = request.getParameter("oth_soi_"+i);
				String oth_village = request.getParameter("oth_village_"+i);
				
				CVRSDQ0001AddressDataM otherAddress = new CVRSDQ0001AddressDataM();
				otherAddress.setAddressType(oth_address_type);
				otherAddress.setCountryCode(oth_country_code);
				otherAddress.setDistrict(oth_district);
				otherAddress.setAmphurCode(oth_amphur);
				otherAddress.setBuilding(oth_building);
				otherAddress.setFloor(oth_floor);
				otherAddress.setMoo(oth_moo);
				otherAddress.setAddressNo(oth_address_no);
				otherAddress.setAddressName(oth_address_name);
				otherAddress.setPoBox(oth_po_box);
				otherAddress.setPostcode(oth_post_code);
				otherAddress.setProvince(oth_province);
				otherAddress.setRoad(oth_road);
				otherAddress.setRoom(oth_room);
				otherAddress.setRoom(oth_room);
				otherAddress.setSoi(oth_soi);
				otherAddress.setVillage(oth_village);
				otherAddresses.add(otherAddress);
			}
			CVRSDQ0001Request.setOtherAddresses(otherAddresses);
			//
			String size_contact= request.getParameter("size_contact");
			int sizeContacts= Integer.valueOf(size_contact);
			ArrayList<CVRSDQ0001ContactDataM> contacts = new ArrayList<CVRSDQ0001ContactDataM>();
			for(int i=0;i<sizeContacts;i++){
				String location = request.getParameter("location_"+i);
				String telephone_num = request.getParameter("telephone_num_"+i);
				String telephone_ext = request.getParameter("telephone_ext_"+i);
				String email = request.getParameter("email_"+i);
				String mobile_no = request.getParameter("mobile_no_"+i);
				String fax = request.getParameter("fax_"+i);
				String fax_ext = request.getParameter("fax_ext_"+i);
				CVRSDQ0001ContactDataM contact = new CVRSDQ0001ContactDataM();
				contact.setEmail(email);
				contact.setLocation(location);
				contact.setTelephoneNumber(telephone_num);
				contact.setExt(telephone_ext);
				contact.setFax(fax);
				contact.setFaxExt(fax_ext);
				contact.setMobileNo(mobile_no);
				contacts.add(contact);
			}
			CVRSDQ0001Request.setContacts(contacts);
		} 
}
