package com.eaf.orig.ulo.pl.app.utility;

import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLCashTransferDataM;

public class MapDefaultDataM {
	public static void map(PLApplicationDataM applicationM){
		PLCashTransferDataM cashTransferM = applicationM.getCashTransfer();
		if(null == cashTransferM){
			cashTransferM = new PLCashTransferDataM();   
			applicationM.setCashTransfer(cashTransferM);
		}	     
		if(OrigUtil.isEmptyString( cashTransferM.getBankTransfer())){
			 cashTransferM.setBankTransfer("004");
		}    
	}
}
