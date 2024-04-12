<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
 <script type="text/javascript" src="/orig/ulo/subform/js/VerificationSubForm.js"></script>
 
<%
	String displayMode = HtmlUtil.EDIT;
	String[] elementSubform = SystemConstant.getConstant("VERIFICATION_2").split(",");
 %>
<section class="table">
	<table width="100%">
<%	for(int i=0;i<elementSubform.length;i++){
			ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFICATION,elementSubform[i]);
			element.writeElement(pageContext,null);%>
<%} %>
	</table>	
</section>