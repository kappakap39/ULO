package com.eaf.orig.ulo.app.view.webaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.engine.SearchFormHandler;
import com.eaf.core.ulo.common.model.SearchDataM;
import com.eaf.core.ulo.common.performance.TraceController;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.dao.InboxDAO;
import com.eaf.orig.ulo.app.dao.WorkflowDAOFactory;
import com.eaf.orig.ulo.app.form.search.InboxSearchFormHandler;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.control.FlowControlDataM;
import com.orig.bpm.workflow.model.BPMInbox;
import com.orig.bpm.workflow.model.BPMInboxInstance;
import com.orig.bpm.workflow.model.BPMSearchTask;
import com.orig.bpm.workflow.proxy.BPMMainFlowProxy;

@SuppressWarnings("serial")
public class InboxWebAction  extends WebActionHelper implements WebAction{
	private static transient Logger logger = Logger.getLogger(InboxWebAction.class);
	@Override
	public Event toEvent(){
		return null;
	}
	@Override
	public boolean requiredModelRequest(){
		return false;
	}
	@Override
	public boolean processEventResponse(EventResponse response){
		return false;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean preModelRequest(){
		UserDetailM userM =	(UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		String roleId = userM.getCurrentRole();
		String transactionId = roleId+"_"+FormatUtil.token(4);
		TraceController trace = new TraceController(this.getClass().getName(),transactionId);
		trace.create("summaryInbox");
		InboxSearchFormHandler searchForm = new InboxSearchFormHandler(getRequest());
		FlowControlDataM flowControl = (FlowControlDataM) getRequest().getSession().getAttribute(SessionControl.FlowControl);
		flowControl.setActionType("Inbox");	
		InboxDAO inboxDao = WorkflowDAOFactory.getInboxDAO();
		try{
			String roleIdToSummary = getRoleMappingGroup(flowControl.getRole());
			String WORK_IN_PROCESS_CONT = "WIP_JOBSTATE_"+roleIdToSummary;
			logger.debug("roleIdToSummary >> "+roleIdToSummary);
			logger.debug("WORK_IN_PROCESS_CONT >> "+WORK_IN_PROCESS_CONT);
			HashMap Summarys = inboxDao.summaryInbox(WORK_IN_PROCESS_CONT,userM.getUserName(),roleIdToSummary);
			String INBOX_FLAG = flowControl.getStoreAction("INBOX_FLAG"); //getRequest().getParameter("INBOX_FLAG");	
			if(!Util.empty(INBOX_FLAG)){
				Summarys.put("INBOX_FLAG", INBOX_FLAG);
			}
			searchForm.setSearchs(Summarys);
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		trace.end("summaryInbox");
//		int atPage = 1;			
//		int itemsPerPage = searchForm.getItemsPerPage();
//		String atPageStr = getRequest().getParameter("atPage");
//		String itemsPerPageStr = getRequest().getParameter("itemsPerPage");	
		String inBoxFlag = searchForm.getSearchs("INBOX_FLAG",MConstant.FLAG.NO);
		logger.debug("inBoxFlag::"+inBoxFlag);
		String INBOX_BTN = getRequest().getParameter("INBOX_BTN");
		logger.debug("INBOX_BTN::"+INBOX_BTN);
		if(!Util.empty(INBOX_BTN)){
			inBoxFlag = INBOX_BTN;
		}
//		if(!Util.empty(atPageStr)){
//			atPage = Integer.parseInt(atPageStr);
//		}
//		if(!Util.empty(itemsPerPageStr)){ 
//			int itemsPerPageTemp = Integer.parseInt(itemsPerPageStr);
//			if(itemsPerPage!=itemsPerPageTemp){ 
//				itemsPerPage = itemsPerPageTemp;
//				searchForm.setAtPage(1);
//			}
//		}
//		int itemIndex = (itemsPerPage*(atPage-1));
		
//		logger.debug("itemsPerPage >>"+itemsPerPage);
//		logger.debug("atPageStr >> "+atPage);
//		logger.debug("itemIndex >> "+itemIndex);
		
		String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
		String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
		
		trace.create("getInbox");	
		BPMMainFlowProxy proxy = null;
		BPMInbox bpmInbox = null;		
		try{
			BPMSearchTask searchTask = new BPMSearchTask();
//			searchTask.setOffset(itemIndex);
//			searchTask.setSize(itemsPerPage);
			searchTask.setUserId(userM.getUserName());
			searchTask.setCurrentRole(flowControl.getRole());
			searchTask.setActiveInbox(inBoxFlag.equals(MConstant.FLAG.YES)?true:false);
			searchTask.setTransactionId(transactionId);
			logger.debug("getBpmInbox()..");			
			proxy = new BPMMainFlowProxy(BPM_HOST,Integer.parseInt(BPM_PORT));
			
			//Synch bpm process on username, no multiple request per username
			synchronized(userM.getUserName().intern()){
				bpmInbox = proxy.getInbox(searchTask);
			}
			searchForm.setState("Success");
		}catch(Exception e){
			logger.fatal("ERROR",e);
			searchForm.setState("Error");
			searchForm.setErrorException(e);
			// NEED TO NOTICE USERS FOR SOME ERROR
			// - HTTP CLIENT TIMED OUT
		}
		trace.end("getInbox");
		
		searchForm.setNextPage(false);
//		searchForm.setAtPage(atPage);
//		searchForm.setItemsPerPage(itemsPerPage);	
		trace.create("loadApplication");
		try{			
			logger.debug("searchInboxData()..");						
			SearchDataM searchM = new SearchDataM();
//				searchM.setAtPage(searchForm.getAtPage());
				searchM.setDbType(searchForm.getDbType());
//				searchM.setIndex(searchForm.getIndex());
//				searchM.setItemsPerPage(itemsPerPage);
				searchM.setSQL(searchForm.getSQL());
//				searchM.setNextPage(searchForm.isNextPage());

			if(null != bpmInbox){
				List<BPMInboxInstance> instants  =  bpmInbox.getInboxInstanceList();
				if(!Util.empty(instants)){
					ArrayList<HashMap<String,Object>> searchResult = inboxDao.search(instants);
					
					ArrayList<HashMap<String,Object>> sortResult = new ArrayList<>();
					for(BPMInboxInstance instant : instants){
						String instanceId = instant.getInstanceId().toString();
						for(HashMap<String,Object> result : searchResult){
							String instantId = result.get("INSTANT_ID").toString();
							if(instanceId.equals(instantId)){
								sortResult.add(result);
							}
						}
					}
					searchResult = sortResult;
					
					if(!Util.empty(searchResult)){
						for(HashMap<String, Object> Row : searchResult){
							String INSTANT_ID = SQLQueryEngine.display(Row,"INSTANT_ID");						
							BPMInboxInstance instant  = getInstant(instants,Integer.parseInt(INSTANT_ID));	
							if(null != instant){
								if(Util.empty(instant.getInstanceDueDate())){
									instant.setInstanceDueDate(ApplicationDate.getDate());
								}
								Row.put("SLA_DATE",FormatUtil.display(new java.sql.Date(instant.getInstanceDueDate().getTime()),FormatUtil.TH,FormatUtil.Format.ddMMyyyy));
								Row.put("TASK_ID", instant.getTaskId());
								BigDecimal SLA_USED_DAY = FormatUtil.toBigDecimal(Row.get("SLA_USED_DAY"),false);
								BigDecimal SLA_DAY = FormatUtil.toBigDecimal(Row.get("SLA_DAY"),false);								 
								logger.debug("SLA_USED_DAY >> "+SLA_USED_DAY);
								logger.debug("SLA_DAY >> "+SLA_DAY);
								Row.put("OVER_SLA_FLAG",(SLA_USED_DAY.compareTo(SLA_DAY)>0)?MConstant.FLAG.YES:MConstant.FLAG.NO);
							}
							// df 2560
							PersonalInfoUtil.setPersonalInfoForSearch(Row);
						}
					}	
					searchForm.setCount(bpmInbox.getSize());
					searchForm.setResults(searchResult);
					searchForm.postSearchResult(searchResult);
				}
				if(!Util.empty(bpmInbox.getCentralQueueSize())){
					searchForm.setCentralQueue(bpmInbox.getCentralQueueSize());
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		trace.end("loadApplication");
		getRequest().getSession().setAttribute(SearchFormHandler.SearchForm,searchForm);	
		trace.trace();
		return true;
	}
	private BPMInboxInstance getInstant(List<BPMInboxInstance> instants,int instantId){
		if(null != instants){
			for (BPMInboxInstance instant : instants) {
				if(instantId==instant.getInstanceId()){	
					return instant;
				}
			}
		}
		return null;
	}
	@Override
	public int getNextActivityType() {
		return 0;
	}
	public String getRoleMappingGroup(String roleId){
		String[] ROLE_ALL_CA_INBOX  = SystemConstant.getArrayConstant("ROLE_ALL_CA_INBOX");
		String ROLE_CA = SystemConstant.getConstant("ROLE_CA");
		List<String> listRoleAllCAInbox = new ArrayList<String>(Arrays.asList(ROLE_ALL_CA_INBOX));
		String mappingRole = roleId;
		if(listRoleAllCAInbox.contains(roleId)){
			mappingRole = ROLE_CA;
		}
		return mappingRole;
	}
}
