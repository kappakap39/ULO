/*
 * Created on Dec 20, 2007
 *
 *  
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.dao;

import java.util.Vector;

import com.eaf.orig.master.shared.model.QTimeOutLoanTypeM;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;

/**
 * @author Joe
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface WorkflowUtilDAO {
	public Vector loadCriticalProcess() throws OrigApplicationMException;
	public double updateForSearchCriticalProcess()throws OrigApplicationMException;
}
