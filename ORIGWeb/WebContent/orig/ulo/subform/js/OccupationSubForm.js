$(document).ready(function(){
	if(null!=CURRENT_POPUP_FIELD_NAME && ""!=CURRENT_POPUP_FIELD_NAME && undefined!=CURRENT_POPUP_FIELD_NAME){
		autoTabNextElement(CURRENT_POPUP_FIELD_NAME);
	}
	$("[name='OCCUPATION']").change();
});

function BUSINESS_TYPEActionJS(elementId,mode,name) {
	try{
		var actionJs = $('[name="'+elementId+'"]').next('.input-group-addon').attr('onclick');
		$('[name="'+elementId+'"]').next('.input-group-addon').attr('onclick','');
		 setTimeout(function() {
			 $('[name="'+elementId+'"]').next('.input-group-addon').attr('onclick',actionJs);
	    }, 300);
		
		var BUSINESS_TYPE = $("#OCCUPATION_SUBFORM [name='BUSINESS_TYPE']").val();
		var PERSONAL_SEQ = $("#OCCUPATION_SUBFORM [name='PERSONAL_SEQ']").val();
		var $data = '&BUSINESS_TYPE='+BUSINESS_TYPE+'&origElementId='+elementId;
		$data += '&PERSONAL_SEQ='+PERSONAL_SEQ;
		loadPopupDialog('POPUP_BUSINESS_TYPE_FORM','INSERT','0',$data,'','');
	}catch(exception){
		errorException(exception);
	}
}
function OCCUPATIONActionJS(elementId,mode,name) {
	try{
		var actionJs = $('[name="'+elementId+'"]').next('.input-group-addon').attr('onclick');
		$('[name="'+elementId+'"]').next('.input-group-addon').attr('onclick','');
		 setTimeout(function() {
			 $('[name="'+elementId+'"]').next('.input-group-addon').attr('onclick',actionJs);
	    }, 300);
		var OCCUPATION = $("#OCCUPATION_SUBFORM [name='OCCUPATION']").val();
		var PERSONAL_SEQ = $("#OCCUPATION_SUBFORM [name='PERSONAL_SEQ']").val();
		var $data = '&OCCUPATION='+OCCUPATION+'&origElementId='+elementId;
		$data += '&PERSONAL_SEQ='+PERSONAL_SEQ;
		loadPopupDialog('POPUP_OCCUPATION_FORM','INSERT','0',$data,'','');
	}catch(exception){
		errorException(exception);
	}
}
function PROFESSIONActionJS(elementId,mode,name) {
	try{
		var actionJs = $('[name="'+elementId+'"]').next('.input-group-addon').attr('onclick');
		$('[name="'+elementId+'"]').next('.input-group-addon').attr('onclick','');
		 setTimeout(function() {
			 $('[name="'+elementId+'"]').next('.input-group-addon').attr('onclick',actionJs);
	    }, 300);
		var PROFESSION = $("#OCCUPATION_SUBFORM [name='PROFESSION']").val();
		var PERSONAL_SEQ = $("#OCCUPATION_SUBFORM [name='PERSONAL_SEQ']").val();
		var $data = '&PROFESSION_TYPE='+PROFESSION;
		$data += '&PERSONAL_SEQ='+PERSONAL_SEQ;
		loadPopupDialog('POPUP_PROFESSION_TYPE_FORM','INSERT','0',$data,'','');
	}catch(exception){
		errorException(exception);
	}
}
function TOT_WORK_YEARInitJS() {
	try{
		$('[name=TOT_WORK_YEAR]').attr('onblur','TOT_WORK_YEARActionJS()');
	}catch(exception){
		errorException(exception);
	}
}
function TOT_WORK_YEARActionJS() {
	// not check bcoz will send to Verify Customer  : 21/8/2015
/*	var element = $('[name=TOT_WORK_YEAR]');
	var year = parseInt(element.val());
	var enBirthDateYear = parseInt($('[name=EN_BIRTH_DATE]').val().split('/')[2]);
	var thisYear = new Date().getFullYear();
	var age = thisYear - enBirthDateYear;
	
	if (year > age) {
		alertBox(ERROR_INVALID_TOT_WORK_OVER_AGE, emptyFocusElementActionJS, 'TOT_WORK_YEAR');
	}
	TOT_checkWorkExp();*/
}
function TOT_WORK_MONTHInitJS() {
	try{
		$('[name=TOT_WORK_MON]').attr('onblur','TOT_WORK_MONActionJS()');
	}catch(exception){
		errorException(exception);
	}
}
function TOT_WORK_MONTHActionJS() {
	try{
		var element = $('[name=TOT_WORK_MONTH]');
		var month = parseInt(element.val());
		if (month > 11) {
			alertBox(ERROR_INVALID_TOT_WORK_MONTH, emptyFocusElementActionJS, 'TOT_WORK_MONTH');
		}
		TOT_checkWorkExp();
	}catch(exception){
		errorException(exception);
	}
}
function TOT_checkWorkExp() {
	try{
		checkWorkExp($('[name=TOT_WORK_YEAR]'), $('[name=TOT_WORK_MONTH]'));
	}catch(exception){
		errorException(exception);
	}
}
function PREV_WORK_YEARInitJS() {
	try{
		$('[name=PREV_WORK_YEAR]').attr('onblur','PREV_WORK_YEARActionJS()');
	}catch(exception){
		errorException(exception);
	}
}
function PREV_WORK_YEARActionJS() {
	try{
		var element = $('[name=PREV_WORK_YEAR]');
		var year = parseInt(element.val());
		var enBirthDateYear = parseInt($('[name=EN_BIRTH_DATE]').val().split('/')[2]);
		var thisYear = new Date().getFullYear();
		var age = thisYear - enBirthDateYear;
		
		if (year > age) {
			alertBox(ERROR_INVALID_TOT_WORK_OVER_AGE, emptyFocusElementActionJS, 'PREV_WORK_YEAR');
		}
		PREV_checkWorkExp();
	}catch(exception){
		errorException(exception);
	}
}
function PREV_WORK_MONTHInitJS() {
	try{
		$('[name=PREV_WORK_MONTH]').attr('onblur','PREV_WORK_MONTHActionJS()');
	}catch(exception){
		errorException(exception);
	}
}
function PREV_WORK_MONTHActionJS() {
	try{
		var element = $('[name=PREV_WORK_MONTH]');
		var month = parseInt(element.val());
		if (month > 11) {
			alertBox(ERROR_INVALID_TOT_WORK_MONTH, emptyFocusElementActionJS, 'PREV_WORK_MONTH');
		}
		PREV_checkWorkExp();
	}catch(exception){
		errorException(exception);
	}
}
function PREV_checkWorkExp() {
	try{
		checkWorkExp($('[name=PREV_WORK_YEAR]'), $('[name=PREV_WORK_MONTH]'));
	}catch(exception){
		errorException(exception);
	}
}
function checkWorkExp(workYear, workMonth) {
	var isError = false;
	try{
		var msg;
		var fixit = function(msg) {
			alertBox(msg, function() {
				workMonth.val('');
				emptyFocusElementActionJS(workYear.attr('name'));
			}, workYear.attr('name'));
		};
		
		if (workYear.val() == '0' && workMonth.val() == '0') {
			isError = true;
		}
		if (parseInt(workYear.val()) < '1' && workMonth.val() == '') {
			isError = true;
		}
		if (isError) {
			msg = ERROR_INVALID_TOT_WORK;
			fixit(msg);
		}
	}catch(exception){
		errorException(exception);
	}
}

function PREV_COMPANYActionJS(element, mode, name){
	try{
		var PREV_COMPANY = $("[name='"+element+"']").val();
		if(!isEmpty(PREV_COMPANY) && PREV_COMPANY.length>1 && !PREV_COMPANY.match(/^[0-9]+$/)){
			
		}else{
			if(!isEmpty(PREV_COMPANY)){
				$("[name="+element+"]")[0].selectize.removeItem(PREV_COMPANY);
				$("[name="+element+"]")[0].selectize.removeOption(PREV_COMPANY);
				alertBox('ERROR_TEXT_COMPANY_NAME');
			}
		}
	}catch(exception){
		errorException(exception);
	}
}

function POSITIONInitJS(elementId,mode,name){
	try{
		var OCCUPATION_CODE = $('[name="OCCUPATION_CODE"]').val();
		if(GOVERNMENT_OFFICER==OCCUPATION_CODE){
//			displayHtmlElement(elementId, '');
		}
	}catch(exception){
		errorException(exception);
	}
}
function POSITION_LEVELInitJS(elementId,mode,name){
	try{
		var OCCUPATION_CODE = $('[name="OCCUPATION_CODE"]').val();
		if(GOVERNMENT_OFFICER==OCCUPATION_CODE){
			displayHtmlElement(elementId, '');
		}
	}catch(exception){
		errorException(exception);
	}
}
function POSITION_CODEInitJS(elementId,mode,name){
	try{
		var OCCUPATION_CODE = $('[name="OCCUPATION_CODE"]').val();
		if(GOVERNMENT_OFFICER!=OCCUPATION_CODE){
//			displayHtmlElement(elementId, '');
		}
	}catch(exception){
		errorException(exception);
	}
}

//function checkReturnDataFromDIH(data,status,xhr,element,name) {
//	if(null != data && "" != data){
//		alertBox('ERROR_COMPANY_NAME');
//		displayHtmlElement(element, '');
//	}	
//}

function SpecialMerchantConfig()
{
	var occu = $("[name='OCCUPATION']").val();
	var kfc = $("[name='KFC']").val();
	var selectObj = $("[name='SPECIAL_MERCHANT_TYPE']");
	
	if(selectObj != null)
	{
		var selectizeObj = selectObj[0].selectize;
		if(!(selectizeObj.isLocked))
		{
			if(occu == "เจ้าของกิจการที่จดทะเบียนพาณิชย์เท่านั้น" || occu == "กิจการตนเอง" )
			{		
				if(kfc == FLAG_YES)
				{
					//selectObj[0].selectize.disable(); //Defect#2179
					selectizeObj.setValue("30");
					selectizeObj.lock();
				}
				else
				{
					//Defect#3352
					//Remove choice Franschise from DropdownList if not KFC
					selectizeObj.removeOption('30');
				}
			}
			else
			{
				targetDisplayHtml('SPECIAL_MERCHANT_TYPE',MODE_VIEW,'SPECIAL_MERCHANT_TYPE','Y');
			}
		}
	}
}


