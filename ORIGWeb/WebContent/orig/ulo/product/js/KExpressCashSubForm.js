function CardInfoPopUpKECActionJS(formId,uniqueId,width,height){	
	try{
		var $data = 'uniqueId='+uniqueId;
		if(formId != ''){
			loadPopupDialog(formId,'INSERT','0',$data,null,width,height);
		}
	}catch(exception){
		errorException(exception);
	}
}

function deleteKECInfoActionJS(uniqueId,applicationType){
	try{
		var subformId =$("#PRODUCT_FORM [name='subformId']").val(); 
		var handleForm = 'Y';
		var $data  = 'uniqueId='+uniqueId;
		var	className ='com.eaf.orig.ulo.app.view.form.subform.product.manual.DeleteMainProductInfo';
		deleteRow(className,$data,PRODUCT_FORMRefreshActionJS,subformId,handleForm);
	}catch(exception){
		errorException(exception);
	}	
}
function PRODUCT_FORMRefreshActionJS(data){
	try{
		var subformId = $("#PRODUCT_FORM [name='subformId']").val();
		refreshSubForm(subformId, 'Y');
	}catch(exception){
		errorException(exception);
	}
}