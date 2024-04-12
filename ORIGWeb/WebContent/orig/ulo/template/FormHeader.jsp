<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script src="orig/ulo/template/js/FormHeader.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
	String formId = ORIGForm.getFormId();
	String roleId = ORIGForm.getRoleId();
 %>
<div class="row formheaderbar">
	<div class="col-xs-12">
		<div class="formheaderbuttons groupList" id="FormHeaderButton">
			<jsp:include page="FormHeaderButton.jsp" flush="true"/>
		</div>
		<%
			ElementInf elementInf = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.PRE_TIME, roleId);
			if(!Util.empty(elementInf.getImplementId())){
				elementInf.writeElement(pageContext, roleId);
			}
		%>
	</div>
</div>
<div class="row formheaderbuttonswrapper">
	<div class="col-xs-12">
		<div class="tab-container"></div>
		<div class="groupList clear" id="FormButtonAction" style="float: right"></div>
	</div>
</div>
<script>
	resizeFormHeader();
	resizeFormTemplate();
</script>
<style>
	.popup_overflow{
	}
	.popover {
		visibility: hidden;
		font-size: 1em;
		
	}
	.popover.in {
		visibility: visible;
	}
	#popover_COMMENT {
		right: 15px;
		margin-left: -10px;
		max-width: none;
		display: block;
		height: <%= SystemConstant.getConstant("HEADER_NOTEPAD_HEIGHT") %>;
		<%= (!Util.empty(SystemConstant.getConstant("HEADER_NOTEPAD_WIDTH"))
		? "width: " + SystemConstant.getConstant("HEADER_NOTEPAD_WIDTH"):"") %>
	}
	#popover_HISTORY {
		right: 15px;
		margin-left: -10px;
		max-width: none;
		display: block;
		height: <%= SystemConstant.getConstant("HEADER_HISTORY_HEIGHT") %>;
		width: 80%;
		<%// (!Util.empty(SystemConstant.getConstant("HEADER_HISTORY_WIDTH"))
		//? "width: " + SystemConstant.getConstant("HEADER_HISTORY_WIDTH"):"")%>
	}
	#popover_AUDIT_TRAIL {
		right: 15px;
		margin-left: -10px;
		max-width: none;
		display: block;
		height: <%= SystemConstant.getConstant("HEADER_AUDIT_TRAIL_HEIGHT") %>;
		<%= (!Util.empty(SystemConstant.getConstant("HEADER_AUDIT_TRAIL_WIDTH"))
		? "width: " + SystemConstant.getConstant("HEADER_AUDIT_TRAIL_WIDTH"):"")%>
	}
	#popover_DOCUMENT_LIST {
		right: 15px;
		margin-left: -10px;
		max-width: none;
		display: block;
		height: <%= SystemConstant.getConstant("HEADER_DOCUMENT_HEIGHT") %>;
		<%= (!Util.empty(SystemConstant.getConstant("HEADER_DOCUMENT_WIDTH"))
		? "width: " + SystemConstant.getConstant("HEADER_DOCUMENT_WIDTH"):"")%>
	}
</style>

<div class="popover-wrapper">
	<div class="popover fade bottom" role="tooltip" id="popover_COMMENT">
		<div class="arrow"></div>
		<h3 class="popover-title" style="display: none;"></h3>
		<div class="popover-content" style="overflow-x:hidden !important;" id="comment_popup">
			<jsp:include page="/orig/ulo/popup/CommentPopup.jsp" ></jsp:include>
		</div>
	</div>
	<div class="popover fade bottom" role="tooltip" id="popover_HISTORY">
		<div class="arrow"></div>
		<h3 class="popover-title" style="display: none;"></h3>
		<div class="popover-content" style="overflow-x:hidden !important;">
			<jsp:include page="/orig/ulo/popup/HistoryActionPopup.jsp" ></jsp:include>
		</div>
	</div>
	<div class="popover fade bottom" role="tooltip" id="popover_AUDIT_TRAIL">
		<div class="arrow"></div>
		<h3 class="popover-title" style="display: none;"></h3>
		<div class="popover-content" style="overflow-x:hidden !important;">
			<jsp:include page="/orig/ulo/popup/AuditTrailPopup.jsp" ></jsp:include>
		</div>
	</div>
<!-- 	<div class="popover fade bottom" role="tooltip" id="popover_DOCUMENT_LIST"> -->
<!-- 		<div class="arrow"></div> -->
<!-- 		<h3 class="popover-title" style="display: none;"></h3> -->
<!-- 		<div class="popover-content" style="overflow-x:hidden !important;" id='HEADER_SECTION_DOCUMENT_HEADER_FORM'> -->
<%-- 			<%pageContext.include("/orig/ulo/template/HeaderFormTemplate.jsp?formId=DOCUMENT_HEADER_FORM",true);%> --%>
<!-- 		</div> -->
<!-- 	</div> -->
</div>

<script>
	
</script>