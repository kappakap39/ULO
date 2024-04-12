function ADD_ROW_BTNInitJS(){
}

function ADD_ROW_BTNActionJS(){
	try{
		addIncomeCategoryPopup();
	}catch(exception){
		errorException(exception);
	}
}

function POPUP_BANK_STATEMENT_FORMAfterLoadPopupFinishActionJS(){
	try{
		createRow('com.eaf.orig.ulo.app.view.util.ajax.DefaultIncomeCategoryList','', refreshMasterPopupForm, '', 'N');
	}catch(exception){
		errorException(exception);
	}
}

function POPUP_BANK_STATEMENT_FORMAfterSaveActionJS() {
	try{
		closeIncomeCategoryPopup();
	}catch(exception){
		errorException(exception);
	}
}

function BANK_NAMEInitJS(){
}

function BANK_NAMEActionJS(element, mode, name){
	try{
		var seq = element.split("_");
		var SALARY_CODE_field = "SALARY_CODE"+"_"+seq[2];
		var ADDITIONAL_field = "ADDITIONAL"+"_"+seq[2];
		var BANK_CODE = $('[name="'+element+'"]').val();
		listBoxFilterAction(SALARY_CODE_field,SALARY_CODE_CACHE,'',BANK_SALARY_LIST_BOX_ID,'BANK_CODE='+BANK_CODE,'');
		listBoxFilterAction(ADDITIONAL_field,SALARY_CODE_CACHE,'',BANK_SALARY_ADDITIONAL_LIST_BOX_ID,'BANK_CODE='+BANK_CODE,'');
	}catch(exception){
		errorException(exception);
	}
}

function SALARY_CODEInitJS(element, mode, name){
}

function SALARY_CODEActionJS(){
}

function ADDITIONALInitJS(element, mode, name){
}

function ADDITIONALActionJS(){
}

$(document).ready(function () {
	$("#BankStatement").on("click", ".BtnRemove", deleteIncomeCategoryPopup);
});