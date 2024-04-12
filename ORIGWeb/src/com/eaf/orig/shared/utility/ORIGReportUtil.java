/*
 * Created on Nov 27, 2007
 *
 *  
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.utility;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.orig.master.ejb.OrigMasterManager;
import com.eaf.orig.master.shared.model.GeneralParamM;
import com.eaf.orig.shared.constant.OrigConstant;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
//import com.eaf.orig.shared.dao.OrigMasterDAOFactory;
//import com.eaf.orig.shared.dao.OrigMasterGenParamDAO;
//import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.service.ORIGEJBService;
//import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author Joe
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ORIGReportUtil extends HttpServlet {
	Logger log = Logger.getLogger(ORIGReportUtil.class);
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log.debug("***** ORIGReportUtil ******");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache,no-store");
		response.setDateHeader("Expires", 0);
		//get parameter value [id, param cretiria search]
//		Map params = new HashMap();
		Enumeration paramNames = request.getParameterNames();
		Map pr = new HashMap();
		
		while (paramNames.hasMoreElements()) {
			String index = (String) paramNames.nextElement();
			Object obj = (Object) request.getParameter(index);
			String value = "";
			
			if (obj instanceof String[]) {
            	String[] objStr = (String[])obj;
                value = objStr[0];
            } else if (obj instanceof String) {
               	String objStr = (String)obj;
                value = objStr;
          	} else {
                String objStr = String.valueOf(obj);
                value = objStr;
           	}		
		
		
			// change date calendar to string
			if (index.equals("f_start_date")) {
            	value = value.substring(6,10)+"/"+value.substring(3,5)+"/"+value.substring(0,2);
          	}
          	if (index.equals("f_end_date")) {
            	value = value.substring(6,10)+"/"+value.substring(3,5)+"/"+value.substring(0,2);
          	}
          	
          	if (index.equals("p_receive_start")) {
            	value = value.substring(6,10)+"/"+value.substring(3,5)+"/"+value.substring(0,2);
          	}
          	if (index.equals("p_receive_end")) {
            	value = value.substring(6,10)+"/"+value.substring(3,5)+"/"+value.substring(0,2);
          	}
          	if (index.equals("p_decision_start")) {
            	value = value.substring(6,10)+"/"+value.substring(3,5)+"/"+value.substring(0,2);
          	}
          	if (index.equals("p_decision_end")) {
            	value = value.substring(6,10)+"/"+value.substring(3,5)+"/"+value.substring(0,2);
          	}
          	
			pr.put(index, value);
		}
				
		System.out.println("Parameter >>>> "+pr); 
		
		// pass parameter value to crystal report	
//		OrigMasterGenParamDAO dao = OrigMasterDAOFactory.getOrigMasterGenParamDAO();
		
		
		// get parameter from database
		String report_type = (String)pr.get("report_type");
		String report_id = "";
//		String report_encry = "";
		GeneralParamM genparamM = new GeneralParamM();
		OrigMasterManager manager = ORIGEJBService.getOrigMasterManager();
//		UtilityDAO  utilDAO = ORIGDAOFactory.getUtilityDAO();
		if (report_type!=null && !"".equals(report_type)) {
			
			// get objectID 
			
			if ("rep_ca_letter".equals(report_type)) {
				report_type = "RPT_CA_LETTER"; 
			} 
			else if ("rep_ver_lst".equals(report_type)) {
				report_type = "RPT_VER_LST"; 
			}
			else if ("rep_consent".equals(report_type)) {
				report_type = "RPT_CONSENT"; 
			} else if ("rep_cmr_pfm".equals(report_type)) {
				report_type = "RPT_CMR_PFM"; 
			} else if ("rep_ca_pfm".equals(report_type)) {
				report_type = "RPT_CA_PFM"; 
			} else if ("rep_ca_sla".equals(report_type)) {
				report_type = "RPT_CA_SLA"; 				
			} else if ("rep_ca_decs".equals(report_type)) {
				report_type = "RPT_CA_DECIS"; 				
			} else if ("rep_over_rd".equals(report_type)) {
				report_type = "RPT_OVER_RIDE";				
			} else if ("rep_by_ltype".equals(report_type)) {
				report_type = "RPT_BY_LOAN"; 
			} else if ("rep_time_res".equals(report_type)) {
				report_type = "RPT_TIMES_RPS"; 
			} else if ("rep_cmr_app".equals(report_type)) {
				report_type = "RPT_CMR_APPE"; 
			} else if ("rep_ca_app".equals(report_type)) {
				report_type = "RPT_CA_APPE"; 				 
			} else if ("rep_pre_contrac".equals(report_type)) {
				report_type = "RPT_PRE_CONTRAC"; 
			} else if ("rep_pre_contrac_g".equals(report_type)) {
				report_type = "RPT_PRE_CONTRAC_G"; 
			} else if ("rep_pre_contrac_ver".equals(report_type)) {
				report_type = "RPT_PRE_CONTRAC_VER"; 
			} 													
			genparamM = manager.selectGenParamM(report_type,OrigConstant.BUSINESS_CLASS_AL);
			report_id = genparamM.getParamValue();
		}
		
		if (report_id==null || "".equals(report_id)) {
			report_id = "0";	
		}
		
		genparamM = manager.selectGenParamM("RPT_SERVERNAME",OrigConstant.BUSINESS_CLASS_AL);
		String serverName = genparamM.getParamValue();
		
		genparamM = manager.selectGenParamM("RPT_USERNAME",OrigConstant.BUSINESS_CLASS_AL);
		String apsuser = genparamM.getParamValue();
		
		genparamM = manager.selectGenParamM("RPT_PASSWORD",OrigConstant.BUSINESS_CLASS_AL);
		String apspassword = genparamM.getParamValue();
		
		genparamM = manager.selectGenParamM("RPT_AUTHTYPE",OrigConstant.BUSINESS_CLASS_AL);
		String apsauthtype = genparamM.getParamValue();
		
		System.out.println("------------------ Crystal (START) -----------------");
		System.out.println("report_type = "+report_type);
		System.out.println("report_id = "+report_id);
		System.out.println("serverName = "+serverName);
		System.out.println("apsuser = "+apsuser);
		System.out.println("apspassword = "+apspassword);
		System.out.println("apsauthtype = "+apsauthtype);
		System.out.println("------------------ Crystal (END) -----------------");
		
		String p_isauto = "0";
		String p_start_date = (String)pr.get("p_start_date");
		String p_end_date = (String)pr.get("p_end_date");
		String p_org_id = (String)pr.get("p_org_id");
		String p_stage_code = (String)pr.get("p_stage_code");		
		String p_status_1 = (String)pr.get("p_status_1");
		String p_status_2 = (String)pr.get("p_status_2");
		String p_status_3 = (String)pr.get("p_status_3");
		String p_status_4 = (String)pr.get("p_status_4");
		String p_status_5 = (String)pr.get("p_status_5");
		String p_status_6 = (String)pr.get("p_status_6");
		String p_status_7 = (String)pr.get("p_status_7");											
		String p_product_code = (String)pr.get("p_product_code");
		String p_dealer_code = (String)pr.get("p_dealer_code");	
		String p_sale_code = (String)pr.get("p_sale_code");			
		String p_channel_code = (String)pr.get("p_channel_code");
		String p_status = (String)pr.get("p_case_result");
		String p_card_type = (String)pr.get("p_card_type");
		String p_ca_id = (String)pr.get("p_ca_id");
		//-----------------Rep_K-leasing--------------------//
		String f_book_date = (String)pr.get("f_book_date");
		String t_book_date = (String)pr.get("t_book_date");
		String f_c_date = (String)pr.get("f_c_date");
		String t_c_date = (String)pr.get("t_c_date");
		String fcmr_code = (String)pr.get("fcmr_code");
		String tcmr_code = (String)pr.get("tcmr_code");
		String fca_code = (String)pr.get("fca_code");
		String tca_code = (String)pr.get("tca_code");
		String foffice_code = (String)pr.get("foffice_code");
		String toffice_code = (String)pr.get("toffice_code");
		String f_book_date_cre = (String)pr.get("f_book_date_cre");
		String t_book_date_cre = (String)pr.get("t_book_date_cre");
		String fca_code_cre = (String)pr.get("fca_code_cre");
		String tca_code_cre = (String)pr.get("tca_code_cre");
		String foffice_code_cre = (String)pr.get("foffice_code_cre");
		String toffice_code_cre = (String)pr.get("toffice_code_cre");
		String f_loan = (String)pr.get("f_loan");
		String t_loan = (String)pr.get("t_loan");
		String app_no = (String)pr.get("app_no");
		String com_fa = (String)pr.get("com_fa");
		String com_ins = (String)pr.get("com_ins");
		String print_user = (String)pr.get("print_user");
		String print_pos = (String)pr.get("print_pos");
		String print_tel = (String)pr.get("print_tel");
		String guarantor_id = (String)pr.get("guarantor_id");
		//-------------------------------------------//
		// --- Cancel
		String p_reson_code = (String)pr.get("p_reson_code");
		
		// --- Decision
		//String p_application_status = (String)pr.get("p_application_status");
		String p_action = (String)pr.get("p_action");
		String p_receive_start = (String)pr.get("p_receive_start");
		String p_receive_end = (String)pr.get("p_receive_end");
		String p_decision_start = (String)pr.get("p_decision_start");
		String p_decision_end = (String)pr.get("p_decision_end");
		String p_status_desc = "";
		
		// get all p_ca_id, p_reson_code from database
		if ("".equals(p_ca_id))
			p_ca_id = "All";
		if ("".equals(p_reson_code))
			p_reson_code = "All";
		if(p_status_1==null) {p_status_1 = "false";} else {p_status_1 = "true";p_status_desc=p_status_desc+"Approval Initial|Approval Once";}
		if(p_status_2==null) {p_status_2 = "false";} else {p_status_2 = "true";if(p_status_desc!="") p_status_desc=p_status_desc+"|";p_status_desc=p_status_desc+"Approval Latest|Approval Once";}
		if(p_status_3==null) {p_status_3 = "false";} else {p_status_3 = "true";if(p_status_desc!="") p_status_desc=p_status_desc+"|";p_status_desc=p_status_desc+"Rejected";}
		if(p_status_4==null) {p_status_4 = "false";} else {p_status_4 = "true";if(p_status_desc!="") p_status_desc=p_status_desc+"|";p_status_desc=p_status_desc+"Withdrawn";}
		if(p_status_5==null) {p_status_5 = "false";} else {p_status_5 = "true";if(p_status_desc!="") p_status_desc=p_status_desc+"|";p_status_desc=p_status_desc+"Pending";}
		if(p_status_6==null) {p_status_6 = "false";} else {p_status_6 = "true";if(p_status_desc!="") p_status_desc=p_status_desc+"|";p_status_desc=p_status_desc+"Escalate";}
		if(p_status_7==null) {p_status_7 = "false";} else {p_status_7 = "true";if(p_status_desc!="") p_status_desc=p_status_desc+"|";p_status_desc=p_status_desc+"Condition Approval";}
		if(p_status_desc=="") p_status_desc="all";
		
		System.out.println("------------------ Param (START) -----------------");
		System.out.println("p_is_auto = "+p_isauto);
		System.out.println("p_start_date = "+p_start_date);
		System.out.println("p_end_date = "+p_end_date);
		System.out.println("p_org_id = "+p_org_id);
		System.out.println("p_stage_code = "+p_stage_code);		
		System.out.println("p_status_1 = "+p_status_1);
		System.out.println("p_status_2= "+p_status_2);
		System.out.println("p_status_3= "+p_status_3);
		System.out.println("p_status_4= "+p_status_4);
		System.out.println("p_status_5= "+p_status_5);
		System.out.println("p_status_6= "+p_status_6);								
		System.out.println("p_status_7= "+p_status_7);		
		System.out.println("p_status_desc= "+p_status_desc);				
		System.out.println("p_product_code = "+p_product_code);
		System.out.println("p_dealer_code = "+p_dealer_code);
		System.out.println("p_sale_code = "+p_sale_code);		
		System.out.println("p_channel_code = "+p_channel_code);
		System.out.println("p_status = "+p_status);
		System.out.println("p_card_type = "+p_card_type);
		System.out.println("p_ca_id = "+p_ca_id);
		System.out.println("p_reson_code = "+p_reson_code);
		System.out.println("p_action = "+p_action);
		System.out.println("p_receive_start = "+p_receive_start);
		System.out.println("p_receive_end = "+p_receive_end);
		System.out.println("p_decision_start = "+p_decision_start);
		System.out.println("p_decision_end = "+p_decision_end);
		//K-rep//
		
		System.out.println("f_book_date = "+f_book_date);
		System.out.println("t_book_date = "+t_book_date);
		System.out.println("f_c_date = "+f_book_date);
		System.out.println("t_c_date = "+t_book_date);
		System.out.println("fcmr_code = "+fcmr_code);
		System.out.println("tcmr_code = "+tcmr_code);
		System.out.println("foffice_code = "+foffice_code);
		System.out.println("toffice_code = "+toffice_code);
		System.out.println("f_book_date_cre = "+f_book_date_cre);
		System.out.println("t_book_date_cre = "+t_book_date_cre);
		System.out.println("fca_code = "+fca_code);
		System.out.println("tca_code = "+tca_code);
		System.out.println("fca_code_cre = "+fca_code_cre);
		System.out.println("tca_code_cre = "+tca_code_cre);
		System.out.println("foffice_code_cre = "+foffice_code_cre);
		System.out.println("toffice_code_cre = "+toffice_code_cre);
		System.out.println("report_type = "+report_type);
		System.out.println("report_id = "+report_id);
		System.out.println("f_loan = "+f_loan);
		System.out.println("t_loan = "+t_loan);
		System.out.println("app_no = "+app_no);
		System.out.println("com_fa = "+com_fa);
		System.out.println("com_ins = "+com_ins);
		System.out.println("print_user = "+print_user);
		System.out.println("print_pos = "+print_pos);
		System.out.println("print_tel = "+print_tel);
		System.out.println("guarantor_id = "+guarantor_id);

		//-----k-----//
		System.out.println("------------------ Param (END) -----------------");
		 Date fromDate=null;
		 Date toDate=null;
		try {
              fromDate=ORIGUtility.parseThaiToEngDate(f_c_date);
              toDate=ORIGUtility.parseThaiToEngDate(t_c_date);
            if ("rep_ca_app".equals(report_type)) {
//			 utilDAO.beforeCallReportCAApp(fromDate,toDate); 		 
            	
            	PLORIGEJBService.getORIGDAOUtilLocal().beforeCallReportCAApp(fromDate, toDate);
			}
		    else if ("rep_ca_decs".equals(report_type)) {
//		       utilDAO.beforeCallReportCADecision(foffice_code,toffice_code,fca_code,tca_code,fromDate,toDate);		
		    	PLORIGEJBService.getORIGDAOUtilLocal().beforeCallReportCADecision(foffice_code, toffice_code, fca_code, tca_code, fromDate, toDate);
			}else if ("rep_over_rd".equals(report_type)) {
//			    utilDAO.beforeCallReportCAOveride(foffice_code,toffice_code,fca_code,tca_code,fromDate,toDate);		
				PLORIGEJBService.getORIGDAOUtilLocal().beforeCallReportCAOveride(foffice_code, toffice_code, fca_code, tca_code, fromDate, toDate);
	       }
        } catch (Exception e) {           
            e.printStackTrace();
        }
		
		
		// forward request dispatcher to crystal report
		
		ServletOutputStream out = response.getOutputStream();
		OutputStreamWriter osw = new OutputStreamWriter(out ,"UTF-8"); 
		osw.write("<HTML>");
		osw.write("<HEAD>"); 
		osw.write("<TITLE>KASIKORN LEASING ORIGINATION SYSTEM</TITLE>");
		osw.write("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
		osw.write("<HEAD>"); 
		osw.write("<form action='"+serverName+"' method='post' name='frmReport'>");
		osw.write("<input type='hidden' name='id' value='"+report_id+"'>");
		osw.write("<input type='hidden' name='apsuser' value='"+apsuser+"'>");
		osw.write("<input type='hidden' name='apspassword' value='"+apspassword+"'>");
		osw.write("<input type='hidden' name='apsauthtype' value='"+apsauthtype+"'>");
		//-------k-rep-------------//
		osw.write("<input type='hidden' name='promptex-foffice_code' value='"+foffice_code+"'>");
		osw.write("<input type='hidden' name='promptex-toffice_code' value='"+toffice_code+"'>");
		osw.write("<input type='hidden' name='promptex-fcmr_code' value='"+fcmr_code+"'>");
		osw.write("<input type='hidden' name='promptex-tcmr_code' value='"+tcmr_code+"'>");
		osw.write("<input type='hidden' name='promptex-fca_code' value='"+fca_code+"'>");
		osw.write("<input type='hidden' name='promptex-tca_code' value='"+tca_code+"'>");
		osw.write("<input type='hidden' name='promptex-f_book_date' value='"+f_book_date+"'>");
		osw.write("<input type='hidden' name='promptex-t_book_date' value='"+t_book_date+"'>");
		osw.write("<input type='hidden' name='promptex-f_c_date' value='"+f_c_date+"'>");
		osw.write("<input type='hidden' name='promptex-t_c_date' value='"+t_c_date+"'>");
		osw.write("<input type='hidden' name='promptex-foffice_code_cre' value='"+foffice_code+"'>");
		osw.write("<input type='hidden' name='promptex-toffice_code_cre' value='"+toffice_code+"'>");
		osw.write("<input type='hidden' name='promptex-fca_code_cre' value='"+fca_code+"'>");
		osw.write("<input type='hidden' name='promptex-tca_code_cre' value='"+tca_code+"'>");
		osw.write("<input type='hidden' name='promptex-f_book_date_cre' value='"+f_book_date+"'>");
		osw.write("<input type='hidden' name='promptex-t_book_date_cre' value='"+t_book_date+"'>");
		osw.write("<input type='hidden' name='promptex-f_loan' value='"+f_loan+"'>");
		osw.write("<input type='hidden' name='promptex-t_loan' value='"+t_loan+"'>");
		osw.write("<input type='hidden' name='promptex-app_no' value='"+app_no+"'>");
		osw.write("<input type='hidden' name='promptex-com_fa' value='"+com_fa+"'>");
		osw.write("<input type='hidden' name='promptex-com_ins' value='"+com_ins+"'>");
		osw.write("<input type='hidden' name='promptex-print_user' value='"+print_user+"'>");
		osw.write("<input type='hidden' name='promptex-print_pos' value='"+print_pos+"'>");
		osw.write("<input type='hidden' name='promptex-print_tel' value='"+print_tel+"'>");
		osw.write("<input type='hidden' name='promptex-guarantor_id' value='"+guarantor_id+"'>");
		
		
		//////-------k-rep-------////////
		
		
		//osw.write("<input type='hidden' name='promptex-param_application_status' value='"+p_application_status+"'>");
		osw.write("<input type='hidden' name='promptex-action' value='"+p_action+"'>");
		osw.write("<input type='hidden' name='promptex-start_receive' value='"+p_receive_start+"'>");
		osw.write("<input type='hidden' name='promptex-end_receive' value='"+p_receive_end+"'>");
		osw.write("<input type='hidden' name='promptex-start_decision' value='"+p_decision_start+"'>");
		osw.write("<input type='hidden' name='promptex-end_decision' value='"+p_decision_end+"'>");

		osw.write("</form>");
		
		osw.write("<script language='JavaScript'>");
		osw.write("top.window.moveTo(0,0);");
		osw.write("if (document.all) {");
		osw.write("top.window.resizeTo(screen.availWidth,screen.availHeight);");
		osw.write("}");
		osw.write("document.frmReport.submit();");
		osw.write("</script>");
		osw.write("</HTML>");
		osw.flush();
		out.flush();
	
	}
	
	public String paramVector2String(Vector vParam, int param) {
		String reStr = "";
		
		for (int i=0;i<vParam.size();i++) {
			if ((i+1)==vParam.size()) {
				reStr += (String)vParam.elementAt(i);
			} else {
				reStr += (String)vParam.elementAt(i) + "|";	
			}
		}
		
		log.debug("param = "+param+" : reStr = "+reStr);	
		return reStr;
}
}
