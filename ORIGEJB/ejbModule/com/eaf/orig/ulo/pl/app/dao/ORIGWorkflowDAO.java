package com.eaf.orig.ulo.pl.app.dao;

import java.util.Vector;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.ORIGWorkflowDataM;

public interface ORIGWorkflowDAO {
	public Vector<ORIGWorkflowDataM> getQueueByOwner(String owner ,String tdID) throws PLOrigApplicationException;
	public String isDataFinalCompleteApprove(String appRecordId)throws PLOrigApplicationException;
}
