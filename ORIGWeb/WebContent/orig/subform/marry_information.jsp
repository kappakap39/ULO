<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.cache.properties.TitleProperties"%>
<%@ page import="com.eaf.orig.cache.properties.TitleEngProperties"%>


<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("MARRY_INFO_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	

	MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
//	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	
	//Vector titleThaiVect = utility.loadCacheByName("Title");
	//Vector titleEngVect = utility.loadCacheByName("TitleEng");
	//Vector occupationVect = utility.loadCacheByName("Occupation");
	Vector positionVect = new Vector();
	Vector genderVect = utility.getMasterDataFormCache("GENDERCDE");
	//Vector cardTypeVect = utility.loadCacheByName("CustomerIdentify");	
	
	PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
	if(personalInfoDataM == null){
		personalInfoDataM = new PersonalInfoDataM();
	}
	String titleEng = cacheUtil.getORIGMasterDisplayNameDataM("TitleEng", personalInfoDataM.getMEngTitleName());
	String titleThai = cacheUtil.getORIGMasterDisplayNameDataM("Title", personalInfoDataM.getMThaiTitleName());
	String positionSelect = personalInfoDataM.getPosition();
	Vector occupationVect = utility.loadCacheByNameAndCheckStatus("Occupation", personalInfoDataM.getOccupation());
	Vector cardTypeVect = utility.loadCacheByNameAndCheckStatus("CustomerIdentify", personalInfoDataM.getCardIdentity());
	//String disablePosition = "disabled";
	if(!ORIGUtility.isEmptyString(personalInfoDataM.getMOccupation())){
		positionVect = cacheUtil.getPositionByOccupation(personalInfoDataM.getMOccupation());
		//disablePosition = "";
	}
	
	Vector<TitleEngProperties> engTitles = utility.loadCacheByNameAndCheckStatus(OrigConstant.CacheName.CACHE_NAME_TITLE_ENG, "Dummy");
	Vector<TitleProperties> thaiTitles = utility.loadCacheByNameAndCheckStatus(OrigConstant.CacheName.CACHE_NAME_TITLE_THAI, "Dummy");
%>

<table cellpadding="" cellspacing="1" width="80%" align="left">
	<tr>
		<td class="textColS" id="marital1M" style="display:none"><%=msgUtil.getTextDescription(request, "CARD_TYPE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MARRY_INFO_SUBFORM","m_card_type")%></Font> :</td>
		<td class="textColS" id="marital1" style="display:"><%=msgUtil.getTextDescription(request, "CARD_TYPE") %> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(cardTypeVect,personalInfoDataM.getMCardType(),"m_card_type",displayMode,"onChange=\"javascript:clearDataWhenChangeField('m_card_type','m_id_no')\" style=\"width:98%;\" ") %></td>
		<td class="textColS" id="marital2M" style="display:none">&nbsp;&nbsp;&nbsp;<%=msgUtil.getTextDescription(request, "ID_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MARRY_INFO_SUBFORM","m_id_no")%></Font> :</td>
		<td class="textColS" id="marital2" style="display:">&nbsp;&nbsp;&nbsp;<%=msgUtil.getTextDescription(request, "ID_NO") %> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getMIDNo(),displayMode,"","m_id_no","textbox","onblur=\"javascript:checkCardType('m_card_type','m_id_no')\" style=\"width:50%;\"","50") %></td>
	</tr>
	<tr>
		<td class="textColS" id="marital3M" style="display:none"><%=msgUtil.getTextDescription(request, "NAME_THAI") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MARRY_INFO_SUBFORM","m_title_thai_desc")%></Font> :</td>
		<td class="textColS" id="marital3" style="display:"><%=msgUtil.getTextDescription(request, "NAME_THAI") %> :</td>
		<td class="inputCol" colspan="2">
			<table  cellpadding="0" cellspacing="0" width="100%" align="left">
			<tr><td width='170'>
				<%=ORIGDisplayFormatUtil.displayPopUpOneTextBoxTagScriptAction(titleThai,displayMode,"7","m_title_thai_desc","textbox","","","...","button_text","LoadTitleThai","m_title_thai","Title","m_title_eng_desc","m_title_eng") %>
			</td><td width='140'>
				<input type="hidden" name="m_title_thai" value="<%=ORIGDisplayFormatUtil.displayHTML(personalInfoDataM.getMThaiTitleName()) %>">
				<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getMThaiFirstName(),displayMode,"","m_name_thai","textbox","onblur=\"javascript:checkThaiFName('m_name_thai')\"","60") %>
			</td><td width='140'>
				<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getMThaiLastName(),displayMode,"","m_surname_thai","textbox","onblur=\"javascript:checkThaiLName('m_surname_thai')\"","50") %></td>
			<td>&nbsp;</td>
			</tr>
			</table>		
		</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td class="textColS" id="marital4M" style="display:none"><%=msgUtil.getTextDescription(request, "NAME_ENG") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MARRY_INFO_SUBFORM","m_name_eng")%></Font> :</td>
		<td class="textColS" id="marital4" style="display:"><%=msgUtil.getTextDescription(request, "NAME_ENG") %> :</td>
		<td class="inputCol" colspan="2">
			<table  cellpadding="0" cellspacing="0" width="100%" align="left">
			<tr><td width='170'>
				<%=ORIGDisplayFormatUtil.displayPopUpOneTextBoxTagScriptAction(titleEng,displayMode,"7","m_title_eng_desc","textbox","","","...","button_text","LoadTitleEng","m_title_eng","TitleEng","m_title_thai_desc","m_title_thai") %>
			</td><td width='140'>
				<input type="hidden" name="m_title_eng" value="<%=ORIGDisplayFormatUtil.displayHTML(personalInfoDataM.getMEngTitleName()) %>">
				<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getMEngFirstName(),displayMode,"","m_name_eng","textbox","onblur=\"javascript:checkEngFName('m_name_eng')\"","30") %>
			</td><td width='140'>
				<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getMEngLastName(),displayMode,"","m_surname_eng","textbox","onblur=\"javascript:checkEngLName('m_surname_eng')\"","50") %></td>
			<td>&nbsp;</td>
			</tr>
			</table>		
		</td>
		<td>&nbsp;</td>
	</tr>
	<% 
		
		String sDate = "";
		if(personalInfoDataM.getMBirthDate()==null){
			//sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
		}else{
			sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(personalInfoDataM.getMBirthDate()));
		}
		
	%>
	<tr>
		<td class="textColS" width="10%" id="marital5M" style="display:none"><%=msgUtil.getTextDescription(request, "BIRTH_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MARRY_INFO_SUBFORM","m_birth_date")%></Font> :</td>
		<td class="textColS" width="10%" id="marital5" style="display:"><%=msgUtil.getTextDescription(request, "BIRTH_DATE") %> :</td>
		<td class="inputCol" width="20%"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"21","m_birth_date","textbox","right","onblur=\"javascript:checkBirthDate('m_birth_date','m_age')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\" ") %></td>
		<td class="textColS" width="20%" id="marital6M" style="display:none">&nbsp;&nbsp;&nbsp;<%=msgUtil.getTextDescription(request, "INCOME") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MARRY_INFO_SUBFORM","m_income")%></Font> :</td>
		<td class="textColS" width="20%" id="marital6" style="display:">&nbsp;&nbsp;&nbsp;<%=msgUtil.getTextDescription(request, "INCOME") %> :</td>
		<td class="inputCol" width="20%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(personalInfoDataM.getMIncome()),displayMode,"","m_income","textboxCurrency","onblur=\"javascript:chcekMaxDecimalValue(this);addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:50%;\"","16") %></td>
	</tr>
	<tr>
		<td class="textColS" id="marital7M" style="display:none"><%=msgUtil.getTextDescription(request, "AGE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MARRY_INFO_SUBFORM","m_age")%></Font> :</td>
		<td class="textColS" id="marital7" style="display:"><%=msgUtil.getTextDescription(request, "AGE") %> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayInteger(personalInfoDataM.getMAge()),displayMode,"","m_age","textboxReadOnly","readOnly","style=\"width:30%;\"") %></td>
		<td class="textColS" id="marital8M" style="display:none">&nbsp;&nbsp;&nbsp;<%=msgUtil.getTextDescription(request, "GENDER") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MARRY_INFO_SUBFORM","m_gender")%></Font> :</td>
		<td class="textColS" id="marital8" style="display:">&nbsp;&nbsp;&nbsp;<%=msgUtil.getTextDescription(request, "GENDER") %> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(genderVect,personalInfoDataM.getMGender(),"m_gender",displayMode," style=\"width:50%;\" ") %></td>
	</tr>
	<tr>
		<td class="textColS" id="marital9M" style="display:none"><%=msgUtil.getTextDescription(request, "OCCUPATION") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MARRY_INFO_SUBFORM","m_occupation")%></Font> :</td>
		<td class="textColS" id="marital9" style="display:"><%=msgUtil.getTextDescription(request, "OCCUPATION") %> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(occupationVect,personalInfoDataM.getMOccupation(),"m_occupation",displayMode,"onChange=\"javascript:parseOccupationToPosition(this.value,'MPositionId','m_position','" + positionSelect + "');\" style=\"width:98%;\" ") %></td>
		<td class="textColS" id="marital10M" style="display:none">&nbsp;&nbsp;&nbsp;<%=msgUtil.getTextDescription(request, "COMPANY") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MARRY_INFO_SUBFORM","m_company")%></Font> :</td>
		<td class="textColS" id="marital10" style="display:">&nbsp;&nbsp;&nbsp;<%=msgUtil.getTextDescription(request, "COMPANY") %> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getMCompany(),displayMode,"","m_company","textbox","style=\"width:50%;\"","50") %></td>
	</tr>
	<tr>
		<td class="textColS" id="marital11M" style="display:none"><%=msgUtil.getTextDescription(request, "POSITION") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MARRY_INFO_SUBFORM","m_position")%></Font> :</td>
		<td class="textColS" id="marital11" style="display:"><%=msgUtil.getTextDescription(request, "POSITION") %> :</td>
		<td class="inputCol" id="MPositionId"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(positionVect,personalInfoDataM.getMPosition(),"m_position",displayMode," style=\"width:98%;\" ") %></td>
		<td class="textColS" id="marital12M" style="display:none">&nbsp;&nbsp;&nbsp;<%=msgUtil.getTextDescription(request, "DEPARTMENT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"MARRY_INFO_SUBFORM","m_department")%></Font> :</td>
		<td class="textColS" id="marital12" style="display:">&nbsp;&nbsp;&nbsp;<%=msgUtil.getTextDescription(request, "DEPARTMENT") %> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getMDepartment(),displayMode,"","m_department","textbox","style=\"width:50%;\"","30") %></td>
	</tr>
</table>
<script language="JavaScript">
$(document).ready(function (){
	var thaiArray = new Array();
	var engArray = new Array();		
	<%
	for(int i=0; i< engTitles.size(); i++) {
		String value = ((TitleEngProperties) engTitles.get(i)).getEnDesc(); %>
		engArray[<%=i%>] = "<%=value%>";
	<%}
	for(int i=0; i< thaiTitles.size(); i++) {
		String value = ((TitleProperties) thaiTitles.get(i)).getThDesc();%>	
		thaiArray[<%=i%>] = "<%=value%>";
	<%}%>
	
	$("#m_title_thai_desc").autocomplete({
			source: thaiArray,
			minLength: 1,
			open: function(){
				$("#m_title_thai_desc").autocomplete("widget").width(300);
			}
	 });
			
	$("#m_title_eng_desc").autocomplete({
			source: engArray,
			minLength: 1,
			open: function(){
				$("#m_title_eng_desc").autocomplete("widget").width(300);
			}
	 });
});

if(document.appFormName.m_birth_date != null){
	setBirthDate('m_birth_date','m_age');
}
function checkMaritalStatus(){
	var form = document.appFormName;
	var status = form.marital_status.value;
	var objs = document.all;
	if(status == '<%=OrigConstant.MARITAL_STATUS_MARRY%>' || status == '<%=OrigConstant.MARITAL_STATUS_MARRY_NO_SIGN%>'){
		var obj1 = eval("objs.marital1M");
		var obj2 = eval("objs.marital1");
		obj1.style.display = "";
		obj2.style.display = "none";
		
		obj1 = eval("objs.marital2M");
		obj2 = eval("objs.marital2");
		obj1.style.display = "";
		obj2.style.display = "none";
		
		obj1 = eval("objs.marital3M");
		obj2 = eval("objs.marital3");
		obj1.style.display = "";
		obj2.style.display = "none";
		
		obj1 = eval("objs.marital4M");
		obj2 = eval("objs.marital4");
		obj1.style.display = "";
		obj2.style.display = "none";
		
		obj1 = eval("objs.marital5M");
		obj2 = eval("objs.marital5");
		obj1.style.display = "";
		obj2.style.display = "none";
		
		obj1 = eval("objs.marital6M");
		obj2 = eval("objs.marital6");
		obj1.style.display = "";
		obj2.style.display = "none";
		
		obj1 = eval("objs.marital7M");
		obj2 = eval("objs.marital7");
		obj1.style.display = "";
		obj2.style.display = "none";
		
		obj1 = eval("objs.marital8M");
		obj2 = eval("objs.marital8");
		obj1.style.display = "";
		obj2.style.display = "none";
		
		obj1 = eval("objs.marital9M");
		obj2 = eval("objs.marital9");
		obj1.style.display = "";
		obj2.style.display = "none";
		
		obj1 = eval("objs.marital10M");
		obj2 = eval("objs.marital10");
		obj1.style.display = "";
		obj2.style.display = "none";
		
		obj1 = eval("objs.marital11M");
		obj2 = eval("objs.marital11");
		obj1.style.display = "";
		obj2.style.display = "none";
		
		obj1 = eval("objs.marital12M");
		obj2 = eval("objs.marital12");
		obj1.style.display = "";
		obj2.style.display = "none";
	}else{
		var obj1 = eval("objs.marital1M");
		var obj2 = eval("objs.marital1");
		obj1.style.display = "none";
		obj2.style.display = "";
		
		obj1 = eval("objs.marital2M");
		obj2 = eval("objs.marital2");
		obj1.style.display = "none";
		obj2.style.display = "";
		
		obj1 = eval("objs.marital3M");
		obj2 = eval("objs.marital3");
		obj1.style.display = "none";
		obj2.style.display = "";
		
		obj1 = eval("objs.marital4M");
		obj2 = eval("objs.marital4");
		obj1.style.display = "none";
		obj2.style.display = "";
		
		obj1 = eval("objs.marital5M");
		obj2 = eval("objs.marital5");
		obj1.style.display = "none";
		obj2.style.display = "";
		
		obj1 = eval("objs.marital6M");
		obj2 = eval("objs.marital6");
		obj1.style.display = "none";
		obj2.style.display = "";
		
		obj1 = eval("objs.marital7M");
		obj2 = eval("objs.marital7");
		obj1.style.display = "none";
		obj2.style.display = "";
		
		obj1 = eval("objs.marital8M");
		obj2 = eval("objs.marital8");
		obj1.style.display = "none";
		obj2.style.display = "";
		
		obj1 = eval("objs.marital9M");
		obj2 = eval("objs.marital9");
		obj1.style.display = "none";
		obj2.style.display = "";
		
		obj1 = eval("objs.marital10M");
		obj2 = eval("objs.marital10");
		obj1.style.display = "none";
		obj2.style.display = "";
		
		obj1 = eval("objs.marital11M");
		obj2 = eval("objs.marital11");
		obj1.style.display = "none";
		obj2.style.display = "";
		
		obj1 = eval("objs.marital12M");
		obj2 = eval("objs.marital12");
		obj1.style.display = "none";
		obj2.style.display = "";
	}
	
}
</script>