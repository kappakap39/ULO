package com.eaf.orig.ulo.app.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.SMSLogDataM;

public interface OrigSMSLogDAO {
	
	public void createOrigSMSLogM(SMSLogDataM smsLogM)throws ApplicationException;
	public void deleteOrigSMSLogM(String applicationGroupId, String smsLogId)throws ApplicationException;
	public ArrayList<SMSLogDataM> loadOrigSMSLogM(String applicationGroupId)throws ApplicationException;
}
