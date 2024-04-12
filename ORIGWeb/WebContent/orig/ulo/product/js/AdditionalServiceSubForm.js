/**
 * 
 */
function PERCENT_LIMIT_MAINCARDInitJS(element, mode, name){
	try{
//	alert("element="+element+" : name="+name );
	var suffix = $("[name='SUFFIX']").val();  
	var value = $("[name='"+element+"']").val();    
	if(!isEmpty(value) && PERCENT_LIMIT_MAINCARD_MANUAL == value){
//		targetDisplayHtml('PERCENT_LIMIT', MODE_EDIT, 'PERCENT_LIMIT_'+suffix);
	}else{
//		targetDisplayHtml('PERCENT_LIMIT_'+suffix, MODE_VIEW);
//		targetDisplayHtml('PERCENT_LIMIT', MODE_VIEW);
		$("[name='PERCENT_LIMIT']").val('');
	}
	}catch(exception){
		errorException(exception);
	}
}

function PERCENT_LIMIT_MAINCARDActionJS(element, mode, name){
	try{
	PERCENT_LIMIT_MAINCARDInitJS(element, mode, name);
	var subformId = $("#SUPPLEMENTARY_CARD_POPUP [name='subformId']").val();
	refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}

function SERVICE_TYPE_SPENDING_ALERTActionJS(fieldName, mode , elementId){
}
function SERVICE_TYPE_DUE_ALERTActionJS(fieldName,mode,elementId){
}
function SERVICE_TYPE_EMAIL_FLAGActionJS(fieldName,mode,elementId){
	try{
	var subformIdMain = $("#PRODUCT_FORM [name='subformId']").val();
	var subformId = $("#PHONE_NO_SUBFORM [name='subformId']").val();
	setPropertiesSubform(subformIdMain,refreshSubForm(subformId,"Y"),elementId);
	}catch(exception){
		errorException(exception);
	} 
}