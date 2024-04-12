<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.util.SessionControl"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<script type="text/javascript" src="orig/ulo/subform/sensitive/js/NormalRekeySensitiveField.js"></script>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>
<%
	Logger logger = Logger.getLogger(this.getClass());
	FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);	
	String roleId = ModuleForm.getRoleId();
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)ModuleForm.getObjectForm();
	ArrayList<ElementInf>  sensitiveFields = ImplementControl.getElements("COMPARE_SENSITIVE",SystemConstant.getConstant("COMPARE_SENSITIVE_"+roleId+"_NORMAL").split(","));
		
%>

<section class="tabletheme">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr class="tabletheme_header">
			<td width="4%"><h1><%=LabelUtil.getText(request,"ROW_NO") %></h1></td>
			<td width="17%"><h1><%=LabelUtil.getText(request,"UN_MATCHED_DATA") %></h1></td>
			<td width="17%"><h1><%=LabelUtil.getText(request,"CONFIRM_DATA") %></h1><span class="btnselect"></span></td>
		</tr>
<%if(!Util.empty(sensitiveFields)){
	int count=0;
	for(ElementInf element :sensitiveFields){
		String elementId = element.getImplementId();
	%> 
		<tr <%if(count%2!=0){ %>class="row"<%} %> id="<%="ELEMENT_TYPE_"+elementId%>">
			<td><%=FormatUtil.display(String.valueOf(++count))%></td>
			<td><%=FormatUtil.display(element.getImplementDesc()) %></td>
			<td><%=element.getElement(request,applicationGroup)%></td>
		</tr>
<%	}
} %>
	</table>
</section>