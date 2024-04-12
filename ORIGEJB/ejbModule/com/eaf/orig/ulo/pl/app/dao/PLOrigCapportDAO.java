package com.eaf.orig.ulo.pl.app.dao;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.CapportGroupDataM;
import com.eaf.orig.ulo.pl.model.app.OverrideCapportDataM;

public interface PLOrigCapportDAO {
	public CapportGroupDataM getCapportGroupDetails(String capportNo) throws PLOrigApplicationException;
	
	public void updateCapportUsed(String capportNo, String appRecordId, String busClass, String capportType, String userName ) throws PLOrigApplicationException;
	
	public OverrideCapportDataM getOverrideCapport(String appRecordID) throws PLOrigApplicationException;
}
