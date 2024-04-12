package com.eaf.orig.ulo.pl.app.ejb;

import javax.ejb.Remote;

import com.eaf.orig.ulo.pl.model.app.PLOrigContactDataM;
import com.eaf.orig.ulo.pl.model.app.SAUserCommissionAppDetialDataM;
import com.eaf.orig.ulo.pl.model.app.SAUserCommissionDataM;

@Remote
public interface SchedulerManager {
	public void SendEmail(PLOrigContactDataM contactM);
	public void SendSMS(PLOrigContactDataM contactM);
	public SAUserCommissionAppDetialDataM CalCommission(String applicationNo);
	public SAUserCommissionDataM getCommissionMALW(SAUserCommissionDataM commissionM);
}
