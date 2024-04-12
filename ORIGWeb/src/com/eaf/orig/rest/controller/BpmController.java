package com.eaf.orig.rest.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.rest.bpm.model.ULOTask;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.shared.service.ServiceLocatorException;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.proxy.ORIGServiceProxy;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.google.gson.Gson;
import com.orig.bpm.proxy.BpmProxyConstant.BPMActivity;
import com.orig.bpm.ulo.workflow.model.WorkflowRequestDataM;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;
import com.orig.bpm.workflow.handle.WorkflowManager;
import com.orig.bpm.workflow.model.BPMInbox;
import com.orig.bpm.workflow.model.BPMInboxInstance;
import com.orig.bpm.workflow.model.BPMInstance;
import com.orig.bpm.workflow.model.BPMSearchTask;
import com.orig.bpm.workflow.model.BPMTask;
import com.orig.bpm.workflow.proxy.BPMMainFlowProxy;
import com.orig.bpm.workflow.util.BPMModelMapper;

@WebServlet(name="BpmController", urlPatterns={"/bpm/*"},description="ULO Rest-API for Flow Management")
public class BpmController extends HttpServlet{
	private static final long serialVersionUID = 1365988109583395116L;
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");	
	String PERSONAL_TYPE_INFO = SystemConstant.getConstant("PERSONAL_TYPE_INFO");	
	String NORMAL_APPLICATION_FORM = SystemConstant.getConstant("NORMAL_APPLICATION_FORM");
	String SUP_CARD_SEPARATELY_FORM = SystemConstant.getConstant("SUP_CARD_SEPARATELY_FORM");
	String INCREASE_APPLICATION_FORM = SystemConstant.getConstant("INCREASE_APPLICATION_FORM");
	private static transient Logger log = Logger.getLogger(BpmController.class);
	private static final Gson gon = new Gson();
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		try{
			String path = request.getPathInfo();
			log.debug("Request path = "+path);
			if("/app".equalsIgnoreCase(path)){
				String action = request.getParameter("action");
				log.debug("putApp : action="+action);
				if("submit".equalsIgnoreCase(action)){
					finishTask(request,response);
				}else if("reassign".equalsIgnoreCase(action)){
					reassignTask(request,response);
				}
			}else if("/inbox".equalsIgnoreCase(path)){
				getInboxTask(request, response);
			}
			else if("/task/all".equalsIgnoreCase(path)){
				getParticipantTask(request, response);
			}
			else if("/task/priority".equalsIgnoreCase(path)){
				setTaskPriority(request, response);
			}
			else if("/instance/del".equalsIgnoreCase(path)){
				deleteBPMProcessInstance(request, response);
			}
			else if("/instance/move".equalsIgnoreCase(path)){
				moveInstanceToSpecificActivity(request, response);
			}
			else if("/instance/detail".equalsIgnoreCase(path)){
				getInstanceDetail(request, response);
			}
			else if("/instance/data".equalsIgnoreCase(path)){
				getInstanceData(request, response);
			}
			else if(path == null || path.isEmpty()){
				screenFlow(request, response);
			}
		}catch(Exception e){
			log.fatal("ERROR ",e);
		}
	}
	public void screenFlow(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.getRequestDispatcher("/orig/ia/ia_home.jsp").forward(request, response);
	}
	private void finishTask(HttpServletRequest request, HttpServletResponse response) throws Exception{
		BPMMainFlowProxy proxy;
		String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
		String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
		String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
		String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
		
		String USER_NAME = request.getParameter("username");
		String PASSWORD = request.getParameter("password");	
		
		if(Util.empty(USER_NAME) || Util.empty(USER_NAME)){
			USER_NAME = BPM_USER_ID;
			PASSWORD = BPM_PASSWORD;
		}

		String instantId = request.getParameter("instantId");		
		String taskId = request.getParameter("taskId");	
				
		log.debug("BPM_HOST >> "+BPM_HOST);
		log.debug("BPM_PORT >> "+BPM_PORT);
		log.debug("USER_NAME >> "+USER_NAME);
//		log.debug("PASSWORD >> "+PASSWORD);
		log.debug("instantId >> "+instantId);
		log.debug("taskId >> "+taskId);
		
		UserDetailM userM = (UserDetailM)request.getSession().getAttribute("ORIGUser");
		if(userM == null){
			userM = new UserDetailM();
//			throw new IllegalArgumentException("UserDetailM from session ORIGUser is not presented");
		}		
		SQLQueryEngine QueryEngine = new SQLQueryEngine();
		HashMap tableApplicationGroup = QueryEngine.LoadModule("SELECT * FROM ORIG_APPLICATION_GROUP WHERE INSTANT_ID = ?",instantId);
		String applicationGroupId = SQLQueryEngine.display(tableApplicationGroup,"APPLICATION_GROUP_ID");		
		log.debug("APPLICATION_GROUP_ID >> "+applicationGroupId);	
		ApplicationGroupDataM applicationGroup = ORIGDAOFactory.getApplicationGroupDAO().loadOrigApplicationGroupM(applicationGroupId);
		if(null == applicationGroup){
			applicationGroup = new ApplicationGroupDataM();
			applicationGroup.setApplicationGroupId(GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APPLICATION_GROUP_PK));
		}
		String APPLICATION_TYPE = request.getParameter("APPLICATION_TYPE");
		String formId = FormControl.getFormIdByApplicationType(APPLICATION_TYPE);		
		if(Util.empty(formId)){
			formId = SystemConstant.getConstant("DEFAULT_APPLICATION_FORM");
		}
		applicationGroup.setApplyChannel(request.getParameter("CHANNEL"));
		applicationGroup.setApplicationTemplate(request.getParameter("TEMPLATE"));
		applicationGroup.setApplicationType(APPLICATION_TYPE);
		applicationGroup.setApplyDate(FormatUtil.toDate(request.getParameter("APPLY_DATE"),HtmlUtil.TH));
		applicationGroup.setBranchNo(request.getParameter("BRANCH_NO"));
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();
		if(null == personalInfos){
			personalInfos = new ArrayList<PersonalInfoDataM>();
		}
		if(NORMAL_APPLICATION_FORM.equals(formId) || INCREASE_APPLICATION_FORM.equals(formId)){
			createPersonalInfo(PERSONAL_TYPE_APPLICANT,1,applicationGroup,request,true);
		}else if(SUP_CARD_SEPARATELY_FORM.equals(formId)){
			createPersonalInfo(PERSONAL_TYPE_INFO,1,applicationGroup,request,false);
			createPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY,2,applicationGroup,request,true);
		}		
		ORIGServiceProxy.getApplicationManager().saveApplication(applicationGroup,userM);					
		try{
			WorkflowManager workflowManager = new WorkflowManager(BPM_HOST,BPM_PORT,USER_NAME,PASSWORD);
//			#rawi comment change logic complete workflow task.
//			proxy = new BPMMainFlowProxy(BPM_HOST,Integer.parseInt(BPM_PORT),USER_NAME,PASSWORD);
//			BPMRequest taskRequest = RestControllerUtil.constructTaskRequest(request);
//			log.debug("Submission request = "+taskRequest);
//			BPMTask task = proxy.finishTask( Long.parseLong(taskId),taskRequest);
//			request.setAttribute("BPMTask",task);
			WorkflowRequestDataM workflowRequest = new WorkflowRequestDataM();
			workflowRequest.setApplicationGroup(applicationGroup);
			workflowRequest.setUniqueId(applicationGroup.getApplicationGroupId());
			workflowRequest.setUserId(USER_NAME);
			workflowRequest.setUsername(userM.getUserName());
			WorkflowResponseDataM workflowResponse =  workflowManager.completeWorkflowTask(workflowRequest, taskId);
			request.setAttribute("BPMTask",workflowResponse.getBpmTask());
		}catch (Exception e){
			log.debug("ERROR ",e);
		}		
		request.getRequestDispatcher("/orig/ia/ia_inbox.jsp").forward(request, response);
	}
	private void createPersonalInfo(String PERSONAL_TYPE,int PERSONAL_SEQ,ApplicationGroupDataM applicationGroup,HttpServletRequest request,boolean IS_REQUEST_DATA){
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();
		if(null == personalInfos){
			personalInfos = new ArrayList<PersonalInfoDataM>();		
			applicationGroup.setPersonalInfos(personalInfos);
		}
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		if(null == personalInfo){	
			String personalId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_INFO_PK);
			personalInfo = new PersonalInfoDataM();
			personalInfo.setPersonalType(PERSONAL_TYPE);
			personalInfo.setSeq(PERSONAL_SEQ);
			personalInfo.setPersonalId(personalId);
			applicationGroup.addPersonalInfo(personalInfo);
		}	
		if(IS_REQUEST_DATA){
			personalInfo.setCidType(request.getParameter("CID_TYPE"));
			personalInfo.setThTitleCode(request.getParameter("TH_TITLE_CODE"));
			personalInfo.setThFirstName(request.getParameter("TH_FIRST_NAME"));
			personalInfo.setThLastName(request.getParameter("TH_LAST_NAME"));
			personalInfo.setBirthDate(FormatUtil.toDate(request.getParameter("TH_BIRTH_DATE"),HtmlUtil.TH));
			personalInfo.setIdno(request.getParameter("IDNO"));
		}
	}
	private void reassignTask(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("application/json");
		BPMMainFlowProxy proxy;
		String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
		String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
		String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
		String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
		
		String userToReassign = request.getParameter("user_to_reassign");				
		if(userToReassign == null || userToReassign.isEmpty()){
			log.debug("Username to reassign is null, proceeding reassign to central queue!");
		}		
		String taskIdParam = request.getParameter("taskId");		
		BPMTask task = null;
		try {
			Long taskId = Long.parseLong(taskIdParam);
			proxy = new BPMMainFlowProxy(BPM_HOST,Integer.parseInt(BPM_PORT),BPM_USER_ID,BPM_PASSWORD);
			if(userToReassign == null || userToReassign.isEmpty()){
				log.debug("Username to reassign is null, proceeding reassign to central queue!");
				task = proxy.sendTaskBackToCentralQueue(taskId);
			}else{
				log.debug("Username to reassign is "+userToReassign+", proceeding reassign to the user!");
				task = proxy.assignTaskToUser(taskId, userToReassign);
			}
			request.setAttribute("BPMTask", task);
		} catch (Exception e) {
			log.debug("ERROR",e);
		}
		response.getWriter().print(gon.toJson(task));
	}
	
	private void getInboxTask(HttpServletRequest request, HttpServletResponse response) throws Exception{
		BPMMainFlowProxy proxy;
		String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
		String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
		String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
		String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");		
		String username = request.getParameter("username");
		String password = request.getParameter("password");		
		if(Util.empty(username) || Util.empty(password)){
			username = BPM_USER_ID;
			password = BPM_PASSWORD;
		}
		BPMInbox inbox = null;
		try{
			proxy = new BPMMainFlowProxy(BPM_HOST,Integer.parseInt(BPM_PORT),username,password);
			BPMSearchTask searchTask = new BPMSearchTask();
			searchTask.setUserId(username);
			searchTask.setActiveInbox(true);
			inbox = proxy.getInbox(searchTask);
		}catch(Exception e){
			log.debug("ERROR",e);
		}
		log.debug("getInboxTask()..inbox >> "+inbox);
		request.setAttribute("BPMInbox",inbox);
		request.setCharacterEncoding("UTF-8");
		request.getRequestDispatcher("/orig/ia/ia_inbox.jsp").forward(request, response);
	}
	
	private void getParticipantTask(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		BPMMainFlowProxy proxy;
		String username = request.getParameter("username_to_inquire");
		String password = request.getParameter("password_to_inquire");
		log.debug("username >> "+username);
//		log.debug("password >> "+password);
		if(Util.empty(username) || Util.empty(password)){
			username = SystemConfig.getProperty("BPM_USER_ID");
			password = SystemConfig.getProperty("BPM_PASSWORD");
		}
		String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
		String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
		try {
			proxy = new BPMMainFlowProxy(BPM_HOST,Integer.parseInt(BPM_PORT),username,password);
			JSONObject json = proxy.getInboxSortedByPriorityAndCreateDate(null, null, null);
			BPMInbox inbox = BPMModelMapper.mapInboxResponse(json);
			List<ULOTask> uloTasks = new ArrayList<ULOTask>();
			if(inbox.getInboxInstanceList() != null){
				for(BPMInboxInstance bpmTask : inbox.getInboxInstanceList()){
					ULOTask task = new ULOTask();
					task.setTask(bpmTask);
					//constructULOTask(task);
				}
				
			}
			request.setAttribute("instanceList", inbox.getInboxInstanceList());
		} catch (Exception e) {
			log.debug("ERROR",e);
		};		
		request.getRequestDispatcher("/orig/ia/admin/task_table.jsp").forward(request, response);
	}
	
	private void deleteBPMProcessInstance(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("application/json");
		BPMMainFlowProxy proxy;
		String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
		String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
		String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
		String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");		
		
		String instanceIdParam = request.getParameter("instanceId");	
		log.debug("deleteBPMProcessInstance () instanceIdParam = "+instanceIdParam);
		BPMInstance instance = null;
		try {
			Integer instanceId = Integer.parseInt(instanceIdParam);
			proxy = new BPMMainFlowProxy(BPM_HOST,Integer.parseInt(BPM_PORT),BPM_USER_ID,BPM_PASSWORD);
			instance = proxy.deleteProcess(instanceId);
			if(instance != null)instance.setTaskList(null);
		} catch (Exception e) {
			log.debug("ERROR",e);
		}
		
		response.getWriter().print(gon.toJson(instance));
	}
	
	private void moveInstanceToSpecificActivity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("application/json");
		BPMMainFlowProxy proxy;
		String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
		String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
		String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
		String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");		
		
		String instanceIdParam = request.getParameter("instanceId");
		String activityParam = request.getParameter("activity");
		log.debug("deleteBPMProcessInstance () instanceIdParam = "+instanceIdParam);
		BPMInstance instance = null;
		BPMActivity activity = null;
		if("de1.1".equalsIgnoreCase(activityParam)){
			activity = BPMActivity.DE1_1_PROCESS;
		}
		else if("de1.2".equalsIgnoreCase(activityParam)){
			activity = BPMActivity.DE1_2_PROCESS;
		}
		else if("fraud".equalsIgnoreCase(activityParam)){
			activity = BPMActivity.FRAUD_PROCESS;
		}
		else if("de2a".equalsIgnoreCase(activityParam)){
			activity = BPMActivity.DE2A_PROCESS;
		}
		else if("de2r".equalsIgnoreCase(activityParam)){
			activity = BPMActivity.DE2R_PROCESS;
		}
		else if("dv1".equalsIgnoreCase(activityParam)){
			activity = BPMActivity.DV1_PROCESS;
		}
		else if("caa".equalsIgnoreCase(activityParam)){
			activity = BPMActivity.CA_ALLOCATE_PROCESS;
		}
		else if("ia".equalsIgnoreCase(activityParam)){
			activity = BPMActivity.IA_PROCESS;
		}
		try {
			Integer instanceId = Integer.parseInt(instanceIdParam);
			proxy = new BPMMainFlowProxy(BPM_HOST,Integer.parseInt(BPM_PORT),BPM_USER_ID,BPM_PASSWORD);
//			WorkflowData workflowData = new WorkflowData();
//			workflowData.setUserName(BPM_USER_ID);
			instance = proxy.forceSendToSpecificActivity(instanceId,activity);
			if(instance != null)instance.setTaskList(null);
		} catch (Exception e) {
			log.debug("ERROR",e);
		}
		response.getWriter().print(gon.toJson(instance));
	}
	
	private void getInstanceDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("application/json");
		BPMMainFlowProxy proxy;
		String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
		String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
		String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
		String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");		
		
		String instanceIdParam = request.getParameter("search_instance");
		log.debug("getInstanceDetail () instance = "+instanceIdParam);
		String result = null;
		try {
			Integer instanceId = Integer.parseInt(instanceIdParam);
			proxy = new BPMMainFlowProxy(BPM_HOST,Integer.parseInt(BPM_PORT),BPM_USER_ID,BPM_PASSWORD);
			JSONObject json = proxy.getInstanceCurrentState(instanceId);
			result = json.toString();
			log.debug("Result : "+json);
		} catch (Exception e) {
			log.debug("ERROR",e);
			result = e.getLocalizedMessage();
		}
		response.getWriter().print(result);
	}
	private void getInstanceData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("application/json");
		BPMMainFlowProxy proxy;
		String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
		String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
		String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
		String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");		
		
		String instanceIdParam = request.getParameter("search_instance_data");
		log.debug("getInstanceDate () instance = "+instanceIdParam);
		String result = null;
		try {
			Integer instanceId = Integer.parseInt(instanceIdParam);
			proxy = new BPMMainFlowProxy(BPM_HOST,Integer.parseInt(BPM_PORT),BPM_USER_ID,BPM_PASSWORD);
			JSONObject json = proxy.getInstanceData(instanceId);
			result = json.toString();
			log.debug("Result : "+json);
		} catch (Exception e) {
			log.debug("ERROR",e);
			result = e.getLocalizedMessage();
		}
		response.getWriter().print(result);
	}
	
	private void setTaskPriority(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("application/json");
		BPMMainFlowProxy proxy;
		String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
		String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
		String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
		String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");		

		String taskIdParam = request.getParameter("taskId");		
		String priority = request.getParameter("priority");		
		log.debug("deleteBPMProcessInstance () taskIdParam = "+taskIdParam);
		log.debug("deleteBPMProcessInstance () priority = "+priority);
		BPMTask task = null;
		try {
			proxy = new BPMMainFlowProxy(BPM_HOST,Integer.parseInt(BPM_PORT),BPM_USER_ID,BPM_PASSWORD);
			task = proxy.setTaskPriority(Long.valueOf(taskIdParam), priority);
		} catch (Exception e) {
			task = new BPMTask();
			task.setResultCode("E");
			task.setResultDesc(e.getLocalizedMessage());
			log.debug("ERROR",e);
		}

		response.getWriter().print(gon.toJson(task));
	}
	
	private void constructULOTask(ULOTask task){
		try {
			Connection conn = OrigServiceLocator.getInstance().getConnection(OrigServiceLocator.ORIG_DB);
		} catch (ServiceLocatorException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
