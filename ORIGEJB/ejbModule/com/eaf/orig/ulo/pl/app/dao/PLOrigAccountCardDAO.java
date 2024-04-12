package com.eaf.orig.ulo.pl.app.dao;

import java.util.Vector;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLAccountCardDataM;
import com.eaf.orig.ulo.pl.model.app.PLAccountDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public interface PLOrigAccountCardDAO {
	
	public Vector<PLAccountCardDataM> loadAccountCard (String accId) throws PLOrigApplicationException;
	public void reIssueCardNo (PLAccountCardDataM accCardM, UserDetailM userM) throws PLOrigApplicationException;
	public PLAccountCardDataM loadAccountCardByCardNo(String cardNo) throws PLOrigApplicationException;
	public String getAppRecordIdByCardNo(String cardNo) throws PLOrigApplicationException;
	public Vector<PLAccountCardDataM> loadAccountCardAndCustNo (String accId) throws PLOrigApplicationException;
	public PLAccountCardDataM loadAccountCardByAppNo(String appNo) throws PLOrigApplicationException;
	public String getCitizenNoByCardNo(String cardNo);
	public void InactiveAccountCard (String appNo, UserDetailM userM) throws PLOrigApplicationException;
	public PLAccountDataM loadAccountNoCardData(String accountId) throws PLOrigApplicationException;
	public boolean isCitizenRelateCardNo(String citizenId, String cardNo)throws PLOrigApplicationException;
	public void updateCardLinkStatus(String cardLinkStatus, String applicationNo, UserDetailM userM) throws PLOrigApplicationException;
	public void processAccountCardForRework(PLApplicationDataM appM, UserDetailM userM)throws PLOrigApplicationException;
	public void setInActiveAccountCard (String accountID, UserDetailM userM) throws PLOrigApplicationException;
}
