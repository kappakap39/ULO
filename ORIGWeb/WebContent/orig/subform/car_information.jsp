<%@ page import="java.util.*" %>
<%@ page import="com.eaf.j2ee.pattern.util.MessageResourceUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.model.VehicleDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGCacheUtil" %>
<%@ page import="com.eaf.orig.shared.model.PersonalInfoDataM" %>
<%@ page import="com.eaf.orig.shared.model.ApplicationDataM" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGFormUtil"%>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant"%>
<%@ page import="com.eaf.orig.profile.model.UserDetailM"%>
<%@ page import="com.eaf.orig.shared.utility.TooltipResourceUtil" %>

<jsp:useBean id="ORIGUser" scope="session" class="com.eaf.orig.profile.model.UserDetailM"/>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>

<% 
	ApplicationDataM applicationDataM = ORIGForm.getAppForm();
	if(applicationDataM == null){
		applicationDataM = new ApplicationDataM();
	}
	ORIGFormUtil formUtil = new ORIGFormUtil();
    TooltipResourceUtil tooltipUtil=TooltipResourceUtil.getInstance();
	String searchType = (String) request.getSession().getAttribute("searchType");
	String displayMode = formUtil.getDisplayMode("CAR_INFO_SUBFORM", ORIGUser.getRoles(), applicationDataM.getJobState(), ORIGForm.getFormID(), searchType);	
	ORIGUtility utility = new ORIGUtility();
	ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
	//Vector carVect = utility.loadCacheByName("CarType");
	Vector carVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",9);
	Vector gearVect = new Vector();	
	VehicleDataM vehicleDataM = applicationDataM.getVehicleDataM();
	String readOnlyForDrawDown = "";
	String styleForDrawDown = "textbox";
	String disableForDrawDown = "";
	if(vehicleDataM == null){
		vehicleDataM = new VehicleDataM();
	}
	
	if((!"NEW".equals(vehicleDataM.getDrawDownStatus()) && !ORIGUtility.isEmptyString(vehicleDataM.getDrawDownStatus())) || ("Y").equals(applicationDataM.getDrawDownFlag())){
		//displayMode = ORIGDisplayFormatUtil.DISPLAY_MODE_VIEW;
		readOnlyForDrawDown = ORIGDisplayFormatUtil.READ_ONLY;
		styleForDrawDown = "textboxReadOnly";
		disableForDrawDown = "disabled";
	}
	Vector carUserVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",20);
	String categoryDesc = cacheUtil.getORIGMasterDisplayNameDataM("CarCategoryType", vehicleDataM.getCategory());
	String brandDesc = cacheUtil.getORIGMasterDisplayNameDataM("CarBrand", vehicleDataM.getBrand());
	String modelDesc = cacheUtil.getORIGCacheDisplayNameFormDB(vehicleDataM.getModel(), "CarModel", vehicleDataM.getBrand());
	String[] modelDescs;
	if(modelDesc != null){
		modelDescs = modelDesc.split(",");
		if(modelDescs != null){
			modelDesc = modelDescs[0];
		}
	}
	String colorDesc = cacheUtil.getORIGMasterDisplayNameDataM("CarColorCode", vehicleDataM.getColor());
	String licenseTypeDesc = cacheUtil.getORIGMasterDisplayNameDataM("LicenseType", vehicleDataM.getLicenseType());
	String provinceDesc = cacheUtil.getORIGMasterDisplayNameDataM("Province", vehicleDataM.getProvince());
	
	PersonalInfoDataM personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, com.eaf.orig.shared.constant.OrigConstant.PERSONAL_TYPE_APPLICANT);

	System.out.println("personalInfoDataM.getCustomerType() = " +personalInfoDataM.getCustomerType());
	
	String formId = ORIGForm.getFormID();
	String sDate;
	int year = Calendar.getInstance().get(Calendar.YEAR);
	
	UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
	Vector userRoles = userM.getRoles();
	String role = (String)userRoles.get(0);
	
	
	Vector objectiveVect = cacheUtil.getNaosCacheDataMs("AL_ALL_ALL",19);
%>

<table cellpadding="" cellspacing="1" width="100%" align="center">
	<%if((formId==null||("Y").equals(applicationDataM.getDrawDownFlag())) && !(OrigConstant.ROLE_PD.equals(userRoles.elementAt(0)) || OrigConstant.ROLE_XCMR.equals(userRoles.elementAt(0)))){ %>
	<tr bgcolor="#F4F4F4">
		<td id="foundCar" align="right"></td>
		<td colspan="5">&nbsp;<%=ORIGDisplayFormatUtil.displayButtonTagScriptAction(MessageResourceUtil.getTextDescription(request, "VIEW"), ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT,"button", "viewCarBnt", "button_text", " onClick=loadPopup(\"car\",\"LoadCarPopup\",\"900\",\"300\",\"300\",\"80\",\""+personalInfoDataM.getIdNo()+"\",\"\",\"\") ", "") %></td>
	</tr>
	<%} %>
	<tr>
		<td class="textColS" width="14%"><%=MessageResourceUtil.getTextDescription(request, "CATEGORY") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CAR_INFO_SUBFORM","car_category")%></Font> :</td>
		<td class="inputCol" width="25%"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(vehicleDataM.getCategory(),displayMode,"5","car_category","25","car_category_desc",styleForDrawDown,readOnlyForDrawDown,"50","...","button_text","LoadCategory",categoryDesc,"CarCategoryType") %></td>
		<td class="textColS" width="15%" nowrap="nowrap">&nbsp;&nbsp;&nbsp;<%=MessageResourceUtil.getTextDescription(request, "ENGINE_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CAR_INFO_SUBFORM","car_engine_no")%></Font> :</td>
		<td class="inputCol" width="15%"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(vehicleDataM.getEngineNo(),displayMode,"","car_engine_no",styleForDrawDown,"style=\"width:80%;\" "+readOnlyForDrawDown,"50") %></td>
		<td class="textColS" width="10%"><%=MessageResourceUtil.getTextDescription(request, "CAR") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CAR_INFO_SUBFORM","car")%></Font> :</td>
		<td class="inputCol" width="21%"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(carVect,vehicleDataM.getCar(),"car",displayMode,"onChange=\"javascript:setdataForCar(this.value)\" style=\"width:80%;\" "+disableForDrawDown) %></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "BRAND") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CAR_INFO_SUBFORM","car_brand")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(vehicleDataM.getBrand(),displayMode,"5","car_brand","25","car_brand_desc",styleForDrawDown,"onchange=\"javascript:checkBrand();\" "+readOnlyForDrawDown,"50","...","button_text","LoadCarBrand",brandDesc,"CarBrand") %></td>
		<td class="textColS" nowrap="nowrap">&nbsp;&nbsp;&nbsp;<%=MessageResourceUtil.getTextDescription(request, "REGIS_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CAR_INFO_SUBFORM","car_register_no")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(vehicleDataM.getRegisterNo(),displayMode,"","car_register_no",styleForDrawDown,"style=\"width:80%;\" "+readOnlyForDrawDown,"20") %></td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "GEAR") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CAR_INFO_SUBFORM","car_gear")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayRadioTagScriptAction("A", displayMode, "car_gear", ORIGDisplayFormatUtil.displayHTML(vehicleDataM.getGear()), "", "", "onClick=\"javascript:setdataForGear(this.value)\" " +disableForDrawDown) %> <font class="textColS">Automatic</font>
			&nbsp;<%=ORIGDisplayFormatUtil.displayRadioTagScriptAction("M", displayMode, "car_gear", ORIGDisplayFormatUtil.displayHTML(vehicleDataM.getGear()), "", "", "onClick=\"javascript:setdataForGear(this.value)\" " +disableForDrawDown) %> <font class="textColS"> Manual</font></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "MODEL") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CAR_INFO_SUBFORM","car_model")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(vehicleDataM.getModel(),displayMode,"5","car_model","25","car_model_desc",styleForDrawDown,readOnlyForDrawDown,"50","...","button_text","LoadCarModel",new String[] {"car_model", "car_brand"},"",ORIGDisplayFormatUtil.displayHTML(modelDesc),"CarModel") %></td>
		<td class="textColS" nowrap="nowrap">&nbsp;&nbsp;&nbsp;<%=MessageResourceUtil.getTextDescription(request, "CLASSIS_NO") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CAR_INFO_SUBFORM","car_classis_no")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(vehicleDataM.getChassisNo(),displayMode,"","car_classis_no",styleForDrawDown,"style=\"width:80%;\" "+readOnlyForDrawDown,"20") %></td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "WEIGHT") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CAR_INFO_SUBFORM","car_weight")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.formatNumber("###,##0",vehicleDataM.getWeight()),displayMode,"","car_weight",styleForDrawDown,"onblur=\"javascript:addComma(this);returnZero(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" onKeyPress=\"keyPressInteger();\" style=\"width:80%;\" "+readOnlyForDrawDown,"") %></td>
	</tr>
	<tr>
	<%if(!OrigConstant.ROLE_CMR.equalsIgnoreCase(role)){ 
	%>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "COLOR") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CAR_INFO_SUBFORM","car_color")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(vehicleDataM.getColor(),displayMode,"5","car_color","25","car_color_desc",styleForDrawDown,readOnlyForDrawDown,"50","...","button_text","LoadCarColor",colorDesc,"CarColorCode") %></td>
		<td class="textColS" nowrap="nowrap">&nbsp;&nbsp;&nbsp;<%=MessageResourceUtil.getTextDescription(request, "CC") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CAR_INFO_SUBFORM","car_cc")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.formatNumber("###,##0.00",vehicleDataM.getCC()),displayMode,"","car_cc",styleForDrawDown,"onblur=\"javascript:addComma(this);addDecimalFormat(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:80%;\" "+readOnlyForDrawDown,"") %></td>
	<%}else{ %> 
		<td class="textColS" colspan="4">&nbsp;</td>
	<%} %>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "YEAR(CE)") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CAR_INFO_SUBFORM","car_year")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(utility.intToString(vehicleDataM.getYear()),displayMode,"","car_year",styleForDrawDown,"onKeyPress=\"keyPressInteger();\" style=\"width:80%;\" "+readOnlyForDrawDown+ " onMouseOver=\""+tooltipUtil.getTooltip(request,"car_year_bc")+"\" ","4") %></td>
	</tr>
	<%if(!OrigConstant.ROLE_CMR.equalsIgnoreCase(role)){ 
	%>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "LICENSE_TYPE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CAR_INFO_SUBFORM","car_license_type")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(vehicleDataM.getLicenseType(),displayMode,"5","car_license_type","25","car_license_type_desc",styleForDrawDown,"onKeyUp=\"javascript:getInsuranceData()\" " +readOnlyForDrawDown,"20","...","button_text","LoadLicenseType",licenseTypeDesc,"LicenseType") %></td>
		<td class="textColS" nowrap="nowrap">&nbsp;&nbsp;&nbsp;<%=MessageResourceUtil.getTextDescription(request, "CAR_USER") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CAR_INFO_SUBFORM","car_user")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(carUserVect,vehicleDataM.getCarUser(),"car_user",displayMode,"style=\"width:80%;\" "+disableForDrawDown) %></td>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "KILOMETER") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CAR_INFO_SUBFORM","car_kilometers")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagScriptAction(ORIGDisplayFormatUtil.formatNumber("###,##0",vehicleDataM.getKelometers()),displayMode,"","car_kilometers",styleForDrawDown,"onKeyPress=\"keyPressInteger();\" onblur=\"javascript:addComma(this);returnZero(this);\" onKeyPress=\"isNumberOnKeyPress(this)\" onkeyup=\"isNumberOnkeyUp(this)\" onfocus=\"removeCommaField(this)\" style=\"width:80%;\" "+readOnlyForDrawDown,"16") %></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "PROVINCE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CAR_INFO_SUBFORM","car_province")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayPopUpTagScriptAction(vehicleDataM.getProvince(),displayMode,"5","car_province","25","car_province_desc",styleForDrawDown,readOnlyForDrawDown,"20","...","button_text","LoadProvince",provinceDesc,"Province") %></td>
	</tr>
	<tr>
		<% if(vehicleDataM.getRegisterDate()==null){
				sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
			}else{
				sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(vehicleDataM.getRegisterDate()));
			}
		%>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "REGIS_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CAR_INFO_SUBFORM","car_register_date")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","car_register_date",styleForDrawDown,"right","onblur=\"javascript:checkDate('car_register_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\" "+readOnlyForDrawDown) %></td>
		<% if(vehicleDataM.getExpiryDate()==null){
				sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
			}else{
				sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(vehicleDataM.getExpiryDate()));
			}
		%>
		<td class="textColS" nowrap="nowrap">&nbsp;&nbsp;&nbsp;<%=MessageResourceUtil.getTextDescription(request, "EXPIRY_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CAR_INFO_SUBFORM","car_expiry_date")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","car_expiry_date",styleForDrawDown,"right","onblur=\"javascript:checkDate('car_expiry_date');\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\" "+readOnlyForDrawDown) %></td>
		<% if(vehicleDataM.getOccupyDate()==null){
				sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(applicationDataM.getNullDate()));
			}else{
				sDate=ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(vehicleDataM.getOccupyDate()));
			}
		%>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "OCCUPY_DATE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CAR_INFO_SUBFORM","car_occupy_date")%></Font> :</td>
		<td class="inputCol"><%=ORIGDisplayFormatUtil.displayInputTagJsDate("appFormName",sDate,displayMode,"","car_occupy_date",styleForDrawDown,"left","onblur=\"javascript:checkDate('car_occupy_date')\" onkeydown=\"javascript:DateFormat(this,this.value,event,false,'3')\" maxlength =\"10\" "+readOnlyForDrawDown) %></td>
	</tr>
	<tr>
		<td class="textColS"><%=MessageResourceUtil.getTextDescription(request, "OBJECTIVE") %><Font color="#ff0000"><%=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CAR_INFO_SUBFORM","car_objective")%></Font> :</td>
		<td class="inputCol" colspan="5"><%=ORIGDisplayFormatUtil.displaySelectTagScriptAction_ORIG(objectiveVect,vehicleDataM.getObjective(),"car_objective",displayMode,"onChange=\"javascript:setdataForObjective(this.value)\" style=\"width:23%;\" "+disableForDrawDown) %></td>
		<!--<td class="textColS"><%//=msgUtil.getTextDescription(request, "CAR_PARK") %><Font color="#ff0000"><%//=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CAR_INFO_SUBFORM","car_park")%></Font> :</td>
		<td class="inputCol">&nbsp;<%//=ORIGDisplayFormatUtil.displayInputTagScriptAction(vehicleDataM.getCarParkLocation(),displayMode,"","car_park",styleForDrawDown,"","50") %></td>
		<td class="textColS"><%//=msgUtil.getTextDescription(request, "TRAVALLING_ROUT") %><Font color="#ff0000"><%//=ORIGDisplayFormatUtil.getMandatory_ORIG(personalInfoDataM.getCustomerType(),ORIGUser.getRoles(),"CAR_INFO_SUBFORM","car_travaling")%></Font> :</td>
		<td class="inputCol">&nbsp;<%//=ORIGDisplayFormatUtil.displayInputTagScriptAction(vehicleDataM.getTravalingRout(),displayMode,"","car_travaling",styleForDrawDown,"","20") %></td>
		-->
	</tr>
	<%} %>
</table>
<input type="hidden" name="car_type_tmp" value="<%=ORIGDisplayFormatUtil.displayHTML(vehicleDataM.getCar()) %>">
<input type="hidden" name="car_objective_tmp" value="<%=ORIGDisplayFormatUtil.displayHTML(vehicleDataM.getObjective()) %>">
<input type="hidden" name="car_gear_tmp" value="<%=ORIGDisplayFormatUtil.displayHTML(vehicleDataM.getGear()) %>">
<input type="hidden" name="car_user_tmp" value="<%=ORIGDisplayFormatUtil.displayHTML(vehicleDataM.getCarUser()) %>">
<script language="JavaScript">
function setdataForCar(value){
	form = document.appFormName;
	if('<%=OrigConstant.CAR_TYPE_NEW%>' == value){
		form.car_year.value = '<%=(year)%>';
	}else{
		form.car_year.value = '';
	}
	form.car_type_tmp.value = value;
	if(value == '' || value == null){
		if(form.car_brand != null){
			form.car_brand.value = "";
		}
		if(form.car_weight != null){
			form.car_weight.value = "";
		}
		if(form.car_cc != null){
			form.car_cc.value = "";
		}
		form.car_gear[0].checked =false;
		form.car_gear[1].checked =false;
	}
}
function checkBrand(){
	form = document.appFormName;
	form.car_model.value = "";
	form.car_model_desc.value = "";
}
function setdataForObjective(value){
	document.appFormName.car_objective_tmp.value = value;
}
function setdataForGear(value){
	document.appFormName.car_gear_tmp.value = value;
}
function setEffectiveDate(value, field, yearIn){
	if(value != ''){
		var newDate = new Date(value);
		var myDate = value.split('/');
		
		var day = myDate[0];
		var month = myDate[1];
		var year = myDate[2];
		
		day = parseFloat(day);
		month = parseFloat(month);
		year = parseFloat(year) + yearIn;
		
		/*var dayTmp = day - 1;
		var monthTmp = month;
		
		if(day == 1){
			dayTmp = getMonthLen(year, monthTmp-1);
		}
		if(day == 1 && month == 1){
			year = year - yearIn;
			monthTmp = month-1;
		}else if(day == 1){
			monthTmp = month-1;
		}
		if(monthTmp == 0){
			monthTmp = 12;
		}*/
		
		if(day.toString().length == 1){
			day = '0'+day;
		}
		if(month.toString().length == 1){
			month = '0'+month;
		}
		
		var newDate = day+'/'+month+'/'+year;
		var fieldObj = eval("document.appFormName."+field);
		fieldObj.value = newDate;
	}
}
function getMonthLen(theYear, theMonth) {
	var oneDay = 1000 * 60 * 60 * 24
	var thisMonth = new Date(theYear, theMonth, 1)
	var previusMonth = new Date(theYear, theMonth - 1, 1)
	var len = Math.ceil((thisMonth.getTime() - previusMonth.getTime())/oneDay)
	return len
}

$(document).ready(function (){
	$("#car_category").autocomplete({
			source: function( request, response ) {
				$.ajax({
					url: "AutoCompleteLookupServlet",
					contentType: "application/json; charset=UTF-8",
					dataType: "json",
					data: {	q:request.term, lookUp: "carCategory"},
					success: function(data) {
						response($.map(data, function(item){
							return {
								label: item.code + " : " + item.desc,
								value: item.code,
								 desc: item.desc
							};
						} ));
					}
				});
			},
			minLength: 1,
			open: function(){
				$("#car_category").autocomplete("widget").width(350);
			} ,
			select: function(event, ui) {
				$("#car_category_desc").val(ui.item.desc);
			}
	});	
	
	$("#car_brand").autocomplete({
			source: function( request, response ) {
				$.ajax({
					url: "AutoCompleteLookupServlet",
					contentType: "application/json; charset=UTF-8",
					dataType: "json",
					data: {	q:request.term, lookUp: "carBrand"},
					success: function(data) {
						response($.map(data, function(item){
							return {
								label: item.code + " : " + item.desc,
								value: item.code,
								 desc: item.desc
							};
						} ));
					}
				});
			},
			minLength: 1,
			open: function(){
				$("#car_brand").autocomplete("widget").width(350);
			} ,
			select: function(event, ui) {
				$("#car_brand_desc").val(ui.item.desc);
			}
	});	
	
	$("#car_model").autocomplete({
			source: function( request, response ) {
				$.ajax({
					url: "AutoCompleteLookupServlet",
					contentType: "application/json; charset=UTF-8",
					dataType: "json",
					data: {	q:request.term, lookUp: "carModel", brand: $("#car_brand").val() },
					success: function(data) {
						response($.map(data, function(item){
							return {
								label: item.code + " : " + item.desc,
								value: item.code,
								 desc: item.desc
							};
						} ));
					}
				});
			},
			minLength: 1,
			open: function(){
				$("#car_model").autocomplete("widget").width(350);
			} ,
			select: function(event, ui) {
				$("#car_model_desc").val(ui.item.desc);
			}
	});
	
	$("#car_color").autocomplete({
			source: function( request, response ) {
				$.ajax({
					url: "AutoCompleteLookupServlet",
					contentType: "application/json; charset=UTF-8",
					dataType: "json",
					data: {	q:request.term, lookUp: "carColor"},
					success: function(data) {
						response($.map(data, function(item){
							return {
								label: item.code + " : " + item.desc,
								value: item.code,
								 desc: item.desc
							};
						} ));
					}
				});
			},
			minLength: 1,
			open: function(){
				$("#car_color").autocomplete("widget").width(350);
			} ,
			select: function(event, ui) {
				$("#car_color_desc").val(ui.item.desc);
			}
	});	
	
	$("#car_license_type").autocomplete({
			source: function( request, response ) {
				$.ajax({
					url: "AutoCompleteLookupServlet",
					contentType: "application/json; charset=UTF-8",
					dataType: "json",
					data: {	q:request.term, lookUp: "carLicenseType"},
					success: function(data) {
						response($.map(data, function(item){
							return {
								label: item.code + " : " + item.desc,
								value: item.code,
								 desc: item.desc
							};
						} ));
					}
				});
			},
			minLength: 1,
			open: function(){
				$("#car_license_type").autocomplete("widget").width(350);
			} ,
			select: function(event, ui) {
				$("#car_license_type_desc").val(ui.item.desc);
			}
	});	
	
	$("#car_province").autocomplete({
			source: function( request, response ) {
				$.ajax({
					url: "AutoCompleteLookupServlet",
					contentType: "application/json; charset=UTF-8",
					dataType: "json",
					data: {	q:request.term, lookUp: "carProvince"},
					success: function(data) {
						response($.map(data, function(item){
							return {
								label: item.code + " : " + item.desc,
								value: item.code,
								 desc: item.desc
							};
						} ));
					}
				});
			},
			minLength: 1,
			open: function(){
				$("#car_province").autocomplete("widget").width(350);
			} ,
			select: function(event, ui) {
				$("#car_province_desc").val(ui.item.desc);
			}
	});	
	
});
</script>