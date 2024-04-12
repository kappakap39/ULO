<%@page import="com.eaf.orig.ulo.dm.util.DocumentManageUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.model.dm.HistoryDataM"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.dm.DocumentDataM"%>
<%@page import="com.eaf.orig.ulo.model.dm.DocumentManagementDataM"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.profile.model.UserDetailM"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.DMFormHandler"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="DMForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.DMFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/js/DMDocumentCheckListSubForm.js"></script>
<%
	DocumentManagementDataM dmManageDataM = DMFormHandler.getObjectForm(request);
	if(Util.empty(dmManageDataM)){
		dmManageDataM = new DocumentManagementDataM();
	}	
	ArrayList<DocumentDataM> documents  = dmManageDataM.getDocument() ;
	if(Util.empty(documents)){
		documents = new ArrayList<DocumentDataM>();
	}	
	HistoryDataM history = dmManageDataM.getHistory();
	if(Util.empty(history)){
		history = new HistoryDataM();
	}
	
	String displayMode = DocumentManageUtil.dmStoreMode(dmManageDataM.getParam1());
%>
<div class="panel panel-default">
<table class="table table-striped table-hover" >
	<%if(!Util.empty(documents)){%>
		<tr>
			<th></th>
			<th></th>
			<th></th>
			<th class="text-center"><%=LabelUtil.getText(request, "DM_INCOMPLETE")%></th>
		</tr>
	<% 	for(DocumentDataM docM :documents){%>
		<tr id='DM_DOC_CHECKLIST'>
			<td><%=FormatUtil.display(CacheControl.displayName(SystemConstant.getConstant("FIELD_ID_DOC_CHECK_LIST"), docM.getDocType()))%></td>	
			<td><%=docM.getNoOfPage() %></td> 
			<td><%=LabelUtil.getText(request, "DM_DOC_UNIT")%></td>
			<td class="text-center check-box-center" id="CHECK_BOX_INCOMPLETE">
			<%=HtmlUtil.checkBox("CHECK_BOX_INCOMPLETE", docM.getDmSubId(), docM.getStatus(), MConstant.DM_STATUS.NOT_IN_WAREHOUSE,displayMode, "", "", request)%></td>			
		</tr>
	<%	}
	  }else{%>
		<tr>
	 		<td colspan="4" align="center"><%=LabelUtil.getText(request,"NO_RECORD_FOUND")%></td>
	 	</tr>
	<%}%>
</table>
<div class="panel-body">
<div class="row form-horizontal">
	<div class="col-md-12">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "DM_REMARK", "col-sm-2 col-md-3 control-label")%>
			<%=HtmlUtil.textarea("DM_REMARK", "", FormatUtil.display(history.getRemark()), "5", "100", "500", displayMode, HtmlUtil.elementTagId("DM_REMARK"), "col-sm-10 col-md-9", request) %>
		</div>
	</div>
</div>
</div>
</div>