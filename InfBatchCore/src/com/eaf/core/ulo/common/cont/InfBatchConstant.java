package com.eaf.core.ulo.common.cont;


public class InfBatchConstant {	
	
	public class InfBatchLib{
		public static final String InfBatchLib = "InfBatchLib";
		public static final String IMBatchLib = "IMBatchLib";
		public static final String MFBatchLib = "MFBatchLib";
		public static final String Jasper = "jasper";
		public static final String Lib = "lib";
		public static final String Report = "report";
		public static final String Resource = "resource";
	}	
	public class Status{
		public static final int EAI_SUCCESS_CODE = 0;
		public static final String SUCCESS = "00";
		public static final String BUSINESS_EXCEPTION = "10";
		public static final String SYSTEM_EXCEPTION = "20";
		public static final String WARNING = "30";
	}
	public class StatusDesc{
		public static final String SUCCESS = "SUCCESS";
		public static final String FAIL = "FAIL";
		public static final String ERROR = "ERROR";
	}
	public class CARD_LINK_RESULT_RESPONSE_CODE{
		public static final String SUCCESS = "00";
		public static final String REJECT = "01";
	}
	public class CARDLINK_RESULT_FLAG{
		public static final String SUCCESS = "S";
		public static final String FAIL = "F";
	}
	public class CARDLINK_RESULT_STATUS{
		public static final String ACTIVE = "A";
		public static final String INACTIVE = "I";
	}
	public class ReportParam{
		public static final String REPORT_ON_REQUEST = "ON_REQUEST";
		public static final String REPORT_TEMPLATE = "TEMPLATE";
		public static final String REPORT_NAME = "REPORT_NAME";
		public static final String INPUT_PATH = "INPUT_PATH";
		public static final String INPUT_NAME = "INPUT_NAME";
		public static final String OUTPUT_PATH = "OUTPUT_PATH";
		public static final String OUTPUT_NAME = "OUTPUT_NAME";
		public static final String JASPER_REPORT_PATH = "JASPER_REPORT_PATH";
		public static final String JASPER_GENERATE = "JASPER_GENERATE";
		public static final String LAYOUT = "Layout";
		public static final String DATA_SET = "Data Set";
		public static final String MODULE_ID = "MODULE_ID";
		public static final String MAX_TASK = "MAX_TASK";
		public static final String BATCH_ID = "BATCH_ID";
		public static final String BATCH_TYPE = "BATCH_TYPE";
		public static final String TASK_ID = "TASK_ID";
		public static final String BACKUP_LASTEST = "BACKUP_LASTEST";
		public static final String GENERATE = "GENERATE";
		public static final String GENERATE_TYPE = "GENERATE_TYPE";
		public static final String FILE_ENCODING = "FILE_ENCODING";
		public static final String FORCE_BACKUP = "FORCE_BACKUP";
		public static final String START_DATE = "START_DATE";
		public static final String END_DATE = "END_DATE";
		public static final String OR_CC_LAYOUT = "CC";
		public static final String OR_KEC_LAYOUT = "KEC";
		public static final String OR_KPL_LAYOUT = "KPL";
		public static final String OR_XPC_LAYOUT = "XPC";
		public static final String OR_XPL_LAYOUT = "XPL";
	}
	
	//Daily Report
	public static final String RE_R003 = "RE_R003";
	public class RE_R004{
		public static final String TOTAL = "RE_R004_TOTAL";
		public static final String NEW = "RE_R004_NEW";
		public static final String ADD_CC = "RE_R004_ADD_CC";
		public static final String UPGRADE_CC = "RE_R004_UPGRADE_CC";
		public static final String INCREASE = "RE_R004_INCREASE";
		public static final String CC_NEW_KEC = "RE_R004_CC_NEW_KEC";
		public static final String CC_ADD_KEC = "RE_R004_CC_ADD_KEC";
		public static final String CC_UPGRADE_KEC = "RE_R004_CC_UPGRADE_KEC";
		public static final String KEC_KPL = "RE_R004_KEC_KPL";
		public static final String KEC_HL = "RE_R004_KEC_HL";
		public static final String KEC_SME = "RE_R004_KEC_SME";
		public static final String KEC_KL = "RE_R004_KEC_KL";
		public static final String CC_HL = "RE_R004_CC_HL";
		public static final String CC_SME = "RE_R004_CC_SME";
		public static final String CC_KL = "RE_R004_CC_KL";
	}
	public class RE_R006{
		public static final String NEW = "RE_R006_NEW";
		public static final String INCREASE = "RE_R006_INCREASE";
	}
	public class RE_R021{
		public static final String APPROVE = "RE_R021_APPROVE";
		public static final String REJECT = "RE_R021_REJECT";
	}
	public static final String RE_R007 = "RE_R007";
	public static final String RE_R008 = "RE_R008";
	public static final String RE_R010 = "RE_R010";
	public static final String RE_R011 = "RE_R011";
	public static final String RE_R012 = "RE_R012";
	public static final String RE_R014 = "RE_R014";
	public static final String RE_R015 = "RE_R015";
	
	//Weekly Report
	public static final String RE_R002 = "RE_R002";
	
	//Bi-Weekly Report
	public static final String RE_R001 = "RE_R001";
	
	//Monthly Report
	public static final String RE_R005 = "RE_R005";
	public static final String RE_R009 = "RE_R009";
	public static final String RE_R011_1 = "RE_R011_1";
	public static final String RE_R011_2 = "RE_R011_2";
	
	//Quarterly Report
	public static final String RE_R020 = "RE_R020";
	
	//text file
	public class CARDLINK{
		public static final String ACCOUNT_SETUP = "CARDLINK_ACCOUNT_SETUP";
		public static final String CASH_DAY1 = "CARDLINK_CASH_DAY1";
		public static final String CUSTOMER_OCCUPATION = "CARDLINK_CUSTOMER_OCCUPATION";
	}
	
	public static final String CRM_VLINK_RT_SALE_PERFORMANCE = "CRM_VLINK_RT_SALE_PERFORMANCE";
	public static final String KM_ALERT_PERFORMANCE = "KM_ALERT_PERFORMANCE";
	public static final String KVI_CLOSE_APPLICATION = "KVI_CLOSE_APPLICATION";
	
	public class MAKE_AFP_PRINT{
		public static final String REJECT_NON_NCB = "MAKE_AFP_PRINT_REJECT_NON_NCB";
		public static final String REJECT_NCB = "MAKE_AFP_PRINT_REJECT_NCB";
		public static final String APPROVE = "MAKE_AFP_PRINT_APPROVE";
	}
	
	public static final String MAKE_PDF = "MAKE_PDF";
	
	public static final String FLAG_YES = "Y";
	public static final String FLAG_NO = "N";
	public static final String STATUS_COMPLETE = "C";
	public static final String STATUS_ERROR = "E";
	public static final String DISABLE = "DISABLE";
	
	public class PATH{
		public static final String PREFIX = "PREFIX";
		public static final String INTERFACE = "INTERFACE";
		public static final String REPORT = "REPORT";
		public static final String LOG_PATH = "LOG_PATH";
		public static final String LOG_FILE_NAME = "LOG_FILE_NAME";
		public static final String BACKUP = "BACKUP_PATH";
	}
	public class ResultCode{
		public static final String SUCCESS = "0";
		public static final String FAIL = "-1";
		public static final String WARNING = "-2";
	}
	
	public class executeResult{
		public static final String NO_DATA = "1:0";
	}
	
	public class batchType{
		public static final String BACKUP = "BACKUP";
		public static final String ARCHIVE = "ARCHIVE";
		public static final String FILE_TRANSFER = "FILE_TRANSFER";
		public static final String PURGE = "PURGE";
		public static final String REORG = "REORG";
		public static final String MONITORING = "MONITORING";
		public static final String REPORT = "REPORT";
		public static final String IMPORT_DATA = "IMPORT_DATA";
		public static final String EXPORT_DATA = "EXPORT_DATA";
		public static final String OTHER = "OTHER";
	}
	
	public class task{
		public static final String REPORT_TASK = "REPORT_TASK";
		public static final String NOTIFICATION_WARE_HOUSE_INCOMPLETE_TASK = "NOTIFICATION_WARE_HOUSE_INCOMPLETE_TASK";
		public static final String NOTIFICATION_WARE_HOUSE_NOT_RECEIVE_TASK = "NOTIFICATION_WARE_HOUSE_NOT_RECEIVE_TASK";
		public static final String NOTIFICATION_WARE_HOUSE_RETURN_TASK = "NOTIFICATION_WARE_HOUSE_RETURN_TASK";
		public static final String LOTUS_NOTE_DSA_TASK = "LOTUS_NOTE_DSA_TASK";
		public static final String APPROVE_LETTER_TASK = "APPROVE_LETTER_TASK";
		public static final String WAREHOUSE_DISPOSE_TASK = "WAREHOUSE_DISPOSE_TASK";
		public static final String DELETE_NCB_TASK = "DELETE_NCB_TASK";
		public static final String DAILY_REPORT2_SEQUENCE_TASK = "DAILY_REPORT2_SEQUENCE_TASK";
		public static final String EDOCGEN_TASK = "EDOCGEN_TASK";
		public static final String REFRESH_SUMMARY_TABLE_TASK = "REFRESH_SUMMARY_TABLE_TASK";
		public static final String CATALOG_AND_TRANSFORM_OLD_APP_TASK = "CATALOG_AND_TRANSFORM_OLD_APP_TASK";
		public static final String CATALOG_OLD_APP_TASK = "CATALOG_OLD_APP_TASK";
		public static final String TRANSFORM_OLD_APP_TASK = "TRANSFORM_OLD_APP_TASK";
		public static final String DELETE_OLD_APP_TASK = "DELETE_OLD_APP_TASK";
		public static final String DELETE_OLD_APP_DB01_TASK = "DELETE_OLD_APP_DB01_TASK";
		public static final String DELETE_OLD_APP_DB03_TASK = "DELETE_OLD_APP_DB03_TASK";
		public static final String DELETE_OLD_APP_IMG_TASK = "DELETE_OLD_APP_IMG_TASK";
		public static final String DELETE_OLD_APP_ARC2_TASK = "DELETE_OLD_APP_ARC2_TASK";
		public static final String DELETE_OLD_APP_UNUSED_BPM_TASK = "DELETE_OLD_APP_UNUSED_BPM_TASK";
		public static final String DELETE_OLD_APP_MLP_TASK = "DELETE_OLD_APP_MLP_TASK";
	}
	
	public class message{
		public static final String DATE_HEADER_REPORT = "\u0E15\u0E31\u0E49\u0E07\u0E41\u0E15\u0E48\u0E27\u0E31\u0E19\u0E17\u0E35\u0E48 {DATE_FROM} \u0E16\u0E36\u0E07 \u0E27\u0E31\u0E19\u0E17\u0E35\u0E48 {DATE_TO}";
		public static final String CONDITION_HEADER_REPORT = "\u0E40\u0E07\u0E37\u0E48\u0E2D\u0E19\u0E44\u0E02 : {CONDITION}";
		public static final String AS_OF_DATE_HEADER_REPORT = "Date as of {AS_OF_DATE}";
//		public static final String DATE_TYPE = "Date Type : ";
//		public static final String DATE_FROM = "Date From : ";
//		public static final String DATE_TO = "Date To : ";
//		public static final String PROJECT_CODE = "Project Code : ";
//		public static final String DOC_FIRST_COMPLETE_FLAG = "Document First Complete Flag : ";
//		public static final String STATION_FROM = "Station From : ";
//		public static final String STATION_TO = "Station To : ";
//		public static final String APPLICATION_STATUS = "Application Status : ";
//		public static final String PRODUCT_CRITERIA = "Product Criteria : ";
//		public static final String BRANCH_REGION = "Branch Region : ";
//		public static final String BRANCH_ZONE = "Branch Zone : ";
//		public static final String DSA_ZONE = "DSA Zone : ";
//		public static final String NBD_ZONE = "NBD Zone : ";
//		public static final String CHANNEL = "Channel : ";
		public static final String CREDIT_LIMIT = "\u0E27\u0E07\u0E40\u0E07\u0E34\u0E19";
		public static final String BATH = "\u0E1A\u0E32\u0E17";
		public static final String TOT_CREDIT_LIMIT = "\u0E27\u0E07\u0E40\u0E07\u0E34\u0E19\u0E23\u0E27\u0E21";
		public static final String CASH_CREDIT_LIMIT = "\u0E42\u0E14\u0E22\u0E27\u0E07\u0E40\u0E07\u0E34\u0E19\u0E02\u0E2D\u0E07\u0E04\u0E38\u0E13\u0E04\u0E37\u0E2D";
		public static final String AMOUNT = "\u0E08\u0E33\u0E19\u0E27\u0E19";
	}
	
	public class ReportCondition{
		public static final String DATE_TYPE = "\u0e1b\u0e23\u0e30\u0e40\u0e20\u0e17\u0e27\u0e31\u0e19\u0e17\u0e35\u0e48";
		public static final String DATE_TYPE_APPLICATION_DATE = "\u0e27\u0e31\u0e19\u0e17\u0e35\u0e48\u0e43\u0e1a\u0e2a\u0e21\u0e31\u0e04\u0e23\u0e40\u0e02\u0e49\u0e32\u0e23\u0e30\u0e1a\u0e1a";
		public static final String DATE_TYPE_LASTDECISION_DATE = "\u0e27\u0e31\u0e19\u0e17\u0e35\u0e48\u0e1e\u0e34\u0e08\u0e32\u0e23\u0e13\u0e32";
		public static final String PROJECT_NO = "\u0e23\u0e2b\u0e31\u0e2a\u0e42\u0e04\u0e23\u0e07\u0e01\u0e32\u0e23";
		public static final String COMPLETE_REPORT = "\u0e04\u0e27\u0e32\u0e21\u0e04\u0e23\u0e1a\u0e16\u0e49\u0e27\u0e19\u0e40\u0e2d\u0e01\u0e2a\u0e32\u0e23";
		public static final String COMPLETE_REPORT_A = "\u0E40\u0E2D\u0E01\u0E2A\u0E32\u0E23\u0E17\u0E31\u0E49\u0E07\u0E2B\u0E21\u0E14";
		public static final String COMPLETE_REPORT_Y = "\u0e04\u0e23\u0e1a\u0e04\u0e23\u0e31\u0e49\u0e07\u0e41\u0e23\u0e01";
		public static final String COMPLETE_REPORT_N = "\u0e44\u0e21\u0e48\u0e04\u0e23\u0e1a\u0e04\u0e23\u0e31\u0e49\u0e07\u0e41\u0e23\u0e01";
		public static final String STATION_FROM = "Station From";
		public static final String STATION_TO = "To";
		public static final String REPORT_FINAL = "Final";
		public static final String BY_PRODUCT = "By Product";
		public static final String PRODUCT = "Product";
		public static final String PRODUCT_CC = "Credit Card";
		public static final String PRODUCT_KEC = "XPC";
		public static final String PRODUCT_KEC_INC = "XPC(Increase)";
		public static final String PRODUCT_KPL = "XPL";
		public static final String PRODUCT_KPL_INC = "XPL(Increase)";
		public static final String PRODUCT_CC_ADD = "Credit Card(Add)";
		public static final String PRODUCT_CC_UP = "Credit Card(Upgrade)";
		public static final String PRODUCT_CC_INC = "Credit Card(Increase)";
		public static final String PRODUCT_CC_NEW = "Credit Card(New)";
		public static final String PRODUCT_CC_KEC = "CC + XPC"; 
		public static final String PRODUCT_CC_NEW_KEC = "CC(New) + XPC"; 
		public static final String PRODUCT_CC_ADD_KEC = "CC(Add) + XPC"; 
		public static final String PRODUCT_CC_UPG_KEC = "CC(Upgrade) + XPC";
		public static final String PRODUCT_KEC_KPL = "XPC + KPL (All Type)";
		public static final String PRODUCT_KEC_HL = "XPC + HL  (All Type)";
		public static final String PRODUCT_KEC_SME = "XPC + SME (All Type)";
		public static final String PRODUCT_KEC_KL = "XPC + KL (All Type)";
		public static final String PRODUCT_CC_HL = "CC + HL  (All Type)";
		public static final String PRODUCT_CC_SME = "CC + SME (All Type)";
		public static final String PRODUCT_CC_KL = "CC + KL (All Type)";
		public static final String BY_CHANNEL = "By Channel";
		public static final String CHANNEL = "Channel";
		public static final String CHANNEL_BRANCH = "Branch";
		public static final String CHANNEL_BRANCH_REGION = "\u0e20\u0e32\u0e04";
		public static final String CHANNEL_BRANCH_ZONE = "\u0e40\u0e02\u0e15";
		public static final String CHANNEL_DSA = "DSA";
		public static final String CHANNEL_DSA_EXPAND_ZONE = "\u0e40\u0e02\u0e15\u0e01\u0e32\u0e23\u0e02\u0e22\u0e32\u0e22";
		public static final String CHANNEL_NBD = "NBD";
		public static final String CHANNEL_NBD_ZONE = "\u0e40\u0e02\u0e15";
	}
	public class ACTIVE_STATUS{
		public static final String ENABLED = "E";
		public static final String DISABLE = "D";
	}
	public class BPM_RESULT_CODE{
		public static final String SUCCESS = "S";
		public static final String FAIL = "E";
	}
	public class RUNNING_PARAM_STACK{
		public static final String PROCESS_PURGE = "PURGE";
		public static final String PROCESS_MIGRATE = "MIGRATE";
		public static final String PROCESS_WARNING = "WARNING";
	}
	public static final String SYSTEM_DATE_FORMAT = "yyyy-MM-dd";
	public static final String SETUP_LOAN = "SETUP_LOAN";
}
