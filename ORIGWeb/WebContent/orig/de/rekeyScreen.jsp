<%@page import="com.eaf.orig.ulo.pl.service.PLORIGEJBService"%>
<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%-- <%@ page import="com.eaf.orig.shared.dao.utility.UtilityDAO"%> --%>
<%-- <%@ page import="com.eaf.orig.shared.dao.ORIGDAOFactory"%> --%>
<%@ page import="com.eaf.orig.shared.model.RekeyDataM"%>






<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	HashMap hMessage = (HashMap)request.getSession().getAttribute("ERROR_MSG");
	if (hMessage == null) {
		hMessage = new HashMap();
	}
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = "EDIT";

	MessageResourceUtil msgUtil = MessageResourceUtil.getInstance();
//	String displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT;
	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	
	Vector occupationVect = utility.loadCacheByName("Occupation");
	Vector businessTypeVect = utility.loadCacheByName("BusinessType");
	Vector businessSizeVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",1);
	Vector positionVect = new Vector();
	//Vector qualificationVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",2);
   Vector qualificationVect = utility.getMasterDataFormCache("EDULEVCDE");
	Vector regisStatusVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",3);
	//Vector regisStatusVect = utility.loadCacheByName("AddressStatus");
	Vector builingConditionVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",11);
	Vector locationVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",5);
	Vector landOwnerVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",6);
	Vector preRecordVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",4);	
	
	String personalType = (String) request.getSession().getAttribute("PersonalType");
	PersonalInfoDataM personalInfoDataM;
	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
	}else if(OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)){
		personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
	}else{
		personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
	}
	if(personalInfoDataM == null){
		personalInfoDataM = new PersonalInfoDataM();
	}
	//String disablePosition = "disabled";
	if(!ORIGUtility.isEmptyString(personalInfoDataM.getOccupation())){
		positionVect = cacheUtil.getPositionByOccupation(personalInfoDataM.getOccupation());
		//disablePosition = "";
	}
	double timeInAddress = personalInfoDataM.getTimeInCurrentAddress();
	String timeInAddressStr = String.valueOf(timeInAddress);
	String timeYear = "0";
	String timeMonth = "0";
	if(timeInAddressStr != null){
		int position = timeInAddressStr.indexOf(".");
		System.out.println("position = "+position);
		if(position == -1){
			timeYear = timeInAddressStr.substring(0, position);
		}else{
			timeYear = timeInAddressStr.substring(0, position);
			timeMonth = timeInAddressStr.substring(position+1, timeInAddressStr.length());
		}
	}
%>


<script type="text/javascript">
			function showHide(objid){					
				var obj = document.getElementById("showMsg"+objid);
				var obj1 = document.getElementById("MSG_"+objid);
				obj.innerHTML = obj1.innerHTML;
				var obj2 = document.getElementById("showIpt"+objid);
				var obj3 = document.getElementById("INP_"+objid);
				obj2.innerHTML = obj3.innerHTML;															
				obj3.innerHTML= "";
								
				
			}
			function setColour(objid){									
				var obj2 = document.getElementById("showIpt"+objid);
				var childs = obj2.getElementsByTagName('INPUT');				
				for(i = 0; i < childs.length; i++){
					var field = childs[i];
					if(field != null){
						if(field.type == 'text'){
							if(x.className == 'inputformnew' || x.className == 'inputformnewRed' || x.className == 'inputformnewCurrency') {
								x.className = "inputformnewRed";
							} else {
								x.className = "textboxRed";
							}
						}else{
							field.className = "ComboBoxRed";
						}
					}
				}
			}						
</script>


<div id ="MSG_OCCUPATION" style="display:none;" >
<%=msgUtil.getTextDescription(request, "OCCUPATION") %><Font color="#ff0000">*</Font> :
</div>

<div id ="INP_OCCUPATION" style="display:none;" >
&nbsp;<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(occupationVect,personalInfoDataM.getOccupation(),"occupation",displayMode,"onChange=\"javascript:parseOccupationToPosition(this.value,'PositionId','position'); \" style=\"width:80%;\" ") %>
</div>

<div id ="MSG_BUS_TYPE" style="display:none;" >
<%=msgUtil.getTextDescription(request, "BUS_TYPE") %><Font color="#ff0000">*</Font> :
</div>

<div id ="INP_BUS_TYPE" style="display:none;">
&nbsp;<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(businessTypeVect,personalInfoDataM.getBusinessType(),"bus_type",displayMode," style=\"width:80%;\" ") %>
</div>

<div id ="MSG_BUS_SIZE" style="display:none;">
<%=msgUtil.getTextDescription(request, "BUS_SIZE") %><Font color="#ff0000">*</Font> :
</div>

<div id ="INP_BUS_SIZE" style="display:none;">
&nbsp;<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(businessSizeVect,personalInfoDataM.getBusinessSize(),"bus_size",displayMode," style=\"width:80%;\"  ") %>
</div>

<div id ="MSG_COMPANY" style="display:none;">
<%=msgUtil.getTextDescription(request, "COMPANY") %><Font color="#ff0000">*</Font> :
</div>

<div id ="INP_COMPANY" style="display:none;">
&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","company","textbox","style=\"width:80%;\" ","50") %>
</div>

<div id ="MSG_DEPARTMENT" style="display:none;">
<%=msgUtil.getTextDescription(request, "DEPARTMENT") %><Font color="#ff0000">*</Font> :
</div>

<div id ="INP_DEPARTMENT" style="display:none;">
&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getDepartment(),displayMode,"","department","textbox","style=\"width:80%;\" ","30") %>
</div>

<div id ="MSG_POSITION" style="display:none;">
<%=msgUtil.getTextDescription(request, "POSITION") %><Font color="#ff0000">*</Font> :
</div>

<div id ="INP_POSITION" style="display:none;">
&nbsp;<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(positionVect,personalInfoDataM.getPosition(),"position",displayMode," style=\"width:80%;\"  ") %>
</div>

<div id ="MSG_SALARY" style="display:none;">
<%=msgUtil.getTextDescription(request, "SALARY") %><Font color="#ff0000">*</Font> :
</div>

<div id ="INP_SALARY" style="display:none;">
&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","salary","textboxCurrency","onblur=\"javascript:chcekMaxDecimalValue(this);addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:80%;\" ","16") %>
</div>

<div id ="MSG_OTHER_INCOME" style="display:none;">
<%=msgUtil.getTextDescription(request, "OTHER_INCOME") %><Font color="#ff0000">*</Font> :
</div>

<div id ="INP_OTHER_INCOME" style="display:none;">
&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","other_income","textboxCurrency","onblur=\"javascript:chcekMaxDecimalValue(this);addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:80%;\" ","16") %>
</div>

<div id ="MSG_POSITION_DESC" style="display:none;">
<%=msgUtil.getTextDescription(request, "POSITION_DESC") %><Font color="#ff0000">*</Font> :
</div>

<div id ="INP_POSITION_DESC" style="display:none;">
&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getPositionDesc(),displayMode,"","position_desc","textbox","style=\"width:80%;\" ","70") %>
</div>


<div id ="MSG_SOURCE_OTHER_INCOME" style="display:none;">
<%=msgUtil.getTextDescription(request, "SOURCE_OTHER_INCOME") %><Font color="#ff0000">*</Font> :
</div>

<div id ="INP_SOURCE_OTHER_INCOME" style="display:none;">
&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(personalInfoDataM.getSourceOfOtherIncome(),displayMode,"","source_other_income","textbox","style=\"width:80%;\" ","50") %>
</div>

<div id ="MSG_SOURCE_QUALIFICATION" style="display:none;">
<%=msgUtil.getTextDescription(request, "QUALIFICATION") %><Font color="#ff0000">*</Font> :
</div>

<div id ="INP_SOURCE_QUALIFICATION" style="display:none;">
&nbsp;<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(qualificationVect,personalInfoDataM.getQualification(),"qualification",displayMode," style=\"width:80%;\"  ") %>
</div>

<div id ="MSG_SOURCE_TOTAL_WORKING" style="display:none;">
<%=msgUtil.getTextDescription(request, "TOTAL_WORKING") %><Font color="#ff0000">*</Font> :
</div>

<div id ="INP_SOURCE_TOTAL_WORKING" style="display:none;">
&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","year","textbox","onBlur=\"javascript:;checkWoringDate('birth_date','year','month',this);returnZero(this);\" onKeyPress=\"javaScript:keyPressInteger()\" style=\"width:30%;\" ","2") %>&nbsp;<%=msgUtil.getTextDescription(request, "YEAR") %>
					   <%=ORIGDisplayFormatUtil.displayInputTagScriptAction("",displayMode,"","month","textbox","onBlur=\"javascript:checkMonth('month');checkWoringDate('birth_date','year','month',this);returnZero(this);\" onKeyPress=\"javaScript:keyPressInteger()\" style=\"width:30%;\" ","2") %>&nbsp;<%=msgUtil.getTextDescription(request, "MONTH") %>
</div>

<div id ="MSG_HOUSE_REGIS_STATUS" style="display:none;">
<%=msgUtil.getTextDescription(request, "HOUSE_REGIS_STATUS") %><Font color="#ff0000">*</Font> :
</div>

<div id ="INP_HOUSE_REGIS_STATUS" style="display:none;">
&nbsp;<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(regisStatusVect,personalInfoDataM.getHouseRegisStatus(),"house_regis_status",displayMode," style=\"width:80%;\"  ") %>
</div>

<div id ="MSG_BUILDING_CONDITION" style="display:none;">
<%=msgUtil.getTextDescription(request, "BUILDING_CONDITION") %><Font color="#ff0000">*</Font> :
</div>

<div id ="INP_BUILDING_CONDITION" style="display:none;">
&nbsp;<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(builingConditionVect,personalInfoDataM.getBulidingCondition(),"building_condition",displayMode," style=\"width:80%;\"  ") %>
</div>

<div id ="MSG_PREVIOUS_RECORD" style="display:none;">
<%=msgUtil.getTextDescription(request, "PREVIOUS_RECORD") %><Font color="#ff0000">*</Font> :
</div>

<div id ="INP_PREVIOUS_RECORD" style="display:none;">
&nbsp;<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(preRecordVect,personalInfoDataM.getPreviousRecord(),"pre_record",displayMode," style=\"width:80%;\"  ") %>
</div>

<div id ="MSG_ASSET_AMT" style="display:none;">
<%=msgUtil.getTextDescription(request, "ASSET_AMT") %><Font color="#ff0000">*</Font> :
</div>

<div id ="INP_ASSET_AMT" style="display:none;">
&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(personalInfoDataM.getAssetAmount()),displayMode,"","asset_amount","textboxCurrency","onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:80%;\" ","16") %>
</div>

<div id ="MSG_LOCATION" style="display:none;">
<%=msgUtil.getTextDescription(request, "LOCATION") %><Font color="#ff0000">*</Font> :
</div>

<div id ="INP_LOCATION" style="display:none;">
&nbsp;<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(locationVect,personalInfoDataM.getLocation(),"location",displayMode," style=\"width:80%;\"  ") %>
</div>

<div id ="MSG_CHEQUE_RETURNED"  style="display:none;">
<%=msgUtil.getTextDescription(request, "CHEQUE_RETURNED") %><Font color="#ff0000">*</Font> :
</div>

<div id ="INP_CHEQUE_RETURNED" style="display:none;">
&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayInteger(personalInfoDataM.getChequeReturn()),displayMode,"","cheque_return","textbox","onKeyPress=\"keyPressInteger(this.value);\" onblur=\"javascript:addCommas('cheque_return')\" style=\"width:80%;\" ","2") %>
</div>

<div id ="MSG_DEBT_AMT" style="display:none;">
<%=msgUtil.getTextDescription(request, "DEBT_AMT") %><Font color="#ff0000">*</Font> :
</div>

<div id ="INP_DEBT_AMT" style="display:none;">
&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayCommaNumber(personalInfoDataM.getDebitAmount()),displayMode,"","debt_amount","textboxCurrency","onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:80%;\" ","16") %>
</div>

<div id ="MSG_LAND_OWNERSHIP" style="display:none;">
<%=msgUtil.getTextDescription(request, "LAND_OWNERSHIP") %><Font color="#ff0000">*</Font> :
</div>

<div id ="INP_LAND_OWNERSHIP" style="display:none;">
&nbsp;<%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(landOwnerVect,personalInfoDataM.getLandOwnerShip(),"land_ownership",displayMode," style=\"width:80%;\" ") %>
</div>

<div id ="MSG_NUMBER_OF_EMPLOYEE" style="display:none;">
<%=msgUtil.getTextDescription(request, "NUMBER_OF_EMPLOYEE") %><Font color="#ff0000">*</Font> :
</div>

<div id ="INP_NUMBER_OF_EMPLOYEE" style="display:none;">
&nbsp;<%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.displayInteger(personalInfoDataM.getNoOfEmployee()),displayMode,"","no_of_employee","textbox","onKeyPress=\"keyPressInteger(this.value);\" onblur=\"javascript:addCommas('no_of_employee')\" style=\"width:80%;\" ","4") %>
</div>

<table cellpadding="" cellspacing="1" width="100%"> 
<%
	if (hMessage.size() >0) {
%>
<tr>
<TD>
<font class="font2ManField">
Data entry incorrect.
</font>
</TD>
</tr>
<%
	}
%>


<%
// 	UtilityDAO utilityDAO = ORIGDAOFactory.getUtilityDAO();
// 	Vector vRekeys = utilityDAO.getRekeyFields();
	Vector vRekeys = PLORIGEJBService.getORIGDAOUtilLocal().getRekeyFields();
	for(int i=0;i< vRekeys.size();i++) {
		RekeyDataM rekeyDataM = (RekeyDataM)vRekeys.get(i);
		if ((i+1)%2!=0) {
%>	
	<tr>
<%
		}	
%>
		<td class="textColS" width="25%" ><div id="showMsg<%=rekeyDataM.getFieldID()%>" ></div></td>				
		<td class="inputCol" width="25%" ><div id="showIpt<%=rekeyDataM.getFieldID()%>" name="showName<%=rekeyDataM.getFieldID()%>"></div></td>
		<script type="text/javascript">
			showHide("<%=rekeyDataM.getFieldID()%>");
<%
			if (hMessage.containsKey(rekeyDataM.getFieldID())) {
%>			
				setColour("<%=rekeyDataM.getFieldID()%>");
<%
			}
%>
		</script>
<% 
		if (((i+1)==vRekeys.size()) && (vRekeys.size()%2!=0)) {
%>
			<td class="textColS" width="25%">&nbsp;</td>				
			<td class="inputCol" width="25%">&nbsp;</td>				
<%		
		}
		if ((i+1)%2==0) {
%>	
	</tr>
<%
		}
	}		
%>

<tr>
	<td class="textColS" colspan="4" ><input type="button" name="saveButton" value ="<%=MessageResourceUtil.getTextDescription(request, "SAVE") %> " class="button_text" onclick="submitAllApp()"></td>					
</tr>
</table>

<script type="text/javascript">
function submitAllApp(){	
	appFormName.action.value ="saveAllApp";
	appFormName.handleForm.value = "N";
	displayLoading('Processing...');
	appFormName.submit();
}
</script>
