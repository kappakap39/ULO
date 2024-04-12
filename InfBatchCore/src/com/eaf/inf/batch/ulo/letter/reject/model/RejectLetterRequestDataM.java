package com.eaf.inf.batch.ulo.letter.reject.model;

import java.io.Serializable;
import java.util.HashMap;
@SuppressWarnings("serial")
public class RejectLetterRequestDataM implements Serializable, Cloneable{
	private HashMap<String,RejectLetterDataM> rejectLetterMap;
	private HashMap<String, String> priorityMap;
	private HashMap<String, String> sellerMap;
	public HashMap<String, RejectLetterDataM> getRejectLetterMap(){
		return rejectLetterMap;
	}
	public void setRejectLetterMap(
			HashMap<String, RejectLetterDataM> rejectLetterMap){
		this.rejectLetterMap = rejectLetterMap;
	}
	public HashMap<String, String> getPriorityMap(){
		return priorityMap;
	}
	public void setPriorityMap(HashMap<String, String> priorityMap){
		this.priorityMap = priorityMap;
	}
	public HashMap<String, String> getSellerMap(){
		return sellerMap;
	}
	public void setSellerMap(HashMap<String, String> sellerMap){
		this.sellerMap = sellerMap;
	}
}
