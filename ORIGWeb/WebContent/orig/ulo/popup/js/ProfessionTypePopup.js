
function POPUP_PROFESSION_TYPE_FORMAfterSaveActionJS(data) {
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
			var codeElementName =origElementId.replace('PROFESSION','PROFESSION_CODE'); 
			var codeObj = $("[name='"+codeElementName+"']");
			codeObj.val(obj.elementParam);
			codeObj.trigger("special-change");
		} else {
			$("[name='PROFESSION']").val(dataVal);
		}
		var formId = getPopupFormId();
		closePopupDialog(formId,POPUP_ACTION_SAVE);
	}catch(exception){
		closePopupDialog(getPopupFormId(),'');
		errorException(exception);
	}
}
function POPUP_PROFESSION_TYPE_FORMAfterCloseActionJS(action){
	var subformId = $('#OCCUPATION_SUBFORM').find('[name=subformId]').val();
	if(action == POPUP_ACTION_SAVE){
		refreshFocusFiledNextTabSubForm(subformId,"Y",CURRENT_POPUP_FIELD_NAME);
	}else{
		refreshSubForm(subformId,"N");
	}
}
function PROFESSION_TYPE_SELECT_OTHERInitJS() {
	try{
		var select ;
		var radios = $("[name='PROFESSION_TYPE_SELECT']:checked");    
		select = radios.val();
		
		if(PROFESSION_OTHER == select){
			targetDisplayHtml('PROFESSION_TYPE_SELECT_OTHER', MODE_EDIT, 'PROFESSION_TYPE_SELECT_OTHER');
		}else{
			targetDisplayHtml('PROFESSION_TYPE_SELECT_OTHER', MODE_VIEW, 'PROFESSION_TYPE_SELECT_OTHER');
			$("[name='PROFESSION_TYPE_SELECT_OTHER']").val('');
		}
	}catch(exception){
		errorException(exception);
	}
}
function PROFESSION_TYPE_SELECTActionJS(action){
	try{
		PROFESSION_TYPE_SELECT_OTHERInitJS();
	}catch(exception){
		errorException(exception);
	}
}