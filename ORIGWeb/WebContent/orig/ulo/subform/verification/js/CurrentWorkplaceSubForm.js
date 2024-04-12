function EDIT_BTNActionJS(){
	try{
		var className="com.eaf.orig.ulo.app.view.util.ajax.DE1_2IncomeDisplayMode";
		var data="";
		ajax(className,data,refreshMasterForm);
	}catch(exception){
		errorException(exception);
	}
}
function EXECUTE_BTNActionJS(){
	try{
		var functionId = DECISION_IMPLEMENT_ACTION_OCCUPATION;
		var formId = $("#FormHandlerManager [name='formId']").val();
		applicationActionService(functionId,DECISION_OCCUPATIONAfterActionJS,formId);
	}catch(exception){
		errorException(exception);
	}
}
function DECISION_OCCUPATIONAfterActionJS() {
	try{
		var formId = $("#FormHandlerManager [name='formId']").val();
		refreshMasterForm(formId);
	}catch(exception){
		errorException(exception);
	}
}
function OCCUPATIONActionJS(element,mode,name) {
	try{
		if(mode == MODE_EDIT) {
			var $data = '&PERSONAL_SEQ='+$('[name="VERIFICATION_PERSONAL_SEQ"]').val();
			loadPopupDialog('POPUP_OCCUPATION_FORM','INSERT','0',$data,"","");
		}
	}catch(exception){
		errorException(exception);
	}
}

function PROFESSIONActionJS(element,mode,name) {
	try{
		if(mode == MODE_EDIT) {
			var $data = '&PERSONAL_SEQ='+$('[name="VERIFICATION_PERSONAL_SEQ"]').val();
			$data +='&PROFESSION_TYPE='+$('[name="PROFESSION"]').val();
			loadPopupDialog('POPUP_PROFESSION_TYPE_FORM','INSERT','0',$data,"","");
		}
	}catch(exception){
		errorException(exception);
	}
}