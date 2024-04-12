package com.eaf.orig.shared.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.AddressDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.SerializeUtil;

public class CopyAddressServlet extends HttpServlet implements Servlet {
	Logger logger = Logger.getLogger(CopyAddressServlet.class);
	
	public CopyAddressServlet() {
		super();
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("<<<<<<< Start CopyAddressServlet >>>>>>>");
		try{
			 ORIGUtility utility = new ORIGUtility();
			 ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
			 ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
			 String personalType = (String) request.getSession().getAttribute("PersonalType");
			 PersonalInfoDataM personalInfoDataM = new PersonalInfoDataM();
			 if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
			 	personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
			 }else if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)) {
			 	personalInfoDataM = utility.getPersonalInfoByType(formHandler.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
			 }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
	            personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
			 }
			 String address_type_copy = (String) request.getParameter("address_type_copy");
			 String address_type = (String) request.getParameter("address_type");
			 String seqStr = request.getParameter("address_seq");
			 int seq = 0;
			 if(seqStr != null && !("").equals(seqStr)){
	            seq = Integer.parseInt(seqStr);
	        }
			 
			 logger.debug("address_type_copy=" + address_type_copy);
			 
			 AddressDataM addressDataM;
			 AddressDataM addressDataM2 = new AddressDataM();
			 
			 if(address_type_copy!=null && !"".equals(address_type_copy)){
			 
			 	addressDataM = utility.getAddressByType(personalInfoDataM, address_type_copy);
			 	
			 	addressDataM2 = (AddressDataM) SerializeUtil.clone(addressDataM);
			 	addressDataM2.setAddressType(address_type);
			 	addressDataM2.setAddressSeq(seq);
			 	
			 	//request.getSession().setAttribute("POPUP_DATA", addressDataM2);
			   // getRequest().getSession().setAttribute("MAIN_POPUP_DATA", personalInfoDataM);
			 }
			    int reside_year=0; 
				int reside_month=0;
				if(addressDataM2.getResideYear()!=null){
				  reside_year=addressDataM2.getResideYear().intValue();
				  reside_month=(int)(addressDataM2.getResideYear().doubleValue()*100%100);
				}
			String provinceDesc = cacheUtil.getORIGMasterDisplayNameDataM("Province", addressDataM2.getProvince());
			 
			StringBuffer sb = new StringBuffer("");
			
			 sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		        /*sb.append("<customer_type>");
		        sb.append(personalInfoDataM.getCustomerType());
		        sb.append("</customer_type>");*/
				sb.append("<list>");
				//Customer Infomation
				//sb.append("<field name=\"address_type\">"+ORIGDisplayFormatUtil.displayHTML(addressDataM2.getAddressType())+"</field>");
				//sb.append("<field name=\"seq\">"+ORIGDisplayFormatUtil.displayHTML(String.valueOf(addressDataM2.getAddressSeq()))+"</field>");
				sb.append("<field name=\"address_no\">"+ORIGDisplayFormatUtil. forHTMLTag(addressDataM2.getAddressNo())+"</field>");
				sb.append("<field name=\"room\">"+ORIGDisplayFormatUtil.forHTMLTag(addressDataM2.getRoom())+"</field>");
				sb.append("<field name=\"floor\">"+ORIGDisplayFormatUtil.forHTMLTag(addressDataM2.getFloor())+"</field>");
				sb.append("<field name=\"apartment\">"+ORIGDisplayFormatUtil.forHTMLTag(addressDataM2.getApartment())+"</field>");
				sb.append("<field name=\"moo\">"+ORIGDisplayFormatUtil.forHTMLTag(addressDataM2.getMoo())+"</field>");
				sb.append("<field name=\"soi\">"+ORIGDisplayFormatUtil.forHTMLTag(addressDataM2.getSoi())+"</field>");
				sb.append("<field name=\"road\">"+ORIGDisplayFormatUtil.forHTMLTag(addressDataM2.getRoad())+"</field>");
				sb.append("<field name=\"tambol\">"+ORIGDisplayFormatUtil.forHTMLTag(addressDataM2.getTambol())+"</field>");
				sb.append("<field name=\"amphur\">"+ORIGDisplayFormatUtil.forHTMLTag(addressDataM2.getAmphur())+"</field>");
				sb.append("<field name=\"province\">"+ORIGDisplayFormatUtil.forHTMLTag(addressDataM2.getProvince())+"</field>");
				sb.append("<field name=\"zipcode\">"+ORIGDisplayFormatUtil.forHTMLTag(addressDataM2.getZipcode())+"</field>");
				sb.append("<field name=\"phone1\">"+ORIGDisplayFormatUtil.forHTMLTag(addressDataM2.getPhoneNo1())+"</field>");
				sb.append("<field name=\"ext1\">"+ORIGDisplayFormatUtil.forHTMLTag(addressDataM2.getPhoneExt1())+"</field>");
				sb.append("<field name=\"mobile_no\">"+ORIGDisplayFormatUtil.forHTMLTag(addressDataM2.getMobileNo())+"</field>");
				sb.append("<field name=\"contact_person\">"+ORIGDisplayFormatUtil.forHTMLTag(addressDataM2.getContactPerson())+"</field>");
				sb.append("<field name=\"phone2\">"+ORIGDisplayFormatUtil.forHTMLTag(addressDataM2.getPhoneNo2())+"</field>");
				sb.append("<field name=\"ext2\">"+ORIGDisplayFormatUtil.forHTMLTag(addressDataM2.getPhoneExt2())+"</field>");
				sb.append("<field name=\"fax_no\">"+ORIGDisplayFormatUtil.forHTMLTag(addressDataM2.getFaxNo())+"</field>");
				sb.append("<field name=\"email\">"+ORIGDisplayFormatUtil.forHTMLTag(addressDataM2.getEmail())+"</field>");
				sb.append("<field name=\"house_id\">"+ORIGDisplayFormatUtil.forHTMLTag(addressDataM2.getHouseID())+"</field>");				
				sb.append("<field name=\"reside_year\">"+ORIGDisplayFormatUtil.displayInteger(reside_year)+"</field>");
				sb.append("<field name=\"reside_month\">"+ORIGDisplayFormatUtil.displayInteger(reside_month)+"</field>");
				sb.append("<field name=\"status\">"+ORIGDisplayFormatUtil.forHTMLTag(addressDataM2.getStatus())+"</field>");
				sb.append("<field name=\"remark\">"+ORIGDisplayFormatUtil.forHTMLTag(addressDataM2.getRemark())+"</field>");
				sb.append("<field name=\"province_desc\">"+ORIGDisplayFormatUtil.forHTMLTag(provinceDesc)+"</field>");
				
				sb.append("</list>");
				
				String returnValue = sb.toString();
				//Create response
				resp.setContentType("text/xml;charset=UTF-8");
				resp.setHeader("Cache-Control", "no-cache");
				
				PrintWriter pw = resp.getWriter();	
				logger.debug("returnValue = "+returnValue);
				
				pw.write(returnValue);
				pw.close();
				logger.debug("==> out doPost");
		 
		}catch(Exception e){
			logger.error("Error >>> ", e);
		}
	}

}