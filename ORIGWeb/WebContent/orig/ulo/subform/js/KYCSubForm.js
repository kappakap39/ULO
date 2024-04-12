 
function WORK_DATEActionJS(){
	try{
		var WORK_START_DATE = $("[name='WORK_START_DATE']").val();
		var WORK_END_DATE = $("[name='WORK_END_DATE']").val();
		if(!isEmpty(WORK_START_DATE) && !isEmpty(WORK_END_DATE)){
			try {	
				if (!validateBetweenDate(WORK_START_DATE,WORK_END_DATE)) {	
					alertBox('ERROR_KYC_WORK_DATE');
					displayHtmlElement('WORK_START_DATE', '');
					displayHtmlElement('WORK_END_DATE', '');
				}			 
			} catch (e) { 
				alert("ERROR : Checking date from - to\n -"+e.description) ;
			}
		}	
	}catch(exception){
		errorException(exception);
	}
}
function REL_TITLE_DESCActionJS(element, mode, name) {
	try{
		var element_NAME = $("[name='"+element+"']").val();
		var REL_TITLE_NAME = 'REL_TITLE_NAME';
		var className = 'com.eaf.orig.ulo.app.view.form.manual.GetTitleName';
		var $data =  $("#KYC_SUBFORM *").serialize();
			$data += '&TITLE_NAME='+element;
			$data += '&TITLE_CODE_NAME='+REL_TITLE_NAME;
		ajax(className, $data, displayJSON);
	}catch(exception){
		errorException(exception);
	}
}
function WORK_END_DATEActionJS() {
	try{
		WORK_DATEActionJS();
	}catch(exception){
		errorException(exception);
	}
}
function WORK_START_DATEActionJS() {
	try{
		WORK_DATEActionJS();
	}catch(exception){
		errorException(exception);
	}
}

function RELATION_FLAGInitJS(element, mode, name) {
	try{
		setRELATION_FLAG(element, mode, name);
	}catch(exception){
		errorException(exception);
	}
}
function RELATION_FLAGActionJS(element, mode, name) {
	try{
		setRELATION_FLAG(element, mode, name);
		var radios = $("[name='"+element+"']:checked");    
		var select = radios.val();
		if(select == FLAG_YES){
			var subformId = $("#KYC_SUBFORM [name='subformId']").val();
			refreshSubForm(subformId,"Y");
		}else{
			$('#KYC .text-danger').hide();
		}
	}catch(exception){
		errorException(exception);
	}
}
function setRELATION_FLAG(element, mode, name) {
	try{
		var radios = $("[name='"+element+"']:checked");    
		var select = radios.val();
		if(select == FLAG_YES){
			targetDisplayHtml('REL_TITLE_DESC', MODE_EDIT, 'REL_TITLE_DESC');
			targetDisplayHtml('REL_NAME', MODE_EDIT, 'REL_NAME');
			targetDisplayHtml('REL_SURNAME', MODE_EDIT, 'REL_SURNAME');
			targetDisplayHtml('REL_POSITION', MODE_EDIT, 'REL_POSITION');
			targetDisplayHtml('REL_DETAIL', MODE_EDIT, 'REL_DETAIL');
			targetDisplayHtml('WORK_START_DATE', MODE_EDIT, 'WORK_START_DATE');
			targetDisplayHtml('WORK_END_DATE', MODE_EDIT, 'WORK_END_DATE');
		}else{
			targetDisplayHtml('REL_TITLE_DESC', MODE_VIEW, 'REL_TITLE_DESC',FLAG_YES);
			targetDisplayHtml('REL_NAME', MODE_VIEW, 'REL_NAME',FLAG_YES);
			targetDisplayHtml('REL_SURNAME', MODE_VIEW, 'REL_SURNAME',FLAG_YES);
			targetDisplayHtml('REL_POSITION', MODE_VIEW, 'REL_POSITION',FLAG_YES);
			targetDisplayHtml('REL_DETAIL', MODE_VIEW, 'REL_DETAIL',FLAG_YES);
			targetDisplayHtml('WORK_START_DATE', MODE_VIEW, 'WORK_START_DATE',FLAG_YES);
			targetDisplayHtml('WORK_END_DATE', MODE_VIEW, 'WORK_END_DATE',FLAG_YES);
		}
	}catch(exception){
		errorException(exception);
	}
}