$(function() {
	try{
		collapserInit();
		var documentType = $('[name="DOCUMENT_TYPE"]:checked').val();
		if (isEmpty(documentType)) {
			$('#' + PRVLG_DOC_TYPE_CUSTOMER_PRODUCT + '_DOCUMENT_TYPE').prop('checked', true);
		}
		DOCUMENT_TYPEOnload('DOCUMENT_TYPE');
	}catch(exception){
		errorException(exception);
	}

});


function collapserInit() {
	try{
		$('.element-body').collapse({
			toggle : false
		});
	}catch(exception){
		errorException(exception);
	}
}

function collapseAll() {
	try{
		var elementBodies = $('.element-body');
		elementBodies.collapse('hide');
		elementBodies.parent().css('background-color','').parent().css('border-color', '');
	}catch(exception){
		errorException(exception);
	}
}

function collapseShow(element) {
	try{
		element.collapse('show');
	}catch(exception){
		errorException(exception);
	}
}

function DOCUMENT_TYPEOnload(elementId) {
	try{
		var subformId = "VERIFY_PRIVILEGE_DOC_PROJECT_CODE_SUBFORM";
		logger.debug('Radio action JS');
		var radioElement = $('[name=' + elementId + ']:checked');
		var rowElement = radioElement.parent().parent().parent().parent();
		var cbody = rowElement.find('.element-body');
		var panelElement = rowElement.find('.panel');
		if (!cbody.hasClass('collapse in')) {
			collapseAll();
		}
		displayHtmlElement("SELECTED_DOCUMENT_TYPE",radioElement.val());	
		panelElement.css('border-color', '#A5A5A5').find('.panel-body').css('background-color','#FBFBFB');
		collapseShow(cbody);
	}catch(exception){
		errorException(exception);
	}
}

function DOCUMENT_TYPEActionJS(elementId) {
	try{
		var subformId = "VERIFY_PRIVILEGE_DOC_PROJECT_CODE_SUBFORM";
//		logger.debug('Radio action JS');
		var radioElement = $('[name=' + elementId + ']:checked');
		var rowElement = radioElement.parent().parent().parent().parent();
		var cbody = rowElement.find('.element-body');
//		var panelElement = rowElement.find('.panel');
//		if (!cbody.hasClass('collapse in')) {
//			collapseAll();
//		}
		displayHtmlElement("SELECTED_DOCUMENT_TYPE",radioElement.val());	
//		panelElement.css('border-color', '#A5A5A5').find('.panel-body').css('background-color','#FBFBFB');
//		collapseShow(cbody);
		refreshSubFormNoBlockScreen(subformId,'Y');
	}catch(exception){
		errorException(exception);
	}
}

//function clearValue(documentType){
//	var documentTypeVal = $('[name=' + documentType + ']:checked').val();
//	if(documentTypeVal == PRVLG_DOC_TYPE_TRADING){
//		
//	}
//}