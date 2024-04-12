package com.eaf.orig.ulo.app.ncb.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.ncb.NcbAccountReportDataM;

public interface OrigNCBAccountReportDAO {
	
	public void createOrigNcbAccountReportM(NcbAccountReportDataM ncbAccountReportM)throws ApplicationException;
	public void deleteOrigNcbAccountReportM(String trackingCode, int seq, String accountReportId)throws ApplicationException;	
	public ArrayList<NcbAccountReportDataM> loadOrigNcbAccountReportM(String trackingCode, int seq)throws ApplicationException;	
	public ArrayList<NcbAccountReportDataM> loadOrigNcbAccountReportM(String trackingCode, int seq,Connection conn)throws ApplicationException;	
	public void saveUpdateOrigNcbAccountReportM(NcbAccountReportDataM ncbAccountReportM)throws ApplicationException;
	public void deleteNotInKeyNcbAccountReport(ArrayList<NcbAccountReportDataM> ncbAccountReportList, String trackingCode, int seq)
			throws ApplicationException;
}
