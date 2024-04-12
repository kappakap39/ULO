package com.eaf.inf.batch.ulo.letter.reject.model;

import java.io.Serializable;
import java.util.HashMap;
@SuppressWarnings("serial")
public class RejectLetterPDFConfigDataM implements Serializable, Cloneable {
	private HashMap<String,String> priorityMap;
	public HashMap<String, String> getPriorityMap(){
		return priorityMap;
	}
	public void setPriorityMap(HashMap<String, String> priorityMap){
		this.priorityMap = priorityMap;
	}
}
