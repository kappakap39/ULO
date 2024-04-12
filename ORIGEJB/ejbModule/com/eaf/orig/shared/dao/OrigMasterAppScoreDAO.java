/*
 * Created on Nov 28, 2007
 * Created by Prawit Limwattanachai
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.shared.dao;

import java.util.HashMap;
import java.util.Vector;

import com.eaf.orig.master.shared.model.ScoreCharacterM;
import com.eaf.orig.master.shared.model.ScoreM;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;

/**
 * @author Administrator
 *
 * Type: OrigMasterAppScoreDAO
 */
public interface OrigMasterAppScoreDAO {
	public Vector getScoreFacterType(String cusType)throws OrigApplicationMException;
	public Vector getCharTypeVect(String scoreCode)throws OrigApplicationMException;
	public String getCusThaiDesc(String cusType)throws OrigApplicationMException;
	public Vector selectCharM(String scoreCode,String charCode)throws OrigApplicationMException;
	public ScoreCharacterM getCharTypeMEdit(String scoreSeq, String seq, String scoreCode, String charCode)throws OrigApplicationMException;
	public Vector getCharTypeSpecificVect(String scoreCode, String charCode)throws OrigApplicationMException;
	public ScoreCharacterM getNewSpecDesc(ScoreCharacterM characterM)throws OrigApplicationMException;
	public Vector getScoreTypeVect(String cusType)throws OrigApplicationMException;
	public HashMap getCharTypeHashMapByScoreCode(Vector scoreTypeVect)throws OrigApplicationMException;
	public int getMaxScoreSeq(String custype)throws OrigApplicationMException;
	public void createModelOrigMasterAppScore(ScoreM scoreM)throws OrigApplicationMException;
	public HashMap getOriginalScoreTypeHash(String cusType)throws OrigApplicationMException;
	public HashMap getOriginalCharTypeHash(Vector scoreCodeVect)throws OrigApplicationMException;
	public ScoreM getOriginalScoreM(String cusType)throws OrigApplicationMException;
	
	
}
