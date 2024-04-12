$(document).ready(function() {
	$('[name="WF_CHECKBOX_ALL"]').click(function(event) {
		try{
			if (this.checked) {
				$(':checkbox').each(function() {
					if (false == this.disabled) {
						this.checked = true;
					}

				});
			} else {
				$(':checkbox').each(function() {
					this.checked = false;
				});
			}
		}catch(exception){
			errorException(exception);
		}
	});
});

function SEARCH_BTNActionJS() {
	try{
		var formElement = $('#appFormName .work_area');
		var checker = countFilledInput(formElement);
		if (checker >= SEARCH_FIELDS_REQUIRED) {
			if (validateElementInputChars('[name=FIRST_NAME],[name=LAST_NAME]')) {
				Pace.block();
				$('#action').val('SearchReAssign');
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
function RESET_BTNActionJS() {
	try{
		clearForm($('#appFormName .work_area'));
	}catch(exception){
		errorException(exception);
	}
}
function WF_CHECKBOXActionJS(fieldName, mode, fieldId) {
}
function isSelected() {
	try {
		$(':checkbox').each(function() {
			if (this.checked == true && "ALL" != this.value) {
				throw new Error('break');
			}
		});
	} catch (e) {
		if (e.message == 'break') {
			return true;
		}
	}

	return false;
}
function isSelectedSameRole() {
	var isSame = true;
	try{
		var role = "";
		$('[name=WF_CHECKBOX]:checked').each(function() {
			var thisRole = $(this).parent().parent().parent().parent().attr('data-role');
			if (role == "") {
				role = thisRole;
				return;
			}
			if (role != thisRole) {
				isSame = false;
			}
		});
	}catch(exception){
		errorException(exception);
	}
	return isSame;
}

function RE_ASSIGN_BTNActionJS() {
	try{
		if(isSelected()){
			if (isSelectedSameRole()){
				Pace.block();
				var $data = getCheckBoxValueList();
				ajax('com.eaf.orig.ulo.app.view.util.ajax.ReAssignTaskCheckRole', $data, RE_ASSIGN_Action);
			} else {
				alertBox(PLEASE_SELECT_SAME_ROLE);
			}
		} else {
			alertBox(PLEASE_SELECT_LESS_THAN_1_APPLICATION);
		}
	}catch(exception){
		Pace.unblockFlag = true;
		Pace.unblock();
		errorException(exception);
	}
}
function RE_ASSIGN_Action(data) {
	try{
		Pace.unblockFlag = true;
		Pace.unblock();
		if(null != data && "" != data){
			alertBox(data);
		}else{
			createPopupDialog('POPUP_RE_ASSIGN_FORM');
			loadPopupDialog("POPUP_RE_ASSIGN_FORM", "INSERT", "0", "ASSING_ROLE_VALUE="+getSelectedRole(), "", "650", "200");
		}
	}catch(exception){
		Pace.unblockFlag = true;
		Pace.unblock();
		errorException(exception);
	}
}
function getCheckBoxValueList() {
	var valList = "";
	try{
		$(':checkbox').each(function() {
			if (this.checked == true && "ALL" != this.value) {
				valList += this.value;
			}
		});
	}catch(exception){
		errorException(exception);
	}
	return valList;
}

function getSelectedRole() {
	var role = "";
	try{
		$('[name=WF_CHECKBOX]:checked').each(function() {
			var thisRole = $(this).parent().parent().parent().parent().attr('data-role');
			if (role == "") {
				role = thisRole;
				return;
			}
		});
	}catch(exception){
		errorException(exception);
	}
	return role;
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