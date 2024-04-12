package com.eaf.service.rest.controller.submitapplication.coverpagetype;

import com.eaf.flp.eapp.util.EAppUtil;

public class ApplicationAppyTypeNewProcessFactory {

	public static ApplicationAppyTypeProcess getApplicationAppyTypeNewProcess(String qr1){
		if(EAppUtil.eAppByQr1(qr1)){
			return new ApplicationAppyTypeNewProcessEAPP();
		}else if (EAppUtil.cjdByQr1(qr1)) {
			return new ApplicationAppyTypeNewProcessCJD();
		}
		return new ApplicationAppyTypeNewProcessNormal();
		
	}
	public static ApplicationAppyTypeProcess getApplicationAppyTypeFollowProcess(String qr1){
		if(EAppUtil.eAppByQr1(qr1)){
			return new ApplicationAppyTypeFollowProcessEAPP();
		}else if (EAppUtil.cjdByQr1(qr1)) {
			return new ApplicationAppyTypeFollowProcessCJD();
		}
		return new ApplicationAppyTypeFollowProcessNormal();
		
	}
}
