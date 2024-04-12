<%@page import="com.eaf.orig.ulo.dm.util.DocumentManageUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.dm.DocumentManagementDataM"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.DMFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="DMForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.DMFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/js/DMWareHouseInfoSubForm.js"></script>
<%
	String  FORM_ID =DMFormHandler.getForm(request).getFormId();
	DocumentManagementDataM dmManageDataM = DMFormHandler.getObjectForm(request);
	if(Util.empty(dmManageDataM)){
		dmManageDataM = new DocumentManagementDataM();
	}
	
	String displayMode = DocumentManageUtil.dmStoreMode(dmManageDataM.getParam1());
%>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "DM_STATUS", "col-sm-4 col-md-5 control-label")%>
					<div class="col-sm-8 col-md-7"><%=FormatUtil.display(CacheControl.getName("2", dmManageDataM.getStatus()))%></div>
				</div>
			</div>
			<div class="clearfix"></div>
			<% if(MConstant.DM_FORM_NAME.DM_BORROW_FORM.equals(FORM_ID)){ %> 
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "DM_FOLDER_NO", "col-sm-4 col-md-5 control-label")%>
					 <div class="col-sm-8 col-md-7"><%=FormatUtil.display(dmManageDataM.getContainerNo())%></div>				
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "DM_BOX_NO", "col-sm-4 col-md-5 control-label")%>
					 <div class="col-sm-8 col-md-7"><%=FormatUtil.display(dmManageDataM.getBoxNo())%></div>				
				</div>
			</div>
			<%}else{ %>
			<div class="col-sm-6">
				<div class="form-group">					
					<%=HtmlUtil.getFieldLabel(request, "DM_FOLDER_NO", "col-sm-4 col-md-5 control-label")%>					
					<%=HtmlUtil.textBox("DM_FOLDER_NO", "",dmManageDataM.getContainerNo(), "", "50", displayMode, HtmlUtil.elementTagId("DM_FOLDER_NO"), "col-sm-8 col-md-7", request) %>			
				</div>
			</div>
			<div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "DM_BOX_NO", "col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.textBox("DM_BOX_NO", "",dmManageDataM.getBoxNo(), "", "50", displayMode, HtmlUtil.elementTagId("DM_BOX_NO"), "col-sm-8 col-md-7", request) %>			
				</div>
			</div>
			<%} %>	
		</div>
	</div>
</div>
<%=HtmlUtil.hidden("DM_IS_DOC_COMPLETE", "")%>