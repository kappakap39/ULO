package com.eaf.orig.shared.ejb;

import java.util.Vector;

import javax.ejb.Remote;

import com.eaf.orig.shared.model.InfBackupLogM;
import com.eaf.orig.shared.model.ServiceEmailSMSQLogM;
import com.eaf.orig.shared.model.ServiceEmailSMSSchedulerQDataM;
import com.eaf.orig.ulo.pl.model.app.PLOrigContactDataM;

@Remote
public interface ORIGContactManager {
	public void createServiceEmailSMSQLog(ServiceEmailSMSQLogM emailSMSQLogM);
	public void increaseEmailSMSQCount(int contactLogID);
	public void createOrigContractLog(PLOrigContactDataM contactM);
	public void deleteEmailSMSQueue(int contactLogID);
	public void updateStatusEmailSMSSchedulerQ(Vector<ServiceEmailSMSSchedulerQDataM> schedulerQVT);
	public void updateStatusEmailSMSSchedulerQ(ServiceEmailSMSSchedulerQDataM schedulerQDataM);
	public void createInterfaceBackupLog(InfBackupLogM infBackupM, String userName);
	public void deleteInterfaceBackupLog(int infBackupId);
}
