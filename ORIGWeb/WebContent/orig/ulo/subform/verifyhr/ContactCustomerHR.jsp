<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.profile.model.UserDetailM"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.HRVerificationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/verifyhr/js/ContactCustomerHR.js"></script>

<%
	Logger logger = Logger.getLogger(this.getClass());
	String subformId ="CONTACT_CUSTOMER_HR_SUBFORM";
	
	String CACHE_NAME_USER = SystemConstant.getConstant("CACHE_NAME_USER");
	String LOCAL_TH = SystemConstant.getConstant("LOCAL_TH");
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	String VERIFY_HR_DISPLAY = SystemConstant.getConstant("VERIFY_HR_DISPLAY");
	
	UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
	PersonalInfoDataM personalInfo =(PersonalInfoDataM)EntityForm.getObjectForm();

	VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
	if(Util.empty(verificationResult)){
		verificationResult = new  VerificationResultDataM();
		personalInfo.setVerificationResult(verificationResult);
	}
	ArrayList<HRVerificationDataM> hrVerifications = verificationResult.getHrVerifications();
		AddressDataM addressWork = personalInfo.getAddress(ADDRESS_TYPE_WORK);
		if(null == addressWork){
			addressWork = new AddressDataM();
		}
		FormUtil formUtil = new FormUtil(subformId,request);
		FormEffects formEffect = new FormEffects(subformId,request);
%>

		
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-12">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "PREV_COMPANY","")%>
					<%=FormatUtil.display(addressWork.getCompanyName()) %>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="panel panel-default">
	<div class="panel-heading"><%=HtmlUtil.getText(request, "PHONE_CONTACT")%></div>
	<table class="table table-striped table-hover">
		<tbody>
			<%	ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.VERIFY_PHONE_NO);
				for(ElementInf elementInf:elements){
					elementInf.setObjectRequest(VERIFY_HR_DISPLAY);
					elementInf.writeElement(pageContext, personalInfo);
				}				
			%>	
		</tbody>
	</table>
</div>
<div class="panel panel-default">
	<div class="panel-body">
		<div class="row form-horizontal">
			<div class="col-sm-12">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "DM_REMARK","col-sm-2") %>
					<%=HtmlUtil.textBox("REMARK", "", "", "", "", "200", "", "col-sm-6", formUtil)%>
				</div>
			</div>
		</div>
		<div class="row form-horizontal">
			<div class="col-sm-12">
				<div class="form-group">
					<%=HtmlUtil.getMandatoryLabel(request, "PHONE_VER_STATUS","col-sm-2") %>
					<%=HtmlUtil.button("ADD_PHONE_VER_STATUS_Y", "ADD_PHONE_VER_STATUS_Y", "ADD_PHONE_VER_STATUS_Y", "btn btn-primary", "", formEffect)%>
					<%=HtmlUtil.button("ADD_PHONE_VER_STATUS_N", "ADD_PHONE_VER_STATUS_N", "ADD_PHONE_VER_STATUS_N", "btn btn-primary", "", formEffect)%>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="panel panel-default">
	<table class="table table-striped table-hover text-center panel-heading">
		<thead>
			<tr>
				<th width="5%"><%=HtmlUtil.getText(request, "SEQ_NO")%></th>
				<th width="10%"><%=HtmlUtil.getText(request, "CONTACT_DATE")%></th>
				<th width="10%"><%=HtmlUtil.getText(request, "PHONE_VER_STATUS")%></th>
				<th width="15%"><%=HtmlUtil.getText(request, "PHONE_NO")%></th>
				<th width="30%"><%=HtmlUtil.getText(request, "DM_REMARK")%></th>
				<th width="15%"><%=HtmlUtil.getText(request, "CONTACT_BY")%></th>
			</tr>
		</thead>
		<tbody>
			<% if(!Util.empty(hrVerifications)){
					for (int i=hrVerifications.size()-1;i>=0;i--) {
						int seq =i+1;
			%>
			<tr>
				<td><%=FormatUtil.display(String.valueOf(seq))%></td>
				<td><%=FormatUtil.display(hrVerifications.get(i).getCreateDate(), LOCAL_TH, "dd/MM/yyyy HH:mm") %></td>
				<td>
					<%=FormatUtil.display(hrVerifications.get(i).getPhoneVerStatus()!=null 
					&& hrVerifications.get(i).getPhoneVerStatus().equals("Y") 
					? LabelUtil.getText(request, "PHONE_VER_STATUS_Y") 
					: LabelUtil.getText(request, "PHONE_VER_STATUS_N"))%>
				</td>
				<td><%=hrVerifications.get(i).getPhoneNo()%></td>
				<td><%=FormatUtil.display(hrVerifications.get(i).getRemark())%></td>
				<td><%=FormatUtil.display(CacheControl.getName(CACHE_NAME_USER, userM.getUserName()))%></td>
			</tr>
			<%		}
				}else{ %>
			<tr>
				<td colspan="6" align="center"><%=HtmlUtil.getText(request, "NO_RECORD_FOUND")%></td>
			</tr>
			<%
				}
			%> 
		</tbody>
	</table>
</div>

