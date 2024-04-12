$(document).ready(function(){
	$('#SECTION_PAYSLIP_MONTHLY_POPUP').find('.titlecontent').remove();
	$("#PayslipMonthly").unbind().on("click", ".BtnRemove", deletePayslipMonthly);	
});
 
function ADD_ROW_BTNInitJS(){
}
function ADD_ROW_BTNActionJS(){
	try{
		createRow('com.eaf.orig.ulo.app.view.util.ajax.AddPayslipMonthly','',refreshSubForm,'PAYSLIP_MONTHLY_POPUP','Y');
	}catch(exception){
		errorException(exception);
	}
}

function TYPEActionJS(){
	try{
		var subFormId = "PAYSLIP_MONTHLY_POPUP";
		refreshSubForm(subFormId,'Y');
	}catch(exception){
		errorException(exception);
	}
}

function ACCUMULATED_MONTHInitJS(){
}


function ACCUMULATED_MONTHActionJS(){
	try{
		var formId = getPopupFormId();
		formHandleAction(formId,'',refreshMasterPopupForm);
	}catch(exception){
		errorException(exception);
	}
}
function MONTH_BONUSInitJS(elementId, mode, name){
	try{
		var ACCUMULATED_MONTH = $("[name='ACCUMULATED_MONTH']").val();
		if(mode == MODE_EDIT){
			if(ACCUMULATED_MONTH == null || ACCUMULATED_MONTH == "" || ACCUMULATED_MONTH == undefined) {
				targetDisplayHtml('MONTH_BONUS', MODE_VIEW,'MONTH_BONUS');
				displayHtmlElement('MONTH_BONUS','');
			}else{		
				targetDisplayHtml('MONTH_BONUS', MODE_EDIT,'MONTH_BONUS');
			}
		}
	}catch(exception){
		errorException(exception);
	}
}

function MONTH_BONUSActionJS(){
}
function YEAR_BONUSInitJS(elementId, mode, name){
	try{
		var ACCUMULATED_MONTH = $("[name='ACCUMULATED_MONTH']").val();
		if(mode == MODE_EDIT){
			if(ACCUMULATED_MONTH == null || ACCUMULATED_MONTH == "" || ACCUMULATED_MONTH == undefined) {
				targetDisplayHtml('YEAR_BONUS', MODE_VIEW,'YEAR_BONUS');
				displayHtmlElement('YEAR_BONUS','');
			} else if(mode == MODE_EDIT) {
				targetDisplayHtml('YEAR_BONUS', MODE_EDIT,'YEAR_BONUS');
			}
		}
	}catch(exception){
		errorException(exception);
	}
}
function YEAR_BONUSActionJS(){
}
function ACCUMULATED_INCOMEInitJS(elementId, mode, name){
	try{
		var ACCUMULATED_MONTH = $("[name='ACCUMULATED_MONTH']").val();
			if(ACCUMULATED_MONTH == null || ACCUMULATED_MONTH == "" || ACCUMULATED_MONTH == undefined) {
				targetDisplayHtml("ACCUMULATED_INCOME",MODE_VIEW,"ACCUMULATED_INCOME");
				displayHtmlElement('ACCUMULATED_INCOME','');
			} else if(mode == MODE_EDIT) {
				targetDisplayHtml("ACCUMULATED_INCOME",MODE_EDIT,"ACCUMULATED_INCOME");
			}
	}catch(exception){
		errorException(exception);
	}
}
function ACCUMULATED_INCOMEActionJS(){
}
function ACCUMULATED_SSOInitJS(elementId, mode, name){
	try{
		var ACCUMULATED_MONTH = $("[name='ACCUMULATED_MONTH']").val();
		if(ACCUMULATED_MONTH == null || ACCUMULATED_MONTH == "" || ACCUMULATED_MONTH == undefined) {
			targetDisplayHtml("ACCUMULATED_SSO",MODE_VIEW,"ACCUMULATED_SSO");
			displayHtmlElement('ACCUMULATED_SSO','');
		} else if(mode == MODE_EDIT){
			targetDisplayHtml("ACCUMULATED_SSO",MODE_EDIT,"ACCUMULATED_SSO");
		}
	}catch(exception){
		errorException(exception);
	}
}
function ACCUMULATED_SSOActionJS(){
}
function OTH_ACCUMULATED_INCOMEInitJS(elementId, mode, name){
	try{
		var ACCUMULATED_MONTH = $("[name='ACCUMULATED_MONTH']").val();
		if(ACCUMULATED_MONTH == null || ACCUMULATED_MONTH == "" || ACCUMULATED_MONTH == undefined) {
			targetDisplayHtml("OTH_ACCUMULATED_INCOME",MODE_VIEW,'OTH_ACCUMULATED_INCOME');
			displayHtmlElement('OTH_ACCUMULATED_INCOME','');
		} else if(mode == MODE_EDIT) {
			targetDisplayHtml("OTH_ACCUMULATED_INCOME",MODE_EDIT,'OTH_ACCUMULATED_INCOME');
		}
	}catch(exception){
		errorException(exception);
	}
}
function OTH_ACCUMULATED_INCOMEActionJS(){
}
function ACCUMULATED_BONUSInitJS(elementId, mode, name){
	try{
		var ACCUMULATED_MONTH = $("[name='ACCUMULATED_MONTH']").val();
		if(ACCUMULATED_MONTH == null || ACCUMULATED_MONTH == "" || ACCUMULATED_MONTH == undefined) {
			targetDisplayHtml("ACCUMULATED_BONUS",MODE_VIEW,'ACCUMULATED_BONUS');
			displayHtmlElement('ACCUMULATED_BONUS','');
		} else if(mode == MODE_EDIT) {
			targetDisplayHtml("ACCUMULATED_BONUS",MODE_EDIT,'ACCUMULATED_BONUS');
		}
	}catch(exception){
		errorException(exception);
	}
}
function ACCUMULATED_BONUSActionJS(){
}
function SALARY_DATEInitJS(elementId, mode, name){
	try{
		var ACCUMULATED_MONTH = $("[name='ACCUMULATED_MONTH']").val();
		if(ACCUMULATED_MONTH == null || ACCUMULATED_MONTH == "" || ACCUMULATED_MONTH == undefined) {
			targetDisplayHtml("SALARY_DATE",MODE_VIEW,'SALARY_DATE');
			displayHtmlElement('SALARY_DATE','');
		} else if(mode == MODE_EDIT) {
			targetDisplayHtml("SALARY_DATE",MODE_EDIT,'SALARY_DATE');
		}
	}catch(exception){
		errorException(exception);
	}
}
function SALARY_DATEActionJS(){
}
/*function KBANK_PENSIONInitJS(){change to config this process in  PayslipKbankPensionDisplayMode.java
	var ACCUMULATED_MONTH = $("[name='ACCUMULATED_MONTH']").val();
	if(ACCUMULATED_MONTH != null && ACCUMULATED_MONTH != "" && ACCUMULATED_MONTH != undefined){
		targetDisplayHtml("KBANK_PENSION",MODE_EDIT,'KBANK_PENSION');
	}else{
		targetDisplayHtml("KBANK_PENSION",MODE_VIEW,'KBANK_PENSION');
		displayHtmlElement('KBANK_PENSION','');
	}
}*/
function KBANK_PENSIONActionJS(){
}

function MONTH2_BONUSInitJS(elementId, mode, name){  
	try{
		var ACCUMULATED_MONTH = $("[name='ACCUMULATED_MONTH']").val();
		if((ACCUMULATED_MONTH == null || ACCUMULATED_MONTH == "" || ACCUMULATED_MONTH == undefined) && mode == MODE_EDIT) {
//			targetDisplayHtml('MONTH2_BONUS', MODE_EDIT,'MONTH2_BONUS');
		} else {
//			targetDisplayHtml('MONTH2_BONUS', MODE_VIEW,'MONTH2_BONUS');
//			displayHtmlElement('MONTH2_BONUS','');
		}
	}catch(exception){
		errorException(exception);
	}
}
function MONTH2_BONUSActionJS(){
}
function YEAR2_BONUSInitJS(elementId, mode, name){
	try{
		var ACCUMULATED_MONTH = $("[name='ACCUMULATED_MONTH']").val();
		if((ACCUMULATED_MONTH == null || ACCUMULATED_MONTH == "" || ACCUMULATED_MONTH == undefined) && mode == MODE_EDIT) {
//			targetDisplayHtml('YEAR2_BONUS', MODE_EDIT,'YEAR2_BONUS');
		} else {
//			targetDisplayHtml('YEAR2_BONUS', MODE_VIEW,'YEAR2_BONUS');
//			displayHtmlElement('YEAR2_BONUS','');
		}
	}catch(exception){
		errorException(exception);
	}
}
function YEAR2_BONUSActionJS(){
}
function BONUSInitJS(elementId, mode, name){
	try{
		var ACCUMULATED_MONTH = $("[name='ACCUMULATED_MONTH']").val();
		if((ACCUMULATED_MONTH == null || ACCUMULATED_MONTH == "" || ACCUMULATED_MONTH == undefined)&& mode != MODE_VIEW) {
//			targetDisplayHtml("BONUS",MODE_EDIT,'BONUS');
		} else {
//			targetDisplayHtml("BONUS",MODE_VIEW,'BONUS');
//			displayHtmlElement('BONUS','');
		}
	}catch(exception){
		errorException(exception);
	}
}
function BONUSActionJS(){
}
function SALARY_DATE2InitJS(element,mode,name){
	try{
		var ACCUMULATED_MONTH = $("[name='ACCUMULATED_MONTH']").val();
		if((ACCUMULATED_MONTH == null || ACCUMULATED_MONTH == "" || ACCUMULATED_MONTH == undefined) && mode != MODE_VIEW) {
//			targetDisplayHtml("SALARY_DATE2",MODE_EDIT,'SALARY_DATE2');
		} else {
//			targetDisplayHtml("SALARY_DATE2",MODE_VIEW,'SALARY_DATE2');
//			displayHtmlElement('SALARY_DATE2','');
		}
	}catch(exception){
		errorException(exception);
	}
}
function SALARY_DATE2ActionJS(){
}
/*function KBANK_PENSION2InitJS(){  change to config this process in  PayslipMonthlyKBankPensionDisplayMode.java
	var ACCUMULATED_MONTH = $("[name='ACCUMULATED_MONTH']").val();
	if((ACCUMULATED_MONTH == null || ACCUMULATED_MONTH == "" || ACCUMULATED_MONTH == undefined)) {
		targetDisplayHtml("KBANK_PENSION2",MODE_EDIT,'KBANK_PENSION2');
	} else {
		targetDisplayHtml("KBANK_PENSION2",MODE_VIEW,'KBANK_PENSION2');
		displayHtmlElement('KBANK_PENSION2','');
	}
}*/
function KBANK_PENSION2ActionJS(){
}

function INCOME_DESCInitJS(){
}

function INCOME_DESCActionJS(element,mode,name){
	try{
		INCOME_OTH_DESCInitJS(element,mode,name);
	}catch(exception){
		errorException(exception);
	}
}
function INCOME_OTH_DESCInitJS(element,mode,name){
	try{
		var par = $($("[name='"+element+"']")).closest('TR');
	    var seq = par.attr('id').split("_");
	    var INCOME_DESC = $('[name="INCOME_DESC_'+seq[1]+'"]').val();
		if(INCOME_DESC == null || INCOME_DESC == "" || INCOME_DESC == undefined) {
			targetDisplayHtml("INCOME_OTH_DESC_"+seq[1],MODE_VIEW,'INCOME_OTH_DESC');
			displayHtmlElement("INCOME_OTH_DESC_"+seq[1],'');
		} else if(INCOME_DESC == INCOME_TYPE_OTHER){
			targetDisplayHtml("INCOME_OTH_DESC_"+seq[1],MODE_EDIT,'INCOME_OTH_DESC');
		} else {
			targetDisplayHtml("INCOME_OTH_DESC_"+seq[1],MODE_VIEW,'INCOME_OTH_DESC');
			displayHtmlElement("INCOME_OTH_DESC_"+seq[1],'');
		}
	}catch(exception){
		errorException(exception);
	}
}
function INCOME_OTH_DESCActionJS(){
}

function deletePayslipMonthly() {
	try{
		var par = $(this).closest('TR');
	    $data = 'seq='+par.attr('id');
	    deleteRow('com.eaf.orig.ulo.app.view.util.ajax.DeletePayslipMonthly',$data,refreshSubForm,'PAYSLIP_MONTHLY_POPUP','Y');
	}catch(exception){
		errorException(exception);
	}
};
function POPUP_PAYSLIP_FORMAfterSaveActionJS() {
	try{
		closeIncomeCategoryPopup();
	}catch(exception){
		errorException(exception);
	}
}
 