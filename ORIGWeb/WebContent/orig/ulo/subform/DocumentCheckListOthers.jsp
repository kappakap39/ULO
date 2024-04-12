<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.orig.ulo.model.app.DocumentCheckListReasonDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.DocumentCheckListDataM"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/js/DocumentCheckListOthers.js"></script>
<%
	String subformId = "DOCUMENT_CHECK_LIST_OTHERS";
	Logger logger = Logger.getLogger(this.getClass());
	FormUtil formUtil = new FormUtil(subformId,request);
	String displayMode = HtmlUtil.EDIT;
	ArrayList<DocumentCheckListDataM> documentCheckLists = ORIGForm.getObjectForm().getDocumentCheckLists();
	if(Util.empty(documentCheckLists)){
		documentCheckLists = new ArrayList<DocumentCheckListDataM>();
	}
%>

<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-md-5">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,"","REQUEST_DOC","REQUEST_DOC","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.dropdown("DOCUMENT_CODE", "", "", "", "", SystemConstant.getConstant("CACH_NAME_DOCUMENT_LIST"), "", "", "", "col-sm-8 col-md-7", request)%>
					<%-- <%=HtmlUtil.dropdown("","", ,"","", SystemConstant.getConstant("CACH_NAME_DOCUMENT_LIST"), "ALL_ALL_ALL", formUtil.getDisplayMode("REQUEST_DOC",""), "","col-sm-8 col-md-7", request)%> --%>
				</div>
			</div>
			<div class="col-md-5">
				<div class="form-group">
					<%=HtmlUtil.getSubFormLabel(request,"","PERSONAL_DOC","PERSONAL_DOC","col-sm-4 col-md-5 control-label")%>
					<%=HtmlUtil.dropdown("APPLICANT_TYPE","","","","", SystemConstant.getConstant("FIELD_ID_PERSONAL_TYPE"), "ALL_ALL_ALL", displayMode, "","col-sm-8 col-md-7", request)%>
				</div>
			</div>
			<div class="col-md-2">
				<div class="form-group">
					<%=HtmlUtil.button("ADD_CHECK_DOC_BTN", "ADD_BTN",displayMode,"btn btn-primary","", request) %>
				</div>
			</div>

		</div>
	</div>
</div>
<div class="row padding-top">
	<div class="col-md-12">
		<table class="table table-hover table-striped table-inbox">
			<tr>
				<th colspan="3"><%=LabelUtil.getText(request, "FOLLOWED_DOC_LIST") %></th>
				<th><%=LabelUtil.getText(request, "RECEIVED_DOC_CORRECT") %></th>
			</tr>
		<%if(!Util.empty(documentCheckLists)){ 
			int ROW_NUM=0;
			for(DocumentCheckListDataM documentCheckList :documentCheckLists){
				documentCheckList.setSeq(ROW_NUM);
				ArrayList<DocumentCheckListReasonDataM> docResonLists  = documentCheckList.getDocumentCheckListReasons();
				String onclickActionJS ="onclick=DELETE_CHECK_DOC_BTNActionJS('"+ROW_NUM+"')";%>	
			<tr>
				<td><%=HtmlUtil.icon("DELETE_DOCUMENT_BTN", HtmlUtil.EDIT, "btnsmall_delete", onclickActionJS, request) %></td>
				<td><%=CacheControl.getName(SystemConstant.getConstant("CACH_NAME_DOCUMENT_LIST"),documentCheckList.getDocumentCode())%></td>
				<td>
				<%if(!Util.empty(docResonLists)){
			  		for(DocumentCheckListReasonDataM docResons:docResonLists){%>
			  			<div class="col-sm-12">
							<div class="form-group">
								<%=HtmlUtil.checkBox("DOC_REASON", documentCheckList.getDocumentCode(), docResons.getDocReason(), docResons.getDocReason(), displayMode, "", "", "col-sm-3 col-md-4", request)%>
								<div class="col-sm-9 col-md-8"><%=CacheControl.getName(SystemConstant.getConstant("CACH_NAME_DOCUMENT_REASON"), documentCheckList.getDocumentCode()+"_"+docResons.getDocReason())%></div>
							</div>
						</div>
			  		<div></div>
			  	 <%} 
			  	}%>
				</td>
				<td><%=HtmlUtil.checkBox("RECEIVE","",MConstant.ORIG_DOC_CHECK_LIST_RECEIVE.RECEIVE_DOC,displayMode,"","", request) %></td>
			</tr>
				<%}
				ROW_NUM++;
			 }else{%>
			 	<tr><td align="center" colspan="4"><%=LabelUtil.getText(request, "NO_RECORD_FOUND")%></td></tr>		
			 <%} %>
		 	<tfoot>
				<tr><td colspan="4"></td></tr>
			</tfoot>
		</table>
	</div>
</div>