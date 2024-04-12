var functionId = 'CREATE_CC_SUPCARD_INFO';
var debouncedCreateProductInfoActionJS = debounce(function(dom){
	createProductInfoActionJS(dom);
}, 500);

function createProductInfoActionJS(dom){
	
	if(dom){
		$(dom).prop('disabled', true);
		setTimeout(function(){
			$(dom).prop('disabled', false);//case something's gone wrong
		},3000);
	}

	try{
		var validateId = VALIDATE_CREATE_CARD_INFO;
		var uniqueId = $("[name=uniqueId]").val();
		var subformId = $('#SUP_PRODUCT_FORM input[name=subformId]').val();
		var PRODUCT_TYPE = $("[name='PRODUCTS_CARD_TYPE']").val();
		var CARD_TYPE = $("[name='CARD_TYPE']").val();
		var CARD_LEVEL = $("[name='CARD_LEVEL']").val();
		var $data = 'subformId='+subformId;
			$data += '&PRODUCTS_CARD_TYPE='+PRODUCT_TYPE;
			$data += '&CARD_TYPE='+CARD_TYPE;
			$data += '&CARD_LEVEL='+CARD_LEVEL;
			$data += '&functionId='+functionId;
			$data += '&uniqueId='+ uniqueId;
			console.log("data : "+$data);
		validateFormAction(validateId,$data,$.proxy(createRowProductInfoAfterValidateFormActionJS,dom));//proxy execution context for further use
	}catch(exception){
		errorException(exception);
	}
}
function refreshProductInfoFormActionJS(){
	try{
		refreshSubForm('SUP_PRODUCT_FORM','Y');
	}catch(exception){
		errorException(exception);
	}
}
function createRowProductInfoAfterValidateFormActionJS(){
	try{
		var subformId =$("#PERSONAL_INFO_SUBFORM [name='subformId']").val();
		setPropertiesSubform(subformId,createRowProductInfoActionJS);
		$(this).prop('disabled', false); //synchronize submit button
	}catch(exception){
		errorException(exception);
	}
}

function createRowProductInfoActionJS(){
	try{
		var subformId =$("#PRODUCT_FORM [name='subformId']").val(); 
		var PRODUCT_TYPE = $("[name='PRODUCTS_CARD_TYPE']").val();
		var CARD_TYPE = $("[name='CARD_TYPE']").val();
		var CARD_LEVEL = $("[name='CARD_LEVEL']").val();
		var uniqueId = $("[name=uniqueId]").val();
		var handleForm = 'Y';
		var	className = 'com.eaf.orig.ulo.app.view.form.subform.product.manual.AddCardInfo';
		var $data = 'PRODUCTS_CARD_TYPE='+PRODUCT_TYPE;
			$data += '&uniqueId='+ uniqueId;
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
		var PRODUCT_TYPE = $("select[name='PRODUCTS_CARD_TYPE']").val();
		listBoxFilterAction('CARD_TYPE',FIELD_ID_CARD_TYPE,'',CARD_TYPE_MAIN_APP_LISTBOX,'PRODUCT_TYPE='+PRODUCT_TYPE,'');
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
		var CARD_CODE = $("select[name='CARD_TYPE']").val();
		$("[name=uniqueId]").val(CARD_CODE.split('_')[0]);
		CARD_CODE = CARD_CODE.split('_')[1];
		// call back CARD_TYPEAfterListBoxFilterActionJS method for set init CARD_LEVEL
		listBoxFilterAction('CARD_LEVEL',FIELD_ID_CARD_LEVEL,'',CARD_LEVEL_LISTBOX,'CARD_CODE='+CARD_CODE,CARD_TYPEAfterListBoxFilterActionJS);
		console.log("Card Type");
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

function PRODUCTS_CARD_TYPEInitJS() {
	try{
		displayHtmlElement('PRODUCTS_CARD_TYPE', PRODUCT_CRADIT_CARD);
		targetDisplayHtml('PRODUCTS_CARD_TYPE', MODE_VIEW, 'PRODUCTS_CARD_TYPE');
		PRODUCTS_CARD_TYPEActionJS();
	}catch(exception){
		errorException(exception);
	}
}

