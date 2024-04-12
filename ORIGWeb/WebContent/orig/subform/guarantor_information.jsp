<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil"%>
<%@ page import="com.eaf.orig.wf.shared.ORIGWFConstant" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("GUARANTOR_INFO_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	

//	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	ORIGUtility utility = new ORIGUtility();
	Vector customerTypeVect = utility.loadCacheByName("CustomerType");
	Vector guarantorVect = utility.getVectorPersonalInfoByType(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_GUARANTOR);
	String maritalStatus; 
	//Vector personaTypeVect=new Vector();	
	String personalType = (String) request.getSession().getAttribute("PersonalType");
	PersonalInfoDataM prmPersonalInfoDataM;
	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
		prmPersonalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
	}else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
		prmPersonalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
	}else{
		prmPersonalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
	}
	if(prmPersonalInfoDataM == null){
		prmPersonalInfoDataM = new PersonalInfoDataM();		
	}
  String roles=(String)ORIGUser.getRoles().get(0);
%>

<table cellpadding="" cellspacing="0" width="100%" align="center" bgcolor="#FFFFFF">
	<tr> 
		<td colspan="5">
			<div id="KLTable">
				<div id="Guarantor">
					<table class="gumayframe3" cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
						<tr><td class="TableHeader">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
								<tr>
									<td class="Bigtodotext3" align="center" width="5%"></td>
									<td class="Bigtodotext3" align="center" width="5%"><%=MessageResourceUtil.getTextDescription(request, "SEQ") %></td>
									<td class="Bigtodotext3" align="center" width="30%"><%=MessageResourceUtil.getTextDescription(request, "NAME") %></td>
									<td class="Bigtodotext3" align="center" width="15%"><%=MessageResourceUtil.getTextDescription(request, "STATUS") %></td>							 
									<td class="Bigtodotext3" align="center" width="15%"><%=MessageResourceUtil.getTextDescription(request, "GUARANTOR_FLAG") %></td>
								</tr>
							</table> 
						</td></tr>
					<% if(guarantorVect != null && guarantorVect.size() > 0){
						PersonalInfoDataM personalInfoDataM;
						String titleName;
						
						for(int i=0; i<guarantorVect.size(); i++){
							personalInfoDataM = (PersonalInfoDataM) guarantorVect.get(i);
							titleName = cacheUtil.getORIGMasterDisplayNameDataM("Title", personalInfoDataM.getThaiTitleName());
							maritalStatus = cacheUtil.getORIGMasterDisplayNameDataM("MaritalStatus", personalInfoDataM.getMaritalStatus());
							String disabledChk ="";
							if(ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW.equals(displayMode) ||(OrigConstant.COBORROWER_FLAG_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag())||OrigConstant.COBORROWER_FLAG_UN_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag()))){							
								disabledChk = " disabled";
							}
					%>
						<tr><td align="center" class="gumaygrey2">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
							<tr>
								<td class="jobopening2" width="5%" align="center"><%=ORIGDisplayFormatUtil.displayCheckBox("delete","guarantorSeq",String.valueOf(personalInfoDataM.getPersonalSeq()),disabledChk) %></td>
								<td class="jobopening2" width="5%" align="center"><%=personalInfoDataM.getPersonalSeq() %></td>
								<td class="jobopening2" width="30%"><a href="javascript:loadPopup('guarantor','LoadGuarantorPopup','1150','500','150','40','<%=personalInfoDataM.getPersonalSeq() %>','<%=personalInfoDataM.getCustomerType() %>','')"><u><%=titleName + personalInfoDataM.getThaiFirstName() +" "+ ORIGDisplayFormatUtil.displayHTML(personalInfoDataM.getThaiLastName())%></u></a></td>
								<td class="jobopening2" width="15%"><%=maritalStatus %></td>
								<% String flag="";
								if(OrigConstant.COBORROWER_FLAG_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag())){
           	                     flag="Co-Borrower  Active ";
          	  
           	                   } else if(OrigConstant.COBORROWER_FLAG_UN_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag())){           	                        
                   	              flag="Co-Borrower  Un Active ";                            	
                            	} else if(OrigConstant.COBORROWER_FLAG_NO.equals(personalInfoDataM.getCoborrowerFlag())|| personalInfoDataM.getCoborrowerFlag()==null){
                            	   flag="Guarantor ";
                            	   if(OrigConstant.ORIG_FLAG_Y.equals(personalInfoDataM.getDebtIncludeFlag())){
                            	     flag="Inclued debt";
                            	   }else{
                            	     flag="not Inculde debt";
                            	   }
                            	}
                            	%> 
								<td class="jobopening2" width="15%"><%=flag%></td>
							</tr>
							</table> 
						</td></tr>
					<%  }
					   }else{%>
						<tr><td align="center" class="gumaygrey2">
							<table cellpadding="0" cellspacing="0" width="100%" align="center" border="0">
							<tr>
					  			<td class="jobopening2" colspan="8" align="center">No Record</td>
					  		</tr>
							</table> 
						</td></tr>
					<% }%>
					</table>
				</div>
			</div>
		</td>
	</tr>
	<tr><td height="20"></td></tr>
	<%if(displayMode.equals(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT)){ %>
		<tr>
			<td align="right" width="60%">
				<SELECT name="coborrowerFlag" class="combobox" >
					<OPTION  value="N">Guarantor</OPTION>
					<%if(OrigConstant.ORIG_FLAG_Y.equals(utility.getGeneralParamByCode("COBORROWER_FLAG")) ){%>
					<OPTION value="Y">Co Borrower</OPTION>
					<%} %>
				</SELECT>
			</td>
			<td class="textColS" align="right" width="22%"><%=MessageResourceUtil.getTextDescription(request, "CUSTOMER_TYPE") %> :<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(customerTypeVect,"","type",displayMode," onChange=\"validateType()\"")%></td>
			<td align="right" width="8%"><INPUT type="button" name="addGuarantorBnt" value=" <%=MessageResourceUtil.getTextDescription(request, "ADD") %> " onClick="javascript:addGuarantor()" class="button_text">
			</td><td align="right" width="8%"><INPUT type="button" name="deleteGuarantorBnt" value=" <%=MessageResourceUtil.getTextDescription(request, "DELETE") %> " onClick="javascript:deleteTableList('guarantorSeq','Guarantor','DeleteGuarantorServlet')" class="button_text"></td>
			<td width="2%"></td>
		</tr>
	<%} %>
</table>
<script language="JavaScript">
function addGuarantor(){
    if( document.appFormName.identification_no.value==''){
      alert("Please Input Identification No.");
      return;
    } 
    if(document.appFormName.coborrowerFlag.value=='Y'&& document.appFormName.application_no.value=='' ){
      alert("Please Save Draft before Add Co-Borrower");
      return;
    }  
    //if( document.appFormName.area_code.value==''){
    //  alert("Please Input Area Code");
    //  return;
    //} 
    <% if (ORIGWFConstant.JobState.CMR_DRAFT_STATE.equalsIgnoreCase(applicationDataM.getJobState())) { %>
    if( document.appFormName.pre_score_mkt_code.value==''    ){
       alert("Please Input Marketing Code");
       return;
      }
    <%}%>          
	if(  checkFieldBeforeDoAction('type', '<%=MessageResourceUtil.getTextDescription(request, "CUSTOMER_TYPE") %>')){
		loadPopup('guarantor','LoadGuarantorPopup','1150','500','150','40','0','')
	}
}
/*function setCustomerType(){
    // alert('test');
     //document.appFormName.type.diabled=true;
     //document.appFormName.type.selectedIndex=2;
     if(document.appFormName.coborrowerFlag.value=='Y'){
     document.appFormName.type.value='<%=prmPersonalInfoDataM.getCustomerType()%>'
     document.appFormName.type.disabled=true;
     }else{
     document.appFormName.type.value=''
     document.appFormName.type.disabled=false;
     }
}*/

function validateType(){
     //  alert("test Validate Type");
     if(document.appFormName.coborrowerFlag.value=='Y'){
       var guarantorCustType=document.appFormName.type.value;
       var customerType='<%=prmPersonalInfoDataM.getCustomerType()%>';
       if( (customerType=='01' ||  customerType=='03') && (guarantorCustType=='01'  || guarantorCustType=='03') ){
        
       }else if(customerType=='02'&& guarantorCustType=='02'  ) {
          
       }else{
         alert("Customer Type not Match");
         appFormName.type.value="";
       } 
     
   }
}
</script>