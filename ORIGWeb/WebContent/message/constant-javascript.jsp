<%@page import="com.eaf.ncb.util.NCBConstant"%>
<%@page import="com.eaf.xrules.shared.constant.PLXrulesConstant"%>
<%@page import="com.eaf.orig.ulo.pl.config.ORIGConfig"%>
<%@page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.ORIGXRulesTool"%>
<%@ page import="com.eaf.orig.ulo.pl.ajax.AjaxDisplayServlet"%>
<%@ page import="com.eaf.j2ee.pattern.util.ErrorUtil" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>

<script type="text/javascript">
	var ERROR_ID_NO 		 	= "<%=ErrorUtil.getShortErrorMessage(request,"ERROR_ID_NO")%>";		
	var CUST_FOREIGNER 		 	= "<%=OrigConstant.CUSTOMER_TYPE_FOREIGNER%>";
	var ERROR_NUMBER		 	= "<%=ErrorUtil.getShortErrorMessage(request,"ERROR_NUMBER")%>";
	var ERROR_EMAIL			 	= "<%=ErrorUtil.getShortErrorMessage(request,"ERROR_EMAIL")%>";
	var PROJECT_CODE_WARNING 	= "<%=ErrorUtil.getShortErrorMessage(request,"PROJECT_CODE_WARNING")%>";
	var DELETE_ADDRESS_WARNING 	= "<%=ErrorUtil.getShortErrorMessage(request, "DELETE_ADDRESS_WARNING")%>";
	var DATE_ERROR_MSG			= "<%=ErrorUtil.getShortErrorMessage(request,"DATE_ERROR_MSG")%>";
	var BIRTH_DATE_ERROR_MSG	= "<%=ErrorUtil.getShortErrorMessage(request,"BIRTH_DATE_ERROR_MSG")%>";
	var EXP_DATE_ERROR_MSG		= "<%=ErrorUtil.getShortErrorMessage(request,"EXP_DATE_ERROR_MSG")%>";
	var MONTH_INPUT				= "<%=ErrorUtil.getShortErrorMessage(request,"MONTH_INPUT_MSG")%>";
	var YEAR_INPUT				= "<%=ErrorUtil.getShortErrorMessage(request,"YEAR_INPUT_MSG")%>";
	var LOAN_INPUT				= "<%=ErrorUtil.getShortErrorMessage(request,"LOAN_INPUT_MSG")%>";
	var CONFIRM_CLEAR_IDNO		= "<%=ErrorUtil.getShortErrorMessage(request,"CONFIRM_CLEAR_IDNO")%>";
	var DELETE_ADDRESS_RECOMMEND = "<%=ErrorUtil.getShortErrorMessage(request,"DELETE_ADDRESS_RECOMMEND")%>";
	
	/**Xrules Constant #SepTemWi*/
	var SUCCESS_CODE 			= "<%=PLXrulesConstant.ExecuteCode.EXE_SUCCESS%>";
	var FINAL_EXE				= "<%=ORIGXRulesTool.Constant.FINAL_EXE%>";
	var RESULT_CODE				= "<%=ORIGXRulesTool.Constant.RESULT_CODE%>";
	var EXECUTE					= "<%=ORIGXRulesTool.Constant.EXECUTE%>";
	var BUTTON					= "<%=ORIGXRulesTool.Constant.BUTTON%>";
	var BUTTON_EDIT				= "<%=ORIGXRulesTool.Constant.BUTTON_EDIT%>";
	var SERVICE					= "<%=ORIGXRulesTool.Constant.SERVICE%>"
	var REJECT					= "<%=PLXrulesConstant.ResultCode.CODE_REJECT%>";
	var CANCEL					= "<%=PLXrulesConstant.ResultCode.CODE_CANCEL%>";
	var BLOCK					= "<%=PLXrulesConstant.ResultCode.CODE_BLOCK%>";
	var PASS 					= "<%=PLXrulesConstant.ResultCode.CODE_PASS%>";
	var DUP 					= "<%=PLXrulesConstant.ResultCode.CODE_DUP%>";
	var REC1					= "<%=PLXrulesConstant.ResultCode.CODE_REC1%>";
	var REC2					= "<%=PLXrulesConstant.ResultCode.CODE_REC2%>";
	var WEIVED					= "<%=PLXrulesConstant.ResultCode.CODE_WAIVED%>";
	var FAIL					= "<%=PLXrulesConstant.ResultCode.CODE_FAIL%>";
	var NA						= "<%=PLXrulesConstant.ResultCode.CODE_NA%>";
	var SUCCESS					= "<%=PLXrulesConstant.ExecuteCode.EXE_SUCCESS%>";
	
	var REJECT_MSG				= "<%=ErrorUtil.getShortErrorMessage(request,"REJECT_APP_MSG")%>";
	var CANCEL_MSG				= "<%=ErrorUtil.getShortErrorMessage(request,"CANCLE_APP_MSG")%>";
	var CANCEL_REC1_MSG			= "<%=ErrorUtil.getShortErrorMessage(request,"CANCEL_REC1_MSG")%>";
	var CANCEL_REC2_MSG			= "<%=ErrorUtil.getShortErrorMessage(request,"CANCEL_REC2_MSG")%>";
	var BLOCK_MSG	 	 		= "<%=ErrorUtil.getShortErrorMessage(request,"DUPLICATE_APP_MSG")%>";
	var NCB_REJECT_MSG			= "<%=ErrorUtil.getShortErrorMessage(request,"NCB_REJECT_MSG")%>";
	
	var INCOME_DEBT				= "<%=PLXrulesConstant.ModuleService.INCOME_DEBT%>";
	var DEPLICATE_APPLICATION	= "<%=PLXrulesConstant.ModuleService.DEPLICATE_APPLICATION%>";
	
	var CLEAR_RULE				= "<%=ORIGXRulesTool.Constant.CLEAR_RULE%>";
	var SENSITIVE_ERROR			= "<%=ErrorUtil.getShortErrorMessage(request,"MSG_SENSITIVE_ERROR")%>";
	var MANUAL					= "<%=ORIGXRulesTool.Constant.MANUAL%>";
	var EAITFB0137I01_ERROR 	= "<%=PLXrulesConstant.EAITFB0137I01_ERROR%>";
	/**End Xrules Constant #SepTemWi*/
	
	var ERROR_PRODUCT_DOMAIN 	= "<%=ErrorUtil.getShortErrorMessage(request,"ERROR_PRODUCT_DOMAIN")%>";
	var ERROR_PRODUCT_GROUP 	= "<%=ErrorUtil.getShortErrorMessage(request,"ERROR_PRODUCT_GROUP")%>";
	var ERROR_PRODUCT_FAMILY 	= "<%=ErrorUtil.getShortErrorMessage(request,"ERROR_PRODUCT_FAMILY")%>";
	var ERROR_PRODUCT 			= "<%=ErrorUtil.getShortErrorMessage(request,"ERROR_PRODUCT")%>";
	var ERROR_SALE_TYPE 		= "<%=ErrorUtil.getShortErrorMessage(request,"ERROR_SALE_TYPE")%>";
	var ERROR_CUSTOMER_TYPE 	= "<%=ErrorUtil.getShortErrorMessage(request,"ERROR_CUSTOMER_TYPE")%>";
	var ERROR_INPUT_NOTEPAD		= "<%=ErrorUtil.getShortErrorMessage(request,"ERROR_INPUT_NOTEPAD")%>";
	var CONFIRM_SEND_MAIL		= "<%=MessageResourceUtil.getTextDescription(request, "CONFIRM_SEND_MAIL")%>";
	var CONFIRM_CLAIM_APP		= "<%=MessageResourceUtil.getTextDescription(request, "CONFIRM_CLAIM_APP")%>";
	var SEND_MAIL_SUCCESS		= "<%=MessageResourceUtil.getTextDescription(request, "SEND_MAIL_SUCCESS")%>";
	var SEND_MAIL_ERROR		    = "<%=ErrorUtil.getShortErrorMessage(request,"SEND_MAIL_ERROR")%>";
	var EAIL_NO_BRANCH_NO_DOC	= "<%=ErrorUtil.getShortErrorMessage(request,"SEND_MAIL_NO_BRANCH_NO_DOC")%>";
	
	var FU_DETAIL_PERSONAL_ERR  = "<%=ErrorUtil.getShortErrorMessage(request,"FU_DETAIL_PERSONAL")%>";
	var FU_DETAIL_PHONE_ERR     = "<%=ErrorUtil.getShortErrorMessage(request,"FU_DETAIL_PHONE")%>";
	var FU_DETAIL_OTHER_PHONE_ERR  = "<%=ErrorUtil.getShortErrorMessage(request,"FU_DETAIL_OTHER_PHONE")%>";
	var FU_DETAIL_CONTACT_RESULT_ERR  = "<%=ErrorUtil.getShortErrorMessage(request,"FU_DETAIL_CONTACT_RESULT")%>";
		
	var ROLE_DE 				= "<%=OrigConstant.ROLE_DE%>";
	var ROLE_DE_SUP				= "<%=OrigConstant.ROLE_DE_SUP%>";
	var ROLE_DC					= "<%=OrigConstant.ROLE_DC%>";
	var ROLE_DC_SUP				= "<%=OrigConstant.ROLE_DC_SUP%>";
	var ROLE_CA					= "<%=OrigConstant.ROLE_CA%>";
	var ROLE_CA_SUP				= "<%=OrigConstant.ROLE_CA_SUP%>";
	var ROLE_FU					= "<%=OrigConstant.ROLE_FU%>";
	var ROLE_FU_SUP				= "<%=OrigConstant.ROLE_FU_SUP%>";
	var ROLE_VC					= "<%=OrigConstant.ROLE_VC%>";
	var ROLE_VC_SUP				= "<%=OrigConstant.ROLE_VC_SUP%>";
	var ROLE_CB					= "<%=OrigConstant.ROLE_CB%>";
	var ROLE_DF					= "<%=OrigConstant.ROLE_DF%>";
	var ROLE_DF_SUP				= "<%=OrigConstant.ROLE_DF_SUP%>";
	var DF_REJECT				= "<%=OrigConstant.ROLE_DF_REJECT%>";
	var ROLE_PO					= "<%=OrigConstant.ROLE_PO%>";
	var ROLE_SP					= "<%=OrigConstant.ROLE_SP%>";
	
	var BUS_CLASS_IC			= "<%=OrigConstant.BusClass.FCP_KEC_IC%>";
	var BUS_CLASS_DC			= "<%=OrigConstant.BusClass.FCP_KEC_DC%>";
	
	var MANDATORY_TYPE_DRAFT 	= "<%=OrigConstant.Mandatory.MANDATORY_TYPE_DRAFT%>";
	var MANDATORY_TYPE_SUBMIT	= "<%=OrigConstant.Mandatory.MANDATORY_TYPE_SUMMIT%>";
	var MANDATORY_TYPE_EXECUTE	= "<%=OrigConstant.Mandatory.MANDATORY_TYPE_EXECUTE%>";
	
	//SUP
	var CHAR_MORE_2						= "<%=ErrorUtil.getShortErrorMessage(request,"CHAR_MORE_2")%>";
	var PRODUCT							= "<%=ErrorUtil.getShortErrorMessage(request,"PRODUCT")%>";
	var SELECT_MORE_2_FIELD				= "<%=ErrorUtil.getShortErrorMessage(request,"SELECT_MORE_2_FIELD")%>";
	var SELECT_APP						= "<%=ErrorUtil.getShortErrorMessage(request,"SELECT_APP")%>";
	var SELECT_PRIORITY					= "<%=ErrorUtil.getShortErrorMessage(request,"SELECT_PRIORITY")%>";
	var CONFIRM_SET_PRIORITY			= "<%=ErrorUtil.getShortErrorMessage(request,"CONFIRM_SET_PRIORITY")%>";
	var CONFIIMR_CHANGE_VALUE			= "<%=ErrorUtil.getShortErrorMessage(request,"CONFIIMR_CHANGE_VALUE")%>";
	var EXPORT_REJECT_SPECIAL_POINT		= "<%=OrigConstant.ExcelType.EXPORT_REJECT_REPORT_SPECIAL%>";
	var CONFIRM_REJECT					= "<%=ErrorUtil.getShortErrorMessage(request,"CONFIRM_REJECT")%>";
	var CONFIRM_UNLOCK_PRE				= "<%=ErrorUtil.getShortErrorMessage(request,"CONFIRM_UNLOCK_PRE")%>";
	var CONFIRM_UNLOCK_SUF				= "<%=ErrorUtil.getShortErrorMessage(request,"CONFIRM_UNLOCK_SUF")%>";
	var SELECT_SALE_TYPE				= "<%=ErrorUtil.getShortErrorMessage(request,"SELECT_SALE_TYPE")%>";
	var SELECT_OWNER					= "<%=ErrorUtil.getShortErrorMessage(request,"SELECT_OWNER")%>";
	var SELECT_DATE						= "<%=ErrorUtil.getShortErrorMessage(request,"SELECT_DATE")%>";
	var SELECT_FILE						= "<%=ErrorUtil.getShortErrorMessage(request,"SELECT_FILE")%>";
	
	//CA
	var FINAL_MORE_OVERIDE				= "<%=ErrorUtil.getShortErrorMessage(request,"FINAL_MORE_OVERIDE")%>";
	var FINAL_MORE_RECOMMEND			= "<%=ErrorUtil.getShortErrorMessage(request,"FINAL_MORE_RECOMMEND")%>";
	var FINAL_MORE_BOT5X				= "<%=ErrorUtil.getShortErrorMessage(request,"FINAL_MORE_BOT5X")%>";
	
	//CB
	var REQUEST_APP						= "<%=ErrorUtil.getShortErrorMessage(request,"REQUEST_APP")%>";
	var SEND_TO_KCBS					= "<%=ErrorUtil.getShortErrorMessage(request,"SEND_TO_KCBS")%>";
	var SELECT_REASON					= "<%=ErrorUtil.getShortErrorMessage(request,"SELECT_REASON")%>";
	var REASON_DETAIL					= "<%=ErrorUtil.getShortErrorMessage(request,"REASON_DETAIL")%>";
	var DAY_MORE_THAN_90				= "<%=ErrorUtil.getShortErrorMessage(request,"DAY_MORE_THAN_90")%>";
	var ERROR_DATE						= "<%=ErrorUtil.getShortErrorMessage(request,"ERROR_DATE")%>";
	var CONSENT_REF_DATE_FROM			= "<%=ErrorUtil.getShortErrorMessage(request,"CONSENT_REF_DATE_FROM")%>";
	var CONSENT_REF_DATE_TO				= "<%=ErrorUtil.getShortErrorMessage(request,"CONSENT_REF_DATE_TO")%>";
	var SERVICE_DOWN					= "<%=ErrorUtil.getShortErrorMessage(request,"SERVICE_DOWN")%>"
	var EMPTY_QUEUE						= "<%=ErrorUtil.getShortErrorMessage(request,"EMPTY_QUEUE")%>"
	
	//SS
	var SELECT_ACTION					= "<%=ErrorUtil.getShortErrorMessage(request,"SELECT_ACTION")%>"
	var SELECT_SEND_CARD_LINK_DATE		= "<%=ErrorUtil.getShortErrorMessage(request,"SELECT_SEND_CARD_LINK_DATE")%>"
	var APP_NO							= "<%=MessageResourceUtil.getTextDescription(request, "APP_NO")%>";
	var RE_ISSUED						= "<%=MessageResourceUtil.getTextDescription(request, "RE_ISSUED")%>";
	var CANCEL_APPLICATION				= "<%=MessageResourceUtil.getTextDescription(request, "CANCEL_APPLICATION")%>";
	var SELECT_DATE_LESS_THAN_TODAY		= "<%=MessageResourceUtil.getTextDescription(request, "SELECT_DATE_LESS_THAN_TODAY")%>";
	
		
	var CAR_INSURANCE					= "<%=ErrorUtil.getShortErrorMessage(request,"CAR_INSURANCE")%>";
	var OCCUPATION_INPUT				= "<%=ErrorUtil.getShortErrorMessage(request,"OCCUPATION_INPUT_MSG")%>";
	
	var JOB_STATUS_ON 					= "<%=OrigConstant.StatusOnJob.ON%>";
	var JOB_STATUS_OFF					= "<%=OrigConstant.StatusOnJob.OFF%>";
	
	var PROJECT_CODE					= "<%=MessageResourceUtil.getTextDescription(request, "PROJECT_CODE")%>";
	var TITLE_THAI						= "<%=MessageResourceUtil.getTextDescription(request, "TITLE_THAI")%>";
	var TITLE_ENG						= "<%=MessageResourceUtil.getTextDescription(request, "TITLE_ENG")%>";
	var WORKPLACE_NAME					= "<%=MessageResourceUtil.getTextDescription(request, "WORKPLACE_NAME")%>";
	
	var RECOMMEND_CODE					= "<%=MessageResourceUtil.getTextDescription(request, "RECOMMEND_CODE")%>";
	var BRANCH_CODE						= "<%=MessageResourceUtil.getTextDescription(request, "BRANCH_CODE")%>";
	var SELLER							= "<%=MessageResourceUtil.getTextDescription(request, "SELLER")%>";
	var SERVICE_SALE_NAME				= "<%=MessageResourceUtil.getTextDescription(request, "SERVICE_SALE_NAME")%>";
	
	var ADDRESS_INFO					= "<%=MessageResourceUtil.getTextDescription(request, "DF_ADDRESS")%>";
	var ZIPCODE							= "<%=MessageResourceUtil.getTextDescription(request, "ZIP_CODE")%>";
	var PROVINCE						= "<%=MessageResourceUtil.getTextDescription(request, "PROVINCE")%>";
	var AMPHUR							= "<%=MessageResourceUtil.getTextDescription(request, "AMPHUR")%>";
	var TAMBOL							= "<%=MessageResourceUtil.getTextDescription(request, "TAMBOL")%>";
	var COUNTRY							= "<%=MessageResourceUtil.getTextDescription(request, "COUNTRY")%>";
	var YEAR_ALERT						= "<%=MessageResourceUtil.getTextDescription(request, "YEAR_ALERT")%>";
	//Define for Salary Type
	var WARNING_SALARY					= "<%=MessageResourceUtil.getTextDescription(request, "WARNING_SALARY")%>";
	var WARNING_BUSINESS_TURNOVER		= "<%=MessageResourceUtil.getTextDescription(request, "WARNING_BUSINESS_TURNOVER")%>";
	var WARNING_BUSINESS_NET_PROFIT		= "<%=MessageResourceUtil.getTextDescription(request, "WARNING_BUSINESS_NET_PROFIT")%>";
	var WARNING_OTHER_INCOME_PER_MONTH	= "<%=MessageResourceUtil.getTextDescription(request, "WARNING_OTHER_INCOME_PER_MONTH")%>";
	var WARNING_SAVINGS					= "<%=MessageResourceUtil.getTextDescription(request, "WARNING_SAVINGS")%>";
	var WARNING_OTHER_SOURCE_INCOME		= "<%=MessageResourceUtil.getTextDescription(request, "WARNING_OTHER_SOURCE_INCOME")%>";
	var CHECK_YEAR_ALERT				= "<%=MessageResourceUtil.getTextDescription(request, "CHECK_YEAR_ALERT")%>";
	var CHECK_MONTH_ALERT				= "<%=MessageResourceUtil.getTextDescription(request, "CHECK_MONTH_ALERT")%>";
	
	var SAVINGS							= "<%=MessageResourceUtil.getTextDescription(request, "SAVINGS")%>";
	var PRODUCT_FEATURE_WARNING			= "<%=MessageResourceUtil.getTextDescription(request, "PRODUCT_FEATURE_WARNING")%>";
	var SALE_TYPE_WARNING				= "<%=MessageResourceUtil.getTextDescription(request, "SALE_TYPE_WARNING")%>";
	
	var FULL_NAME						= "<%=MessageResourceUtil.getTextDescription(request, "FULL_NAME")%>";
	
	var DISPLAY_MODE_EDIT				= "<%=HTMLRenderUtil.DISPLAY_MODE_EDIT%>";
	var DISPLAY_MODE_VIEW				= "<%=HTMLRenderUtil.DISPLAY_MODE_VIEW%>";
	
	//Context path
	var CONTEXT_PATH_ORIG				= "<%=request.getContextPath()%>";
	//Scheme (HTTP, HTTPS)
	var SCHEME_ORIG						= "<%=request.getScheme()%>";
	//Port
	var PORT_ORIG						= "<%=request.getServerPort()%>";
	
	var ACTION_REQUEST_FU				= "<%=OrigConstant.Action.REQUEST_FU%>";
	var ACTION_SEND						= "<%=OrigConstant.wfProcessState.SEND%>";	
	
	var SEARCH_TYPE_DESC 				= "<%=OrigConstant.PopupSearchType.SEARCH_TYPE_DESC%>";	
	//DF Jobstate
	var DF_APPROVE_JOBSTATE				= "<%=OrigConstant.roleJobState.DF_APPROVE%>";
	var DF_REJECT_JOBSTATE				= "<%=OrigConstant.roleJobState.DF_REJECT%>";
	var DECISION_CONFIRM_REJECT			= "<%=OrigConstant.Action.CONFIRM_REJECT%>";
	
	var POS_DUR_DATE_MSG				="<%=ErrorUtil.getShortErrorMessage(request,"POS_DUR_DATE_MSG")%>";
	
	var MSG_REQUIRE_PHONENO				= "<%=ErrorUtil.getShortErrorMessage(request,"MSG_REQUIRE_PHONENO")%>";
	var MSG_REQUIRE_OTHER_PHONENO 		= "<%=ErrorUtil.getShortErrorMessage(request,"MSG_REQUIRE_OTHER_PHONENO")%>";
	var MSG_REQUIRE_PHONE_STATUS		= "<%=ErrorUtil.getShortErrorMessage(request,"MSG_REQUIRE_PHONE_STATUS")%>";
	var MSG_REQUIRE_PERSONAL_TYPE		= "<%=ErrorUtil.getShortErrorMessage(request,"MSG_REQUIRE_PERSONAL_TYPE")%>";
	var MSG_REQUIRE_ADDRESS_TYPE		= "<%=ErrorUtil.getShortErrorMessage(request,"MSG_REQUIRE_ADDRESS_TYPE")%>";
	var MSG_REQUIRE_NAME_ENG			= "<%=ErrorUtil.getShortErrorMessage(request,"MSG_REQUIRE_NAME_ENG")%>";
	var MSG_REQUIRE_BIRTH_DAY			= "<%=ErrorUtil.getShortErrorMessage(request,"MSG_REQUIRE_BIRTH_DAY")%>";
	var MSG_REQUIRE_IDNO				= "<%=ErrorUtil.getShortErrorMessage(request,"MSG_REQUIRE_IDNO")%>";
	var MSG_REQUIRE_VERCUS_RESULT		= "<%=ErrorUtil.getShortErrorMessage(request,"MSG_REQUIRE_VERCUS_RESULT")%>";
	var REQUIRE_INCOME_TYPE 			= "<%=ErrorUtil.getShortErrorMessage(request,"REQUIRE_INCOME_TYPE")%>";
	
	//Address popup
	var REQUIRE_ADDRESS_TYPE			= "<%=ErrorUtil.getShortErrorMessage(request,"ADDRESS_TYPE")%>";
	var REQUIRE_ZIPCODE					= "<%=ErrorUtil.getShortErrorMessage(request,"ZIPCODE")%>";
	var REQUIRE_COUNTRY					= "<%=ErrorUtil.getShortErrorMessage(request,"COUNTRY")%>";
	var ADDRESS_STYLE					= "<%=ErrorUtil.getShortErrorMessage(request,"ADDRESS_STYLE")%>";
	
	//busclass
	var INCREASE_BUSSCLASS				="<%=OrigConstant.BusClass.FCP_KEC_IC%>";
	var DECREASE_BUSSCLASS				="<%=OrigConstant.BusClass.FCP_KEC_DC%>";
	
	//Saving popup
	var REQUIRE_SAVING_TYPE				= "<%=ErrorUtil.getShortErrorMessage(request,"REQUIRE_SAVING_TYPE")%>";
	var REQUIRE_BANK_NAME				= "<%=ErrorUtil.getShortErrorMessage(request,"REQUIRE_BANK_NAME")%>";
	var REQUIRE_ACCOUNT_NO				= "<%=ErrorUtil.getShortErrorMessage(request,"REQUIRE_ACCOUNT_NO")%>";
	var REQUIRE_AVG_SUMMARY				= "<%=ErrorUtil.getShortErrorMessage(request,"REQUIRE_AVG_SUMMARY")%>";
	
	
	var DUPLICATE_APPLICATION			="<%=MessageResourceUtil.getTextDescription(request, "DUPLICATE_APPLICATION")%>";
	var EXISTING_KEC					="<%=MessageResourceUtil.getTextDescription(request, "EXISTING_KEC")%>";
	var WATCH_LIST_FRAUD				="<%=MessageResourceUtil.getTextDescription(request, "WATCH_LIST_FRAUD")%>";
	var PAY_ROLL						="<%=MessageResourceUtil.getTextDescription(request, "PAY_ROLL")%>";
	var LIST_COMPANY					="<%=MessageResourceUtil.getTextDescription(request, "LIST_COMPANY")%>";
	var FRAUD_COMPANY					="<%=MessageResourceUtil.getTextDescription(request, "FRAUD_COMPANY")%>";
	var DOCUMENT_CHECK_LIST				="<%=MessageResourceUtil.getTextDescription(request, "DOCUMENT_CHECK_LIST")%>";
	var VERIFY_CUSTOMER					="<%=MessageResourceUtil.getTextDescription(request, "VERIFY_CUSTOMER")%>";
	var VERIFY_HR						="<%=MessageResourceUtil.getTextDescription(request, "VERIFY_HR")%>";
	var REQUEST_NCB						="<%=MessageResourceUtil.getTextDescription(request, "REQUEST_NCB")%>";
	var FOLLOW_DETAIL					="<%=MessageResourceUtil.getTextDescription(request, "FOLLOW_DETAIL")%>";
	var BSCORE							="<%=MessageResourceUtil.getTextDescription(request, "BSCORE")%>";
	var NPL_LPM							="<%=MessageResourceUtil.getTextDescription(request, "NPL_LPM")%>";
	var NCB_RESULT						="<%=MessageResourceUtil.getTextDescription(request, "NCB_RESULT")%>";
	
	var NUMBER_ERROR_OVER_MAX			="<%=ErrorUtil.getShortErrorMessage(request,"NUMBER_ERROR_OVER_MAX")%>";
	var SEARCH_TYPE_LIKE				="<%=PLXrulesConstant.SearchType.SEARCH_TYPE_LIKE%>";
	
	var CASH_FIRST_MORE_FINAL			="<%=ErrorUtil.getShortErrorMessage(request,"CASH_FIRST_MORE_FINAL")%>";
	var CLEAR_DATA_EAI					="<%=MessageResourceUtil.getTextDescription(request, "CLEAR_DATA_EAI")%>";
	var WAITING_NCB_DATA				="<%=NCBConstant.ncbResultCode.WAITING_NCB_DATA%>";
	var EXISTING_KEC_SERVICE			="<%=String.valueOf(PLXrulesConstant.ModuleService.EXISTING_KEC)%>";
	var FRAUD_COMPANY_SERVICE			="<%=String.valueOf(PLXrulesConstant.ModuleService.FRAUD_COMPANY)%>";
	var PAY_ROLL_SERVICE				="<%=String.valueOf(PLXrulesConstant.ModuleService.PAY_ROLL)%>";
	var CODE_CHANGE_BUSCLASS			="<%=PLXrulesConstant.ResultCode.CODE_CHANGE_BUSCLASS%>";
	var FRAUD_COMPANY_ERROR 			="<%=ErrorUtil.getShortErrorMessage(request,"MSG_REQUIRE_FRAUDCOMPANY_DECISION")%>";
	var KEC_BLOCK_CODE_SERVICE			="<%=String.valueOf(PLXrulesConstant.ModuleService.KEC_BLOCKCODE)%>";
	var MONTH_PROFILE_SERVICE			="<%=String.valueOf(PLXrulesConstant.ModuleService.MONTH_PROFILE)%>";
	var MSG_NOT_EQUAL_CARD_STATUS		="<%=MessageResourceUtil.getTextDescription(request, "MSG_NOT_EQUAL_CARD_STATUS")%>";
	
	var FCP_KEC_IC 					= "<%=OrigConstant.BusClass.FCP_KEC_IC%>";
	var FCP_KEC_DC 					= "<%=OrigConstant.BusClass.FCP_KEC_DC%>";
	var FCP_KEC_XS 					= "<%=OrigConstant.BusClass.FCP_KEC_XS%>";
	var FCP_KEC_NM 					= "<%=OrigConstant.BusClass.FCP_KEC_NM%>";
	var FCP_KEC_CC 					= "<%=OrigConstant.BusClass.FCP_KEC_CC%>";
	var FCP_KEC_CG 					= "<%=OrigConstant.BusClass.FCP_KEC_CG%>";
	var FCP_KEC_HL 					= "<%=OrigConstant.BusClass.FCP_KEC_HL%>";
	var FCP_KEC_AL 					= "<%=OrigConstant.BusClass.FCP_KEC_AL%>";
	var FCP_KEC_SM 					= "<%=OrigConstant.BusClass.FCP_KEC_SME%>";
	var FCP_ALL_ALL 				= "<%=OrigConstant.BusClass.FCP_ALL_ALL%>";
	var MSG_CHANGE_CG 				= "<%=MessageResourceUtil.getTextDescription(request, "MSG_CHANGE_CG")%>";
	var MSG_CHANGE_SALE_TYPE 		= "<%=MessageResourceUtil.getTextDescription(request, "MSG_CHANGE_SALE_TYPE")%>";
	
	var CONFIRM_CLEAR_CUS_TYPE		= "<%=ErrorUtil.getShortErrorMessage(request,"CONFIRM_CLEAR_CUS_TYPE")%>";
	var SERACH_TYPE_ENQUIRY			= "<%=OrigConstant.SEARCH_TYPE_ENQUIRY%>";	
	var SAVEING_INCOME_TYPE			= "<%=OrigConstant.ApplicantIncomeType.SAVEING_INCOME%>";
	var ID_DIFF_CARD 				= "<%=ErrorUtil.getShortErrorMessage(request,"ID_DIFF_CARD")%>";
	
	var FORMAT_DATE_ERROR			= "<%=ErrorUtil.getShortErrorMessage(request,"FORMAT_DATE_ERROR")%>";
	var TIMEOUT_NCB 				= "<%=ORIGConfig.RETRIEVE_NCB_DATA_TIMEOUT%>";
	var RETRIEVE_NCB_DATA_TIMEOUT	= parseInt(TIMEOUT_NCB);
	
	var MODULE_DE_SUP_DECISION 		= "<%=String.valueOf(OrigConstant.IlogModule.MODULE_DE_SUP_DECISION)%>";
	var MODULE_DC_DECISION 			= "<%=String.valueOf(OrigConstant.IlogModule.MODULE_DC_DECISION)%>";
	var MODULE_DCI_DECISION 		= "<%=String.valueOf(OrigConstant.IlogModule.MODULE_DCI_DECISION)%>";
	var MODULE_VC_DECISION 			= "<%=String.valueOf(OrigConstant.IlogModule.MODULE_VC_DECISION)%>";
	
	//attachment
	var CONFIRM_DELETE				= "<%=MessageResourceUtil.getTextDescription(request, "CONFIRM_DELETE")%>";
	var SELECT_DATA_TO_DELETE		= "<%=MessageResourceUtil.getTextDescription(request, "SELECT_DATA_TO_DELETE")%>";	
	var ERROR_SELLER_BRANCH_CODE  	= "<%=ErrorUtil.getShortErrorMessage(request,"ERROR_BUNDLE_SELLER_BRANCH_CODE")%>";
	
	var FU_RECEIVE_DATE 			= "<%=ErrorUtil.getShortErrorMessage(request,"FU_RECEIVE_DATE")%>";
	var FU_FOLLOW_DATE				= "<%=ErrorUtil.getShortErrorMessage(request,"FU_FOLLOW_DATE")%>";
	var NEED_CRITERIA				= "<%=ErrorUtil.getShortErrorMessage(request,"NEED_CRITERIA")%>";
	var TH_FNAME_COUNT_CHAR			= "<%=ErrorUtil.getShortErrorMessage(request,"TH_FNAME_COUNT_CHAR")%>";
	var TH_LNAME_COUNT_CHAR			= "<%=ErrorUtil.getShortErrorMessage(request,"TH_LNAME_COUNT_CHAR")%>";
	var INTERNAL_SERVER_ERROR		= "<%=ErrorUtil.getShortErrorMessage(request,"INTERNAL_SERVER_ERROR")%>";
	var SCRIPT_ERROR				= "ERROR: Script Function >> ";
	
	var COLOR_STYLE_YELLOW			= "verify-div-yellow";
	var COLOR_STYLE_GREEN			= "verify-div-green";
	var COLOR_STYLE_RED				= "verify-div-red";
	
	var RESULT_DESC_PASS			= "<%=PLXrulesConstant.ResultDesc.CODE_PASS_DESC%>";
	var RESULT_DESC_OVERRIDE		= "<%=PLXrulesConstant.ResultDesc.CODE_OVERRIDE_DESC%>";
	var RESULT_DESC_FAIL			= "<%=PLXrulesConstant.ResultDesc.CODE_FAIL_DESC%>";
	var RECOMMEND_DESC_REJECT		= "<%=OrigConstant.recommendResult.REJECT_DESC%>";
	//applicant 
	var APPLICANT_CARD_EXPIRED 		= "<%=ErrorUtil.getShortErrorMessage(request,"APPLICANT_CARD_EXPIRED")%>";
	
	var CASH_DAY_5					= "<%=OrigConstant.cashDayType.CASH_DAY_5%>";
	var CASH_DAY_1					= "<%=OrigConstant.cashDayType.CASH_DAY_1%>";
	var PLEASE_SELECT				= "<%=MessageResourceUtil.getTextDescription(request, "PLEASE_SELECT")%>";
	var CASH_DAY_1_PERCENT_DROP_DOWN= ""; //For store drop down of cash day 1 percent
	var MSG_SELECT_APP_REASSIGN		= "<%=MessageResourceUtil.getTextDescription(request,"MSG_SELECT_APP_REASSIGN")%>";
	var CONFIRM_CHANGE_CARD_NO_ICDC	= "<%=MessageResourceUtil.getTextDescription(request,"CONFIRM_CHANGE_CARD_NO_ICDC")%>";
	var	NOT_FOUND_CARD_NO			= "<%=ErrorUtil.getShortErrorMessage(request,"NOT_FOUND_CARD_NO")%>";
	var MSG_CARD_NOT_ACTIVE_ICDC    = "<%=MessageResourceUtil.getTextDescription(request,"MSG_CARD_NOT_ACTIVE_ICDC")%>";
	var MSG_CONFIRM_SEND_EMAIL_FOLLOW = "<%=MessageResourceUtil.getTextDescription(request,"MSG_CONFIRM_SEND_EMAIL_FOLLOW")%>";
	
	var INTERFACE_TYPE_PAYROLL      = "<%=OrigConstant.InterfaceType.PAYROLL%>";
	var	MSG_RE_RETRIEVE_NCB_DATA	= "<%=MessageResourceUtil.getTextDescription(request,"MSG_RE_RETRIEVE_NCB_DATA")%>";
	var MIN_FINAL					= "<%=ErrorUtil.getShortErrorMessage(request, "MIN_FINAL")%>";
	var BATH						= "<%=MessageResourceUtil.getTextDescription(request, "BATH")%>";	
	
	var ERROR_JS					= "\u0E40\u0E01\u0E34\u0E14\u0E02\u0E49\u0E2D\u0E1C\u0E34\u0E14\u0E1E\u0E25\u0E32\u0E14"+" ";
	
</script>