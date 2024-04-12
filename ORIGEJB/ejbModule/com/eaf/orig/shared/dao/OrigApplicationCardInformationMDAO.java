/*
 * Created on Dec 18, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.dao;

import java.util.Vector;

import com.eaf.orig.shared.dao.exceptions.OrigApplicationCardInformationMException;
import com.eaf.orig.shared.model.CardInformationDataM;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface OrigApplicationCardInformationMDAO {
	public void createModelOrigApplicationCardInformationDataM(CardInformationDataM prmCardInformationDataM)throws OrigApplicationCardInformationMException;
	public void deleteModelOrigApplicationCardInformationDataM(CardInformationDataM prmCardInformationDataM)throws OrigApplicationCardInformationMException;	 
	public Vector loadModelOrigApplicationCardInformationDataM(String applicationRecordId, String providerUrlEXT, String jndiEXT)throws OrigApplicationCardInformationMException; 
	public void saveUpdateModelOrigApplicationCardInformationDataM(CardInformationDataM prmCardInformationDataM)throws OrigApplicationCardInformationMException;
	public void deleteNotInKeyTableORIG_APPLICATION_CARD_INFORMATION(Vector cardInfoVect, String appRecordID) throws OrigApplicationCardInformationMException;
}
