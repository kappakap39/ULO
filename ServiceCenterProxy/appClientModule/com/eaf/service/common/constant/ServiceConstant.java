package com.eaf.service.common.constant;


public class ServiceConstant {
	public ServiceConstant(){
		super();
	}
	public static final String OUT = "O";
	public static final String IN = "I";
	public static final String TRANSECTION = "T";
	public static final String YES = "Y";
	public static final String NO = "N";
	public class Status{
		public static final int EAI_SUCCESS_CODE = 0;
		public static final int SMS_SUCCESS_CODE = 0;
		public static final String SUCCESS = "00";
		public static final String BUSINESS_EXCEPTION = "10";
		public static final String SYSTEM_EXCEPTION = "20";
		public static final String WARNING = "30";
	}
	public class RestStatus{
		public static final String STATUS_SUCCESS = "S";
		public static final String STATUS_FAIL="F";
		public static final String HTTP_STATUS_SUCCESS = "200";
	}
	public class StatusDesc{
		public static final String SUCCESS = "Success";
		public static final String BUSINESS_EXCEPTION = "Bussiness Exception";
		public static final String SYSTEM_EXCEPTION = "System Exception";
		public static final String WARNING = "Warning";
	}
	public class ServiceId{
		public static final String UpdateCallOperator = "UpdateCallOperator";
		public static final String SubmitApplication = "SubmitApplication";
		public static final String FollowUpResult = "FollowUpResult";
		public static final String FullFraudResult = "FullFraudResult";
		public static final String FullFraudCheck = "FullFraudCheck";
		public static final String CheckProductDup = "CheckProductDup";
		public static final String ESubmitApplication = "ESubmitApplication";
		public static final String ESubmitApplicationIM = "ESubmitApplicationIM";
		public static final String ESubmitApplicationWF = "ESubmitApplicationWF";
		public static final String ECreateDocSet = "ECreateDocSet";
		public static final String PostApproval = "PostApproval";
		public static final String InitialApplication = "InitialApplication";
		public static final String CJDSubmitApplication = "CJDSubmitApplication";
		public static final String CJDInitialApplication = "CJDInitialApplication";
		public static final String CJDSubmitCardlinkAction = "CJDSubmitCardlinkAction";
		public static final String CJDCardlinkAction = "CJDCardlinkAction";
		public static final String CJDSubmitIncomeAction = "CJDSubmitIncomeAction";
		public static final String CJDIncomeAction = "CJDIncomeAction";
		public static final String CJDCardResult = "CJDCardResult";
		public static final String CJDInitialIncomeAction = "CJDInitialIncomeAction";
		public static final String CJDIMAction = "CJDIMAction";
		public static final String CJDMLSResult = "CJDMLSResult";
	}
	public class ErrorSeverityCode{
		public static final String SYSTEM_UNKNOW = "00";
		public static final String FATAL = "01";
		public static final String ERROR = "02";
		public static final String WARNING = "03";
		public static final String INFO = "04";
		public static final String DEBUG = "05";
		public static final String TRACE = "06";
	}
}
