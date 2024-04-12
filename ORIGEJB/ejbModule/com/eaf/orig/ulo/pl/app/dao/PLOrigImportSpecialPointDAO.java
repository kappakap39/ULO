package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Date;
import java.util.Vector;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLImportSpecialPointDataM;

public interface PLOrigImportSpecialPointDAO {
	
	public Vector<PLImportSpecialPointDataM> saveTableOrig_Special_Point(Vector<PLImportSpecialPointDataM> importSpePointVect, Date dataDate) throws PLOrigApplicationException;

}