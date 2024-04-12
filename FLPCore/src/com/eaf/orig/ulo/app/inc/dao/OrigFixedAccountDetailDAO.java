package com.eaf.orig.ulo.app.inc.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.FixedAccountDetailDataM;

public interface OrigFixedAccountDetailDAO {
	
	public void createOrigFixedAccountDetailM(FixedAccountDetailDataM fixedAccountDetailM)throws ApplicationException;
	public void deleteOrigFixedAccountDetailM(String fixedAccId, String fixedAccDetailId)throws ApplicationException;
	public ArrayList<FixedAccountDetailDataM> loadOrigFixedAccountDetailM(String fixedAccId)throws ApplicationException;	 
	public void saveUpdateOrigFixedAccountDetailM(FixedAccountDetailDataM fixedAccountDetailM)throws ApplicationException;
	public void deleteNotInKeyFixedAccountDetail(ArrayList<FixedAccountDetailDataM> fixedAccountDtlList, 
			String fixedAccId) throws ApplicationException;
}
