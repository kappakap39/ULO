<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.core.ulo.common.util.FormDisplayModeUtil"%>
<%@page import="java.awt.DisplayMode"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.AttachmentDataM"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>

<script type="text/javascript" src="orig/ulo/subform/js/AttachmentSubForm.js"></script>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>
<%
	Logger logger = Logger.getLogger(this.getClass());
	ArrayList<AttachmentDataM> attachmentList = ORIGForm.getObjectForm().getAttachments();
	
	if(Util.empty(attachmentList)){
		attachmentList  = new  ArrayList<AttachmentDataM>();
	}	
	String displayMode = HtmlUtil.EDIT;
	String subformId="ATTACHMENT_SUBFORM";
	FormEffects formEffect = new FormEffects(subformId,request);
		
%>

<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-md-12">
				<div class="form-group">
				<div class="col-sm-5 col-md-4"><%=HtmlUtil.button("ATTACH_FILE_BTN", "", "ATTACH_FILE_BTN", "ATTACH_FILE_BTN", "btn btn-primary", "", formEffect) %></div>
					<%-- <div class="col-sm-5 col-md-4"><%=HtmlUtil.button("ATTACH_FILE_BTN", "ATTACH_FILE_BTN",DisplayMode, "btn btn-primary", "", request) %></div> --%>
				</div>
			</div>
		</div>
	</div>
	<table class="table table-striped table-hover">
	<% if(!Util.empty(attachmentList)){
		for(AttachmentDataM attachment : attachmentList){
			String onclickActionJS= "onclick=DELETE_ATTACH_FILE_BTNActionJS('"+attachment.getAttachId()+"')";
			String linkDownLoad="";
			if(!Util.empty(attachment.getFileName())){
				String param="ATTACHMENT_ID="+attachment.getAttachId()+"&DOWNLOAD_ID=DOWNLOAD_ATTACH_FILE";
				linkDownLoad =HtmlUtil.hyperlink("FIEL_NAME", attachment.getFileName(), request.getContextPath()+"/DownloadServlet?"+param, "", displayMode, "", request);
			}
			String fileTypeDesc= CacheControl.getName(SystemConstant.getConstant("FIELD_ID_FILE_TYPE"), attachment.getFileType());
			if(SystemConstant.getConstant("ATTACHMENT_OTHER").equals(attachment.getFileType()) && !Util.empty(attachment.getFileTypeOth())){
				fileTypeDesc +="("+attachment.getFileTypeOth()+")";
			}
			%>
			<tr class="">
				<%-- <td><%=HtmlUtil.icon("DELETE_ATTACH_FILE_BTN", DisplayMode, "btnsmall_delete", onclickActionJS, request) %></td> --%>
				<td><%=HtmlUtil.icon("DELETE_ATTACH_FILE_BTN", "DELETE_ATTACH_FILE_BTN", "btnsmall_delete", onclickActionJS, formEffect) %></td>
				<td align="left"><%=fileTypeDesc%></td>
				<td><%=linkDownLoad %></td>
				<td class="text_center"><%=LabelUtil.getText(request,"FILE_SIZE")%>&nbsp; <%=FormatUtil.display(attachment.getFileSize(),FormatUtil.Format.CURRENCY_FORMAT)%>&nbsp;<%=LabelUtil.getText(request,"FILE_UNIT")%></td>
			</tr>
	<%	}
	}else{%>
		<tr><td colspan="4" align="center"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td></tr>
	<%} %>
	</table>
</div>