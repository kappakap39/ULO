package com.eaf.orig.ulo.pl.app.dao;

import java.math.BigDecimal;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.UserPointDataM;

public interface PLOrigUserDAO {
	public UserPointDataM getUserPointDetails(String userName, ProcessMenuM menuM) throws PLOrigApplicationException;
	public BigDecimal getUserLendingLimit(String userName, String busClass) throws PLOrigApplicationException;
	public UserDetailM getUserDetail(String userName) throws PLOrigApplicationException;
}
