package com.eaf.orig.ulo.app.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;

public interface PegaApplicationDAO {
	public boolean isCheckListReason(ArrayList<String> reasonIds,String document,boolean isMandatory)throws ApplicationException;
	public String getDocumentReasonType(ArrayList<String> reasonIds,String documentCode,boolean mandatoryFlag)throws ApplicationException;
}
