package com.eaf.orig.ulo.pl.app.dao;

import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLPaymentMethodDataM;

public interface PLOrigPaymentMethodDAO {

	public void updateSavePaymentMethod(PLPaymentMethodDataM paymentM, String appRecId) throws PLOrigApplicationException;
	public PLPaymentMethodDataM loadPaymentMethod(String appID) throws PLOrigApplicationException;
	
}
