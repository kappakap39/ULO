function BTN_ADD_FDRInitJS(element, mode, name) {
	try{
		var row = element.replace(name, "");
		var show_flag = $("[name='SHOW_DETAIL"+row+"']").val();
		if(FLAG_YES == show_flag) {
			$("[name='ADD_FDR"+row+"']").show();
			$("[name='"+element+"']").hide();
			$("[name='BTN_DEL_FDR"+row+"']").show();
		}
	}catch(exception){
		errorException(exception);
	}
}
function BTN_ADD_FDRActionJS(element, mode, name) {
	try{
		var row = element.replace(name, "");
		$("[name='ADD_FDR"+row+"']").show();
		$("[name='"+element+"']").hide();
		$("[name='BTN_DEL_FDR"+row+"']").show();
	}catch(exception){
		errorException(exception);
	}
}
function BTN_DEL_FDRActionJS(element, mode, name) {
	try{
		var subformId = $('#DOCUMENT_CHECK_LIST input[name="subformId"]').val();
		var headerFormTemplate = $('#DOCUMENT_CHECK_LIST input[name="headerFormTemplate"]').val();
		var elementObjectId = element.replace(name+"_", "");
		var $data = '&elementObjectId='+elementObjectId+"&subFormId=" + subformId;
		var className = 'com.eaf.orig.ulo.app.view.util.ajax.DeleteFolowedDocReason';
		if(headerFormTemplate == undefined || headerFormTemplate == "") {
			deleteRow(className, $data, refreshSubForm, subformId,'Y');
		} else {
			deleteRow(className, $data, BTN_DEL_DOCUMENT_REASONAfterActionJS,subformId,'Y','Y');
		}
	}catch(exception){
		errorException(exception);
	}
}
function BTN_DEL_DOCUMENT_REASONAfterActionJS(data){
	try{
		refreshHeaderFormTemplate('DOCUMENT_HEADER_FORM');
	}catch(exception){
		errorException(exception);
	}
}
