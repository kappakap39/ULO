<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<script type="text/javascript" src="orig/ulo/button/js/DMBorrowButton.js"></script>
<div class="row">
	<div class="col-xs-12" style="width: auto;float:right;padding:10px;">
	<%
		out.print(HtmlUtil.button("SAVE_DMBORROW_BTN","", HtmlUtil.EDIT,"btn_save", "title=Save",request));
		out.print(HtmlUtil.button("CLOSE_DMBORROW_BTN","",HtmlUtil.EDIT,"btn_close", "title=Cancel",request));
	%>
	</div>
</div>
<%=HtmlUtil.hidden("buttonAction","")%>