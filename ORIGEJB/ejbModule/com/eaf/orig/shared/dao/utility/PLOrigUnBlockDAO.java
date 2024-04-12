package com.eaf.orig.shared.dao.utility;

import java.util.Vector;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLInboxUnBlockDataM;

public interface PLOrigUnBlockDAO{	
	public Vector<PLInboxUnBlockDataM>loadSubTableUnBlock (String IDNO, String role, String userName) throws PLOrigApplicationException;
}
