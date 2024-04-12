<%@page import="com.eaf.core.ulo.common.properties.SystemConfig"%>
<%@page import="com.orig.bpm.workflow.proxy.BPMWorkflowDMProxy"%>
<%@page import="com.orig.bpm.ulo.model.WorkflowDataM"%>
<%
	try{
		String BPM_HOST = SystemConfig.getProperty("BPM_HOST");
		String BPM_PORT = SystemConfig.getProperty("BPM_PORT");
		String BPM_USER_ID = SystemConfig.getProperty("BPM_USER_ID");
		String BPM_PASSWORD = SystemConfig.getProperty("BPM_PASSWORD");
		BPMWorkflowDMProxy proxy = new BPMWorkflowDMProxy(BPM_HOST,BPM_PORT,BPM_USER_ID,BPM_PASSWORD);
		WorkflowDataM workflowM = new WorkflowDataM();
			workflowM.setInstantId(1);
			workflowM.setBpdId(SystemConfig.getProperty("DM_BPD_ID"));
			workflowM.setProcessAppId(SystemConfig.getProperty("DM_PROCESS_APP_ID"));
		proxy.borrowDocument(workflowM);
	}catch(Exception e){
	}
%>