/*
 * Created on Jan 17, 2008
 *
 *  
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.dao;

import com.eaf.orig.shared.dao.exceptions.OrigApplicationLogMException;
import com.eaf.orig.shared.model.SMSDataM;

/**
 * @author Avalant
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface OrigSmsLogDAO {
	public void createModelSmsLogM(String appRecordId, SMSDataM prmSMSDataM)throws OrigApplicationLogMException;
}
