<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%
	ElementInf elementInf = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.VERIFICATION,"CUSTOMER_INFO_APPLICANT");
 		elementInf.writeElement(pageContext,null);
%>