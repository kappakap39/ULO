function ADD_FIXED_BTNInitJS(){
}
function ADD_FIXED_BTNActionJS(){
	try{
		validateAccountSaveingAndCurrentAccount();
	}catch(exception){
		errorException(exception);
	}
}
function validateAccountSaveingAndCurrentAccount(){
	try{
		Pace.block();
		var acctNo = $("[name='ACCOUNT_NO']").val();
		var sub = $("[name='SUB']").val();
		if(acctNo == null || acctNo == "" || acctNo == undefined) {
			alertBox('ERROR_ENTER_ACCT_SUB');
		}else{
			var $data = 'acctNo='+acctNo+'&sub='+sub;
			ajax('com.eaf.orig.ulo.app.view.util.ajax.ValidateSavingAndCurrentAccount',$data, afterValidateAccountSavingAndCurrent);
		}
	}catch(exception){
		errorException(exception);
	}
}
function callToServiceCBS1215I01() {
	try{
		Pace.block();
		var acctNo = $("[name='ACCOUNT_NO']").val();
		var sub = $("[name='SUB']").val();
		if(acctNo == null || acctNo == "" || acctNo == undefined) {
			alertBox('ERROR_ENTER_ACCT_SUB');
		}else{
			var $data = 'acctNo='+acctNo+'&sub='+sub;
			ajax('com.eaf.orig.ulo.app.view.util.ajax.CallServiceCBS1215I01',$data, loadPopupFromService);			
		}
	}catch(exception){
		errorException(exception);
	}
}
function afterValidateAccountSavingAndCurrent(data){
	if(data != null && data != "" && data != undefined) {
		var obj = $.parseJSON(data);
		var statusCode = obj.statusCode
		var errorMsg = obj.errorMsg;
		if(BUSINESS_EXCEPTION == statusCode){
			alertBox(errorMsg);
		}else if(SUCCESS_CODE == statusCode){
			callToServiceCBS1215I01();
		}
	}
}
function loadPopupFromService(formID) {
	try{
		if(formID == null || formID == undefined || formID == "") {
			refreshMasterPopupForm();
		}else if(formID == SYSTEM_EXCEPTION){
			alertBox('ERROR_NOT_FOUND_FIX_ACCOUNT');
		}else{
			var rowID = "";
			var acctNo = $("[name='ACCOUNT_NO']").val();
			var sub = $("[name='SUB']").val();
			var $data = 'acctNo='+acctNo+'&sub='+sub;
			loadPopupDialog(formID,'INSERT',rowID,$data);
		}
	}catch(exception){
		errorException(exception);
	}
}

function POPUP_FIXED_GUARANTEE_FORMAfterSaveActionJS() {
	try{
		closeIncomeCategoryPopup();
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_FIXED_GUARANTEE_FORMMandatoryActionJS(response, formId) {
	try{
		var flag = $.parseJSON(response);
		if("true" == flag.NotFound) {
			var subformId = $('#FIXED_GUARANTEE_POPUP input[name="subformId"]').val();
			refreshSubForm(subformId,'N');
		}
	}catch(exception){
		errorException(exception);
	}
}
$(document).ready(function () {
	$("#FixedGuarantee").on("click", ".BtnRemove", deleteIncomeCategoryPopup);
});

function ACCOUNT_NOActionJS(element, mode, name) {

}