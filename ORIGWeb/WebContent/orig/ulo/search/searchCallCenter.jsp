<%@page import="com.eaf.orig.ulo.control.util.ApplicationUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConfig"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil"%>
<%@page import="com.eaf.core.ulo.common.engine.SQLQueryEngine"%>
<%@page import="com.google.gson.Gson"%>
<%@page import="com.eaf.inf.batch.ulo.notification.util.NotificationUtil" %>
<%@page import="com.eaf.inf.batch.ulo.notification.model.NotifyApplicationSegment"%>
<%@page import="com.eaf.inf.batch.ulo.notification.model.Reason"%>
<script type="text/javascript" src="orig/ulo/search/js/searchCallCenter.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="SearchForm" scope="session" class="com.eaf.core.ulo.common.engine.SearchFormHandler" />

<%
    Logger logger = Logger.getLogger(this.getClass());

    // Cache
    String FIELD_ID_PRODUCT_TYPE = SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE");
    String CACHE_APPLY_CHANNEL = SystemConstant.getConstant("CACHE_APPLY_CHANNEL");
	String FIELD_ID_APPLICATION_STATUS = SystemConstant.getConstant("FIELD_ID_APPLICATION_STATUS");
	String FIELD_ID_SEARCH_CALLCENTER = SystemConstant.getConstant("FIELD_ID_SEARCH_CALLCENTER");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String CACHE_CARD_INFO = SystemConstant.getConstant("CACHE_CARD_INFO");
	String CACHE_NAME_CARDTYPE = SystemConstant.getConstant("CACHE_NAME_CARDTYPE");
	String WIP_JOBSTATE_END = SystemConfig.getGeneralParam("WIP_JOBSTATE_END");
	String WIP_JOBSTATE_SYS = SystemConfig.getGeneralParam("WIP_JOBSTATE_SYS");
	ArrayList<String> DECISION_FINAL_DECISION = SystemConstant.getArrayListConstant("DECISION_FINAL_DECISION_CALL_CENTER_EXCEPTION");
	String CHANNEL_BRANCH = SystemConstant.getConstant("CHANNEL_BRANCH");
	String JOBSTATE_POST_APPROVED = SystemConstant.getConstant("JOBSTATE_POST_APPROVED");
	String JOBSTATE_REJECT = SystemConstant.getConstant("JOBSTATE_REJECT");
	String JOB_STATE_CANCELLED = SystemConfig.getGeneralParam("JOB_STATE_CANCELLED");
	String JOB_STATE_REJECTED = SystemConfig.getGeneralParam("JOB_STATE_REJECTED");
	String JOBSTATE_APPROVED = SystemConstant.getConstant("JOBSTATE_APPROVED");
	
    ArrayList<HashMap<String, Object>> results = SearchForm.getResults();
	logger.debug("results : "+results);
    BigDecimal TOTAL = FormatUtil.toBigDecimal(FormatUtil.toString(SearchForm.getCount()));
%>
<div class="panel panel-default working_area" style="margin: 15px">
	<div class="panel-body" style="padding: 15px;">
		<div class="row">
			<div class="col-md-4">
				<div class="row form-horizontal">
					<div class="col-sm-12">
						<div class="form-group">
							<%=HtmlUtil.getFieldLabel(request, "APPLICATION_NO","col-sm-4 col-md-5 control-label")%>
							<%=HtmlUtil.textBox("APPLICATION_NO", "",SearchForm.getString("APPLICATION_NO"), "", "15", HtmlUtil.EDIT, "","col-sm-8 col-md-7", request)%>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="row form-horizontal">
					<div class="col-sm-12">
						<div class="form-group">
							<%=HtmlUtil.getFieldLabel(request, "DM_APPLICATION_STATUS", "col-sm-4 col-md-5 control-label")%>
							<%=HtmlUtil.dropdown("APPLICATION_STATUS", "", "",SearchForm.getString("APPLICATION_STATUS"), "", FIELD_ID_SEARCH_CALLCENTER, "", "", "","col-sm-8 col-md-7", request)%>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="row form-horizontal">
					<div class="col-sm-12">
						<div class="form-group">
							<%=HtmlUtil.getFieldLabel(request, "EQ_PRODUCT","col-sm-4 col-md-5 control-label")%>
							<%=HtmlUtil.dropdown("PRODUCT", "", "", SearchForm.getString("PRODUCT"), "",FIELD_ID_PRODUCT_TYPE, "", "", "", "col-sm-8 col-md-7", request)%>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="row form-horizontal">
					<div class="col-sm-12">
						<div class="form-group">
							<%=HtmlUtil.getFieldLabel(request, "DM_FIRST_NAME","col-sm-4 col-md-5 control-label")%>
							<%=HtmlUtil.textBox("TH_FIRST_NAME", "", SearchForm.getString("TH_FIRST_NAME"),"", "120", HtmlUtil.EDIT, "", "col-sm-8 col-md-7", request)%>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="row form-horizontal">
					<div class="col-sm-12">
						<div class="form-group">
							<%=HtmlUtil.getFieldLabel(request, "DM_LAST_NAME", "col-sm-4 col-md-5 control-label")%>
							<%=HtmlUtil.textBox("TH_LAST_NAME", "", SearchForm.getString("TH_LAST_NAME"), "", "50", HtmlUtil.EDIT, "", "col-sm-8 col-md-7", request)%>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="row form-horizontal">
					<div class="col-sm-12">
						<div class="form-group">
							<%=HtmlUtil.getFieldLabel(request, "DM_IMPORTANT_DOC_NO", "col-sm-4 col-md-5 control-label")%>
							<%=HtmlUtil.textBox("IDNO", "", SearchForm.getString("IDNO"), "", "20",HtmlUtil.EDIT, "", "col-sm-8 col-md-7", request)%>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-4">
				<div class="row form-horizontal">
					<div class="col-sm-12">
						<div class="form-group">
							<%=HtmlUtil.getFieldLabel(request, "APPLY_CHANNEL","col-sm-4 col-md-5 control-label")%>
							<%=HtmlUtil.dropdown("APPLY_CHANNEL", "", "",SearchForm.getString("APPLY_CHANNEL"), "", CACHE_APPLY_CHANNEL, "", "", "", "col-sm-8 col-md-7", request)%>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="row form-horizontal">
					<div class="col-sm-12">
						<div class="form-group">
							<%=HtmlUtil.getFieldLabel(request, "EQ_SCAN_DATE_FROM", "col-sm-4 col-md-5 control-label")%>
							<%=HtmlUtil.calendar("SCAN_DATE_FROM", "", "SCAN_DATE_FROM",
							FormatUtil.toDate(SearchForm.getString("SCAN_DATE_FROM"),FormatUtil.TH,FormatUtil.Format.ddMMyyyy), "", HtmlUtil.EDIT, "",FormatUtil.TH, "col-sm-8 col-md-7", request)%>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-4">
				<div class="row form-horizontal">
					<div class="col-sm-12">
						<div class="form-group">
							<%=HtmlUtil.getFieldLabel(request, "EQ_SCAN_DATE_TO","col-sm-4 col-md-5 control-label")%>
							<%=HtmlUtil.calendar("SCAN_DATE_TO", "", "SCAN_DATE_TO",
							FormatUtil.toDate(SearchForm.getString("SCAN_DATE_TO"),FormatUtil.TH,FormatUtil.Format.ddMMyyyy), "", HtmlUtil.EDIT, "",FormatUtil.TH, "col-sm-8 col-md-7", request)%>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row">
			<div class="col-sm-4">
				<div class="form-group">
				</div>
			</div>
			<div class="col-sm-4">
					<div class="form-group">
							<%=HtmlUtil.getFieldLabel(request, "FINAL_DECISION_DATE_FROM", "col-sm-4 col-md-5 control-label")%>
							<%=HtmlUtil.calendar("FINAL_DECISION_DATE_FROM", "", "FINAL_DECISION_DATE_FROM",
							FormatUtil.toDate(SearchForm.getString("FINAL_DECISION_DATE_FROM"),FormatUtil.TH,FormatUtil.Format.ddMMyyyy), "", HtmlUtil.EDIT, "", FormatUtil.TH,
							"col-sm-8 col-md-7", request)%>
					</div>
			</div>
			<div class="col-sm-4">
					<div class="form-group">
							<%=HtmlUtil.getFieldLabel(request, "FINAL_DECISION_DATE_TO", "col-sm-4 col-md-5 control-label")%>
							<%=HtmlUtil.calendar("FINAL_DECISION_DATE_TO", "", "FINAL_DECISION_DATE_TO",FormatUtil.toDate(SearchForm.getString("FINAL_DECISION_DATE_TO"),FormatUtil.TH,FormatUtil.Format.ddMMyyyy),
							"", HtmlUtil.EDIT, "", FormatUtil.TH, "col-sm-8 col-md-7", request)%>
					</div>
			</div>
		</div>
		
		<div class="row">
			<div class="col-md-12 text-center">
				<%=HtmlUtil.button("EQ_SEARCH_BTN", "EQ_SEARCH_BTN", HtmlUtil.EDIT,"btn2 btn2-green", "", request)%>
				<%=HtmlUtil.button("EQ_RESET_BTN", "EQ_RESET_BTN", HtmlUtil.EDIT, "btn2", "",request)%>
			</div>
		</div>
	</div>
</div>
<div class="row padding-top">
	<div class="col-md-12">
		<div class="titlesearch">
			<%=HtmlUtil.getFieldLabel(request, "TOTAL_RECORD")%><%=FormatUtil.display(TOTAL,FormatUtil.Format.NUMBER_FORMAT)%>
		</div>
	</div>
</div>
<!--  Line here  -->

<table class="table table-callcenter table-hover" style="width: 100%;">
	<thead>
		<tr>
			<td width="5%"></td>
			<td width="15%"></td>
			<td width="10%"></td>
			<td width="20%"></td>
			<td width="15%"></td>
			<td width="15%"></td>
			<td width="15%"></td>
			<td width="10%"></td>
		</tr>
	</thead>
	<%
	    if (!Util.empty(results)) {
			for (HashMap<String, Object> Row : results) {
	%>
	<tr class="appheader">
		<td class="firstcol text-left"><img src="<%=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_PRIORITY"),
			    FormatUtil.display(Row, "PRIORITY"), "SYSTEM_ID1")%>" /></td>
		<td class="text-left"><%=HtmlUtil.getFieldLabel(request, "APPLICATION_NO")%></td>
		<td class="text-left" colspan="2" align="left"><%=FormatUtil.display(ApplicationUtil.getApplicationGroupNo(Row))%></td>
		<td class="text-left"><%=HtmlUtil.getFieldLabel(request, "APPLY_CHANNEL")%></td>
		<td class="text-left" colspan="2" align="left"><%=CHANNEL_BRANCH.equals(FormatUtil.display(Row, "APPLY_CHANNEL"))?CacheControl.displayName(CACHE_APPLY_CHANNEL, FormatUtil.display(Row, "APPLY_CHANNEL"))+" - "+FormatUtil.display(Row, "BRANCH_NAME"):CacheControl.displayName(CACHE_APPLY_CHANNEL, FormatUtil.display(Row, "APPLY_CHANNEL"))%></td>
		<td></td>
	</tr>
	<% 
		List<HashMap<String, Object>> salesData = (List<HashMap<String, Object>>) Row.get("salesData");
		HashMap<String, Object> salesRecord = null;
		if (Util.empty(salesData)) {
			salesRecord = new HashMap<String, Object>();
		} else {
			salesRecord = salesData.get(0);
		}
		String jobState = FormatUtil.display(Row, "JOB_STATE");
		String applicationStatus = FormatUtil.display(Row, "APPLICATION_STATUS");
		String source = FormatUtil.display(Row, "SOURCE");   
	%>
 	<tr class="appdetail">
		<td rowspan="2" class="firstcol text-left">
		<% 	if (!WIP_JOBSTATE_END.contains(jobState) && !WIP_JOBSTATE_SYS.contains(jobState) && !(ApplicationUtil.eApp(source) && SystemConfig.containsGeneralParam("APPLICATION_STATUS_SYSTEM", applicationStatus))) { %>
			<a class="btncancelapp"	href="#" row-data="{rowtype:group,uniqueid:<%=FormatUtil.display(Row, "APPLICATION_GROUP_ID")%>}">
			<img src="images/ulo/cancelaudit.png" /></a>
		<%} %>
		</td>
		<td class="text-left"><%=HtmlUtil.getFieldLabel(request, "EQ_SALES_ID")%></td>
		<td colspan="2" class="text-left"><%=FormatUtil.display(salesRecord, "SALES_ID")%></td>
		<td class="text-left"><%=HtmlUtil.getFieldLabel(request, "EQ_DOC_CREATE_DATE")%></td>
		<td class="text-left">
			<%=FormatUtil.display(Row, "CREATE_DATE").substring(0,10)%>
		</td>
		<td class="text-left"><%=HtmlUtil.getFieldLabel(request, "APPLICATION_TYPE")%></td>
		<td class="text-left"><%=FormatUtil.display(Row, "APPLICATION_TYPE")%></td>
	</tr>
	<tr class="appdetail">
		<!-- image -->
		<td class="text-left"><%=HtmlUtil.getFieldLabel(request, "EQ_SALES_NAME")%></td>
		<td class="text-left" colspan="2"><%=FormatUtil.display(salesRecord, "SALES_NAME")%></td>
		<td class="text-left" colspan=""><%=HtmlUtil.getFieldLabel(request, "EQ_ORDER_DOC")%></td>
		<td class="text-left">
			<%
				List<HashMap<String, Object>> docList = (List<HashMap<String, Object>>) Row.get("docData");
 			    logger.debug("docList : " + docList);
 			    if (!Util.empty(docList)){
	 				for (int i = 0, count = docList.size(); i < count; i++) {
	 				    HashMap<String, Object> row = docList.get(i);
	 				    out.write(FormatUtil.display(row, "TH_DESC") + "<br>");
	 				}
 			    }else{
 			    	out.write("-");
 			    }
			%>
		</td>
		<td></td>
		<td></td>
	</tr>
	<%
	    List<HashMap<String, Object>> dataTmp = (List<HashMap<String, Object>>) Row.get("subData");
		if (!Util.empty(dataTmp)) {
				for (HashMap<String, Object> index : dataTmp) {
				    logger.debug("subData :" + index);
				    
				    //Display cardType for KPL
				    String isKPLStr = Row.get("isKPL").toString();
				    boolean isKPL = Boolean.parseBoolean(isKPLStr);
				    String cardTypeId = FormatUtil.display(index, "CARD_TYPE");
				    String appStatus = FormatUtil.display(index, "APPLICATION_STATUS");
				    if(isKPL && (!JOBSTATE_POST_APPROVED.equals(jobState) 
				    				&& !JOB_STATE_REJECTED.equals(jobState)
				    				&& !JOBSTATE_REJECT.equals(jobState)
				    				&& !JOB_STATE_CANCELLED.equals(jobState)))
				    {
				    	appStatus = LabelUtil.getText(request, "WORK_IN_PROGRESS");
				    }
				    if(isKPL && JOBSTATE_APPROVED.equals(jobState)){
				   	  	appStatus = LabelUtil.getText(request, "APPROVED");
				    }
				    else
				    {
				    	appStatus = HtmlUtil.getFinalDecisionThaiText(
								FormatUtil.display(index, "APPLICATION_STATUS"),
								FormatUtil.display(index, "THAI_NAME"),
								FormatUtil.display(index, "ROLE_DESC"),
								request);
				    }
				    HashMap<String, Object> CardInfo = CardInfoUtil.getCardInfo(cardTypeId);
					String searchCardLevel = SQLQueryEngine.display(CardInfo, "CARD_LEVEL");
				    String cardLevel=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_CARD_LEVEL"),FormatUtil.display(searchCardLevel));
				    String imageUrl = "images/template/EmptyCard.png";
				    if(!Util.empty(SystemConfig.getProperty("PRODUCT_IMAGE_URL").replace("{cardTypeId}",cardTypeId)))
				    {
				   		imageUrl = SystemConfig.getProperty("PRODUCT_IMAGE_URL").replace("{cardTypeId}",cardTypeId);
				    }
				    String firstInstallmentD = null;
				    String firstInstallmentM_Y = null;
				    if(isKPL)
				    {
				    	imageUrl = "images/kpl_logo/K-Personal-Loan.jpg";
				    	if(index.get("FIRST_INSTALLMENT_DATE") != null)
				    	{   
				    		firstInstallmentD = FormatUtil.display(FormatUtil.toDate(index.get("FIRST_INSTALLMENT_DATE").toString(), FormatUtil.EN, "yyyy-MM-dd HH:mm:ss.S"), FormatUtil.EN, "dd");
				    		firstInstallmentM_Y = FormatUtil.display(FormatUtil.toDate(index.get("FIRST_INSTALLMENT_DATE").toString(), FormatUtil.EN, "yyyy-MM-dd HH:mm:ss.S"), FormatUtil.EN, "MM/yyyy");
				    	}
				    	firstInstallmentD = (Util.empty(firstInstallmentD)) ? "-" : firstInstallmentD;
				    	firstInstallmentM_Y = (Util.empty(firstInstallmentM_Y)) ? "-" : firstInstallmentM_Y;
				    }
				    
				  NotifyApplicationSegment notiApplicationSegment = NotificationUtil.notifyApplication(FormatUtil.display(Row, "APPLICATION_GROUP_ID"));
				  
				  //check if current index is KPL for display addtional KPL fields
	%>
	<tr class="cardheader2">
		<td ><%=FormatUtil.display(Row, "JOB_STATE") %></td>
		<% if(isKPL){ %>
			<td class="text-left"><%=LabelUtil.getText(request, "KPL")%></td>
			<td colspan="2" class="text-left"></td>
		<% }else { %>
		<td class="text-left"><%=FormatUtil.display(CacheControl.getName(CACHE_NAME_CARDTYPE,cardTypeId))%></td>
		<td colspan="2" class="text-left"><%=(PERSONAL_TYPE_SUPPLEMENTARY.equals(FormatUtil.display(index, "PERSONAL_TYPE"))? 
						LabelUtil.getText(request, "SUB_CARD"): LabelUtil.getText(request, "MAIN_CARD") )+"/"+cardLevel %></td>
		<% } %>
		<td class="text-left"><%=HtmlUtil.getFieldLabel(request, "EQ_PROJECT_CODE_DESC")%></td>
		<td colspan="3" class="text-left"><%=FormatUtil.display(index, "PROJECT_CODE")%></td>
	</tr>
	<tr class="carddetail">
		<td class="text-left">
<%-- 		<% if (!DECISION_FINAL_DECISION.contains(FormatUtil.display(index, "APPLICATION_STATUS"))) { %> --%>
			<% if (!DECISION_FINAL_DECISION.contains(FormatUtil.display(Row, "JOB_STATE")) && !(ApplicationUtil.eApp(source) && SystemConfig.containsGeneralParam("APPLICATION_STATUS_SYSTEM", applicationStatus))) { %>
				<a class="btncancelaudit" row-data="{rowtype:item,uniqueid:<%=FormatUtil.display(Row, "APPLICATION_GROUP_ID")%>,itemid:<%=FormatUtil.display(index, "APPLICATION_RECORD_ID")%>}" 
				href="#">
					<img src="images/ulo/cancelaudit.png" />
				</a>
			<% } %>
		</td>
		<td class="text-left" style="max-width: 190px; overflow: visible; text-overflow: ellipsis; white-space: nowrap;">
			<img src="<%=imageUrl%>" style="max-width: 170px;" />
			<% if(!isKPL){ %>
			<br/><%=FormatUtil.display(index, "CARD_TYPE_DESC")%><br/>
			<%=FormatUtil.display(index, "APPLICATION_CARD_TYPE")%><br/>
			<%=HtmlUtil.getFieldLabel(request, "EQ_PROJECT_CODE_DESC")%>
			<%=FormatUtil.display(index, "PROJECT_CODE")%>
			<% } else { %>
				<br/><br/><br/>
				<%=HtmlUtil.getFieldLabel(request, "EQ_PROJECT_CODE_DESC")%>
				<%=FormatUtil.display(index, "PROJECT_CODE")%>
				&nbsp;&nbsp;
				<%=CacheControl.getName(SystemConstant.getConstant("FIELD_ID_KPL_PROJECT_CODE"),
			    FormatUtil.display(index, "PROJECT_CODE"), "DISPLAY_NAME")%>
			<% } %>
		</td>
		<td class="text-left"><%=HtmlUtil.getFieldLabel(request, "ID")%><br /> <%=HtmlUtil.getFieldLabel(request, "EQ_NAME")%><br />
		</td>
		<td class="text-left">
			<%=FormatUtil.display(index, "IDNO")%><br /> <%=FormatUtil.display(index, "TH_FIRST_NAME")%>
			<%=FormatUtil.display(index, "TH_LAST_NAME")%><br /> <%=FormatUtil.display(index, "EN_FIRST_NAME").equals("-") ? "" : FormatUtil.display(index, "EN_FIRST_NAME")%>
			<%=FormatUtil.display(index, "EN_LAST_NAME").equals("-") ? "" : FormatUtil.display(index, "EN_LAST_NAME")%>
		</td>
		<td class="text-left" colspan="2">
			<table class="subtable" style="width: 100%">
				<tr>
					<td class="text-left" style="width: 52%"><%=HtmlUtil.getFieldLabel(request, "APPROVE_DATE")%></td>
					<td class="text-left">
							<%=FormatUtil.sqlDateToThaiDate(FormatUtil.display(index, "APPROVE_DATE")) == null ? "-" 
							: FormatUtil.sqlDateToThaiDate(FormatUtil.display(index, "APPROVE_DATE"))%>
					</td>
				</tr>
				<tr>
					<td class="text-left"><%=HtmlUtil.getFieldLabel(request, "DECISION_RESULT")%></td>
					<td class="text-left">
							<%=appStatus%>
					</td>
				</tr>
				<% if(isKPL){ %>
				<tr>
					<td class="text-left"><%=HtmlUtil.getFieldLabel(request, "REQUEST_AMT")%></td>
					<td><%=FormatUtil.displayCurrency(FormatUtil.display(index, "REQUEST_LOAN_AMT"))%></td>
				</tr>
				<% } %>
				<tr>
					<td class="text-left"><%=HtmlUtil.getFieldLabel(request, (isKPL) ? "KPL_APPROVE_AMT" : "APPROVE_AMT")%></td>
					<td><%=FormatUtil.displayCurrency(FormatUtil.display(index, "LOAN_AMT"))%></td>
				</tr>
				<% if(isKPL){ %>
				<tr>
					<td class="text-left"><%=HtmlUtil.getFieldLabel(request, "LOAN_ACCOUNT_NO")%></td>
					<td><%=FormatUtil.display(index, "ACCOUNT_NO")%></td>
				</tr>
				<% } %>
				<tr>
					<td class="text-left"><%=HtmlUtil.getFieldLabel(request, "DUE_CYCLE")%></td>
					<td><%=(isKPL) ? firstInstallmentD : FormatUtil.display(index, "DUE_CYCLE")%></td>
				</tr>
				<% if(isKPL){ %>
					<tr>
						<td class="text-left"><%=HtmlUtil.getFieldLabel(request, "LOAN_LENGTH")%></td>
						<td><%=FormatUtil.display(index, "TERM")%></td>
					</tr>
					<tr>
						<td class="text-left"><%=HtmlUtil.getFieldLabel(request, "INT_RATE")%></td>
						<td><%=FormatUtil.display(index, "INTEREST_RATE")%></td>
					</tr>
					<tr>
						<td class="text-left"><%=HtmlUtil.getFieldLabel(request, "INSTALLMENT_AMT")%></td>
						<td><%=FormatUtil.displayCurrency(FormatUtil.display(index, "INSTALLMENT_AMT"))%></td>
					</tr>
					<tr>
						<td class="text-left"><%=HtmlUtil.getFieldLabel(request, "FIRST_INSTALLMENT_DATE")%></td>
						<td>
							<%=firstInstallmentM_Y%>
						</td>
					</tr>
					<tr>
						<td class="text-left"><%=HtmlUtil.getFieldLabel(request, "AUTO_PAY_ACCOUNT_NO")%></td>
						<td><%=FormatUtil.display(index, "AUTO_PAY_ACCOUNT_NO")%></td>
					</tr>
					<tr>
						<td class="text-left"><%=HtmlUtil.getFieldLabel(request, "LOAN_SETUP_STATUS")%></td>
						<td><%=FormatUtil.display(index, "LOAN_SETUP_STATUS")%></td>
					</tr>
				<% } else { %>
				<tr>
					<td class="text-left"><%=HtmlUtil.getFieldLabel(request, "CURRENT_CREDIT_LIMIT")%></td>
					<td><%=FormatUtil.displayCurrency(FormatUtil.display(index, "CURRENT_CREDIT_LIMIT"))%></td>
				</tr>
				<% } %>
				<tr>
					<td class="text-left"><%=HtmlUtil.getFieldLabel(request, "REASON")%></td>
					<%
// 					String reason = (String) index.get("REASONS");
	 			    String reason = "-";
	 			    if(JOB_STATE_CANCELLED.equals(jobState)){
	 			    	reason = (String) index.get("REASONS");
	 			    }
	 			    else{
		 			    Reason reasonNoti = notiApplicationSegment.findReasonApplication((String)index.get("APPLICATION_RECORD_ID"));
		 			    if(!Util.empty(reasonNoti) && !Util.empty(reasonNoti.getReasonCode())){
		 			    	String reasonNotiRejectCode = reasonNoti.getReasonCode();
		 			    	String reasonNotiCode = CacheControl.getName(SystemConstant.getConstant("CACHE_REJECT_LETTER_REASON"), reasonNotiRejectCode, "VALUE");
		 			    	reason = FormatUtil.displayText(CacheControl.getName(SystemConstant.getConstant("FILED_ID_DISPLAY_REJECT_REASON"), "CHOICE_NO", reasonNotiCode , "DISPLAY_NAME"));
		 			    }
	 			    }
	 			    
	 			    logger.debug("reason : " + reason); 
	 			    
	 			    %>
	 			    <td class="text-left"><%=reason%></td>
				</tr>
			</table>
		</td>
		<td class="text-left"><%=HtmlUtil.getFieldLabel(request, "EQ_OTHER")%></td>
		<td class="text-left">
			<!-- Cash transfer types --> <%
     			List<HashMap<String, Object>> cashTransferList = (List<HashMap<String, Object>>) index.get("CASH_TRANSFER");
 			    logger.debug("cashTransferList : " + cashTransferList);
 			    if (null != cashTransferList) {
	 				for (int i = 0, count = cashTransferList.size(); i < count; i++) {
	 				    HashMap<String, Object> row = cashTransferList.get(i);
	 				    String CASH_TRANSFER_TYPE = FormatUtil.display(row, "CASH_TRANSFER_TYPE");
	 				    String PERCENT_TRANSFER = FormatUtil.display(row, "PERCENT_TRANSFER");
	 				    String FIRST_TRANSFER_AMOUNT = FormatUtil.display(row, "FIRST_TRANSFER_AMOUNT");
	 				    String TRANSFER_ACCOUNT = FormatUtil.display(row, "TRANSFER_ACCOUNT");
	 				    out.write("<b>"+CASH_TRANSFER_TYPE + "</b><br>");
	 				    if(!Util.empty(PERCENT_TRANSFER)){
	 				      out.write(HtmlUtil.getText(request, "EQ_PERCEN_TRANSFER")+"&nbsp;"+PERCENT_TRANSFER + "%<br>");
	 				    }
	 				    if(!Util.empty(FIRST_TRANSFER_AMOUNT)){
	 				     out.write(HtmlUtil.getText(request, "EQ_TRANSFER_AMOUNT")+"&nbsp;"+FIRST_TRANSFER_AMOUNT + "<br>");
	 				    }
	 				    if(!Util.empty(TRANSFER_ACCOUNT)){
	 				     out.write(HtmlUtil.getText(request, "TRANSFER_ACCOUNT")+"&nbsp;"+TRANSFER_ACCOUNT + "<br>");
	 				    }
	 				}
 			    }

 			    List<HashMap<String, Object>> additionalServicesList = (List<HashMap<String, Object>>) index.get("ADDITIONAL_SERVICES");
 			    logger.debug("additionalServicesList : " + additionalServicesList);
 			    if (null != additionalServicesList) {
	 				for (int i = 0, count = additionalServicesList.size(); i < count; i++) {
	 				    HashMap<String, Object> row = additionalServicesList.get(i);
	 				    out.write(FormatUtil.display(row, "DISPLAY_NAME") + "<br>");
	 				}
 			    }
 %>
		</td>
	</tr>
	<%
	    }
			    }
			}
	    }else{
	%>
			<tr><td colspan="999" align="center"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td></tr>
	<%} %>
</table>
<section class="btnbarlong">
	<jsp:include page="/orig/ulo/util/valuelist.jsp" />
</section>
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