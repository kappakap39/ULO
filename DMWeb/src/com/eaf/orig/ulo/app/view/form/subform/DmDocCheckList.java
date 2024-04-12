package com.eaf.orig.ulo.app.view.form.subform;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.dm.DocumentDataM;
import com.eaf.orig.ulo.model.dm.DocumentManagementDataM;
import com.eaf.orig.ulo.model.dm.HistoryDataM;

public class DmDocCheckList extends ORIGSubForm{
	String DM_APPLICATION_STATUS_IN_PROCESS = SystemConstant.getConstant("DM_APPLICATION_STATUS_IN_PROCESS");
	ArrayList<String> DM_CONDITION_STATUS_LIST = SystemConstant.getArrayListConstant("DM_CONDITION_STATUS_LIST");
	ArrayList<String> DM_CONDITION_STATUS_UPDATE_ACTION = SystemConstant.getArrayListConstant("DM_CONDITION_STATUS_UPDATE_ACTION");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		DocumentManagementDataM dmManageDataM = (DocumentManagementDataM)appForm;
		ArrayList<DocumentDataM> document  = dmManageDataM.getDocument();
		ArrayList<String> DocCheckAll = new ArrayList<String>();
		
		if(Util.empty(document)){
			document = new ArrayList<DocumentDataM>();
			dmManageDataM.setDocument(document) ;
			
		} 		
		for(DocumentDataM docM :document){
			String isComplete = request.getParameter("CHECK_BOX_INCOMPLETE_"+docM.getDmSubId());
			if(Util.empty(isComplete) && !DM_APPLICATION_STATUS_IN_PROCESS.equals(dmManageDataM.getParam1())){
				isComplete =MConstant.DM_STATUS.AVAILABLE;
			}
			docM.setStatus(isComplete);
			docM.setUpdateBy(userM.getUserName());
			docM.setUpdateDate(ApplicationDate.getTimestamp());
			DocCheckAll.add(isComplete);
			
		}		
				
		// will change status to AVAILABLE  When all input data and document check list is complete
		
		if(DM_CONDITION_STATUS_LIST.contains(dmManageDataM.getStatus())|| Util.empty(dmManageDataM.getStatus())){
			HistoryDataM history = new HistoryDataM();		
			history.setRemark(request.getParameter("DM_REMARK"));
			history.setCreateDate(ApplicationDate.getTimestamp());
			history.setActionDate(ApplicationDate.getDate());
			history.setCreateBy(userM.getUserName());
			String dmCurrentStatus =dmManageDataM.getStatus();
			if(DM_CONDITION_STATUS_UPDATE_ACTION.contains(dmCurrentStatus)){
				history.setAction(MConstant.DM_MANAGEMENT_ACTION.UPDATE_ACTION);
			}else{
				history.setAction(MConstant.DM_MANAGEMENT_ACTION.STORE_ACTION);
			}
							
			if(DocCheckAll.contains(MConstant.DM_STATUS.NOT_IN_WAREHOUSE)){
				dmManageDataM.setStatus(MConstant.DM_STATUS.NOT_IN_WAREHOUSE);
				history.setStatus(MConstant.DM_STATUS.NOT_IN_WAREHOUSE);
			}else{
				dmManageDataM.setStatus(MConstant.DM_STATUS.AVAILABLE);
				history.setStatus(MConstant.DM_TRANSACTION_STATUS.AVAILABLE);
			}
			dmManageDataM.setHistory(history);
		} 	
	}
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		return null;
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
}
