package com.eaf.orig.ulo.pl.app.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.eaf.cache.TableLookupCache;
import com.eaf.cache.data.MandatoryCacheDataM;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public class ManualMandatory extends LogicMandatory{
	public static ArrayList<MandatoryDataM> fields = null;
	public ManualMandatory(){
		super();
		if(null == fields){
			fields = new ArrayList<MandatoryDataM>();		
			create();
		}
	}	
	public static void create(){
		fields.add(create("OCCUPATION_SUBFORM","occ_sp_flag",IGNORE_DECREASE));
		fields.add(create("OCCUPATION_SUBFORM","solo_view",IGNORE_DECREASE));
		fields.add(create("OCCUPATION_SUBFORM","lec",IGNORE_DECREASE));
		fields.add(create("SERVICE_POLITICS_SUBFORM","have_related",IGNORE_DECREASE));
		fields.add(create("SERVICE_POLITICS_SUBFORM","ser_sanction_list",IGNORE_DECREASE));
		fields.add(create("SERVICE_POLITICS_SUBFORM","ser_customer_risk",IGNORE_DECREASE));
		fields.add(create("CASH_DAY_ONE_SUBFROM","cash_day1_bank_transfer",CASH_DAY5));
		fields.add(create("CASH_DAY_ONE_SUBFROM","cash_day1_account_no",CASH_DAY5));
	}
	public static MandatoryDataM create(String subFormID,String fieldName,int logic){
		MandatoryDataM mandatory = new MandatoryDataM();
			mandatory.setSubFormID(subFormID);
			mandatory.setFieldName(fieldName);
			mandatory.setLogic(logic);
		return mandatory;
	}
	
	public static String getManadatory(String formID,String fieldName,HttpServletRequest request){
		if(Mandatory(formID,fieldName,request)){
			return "<font color=\"#ff0000\">*</font>";
		}
		return "";
	}
		
	public static boolean Mandatory(String formID,String fieldName,HttpServletRequest request){		
		MandatoryDataM mandatoryM = get(formID, fieldName);
		if(null != mandatoryM){
			if(doLogic(mandatoryM, request)){
				return true;
			}
		}
		return false;
	}
	
	public static void MandatorySubForm(String subFormID,HttpServletRequest request){
		
		String ERROR_PREFIX = ErrorUtil.getShortErrorMessage(request,"ERROR_PREFIX");
		
		ArrayList<MandatoryDataM> manVect = get(subFormID);
		
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		if (formHandler == null){
			formHandler = new PLOrigFormHandler();
		}
		PLApplicationDataM applicationM = formHandler.getAppForm();
		if (applicationM == null){
			applicationM = new PLApplicationDataM();
		}

		if(null != manVect){
			for(MandatoryDataM dataM : manVect){
				if(Mandatory(dataM.getSubFormID(),dataM.getFieldName(),request)){
					String value = request.getParameter(dataM.getFieldName());
					if(OrigUtil.isEmptyString(value)){
						String errorMsg = getDisplayName(dataM.getFieldName(), subFormID, request);
						formHandler.PushErrorMessage(dataM.getFieldName(), ERROR_PREFIX+""+errorMsg);
					}
				}
			}
		}
	}
	
	private static String getDisplayName(String fields, String subFormID,HttpServletRequest request) {
		HashMap hash = TableLookupCache.getCacheStructure();
		Vector vMandatory = (Vector) (hash.get("MandatoryField"));
		MandatoryCacheDataM cacheM = new MandatoryCacheDataM();
		for (int i = 0; i < vMandatory.size(); i++) {
			cacheM = (MandatoryCacheDataM) vMandatory.elementAt(i);
			if ((cacheM.getFieldName()).equals(fields)
					&& cacheM.getSubFormName().equals(subFormID)){
				return cacheM.getErrorMessage();
			}
		}
		return "";
	}
	
	public static MandatoryDataM get(String formID,String fieldName){
		if(null == fields){
			fields = new ArrayList<MandatoryDataM>();		
			create();
		}
		for(MandatoryDataM dataM:fields){
			if(dataM.getSubFormID().equals(formID)
					&& dataM.getFieldName().equals(fieldName)){
				return dataM;
			}
		}
		return null;
	}
	
	public static ArrayList<MandatoryDataM> get(String subFormID){
		if(null == fields){
			fields = new ArrayList<MandatoryDataM>();		
			create();
		}
		ArrayList<MandatoryDataM> manVect = new ArrayList<MandatoryDataM>();
		for(MandatoryDataM dataM:fields){
			if(dataM.getSubFormID().equals(subFormID)){
				manVect.add(dataM);
			}
		}
		return manVect;
	}
}
