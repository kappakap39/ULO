function MAIN_CARD_NUMBERActionJS(){
	try{
		var className = 'com.eaf.orig.ulo.app.view.util.dih.CardHolderNameInformation';
		var $data =  $("#CARD_REQUEST_INFO_SUBFORM *").serialize();	
		ajax(className,$data, MAIN_CARD_NUMBERAfterActionJS);
	}catch(exception){
		errorException(exception);
	}
}
function MAIN_CARD_NUMBERAfterActionJS(data){
	try{
 		var subformId =$("#CARD_REQUEST_INFO_SUBFORM [name='subformId']").val(); 
 		var main_cardObj = $("[name='MAIN_CARD_NUMBER']").val();
 		var dataDesc = getDataMainCardNumber(data);
 		if(main_cardObj && (isEmpty(dataDesc) || undefined==dataDesc)){
	 		alertBox('ERROR_MAIN_CARD_NO_NOT_FOUND',focusSelectElementActionJS,"MAIN_CARD_NUMBER",DEFAULT_ALERT_BOX_WIDTH);
 		}else{
	 		refreshSubForm(subformId);
 		} 
	}catch(exception){
		errorException(exception);
	}
}
function ERROR_MAIN_CARD_NO_NOT_FOUNDAfterAlertActionJS(){
	
}

function getDataMainCardNumber(data){
	var desc_refCard="";
	try{
		var $values = $.parseJSON(data);	
		$.map($values, function(REFER_CARD_DESC){
			desc_refCard = REFER_CARD_DESC.value;
		});
	}catch(e){}
	return desc_refCard;
}
function ADD_CARD_REQUESTActionJS(){
	validateFormAction(VALIDATE_ADD_SUP_CARD_INFO,$("#CARD_REQUEST_INFO_SUBFORM *").serialize(), function() {
		try{
			var	className ='com.eaf.orig.ulo.app.view.form.subform.product.manual.AddCardRequestInfo';
			var $data =  $("#CARD_REQUEST_INFO_SUBFORM *").serialize();
			createRow(className,$data,ADD_CARD_REQUESTAfterActionJS);
		}catch(exception){
			errorException(exception);
		}
	});
}

function ADD_CARD_REQUESTAfterActionJS(){
	try{
		var subformId =$("#CARD_REQUEST_INFO_SUBFORM [name='subformId']").val(); 
		refreshSubForm(subformId);
	}catch(exception){
		errorException(exception);
	}
}
function DELETE_SUP_CARDActionJS(applicationRecordId){
	try{
		var subformId =$("#CARD_REQUEST_INFO_SUBFORM [name='subformId']").val();
		var $data = '&APP_RECORD_ID='+applicationRecordId;
		deleteRow('com.eaf.orig.ulo.app.view.form.subform.product.manual.DeleteCardRequestInfo',$data,refreshSubForm,subformId,'Y');
	}catch(exception){
		errorException(exception);
	}
}

function PRODUCTS_CARD_TYPEAfterActionJS() {
	try{
		var PRODUCT_TYPE = $("[name='PRODUCTS_CARD_TYPE']").val();
		listBoxFilterAction('CARD_TYPE',FIELD_ID_CARD_TYPE,'',CARD_TYPE_LISTBOX,'PRODUCT_TYPE='+PRODUCT_TYPE,'');
	}catch(exception){
		errorException(exception);
	}
}
function CARD_TYPEActionJS(){
	try{
		var CARD_CODE = $("[name='CARD_TYPE']").val();
		// call back CARD_TYPEAfterListBoxFilterActionJS method for set init CARD_LEVEL
		listBoxFilterAction('CARD_LEVEL',FIELD_ID_CARD_LEVEL,'',CARD_LEVEL_LISTBOX,'CARD_CODE='+CARD_CODE,CARD_TYPEAfterListBoxFilterActionJS);
	}catch(exception){
		errorException(exception);
	}
}

//set init CARD_LEVEL
function CARD_TYPEAfterListBoxFilterActionJS(elementId){
	try{
		var elementObject = $("[name='"+elementId+"']");
		var options = elementObject[0].selectize.options;
			for (var key in options) {
				 // set init selectbox CARD_LEVEL because  key is dynamic not have = 0 
				 elementObject[0].selectize.setValue(options[key].code);
				 break;				
			}
	}catch(exception){
		errorException(exception);
	}
}

function PRODUCTS_CARD_TYPEInitJS(){
	try{
		listBoxFilterAction('PRODUCTS_CARD_TYPE',FIELD_ID_PRODUCT_TYPE,'',PRODUCT_LISTBOX_FILTER,'','');
	}catch(exception){
		errorException(exception);
	}
}
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

function POPUP_SUPPLEMENTARY_FORM_KCC_2AfterSaveActionJS() {
	try{
		var subformId = $("#CARD_REQUEST_INFO_SUBFORM [name='subformId']").val();
		closePopupDialog('POPUP_SUPPLEMENTARY_FORM_KCC_2', POPUP_ACTION_SAVE);
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}