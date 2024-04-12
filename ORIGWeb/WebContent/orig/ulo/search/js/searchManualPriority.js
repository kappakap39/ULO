function SEARCH_BTNActionJS(){
	try{
		var formElement = $('#appFormName .work_area');
		var checker = countFilledInput(formElement);
		if (checker >= SEARCH_FIELDS_REQUIRED) {
			if (validateElementInputChars('[name=FIRST_NAME],[name=LAST_NAME]')) {
				Pace.block();
				$('#action').val('SearchManualPriority');
				$('#handleForm').val('N');
				$('#appFormName').submit();
			} else {
				alertBox(PLEASE_INPUT_MORE_CHARACTER);
			}
		} else {
			alertBox('ERROR_REQUIRED_ONE_CRITERIA');
		}
	}catch(exception){
		errorException(exception);
	}
}

function RESET_BTNActionJS(){
	try{
		clearForm($('#appFormName .work_area'));
	}catch(exception){
		errorException(exception);
	}
}

function isSelected(){
	try{
		if ($('[name^=WF_CHECKBOX]:not([name$=ALL]):checked').length > 0 && $('[name=PRIORITY]').is(':checked')) {
			return true;
		} else {
			return false;
		}
	}catch(exception){
		errorException(exception);
	}
	return false;
}
function WF_CHECKBOXActionJS(fieldName,mode,fieldId){	
	try{
		if("WF_CHECKBOX_ALL"==fieldName){
			$('.enqtable .enqlist input').prop('checked', $('[name=WF_CHECKBOX_ALL]').prop('checked'));
		} else {
			var checkboxCount = $('.enqtable .enqlist input').length;
			var checkedboxCount = $('.enqtable .enqlist input:checked').length;
			$('[name=WF_CHECKBOX_ALL]').prop({
				'indeterminate': (checkedboxCount > 0) ^ (checkboxCount == checkedboxCount),
				'checked': (checkboxCount == checkedboxCount)
			});
		}
	}catch(exception){
		errorException(exception);
	}
}

function SET_PRIORITY_BTNActionJS(){
	try{
		var checkBoxValue = getCheckBoxValueList();
		var isCheckPriority = $('[name=PRIORITY]').is(':checked');
		
		if(isCheckPriority && ""!=checkBoxValue && null!=checkBoxValue && undefined !=checkBoxValue){
			confirmBox(MSG_CONFIRM_CHANGE_PRIORITY,function(comfirmFlag){
				if(comfirmFlag == "Y"){
					Pace.block();
					var $data = checkBoxValue+"&PRIORITY="+getRadioCheckedValue("PRIORITY");
					ajax('com.eaf.orig.ulo.app.view.util.ajax.ManualPriorityTask',$data,AfterManualPriority);
				}
			});
		}else{
			if(null ==checkBoxValue || ""==checkBoxValue || undefined==checkBoxValue){
				alertBox(PLEASE_SELECT_LESS_THAN_1_APPLICATION);
			}else if(!isCheckPriority){
				alertBox(PLEASE_SELECT_PRIORITY);
			}
		}
	}catch(exception){
		errorException(exception);
	}
}

function getCheckBoxValueList(){
	var valList="";
	try{
		$('[name^=WF_CHECKBOX]:not([name$=ALL]):checked').each(function() {
			valList+="&CHECK_BOX_VALUE="+this.value;
		});
	}catch(exception){
		errorException(exception);
	}	
	return valList;
}
function AfterManualPriority(data){
	try{
		if(null!=data && ""!=data){
			Pace.unblockFlag = true;
			Pace.unblock();
			alertBox(""+data+"");
		}else{
			$('[name="SEARCH_BTN"]').click();
		}
	}catch(exception){
		Pace.unblockFlag = true;
		Pace.unblock();
		errorException(exception);
	}	
}

function getRadioCheckedValue(obj_name){
	try{
		   return $('[name='+obj_name+'][type=radio]:checked').val();
	}catch(exception){
		errorException(exception);
	}
	return "";
}

function FIRST_NAMEActionJS(element,mode,fieldName){
	try{
		var elementObject = $("[name='"+element+"']");
		var elementValue = elementObject.val();
		elementValue = elementValue.toUpperCase();
		elementObject.val(elementValue);
	}catch(exception){
		errorException(exception);
	}
}
function LAST_NAMEActionJS(element,mode,fieldName){
	try{
		var elementObject = $("[name='"+element+"']");
		var elementValue = elementObject.val();
		elementValue = elementValue.toUpperCase();
		elementObject.val(elementValue);
	}catch(exception){
		errorException(exception);
	}
}
function ID_NOActionJS(element,mode,fieldName){
	try{
		var elementObject = $("[name='"+element+"']");
		var elementValue = elementObject.val();
		elementValue = elementValue.toUpperCase();
		elementObject.val(elementValue);
	}catch(exception){
		errorException(exception);
	}
}
function APPLICATION_GROUP_NOActionJS(element,mode,fieldName)
{
	try{
		var elementObject = $("[name='"+element+"']");
		var elementValue = elementObject.val();
		elementValue = elementValue.toUpperCase();
		if(elementValue && /^[0-9]*$/.test(elementValue))
		{
			elementValue = 'QR-' + elementValue;
		}
		elementObject.val(elementValue);
	}catch(exception){
		errorException(exception);
	}
}