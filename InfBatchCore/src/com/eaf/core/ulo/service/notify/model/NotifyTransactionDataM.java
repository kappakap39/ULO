package com.eaf.core.ulo.service.notify.model;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class NotifyTransactionDataM implements Serializable,Cloneable{
	public NotifyTransactionDataM(){
		super();
	}
	private ArrayList<NotifyTransactionResultDataM> transactions = new ArrayList<NotifyTransactionResultDataM>();
	public ArrayList<NotifyTransactionResultDataM> getTransactions() {
		return transactions;
	}
	public void setTransactions(ArrayList<NotifyTransactionResultDataM> transactions) {
		this.transactions = transactions;
	}	
}
