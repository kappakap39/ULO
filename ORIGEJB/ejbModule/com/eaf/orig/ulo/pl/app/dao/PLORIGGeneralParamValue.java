package com.eaf.orig.ulo.pl.app.dao;

import com.eaf.orig.master.shared.model.GeneralParamM;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;

public interface PLORIGGeneralParamValue {
	public GeneralParamM getGeneralParamvalueWithBussclass(String paramCode, String busClass) throws PLOrigApplicationException;
	public GeneralParamM getGeneralParamvalue(String paramCode) throws PLOrigApplicationException;
}
