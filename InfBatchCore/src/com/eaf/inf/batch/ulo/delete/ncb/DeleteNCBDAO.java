package com.eaf.inf.batch.ulo.delete.ncb;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;

public interface DeleteNCBDAO {
	public void deleteNCBDTable(String trackingCode, Connection conn) throws InfBatchException;
	public void deleteNCBAccountReport(String trackingCode, Connection conn) throws InfBatchException;
	public void deleteNCBAccountRuleReport(ArrayList<String> accountReportIDs, Connection conn) throws InfBatchException;
	public ArrayList<TaskObjectDataM>  selectDeleteTrackingCode() throws InfBatchException;
	public ArrayList<String>  selectDeleteAccountReportID(String trackingCode, Connection conn) throws InfBatchException;

}
