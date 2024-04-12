package com.eaf.orig.ulo.pl.app.utility;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.model.app.PLAddressDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLBundleALDataM;
import com.eaf.orig.ulo.pl.model.app.PLBundleHLDataM;
import com.eaf.orig.ulo.pl.model.app.PLBundleSMEDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLSaleInfoDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDebtBdDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class SensitiveFiledManualEngine{
	static Logger logger = Logger.getLogger(SensitiveFiledManualEngine.class);
	public SensitiveFiledManualEngine(){
		super();
	}
	public class Field{
		public static final String kec_permit_cash = "kec_permit_cash";
		public static final String kec_cash_payment = "kec_cash_payment";
		public static final String kec_permit = "kec_permit";
		public static final String channel = "channel";
		public static final String salary = "salary";
		public static final String result_1031 = "result_1031";
		public static final String doccode = "doccode";
		public static final String editcisno = "edit-cis-no";
		public static final String editclassifylevel = "edit-classify-level";
		public static final String editccblockcode = "edit-cc-blockcode";
		public static final String editnpl = "edit-npl";
		public static final String editamctamc = "edit-amctamc";
		public static final String editbankruptcy = "edit-bankruptcy";
		public static final String editcccurrentbalance = "edit-cc-current-balance";
		public static final String editcclastpaymentdate = "edit-cc-lastpayment-date";
		public static final String editkeccurrentbalance = "edit-kec-current-balance";
		public static final String editkeclastpaymentdate = "edit-kec-lastpayment-date";
		public static final String loanincome = "loan-income";
		public static final String otherincome = "other-income";
		public static final String project_code = "project_code";
		public static final String result_1030 = "result_1030";
		public static final String result_1029 = "result_1029";
		public static final String fraudcomp_decision = "fraudcomp-decision";
		public static final String address_status = "address_status";
		public static final String totaldebt = "total-debt";
		public static final String bot5x_total = "bot5x_total";
		public static final String certification = "certification";
		public static final String address_object = "address_object";
		public static final String code_1019 = "code_1019";
	}
	public void ManualSensitive(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
											,JSONArray jsonArray ,List<String> sButton){
		logger.debug("ManualSensitive [attrName] "+attrName);		
		if(Field.kec_permit_cash.equals(attrName)){
			this.SensitiveFieldkec_permit_cash(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.kec_cash_payment.equals(attrName)){
			this.SensitiveFieldkec_cash_payment(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.kec_permit.equals(attrName)){
			this.SensitiveFieldkec_permit(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.channel.equals(attrName)){
			this.SensitiveFieldChannel(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.salary.equals(attrName)){
			this.SensitiveFieldSalary(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.result_1031.equals(attrName)){
			this.SensitiveFieldSaleVolume(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.doccode.equals(attrName)){
			this.SensitiveFieldDocCode(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.editcisno.equals(attrName)){
			this.SensitiveFieldCisNo(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.editclassifylevel.equals(attrName)){
			this.SensitiveFieldClassifyLevel(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.editccblockcode.equals(attrName)){
			this.SensitiveFieldCCBlockCode(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.editnpl.equals(attrName)){
			this.SensitiveFieldNPL(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.editamctamc.equals(attrName)){
			this.SensitiveFieldAmcTamc(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.editbankruptcy.equals(attrName)){
			this.SensitiveFieldBankruptcy(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.editcccurrentbalance.equals(attrName)){
			this.SensitiveFieldCCCurrentBalance(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.editcclastpaymentdate.equals(attrName)){
			this.SensitiveFieldCCLastPaymentDate(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.editkeccurrentbalance.equals(attrName)){
			this.SensitiveFieldKecCurrentBalance(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.editkeclastpaymentdate.equals(attrName)){
			this.SensitiveFieldKecLastPaymentDate(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.loanincome.equals(attrName)){
			this.SensitiveFieldLoanIncome(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.otherincome.equals(attrName)){
			this.SensitiveFieldOtherIncome(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.project_code.equals(attrName)){
			this.SensitiveFieldProjectCode(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.result_1029.equals(attrName)){
			this.SensitiveFieldResult1029(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.result_1030.equals(attrName)){
			this.SensitiveFieldResult1030(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.fraudcomp_decision.equals(attrName)){
			this.SensitiveFieldFraudCompDecision(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.address_status.equals(attrName)){
			this.SensitiveFieldAddressStatus(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.totaldebt.equals(attrName)){
			this.SensitiveFieldTotalDebt(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else 
		if(Field.bot5x_total.equals(attrName)){
			this.SensitiveFieldTotalBOT5X(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.certification.equals(attrName)){
			this.SensitiveFieldCertification(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.address_object.equals(attrName)){
			this.SensitiveFieldAddressObject(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}else
		if(Field.code_1019.equals(attrName)){
			this.SensitiveFieldCode1019(attrName, request, appM, sAppM, userM, jsonArray, sButton);
		}
	}	
	
	public void SensitiveFieldAddressStatus(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
							,JSONArray jsonArray ,List<String> sButton){
		
		if(!OrigConstant.BusClass.FCP_KEC_CC.equals(appM.getBusinessClassId())){
			return;
		}
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		PLPersonalInfoDataM sPersonalM = sAppM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();	
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}
		JSONObject obj = null;
		String addressStatus = request.getParameter(attrName);
		String addressType = request.getParameter("address_type");
		
		PLAddressDataM addressM = this.GetAddress(addressType, sPersonalM.getAddressVect());
		if(null == addressM) addressM = new PLAddressDataM();
		
		ObjectEngine objEngine = new ObjectEngine();
		if(objEngine.CompareValueObj(addressStatus, addressM.getAdrsts() , ClassEngine.STRING)){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Not Sensitive!");
			return;
		}
		logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
		SensitiveFieldTool senstitiveTool = new SensitiveFieldTool();
		
		obj = new JSONObject();
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.MANUAL);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE, ORIGXRulesTool.Constant.CLEAR_RULE);
//		obj.put(Constant.STYLE,""); 
		jsonArray.put(obj);
		
		xrulesVerM.setBundlingCCResidentResultCode(null);
		xrulesVerM.setBundlingCCResidentResultDesc(null);
		xrulesVerM.setBundlingCCResidentUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setBundlingCCResidentUpdateBy(userM.getUserName());
		
		//Remove data from policy rule
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CC_RESIDENCE_TYPE);
		
		if(!senstitiveTool.ValidateSensitiveButton(sButton, PLXrulesConstant.ButtonID.BUTTON_7002)){
			sButton.add(PLXrulesConstant.ButtonID.BUTTON_7002);
			senstitiveTool.ButtonExecuteLogic(PLXrulesConstant.ButtonID.BUTTON_7002, appM, sAppM, ORIGXRulesTool.Constant.CLEAR_RULE);
		}
		
	}
	
	public void SensitiveFieldFraudCompDecision(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
						,JSONArray jsonArray ,List<String> sButton){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		PLPersonalInfoDataM sPersonalM = sAppM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();	
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}
		JSONObject obj = null;
		String value = request.getParameter(attrName);		
		ObjectEngine objEngine = new ObjectEngine();
		if(objEngine.CompareValueObj(value, sXrulesVerM.getFraudCompanyDecision(), ClassEngine.STRING)){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Not Sensitive!");
			return;
		}
		logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
		SensitiveFieldTool senstitiveTool = new SensitiveFieldTool();
		obj = new JSONObject();
//			obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//			obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.FRAUD_COMPANY);
//			obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");	
//			obj.put(Constant.STYLE, ORIGLogic.LogicColorStyleResult("","")); 
		jsonArray.put(obj);
			
		xrulesVerM.setFraudCompanyResult(null);
		xrulesVerM.setFraudCompanyCode(null);
		xrulesVerM.setFraudCompanyUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setFraudCompanyUpdateBy(userM.getUserName());
		/*Remove Policy Rules*/
		 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_FRAUD_COMPANY);			 		  
		/*End Remove Policy Rules*/		
		if(!senstitiveTool.ValidateSensitiveButton(sButton, PLXrulesConstant.ButtonID.BUTTON_7001)){
			sButton.add(PLXrulesConstant.ButtonID.BUTTON_7001);
			senstitiveTool.ButtonExecuteLogic(PLXrulesConstant.ButtonID.BUTTON_7001, appM, sAppM, ORIGXRulesTool.Constant.CLEAR_RULE);
		}
	}
	
	public void SensitiveFieldResult1029(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
						,JSONArray jsonArray ,List<String> sButton){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		PLPersonalInfoDataM sPersonalM = sAppM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();	
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}
		JSONObject obj = null;
		String value = request.getParameter(attrName);		
		ObjectEngine objEngine = new ObjectEngine();
		if(objEngine.CompareValueObj(value, sXrulesVerM.getWebDssKbankCode(), ClassEngine.STRING)){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Not Sensitive!");
			return;
		}
		logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
		SensitiveFieldTool senstitiveTool = new SensitiveFieldTool();
		obj = new JSONObject();
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.AMC_TAMC_COMPANY);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
//		obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
		jsonArray.put(obj);
		obj = new JSONObject();
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.AMC_TAMC_COMPANY_FIRST);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
//		obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
		jsonArray.put(obj);
		obj = new JSONObject();
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.BANKRUPTCY_COMPANY);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");	
//		obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
		jsonArray.put(obj);
		obj = new JSONObject();
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//		obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.BANKRUPTCY_COMPANY_FIRST);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//		obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//		obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
//		obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
		jsonArray.put(obj);
			
		xrulesVerM.setAmcTamcCompanyResult("");
		xrulesVerM.setAmcTamcCompanyCode("");
		xrulesVerM.setAmcTamcCompanyUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setAmcTamcCompanyUpdateBy(userM.getUserName());	
		xrulesVerM.setAmctamcCompanyFirstResult("");
		xrulesVerM.setAmctamcCompanyFirstCode("");
		xrulesVerM.setAmctamcCompanyFirstUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setAmctamcCompanyFirstUpdateBy(userM.getUserName());
		
		xrulesVerM.setBankRuptcyCompanyResult("");
		xrulesVerM.setBankRuptcyCompanyCode("");
		xrulesVerM.setBankRuptcyCompanyUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setBankRuptcyCompanyUpdateBy(userM.getUserName());	
		xrulesVerM.setBankruptcyCompanyFirstResult("");
		xrulesVerM.setBankruptcyCompanyFirstCode("");
		xrulesVerM.setBankruptcyCompanyFirstUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setBankruptcyCompanyFirstUpdateBy(userM.getUserName());	
		/*Remove Policy Rules*/
		 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_AMC_TAMC_COMPANY);			 
		 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_BANKRUPTCY_COMPANY);
		 /*End Remove Policy Rules*/		
		if(!senstitiveTool.ValidateSensitiveButton(sButton, PLXrulesConstant.ButtonID.BUTTON_7001)){
			sButton.add(PLXrulesConstant.ButtonID.BUTTON_7001);
			senstitiveTool.ButtonExecuteLogic(PLXrulesConstant.ButtonID.BUTTON_7001, appM, sAppM, ORIGXRulesTool.Constant.CLEAR_RULE);
		}
	}
	public void SensitiveFieldResult1030(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
			,JSONArray jsonArray ,List<String> sButton){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		PLPersonalInfoDataM sPersonalM = sAppM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();	
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}
		JSONObject obj = null;
		String value = request.getParameter(attrName);		
		ObjectEngine objEngine = new ObjectEngine();
		if(objEngine.CompareValueObj(value, xrulesVerM.getFinancialStatementLastCode(), ClassEngine.STRING)){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Not Sensitive!");
			return;
		}
		logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
		SensitiveFieldTool senstitiveTool = new SensitiveFieldTool();
		obj = new JSONObject();
//			obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//			obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.AMC_TAMC_COMPANY);
//			obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");	
//			obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
		jsonArray.put(obj);
		obj = new JSONObject();
//			obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//			obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.AMC_TAMC_COMPANY_FIRST);
//			obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");	
//			obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
		jsonArray.put(obj);
		obj = new JSONObject();
//			obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//			obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.BANKRUPTCY_COMPANY);
//			obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
//			obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
		jsonArray.put(obj);
		obj = new JSONObject();
//			obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//			obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.BANKRUPTCY_COMPANY_FIRST);
//			obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");	
//			obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
		jsonArray.put(obj);
			
		xrulesVerM.setAmcTamcCompanyResult("");
		xrulesVerM.setAmcTamcCompanyCode("");
		xrulesVerM.setAmcTamcCompanyUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setAmcTamcCompanyUpdateBy(userM.getUserName());	
		xrulesVerM.setAmctamcCompanyFirstResult("");
		xrulesVerM.setAmctamcCompanyFirstCode("");
		xrulesVerM.setAmctamcCompanyFirstUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setAmctamcCompanyFirstUpdateBy(userM.getUserName());
		
		xrulesVerM.setBankRuptcyCompanyResult("");
		xrulesVerM.setBankRuptcyCompanyCode("");
		xrulesVerM.setBankRuptcyCompanyUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setBankRuptcyCompanyUpdateBy(userM.getUserName());	
		xrulesVerM.setBankruptcyCompanyFirstResult("");
		xrulesVerM.setBankruptcyCompanyFirstCode("");
		xrulesVerM.setBankruptcyCompanyFirstUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setBankruptcyCompanyFirstUpdateBy(userM.getUserName());	
		
		/*Remove Policy Rules*/
		 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_AMC_TAMC_COMPANY);			 
		 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_BANKRUPTCY_COMPANY);
		 /*End Remove Policy Rules*/		  
		if(!senstitiveTool.ValidateSensitiveButton(sButton, PLXrulesConstant.ButtonID.BUTTON_7001)){
			sButton.add(PLXrulesConstant.ButtonID.BUTTON_7001);
			senstitiveTool.ButtonExecuteLogic(PLXrulesConstant.ButtonID.BUTTON_7001, appM, sAppM, ORIGXRulesTool.Constant.CLEAR_RULE);
		}
	}
	public void SensitiveFieldProjectCode(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
											,JSONArray jsonArray ,List<String> sButton){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		String projectCode = request.getParameter(attrName);		
		ObjectEngine objEngine = new ObjectEngine();
		if(objEngine.CompareValueObj(projectCode, appM.getProjectCode(), ClassEngine.STRING)){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Not Sensitive!");
			return;
		}

		JSONObject obj = null;
		
		if(!PLXrulesConstant.ResultCode.CODE_PASS.equals(xrulesVerM.getVerCusResultCode()) 
				&& !PLXrulesConstant.ResultCode.CODE_FAIL.equals(xrulesVerM.getVerCusResultCode())){
			
			logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
			
			xrulesVerM.setVerCusResult(null);
			xrulesVerM.setVerCusResultCode(null);
			xrulesVerM.setVerCusUpdateBy(null);
			xrulesVerM.setVerCusUpdateDate(null);			
			xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_VERIFY_CUSTOMER);
			
		 	obj = new JSONObject();
//				obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//				obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.VERIFY_CUSTOMER);
//				obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE_NON_MESSAGE);
//				obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//				obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");	
//				obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
			jsonArray.put(obj);
		
		}
		if(!PLXrulesConstant.ResultCode.CODE_PASS.equals(xrulesVerM.getVerHRResultCode()) 
				&& !PLXrulesConstant.ResultCode.CODE_FAIL.equals(xrulesVerM.getVerHRResultCode())){
			
			logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
			
			xrulesVerM.setVerHRResult(null);
			xrulesVerM.setVerHRResultCode(null);
			xrulesVerM.setVerHRUpdateBy(null);
			xrulesVerM.setVerHRUpdateDate(null);
			xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_VERIFY_HR);
		 	obj = new JSONObject();
//				obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//				obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.VERIFY_HR);
//				obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE_NON_MESSAGE);
//				obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//				obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");	
//				obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
			jsonArray.put(obj);
		}
	}
	
	public void SensitiveFieldKecCurrentBalance(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
								,JSONArray jsonArray ,List<String> sButton){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		PLPersonalInfoDataM sPersonalM = sAppM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();	
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}
		JSONObject obj = null;
		ObjectEngine objEngine = new ObjectEngine();
		BigDecimal value = DataFormatUtility.StringToBigDecimalEmptyNull(request.getParameter(attrName));
		if(objEngine.CompareValueObj(value, sXrulesVerM.getKecCurrentBalance() , ClassEngine.BIGDECIMALNULL)){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Not Sensitive!");
			return;
		}
		logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
		SensitiveFieldTool senstitiveTool = new SensitiveFieldTool();
			obj = new JSONObject();
//			obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//			obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.KEC_BLOCKCODE);
//			obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");	
//			obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
			jsonArray.put(obj);
			
			xrulesVerM.setBlockCodeKecCode("");
			xrulesVerM.setBlockCodeKecResult("");
			xrulesVerM.setBlockCodeKecUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setBlockCodeKecUpdateBy(userM.getUserName());
			
			if(!senstitiveTool.ValidateSensitiveButton(sButton, PLXrulesConstant.ButtonID.BUTTON_7001)){
				sButton.add(PLXrulesConstant.ButtonID.BUTTON_7001);
				senstitiveTool.ButtonExecuteLogic(PLXrulesConstant.ButtonID.BUTTON_7001, appM, sAppM, ORIGXRulesTool.Constant.CLEAR_RULE);
			}
	}
	
	public void SensitiveFieldKecLastPaymentDate(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
			,JSONArray jsonArray ,List<String> sButton){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		PLPersonalInfoDataM sPersonalM = sAppM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();	
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}
		JSONObject obj = null;
		Date value = null;
		try{
			value = DataFormatUtility.StringEnToDateEn(request.getParameter(attrName),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S);
		}catch(Exception e){
			logger.fatal("Exception ",e);
		}		
		if(null != value && null != sXrulesVerM.getKecLastPaymentDate() && value.equals(sXrulesVerM.getKecLastPaymentDate())){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Not Sensitive!");
			return;
		}
		logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
		SensitiveFieldTool senstitiveTool = new SensitiveFieldTool();
			obj = new JSONObject();
//			obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//			obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.KEC_BLOCKCODE);
//			obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
//			obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
			jsonArray.put(obj);
			
			xrulesVerM.setBlockCodeKecCode("");
			xrulesVerM.setBlockCodeKecResult("");
			xrulesVerM.setBlockCodeKecUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setBlockCodeKecUpdateBy(userM.getUserName());
			
			if(!senstitiveTool.ValidateSensitiveButton(sButton, PLXrulesConstant.ButtonID.BUTTON_7001)){
				sButton.add(PLXrulesConstant.ButtonID.BUTTON_7001);
				senstitiveTool.ButtonExecuteLogic(PLXrulesConstant.ButtonID.BUTTON_7001, appM, sAppM, ORIGXRulesTool.Constant.CLEAR_RULE);
			}
	}
	
	public void SensitiveFieldCCLastPaymentDate(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
			,JSONArray jsonArray ,List<String> sButton){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		PLPersonalInfoDataM sPersonalM = sAppM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();	
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}
		JSONObject obj = null;
		Date value = null;
		try{
			value = DataFormatUtility.StringEnToDateEn(request.getParameter(attrName),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S);
		}catch(Exception e){
			logger.fatal("Exception ",e);
		}		
		if(null != value && null != sXrulesVerM.getcCLastPaymentDate() && value.equals(sXrulesVerM.getcCLastPaymentDate())){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Not Sensitive!");
			return;
		}
		logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
		SensitiveFieldTool senstitiveTool = new SensitiveFieldTool();
			obj = new JSONObject();
//			obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//			obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.CREDIT_CARD_BLOCKCODE);
//			obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");	
//			obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
			jsonArray.put(obj);
			
			xrulesVerM.setBlockCodeccCode("");
			xrulesVerM.setBlockCodeccResult("");
			xrulesVerM.setBlockCodeccUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setBlockCodeccUpdateBy(userM.getUserName());	
			
			if(!senstitiveTool.ValidateSensitiveButton(sButton, PLXrulesConstant.ButtonID.BUTTON_7001)){
				sButton.add(PLXrulesConstant.ButtonID.BUTTON_7001);
				senstitiveTool.ButtonExecuteLogic(PLXrulesConstant.ButtonID.BUTTON_7001, appM, sAppM, ORIGXRulesTool.Constant.CLEAR_RULE);
			}
	}
	
	public void SensitiveFieldCCCurrentBalance(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
			,JSONArray jsonArray ,List<String> sButton){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		PLPersonalInfoDataM sPersonalM = sAppM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();	
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}
		JSONObject obj = null;
		ObjectEngine objEngine = new ObjectEngine();
		BigDecimal value = DataFormatUtility.StringToBigDecimalEmptyNull(request.getParameter(attrName));
		if(objEngine.CompareValueObj(value, sXrulesVerM.getcCCurrentBalance() , ClassEngine.BIGDECIMALNULL)){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Not Sensitive!");
			return;
		}
		logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
		SensitiveFieldTool senstitiveTool = new SensitiveFieldTool();
			obj = new JSONObject();
//			obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//			obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.CREDIT_CARD_BLOCKCODE);
//			obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");	
//			obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
			jsonArray.put(obj);
			
			xrulesVerM.setBlockCodeccCode("");
			xrulesVerM.setBlockCodeccResult("");
			xrulesVerM.setBlockCodeccUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setBlockCodeccUpdateBy(userM.getUserName());	
			
			if(!senstitiveTool.ValidateSensitiveButton(sButton, PLXrulesConstant.ButtonID.BUTTON_7001)){
				sButton.add(PLXrulesConstant.ButtonID.BUTTON_7001);
				senstitiveTool.ButtonExecuteLogic(PLXrulesConstant.ButtonID.BUTTON_7001, appM, sAppM, ORIGXRulesTool.Constant.CLEAR_RULE);
			}
	}
	
	public void SensitiveFieldBankruptcy(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
			,JSONArray jsonArray ,List<String> sButton){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		PLPersonalInfoDataM sPersonalM = sAppM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();	
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}
		JSONObject obj = null;
		String value = request.getParameter(attrName);
		ObjectEngine objEngine = new ObjectEngine();
		if(objEngine.CompareValueObj(value, sXrulesVerM.getEditBankruptcy(), ClassEngine.STRING)){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Not Sensitive!");
			return;
		}
		logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
		SensitiveFieldTool senstitiveTool = new SensitiveFieldTool();
			obj = new JSONObject();
//			obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//			obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.BANKRUPTCY);
//			obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
//			obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
			jsonArray.put(obj);
			
			xrulesVerM.setBankruptcyResult("");
			xrulesVerM.setBankruptcyCode("");
			xrulesVerM.setBankruptcyUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setBankruptcyUpdateBy(userM.getUserName());
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_BANKRUPTCY);
			 /*End Remove Policy Rules*/
			
			if(!senstitiveTool.ValidateSensitiveButton(sButton, PLXrulesConstant.ButtonID.BUTTON_7001)){
				sButton.add(PLXrulesConstant.ButtonID.BUTTON_7001);
				senstitiveTool.ButtonExecuteLogic(PLXrulesConstant.ButtonID.BUTTON_7001, appM, sAppM, ORIGXRulesTool.Constant.CLEAR_RULE);
			}
	}
	
	public void SensitiveFieldAmcTamc(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
			,JSONArray jsonArray ,List<String> sButton){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		PLPersonalInfoDataM sPersonalM = sAppM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();	
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}
		JSONObject obj = null;
		String value = request.getParameter(attrName);
		ObjectEngine objEngine = new ObjectEngine();
		if(objEngine.CompareValueObj(value, sXrulesVerM.getEditAmcTamc(), ClassEngine.STRING)){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Not Sensitive!");
			return;
		}
		logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
		SensitiveFieldTool senstitiveTool = new SensitiveFieldTool();
			obj = new JSONObject();
//			obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//			obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.AMC_TAMC);
//			obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
//			obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
			jsonArray.put(obj);
			
			xrulesVerM.setAmcTamcResult("");
			xrulesVerM.setAmcTamcCode("");
			xrulesVerM.setAmcTamcUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setAmcTamcUpdateBy(userM.getUserName());	
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_AMC_TAMC);
			 /*End Remove Policy Rules*/
			if(!senstitiveTool.ValidateSensitiveButton(sButton, PLXrulesConstant.ButtonID.BUTTON_7001)){
				sButton.add(PLXrulesConstant.ButtonID.BUTTON_7001);
				senstitiveTool.ButtonExecuteLogic(PLXrulesConstant.ButtonID.BUTTON_7001, appM, sAppM, ORIGXRulesTool.Constant.CLEAR_RULE);
			}	
	}
	
	public void SensitiveFieldNPL(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
			,JSONArray jsonArray ,List<String> sButton){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		PLPersonalInfoDataM sPersonalM = sAppM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();	
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}
		JSONObject obj = null;
		String value = request.getParameter(attrName);
		ObjectEngine objEngine = new ObjectEngine();
		if(objEngine.CompareValueObj(value, sXrulesVerM.getEditNpl(), ClassEngine.STRING)){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Not Sensitive!");
			return;
		}
		logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
		SensitiveFieldTool senstitiveTool = new SensitiveFieldTool();
			obj = new JSONObject();
//			obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//			obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.NPL_LPM);
//			obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
//			obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
			jsonArray.put(obj);
			
			xrulesVerM.setNplLpmResult("");
			xrulesVerM.setNplLpmCode("");
			xrulesVerM.setNplLpmUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setNplLpmUpdateBy(userM.getUserName());
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_NPL_LPM);
			 /*End Remove Policy Rules*/
			if(!senstitiveTool.ValidateSensitiveButton(sButton, PLXrulesConstant.ButtonID.BUTTON_7001)){
				sButton.add(PLXrulesConstant.ButtonID.BUTTON_7001);
				senstitiveTool.ButtonExecuteLogic(PLXrulesConstant.ButtonID.BUTTON_7001, appM, sAppM, ORIGXRulesTool.Constant.CLEAR_RULE);
			}			
	}
	
	public void SensitiveFieldCCBlockCode(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
			,JSONArray jsonArray ,List<String> sButton){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		PLPersonalInfoDataM sPersonalM = sAppM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();	
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}
		JSONObject obj = null;
		String value = request.getParameter(attrName);
		ObjectEngine objEngine = new ObjectEngine();
		if(objEngine.CompareValueObj(value, sXrulesVerM.getEditBlockCode(), ClassEngine.STRING)){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Not Sensitive!");
			return;
		}
		logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
		SensitiveFieldTool senstitiveTool = new SensitiveFieldTool();
			obj = new JSONObject();
//			obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//			obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.CREDIT_CARD_BLOCKCODE);
//			obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");	
//			obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
			jsonArray.put(obj);
			
			xrulesVerM.setBlockCodeccCode("");
			xrulesVerM.setBlockCodeccResult("");
			xrulesVerM.setBlockCodeccUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setBlockCodeccUpdateBy(userM.getUserName());
			
			if(!senstitiveTool.ValidateSensitiveButton(sButton, PLXrulesConstant.ButtonID.BUTTON_7001)){
				sButton.add(PLXrulesConstant.ButtonID.BUTTON_7001);
				senstitiveTool.ButtonExecuteLogic(PLXrulesConstant.ButtonID.BUTTON_7001, appM, sAppM, ORIGXRulesTool.Constant.CLEAR_RULE);
			}
	}
	
	public void SensitiveFieldClassifyLevel(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
			,JSONArray jsonArray ,List<String> sButton){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		PLPersonalInfoDataM sPersonalM = sAppM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();	
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}
		JSONObject obj = null;
		String value = request.getParameter(attrName);
		ObjectEngine objEngine = new ObjectEngine();
		if(objEngine.CompareValueObj(value, sXrulesVerM.getEditClassifyLevel(), ClassEngine.STRING)){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Not Sensitive!");
			return;
		}
		logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
		SensitiveFieldTool senstitiveTool = new SensitiveFieldTool();
			obj = new JSONObject();
//			obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//			obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.CLASSIFY_LEVEL_LPM);
//			obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");	
//			obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
			jsonArray.put(obj);
			
			xrulesVerM.setLpmClassifyLvCode("");
			xrulesVerM.setLpmClassifyLvResult("");
			xrulesVerM.setLpmClassifyLvUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setLpmClassifyLvUpdateBy(userM.getUserName());
			/*Remove Policy Rules*/
			 xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_CLASSIFY_LEVEL_LPM);
			 /*End Remove Policy Rules*/
			if(!senstitiveTool.ValidateSensitiveButton(sButton, PLXrulesConstant.ButtonID.BUTTON_7001)){
				sButton.add(PLXrulesConstant.ButtonID.BUTTON_7001);
				senstitiveTool.ButtonExecuteLogic(PLXrulesConstant.ButtonID.BUTTON_7001, appM, sAppM, ORIGXRulesTool.Constant.CLEAR_RULE);
			}
	}
	
	public void SensitiveFieldCisNo(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
			,JSONArray jsonArray ,List<String> sButton){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		PLPersonalInfoDataM sPersonalM = sAppM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();	
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}
		JSONObject obj = null;
		String value = request.getParameter(attrName);
		ObjectEngine objEngine = new ObjectEngine();
		if(objEngine.CompareValueObj(value, sXrulesVerM.getEditCisNo(), ClassEngine.STRING)){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Not Sensitive!");
			return;
		}
		logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
		SensitiveFieldTool senstitiveTool = new SensitiveFieldTool();
			obj = new JSONObject();
//			obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//			obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.CIS_NO);
//			obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"-");	
//			obj.put(Constant.STYLE,""); 
			jsonArray.put(obj);
			obj = new JSONObject();
//			obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//			obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.BEHAVIOR_RISK_GRADE);
//			obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");	
//			obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
			jsonArray.put(obj);
			personalM.setCisNo(null);
			xrulesVerM.setBehaviorRiskGradeCode(null);
			xrulesVerM.setBehaviorRiskGradeResult(null);
			xrulesVerM.setBehavRiskGradeDecision(null);
			xrulesVerM.setxRulesBScoreVect(null);
		if(!senstitiveTool.ValidateSensitiveButton(sButton, PLXrulesConstant.ButtonID.BUTTON_7001)){
			sButton.add(PLXrulesConstant.ButtonID.BUTTON_7001);
			senstitiveTool.ButtonExecuteLogic(PLXrulesConstant.ButtonID.BUTTON_7001, appM, sAppM, ORIGXRulesTool.Constant.CLEAR_RULE);
		}			
	}
	
	private boolean isSensitiveDoc(String docCode){
		ORIGCacheUtil cache = new ORIGCacheUtil();
		String sensitive = cache.getGeneralParamByCode("SENSITIVE_DOC_CODE");
		if(OrigUtil.isEmptyString(sensitive)){
			return false;
		}
		String [] code = sensitive.split("\\,");
		for (String string : code) {
			if(null != string && string.equals(docCode)){
				return true;
			}
		}
		return false;
	}
	
	public void SensitiveFieldDocCode(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
			,JSONArray jsonArray ,List<String> sButton){
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		PLPersonalInfoDataM sPersonalM = sAppM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();	
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}
		JSONObject obj = null;
		Vector<PLDocumentCheckListDataM> sDocCheckListVect 	= sAppM.getDocCheckListVect();
		
		String attrDoc = request.getParameter("attrDocName");
		
		logger.debug("attrDoc >> "+attrDoc);
		
		String resultDoc = request.getParameter(attrDoc);
//		modify #septemwi get doccode29 
		
		String docCode = (!OrigUtil.isEmptyString(attrDoc))?attrDoc.replaceAll("radio_", ""):"";		
		logger.debug("docCode >> "+docCode);
		
		if(!isSensitiveDoc(docCode)){
			logger.debug("ManualSensitive [attr] "+attrDoc+" >> Not Sensitive!");
			return;
		}
		
		PLDocumentCheckListDataM docM = this.GetDocDetail(docCode, sDocCheckListVect);
		if(null == docM) docM = new PLDocumentCheckListDataM();
		
		ObjectEngine objEngine = new ObjectEngine();
		if(objEngine.CompareValueObj(resultDoc, docM.getReceive(), ClassEngine.STRING)){
			logger.debug("ManualSensitive [attr] "+attrDoc+" >> Not Sensitive!");
			return;
		}
		logger.debug("ManualSensitive [attr] "+attrDoc+" >> Sensitive!");
		SensitiveFieldTool senstitiveTool = new SensitiveFieldTool();
			obj = new JSONObject();
//			obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//			obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.DEMOGRAPHIC);
//			obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
//			obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
			jsonArray.put(obj);
			
			xrulesVerM.setDemographicCode("");
			xrulesVerM.setDemographicResult("");
			xrulesVerM.setAgeVerCode("");
			xrulesVerM.setAgeVerResult("");
			xrulesVerM.setNationalityVerCode("");
			xrulesVerM.setNationalityVerResult("");
			xrulesVerM.setDemographicUpdateDate(new Timestamp(new java.util.Date().getTime()));
			xrulesVerM.setDemographicUpdateBy(userM.getUserName());
			
		if(!senstitiveTool.ValidateSensitiveButton(sButton, PLXrulesConstant.ButtonID.BUTTON_7001)){
			sButton.add(PLXrulesConstant.ButtonID.BUTTON_7001);
			senstitiveTool.ButtonExecuteLogic(PLXrulesConstant.ButtonID.BUTTON_7001, appM, sAppM, ORIGXRulesTool.Constant.CLEAR_RULE);
		}
		
	}
	public PLDocumentCheckListDataM GetDocDetail(String docCode ,Vector<PLDocumentCheckListDataM> sDocCheckListVect){
		if(!OrigUtil.isEmptyVector(sDocCheckListVect)){
			for(PLDocumentCheckListDataM docM : sDocCheckListVect){
				if(null != docM && docM.getDocCode().equals(docCode)){
					return docM;
				}
			}
		}
		return null;
	}
	
	public PLAddressDataM GetAddress(String addressType, Vector<PLAddressDataM> addressVect){
		if(!OrigUtil.isEmptyVector(addressVect)){
			for(PLAddressDataM addressM : addressVect){
				if(null != addressM && addressM.getAddressType().equals(addressType)){
					return addressM;
				}
			}
		}
		return null;
	}
	
	public void SensitiveFieldSaleVolume(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
							,JSONArray jsonArray ,List<String> sButton){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		String saleVolume = request.getParameter(attrName);		
		ObjectEngine objEngine = new ObjectEngine();
		if(objEngine.CompareValueObj(saleVolume, xrulesVerM.getSaleVolumeCode(), ClassEngine.STRING)){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Not Sensitive!");
			return;
		}
		if(!PLXrulesConstant.ResultCode.CODE_PASS.equals(xrulesVerM.getVerHRResultCode()) 
				&& !PLXrulesConstant.ResultCode.CODE_FAIL.equals(xrulesVerM.getVerHRResultCode())){
			
			logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
			
			xrulesVerM.setVerHRResult(null);
			xrulesVerM.setVerHRResultCode(null);
			xrulesVerM.setVerHRUpdateBy(null);
			xrulesVerM.setVerHRUpdateDate(null);
			xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_VERIFY_HR);
			
			JSONObject obj = null;
		 	obj = new JSONObject();
//				obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//				obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.VERIFY_HR);
//				obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//				obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//				obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");	
//				obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
			jsonArray.put(obj);
		}
	}
	
	public void SensitiveFieldOtherIncome(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
											,JSONArray jsonArray ,List<String> sButton){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		PLXRulesDebtBdDataM xrulesDebtM = xrulesVerM.getXRulesDebtBdDataM();
		if(null == xrulesDebtM){
			xrulesDebtM = new PLXRulesDebtBdDataM();
			xrulesVerM.setXRulesDebtBdDataM(xrulesDebtM);
		}
		ObjectEngine objEngine = new ObjectEngine();
		BigDecimal otherIncome = DataFormatUtility.StringToBigDecimal(request.getParameter(attrName));
		if(objEngine.CompareValueObj(otherIncome, xrulesDebtM.getOtherIncome(), ClassEngine.BIGDECIMAL)){
			logger.debug("ManualSensitive [attr] "+attrName+"  >> Not Sensitive!");
			return;
		}
		if(!PLXrulesConstant.ResultCode.CODE_PASS.equals(xrulesVerM.getVerHRResultCode()) 
				&& !PLXrulesConstant.ResultCode.CODE_FAIL.equals(xrulesVerM.getVerHRResultCode())){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
			
			xrulesVerM.setVerHRResult(null);
			xrulesVerM.setVerHRResultCode(null);
			xrulesVerM.setVerHRUpdateBy(null);
			xrulesVerM.setVerHRUpdateDate(null);
			xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_VERIFY_HR);
			
			JSONObject obj = null;
		 	obj = new JSONObject();
//				obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//				obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.VERIFY_HR);
//				obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//				obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//				obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");	
//				obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
			jsonArray.put(obj);
		}
	}
	public void SensitiveFieldLoanIncome(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
											,JSONArray jsonArray ,List<String> sButton){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		PLXRulesDebtBdDataM xrulesDebtM = xrulesVerM.getXRulesDebtBdDataM();
		if(null == xrulesDebtM){
			xrulesDebtM = new PLXRulesDebtBdDataM();
			xrulesVerM.setXRulesDebtBdDataM(xrulesDebtM);
		}
		ObjectEngine objEngine = new ObjectEngine();
		BigDecimal loanIncome = DataFormatUtility.StringToBigDecimal(request.getParameter(attrName));
		if(objEngine.CompareValueObj(loanIncome, xrulesDebtM.getLoanIncome(), ClassEngine.BIGDECIMAL)){
			logger.debug("ManualSensitive [attr] "+attrName+"  >> Not Sensitive!");
			return;
		}
		if(!PLXrulesConstant.ResultCode.CODE_PASS.equals(xrulesVerM.getVerHRResultCode()) 
				&& !PLXrulesConstant.ResultCode.CODE_FAIL.equals(xrulesVerM.getVerHRResultCode())){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
			
			xrulesVerM.setVerHRResult(null);
			xrulesVerM.setVerHRResultCode(null);
			xrulesVerM.setVerHRUpdateBy(null);
			xrulesVerM.setVerHRUpdateDate(null);
			xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_VERIFY_HR);
			
			JSONObject obj = null;
		 	obj = new JSONObject();
//				obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//				obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.VERIFY_HR);
//				obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//				obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//				obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");	
//				obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
			jsonArray.put(obj);
		}
	}
	public void SensitiveFieldSalary(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
											,JSONArray jsonArray ,List<String> sButton){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		PLXRulesDebtBdDataM xrulesDebtM = xrulesVerM.getXRulesDebtBdDataM();
		if(null == xrulesDebtM){
			xrulesDebtM = new PLXRulesDebtBdDataM();
			xrulesVerM.setXRulesDebtBdDataM(xrulesDebtM);
		}
		ObjectEngine objEngine = new ObjectEngine();
		BigDecimal salary = DataFormatUtility.StringToBigDecimal(request.getParameter(attrName));
		if(objEngine.CompareValueObj(salary, xrulesDebtM.getSalary(), ClassEngine.BIGDECIMAL)){
			logger.debug("ManualSensitive [attr] "+attrName+"  >> Not Sensitive!");
			return;
		}
		if(!PLXrulesConstant.ResultCode.CODE_PASS.equals(xrulesVerM.getVerHRResultCode()) 
				&& !PLXrulesConstant.ResultCode.CODE_FAIL.equals(xrulesVerM.getVerHRResultCode())){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
			
			xrulesVerM.setVerHRResult(null);
			xrulesVerM.setVerHRResultCode(null);
			xrulesVerM.setVerHRUpdateBy(null);
			xrulesVerM.setVerHRUpdateDate(null);
			xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_VERIFY_HR);
						
			JSONObject obj = null;
		 	obj = new JSONObject();
//				obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//				obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.VERIFY_HR);
//				obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//				obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//				obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");
//				obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
			jsonArray.put(obj);
		}
	}
	
	public void SensitiveFieldChannel(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
											,JSONArray jsonArray ,List<String> sButton){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		String channel = request.getParameter(attrName);		
		ObjectEngine objEngine = new ObjectEngine();
		if(objEngine.CompareValueObj(channel, appM.getApplyChannel(), ClassEngine.STRING)){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Not Sensitive!");
			return;
		}
		
		if(!PLXrulesConstant.ResultCode.CODE_PASS.equals(xrulesVerM.getVerCusResultCode()) 
				&& !PLXrulesConstant.ResultCode.CODE_FAIL.equals(xrulesVerM.getVerCusResultCode())){			
			logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
			
			xrulesVerM.setVerCusResult(null);
			xrulesVerM.setVerCusResultCode(null);
			xrulesVerM.setVerCusUpdateBy(null);
			xrulesVerM.setVerCusUpdateDate(null);
			xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_VERIFY_CUSTOMER);
			
			JSONObject obj = null;
		 	obj = new JSONObject();
//				obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//				obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.VERIFY_CUSTOMER);
//				obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//				obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//				obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");	
//				obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
			jsonArray.put(obj);
		}		
	}
	public void SensitiveFieldkec_permit(String attrName ,HttpServletRequest request ,PLApplicationDataM appM 
			,PLApplicationDataM sAppM ,UserDetailM userM 
					,JSONArray jsonArray ,List<String> sButton){

		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		PLBundleHLDataM bundleHLM =  appM.getBundleHLM();
		if(null == bundleHLM){
			bundleHLM = new PLBundleHLDataM();
		}
		PLPersonalInfoDataM sPersonalM = sAppM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();	
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}
		PLBundleHLDataM sBundleHLM = sAppM.getBundleHLM();
		if(null == sBundleHLM){
			sBundleHLM = new PLBundleHLDataM();
		}
		JSONObject obj = null;
		SensitiveFieldTool senstitiveTool = new SensitiveFieldTool();
		ObjectEngine objEngine = new ObjectEngine();
		BigDecimal kec_permit = DataFormatUtility.StringToBigDecimal(request.getParameter(attrName));
		if(objEngine.CompareValueObj(kec_permit, sBundleHLM.getApproveCreditLine(), ClassEngine.BIGDECIMAL)){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Not Sensitive!");
			return;
		}
		logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
		obj = new JSONObject();
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.MANUAL);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE, ORIGXRulesTool.Constant.CLEAR_RULE);
//		obj.put(Constant.STYLE,""); 		
		jsonArray.put(obj);
		if(!senstitiveTool.ValidateSensitiveButton(sButton, PLXrulesConstant.ButtonID.BUTTON_7001)){
			sButton.add(PLXrulesConstant.ButtonID.BUTTON_7001);
			senstitiveTool.ButtonExecuteLogic(PLXrulesConstant.ButtonID.BUTTON_7001, appM, sAppM, ORIGXRulesTool.Constant.CLEAR_RULE);
		}
		if(!senstitiveTool.ValidateSensitiveButton(sButton, PLXrulesConstant.ButtonID.BUTTON_7002)){
			sButton.add(PLXrulesConstant.ButtonID.BUTTON_7002);
			senstitiveTool.ButtonExecuteLogic(PLXrulesConstant.ButtonID.BUTTON_7002, appM, sAppM, ORIGXRulesTool.Constant.CLEAR_RULE);
		}
	}
	public void SensitiveFieldkec_permit_cash(String attrName ,HttpServletRequest request ,PLApplicationDataM appM 
													,PLApplicationDataM sAppM ,UserDetailM userM 
															,JSONArray jsonArray ,List<String> sButton){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		PLBundleSMEDataM bundleSMEM =  appM.getBundleSMEM();
		if(null == bundleSMEM){
			bundleSMEM = new PLBundleSMEDataM();
		}
		PLPersonalInfoDataM sPersonalM = sAppM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();	
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}
		PLBundleSMEDataM sBundleSMEM = sAppM.getBundleSMEM();
		if(null == sBundleSMEM){
			sBundleSMEM = new PLBundleSMEDataM();
		}
		JSONObject obj = null;
		SensitiveFieldTool senstitiveTool = new SensitiveFieldTool();
		ObjectEngine objEngine = new ObjectEngine();
		BigDecimal kec_permit_cash = DataFormatUtility.StringToBigDecimal(request.getParameter(attrName));
		if(objEngine.CompareValueObj(kec_permit_cash, sBundleSMEM.getApproveCreditLine(), ClassEngine.BIGDECIMAL)){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Not Sensitive!");
			return;
		}
		logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
		obj = new JSONObject();
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.MANUAL);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE, ORIGXRulesTool.Constant.CLEAR_RULE);
//		obj.put(Constant.STYLE,""); 
		jsonArray.put(obj);
		if(!senstitiveTool.ValidateSensitiveButton(sButton, PLXrulesConstant.ButtonID.BUTTON_7001)){
			sButton.add(PLXrulesConstant.ButtonID.BUTTON_7001);
			senstitiveTool.ButtonExecuteLogic(PLXrulesConstant.ButtonID.BUTTON_7001, appM, sAppM, ORIGXRulesTool.Constant.CLEAR_RULE);
		}
		if(!senstitiveTool.ValidateSensitiveButton(sButton, PLXrulesConstant.ButtonID.BUTTON_7002)){
			sButton.add(PLXrulesConstant.ButtonID.BUTTON_7002);
			senstitiveTool.ButtonExecuteLogic(PLXrulesConstant.ButtonID.BUTTON_7002, appM, sAppM, ORIGXRulesTool.Constant.CLEAR_RULE);
		}
	}
	public void SensitiveFieldkec_cash_payment(String attrName ,HttpServletRequest request ,PLApplicationDataM appM 
														,PLApplicationDataM sAppM ,UserDetailM userM 
																,JSONArray jsonArray ,List<String> sButton){

		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		PLBundleALDataM bundleALM =  appM.getBundleALM();
		if(null == bundleALM){
			bundleALM = new PLBundleALDataM();
		}
		PLPersonalInfoDataM sPersonalM = sAppM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();	
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}
		PLBundleALDataM sBundleALM = sAppM.getBundleALM();
		if(null == sBundleALM){
			sBundleALM = new PLBundleALDataM();
		}
		JSONObject obj = null;
		SensitiveFieldTool senstitiveTool = new SensitiveFieldTool();
		ObjectEngine objEngine = new ObjectEngine();
		BigDecimal kec_cash_payment = DataFormatUtility.StringToBigDecimal(request.getParameter(attrName));
		if(objEngine.CompareValueObj(kec_cash_payment, sBundleALM.getApproveCreditLine(), ClassEngine.BIGDECIMAL)){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Not Sensitive!");
			return;
		}
		logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
		obj = new JSONObject();
//		obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.MANUAL);
//		obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE, ORIGXRulesTool.Constant.CLEAR_RULE);
//		obj.put(Constant.STYLE,""); 
		jsonArray.put(obj);
		if(!senstitiveTool.ValidateSensitiveButton(sButton, PLXrulesConstant.ButtonID.BUTTON_7001)){
			sButton.add(PLXrulesConstant.ButtonID.BUTTON_7001);
			senstitiveTool.ButtonExecuteLogic(PLXrulesConstant.ButtonID.BUTTON_7001, appM, sAppM, ORIGXRulesTool.Constant.CLEAR_RULE);
		}
		if(!senstitiveTool.ValidateSensitiveButton(sButton, PLXrulesConstant.ButtonID.BUTTON_7002)){
			sButton.add(PLXrulesConstant.ButtonID.BUTTON_7002);
			senstitiveTool.ButtonExecuteLogic(PLXrulesConstant.ButtonID.BUTTON_7002, appM, sAppM, ORIGXRulesTool.Constant.CLEAR_RULE);
		}
	}
	
	public void SensitiveFieldTotalDebt(String attrName ,HttpServletRequest request ,PLApplicationDataM appM 
						,PLApplicationDataM sAppM ,UserDetailM userM 
								,JSONArray jsonArray ,List<String> sButton){

		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		PLXRulesDebtBdDataM debtDataM = xrulesVerM.getXRulesDebtBdDataM();
		if(null == debtDataM){
			debtDataM = new PLXRulesDebtBdDataM();
			xrulesVerM.setXRulesDebtBdDataM(debtDataM);
		}
		
		PLPersonalInfoDataM sPersonalM = sAppM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();	
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}
		PLXRulesDebtBdDataM sdebtDataM = sXrulesVerM.getXRulesDebtBdDataM();
		if(null == sdebtDataM){
			sdebtDataM = new PLXRulesDebtBdDataM();
			sXrulesVerM.setXRulesDebtBdDataM(sdebtDataM);
		}
		JSONObject obj = null;
		SensitiveFieldTool senstitiveTool = new SensitiveFieldTool();
		ObjectEngine objEngine = new ObjectEngine();
		BigDecimal total_debt = DataFormatUtility.StringToBigDecimal(request.getParameter(attrName));
		
		logger.debug("total_debt >> "+total_debt);
		
		if(objEngine.CompareValueObj(total_debt, sdebtDataM.getTotalDebt(), ClassEngine.BIGDECIMAL)){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Not Sensitive!");
			return;
		}
		logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");

		xrulesVerM.setDEBT_BDResult("");
		xrulesVerM.setDebtBdCode("");
		xrulesVerM.setSummaryIncomeVerCode("");
		xrulesVerM.setSummaryIncomeVerResult("");
		debtDataM.setDebtBurdentScore(null);
		
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SUMMARY_INCOME);	
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_PERCENT_DEBT_BURDEN);
		
	 	obj = new JSONObject();
//			obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//			obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.INCOME_DEBT);
//			obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");	
//			obj.put(Constant.STYLE,""); 
		jsonArray.put(obj);
		
		xrulesVerM.setBot5xResultCode("");
		xrulesVerM.setBot5xResultDesc("");
		xrulesVerM.setBot5xUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setBot5xUpdateBy(userM.getUserName());	
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_ROLE_ID_BOT5X);	
		
		if(!senstitiveTool.ValidateSensitiveButton(sButton, PLXrulesConstant.ButtonID.BUTTON_7002)){
			sButton.add(PLXrulesConstant.ButtonID.BUTTON_7002);
			senstitiveTool.ButtonExecuteLogic(PLXrulesConstant.ButtonID.BUTTON_7002, appM, sAppM, ORIGXRulesTool.Constant.CLEAR_RULE);
		}
	}
	
	public void SensitiveFieldTotalBOT5X(String attrName ,HttpServletRequest request ,PLApplicationDataM appM 
						,PLApplicationDataM sAppM ,UserDetailM userM 
								,JSONArray jsonArray ,List<String> sButton){

		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();	
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		PLXRulesDebtBdDataM debtDataM = xrulesVerM.getXRulesDebtBdDataM();
		if(null == debtDataM){
			debtDataM = new PLXRulesDebtBdDataM();
			xrulesVerM.setXRulesDebtBdDataM(debtDataM);
		}
		PLPersonalInfoDataM sPersonalM = sAppM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM sXrulesVerM = sPersonalM.getXrulesVerification();	
		if(null == sXrulesVerM){
			sXrulesVerM = new PLXRulesVerificationResultDataM();
			sPersonalM.setXrulesVerification(sXrulesVerM);
		}
		PLXRulesDebtBdDataM sdebtDataM = sXrulesVerM.getXRulesDebtBdDataM();
		if(null == sdebtDataM){
			sdebtDataM = new PLXRulesDebtBdDataM();
			sXrulesVerM.setXRulesDebtBdDataM(sdebtDataM);
		}
		JSONObject obj = null;		
		
		SensitiveFieldTool senstitiveTool = new SensitiveFieldTool();
		ObjectEngine objEngine = new ObjectEngine();
		BigDecimal total_bot5x = DataFormatUtility.StringToBigDecimal(request.getParameter(attrName));
		
		logger.debug("total_bot5x >> "+total_bot5x);
		
		if(objEngine.CompareValueObj(total_bot5x, sAppM.getBot5xTotal(), ClassEngine.BIGDECIMAL)){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Not Sensitive!");
			return;
		}
		logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");

		xrulesVerM.setBot5xResultCode("");
		xrulesVerM.setBot5xResultDesc("");
		xrulesVerM.setBot5xUpdateDate(new Timestamp(new java.util.Date().getTime()));
		xrulesVerM.setBot5xUpdateBy(userM.getUserName());	
		
		xrulesVerM.setDEBT_BDResult("");
		xrulesVerM.setDebtBdCode("");
		xrulesVerM.setSummaryIncomeVerCode("");
		xrulesVerM.setSummaryIncomeVerResult("");
		debtDataM.setDebtBurdentScore(null);
		
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_SUMMARY_INCOME);	
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_PERCENT_DEBT_BURDEN);
		
	 	obj = new JSONObject();
//			obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//			obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.INCOME_DEBT);
//			obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//			obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//			obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");	
//			obj.put(Constant.STYLE,""); 
		jsonArray.put(obj);
		
		xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_ROLE_ID_BOT5X);	
		
		if(!senstitiveTool.ValidateSensitiveButton(sButton, PLXrulesConstant.ButtonID.BUTTON_7002)){
			sButton.add(PLXrulesConstant.ButtonID.BUTTON_7002);
			senstitiveTool.ButtonExecuteLogic(PLXrulesConstant.ButtonID.BUTTON_7002, appM, sAppM, ORIGXRulesTool.Constant.CLEAR_RULE);
		}
	}
	
	
	public void SensitiveFieldCertification(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
											,JSONArray jsonArray ,List<String> sButton){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		PLSaleInfoDataM saleInfoM = appM.getSaleInfo();
		if(null == saleInfoM){
			saleInfoM = new PLSaleInfoDataM();
		}
		String certification = request.getParameter(attrName);		
		ObjectEngine objEngine = new ObjectEngine();
		if(objEngine.CompareValueObj(certification, saleInfoM.getSalesGuarantee(), ClassEngine.STRING)){
			logger.debug("ManualSensitive [attr] "+attrName+" >> Not Sensitive!");
			return;
		}
		
		if(!PLXrulesConstant.ResultCode.CODE_PASS.equals(xrulesVerM.getVerCusResultCode()) 
				&& !PLXrulesConstant.ResultCode.CODE_FAIL.equals(xrulesVerM.getVerCusResultCode())){			
			logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
			
			xrulesVerM.setVerCusResult(null);
			xrulesVerM.setVerCusResultCode(null);
			xrulesVerM.setVerCusUpdateBy(null);
			xrulesVerM.setVerCusUpdateDate(null);
			xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_VERIFY_CUSTOMER);
			
			JSONObject obj = null;
		 	obj = new JSONObject();
//				obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//				obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.VERIFY_CUSTOMER);
//				obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//				obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//				obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");	
//				obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
			jsonArray.put(obj);
		}		
	}
	
	public void SensitiveFieldAddressObject(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
											,JSONArray jsonArray ,List<String> sButton){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		if(!PLXrulesConstant.ResultCode.CODE_PASS.equals(xrulesVerM.getVerCusResultCode()) 
				&& !PLXrulesConstant.ResultCode.CODE_FAIL.equals(xrulesVerM.getVerCusResultCode())){			
			logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
			
			xrulesVerM.setVerCusResult(null);
			xrulesVerM.setVerCusResultCode(null);
			xrulesVerM.setVerCusUpdateBy(null);
			xrulesVerM.setVerCusUpdateDate(null);
			xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_VERIFY_CUSTOMER);
			
			JSONObject obj = null;
		 	obj = new JSONObject();
//				obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//				obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.VERIFY_CUSTOMER);
//				obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//				obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//				obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");	
//				obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
			jsonArray.put(obj);
		}		
	}	
		
	public void SensitiveFieldCode1019(String attrName ,HttpServletRequest request ,PLApplicationDataM appM ,PLApplicationDataM sAppM ,UserDetailM userM 
											,JSONArray jsonArray ,List<String> sButton){
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		if(null == xrulesVerM){
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		if(!PLXrulesConstant.ResultCode.CODE_PASS.equals(xrulesVerM.getVerHRResultCode()) 
				&& !PLXrulesConstant.ResultCode.CODE_FAIL.equals(xrulesVerM.getVerHRResultCode())){			
			logger.debug("ManualSensitive [attr] "+attrName+" >> Sensitive!");
			
			xrulesVerM.setVerHRResult(null);
			xrulesVerM.setVerHRResultCode(null);
			xrulesVerM.setVerHRUpdateBy(null);
			xrulesVerM.setVerHRUpdateDate(null);
			xrulesVerM.removePLPolicyRuleByPolicyID(OrigConstant.POLICY_RULE_ID_VERIFY_HR);
			
			JSONObject obj = null;
		 	obj = new JSONObject();
//				obj.put(ORIGXRulesTool.Constant.TYPE, ORIGXRulesTool.Constant.SERVICE);
//				obj.put(ORIGXRulesTool.Constant.SERVICE_ID,PLXrulesConstant.ModuleService.VERIFY_HR);
//				obj.put(ORIGXRulesTool.Constant.SENSITIVE_TYPE,ORIGXRulesTool.Constant.CLEAR_RULE);
//				obj.put(ORIGXRulesTool.Constant.RESULT_CODE,"");
//				obj.put(ORIGXRulesTool.Constant.RESULT_DESC,"");	
//				obj.put(Constant.STYLE,ORIGLogic.LogicColorStyleResult("","")); 
			jsonArray.put(obj);
		}		
	}	
	
}
