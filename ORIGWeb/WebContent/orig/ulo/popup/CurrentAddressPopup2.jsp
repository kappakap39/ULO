<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler" />

<script type="text/javascript" src="orig/ulo/popup/js/AddressPopup.js"></script>
<%
	String PERSONAL_TYPE = ModuleForm.getRequestDataString("PERSONAL_TYPE");
	String CURRENT_ADDRESS = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
%>
<!-- New layout is not finish yet. -->
<div class="panel panel-default">
	<% 
		ArrayList<ElementInf> elementInfs = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.ADDRESS_MAILING);
		for(ElementInf elementInf : elementInfs){
			elementInf.writeElement(pageContext, PERSONAL_TYPE);
		}
	%>
</div>

<%=HtmlUtil.hidden("THIS_PAGE", CURRENT_ADDRESS)%>
<%=HtmlUtil.hidden("COPY_ADDRESS", "")%>
