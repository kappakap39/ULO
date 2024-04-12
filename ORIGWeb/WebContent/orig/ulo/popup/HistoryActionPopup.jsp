<%@page import="com.google.gson.Gson"%>
<%@page import="com.eaf.orig.ulo.model.app.ReasonLogDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ReasonDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConfig" %>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.model.app.PolicyRulesDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationLogDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.AuditTrailDataM"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl" %>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/popup/js/HistoryActionPopup.js"></script>
<%
	//String subformId = "AUDITTRAIL_POPUP";
	Logger logger = Logger.getLogger(this.getClass());
	int PERSONAL_SEQ = 1;
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_HISTORY_ACTION = FormatUtil.getSmartDataEntryId("",PERSONAL_SEQ);
	//ArrayList<ApplicationLogDataM> applicationLogs =applicationGroup.getApplicationLogs();
	ArrayList<ApplicationLogDataM> applicationLogs =applicationGroup.getSortDateAscendingApplicationLog();
	String PARAM_CODE_JOB_STATE_PRE_REJECT = SystemConstant.getConstant("PARAM_CODE_JOB_STATE_PRE_REJECT");
	String PARAM_CODE_JOB_STATE_CANCELLED = SystemConstant.getConstant("PARAM_CODE_JOB_STATE_CANCELLED");
	String PARAM_CODE_JOB_STATE_REJECTED = SystemConstant.getConstant("PARAM_CODE_JOB_STATE_REJECTED");
	String FIELD_ID_CANCELLED = SystemConstant.getConstant("FIELD_ID_CANCELLED");
	String FIELD_ID_REJECTED = SystemConstant.getConstant("FIELD_ID_REJECTED");
%>
<style>
	#popover_HISTORY {
		width: 90%;
		height: 70%;
	}
	
	#popover_HISTORY thead tr , #popover_HISTORY tbody tr {
		display: flex;
	}
	
	#popover_HISTORY tbody tr td {
		text-align: center;
	}
	
	#popover_HISTORY td:nth-child(1), #popover_HISTORY th:nth-child(1){
		width: 10%;
	}
	#popover_HISTORY td:nth-child(2), #popover_HISTORY th:nth-child(2){
		width: 15%;
	}
	#popover_HISTORY td:nth-child(3), #popover_HISTORY th:nth-child(3){
		width: 15%;
	}
	#popover_HISTORY td:nth-child(4), #popover_HISTORY th:nth-child(4){
		width: 15%;
	}
	#popover_HISTORY td:nth-child(5), #popover_HISTORY th:nth-child(5){
		width: 25%;
	}
	#popover_HISTORY td:nth-child(6), #popover_HISTORY th:nth-child(6){
		width: 20%;
	}
</style>
<div class="row padding-top full-box">
		<div class="full-box">
			<table class="table table-hover table-striped scrollable-table">
				<thead>
					<tr>
						<th><%=LabelUtil.getText(request, "SEQ") %></th>
						<th><%=LabelUtil.getText(request, "ACTION_UPDATE_DATE") %></th>
						<th><%=LabelUtil.getText(request, "ACTION_STATUS") %></th>
						<th><%=LabelUtil.getText(request, "ACTION_BY") %></th>
						<th><%=LabelUtil.getText(request, "APPLICATION_STATUS") %></th>
						<th><%=LabelUtil.getText(request, "REASON_REJECT") %></th>
					</tr>
				</thead>
				<tbody class="medium-scrollable">
					<%
						String[] JOB_STATE_PRE_REJECT =SystemConfig.getGeneralParam(PARAM_CODE_JOB_STATE_PRE_REJECT).split(",");
						String[] JOB_STATE_CANCELLED =SystemConfig.getGeneralParam(PARAM_CODE_JOB_STATE_CANCELLED).split(",");
						String[] JOB_STATE_REJECTED =SystemConfig.getGeneralParam(PARAM_CODE_JOB_STATE_REJECTED).split(",");
						ArrayList<String> JOB_STATE_PRE_REJECT_LIST = new ArrayList<String>(Arrays.asList(JOB_STATE_PRE_REJECT));
						ArrayList<String> JOB_STATE_CANCELLED_LIST = new ArrayList<String>(Arrays.asList(JOB_STATE_CANCELLED));
						ArrayList<String> JOB_STATE_REJECTED_LIST = new ArrayList<String>(Arrays.asList(JOB_STATE_REJECTED));
					if(!Util.empty(applicationLogs)){ 
						int count=0;
						for(ApplicationLogDataM applicationLog:applicationLogs){
							ArrayList<ApplicationDataM>   applicationList = new ArrayList<ApplicationDataM>();
							if(null != applicationLog.getLifeCycle()){
								applicationList = applicationGroup.getApplicationLifeCycle(applicationLog.getLifeCycle());
							}
							StringBuilder resonListStr = new StringBuilder();
							if(!Util.empty(applicationList)){
								for(ApplicationDataM applicationData :applicationList){
									ArrayList<ReasonDataM> reasonList =applicationData.getReasons();
									String JOB_STATE = applicationLog.getJobState();
									if(JOB_STATE_PRE_REJECT_LIST.contains(JOB_STATE)){
									for(int i=0;null!=reasonList && i<reasonList.size();i++){
										ReasonDataM reason =  reasonList.get(i);
										if(null != reason){
											String resonTxt = ListBoxControl.getName(FIELD_ID_REJECTED,reason.getReasonCode());
	// 										resonListStr.append(FormatUtil.display(reason.getReasonCode()));
											resonListStr.append(resonTxt);
											if(i<reasonList.size()-1){
												resonListStr.append(",");
											}
										}
									}
									}else if(JOB_STATE_CANCELLED_LIST.contains(JOB_STATE)){
									for(int i=0;null!=reasonList && i<reasonList.size();i++){
										ReasonDataM reason =  reasonList.get(i);
										if(null != reason){
											String resonTxt = ListBoxControl.getName(FIELD_ID_CANCELLED,reason.getReasonCode());
	// 										resonListStr.append(FormatUtil.display(reason.getReasonCode()));
											resonListStr.append(resonTxt);
											if(i<reasonList.size()-1){
												resonListStr.append(",");
											}
										}
									}
									}else if(JOB_STATE_REJECTED_LIST.contains(JOB_STATE)){
									ArrayList<PolicyRulesDataM> policyRoles = null;
									VerificationResultDataM verification = applicationData.getVerificationResult();
									if(!Util.empty(verification))
										policyRoles = verification.getPolicyRules();
									if(!Util.empty(reasonList)){
										for(int i=0;null!=reasonList && i<reasonList.size();i++){
											ReasonDataM reason =  reasonList.get(i);
												if(null != reason){
													String resonTxt = ListBoxControl.getName(FIELD_ID_REJECTED,reason.getReasonCode());
			// 										resonListStr.append(FormatUtil.display(reason.getReasonCode()));
													resonListStr.append(resonTxt);
													if(i<reasonList.size()-1){
														resonListStr.append(",");
													}
												}
											}
										}else if(!Util.empty(policyRoles)){
												int index = 0;
												int lengthReason=0;
												for(PolicyRulesDataM policyRole : policyRoles){
													if(!Util.empty(policyRole.getReason())){
														lengthReason++;
													}
												}
												for(PolicyRulesDataM policyRole : policyRoles){
													if(!Util.empty(policyRole) && !Util.empty(policyRole.getReason())){
													String resonTxt = ListBoxControl.getName(FIELD_ID_REJECTED,policyRole.getReason());
			// 										resonListStr.append(FormatUtil.display(reason.getReasonCode()));
													resonListStr.append(resonTxt);
													if(index<lengthReason-1){
														resonListStr.append(",");
													}
													index++;
												}
											}
											logger.debug("defect info Full String : "+resonListStr);
										}
									}
								}	
							}else if(!Util.empty(applicationGroup.getReasons())){
								ArrayList<ReasonDataM> reasonList =applicationGroup.getReasons();
									String JOB_STATE = applicationLog.getJobState();
									if(JOB_STATE_PRE_REJECT_LIST.contains(JOB_STATE)){
									for(int i=0;null!=reasonList && i<reasonList.size();i++){
										ReasonDataM reason =  reasonList.get(i);
										if(null != reason){
											String resonTxt = ListBoxControl.getName(FIELD_ID_REJECTED,reason.getReasonCode());
	// 										resonListStr.append(FormatUtil.display(reason.getReasonCode()));
											resonListStr.append(resonTxt);
											if(i<reasonList.size()-1){
												resonListStr.append(",");
											}
										}
									}
									}else if(JOB_STATE_CANCELLED_LIST.contains(JOB_STATE)){
									for(int i=0;null!=reasonList && i<reasonList.size();i++){
										ReasonDataM reason =  reasonList.get(i);
										if(null != reason){
											String resonTxt = ListBoxControl.getName(FIELD_ID_CANCELLED,reason.getReasonCode());
	// 										resonListStr.append(FormatUtil.display(reason.getReasonCode()));
											resonListStr.append(resonTxt);
											if(i<reasonList.size()-1){
												resonListStr.append(",");
											}
										}
									}
									}else if(JOB_STATE_REJECTED_LIST.contains(JOB_STATE)){
									for(int i=0;null!=reasonList && i<reasonList.size();i++){
										ReasonDataM reason =  reasonList.get(i);
										if(null != reason){
											String resonTxt = ListBoxControl.getName(FIELD_ID_REJECTED,reason.getReasonCode());
	// 										resonListStr.append(FormatUtil.display(reason.getReasonCode()));
											resonListStr.append(resonTxt);
											if(i<reasonList.size()-1){
												resonListStr.append(",");
											}
										}
									}
									}
								
							}
							if(Util.empty(resonListStr.toString())){
								resonListStr = new StringBuilder("-");
							}
							%>
						<tr>
							<td><%=FormatUtil.toString(++count)%></td>
							<td><%=FormatUtil.display(applicationLog.getUpdateDate(),FormatUtil.TH,FormatUtil.Format.ddMMyyyy)%>
								<%=FormatUtil.display(applicationLog.getUpdateDate(),FormatUtil.TH,FormatUtil.Format.HHMMSS)%></td>
							<td><%=FormatUtil.displayText(applicationLog.getCreateBy())%> - <%=FormatUtil.display(applicationLog.getAction())%></td>
							<td><%=CacheControl.getName("User",applicationLog.getCreateBy())%></td>
							<td><%=FormatUtil.displayText(applicationLog.getApplicationStatus())%></td>
							<td><%=FormatUtil.displayText(resonListStr.toString())%></td>
						</tr>
					<% }
					}else{ %>
						<tr>
							<td colspan="6" align="center"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td>
						</tr>
					<%} %>
				</tbody>
				<tfoot>
					<tr><td colspan="6"></td></tr>
				</tfoot>
		</table>
	</div>
</div>