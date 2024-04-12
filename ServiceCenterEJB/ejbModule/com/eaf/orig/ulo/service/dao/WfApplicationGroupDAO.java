package com.eaf.orig.ulo.service.dao;

import java.sql.Date;

import com.ava.workflow.ApplicationGroupM;
import com.eaf.service.common.exception.ServiceControlException;
import com.eaf.service.module.model.SearchWorkFlowInquiryDataM;

public interface WfApplicationGroupDAO {
	public ApplicationGroupM loadApplicationGroup(String applicationGroupId)throws ServiceControlException;
	public SearchWorkFlowInquiryDataM getApplicationGroupId(String CISCustomerId)throws ServiceControlException;
	public SearchWorkFlowInquiryDataM getApplicationGroupId(String docId, String docType)throws ServiceControlException;
	public SearchWorkFlowInquiryDataM getApplicationGroupId(String thFirstName, String thLastName, Date dateOfBirth)throws ServiceControlException;
	public ApplicationGroupM loadApplicationGroupCat(String applicationGroupId) throws ServiceControlException;
	public SearchWorkFlowInquiryDataM getApplicationGroupIdCat(String cisNo,String docNo, String docType, String thFirstName, String thLastName, Date birthDate) throws ServiceControlException;
}
