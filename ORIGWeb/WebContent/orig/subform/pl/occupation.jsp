<%@page import="com.eaf.orig.ulo.pl.app.utility.ManualMandatory"%>
<%@page import="com.eaf.orig.ulo.pl.app.utility.ORIGLogic"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil"%>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.DataFormatUtility"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM"%>
<%@ page import="com.eaf.orig.ulo.pl.model.app.PLApplicationDataM"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil"%>

<script type="text/javascript" src="orig/js/subform/pl/occupation.js"></script>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM" />
<jsp:useBean id="PLORIGForm" scope="session" class="com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler" /> 

<% 	  
	  org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger("orig/subform/pl/occupation.jsp");	

	  PLApplicationDataM applicationM = PLORIGForm.getAppForm();
	  PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
// 	  ORIGCacheUtil origc = new ORIGCacheUtil();
	  ORIGFormUtil formUtil = new ORIGFormUtil();
	  
 	  String searchType = (String) request.getSession().getAttribute("searchType");
 	  String displayMode = formUtil.getDisplayMode("OCCUPATION_SUBFORM", ORIGUser.getRoles(), applicationM.getJobState(), PLORIGForm.getFormID(), searchType);
	  String currentRole = ORIGUser.getCurrentRole();
	   	  
//  	  septemwi comment change to ManualMandatory
//  	  String spMandatory = "";
//  	  OrigBusinessClassUtil busclass = new OrigBusinessClassUtil();
//  	  if(!busclass.isContainsBusGroup(applicationM, OrigConstant.BusGroup.KEC_ICDC_FLOW)){
//  	  		if(OrigConstant.ROLE_DC.equals(currentRole) || OrigConstant.ROLE_DC_SUP.equals(currentRole) ||
// 					OrigConstant.ROLE_VC.equals(currentRole) || OrigConstant.ROLE_VC_SUP.equals(currentRole)){
// 				spMandatory = "*";
// 			}
//  	  }
// 	String spMandatory = ORIGLogic.spMandatory(applicationM,currentRole);
	
// 	DISPLAY_MODE occ_occupation_type_text
	String mode_occupation_text = ORIGLogic.DisplayModeOccupationText(displayMode, personalM.getProfession());	
	
// 	DISPLAY_MODE occ_business_type_text
	String mode_business_text = ORIGLogic.DisplayModeBusinessText(displayMode, personalM.getBusinessType());
	
// 	DISPLAY_MODE occ_other_income_text
	String mode_other_income_text = ORIGLogic.DisplayModeSourceIncomeText(displayMode, personalM.getSourceOfIncome());;
%>

<table class="FormFrame">
	<tr height="25">
	<td><fieldset class="field-set"> <legend><%=PLMessageResourceUtil.getTextDescription(request, "PRESENT_WORKPLACE") %></legend>
    	<table class="FormFrame">
        	<tr height="40">
            	<td class="textR" nowrap="nowrap" width="20%">
		        	<%=PLMessageResourceUtil.getTextDescription(request, "OCCUPATION",ORIGUser,personalM.getCustomerType(),"OCCUPATION_SUBFORM","occ_occupation")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"OCCUPATION_SUBFORM","occ_occupation")%>&nbsp;:&nbsp;
                </td>
                <td class="inputL" width="25%"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(15, applicationM.getBusinessClassId(), personalM.getOccupation(),"occ_occupation",displayMode," style='width:130px' ")%></td>
        	    <td class="textR" nowrap="nowrap" width="25%">
	                <%=PLMessageResourceUtil.getTextDescription(request, "OCCUPATION_TYPE",ORIGUser,personalM.getCustomerType(),"OCCUPATION_SUBFORM","occ_occupation_type")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"OCCUPATION_SUBFORM","occ_occupation_type")%>&nbsp;:&nbsp;
                </td>
                <td class="inputL" width="30%" nowrap="nowrap">
                    <%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(16, applicationM.getBusinessClassId(), personalM.getProfession(), "occ_occupation_type",displayMode,"")%>
                    <%=HTMLRenderUtil.displayInputTag(personalM.getProfessionOther(), mode_occupation_text, "25", "occ_occupation_type_text","textbox")%>
                </td>
			</tr>
<%--#septemwi comment remove to main_applicant subform			
          	<tr height="25">
            	<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "WORKPLACE_NAME",ORIGUser,personalM.getCustomerType(),"OCCUPATION_SUBFORM","occ_workplaceTitle2")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"OCCUPATION_SUBFORM","occ_name_th")%>&nbsp;:&nbsp;</td>
                <td class="inputL" nowrap="nowrap">                
                <%=HTMLRenderUtil.DisplayPopUpTextBoxDescFieldIDAndTextBox(personalM.getCompanyTitle(),17,displayMode,"occ_workplaceTitle2","textbox-code","80"
						,personalM.getCompanyName(),"occ_name_th","textbox-b","100","occ_workplace_Title"
							,"LoadTitleWorkPlace" ,MessageResourceUtil.getTextDescription(request, "WORKPLACE_NAME"))%>
                
                </td>                                        
          	    <td class="textR" nowrap="nowrap"><%=MessageResourceUtil.getTextDescription(request, "DEPARTMENT") %>&nbsp;:&nbsp;</td>
         	    <td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getDepartment(), displayMode, "80", "occ_department","textbox","","80")%></td>
			</tr>
--%>			
          	<tr height="25">
				<td class="textR" nowrap="nowrap">
					<%=PLMessageResourceUtil.getTextDescription(request, "POSITION",ORIGUser,personalM.getCustomerType(),"OCCUPATION_SUBFORM","occ_position")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"OCCUPATION_SUBFORM","occ_position")%>&nbsp;:&nbsp;
	            </td>
	            <td class="inputL" nowrap="nowrap">
	            	<%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getPositionDesc(), displayMode, "100", "occ_position","textbox","","100")%></td>
	            <td class="textColS" nowrap="nowrap">
					<%=PLMessageResourceUtil.getTextDescription(request, "POSITION_LEVEL",ORIGUser,personalM.getCustomerType(),"OCCUPATION_SUBFORM","occ_position_level")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"OCCUPATION_SUBFORM","occ_position_level")%>&nbsp;:&nbsp;
	            </td>
	            <td class="inputL" width="33%">
	                <%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(77, applicationM.getBusinessClassId(), personalM.getPositionLevel(), "occ_position_level",displayMode," style='width:60%' ")%>
	            </td>
			</tr>
			<tr height="25">
               <td class="textR" nowrap="nowrap">
					<%=PLMessageResourceUtil.getTextDescription(request, "YEAR_OF_SERVICE",ORIGUser,personalM.getCustomerType(),"OCCUPATION_SUBFORM","occ_service_years")%>
					<%=ORIGLogic.DisplayMandatoryYearOfService(applicationM.getBusinessClassId(),currentRole)%>
					&nbsp;:&nbsp;
               </td>
	           <td class="inputL" nowrap="nowrap">
		            <%=HTMLRenderUtil.displayInputTagScriptAction(DataFormatUtility.IntToString(personalM.getCurrentWorkYear()), displayMode, "2", "occ_service_years","textboxCode"," onkeypress= \"return keyPressInteger(event)\" ","2")%>
                    <%=PLMessageResourceUtil.getTextDescription(request, "YEAR") %>&nbsp;
                    <%=HTMLRenderUtil.displayInputTagScriptAction(DataFormatUtility.IntToString(personalM.getCurrentWorkMonth()), displayMode, "2", "occ_service_month","textboxCode"," onkeypress= \"return keyPressInteger(event)\" ","2")%>&nbsp;
                    <%=PLMessageResourceUtil.getTextDescription(request, "MONTH") %>
		       </td>
	           <td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "AVAILABLE_TIME") %>&nbsp;:&nbsp;</td>
	           <td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getContactTime(), displayMode, "50", "occ_available_time","textbox","","50")%></td>
	     	</tr>
			<tr height="25">
				<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "BUSINESS_GROUP") %>&nbsp;:&nbsp;</td>
         		<td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(19, applicationM.getBusinessClassId(), personalM.getBusNature(), "occ_business_group",displayMode,"")%></td>
            	<td class="textR" nowrap="nowrap">
            		<%=PLMessageResourceUtil.getTextDescription(request, "BUSINESS_TYPE", ORIGUser, personalM.getCustomerType(), "OCCUPATION_SUBFORM", "occ_business_type")%>&nbsp;:&nbsp;</td>
            	<td class="inputL" nowrap="nowrap">
              		 <%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(18, applicationM.getBusinessClassId(),personalM.getBusinessType(), "occ_business_type", displayMode,"")%>
              		 <%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getBusTypeOther(), mode_business_text, "50", "occ_business_type_text", "textbox","","50")%>
				</td>
			</tr>
			<tr>
				<td class="textR" nowrap="nowrap"> <%=PLMessageResourceUtil.getTextDescription(request, "TYPE_OF_COMPANY", ORIGUser, personalM.getCustomerType(), "OCCUPATION_SUBFORM", "occ_type_of_company")%>&nbsp;:&nbsp;</td>
				<td><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(20, applicationM.getBusinessClassId(),personalM.getCompanyType(), "occ_type_of_company", displayMode, " style='width:65%' ")%></td>
				<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "SIZE_OF_COMPANY") %>&nbsp;:&nbsp;</td>
				<td><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(21, applicationM.getBusinessClassId(), personalM.getBusinessSize(), "occ_size_of_company", displayMode, " style='width:65%' ")%></td>
			</tr>
			<tr>
				<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "EMPLOYMENT_STYLE") %>&nbsp;:&nbsp;</td>
				<td><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(81, applicationM.getBusinessClassId(), personalM.getEmploymentStatus(), "occ_emp_style", displayMode, " style='width:65%' ")%></td>				
			</tr>
										
   	</table>
</fieldset></td>
</tr>
<tr height="25">
<td><fieldset class="field-set"><legend><%=PLMessageResourceUtil.getTextDescription(request, "OLD_WORKPLACE") %></legend>
     <table class="FormFrame">
       <tr height="40">
         <td class="textR" nowrap="nowrap" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "OLD_WORKPLACE_NAME") %>&nbsp;:&nbsp;</td>
<%-- 
         <td class="inputL" nowrap="nowrap" width="25%">                 
         <%=HTMLRenderUtil.DisplayPopUpTextBoxDescFieldIDAndTextBox(personalM.getPrevCompanyTitle(),17,displayMode,"occ_old_workplaceTitle","textbox-code","80"
						,personalM.getPrevCompany(),"occ_old_workplacename","textbox-b","100","occ_old_workplace_Title"
							,"LoadTitleWorkPlace" ,MessageResourceUtil.getTextDescription(request, "WORKPLACE_NAME"))%>
         </td>
--%>
         <td class="inputL" nowrap="nowrap" width="25%">	
			<table>
				<tr>
					<td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displaySelectTagScriptActionBusClassFieldID(applicationM.getBusinessClassId(), 17, personalM.getPrevCompanyTitle(),"occ_old_workplace_Title",displayMode,"")%></td>
					<td class="inputL" ><%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getPrevCompany(), displayMode, "100", "occ_old_workplacename", "textbox-b","","100")%></td>	
				</tr>
			</table>
 		 </td>
         <td class="textR" nowrap="nowrap"  width="25%"><%=PLMessageResourceUtil.getTextDescription(request, "OLD_WORKPLACE_TEL") %>&nbsp;:&nbsp;</td>
         <td class="inputL" nowrap="nowrap"  width="30%">
         		<%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getPrevCompanyPhoneNo(), displayMode, "9", "occ_old_workplace_tel","textbox"," onkeypress= \"return keyPressInteger(event)\" ","9")%>
       	 </td>
       </tr>
       <tr height="25">
         <td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "OLD_YEAR_OF_SERVICE") %>&nbsp;:&nbsp;</td>
         <td class="inputL" nowrap="nowrap">
               <%=HTMLRenderUtil.displayInputTagScriptAction(DataFormatUtility.IntToString(personalM.getPrevWorkYear()), displayMode, "2", "occ_old_service_years","textboxCode"," onkeypress= \"return keyPressInteger(event)\" ","2")%>&nbsp;
               <%=PLMessageResourceUtil.getTextDescription(request, "YEAR") %>&nbsp;
               <%=HTMLRenderUtil.displayInputTagScriptAction(DataFormatUtility.IntToString(personalM.getPrevWorkMonth()), displayMode, "2", "occ_old_service_month","textboxCode"," onkeypress= \"return keyPressInteger(event)\" ","2")%>&nbsp;
               <%=PLMessageResourceUtil.getTextDescription(request, "MONTH") %>
         </td>
         <td class="inputL">&nbsp;</td>
       </tr>
     </table>
</fieldset></td>
</tr> 
<tr height="25"><td><fieldset class="field-set"><legend><%=PLMessageResourceUtil.getTextDescription(request, "SALARY") %></legend>
<table class="FormFrame" id="radio_table">
	  <tr height="40">
	    <td class="textR" nowrap="nowrap" width="20%">
			<%=PLMessageResourceUtil.getTextDescription(request, "PROFESSION_TYPE_OCC",ORIGUser,personalM.getCustomerType(),"OCCUPATION_SUBFORM","applicant_radio")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"OCCUPATION_SUBFORM","applicant_radio")%>&nbsp;:&nbsp;
		</td>
		<td class="inputL" nowrap="nowrap" width="25%"><%=HTMLRenderUtil.displayRadioTagScriptAction("01", displayMode, "applicant_radio", personalM.getApplicationIncomeType(), "", "", "") %>
			<%=PLMessageResourceUtil.getTextDescription(request, "REGULAR_SALARY") %></td>
		<td class="textR" nowrap="nowrap" width="25%" id="applicant_radio_01">
			<%=PLMessageResourceUtil.getTextDescription(request, "SALARY_MONTH",ORIGUser,personalM.getCustomerType(),"OCCUPATION_SUBFORM","occ_salary_month")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"OCCUPATION_SUBFORM","occ_salary_month")%><span id="man_radio_01"></span>&nbsp;:&nbsp;
		</td>	
		<td class="inputL" nowrap="nowrap" width="30%">
			<%=HTMLRenderUtil.DisplayInputCurrency(personalM.getSalary(), displayMode, "########0.00", "occ_salary_month","textbox-currency applicant_radio_01 salaryInput","","12",false)%>
	  	</td>
	 </tr>
	 <tr height="40">
	  	<td nowrap="nowrap">&nbsp;</td>
	  	<td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.displayRadioTagScriptAction("02", displayMode, "applicant_radio", personalM.getApplicationIncomeType(), "", "", "") %>
			<%=PLMessageResourceUtil.getTextDescription(request, "OWNER_BUSINESS") %></td>
		<td class="textR" nowrap="nowrap" id="applicant_radio_02">
			<%=PLMessageResourceUtil.getTextDescription(request, "BUSINESS_TURNOVER",ORIGUser,personalM.getCustomerType(),"OCCUPATION_SUBFORM","occ_business_turnover")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"OCCUPATION_SUBFORM","occ_business_turnover")%><span id="man_radio_02"></span>&nbsp;:&nbsp;
		</td>	
		<td class="inputL" nowrap="nowrap">
			<%=HTMLRenderUtil.DisplayInputCurrency(personalM.getCirculationIncome(), displayMode, "########0.00", "occ_business_turnover","textbox-currency applicant_radio_02 salaryInput","","12",false)%>
	  	</td>
	 </tr>
	 <tr height="40">
	  	 <td nowrap="nowrap">&nbsp;</td>	   
	    <td class="textR" nowrap="nowrap" colspan="2" id="applicant_radio_02_1">
	    	<%=PLMessageResourceUtil.getTextDescription(request, "BUSINESS_NET_PROFIT",ORIGUser,personalM.getCustomerType(),"OCCUPATION_SUBFORM","occ_business_net_profit")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"OCCUPATION_SUBFORM","occ_business_net_profit")%><span id="man_radio_02-1"></span>&nbsp;:&nbsp;
		</td>	
		<td class="inputL" nowrap="nowrap">
			<%=HTMLRenderUtil.DisplayInputCurrency(personalM.getNetProfitIncome(), displayMode, "########0.00", "occ_business_net_profit","textbox-currency applicant_radio_02_1 salaryInput","","12",false)%>
	 	</td>
	 </tr>   
	 <tr height="40">
	   <td nowrap="nowrap">&nbsp;</td>
	   <td class="inputL" nowrap="nowrap">
	  		<%=HTMLRenderUtil.displayRadioTagScriptAction("03", displayMode, "applicant_radio", personalM.getApplicationIncomeType(), "", "", "") %><%=MessageResourceUtil.getTextDescription(request, "FREELANCE") %>
	   </td>
	  <td class="textR" nowrap="nowrap" id="applicant_radio_03">
	  	<%=PLMessageResourceUtil.getTextDescription(request, "SALARY_PER_MONTH") %><span id="man_radio_03"></span>&nbsp;:&nbsp;
	  </td>	
	  <td class="inputL" nowrap="nowrap">
		<%=HTMLRenderUtil.DisplayInputCurrency(personalM.getFreelanceIncome(), displayMode, "########0.00", "occ_other_income_per_month","textbox-currency applicant_radio_03 salaryInput","","12",false)%>
	  </td>
	</tr>
	<tr height="40">
		<td nowrap="nowrap">&nbsp;</td>
		<td class="inputL" nowrap="nowrap">
			<%=HTMLRenderUtil.displayRadioTagScriptAction("05", displayMode, "applicant_radio", personalM.getApplicationIncomeType(), "", "", "") %><%=PLMessageResourceUtil.getTextDescription(request, "SAVINGS") %></td>
		<td class="textR" nowrap="nowrap" id="applicant_radio_05">
			<%=PLMessageResourceUtil.getTextDescription(request, "SAVINGS",ORIGUser,personalM.getCustomerType(),"OCCUPATION_SUBFORM","occ_savings")%><%=HTMLRenderUtil.getMandatory_ORIG(personalM.getCustomerType(),ORIGUser.getRoles(),"OCCUPATION_SUBFORM","occ_savings")%><span id="man_radio_05"></span>&nbsp;:&nbsp;
		</td>
		<td class="inputL" nowrap="nowrap">
		      <%=HTMLRenderUtil.DisplayInputCurrency(personalM.getSavingIncome(), displayMode, "########0.00", "occ_savings","textbox-currency applicant_radio_05 salaryInput","","12",false)%>
				<img src="images/data2.png" onclick="javascript:LoadSavingPopup('LoadPLSavingPopup','<%=displayMode%>')" onmouseover="style.cursor='pointer';" />                                       		     
		</td>
	</tr>
    <tr height="40">
		<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "OTHER_INCOME_PER_MONTH") %>&nbsp;:&nbsp;</td>
		<td class="inputL" nowrap="nowrap">
			<%=HTMLRenderUtil.DisplayInputCurrency(personalM.getOtherIncome(), displayMode, "########0.00", "occ_other_income_per_month2","textbox-currency","","12",false)%>
		</td>
		<td class="textR" nowrap="nowrap" id="other_income_label"><%=PLMessageResourceUtil.getTextDescription(request, "SOURCE_OTHER_INCOME") %>&nbsp;:&nbsp;</td>
		<td class="inputL" nowrap="nowrap">
			<%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(23, applicationM.getBusinessClassId(), personalM.getSourceOfIncome(), "occ_source_other_income",displayMode,"")%>
			&nbsp;
			<%=HTMLRenderUtil.displayInputTagScriptAction(personalM.getSourceOfOtherIncome(), mode_other_income_text, "50", "occ_other_income_text","textbox","","50")%>
		</td>	
	</tr>
	<tr height="40">
			<td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "ASSET_VALUE") %>&nbsp;:&nbsp;</td>
			<td class="inputL" nowrap="nowrap"><%=HTMLRenderUtil.DisplayInputCurrency(personalM.getAssetAmount(), displayMode, "########0.00", "asset_amount","textbox-currency","","12",false)%></td>
			<td class="textR" nowrap="nowrap">&nbsp;</td>
			<td class="inputL" nowrap="nowrap">&nbsp;</td>
	 </tr>
</table>
</fieldset></td>
</tr>

<tr height="40">
  <td><fieldset class="field-set"><legend><%=PLMessageResourceUtil.getTextDescription(request, "PROCESS_INCOME") %></legend>
    <table class="FormFrame">
      <tr height="40">
        <td class="textR" nowrap="nowrap" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "PROCESS_INCOME") %>&nbsp;:&nbsp;</td>
        <td class="inputL" nowrap="nowrap" width="25%"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(24, applicationM.getBusinessClassId(), personalM.getReceiveIncomeMethod(), "occ_process_income",displayMode," style='width:230px' ")%>
        </td>
        <td class="textR" nowrap="nowrap" width="25%"><%=PLMessageResourceUtil.getTextDescription(request, "BANK_NAME") %>&nbsp;:&nbsp;</td>
        <td class="inputL" nowrap="nowrap" width="30%"><%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(25, applicationM.getBusinessClassId(), personalM.getReceiveIncomeBank(), "occ_bank_name",displayMode," style='width:250px' ")%></td>
      </tr>
      <tr height="25">
        <td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "EXPENDITURE_MONTH") %>&nbsp;:&nbsp;</td>
        <td class="inputL" nowrap="nowrap">
        	<%=HTMLRenderUtil.DisplayInputCurrency(personalM.getMonthlyIncome(), displayMode, "########0.00", "occ_expenditure_month","textbox-currency","","12",false)%>
        </td>
        <td class="textR" nowrap="nowrap">&nbsp;</td>
        <td class="inputL" nowrap="nowrap">&nbsp;</td>
      </tr>
    </table>
    </fieldset></td>
</tr>

<tr height="40">
  <td><fieldset class="field-set"><legend><%=PLMessageResourceUtil.getTextDescription(request, "FUNDING") %></legend>
    <table class="FormFrame">
      <tr height="40">
        <td class="textR" nowrap="nowrap" width="20%"><%=PLMessageResourceUtil.getTextDescription(request, "WEALTH_FROM_KBANK") %>&nbsp;:&nbsp;</td>
        <td class="inputL" nowrap="nowrap" width="25%">
       		<%=HTMLRenderUtil.DisplayInputCurrency(personalM.getWealthKBank(), displayMode, "########0.00", "occ_wealth_bank","textbox-currency","onblur=\"calSummaryWealth()\"","12",false)%>
        </td>
        <td class="textR" nowrap="nowrap" width="25%"><%=PLMessageResourceUtil.getTextDescription(request, "WEALTH_FROM_EXT_BANK") %>&nbsp;:&nbsp;</td>
        <td class="inputL" nowrap="nowrap" width="30%">
        	<%=HTMLRenderUtil.DisplayInputCurrency(personalM.getWealthNonKBank(), displayMode, "########0.00", "occ_wealth_ext_bank","textbox-currency","onblur=\"calSummaryWealth()\"","12",false)%>
        </td>
      </tr>
      <tr height="25">
        <td class="textR" nowrap="nowrap"><%=PLMessageResourceUtil.getTextDescription(request, "WEALTH_SUMMARY") %>&nbsp;:&nbsp;</td>
        <td class="inputL" nowrap="nowrap">
        	<%=HTMLRenderUtil.DisplayInputCurrency(personalM.getSummaryWealth(), HTMLRenderUtil.DISPLAY_MODE_VIEW, "########0.00", "occ_summary_wealth","textbox-currency","","12",false)%>
        </td>
        <td class="textR" nowrap="nowrap">&nbsp;</td>
        <td class="inputL" nowrap="nowrap"></td>
      </tr>
    </table>
    </fieldset></td>
</tr>

<tr height="40">
  <td>
  	<fieldset class="field-set"><legend><%=PLMessageResourceUtil.getTextDescription(request, "SP") %></legend>
	    <table class="FormFrame">
	      <tr height="40">
	        <td class="textR" nowrap="nowrap" width="20%">
	        	<%=PLMessageResourceUtil.getTextDescription(request, "SP_FLAG") %>
	        	<%=ManualMandatory.getManadatory("OCCUPATION_SUBFORM","occ_sp_flag",request)%>&nbsp;:&nbsp;</td>
	        <td class="inputL" nowrap="nowrap" width="25%">
	       	 	<%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(89, applicationM.getBusinessClassId(), personalM.getSpFlag(), "occ_sp_flag",displayMode,"")%>
	        </td>
	        <td class="textR" nowrap="nowrap" width="25%">
	       		<%=PLMessageResourceUtil.getTextDescription(request, "SOLO_VIEW",ORIGUser,personalM.getCustomerType(),"OCCUPATION_SUBFORM","solo_view")%>
	        	<%=ManualMandatory.getManadatory("OCCUPATION_SUBFORM","solo_view",request)%>&nbsp;:&nbsp;
	        </td>
	        <td class="inputL" nowrap="nowrap" width="30%">
	        	<%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(131, applicationM.getBusinessClassId(), personalM.getSoloView(), "solo_view",displayMode,"")%>
	        </td>
	      </tr>
	      <tr height="40">
	        <td class="textR" nowrap="nowrap" width="20%">
	        	<%=PLMessageResourceUtil.getTextDescription(request, "LEC",ORIGUser,personalM.getCustomerType(),"OCCUPATION_SUBFORM","lec")%>
	        	<%=ManualMandatory.getManadatory("OCCUPATION_SUBFORM","lec",request)%>&nbsp;:&nbsp;
	        </td>
	        <td class="inputL" nowrap="nowrap" width="25%">
	       	 	<%=HTMLRenderUtil.displaySelectTagScriptAction_ORIG(132, applicationM.getBusinessClassId(), personalM.getLec(), "lec",displayMode,"")%>
	        </td>
	        <td class="textR" nowrap="nowrap" width="25%"></td>
	        <td class="inputL" nowrap="nowrap" width="30%"></td>
	      </tr>
	    </table>
    </fieldset>
   </td>
</tr>
</table>

<%=HTMLRenderUtil.displayHiddenField(personalM.getProfession(),"professionCode")%>
<%=HTMLRenderUtil.displayHiddenField(ORIGUser.getCurrentRole(),"occ_workplace_role")%>
<%=HTMLRenderUtil.displayHiddenField(personalM.getReceiveIncomeMethod(),"other_source_choise")%>
<%=HTMLRenderUtil.displayHiddenField(displayMode,"occ_displayMode")%>
<%=HTMLRenderUtil.displayHiddenField(personalM.getApplicationIncomeType(),"occ_incomeType")%>
