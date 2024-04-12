package com.eaf.orig.ulo.constant;

import com.eaf.orig.shared.constant.OrigConstant;

public class MConstant extends OrigConstant{
	public static final String LOGON = "LOGON";
	public static final String LOGOUT = "LOGOUT";
	public class FLAG{
		public static final String NOTFOUND = "404";
		public static final String ACTIVE = "A";
		public static final String INACTIVE = "I";
		public static final String YES = "Y";
		public static final String NO = "N";
		public static final String MANDATORY = "M";
		public static final String WRONG = "W";
		public static final String TEMP = "T";
		public static final String SUBMIT = "S";
		public static final String NA = "99";
	}
	public class PROCESS{
		public static final String SUCCESS = "SUCCESS";
		public static final String FAIL = "FAIL";
	}
	public class ADDRESS_TYPE{
		public static final String CURRENT = "C";
		public static final String WORK = "W";
		public static final String DOCUMENT = "D";
	}
	public class DM_FORM_NAME{
		public static final String DM_STORE_FORM = "DM_STORE_FORM";
		public static final String DM_BORROW_FORM = "DM_BORROW_FORM";
	}
	public class DM_SEQUENCE_NAME{
		public static final String PK_DM_ID = "DM_ID_PK";
		public static final String PK_DM_SUB_ID = "DM_SUB_ID_PK";
		public static final String PK_DM_TRANSACTION_ID = "DM_TRANSACTION_ID_PK";
	}
	public class DM_MANAGEMENT_ACTION{
		public static final String BORROW_ACTION = "BR";
		public static final String WITHDRAW_ACTION = "WD";
		public static final String CUSTOMER_REQ_ACTION = "CR";
		public static final String RETURN_ACTION = "RT";
		public static final String STORE_ACTION = "ST";
		public static final String UPDATE_ACTION = "UD";
		public static final String DISPOSE_ACTION = "DP";
	}
	public class PERSONAL_TYPE{
		public static final String APPLICANT = "A";
		public static final String GUARANTOR = "G";
		public static final String SUP_CARD = "S";
	}
	public class DM_STATUS{
		public static final String NOT_IN_WAREHOUSE = "N";
		public static final String AVAILABLE= "A";
		public static final String BORROWED= "B";
		public static final String DISPOSED= "D";
		public static final String WITHDRAWN= "W";		
	}
	public class DM_TRANSACTION_STATUS{
		public static final String AVAILABLE ="A";
		public static final String BORROWED = "B";
		public static final String DISPOSED = "D";
		public static final String WITHDRAWN ="W";
	}
	public class IMPORT{
		public static final String IMPORT_WITHDRAW_AUTHORITY = "IMPORT_WITHDRAW_AUTHORITY";
		public static final String IMPORT_ATTACH_FILE = "IMPORT_ATTACH_FILE";
		public static final String IMPORT_OT_DATA = "IMPORT_OT_DATA";
		public static final String UPLOAD_FORECAST = "UPLOAD_FORECAST";
		public static final String UPDATE_PRODUCT_IMAGE = "UPDATE_PRODUCT_IMAGE";
		public static final String UPLOAD_COMPANY_NAME = "UPLOAD_COMPANY_NAME";
		public static final String UPLOAD_USER="UPLOAD_USER";
		public static final String UPLOAD_PTT_BLUE_CARD="UPLOAD_PTT_BLUE_CARD";
	}
	public class DOWNLOAD{
		public static final String DOWNLOAD_ATTACH_FILE = "DOWNLOAD_ATTACH_FILE";
		public static final String DOWNLOAD_OLD_ATTACH_FILE = "DOWNLOAD_OLD_ATTACH_FILE";
		public static final String DOWNLOAD_EXCEL= "DOWNLOAD_EXCEL";
		public static final String DOWNLOAD_WITHDRAW_AUTHORITY_EXCEL= "DOWNLOAD_WITHDRAW_AUTHORITY_EXCEL";
		public static final String DOWNLOAD_LINK_REPORT="DOWNLOAD_LINK_REPORT";
	}
	public class EXCEL_ID{
		public static final String IMPORT_WITHDRAW_AUTHORITY = "IMPORT_WITHDRAW_AUTHORITY";
	}
	public class EXCEL_REJECT_REASON{
		public static final String  INCORRECT_ID_FORMAT = "\u0E23\u0E39\u0E1B\u0E41\u0E1A\u0E1A\u0E23\u0E2B\u0E31\u0E2A\u0E1E\u0E19\u0E31\u0E01\u0E07\u0E32\u0E19\u0E44\u0E21\u0E48\u0E16\u0E39\u0E01\u0E15\u0E49\u0E2D\u0E07";
		public static final String  ID_NOT_FOUND = "\u0E44\u0E21\u0E48\u0E1E\u0E1A\u0E23\u0E2B\u0E31\u0E2A\u0E1E\u0E19\u0E31\u0E01\u0E07\u0E32\u0E19\u0E19\u0E35\u0E49\u0E43\u0E19\u0E23\u0E30\u0E1A\u0E1A";
		public static final String  ID_DUPLICATE = "\u0E23\u0E2B\u0E31\u0E2A\u0E1E\u0E19\u0E31\u0E01\u0E07\u0E32\u0E19\u0E0B\u0E49\u0E33";
	
	}
	public class Product{
		public static final String CREDIT_CARD = "CC";
		public static final String K_EXPRESS_CASH = "KEC";
		public static final String K_PERSONAL_LOAN = "KPL";
	}
	public class INBOX_PRIORITY{
		public static final String SPECIAL = "Special";
		public static final String URGENT = "Urgent";
		public static final String HIGH = "High";
		public static final String NORMAL = "Normal";
	}
	public class ORIG_APPLICATION_GROUP_PRIORITY{
		public static final String SPECIAL = "1";
		public static final String URGENT = "2";
		public static final String HIGH = "3";
		public static final String NORMAL = "4";
	}
	public class ORIG_APPLICATION_GROUP_JOB_TYPE{
		public static final String SEND_BACK_APPLICATION = "B";
		public static final String NEW_JOB = "N";
		public static final String CONTACT_CUSTOMER_HR = "C";
		public static final String DOCUMENT_RETURNED = "D";
		 
	}
	public class ROLE{
		public static final String DE1_1 = "DE1_1";
		public static final String DE1_2 = "DE1_2";
		public static final String DE2 = "DE2";
		public static final String CA = "CA";
		public static final String VT = "VT";
		public static final String DV = "DV";
		public static final String FRAUD = "FR";
		public static final String DVS1 = "DVS1";
		public static final String DVS1S3 = "DVS1S3";
		public static final String BPMDE1_2 = "DE1.2";
	}
	public class FIELD_TYPE{
		public static final String TEXT_BOX = "TEXTBOX";
		public static final String DROP_DOWN = "DROPDOWN";
	}
	public class ORIG_DOC_CHECK_LIST_RECEIVE{
		public static final String NOT_RECEIVE_DOC = "N";
		public static final String RECEIVE_DOC = "R";
		public static final String OVERRIDE_DOC = "O";
		public static final String TRACK_DOC = "T";
	}
	public class XRULES_REQUIRED_DOC_DOCUMENT_GROUP_CODE_DESC{
		public static final String MANDATORY = "M";
	}
	public class APPLICATION_TYPE{
		public static final String NORMAL = "Normal";
		public static final String INCREASE = "Increase";
		public static final String ADD_SUP = "AddSup";
	}
	public class SALARY_TYPE{
		public static final String SALARY = "S";
		public static final String ADDITIONAL = "A";
	}
	public class IMPLEMENT_TYPE{
		public static final String WEBSITE = "WEBSITE";
		public static final String DECISION = "DECISION";
		public static final String DOCUMENT_OF_PROJECT= "DOCUMENT_OF_PROJECT";
		public static final String PRODUCT_OF_PROJECT  = "PRODUCT_OF_PROJECT";
		public static final String PROJECT_CODE = "PROJECT_CODE";
		public static final String VERIFICATION = "VERIFICATION";
		public static final String VERIFICATION_VALIDATION= "VERIFICATION_VALIDATION";
		public static final String QUESTION_FIELDS= "QUESTION_FIELDS";
		public static final String SUMMARY_NORMAL= "SUMMARY_NORMAL";
		public static final String SUMMARY_ADDSUP= "SUMMARY_ADDSUP";
		public static final String SUMMARY_INCREASE= "SUMMARY_INCREASE";
		public static final String COMPARE_SENSITIVE= "COMPARE_SENSITIVE";
		public static final String CIS_COMPARE= "CIS_COMPARE";
		public static final String VERIFY_PHONE_NO = "VERIFY_PHONE_NO";
		public static final String VERIFY_PHONE_NO_DUMMY = "VERIFY_PHONE_NO_DUMMY";
		public static final String VERIFY_QUESTION_HR = "VERIFY_QUESTION_HR";
		public static final String VERIFY_QUESTION_CUSTOMER = "VERIFY_QUESTION_CUSTOMER";
		public static final String APPROVAL_RESULT = "APPROVAL_RESULT";
		public static final String VERIFY_OTHER_QUESTION = "VERIFY_OTHER_QUESTION";
		public static final String VERIFY_CUSTOMER_PAYMENTS = "VERIFY_CUSTOMER_PAYMENTS";
		public static final String VERIFY_CUSTOMER_CIS = "VERIFY_CUSTOMER_CIS";
		public static final String VERIFY_CUSTOMER_EMAIL = "VERIFY_CUSTOMER_EMAIL";
		public static final String VERIFY_CUSTOMER_ATM_SERVICE = "VERIFY_CUSTOMER_ATM_SERVICE";
		public static final String VERIFY_CUSTOMER_CASH_TRANSFER = "VERIFY_CUSTOMER_CASH_TRANSFER";
		public static final String REFERENCE_PERSON_1 = "REFERENCE_PERSON_1";
		public static final String REFERENCE_PERSON_2 = "REFERENCE_PERSON_2";
		public static final String REFERENCE_PERSON_3 = "REFERENCE_PERSON_3";
		public static final String ADDRESS = "ADDRESS";
		public static final String ADDRESS_MAILING = "ADDRESS_MAILING";
		public static final String CARD_REQUEST_INCREASE_CC = "CARD_REQUEST_INCREASE_CC";
		public static final String CARD_REQUEST_INCREASE_KEC = "CARD_REQUEST_INCREASE_KEC";
		public static final String CARD_REQUEST_INCREASE_KPL = "CARD_REQUEST_INCREASE_KPL";
		public static final String PRE_TIME = "PRE_TIME";
		public static final String FOLLOWED_DOC_REASON = "FOLLOWED_DOC_REASON";
		public static final String ADD_PERSONAL_INFO = "ADD_PERSONAL_INFO";
		public static final String PRODUCT_FORM = "PRODUCT_FORM";
		public static final String NCB_REPORT = "NCB_REPORT";
	}
	public class COMPARE_SCREEN_TYPE{
		public static final String DEBT_INFO = "VERIFY_DEBT_SUBFORM";
	}
	public class OR_RESULT {
		public static final String PASS = "Pass";
		public static final String REFER = "Refer";
		public static final String POLICY_EXCEPTION = "Exception";
		public static final String FAIL = "Fail";
	}
	public class REF_LEVEL {
		public static final String APPLICATION = "A";
		public static final String PERSONAL_INFO = "P";
		public static final String LOAN = "L";
		public static final String APPLICATION_GROUP = "AG";
		public static final String PAYMENT_METHOD = "PM";
	}
	public class CARD_NO_GEN {
		public static final String CARD_NO = "CARD_NO";
		public static final String CARDLINK_CUST_NO = "CUST_NO";
		public static final String PARAM_TYPE_CARD = "CRD_NO";
		public static final String HH_MEMBERSHIP_NO = "HH_MEM_NO";
		public static final String PARAM_TYPE_MEMNO = "MEM_NO";
		public static final String PP_CARD_NO = "PP_CARD_NO";
		public static final String PARAM_CODE_PP_NO = "PP_NO";
	}
	public class AuditFlag{
		public static final String WAIT_AUDIT_DATA = "W";
		public static final String AUDIT_DATA = "Y";
	}
	public class NOTIFY_COMPLETE {
		public static final String PEGA = "PEGA";
	}
	public class RunningParamId{
		public static final String CARD_LINK_REF_NO = "CARD_LINK_REF_NO";
	}
	public class RunningParamType{
		public static final String CARD_LINK = "CARD_LINK";
	}
	public class UPLOAD_FILE_MANUAL{
		public static final String UPLOAD_SOLO_FILE = "UPLOAD_SOLO_FILE";
		public static final String UPLOAD_CAP_PORT = "UPLOAD_CAP_PORT";
		public static final String UPLOAD_CO_BRAND_CUSTOMER = "UPLOAD_CO_BRAND_CUSTOMER";
		public static final String UPLOAD_BILLING_CYCLE = "UPLOAD_BILLING_CYCLE";
		public static final String UPLOAD_KBANK_SALARY = "UPLOAD_KBANK_SALARY";
		public static final String UPLOAD_THAIBEV_PARTNER = "UPLOAD_THAIBEV_PARTNER";
		public static final String UPLOAD_CARD_HIERACHY = "UPLOAD_CARD_HIERACHY";
		public static final String UPLOAD_CARD_TRUSTED_COMPANY = "UPLOAD_CARD_TRUSTED_COMPANY";
		public static final String UPLOAD_COA_MAPPING = "UPLOAD_COA_MAPPING";
		public static final String UPLOAD_FICS_MAPPING = "UPLOAD_FICS_MAPPING";
		public static final String UPLOAD_COMPANY_GROUP = "UPLOAD_COMPANY_GROUP";
		
		public static final String UPLOAD_LOAN_XREF = "UPLOAD_LOAN_XREF";
		public static final String UPLOAD_LOAN_INT_TYPE_XREF = "UPLOAD_LOAN_INT_TYPE_XREF";
		public static final String UPLOAD_PRICING= "UPLOAD_PRICING";
	}
	public class DOWNLOAD_INVALID_FILE_MANUAL{
		public static final String DOWNLOAD_INVALID_SOLO_FILE = "DOWNLOAD_INVALID_SOLO_FILE";
		public static final String DOWNLOAD_INVALID_CAP_PORT = "DOWNLOAD_INVALID_CAP_PORT";
		public static final String DOWNLOAD_INVALID_CO_BRAND_CUSTOMER = "DOWNLOAD_INVALID_CO_BRAND_CUSTOMER";
		public static final String DOWNLOAD_INVALID_BILLING_CYCLE = "DOWNLOAD_INVALID_BILLING_CYCLE";
		public static final String DOWNLOAD_INVALID_KBANK_SALARY = "DOWNLOAD_INVALID_KBANK_SALARY";
		public static final String DOWNLOAD_INVALID_THAIBEV_PARTNER = "DOWNLOAD_INVALID_THAIBEV_PARTNER";
		public static final String DOWNLOAD_INVALID_CARD_HIERACHY = "DOWNLOAD_INVALID_CARD_HIERACHY";
		public static final String DOWNLOAD_INVALID_CARD_TRUSTED_COMPANY = "DOWNLOAD_INVALID_CARD_TRUSTED_COMPANY";
		public static final String DOWNLOAD_INVALID_COA_MAPPING = "DOWNLOAD_INVALID_COA_MAPPING";
		public static final String DOWNLOAD_INVALID_FICS_MAPPING = "DOWNLOAD_INVALID_FICS_MAPPING";
		public static final String DOWNLOAD_INVALID_COMPANY_GROUP = "DOWNLOAD_INVALID_COMPANY_GROUP";
		public static final String DOWNLOAD_INVALID_LINK = "EXCEL";
		public static final String DOWNLOAD_INVALID_USER = "DOWNLOAD_INVALID_USER";
		public static final String DOWNLOAD_INVALID_PTT_BLUE_CARD = "DOWNLOAD_INVALID_PTT_BLUE_CARD";
		
		public static final String DOWNLOAD_INVALID_LOAN_XREF = "DOWNLOAD_INVALID_LOAN_XREF";
		public static final String DOWNLOAD_INVALID_LOAN_INT_TYPE_XREF = "DOWNLOAD_INVALID_LOAN_INT_TYPE_XREF";
		public static final String DOWNLOAD_INVALID_PRICING = "DOWNLOAD_INVALID_PRICING";
	}
	public class LOAN_SETUP_SEQUENCE_NAME{
		public static final String PA_CLAIM_ID_PK = "PA_CLAIM_ID_PK";
		public static final String PA_STAMP_DUTY_ID_PK = "PA_STAMP_DUTY_ID_PK";
	}
	public class CAPPORT_SEQUENCE_NAME{
		public static final String CPT_CAP_PORT_ID_PK = "CPT_CAP_PORT_ID_PK";
	}
	public class SOURCE_NAME{
		public static final String TABLET = "Tablet";
		public static final String CJD = "CJD";
	}
	public static final String WM_TASK_ID_PK_SEQUENCE = "WM_TASK_ID_PK";
	public class IMType{
		public static final String NORMAL = "NORMAL";
		public static final String E_APP = "EAPP";
	}
	
	public class TASK_STATUS{
		public static final String RUNNING = "1";
		public static final String WAIT_ERROR_COLLECT = "2";
		public static final String WAIT_FOR_ASSIGN = "3";
		public static final String COMPLETE = "4";
	}
	public static final String ACCOUNT_ID_PK = "ACCOUNT_ID_PK";
	public static final String EAPP_INSTANCE_PREFIX = "00-";
	public static final String FOUND_EAPP_DV_CENTRAL_QUEUE = "FOUND_EAPP_DV_CENTRAL_QUEUE";
}

