//function REF_NAMEActionJS(elementId,mode,fieldId){
//	try{
//		var className = 'com.eaf.orig.ulo.app.view.util.ajax.RetrieveSaleInformation';
//		var data ="&SALE_ID="+$('[name="'+elementId+'"]').val();
//			data+="&FIELD_NAME=REF_BRANCH_CODE";
//			data+="&SALE_TYPE=02";
//		ajax(className, data,SALES_BRANCH_AfterActionJS);	
//	}catch(exception){
//		errorException(exception);
//	}
//}
//function PERCENT_LIMIT_MAINCARDActionJS(element, mode, name){
//	try{
//	PERCENT_LIMIT_MAINCARDInitJS(element, mode, name);
//	var subformId = $("#SUPPLEMENTARY_CARD_POPUP [name='subformId']").val();
//	refreshSubForm(subformId,"Y");
//	}catch(exception){
//		errorException(exception);
//	}
//}
function REF_NAMEActionJS(elementId,mode,fieldId){
	try{
	//REF_NAMEInitJS(element, mode, name);
		retrieveSaleInformation();
	}catch(exception){
		errorException(exception);
	}

}

//function SALES_NAMEActionJS(elementId,mode,fieldId){
//	try{	
//		var className = 'com.eaf.orig.ulo.app.view.util.ajax.RetrieveSaleInformation';
//		var data ="&SALE_ID="+$('[name="'+elementId+'"]').val();
//			data+="&FIELD_NAME=SALES_BRANCH_CODE";
//			data+="&SALE_TYPE=01";
//		ajax(className, data,SALES_BRANCH_AfterActionJS);	
//	}catch(exception){
//		errorException(exception);
//	}
//}
function SALES_NAMEActionJS(elementId,mode,fieldId){
	try{
	//REF_NAMEInitJS(element, mode, name);
		retrieveSaleInformation();
	}catch(exception){
		errorException(exception);
	}

}

//function CASH_DAYONE_NAMEActionJS(elementId,mode,fieldId){ 
//	try{
//		var className = 'com.eaf.orig.ulo.app.view.util.ajax.RetrieveSaleInformation';
//		var data ="&SALE_ID="+$('[name="'+elementId+'"]').val();
//			data+="&FIELD_NAME=CASH_DAYONE_BRANCH_CODE";
//			data+="&SALE_TYPE=03";
//		ajax(className, data,SALES_BRANCH_AfterActionJS);	
//	}catch(exception){
//		errorException(exception);
//	}
//}
//function CASH_DAYONE_NAMEAferActionJS(data){
////	displayJSON(data);
//	var subformId = $('#BANK_STAFF_SUBFORM input[name="subformId"]').val();
//	refreshSubForm(subformId,'Y');
//}

function CASH_DAYONE_NAMEActionJS(elementId,mode,fieldId){
	try{
	//REF_NAMEInitJS(element, mode, name);
		retrieveSaleInformation();
	}catch(exception){
		errorException(exception);
	}

}

function SALES_NAME_ALLIANCEActionJS(elementId,mode,fieldId){
	try{
	//REF_NAMEInitJS(element, mode, name);
	var subformId = $("#BANK_STAFF_SUBFORM [name='subformId']").val();
	refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}

}

function retrieveSaleInformation(){ 
	try{
		var className = 'com.eaf.orig.ulo.app.view.util.ajax.RetrieveSaleInformation';
		var data ="&FUNCTION_CODE=NORMAL";
			data+="&REF_NAME="+$('[name="REF_NAME"]').val();
			data+="&SALES_NAME="+$('[name="SALES_NAME"]').val();
			data+="&CASH_DAYONE_NAME="+$('[name="CASH_DAYONE_NAME"]').val();
		
		ajax(className, data,refreshSaleInformation);	
	}catch(exception){
		errorException(exception);
	}
}
function refreshSaleInformation(data){ 
	console.log(data);
	try{
		//REF_NAMEInitJS(element, mode, name);
		var subformId = $("#BANK_STAFF_SUBFORM [name='subformId']").val();
		refreshSubForm(subformId,"Y");
		}catch(exception){
			errorException(exception);
		}
}
function CASH_DAYONE_NAMEAferActionJS(data){
//	displayJSON(data);
	var subformId = $('#BANK_STAFF_SUBFORM input[name="subformId"]').val();
	refreshSubForm(subformId,'Y');
} 

function SALES_BRANCH_AfterActionJS( data ){
	displayJSON(data);
}

function SALES_BRANCH_CODEActionJS(){}