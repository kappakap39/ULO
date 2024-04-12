package com.eaf.orig.shared.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.MessageResourceUtil;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.LoanDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.VehicleDataM;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;

public class RewriteCarDetailServlet extends HttpServlet implements Servlet {
    Logger log = Logger.getLogger(RewriteCarDetailServlet.class);
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public RewriteCarDetailServlet() {
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
		log.debug("Start RewriteCarDetailServlet");
		try{
			ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
			ORIGFormHandler ORIGForm = (ORIGFormHandler) req.getSession().getAttribute("ORIGForm");	
			Vector vehicleVt = new Vector();
			VehicleDataM vehicleM = null;
			boolean matchFlag = false;
			double totalDrawdown = 0;
			double totalCreateCar = 0;
			
			ApplicationDataM appM = ORIGForm.getAppForm();
			if(appM == null){
			    appM = new ApplicationDataM();
			}
			Vector personalVt = appM.getPersonalInfoVect();
			PersonalInfoDataM personalDataM = (PersonalInfoDataM)personalVt.get(0);
			vehicleM = appM.getVehicleDataM();
			if(vehicleM==null){
			    vehicleM = new VehicleDataM();
			    }
			log.debug("Vehicle ID is:"+vehicleM.getVehicleID());
			String mode  = req.getParameter("mode");
			String vehicleIdToken  = req.getParameter("vehicleId");
			
			Vector vehicleVtTemp = (Vector)req.getSession().getAttribute("VEHICLE_RESULT");
			VehicleDataM vehicleMTemp = null;
			
			if(mode!=null&&mode.equals("Delete")){
			    //Delete car detail
			    StringTokenizer strToken  = new StringTokenizer(vehicleIdToken,",");
					while (strToken.hasMoreTokens()){
						String vehicleId = strToken.nextToken();
						log.debug("vehicleId for delete="+vehicleId);
						for(int i=0;i<vehicleVtTemp.size();i++){
				        	vehicleMTemp = (VehicleDataM)vehicleVtTemp.get(i);
							log.debug("vehicleMTemp ID is:"+vehicleMTemp.getVehicleID()); 	
							if(vehicleMTemp.getVehicleID()== Integer.parseInt(vehicleId)){
								log.debug("match for delete");
				        	    vehicleVtTemp.remove(i);
				        	    break;
				        	    }
						    }
					}
			    }else{
			        //Add car detail
			        if(vehicleVtTemp!=null&&vehicleVtTemp.size()>0){
					    for(int i=0;i<vehicleVtTemp.size();i++){
					        	vehicleMTemp = (VehicleDataM)vehicleVtTemp.get(i);
								log.debug("vehicleMTemp ID is:"+vehicleMTemp.getVehicleID());
					        	if(vehicleMTemp.getVehicleID()==vehicleM.getVehicleID()){
									log.debug("match");
					        	    vehicleVtTemp.set(i,vehicleM);
					        	    matchFlag = true;
					        	    break;
					        	    }
					        }
					    if(!matchFlag){
							log.debug("not match");
					        vehicleVtTemp.add(vehicleM);
					        }
					    
					    }else{
					        vehicleVtTemp = new Vector();
					        vehicleVtTemp.add(vehicleM);
					        }
			        }
					 
			StringBuffer result = new StringBuffer("");
			
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
				    ORIGUtility utility = new ORIGUtility();
				    for(int i=0;i<vehicleVtTemp.size();i++){
				        vehicleM = (VehicleDataM)vehicleVtTemp.get(i);
				        
				        category = cacheUtil.getORIGMasterDisplayNameDataM("CarCategoryType", vehicleM.getCategory());
				        brand = cacheUtil.getORIGMasterDisplayNameDataM("CarBrand", vehicleM.getBrand());
				        car = cacheUtil.getNaosCacheDisplayNameDataM(9, vehicleM.getCar());
				        gear = utility.getGearDesc(vehicleM.getGear());
				        
				        //get toatlCreateCar
				        LoanDataM loanDataM = null;
				        //if(vehicleM.getDrawDownStatus().equals("NEW")||vehicleM.getDrawDownStatus().equals("PROGRESS")){
				            loanDataM = vehicleM.getLoanDataM();
				            log.debug("loanDataM = "+loanDataM);
				            //totalDrawdown = totalDrawdown + loanDataM.getCostOfFinancialAmt().doubleValue();
				            //totalCreateCar = totalCreateCar + loanDataM.getCostOfFinancialAmt().doubleValue();
				        //}
				        totalCreateCarTmp = totalCreateCarTmp+loanDataM.getCostOfFinancialAmt().doubleValue();
				        result.append("<tr><td align=\"center\" class=\"gumaygrey2\"> ");
				        result.append("<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" align=\"center\" border=\"0\">");
				        result.append("<TR onmouseover=\"this.style.cursor='hand' ;this.style.background='#D7D6D7'\" onmouseout=\" this.style.background='#FFFFFF'\" >");
				        if(vehicleM.getDrawDownStatus().equals("NEW")){
				            result.append("<TD class=\"jobopening2\" width=\"5%\"><INPUT type=\"checkbox\" name=\"chkCar\" value=\""+vehicleM.getVehicleID()+"\"></TD>");
				        }else{
				            result.append("<TD class=\"jobopening2\" width=\"5%\"></TD>");
				        }
				        result.append("<TD class=\"jobopening2\" width=\"5%\">"+ORIGDisplayFormatUtil.displayRadioTagScriptAction(String.valueOf(vehicleM.getVehicleID()),ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT,"dupCarID","","","","")+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"5%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleM.getVehicleID()+",'edit')\">"+(i+1)+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"10%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(personalDataM.getClientGroup())+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"15%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(category)+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"15%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(brand)+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"10%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(car)+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"10%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(vehicleM.getChassisNo())+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"5%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(gear)+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"10%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(vehicleM.getDrawDownStatus())+"</TD>");
				        result.append("<TD class=\"jobopening2\" width=\"10%\" onClick=\"loadCarPopup('car detail','LoadCarDetailPopUp','1150','500','150','40',"+vehicleM.getVehicleID()+",'edit')\">"+ORIGDisplayFormatUtil.forHTMLTag(vehicleM.getContractNo())+"</TD>");
				        result.append("</TR>");
				        result.append("</table></td></tr>");
				        

						log.debug("getCategory is:"+vehicleM.getCategory());
						log.debug("getBrand is:"+vehicleM.getBrand());
						log.debug("getCar is:"+vehicleM.getCar());
						log.debug("getChassisNo is:"+vehicleM.getChassisNo());
						log.debug("getGear is:"+vehicleM.getGear());
				        }
				    }
				double totalFinalCreditExpiry = appM.getTotalFinalCreditExpiry();
				totalDrawdown = appM.getDrawDownAmount();
				if(totalDrawdown < totalFinalCreditExpiry){
					totalCreateCarTmp = totalCreateCarTmp - totalDrawdown;
				}else{
					totalCreateCarTmp = totalCreateCarTmp - totalFinalCreditExpiry;
				}
				appM.setCreateCarAmount(totalCreateCarTmp);
				log.debug("Size of car detail:"+vehicleVtTemp.size());
			    req.getSession().setAttribute("VEHICLE_RESULT",vehicleVtTemp);
			    
				result.append("</table>");
				log.debug(">> result : " + result.toString()+":"+ORIGDisplayFormatUtil.displayTwoDigitsNumber(totalCreateCarTmp));
				resp.setContentType("text/xml;charset=UTF-8");
				resp.setHeader("Cache-Control", "no-cache");
				PrintWriter pw = resp.getWriter();			
				pw.write(result.toString()+":"+ORIGDisplayFormatUtil.displayTwoDigitsNumber(totalCreateCarTmp));
				pw.close();
			
		    }catch (Exception e) {
		        log.error("exception ",e);
            }
		
		log.debug("End RewriteCarDetailServlet");
		
	}

}