package com.eaf.orig.shared.ejb;

import javax.ejb.Local;

import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.CardInformationDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

/**
 * Remote interface for Enterprise Bean: ORIGGeneratorManager
 */
@Local
public interface ORIGGeneratorManager{
	public String generateUniqueIDByName(String name);
	public String generateUniqueIDByName(String name, int dbType) ;
	public String generateApplicationNo(ApplicationDataM applicationDataM);
	public String generateCardNo(CardInformationDataM card);
	public String getApplicationNo(PLApplicationDataM appM);
	public String generateNextSequenceID(String seqName);
}
