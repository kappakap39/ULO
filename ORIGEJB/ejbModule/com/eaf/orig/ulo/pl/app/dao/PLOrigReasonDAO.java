package com.eaf.orig.ulo.pl.app.dao;

import java.util.Vector;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;

public interface PLOrigReasonDAO {
	
	public void updateSaveOrigReason(Vector<PLReasonDataM> reasonVect, String appRecID ,String role) throws PLOrigApplicationException;
	public Vector<PLReasonDataM> loadOrigReason (String appRecId) throws PLOrigApplicationException;
	public Vector<String> loadReasonEnquiry (String appRecId) throws PLOrigApplicationException;
	public Vector<String> loadReasonCallCenterTracking(String appRecId, String jobState)throws PLOrigApplicationException;

}
