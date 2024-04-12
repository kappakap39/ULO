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
import com.eaf.orig.shared.constant.EJBConstant;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.InsuranceDataM;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.VehicleDataM;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.SerializeUtil;

public class DupCarDetailServlet extends HttpServlet implements Servlet {
    Logger log = Logger.getLogger(DupCarDetailServlet.class);
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public DupCarDetailServlet() {
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
		log.debug("Start DupCarDetailServlet");
		try{
			ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
			ORIGFormHandler ORIGForm = (ORIGFormHandler) req.getSession().getAttribute("ORIGForm");	
			
			String dup_amount = req.getParameter("dup_amount");
			String dupCarID = req.getParameter("dupCarID");
			log.debug("dup_amount = "+dup_amount);
			log.debug("dupCarID = "+dupCarID);
			Vector vehicleVtTemp = (Vector)req.getSession().getAttribute("VEHICLE_RESULT");
			double totalDrawdown = 0;
			
			ApplicationDataM appM = ORIGForm.getAppForm();
			if(appM == null){
			    appM = new ApplicationDataM();
			}
			double totalCreditlimit = ORIGForm.getAppForm().getTotalCreditLimit();
	        double totalCreateCar = ORIGForm.getAppForm().getCreateCarAmount();
	        double available = totalCreditlimit-totalCreateCar;
	        log.debug("available = "+available);
	        if(available < 0){
	        	available = 0;
	        }
			int amount = Integer.parseInt(dup_amount);
			StringBuffer result = new StringBuffer("");
			
			ORIGUtility utility = new ORIGUtility();
			VehicleDataM vehicleDataM = utility.getVehicleDataMDetailById(vehicleVtTemp, Integer.parseInt(dupCarID));
			VehicleDataM vehicleDataMTmp = (VehicleDataM) SerializeUtil.clone(vehicleDataM);
			LoanDataM loanDataM = vehicleDataMTmp.getLoanDataM();
			double availableTmp = loanDataM.getCostOfFinancialAmt().doubleValue()*amount;
			log.debug("availableTmp = "+availableTmp);
			//If over total credit limit not allow to duplicate car
			if(availableTmp <= available){
				Vector personalVt = appM.getPersonalInfoVect();
				PersonalInfoDataM personalDataM = (PersonalInfoDataM)personalVt.get(0);
				
				vehicleDataMTmp.setDrawDownStatus("NEW");
				vehicleDataMTmp.setAppRecordID("");
				vehicleDataMTmp.setEngineNo("");
				vehicleDataMTmp.setChassisNo("");
				vehicleDataMTmp.setRegisterNo("");
				loanDataM.setLoanID(0);
				InsuranceDataM insuranceDataM = vehicleDataMTmp.getInsuranceDataM();
				insuranceDataM.setInsuranceID(0);
				
				for(int i=0; i<amount; i++){
					//int vehicleID = Integer.parseInt(ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.DUP_VEHICLE_ID));
					ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
					int vehicleID = Integer.parseInt(generatorManager.generateUniqueIDByName(EJBConstant.DUP_VEHICLE_ID));
					log.debug("vehicleID = "+vehicleID);
					vehicleDataMTmp = (VehicleDataM) SerializeUtil.clone(vehicleDataMTmp);
					vehicleDataMTmp.setVehicleID(vehicleID);
					vehicleVtTemp.add(vehicleDataMTmp);
				}
				
				result.append("<table class=\"gumayframe3\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" align=\"center\" border=\"0\">");
				result.append("<tr><td class=\"TableHeader\">");
				result.append("<table width=\"100%\" border=\"0\"  cellSpacing=\"0\" cellPadding=\"0\" >");
				result.append("<tr><td class=\"Bigtodotext3\" colspan=\"11\" align=\"left\">Car & Insurance & Loan</td></tr>");
				result.append("</table></td></tr>");
				result.append("<tr><td class=\"TableHeader\">");
				result.append("<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" align=\"center\" border=\"0\">");
				
				result.append("<tr><td class=\"Bigtodotext3\" width=\"5%\">Delete</td>");
				result.append("<td class=\"Bigtodotext3\" width=\"5%\">Copy</td>");
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
				
				double totalCreateCarTmp = 0;
				if(vehicleVtTemp!=null&&vehicleVtTemp.size()>0){
				    String category = "";
				    String brand = "";
				    String car = "";
				    String gear = "";
				    for(int i=0;i<vehicleVtTemp.size();i++){
				        vehicleDataM = (VehicleDataM)vehicleVtTemp.get(i);
						
				        category = cacheUtil.getORIGMasterDisplayNameDataM("CarCategoryType", vehicleDataM.getCategory());
				        brand = cacheUtil.getORIGMasterDisplayNameDataM("CarBrand", vehicleDataM.getBrand());
				        car = cacheUtil.getNaosCacheDisplayNameDataM(9, vehicleDataM.getCar());
				        gear = utility.getGearDesc(vehicleDataM.getGear());
				        //get toatlCreateCar
				        loanDataM = null;
				        //if(vehicleDataM.getDrawDownStatus().equals("NEW")||vehicleDataM.getDrawDownStatus().equals("PROGRESS")){
				            loanDataM = vehicleDataM.getLoanDataM();
				            log.debug("loanDataM.getCostOfFinancialAmt() = "+loanDataM.getCostOfFinancialAmt());
				            totalDrawdown = totalDrawdown + loanDataM.getCostOfFinancialAmt().doubleValue();
				        //}
				        totalCreateCarTmp = totalCreateCarTmp+loanDataM.getCostOfFinancialAmt().doubleValue();
				        result.append("<tr><td align=\"center\" class=\"gumaygrey2\"> ");
				        result.append("<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" align=\"center\" border=\"0\">");
				        result.append("<TR onmouseover=\"this.style.cursor='hand' ;this.style.background='#D7D6D7'\" onmouseout=\" this.style.background='#FFFFFF'\" >");
				        if(vehicleDataM.getDrawDownStatus().equals("NEW")){
				            result.append("<TD class=\"jobopening2\" width=\"5%\"><INPUT type=\"checkbox\" name=\"chkCar\" value=\""+vehicleDataM.getVehicleID()+"\"></TD>");
				        }else{
				            result.append("<TD class=\"jobopening2\" width=\"5%\"></TD>");
				        }
				        log.debug("vehicleDataM.getVehicleID() = "+vehicleDataM.getVehicleID());
				        result.append("<TD class=\"jobopening2\" width=\"5%\">"+ORIGDisplayFormatUtil.displayRadioTagScriptAction(String.valueOf(vehicleDataM.getVehicleID()),ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT,"dupCarID","Y","","","")+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"5%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleDataM.getVehicleID()+",'edit')\">"+(i+1)+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"10%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleDataM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(personalDataM.getClientGroup())+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"15%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleDataM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(category)+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"15%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleDataM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(brand)+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"10%\" wonClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleDataM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(car)+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"10%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleDataM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(vehicleDataM.getChassisNo())+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"5%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleDataM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(gear)+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"10%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleDataM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(vehicleDataM.getDrawDownStatus())+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"10%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleDataM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(vehicleDataM.getContractNo())+"</TD>");
				        result.append("</TR>");
				        result.append("</table></td></tr>");
	
						log.debug("getCategory is:"+vehicleDataM.getCategory());
						log.debug("getBrand is:"+vehicleDataM.getBrand());
						log.debug("getCar is:"+vehicleDataM.getCar());
						log.debug("getChassisNo is:"+vehicleDataM.getChassisNo());
						log.debug("getGear is:"+vehicleDataM.getGear());
				        }
				    }
					double totalFinalCreditExpiry = appM.getTotalFinalCreditExpiry();
					if(totalDrawdown < totalFinalCreditExpiry){
						totalCreateCarTmp = totalCreateCarTmp - totalDrawdown;
					}else{
						totalCreateCarTmp = totalCreateCarTmp - totalFinalCreditExpiry;
					}
					appM.setCreateCarAmount(totalCreateCarTmp);
					result.append("</table>");
					log.debug(">> result : " + result.toString()+":"+ORIGDisplayFormatUtil.displayTwoDigitsNumber(totalDrawdown));
					resp.setContentType("text/xml;charset=UTF-8");
					resp.setHeader("Cache-Control", "no-cache");
					PrintWriter pw = resp.getWriter();			
					pw.write(result.toString()+":"+ORIGDisplayFormatUtil.displayTwoDigitsNumber(totalDrawdown));
					pw.close();
				}else{
					result.append("FAIL");
					resp.setContentType("text/xml;charset=UTF-8");
					resp.setHeader("Cache-Control", "no-cache");
					PrintWriter pw = resp.getWriter();			
					pw.write(result.toString());
					pw.close();
				}
			
				log.debug("Size of car detail:"+vehicleVtTemp.size());
			    req.getSession().setAttribute("VEHICLE_RESULT",vehicleVtTemp);
				
			
		    }catch (Exception e) {
		        log.error("exception ",e);
            }
		
		log.debug("End RewriteCarDetailServlet");
		
	}

}