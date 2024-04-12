function ADD_YEARLY_BTNInitJS(){
}
function ADD_YEARLY_BTNActionJS(){
	try{
		add50TawiIncomePopup(INC_TYPE_YEARLY_50TAWI);
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_YEARLY_TAWI_FORMAfterSaveActionJS() {
	try{
		closeIncomeCategoryPopup();
	}catch(exception){
		errorException(exception);
	}
}
function add50TawiIncomePopup(incomeType) {
	try{
		var chk50Tawi_YEAR = $("[name='chk50Tawi_YEAR']:checked").val();
		var chk50Tawi_MON = $("[name='chk50Tawi_MON']:checked").val();
		var $data = '&chk50Tawi_YEAR='+chk50Tawi_YEAR+'&chk50Tawi_MON='+chk50Tawi_MON+'&incomeType='+incomeType;
		var formId = getPopupFormId();
		formHandleAction(formId,$data,createNewRow);
	}catch(exception){
		errorException(exception);
	}
}
function createNewRow(data,formId,str){
	try{
		createRow('com.eaf.orig.ulo.app.view.util.ajax.Add50TawiIncome',str,refreshMasterPopupForm,'','N');
	}catch(exception){
		errorException(exception);
	}
}
function chk50TawiInitJS(){
}
function chk50TawiActionJS(element,mode,name,obj){
	try{
		var incomeType = $("[name='"+element+"']:checked").val();
		if(incomeType =="" || incomeType == undefined) {
			incomeType = "";
		}
		var chk50Tawi_YEAR = $("[name='chk50Tawi_YEAR']:checked").val();
		var chk50Tawi_MON = $("[name='chk50Tawi_MON']:checked").val();
		var $data = '&chk50Tawi_YEAR='+chk50Tawi_YEAR+'&chk50Tawi_MON='+chk50Tawi_MON+'&incomeType='+incomeType;
		$data = $data+'&element='+element;
		var formId = getPopupFormId();
		formHandleAction(formId,$data,reCheck50Tawi);
	}catch(exception){
		errorException(exception);
	}	
}
function reCheck50Tawi(data,formId,str) {
	try{
		ajax('com.eaf.orig.ulo.app.view.util.ajax.Recheck50TawiIncome',str,refreshMasterPopupForm);
	}catch(exception){
		errorException(exception);
	}
}
$(document).ready(function () {
	$("#YearlyTawi").on("click", ".BtnRemove", delete50TawiIncomePopup);
});