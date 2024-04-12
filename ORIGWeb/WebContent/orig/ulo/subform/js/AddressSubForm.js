function PLACE_RECEIVE_CARDInitJS(element, mode, name){
	try{
		if(MODE_EDIT == mode){
			if ($('[name=APPLICATION_TEMPLATE_ID]').val() == KPL_SINGLE) {
				targetDisplayHtml('PLACE_RECEIVE_CARD', MODE_VIEW);
			} else {
				targetDisplayHtml('PLACE_RECEIVE_CARD', MODE_EDIT);
			}
		}
	}catch(exception){
		errorException(exception);
	}
}
function getPopupForm(SEND_DOC ,ADDRESS_TYPE , MATCHES_CARDLINK){
	var popupForm='';
	try{
		var APPLICATION_STATUS = $('#ADDRESS_SUBFORM [name=APPLICATION_STATUS]').val();
		var formType = '_2';
		try{
			if(FLOW_ACTION_TYPE==ACTION_TYPE_ENQUIRY && lookup(APPPLICATION_STATUS_CARDLINK_ADDRESS_DISPLAY,APPLICATION_STATUS)){
				formType = '_2';
			}else{
				if(SEND_DOC != ADDRESS_TYPE && ROLE_ADDRESS_POPUP_CONDITION.indexOf(ROLE_ID)==-1 && ADDRESS_TYPE != ADDRESS_TYPE_DOCUMENT){
					formType = '_1';
				}
				if(ROLE_ADDRESS_POPUP_CONDITION.indexOf(ROLE_ID)>-1 && MATCHES_CARDLINK != "TRUE"){
					formType = '_3';
				}
			}
		}catch(exception){
			errorException(exception);
		}
		if(ADDRESS_TYPE==ADDRESS_TYPE_CURRENT){
			popupForm='POPUP_CURRENT_ADDRESS_FORM'+formType;
		}else if(ADDRESS_TYPE==ADDRESS_TYPE_WORK){
			popupForm='POPUP_OFFICE_ADDRESS_FORM'+formType;
		}else if(ADDRESS_TYPE==ADDRESS_TYPE_DOCUMENT){
			popupForm='POPUP_HOME_ADDRESS_FORM'+formType;
		}
	}catch(exception){
		errorException(exception);
	}
	return popupForm;
}
function BRANCH_RECEIVE_CARDActionJS(){
	var descriptionBranch = $("select[name='BRANCH_RECEIVE_CARD']")[0].selectize.$input.text();
	displayHtmlElement('BRANCH_RECEIVE_CARD_NAME', descriptionBranch);
}
function EDIT_ADDRESSActionJS(ADDRESS_TYPE){
	try{
		$('#ADDRESS_SUBFORM').find('a').attr('style',"pointer-events: none;");
		setTimeout(function(event) {
			$('#ADDRESS_SUBFORM').find('a').removeAttr('style');
	   }, 300);		
		var subformId = $("#ADDRESS_SUBFORM [name='subformId']").val();
		setPropertiesSubform(subformId,function(data,status,xhr){		
			try{
				var PERSONAL_SEQ = $("#ADDRESS_SUBFORM [name='PERSONAL_SEQ']").val();
				var PERSONAL_TYPE = $("#ADDRESS_SUBFORM [name='PERSONAL_TYPE']").val();
				var SEND_DOC = $("[name='SEND_DOC']").val();
				var MATCHES_CARDLINK = $("#ADDRESS_SUBFORM [name='MATCHES_CARDLINK']").val();
				var $data = '&ADDRESS_TYPE='+ADDRESS_TYPE;
					$data += '&PERSONAL_SEQ='+PERSONAL_SEQ;
					$data += '&PERSONAL_TYPE='+PERSONAL_TYPE;	
					$data += '&SEND_DOC='+SEND_DOC;	
					$data += '&ACTION_TYPE='+ACTION_EDIT;
				var popupForm = getPopupForm(SEND_DOC ,ADDRESS_TYPE,MATCHES_CARDLINK);
				loadPopupDialog(popupForm,'UPDATE','0',$data,'N');
			}catch(exception){
				errorException(exception);
			}
		});
	}catch(exception){
		errorException(exception);
	}
}

function PLACE_RECEIVE_CARDActionJS() {
	try{
		var subformId = $("#ADDRESS_SUBFORM [name='subformId']").val();
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}	
}
function BRANCH_RECEIVE_CARDInitJS(){
	try{
		var PLACE_RECEIVE_CARD = $("[name='PLACE_RECEIVE_CARD']").val();
		if(PLACE_RECEIVE_CARD == PLACE_RECEIVE_CARD_MAJOR){
			targetDisplayHtml("BRANCH_RECEIVE_CARD",MODE_EDIT,"BRANCH_RECEIVE_CARD");
		}else{
			targetDisplayHtml("BRANCH_RECEIVE_CARD",MODE_VIEW,"BRANCH_RECEIVE_CARD","Y");
			displayHtmlElement('BRANCH_RECEIVE_CARD', '');
		}
	}catch(exception){
		errorException(exception);
	}
}

function SEND_DOCInitJS(){
	
}
function SEND_DOCActionJS(element, mode, name){
	try{
		var subformId = $("#ADDRESS_SUBFORM [name='subformId']").val();
		setPropertiesSubform(subformId,function(data,status,xhr,element){	
			try{
				var SEND_DOC = $("#ADDRESS_SUBFORM [name='"+element+"']").val();
				var PERSONAL_SEQ = $("#ADDRESS_SUBFORM [name='PERSONAL_SEQ']").val();
				var PERSONAL_TYPE = $("#ADDRESS_SUBFORM [name='PERSONAL_TYPE']").val();
				var MATCHES_CARDLINK = $("#ADDRESS_SUBFORM [name='MATCHES_CARDLINK']").val();
				displayHtmlElement('PLACE_RECEIVE_CARD', SEND_DOC);		
				if(lookup(SEND_DOC_SUBFORM_ID_ACTION,subformId)){
					var $data = '&ADDRESS_TYPE='+SEND_DOC;
						$data += '&PERSONAL_SEQ='+PERSONAL_SEQ;
						$data += '&PERSONAL_TYPE='+PERSONAL_TYPE;
						$data += '&SEND_DOC='+SEND_DOC;	
						$data += '&ACTION_TYPE='+ACTION_ADD;	
						
					var popupForm='';
					var formType = '_2';
					if(ROLE_ID == ROLE_DE2 && MATCHES_CARDLINK != "TRUE"){
						formType = '_3';
					}
					if(SEND_DOC==ADDRESS_TYPE_CURRENT){
						popupForm='POPUP_CURRENT_ADDRESS_FORM'+formType;
					}else if(SEND_DOC==ADDRESS_TYPE_WORK){
						popupForm='POPUP_OFFICE_ADDRESS_FORM'+formType;
					}else if(SEND_DOC==ADDRESS_TYPE_DOCUMENT){
						popupForm='POPUP_HOME_ADDRESS_FORM'+formType;
					}
					loadPopupDialog(popupForm,'INSERT','0',$data);
				}	
			}catch(exception){
				errorException(exception);
			}				
		},element);
	}catch(exception){
		errorException(exception);
	}
}

function openPopUpAddress(){
	
}
function ZIPCODEActionJS(element, mode, name){
	try{
		var ZIPCODE = $("#ADDRESS_SUBFORM [name='"+element+"']").val();
		if(null!=ZIPCODE && ""!=ZIPCODE){
			if(ZIPCODE.length < 5){
				alertBox('ERROR_ZIPCODE');
				displayHtmlElement(element, '');
			}
		}
	}catch(exception){
		errorException(exception);
	}
}

function COMPANY_TITLEActionJS(element, mode, name){
	try{
		var COMPANY_TITLE = $("[name='"+element+"']").val();
		var suffix = element.replace(name,'');
		var COMPANY_NAME = $("[name='COMPANY_NAME"+suffix+"']").val();
		if(!isEmpty(COMPANY_NAME) && !isEmpty(COMPANY_TITLE)){
			var $data = 'COMPANY_TITLE='+COMPANY_TITLE+'&COMPANY_NAME='+encodeURIComponent(COMPANY_NAME);
			ajax('com.eaf.orig.ulo.app.view.util.ajax.GetCompanyNameFromDIH',$data,companyNameDIHResponseActionJS,element,name);
		}
	}catch(exception){
		errorException(exception);
	}
}
function COMPANY_NAMEActionJS(element, mode, name){
	try{
		var COMPANY_NAME = $("[name='"+element+"']").val();
		var suffix = element.replace(name,'');
		var COMPANY_TITLE = $("[name='COMPANY_TITLE"+suffix+"']").val();
		if(!isEmpty(COMPANY_NAME) && COMPANY_NAME.length>1 && !COMPANY_NAME.match(/^[0-9]+$/)){
			if(!isEmpty(COMPANY_NAME) && !isEmpty(COMPANY_TITLE)){
				var $data = 'COMPANY_TITLE='+COMPANY_TITLE+'&COMPANY_NAME='+encodeURIComponent(COMPANY_NAME);
				ajax('com.eaf.orig.ulo.app.view.util.ajax.GetCompanyNameFromDIH',$data,companyNameDIHResponseActionJS,element,name);
			}
		}else{
			if(!isEmpty(COMPANY_NAME)){
				$("[name="+element+"]")[0].selectize.removeItem(COMPANY_NAME);
				$("[name="+element+"]")[0].selectize.removeOption(COMPANY_NAME);
				alertBox('ERROR_TEXT_COMPANY_NAME');
			}
		}
	}catch(exception){
		errorException(exception);
	}
}
function companyNameDIHResponseActionJS(data,status,xhr,element,name) {
	try{
		if(!isEmpty(data)){
			var responseObject = $.parseJSON(data);
			if(null != responseObject && responseObject != undefined){
				var errorTextCode = responseObject.errorTextCode;
				if(!isEmpty(errorTextCode)){
					alertBox(errorTextCode);
				}
			}
		}
	}catch(exception){
		errorException(exception);
	}
}

function PROVINCEActionJS(name,mode,element){
	try{
		$('[name="'+name+'"]').attr('disabled',true);
		setTimeout(function(){$('[name="'+name+'"]').attr('disabled',false);}, 150);
		if(MODE_EDIT == mode){
			var $data = "&ADDRESS_TYPE="+$('[name="'+name+'_TYPE"]').val();
				   $data+="&PERSONAL_SEQ="+$('[name="PERSONAL_SEQ"]').val();
				   $data+="&PERSONAL_TYPE="+$('[name="PERSONAL_TYPE"]').val();				   
				   $data+="&FUNCTION_POPUP="+SEARCH_BY_TAMBOL;		   
			loadPopupMasterAddress($data); 
		}
		clearTimeout(150);
	}catch(exception){
		errorException(exception);
	}
}

function loadPopupMasterAddress(data){
	try{

		loadPopupDialog('SERACH_ADDRESS_ZIPCODE_FORM','INSERT','0',data,"");
	}catch(exception){
		errorException(exception);
	}
}
 