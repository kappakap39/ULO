$(function() {
	$('.enqall').click(function(e) {
		try{
		    $('.enqtable input.enqlist:not(:disabled)').prop('checked', $(this).prop('checked'));
		}catch(exception){
			errorException(exception);
		}
	});
	
	$('.enqlist').click(function(e) {
		try{
			e.stopPropagation(); // This will not touch parent
			var container = $(this).parent().parent().parent();
			var checkboxCount = container.find('input.enqlist:not(:disabled)').length;
			var checkedboxCount = container.find('input.enqlist:checked').length;
			$('.enqall').prop({
				'indeterminate': (checkedboxCount > 0) ^ (checkboxCount == checkedboxCount),
				'checked': (checkboxCount == checkedboxCount)
			});
		}catch(exception){
			errorException(exception);
		}
	});
	
	$('.enqtable td:first-child').click(function(e) {
		$(this).find('input.enqlist').trigger('click');
	});
});

function CLEAR_VIEWEQActionJS(){
	var elementObject =$("[name='EQ_SEARCH_BTN']");
	var onclickActionJS = elementObject.attr('onclick').replace('VIEW','EDIT');
	elementObject.attr('onclick',onclickActionJS);
}

function getSearchEnquiryPage() {
	return $('[name=searchClaimJobEnquiryPage]').val();
}

function SAVE_CLAIM_BTNActionJS(element,mode,name)
{
	confirmBox(MSG_CONFIRM_SAVE_CLAIM,function(choice) 
	{
		if (choice == "Y") 
		{
			var complete = 0;
			var todo = 0;
			var claimInfos = $('#resultsBody').find("input[name^='CLAIM_FLAG']");
			
			Pace.block();
			claimInfos.each(function() 
			{
					var name = $(this).attr('name');
					var mode = $(this).prop('disabled');
					var check = $(this).is(":checked");
					var isClaimed = $(this).attr('isClaimed');
					if(!mode)
					{
						todo++;
						var claimId = name.substring(11);
						var $data = 'claimId=' + claimId + '&claimFlag=' + check + '&isClaimed=' + isClaimed;
						//alert('SAVE_CLAIM_BTN is clicked - Claim claimId : ' + claimId);
										
						try{
							 ajax('com.eaf.orig.ulo.app.view.util.pa.PAClaimJobAjax',$data,function(){complete++;});
						}catch(exception)
						{
							Pace.unblockFlag = true;
							Pace.unblock();
							errorException(exception);
						}
						
					}
			}
			);
			
			var startTime = new Date().getTime();
			var interval = setInterval(function() {
			    if (complete == todo) 
			    {
			    	 clearInterval(interval);
			    	 Pace.unblockFlag = true;
					 Pace.unblock();
					 refreshSearchControlActionJS();
			    }
			    else if(((new Date().getTime() - startTime) > 20000))
			    {
			    	 alert('Timeout');
			    	 clearInterval(interval);
			    	 Pace.unblockFlag = true;
					 Pace.unblock();
					 refreshSearchControlActionJS();
			    }
			}, 1000);
			
		}
	});
}

function CLAIM_JOB_EXPORT_BUTTONActionJS(element,mode,name)
{
	//alert('Export Button is clicked');
	var claimType = $('[name=ACTION_TYPE]').val();
	document.getElementById('my_iframe').src = "/ORIGWeb/exportClaim?claimType=" + claimType;
}

function enable_CLAIM_JOB_EXPORT_BUTTON()
{
	var exportBTN = $('[name=CLAIM_JOB_EXPORT_BUTTON]');
	exportBTN.addClass('btn2-green');
	exportBTN.prop('disabled', false);
}

function COMPLETE_BTNActionJS(element,mode,name)
{
	confirmBox(MSG_CONFIRM_COMPLETE_CLAIM,function(choice) 
	{
		if (choice == "Y") 
		{
			Pace.block();
			$('#resultsBody').find("input[name^='COMPLETE_FLAG']").each(function() 
			{
				
				var name = $(this).attr('name');
				var mode = $(this).prop('disabled');
				var check = $(this).is(":checked");
				if(!mode && check)
				{
					var claimId = name.substring(14);
					var $data = 'claimId=' + claimId ;
					
					//alert('COMPLETE_BTN is clicked - complete job for claimId : ' + claimId);
										
					try{
						ajax('com.eaf.orig.ulo.app.view.util.pa.PACompleteJobAjax',$data,function(){});
					}catch(exception)
					{
						Pace.unblockFlag = true;
						Pace.unblock();
						errorException(exception);
					}
				}
			}
			);
			Pace.unblockFlag = true;
			Pace.unblock();
			refreshSearchControlActionJS();
		}
	});
}

function getMemoLink(appGroupNo)
{
	alert('getMemoLink : ' + appGroupNo);
	//var isMyTask = $('[name=isMyTask]').val();
	//loadPopupDialog('POPUP_INSTRUCTION_MEMO_FORM','INSERT','0','appGroupNo=' + appGroupNo + '&isMyTask=' + isMyTask);
}

function getStampLink(appGroupNo, completeFlag)
{
	//alert('getStampLink : ' + appGroupNo);
	var isMyTask = $('[name=isMyTask]').val();
	if(isMyTask == "true" && completeFlag == "Y")
	{
		isMyTask = "false";
	}
	loadPopupDialog('POPUP_STAMP_DUTY_FORM','INSERT','0','appGroupNo=' + appGroupNo + '&isMyTask=' + isMyTask);
}

function getMailLink(appGroupNo, completeFlag)
{
	//alert('getMailLink : ' + appGroupNo);
	var isMyTask = $('[name=isMyTask]').val();
	if(isMyTask == "true" && completeFlag == "Y")
	{
		isMyTask = "false";
	}
	loadPopupDialog('POPUP_MAILING_ADDRESS_FORM','INSERT','0','appGroupNo=' + appGroupNo + '&isMyTask=' + isMyTask);
}

function cancel_AccSetup(appGroupNo)
{
	//alert('cancel_AccSetup : ' + appGroupNo);
	loadPopupDialog('POPUP_AS_CANCEL_REASON_FORM','INSERT','0','appGroupNo=' + appGroupNo);
}

function CLAIM_FLAGActionJS(element,mode,name)
{
}

function EQ_SEARCH_BTNActionJS(element,mode,name){
	if(mode == MODE_VIEW){
		return;
	}else if(mode == MODE_EDIT){
		EQ_SEARCH_AfterAction();	
	}
}
function EQ_SEARCH_AfterAction(){
	try{	
		var formElement = $('#appFormName .work_area');
		var checker = countFilledInput(formElement);
//		checker = 2;  FOR DEBUGGING PURPOSE
			if (checker >= SEARCH_FIELDS_REQUIRED) {
			if (validateElementInputChars('[name=TH_FIRST_NAME],[name=TH_LAST_NAME],[name=COMPANY]')) {
				Pace.block();
				$('#action').val(getSearchEnquiryPage());
				$('#handleForm').val('N');
				$('#appFormName').submit();
			} else {
				alertBox(ERROR_LENGTH_OF_FIELD);
			}
		} else {
			alertBox('ERROR_REQUIRED_ONE_CRITERIA');
		}
	}catch(exception){
		errorException(exception);
	}

}
function EQ_RESET_BTNActionJS(){
	try{
		clearForm($('#appFormName .work_area'));
	}catch(exception){
		errorException(exception);
	}
}

function TH_FIRST_NAMEActionJS(element,mode,fieldName){
	try{
		var elementObject = $("[name='"+element+"']");
		var elementValue = elementObject.val();
	 	elementValue = elementValue.toUpperCase();
	 	elementObject.val(elementValue);
	}catch(exception){
		errorException(exception);
	}
}
function TH_LAST_NAMEActionJS(element,mode,fieldName){
	try{
		var elementObject = $("[name='"+element+"']");
		var elementValue = elementObject.val();
		elementValue = elementValue.toUpperCase();
		elementObject.val(elementValue);
	}catch(exception){
		errorException(exception);
	}
}