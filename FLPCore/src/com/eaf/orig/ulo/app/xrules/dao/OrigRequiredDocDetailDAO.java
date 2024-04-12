package com.eaf.orig.ulo.app.xrules.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.RequiredDocDetailDataM;

public interface OrigRequiredDocDetailDAO {
	
	public void createOrigRequiredDocDetailM(RequiredDocDetailDataM requiredDocDtlM)throws ApplicationException;
	public void createOrigRequiredDocDetailM(RequiredDocDetailDataM requiredDocDtlM,Connection conn)throws ApplicationException;
	public void deleteOrigRequiredDocDetailM(String requiredDocId, String requiredDocDetailId)throws ApplicationException;
	public void deleteOrigRequiredDocDetailM(String requiredDocId, String requiredDocDetailId,Connection conn)throws ApplicationException;
	public ArrayList<RequiredDocDetailDataM> loadOrigRequiredDocDetailM(String requiredDocId)throws ApplicationException;	
	public ArrayList<RequiredDocDetailDataM> loadOrigRequiredDocDetailM(String requiredDocId,Connection conn)throws ApplicationException;
	public void saveUpdateOrigRequiredDocDetailM(RequiredDocDetailDataM requiredDocDtlM)throws ApplicationException;
	public void saveUpdateOrigRequiredDocDetailM(RequiredDocDetailDataM requiredDocDtlM,Connection conn)throws ApplicationException;
	public void deleteNotInKeyRequiredDocDetail(ArrayList<RequiredDocDetailDataM> requiredDocDtlList, 
			String requiredDocId) throws ApplicationException;
	public void deleteNotInKeyRequiredDocDetail(ArrayList<RequiredDocDetailDataM> requiredDocDtlList, 
			String requiredDocId,Connection conn) throws ApplicationException;
}
