<%@page import="com.eaf.orig.ulo.model.app.ApplicationGroupDataM"%>
<%@page import="com.eaf.core.ulo.common.properties.SystemConstant"%>
<%@page import="com.eaf.orig.ulo.constant.MConstant"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.eaf.orig.ulo.model.app.PersonalInfoDataM"%>
<%@page import="com.eaf.core.ulo.common.display.FormatUtil"%>
<%@page import="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"%>
<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="com.eaf.core.ulo.common.display.HtmlUtil"%>
<%@page import="com.eaf.core.ulo.common.message.LabelUtil"%>
<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler"/>

<script type="text/javascript" src="orig/ulo/subform/increase/js/IncomeSubForm.js"></script>
<%
	Logger logger = Logger.getLogger(this.getClass());
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
// 	PersonalInfoDataM personalInfo = ORIGForm.getObjectForm().getPersonalInfo(1);
	ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
	PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
	if(null == personalInfo){
		personalInfo = new PersonalInfoDataM();
	}
	String personalElementId = FormatUtil.getPersonalElementId(personalInfo);
	String displayMode = HtmlUtil.EDIT;
// 	String tagId = PersonalInfoDataM.PersonalType.APPLICANT+"_1";
%>
<section class="table">
<table width='100%'>
	<tbody>
		<tr>
			<td colspan="5" class="subtoppic label"><%=LabelUtil.getText(request, "INCOME_SUBFORM")%>&nbsp;<span class="require">*</span></td>
		</tr>
		<tr>
			<td class="label"><%=LabelUtil.getText(request, "SALARY_DESC")%></td>
			<td><%=HtmlUtil.getSubFormLabel(request, "SALARY")%> </td>
			<td>
				<%=HtmlUtil.currencyBox("SALARY",personalInfo.getSalary(),true,"15",displayMode,HtmlUtil.elementTagId("SALARY",personalElementId),request)%>
				<span class="onlyfloatleft"><%=LabelUtil.getText(request, "BAHT")%> </span>
			</td>
			<td></td>		
			<td></td>							
		</tr>
		<tr>
			<td class="label"><%=LabelUtil.getText(request, "OWNER")%></td>
			<td><%=HtmlUtil.getSubFormLabel(request, "CIRCULATION_INCOME")%> </td>
			<td>
				<%=HtmlUtil.currencyBox("CIRCULATION_INCOME",personalInfo.getCirculationIncome(),true,"15",displayMode,HtmlUtil.elementTagId("CIRCULATION_INCOME",personalElementId),request)%>
				<span class="onlyfloatleft"><%=LabelUtil.getText(request, "BAHT")%> </span>
			</td>		
			<td class="label"><%=HtmlUtil.getSubFormLabel(request,"NET_PROFIT_INCOME")%> </td>	
			<td>
				<%=HtmlUtil.currencyBox("NET_PROFIT_INCOME",personalInfo.getNetProfitIncome(),true,"15",displayMode,HtmlUtil.elementTagId("NET_PROFIT_INCOME",personalElementId),request)%>
				<span class="onlyfloatleft"><%=LabelUtil.getText(request, "BAHT")%> </span>
			</td>
		</tr>
		<tr>
			<td class="label"></td>
			<td><%=HtmlUtil.getSubFormLabel(request, "PROCEDURE_TYPE")%> </td>
			<td>
				<%=HtmlUtil.textBox("PROCEDURE_TYPE",personalInfo.getSorceOfIncome(), "100", displayMode,HtmlUtil.elementTagId("PROCEDURE_TYPE",personalElementId),request)%>
			</td>		
			<td class="label"></td>	
			<td></td>
		</tr>
		 <tr>
		 	<td class="label"><%=LabelUtil.getText(request, "SELF_EMPLOYED")%></td>
			<td><%=HtmlUtil.getSubFormLabel(request, "FREELANCE_INCOME")%> </td>
		 	<td>
		 		<%=HtmlUtil.currencyBox("FREELANCE_INCOME",personalInfo.getFreelanceIncome(),true,"15",displayMode,HtmlUtil.elementTagId("FREELANCE_INCOME",personalElementId),request)%>
		 		<span class="onlyfloatleft"><%=LabelUtil.getText(request, "BAHT")%> </span>
		 	</td>	
			<td><%=HtmlUtil.getSubFormLabel(request, "FREELANCE_TYPE")%> </td>
		 	<td>
		 		<%=HtmlUtil.currencyBox("FREELANCE_TYPE",null,true,"15",displayMode,HtmlUtil.elementTagId("FREELANCE_TYPE",personalElementId),request)%>
		 		<span class="onlyfloatleft"><%=LabelUtil.getText(request, "BAHT")%> </span>
		 	</td>	
		 </tr>
		 <tr>
		 	<td class="label"><%=LabelUtil.getText(request, "SAVING_INCOME")%></td>
			<td><%=HtmlUtil.getSubFormLabel(request, "SAVING_INCOME")%> </td>
		 	<td>
		 		<%=HtmlUtil.currencyBox("SAVING_INCOME",personalInfo.getSavingIncome(),true,"15",displayMode,HtmlUtil.elementTagId("SAVING_INCOME",personalElementId),request)%>
		 		<span class="onlyfloatleft"><%=LabelUtil.getText(request, "BAHT")%> </span>
		 	</td>	
			<td></td>
			<td></td>
		 </tr>
		 <tr>
		 	<td class="label"><%=LabelUtil.getText(request, "OTHER")%></td>
			<td><%=HtmlUtil.getSubFormLabel(request, "MONTHLY_INCOME")%> </td>
		 	<td>
		 		<%=HtmlUtil.currencyBox("MONTHLY_INCOME",personalInfo.getMonthlyIncome(),true,"15",displayMode,HtmlUtil.elementTagId("MONTHLY_INCOME",personalElementId),request)%>
		 		<span class="onlyfloatleft"><%=LabelUtil.getText(request, "BAHT")%> </span>
		 	</td>	
			<td></td>
			<td></td>
		 </tr>
		 <tr>
		 	<td></td>
		 	<td class="label"><%=HtmlUtil.getSubFormLabel(request, "OTHER_INCOME")%></td>
		 	<td colspan="3">
		 		<table>
		 			<tr>
		 				<td><%=HtmlUtil.checkBox("SRC_OTH_INC_BONUS",personalInfo.getSrcOthIncBonus(),MConstant.FLAG.YES,displayMode,HtmlUtil.elementTagId("SRC_OTH_INC_BONUS",personalElementId),"BONUS",request)%></td>
		 				<td><%=HtmlUtil.checkBox("SRC_OTH_INC_COMMISSION",personalInfo.getSrcOthIncCommission(),MConstant.FLAG.YES,displayMode,HtmlUtil.elementTagId("SRC_OTH_INC_COMMISSION",personalElementId),"COMMISSION",request)%></td>
		 				<td><%=HtmlUtil.checkBox("SRC_OTH_INC_OTHER",personalInfo.getSrcOthIncOther(),MConstant.FLAG.YES,displayMode,HtmlUtil.elementTagId("SRC_OTH_INC_OTHER",personalElementId),"OTHER",request)%>	</td>
		 				<td><%=HtmlUtil.textBox("OTHER_INCOME_DESC",personalInfo.getSorceOfIncomeOther(),"50",displayMode,HtmlUtil.elementTagId("OTHER_INCOME_DESC",personalElementId),request)%></td>
		 			</tr>
		 		</table>
		 	</td>		
		 </tr>
		
	</tbody>
</table>
</section>

