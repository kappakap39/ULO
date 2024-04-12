var debouncedCreateProductInfoActionJS = debounce(function(dom){
	createProductInfoActionJS(dom);
},500);

function createProductInfoActionJS(dom){
	if(dom){
		$(dom).prop('disabled', true);
		setTimeout(function(){
			$(dom).prop('disabled', false);//case something's gone wrong
		},3000);
	}

	
	Pace.block();
	try{
		var validateId = VALIDATE_CREATE_CARD_INFO;
		var $data = $('#PRODUCT_FORM *').serialize(); 
		validateFormAction(validateId,$data,$.proxy(createRowProductInfoAfterValidateFormActionJS,dom));
	}catch(exception){
		errorException(exception);
	}
}
function refreshProductInfoFormActionJS(){
	Pace.block();
	try{
		if(manualRefreshSubForm('PRODUCT_FORM','Y')){
			var referenceSubformId = $("#REFERENCE_PERSON_SUBFORM [name='subformId']").val();
			if(referenceSubformId != undefined){
				manualRefreshSubForm(referenceSubformId,"Y");	
			}	
		}
	}catch(exception){
		errorException(exception);
	}
//	Pace.unblockFlag = true;
//	Pace.unblock();
}
function createRowProductInfoAfterValidateFormActionJS(){
	try{
		var subformId =$("#PERSONAL_INFO_SUBFORM [name='subformId']").val();
		setPropertiesSubform(subformId,createRowProductInfoActionJS);
		$(this).prop('disabled', false);//case something's gone wrong
	}catch(exception){
		errorException(exception);
	}	
}

function createRowProductInfoActionJS(){
	Pace.block();
	try{
		var subformId =$("#PRODUCT_FORM [name='subformId']").val(); 
		var PRODUCT_TYPE = $("[name='PRODUCTS_CARD_TYPE']").val();
		var CARD_TYPE = $("[name='CARD_TYPE']").val();
		var CARD_LEVEL = $("[name='CARD_LEVEL']").val();
		var handleForm = 'Y';
		var functionId = 'CREATE_PRODUCT_INFO';
		var	className ='com.eaf.orig.ulo.app.view.form.subform.product.manual.AddCardInfo';
		var $data = 'PRODUCTS_CARD_TYPE='+PRODUCT_TYPE;
			$data += '&CARD_TYPE='+CARD_TYPE;
			$data += '&CARD_LEVEL='+CARD_LEVEL;
			$data += '&APPLICATION_CARD_TYPE='+APPLICATION_CARD_TYPE_BORROWER;		
			$data += '&functionId='+functionId;
		createRow(className,$data,refreshProductInfoFormActionJS,subformId,handleForm);
	}catch(exception){
		errorException(exception);
	}
}
function PRODUCTS_CARD_TYPEActionJS(){
	try{
		var PRODUCT_TYPE = $("[name='PRODUCTS_CARD_TYPE']").val();
		if(ROLE_DE1_1 == ROLE_ID)
		{
			listBoxFilterAction('CARD_TYPE',FIELD_ID_CARD_TYPE,'','CARD_TYPE_LISTBOX_ACTIVE_ONLY','PRODUCT_TYPE='+PRODUCT_TYPE,'');
		}
		else
		{
			listBoxFilterAction('CARD_TYPE',FIELD_ID_CARD_TYPE,'',CARD_TYPE_LISTBOX,'PRODUCT_TYPE='+PRODUCT_TYPE,'');
		}
		if(PRODUCT_TYPE == PRODUCT_CODE_KEC){
			targetDisplayHtml('CARD_LEVEL',MODE_VIEW,'CARD_LEVEL','Y');
			targetDisplayHtml('CARD_TYPE',MODE_EDIT,'CARD_TYPE','Y');
		}else if(PRODUCT_TYPE == PRODUCT_CODE_PLG || PRODUCT_TYPE == PRODUCT_CODE_PLT || PRODUCT_TYPE == PRODUCT_CODE_PLP){
			targetDisplayHtml('CARD_TYPE',MODE_VIEW,'CARD_TYPE','Y');
			targetDisplayHtml('CARD_LEVEL',MODE_VIEW,'CARD_LEVEL','Y');
		}else{
			targetDisplayHtml('CARD_TYPE',MODE_EDIT,'CARD_TYPE','Y');
			targetDisplayHtml('CARD_LEVEL',MODE_EDIT,'CARD_LEVEL','Y');
		}	
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

