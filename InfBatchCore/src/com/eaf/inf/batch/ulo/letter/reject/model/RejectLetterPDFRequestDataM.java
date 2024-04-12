package com.eaf.inf.batch.ulo.letter.reject.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("serial")
public class RejectLetterPDFRequestDataM implements Serializable,Cloneable{
	public RejectLetterPDFRequestDataM(){
		super();
	}
	private ArrayList<RejectLetterPDFDataM> rejectLetterPDFs;
	private HashMap<String, String> priorityMap;
	public ArrayList<RejectLetterPDFDataM> getRejectLetterPDFs(){
		return rejectLetterPDFs;
	}
	public void setRejectLetterPDFs(ArrayList<RejectLetterPDFDataM> rejectLetterPDFs){
		this.rejectLetterPDFs = rejectLetterPDFs;
	}
	public HashMap<String, String> getPriorityMap(){
		return priorityMap;
	}
	public void setPriorityMap(HashMap<String, String> priorityMap){
		this.priorityMap = priorityMap;
	}
}
