function ADD_CARD_INFO_BORROWERActionJS(){
	try{
		createProductInfoActionJS();
	}catch(exception){
		errorException(exception);
	}
}
function createProductInfoActionJS() {
	try{
		var validateId = VALIDATE_CREATE_CARD_INFO;
		var $data = $('#SECTION_IA_PRODUCT_FORM *').serialize();
		validateFormAction(validateId, $data, createRowProductInfoActionJS);
	}catch(exception){
		errorException(exception);
	}
}

function createRowProductInfoActionJS() {
	try{
		var subformId = $("#SECTION_IA_PRODUCT_FORM [name='subformId']").val();
		var PRODUCT_TYPE = $("[name='PRODUCTS_CARD_TYPE']").val();
		var CARD_TYPE = $("[name='CARD_TYPE']").val();
		var CARD_LEVEL = $("[name='CARD_LEVEL']").val();
		var PROJECT_CODE = $("[name='PROJECT_CODE']").val();
		var handleForm = 'Y';
		var functionId = 'CREATE_PRODUCT_INFO_IA';
		var className = 'com.eaf.orig.ulo.app.view.form.subform.product.manual.AddCardInfo';
		var $data = 'PRODUCTS_CARD_TYPE=' + PRODUCT_TYPE;
		$data += '&CARD_TYPE=' + CARD_TYPE;
		$data += '&CARD_LEVEL=' + CARD_LEVEL;
		$data += '&PROJECT_CODE=' + PROJECT_CODE;
		$data += '&APPLICATION_CARD_TYPE=' + APPLICATION_CARD_TYPE_BORROWER;
		$data += '&functionId=' + functionId;
		createRow(className, $data, refreshProductInfoFormActionJS, subformId,handleForm);
	}catch(exception){
		errorException(exception);
	}
}
function PRODUCTS_CARD_TYPEInitJS(){
	try{
		listBoxFilterAction('PRODUCTS_CARD_TYPE',FIELD_ID_PRODUCT_TYPE,'',PRODUCT_LISTBOX_FILTER,'',PRODUCTS_CARD_TYPEAfterListBoxFilterActionJS);
	}catch(exception){
		errorException(exception);
	}
}

function PRODUCTS_CARD_TYPEAfterListBoxFilterActionJS()
{
	//Set dropdown list value to PLG if(isKPL)
	if($("[name='isKPL']").val() == "true")
	{
		$("select[name='PRODUCTS_CARD_TYPE']")[0].selectize.setValue(PRODUCT_CODE_PLG, false);
	}
}

function PRODUCTS_CARD_TYPEActionJS() {
	try{
		var PRODUCT_TYPE = $("[name='PRODUCTS_CARD_TYPE']").val();
		//listBoxFilterAction('CARD_TYPE',FIELD_ID_CARD_TYPE,'',CARD_TYPE_LISTBOX,'PRODUCT_TYPE='+PRODUCT_TYPE,'');
		listBoxFilterAction('CARD_TYPE',FIELD_ID_CARD_TYPE,'','CARD_TYPE_LISTBOX_ACTIVE_ONLY','PRODUCT_TYPE='+PRODUCT_TYPE,'');
		if(PRODUCT_TYPE == PRODUCT_CODE_KEC){
			targetDisplayHtml('CARD_LEVEL',MODE_VIEW,'CARD_LEVEL','Y');
			targetDisplayHtml('CARD_TYPE',MODE_EDIT,'CARD_TYPE','Y');
			targetDisplayHtml('PROJECT_CODE',MODE_EDIT,'PROJECT_CODE','Y');
		}else if(PRODUCT_TYPE == PRODUCT_CODE_PLG || PRODUCT_TYPE == PRODUCT_CODE_PLT || PRODUCT_TYPE == PRODUCT_CODE_PLP){
			targetDisplayHtml('CARD_TYPE',MODE_VIEW,'CARD_TYPE','Y');
			targetDisplayHtml('CARD_LEVEL',MODE_VIEW,'CARD_LEVEL','Y');
            targetDisplayHtml('PROJECT_CODE',MODE_VIEW,'PROJECT_CODE','Y');
		}else{
			targetDisplayHtml('CARD_TYPE',MODE_EDIT,'CARD_TYPE','Y');
			targetDisplayHtml('CARD_LEVEL',MODE_EDIT,'CARD_LEVEL','Y');
			targetDisplayHtml('PROJECT_CODE',MODE_EDIT,'PROJECT_CODE','Y');
		}	
	}catch(exception){
		errorException(exception);
	}
}
function CARD_TYPEActionJS() {
	try{
		var CARD_CODE = $("[name='CARD_TYPE']").val();
		// call back CARD_TYPEAfterListBoxFilterActionJS method for set init CARD_LEVEL
		listBoxFilterAction('CARD_LEVEL', FIELD_ID_CARD_LEVEL, '',
				CARD_LEVEL_LISTBOX, 'CARD_CODE=' + CARD_CODE, CARD_TYPEAfterListBoxFilterActionJS);
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

function BTN_EXECUTEActionJS(){
	try{
		var formId = $("#FormHandlerManager [name='formId']").val();
		var action = BUTTON_ACTION_EXECUTE;
		var handleForm = 'Y';
		var validateForm = 'Y';
		console.log("BTN_EXECUTEActionJS");
		processApplicationFormAction(formId,action,handleForm,validateForm,DECISION_IA_EXECUTEAfterProcessActionJS);
	}catch(exception){
		errorException(exception);
	}
}
function DECISION_IA_EXECUTEAfterProcessActionJS(){
	try{
		var functionId = DECISION_SERVICE_ACTION;
		var action = BUTTON_ACTION_EXECUTE;
		var formId = $("#FormHandlerManager [name='formId']").val();
		applicationActionService(functionId,DECISION_IA_EXECUTEAfterActionJS,formId,action);
	}catch(exception){
		errorException(exception);
	}
}
function DECISION_IA_EXECUTEAfterActionJS(data){
	try{
		var decisionApplication = $.parseJSON(data);
		if(decisionApplication.resultCode==BUSINESS_EXCEPTION){
			var formId = $("#FormHandlerManager [name='formId']").val();
			displayErrorMsg(formId,decisionApplication.resultDesc);
		}else{
			var subformId =$("#DOCUMENT_CHECK_LIST [name='subformId']").val();
//			alert("subformId : "+subformId);
			refreshSubForm(subformId);
			refreshFormHeader();
		}
	}catch(exception){
		errorException(exception);
	}
}

function callBackDelKPLActionJS() {
	try{
		//refreshProductInfoFormActionJS();
		refreshMasterForm();
		refreshFormHeader();
	}catch(exception){
		errorException(exception);
	}
}

function deleteProductActionJS(uniqueId) {
	try{
		var subformId = $("#PRODUCT_FORM [name='subformId']").val();
		var handleForm = 'Y';
		var $data = 'uniqueId=' + uniqueId;
		var className = 'com.eaf.orig.ulo.app.view.form.subform.product.manual.DeleteProductInfo';
		deleteRow(className, $data, callBackDelKPLActionJS, subformId, handleForm);
	}catch(exception){
		errorException(exception);
	}

}

function refreshProductInfoFormActionJS() {
	try{
		refreshSubForm('IA_PRODUCT_FORM', 'Y');
	}catch(exception){
		errorException(exception);
	}
}

function refreshDocumentCheckListFormActionJS() {
	try{
		refreshSubForm('DOCUMENT_CHECK_LIST', 'Y');
	}catch(exception){
		errorException(exception);
	}
}

function createRowKPLProductInfoActionJS()
{
	try{
		var subformId = $("#SECTION_IA_PRODUCT_FORM [name='subformId']").val();
		var PRODUCT_TYPE = $("[name='PRODUCTS_CARD_TYPE']").val();
		var handleForm = 'Y';
		var functionId = 'CREATE_PRODUCT_INFO_IA';
		var className = 'com.eaf.orig.ulo.app.view.form.subform.product.manual.AddCardInfo';
		var $data = 'PRODUCTS_CARD_TYPE=' + 'PLG';
		$data += '&CARD_TYPE=';
		$data += '&CARD_LEVEL=';
		$data += '&PROJECT_CODE=';
		$data += '&APPLICATION_CARD_TYPE=' + APPLICATION_CARD_TYPE_BORROWER;
		$data += '&functionId=' + functionId;
		createRow(className, $data, refreshProductInfoFormActionJS, subformId,handleForm);
	}catch(exception){
		errorException(exception);
	}
}


// Hijack delete personal applicant function !
// Need to refresh product form after deleted personal applicant
DELETE_PERSONAL_APPLICANT_INFOActionJS = function(PERSONAL_ID){
	try{
		var subformId =$("#LIST_SUP_PERSONAL_INFO_SUBFORM [name='subformId']").val();
		var $data = '&PERSONAL_ID='+PERSONAL_ID;
			$data += '&subformId='+subformId;
		deleteRow('com.eaf.orig.ulo.app.view.form.subform.product.manual.DeleteListSupPersonalInfo', $data, function(subformId,handleForm) {
			refreshSubForm(subformId,handleForm);
			refreshProductInfoFormActionJS();
			refreshDocumentCheckListFormActionJS();
		});
	}catch(exception){
		errorException(exception);
	}
};
