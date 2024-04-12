package com.eaf.orig.shared.ajax;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.MessageResourceUtil;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.shared.constant.OrigConstant;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
//import com.eaf.orig.shared.dao.OrigVehicleMDAO;
import com.eaf.orig.shared.ejb.ORIGApplicationManager;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.VehicleDataM;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class LoadVehicleDetailServlet extends HttpServlet implements Servlet {
    Logger log = Logger.getLogger(LoadVehicleDetailServlet.class);
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public LoadVehicleDetailServlet() {
		super();
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req,resp);
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.debug("Start LoadVehicleDetailServlet");
		try{
			ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
			ORIGFormHandler ORIGForm = (ORIGFormHandler) req.getSession().getAttribute("ORIGForm");	
			Vector vehicleVt = new Vector();
			
		    String coClientGroup = req.getParameter("coClientGroup");
		    log.debug("CoClientGroup is:"+coClientGroup);
			ApplicationDataM appM = ORIGForm.getAppForm();
			if(appM == null){
			    appM = new ApplicationDataM();
			}
			Vector personalVt = new Vector();
			PersonalInfoDataM personalInfoDataM = new PersonalInfoDataM();
		    personalInfoDataM.setPersonalType(OrigConstant.PERSONAL_TYPE_APPLICANT);
		    personalInfoDataM.setIdNo(coClientGroup);
		    personalInfoDataM.setClientGroup(coClientGroup);
			
			
        	//StringBuffer sql = new StringBuffer();			 
			StringBuffer result = new StringBuffer("");
			//ValueListM valueListM = new ValueListM();
			//Vector elementVt = null;
			//int index = 0;
			
			//sql.append("SELECT V.VEHICLE_ID, (SELECT K.CLTGRP FROM  HPMSHP00 K WHERE K.IDNO = V.IDNO ) AS CLIENT_GROUP, V.CATEGORY, V.BRAND, V.CAR, V.CHASSIS_NUMBER, V.GEAR, V.DRAW_DOWN_STATUS, V.IDNO, (SELECT K.CUSTYP FROM HPMSHP00 K WHERE K.IDNO = V.IDNO ) AS CUST_TYPE, CONTRACT_NO FROM ORIG_VEHICLE V");
			
		    //if(!ORIGUtility.isEmptyString(coClientGroup)){
		    //    sql.append(" WHERE V.IDNO = ? ");
		    //   valueListM.setString(++index,coClientGroup);
			//}
			
//			OrigVehicleMDAO origVehicleMDAO = ORIGDAOFactory.getOrigVehicleMDAO();
//			Vector resultVt = origVehicleMDAO.loadVehicleDetailByCustomerId(coClientGroup);
			
			Vector resultVt = PLORIGEJBService.getORIGDAOUtilLocal().loadVehicleDetailByCustomerId(coClientGroup);
			
			log.debug("resultVt.size() .. "+resultVt.size());
			
			result.append("<table class=\"gumayframe3\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" align=\"center\" border=\"0\">");
			result.append("<tr><td class=\"TableHeader\">");
			result.append("<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" align=\"center\" border=\"0\">");
			result.append("<tr><td class=\"Bigtodotext3\" colspan=\"11\" align=\"left\">Car & Insurance & Loan</td></tr>");
			result.append("</table></td></tr>");
			result.append("<tr><td class=\"TableHeader\">");
			result.append("<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" align=\"center\" border=\"0\">");			
			result.append("<tr><td class=\"Bigtodotext3\" width=\"5%\">"+MessageResourceUtil.getTextDescription(req, "DELETE")+"</td>");
			result.append("<td class=\"Bigtodotext3\" width=\"5%\">"+MessageResourceUtil.getTextDescription(req, "COPY")+"</td>");
			result.append("<td class=\"Bigtodotext3\" width=\"5%\">"+MessageResourceUtil.getTextDescription(req, "SEQ")+"</td>");
			result.append("<td class=\"Bigtodotext3\" width=\"10%\">"+MessageResourceUtil.getTextDescription(req, "CLIENT_GROUP_CODE")+"</td>");
			result.append("<td class=\"Bigtodotext3\" width=\"15%\">"+MessageResourceUtil.getTextDescription(req, "CATEGORY")+"</td>");
			result.append("<td class=\"Bigtodotext3\" width=\"15%\">"+MessageResourceUtil.getTextDescription(req, "BRAND")+"</td>");
			result.append("<td class=\"Bigtodotext3\" width=\"10%\">"+MessageResourceUtil.getTextDescription(req, "CAR")+"</td>");
			result.append("<td class=\"Bigtodotext3\" width=\"10%\">"+MessageResourceUtil.getTextDescription(req, "CLASSIS_NO")+"</td>");
			result.append("<td class=\"Bigtodotext3\" width=\"5%\">"+MessageResourceUtil.getTextDescription(req, "GEAR")+"</td>");
			result.append("<td class=\"Bigtodotext3\" width=\"10%\">"+MessageResourceUtil.getTextDescription(req, "CAR_STATUS")+"</td>");
			result.append("<td class=\"Bigtodotext3\" width=\"10%\">"+MessageResourceUtil.getTextDescription(req, "CONTRACT_NO")+"</td>");
			result.append("</tr>");
			result.append("</table></td></tr>");
				
				if(resultVt==null)resultVt = new Vector();
			    
				String category = "";
			    String brand = "";
			    String car = "";
			    String gear = "";
			    if(resultVt.size() > 0){
			    	VehicleDataM vehicleDataM;
			    	ORIGUtility utility = new ORIGUtility();
				    for(int i=0;i<resultVt.size();i++){
				        vehicleDataM = (VehicleDataM)resultVt.get(i);
				        
				        category = cacheUtil.getORIGMasterDisplayNameDataM("CarCategoryType", vehicleDataM.getCategory());
				        brand = cacheUtil.getORIGMasterDisplayNameDataM("CarBrand", vehicleDataM.getBrand());
				        car = cacheUtil.getNaosCacheDisplayNameDataM(9, vehicleDataM.getCar());
				        gear = utility.getGearDesc(vehicleDataM.getGear());
				        result.append("<tr><td align=\"center\" class=\"gumaygrey2\"> ");
				        result.append("<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" align=\"center\" border=\"0\">");
				        result.append("<TR onmouseover=\"this.style.cursor='hand' ;this.style.background='#D7D6D7'\" onmouseout=\" this.style.background='#FFFFFF'\" >");
				        if(vehicleDataM.getDrawDownStatus().equals("NEW")){
				            result.append("<TD class=\"jobopening2\" width=\"5%\"><INPUT type=\"checkbox\" name=\"chkCar\" value=\""+vehicleDataM.getVehicleID()+"\"></TD>");
				        }else{
				            result.append("<TD class=\"jobopening2\" width=\"5%\"></TD>");
				        }
				        result.append("<TD class=\"jobopening2\" width=\"5%\">"+ORIGDisplayFormatUtil.displayRadioTagScriptAction(String.valueOf(vehicleDataM.getVehicleID()),ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT,"dupCarID","Y","","","")+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"5%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleDataM.getVehicleID()+",'edit')\">"+(i+1)+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"10%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleDataM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(vehicleDataM.getClientGroup())+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"15%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleDataM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(category)+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"15%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleDataM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(brand)+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"10%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleDataM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(car)+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"10%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleDataM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(vehicleDataM.getChassisNo())+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"5%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleDataM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(gear)+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"10%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleDataM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(vehicleDataM.getDrawDownStatus())+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"10%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleDataM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(vehicleDataM.getContractNo())+"</TD>");
				        result.append("</TR>");
				        result.append("</table></td></tr>");
				    	
				        log.debug("Customer Type = "+vehicleDataM.getCustomerType());
				        personalInfoDataM.setCustomerType(vehicleDataM.getCustomerType());
				        personalInfoDataM.setClientGroup(vehicleDataM.getClientGroup());
				        personalInfoDataM.setIdNo(coClientGroup);
				        
				  }
			   }else{
				   result.append("<tr><td align=\"center\" class=\"gumaygrey2\"> ");
			        result.append("<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" align=\"center\" border=\"0\">");
			        result.append("<TR><TD class=\"jobopening2\" colspan=\"11\" align=\"center\">Results Not Found.</TD></TR>");
			        result.append("</table></td></tr>");
			   }
				
			    
			    personalInfoDataM.setCustomerType("01"); 
				
				if(ORIGUtility.isEmptyString(personalInfoDataM.getCustomerType())){
					String providerUrlEXT = null;
					String jndiEXT = null;
//					ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
//					String customerType = applicationEXTManager.selectCustomerType(coClientGroup);
//					personalInfoDataM.setCustomerType(customerType);
					String clientGroup = (String) req.getSession(true).getAttribute("CLIENT_GROUP_NAME");
					personalInfoDataM.setClientGroup(clientGroup);
				}
				
			    personalVt.add(personalInfoDataM);
			    appM.setPersonalInfoVect(personalVt);
			    
			    ORIGApplicationManager applicationManager = ORIGEJBService.getApplicationManager();
			    vehicleVt = applicationManager.loadCarDetail(coClientGroup);
			    
			    req.getSession().setAttribute("VEHICLE_RESULT",vehicleVt);
			    
				result.append("</table>");
				if(resultVt.size() <= 0){
					result.append(":0");
				}else{
					result.append(":1");
				}
				log.debug(">> result : " + result.toString());
				resp.setContentType("text/xml;charset=UTF-8");
				resp.setHeader("Cache-Control", "no-cache");
				PrintWriter pw = resp.getWriter();			
				pw.write(result.toString());
				pw.close();
			
		    }catch (Exception e) {
		        log.error("exception ",e);
            }
		
		log.debug("End LoadVehicleDetailServlet");
		
	}

}