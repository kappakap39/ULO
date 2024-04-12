<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.sun.xml.internal.ws.org.objectweb.asm.Label"%>
<%@page import="com.eaf.orig.ulo.control.util.DocumentCheckListUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.core.ulo.common.display.DocumentCheckListDisplayHelper"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.RequiredDocDetailDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.RequiredDocDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.DocumentCommentDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler" />
<script type="text/javascript" src="orig/ulo/subform/js/DocumentCheckList.js"></script>
<%
	String roleId = ORIGForm.getRoleId();
	String formId = FormControl.getFormId(request);
	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
	String ACTION_TYPE = flowControl.getActionType();
	String formTemplateType = request.getParameter("formTemplateType");
	String BUTTON_ACTION = SystemConstant.getConstant("BUTTON_ACTION_SEND_TO_FU_"+flowControl.getRole().toUpperCase());
	if(Util.empty(BUTTON_ACTION)){
		BUTTON_ACTION = SystemConstant.getConstant("BUTTON_ACTION_SEND_TO_FU");
	}
	String subformHeader =LabelUtil.getText(request, "DOCUMENT_CHECK_LIST");
	String FORM_TEMPLATE_TYPE_HEADER = SystemConstant.getConstant("FORM_TEMPLATE_TYPE_HEADER");	
	if(Util.empty(formId)){
		formId = "NORMAL_APPLICATION_FORM";
	}
	String subFormId = request.getParameter("subFormId");

	if(Util.empty(subFormId)){
		subFormId ="DOCUMENT_CHECK_LIST";
	}
	Logger logger = Logger.getLogger(this.getClass());
	logger.debug("DocumentCheckList formId : "+formId);
	logger.debug("DocumentCheckList subFormId: "+subFormId);
	logger.debug("BUTTON_ACTION: "+BUTTON_ACTION);
	
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String FIELD_ID_DOC_SCENARIO_GROUP = SystemConstant.getConstant("FIELD_ID_DOC_SCENARIO_GROUP");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String VERIFY_CUSTOMER_FORM = SystemConstant.getConstant("VERIFY_CUSTOMER_FORM");
	String CACHE_NAME_USER = SystemConstant.getConstant("CACHE_NAME_USER");
	String CALL_OPERATOR_FOLLOW = SystemConstant.getConstant("CALL_OPERATOR_FOLLOW");
	String displayMode = HtmlUtil.EDIT;
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if (null == applicationGroup) {
		applicationGroup = new ApplicationGroupDataM();
	}
	PersonalInfoDataM personalInfoA = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
	if (null == personalInfoA) {
		personalInfoA = new PersonalInfoDataM();
	}
	VerificationResultDataM verificationA = personalInfoA.getVerificationResult();
	if (null == verificationA) {
		verificationA = new VerificationResultDataM();
	}
	ArrayList<RequiredDocDataM> requiredDocListsA = verificationA.getRequiredDocs();
	if (null == requiredDocListsA) {
		requiredDocListsA = new ArrayList<RequiredDocDataM>();
	}
	ArrayList<DocumentCommentDataM> docComments = applicationGroup.getDocumentComments();
	if (null == docComments) {
		docComments = new ArrayList<DocumentCommentDataM>();
	}

	logger.debug("DocumentComments size : " + docComments.size());

	//PERSONAL_TYPE_SUPPLEMENTARY
	ArrayList<PersonalInfoDataM> personals = applicationGroup.getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);
	// Load Document Check List
	DocumentCheckListDisplayHelper docDisplayA = new DocumentCheckListDisplayHelper(personalInfoA, applicationGroup);
	Map<String, Map<String, Map<String, String>>> docsA = docDisplayA.getDocCheckList();
	
	FormUtil formUtil = new FormUtil(formId,subFormId,request);
	String SEND_TO_FU_BTNActionJS = "onclick=SEND_TO_FU_BTNActionJS('"+BUTTON_ACTION+"') title='Send to Follow Up' ";
	String DOC_CODE_LIST_A = "";
	String DOC_CODE_LIST_S = "";
	FormEffects formEffect = new FormEffects(subFormId,request);
	if(FORM_TEMPLATE_TYPE_HEADER.equals(formTemplateType)){
		formEffect.setFormId("DOCUMENT_HEADER_FORM");
	}
	
	boolean isVerCusCondition = false;
	if(VERIFY_CUSTOMER_FORM.equals(formId)){
		isVerCusCondition = true;
		subformHeader = LabelUtil.getText(request, "DOCUMENT_CHECK_LIST_SUMMARY");
	}
	
// # Defect I094
// 	if(!CALL_OPERATOR_FOLLOW.equals(applicationGroup.getCallOperator()) && isVerCusCondition){
// 		requiredDocListsA = null;
// 		personals=null;
// 	}
	
	
	boolean isViewCondition = false;
	if(SystemConstant.getConstant("ROLE_FU").equals(roleId) || SystemConstant.lookup("ACTION_TYPE_ENQUIRY",ACTION_TYPE)){
		isViewCondition = true;
	}
%>

<header class="titlecontent"><%=subformHeader%>
	<div style="float: right;">
		<%=HtmlUtil.button("INTERNAL_INCOME_BTN","INTERNAL_INCOME_BTN","EXECUTE_INTERNAL_INCOME","","", formEffect)%>
	</div>
	<span class="badge-position">
		<%=HtmlUtil.button("DOCUMENT_CHECK_LIST_FLAG","DOCUMENT_CHECK_LIST_FLAG","NOTIFY_TEXT","badge","", formEffect)%>
	</span>
</header>
<%=HtmlUtil.icon("SEND_TO_FU", "SEND_TO_FU", "btnsmall_fu", SEND_TO_FU_BTNActionJS, formEffect)%>
<div class="row padding-top">
	<div class="col-md-12">
		<div class="panel panel-default">
			<div class="panel-heading"><%=LabelUtil.getText(request, "PERSONAL_MAIN")%></div>
			<table class="table table-documentchecklist">
				<%-- Head table --%>
				<%
					if (!Util.empty(requiredDocListsA) && !Util.empty(verificationA.getDocCompletedFlag())) {
				%>
				<thead>
					<tr>
						<th><%=LabelUtil.getText(request, "REQUIRED_DOC_LIST")%></th>
						<th class="text-center"><%=LabelUtil.getText(request, "RECEIVED")%></th>
						<%
							for (String groupCode : docDisplayA.getGroups()) {
						%>
						<th class="text-center" ><%=LabelUtil.getText(request,"SCEN")%><%=FormatUtil.display(groupCode)%></th>
						<%
							}
						%>						
						<th class="text-center" style="width: 35%"><%=LabelUtil.getText(request, "FOLLOWED_DOC_REASON")%></th>
					</tr>
				</thead>
				<tbody>
					<%-- /Head Table --%>
					<%
						logger.debug("docsA >> "+docsA);
							for (Entry<String, Map<String, Map<String, String>>> entry1 : docsA.entrySet()) {			
								boolean  isReceiveNoFollowUp =DocumentCheckListUtil.isReceiveDocumentNoFollowUp(entry1,isVerCusCondition);
								if(isReceiveNoFollowUp){
// 									logger.debug("case summary of verify customer and document complete  and not follow up ");
								}else{											
									out.print("<tr class=\"docgroup\"><td  class= 'docgroup-text-left'>");
									out.print(CacheControl.getName(FIELD_ID_DOC_SCENARIO_GROUP, entry1.getKey())); // Scenario Type
									out.print("</td><td colspan=\"" + (3 + docDisplayA.getGroups().size()) + "\"></td></tr>");
									
									for (Entry<String, Map<String, String>> entry2 : entry1.getValue().entrySet()) {
										Map<String, String> data = entry2.getValue();
									 	if(isVerCusCondition && data.containsKey("found") && "Y".equals(data.get("found")) && !data.containsKey("docCheckListDesc")){
// 									 			logger.debug("document complete  and not follow up");
									 	}else{
									 		String Doc_Code=entry2.getKey();
										if(!Util.empty(DOC_CODE_LIST_A)){
											DOC_CODE_LIST_A = DOC_CODE_LIST_A+",";
										}
										DOC_CODE_LIST_A += Doc_Code;
										out.print("<tr class=\"doctype\"><td>");
										out.print(CacheControl.getName(SystemConstant.getConstant("CACH_NAME_DOCUMENT_LIST"), Doc_Code) ); // Doc_Code naja
										out.print("</td>");
										out.print("<td class=\"text-center\">");
	
										boolean chkShow = false;
										if (data.containsKey("found") && "Y".equals(data.get("found"))) {
											out.print("<img src=\"images/ulo/compareFlag2.png\" />");
											chkShow = true;
										}else{
											out.print("<img src=\"images/ulo/unCheckFlag.png\" />");
										}
										out.print("</td>");
	
										for (String m : docDisplayA.getGroups()) {
											out.print("<td class=\"text-center\">");
											logger.debug("M : " + m);
											if (data.containsKey(m)) {
											logger.debug(m+" : " + data.get(m));
												out.print("Y".equals(data.get(m)) ? "M" : "O");
											}/* else{
												out.print("O");
											} */
											out.print("</td>");
										}
										
										String docCheckListDesc="";
										if( isViewCondition || isVerCusCondition){							
											if(data.containsKey("docCheckListDesc")){
												 docCheckListDesc =  data.get("docCheckListDesc");
											}else{
												docCheckListDesc = isVerCusCondition?LabelUtil.getText(request,"NOT_RECEIVE_DOCUMENT"):"";
											}
										}else{
											docCheckListDesc = "DOC_CODE="+Doc_Code+"&PERSONAL_TYPE="+PERSONAL_TYPE_APPLICANT+"&PERSONAL_ID="+personalInfoA.getPersonalId();
										}
										ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.FOLLOWED_DOC_REASON);
										for(ElementInf elementInf:elements){
											elementInf.setObjectRequest(applicationGroup);
											if(MConstant.FLAG.YES.equals(elementInf.renderElementFlag(request, ""))){
												out.print("<td class=\"text-center\" id=\"VALUE_CHECK_LIST_APPLICANT\" >");
												if(chkShow){
													elementInf.writeElement(pageContext, docCheckListDesc);
												}
												out.print("</td>");
											}
										}
									
									}
								  }
								}
							}
							out.print(HtmlUtil.hidden("DOC_CODE_LIST_"+PERSONAL_TYPE_APPLICANT, DOC_CODE_LIST_A));
						} else {
					%>
					<tr>
						<td class="text-center"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td>
					</tr>
					<%
						}
					%>
				</tbody>
			</table>
		</div>
	</div>
</div>
<%
if(!Util.empty(personals)){
	int countPersonal = 0;
	for(PersonalInfoDataM personalInfoS:personals){
		countPersonal++;
		if (null == personalInfoS) {
			personalInfoS = new PersonalInfoDataM();
		}
		VerificationResultDataM verificationS = personalInfoS.getVerificationResult();
		if (null == verificationS) {
			verificationS = new VerificationResultDataM();
		}
		ArrayList<RequiredDocDataM> requiredDocListsS = verificationS.getRequiredDocs();
		if (null == requiredDocListsS) {
			requiredDocListsS = new ArrayList<RequiredDocDataM>();
		}
		// Load Document Check List
		DocumentCheckListDisplayHelper docDisplayS = new DocumentCheckListDisplayHelper(personalInfoS, applicationGroup);
		Map<String, Map<String, Map<String, String>>> docsS = docDisplayS.getDocCheckList();
%>
<div class="row padding-top">
	<div class="col-md-12">
		<!-- 		<span style="color: red; font-weight: bold;">DEAR ALL "TESTER" : DO NOT ASSIGN ANOTHER DEFECT TO THIS SECTION AT THIS TIME. IT HAS NOT BEEN COMPLETED YET. THANK YOU.</span> -->
		<div class="panel panel-default">
			<div class="panel-heading"><%=LabelUtil.getText(request, "PERSONAL_SUB_AT")+countPersonal%></div>
			<table class="table table-documentchecklist">
				<%-- Head table --%>
				<%
					if (!Util.empty(requiredDocListsS) && !Util.empty(verificationS.getDocCompletedFlag())) {
				%>
				<thead>
					<tr>
						<th width=""><%=LabelUtil.getText(request, "REQUIRED_DOC_LIST")%></th>
						<th class="text-center"><%=LabelUtil.getText(request, "RECEIVED")%></th>
						<%
							for (String groupCode : docDisplayS.getGroups()) {
						%>
						<th class="text-center"><%=LabelUtil.getText(request,"SCEN")%><%=FormatUtil.display(groupCode)%></th>
						<%
							}
						%>
						<th class="text-center"  style="width: 35%"><%=LabelUtil.getText(request, "FOLLOWED_DOC_REASON")%></th>
					</tr>
				</thead>
				<tbody>
					<%-- /Head Table --%>
					<%
						logger.debug(docsS);
							for (Entry<String, Map<String, Map<String, String>>> entry1 : docsS.entrySet()) {
								boolean  isReceiveNoFollowUp =DocumentCheckListUtil.isReceiveDocumentNoFollowUp(entry1,isVerCusCondition);
								if(isReceiveNoFollowUp){
									logger.debug("case summary of verify customer and document complete  and not follow up ");
								}else{
								out.print("<tr class=\"docgroup\"><td class= 'docgroup-text-left'>");
								out.print(CacheControl.getName(FIELD_ID_DOC_SCENARIO_GROUP, entry1.getKey())); // Scenario Type
								out.print("</td><td colspan='"+(3 + docDisplayS.getGroups().size())+"'/td></tr>");

								for (Entry<String, Map<String, String>> entry2 : entry1.getValue().entrySet()) {
									Map<String, String> data = entry2.getValue();
									if(isVerCusCondition && data.containsKey("found") && "Y".equals(data.get("found")) && !data.containsKey("docCheckListDesc")){
								 		logger.debug("document complete  and not follow up");
								 	}else{								 	
								 	  	String Doc_Code = entry2.getKey();
										if(!Util.empty(DOC_CODE_LIST_S)){
											DOC_CODE_LIST_S = DOC_CODE_LIST_S+",";
										}
										DOC_CODE_LIST_S += Doc_Code;
										out.print("<tr class=\"doctype\"><td>");
										out.print(CacheControl.getName(SystemConstant.getConstant("CACH_NAME_DOCUMENT_LIST"), Doc_Code) ); // Doc Code naja
										out.print("</td>");
										out.print("<td class=\"text-center\">");
	
										boolean chkShow = false;
										if (data.containsKey("found") && "Y".equals(data.get("found"))) {
											out.print("<img src=\"images/ulo/compareFlag2.png\" />");
											chkShow = true;
										}else{
											out.print("<img src=\"images/ulo/unCheckFlag.png\" />");
										}
										out.print("</td>");
	
										for (String m : docDisplayS.getGroups()) {
											out.print("<td class=\"text-center\">");
											logger.debug("M : " + m);
											if (data.containsKey(m)) {
											logger.debug(m+" : " + data.get(m));
												out.print("Y".equals(data.get(m)) ? "M" : "O");
											}/* else{
												out.print("O");
											} */
											out.print("</td>");
										}
										
										String docCheckListDesc="";
										if(isViewCondition|| isVerCusCondition){
											if(data.containsKey("docCheckListDesc")){
												docCheckListDesc = data.get("docCheckListDesc");
											}else{
												docCheckListDesc = isVerCusCondition?LabelUtil.getText(request,"NOT_RECEIVE_DOCUMENT"):"";
											}
										}else{
											docCheckListDesc = "DOC_CODE="+Doc_Code+"&PERSONAL_TYPE="+PERSONAL_TYPE_SUPPLEMENTARY+"&PERSONAL_ID="+personalInfoS.getPersonalId();
										}
										ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.FOLLOWED_DOC_REASON);
										for(ElementInf elementInf:elements){
											elementInf.setObjectRequest(applicationGroup);
											if(MConstant.FLAG.YES.equals(elementInf.renderElementFlag(request, ""))){
												out.print("<td class=\"text-center\" id=\"VALUE_CHECK_LIST_SUPPLEMENTARY\">");
												if(chkShow){
													elementInf.writeElement(pageContext, docCheckListDesc);
												}
												out.print("</td>");
											}
										}
									}
								}
						     }
						  }
						  out.print(HtmlUtil.hidden("DOC_CODE_LIST_"+PERSONAL_TYPE_SUPPLEMENTARY, DOC_CODE_LIST_S));
						} else {
					%>
					<tr>
						<td class="text-center"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td>
					</tr>
					<%
						}
					%>
				</tbody>
			</table>
		</div>
	</div>
</div>	
<%}
}else{%>
<div class="row padding-top">
	<div class="col-md-12">
		<!-- 		<span style="color: red; font-weight: bold;">DEAR ALL "TESTER" : DO NOT ASSIGN ANOTHER DEFECT TO THIS SECTION AT THIS TIME. IT HAS NOT BEEN COMPLETED YET. THANK YOU.</span> -->
		<div class="panel panel-default">
			<div class="panel-heading"><%=HtmlUtil.getText(request,"SUPPLEMENTARY")%></div>
			<table class="table table-documentchecklist">
				<tr>
					<td class="text-center"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td>
				</tr>
			</table>
		</div>
	</div>
</div>
<%}%>