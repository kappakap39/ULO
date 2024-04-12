package com.eaf.inf.batch.ulo.pega.dao;

import java.util.ArrayList;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.pega.UpdateApprovalStatusRequestDataM;

public interface UpdateApprovalStatusDAO {
//	public Date getApplicationDate() throws InfBatchException;
	public ArrayList<ApplicationGroupDataM> loadApplicationGroup() throws InfBatchException;
	public UpdateApprovalStatusRequestDataM loadNotifyApplication(String applicationGroupId) throws InfBatchException;
	public boolean isUpdateBatchLog(String applicationRecordId) throws InfBatchException;
	public ArrayList<ApplicationDataM> loadApplication(String applicationGroupId) throws InfBatchException;
	public ArrayList<ApplicationGroupDataM> loadUpdateApprovalStatusApplicationGroup() throws InfBatchException;
}
