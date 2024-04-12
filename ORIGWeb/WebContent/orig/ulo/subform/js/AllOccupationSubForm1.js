function OCCUPATIONActionJS(){
	try{
		if($('[name="OCCUPATION"]').val()=='01'){
			var labelField = $('#LabelField_LEVEL').text();
			$('#LabelField_LEVEL').html(labelField.replace(':','<span class="require">*</span>:'));
		}else{
			$('#LabelField_LEVEL').find('.require').remove();
		}
	}catch(exception){
		errorException(exception);
	}
}

function PROFESSIONActionJS(){
	try{
		if($('[name="PROFESSION"]').val()=='OTH'){
			$('[name="PROFESSION_OTHER"]').before('<span class="require">*</span>');		 
		}else{
			$('#InputField_PROFESSION_OTHER').find('.require').remove();
		}
	}catch(exception){
		errorException(exception);
	}
}


function POSITION_CODEActionJS(){
	try{
		if($('[name="POSITION_CODE"]').val()=='OTH'){
			$('[name="POSITION_CODE"]').before('<span class="require">*</span>');		 
		}else{
			$('#InputField_POSITION_CODE').find('.require').remove();
		}
	}catch(exception){
		errorException(exception);
	}
}

function validateMonth(obj){
	try{
		if(obj.value>11){
			alert('\u0E2D\u0E32\u0E22\u0E38\u0E07\u0E32\u0E19 (\u0E40\u0E14\u0E37\u0E2D\u0E19) \u0E08\u0E30\u0E15\u0E49\u0E2D\u0E07\u0E21\u0E35\u0E04\u0E48\u0E32 0-11');
			obj.value='';
		}
	}catch(exception){
		errorException(exception);
	}
}