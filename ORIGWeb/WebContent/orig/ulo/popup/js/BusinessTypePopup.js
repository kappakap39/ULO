
function POPUP_BUSINESS_TYPE_FORMAfterSaveActionJS(data) {	
	try{
		var obj = $.parseJSON(data);
		var origElementId = obj.elementId;
		var dataVal = obj.elementValue;
		if(dataVal == undefined) {
			dataVal = "";
		}
		var parentForm = obj.formName;
		if(parentForm != undefined && REKEY_FORM == parentForm) {
			var elementObj = $("[name='"+origElementId+"']");
			elementObj.val(dataVal);
			var codeElementName =origElementId.replace('BUSINESS_TYPE','BUSINESS_TYPE_CODE'); 
			var codeObj = $("[name='"+codeElementName+"']");
			codeObj.val(obj.elementParam);
		} else {
			$("[name='BUSINESS_TYPE']").val(dataVal);
		}
		closePopupDialog('POPUP_BUSINESS_TYPE_FORM',POPUP_ACTION_SAVE);
	}catch(exception){
		closePopupDialog(getPopupFormId(),'');
		errorException(exception);
	}
}
function POPUP_BUSINESS_TYPE_FORMAfterCloseActionJS(action){ //alert("refresh");
	var subformId = $('#OCCUPATION_SUBFORM').find('[name=subformId]').val();
	if(action == POPUP_ACTION_SAVE){
		refreshFocusFiledNextTabSubForm(subformId,"Y",CURRENT_POPUP_FIELD_NAME);
	}else{
		//Defect FUT.NO 404
//		refreshSubForm(subformId,"N");
	}
	
}
function BUSINESS_TYPE_SELECT_OTHERInitJS() {
	try{
		var select ;
		var radios = $("[name='BUSINESS_TYPE_SELECT']:checked");    
		select = radios.val();

		if(BUSINESS_TYPE_OTHER == select){
			targetDisplayHtml('BUSINESS_TYPE_SELECT_OTHER', MODE_EDIT, 'BUSINESS_TYPE_SELECT_OTHER');
		}else{
			targetDisplayHtml('BUSINESS_TYPE_SELECT_OTHER', MODE_VIEW, 'BUSINESS_TYPE_SELECT_OTHER');
			$("[name='BUSINESS_TYPE_SELECT_OTHER']").val('');
		}
	}catch(exception){
		errorException(exception);
	}
}
function BUSINESS_TYPE_SELECTActionJS(action){
	try{
		BUSINESS_TYPE_SELECT_OTHERInitJS();
	}catch(exception){
		errorException(exception);
	}	
}