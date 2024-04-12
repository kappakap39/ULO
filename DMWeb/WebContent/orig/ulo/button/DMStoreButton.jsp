<%@page import="com.eaf.orig.ulo.dm.util.DocumentManageUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.DMFormHandler"%>
<%@page import="com.eaf.orig.ulo.model.dm.DocumentManagementDataM"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<jsp:useBean id="DMForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.DMFormHandler"/>
<script type="text/javascript" src="orig/ulo/button/js/DMStoreButton.js"></script>
<div class="row">
	<div class="col-xs-12" style="width: auto;float:right;padding:10px;">
	<%	
		DocumentManagementDataM dmManageDataM = DMFormHandler.getObjectForm(request);
		if(Util.empty(dmManageDataM)){
			dmManageDataM = new DocumentManagementDataM();
		}
		out.print(HtmlUtil.button("SAVE_DMSTORE_BTN","",DocumentManageUtil.dmButtonStoreMode(dmManageDataM.getParam1()),"btn_save", "title=Save",request));
		out.print(HtmlUtil.button("CLOSE_DMSTORE_BTN","",HtmlUtil.EDIT,"btn_close", "title=Cancel",request));
	%>
	</div>
</div>
<%=HtmlUtil.hidden("buttonAction","")%>