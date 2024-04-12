
function loadCraditCardInfoActionJS(formId,uniqueId,width,height){
	try{
		var $data = 'uniqueId='+uniqueId;
		if(formId != ''){
			loadPopupDialog(formId,'INSERT','0',$data,null,width,height);
		}
	}catch(exception){
		errorException(exception);
	}
}
function deleteCraditCardActionJS(uniqueId,applicationType){
	try{
		var subformId =$("#PRODUCT_FORM [name='subformId']").val(); 
		var handleForm = 'Y';
		var $data  = 'uniqueId='+uniqueId;
			$data += '&APPLICATION_CARD_TYPE='+applicationType;
		var	className ='com.eaf.orig.ulo.app.view.form.subform.product.manual.DeleteProductInfo';
		deleteRow(className,$data,callBackAddSupCraditCardActionJS,subformId,handleForm);
	}catch(exception){
		errorException(exception);
	}	
}

var uniqueIdTmp;
function createSupCraditCardActionJS(uniqueId){
	try{
		uniqueIdTmp = uniqueId;
		var validateId = VALIDATE_ADD_SUP_CARD_INFO;
		var $data = $('#PRODUCT_FORM *').serialize();
			$data +='&uniqueId='+uniqueId;
		validateFormAction(validateId,$data,createSupCraditCardsetPro);
	}catch(exception){
		errorException(exception);
	}	
}

function createSupCraditCardsetPro(){
	try{
		var subformId =$("#PRODUCT_FORM [name='subformId']").val();
		setPropertiesSubform(subformId,createRowSupCArdActionJS);	
	}catch(exception){
		errorException(exception);
	}	
}


function createRowSupCArdActionJS(){
	try{
		var functionId = 'CREATE_CC_SUPCARD_INFO';
		var $data  = 'uniqueId='+uniqueIdTmp;
			$data += '&functionId='+functionId;
		var	className ='com.eaf.orig.ulo.app.view.form.subform.product.manual.AddCardInfo';
		createRow(className,$data,callBackAddSupCraditCardActionJS);
	}catch(exception){
		errorException(exception);
	}	
}

function callBackAddSupCraditCardActionJS(data){
	try{
		refreshSubForm('SUP_PRODUCT_FORM','Y');
	}catch(exception){
		errorException(exception);
	}
}

function POPUP_AMWAY_FORM_KCC_SUPAfterSaveActionJS(){
	try{
		var subformId = $("#SUP_CREDIT_CARD_SUBFORM [name='subformId']").val();
		closePopupDialog('POPUP_AMWAY_FORM_KCC_SUP',POPUP_ACTION_SAVE);
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_AMWAY_PRODUCT_FORM_KCCAfterSaveActionJS(){
	try{
		closePopupDialog('POPUP_AMWAY_PRODUCT_FORM_KCC',POPUP_ACTION_SAVE);
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_SUPPLEMENTARY_FORM_KCCAfterSaveActionJS(){
	try{
		closePopupDialog('POPUP_SUPPLEMENTARY_FORM_KCC',POPUP_ACTION_SAVE);
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_SUPPLEMENTARY_FORM_KCC_2AfterSaveActionJS(){
	try{
		var subformId = $("#SUP_CREDIT_CARD_SUBFORM [name='subformId']").val();
		closePopupDialog('POPUP_SUPPLEMENTARY_FORM_KCC_2',POPUP_ACTION_SAVE);
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}




