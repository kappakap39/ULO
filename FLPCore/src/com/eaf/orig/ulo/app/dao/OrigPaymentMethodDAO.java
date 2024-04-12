package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;

public interface OrigPaymentMethodDAO {
	
	public void createOrigPaymentMethodM(PaymentMethodDataM paymentMethodM)throws ApplicationException;
	public void deleteOrigPaymentMethodM(String loanId, String paymentMethodId)throws ApplicationException;
	public ArrayList<PaymentMethodDataM> loadOrigPaymentMethodM(List<String> paymentIds)throws ApplicationException;	 
	public void saveUpdateOrigPaymentMethodM(PaymentMethodDataM paymentMethodM)throws ApplicationException;
	public void saveUpdateOrigPaymentMethodM(PaymentMethodDataM paymentMethodM,Connection conn)throws ApplicationException;
	public ArrayList<PaymentMethodDataM> loadOrigPaymentMethod(ArrayList<String> personalIds) throws ApplicationException;
	public ArrayList<PaymentMethodDataM> loadOrigPaymentMethod(ArrayList<String> personalIds,Connection conn) throws ApplicationException;
	void createOrigPaymentMethodM(PaymentMethodDataM paymentMethodM,Connection conn) throws ApplicationException;
	public void deleteNotInKeyOrigPaymentMethod(String personalId,String paymentMethodId)throws ApplicationException;
	public void deleteNotInKeyOrigPaymentMethod(String personalId,String paymentMethodId,Connection conn)throws ApplicationException;
	public void deleteInactivePaymentMethod(ApplicationGroupDataM applicationGroup)throws ApplicationException;
}
