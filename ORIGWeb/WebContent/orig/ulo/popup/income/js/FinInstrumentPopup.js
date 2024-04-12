function ADD_ROW_BTNInitJS(){
}
function ADD_ROW_BTNActionJS(){
	try{
		addIncomeCategoryPopup();
	}catch(exception){
		errorException(exception);
	}	
}

function POPUP_FIN_INSTRUMENT_FORMAfterLoadPopupFinishActionJS(){
	try{
		createRow('com.eaf.orig.ulo.app.view.util.ajax.DefaultIncomeCategoryList','', refreshMasterPopupForm, '', 'N');
	}catch(exception){
		errorException(exception);
	}
}

function POPUP_FIN_INSTRUMENT_FORMAfterSaveActionJS() {
	try{
		closeIncomeCategoryPopup();
	}catch(exception){
		errorException(exception);
	}
}
function INSTRUMENT_TYPEInitJS(){
}

function INSTRUMENT_TYPEActionJS(element,mode,name){
//	INSTRUMENT_TYPE_OTHInitJS(element,mode,name);
}
function INSTRUMENT_TYPE_OTHInitJS(element,mode,name){
//	var par = $($("[name='"+element+"']")).closest('TR');
//    var seq = par.attr('id').split("_");
//    var INSTRUMENT_TYPE = $('[name="INSTRUMENT_TYPE_'+seq[1]+'"]').val();
//	if(INSTRUMENT_TYPE == null || INSTRUMENT_TYPE == "" || INSTRUMENT_TYPE == undefined) {
//		targetDisplayHtml("INSTRUMENT_TYPE_OTH_"+seq[1], MODE_VIEW,"INSTRUMENT_TYPE_OTH");
//		displayHtmlElement("INSTRUMENT_TYPE_OTH_"+seq[1],'');
//	} else if(INSTRUMENT_TYPE == INSTRUMENT_TYPE_OTHER_VAL){
//		targetDisplayHtml("INSTRUMENT_TYPE_OTH_"+seq[1], MODE_EDIT,"INSTRUMENT_TYPE_OTH");
//	} else {
//		targetDisplayHtml("INSTRUMENT_TYPE_OTH_"+seq[1], MODE_VIEW,"INSTRUMENT_TYPE_OTH");
//		displayHtmlElement("INSTRUMENT_TYPE_OTH_"+seq[1],'');
//	}
}
function INSTRUMENT_TYPE_OTHActionJS(){
}
function START_DATEInitJS(){
}
function START_DATEActionJS(element,mode,name){
	validateFinInstrumentDate(element);
}
function END_DATEInitJS(){
}
function END_DATEActionJS(element,mode,name){
	validateFinInstrumentDate(element);
}
function validateFinInstrumentDate(element){
	try{
		var par = $($("[name='"+element+"']")).closest('TR');
	    var seq = par.attr('id').split("_");
	    var dateFrom = $('[name="START_DATE_'+seq[1]+'"]').val();
		var dateTo = $('[name="END_DATE_'+seq[1]+'"]').val();
	    var isValid = validateBetweenDate(dateFrom,dateTo);
	    if(!isValid) {
	    	alertBox('ERROR_ID_FIN_INSTRUMENT_DATE', emptyFocusElementActionJS, element);
	    }
	}catch(exception){
		errorException(exception);
	}
}
$(document).ready(function () {
	$("#FinInstrument").on("click", ".BtnRemove", deleteIncomeCategoryPopup);
});