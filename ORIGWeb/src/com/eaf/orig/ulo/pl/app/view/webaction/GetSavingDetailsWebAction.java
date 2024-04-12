package com.eaf.orig.ulo.pl.app.view.webaction;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLFinancialInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class GetSavingDetailsWebAction extends WebActionHelper implements WebAction {

	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public Event toEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean requiredModelRequest() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean preModelRequest() {

		logger.debug("[GetSavingDetailsWebAction]...[preModelRequest]");

		PLOrigFormHandler plorigform = (PLOrigFormHandler) getRequest().getSession().getAttribute(PLOrigFormHandler.PLORIGForm);
		PLApplicationDataM plapplicationDataM = plorigform.getAppForm();

		if (plapplicationDataM == null) {
			plapplicationDataM = new PLApplicationDataM();
		}

		PLPersonalInfoDataM plPersonalInfoDataM = plapplicationDataM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);

		if (plPersonalInfoDataM == null) {
			plPersonalInfoDataM = new PLPersonalInfoDataM();
		}
		StringBuilder tableResult = new StringBuilder();
		StringBuilder tableSummary = new StringBuilder();
		JsonObjectUtil _JsonObjectUtil = new JsonObjectUtil();
		BigDecimal summary = new BigDecimal(0);
		String actionType = getRequest().getParameter("actionType");
		logger.debug("actionType=" + actionType);
		Vector<PLFinancialInfoDataM> vFinanceDetails = (Vector<PLFinancialInfoDataM>)getRequest().getSession().getAttribute("PL_SAVING_DETAIL");
		if ("save".equals(actionType)) {
             plPersonalInfoDataM.setFinancialInfoVect(vFinanceDetails);
          //  summary=DataFormatUtility.StringToBigDecimal(getRequest().getParameter("saving_summary"));
            //logger.debug(" summary="+summary);
            
             //set index
             for(int i=0;i<vFinanceDetails.size();i++){
            	  PLFinancialInfoDataM financeDetail=  vFinanceDetails.get(i);
            	  financeDetail.setSeq(i+1);
            	  summary = summary.add(financeDetail.getFinancialAmount());
             }          
             plPersonalInfoDataM.setSavingIncome(summary);
             try {
				_JsonObjectUtil.CreateJsonObject("saving_summary", DataFormatUtility.displayCommaNumber(summary));
			    _JsonObjectUtil.ResponseJsonArray(getResponse());
			} catch (IOException e) {				 
				e.printStackTrace();
			} catch (Exception e) {				 
				e.printStackTrace();
			}
             return true;
             
		} else if ("delete".equals(actionType)) {
			//get parameter value
			String[] deleteSaving=getRequest().getParameterValues("saving");
			logger.debug("delete="+deleteSaving);					
			if(deleteSaving!=null){
				for(int i=0;i<deleteSaving.length;i++){				
					logger.debug("deleteSaving[i])="+deleteSaving[i]);
					for(int j=vFinanceDetails.size()-1;j>=0 ; j--){
						PLFinancialInfoDataM plFinancialRemoveDataM=vFinanceDetails.get(j);
						//int index=
						if( ("saving_index_"+plFinancialRemoveDataM.getSeq()).equals(deleteSaving[i])){
						  logger.debug("remove "+deleteSaving[i])	;
						  vFinanceDetails.remove(j);
						  break;
						}
					}
					
				}
			}

		} else if ("add".equals(actionType)) {
			String financeType = getRequest().getParameter("sav_type");
			String sav_bankname = getRequest().getParameter("sav_bankname");
			String sav_fund = getRequest().getParameter("sav_fund");
			String sav_balance = getRequest().getParameter("sav_balance");
			
			logger.debug("[GetSavingDetailsWebAction]...[FinanceIndex]...[Parameter vFinanceDetails]" + vFinanceDetails.size());
			logger.debug("[GetSavingDetailsWebAction]...[FinanceIndex]...[Parameter financeType]" + financeType);
			logger.debug("[GetSavingDetailsWebAction]...[FinanceIndex]...[Parameter sav_bankname]" + sav_bankname);
			logger.debug("[GetSavingDetailsWebAction]...[FinanceIndex]...[Parameter sav_fund]" + sav_fund);
			logger.debug("[GetSavingDetailsWebAction]...[FinanceIndex]...[Parameter sav_balance]" + sav_balance);

			PLFinancialInfoDataM plFinancialDataM = new PLFinancialInfoDataM();
			plFinancialDataM.setFinanceIndex(vFinanceDetails.size());
			plFinancialDataM.setFinancialType(financeType);
			plFinancialDataM.setBank(sav_bankname);
			plFinancialDataM.setFinancialNo(sav_fund);
			plFinancialDataM.setFinancialAmount(DataFormatUtility.StringToBigDecimal(sav_balance));
			vFinanceDetails.add(plFinancialDataM);
		}
		
		if (ORIGUtility.isEmptyVector(vFinanceDetails)) {
			vFinanceDetails = new Vector<PLFinancialInfoDataM>();
			plPersonalInfoDataM.setFinancialInfoVect(vFinanceDetails);
		}


//		for (PLFinancialInfoDataM financeDetail : vFinanceDetails) {
//			logger.debug("0000000000000]" + plFinancialDataM.getFinancialAmount());
//
//		}

		

		

		



//		for (PLFinancialInfoDataM financeDetail : vFinanceDetails) {
//			summary = summary.add(financeDetail.getFinancialAmount());
//			logger.debug("[GetSavingDetailsWebAction]...[FinanceIndex]...[Parameter summary1]" + financeDetail.getFinancialAmount());
//			logger.debug("[GetSavingDetailsWebAction]...[FinanceIndex]...[Parameter summary2]" + summary);
//		}

		
		//plPersonalInfoDataM.setFinancialInfoVect(vFinanceDetails);
		
		// plPersonalInfoDataM.setFinanceVect(vFinanceDetails);

		try {
			tableResult.append("<table class=\"TableFrame\" id=\"savingresult\">");
			tableResult.append("<tr class=\"Header\">");
			tableResult.append("<td class=\"textR\" width=\"5%\" align='center'>" + HTMLRenderUtil.displayCheckBox("", "checkbox1", "all",HTMLRenderUtil.DISPLAY_MODE_EDIT ,"") + "</td>");
			tableResult.append("<td class=\"textR\" width=\"15%\">" + PLMessageResourceUtil.getTextDescription(getRequest(), "TYPE") + "</td>");
			tableResult.append("<td class=\"textR\" width=\"20%\">" + PLMessageResourceUtil.getTextDescription(getRequest(), "BANK") + "</td>");
			tableResult.append("<td class=\"textR\" width=\"20%\">" + PLMessageResourceUtil.getTextDescription(getRequest(), "FUND_ACCOUNT_NUMBER") + "</td>");
			tableResult.append("<td class=\"textR\" width=\"20%\">" + PLMessageResourceUtil.getTextDescription(getRequest(), "SAVING_AVG_CURRENT") + "</td>");
			tableResult.append("</tr>");
			if (vFinanceDetails.size() > 0) {
				for (int i = 0; i < vFinanceDetails.size(); i++) {
					PLFinancialInfoDataM financeDetail =vFinanceDetails.get(i);
					tableResult.append("<tr").append(" id=\"").append("saving_index_").append(financeDetail.getSeq()).append("\"").append(">");
					tableResult.append("<td class=\"inputL\"\" id=\"checkNo\" width='6%' align='center'>"
							+ HTMLRenderUtil.displayCheckBox("", "saving", "saving_index_"+financeDetail.getSeq(), HTMLRenderUtil.DISPLAY_MODE_EDIT,"") + "</td>");
					// tableResult.append("<td class=\"jobopening2\" id=\"saving_number\" width='10%' align='center'>"
					// +HTMLRenderUtil.displayHTML(DataFormatUtility.displayIntToString(plFinancialDataM.getFinanceIndex()))+"</td>");
					tableResult.append("<td class=\"inputL\" id=\"saving_type\" width='9%'>"
							+ HTMLRenderUtil.displayHTMLFieldIDDesc(financeDetail.getFinancialType(), 75) + "</td>");
					tableResult.append("<td class=\"inputL\" id=\"saving_bank\" width='15%'>"
							+ HTMLRenderUtil.displayHTMLFieldIDDesc(financeDetail.getBank(), 25) + "</td>");
					tableResult.append("<td class=\"inputL\" id=\"account_no\" width='26%'>"
							+ HTMLRenderUtil.displayHTML(financeDetail.getFinancialNo()) + "</td>");
					tableResult.append("<td  class=\"textR\"   id=\"saving_avg_current\">"
							+ HTMLRenderUtil.displayHTML(DataFormatUtility.displayCommaNumber(financeDetail.getFinancialAmount())) + "</td>");
					tableResult.append("</tr>");
					summary = summary.add(financeDetail.getFinancialAmount());
				}
			} else {

			}
			tableResult.append("</table>");

			tableSummary.append(DataFormatUtility.displayCommaNumber(summary));

			logger.debug("[GetSavingDetailsWebAction]...[summary]...[summary]" + summary);

			_JsonObjectUtil.CreateJsonObject("savingresult", tableResult.toString());
			_JsonObjectUtil.CreateJsonObject("saving_summary", tableSummary.toString());
			//_JsonObjectUtil.CreateJsonObject("h_saving_val", DataFormatUtility.displayCommaNumber(plPersonalInfoDataM.getSavingIncome()));

			_JsonObjectUtil.ResponseJsonArray(getResponse());
		} catch (Exception e1) {
			logger.fatal("Error ", e1);
		}

		return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.NOTFORWARD;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
    
	
	
}
