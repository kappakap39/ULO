function SEARCH_PROVINCEActionJS(element,mode,name){
//	var COUNT_SEARCH_PROVINCE = $('[name='+element+']').val();
//	try{
//		if(!isEmpty(COUNT_SEARCH_PROVINCE)&&COUNT_SEARCH_PROVINCE.length<=SEARCH_FIELDS_REQUIRED_ADDRESS){
//		}
//	}catch(exception){
//		errorException(exception);
//	}
}

function SEARCH_ZIPCODEActionJS(element,mode,name){
//	var COUNT_SEARCH_PROVINCE = $('[name='+element+']').val();
//	try{
//	if(!isEmpty(COUNT_SEARCH_PROVINCE)&&COUNT_SEARCH_PROVINCE.length<=SEARCH_FIELDS_REQUIRED_ADDRESS_4){
//		var $data = element+'='+COUNT_SEARCH_PROVINCE;
//		validateFormAction(VALIDATE_ZIPCODE_LENGTH, $data,"",DISPLAY_ERROR_POPUP);
//		}
//	}catch(exception){
//		errorException(exception);
//	}
}

function ValidateProvinceLengthAfterValidateFormActionJS(data){
	var SEARCH_TAMBOL = $('[name="SEARCH_TAMBOL"]').val();
	var SEARCH_AMPHUR = $('[name="SEARCH_AMPHUR"]').val();
	var SEARCH_PROVINCE = $('[name="SEARCH_PROVINCE"]').val();
	var SEARCH_ZIPCODE = $('[name="SEARCH_ZIPCODE"]').val();
	try{
		if(isEmpty(SEARCH_TAMBOL) && isEmpty(SEARCH_AMPHUR) && isEmpty(SEARCH_PROVINCE)  && isEmpty(SEARCH_ZIPCODE)){
			 alertBox(ERROR_REQUIRED_ONE_CRITERIA);
		}else{
			 Pace.block();
			 var className = 'com.eaf.orig.ulo.app.view.util.ajax.SearchAddressZipCodeAjax';
			 var $data =  $("#SECTION_ADDRESS_ZIPCODE_POPUP *").serialize();
			 ajax(className, $data,ADDRESS_SEARCH_BTNAfterActionJS);
		 }	
	}catch(exception){
		errorException(exception);
	} 
}

function ADDRESS_SEARCH_BTNActionJS(element,mode,name){
	try{
		var SEARCH_TAMBOL = $('[name="SEARCH_TAMBOL"]').val();
		var SEARCH_AMPHUR = $('[name="SEARCH_AMPHUR"]').val();
		var SEARCH_PROVINCE = $('[name="SEARCH_PROVINCE"]').val();
		var SEARCH_ZIPCODE = $('[name="SEARCH_ZIPCODE"]').val();	
		var $data = 'SEARCH_PROVINCE='+SEARCH_PROVINCE;
				$data += '&SEARCH_ZIPCODE='+SEARCH_ZIPCODE;
				$data +='&SEARCH_TAMBOL'+SEARCH_TAMBOL;
				$data +='&SEARCH_AMPHUR'+SEARCH_AMPHUR;
		validateFormAction(VALIDATE_PROVINCE_LENGTH, $data,null,DISPLAY_ERROR_POPUP);	
	}catch(exception){
		errorException(exception);
	} 
}

function  ADDRESS_SEARCH_BTNAfterActionJS(data){
	refreshSubForm("ADDRESS_ZIPCODE_POPUP");		
}


function SERACH_ADDRESS_ZIPCODE_FORMAfterSaveActionJS(data) {
	try{
		displayJSON(data);
		closePopupDialog('SERACH_ADDRESS_ZIPCODE_FORM',POPUP_ACTION_SAVE);
		if(!isEmpty(data)){
			var CARD_LINK = $("[name='CARD_LINK']").val();
			if("Y" == CARD_LINK){
				targetDisplayHtml("CL_AMPHUR",MODE_VIEW,"targetDisplayHtml");
				targetDisplayHtml("CL_PROVINCE",MODE_VIEW,"targetDisplayHtml");
				targetDisplayHtml("CL_ZIPCODE",MODE_VIEW,"targetDisplayHtml");
			}else{
				targetDisplayHtml("AMPHUR",MODE_VIEW,"targetDisplayHtml");
//#Comment For popupList change field subform
//				targetDisplayHtml("PROVINCE",MODE_VIEW,"targetDisplayHtml");
				targetDisplayHtml("ZIPCODE",MODE_VIEW,"targetDisplayHtml");
			}
		}
	}catch(exception){
		errorException(exception);
	}
}