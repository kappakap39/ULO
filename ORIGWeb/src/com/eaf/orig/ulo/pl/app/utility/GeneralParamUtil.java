package com.eaf.orig.ulo.pl.app.utility;

import com.eaf.orig.cache.properties.GeneralParamProperties;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;

public class GeneralParamUtil {
	
	public Boolean CheckJobState(String paramCode, String busClassId, String jobState){
		if(!OrigUtil.isEmptyString(paramCode) && !OrigUtil.isEmptyString(busClassId) && !OrigUtil.isEmptyString(jobState)) {
			ORIGCacheUtil cacheM = ORIGCacheUtil.getInstance();
			GeneralParamProperties paramM = cacheM.loadGeneralParamCacheGroup(paramCode, busClassId);
			if(!OrigUtil.isEmptyString(paramM.getParamvalue())){
				String jobStateArr[] = paramM.getParamvalue().split(",");
				if(null != jobStateArr && jobStateArr.length>0){
					for(int i=0; i<jobStateArr.length; i++){
						if(jobState.equals(jobStateArr[i])) {
							return true;
						}
					}
				}
			}
			return false;
		}
		return false;
	}

}
