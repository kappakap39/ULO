function REF_TITLE_DESCActionJS(element, mode, name) {
	try{
		var element_NAME = $("[name='REF_PERSON']").val();
		var TH_TITLE_CODE = 'TH_TITLE_CODE_'+element_NAME;
		var className = 'com.eaf.orig.ulo.app.view.form.manual.GetTitleName';
		var $data =  $("#REFERENCE_PERSON_SUBFORM *").serialize();
			$data += '&TITLE_NAME='+element;
			$data += '&TITLE_CODE_NAME='+TH_TITLE_CODE;
		ajax(className, $data, displayJSON);
	}catch(exception){
		errorException(exception);
	}
}