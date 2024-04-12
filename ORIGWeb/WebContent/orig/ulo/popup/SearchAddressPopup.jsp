<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%-- <%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%> --%>
<%-- <%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%> --%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%-- <%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%> --%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="Form" scope="session" class="com.eaf.j2ee.pattern.control.FormHelper"/>
<jsp:useBean id="ModuleForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler"/>

<script type="text/javascript" src="orig/ulo/popup/js/AddressPopup.js"></script>
<%
	String subformId = "SERACH_ADDRESS_POPUP";	
	String ADDRESS_ELEMENT_ID = ModuleForm.getRequestData("PERSONAL_TYPE")+"_"+ModuleForm.getRequestData("ADDRESS_TYPE");
	String ELEMENT_ID = request.getParameter("ADDRESS_ELEMENT_ID");
	Logger logger = Logger.getLogger(this.getClass());
// 	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");		
// 	HashMap<String, AddressDataM> hashAddress = (HashMap<String, AddressDataM>) ModuleForm.getObjectForm();
// 	AddressDataM currentAddress = hashAddress.get(AddressDataM.ADDRESS_TYPE.CURRENT);
// 	if (null == currentAddress) {
// 		currentAddress = new AddressDataM();
// 	}
	
	String displayMode = HtmlUtil.EDIT;
	
	FormUtil formUtil = new FormUtil(subformId,request);
%>
<div class="panel panel-default">
<div class="panel-heading"><%=LabelUtil.getText(request, "SEARCH_BY")%></div>
<div class="panel-body"> 
<div class="row form-horizontal">
	<div class="col-sm-12">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"SEARCH_TAMBOL","SEARCH_TAMBOL","col-sm-2 col-md-5 marge-label control-label")%>
			<div class="col-sm-10 col-md-9 marge-field">
				<div class="row">
					<div class="col-xs-12">
						<%=HtmlUtil.textBox("SEARCH_TAMBOL","SEARCH_TAMBOL_"+ADDRESS_ELEMENT_ID,"SEARCH_TAMBOL",
							null,"","30","","col-sm-5 col-xs-10 col-xs-padding",formUtil)%>
						<div class="col-xs-2">
							<%=HtmlUtil.button("SEARCH_TAMBOL_BTN","DM_SEARCH_BTN","","",HtmlUtil.elementTagId("SEARCH_TAMBOL"),request) %>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</div>

<div class="panel-heading"><%=LabelUtil.getText(request, "SEARCH_BY")%></div>
<div class="panel-body"> 
<div class="row form-horizontal">
	<div class="col-sm-12">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"SEARCH_AMPHUR","SEARCH_AMPHUR","col-sm-2 col-md-5 marge-label control-label")%>
			<div class="col-sm-10 col-md-9 marge-field">
				<div class="row">
					<div class="col-xs-12">
						<%=HtmlUtil.textBox("SEARCH_AMPHUR","SEARCH_AMPHUR_"+ADDRESS_ELEMENT_ID,"SEARCH_AMPHUR",
							null,"","30","","col-sm-5 col-xs-10 col-xs-padding",formUtil)%>
						<div class="col-xs-2"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clearfix"></div>
	<div class="col-sm-12">
		<div class="form-group">
			<%=HtmlUtil.getSubFormLabel(request,subformId,"PROVINCE","PROVINCE","col-sm-2 col-md-5 marge-label control-label")%>
			<div class="col-sm-10 col-md-9 marge-field">
				<div class="row">
					<div class="col-xs-12">
						<%=HtmlUtil.textBox("SEARCH_PROVINCE","SEARCH_PROVINCE_"+ADDRESS_ELEMENT_ID,"SEARCH_PROVINCE",
							null,"","15","","col-sm-5 col-xs-10 col-xs-padding",formUtil)%>
						<div class="col-xs-2"><%=HtmlUtil.button("SEARCH_PROVINCE_BTN","DM_SEARCH_BTN","","",HtmlUtil.elementTagId("SEARCH_PROVINCE_BTN"),request) %></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
</div>
<div class="panel panel-default"  id="dataSearch">
<table id="dataSearch" class="table table-striped table-hover" >
	<thead>
		<tr class="tabletheme_header">
			<th></th>
			<th><%=LabelUtil.getText(request,"TAMBOL")%></th>
			<th><%=LabelUtil.getText(request,"AMPHUR")%></th>
			<th><%=LabelUtil.getText(request,"PROVINCE")%></th>
			<th><%=LabelUtil.getText(request,"ZIPCODE")%></th>
		</tr>
	</thead>
	<tbody>
	
	</tbody>
</table>
</div>