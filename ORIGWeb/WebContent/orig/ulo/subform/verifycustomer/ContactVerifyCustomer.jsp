<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormEffects"%>
<%@page import="com.eaf.orig.ulo.control.util.PersonalInfoUtil"%>
<%@page import="com.eaf.orig.profile.model.UserDetailM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemCache"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Date"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.app.view.form.privilegeprojectcode.util.PrivilegeUtil"%>
<%@page import="com.eaf.orig.ulo.model.app.IdentifyQuestionSetDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PhoneVerificationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.HRVerificationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.LoanDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.CardDataM"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.app.view.util.kpl.KPLUtil"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.properties.CacheControl"%>
<%@page import="com.eaf.core.ulo.common.properties.FormControl"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="java.util.Collections"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>
<script type="text/javascript" src="orig/ulo/subform/verifycustomer/js/ContactVerifyCustomer.js"></script>

<%
	Logger logger = Logger.getLogger(this.getClass());
	String CACHE_NAME_USER = SystemConstant.getConstant("CACHE_NAME_USER");
	String LOCAL_TH = SystemConstant.getConstant("LOCAL_TH");
	String FIELD_ID_CONTACT_TIME = SystemConstant.getConstant("FIELD_ID_CONTACT_TIME");
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");	
	String VERIFY_CUSTOMER_DISPLAY = SystemConstant.getConstant("VERIFY_CUSTOMER_DISPLAY");
	String PHONE_CARD_LINK = SystemConstant.getConstant("PHONE_CARD_LINK");
	String displayMode = HtmlUtil.EDIT;
	UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
	PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
	if(Util.empty(personalInfo)){
		personalInfo = new PersonalInfoDataM();
	}
	VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
	if(Util.empty(verificationResult)){
		verificationResult = new VerificationResultDataM();
	}
	ApplicationDataM applicationInfo = applicationGroup.getApplicationProduct(PRODUCT_CRADIT_CARD);
	if(Util.empty(applicationInfo)){
		applicationInfo = new ApplicationDataM();
	}
	CardDataM borrowerCard = applicationInfo.getCard();
	if(Util.empty(borrowerCard)){
		borrowerCard = new CardDataM();
	}
	FormUtil formUtil = new FormUtil("CONTACT_CUSTOMER_VERIFY_SUBFORM",request);
	FormEffects formEffect = new FormEffects("CONTACT_CUSTOMER_VERIFY_SUBFORM",request);
	
	boolean isKPL = KPLUtil.isKPL(applicationGroup);
%>

<div class="panel panel-default">
	<div class="panel-body" id="ContactCustomerHR" >
		<div class="row form-horizontal">
			<div class="col-sm-3">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "CARD_TYPE","")%>
					<%=FormatUtil.display(PrivilegeUtil.getCardTypeDesc(applicationGroup.getApplicationTemplate()))%>
				</div>
			</div>
			<%if(isKPL){ %>
			<div class="col-sm-3">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "LOAN_TYPE","")%>
					<%=FormatUtil.display("Xpress Loan")%>
				</div>
			</div>
			<% } %>
			 <div class="col-sm-6">
				<div class="form-group">
					<%=HtmlUtil.getFieldLabel(request, "CONTACT_TIME","col-sm-5")%>
					<%=HtmlUtil.dropdown("CONTACT_TIME", "", "", "CONTACT_TIME", "", personalInfo.getContactTime(), "", FIELD_ID_CONTACT_TIME, "ALL","", "col-sm-6", formUtil) %>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="panel panel-default">
	<div class="panel-heading"><%=LabelUtil.getText(request,"PHONE_CONTACT")%></div>
	<table class="table table-striped table-hover">
		<tbody>
			<%	
				ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.VERIFY_PHONE_NO);
				for(ElementInf elementInf:elements){
					elementInf.setObjectRequest(VERIFY_CUSTOMER_DISPLAY);
					elementInf.writeElement(pageContext, personalInfo);
				}
				if(Util.empty(personalInfo.getVerCusPhoneNo())){
					elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.VERIFY_PHONE_NO_DUMMY);
					for(ElementInf elementInf:elements){
					elementInf.setObjectRequest(VERIFY_CUSTOMER_DISPLAY);
					elementInf.writeElement(pageContext, personalInfo);
					}
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
					<%=HtmlUtil.getFieldLabel(request, "DM_REMARK","col-sm-2")%>
					<%=HtmlUtil.textBox("REMARK", "", "", "", "", "200", "", "col-sm-6", formUtil)%>
				</div>
			</div>
		</div>
		<div class="row form-horizontal">
			<div class="col-sm-12">
				<div class="form-group">
					<%=HtmlUtil.getMandatoryLabel(request, "PHONE_VER_STATUS","col-sm-2") %>
					<%=HtmlUtil.button("ADD_PHONE_VER_STATUS_CUS_Y", "ADD_PHONE_VER_STATUS_CUS_Y", "ADD_PHONE_VER_STATUS_Y", "btn btn-primary", "", formEffect)%>
					<%=HtmlUtil.button("ADD_PHONE_VER_STATUS_CUS_N", "ADD_PHONE_VER_STATUS_CUS_N", "ADD_PHONE_VER_STATUS_N", "btn btn-primary", "", formEffect)%>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="panel panel-default">
	<table class="table table-striped table-hover text-center panel-heading">
		<thead>
			<% ArrayList<PhoneVerificationDataM> phoneVerifications = verificationResult.getPhoneVerifications();%>
			<tr>
				<th width="5%"><%=HtmlUtil.getText(request, "SEQ_NO")%></th>
				<th width="10%"><%=HtmlUtil.getText(request, "LIFE_CYCLE")%></th>
				<th width="10%"><%=HtmlUtil.getText(request, "CONTACT_DATE")%></th>
				<th width="10%"><%=HtmlUtil.getText(request, "PHONE_VER_STATUS")%></th>
				<th width="15%"><%=HtmlUtil.getText(request, "CONTACT_TYPE")%></th>
				<th width="15%"><%=HtmlUtil.getText(request, "PHONE_NO_EMAIL")%></th>
				<th width="25%"><%=HtmlUtil.getText(request, "DM_REMARK")%></th>
				<th width="15%"><%=HtmlUtil.getText(request, "CONTACT_BY")%></th>
			</tr>
		</thead>
		<tbody>
			<% if(!Util.empty(phoneVerifications)){
					Collections.sort(phoneVerifications,new PhoneVerificationDataM());
					for (int i=phoneVerifications.size()-1;i>=0;i--) {
						int seq = i+1;
			%>
			<tr>
				<td><%=seq%></td>
				<%
					String lifeCycle = "";
					if(phoneVerifications.get(i).getLifeCycle() == 1 || Util.empty(phoneVerifications.get(i).getLifeCycle())){
						lifeCycle = HtmlUtil.getText(request, "FIRST_APPLICATION_CYCLE");
					}else{
						lifeCycle = HtmlUtil.getText(request, "APPLICATION_CYCLE") + " " + phoneVerifications.get(i).getLifeCycle() ;
					}
				 %>
				<td><%=lifeCycle%></td>
			
				<td><%=FormatUtil.display(phoneVerifications.get(i).getCreateDate(), LOCAL_TH, "dd/MM/yyyy HH:mm") %></td>
				<td><%=phoneVerifications.get(i).getPhoneVerStatus().equals("Y") ? LabelUtil
						.getText(request, "PHONE_VER_STATUS_Y") : LabelUtil
						.getText(request, "PHONE_VER_STATUS_N")%>
				</td>
				<td>
					<%	String refId = verificationResult.getRefId();
						String personalType ="";
						if(personalInfo.getPersonalId().equals(refId)){
							personalType = personalInfo.getPersonalType();
						}
						String ContactTypeConstant = "PHONE_TYPE_"+phoneVerifications.get(i).getContactType();
						if(!Util.empty(SystemConstant.getConstant(ContactTypeConstant)) && PHONE_CARD_LINK.contains(SystemConstant.getConstant(ContactTypeConstant))){
							personalType =PersonalInfoDataM.PersonalType.APPLICANT;
						}
					%>
					<%=FormatUtil.display(personalType!=null && personalType.equals(PersonalInfoDataM.PersonalType.APPLICANT)?HtmlUtil.getText(request, "PRIMARY_CARD"):HtmlUtil.getText(request, "SUPPLEMENTARY_CARD") ) %>
					<%=!Util.empty(phoneVerifications.get(i).getContactType()) ? HtmlUtil.getText(request,SystemConstant.getConstant(ContactTypeConstant)) : HtmlUtil.getText(request,"PHONE_NUMBER")  %>
				</td>
				<td><%=FormatUtil.display(phoneVerifications.get(i).getTelephoneNumber())%></td>
				<td><%=FormatUtil.display(phoneVerifications.get(i).getRemark())%></td>
				<td><%=FormatUtil.display(CacheControl.getName(CACHE_NAME_USER, phoneVerifications.get(i).getCreateBy()))%></td>
			</tr>
			<%
				}
			}else{
			%>
			<tr>
				<td colspan="7" align="center">
					<%=HtmlUtil.getText(request, "NO_RECORD_FOUND") %>
				</td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
</div>	
