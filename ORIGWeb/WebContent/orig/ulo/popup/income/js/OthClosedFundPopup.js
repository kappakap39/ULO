function ADD_ROW_BTNInitJS(){
}
function ADD_ROW_BTNActionJS(){
	try{
		addIncomeCategoryPopup();
	}catch(exception){
		errorException(exception);
	}
}

function POPUP_OTH_CLOSED_FUND_FORMAfterLoadPopupFinishActionJS(){
	try{
		createRow('com.eaf.orig.ulo.app.view.util.ajax.DefaultIncomeCategoryList','', refreshMasterPopupForm, '', 'N');
	}catch(exception){
		errorException(exception);
	}
}

function POPUP_OTH_CLOSED_FUND_FORMAfterSaveActionJS() {
	try{
		closeIncomeCategoryPopup();
	}catch(exception){
		errorException(exception);
	}
}
$(document).ready(function () {
	$("#OthClosedFund").on("click", ".BtnRemove", deleteIncomeCategoryPopup);
});

function ACCOUNT_NOActionJS(element, mode, name) {

}