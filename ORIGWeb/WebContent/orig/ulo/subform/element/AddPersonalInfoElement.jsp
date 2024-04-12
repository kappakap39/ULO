<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<script type="text/javascript" src="orig/ulo/subform/element/js/AddPersoanlInfoElement.js"></script>
<%
	String subformId = request.getParameter("subformId");
	String FIELD_ID_PERSONAL_TYPE = SystemConstant.getConstant("FIELD_ID_PERSONAL_TYPE");	
	FormUtil formUtil = new FormUtil(subformId,request);
	FormEffects formEffect = new FormEffects(subformId,request);
%>
<div class="panel panel-default">
<div class="panel-body">
<div class="row form-horizontal">
	<div class="col-sm-12">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"PERSONAL_TYPE","PERSONAL_TYPE","col-sm-2 col-md-5 marge-label control-label")%>
			<div class="col-sm-10 col-md-9 marge-field">
				<div class="row">
					<div class="col-xs-12">
						<%=HtmlUtil.dropdown("PERSONAL_TYPE", "PERSONAL_TYPE", "PERSONAL_TYPE", "", 
							"","",FIELD_ID_PERSONAL_TYPE,"ALL_ALL_ALL", "", "col-sm-5 col-xs-10 col-xs-padding", formUtil)%>
						<div class="col-xs-2"><%=HtmlUtil.button("ADD_PERSONAL_APPLICANT_INFO","ADD_PERSONAL_APPLICANT_INFO","","btnsmall_add","",formEffect)%></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
</div>
