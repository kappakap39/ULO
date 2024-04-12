var PERSONAL_SEQ = 1;

function ADRSTSInitJS(element,mode,fieldName){
	try{
		ADRSTSActionJS();
	}catch(exception){
		errorException(exception);
	}
}
function ADRSTSActionJS(element,mode,fieldName) {
	try{
		if(mode == MODE_EDIT){
			var ADRSTS = $("[name='ADRSTS']").val();
			if(ADRSTS_TYPE_HOME==ADRSTS || ADRSTS_TYPE_INSTALMENT==ADRSTS || ADRSTS_TYPE_RENT==ADRSTS){
				targetDisplayHtml("RENTS",MODE_EDIT,"targetDisplayHtml");
			}else {
				targetDisplayHtml("RENTS",MODE_VIEW,"targetDisplayHtml","Y");	
			}
		}
	}catch(exception){
		errorException(exception);
	}	
}
function COUNTRYInitJS(element,mode,fieldName){
	try{
		if(null==$('[name="'+element+'"]').val() || ""==$('[name="'+element+'"]').val()){
			displayHtmlElement('COUNTRY', NATIONALITY_TH);
		}
	}catch(exception){
		errorException(exception);
	}
}
function RESIDEMActionJS() {
	try{
		var RESIDEM = $("[name='RESIDEM']").val();
		if (parseInt(RESIDEM) >= 12 || parseInt(RESIDEM) < 0) {
			$("[name='RESIDEM']").val("");
		}
	}catch(exception){
		errorException(exception);
	}
}

function ADDRESS_NAMEActionJS() {
	try{
		var ADDRESS_NAME = $("[name='ADDRESS_NAME']").val();
		if (ADDRESS_NAME.length == 1 && !validateSpecialCharacters(ADDRESS_NAME)) {
			$("[name='ADDRESS_NAME']").val("");
		}
	}catch(exception){
		errorException(exception);
	}
}

function ADDRESS_IDActionJS() {
	try{
		var ADDRESS_ID = $("[name='ADDRESS_ID']").val();
		if (ADDRESS_ID.length == 1 && !validateSpecialCharacters(ADDRESS_ID)) {
			$("[name='ADDRESS_ID']").val("");
		}
	}catch(exception){
		errorException(exception);
	}
}

function MOOActionJS() {
	try{
		var MOO = $("[name='MOO']").val();
		var numcheck = /^[0-9\-\/]*$/;
		if (!numcheck.test(MOO)) {
			$("[name='MOO']").val("");
		}
	}catch(exception){
		errorException(exception);
	}
}

function validateSpecialCharacters(value) {
	var numcheck = /^[a-zA-Z0-9]+$/;
	if (!numcheck.test(value)) {
		try {
			return false;
		} catch (e) {
		}
	} else {
		return true;
	}
}

function POPUP_CURRENT_ADDRESS_FORM_1AfterSaveActionJS() {	
	try{
		var subformId = $("#ADDRESS_SUBFORM [name='subformId']").val();
		closePopupDialog('POPUP_CURRENT_ADDRESS_FORM_1',POPUP_ACTION_SAVE);
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_CURRENT_ADDRESS_FORM_2AfterSaveActionJS() {	
	try{
		var subformId = $("#ADDRESS_SUBFORM [name='subformId']").val();
		closePopupDialog('POPUP_CURRENT_ADDRESS_FORM_2',POPUP_ACTION_SAVE);
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_CURRENT_ADDRESS_FORM_3AfterSaveActionJS() {	
	try{
		closePopupDialog('POPUP_CURRENT_ADDRESS_FORM_3',POPUP_ACTION_SAVE);
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_CURRENT_ADDRESS_FORM_1AfterCloseActionJS(action){
	try{
		if(action == POPUP_ACTION_SAVE){
			var SUBFORM_ID = $("#ADDRESS_SUBFORM [name='subformId']").val();
			refreshSubForm(SUBFORM_ID);
		}
	}catch(exception){
		errorException(exception);
	}	
}
function POPUP_CURRENT_ADDRESS_FORM_2AfterCloseActionJS(action){
	try{
		POPUP_CURRENT_ADDRESS_FORM_1AfterCloseActionJS(action);
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_CURRENT_ADDRESS_FORM_3AfterCloseActionJS(action){
	try{
		POPUP_CURRENT_ADDRESS_FORM_1AfterCloseActionJS(action);
	}catch(exception){
		errorException(exception);
	}
}

function POPUP_HOME_ADDRESS_FORM_1AfterSaveActionJS() {	
	try{
		var subformId = $("#ADDRESS_SUBFORM [name='subformId']").val();
		closePopupDialog('POPUP_HOME_ADDRESS_FORM_1',POPUP_ACTION_SAVE);
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_HOME_ADDRESS_FORM_2AfterSaveActionJS() {	
	try{
		var subformId = $("#ADDRESS_SUBFORM [name='subformId']").val();
		closePopupDialog('POPUP_HOME_ADDRESS_FORM_2',POPUP_ACTION_SAVE);
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_HOME_ADDRESS_FORM_3AfterSaveActionJS() {	
	try{
		var subformId = $("#ADDRESS_SUBFORM [name='subformId']").val();
		closePopupDialog('POPUP_HOME_ADDRESS_FORM_3',POPUP_ACTION_SAVE);
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_HOME_ADDRESS_FORM_1AfterCloseActionJS(action){
	try{
		if(action == POPUP_ACTION_SAVE){
			var SUBFORM_ID = $("#ADDRESS_SUBFORM [name='subformId']").val();
			refreshSubForm(SUBFORM_ID);
		}	
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_HOME_ADDRESS_FORM_2AfterCloseActionJS(action){
	try{
		POPUP_HOME_ADDRESS_FORM_1AfterCloseActionJS(action);
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_HOME_ADDRESS_FORM_3AfterCloseActionJS(action){
	try{
		POPUP_HOME_ADDRESS_FORM_1AfterCloseActionJS(action);
	}catch(exception){
		errorException(exception);
	}
}

function POPUP_OFFICE_ADDRESS_FORM_1AfterSaveActionJS() {	
	try{
		var subformId = $("#ADDRESS_SUBFORM [name='subformId']").val();
		closePopupDialog('POPUP_OFFICE_ADDRESS_FORM_1',POPUP_ACTION_SAVE);
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_OFFICE_ADDRESS_FORM_2AfterSaveActionJS() {	
	try{
		var subformId = $("#ADDRESS_SUBFORM [name='subformId']").val();
		closePopupDialog('POPUP_OFFICE_ADDRESS_FORM_2',POPUP_ACTION_SAVE);
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_OFFICE_ADDRESS_FORM_3AfterSaveActionJS() {	
	try{
		var subformId = $("#ADDRESS_SUBFORM [name='subformId']").val();
		closePopupDialog('POPUP_OFFICE_ADDRESS_FORM_3',POPUP_ACTION_SAVE);
		refreshSubForm(subformId,"Y");
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_OFFICE_ADDRESS_FORM_1AfterCloseActionJS(action){
	try{
		if(action == POPUP_ACTION_SAVE){
			var SUBFORM_ID = $("#ADDRESS_SUBFORM [name='subformId']").val();
			refreshSubForm(SUBFORM_ID);
		}	
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_OFFICE_ADDRESS_FORM_2AfterCloseActionJS(action){
	try{
		POPUP_OFFICE_ADDRESS_FORM_1AfterCloseActionJS(action);
	}catch(exception){
		errorException(exception);
	}
}
function POPUP_OFFICE_ADDRESS_FORM_3AfterCloseActionJS(action){
	try{
		POPUP_OFFICE_ADDRESS_FORM_1AfterCloseActionJS(action);
	}catch(exception){
		errorException(exception);
	}
}

function BTN_COPY_ADDRESSActionJS(){
	try{
		var COPY_ADDRESS_TYPE = $("[name='COPY_ADDRESS_TYPE']").val();
		var ADDRESS_TYPE_PAGE = $("[name='THIS_PAGE']").val();
		var className = 'com.eaf.orig.ulo.app.view.form.manual.CopyAddressPopupForm';
		var data = "FUNCTION="+TYPE_COPY;
			data += "&COPY_ADDRESS_TYPE=" + COPY_ADDRESS_TYPE;
			data +="&ADDRESS_TYPE_PAGE="+ADDRESS_TYPE_PAGE;
			 ajax(className, data, displayJSON);
	}catch(exception){
		errorException(exception);
	}
}
function COPY_ADDRESSActionJS(COPY_ADDRESS_TYPE,ADDRESS_TYPE_PAGE,FUNCTION){
	try{
		var className = 'com.eaf.orig.ulo.app.view.form.manual.CopyAddressPopupForm';
		var data = "FUNCTION="+FUNCTION;
		data += "&COPY_ADDRESS_TYPE=" + COPY_ADDRESS_TYPE;
		data +="&ADDRESS_TYPE_PAGE="+ADDRESS_TYPE_PAGE;
		data =COPY_ADDRESS(data);
		ajax(className, data,BTN_COPPY_HOMEAfterActionJS);
	}catch(exception){
		errorException(exception);
	}	
}
function BTN_COPPY_CURRENTActionJS(){
	try{
		var COPY_ADDRESS_TYPE = ADDRESS_TYPE_CURRENT;
		var ADDRESS_TYPE_PAGE = ADDRESS_TYPE_CURRENT_CARDLINK;
		COPY_ADDRESSActionJS(COPY_ADDRESS_TYPE,ADDRESS_TYPE_PAGE,TYPE_COPY_CARDLINK);
	}catch(exception){
		errorException(exception);
	}
}
function BTN_COPPY_CURRENTAfterActionJS(data){
	try{
		displayJSON(data);
		previewCardLinkAddress();
	}catch(exception){
		errorException(exception);
	}
}
function BTN_COPPY_HOMEActionJS(){
	try{
		var COPY_ADDRESS_TYPE = ADDRESS_TYPE_DOCUMENT;
		var ADDRESS_TYPE_PAGE = ADDRESS_TYPE_DOCUMENT_CARDLINK;
		COPY_ADDRESSActionJS(COPY_ADDRESS_TYPE,ADDRESS_TYPE_PAGE,TYPE_COPY_CARDLINK);
	}catch(exception){
		errorException(exception);
	}
}
function BTN_COPPY_HOMEAfterActionJS(data){
	try{
		displayJSON(data);
		previewCardLinkAddress();
	}catch(exception){
		errorException(exception);
	}
}
function BTN_COPPY_OFFICEActionJS(){
	try{
		var COPY_ADDRESS_TYPE = ADDRESS_TYPE_WORK;
		var ADDRESS_TYPE_PAGE = ADDRESS_TYPE_WORK_CARDLINK;
		COPY_ADDRESSActionJS(COPY_ADDRESS_TYPE,ADDRESS_TYPE_PAGE,TYPE_COPY_CARDLINK);
	}catch(exception){
		errorException(exception);
	}
}
function BTN_COPPY_OFFICEAfterActionJS(data){
	try{
		displayJSON(data);
		previewCardLinkAddress();
	}catch(exception){
		errorException(exception);
	}
}
function COPY_ADDRESS(data){
	try{
		data +='&'+$('#addressTable :input').serialize();
		data +='&ADRSTS='+$('select[name=ADRSTS]').val();
		data +='&RESIDEY='+$('input[name=RESIDEY]').val();
		data +='&RESIDEM='+$('input[name=RESIDEM]').val();
	}catch(exception){
		errorException(exception);
	}
	return data;	
}
function ADDRESS_IDActionJS(name,mode,element){
	
}
function CL_ADDRESS_IDActionJS(name,mode,element){
	try{
		previewCardLinkAddress();
	}catch(exception){
		errorException(exception);
	}
}
function MOOActionJS(name,mode,element){
}
function CL_MOOActionJS(name,mode,element){
	try{
		previewCardLinkAddress();
	}catch(exception){
		errorException(exception);
	}
}
function SOIActionJS(name,mode,element){
}
function CL_SOIActionJS(name,mode,element){
	previewCardLinkAddress();
}
function MOO_BUILDING_SOIActionJS(name,mode,element){
	try{
		previewCardLinkAddress();
	}catch(exception){
		errorException(exception);
	}
}
function CL_MOO_BUILDING_SOIActionJS(name,mode,element){
	try{
		previewCardLinkAddress();
	}catch(exception){
		errorException(exception);
	}
}
function ROADActionJS(name,mode,element){
}
function CL_ROADActionJS(name,mode,element){
	try{
		previewCardLinkAddress();
	}catch(exception){
		errorException(exception);
	}
}
function TAMBOLActionJS(name,mode,element){
	try{
		$('[name="'+name+'"]').attr('disabled',true);
		setTimeout(function(){$('[name="'+name+'"]').attr('disabled',false);}, 150);
		if(MODE_EDIT == mode){
			var ADDRESS_ELEMENT_ID = $("[name='ADDRESS_ELEMENT_ID']").val();//alert(ADDRESS_ELEMENT_ID);
			var  $data= '&ADDRESS_ELEMENT_ID='+ADDRESS_ELEMENT_ID;
			   	 $data+="&PERSONAL_SEQ="+$('[name="PERSONAL_SEQ"]').val();
			   	 $data+="&PERSONAL_TYPE="+$('[name="PERSONAL_TYPE"]').val();				   
			   	 $data+="&FUNCTION_POPUP="+SEARCH_BY_TAMBOL;		   
			   	 loadPopupMasterAddress($data); 			
//			popupTAMBOL("");
		}
		clearTimeout(150);
	}catch(exception){
		errorException(exception);
	}
}
function CL_TAMBOLActionJS(name,mode,element){
	try{
		previewCardLinkAddress();
	}catch(exception){
		errorException(exception);
	}
}
function popupTAMBOL(data){
	try{
		var ADDRESS_ELEMENT_ID = $("[name='ADDRESS_ELEMENT_ID']").val();//alert(ADDRESS_ELEMENT_ID);
//		Pace.restart();
		var $data = 'FUNCTION_POPUP='+data;
			$data += '&ADDRESS_ELEMENT_ID='+ADDRESS_ELEMENT_ID;
		loadPopupDialog('POPUP_SERACH_ADDRESS_FORM','INSERT','0',$data);	
	}catch(exception){
		errorException(exception);
	}
}
function AMPHURActionJS(name,mode,element){
}
function CL_AMPHURActionJS(name,mode,element){
	try{
		previewCardLinkAddress();
	}catch(exception){
		errorException(exception);
	}
}
function PROVINCEActionJS(name,mode,element){
	try{
		if(MODE_EDIT == mode){
			var ADDRESS_ELEMENT_ID = $("[name='ADDRESS_ELEMENT_ID']").val();//alert(ADDRESS_ELEMENT_ID);
			var	  $data="&PERSONAL_SEQ="+$('[name="PERSONAL_SEQ"]').val();
				  $data+= '&ADDRESS_ELEMENT_ID='+ADDRESS_ELEMENT_ID;
				  $data+="&PERSONAL_TYPE="+$('[name="PERSONAL_TYPE"]').val();				   
				  // $data+="&FUNCTION_POPUP="+SEARCH_BY_PROVINCE;
				  $data+="&FUNCTION_POPUP="+SEARCH_BY_TAMBOL;
			loadPopupMasterAddress($data); 
		}
	}catch(exception){
		errorException(exception);
	}
	
	
}
function CL_PROVINCEActionJS(name,mode,element){
	try{
		previewCardLinkAddress();
	}catch(exception){
		errorException(exception);
	}
}
function ZIPCODEActionJS(name,mode,element){
	try{
		var ZIPCODE = $("[name='"+name+"']").val();
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
function CL_ZIPCODEActionJS(name,mode,element){
	try{
		var ZIPCODE = $("[name='"+name+"']").val();
		if(null!=ZIPCODE && ""!=ZIPCODE){
			if(ZIPCODE.length < 5){
				alertBox('ERROR_ZIPCODE');
				displayHtmlElement(element, '');
			}
		}
		previewCardLinkAddress();
	}catch(exception){
		errorException(exception);
	}
}
function CL_COMPANY_NAMEActionJS(name,mode,element){
	try{
		previewCardLinkAddress();
	}catch(exception){
		errorException(exception);
	}
}
function CL_DEPARTMENTActionJS(name,mode,element){
	try{
		previewCardLinkAddress();
	}catch(exception){
		errorException(exception);
	}
}
function CL_BUILDINGActionJS(name,mode,element){
	try{
		previewCardLinkAddress();
	}catch(exception){
		errorException(exception);
	}
}
function CL_FLOORActionJS(name,mode,element){
	try{
		previewCardLinkAddress();
	}catch(exception){
		errorException(exception);
	}
}

function previewCardLinkAddress(){
	try{
		var typePage="";
		var addressType=$("[name='THIS_PAGE']").val();
		if(addressType==ADDRESS_TYPE_CURRENT){
			typePage=ADDRESS_TYPE_CURRENT_CARDLINK;
		}else if(addressType==ADDRESS_TYPE_WORK){
			typePage=ADDRESS_TYPE_WORK_CARDLINK;
		}else if(addressType==ADDRESS_TYPE_DOCUMENT){
			typePage=ADDRESS_TYPE_DOCUMENT_CARDLINK;
		}
		var COPY_ADDRESS_TYPE = addressType;
		var ADDRESS_TYPE_PAGE = typePage;
		var className = 'com.eaf.orig.ulo.app.view.form.manual.CardLinkAddressPopupForm';
		var data = "FUNCTION="+TYPE_SAMPLE_CARDLINK;
			data += "&COPY_ADDRESS_TYPE=" + COPY_ADDRESS_TYPE;
			data +="&ADDRESS_TYPE_PAGE="+ADDRESS_TYPE_PAGE;
			data = COPY_CARDLINK_ADDRESS(data);
			ajax(className, data, cardLinkAddressAfterCopyAction);
	}catch(exception){
		errorException(exception);
	}
}

function cardLinkAddressAfterCopyAction(data){
	try{
		data = JSON.parse(data);
		if(!isEmpty(data)){
			for(var i in data) {
				$('#'+data[i].elementId+'').html(data[i].elementValue);
				$('#'+data[i].elementId+'').css('color','#'+data[i].elementParam);
			}
		}
	}catch(exception){
		errorException(exception);
	}
}

function COPY_CARDLINK_ADDRESS(data){
	try{
		data +='&'+$('#cardlinkTable :input').serialize();
	}catch(exception){
		errorException(exception);
	}
	return data;
}

function SEARCH_PROVINCE_BTNActionJS(){
	try{
		var SEARCH_AMPHUR = $("[name='SEARCH_AMPHUR']").val();
		var SEARCH_PROVINCE = $("[name='SEARCH_PROVINCE']").val();
		if (isEmpty(SEARCH_AMPHUR) || isEmpty(SEARCH_PROVINCE)) {
			alertBox('ERROR_AMPHUR');
		}else{
			SEARCH_ADDRESS_POPUPActionJS(SEARCH_BY_PROVINCE);
		}
	}catch(exception){
		errorException(exception);
	}
}

function SEARCH_TAMBOL_BTNActionJS(){
	try{
		var SEARCH_TAMBOL = $("[name='SEARCH_TAMBOL']").val();
		if (isEmpty(SEARCH_TAMBOL)) {
			alertBox('ERROR_TAMBOL');
		}else{
			SEARCH_ADDRESS_POPUPActionJS(SEARCH_BY_TAMBOL);
		}
	}catch(exception){
		errorException(exception);
	}
}

function SEARCH_ADDRESS_POPUPActionJS(function_id){
	try{
		var className = 'com.eaf.orig.ulo.app.view.util.inf.SearchAddressInfo';
		var $data =  $("#SECTION_SERACH_ADDRESS_POPUP *").serialize();	
			$data += "&FUNCTION="+function_id;
		ajax(className, $data,SEARCH_ADDRESS_POPUPAfterActionJS);
	}catch(exception){
		errorException(exception);
	}
}

function SEARCH_ADDRESS_POPUPAfterActionJS(data){
	try{
		data = JSON.parse(data);
		var tableData = $("#dataSearch table > tbody");
		tableData.html('');
		if(!isEmpty(data)){
			for(var i in data) {
				var elrow = "<tr>"+
								"<td><input type=\"radio\" name=\"SEARCH_ADDRESS_SELECT\" value=\'"+JSON.stringify(data[i],null,"")+"\' property=\"radio\" ></td>"+
								"<td>" + data[i].tumbol +"</td>"+
								"<td>" + data[i].amphur + "</td>"+
								"<td>" + data[i].province+"</td>"+
								"<td>" + data[i].zipCode+"</td>"+
							"</tr>";
				tableData.append(elrow);
			}
		}else{
			var elrow = "<tr>"+"<td colspan=\"5\">"+NO_RECORD_FOUND+"</td>"+"</tr>";
			tableData.append(elrow);
		}
	}catch(exception){
		errorException(exception);
	}	
}


function POPUP_SERACH_ADDRESS_FORMAfterSaveActionJS(data) {
	try{
		displayJSON(data);
		closePopupDialog('POPUP_SERACH_ADDRESS_FORM',POPUP_ACTION_SAVE);
		if(!isEmpty(data)){
			var CARD_LINK = $("[name='CARD_LINK']").val();
			if("Y" == CARD_LINK){
				targetDisplayHtml("CL_AMPHUR",MODE_VIEW,"targetDisplayHtml");
				targetDisplayHtml("CL_PROVINCE",MODE_VIEW,"targetDisplayHtml");
				targetDisplayHtml("CL_ZIPCODE",MODE_VIEW,"targetDisplayHtml");
			}else{
				targetDisplayHtml("AMPHUR",MODE_VIEW,"targetDisplayHtml");
				targetDisplayHtml("PROVINCE",MODE_VIEW,"targetDisplayHtml");
				targetDisplayHtml("ZIPCODE",MODE_VIEW,"targetDisplayHtml");
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

function loadPopupMasterAddress(data){
	try{
		loadPopupDialog('SERACH_ADDRESS_ZIPCODE_FORM','INSERT','0',data);
		
	}catch(exception){
		errorException(exception);
	}
}