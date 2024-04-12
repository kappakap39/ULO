package com.eaf.orig.ulo.app.key.dao;

import java.sql.Connection;

import com.eaf.orig.ulo.app.dao.exception.UniqueIDGeneratorException;
import com.eaf.orig.ulo.model.app.CardDataM;

public interface UniqueIDGeneratorDAO {
	public String getCardNo(CardDataM card) throws UniqueIDGeneratorException;
	public String getCardLinkNo(CardDataM card) throws UniqueIDGeneratorException;
	public String getHisHerMembershipNo(CardDataM card) throws UniqueIDGeneratorException;
	public String getPriorityPassNo(CardDataM card) throws UniqueIDGeneratorException;
	public String getGenerateRunningNo(String paramId,String paramType) throws UniqueIDGeneratorException;
	public String getGenerateRunningNo(String paramId,String paramType,Connection conn) throws UniqueIDGeneratorException;
//	public String getGenerateRunningNo(String paramId,String paramType,boolean isBatch) throws UniqueIDGeneratorException;
	public String getGenerateRunningNoStack(String paramId,String paramType) throws UniqueIDGeneratorException;
	
	public String getCardNo(CardDataM card, Connection conn) throws UniqueIDGeneratorException;
	public String getCardLinkNo(CardDataM card, Connection conn) throws UniqueIDGeneratorException;
	public String getHisHerMembershipNo(CardDataM card, Connection conn) throws UniqueIDGeneratorException;
	public String getPriorityPassNo(CardDataM card, Connection conn) throws UniqueIDGeneratorException;
	public String getGenerateRunningNoStack(String paramId,String paramType, Connection conn) throws UniqueIDGeneratorException;
}
