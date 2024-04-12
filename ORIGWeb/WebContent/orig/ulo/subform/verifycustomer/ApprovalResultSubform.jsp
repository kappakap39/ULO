<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.orig.ulo.model.control.FlowControlDataM"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<%
	Logger logger = Logger.getLogger(this.getClass());
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
	String[] DISPLAY_PRODUCT  = SystemConstant.getArrayConstant("DISPLAY_PRODUCT");
	ArrayList<String> products = applicationGroup.getMaxLifeCycleProducts(DISPLAY_PRODUCT);
	
%>
<% 
	for(String product : products){ 
%>
<section id="SECTION_APPROVAL_<%=product%>" class="warpSubFormTemplate SECTION_PRODUCT_<%=product%>">
	<header class="titlecontent"><%=LabelUtil.getText(request,product)%></header>
		<section class ="work_area">
			<div class=" panel-default panel">
			 <table class="table table-striped table-hover">
			 	<thead>
						<tr>
							<th width="25%"><%=HtmlUtil.getText(request,"PRODUCT_CURRENT")%></th>
							<th width="40%"><%=HtmlUtil.getText(request,"DECISION_RESULT")%></th>
							<th width="15%"></th>
							<th width="20%"><%=HtmlUtil.getText(request,"DECISION_CUSTOMER")%></th>
						</tr>
				</thead>
				<tbody>
					<%	
						ElementInf elementInf = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.APPROVAL_RESULT, product);
						elementInf.writeElement(pageContext, applicationGroup);
					%>
				</tbody>
			 </table>
		</div>
	</section>
</section>
<%}%>