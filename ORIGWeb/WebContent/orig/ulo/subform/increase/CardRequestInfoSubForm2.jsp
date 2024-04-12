<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/js/CardRequestInfoSubForm.js"></script>
<%
	String subformId = "INCREASE_CARD_REQUEST_INFO_SUBFROM_2";
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	int PERSONAL_SEQ = personalInfo.getSeq();		
// 	String TAG_SMART_DATA_PERSONAL = FormatUtil.getSmartDataEntryId(PERSONAL_TYPE,PERSONAL_SEQ);
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
	FormUtil formUtil = new FormUtil(subformId,request);
	
	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
	String ACTION_TYPE = flowControl.getActionType();
%>	
<% 
	ArrayList<String> products = new ArrayList<String>();
	if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",ACTION_TYPE)){
		products=applicationGroup.getDisplayEnquiryProducts(SystemConstant.getArrayConstant("DISPLAY_PRODUCT"));
	}else{
		products = applicationGroup.getProducts(SystemConstant.getArrayConstant("DISPLAY_PRODUCT"));
	}
%>

<%
if(!Util.empty(products)){
	for(String product : products){
%>
		<section id="SECTION_PRODUCT_<%=product%>" class="warpSubFormTemplate SECTION_PRODUCT_<%=product%>">
<!-- 			<div class="panel panel-default container-fluid"> -->
			<%
				ArrayList<ElementInf> elementInfs = ImplementControl.getElements("LIST_CARD_REQUEST_INCREASE_"+product);
				for(ElementInf elementInf : elementInfs){
					elementInf.writeElement(pageContext, applicationGroup);
				}
			%>				
<!-- 			</div> -->
		</section>
	<%}%>	
<%}%>

<%
if(!Util.empty(products)){
	for(String product : products){
%>
		<section id="SECTION_PRODUCT_<%=product%>" class="warpSubFormTemplate SECTION_PRODUCT_<%=product%>">
<!-- 			<div class="panel panel-default container-fluid"> -->
			<%
				ArrayList<ElementInf> elementInfs = ImplementControl.getElements("CARD_REQUEST_INCREASE_"+product);
				for(ElementInf elementInf : elementInfs){
					%>
					<table>
					<tr><td><header class="titlecontent"><%=LabelUtil.getText(request,product)%></header></td></tr>
					</table>
					<%
					elementInf.writeElement(pageContext, applicationGroup);
				}
			%>				
<!-- 			</div> -->
		</section>
	<%}%>	
<%}else{%>
 	<table class="table table-striped">
	 	<tr>
	 		<td align="center" colspan="999"><%=LabelUtil.getText(request,"NO_RECORD_FOUND")%></td>
	 	</tr>
 	</table>
<%}%>	
	
	