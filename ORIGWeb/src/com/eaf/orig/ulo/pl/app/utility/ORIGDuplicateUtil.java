package com.eaf.orig.ulo.pl.app.utility;

import java.util.Vector;

//import com.ava.bpm.util.BpmConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.model.app.ApplicationDuplicateM;

public class ORIGDuplicateUtil{
	public String GetDecisionApplication(Vector<ApplicationDuplicateM> appDuplicateVect){
		ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
		String gCancleRole = cacheUtil.getGeneralParamValue("DUPLICATE_CANCLE_ROLE","ALL_ALL_ALL");
		if(!OrigUtil.isEmptyString(gCancleRole)){
			String [] gCancleArray = gCancleRole.split(",");
			for (String role : gCancleArray) {
				for(ApplicationDuplicateM appDupM : appDuplicateVect){
					if(role.equalsIgnoreCase(appDupM.getRoleState()))
						return WorkflowConstant.Action.CANCELED;
				}
			}
		}
		return WorkflowConstant.Action.BLOCK;
	}
	public void RemoveBlockStateOut(Vector<ApplicationDuplicateM> appDuplicateVect){
		if(!OrigUtil.isEmptyVector(appDuplicateVect)){
			for(int i=appDuplicateVect.size()-1; i>= 0; --i){	
				 ApplicationDuplicateM appDupM = (ApplicationDuplicateM)appDuplicateVect.get(i);
//				 if(BpmConstant.BLOCK_STATE.equals(appDupM.getActivityState())){
//					 appDuplicateVect.remove(i);
//				 }
			}
		}
	}
	public void RemoveNotCancleStateOut(Vector<ApplicationDuplicateM> appDuplicateVect){
		ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
		String gCancleRole = cacheUtil.getGeneralParamValue("DUPLICATE_CANCLE_ROLE","ALL_ALL_ALL");
		if(!OrigUtil.isEmptyString(gCancleRole)){
			String [] gCancleArray = gCancleRole.split(",");
			for (String role : gCancleArray) {
				for(int i=appDuplicateVect.size()-1; i>= 0; --i){	
					 ApplicationDuplicateM appDupM = (ApplicationDuplicateM)appDuplicateVect.get(i);
					 if(role.equals(appDupM.getRoleState())){
						 appDuplicateVect.remove(i);
					 }
				}
			}
		}
	}
}
