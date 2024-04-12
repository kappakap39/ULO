package com.eaf.orig.ulo.app.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.EnquiryLogDataM;

public interface OrigEnquiryLogDAO {
	
	public void createOrigEnquiryLogM(EnquiryLogDataM enquiryLogM)throws ApplicationException;
	public void deleteOrigEnquiryLogM(String applicationGroupId, String enquiryLogId)throws ApplicationException;
	public ArrayList<EnquiryLogDataM> loadOrigEnquiryLogM(String applicationGroupId)throws ApplicationException;	 
	public void saveUpdateOrigEnquiryLogM(EnquiryLogDataM enquiryLogM)throws ApplicationException;
	public void deleteNotInKeyEnquiryLog(ArrayList<EnquiryLogDataM> enquiryLogList, 
			String applicationGroupId) throws ApplicationException;
}
