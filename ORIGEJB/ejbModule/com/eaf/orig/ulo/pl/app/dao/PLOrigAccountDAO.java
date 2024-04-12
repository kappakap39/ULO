package com.eaf.orig.ulo.pl.app.dao;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLAccountDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public interface PLOrigAccountDAO {
	
	public PLAccountDataM loadAccount (String accId) throws PLOrigApplicationException;
	public void saveUpdateAccount(PLApplicationDataM appM) throws PLOrigApplicationException;
	public void saveUpdateAccountByPLSQL(PLApplicationDataM appM) throws PLOrigApplicationException;
	public String reissueCustNo(String accId) throws PLOrigApplicationException;
	public void updateCardLinkStatus (String accId, String cardLink_Status, String userName) throws PLOrigApplicationException;
	PLAccountDataM loadAccountByApplicationNo(String applicationNo) throws PLOrigApplicationException;
	void updateCASHDAY1_STATUS(String application_no, String cashDay1_Status, String userName) throws PLOrigApplicationException;
	public void InactiveAccount (String appNo, UserDetailM userM) throws PLOrigApplicationException;
	public void setInActiveAccount(String accountID, UserDetailM userM) throws PLOrigApplicationException;
	public int getCardLinkStatus (String accId) throws PLOrigApplicationException;

}
