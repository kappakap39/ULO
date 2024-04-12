function POPUP_BUNDLE_HL_FORMAfterSaveActionJS() {
	try{
		closeIncomeCategoryPopup();
	}catch(exception){
		errorException(exception);
	}
}

function APPROVAL_DATEActionJS(element, mode, name) {
	try{
		var APPROVAL_DATE = $("[name='"+element+"']").val();
		if(!validateBetweenDate(APPROVAL_DATE,TH_CURRENT_DATE)){
			$("[name='"+element+"']").val("");
			alertBox('APPROVAL_DATE_ERROR');
		}
	}catch(exception){
		errorException(exception);
	}
}

function BUNDLING_HL_ACCOUNT_NOActionJS(element,mode,name){
	try{
		if(mode == MODE_EDIT){
			var subformId = $("#BUNDLING_HL_POPUP [name='subformId']").val();
			var className = 'com.eaf.orig.ulo.app.view.util.ajax.ValidateBundleAccountNo';
			var $data  = $('#SECTION_'+subformId+' *').serialize();
				$data += '&elementFieldId='+element;
				$data += '&'+element+'='+$("[name='"+element+"']").val();
				$data += '&TYPE_LIMIT='+ACCOUNT_TYPE_LOAN;
				$data += '&BUNDLE_TYPE='+INC_TYPE_BUNDLE_HL;
			ajax(className,$data,BUNDLING_HL_ACCOUNT_NOAfterAjaxActionJS);
		}
	}catch(exception){
		errorException(exception);
	}
	
}

function BUNDLING_HL_ACCOUNT_NOAfterAjaxActionJS(data){
	try{
		var $json = $.parseJSON(data);
		if(!isEmpty($json.errorMsg)){
			alertBox($json.errorMsg,focusSelectElementActionJS,'BUNDLE_ACCOUNT_NO');
		}
		var accountName = ($json.accountName)?$json.accountName:'';
		var accountDate = ($json.accountDate)?$json.accountDate:'';
		displayHtmlElement('ACCOUNT_NAME',accountName);
		displayHtmlElement('ACCOUNT_DATE',accountDate);
	}catch(exception){
		errorException(exception);
	}
}