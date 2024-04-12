package com.eaf.orig.ulo.app.view.form.manual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.performance.TraceController;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.olddata.dao.OldDataDAO;
import com.eaf.orig.ulo.app.olddata.dao.OldDataDAOFactory;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageDataM;

@SuppressWarnings("serial")
public class OldDataApplicationForm extends FormHelper implements FormAction{
	public static transient Logger logger = Logger.getLogger(OldDataApplicationForm.class);
	public String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	public String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");	
	public String PERSONAL_TYPE_INFO = SystemConstant.getConstant("PERSONAL_TYPE_INFO");	
	public String SUP_CARD_SEPARATELY_FORM = SystemConstant.getConstant("SUP_CARD_SEPARATELY_FORM");
	public String FLIP_TYPE_INC = SystemConstant.getConstant("FLIP_TYPE_INC");
	public String DEFAULT_IA_APPLICATION_FORM = SystemConstant.getConstant("DEFAULT_IA_APPLICATION_FORM");
	public String CACHE_TEMPLATE = SystemConstant.getConstant("CACHE_TEMPLATE");
	@Override
	public Object getObjectForm() throws Exception{
		logger.debug("getObjectForm...");
		String applicationGroupId = getRequestData("APPLICATION_GROUP_ID");
		String taskId = getRequestData("TASK_ID");
		String transactionId = (String)request.getSession().getAttribute("transactionId");
		logger.debug("transactionId >> "+transactionId);
		logger.debug("applicationGroupId >> "+applicationGroupId);
		logger.debug("taskId >> "+taskId);
		return getApplicationGroup(applicationGroupId,taskId,transactionId);
	}
	public ApplicationGroupDataM getApplicationGroup(String applicationGroupId,String taskId,String transactionId) throws Exception{
		ApplicationGroupDataM applicationGroup = null;
		try{
			TraceController trace = new TraceController(this.getClass().getName(),transactionId);
			trace.create("LoadApplication");
			//Load Old Application Data from TRANSFORM table
			OldDataDAO oldDataDAO = OldDataDAOFactory.getOldDataDAO();
			applicationGroup = oldDataDAO.loadOldAppGroup(applicationGroupId);
			
			applicationGroup.setTaskId(taskId);	
			trace.end("LoadApplication");
			
			int lifeCycle = applicationGroup.getMaxLifeCycle();
			logger.debug("lifeCycle : "+lifeCycle);
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ApplicationException(e.getMessage());
		}
		return applicationGroup;
	}
	
	@Override
	public ArrayList<ORIGSubForm> filterSubform(HttpServletRequest request,ArrayList<ORIGSubForm> subforms, Object objectForm) {
		logger.debug("filterSubform...");
		ArrayList<ORIGSubForm> filterSubforms = new ArrayList<ORIGSubForm>();
		if(!Util.empty(subforms)){
			for (ORIGSubForm origSubForm : subforms) {
				String subformId = origSubForm.getSubFormID();
				String renderSubformFlag = origSubForm.renderSubformFlag(request,objectForm);
				logger.debug("[subformId] "+subformId+" renderSubformFlag >> "+renderSubformFlag);
				if(MConstant.FLAG.YES.equals(renderSubformFlag)){
					filterSubforms.add(origSubForm);
				}
			}
		}
		return filterSubforms;
	}
	
	@Override
	public void displayValueForm(HttpServletRequest request,ArrayList<ORIGSubForm> subforms, Object objectForm) {
		logger.debug("displayValueForm...");
		logger.debug("formId : "+formId);
		logger.debug("formLevel : "+formLevel);
		logger.debug("roleId : "+roleId);
		if(!Util.empty(subforms)){
			for(ORIGSubForm origSubForm : subforms){
				try{
					origSubForm.setFormId(formId);
					origSubForm.setFormLevel(formLevel);
					origSubForm.setRoleId(roleId);
					if(objectForm instanceof ApplicationGroupDataM){
						ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
						if(!Util.empty(applicationGroup)){
							logger.debug("reprocessFlag : "+applicationGroup.getReprocessFlag());
							if(Util.empty(applicationGroup.getReprocessFlag())){
								origSubForm.displayValueForm(request,objectForm);
							}
						}else{
							origSubForm.displayValueForm(request,objectForm);
						}
					}else{
						origSubForm.displayValueForm(request,objectForm);
					}
				}catch(Exception e){
					logger.fatal("ERROR ",e);
				}
			}
		}
	}
	
}
