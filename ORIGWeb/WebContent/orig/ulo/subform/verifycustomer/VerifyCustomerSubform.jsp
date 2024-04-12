<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.orig.ulo.model.app.AddressDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.ElementInf"%>
<%@page import="com.eaf.core.ulo.common.properties.ImplementControl"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>

<script type="text/javascript" src="orig/ulo/subform/question/js/VerifyCustomerQuestion.js"></script>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>

<%
	String subformId = request.getParameter("subformId");
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY"); 
	String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP"); 
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	
	ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
	PersonalInfoDataM personalApplicant = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
	ArrayList<PersonalInfoDataM> personalSupplementarys = applicationGroup.getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);
%>

<%	if(null != personalApplicant){ %>
	<section id="SECTION_IDENTIFY_QUESTION_CARD_<%=personalApplicant.getPersonalId() %>" 
		class="SECTION_IDENTIFY_QUESTION_CARD ">
		<div class="panel panel-default">
		<div class="panel-heading"><%=HtmlUtil.getFieldLabel(request, "BORROWER","")%>&nbsp;<%=FormatUtil.display(personalApplicant.getThName()) %></div>
			<div class="panel-body">
				<jsp:include page="/orig/ulo/subform/question/VerifyCustomerQuestion.jsp" />
		<%
			ElementInf elementInf = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.QUESTION_FIELDS, "VERIFY_CUSTOMER_QUESTION");
			elementInf.writeElement(pageContext, personalApplicant.getPersonalId());
		%>
		<%
			elementInf = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.QUESTION_FIELDS, "VERIFY_CUSTOMER_OTHER_QUESTION");
			
			if(MConstant.FLAG.YES.equals(elementInf.renderElementFlag(request, personalApplicant.getPersonalId()))){
				elementInf.writeElement(pageContext, personalApplicant.getPersonalId());
			}
			
		%>
			</div>
		</div>
	</section>
<%	}%>
<%	if(!Util.empty(personalSupplementarys)){
		for(PersonalInfoDataM personalSupplementary : personalSupplementarys){

			AddressDataM addressCurrent	= personalSupplementary.getAddress(ADDRESS_TYPE_CURRENT);
			AddressDataM addressWork = personalSupplementary.getAddress(ADDRESS_TYPE_WORK);
			if(addressCurrent == null) {
				addressCurrent = new AddressDataM();
			}
			if(addressWork == null) {
				addressWork = new AddressDataM();
			}
	%>
	<section id="SECTION_IDENTIFY_QUESTION_SUBCARD_<%=personalSupplementary.getPersonalId() %>" 
		class=" SECTION_IDENTIFY_QUESTION_SUBCARD ">
		<div class="panel panel-default">
		<div class="panel-heading"><%=HtmlUtil.getFieldLabel(request, "SUPPLEMENTARY","")%>&nbsp;<%=FormatUtil.display(personalSupplementary.getThName()) %></div>
			<div class="panel-body">
				<div class="form-horizontal">
					<div class="form-group">
						<div class="col-sm-6">
							<div class="col-sm-6">
								<%=HtmlUtil.getFieldLabel(request, "HOME_PHONE") %>
							</div>
							<div class="col-sm-6">
								<%=
									FormatUtil.display(Util.empty(addressCurrent.getExt1()) ? 
									FormatUtil.getPhoneNo(addressCurrent.getPhone1()) :
									FormatUtil.getPhoneNo(addressCurrent.getPhone1()) +" "+LabelUtil.getText(request, "TO")+" "
									+FormatUtil.display(addressCurrent.getExt1()))  
								%>
							</div>
						</div>
						<div class="col-sm-6">
							<div class="col-sm-6">
								<%=HtmlUtil.getFieldLabel(request, "MOBILE") %>
							</div>
							<div class="col-sm-6">
								<%=FormatUtil.getMobileNo(personalSupplementary.getMobileNo()) %>
							</div>
						</div>
					</div>
				</div>
				<div class="form-horizontal">

						<div class="form-group">
							<div class="col-sm-6">
								<div class="col-sm-6">
									<%=HtmlUtil.getFieldLabel(request, "CONTACT_TYPE_OFFICE_PHONE") %>
								</div>
								<div class="col-sm-6">
									<%=
										FormatUtil.display(Util.empty(addressWork.getExt1()) ? 
										FormatUtil.getPhoneNo(addressWork.getPhone1()) :
										FormatUtil.getPhoneNo(addressWork.getPhone1()) +" "+LabelUtil.getText(request, "TO")+" "
											+FormatUtil.display(addressWork.getExt1()))
									%>
								</div>
							</div>
						</div>
				</div>
				<%if(APPLICATION_TYPE_ADD_SUP.equals(applicationGroup.getApplicationType())){%>
 					<jsp:include page="/orig/ulo/subform/question/VerifyCustomerQuestion.jsp" /> 
 				<%} %> 
				<%
					ElementInf elementInf = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.QUESTION_FIELDS, "VERIFY_CUSTOMER_QUESTION");
					elementInf.writeElement(pageContext, personalSupplementary.getPersonalId());
				%>
				<%
					elementInf = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.QUESTION_FIELDS, "VERIFY_CUSTOMER_OTHER_QUESTION");
					if(MConstant.FLAG.YES.equals(elementInf.renderElementFlag(request, personalSupplementary.getPersonalId()))){
						elementInf.writeElement(pageContext, personalSupplementary.getPersonalId());
					}					
				%>
			</div>
		</div>
	</section>
<%		} 
	} %>
	
	
