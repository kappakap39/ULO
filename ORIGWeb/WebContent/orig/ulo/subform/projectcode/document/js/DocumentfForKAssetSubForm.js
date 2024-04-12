//$(function() {
//	try{
//		collapserInitSection();
//		FirstKASSET_TYPEActionJS('KASSET_TYPE');	
//	}catch(exception){
//		errorException(exception);
//	}
// });

//function collapseAllSection() {
//	try{
//		var elementBodies = $('.section');
//		elementBodies.collapse('hide');
//	}catch(exception){
//		errorException(exception);
//	}
//}

//function collapserInitSection() {
//	try{
//		$('.section').collapse({
//			toggle : false
//		});
//	}catch(exception){
//		errorException(exception);
//	}
//}

//function collapseShowSection(element) {
//	try{
//		element.collapse('show');
//	}catch(exception){
//		errorException(exception);
//	}
//}

function KASSET_TYPEActionJS(elementId){
	try{
		var subformId ="VERIFY_PRIVILEGE_DOC_PROJECT_CODE_SUBFORM";
//		logger.debug('Radio action');
//		var radioElement = $('[name=' + elementId + ']:checked');
//		var rowElement = radioElement.parent().parent().parent().parent();
//		var cbody = rowElement.find('.section');
//		if (!cbody.hasClass('collapse in')) {
//			collapseAllSection();
//		}
//		collapseShowSection(cbody);
//		KASSET_TYPE_MORE_6_MONTH
		refreshSubFormNoBlockScreen(subformId,'Y');
	}catch(exception){
		errorException(exception);
	}
}


//function FirstKASSET_TYPEActionJS(elementId){
//	try{
//		var subformId ="VERIFY_PRIVILEGE_DOC_PROJECT_CODE_SUBFORM";
//		var radioElement = $('[name=' + elementId + ']:checked');
//		var rowElement = radioElement.parent().parent().parent().parent();
//		console.log(rowElement);
//		var cbody = rowElement.find('.section');
//		if (!cbody.hasClass('collapse in')) {
//			collapseAllSection();
//		}
//		collapseShowSection(cbody);
////		KASSET_TYPE_MORE_6_MONTH
//	}catch(exception){
//		errorException(exception);
//		}
//	}
