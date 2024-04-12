package com.eaf.orig.ulo.pl.key.dao;

import com.eaf.orig.shared.key.dao.exception.UniqueIDGeneratorException;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

@Deprecated
public interface PLUniqueIDGeneratorDAO extends
		com.eaf.orig.shared.key.dao.UniqueIDGeneratorDAO {

	public String getApplicationNo(PLApplicationDataM appM) throws UniqueIDGeneratorException;
	public long getNextSequenceID(String seqName) throws UniqueIDGeneratorException; 
}
