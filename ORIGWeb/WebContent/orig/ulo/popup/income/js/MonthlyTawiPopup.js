function ADD_MONTHLY_BTNInitJS(){
}
function ADD_MONTHLY_BTNActionJS(){
	try{
		add50TawiIncomePopup(INC_TYPE_MONTHLY_50TAWI);
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_MONTHLY_TAWI_FORMAfterSaveActionJS() {
	try{
		closeIncomeCategoryPopup();
	}catch(exception){
		errorException(exception);
	}
}
$(document).ready(function () {
	$("#MonthlyTawi").on("click", ".BtnRemove", delete50TawiIncomePopup);
});