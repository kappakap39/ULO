package com.eaf.orig.ulo.app.xrules.dao;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.cardMaintenance.CardMaintenanceDataM;

public interface OrigCardMaintenanceDAO {
	public CardMaintenanceDataM getCardMaintenance(String applicationGroupId,String cardlinkRefNo)throws ApplicationException;
	public CardMaintenanceDataM getCardMaintenance(String applicationGroupId,String cardlinkRefNo, String regType)throws ApplicationException;
	public double updateCard(CardDataM card)throws ApplicationException;
	public String getCardId(String applicationRecordId)throws ApplicationException;
	public double resendCardlinkCard(String applicationRecordId)throws ApplicationException;
	public double resendCardlinkCard(String applicationRecordId,Integer seq)throws ApplicationException;
	public double cancelCardlinkCard(String applicationRecordId)throws ApplicationException;
	public double updateNewCardlinkCustFlag(String cardlinkCustNo,String personalId)throws ApplicationException;
	public double generateNewCustNo(String personalId)throws ApplicationException;
	public double updateCustIdOrigCard(String personalId,String cardlinkCustId)throws ApplicationException;
	public double resendCardlinkCust(String personalId,String cardlinkCustNo)throws ApplicationException;
	public double cancelCardlinkCust(String personalId)throws ApplicationException;
	
	public double updateCard(CardDataM card, String regType) throws ApplicationException;
	public double resendCardlinkCard(String applicationRecordId, String regType) throws ApplicationException;
	public double resendCardlinkCard(String applicationRecordId, Integer seq, String regType) throws ApplicationException;
	public double cancelCardlinkCard(String applicationRecordId, String regType) throws ApplicationException;
	public double resendCardlinkCust(String personalId, String cardlinkCustNo, String regType) throws ApplicationException;
	public double cancelCardlinkCust(String personalId, String regType) throws ApplicationException;
	public double updateCustIdOrigCard(String cardId, String cardlinkCustId, String regType) throws ApplicationException;
	public double updateNewCardlinkCustFlag(String cardlinkCustNo, String personalId, String regType) throws ApplicationException;
	public String getCardId(String applicationRecordId, String regType) throws ApplicationException;
	public void cancelAppGroupMLP(String applicationGroupId, String userName) throws ApplicationException;
	public void cancelAppMLP(String applicationRecordId, String userName) throws ApplicationException;
	public void createCancelReasonMLP(String applicationGroupId, String applicationRecordId, String reason, String userName, String userRole) throws ApplicationException;
}
