<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.control.util.ApplicationUtil"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.sql.Date" %>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.AttachmentDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<%@page import="com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler" />
<script type="text/javascript" src="orig/ulo/subform/js/ApplicationInfoSubForm.js"></script>
<%
	String subformId = "OLD_DATA_SUBFORM";
	Logger logger = Logger.getLogger(this.getClass());
	String CACHE_TEMPLATE = SystemConstant.getConstant("CACHE_TEMPLATE");
	String SEARCH_BRANCH_INFO = SystemConstant.getConstant("SEARCH_BRANCH_INFO");
	String CACHE_APPLY_CHANNEL = SystemConstant.getConstant("CACHE_APPLY_CHANNEL");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	boolean SHOW_ATTACHMENT = MConstant.FLAG.YES.equals(SystemConstant.getConstant("OLD_DATA_PAGE_SHOW_ATTACHMENT"));
	
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	if (null == applicationGroup) {
		applicationGroup = new ApplicationGroupDataM();
	}	
	PersonalInfoDataM personalInfoA = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
	PersonalInfoDataM personalInfoS = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
	
	ArrayList<ApplicationDataM> appList = applicationGroup.getApplications();
	
	ApplicationDataM appA = null;
	ApplicationDataM appS = null;
	for(ApplicationDataM app : appList)
	{
		if(app.getUpdateBy() != null)
		{
			if(personalInfoA != null && app.getUpdateBy().equals(personalInfoA.getPersonalId()))
			{
				appA = app;
			}
			if(personalInfoS != null && app.getUpdateBy().equals(personalInfoS.getPersonalId()))
			{
				appS = app;
			}
		}
	}
	
	String productA = "";
	String productS = "";
	String applicationTypeA = "";
	String applicationTypeS = "";
	
	boolean isKPL = KPLUtil.isKPL(applicationGroup);
	
	if(appA != null)
	{
		productA = (isKPL) ? HtmlUtil.getText(request, "KPL") : CardInfoUtil.getCardDetail(appA.getCardlinkFlag(),"CARD_TYPE_DESC");
		applicationTypeA = appA.getApplicationType();
	}
	if(appS != null)
	{
		productS = CardInfoUtil.getCardDetail(appS.getCardlinkFlag(),"CARD_TYPE_DESC");
		applicationTypeS = appS.getApplicationType();
	}
	
	if(null == personalInfoA){
		personalInfoA = new PersonalInfoDataM();
	}
	
	//Attachments
	ArrayList<AttachmentDataM> attachmentList = (SHOW_ATTACHMENT) ? applicationGroup.getAttachments() : null;
	if(Util.empty(attachmentList))
	{
		attachmentList  = new  ArrayList<AttachmentDataM>();
	}	
	
	String displayMode = HtmlUtil.EDIT;

	FormUtil formUtil = new FormUtil(subformId,request);
	
	String appGroupNo = ApplicationUtil.getApplicationGroupNo(applicationGroup);
%>
<div class="panel panel-default">
<div class="panel-body">
<div class="row form-horizontal">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "APPLICATION_NO", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7"><%=FormatUtil.display(appGroupNo)%></div>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "APPLICATION_TEMPLATE", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7"><%=CacheControl.getName(CACHE_TEMPLATE, applicationGroup.getApplicationTemplate(), request)%></div>
		</div>
			<%=HtmlUtil.hidden("APPLICATION_TEMPLATE_ID", applicationGroup.getApplicationTemplate()) %>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId, "APPLY_DATE", "APPLY_DATE", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7"><%=FormatUtil.display(applicationGroup.getApplicationDate(), FormatUtil.TH, FormatUtil.Format.ddMMyyyy)%></div>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "ARCHIVE_DATE", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7"><%=FormatUtil.display(applicationGroup.getUpdateDate(), FormatUtil.TH, FormatUtil.Format.ddMMyyyy)%></div>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "APPLICATION_STATUS", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7"><%=applicationGroup.getApplicationStatus()%></div>
		</div>
	</div>
</div>
</div>
</div>

<header class="titlecontent" ><%=HtmlUtil.getText(request, "LIST_PERSONAL_INFO")%></header>
<div style="height: 60px;">&nbsp;</div>
<div class="panel panel-default">
<div class="panel-body">
<b>[<%=HtmlUtil.getText(request, "PRIMARY_CARD")%>]</b>
<div class="row form-horizontal">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "TH_FIRST_LAST_NAME", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7"><%=FormatUtil.display(personalInfoA.getThFirstName() + " " + personalInfoA.getThLastName())%></div>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "ID_NO", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7"><%=personalInfoA.getIdno()%></div>
		</div>
	</div>
</div>
<div class="row form-horizontal">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "PRODUCT", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7"><%=productA%></div>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "APPLICATION_TYPE", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7"><%=applicationTypeA%></div>
		</div>
	</div>
</div>
<% if(!Util.empty(personalInfoS)){%>
<br>
<b>[<%=HtmlUtil.getText(request, "SUPPLEMENTARY_CARD")%>]</b>
<div class="row form-horizontal">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "TH_FIRST_LAST_NAME", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7"><%=FormatUtil.display(personalInfoS.getThFirstName() + " " + personalInfoS.getThLastName())%></div>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "ID_NO", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7"><%=personalInfoS.getIdno()%></div>
		</div>
	</div>
</div>
<div class="row form-horizontal">
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "PRODUCT", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7"><%=productS%></div>
		</div>
	</div>
	<div class="col-sm-6">
		<div class="form-group">
			<%=HtmlUtil.getFieldLabel(request, "APPLICATION_TYPE", "col-sm-4 col-md-5 control-label")%>
			<div class="col-sm-8 col-md-7"><%=applicationTypeS%></div>
		</div>
	</div>
</div>
<% } %>
</div>
<% if(SHOW_ATTACHMENT){ %>
</div>
<header class="titlecontent"><%=LabelUtil.getText(request,"ATTACH_FILE")%></header>
<div style="height: 60px;">&nbsp;</div>
<div class="panel panel-default">
	<table class="table table-striped table-hover">
	<% if(!Util.empty(attachmentList)){
		for(AttachmentDataM attachment : attachmentList){
			String linkDownLoad="";
			if(!Util.empty(attachment.getFileName())){
				String param="ATTACHMENT_ID="+attachment.getAttachId()+"&DOWNLOAD_ID=DOWNLOAD_OLD_ATTACH_FILE";
				linkDownLoad =HtmlUtil.hyperlink("FIEL_NAME", attachment.getFileName(), request.getContextPath()+"/DownloadServlet?"+param, "", displayMode, "", request);
			}
			String fileTypeDesc= CacheControl.getName(SystemConstant.getConstant("FIELD_ID_FILE_TYPE"), attachment.getFileType());
			if(SystemConstant.getConstant("ATTACHMENT_OTHER").equals(attachment.getFileType()) && !Util.empty(attachment.getFileTypeOth())){
				fileTypeDesc +="("+attachment.getFileTypeOth()+")";
			}
			%>
			<tr class="">
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
<% } %>