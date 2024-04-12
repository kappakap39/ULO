package com.eaf.core.ulo.service.template.cont;


public class TemplateBuilderConstant {
    public class TemplateType{
        public static final String EMAIL = "EMAIL";
        public static final String LETTER = "LETTER";
        public static final String SMS = "SMS";
        public static final String K_MOBILE = "KMOBILE";
        public static final String REJECT_LETTER = "REJECT_LETTER";
        public static final String REJECT_LETTER_PDF = "REJECT_LETTER_PDF";
        public static final String MAKEPDF = "MAKEPDF";
        public static final String MAKEPDF_CH = "MAKEPDF_CH";
        public static final String PERSONAL_ID = "PERSONAL_ID";
        public static final String PERSONAL_TYPE = "PERSONAL_TYPE";
        public static final String MLS_KPLUS = "MLS_K+";
    }
    public class TemplateVariableName {
        public static final String PRODUCT_NAME = "PRODUCT_NAME";
        public static final String PRODUCT_NAME_TH = "PRODUCT_NAME_TH";
        public static final String PRODUCT_NAME_EN = "PRODUCT_NAME_EN";
        public static final String PRODUCT_NAME_TH_EN = "PRODUCT_NAME_TH_EN";
        public static final String CREDIT_LIMITE ="CREDIT_LIMIT";
        public static final String APPLICATION_NO ="APPLICATION_NO";
        public static final String BRANCH_NAME   = "BRANCH_NAME";
        public static final String CUSTOMER_NAME ="CUSTOMER_NAME";
        public static final String CUSTOMER_NAME_TH ="CUSTOMER_NAME_TH";
        public static final String CUSTOMER_NAME_EN ="CUSTOMER_NAME_EN";
        public static final String REFERENCE_NO = "REFERENCE_NO";
        public static final String PO_TELEPHONE_NO ="PO_TELEPHONE_NO";
        public static final String PO_EMAIL = "EMAIL_PO_CONTACTCENTER";
        public static final String IDNO= "ID_NO";
        public static final String ACCOUNT_NO = "ACCOUNT_NO";
        public static final String ACCOUNT_NO_LAST_4_DIGIT = "ACCOUNT_NO_LAST_4_DIGIT";
        public static final String ACCOUNT_NAME = "ACCOUNT_NAME";
        public static final String AMOUNT    = "AMOUNT";
        public static final String FIRSTNAME_LASTNAME = "FIRSTNAME_LASTNAME";
        public static final String EMAIL_POLICY_PO = "EMAIL_POLICY_PO";
        public static final String APPROVE = "APPROVE";
        public static final String REJECT = "REJECT";
        public static final String BORROWED_DATE = "BORROWED_DATE";
        public static final String DUE_DATE = "DUE_DATE";
        public static final String DEPARTMENT_TEAM = "DEPARTMENT_TEAM";
        public static final String DEPARTMENT_NAME = "DEPARTMENT_NAME";
        public static final String TELEPHONE_NO = "TELEPHONE_NO";
        public static final String TIMES = "TIMES";
        public static final String REMARK = "REMARK";
        public static final String NO = "NO";
        public static final String SCANNED_DATE = "SCANNED_DATE";
        public static final String INCOMPLETE_DOCUMENT_NAME = "INCOMPLETE_DOCUMENT_NAME";
        public static final String CUSTOMER_NAME_DUE_DATE = "CUSTOMER_NAME_DUE_DATE";
        public static final String DOCUMENT_DETAIL = "DOCUMENT_DETAIL";
        public static final String ADDRESS_LINE_1 = "ADDRESS1";
        public static final String ADDRESS_LINE_2 = "ADDRESS2";
        public static final String CONTACT_CENTER_NO_PRODUCT = "CONTACT_CENTER_NO_PRODUCT";
        public static final String CONTACT_CENTER_NO_CC = "CONTACT_CENTER_NO_CC";
        public static final String CONTACT_CENTER_NO_KPL = "CONTACT_CENTER_NO_KPL";
        public static final String CONTACT_CENTER_NO_KEC = "CONTACT_CENTER_NO_KEC";
        public static final String LANGUAGE_FLAG = "LANGUAGE_FLAG";
        public static final String PRODUCT_TYPE = "PRODUCT_TYPE";
        public static final String CARD_TYPE = "CARD_TYPE";
        public static final String CARD_LEVEL = "CARD_LEVEL";
        public static final String FINAL_DECISION_DATE = "FINAL_DECISION_DATE";
        public static final String APPLICATION_RECORD_ID = "APPLICATION_RECORD_ID";
        public static final String REJECTED_REASON_ALL_PRODUCT = "REJECTED_REASON_ALL_PRODUCT";
        public static final String PRODUCT_FULL_DESCRIPTION = "PRODUCT_FULL_DESCRIPTION";
        public static final String WEBSITE_ALL_PRODUCT = "WEBSITE_ALL_PRODUCT";
        public static final String WEBSITE_BY_PRODUCT = "WEBSITE_BY_PRODUCT";
        public static final String DOCUMENT_LIST = "DOCUMENT_LIST";
        public static final String EAPP_DOCUMENT_LIST = "EAPP_DOCUMENT_LIST";
        public static final String EMAIL_PRIMARY = "EMAIL_PRIMARY";
        
        public static final String NOTIFICATION_APPLICATION_TYPE_NEW= "NOTIFICATION_APPLICATION_TYPE_NEW";
        public static final String CREDIT_LIMIT = "CREDIT_LIMIT";
        public static final String CUSTOMER_NAME_SURNAME = "CUSTOMER_NAME_SURNAME";
        public static final String CREDIT_LINE = "CREDIT_LINE";
        public static final String CARD_NO ="CARD_NO";
        public static final String CUST_NO = "CUST_NO";
        public static final String MEMBER_SHIP= "MEMBER_SHIP";
        public static final String PRIORITY_PASS = "PRIORITY_PASS";
        public static final String CONTACT_POINT = "CONTACT_POINT";
        public static final String CONTACT_POINT_TH = "CONTACT_POINT_TH";
        public static final String CONTACT_POINT_EN = "CONTACT_POINT_EN";
        
        public static final String CARD_TYPE_TH = "CARD_TYPE_TH";
        public static final String CARD_TYPE_EN = "CARD_TYPE_EN";
        public static final String DOCUMENT_LIST_TH = "DOCUMENT_LIST_TH";
        public static final String DOCUMENT_LIST_OTHER = "DOCUMENT_LIST_OTHER";
        public static final String EAPP_DOCUMENT_LIST_TH = "EAPP_DOCUMENT_LIST_TH";
        public static final String EAPP_DOCUMENT_LIST_OTHER = "EAPP_DOCUMENT_LIST_OTHER";
        
        public static final String REJECT_REASON = "REJECT_REASON";
        public static final String CONTACT_NO_SPACE = "CONTACT_NO_SPACE";
        public static final String WEBSITE_NOSPACE = "WEBSITE_NOSPACE";
        public static final String BUNDLE_FULL_DESCRIPTION = "BUNDLE_FULL_DESCRIPTION";
        
//        for Capoprt Notify by Watjanarat.P
        public static final String CAP_PORT_NAME = "CAP_PORT_NAME";
        public static final String CAP_AMOUNT = "CAP_AMOUNT";
        public static final String GRANTED_AMOUNT = "GRANTED_AMOUNT";
        public static final String CAP_APPLICATION = "CAP_APPLICATION";
        public static final String GRANTED_APPLICATION = "GRANTED_APPLICATION";
        public static final String WARNING_POINT = "WARNING_POINT";
		
		public static final String TRANSFER_AMT = "TRANSFER_AMT";
		public static final String TERM = "TERM";
		public static final String INSTALLMENT = "INSTALLMENT";
		
		public static final String INTEREST = "INTEREST";
		public static final String TRANSFER_TO_LAST4_DIGITS = "TRANSFER_TO_LAST4_DIGITS";
		
		//KPL Additional - REJECT_LETTER / EDOCGen
		//public static final String FINAL_DECISION_DATE = "FINAL_DECISION_DATE";
		public static final String LETTER_NO = "LETTER_NO";
		public static final String CIS_NO = "CIS_NO";
		//public static final String APPLICATION_NO = "APPLICATION_NO";
		//public static final String PRODUCT_NAME = "PRODUCT_NAME";
		//public static final String CUSTOMER_NAME = "CUSTOMER_NAME";
		public static final String ADDRESS1 = "ADDRESS1";
		public static final String ADDRESS2 = "ADDRESS2";
		public static final String EDOC_LETTER_TEMPLATE = "EDOC_LETTER_TEMPLATE";
		public static final String SEND_METHOD = "SEND_METHOD";
		//public static final String EMAIL_PRIMARY = "EMAIL_PRIMARY";
		public static final String EDOC_EMAIL_TEMPLATE = "EDOC_EMAIL_TEMPLATE";
		public static final String LANGUAGE = "LANGUAGE";
		public static final String ATTACH_FILE = "ATTACH_FILE";
		public static final String MASTER_OR_COPY = "MASTER_OR_COPY";
		
		public static final String TH_FIRST_NAME = "TH_FIRST_NAME";
		public static final String TH_MID_NAME = "TH_MID_NAME";
		public static final String TH_LAST_NAME = "TH_LAST_NAME";
		
		public static final String EDOC_SEND_METHOD = "EDOC_SEND_METHOD";
		public static final String SEND_TO = "SEND_TO";
		
		public static final String LETTER_CHANNEL = "LETTER_CHANNEL";
		
		public static final String ARTWORK_KPLUS = "ARTWORK_KPLUS";
		public static final String ARTWORK_LANDING_PAGE = "ARTWORK_LANDING_PAGE";
		public static final String ARTWORK_PRODUCT = "ARTWORK_PRODUCT";
		public static final String MSG_CREDIT_LIMIT = "MSG_CREDIT_LIMIT";
		public static final String MSG_TOT_CREDIT_LIMIT = "MSG_TOT_CREDIT_LIMIT";
		public static final String MSG_CREDIT_LIMIT_CASH_TRANFER = "MSG_CREDIT_LIMIT_CASH_TRANFER";
		public static final String MSG_AMOUNT = "MSG_AMOUNT";
		public static final String PRODUCT_NAME_2LANG = "PRODUCT_NAME_2LANG";
		public static final String FULL_CARD_NUMBER = "FULL_CARD_NUMBER";
    }
    public class TemplateSortingName{
        public static final String ZIPCODE = "ZIPCODE";
    }
    public class TemplateName{
    	public static final String NOTIFY_MLS = "NOTIFY_MLS";
    }
}