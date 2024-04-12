<%@page import="com.eaf.core.ulo.common.util.FormDisplayModeUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript" src="orig\ulo\subform\projectcode\product\js\VerifyPrivilegeProductProjectCodeSubForm.js"></script>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<%
FormUtil formUtil = new FormUtil("VERIFY_PRIVILEGE_PRODUCT_PROJECT_CODE_SUBFORM",request);
String displayMode = FormDisplayModeUtil.getDisplayMode("", "", formUtil);
String[] productOfProjectSubform = SystemConstant.getConstant("PRODUCT_OF_PROJECT").split(",");
 %> 
<div class="panel panel-default">
	<div class="panel-body">
	<div class="row form-horizontal">
			<div class="col-sm-12">
				<div class="form-group">
					<div class="col-sm-7 col-md8 bold"><label for="07_DOCUMENT_TYPE"><%=LabelUtil.getText(request, "DOCUMENT_FOR_CUSTOMER_PRODUCT")%></label></div>
				</div>
			</div>
		</div>
		<div class="row form-horizontal element-body collapse">
<%	for(int i=0;i<productOfProjectSubform.length;i++){ 
		ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.PRODUCT_OF_PROJECT,productOfProjectSubform[i]);%>
		<div class="col-sm-12">
			<div class="form-group row">
<!-- 				<div class="col-md-1 privilege-nopadding-right"></div> -->
					<div class="col-md-12">		
						<% element.writeElement(pageContext,displayMode);%>
					</div>		
				</div>
		</div>
		
<%	} %>	
		</div>
	</div>
</div>