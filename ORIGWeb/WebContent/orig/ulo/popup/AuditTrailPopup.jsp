<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collections"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.AuditTrailDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<script type="text/javascript" src="orig/ulo/popup/js/AuditTrailPopup.js"></script>
<%
	String subformId = "AUDITTRAIL_POPUP";
	Logger logger = Logger.getLogger(this.getClass());
// 	int PERSONAL_SEQ = 1;	
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	ArrayList<AuditTrailDataM> auditTrails = applicationGroup.getAuditTrails();
	if(Util.empty(auditTrails)){
		 auditTrails = new 	ArrayList<AuditTrailDataM>();
		 applicationGroup.setAuditTrails(auditTrails);
	}
	String displayMode = HtmlUtil.EDIT;
// 	String TAG_SMART_DATA_NOTEPAD = FormatUtil.getSmartDataEntryId("",PERSONAL_SEQ);
%>
<div class="row padding-top full-box" style="padding:0">
	<div class="full-box">
		<table class="table table-hover table-striped scrollable-table">
			<thead>
				<tr>
					<th><%=LabelUtil.getText(request, "FIELD_NAME") %></th>
					<th><%=LabelUtil.getText(request, "OLD_VALUE") %></th>
					<th><%=LabelUtil.getText(request, "NEW_VALUE") %></th>
					<th><%=LabelUtil.getText(request, "UPDATE_DATE") %></th>
					<th><%=LabelUtil.getText(request, "CREATE_BY") %></th>
					<th><%=LabelUtil.getText(request, "ROLE") %></th>
					<th><%=LabelUtil.getText(request, "UPDATE_BY") %></th>
					<th><%=LabelUtil.getText(request, "ROLE") %></th>
				</tr>
			</thead>
			<tbody class="medium-scrollable">
			<%if(!Util.empty(auditTrails)){
				for(AuditTrailDataM auditTrail:auditTrails){%>
				<tr>
					<td><%=FormatUtil.display(auditTrail.getFieldName()) %></td>
					<td><%=FormatUtil.display(auditTrail.getOldValue()) %></td>
					<td><%=FormatUtil.display(auditTrail.getNewValue()) %></td>
					<td><%=FormatUtil.display(auditTrail.getUpdateDate(),FormatUtil.TH,FormatUtil.Format.ddMMyyyy)%>
						<%=FormatUtil.display(auditTrail.getUpdateDate(),FormatUtil.TH,FormatUtil.Format.HHMMSS)%></td>
					<td><%=CacheControl.getName("User", auditTrail.getCreateBy())%></td>	
					<td><%=FormatUtil.display(auditTrail.getCreateRole()) %></td>
					<td><%=CacheControl.getName("User", auditTrail.getUpdateBy())%></td>
					<td><%=FormatUtil.display(auditTrail.getRole()) %></td>
				</tr>
				<%}
				}else{ %>
				<tr>
					<td colspan="6" align="center"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td>
				</tr>
				<%} %>
			</tbody>
			<tfoot>
				<tr><td colspan="6"></td></tr>
			</tfoot>
		</table>
	</div>
</div>


