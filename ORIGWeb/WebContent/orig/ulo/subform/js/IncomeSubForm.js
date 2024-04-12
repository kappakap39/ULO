$(document).ready(function(){
	try{
		$('[name="MONTHLY_INCOME"]').blur(function(){MONTHLY_INCOMEInitJS();});
	}catch(exception){
		errorException(exception);
	}
});

function MONTHLY_INCOMEInitJS(){
	try{
		$('#LabelField_OTHER_INCOME').find('.require').remove();
		if($('[name="MONTHLY_INCOME"]').val()>0){
			var textField = $('#LabelField_OTHER_INCOME').text();
			$('#LabelField_OTHER_INCOME').html(textField.replace(':','<span class="require">*</span>:'));
		} 
	}catch(exception){
		errorException(exception);
	}
}

function SRC_OTH_INC_BONUSActionJS(){
 
} 
function SRC_OTH_INC_COMMISSIONActionJS(){
	 
}
function SRC_OTH_INC_OTHERActionJS(){
  
}

function RECEIVE_INCOME_METHODActionJS(element, mode, name){
	if(mode == MODE_EDIT){
		RECEIVE_INCOME_BANKInitJS('RECEIVE_INCOME_BANK',MODE_EDIT,'RECEIVE_INCOME_BANK');
	}
}

function RECEIVE_INCOME_BANKInitJS(element, mode, name){
	if(mode == MODE_EDIT){
		var RECEIVE_INCOME_VALUE = $("[name='RECEIVE_INCOME_METHOD']").val();
		if(RECEIVE_INCOME_METHOD_CASH_CHEQUE == RECEIVE_INCOME_VALUE){
			targetDisplayHtml(element,MODE_VIEW,name,'Y');	
		}else{
			targetDisplayHtml(element,MODE_EDIT,name,'N');
		}
	}
}
 