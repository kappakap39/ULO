package com.eaf.orig.ulo.pl.app.utility;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import com.eaf.cache.data.MandatoryCacheDataM;
import com.eaf.j2ee.pattern.util.MessageResourceUtil;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLAuditTrailDataM;

public class AuditTrailTool {
	
	static Logger log = Logger.getLogger(AuditTrailTool.class);
	
	public void AuditTrail(HttpServletRequest request, UserDetailM userM, Vector<ORIGSubForm> subFormVect) {
		
		PLOrigFormHandler  formHandler = (PLOrigFormHandler) request.getSession().getAttribute(PLOrigFormHandler.PLORIGForm);		
		PLApplicationDataM applicationM = formHandler.getAppForm();
		
		PLApplicationDataM cApplicationM = (PLApplicationDataM) request.getSession().getAttribute(PLOrigFormHandler.CloanPlApplication);
		
		log.debug("CheckFirstApp >> "+applicationM.getCheckFirstApp());
		
		if(OrigConstant.FLAG_Y.equals(applicationM.getCheckFirstApp())){
			log.debug("is first application not validate AuditTrail... ");
			return;
		}
		
		String current_role = userM.getCurrentRole();
		
		ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
		Vector<MandatoryCacheDataM> auditVect = getAuditTrailForm(subFormVect, cacheUtil.loadAuditTrailField(OrigConstant.FLAG_Y));	
				
		log.debug("do validate AuditTrail... "+applicationM.getAuditFlag());
		
		if(auditVect != null && OrigConstant.FLAG_Y.equals(applicationM.getAuditFlag())){
					
			Vector<PLAuditTrailDataM> auditTrailVect = new Vector<PLAuditTrailDataM>();
			PLAuditTrailDataM auditTrailM = null;		
			
			for(MandatoryCacheDataM auditM :auditVect){							
				try{					
					ObjectEngine objEngine = new ObjectEngine();					
						objEngine.setSystem01(auditM.getSystemId1());
						objEngine.setSystem02(auditM.getSystemId2());
						
					Object cloneObj = objEngine.FindObject(auditM.getModelName(), cApplicationM);	
					Object appObj = objEngine.FindObject(auditM.getModelName(), applicationM);
										
					ClassEngine classEngine = new ClassEngine();
					
						Field fAppObj = classEngine.getField(appObj, auditM.getAttributeName());
						Object aObjValue = classEngine.getValue(fAppObj, appObj);
		
						Field fCloneObj = classEngine.getField(cloneObj, auditM.getAttributeName());
						Object cObjValue = classEngine.getValue(fCloneObj, cloneObj);
					
					String typeObj = classEngine.getType(fAppObj);
										
					boolean compare = objEngine.CompareValueObj(aObjValue, cObjValue, typeObj);
									
					if(!compare){
						auditTrailM = new PLAuditTrailDataM();
						String fieldName = auditM.getFieldName();
						log.debug("field name >> "+fieldName);
						auditTrailM.setFieldName(MessageResourceUtil.getTextDescription(request,(fieldName).toUpperCase()));
						
						auditTrailM.setNewValue(displayValue(aObjValue,typeObj,auditM.getFieldId(),fieldName));
						auditTrailM.setOldValue(displayValue(cObjValue,typeObj,auditM.getFieldId(),fieldName));
						
						auditTrailM.setCreateBy(userM.getUserName());
						auditTrailM.setCreateDate(new Timestamp(new Date().getTime()));
						
						auditTrailM.setRole(current_role);
						
						auditTrailVect.add(auditTrailM);
						
					}	
					
				}catch(Exception e){
					log.fatal(e.getMessage());
				}
			}			
			applicationM.setAuditTrailDataVect(auditTrailVect);
			if(null != auditTrailVect){
				log.debug("auditTrailVect.size >> "+auditTrailVect.size());
			}
		}		
	}
	public String displayValue(Object obj,String typeObj,int fieldID,String fileName) throws Exception{
		if(null == obj) return null;
		if(fieldID != 0){
			ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
			return cacheUtil.getORIGCacheDisplayNameDataM(fieldID, String.valueOf(obj));
		}
		if(ClassEngine.STRING.equals(typeObj)){
			String object = (String) obj;
			return (OrigUtil.isEmptyString(object))?null:object;
		}else if(ClassEngine.DATE.equals(typeObj)){
			try{
				Date object = (Date) obj;
				if("birth_date".equals(fileName)){
					return DataFormatUtility.dateEnToStringDateEn(object, DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S);
				}else{
					return DataFormatUtility.DateEnToStringDateTh(object, DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S);
				}
			}catch(Exception e){
				return null;
			}
		}else if(ClassEngine.TIMESTAMP.equals(typeObj)){
			try{
				Timestamp object = (Timestamp) obj;
				return DataFormatUtility.TimestampEnToStringDateTh(object, DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S_HHMM);
			}catch(Exception e){
				return null;
			}
		}else if(ClassEngine.BIGDECIMAL.equals(typeObj)){
			BigDecimal object = (BigDecimal) obj;
			return DataFormatUtility.DisplayNumber(object,"########0.00", true);
		}else if(ClassEngine.INT.equals(typeObj)){
			return String.valueOf(typeObj);
		}		
		return String.valueOf(typeObj);
	}
	private Vector<MandatoryCacheDataM> getAuditTrailForm( Vector<ORIGSubForm> subFormVect,Vector<MandatoryCacheDataM> auditVect){
		Vector<MandatoryCacheDataM> data = new Vector<MandatoryCacheDataM>();
		for(MandatoryCacheDataM dataM : auditVect){
			if(found(subFormVect, dataM)){
				data.add(dataM);
			}
		}
		return data;
	}
	private boolean found(Vector<ORIGSubForm> subFormVect,MandatoryCacheDataM dataM){
		for (ORIGSubForm origSubForm : subFormVect) {
			if(origSubForm.getSubFormID().equals(dataM.getSubFormName())){
				return true;
			}else if("ADDRESS_SUBFORM".equals(origSubForm.getSubFormID())
						&& "POPUP_ADDRESS_SUBFORM".equals(dataM.getSubFormName())){
				return true;
			}
		}
		return false;
	}
}
