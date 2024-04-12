package com.eaf.orig.ulo.pl.app.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import com.eaf.orig.shared.model.InfLogDataM;
import com.eaf.orig.shared.model.ServiceEmailSMSSchedulerQDataM;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public interface PLORIGSchedulerDAO {
	public ArrayList<String> loadAutoRejectConfirmReject(int sla) throws PLOrigApplicationException;
	public ArrayList<String> loadAutoRejectVC(int sla) throws PLOrigApplicationException;
	public ArrayList<String> loadAutoRejectFollowUpIQ() throws PLOrigApplicationException;
	public ArrayList<String> loadAutoSendEmailFollowUp() throws PLOrigApplicationException;
	public Vector<ServiceEmailSMSSchedulerQDataM> loadEmailSMSSchedulerQueue(String serviceType) throws PLOrigApplicationException;
	public void updateStatusEmailSMSSchedulerQ( ServiceEmailSMSSchedulerQDataM emailSMSschedulerQM) throws PLOrigApplicationException;
	public ArrayList<String> loadAutoCompleteJob() throws PLOrigApplicationException;
	public HashMap<String, Integer> loadWarningCardNo(String paramIdMain, String paramIdTemp)throws PLOrigApplicationException;
	public void insertInterfaceLog(InfLogDataM infLogM)throws PLOrigApplicationException;
}
