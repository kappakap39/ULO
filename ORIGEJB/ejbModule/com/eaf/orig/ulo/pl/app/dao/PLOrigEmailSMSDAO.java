package com.eaf.orig.ulo.pl.app.dao;

import com.eaf.cache.data.CacheDataM;
import com.eaf.orig.cache.properties.DocumentReasonProperties;
import com.eaf.orig.cache.properties.GeneralParamProperties;
import com.eaf.orig.cache.properties.ProductProperties;
import com.eaf.orig.shared.model.EmailM;
import com.eaf.orig.shared.model.EmailTemplateMasterM;
import com.eaf.orig.shared.model.SMSPrepareDataM;
import com.eaf.orig.shared.model.ServiceEmailSMSQLogM;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLOrigContactDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesFUVerificationDataM;

public interface PLOrigEmailSMSDAO {
	public String getEMailTemplateMasterValue(String emailTemplateID, String templateType)throws PLOrigApplicationException;
	public ProductProperties getProductData(String productCode) throws PLOrigApplicationException;
	public GeneralParamProperties getGeneralParamData (String paramCode) throws PLOrigApplicationException;
	public CacheDataM getDocumentData (String documentCode) throws PLOrigApplicationException;
	public DocumentReasonProperties getDocReasonData (String documentCode, String reasonCode) throws PLOrigApplicationException;
	public String getKBANKEmployeeEmail(String branchCode, String companyTitleCode) throws PLOrigApplicationException;
	public String getKBANKEmployeeEmails(String branchCode, String busClass) throws PLOrigApplicationException;
	public String getKBANKSendToEmails(String appId, String channelCode, String saleId, String branchCode, String refId, String busClass, String emailTemplate) throws PLOrigApplicationException;
	public void createOrigContactLog(PLOrigContactDataM contactM) throws PLOrigApplicationException;
	public int countOrigContactLog(String appRecordId, String templateName) throws PLOrigApplicationException;
	public String getSMSTemplateMasterValue(String smsTemplateCode, String nationality, String busClass)throws PLOrigApplicationException;
	public String getFollowUpAutoEmailSendTo(String appId)throws PLOrigApplicationException;
	public EmailM getSendFromByUser(String userName) throws PLOrigApplicationException;
	public EmailM getSendFromByOwner(String appId) throws PLOrigApplicationException;
	public int getContactLogID()throws PLOrigApplicationException;
	public void createServiceEmailSMSQLog(ServiceEmailSMSQLogM emailSMSQLogM)throws PLOrigApplicationException;
	public int getEmailSMSQCount(int contactLogID) throws PLOrigApplicationException;
	public void increaseEmailSMSQCount(int contactLogID) throws PLOrigApplicationException;
	public void deleteEmailSMSQueue(int contactLogID) throws PLOrigApplicationException;
	public EmailTemplateMasterM getEMailTemplateMaster(String emailTemplateID, String busClass)throws PLOrigApplicationException;
	public void insertFollowDetail(PLXRulesFUVerificationDataM xrulesFuVerM)throws PLOrigApplicationException;
	public String getKBankUserNameSurname(String userName) throws PLOrigApplicationException;
	public int getFollowUpSLA(String appId)throws PLOrigApplicationException;
	public SMSPrepareDataM prepareSMSData(String appId, String templateName)throws PLOrigApplicationException;
}
