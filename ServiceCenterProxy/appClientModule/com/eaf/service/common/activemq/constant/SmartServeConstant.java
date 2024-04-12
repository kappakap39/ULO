package com.eaf.service.common.activemq.constant;

public class SmartServeConstant {
	public static String PREFIX_FILE_PROPERTIES = "smartserve";
	public static String PREFIX_JMS_PROPERTIES = "jms.smartServe.";
	public static String PREFIX_SS_PROPERTIES = "smartServe.";
	public static String HOST = "SmartServ";

	//public static String JMS_JNDI_NAME = SpaceHubConfigManager.getConfig(PREFIX_FILE_PROPERTIES, PREFIX_JMS_PROPERTIES + "jndiName"); // "jms/pegaQueueConnectionFactory";

	public static String FLP01_FUNCTIONNAME = "SmartServeCaseCreationForFLP01";
	public static String FLP01_ACTION = "ConsentComplete";

	public static String FLP02_FUNCTIONNAME = "SmartServeCaseCreationForFLP02";
	public static String FLP02_ACTION = "DocumentComplete";

	public static String FLP03_FUNCTIONNAME = "SmartServeCaseCreationForFLP03";
	public static String FLP03_ACTION = "FollowUpDocument";

	public static String FLP04_FUNCTIONNAME = FLP02_FUNCTIONNAME;
	public static String FLP04_ACTION = "MODocumentComplete";
	
	public static String FLP05_FUNCTIONNAME = "FLPExceptionRequest";
	
	public static String IDENTDOC_TYPE_ID = "1";
	public static String IDENTDOC_TYPE_PASSPORT = "2";
	public static String IDENTDOC_TYPE_ALIEN = "3";
	public static String IDENTDOC_TYPE_CORPORATE = "4";
	public static String LANGUAGE_TH = "th";
	public static String LANGUAGE_EN = "en";
	
	public static String DOC_INCOMPLETE_TYPE_1 = "DocIncompleteType1";
	public static String DOC_INCOMPLETE_TYPE_2 = "DocIncompleteType2";
	public static String DOC_INCOMPLETE_TYPE_3 = "DocIncompleteType3";
	public static String DOC_INCOMPLETE_TYPE_4 = "DocIncompleteType4";
}
