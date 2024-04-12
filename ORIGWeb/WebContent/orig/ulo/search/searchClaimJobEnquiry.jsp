<%@page import="java.sql.Timestamp"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.control.util.ApplicationUtil"%>
<%@page import="java.math.BigDecimal"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.Vector"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.sql.Date"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConfig" %>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.InquiryHelper"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.app.view.util.pa.PAUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.service.common.util.ServiceUtil"%>
<%@page import="com.eaf.core.ulo.common.engine.SearchFormHandler"%>
<script type="text/javascript" src="orig/ulo/search/js/searchClaimJobEnquiry.js?v=1"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="SearchForm" scope="session" class="com.eaf.core.ulo.common.engine.SearchFormHandler" />
<%
	Logger logger = Logger.getLogger(this.getClass());
	ArrayList<HashMap<String, Object>> results = SearchForm.getResults();
	int TOTAL = SearchForm.getCount();

	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
	String ACTION_TYPE = flowControl.getActionType();
	String roleId = flowControl.getRole();
	String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
	String FIELD_ID_CLAIM_JOB_STATUS = SystemConstant.getConstant("FIELD_ID_CLAIM_JOB_STATUS");
	String FIELD_ID_Y_OR_N = SystemConstant.getConstant("FIELD_ID_Y_OR_N");
	String CACHE_APPLY_CHANNEL = SystemConstant.getConstant("CACHE_APPLY_CHANNEL");
	String TOTAL_RECORD = LabelUtil.getText(request, "TOTAL_RECORD");
	ArrayList<String> WIP_JOBSTATE_END = SystemConfig.getArrayListGeneralParam("WIP_JOBSTATE_END");
	String JOB_STATE_DE2_APPROVE_SUBMIT = SystemConfig.getGeneralParam("JOB_STATE_DE2_APPROVE_SUBMIT");
	String WF_STATE_RUNNING = SystemConstant.getConstant("WF_STATE_RUNNING");
	String PROCESSING = SystemConstant.getConstant("PROCESSING");
	String SEARCH_SALE_ID = SystemConstant.getConstant("SEARCH_SALE_NAME");
	String APPLICANT_INFO_TH_FULL_NAME = SystemConstant.getConstant("APPLICANT_INFO_TH_FULL_NAME");
	String APPLICANT_INFO_IDNO = SystemConstant.getConstant("APPLICANT_INFO_IDNO");
	String formatDateL = SystemConstant.getConstant("PA_DATE_FORMAT_L");
	String formatDateS = SystemConstant.getConstant("PA_DATE_FORMAT_S");
	String CTALLHEAD = SystemConstant.getConstant("CTALLHEAD");
	boolean isCTHead = ORIGUser.getRoles().contains(CTALLHEAD);
	int completeFlagCount = PAUtil.countCompleteFlag(results);
	boolean isMyTask = PAUtil.isMyTask(ACTION_TYPE);
	boolean isAccountSetup = PAUtil.isAccountSetup(ACTION_TYPE);
	boolean isStampDuty = PAUtil.isStampDuty(ACTION_TYPE);
	boolean unClaimAuthorized = PAUtil.isUnClaimAuthorized(ORIGUser.getUserName());
	logger.debug("searchClaimJobEnquiryPage - Role : " + roleId);
	
%>
<%= HtmlUtil.hidden("searchClaimJobEnquiryPage", "SearchClaimJobEnquiry") %>
<section class="work_area padding-top">
	<div class="row">
		<div class="col-xs-12">
			<div class="panel panel-default">
				<div class="panel-body" style="padding: 15px;">
					<div class="row form-horizontal">
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "DE2_SUBMIT_DATE", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.calendar("DE2_SUBMIT_DATE", "", "DE2_SUBMIT_DATE",
								FormatUtil.toDate(SearchForm.getString("DE2_SUBMIT_DATE"),FormatUtil.TH,FormatUtil.Format.ddMMyyyy), "", HtmlUtil.EDIT, "", FormatUtil.TH,
								"col-sm-8 col-md-7", request)%>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "CREDIT_LIMIT_DATE", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.calendar("CREDIT_LIMIT_DATE", "", "CREDIT_LIMIT_DATE",
								FormatUtil.toDate(SearchForm.getString("CREDIT_LIMIT_DATE"),FormatUtil.TH,FormatUtil.Format.ddMMyyyy), "", HtmlUtil.EDIT, "", FormatUtil.TH,
								"col-sm-8 col-md-7", request)%>							
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
							<%=HtmlUtil.getFieldLabel(request, "APPLICATION_NO", "col-sm-4 col-md-5 control-label")%>
							<%=HtmlUtil.textBox("APPLICATION_GROUP_NO", "", SearchForm.getString("APPLICATION_GROUP_NO"), "", "20", "", "",
							"col-sm-8 col-md-7", request)%>
							</div>
						</div>
					</div>
					<div class="row form-horizontal">
						<div class="col-sm-6">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "TH_FIRST_LAST_NAME", "col-sm-3 control-label")%>
								<div class="input-group" style="width: 70%; padding-left: 6%; position: relative; ">
						       		<%=HtmlUtil.textBoxTH("TH_FIRST_NAME", "",SearchForm.getString("TH_FIRST_NAME"), "textbox-name-1", "60", "", "", "input-group-btn", request)%>
						        	<%=HtmlUtil.textBoxTH("TH_MID_NAME", "", SearchForm.getString("TH_MID_NAME"), "textbox-name-2", "60", " placeholder='" + LabelUtil.getText(request, "SHOW_TH_MID_NAME") + "'", "", "input-group-btn", request)%>
									<%=HtmlUtil.textBoxTH("TH_LAST_NAME", "", SearchForm.getString("TH_LAST_NAME"), "textbox-name-3", "60", "", "", "input-group-btn", request)%>
								</div>
							</div>
						</div>
						<div class="col-sm-2"></div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "CIS_NO", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.textBox("CIS_NO", "", SearchForm.getString("CIS_NO"), "", "20", "", "", "col-sm-8 col-md-7", request)%>
							</div>
						</div>
					</div>
					<div class="row form-horizontal">
						<% if(!isMyTask) { %>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "JOB_STATUS", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.dropdown("CLAIM_JOB_STATUS", "", "", SearchForm.getString("CLAIM_JOB_STATUS"), "",
								FIELD_ID_CLAIM_JOB_STATUS, "ACTIVE", "", "", "col-sm-8 col-md-7", request)%>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "CLAIM_BY", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.textBox("CLAIM_BY", "", SearchForm.getString("CLAIM_BY"), "", "20", "", "", "col-sm-8 col-md-7", request)%>
							</div>
						</div>
						<% } else { %>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "COMPLETE_FLAG", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.dropdown("CLAIM_FLAG", "", "", SearchForm.getString("CLAIM_FLAG"), "",
								FIELD_ID_Y_OR_N, "ACTIVE", "", "", "col-sm-8 col-md-7", request)%>
							</div>
						</div>
						<div class="col-sm-4"></div>
						<% } %>
					</div>
					<div class="row">
						<div class="col-md-12 text-center">
							<%=HtmlUtil.button("EQ_SEARCH_BTN", "EQ_SEARCH_BTN", HtmlUtil.EDIT, "btn2 btn2-green", "", request)%>
							<%=HtmlUtil.button("EQ_RESET_BTN", "EQ_RESET_BTN", HtmlUtil.EDIT, "btn2", "", request)%>
							<% if(!isMyTask){ %>
							<%=HtmlUtil.button("CLAIM_JOB_EXPORT_BUTTON", "CLAIM_JOB_EXPORT_BUTTON", HtmlUtil.VIEW, "btn2", "", request)%>
							<% } %>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<div class="row">
	<div class="col-sm-12">
		<div>
			<h4><%=PAUtil.getClaimJobActionLabel(ACTION_TYPE)%></h4>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-5"></div>
	<% if(!isMyTask){ %>
	<div class="col-sm-7">
		<%=HtmlUtil.button("SAVE_CLAIM_BTN", "SAVE_CLAIM_BTN", HtmlUtil.EDIT, "col-sm-3 btn2 btn2-green pull-right", "", request)%>
		<div class="pull-right col-sm-1"></div>
		<table class=".table-sm pull-right col-sm-8">
			<tr bgcolor="#AAFF80">
				<td></td>
				<td>Total&nbsp;&nbsp;&nbsp;</td>
				<td><%=HtmlUtil.getText(request, "WAIT_PROCESS")%>&nbsp;&nbsp;&nbsp;</td>
				<td><%=HtmlUtil.getText(request, "COMPLETED")%>&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td><%=PAUtil.getClaimJobActionLabel(ACTION_TYPE)%>&nbsp;&nbsp;&nbsp;</td>
				<td align="center"><b><%=SearchForm.getCount()%>&nbsp;&nbsp;&nbsp;</b></td>
				<td align="center"><font color="red"><%=(SearchForm.getCount()-completeFlagCount)%>&nbsp;&nbsp;&nbsp;</font></td>
				<td align="center"><font color="#00CC66"><%=completeFlagCount%>&nbsp;&nbsp;&nbsp;</font></td>
			</tr>
		</table>
	</div>
	<% } else { %>
	<div class="col-sm-6">
		<%=HtmlUtil.button("COMPLETE_BTN", "COMPLETE", HtmlUtil.EDIT, "btn2 btn2-green pull-right", "", request)%>
	</div>
	<% } %>
</div>
<div class="row padding-top">
	<div class="col-md-12">
		<table class="table table-bordered table-striped enqtable">
			<thead>
				<tr bgcolor="#00A950">
					<% if(!isMyTask){ %>
					<th style="width: 40px;"></th>
					<% } %>
					<th style="min-width: 10px;"><font color="white">No.</font></th>
					<th style="min-width: 10px;"><font color="white">ประเภท</font></th>
					<th style="min-width: 100px;"><font color="white">หมายเลขใบสมัคร</font></th>
					<th style="min-width: 120px;"><font color="white">หมายเลข CIS</font></th>
					<th style="min-width: 90px;"><font color="white">ชื่อ</font></th>
					<th style="min-width: 60px;"><font color="white">ชื่อกลาง</font></th>
					<th style="min-width: 110px;"><font color="white">นามสกุล</font></th>
					<th style="min-width: 50px;"><font color="white">DE2 Submit Date</font></th>
					<th style="min-width: 50px;"><font color="white">วันที่ตั้งวงเงิน</font></th>
					<% if(isStampDuty) { %>
					<th style="min-width: 50px;"><font color="white">ค่าอากร</font></th>
					<% } %>
					<th style="min-width: 50px;"><font color="white">View</font></th>
					<% if(!isMyTask){ %>
					<th style="min-width: 70px;"><font color="white">Claim Flag</font></th>
					<th style="min-width: 100px;"><font color="white">Claim By</font></th>
					<% } %>
					<th style="min-width: 120px;"><font color="white">Claim Date</font></th>
					<% if(isMyTask){ %>
					<th style="min-width: 70px;"><font color="white">Complete Flag</font></th>
					<% } %>
					<th style="min-width: 100px;"><font color="white">Complete Date</font></th>
					<th style="min-width: 100px;"><font color="white">เลขที่บัญชีเงินกู้</font></th>
				</tr>
			</thead>
			<tbody id="resultsBody">
				<%
					if (!Util.empty(results)) 
					{
						
						SearchFormHandler searchForm = (SearchFormHandler)request.getSession().getAttribute("SearchForm");
						int itemsPerPage = searchForm.getItemsPerPage();
						int atPage = searchForm.getAtPage();
						
						int row_Index = ((atPage-1) * itemsPerPage) + 1;
						
						for (HashMap<String, Object> Row : results) 
						{
							logger.debug("APPLICATION_STATUS : "+Row.get("APPLICATION_STATUS"));
							String updateDateTime = FormatUtil.display(Row, "UPDATE_DATE");
							
							String lastupdate = "";
							if (!Util.empty(updateDateTime)) {
								lastupdate = LabelUtil.getText(request, "LAST_UPDATE");
							}
							String loadApplicationActionJS = "";
							if(SystemConstant.getConstant("ACTION_TYPE_ENQUIRY").equals(ACTION_TYPE)){
								loadApplicationActionJS = "onclick=loadApplicationActionJS('" + FormatUtil.display(Row, "APPLICATION_GROUP_ID")
									+ "')";
							}
							String interfaceCode = FormatUtil.display(Row, "INTERFACE_CODE");
							Date minFinalAppDecisionDate = (java.sql.Date)Row.get("MIN_FINAL_APP_DECISION_DATE");
							String DE2SubmitDate =   PAUtil.formatDate(Row.get("DE2_SUBMIT_DATE"),formatDateL);
							//String creditLimitDate = PAUtil.calcCreditLimitDate(Row.get("DE2_SUBMIT_DATE"));
							String creditLimitDate = PAUtil.formatDate(Row.get("LOAN_ACCOUNT_OPEN_DATE"),formatDateS);
							String closeAppFlag = FormatUtil.display(Row, "CLOSE_APP_FLAG");
							String claimId = FormatUtil.display(Row, "CLAIM_ID");
							String claimJobType = FormatUtil.display(Row, "CLAIM_JOB_TYPE");
							String claimFlag = FormatUtil.display(Row, "CLAIM_FLAG");
							String claimBy = FormatUtil.display(Row, "CLAIM_BY");
							String appGroupNo = FormatUtil.display(Row, "APPLICATION_GROUP_NO");
							String completeFlag = FormatUtil.display(Row, "COMPLETE_FLAG");
							String loanSetupStatus = FormatUtil.display(Row, "LOAN_SETUP_STATUS");
							String accountStatus = FormatUtil.display(Row, "LOAN_ACCOUNT_STATUS");
							if(!Util.empty(accountStatus))
							{
								accountStatus = (MConstant.FLAG.ACTIVE.equals(accountStatus)) ? "(Active)" : "(Inactive)";
							}
							logger.debug("closeAppFlag : " + closeAppFlag);
							logger.debug("interfaceCode : " + interfaceCode);
				%>
					
						
							<tr>
								<% if(!isMyTask){ %>
								<td>
									<% if(isStampDuty && !loanSetupStatus.equalsIgnoreCase("COMPLETED") && !claimFlag.equals(MConstant.FLAG.YES))
									{ %>
										 <img src="images/ulo/cancelaudit.png" onclick="cancel_AccSetup('<%=appGroupNo%>');"/>
									<% } %>
								</td>
								<% } %>
								<td align="center">
									<%=row_Index%>
								</td>
								<td align="center">
									<%=claimJobType%>
								</td>
								<td align="center">
									<%=appGroupNo%>
								</td>
								<td align="center">
									<%=Row.get("CIS_NO")%>
								</td>
								<td align="center">
									<%=Row.get("TH_FIRST_NAME")%>
								</td>
								<td align="center">
									<%=(Row.get("TH_MID_NAME") == null) ? " - " : Row.get("TH_MID_NAME")%>
								</td>
								<td align="center">
									<%=Row.get("TH_LAST_NAME")%>
								</td>
								<td align="center"> <!--DE2_SUBMIT_DATE-->
									<%=DE2SubmitDate%>
								</td>
								<td align="center"> <!--LOAN_ACCOUNT_OPEN_DATE-->
									<%=creditLimitDate%>
								</td>
								<% if(isStampDuty) { %>
								<td align="center">
									<%=FormatUtil.display(Row, "STAMP_DUTY_FEE")%>
								</td>
								<% } %>
								<td align="center">
									<a href="#" onclick="<%=PAUtil.getViewLink(claimJobType,appGroupNo,completeFlag)%>">View</a>
								</td>
								<% if(!isMyTask){ %>
								<td align="center">
									<%=HtmlUtil.checkBox("CLAIM_FLAG", claimId, claimFlag, MConstant.FLAG.YES, PAUtil.claimFlagCheckBox(claimFlag, loanSetupStatus, isCTHead), "isClaimed='"+ !Util.empty(claimBy) +"'", "", request)%>
								</td>
								<td align="center">
									<%=claimBy%>
								</td>
								<% } %>
								<td align="center">
									<%=PAUtil.formatDate(Row.get("CLAIM_DATE"),formatDateL)%>
								</td>
								<% if(isMyTask){ %>
								<td align="center">
									<%=HtmlUtil.checkBox("COMPLETE_FLAG", claimId, completeFlag, MConstant.FLAG.YES, PAUtil.completeFlagCheckBox(completeFlag), "", "", request)%>
								</td>
								<% } %>
								<td align="center">
									<%=PAUtil.formatDate(Row.get("COMPLETE_DATE"),formatDateL)%>
								</td>
								<td align="center">
									<%=(Row.get("LOAN_ACCOUNT_NO") == null) ? " - " : Row.get("LOAN_ACCOUNT_NO")%><br><%=accountStatus%></br>
								</td>
							</tr>
							
				<%
					row_Index++;
					}
					
				%>
					<script type="text/javascript">
						enable_CLAIM_JOB_EXPORT_BUTTON();
					</script>
				<%	
					} else {
				%>
				<tr>
					<td colspan="20" align="center"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td>
				</tr>
				<%
					}
				%>
			</tbody>
			<tfoot>
			</tfoot>
		</table>
	</div>
</div>
<iframe id="my_iframe" style="display:none;"></iframe>
<section class="btnbarlong">
	<jsp:include page="/orig/ulo/util/valuelist.jsp" />
</section>

<%=HtmlUtil.hidden("APPLICATION_GROUP_ID", "")%>
<%=HtmlUtil.hidden("ACTION_TYPE", ACTION_TYPE)%>
<%=HtmlUtil.hidden("currentUser", ORIGUser.getUserName())%>
<%=HtmlUtil.hidden("isMyTask", String.valueOf(isMyTask))%>

<script type="text/javascript">
	$(function(){
	var rx = /INPUT|SELECT/i;
	$(document).bind("keydown keypress keyup",function(e){
		if(e.which == 13){
			if(rx.test(e.target.tagName) || e.target.disabled || e.target.readOnly){
				e.preventDefault();
			}
		}
	}); 
	});
</script>