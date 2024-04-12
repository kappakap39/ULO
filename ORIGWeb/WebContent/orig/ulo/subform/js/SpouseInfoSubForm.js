$(document).ready(function(){
	 
});


function M_TITLE_DESCActionJS(element, mode, name) {
	try{
		var element_NAME = $("[name='"+element+"']").val();
		var M_TITLE_CODE = 'M_TITLE_CODE';
		var className = 'com.eaf.orig.ulo.app.view.form.manual.GetTitleName';
		var $data =  $("#SPOUSE_INFO_SUBFORM *").serialize();
			$data += '&TITLE_NAME='+element;
			$data += '&TITLE_CODE_NAME='+M_TITLE_CODE;
		ajax(className, $data, displayJSON);
	}catch(exception){
		errorException(exception);
	}
}