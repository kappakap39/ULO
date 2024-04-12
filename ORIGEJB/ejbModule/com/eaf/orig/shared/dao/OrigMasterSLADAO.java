/*
 * Created on Jan 23, 2008
 *
 *  
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.dao;

import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
import com.eaf.orig.shared.model.SLADataM;

/**
 * @author Weeraya
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface OrigMasterSLADAO {
	public double updateSLADataM(SLADataM dataM)throws OrigApplicationMException;
}
