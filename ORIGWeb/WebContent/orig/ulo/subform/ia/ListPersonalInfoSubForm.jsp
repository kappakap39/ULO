<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.ListBoxControl"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/js/ListSupPersonalInfoSubForm.js"></script>
<%
	String subformId = "IA_LIST_PERSONAL_INFO_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());	
	String displayMode = HtmlUtil.EDIT;
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	ArrayList<PersonalInfoDataM> personals = applicationGroup.getPersonalInfos();	
	String FIELD_ID_PERSONAL_TYPE = SystemConstant.getConstant("FIELD_ID_PERSONAL_TYPE");		
	String PERSONAL_TYPE_INFO = SystemConstant.getConstant("PERSONAL_TYPE_INFO");		
	FormUtil formUtil = new FormUtil(subformId,request);
	FormEffects formEffect = new FormEffects(subformId,request);
	boolean isKPL = KPLUtil.isKPL(applicationGroup);
%>
<%
ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.ADD_PERSONAL_INFO);
for(ElementInf elementInf:elements){
	elementInf.setObjectRequest(applicationGroup);
// 	if(MConstant.FLAG.YES.equals(elementInf.renderElementFlag(request, applicationGroup))){
		elementInf.writeElement(pageContext,subformId);
// 	}
}
 %>
<!-- <div class="panel panel-default"> -->
<!-- <div class="panel-body"> -->
<!-- <div class="row form-horizontal"> -->
<!-- 	<div class="col-sm-12"> -->
<!-- 		<div class="form-group"> -->
<%-- 			<%=HtmlUtil.getSubFormLabel(request,subformId,"PERSONAL_TYPE","PERSONAL_TYPE","col-sm-2 col-md-5 marge-label control-label")%> --%>
<!-- 			<div class="col-sm-10 col-md-9 marge-field"> -->
<!-- 				<div class="row"> -->
<!-- 					<div class="col-xs-12"> -->
<%-- 						<%=HtmlUtil.dropdown("PERSONAL_TYPE", "PERSONAL_TYPE", "PERSONAL_TYPE", "",  --%>
<%-- 							"","",FIELD_ID_PERSONAL_TYPE,"ALL_ALL_ALL", "", "col-sm-5 col-xs-10 col-xs-padding", formUtil)%> --%>
<%-- 						<div class="col-xs-2"><%=HtmlUtil.button("ADD_PERSONAL_APPLICANT_INFO","ADD_PERSONAL_APPLICANT_INFO","","btnsmall_add","",formEffect)%></div> --%>
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- </div> -->
<!-- </div> -->
<!-- </div> -->
<div class="panel panel-default">
<div class="panel-heading"><%=LabelUtil.getText(request,"LIST_PERSONAL_INFO")%></div>
<table class="table table-striped table-hover">
	<%
		if(!Util.empty(personals)){
		int ROW_NUM = 1;
		for(PersonalInfoDataM personalInfo:personals){
			if(!PERSONAL_TYPE_INFO.equals(personalInfo.getPersonalType())){
			String PERSONAL_ID = personalInfo.getPersonalId();
 			String PERSONAL_SEQ = FormatUtil.getString(personalInfo.getSeq());
 			String PERSONAL_TYPE = personalInfo.getPersonalType();
 			String loadActionJS = "onclick=LOAD_PERSONAL_APPLICANT_INFOActionJS('"+PERSONAL_ID+"','"+PERSONAL_SEQ+"','"+PERSONAL_TYPE+"')";
 			String deleteActionJS = "onclick=DELETE_PERSONAL_APPLICANT_INFOActionJS('"+PERSONAL_ID+"')";
	%>
	 	<tr>
	 		<td width="5%"><%=HtmlUtil.icon("DELETE_SUP_PERSONAL_INFO","DELETE_SUP_PERSONAL_INFO","btnsmall_delete",deleteActionJS,formEffect) %></td>
	 		<td width="5%"><%= ROW_NUM %></td>
	 		<td><%=ListBoxControl.getName(FIELD_ID_PERSONAL_TYPE,personalInfo.getPersonalType())%></td>
	 		<td><%=HtmlUtil.linkText("LOAD_SUP_PERSONAL_INFO",personalInfo.getThName(),HtmlUtil.EDIT,loadActionJS,request)%></td>
	 		<td><%=personalInfo.getIdno() %></td>
	 	</tr>
	 <%ROW_NUM++;}
	 }%>
	 <%}else{%>
	 	<tr>
	 		<td align="center" colspan="999"><%=LabelUtil.getText(request,"NO_RECORD_FOUND")%></td>
	 	</tr>
	 <%}%>
</table>
</div>
<% if(isKPL) {%>
<script>
if(typeof $('[name="PERSONAL_TYPE"]')[0] !== "undefined")
{
	$('[name="PERSONAL_TYPE"]')[0].selectize.removeOption('S');
}
</script>
<% } %>