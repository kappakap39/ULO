/*
 * Created on Jan 2, 2008
 * Created by Sankom Sanpunya
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

import com.eaf.orig.shared.dao.exceptions.OrigPreScoreMException;
import com.eaf.orig.shared.model.PreScoreDataM;

/**
 * @author Sankom
 *
 * Type: OrigPreScoreMDAO
 */
public interface OrigPreScoreMDAO {
	public void createModelOrigPreScoreM(PreScoreDataM prmOrigPreScoreDataM)throws OrigPreScoreMException;
	public void deleteModelOrigPreScoreM(PreScoreDataM prmOrigPreScoreDataM)throws OrigPreScoreMException; 
	public PreScoreDataM loadModelOrigPreScoreM(String applicationRecordId,String cmpcde,String idno)throws OrigPreScoreMException; 
	public void saveUpdateModelOrigPreScoreM(PreScoreDataM prmOrigPreScoreDataM)throws OrigPreScoreMException;
}
