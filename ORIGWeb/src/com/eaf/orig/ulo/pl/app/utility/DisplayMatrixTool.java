package com.eaf.orig.ulo.pl.app.utility;

import java.math.BigDecimal;
//import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.cache.KeySubForm;
import com.eaf.cache.SubFormCache;
//import com.eaf.cache.TableLookupCache;
import com.eaf.orig.cache.properties.SubFormDisplayModeProperties;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.cache.data.MatrixCache;
import com.eaf.xrules.cache.model.MTDisplayDetailDataM;
import com.eaf.xrules.cache.model.MTDisplayGroupDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDebtBdDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXrulesNplLpmDataM;

public class DisplayMatrixTool{
	Logger logger = Logger.getLogger(this.getClass());
	public class Constant{
		public static final String POSITION_FULL = "FULL";
		public static final String POSITION_NORMAL = "NORMAL";
		public static final String POSITION_MANUAL = "MANUAL";
		public static final String FIELD_TYPE_SERVICE = "SERVICE";
		public static final String FILED_TYPE_HEADER = "HEADER";
		public static final String FILED_TYPE_BLANK = "BLANK";
		public static final String FILED_TYPE_BUTTION = "BUTTON";
		public static final String POPUP_TYPE_RESULT = "RESULT";
		public static final String POPUP_TYPE_BUTTON = "BUTTON";
		public static final String DISPLAY_TYPE_TEXTBOX ="TEXTBOX";
		public static final String DISPLAY_TYPE_BUTTON_TEXT ="BUTTON_TEXT";
		public static final String DISPLAY_TYPE_MANUAL = "MANUAL";
		public static final String DISPLAY_TYPE_SELECTBOX = "SELECTBOX";
		public static final String DISPLAY_MODE_VIEW = "VIEW";
	}
	public static String doubleQuote = "\"";	
//	public String DisplayMatrix(XrulesRequestDataM requestM,PLApplicationDataM appM,HttpServletRequest request ,String searchType,String formID){
//		StringBuilder str = new StringBuilder();
//		XrulesCacheTool cache = new XrulesCacheTool();		
//		Vector<MTDisplayGroupDataM> vect = cache.GetMatrixDisplay(requestM);
//			for(MTDisplayGroupDataM groupM : vect) {
//				str.append("<div><fieldset class=\"Fieldset\"><legend>").append(DisplayGroupLabel(groupM.getGroupLabel(), request)).append("</legend>");
//					str.append(this.DisplayMatrixDetail(groupM.getDisplayVect(),appM, request,searchType,formID));
//				str.append("</fieldset></div>");
//			}	
//		return str.toString();
//	}
	public String DisplayMatrix(PLApplicationDataM applicationM,HttpServletRequest request ,String searchType,String formID){
		StringBuilder str = new StringBuilder("");
		Vector<MTDisplayGroupDataM> vect = MatrixCache.getMatrixDisplay().get(applicationM.getMatrixServiceID());
		if(null != vect){
			for(MTDisplayGroupDataM groupM : vect) {
				str.append("<div><fieldset class=\"Fieldset\"><legend>").append(DisplayGroupLabel(groupM.getGroupLabel(), request)).append("</legend>");
					str.append(this.DisplayMatrixDetail(groupM.getDisplayVect(),applicationM, request,searchType,formID));
				str.append("</fieldset></div>");
			}	
		}
		return str.toString();
	}
	private String DisplayMatrixDetail(Vector<MTDisplayDetailDataM> displayVect ,PLApplicationDataM appM , HttpServletRequest request,String searchType,String formID){
		if(null == displayVect) return "";
		StringBuilder str = new StringBuilder();
			str.append("<table class=\"FormFrame\">");
			str.append("<tr>").append("<td width=\"20%\"></td><td width=\"25%\"></td><td width=\"25%\"></td><td width=\"30%\"></td>").append("</tr>");
			for(MTDisplayDetailDataM detailM :displayVect){
				if(detailM.getDisplaySequence()%2 != 0) str.append("<tr>");				
					str.append(this.DisplayService(detailM,appM, request,searchType,formID));
				if(detailM.getDisplaySequence()%2 == 0) str.append("</tr>");				
			}
			str.append("<tr>").append("<td></td><td></td><td></td><td></td>").append("</tr>");
			str.append("</table>");
		return str.toString();
	}
	
	private String DisplayService(MTDisplayDetailDataM detailM,PLApplicationDataM appM, HttpServletRequest request,String searchType,String formID){
		StringBuilder str = new StringBuilder();
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		String displayMode = getDisplayMode("VERIFICATION_SUBFORM", userM.getRoles(), appM.getJobState(), formID, searchType);
		
		if(Constant.FIELD_TYPE_SERVICE.equals(detailM.getFieldType())){
			 if(Constant.POSITION_FULL.equals(detailM.getPosition())){
				 if(Constant.DISPLAY_TYPE_BUTTON_TEXT.equals(detailM.getDisplayType())){
					 str.append(this.DisplayFullServiceButtonField(detailM, appM, request ,displayMode));
				 }
				 if(Constant.DISPLAY_TYPE_MANUAL.equals(detailM.getDisplayType())){
					 str.append(this.DisplayManualService(detailM, appM,request,displayMode));
				 }
			 }
			 if(Constant.POSITION_NORMAL.equals(detailM.getPosition())){
				 if(Constant.DISPLAY_TYPE_TEXTBOX.equals(detailM.getDisplayType())){
					str.append("<td class=\"textR\">");
				 	str.append(this.DisplayServiceFieldName(detailM.getServiceID(), request));	
				 	str.append(" ").append(":").append(" ");
				 	str.append("</td>");
				 	str.append("<td class=\"inputL\">");				 
					str.append(this.DisplayServiceFieldValue(detailM,appM,request));
					str.append("</td>");
				 }
				 if(Constant.DISPLAY_TYPE_BUTTON_TEXT.equals(detailM.getDisplayType())){
					 str.append(this.DisplayServiceButtonText(detailM, appM, request));
				 }	
				 if(Constant.DISPLAY_TYPE_SELECTBOX.equals(detailM.getDisplayType())){
					str.append("<td class=\"textR\">");
				 	str.append(this.DisplayServiceFieldName(detailM.getServiceID(), request));	
				 	str.append(" ").append(":").append(" ");
				 	str.append("</td>");
				 	str.append("<td class=\"inputL\">");				 
				 	str.append(this.DisplaySelectBoxService(detailM, appM, request ,displayMode));
					str.append("</td>");					
				 }
			 }
			 if(Constant.POSITION_MANUAL.equals(detailM.getPosition())){
				 str.append("<td width=\"100%\">");
				 str.append(this.DisplayManualService(detailM, appM,request,displayMode));
				 str.append("</td>");
			 }
		}
		if(Constant.FILED_TYPE_HEADER.equals(detailM.getFieldType())){
			str.append("<td class=\"inputL\">");
			str.append("<b>");
			str.append(this.DisplayHeaderLabel(detailM.getSystem01(), request));
			str.append("</b>");
			str.append("</td>");
			str.append("<td class=\"inputL\">");
			str.append("</td>");
		}
		if(Constant.FILED_TYPE_BUTTION.equals(detailM.getFieldType())){
			if(Constant.POSITION_FULL.equals(detailM.getPosition())){
				str.append(this.DisplayButtonField(detailM, request,appM,displayMode));
			}
		}
		if(Constant.FILED_TYPE_BLANK.equals(detailM.getFieldType())){
			str.append("<td class=\"textR\">");
			str.append("</td>");
			str.append("<td class=\"textR\">");
			str.append("</td>");
		}
		return str.toString();
	}	
	private String DisplaySelectBoxService(MTDisplayDetailDataM detailM,PLApplicationDataM appM,HttpServletRequest request ,String displayMode){
		StringBuilder str = new StringBuilder();
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM plPersonalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = plPersonalM.getXrulesVerification();		
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			plPersonalM.setXrulesVerification(xrulesVerM);
		}
		String attrResult = "result_"+detailM.getServiceID();
		str.append("<table cellpadding=\"0\" cellspacing=\"0\">");
		str.append("<tr>");
			switch(detailM.getServiceID()){
				case PLXrulesConstant.ModuleService.RETROSPECITVE_POSITIVE:
					str.append("<td>");
					str.append(HTMLRenderUtil.displaySelectBox(appM.getBusinessClassId(), 90,xrulesVerM.getFinancialStatementLastCode(),attrResult,displayMode,""));
					str.append("</td>");
					break;
				case PLXrulesConstant.ModuleService.CIRCULATION:
					str.append("<td>");
					str.append(HTMLRenderUtil.displaySelectBox(appM.getBusinessClassId(), 51,xrulesVerM.getSaleVolumeCode(),attrResult,displayMode,""));
					str.append("</td>");
					break;
				default:
					break;
			}	
		str.append("</tr>");
		str.append("</table>");
		return str.toString();
	}
	private String DisplayManualService(MTDisplayDetailDataM detailM,PLApplicationDataM appM,HttpServletRequest request,String displayMode){
		StringBuilder str = new StringBuilder();
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM plPersonalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
//		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		PLXRulesVerificationResultDataM xrulesVerM = plPersonalM.getXrulesVerification();		
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			plPersonalM.setXrulesVerification(xrulesVerM);
		}
		BigDecimal zero = new BigDecimal("0.00");
		String attrResult = "result_"+detailM.getServiceID();		
		String attrRemark = "remark_"+detailM.getServiceID();		
		String styleTextBox = "textbox";
		
		if(HTMLRenderUtil.DISPLAY_MODE_VIEW.equals(displayMode)){
			styleTextBox = "textbox-readonly";
		}
				
		switch(detailM.getServiceID()){
			case PLXrulesConstant.ModuleService.INCOME_DEBT:					
					PLXRulesDebtBdDataM debtDataM = xrulesVerM.getXRulesDebtBdDataM();
					if(null == debtDataM){
						debtDataM = new PLXRulesDebtBdDataM();
						xrulesVerM.setXRulesDebtBdDataM(debtDataM);
					}					
					str.append("<table class=\"FormFrame\" id=\"table-incomedebt\">");
						str.append("<tr><td class=\"textL\" width=\"20%\"><b>");
						str.append(this.getTextDescription(request,"TOTAL_INCOME_MONTH"));
						str.append("</b></td><td class=\"inputL\" width=\"25%\"></td><td class=\"textR\" width=\"25%\"></td><td class=\"inputL\" width=\"30%\"></td></tr>");
						str.append("<tr><td class=\"textR\">");
						str.append(this.getTextDescription(request,"PLXRULES_REVENUE"));
						str.append("<div class='mandatory-box'>*</div>&nbsp;:&nbsp;</td>");
						str.append("<td class=\"inputL\">");
						str.append(HTMLRenderUtil.DisplayInputCurrency(debtDataM.getSalary(),displayMode,"########0.00","salary","textbox-currency","","12", false));
						str.append("</td>");
						str.append("<td class=\"textR\">");
						str.append(this.getTextDescription(request,"RETURNEDCHECKS"));
						str.append("&nbsp;:&nbsp;</td>");
						str.append("<td class=\"inputL\">");
						str.append(HTMLRenderUtil.DisplayInputNumber(debtDataM.getCheuqeReturn(),displayMode,"##0.00","0.00","100.00","cheuqe-return","textbox-number","","6",true));
						str.append("</td></tr>");
						
						str.append("<tr><td class=\"textR\">");					
						str.append(this.getTextDescription(request,"PLXRULES_CREDITREVENUE"));
						str.append("&nbsp;:&nbsp;</td>");
						str.append("<td class=\"inputL\">");
						str.append(HTMLRenderUtil.DisplayInputCurrency(debtDataM.getLoanIncome(),displayMode,"########0.00","loan-income","textbox-currency","","12", false));
						str.append("</td><td class=\"textR\"></td><td class=\"inputL\"></td></tr>");	
						
						str.append("<tr><td class=\"textR\">");
						str.append(this.getTextDescription(request,"OTHERREVENUE"));
						str.append("&nbsp;:&nbsp;</td>");
						str.append("<td class=\"inputL\">");
						str.append(HTMLRenderUtil.DisplayInputCurrency(debtDataM.getOtherIncome(),displayMode,"########0.00","other-income","textbox-currency","","12", false));
						str.append("</td>");
						
						String displayModeIncome = HTMLRenderUtil.DISPLAY_MODE_VIEW;
						String styleOtherIncomeRemark = "textbox-readonly";						
						if(null == debtDataM.getOtherIncome()){
							debtDataM.setOtherIncome(new BigDecimal("0.00"));
						}						
						if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayMode) &&null != debtDataM.getOtherIncome() && debtDataM.getOtherIncome().compareTo(zero) > 0){
							styleOtherIncomeRemark = "textbox";
							displayModeIncome = HTMLRenderUtil.DISPLAY_MODE_EDIT;
						}
								
						if(HTMLRenderUtil.DISPLAY_MODE_VIEW.equals(displayMode)){
							displayModeIncome = HTMLRenderUtil.DISPLAY_MODE_VIEW;
							styleOtherIncomeRemark = "textbox-readonly";
						}
						
						str.append("<td class=\"textR\">");
						str.append(this.getTextDescription(request,"OTHERREASON"));
						str.append("&nbsp;:&nbsp;</td>");
						str.append("<td class=\"inputL\" id = 'td-other-income-remark'>");
						str.append(HTMLRenderUtil.displayInputTagScriptAction(debtDataM.getOtherIncomeRemark(),displayModeIncome,"","other-income-remark",styleOtherIncomeRemark,"","200"));
						str.append("</td></tr>");
						
						str.append("<tr><td class=\"textR\">");
						str.append(this.getTextDescription(request,"SUMREVENUE"));
						str.append("&nbsp;:&nbsp;</td>");
						str.append("<td class=\"inputL\">");
						str.append(HTMLRenderUtil.DisplayInputCurrency(debtDataM.getTotalIncome(),HTMLRenderUtil.DISPLAY_MODE_VIEW,"########0.00","total-income","textbox-currency-view","","12", false));
						str.append("</td><td class=\"textR\"></td><td class=\"inputL\"></td></tr>");
						
						str.append("<tr><td class=\"textL\"><b>");
						str.append(this.getTextDescription(request,"TOTAL_DEBT_MONTH"));
						str.append("</b></td><td class=\"inputL\"></td><td class=\"textR\"></td><td class=\"inputL\"></td></tr>");
						
						str.append("<tr><td class=\"textR\">");
						str.append(this.getTextDescription(request,"INSTALLMENTNCB_CONSUMER"));
						str.append("</td>");
						str.append("<td class=\"inputL\">");
						str.append(HTMLRenderUtil.DisplayInputCurrency(debtDataM.getConsumerLoanDebtTotal(),HTMLRenderUtil.DISPLAY_MODE_VIEW,"########0.00","ncb-consumer","textbox-currency-view","","12", false));
						str.append("</td><td class=\"textR\"></td><td class=\"inputL\"></td></tr>");
						
						str.append("<tr><td class=\"textR\">");
						str.append(this.getTextDescription(request,"INSTALLMENTNCB_COMMERCIAL"));
						str.append("</td>");
						str.append("<td class=\"inputL\">");
						str.append(HTMLRenderUtil.DisplayInputCurrency(debtDataM.getCommercialLoanDebtTotal(),HTMLRenderUtil.DISPLAY_MODE_VIEW,"########0.00","ncb-commercial","textbox-currency-view","","12", false));
						str.append("</td><td class=\"textR\"></td><td class=\"inputL\"></td></tr>");
						
						str.append("<tr><td class=\"textR\">");
						str.append(this.getTextDescription(request,"OTHERINSTALLMENT"));
						str.append("&nbsp;:&nbsp;</td>");
						str.append("<td class=\"inputL\">");
						str.append(HTMLRenderUtil.DisplayInputCurrency(debtDataM.getOtherDebt(),displayMode,"########0.00","other-debt","textbox-currency","","12", false));
						str.append("</td>");
						str.append("<td class=\"textR\">");
						
						String styleOtherncbRemark = "textbox-readonly";						
						String displayModeOtherDebt = HTMLRenderUtil.DISPLAY_MODE_VIEW;	
						if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayMode) &&null != debtDataM.getOtherDebt() && debtDataM.getOtherDebt().compareTo(zero) > 0){
							styleOtherncbRemark = "textbox";
							displayModeOtherDebt = HTMLRenderUtil.DISPLAY_MODE_EDIT;
						}						
						
						if(HTMLRenderUtil.DISPLAY_MODE_VIEW.equals(displayMode)){
							displayModeOtherDebt = HTMLRenderUtil.DISPLAY_MODE_VIEW;
							styleOtherncbRemark = "textbox-readonly";
						}
						logger.debug("=================");
						logger.debug("Display mode other debt "+displayModeOtherDebt);
						logger.debug("=================");
						str.append(this.getTextDescription(request,"OTHERREASON"));
						str.append("&nbsp;:&nbsp;</td>");
						str.append("<td class=\"inputL\" id = 'td-other-debt-remark'>");
						str.append(HTMLRenderUtil.displayInputTagScriptAction(debtDataM.getOtherDebtRemark(),displayModeOtherDebt,"","other-debt-remark",styleOtherncbRemark,"","200"));
						str.append("</td></tr>");
						
						str.append("<tr><td class=\"textR\">");
						str.append(this.getTextDescription(request,"SUMINSTALLMENT"));
						str.append("&nbsp;:&nbsp;</td>");
						str.append("<td class=\"inputL\">");
						str.append(HTMLRenderUtil.DisplayInputCurrency(debtDataM.getTotalDebt(),HTMLRenderUtil.DISPLAY_MODE_VIEW,"########0.00","total-debt","textbox-currency-view","","12", false));
						str.append("</td><td class=\"textR\"></td><td class=\"inputL\"></td></tr>");
						str.append("<tr><td></td></tr>");
						
						str.append("<tr><td class=\"textR\">");
						str.append(this.getTextDescription(request,"DEBTBURDEN"));
						str.append("</td>");
						str.append("<td class=\"inputL\">");
						str.append(HTMLRenderUtil.DisplayInputNumber(debtDataM.getDebtBurdentScore(),HTMLRenderUtil.DISPLAY_MODE_VIEW,"##0.00","0.00","100.00",attrResult,"textbox-number-view","","6",true));
						str.append("</td><td class=\"textR\"></td><td class=\"inputL\"></td></tr>");						
						
						str.append("</table>");
					break;
			case PLXrulesConstant.ModuleService.TOT:	
					str.append("<td class=\"textR\">");
					str.append(this.DisplayServiceFieldName(detailM.getServiceID(), request));
					str.append("&nbsp;:&nbsp;</td>");
					str.append("<td class=\"inputL\">");
					str.append(HTMLRenderUtil.displaySelectBox(appM.getBusinessClassId(), 59,xrulesVerM.getWebTotCode(),attrResult,displayMode,""));
					str.append("</td>");
					str.append("<td class=\"textR\">");
					str.append(this.getTextDescription(request,"REMARK"));
					str.append("&nbsp;:&nbsp;</td>");
					str.append("<td class=\"inputL\">");
					str.append(HTMLRenderUtil.DisplayInputXrules(xrulesVerM.getWebTotRemark(),displayMode,attrRemark,styleTextBox,"200"));
					str.append("</td>");
					break;
			case PLXrulesConstant.ModuleService.SSO:
					str.append("<td class=\"textR\">");
					str.append(this.DisplayServiceFieldName(detailM.getServiceID(), request));
					str.append("&nbsp;:&nbsp;</td>");
					str.append("<td class=\"inputL\">");
					str.append(HTMLRenderUtil.displaySelectBox(appM.getBusinessClassId(),60,xrulesVerM.getWebSsoCode(),attrResult,displayMode,""));
					str.append("</td>");
					str.append("<td class=\"textR\">");
					str.append(this.getTextDescription(request,"REMARK"));
					str.append("&nbsp;:&nbsp;</td>");
					str.append("<td class=\"inputL\">");
					str.append(HTMLRenderUtil.DisplayInputXrules(xrulesVerM.getWebSsoRemark(),displayMode,attrRemark,styleTextBox,"200"));
					str.append("</td>");
					break;
			case PLXrulesConstant.ModuleService.RD:
					str.append("<td class=\"textR\">");
					str.append(this.DisplayServiceFieldName(detailM.getServiceID(), request));
					str.append("&nbsp;:&nbsp;</td>");
					str.append("<td class=\"inputL\">");
					str.append(HTMLRenderUtil.displaySelectBox(appM.getBusinessClassId(),60,xrulesVerM.getWebRdCode(),attrResult,displayMode,""));
					str.append("</td>");
					str.append("<td class=\"textR\">");
					str.append(this.getTextDescription(request,"REMARK"));
					str.append("&nbsp;:&nbsp;</td>");
					str.append("<td class=\"inputL\">");
					str.append(HTMLRenderUtil.DisplayInputXrules(xrulesVerM.getWebRdRemark(),displayMode,attrRemark,styleTextBox,"200"));
					str.append("</td>");
					break;
			case PLXrulesConstant.ModuleService.DSS:
					str.append("<td class=\"textR\">");
					str.append(this.DisplayServiceFieldName(detailM.getServiceID(), request));
					str.append("&nbsp;:&nbsp;</td>");
					str.append("<td class=\"inputL\">");
					str.append(HTMLRenderUtil.displaySelectBox(appM.getBusinessClassId(),60,xrulesVerM.getWebDssKbankCode(),attrResult,displayMode,""));
					str.append("</td>");
					str.append("<td class=\"textR\">");
					str.append(this.getTextDescription(request,"REMARK"));
					str.append("&nbsp;:&nbsp;</td>");
					str.append("<td class=\"inputL\">");
					str.append(HTMLRenderUtil.DisplayInputXrules(xrulesVerM.getWebDssKbankRemark(),displayMode,attrRemark,styleTextBox,"200"));
					str.append("</td>");
					break;
			case PLXrulesConstant.ModuleService.DELAY_DOC:
					str.append("<td class=\"textR\" colspan=\"1\">");
					str.append("</td>");
					str.append("<td class=\"textL\" colspan=\"3\">");
//					#septemwi comment change to FuNoTimeOutFlag
					str.append(HTMLRenderUtil.displayCheckBox(appM.getFuNoTimeOutFlag(),attrResult,HTMLRenderUtil.COMPARE_CHECKBOX_VALUE, displayMode,""));
					str.append(this.DisplayServiceFieldName(detailM.getServiceID(),request));
					str.append("</td>");
					break;
			default:
				break;
		}
		return str.toString();
	}
	private String DisplayServiceFieldName(int serviceID ,HttpServletRequest request){
		return this.getTextDescription(request,"SERVICE_NAME_"+String.valueOf(serviceID));
	}
	private String DisplayGroupLabel(String code ,HttpServletRequest request ){
		return this.getTextDescription(request,code);
	}
	private String DisplayHeaderLabel(String code ,HttpServletRequest request ){
		return this.getTextDescription(request,code);
	}
	private String DisplayButtonField(MTDisplayDetailDataM detailM,HttpServletRequest request ,PLApplicationDataM appM,String displayMode){
		StringBuilder str = new StringBuilder("");
		String attrButton = "button_"+detailM.getServiceID();
		String buttonName = this.getTextDescription(request, "BUTTION_NAME_"+detailM.getServiceID());
		String attrEdit = "edit_"+detailM.getServiceID();
		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();
		String buttonStyle = "button";
		switch(detailM.getServiceID()){
			case PLXrulesConstant.ModuleService.BUTTON_EXECUTE1:
				str.append("<td width=\"100%\" colspan=\"4\" class=\"textC\">");
				str.append("<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
				str.append("<tr>");	
					str.append("<td class=\"textR\" width=\"50%\">");
					buttonStyle = xrulesTool.ButtonStyleEngine(attrButton, appM);
					str.append(HTMLRenderUtil.DisplayButton(attrButton, buttonName, displayMode, buttonStyle));
					str.append("</td>");
					str.append("<td class=\"textL\" width=\"40%\">");	
					str.append("&nbsp;");
					if(Constant.POPUP_TYPE_BUTTON.equals(detailM.getPopupType())){
						String buttonStyleEdit = xrulesTool.ButtonEditStyleEngie(appM);
						str.append(HTMLRenderUtil.DisplayButton(attrEdit, "Edit", HTMLRenderUtil.DISPLAY_MODE_EDIT, buttonStyleEdit));
					}				
					str.append("</td>");
					str.append("<td class=\"textR\" width=\"10%\">");
//						buttonStyle = xrulesTool.ButtonStyleEngine(attrButton, appM);
						str.append(HTMLRenderUtil.DisplayButton("reset_execute1", "Reset", displayMode, "button-reset"));
					str.append("</td>");
				str.append("</tr>");
				str.append("</table>");
				str.append("</td>");
				break;
			case PLXrulesConstant.ModuleService.BUTTION_EXECUTE2:
				str.append("<td class=\"textC\" colspan=\"4\">");
				buttonStyle = xrulesTool.ButtonStyleEngine(attrButton, appM);
				str.append(HTMLRenderUtil.DisplayButton(attrButton, buttonName, displayMode, buttonStyle));
				str.append("</td>");
				break;
			default:
				break;
		}
		return str.toString();
	}
	private String DisplayServiceButtonText(MTDisplayDetailDataM detailM,PLApplicationDataM appM, HttpServletRequest request){
			StringBuilder str = new StringBuilder();
			if(null == appM) appM = new PLApplicationDataM();
			PLPersonalInfoDataM plPersonalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			PLXRulesVerificationResultDataM xrulesVerM = plPersonalM.getXrulesVerification();		
			if(null == xrulesVerM){
				xrulesVerM = new PLXRulesVerificationResultDataM();
				plPersonalM.setXrulesVerification(xrulesVerM);
			}
			String attrResult = "result_"+detailM.getServiceID();
			String attrCode = "code_"+detailM.getServiceID();
			String attrButton = "button_"+detailM.getServiceID();
			String buttonName = this.getTextDescription(request, "BUTTION_NAME_"+detailM.getServiceID());
						
			switch(detailM.getServiceID()){
				case PLXrulesConstant.ModuleService.VERIFY_CUSTOMER:
					 str.append("<td class=\"textR\">");
					 str.append(HTMLRenderUtil.DisplayButton(attrButton, buttonName, HTMLRenderUtil.DISPLAY_MODE_EDIT, "button"));
					 str.append("</td>");
					 str.append("<td class=\"textL\">");
					 str.append("<table cellpadding=\"0\" cellspacing=\"0\">");
						str.append("<tr>");
							str.append("<td>");
							str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getVerCusResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
											,ORIGLogic.LogicColorStyleResult(xrulesVerM.getVerCusResultCode(), xrulesVerM.getVerCusResult()),""));
							str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getVerCusResultCode()),attrCode));
							str.append("</td>");
						str.append("</tr>");
					str.append("</table>");
					str.append("</td>");
					break;
				case PLXrulesConstant.ModuleService.VERIFY_HR:
					 str.append("<td class=\"textR\">");
					 str.append(HTMLRenderUtil.DisplayButton(attrButton, buttonName, HTMLRenderUtil.DISPLAY_MODE_EDIT, "button"));
					 str.append("</td>");
					 str.append("<td class=\"textL\">");
					 str.append("<table cellpadding=\"0\" cellspacing=\"0\">");
						str.append("<tr>");
							str.append("<td>");
							str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getVerHRResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
									,ORIGLogic.LogicColorStyleResult(xrulesVerM.getVerHRResultCode(), xrulesVerM.getVerHRResult()),""));
							str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getVerHRResultCode()),attrCode));
							str.append("</td>");
						str.append("</tr>");
					str.append("</table>");
					str.append("</td>");
					break;
				default:
					break;
			}
		return str.toString();
	}
	public String NcbStyleTextBox(String ncbCode){
		if(OrigUtil.isEmptyString(ncbCode) 
			|| PLXrulesConstant.ResultCode.CODE_NCB_IMG.equals(ncbCode)
				||PLXrulesConstant.ResultCode.CODE_SEND_BACK.equals(ncbCode) ){
			return "verify-textbox-ncb-write";
		}
		if(PLXrulesConstant.ResultCode.CODE_PASS.equals(ncbCode)){
			return "verify-textbox-ncb-green";
		}
		if(PLXrulesConstant.ResultCode.CODE_PASS_OR.equals(ncbCode)||
				PLXrulesConstant.ResultCode.CODE_OVERRIDE.equals(ncbCode)){
			return "verify-textbox-ncb-yellow";
		}
		if(PLXrulesConstant.ResultCode.CODE_ASK_NCB.equals(ncbCode)){
			return "verify-textbox-ncb-orange";
		}
		if(PLXrulesConstant.ResultCode.CODE_FAIL.equals(ncbCode)){
			return "verify-textbox-ncb-red";
		}
		return "verify-textbox-ncb-write";
	}
	public String NcbResult(String ncbCode ,String ncbResult,String ncbComment){
		StringBuilder str = new StringBuilder();
		if(PLXrulesConstant.ResultCode.CODE_PASS.equals(ncbCode)
				||PLXrulesConstant.ResultCode.CODE_FAIL.equals(ncbCode)){
			str.append(HTMLRenderUtil.replaceNull(ncbResult));
			if(!OrigUtil.isEmptyString(ncbComment)){
				str.append(" (");
				str.append(HTMLRenderUtil.replaceNull(ncbComment));
				str.append(")");
			}
		}else{
			str.append(HTMLRenderUtil.replaceNull(ncbResult));
		}
		return str.toString();
	}
	private String DisplayFullServiceButtonField(MTDisplayDetailDataM detailM,PLApplicationDataM appM, HttpServletRequest request ,String displayMode){
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		String role = userM.getCurrentRole();		
		
		StringBuilder str = new StringBuilder("");		
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM plPersonalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = plPersonalM.getXrulesVerification();		
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();	
			plPersonalM.setXrulesVerification(xrulesVerM);
		}
		String attrResult = "result_"+detailM.getServiceID();
		String attrCode = "code_"+detailM.getServiceID();
		String attrPopup = "popup_"+detailM.getServiceID();
		String attrButton = "button_"+detailM.getServiceID();
		String buttonName = this.getTextDescription(request, "BUTTION_NAME_"+detailM.getServiceID());
		String ncbStyleTextbox = null;
		String ncbResult = null;
		switch(detailM.getServiceID()){
			case PLXrulesConstant.ModuleService.REQUEST_NCB_FICO:
				 ncbStyleTextbox = this.NcbStyleTextBox(xrulesVerM.getNCBCode());
				 ncbResult = this.NcbResult(xrulesVerM.getNCBCode(), xrulesVerM.getNCBResult(), xrulesVerM.getNcbSupComment());
				 str.append("<td class=\"textR\">");
				 str.append(HTMLRenderUtil.DisplayButton(attrButton, buttonName, HTMLRenderUtil.DISPLAY_MODE_EDIT, "button"));
				 str.append("</td>");
				 str.append("<td class=\"textL\" colspan=\"3\">");
					 str.append("<table cellpadding=\"0\" cellspacing=\"0\">");
						str.append("<tr>");
							str.append("<td>");
							str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(ncbResult),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult,ncbStyleTextbox,""));
							str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getNCBCode()),attrCode));
							str.append("</td>");
							if(Constant.POPUP_TYPE_RESULT.equals(detailM.getPopupType())){
								str.append("<td>");
								str.append("&nbsp;");
								str.append(HTMLRenderUtil.DisplayIconResult(attrPopup));
								str.append("</td>");
							}
						str.append("</tr>");
					str.append("</table>");
				 str.append("</td>");
				break;
			case PLXrulesConstant.ModuleService.RETRIEVE_OLD_NCB:
				 ncbStyleTextbox = this.NcbStyleTextBox(xrulesVerM.getNCBCode());
				 ncbResult = this.NcbResult(xrulesVerM.getNCBCode(), xrulesVerM.getNCBResult(), xrulesVerM.getNcbSupComment());
				 str.append("<td class=\"textR\">");
				 str.append(HTMLRenderUtil.DisplayButton(attrButton, buttonName,displayMode, "button"));
				 str.append("</td>");
				 str.append("<td class=\"textL\" colspan=\"3\">");
					 str.append("<table cellpadding=\"0\" cellspacing=\"0\">");
						str.append("<tr>");
							str.append("<td>");
							str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(ncbResult),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult,ncbStyleTextbox,""));
							str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getNCBCode()),attrCode));
							str.append("</td>");
							if(Constant.POPUP_TYPE_RESULT.equals(detailM.getPopupType())){
								str.append("<td>");
								str.append("&nbsp;");
								str.append(HTMLRenderUtil.DisplayIconResult(attrPopup));
								str.append("</td>");
							}
						str.append("</tr>");
					str.append("</table>");
				 str.append("</td>");
				break;
			case PLXrulesConstant.ModuleService.WAIVED_NCB:				
				 xrulesVerM.setNCBCode(PLXrulesConstant.ResultCode.CODE_WAIVED);
				 xrulesVerM.setNCBResult(PLXrulesConstant.ResultDesc.CODE_WAIVED_DESC);
				 xrulesVerM.setNcbFicoCode(PLXrulesConstant.ResultCode.CODE_WAIVED);
				 xrulesVerM.setNcbFicoResult(PLXrulesConstant.ResultDesc.CODE_WAIVED_DESC);	
				 ncbStyleTextbox = this.NcbStyleTextBox(xrulesVerM.getNCBCode());
				 ncbResult = this.NcbResult(xrulesVerM.getNCBCode(), xrulesVerM.getNCBResult(), xrulesVerM.getNcbSupComment());
				 str.append("<td class=\"textR\">");
				 str.append(HTMLRenderUtil.DisplayButton(attrButton, buttonName, HTMLRenderUtil.DISPLAY_MODE_EDIT, "button"));
				 str.append("</td>");
				 str.append("<td class=\"textL\" colspan=\"3\">");
					 str.append("<table cellpadding=\"0\" cellspacing=\"0\">");
						str.append("<tr>");
							str.append("<td>");
							str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(ncbResult),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult,ncbStyleTextbox,""));
							str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getNCBCode()),attrCode));
							str.append("</td>");
							if(Constant.POPUP_TYPE_RESULT.equals(detailM.getPopupType())){
								str.append("<td>");
								str.append("&nbsp;");
								str.append(HTMLRenderUtil.DisplayIconResult(attrPopup));
								str.append("</td>");
							}
						str.append("</tr>");
					str.append("</table>");
				 str.append("</td>");
				break;
			case PLXrulesConstant.ModuleService.DOC_LIST:
				String docResult = "";
				if(OrigConstant.ROLE_DC.equals(role)){
					docResult = appM.getDocListResult();
					if(!OrigUtil.isEmptyString(docResult) && docResult.indexOf(OrigConstant.ROLE_DE)>0){
						appM.setTempDocList(appM.getDocListResult());
						appM.setTempDocListCode(appM.getDocListResultCode());
						appM.setDocListResult("");
						appM.setDocListResultCode("");
					}
				}
				
				 str.append("<td class=\"textR\">");
				 str.append(HTMLRenderUtil.DisplayButton(attrButton, buttonName, HTMLRenderUtil.DISPLAY_MODE_EDIT, "button"));
				 str.append("</td>");
				 str.append("<td class=\"textL\" colspan=\"3\">");
				 str.append("<table cellpadding=\"0\" cellspacing=\"0\">");
					str.append("<tr>");
						str.append("<td>");
						str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(appM.getDocListResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult,"verify-textbox-b-readonly",""));
						str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(appM.getDocListResultCode()),attrCode));
						str.append("</td>");
						str.append("<td>");	
						str.append("&nbsp;");
						if(Constant.POPUP_TYPE_BUTTON.equals(detailM.getPopupType())){
							str.append(HTMLRenderUtil.DisplayButton("follow_detail", "Follow Details", HTMLRenderUtil.DISPLAY_MODE_EDIT, "button"));
						}				
						str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(appM.getSmsFollowUp()),"sms-followup"));
						str.append("</td>");
					str.append("</tr>");
				str.append("</table>");	
				 str.append("</td>");
				 break;
			default:
				break;
		}
		
		return str.toString();
	}
	private String DisplayServiceFieldValue(MTDisplayDetailDataM detailM,PLApplicationDataM appM, HttpServletRequest request){
		StringBuilder str = new StringBuilder("");		
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM plPersonalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = plPersonalM.getXrulesVerification();		
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();	
			plPersonalM.setXrulesVerification(xrulesVerM);
		}
		PLXrulesNplLpmDataM nplM = xrulesVerM.getxRulesNplLpmM();
		if(null == nplM) nplM = new PLXrulesNplLpmDataM();
		String attrResult = "result_"+detailM.getServiceID();
		String attrCode = "code_"+detailM.getServiceID();
		String attrPopup = "popup_"+detailM.getServiceID();
		str.append("<table cellpadding=\"0\" cellspacing=\"0\">");
		str.append("<tr>");
			switch(detailM.getServiceID()){
				case PLXrulesConstant.ModuleService.SPECIAL_CUSTOMER:
					str.append("<td class=\"inputL\">");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getSpecialCusResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
												,ORIGLogic.LogicColorStyleResult(xrulesVerM.getSpecialCusCode(), xrulesVerM.getSpecialCusResult()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getSpecialCusCode()),attrCode));	
					str.append("</td>");
					break;
				case PLXrulesConstant.ModuleService.DEMOGRAPHIC:
					str.append("<td class=\"inputL\">");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getDemographicResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
												,ORIGLogic.LogicColorStyleResult(xrulesVerM.getDemographicCode(), xrulesVerM.getDemographicResult()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getDemographicCode()),attrCode));	
					str.append("</td>");
					break;
				case PLXrulesConstant.ModuleService.DEPLICATE_APPLICATION:
					str.append("<td>");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getDedupResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
												,ORIGLogic.LogicColorStyleResult(xrulesVerM.getDedupCode(), xrulesVerM.getDedupResult()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getDedupCode()),attrCode));
					str.append("</td>");					
					if(Constant.POPUP_TYPE_RESULT.equals(detailM.getPopupType())){
						str.append("<td>");
						str.append("&nbsp;");
						str.append(HTMLRenderUtil.DisplayIconResult(attrPopup));
						str.append("</td>");
					}					
					break;
				case PLXrulesConstant.ModuleService.EXISTING_KEC:
					str.append("<td>");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getExistCustResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
												,ORIGLogic.LogicColorStyleResult(xrulesVerM.getExistCustCode(), xrulesVerM.getExistCustResult()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getExistCustCode()),attrCode));
					str.append("</td>");					
					if(Constant.POPUP_TYPE_RESULT.equals(detailM.getPopupType())){
						str.append("<td>");
						str.append("&nbsp;");
						str.append(HTMLRenderUtil.DisplayIconResult(attrPopup));
						str.append("</td>");
					}					
					break;
				case PLXrulesConstant.ModuleService.CREDIT_CARD_BLOCKCODE:
					str.append("<td>");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getBlockCodeccResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
												,ORIGLogic.LogicColorStyleResult(xrulesVerM.getBlockCodeccCode(), xrulesVerM.getBlockCodeccResult()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getBlockCodeccCode()),attrCode));
					str.append("</td>");
					break;
				case PLXrulesConstant.ModuleService.KEC_BLOCKCODE:
					str.append("<td>");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getBlockCodeKecResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
											,ORIGLogic.LogicColorStyleResult(xrulesVerM.getBlockCodeKecCode(), xrulesVerM.getBlockCodeKecResult()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getBlockCodeKecCode()),attrCode));
					str.append("</td>");
					break;
				case PLXrulesConstant.ModuleService.NPL_LPM:
					str.append("<td>");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getNplLpmResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
											,ORIGLogic.LogicColorStyleResult(xrulesVerM.getNplLpmCode(), xrulesVerM.getNplLpmResult()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getNplLpmCode()),attrCode));
					str.append("</td>");
					if(Constant.POPUP_TYPE_RESULT.equals(detailM.getPopupType())){
						str.append("<td>");
						str.append("&nbsp;");
						str.append(HTMLRenderUtil.DisplayIconResult(attrPopup));
						str.append("</td>");
					}	
					break;
				case PLXrulesConstant.ModuleService.CLASSIFY_LEVEL_LPM:
					str.append("<td>");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getLpmClassifyLvResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
											,ORIGLogic.LogicColorStyleResult(xrulesVerM.getLpmClassifyLvCode(), xrulesVerM.getLpmClassifyLvResult()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getLpmClassifyLvCode()),attrCode));
					str.append("</td>");
					break;
				case PLXrulesConstant.ModuleService.LAST_RESURUCTURE_DATE_LPM:
					str.append("<td ").append(" id=").append("'").append(attrResult).append("' >");
					str.append(DataFormatUtility.DateEnToStringDateTh(nplM.getLastRestructureDate(), DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S));
					str.append("</td>");
					break;
				case PLXrulesConstant.ModuleService.STATUS_RESTRUCTURE_FLAG:
					str.append("<td ").append(" id=").append("'").append(attrResult).append("' >");
					str.append(HTMLRenderUtil.replaceNull(nplM.getStatusRestructureFlag()));
					str.append("</td>");
					break;
				case PLXrulesConstant.ModuleService.SP:
					str.append("<td>");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getSpResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
										,ORIGLogic.LogicColorStyleResult(xrulesVerM.getSpResultCode(), xrulesVerM.getSpResult()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getSpResultCode()),attrCode));
					str.append("</td>");
					break;
				case PLXrulesConstant.ModuleService.SANCTION_LIST:
					str.append("<td>");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getSanctionListResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
										,ORIGLogic.LogicColorStyleResult(xrulesVerM.getSanctionListResultCode(), xrulesVerM.getSanctionListResult()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getSanctionListResultCode()),attrCode));
					str.append("</td>");
					break;
				case PLXrulesConstant.ModuleService.BANKRUPTCY:
					str.append("<td>");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getBankruptcyResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
										,ORIGLogic.LogicColorStyleResult(xrulesVerM.getBankruptcyCode(), xrulesVerM.getBankruptcyResult()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getBankruptcyCode()),attrCode));
					str.append("</td>");
					break;
				case PLXrulesConstant.ModuleService.AMC_TAMC:
					str.append("<td>");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getAmcTamcResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
										,ORIGLogic.LogicColorStyleResult(xrulesVerM.getAmcTamcCode(), xrulesVerM.getAmcTamcResult()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getAmcTamcCode()),attrCode));
					str.append("</td>");
					break;
				case PLXrulesConstant.ModuleService.WATCHLIST_FRAUD:
					str.append("<td>");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getWatchListResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
										,ORIGLogic.LogicColorStyleResult(xrulesVerM.getWatchListCode(), xrulesVerM.getWatchListResult()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getWatchListCode()),attrCode));
					str.append("</td>");					
					if(Constant.POPUP_TYPE_RESULT.equals(detailM.getPopupType())){
						str.append("<td>");
						str.append("&nbsp;");
						str.append(HTMLRenderUtil.DisplayIconResult(attrPopup));
						str.append("</td>");
					}					
					break;
				case PLXrulesConstant.ModuleService.BEHAVIOR_RISK_GRADE:
					str.append("<td>");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getBehaviorRiskGradeResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
										,ORIGLogic.LogicColorStyleResult(xrulesVerM.getBehaviorRiskGradeCode(), xrulesVerM.getBehaviorRiskGradeResult()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getBehaviorRiskGradeCode()),attrCode));
					str.append("</td>");					
					if(Constant.POPUP_TYPE_RESULT.equals(detailM.getPopupType())){
						str.append("<td>");
						str.append("&nbsp;");
						str.append(HTMLRenderUtil.DisplayIconResult(attrPopup));
						str.append("</td>");
					}					
					break;
				case PLXrulesConstant.ModuleService.PAY_ROLL:
					str.append("<td>");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getPayrollResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
									,ORIGLogic.LogicColorStyleResult(xrulesVerM.getPayrollCode(), xrulesVerM.getPayrollResult()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getPayrollCode()),attrCode));
					str.append("</td>");					
					if(Constant.POPUP_TYPE_RESULT.equals(detailM.getPopupType())){
						str.append("<td>");
						str.append("&nbsp;");
						str.append(HTMLRenderUtil.DisplayIconResult(attrPopup));
						str.append("</td>");
					}					
					break;
				case PLXrulesConstant.ModuleService.KBANK_EMPLOYEE:
					str.append("<td>");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getkBankEmployeeResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
									,ORIGLogic.LogicColorStyleResult(xrulesVerM.getkBankEmployeeCode(), xrulesVerM.getkBankEmployeeResult()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getkBankEmployeeCode()),attrCode));
					str.append("</td>");
					break;	
				case PLXrulesConstant.ModuleService.LISTED_COMPANY:
					str.append("<td>");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getListedCompanyResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
								,ORIGLogic.LogicColorStyleResult(xrulesVerM.getListedCompanyCode(), xrulesVerM.getListedCompanyResult()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getListedCompanyCode()),attrCode));
					str.append("</td>");					
					if(Constant.POPUP_TYPE_RESULT.equals(detailM.getPopupType())){
						str.append("<td>");
						str.append("&nbsp;");
						str.append(HTMLRenderUtil.DisplayIconResult(attrPopup));
						str.append("</td>");
					}					
					break;
				case PLXrulesConstant.ModuleService.FRAUD_COMPANY:
					str.append("<td>");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getFraudCompanyResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
									,ORIGLogic.LogicColorStyleResult(xrulesVerM.getFraudCompanyCode(), xrulesVerM.getFraudCompanyResult()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getFraudCompanyCode()),attrCode));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getFraudCompanySearchType()),"fraud-comp-searchtype"));
					str.append("</td>");					
					if(Constant.POPUP_TYPE_RESULT.equals(detailM.getPopupType())){
						str.append("<td>");
						str.append("&nbsp;");
						str.append(HTMLRenderUtil.DisplayIconResult(attrPopup));
						str.append("</td>");
					}					
					break;
				case PLXrulesConstant.ModuleService.BANKRUPTCY_COMPANY:
					str.append("<td>");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getBankRuptcyCompanyResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
									,ORIGLogic.LogicColorStyleResult(xrulesVerM.getBankRuptcyCompanyCode(), xrulesVerM.getBankRuptcyCompanyResult()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getBankRuptcyCompanyCode()),attrCode));
					str.append("</td>");
					break;
				case PLXrulesConstant.ModuleService.BANKRUPTCY_COMPANY_FIRST:
					str.append("<td>");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getBankruptcyCompanyFirstResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
									,ORIGLogic.LogicColorStyleResult(xrulesVerM.getBankruptcyCompanyFirstCode(), xrulesVerM.getBankruptcyCompanyFirstResult()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getBankruptcyCompanyFirstCode()),attrCode));
					str.append("</td>");
					break;
				case PLXrulesConstant.ModuleService.AMC_TAMC_COMPANY:
					str.append("<td>");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getAmcTamcCompanyResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
									,ORIGLogic.LogicColorStyleResult(xrulesVerM.getAmcTamcCompanyCode(), xrulesVerM.getAmcTamcCompanyResult()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getAmcTamcCompanyCode()),attrCode));
					str.append("</td>");
					break;
				case PLXrulesConstant.ModuleService.AMC_TAMC_COMPANY_FIRST:
					str.append("<td>");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getAmctamcCompanyFirstResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
							,ORIGLogic.LogicColorStyleResult(xrulesVerM.getAmctamcCompanyFirstCode(), xrulesVerM.getAmctamcCompanyFirstResult()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getAmctamcCompanyFirstCode()),attrCode));
					str.append("</td>");
					break;
				case PLXrulesConstant.ModuleService.MONTH_PROFILE:
					str.append("<td>");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getMonthProfileResult()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
							,ORIGLogic.LogicColorStyleResult(xrulesVerM.getMonthProfileCode(), xrulesVerM.getMonthProfileResult()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getMonthProfileCode()),attrCode));
					str.append("</td>");
					break;
				case PLXrulesConstant.ModuleService.SOLO_VIEW:
					str.append("<td>");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getSoloViewResultDesc()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
							,ORIGLogic.LogicColorStyleResult(xrulesVerM.getSoloViewResultCode(), xrulesVerM.getSoloViewResultDesc()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getSoloViewResultCode()),attrCode));
					str.append("</td>");
					break;
				case PLXrulesConstant.ModuleService.LEC:
					str.append("<td>");
					str.append(HTMLRenderUtil.DisplayInputXrules(HTMLRenderUtil.replaceNull(xrulesVerM.getLecResultDesc()),HTMLRenderUtil.DISPLAY_MODE_VIEW,attrResult
							,ORIGLogic.LogicColorStyleResult(xrulesVerM.getLecResultCode(), xrulesVerM.getLecResultDesc()),""));
					str.append(HTMLRenderUtil.displayHiddenField(HTMLRenderUtil.replaceNull(xrulesVerM.getLecResultCode()),attrCode));
					str.append("</td>");
					break;
				default:
					break;
			}
		str.append("</tr>");
		str.append("</table>");
		return str.toString();
	}

	public String getTextDescription(HttpServletRequest request,String textCode){    
		Locale locale = Locale.getDefault();	
		Locale currentLocale = (Locale) (request.getSession().getAttribute("LOCALE"));		
		if (null != currentLocale) locale = currentLocale;
		ResourceBundle resource = ResourceBundle.getBundle(locale + "/properties/getMessage", locale);
		if (textCode != null && !textCode.equals("")) {
			try {
				return resource.getString(textCode);
			} catch (Exception e) {
				logger.error("Error "+e.getMessage());
			}
		}
		return "";
	}
	public String getDisplayMode(String subFormID, Vector roleIDs, String appJobState, String formID, String searchType) {
		if(!("Enquiry").equals(searchType)){
//			#septemwi comment change Logic get DisplayMode From >> SubFormCache.getDisplayMode()			
//			HashMap h = TableLookupCache.getCacheStructure();
//			Vector allDisplayMode = (Vector) (h.get("SubFormDisplayModeCacheDataM"));
//			for (int i = 0; i < allDisplayMode.size(); i++) {
//				SubFormDisplayModeProperties displayMode = (SubFormDisplayModeProperties) allDisplayMode.get(i);
//				String subFormIDInMode = displayMode.getSubFormID();
//				String roleID = displayMode.getRoleID();
//				String jobState = displayMode.getJobState();
//				String FormID = displayMode.getFormID();
//				if (null != formID && formID.equals(FormID)) { 
//					if (subFormID != null && subFormID.equals(subFormIDInMode)) { 
//						if (roleID != null && roleIDs.contains(roleID)) { 
//							if (appJobState != null && appJobState.trim().equals(jobState.trim())) {
//								return displayMode.getDisplayMode();
//								
//							} 
//						} 
//					} 
//				} 
//			}
			String roleID = getCurrentRole(roleIDs);
			String key = KeySubForm.getKeyDisplayMode(subFormID, roleID, appJobState, formID);
			if(null != key){
				SubFormDisplayModeProperties prop = SubFormCache.getDisplayMode().get(key);
				if(null != prop){
					return prop.getDisplayMode();
				}
			}
		}		
		return DisplayMatrixTool.Constant.DISPLAY_MODE_VIEW;
	}
	public String getCurrentRole(Vector<String> roleIDs){		
		if(roleIDs != null && roleIDs.size() > 0){
			return roleIDs.elementAt(0);
		}
		return "";
	}
	public String getMatrixServiceID(UserDetailM userM ,PLApplicationDataM applicationM,ProcessMenuM currentMenuM,String searchType){
		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();
		XrulesRequestDataM requestM = xrulesTool.MapDisplayXrulesRequestDataM(applicationM,null,userM,searchType ,currentMenuM.getMenuJobstate());	
		XrulesCacheTool cache = new XrulesCacheTool();
		String mtServiceID = cache.GetMatrixServiceID(requestM);
		logger.debug("getMatrixServiceID().."+mtServiceID);
		return mtServiceID;
	}
	
}
