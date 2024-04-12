function WM_SEARCH_BTNActionJS(element, mode, name) {
	if(mode == MODE_VIEW) {
		return;
	} else if(mode == MODE_EDIT) {
		WM_SEARCH_AfterAction();	
	}
}

function WM_SEARCH_AfterAction() {
	try {
		var formElement = $('#appFormName .work_area');
		var checker = countFilledInput(formElement);
		if(checker >= SEARCH_FIELDS_REQUIRED) {
			Pace.block();
			$('#action').val('SearchWMTask');
			$('#handleForm').val('N');
			$('#appFormName').submit();
		} else {
			alertBox('ERROR_REQUIRED_ONE_CRITERIA');
		}
	} catch(exception) {
		errorException(exception);
	}
}

function WM_VIEW_ERROR_ROW_CLICK(taskId) {
	loadPopupDialog("POPUP_WM_TASK_VIEW_ERROR_FORM", "INSERT", "0", "taskId=" + taskId, null, "650px", "320px");
}

function WM_RESET_BTNActionJS() {
	try {
		clearForm($('#appFormName .work_area'));
	} catch(exception) {
		errorException(exception);
	}
}

function WM_RETRY_BTN_CLICK(taskId) {
	try {
		confirmBox('Are you sure you want to retry this task?', function(confirmFlag) {
			if(FLAG_YES == confirmFlag) {
				Pace.block();
				var $data = "&TASK_ID=" + taskId;
				ajax('com.eaf.orig.ulo.app.view.util.ajax.RetryWMTask', $data, WM_RETRYCallback);
			}
		});
	} catch(exception) {
		Pace.unblockFlag = true;
		Pace.unblock();
		errorException(exception);
	}
	return false;
}

function WM_RETRYCallback(data) {
	try{
		WM_SEARCH_AfterAction();
	} catch(exception) {
		Pace.unblockFlag = true;
		Pace.unblock();
		errorException(exception);
	}
}

function WM_RESUBMIT_BTN_CLICK(refCode) {
	try {
		confirmBox('Are you sure you want to resubmit this application?', function(confirmFlag) {
			if(FLAG_YES == confirmFlag) {
				Pace.block();
				var $data = "&REF_CODE=" + refCode;
				ajax('com.eaf.orig.ulo.app.view.util.ajax.ResubmitApplication', $data, WM_RESUBMITCallback);
			}
		});		
	} catch(exception) {
		Pace.unblockFlag = true;
		Pace.unblock();
		errorException(exception);
	}
}

function WM_RESUBMITCallback(data) {
	try{
		WM_SEARCH_AfterAction();
	} catch(exception) {
		Pace.unblockFlag = true;
		Pace.unblock();
		errorException(exception);
	}
}

$(function() {
	$('.BTN_CANCEL_APP').click(function(e) {
		try {
			e.stopPropagation();
			e.preventDefault();
			var $data = 'rowData=' + $(this).attr('row-data');
			loadPopupDialog('POPUP_CALLCENTER_CANCEL_FORM', 'INSERT', '0', $data, '', '', POPUP_WIDTH_DEFUALT);
		} catch(exception) {
			errorException(exception);
		}
	});
	
	$('.BTN_COLLAPSE').click(function(e) {
		var $this = $(this);
		if(!$this.hasClass('panel-collapsed')) {
			$this.parents('.panel').find('.special_collapse').slideUp();
			$this.addClass('panel-collapsed');
			$this.find('i').removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
		} else {
			$this.parents('.panel').find('.special_collapse').slideDown();
			$this.removeClass('panel-collapsed');
			$this.find('i').removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
		}
	});
	
	$('.BTN_COLLAPSE_ALL').click(function(e) {
		$('.special_collapse').collapse('hide');
	});
	
	$('.BTN_EXPAND_ALL').click(function(e) {
		$('.special_collapse').collapse('show');
	});
});