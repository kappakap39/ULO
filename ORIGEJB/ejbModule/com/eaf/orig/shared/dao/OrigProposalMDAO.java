/*
 * Created on Sep 20, 2007
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


import com.eaf.orig.shared.dao.exceptions.OrigProposalMException;
import com.eaf.orig.shared.model.ProposalDataM;

/**
 * @author Baworn Srisatjalertwaja
 *
 * Type: OrigProposalMDAO
 */
public interface OrigProposalMDAO {
	public void createModelOrigProposalM(ProposalDataM prmProposalDataM)throws OrigProposalMException;
	public void deleteModelOrigProposalM(ProposalDataM prmProposalDataM)throws OrigProposalMException;
	public ProposalDataM loadModelOrigProposalM(String applicationRecordId)throws OrigProposalMException;
	public void saveUpdateModelOrigProposalM(ProposalDataM prmProposalDataM)throws OrigProposalMException;
}
