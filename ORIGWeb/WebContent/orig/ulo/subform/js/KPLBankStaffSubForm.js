function REF_NAMEActionJS(elementId,mode,fieldId){
	try{
	//REF_NAMEInitJS(element, mode, name);
		retrieveSaleInformation();
	}catch(exception){
		errorException(exception);
	}

}

function SALES_NAMEActionJS(elementId,mode,fieldId){
	try{
	//REF_NAMEInitJS(element, mode, name);
		retrieveSaleInformation();
	}catch(exception){
		errorException(exception);
	}

}

function retrieveSaleInformation(){ 
	try{
		var className = 'com.eaf.orig.ulo.app.view.util.ajax.RetrieveSaleInformation';
		var data ="&FUNCTION_CODE=KPL";
			data+="&SALES_NAME="+$('[name="SALES_NAME"]').val();
		
		ajax(className, data,refreshSaleInformation);	
	}catch(exception){
		errorException(exception);
	}
}
function refreshSaleInformation(data){ 
	console.log(data);
	try{
		//REF_NAMEInitJS(element, mode, name);
		var subformId = $("#KPL_BANK_STAFF_SUBFORM [name='subformId']").val();
		refreshSubForm(subformId,"Y");
		}catch(exception){
			errorException(exception);
		}
}

function SALES_BRANCH_AfterActionJS( data ){
	displayJSON(data);
}

function SALES_BRANCH_CODEActionJS(){}


function SALES_NAMEInitJS()
{
	if(!($("[name='SAVING_PLUS']").is(":checked")))
	{targetDisplayHtml('SALES_NAME',MODE_VIEW,'SALES_NAME','Y');}
}

function SALES_PHONE_NOInitJS()
{
	if(!($("[name='SAVING_PLUS']").is(":checked")))
	{targetDisplayHtml('SALES_PHONE_NO',MODE_VIEW,'SALES_PHONE_NO','Y');}
}

function SALES_BRANCH_CODEInitJS()
{
	if(!($("[name='SAVING_PLUS']").is(":checked")))
	{targetDisplayHtml('SALES_BRANCH_CODE',MODE_VIEW,'SALES_BRANCH_CODE','Y');}
}

function SALES_INFO_NAMEInitJS()
{
	if(!($("[name='SAVING_PLUS']").is(":checked")))
	{targetDisplayHtml('SALES_INFO_NAME',MODE_VIEW,'SALES_INFO_NAME','Y');}
}