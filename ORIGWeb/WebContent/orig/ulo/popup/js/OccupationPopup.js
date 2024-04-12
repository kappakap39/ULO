function POPUP_OCCUPATION_PERSONAL_FORMAfterSaveActionJS(data){
	try{
		POPUP_OCCUPATION_FORMAfterSaveActionJS(data);
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_OCCUPATION_FORMAfterSaveActionJS(data) {
	try{
		var obj = $.parseJSON(data);
		var origElementId = obj.elementId;
		var dataVal = obj.elementValue;
		if(dataVal == undefined) {
			dataVal = "";
		}
		var parentForm = obj.formName;
		if(parentForm != undefined && REKEY_FORM == parentForm) {
			try{
				var elementObj = $("[name='"+origElementId+"']");
				elementObj.val(dataVal);
				var codeElementName =origElementId.replace('OCCUPATION','OCCUPATION_CODE'); 
				var codeObj = $("[name='"+codeElementName+"']");
				codeObj.val(obj.elementParam);
//				codeObj.trigger("special-change");
			}catch(e){
			}		
			var formId = getPopupFormId();
			closePopupDialog(formId,'');
			
		} else {
			$("[name='OCCUPATION']").val(dataVal);
			var formId = getPopupFormId();
			closePopupDialog(formId,POPUP_ACTION_SAVE);
		
		}
		
//		#rawi comment change to used getPopupFormId() function for get formId 's popup
//		var formId = $("#PopupFormHandlerManager [name='formId']").val();
//		var formId = getPopupFormId();
//		closePopupDialog(formId,POPUP_ACTION_SAVE);
	}catch(exception){
		closePopupDialog(getPopupFormId(),'');
		errorException(exception);
	}
}
function POPUP_OCCUPATION_FORMAfterCloseActionJS(action){
	try{var subformId = $('#OCCUPATION_SUBFORM').find('[name=subformId]').val();
		if(action == POPUP_ACTION_SAVE){
			// Refreshing sub form					
			refreshFocusFiledNextTabSubForm(subformId,"Y",CURRENT_POPUP_FIELD_NAME);
		}
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_OCCUPATION_PERSONAL_FORMAfterCloseActionJS(action){
	try{
		var subformId = $('#OCCUPATION_SUBFORM').find('[name=subformId]').val();
		if(action == POPUP_ACTION_SAVE){
			// Refreshing sub form
			var subformIdManual = $('#PERSONAL_INFO_SUBFORM').find('[name=subformId]').val();
				var referenceSubformId = $("#ADDRESS_SUBFORM [name='subformId']").val();
				if(referenceSubformId != undefined){
					refreshSubForm(referenceSubformId,"Y");
				}	
			refreshSubForm(subformId,"Y");
			autoTabNextElement(CURRENT_POPUP_FIELD_NAME);
		}else{
			refreshSubForm(subformId,"N");
		}
	}catch(exception){
		errorException(exception);
	}
}
function OCCUPATION_SELECT_OTHERInitJS() {
	try{
		var select ;
		var radios = $("[name='OCCUPATION_SELECT']:checked");    
	    select = radios.val();
		
		if(OCCUPATION_OTHER == select){
			targetDisplayHtml('OCCUPATION_SELECT_OTHER', MODE_EDIT, 'OCCUPATION_SELECT_OTHER');
		}else{
			targetDisplayHtml('OCCUPATION_SELECT_OTHER', MODE_VIEW, 'OCCUPATION_SELECT_OTHER');
			$("[name='OCCUPATION_SELECT_OTHER']").val('');
		}
	}catch(exception){
		errorException(exception);
	}
}
function OCCUPATION_SELECTActionJS(action){
	try{
		OCCUPATION_SELECT_OTHERInitJS();
	}catch(exception){
		errorException(exception);
	}
}
