function ADD_SUP_PERSONAL_INFOActionJS(){
	try{
		var countSubPersonal = $('.LinkText_LOAD_SUP_PERSONAL_INFO').length;
		var $data  = '&REQ_PERSONAL_TYPE='+$("#LIST_SUP_PERSONAL_INFO_SUBFORM [name='PERSONAL_TYPE']").val();
			$data += '&FUNTION_SUPPLEMENT='+FUNCTION_PERSONAL_ADD;
			$data += '&REQ_PERSONAL_SEQ=';
			$data += '&REQ_PERSONAL_ID=';
		var handleForm = 'Y';
		var validateForm = 'N';
		validateFormAction(VALIDATE_CREATE_SUP_CARD_INFO,"count="+countSubPersonal, function() {
			loadNextTabAction('SUPPLEMENTARY_APPLICANT_FORM','INSERT','0',$data,handleForm,validateForm);
		});
	}catch(exception){
		errorException(exception);
	}
	
}
function ADD_PERSONAL_APPLICANT_INFOActionJS(){
	try{
		var REQ_PERSONAL_TYPE = $("#LIST_SUP_PERSONAL_INFO_SUBFORM [name='PERSONAL_TYPE']").val();
		if(isEmpty(REQ_PERSONAL_TYPE)){
			alertBox('ERROR_ADD_LIST_PERSONAL');
		}else{
			var $data  = '&REQ_PERSONAL_TYPE='+REQ_PERSONAL_TYPE;
				$data += '&REQ_PERSONAL_SEQ=';
				$data += '&REQ_PERSONAL_ID=';
			var handleForm = 'Y';
			var validateForm = 'N';
			validateFormAction(VALIDATE_ADD_PERSONAL_APPLICANT,$data, function() {
				loadNextTabAction('IA_PERSONAL_APPLICANT_FORM','INSERT','0',$data,handleForm,validateForm);
			});
		}
	}catch(exception){
		errorException(exception);
	}
}
function PERSONAL_TYPEActionJS(){
	
}
function LOAD_SUP_PERSONAL_INFOActionJS(PERSONAL_ID,PERSONAL_SEQ,PERSONAL_TYPE){
	try{
		var handleForm = 'Y';
		var validateForm = 'N';
		var $data  = '&REQ_PERSONAL_SEQ='+PERSONAL_SEQ;
			$data += '&REQ_PERSONAL_TYPE='+PERSONAL_TYPE;
			$data += '&REQ_PERSONAL_ID='+PERSONAL_ID;
		loadNextTabAction('SUPPLEMENTARY_APPLICANT_FORM','UPDATE',PERSONAL_SEQ,$data,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}
function LOAD_PERSONAL_APPLICANT_INFOActionJS(PERSONAL_ID,PERSONAL_SEQ,PERSONAL_TYPE){
	try{
		var handleForm = 'Y';
		var validateForm = 'N';
		var $data  = '&REQ_PERSONAL_SEQ='+PERSONAL_SEQ;
			$data += '&REQ_PERSONAL_TYPE='+PERSONAL_TYPE;
			$data += '&REQ_PERSONAL_ID='+PERSONAL_ID;
		loadNextTabAction('IA_PERSONAL_APPLICANT_FORM','UPDATE',PERSONAL_SEQ,$data,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
	
}
function DELETE_SUP_PERSONAL_INFOActionJS(PERSONAL_ID){
	try{
		var subformId =$("#LIST_SUP_PERSONAL_INFO_SUBFORM [name='subformId']").val();
		var $data = '&PERSONAL_ID='+PERSONAL_ID;
			$data += '&subformId='+subformId;
		deleteRow('com.eaf.orig.ulo.app.view.form.subform.product.manual.DeleteListSupPersonalInfo'
				,$data,DELETE_SUP_PERSONAL_INFOAfterActionJS);
	}catch(exception){
		errorException(exception);
	}
}
function DELETE_SUP_PERSONAL_INFOAfterActionJS(data){
	try{
		var listSupPersonalInfoSubformId =$("#LIST_SUP_PERSONAL_INFO_SUBFORM [name='subformId']").val();
		refreshSubForm(listSupPersonalInfoSubformId,'Y');
		var cardRequestInfoSubformId =$("#CARD_REQUEST_INFO_SUBFORM [name='subformId']").val();
		refreshSubForm(cardRequestInfoSubformId,'Y');
	}catch(exception){
		errorException(exception);
	}
}
function DELETE_PERSONAL_APPLICANT_INFOActionJS(PERSONAL_ID){
	try{
		var subformId =$("#LIST_SUP_PERSONAL_INFO_SUBFORM [name='subformId']").val();
		var $data = '&PERSONAL_ID='+PERSONAL_ID;
			$data += '&subformId='+subformId;
		deleteRow('com.eaf.orig.ulo.app.view.form.subform.product.manual.DeleteListSupPersonalInfo',$data,refreshSubForm);
	}catch(exception){
		errorException(exception);
	}
}