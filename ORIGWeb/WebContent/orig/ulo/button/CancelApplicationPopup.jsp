<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<script type="text/javascript" src="orig/ulo/button/js/CancelApplicationPopup.js"></script>

<div class="popupheader" style="float: left;">
	<h3 class="popuptitle" style="margin-top: 1px;"></h3>
</div>
<section class='work_tools_area  formheaderbuttonswrapper' style="width: auto; margin: 0">
	<%=HtmlUtil.button("SAVE_POPUP_BTN","",HtmlUtil.EDIT,"btn_icon", "tooltip title='Cancel Application'",request) %>
	<%=HtmlUtil.button("CLOSE_POPUP_BTN","",HtmlUtil.EDIT, "btn_close","", request) %>
</section>