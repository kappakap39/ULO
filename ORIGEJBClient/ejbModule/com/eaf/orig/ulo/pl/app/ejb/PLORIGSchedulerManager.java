package com.eaf.orig.ulo.pl.app.ejb;
import javax.ejb.Remote;

import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.profile.model.UserDetailM;

@Remote
public interface PLORIGSchedulerManager {
	public void processAutoRejectConfirmReject();
	public void processAutoRejectFU();
	public void processAutoRejectVC();
	public void processAutoIncreaseDecrease(String attachmentId);
	public void processAutoSendEmail();
	public void processImportInterface(ORIGCacheDataM cacheM, UserDetailM userM) throws Exception;
	//public void processImportListedCompany();
	//public void processImportSaleTransaction();
	public void processSendEmailSMSScheduler(String serviceType);
	public void processCalculateCommision(java.util.Date calDate);
	public void processWarnningCardNo();
	public void processCalculateCommisionPeriod(String period);
	public void clearDataDeleteImportInterface(ORIGCacheDataM cacheM, String userName);
}
