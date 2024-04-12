<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/js/CardRequestInfoSubForm.js"></script>

<%
	String subformId = "INCREASE_CARD_REQUEST_INFO_SUBFROM_1";
	String SP_SIGN_OFF = "SP_SIGN_OFF";
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
	FormEffects formEffects = new FormEffects(subformId,request);
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
%>
<%if(!applicationGroup.isVeto()){ %>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-12">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,subformId,"CARD_NO","CARD_NO","col-sm-2 col-md-5 marge-label control-label")%>
					<div class="col-sm-10 col-md-9 marge-field">
						<div class="row">
							<div class="col-xs-12">
								<%=HtmlUtil.textBoxCardNo("CARD_NO", "", "CARD_NO_"+personalElementId, "CARD_NO", 
									null, "", "", "col-sm-8 col-md-7",formUtil) %>
								<div class="col-xs-2">
<%-- 									<%=HtmlUtil.button("BTN_ADD_CARD","","","btnsmall_add","",request) %> --%>
									<%=HtmlUtil.button("BTN_ADD_CARD", "", "BTN_ADD_CARD", "", "btnsmall_add", "", formEffects) %>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<%} %>
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
					<tr><td><header class="subtitlecontent"><%=LabelUtil.getText(request,SP_SIGN_OFF)%></header></td></tr>
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