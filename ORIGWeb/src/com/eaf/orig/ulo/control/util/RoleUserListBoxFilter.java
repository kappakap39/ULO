package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.ias.shared.model.ObjectM;
import com.eaf.orig.ulo.app.dao.ReAssignDAO;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class RoleUserListBoxFilter implements ListBoxFilterInf  {
	private static transient Logger logger = Logger.getLogger(RoleUserListBoxFilter.class);
	String LIST_BOX_CONFIG_ID_ROLE = SystemConstant.getConstant("CONFIG_ID_FILTER_ROLE");
	String CONFIG_ID_FILTER_USER = SystemConstant.getConstant("CONFIG_ID_FILTER_USER");
	String CONFIG_ID_FILTER_USER_IS_LOG_ON = SystemConstant.getConstant("CONFIG_ID_FILTER_USER_IS_LOG_ON");
	ArrayList<String> RE_ASSIGN_ROLE_CA = SystemConstant.getArrayListConstant("RE_ASSIGN_ROLE_CA");
	ArrayList<String> CONDITION_RE_ASSIGN_ROLE =SystemConstant.getArrayListConstant("CONDITION_RE_ASSIGN_ROLE");
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,String cacheId, String typeId, HttpServletRequest request) {
		logger.debug("configId : "+configId);
		logger.debug("cacheId : "+cacheId);
		ArrayList<HashMap<String,Object>> List = ListBoxControl.getListBox(cacheId,"ACTIVE");
		ArrayList<HashMap<String,Object>> vListboxFilterValue = new ArrayList<>();
		
		logger.debug("LIST_BOX_CONFIG_ID_ROLE : "+LIST_BOX_CONFIG_ID_ROLE);
		logger.debug("CONFIG_ID_FILTER_USER : "+CONFIG_ID_FILTER_USER);
		logger.debug("CONFIG_ID_FILTER_USER_IS_LOG_ON : "+CONFIG_ID_FILTER_USER_IS_LOG_ON);
		
		ArrayList<String> roleList = this.getUnderLoginRole(request);
		logger.debug("roleList : "+roleList);
		
		ReAssignDAO reAssignDao = ModuleFactory.getReAssignDAO();
		ArrayList<String> removeRoleValueList = this.getRemoveRoleList(request);
		logger.debug("removeRoleValueList : "+removeRoleValueList);
		
		try {
			if(!Util.empty(roleList)){
				if((LIST_BOX_CONFIG_ID_ROLE).equals(configId) && !Util.empty(List) ){
					for (HashMap<String, Object> hRole : List){
						String VALUE = SQLQueryEngine.display(hRole,"CODE");
						String SYSTEM_ID3 = SQLQueryEngine.display(hRole,"SYSTEM_ID3");
						//#Chatmongkol FUT Defect : 507#  Change Logic Search Role
//						 if(roleList.contains(VALUE) && !removeRoleValueList.contains(VALUE)){
//							 vListboxFilterValue.add(hRole);
//						 }
						if(MConstant.FLAG.YES.equals(SYSTEM_ID3) && roleList.contains(VALUE)){
							vListboxFilterValue.add(hRole);
						}
					}
				}else if ((CONFIG_ID_FILTER_USER).equals(configId)){
					ArrayList<String> staffNames = reAssignDao.getUserRole(roleList);
					for (HashMap<String, Object> hUser : List){
						String VALUE = SQLQueryEngine.display(hUser,"CODE");
						 if(!Util.empty(staffNames) && staffNames.contains(VALUE)){
							 vListboxFilterValue.add(hUser);
						 }
					}
					
				}else if(CONFIG_ID_FILTER_USER_IS_LOG_ON.equals(configId)){
					ArrayList<String> selectedRoles =this.getSelectedRoles(request);
					logger.debug("selectedRoles : "+selectedRoles);
					ArrayList<String> staffNames = reAssignDao.getActiveInboxUserRole(selectedRoles);
					logger.debug("staffNames : "+staffNames);
					for (HashMap<String, Object> hUser : List){
						String VALUE = SQLQueryEngine.display(hUser,"CODE");
						 if(!Util.empty(staffNames) && staffNames.contains(VALUE)){
//							 if (reAssignDao.checkUserLogOnAndInboxFlag(VALUE)) {
								 vListboxFilterValue.add(hUser);
//							 }
						 }
					}
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		logger.debug("vListboxFilterValue : "+vListboxFilterValue);
		return vListboxFilterValue;
	}
	@Override
	public void setFilterProperties(String configId, String cacheId, String typeId,HttpServletRequest request) {
		
	}
	
	private ArrayList<String> getUnderLoginRole(HttpServletRequest request){
		ArrayList<String> roleList = new ArrayList<String>();
		Vector<ObjectM> imObjects = (Vector<ObjectM>)request.getSession().getAttribute("iamObjects");
		if(!Util.empty(imObjects)){
			for(ObjectM objectDetail :imObjects){			 
				if(SystemConstant.getConstant("OBJECT_TYPE").equals(objectDetail.getObjectType())){
					roleList.add(objectDetail.getObjectName());
					logger.debug("Role Name::"+objectDetail.getObjectName());
				}
				
			}
		}
		return  roleList;
	}
	
	private ArrayList<String> getRemoveRoleList(HttpServletRequest request){
		ArrayList<String> removeRoles = new ArrayList<String>();
		try {
			FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
			String PAGE_NAME = flowControl.getStoreAction("page");
			String CONSTANT_REMOVE_ROLE_NAME ="REMOVE_ROLE_"+PAGE_NAME+"_"+flowControl.getRole();
			String[] REMOVE_ROLE_VALUE_LIST = SystemConstant.getArrayConstant(CONSTANT_REMOVE_ROLE_NAME);					
			logger.debug("==CONSTANT_REMOVE_ROLE_NAME=="+CONSTANT_REMOVE_ROLE_NAME);	
			if(!Util.empty(REMOVE_ROLE_VALUE_LIST)){
				removeRoles =  new ArrayList<String>(Arrays.asList(REMOVE_ROLE_VALUE_LIST));
			}
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return removeRoles;
	}
	
	private ArrayList<String> getSelectedRoles(HttpServletRequest request){
		ArrayList<String> selectedRoles = new ArrayList<String>();
		try {
			ModuleFormHandler moduleHandler = (ModuleFormHandler) request.getSession().getAttribute("ModuleForm");
			 String ASSING_ROLE_VALUE = moduleHandler.getRequestData("ASSING_ROLE_VALUE").replace(".", "_");
			 logger.debug(">>ASSING_ROLE_VALUE>>"+ASSING_ROLE_VALUE);
			 if(!Util.empty(ASSING_ROLE_VALUE)){
				 if(CONDITION_RE_ASSIGN_ROLE.contains(ASSING_ROLE_VALUE)){
					 addUserRoles(selectedRoles,SystemConstant.getArrayListConstant("RE_ASSIGN_ROLE_"+ASSING_ROLE_VALUE));
				 }else{
					 selectedRoles.add(ASSING_ROLE_VALUE.trim());
				 }
			 }
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return selectedRoles;
	}
	
	private void addUserRoles(ArrayList<String> selectedRoles,ArrayList<String> roles){
		if(!Util.empty(roles)){
			for(String role:roles){
				selectedRoles.add(role);
			}
		}
	}
	
	@Override
	public void init(Object objectForm) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setElementDisplayMode(String displayMode) {
		
	}
}
