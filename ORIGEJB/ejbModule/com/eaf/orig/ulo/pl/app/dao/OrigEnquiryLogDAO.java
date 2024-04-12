package com.eaf.orig.ulo.pl.app.dao;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.EnquiryLogDataM;

public interface OrigEnquiryLogDAO {
	public void insertTable_OrigEnquiryLog(EnquiryLogDataM enqLogM) throws PLOrigApplicationException;
}
