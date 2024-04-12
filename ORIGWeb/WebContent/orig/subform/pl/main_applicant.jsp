<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.shared.util.OrigUtil"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil"%>
<%@ page import="org.apache.log4j.Logger"%>
<%@ page import="java.util.Vector"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM" %>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler"/>
<jsp:useBean id="searchType" scope="session" class="java.lang.String"/>

<script type="text/javascript" src="orig/js/subform/pl/main_applicant.js"></script>

<% 	
	org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/subform/pl/main_applicant.jsp");
	
	PLApplicationDataM applicationM = PLORIGForm.getAppForm();
	if(null == applicationM) applicationM = new PLApplicationDataM();	
	
	PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
// 	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil origc = new ORIGCacheUtil();
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String displayMode = formUtil.getDisplayMode("MAIN_APPLICANT_SUBFORM", ORIGUser.getRoles(), applicationM.getJobState(), PLORIGForm.getFormID(), searchType);
	
	if(OrigUtil.isEmptyString(personalM.getRace())) personalM.setRace("01");
	if(OrigUtil.isEmptyString(personalM.getCidType())) personalM.setCidType("01");
	
	String role = ORIGUser.getCurrentRole();
	
	String idnoDisplayMode=HTMLRenderUtil.DISPLAY_MODE_EDIT;
	if(OrigConstant.ROLE_VC.equals(role) || OrigConstant.ROLE_CA.equals(role) || OrigConstant.ROLE_FU.equals(role)){ 
	  	idnoDisplayMode = HTMLRenderUtil.DISPLAY_MODE_VIEW;
	}
	String refreshICDC=(String)request.getSession().getAttribute("REFRESH_ICDC");
    request.getSession().removeAttribute("REFRESH_ICDC");
%>   
<%=HTMLRenderUtil.displayHiddenField(displayMode,"mode-mainapplicant")%>
<table class="FormFrame">
	<tr> 
		<td class="textR" nowrap="nowrap" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "APPICANT_TYPE",ORIGUser,personalM.getCustomerType(),"MAIN_APPLICANT_SUBFORM","customertype")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"MAIN_APPLICANT_SUBFORM","customertype")%>&nbsp;:&nbsp;</td>
		<td class="inputL"  width="25%" ><%=HTMLRenderUtil.displaySelectTagScriptActionBusClassFieldID(applicationM.getBusinessClassId(),1, personalM.getCustomerType(),"customertype",displayMode,"")%></td>
		<td class="textR" nowrap="nowrap" width="25%" ><%=PLMessageResourceUtil.getTextDescription(request, "CIS_ID") %>&nbsp;:&nbsp;</td>
		<td class="inputL" width="30%" id="result_1038" ><%=HTMLRenderUtil.DisplayReplaceLineWithNull(personalM.getCisNo())%> </td> 
	</tr>	
	<tr> 
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "PAYROLL_CUSTOMER_NUMBER")%>&nbsp;:&nbsp;</td>
		<td class="inputL" id="result_1039" ><%=HTMLRenderUtil.DisplayReplaceLineWithNull(personalM.getCardLinkCustNo())%></td>
		<td colspan="2"></td>		
	</tr>	
	<tr> 
		<td class="textR" nowrap="nowrap" ><%=PLMessageResourceUtil.getTextDescription(request, "IMPORTANT_DOC",ORIGUser,personalM.getCustomerType(),"MAIN_APPLICANT_SUBFORM","cardtype")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"MAIN_APPLICANT_SUBFORM","cardtype")%>&nbsp;:&nbsp;</td>		
		<td class="inputL"  id="element_cardtype">
			<%=HTMLRenderUtil.displaySelectTagScriptActionBusClassFieldID(applicationM.getBusinessClassId(), 2 , personalM.getCidType(),"cardtype",displayMode," onchange='changeCidType();' ")%>
		</td>
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "IDENTIFICATION_NO",ORIGUser,personalM.getCustomerType(),"MAIN_APPLICANT_SUBFORM","identification_no")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"MAIN_APPLICANT_SUBFORM","identification_no")%>&nbsp;:&nbsp;</td>
		<td class="inputL" id="element_idNo">	
			<%=HTMLRenderUtil.DisplayInputButtonReset(personalM.getIdNo(),idnoDisplayMode,"identification_no","reset-idno","textbox","13")%>				    
		</td>
	</tr>
	<tr> 
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "CARD_EXPIRE_DATE",ORIGUser,personalM.getCustomerType(),"MAIN_APPLICANT_SUBFORM","card_expire_date")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"MAIN_APPLICANT_SUBFORM","card_expire_date")%>&nbsp;:&nbsp;</td>
		<td class="inputL" id="element_card_expire_date" >
			<%=HTMLRenderUtil.displayInputTagJsDate("", DataFormatUtility.DateEnToStringDateTh(personalM.getCidExpDate(), 1), displayMode, "8", "card_expire_date","","right","")%>
		</td>
		<td></td>
		<td></td>
	</tr>
	<tr> 
		<td  class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "NAME_THAI",ORIGUser,personalM.getCustomerType(),"MAIN_APPLICANT_SUBFORM","name_th")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"MAIN_APPLICANT_SUBFORM","name_th")%>&nbsp;:&nbsp;</td>
		<td class="inputL">
			<%=HTMLRenderUtil.DisplayPopUpTextBoxDescFieldIDAndTextBox(personalM.getThaiTitleName(),3,displayMode,"titleThai","textbox-code","120"
										,personalM.getThaiFirstName(),"name_th","textbox thaiChar","120","title_thai"
											,"LoadTitleThaiNew" ,MessageResourceUtil.getTextDescription(request, "TITLE_THAI"))%>
		</td>		
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "SURNAME_THAI",ORIGUser,personalM.getCustomerType(),"MAIN_APPLICANT_SUBFORM","surname_th")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"MAIN_APPLICANT_SUBFORM","surname_th")%>&nbsp;:&nbsp;</td>
		<td class="inputL" ><%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getThaiLastName(), displayMode, "50", "surname_th","textbox-30length thaiChar","","50")%></td>
	</tr>
	<tr> 
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "MID_NAME_THAI",ORIGUser,personalM.getCustomerType(),"MAIN_APPLICANT_SUBFORM","middlename_th")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"MAIN_APPLICANT_SUBFORM","middlename_th")%>&nbsp;:&nbsp;</td>
		<td class="inputL" ><%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getThaiMidName(), displayMode, "60", "middlename_th","textbox thaiChar","","60")%></td>
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "OLD_SURNAME",ORIGUser,personalM.getCustomerType(),"MAIN_APPLICANT_SUBFORM","old_surname_th")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"MAIN_APPLICANT_SUBFORM","old_surname_th")%>&nbsp;:&nbsp;</td>
		<td class="inputL" ><%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getThaiOldLastName(), displayMode, "50", "old_surname_th","textbox-30length thaiChar","","50")%></td>
	</tr>
	<tr> 
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "NAME_ENG",ORIGUser,personalM.getCustomerType(),"MAIN_APPLICANT_SUBFORM","name_eng")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"MAIN_APPLICANT_SUBFORM","name_eng")%>&nbsp;:&nbsp;</td>
		<td class="inputL">		
		<%=HTMLRenderUtil.DisplayPopUpTextBoxDescFieldIDAndTextBox(personalM.getEngTitleName(),4,displayMode,"titleEng","textbox-code","50"
										,personalM.getEngFirstName(),"name_eng","textbox EngChar","120","title_eng"
											,"LoadTitleEngNew" ,MessageResourceUtil.getTextDescription(request, "TITLE_ENG"))%>	
		
		</td>
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "SURNAME_ENG",ORIGUser,personalM.getCustomerType(),"MAIN_APPLICANT_SUBFORM","surname_eng")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"MAIN_APPLICANT_SUBFORM","surname_eng")%>&nbsp;:&nbsp;</td>
		<td class="inputL" ><%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getEngLastName(), displayMode, "50", "surname_eng","textbox-30length EngChar","","50")%></td>
	</tr>
	<tr> 
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "MID_NAME_ENG",ORIGUser,personalM.getCustomerType(),"MAIN_APPLICANT_SUBFORM","middlename_eng")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"MAIN_APPLICANT_SUBFORM","middlename_eng")%>&nbsp;:&nbsp;</td>
		<td class="inputL" ><%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getEngMidName(), displayMode, "60", "middlename_eng","textbox EngChar","","60")%></td>
		<td></td>
		<td></td>
	</tr> 
	<tr> 
		<td class="textR" nowrap="nowrap">
			<%=PLMessageResourceUtil.getTextDescription(request, "BIRTH_DATE_DC",ORIGUser,personalM.getCustomerType(),"MAIN_APPLICANT_SUBFORM","birth_date")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"MAIN_APPLICANT_SUBFORM","birth_date")%>&nbsp;:&nbsp;</td>
		<td class="inputL"  id="element_birth_date">
			<%String birthDate = DataFormatUtility.dateEnToStringDateEn(personalM.getBirthDate(),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S);%>
		<%=HTMLRenderUtil.displayInputTagJsDate("", birthDate, displayMode, "","birth_date","birthDate", "right"," onblur=\"GetAge()\" ","AD")%>
		</td>
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "AGE") %>&nbsp;&nbsp;:&nbsp;</td>
		<td class="inputL" id="element_age" ><%=HTMLRenderUtil.replaceNull(DataFormatUtility.IntToString(personalM.getAge()))%> </td>
	</tr>
	<tr> 
		<td class="textR" nowrap="nowrap">
			<%=PLMessageResourceUtil.getTextDescription(request, "GENDER",ORIGUser,personalM.getCustomerType(),"MAIN_APPLICANT_SUBFORM","gender")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"MAIN_APPLICANT_SUBFORM","gender")%>&nbsp;:&nbsp;
		</td>
		<td class="inputL" id="element_gender"><%=HTMLRenderUtil.displaySelectTagScriptActionBusClassFieldID(applicationM.getBusinessClassId(), 5, personalM.getGender(),"gender",displayMode,"")%></td>
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "EDUCATION",ORIGUser,personalM.getCustomerType(),"MAIN_APPLICANT_SUBFORM","education")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"MAIN_APPLICANT_SUBFORM","education")%>&nbsp;:&nbsp;</td>
		<td class="inputL" id="element_education" ><%=HTMLRenderUtil.displaySelectTagScriptActionBusClassFieldID(applicationM.getBusinessClassId(), 6, personalM.getDegree(),"education",displayMode,"")%></td>
	</tr>
	<tr> 
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "NATIONALITY",ORIGUser,personalM.getCustomerType(),"MAIN_APPLICANT_SUBFORM","nationality")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"MAIN_APPLICANT_SUBFORM","nationality")%>&nbsp;:&nbsp;</td>
		<td class="inputL" id="element_nationality"><%=HTMLRenderUtil.displaySelectTagScriptActionBusClassFieldID(applicationM.getBusinessClassId(), 7, personalM.getNationality(),"nationality",displayMode,"")%></td>
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "RACE",ORIGUser,personalM.getCustomerType(),"MAIN_APPLICANT_SUBFORM","race")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"MAIN_APPLICANT_SUBFORM","race")%>&nbsp;:&nbsp;</td>
		<td class="inputL" id="element_race"><%=HTMLRenderUtil.displaySelectTagScriptActionBusClassFieldID(applicationM.getBusinessClassId(), 8, personalM.getRace(),"race",displayMode,"")%></td>
	</tr>
	<tr> 
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "MARRIAGE_STATUS",ORIGUser,personalM.getCustomerType(),"MAIN_APPLICANT_SUBFORM","marriage_status")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"MAIN_APPLICANT_SUBFORM","marriage_status")%>&nbsp;:&nbsp;</td>
		<td class="inputL" id="element_marriage_status"><%=HTMLRenderUtil.displaySelectTagScriptActionBusClassFieldID(applicationM.getBusinessClassId(), 9, personalM.getMaritalStatus(),"marriage_status",displayMode,"")%></td>
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "NO_OF_CHILDREN",ORIGUser,personalM.getCustomerType(),"MAIN_APPLICANT_SUBFORM","no_of_children")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"MAIN_APPLICANT_SUBFORM","no_of_children")%>&nbsp;:&nbsp;</td>
		<td class="inputL">	
		<%
			BigDecimal noChild = null;
			if(personalM.getNoOfchild() != 0){
				noChild = new BigDecimal(personalM.getNoOfchild());
			}
		%>
		<%=HTMLRenderUtil.DisplayInputNumber(noChild,displayMode,"#0","0","99","no_of_children","textbox-number"," style='width:35px;' ","2",true) %>
		</td>
	</tr>
	<tr> 
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "EMAIL",ORIGUser,personalM.getCustomerType(),"MAIN_APPLICANT_SUBFORM","email1")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"MAIN_APPLICANT_SUBFORM","email1")%>&nbsp;:&nbsp;</td>
		<td class="inputL"><%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getEmail1(), displayMode, "50", "email1","textbox"," onblur=\"checkEmail('email1')\" ","50")%></td>
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "SUB_EMAIL",ORIGUser,personalM.getCustomerType(),"MAIN_APPLICANT_SUBFORM","sub_email")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"MAIN_APPLICANT_SUBFORM","sub_email")%>&nbsp;:&nbsp;</td>
		<td class="inputL"><%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getEmail2(), displayMode, "50", "sub_email","textbox"," onblur=\"checkEmail('sub_email')\" ","50")%></td>
	</tr>		
	
	<tr> 
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "LOAN_OBJECTIVE",ORIGUser,personalM.getCustomerType(),"MAIN_APPLICANT_SUBFORM","other_loan_objective")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"MAIN_APPLICANT_SUBFORM","other_loan_objective")%>&nbsp;:&nbsp;</td>
		<td colspan="3">
			<table>
				<tr>
					<td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displaySelectTagScriptActionBusClassFieldID(applicationM.getBusinessClassId(), 64, personalM.getLoanObjective(),"loan_objective",displayMode,"")%></td>
					<td class="inputL" ><%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getLoanObjectiveDesc(), displayMode, "100", "other_loan_objective", "textbox","","100")%></td>	
				</tr>
			</table>
		</td>
	</tr>
	<tr id="workplace-role">
     <td class="textR" nowrap="nowrap">
	       <%=PLMessageResourceUtil.getTextDescription(request, "WORKPLACE_NAME",ORIGUser,personalM.getCustomerType(),"MAIN_APPLICANT_SUBFORM","occ_workplaceTitle2")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"MAIN_APPLICANT_SUBFORM","birth_date")%>&nbsp;:&nbsp;
     </td>
 <%--#septemwi change to new textbox
        <td class="inputL" nowrap="nowrap" colspan="3">			
			<%=HTMLRenderUtil.DisplayPopUpTextBoxDescFieldIDAndTextBox(personalM.getCompanyTitle(),17,displayMode,"occ_workplaceTitle2","textbox-code","80"
										,personalM.getCompanyName(),"occ_name_th","textbox-b","100","main_workplace_Title"
											,"LoadTitleWorkPlace" ,MessageResourceUtil.getTextDescription(request, "WORKPLACE_NAME"))%>
			
        </td>
 --%>
 	<td class="inputL" nowrap="nowrap" colspan="3">	
		<table>
			<tr>
				<td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displaySelectTagScriptActionBusClassFieldID(applicationM.getBusinessClassId(), 17, personalM.getCompanyTitle(),"main_workplace_Title",displayMode,"")%></td>
				<td class="inputL" ><%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getCompanyName(), displayMode, "100", "occ_name_th", "textbox-b","","100")%></td>	
			</tr>
		</table>
 	</td>
    </tr>
</table>

<%=HTMLRenderUtil.displayHiddenField(personalM.getNationality(),"main_nationality")%>
<%=HTMLRenderUtil.displayHiddenField(displayMode,"main_displaymode")%>
<%=HTMLRenderUtil.displayHiddenField(applicationM.getBusinessClassId(),"main_bussclass")%>
<script>//sankom add for refresh icdc
var REFRESH_ICDC="<%=HTMLRenderUtil.displayHTML(refreshICDC)%>";
</script>