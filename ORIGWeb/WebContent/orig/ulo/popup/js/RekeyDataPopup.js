$(document).ready(function(){
});



//#rawi comment change to call fico before save application >> OrigApplicationForm.js
function REKEY_SENSITIVE_FORMAfterSaveActionJS(data) {
	try{
		var formId = getPopupFormId();
		$("#REKEY_SENSITIVE_FORM").off('copy paste');
		RightClick.EnableRightClick();
		if(responseSuccess(data)){
			closePopupDialog(formId,BUTTON_ACTION_SUBMIT);
		}else{
			var errorData = $.parseJSON(data);
			if(!isEmpty(errorData.responseFunction)){
				var responseFunction = errorData.responseFunction;
				if(REKEY_FUNCTION_POINT_APPLICANT_CIS_DUPLICATE==responseFunction || REKEY_FUNCTION_POINT_DIH_FAILED==responseFunction){
					displayHtmlElement('CIS_NUMBER','');
				}
			}			
			var $error = $.parseJSON(responseData);
			displayErrorMsg(formId,getDataFormObject($error,'ERROR'));
		}		
	}catch(exception){
		errorException(exception);
	}
}

function REKEY_TH_BIRTH_DATEActionJS(element,mode,name){
	try{
		var errorFlag = 'N';
		var TH_BIRTH_DATE = $("[name='"+element+"']").val();
		if(!isEmpty(TH_BIRTH_DATE)){
			var day = TH_BIRTH_DATE.split("/")[0];
			var month = TH_BIRTH_DATE.split("/")[1];
			var year = TH_BIRTH_DATE.split("/")[2];
			var EN_BIRTH_DATE = day + "/" + month + "/" + (parseInt(year) - 543);
			if (!validateCurrentDateToDate(TH_BIRTH_DATE, LOCAL_TH)) {
				errorFlag = 'Y';
				alertBox('ERROR_TH_BIRTH_DATE_MORETHAN_CURRENTDATE');
				displayHtmlElement(element,'');
			}
		}
	}catch(exception){
		errorException(exception);
	}
}

function BRANCH_RECEIVE_CARDActionJS(){
	var descriptionBranch = $("select[name='REKEY_BRANCH_RECEIVE_CARD']")[0].selectize.$input.text();
	displayHtmlElement('REKEY_BRANCH_RECEIVE_CARD_NAME', descriptionBranch);
}

function REKEY_DOCUMENT_PROVINCEActionJS(name,mode,element){
	callSerchAddressPopup(name,mode,element);
}
function REKEY_DOCUMENT_ZIPCODEActionJS(name,mode,element){
	callSerchAddressPopup(name,mode,element);
}
function REKEY_CURRENT_ZIPCODEActionJS(name,mode,element){
	callSerchAddressPopup(name,mode,element);
}
function REKEY_CURRENT_PROVINCEActionJS(name,mode,element){
	callSerchAddressPopup(name,mode,element);
}
function REKEY_COMPANY_PROVINCEActionJS(name,mode,element){
	callSerchAddressPopup(name,mode,element);
}
function REKEY_COMPANY_ZIPCODEActionJS(name,mode,element){
	callSerchAddressPopup(name,mode,element);
}
function callSerchAddressPopup(name,mode,element){
	try{
		if(MODE_EDIT == mode){
			var $data = "&ADDRESS_TYPE="+$('[name="'+name+'_REKEY_ADDRESS_TYPE"]').val();
				   $data+="&PERSONAL_SEQ="+$('[name="'+name+'_REKEY_PERSONAL_SEQ"]').val();
				   $data+="&PERSONAL_TYPE="+$('[name="'+name+'_REKEY_PERSONAL_TYPE"]').val();	
				   $data+="&IS_REKEY=Y";
				   $data+="&ADDRESS_ID="+$('[name="'+name+'_REKEY_ADDRESS_ID"]').val();			
				   $data+="&FUNCTION_POPUP="+SEARCH_BY_TAMBOL;		   
			loadPopupMasterAddress($data); 
		}
	}catch(exception){
		errorException(exception);
	}
}

function loadPopupMasterAddress(data){
	try{
		loadPopupDialog('SERACH_ADDRESS_ZIPCODE_FORM','INSERT','0',data);
		
	}catch(exception){
		errorException(exception);
	}
}

function REKEY_SENSITIVE_FORMAfterCloseActionJS(popupAction){
	try{
		if(popupAction == BUTTON_ACTION_SUBMIT){
//			var formId = $("#FormHandlerManager [name='formId']").val();
//			var action = BUTTON_ACTION_SUBMIT;
//			var handleForm = 'Y';
//			var validateForm = 'Y';
//			if(!authorizedApplicationAction(formId,action,handleForm,validateForm)){
//				applicationActionService(FICO_DECISION_ACTION,FicoDecisionAfterActionJS,formId,action,handleForm,validateForm);
//			}
			refreshMasterForm('SUMMIT_REKEY_SENSITIVE_FORM');
		}else{
			Pace.unblockFlag = true;
			Pace.unblock();
		}
	}catch(exception){
		errorException(exception);
	}
}
function SUMMIT_REKEY_SENSITIVE_FORMProcessActionJS(){
	try{
		var formId = $("#FormHandlerManager [name='formId']").val();
		var action = BUTTON_ACTION_SUBMIT;
		var handleForm = 'Y';
		var validateForm = 'Y';
		saveApplicationForm(formId,action,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}
function REKEY_ACCOUNT_NOActionJS(element, mode, name) {
	try{
		accountNoActionJs(element, mode, name);
	}catch(exception){
		errorException(exception);
	}
}
function REKEY_SUP_ACCOUNT_NOActionJS(element, mode, name) {
	try{
		accountNoActionJs(element, mode, name);
	}catch(exception){
		errorException(exception);
	}
}
function REKEY_TRANSFER_ACCOUNTActionJS(element, mode, name) {
	try{
		accountNoActionJs(element, mode, name);
	}catch(exception){
		errorException(exception);
	}
}
function REKEY_CALL_FOR_CASH_ACCOUNT_NOActionJS(element, mode, name) {
	try{
		accountNoActionJs(element, mode, name);
	}catch(exception){
		errorException(exception);
	}
}

function accountNoActionJs(element, mode, name){
	try{
		var productName =$('[name="PRODUCT_NAME_'+element+'"]').val();
		var data = '&isRekey=Y'+"&productName="+productName;
		accountValidation(element, mode, "ACCOUNT_NAME",data);
	}catch(exception){
		errorException(exception);
	}
}


function REKEY_BUSINESS_TYPEActionJS(element, mode, name) {
	try{
		var $data = '&origElementId='+element+"&origElementValue="+$('[name="'+element+'"]').val();
		loadPopupDialog('POPUP_BUSINESS_TYPE_FORM','INSERT','0',$data,'','600px');
	}catch(exception){
		errorException(exception);
	}
}
function REKEY_OCCUPATIONActionJS(element,mode,name) {
	try{
		var $data = '&origElementId='+element+"&origElementValue="+$('[name="'+element+'"]').val()+"&ElementNamePersonal="+element;
		loadPopupDialog('POPUP_OCCUPATION_FORM','INSERT','0',$data,'','600px');
	}catch(exception){
		errorException(exception);
	}	
}

function REKEY_PROFESSIONActionJS(element,mode,name) {
	try{
		var $data = '&origElementId='+element+"&origElementValue="+$('[name="'+element+'"]').val();
		loadPopupDialog('POPUP_PROFESSION_TYPE_FORM','INSERT','0',$data,'','600px');
	}catch(exception){
		errorException(exception);
	}
}
function REKEY_SUP_CARD_INFO_CARD_TYPEActionJS(element,mode,name){
	try{
		var CARD_CODE = $("[name='"+element+"']").val();
		var suffix = element.replace(name,"");
		listBoxFilterAction("REKEY_SUP_CARD_INFO_CARD_LEVEL"+suffix,FIELD_ID_CARD_LEVEL,'',CARD_LEVEL_LISTBOX,'CARD_CODE='+CARD_CODE,'');
		console.log("Card Type");
	}catch(exception){
		errorException(exception);
	}
}
function REKEY_PRODUCT_ELEMENT_CARD_TYPEActionJS(element,mode,name){
	try{
		var CARD_CODE = $("[name='"+element+"']").val();
		var suffix = element.replace(name,"");
		listBoxFilterAction("REKEY_PRODUCT_ELEMENT_CARD_LEVEL"+suffix,FIELD_ID_CARD_LEVEL,'',CARD_LEVEL_LISTBOX,'CARD_CODE='+CARD_CODE,'');
		console.log("Card Type");
	}catch(exception){
		errorException(exception);
	}
}
function REKEY_CC_CARD_TYPEActionJS(element,mode,name){
	try{
		var CARD_CODE = $("[name='"+element+"']").val();
		var suffix = element.replace(name,"");
		listBoxFilterAction("REKEY_CC_CARD_LEVEL"+suffix,FIELD_ID_CARD_LEVEL,'',CARD_LEVEL_LISTBOX,'CARD_CODE='+CARD_CODE,'');
		console.log("Card Type");
	}catch(exception){
		errorException(exception);
	}
}
function REKEY_ID_NOActionJS(element, mode, name) {
	try{
		var prefixElement = element.split("_");
		var prefix = prefixElement[prefixElement.length-2];
		prefix += "_"+prefixElement[prefixElement.length-1];
		var CID_TYPE = $("[name='CID_TYPE_"+prefix+"']").val();
		var objectElement = $("[name='REKEY_CID_TYPE_"+prefix+"']");
		if(!isEmpty(objectElement) && !isEmpty(ID_NO)){
			CID_TYPE = objectElement.val();
			if(isEmpty(CID_TYPE))
				alertBox('ERROR_MANDATORY_FIELD_CID_TYPE',emptyFocusElementActionJS, element);
		}
		var ID_NO = $("[name='"+element+"']").val();
		var errorFlag = 'N';
		if(!isEmpty(ID_NO)){
			if(CID_TYPE == CIDTYPE_IDCARD){
				var isNationalId = validateNationalId(ID_NO);
				if (!isNationalId) {
					errorFlag = 'Y';
					alertBox('ERROR_ID_NO_NATIONAL_ID_WRONG_FORMAT',emptyFocusElementActionJS, element);
				}
			}else{
				if(ID_NO.length < 2){
					errorFlag = 'Y';
					alertBox('ERROR_ID_NO_WRONG_FORMAT', emptyFocusElementActionJS,element);
				}
			}
		}
	}catch(exception){
		errorException(exception);
	}
}
function REKEY_KEC_CARD_TYPEActionJS(element,mode,name){
	try{
		var CARD_CODE = $("[name='"+element+"']").val();
		var suffix = element.replace(name,"");
		listBoxFilterAction("REKEY_KEC_CARD_LEVEL"+suffix,FIELD_ID_CARD_LEVEL,'',CARD_LEVEL_LISTBOX,'CARD_CODE='+CARD_CODE,'');
		console.log("Card Type");
	}catch(exception){
		errorException(exception);
	}
}
function REKEY_KPLActionJS(element,mode,name){
	try{
		var CARD_CODE = $("[name='"+element+"']").val();
		var CARD_CODE_VAL = CARD_CODE.split('_')[1];
		if(CARD_CODE_VAL != null && CARD_CODE_VAL != "" && CARD_CODE_VAL != undefined) {
			CARD_CODE = CARD_CODE_VAL;
		}
		var suffix = element.replace(name,"");
		listBoxFilterAction('REKEY_CARD_LEVEL'+suffix,FIELD_ID_CARD_LEVEL,'',CARD_LEVEL_LISTBOX,'REKEY_CARD_CODE='+CARD_CODE,'');
		console.log("Card Type");
	}catch(exception){
		errorException(exception);
	}
}
function REKEY_PLACE_RECEIVE_CARDActionJS(element,mode,name) {
	try{
		var branchReceiveCardName = "REKEY_BRANCH_RECEIVE_CARD"+element.replace(name,'');
		var PLACE_RECEIVE_CARD = $("[name='"+element+"']").val();
		if(PLACE_RECEIVE_CARD == PLACE_RECEIVE_CARD_MAJOR){
			targetDisplayHtml(branchReceiveCardName,MODE_EDIT,"REKEY_BRANCH_RECEIVE_CARD");
		}else{
			targetDisplayHtml(branchReceiveCardName,MODE_VIEW,"REKEY_BRANCH_RECEIVE_CARD","Y");
			displayHtmlElement(branchReceiveCardName, '');
		}
	}catch(exception){
		errorException(exception);
	}
}

function REKEY_PAYMENT_METHODActionJS(element,mode,name){
	try{
		var PAYMENT_METHOD = $("[name='"+element+"']").val();
		var ratioObjName = "REKEY_PAYMENT_RATIO"+element.replace(name,'');
		var accNoObjName = "REKEY_ACCOUNT_NO"+element.replace(name,'');
		if(PAYMENT_METHOD == PAYMENT_METHOD_DEPOSIT_ACCOUNT) {
			targetDisplayHtml(ratioObjName,MODE_EDIT,"REKEY_PAYMENT_RATIO","Y");
			displayHtmlElement(ratioObjName, '');
			targetDisplayHtml(accNoObjName,MODE_EDIT,"REKEY_ACCOUNT_NO","Y");
			displayHtmlElement(accNoObjName, '');
		} else {
			targetDisplayHtml(ratioObjName,MODE_VIEW,"REKEY_PAYMENT_RATIO","Y");
			displayHtmlElement(ratioObjName, '');
			targetDisplayHtml(accNoObjName,MODE_VIEW,"REKEY_ACCOUNT_NO","Y");
			displayHtmlElement(accNoObjName, '');
		}
	}catch(exception){
		errorException(exception);
	}
}
function REKEY_POLICY_EXCEPTION_AUTHORIZED_BYActionJS(){
	
}

$(function() {
	/*
	 * If you want to show-up message on right click
	 * RightClick.DisableRightClick('Hello');
	 */ 
	RightClick.DisableRightClick();
	$("#REKEY_SENSITIVE_FORM").on('copy paste', function(e) {
	    e.preventDefault();
	    return false;
	});
	$("input[name='REKEY_OCCUPATION_CODE']").on("special-change", function () {
		try{
			var elementName = $(this).attr("name");
			var OCCUPATION_CODE = $(this).val();
			var POSITION_CODE = "REKEY_POSITION_CODE"+elementName.replace('REKEY_OCCUPATION_CODE','');
			var POSITION_LEVEL = "REKEY_POSITION_LEVEL"+elementName.replace('REKEY_OCCUPATION_CODE','');
			if(OCCUPATION_CODE == GOVERNMENT_OFFICER) {
				targetDisplayHtml(POSITION_CODE,MODE_EDIT,"REKEY_POSITION_CODE","Y");
				displayHtmlElement(POSITION_CODE, '');
				
				targetDisplayHtml(POSITION_LEVEL,MODE_VIEW,"REKEY_POSITION_LEVEL","Y");
				displayHtmlElement(POSITION_LEVEL, '');
			} else {
				targetDisplayHtml(POSITION_CODE,MODE_VIEW,"REKEY_POSITION_CODE","Y");
				displayHtmlElement(POSITION_CODE, '');
				
				targetDisplayHtml(POSITION_LEVEL,MODE_EDIT,"REKEY_POSITION_LEVEL","Y");
				displayHtmlElement(POSITION_LEVEL, '');
			}
		}catch(exception){
			errorException(exception);
		}
	});
});
