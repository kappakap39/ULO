package com.eaf.orig.ulo.pl.app.dao;

import com.eaf.orig.shared.dao.PLORIGDAOFactory;
import com.eaf.orig.ulo.pl.app.dao.exception.PLWFDAOException;

public class WFLoadCentralJob{	
	public int LoadCentralJob (String ATID, String ATID2) throws PLWFDAOException{
		PLWFLoadCentralJobAmountDAO wfDAO = PLORIGDAOFactory.getPLWFLoadCentralJobAmountDAO();
		return wfDAO.loadCentralJob(ATID, ATID2);
	}
}
