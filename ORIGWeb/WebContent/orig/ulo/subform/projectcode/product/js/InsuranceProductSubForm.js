
function ADD_PRODUCT_BTN_2ActionJS(){
	try{
		createRow('com.eaf.orig.ulo.app.view.util.ajax.AddInsuranceProduct','',refreshSubForm
				,'VERIFY_PRIVILEGE_DOC_PROJECT_CODE_SUBFORM','Y');
	}catch(exception){
		errorException(exception);
	}
}

function DELETE_PRODUCT_BTN2ActionJS(seq){
	try{
		var $data = '&SEQ='+seq;
		deleteRow('com.eaf.orig.ulo.app.view.util.ajax.DeleteInsuranceProduct',$data,refreshSubForm
				,'VERIFY_PRIVILEGE_DOC_PROJECT_CODE_SUBFORM','Y');
	}catch(exception){
		errorException(exception);
	}

}

function POLICY_NOInitJS(elementName,mode,displayName){
	try{
		$('[name="'+elementName+'"]').attr('onkeypress'
				,'NUMBEROnKeyPressAction("'+elementName+'","'+mode+'","'+displayName+'",event)');
	}catch(exception){
		errorException(exception);
	}
}
function POLICY_NOActionJS (){}