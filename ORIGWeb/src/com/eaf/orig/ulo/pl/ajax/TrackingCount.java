package com.eaf.orig.ulo.pl.ajax;

import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.app.utility.ORIGLogic;
import com.eaf.orig.ulo.pl.app.utility.WebActionUtil;
import com.eaf.orig.ulo.pl.model.app.TrackingDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class TrackingCount implements AjaxDisplayGenerateInf{
	
	static Logger logger = Logger.getLogger(TrackingCount.class);
	
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {

		TrackingDataM trackM = new TrackingDataM();
		
		setParameter(request,trackM);
			
		int countAQ = 0;
		int countUser = 0;
		String countLogOn = "0/0";
		String countOnJob = "0/0";
		int countPreviousJob = 0;
		int countPreviousJobEdit = 0;
		int countNewJob = 0;
		int countSubmitJob = 0;
		int countSubmitEditJob = 0;
		int countRemainJob = 0;
		int countCancleJob = 0;
		int countReassignJob = 0;
		int countBlockJob = 0;
		
		JsonObjectUtil json = new JsonObjectUtil();
		
		try{
			ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();			
			countAQ = origBean.countAutoQueue(trackM);
			countUser = origBean.countUser(trackM);
			countLogOn = origBean.countLogOn(trackM);
			countOnJob = origBean.countOnJob(trackM);
			
			HashMap<String, Integer> hWfJob = origBean.countWfJob(trackM);
			
			countPreviousJob = get(hWfJob,"countPreviousJob");
			countPreviousJobEdit = get(hWfJob,"countPreviousJobEdit");
			countNewJob = get(hWfJob,"countNewJob");
			countSubmitJob = get(hWfJob,"countSubmitJob");
			countSubmitEditJob = get(hWfJob,"countSubmitEditJob");
			countRemainJob = get(hWfJob,"countRemainJob");
			countCancleJob = get(hWfJob,"countCancleJob");
			countReassignJob = get(hWfJob,"countReassignJob");
			countBlockJob = get(hWfJob, "countBlockJob");
			
		}catch(Exception e){
			logger.fatal("Exception ",e);
		}
		
		json.CreateJsonObject("autoQueue", countAQ);
		json.CreateJsonObject("countUser", display(countUser));
		json.CreateJsonObject("countLogOn",display(countLogOn));
		json.CreateJsonObject("countOnJob", display(countOnJob));
		json.CreateJsonObject("countPreviousJob", display(countPreviousJob));
		json.CreateJsonObject("countPreviousJobEdit", display(countPreviousJobEdit));
		json.CreateJsonObject("countNewJob", display(countNewJob));
		json.CreateJsonObject("countSubmitJob", display(countSubmitJob));
		json.CreateJsonObject("countSubmitEditJob", display(countSubmitEditJob));
		json.CreateJsonObject("countRemainJob", display(countRemainJob));
		json.CreateJsonObject("countCancleJob", display(countCancleJob));
		json.CreateJsonObject("countReassignJob", display(countReassignJob));
		json.CreateJsonObject("countBlockJob", display(countBlockJob));
		
		return json.returnJson();
	}
	
	public void setParameter(HttpServletRequest request,TrackingDataM trackM){
		
		String firstName = request.getParameter("empFirstName");
		String lastName  = request.getParameter("empLastName");
		String logOn	 = request.getParameter("statusLogOn");
		String onJob 	 = request.getParameter("statusOnJob");
		String empID	 = request.getParameter("empId");
		
		String tracking_group = request.getParameter("tracking_group");
		String tracking_role = request.getParameter("tracking_role");
		
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");		
		
		String current_role = userM.getCurrentRole();
		
		String searchRole = WebActionUtil.getUnderRole(current_role);
		String wfRole = WebActionUtil.getUnderRoleWf(current_role);
		
		Vector<ORIGCacheDataM> vRole = new Vector<ORIGCacheDataM>();
		
		boolean fixrole = true;
		if(ORIGLogic.superTrackingSearch(current_role) && !OrigUtil.isEmptyString(tracking_role)){
			searchRole = tracking_role;
			wfRole = WebActionUtil.getRoleWf(tracking_role);
		}else if(ORIGLogic.superTrackingSearch(current_role)){
			fixrole = false;
			vRole = getRole(current_role,tracking_group,tracking_role);
		}
		
		logger.debug("searchRole >> "+searchRole);
		logger.debug("wfRole >> "+wfRole);
		
		trackM.setFirstName(firstName);
		trackM.setLastName(lastName);
		trackM.setLogOn(logOn);
		trackM.setOnJob(onJob);
		trackM.setEmpID(empID);
		trackM.setRole(searchRole);
		trackM.setWfRole(wfRole);
		trackM.setvRole(vRole);
		trackM.setFixrole(fixrole);
		
	}
	public Vector<ORIGCacheDataM> getRole(String current_role ,String tracking_group ,String tracking_role){
		Vector<ORIGCacheDataM> vRole = new Vector<ORIGCacheDataM>();
		if(ORIGLogic.superTrackingSearch(current_role)){		
			ORIGCacheUtil origCache = ORIGCacheUtil.getInstance();
			vRole = (Vector<ORIGCacheDataM>)origCache.getRoleTracking();
			if(!OrigUtil.isEmptyString(tracking_group)){
				vRole = getRoleByGroup(vRole,tracking_group);
			}
			if(!OrigUtil.isEmptyString(tracking_role)){
				vRole = getRole(vRole,tracking_role);
			}
		}
		return vRole;		
	}
	public Vector<ORIGCacheDataM> getRole(Vector<ORIGCacheDataM> vRole,String role){
		Vector<ORIGCacheDataM>  data = new Vector<ORIGCacheDataM>();
			for(ORIGCacheDataM dataM : vRole){
				if(null != role && role.equals(dataM.getCode())){
					data.add(dataM);
				}
			}
		return data;		
	}
	public Vector<ORIGCacheDataM> getRoleByGroup(Vector<ORIGCacheDataM> vRole,String tracking_group){
		Vector<ORIGCacheDataM>  data = new Vector<ORIGCacheDataM>();
			for(ORIGCacheDataM dataM : vRole){
				if(null != tracking_group && tracking_group.equals(dataM.getSystemID2())){
					data.add(dataM);
				}
			}
		return data;		
	}
	public int get(HashMap<String, Integer> hWfJob ,String key){
		if(null == hWfJob) return 0;
		Integer value = hWfJob.get(key);
		if(null == value) return 0;
		return value;
	}
	public String display(String value){
		StringBuilder str = new StringBuilder("");
		str.append("(");
		str.append(value);
		str.append(")");
		return str.toString();
	}
	public String display(int value){
		StringBuilder str = new StringBuilder("");
		str.append("(");
		str.append(value);
		str.append(")");
		return str.toString();
	}
}
