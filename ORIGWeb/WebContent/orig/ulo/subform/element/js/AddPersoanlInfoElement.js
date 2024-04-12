function BTN_ADD_FDRActionJS(element, mode, name) {
	try{
		var row = element.replace(name, "");
//		document.getElementById("ADD_FDR"+row).style.display = "inline";
		$("[name='ADD_FDR"+row+"']").show();
		$("[name='"+element+"']").hide();
		$("[name='BTN_DEL_FDR"+row+"']").show();
	}catch(exception){
		errorException(exception);
	}
}
function BTN_DEL_FDRActionJS(element, mode, name) {
	try{
		var row = element.replace(name, "");
//		document.getElementById("ADD_FDR"+row).style.display = "inline";
		$("[name='ADD_FDR"+row+"']").hide();
		$("[name='"+element+"']").hide();
		$("[name='BTN_ADD_FDR"+row+"']").show();
//		$("[name='FOLLOWED_DOC_REASON_SELECT_001']").attr('checked',false);
		targetDisplayHtml('FOLLOWED_DOC_REASON_SELECT'+row, MODE_EDIT, 'FOLLOWED_DOC_REASON_SELECT'+row,'Y');
	}catch(exception){
		errorException(exception);
	}
}