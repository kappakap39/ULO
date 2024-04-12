<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.eaf.core.ulo.common.util.FormDisplayModeUtil"%>
<%@page import="com.eaf.core.ulo.common.util.FormUtil"%>
<%@page import="com.eaf.core.ulo.common.util.Util"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.model.app.OtherIncomeDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeCategoryDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IncomeInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.VerificationResultDataM"%>
<%@page import="com.eaf.orig.ulo.model.app.IdentifyQuestionDataM"%>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="EntityForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler"/>

<%
	Logger logger = Logger.getLogger(this.getClass());
	
	PersonalInfoDataM personalInfo = (PersonalInfoDataM)EntityForm.getObjectForm();
	if(Util.empty(personalInfo)){
		personalInfo = new  PersonalInfoDataM();
	}
	String OTHER_INCOME_QUESTION = SystemConstant.getConstant("OTHER_INCOME_QUESTION");
	String FIELD_ID_OTHER_INCOME_TYPE = SystemConstant.getConstant("FIELD_ID_OTHER_INCOME_TYPE");
	String INC_TYPE_OTH_INCOME = SystemConstant.getConstant("INC_TYPE_OTH_INCOME");

	IncomeInfoDataM allincome = personalInfo.getIncomeByType(INC_TYPE_OTH_INCOME);
	if(Util.empty(allincome)){
		allincome = new IncomeInfoDataM();
		allincome.setIncomeType(INC_TYPE_OTH_INCOME);
		personalInfo.addIncomeInfo(allincome);
	}
	
	VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
	if(Util.empty(verificationResult)){
		verificationResult = new  VerificationResultDataM();
	}
	
	IdentifyQuestionDataM identifyQuestion = verificationResult.getIndentifyQuesitionNo(SystemConstant.getConstant("OTHER_INCOME_QUESTION"));
	if(Util.empty(identifyQuestion)){
		identifyQuestion = new  IdentifyQuestionDataM();
	}
	
	String subformId ="IDENTIFY_QUESTION_SUBFORM";
	FormUtil formUtil = new FormUtil(subformId,request);
	String displayMode = FormDisplayModeUtil.getDisplayMode("SOURCE_OF_OTHER_INCOME", "", formUtil);
%>

	<td><%= HtmlUtil.getFieldLabel(request, "SOURCE_OF_OTHER_INCOME","")%></td>
	<td></td>
	<td class="text-right"><%=HtmlUtil.checkBoxInline("RESULT_CHECK", OTHER_INCOME_QUESTION, "", "SOURCE_OF_OTHER_INCOME", identifyQuestion.getResult(), "", "Y"
	, HtmlUtil.elementTagId("RESULT_CHECK_"+OTHER_INCOME_QUESTION) + "style='min-height:0px'", "RESULT_CHECK", "checkbox-inline text-left", formUtil)%></td>

<tr>
	<td></td>
	<td colspan="3">
		<table class="table table-bordered">
			<tbody>
				<%		
					ArrayList<IncomeCategoryDataM> incomeCategorys = allincome.getAllIncomes();
					logger.debug("incomeCategorys >>> "+incomeCategorys);
					if(!Util.empty(incomeCategorys)){
						for(int i=0; i<incomeCategorys.size(); i++ ){
							OtherIncomeDataM otherIncome = (OtherIncomeDataM) incomeCategorys.get(i);
							String delTag ="onclick=DELETE_PRODUCT_BTNActionJS('"+otherIncome.getSeq()+"')";
							logger.debug("getIncomeType >> "+ otherIncome.getIncomeType());
							logger.debug("getseq >> "+ otherIncome.getSeq());
				 			%>
							<tr>
								<td width="30px"><%=HtmlUtil.icon("DEL_CARDINFO", "", "btnsmall_delete", delTag, request) %></td>
								<td width="200px"><%=HtmlUtil.textBox("INCOME_TYPE", FormatUtil.getString(otherIncome.getSeq()) , otherIncome.getIncomeTypeDesc(), "", "50", "", "INCOME_TYPE_"+FormatUtil.getString(otherIncome.getSeq()), "", request)%></td>
								<td width="200px"><%=HtmlUtil.currencyBox("INCOME_AMOUNT", FormatUtil.getString(otherIncome.getSeq()), otherIncome.getIncomeAmount(), "", false, "10", displayMode, "INCOME_AMOUNT_"+FormatUtil.getString(otherIncome.getSeq()), "", request)%></td>
							<% if(i == incomeCategorys.size()-1){	%>
						 		<td rowspan="9999" style="border-top-color: white;border-left-color: white;border-right-color: white;border-bottom-color: white;">
									<%=HtmlUtil.button("ADD_OTHERINCOME_BTN","ADD_BTN",displayMode,"",HtmlUtil.elementTagId("ADD_OTHERINCOME_BTN"), request) %>
								</td>
				 			<% } %>
				 			</tr>
				 			<%
				 		}
				 	} else{
				 	%>
				 	<tr>
						<td width="430px"><%=HtmlUtil.getText(request, "NO_RECORD_FOUND")%></td>
						<td style="border:none;"><%=HtmlUtil.button("ADD_OTHERINCOME_BTN","ADD_BTN",displayMode,"",HtmlUtil.elementTagId("ADD_OTHERINCOME_BTN"), request) %></td>
					</tr>
			 	<%
				 	}	
				%>
			</tbody>
			</table>
			</td>
			</tr>