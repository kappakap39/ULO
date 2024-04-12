/**
 * #SeptemWi
 */
$(document).ready(function (){
	SensitiveEngine('#fraudcomp-decision');
});
function SaveFraudCompany(){
	if(MandatoryFraudCompany()){
		var dataString = 'fraudcomp-decision='+$('#fraudcomp-decision').val();
		$AjaxFrontController('SaveFraudCompanyWebAction','N',null,dataString,CloseFraudCompanyPopup);
	}
}
function MandatoryFraudCompany(){	
	if($('#fraudcomp-decision').val() == ''){
		$('#div-fraud-madatory').append('<div>'+FRAUD_COMPANY_ERROR+'</div>');
		return false;
	}
	return true;
}
function CloseFraudCompanyPopup(data){
	$dialog.dialog("close");	
	closeDialog();
}
