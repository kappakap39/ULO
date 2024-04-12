function BRANCH_STAFF_NAMEActionJS(elementId,mode,fieldId){ 
	try{
		retrieveSaleInformation();
	}catch(exception){
		errorException(exception);
	}
}


function retrieveSaleInformation(){ 
	try{
		var className = 'com.eaf.orig.ulo.app.view.util.ajax.RetrieveSaleInformation';
		var data ="&FUNCTION_CODE=INC";
			data+="&BRANCH_STAFF_NAME="+$('[name="BRANCH_STAFF_NAME"]').val();
		
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