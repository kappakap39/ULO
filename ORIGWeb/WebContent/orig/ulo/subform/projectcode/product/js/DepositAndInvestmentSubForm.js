
function ADD_PRODUCT_BTNActionJS(){
	try{
		createRow('com.eaf.orig.ulo.app.view.util.ajax.AddDepositAndInvestment','',refreshSubForm,'VERIFY_PRIVILEGE_DOC_PROJECT_CODE_SUBFORM','Y');
//		VERIFY_PRIVILEGE_PRODUCT_PROJECT_CODE_SUBFORM
	}catch(exception){
		errorException(exception);
	}
} 
function DELETE_PRODUCT_BTNActionJS(seq){
	try{
		var $data = '&SEQ='+seq;
		deleteRow('com.eaf.orig.ulo.app.view.util.ajax.DeleteDepositAndInvestment',$data,refreshSubForm,'VERIFY_PRIVILEGE_DOC_PROJECT_CODE_SUBFORM','Y');
	}catch(exception){
		errorException(exception);
	}
}