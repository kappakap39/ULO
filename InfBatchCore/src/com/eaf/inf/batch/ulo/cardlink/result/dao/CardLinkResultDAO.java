package com.eaf.inf.batch.ulo.cardlink.result.dao;

import java.sql.Connection;
import java.util.HashMap;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.inf.batch.ulo.cardlink.result.model.InfCardLinkResultDataM;

public interface CardLinkResultDAO {
	public void insertInfCardLinkResult(InfCardLinkResultDataM  infCardLinkResult,Connection conn) throws InfBatchException;
	public void updateCardLinkFlag(InfCardLinkResultDataM infCardLinkResult,Connection conn) throws InfBatchException;
	public void updateCardlinkNextDay(String applicationGroupId,Connection conn)throws InfBatchException;
	public String isCardLinkRefNoExist(String cardLinkRefNo,String mainCardNo, String supCardNo, Connection conn) throws InfBatchException;
	public void updateNewCardlinkCustFlag(String flag, String idNo, Connection conn) throws InfBatchException;
	public HashMap<String,String> selectCardLinkInfoWithAppGroupId(String cardLinkCustNo, String applicationGroupId, Connection conn) throws InfBatchException;
	public void updateCardLinkCustNo(String cardLinkCustNo, String personalId, Connection conn) throws InfBatchException;
	public String getPersonalIdNewCardLinkCustTo(String firstTwoDigitCardLinkCustNo, String idNo, Connection conn) throws InfBatchException;
	public void updateCardlinkFlagGenerate(Connection conn, String idNo) throws InfBatchException;
	public void updateCardLinkFlag(InfCardLinkResultDataM infCardLinkResult,Connection conn, boolean sendtoMLS) throws InfBatchException;
	
}
