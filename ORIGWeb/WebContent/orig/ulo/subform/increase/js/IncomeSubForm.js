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