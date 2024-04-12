<%@page import="com.eaf.orig.ulo.model.cardMaintenance.CardMaintenanceDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<jsp:useBean id="CardMaintenanceForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.CardMaintenanceFormHandler"/>
<script type="text/javascript" src="orig/ulo/button/js/CardMaintenanceButton.js"></script>
<%
CardMaintenanceDataM cardMaintenance = (CardMaintenanceDataM)CardMaintenanceForm.getObjectForm();
 %>
<div class="row">
	<div class="col-sm-3" style="width: auto;">
		<%		
			if("EDIT".equals(cardMaintenance.getDisplayMode())){
				out.print(HtmlUtil.button("SAVE_CARD_MAINTENANCE_BTN","",HtmlUtil.EDIT,"btn_save", "tooltip title='Save CardMaintenance'",request));
			}
			out.print(HtmlUtil.button("CLOSE_CARD_MAINTENANCE_BTN","",HtmlUtil.EDIT,"btn_close", "tooltip title='Close CardMaintenance'",request));
		%>
	</div>
</div>
<script>
	reloadTooltip();
</script>