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
<script type="text/javascript" src="orig/ulo/search/js/searchLetter.js?v=1"></script>
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
	String FIELD_ID_LETTER_TYPE = SystemConstant.getConstant("FIELD_ID_LETTER_TYPE");
	String FIELD_ID_RESEND_TYPE = SystemConstant.getConstant("FIELD_ID_RESEND_TYPE");
	String KPL_NCB_REJECT_LETTER_TEMPLATE = SystemConstant.getConstant("KPL_NCB_REJECT_LETTER_TEMPLATE");
	String FIELD_ID_KPL_LETTER_TEMPLATE_MAPPING = SystemConstant.getConstant("FIELD_ID_KPL_LETTER_TEMPLATE_MAPPING");
	String RESEND_TYPE_PAPER_STR = SystemConstant.getConstant("RESEND_TYPE_PAPER_STR");
	String RESEND_TYPE_EMAIL_STR = SystemConstant.getConstant("RESEND_TYPE_EMAIL_STR");
	String RESEND_TYPE_PAPER = SystemConstant.getConstant("RESEND_TYPE_PAPER");
	String RESEND_TYPE_EMAIL = SystemConstant.getConstant("RESEND_TYPE_EMAIL");
	String LETTER_TYPE_REJECT = SystemConstant.getConstant("LETTER_TYPE_REJECT");
	String TOTAL_RECORD = LabelUtil.getText(request, "TOTAL_RECORD");
	ArrayList<String> WIP_JOBSTATE_END = SystemConfig.getArrayListGeneralParam("WIP_JOBSTATE_END");
	String formatDateL = SystemConstant.getConstant("PA_DATE_FORMAT_L");
	String formatDateS = SystemConstant.getConstant("PA_DATE_FORMAT_S");
	logger.debug("searchLetter Page - Role : " + roleId);
	
%>
<%= HtmlUtil.hidden("searchLetterPage", "SearchLetter") %>
<section class="work_area padding-top">
	<div class="row">
		<div class="col-xs-12">
			<div class="panel panel-default">
				<div class="panel-body" style="padding: 15px;">
					<div class="row form-horizontal">
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "LETTER_NUMBER", "col-sm-4 col-md-5 control-label")%>
								<div id="InputField_APPLICATION_GROUP_NO" class="InputField InputField_APPLICATION_GROUP_NO col-sm-8 col-md-7" type="textbox-text">
								<input type="text" name="LETTER_NUMBER" maxlength="30" value="<%=FormatUtil.display(SearchForm.getString("LETTER_NUMBER"))%>" class="form-control textbox" property="textbox-text">
								</div>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
							<%=HtmlUtil.getFieldLabel(request, "APPLICATION_NO", "col-sm-4 col-md-5 control-label")%>
							<%=HtmlUtil.textBox("APPLICATION_GROUP_NO", "", SearchForm.getString("APPLICATION_GROUP_NO"), "", "20", "", "",
							"col-sm-8 col-md-7", request)%>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "EQ_PRODUCT", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.dropdown("PRODUCT", "", "KPL_PRODUCT_LISTBOX_FILTER", SearchForm.getString("PRODUCT"), "", FIELD_ID_PRODUCT_TYPE, "", "", "",
								"col-sm-8 col-md-7", request)%>
							</div>
						</div>
					</div>
					<div class="row form-horizontal">
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "ID_NO", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.textBox("IDNO", "", SearchForm.getString("IDNO"), "", "20", "", "", "col-sm-8 col-md-7", request)%>
							</div>
						</div>
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
					</div>
					<div class="row form-horizontal">
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "DE2_SUBMIT_DATE_FROM", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.calendar("DE2_SUBMIT_DATE_FROM", "", "DE2_SUBMIT_DATE_FROM",
								FormatUtil.toDate(SearchForm.getString("DE2_SUBMIT_DATE_FROM"),FormatUtil.TH,FormatUtil.Format.ddMMyyyy), "", HtmlUtil.EDIT, "", FormatUtil.TH,
								"col-sm-8 col-md-7", request)%>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "DE2_SUBMIT_DATE_TO", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.calendar("DE2_SUBMIT_DATE_TO", "", "DE2_SUBMIT_DATE_TO",FormatUtil.toDate(SearchForm.getString("DE2_SUBMIT_DATE_TO"),FormatUtil.TH,FormatUtil.Format.ddMMyyyy),
								"", HtmlUtil.EDIT, "", FormatUtil.TH, "col-sm-8 col-md-7", request)%>
							</div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
								<%=HtmlUtil.getFieldLabel(request, "LETTER_TYPE", "col-sm-4 col-md-5 control-label")%>
								<%=HtmlUtil.dropdown("LETTER_TYPE", "", "", SearchForm.getString("LETTER_TYPE"), "", FIELD_ID_LETTER_TYPE, "", "", "",
								"col-sm-8 col-md-7", request)%>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12 text-center">
							<%=HtmlUtil.button("LETTER_SEARCH_BTN", "EQ_SEARCH_BTN", HtmlUtil.EDIT, "btn2 btn2-green", "", request)%>
							<%=HtmlUtil.button("LETTER_RESET_BTN", "EQ_RESET_BTN", HtmlUtil.EDIT, "btn2", "", request)%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<div class="row form-horizontal">
	<div class="col-sm-6">
		<%=HtmlUtil.button("RESEND_BTN", "RESEND", HtmlUtil.EDIT, "btn2 btn2-green", "", request)%>
	</div>
</div>
<div class="row padding-top">
	<div class="col-md-12">
		<table class="table table-bordered table-striped enqtable">
			<thead>
				<tr bgcolor="#00A950">
					<th style="width: 40px;"><input name='CHECK_ALL' type='checkbox' class='form-control enqall' /></th>
					<th style="min-width: 10px;"><font color="white">Letter Number</font></th>
					<th style="min-width: 100px;"><font color="white">หมายเลขใบสมัคร</font></th>
					<th style="min-width: 120px;"><font color="white">เลขที่เอกสารสำคัญ</font></th>
					<th style="min-width: 90px;"><font color="white">ชื่อ</font></th>
					<th style="min-width: 60px;"><font color="white">ชื่อกลาง</font></th>
					<th style="min-width: 110px;"><font color="white">นามสกุล</font></th>
					<th style="min-width: 80px;"><font color="white">DE2 Submit Date</font></th>
					<th style="min-width: 50px;"><font color="white">ประเภทจดหมาย</font></th>
					<th style="min-width: 50px;"><font color="white">ผลิตภัณฑ์</font></th>
					<th style="min-width: 150px;"><font color="white">Template</font></th>
					<th style="min-width: 80px;"><font color="white">Re-Send Type</font></th>
					<th style="min-width: 200px;"><font color="white">Re-Send Email</font></th>
					<th style="min-width: 120px;"><font color="white">Re-Send Date</font></th>
				</tr>
			</thead>
			<tbody id="resultsBody">
				<%
					if (!Util.empty(results)) 
					{
						
						SearchFormHandler searchForm = (SearchFormHandler)request.getSession().getAttribute("SearchForm");
						int itemsPerPage = searchForm.getItemsPerPage();
						int atPage = searchForm.getAtPage();
						
						for (HashMap<String, Object> Row : results) 
						{
							String letterNumber = (Row.get("LETTER_NUMBER") != null) ? Row.get("LETTER_NUMBER").toString() : "";
							int index = letterNumber.indexOf(MConstant.FLAG_N);
							String letterNumberST = (letterNumber.substring(index)).replace("/", "_");
							System.out.println("letterNumberST : " + letterNumberST);
							String resendTypeValue = "";
							String resendTypeDisplayMode = HtmlUtil.EDIT;
							String resendEmailDisplayMode = HtmlUtil.EDIT;
							String cusEmail = Util.empty(Row.get("EMAIL_PRIMARY")) ?  "" : Row.get("EMAIL_PRIMARY").toString();
							String resendEmail =  Util.empty(Row.get("CUSTOMER_RESEND_EMAIL_ADDRESS")) ? "" : Row.get("CUSTOMER_RESEND_EMAIL_ADDRESS").toString();
							if(KPL_NCB_REJECT_LETTER_TEMPLATE.equals(FormatUtil.display(Row, "LETTER_TEMPLATE")))
							{
								resendTypeValue = RESEND_TYPE_PAPER_STR;
								resendTypeDisplayMode = HtmlUtil.VIEW;
								resendEmailDisplayMode = HtmlUtil.VIEW;
							}
							else
							{
								String resendMethod = FormatUtil.display(Row, "CUSTOMER_RESEND_SEND_METHOD");
								if(Util.empty(resendMethod)){}
								else if (resendMethod.equals(RESEND_TYPE_PAPER))
								{
									resendTypeValue = RESEND_TYPE_PAPER_STR;
									resendEmailDisplayMode = HtmlUtil.VIEW;
								}
								else if (resendMethod.equals(RESEND_TYPE_EMAIL))
								{
									resendTypeValue = RESEND_TYPE_EMAIL_STR;
								}
							}
							
							if(Util.empty(resendEmail))
							{
								resendEmail = cusEmail;
							}
				%>
							<tr>
								<td align="center">
									<%=HtmlUtil.checkBox("CHECK_BOX", letterNumber, "", "01" , "", 
									"", "", request)%>
								</td>
								<td align="center">
									<%=letterNumber%>
								</td>
								<td align="center">
									<%=Row.get("APPLICATION_GROUP_NO")%>
								</td>
								<td align="center">
									<%=Row.get("IDNO")%>
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
								<td align="center">
									<%=PAUtil.formatDate(Row.get("DE2_SUBMIT_DATE"),formatDateS)%>
								</td>
								<td align="center">
									<%=CacheControl.getName(FIELD_ID_LETTER_TYPE, FormatUtil.display(Row, "LETTER_TYPE"))%>
								</td>
								<td align="center">
									<%=FormatUtil.display(Row, "PRODUCT")%>
								</td>
								<td align="center">
									<%=(LETTER_TYPE_REJECT.equals(FormatUtil.display(Row, "LETTER_TYPE"))) ? CacheControl.getName(FIELD_ID_KPL_LETTER_TEMPLATE_MAPPING,
			    					FormatUtil.display(Row, "LETTER_TEMPLATE"), "DISPLAY_NAME") : ""%>
								</td>
								<td align="center">
									<%=HtmlUtil.dropdown("RESEND_TYPE", letterNumberST, "", resendTypeValue, "", FIELD_ID_RESEND_TYPE, "", resendTypeDisplayMode , " onChange=\"showHideEmail(this);\" ",
									"col-sm-12", request)%>
								</td>
								<td align="center">
									<%=HtmlUtil.textBox("RESEND_EMAIL", letterNumber, RESEND_TYPE_EMAIL_STR.equals(resendTypeValue) ? resendEmail : "", "", "200", resendEmailDisplayMode, "",
									"col-sm-12", request)%>
								</td>
								<%=HtmlUtil.hidden("RESEND_EMAIL_TEMP_" + letterNumberST, resendEmail)%>
								<td align="center">
									<%=FormatUtil.display(Row, "CUSTOMER_RESEND_DATE")%>
								</td>
							</tr>
					
				<%
						}
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
<section class="btnbarlong">
	<jsp:include page="/orig/ulo/util/valuelist.jsp" />
</section>
<%=HtmlUtil.hidden("currentUser", ORIGUser.getUserName())%>

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
<script>
$("input[name='CHECK_ALL']").on('change', function(e) {
	  $('input:checkbox').not(this).prop('checked', this.checked);
});
</script>
