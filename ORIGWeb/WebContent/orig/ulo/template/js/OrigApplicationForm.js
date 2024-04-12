/*function FICO_DE1_1AfterFicoActionJS(data,formId,action,handleForm,validateForm){
	try{
		saveApplicationFormAction(formId,action,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}
function FICO_FRAUDAfterFicoActionJS(data,formId,action,handleForm,validateForm){
	try{
		saveApplicationFormAction(formId,action,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}
function FICO_DE1_2AfterFicoActionJS(data,formId,action,handleForm,validateForm){
	try{
		var responseData = getResponseData(data);	
		var ficoApplication = $.parseJSON(responseData);
		var incomeScreenFlag = ficoApplication.incomeScreenFlag;
		if(incomeScreenFlag == FLAG_YES){
			var requestData = '';
			LoadRefreshFormAction(requestData);
		}else{
			saveApplicationFormAction(formId,action,handleForm,validateForm);
		}
	}catch(exception){
		errorException(exception);
	}
}
function FICO_CAAfterFicoActionJS(data,formId,action,handleForm,validateForm){
	try{
		var ficoApplication = $.parseJSON(data);
		var callEscalateFlag = ficoApplication.callEscalateFlag;
		if(callEscalateFlag == FLAG_YES){
			var $data  = 'className=com.eaf.orig.ulo.app.view.util.manual.SummaryPageAction';
				$data +='&callEscalateFlag='+FLAG_YES;
			var url = CONTEXT_PATH+'/Ajax';
			$.post(url,$data,function(dataVal,status,xhr){
				var responseData = getResponseData(dataVal);	
				var summary = $.parseJSON(responseData);
				var popupFormId = summary.formId;
				if(""!=popupFormId && null!=popupFormId){
					loadPopupDialog(popupFormId,'INSERT','0','');
				}else {
					if(!authorizedApplicationAction(formId,action,handleForm,validateForm)){
						applicationActionService(FICO_DECISION_ACTION,FicoDecisionAfterActionJS,formId,action,handleForm,validateForm);
					}
				}
				
			});
		}else{
			saveApplicationFormAction(formId,action,handleForm,validateForm);
		}
	}catch(exception){
		errorException(exception);
	}
}
function FICO_IAAfterFicoActionJS(data,formId,action,handleForm,validateForm){
	try{
		var ficoApplication = $.parseJSON(data);
		var docCompleteFlag = ficoApplication.docCompleteFlag;
		if(docCompleteFlag == FLAG_YES){
			saveApplicationFormAction(formId,action,handleForm,validateForm);
		}else{
			alertBox('ERROR_DOC_COMPLETE');
		}
	}catch(exception){
		errorException(exception);
	}
}

function FICO_DV2AfterFicoActionJS(data,formId,action,handleForm,validateForm){
	try{
		var formId = $("#FormHandlerManager [name='formId']").val();
		var ficoApplication = $.parseJSON(data);
		var action = BUTTON_ACTION_SUBMIT;
		var diffRequestFlag = ficoApplication.diffRequestFlag;
		var docCompleteFlag = ficoApplication.docCompleteFlag;
		if(FLAG_NO==docCompleteFlag){
			alertBox('ERROR_DOCUMENT_COMPLETE_FLAG');
		}else{
			if(diffRequestFlag == FLAG_YES){
				alertBox('ERROR_KPL_REQUEST_AMT_DIFF',LoadRefreshFormAction,"",DEFAULT_ALERT_BOX_WIDTH);
			}else{
				saveApplicationFormAction(formId,action,handleForm,validateForm);	
			}
		}
	}catch(exception){
		errorException(exception);
	}
}
function FICO_DE2AfterFicoActionJS(data,formId,action,handleForm,validateForm){
	try{
		saveApplicationFormAction(formId,action,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}*/



/*============================================
after call decision point function 
============================================*/
function REKEY_SENSITIVE_FORMAfterSaveActionJS() {
	try{
		var formId = $("#FormHandlerManager [name='formId']").val();
		var action = BUTTON_ACTION_SAVE;
		var handleForm = 'N';
		var validateForm = 'N';
		if(ROLE_ID == ROLE_DE1_1){
			applicationActionService(DECISION_IMPLEMENT_ACTION_DE1_1,DECISION_DE1_1AfterDecisionActionJS,formId,action,handleForm,validateForm);	
		}else if(ROLE_ID == ROLE_DE1_2){
			applicationActionService(DECISION_IMPLEMENT_ACTION_DE1_2,DECISION_DE1_2AfterDecisionActionJS,formId,action,handleForm,validateForm);	
		}
		CLOSE_POPUP_BTNActionJS();
	}catch(exception){
		errorException(exception);
	}
}

function DECISION_DE1_1AfterDecisionActionJS(data,formId,action,handleForm,validateForm){
	try{
		saveApplicationFormAction(formId,action,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}
function DECISION_FRAUDAfterDecisionActionJS(data,formId,action,handleForm,validateForm){
	try{
		saveApplicationFormAction(formId,action,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}
function DECISION_DE1_2AfterDecisionActionJS(data,formId,action,handleForm,validateForm){
	try{
		var decisionApplication = $.parseJSON(data);
		var incomeScreenFlag = decisionApplication.incomeScreenFlag;
		var savingPlusFlag = decisionApplication.savingPlusFlag;
		if(savingPlusFlag == 'A')
		{
			action='SEND_TO_FU_PEGA';
		}
		if(incomeScreenFlag == FLAG_YES){
			var requestData = '';
			LoadRefreshFormAction(requestData);
			refreshFormHeader();
		}
		else{
			saveApplicationFormAction(formId,action,handleForm,validateForm);
		}
	}catch(exception){
		errorException(exception);
	}
}
function DECISION_CAAfterDecisionActionJS(data,formId,action,handleForm,validateForm){
	try{
		var decisionApplication = $.parseJSON(data);
//		var callEscalateFlag = decisionApplication.callEscalateFlag;
			var $data  = 'className=com.eaf.orig.ulo.app.view.util.manual.SummaryPageAction';
				$data +='&callEscalateFlag='+FLAG_YES;
			var url = CONTEXT_PATH+'/Ajax';
			$.post(url,$data,function(dataVal,status,xhr){
				var responseData = getResponseData(dataVal);	
				var summary = $.parseJSON(responseData);
				var popupFormId = summary.formId;
				if(""!=popupFormId && null!=popupFormId){
					loadPopupDialog(popupFormId,'INSERT','0','');
				}else {
					if(!authorizedApplicationAction(formId,action,handleForm,validateForm)){
						applicationActionService(DECISION_SERVICE_ACTION,SummaryPageActionAfterCallDecision,formId,action,handleForm,validateForm);
					}
				}
				
			});
	}catch(exception){
		errorException(exception);
	}
}
function DECISION_CA_RECALAfterDecisionActionJS(data,formId,action,handleForm,validateForm){
	try{
		var decisionApplication = $.parseJSON(data);
		var debtAmount = decisionApplication.debtAmount;
		var debtBurdenCreditLimit = decisionApplication.debtBurdenCreditLimit;
		var debtRecommend = decisionApplication.debtRecommend;
		var debtBurden = decisionApplication.debtBurden;

		$('#DEBT_REC_VALUE').text(debtBurdenCreditLimit);
		$('#DEBT_BURDEN_VALUE').text(debtBurden);
		$('#DEBT_AMOUNT_VALUE').text(debtAmount);
	
		Pace.unblockFlag = true;Pace.unblock();
	}catch(exception){
		errorException(exception);
	}
}
function DECISION_CA_RECALAfterDecisionActionJS(data,formId,action,handleForm,validateForm){
	try{
		var decisionApplication = $.parseJSON(data);
		var debtAmount = decisionApplication.debtAmount;
		var debtBurdenCreditLimit = decisionApplication.debtBurdenCreditLimit;
		var debtRecommend = decisionApplication.debtRecommend;
		var debtBurden = decisionApplication.debtBurden;

		$('#DEBT_REC_VALUE').text(debtBurdenCreditLimit);
		$('#DEBT_BURDEN_VALUE').text(debtBurden);
		$('#DEBT_AMOUNT_VALUE').text(debtAmount);
	
		Pace.unblockFlag = true;Pace.unblock();
	}catch(exception){
		errorException(exception);
	}
}

function SummaryPageActionAfterCallDecision(data,formId,action,handleForm,validateForm){
	var decisionApplication = $.parseJSON(data);
	if(decisionApplication != undefined){
		var resultCode = decisionApplication.resultCode;
		var savingPlusFlag = decisionApplication.savingPlusFlag;
		action='SUBMIT';
		if(resultCode == SUCCESS_CODE || resultCode == NO_ACTION){
			if(savingPlusFlag == 'A')
			{
				action='SEND_TO_FU_PEGA';
				
				var msg = null;
				try
				{
					msg = eval('MSG_CONFIRM_SEND_PEGA_SAVING_PLUS');
				}
				catch(e)
				{ msg = 'MSG_CONFIRM_SEND_PEGA_SAVING_PLUS';}
				
				/*if(!alert(msg))
				{
					saveApplicationFormAction(formId,action,handleForm,validateForm);
			    }*/
				
				var dlgprop = {
						nl2br: false,
						title: 'Alert',
						closable: false,
						draggable: true,
						message: '<div class="row formDialog"><div class="col-xs-12">'+msg+'</div></div>', // Body
						buttons: [
						   {
							   label: 'OK',
							   cssClass: 'btn-primary',
							   action: function(dialog) {
								    dialog.close();
								    saveApplicationFormAction(formId,action,handleForm,validateForm);
							   }
						   }
						]
					};
					Pace.unblockFlag = true;
					Pace.unblock();
					openAlertDialog(dlgprop,'auto',DEFAULT_ALERT_BOX_WIDTH, height);
			}
			else
			{saveApplicationFormAction(formId,action,handleForm,validateForm);}
		}
	}else{
		saveApplicationFormAction(formId,action,handleForm,validateForm);
	}
}

function DECISION_DV2AfterDecisionActionJS(data,formId,action,handleForm,validateForm){
	try{
		var formId = $("#FormHandlerManager [name='formId']").val();
		var decisionApplication = $.parseJSON(data);
		var action = BUTTON_ACTION_SUBMIT;
		var diffRequestFlag = decisionApplication.diffRequestFlag;
		var docCompleteFlag = decisionApplication.docCompleteFlag;
		var rejectFlag = decisionApplication.rejectFlag;
		var savingPlusFlag = decisionApplication.savingPlusFlag;
		var lowIncomeFlag = decisionApplication.lowIncomeFlag;
		//alert('docCompleteFlag = ' + docCompleteFlag + ' savingPlusFlag = ' + savingPlusFlag+' lowIncomeFlag= '+lowIncomeFlag);
		if(savingPlusFlag == 'A')
		{
			action='SEND_TO_FU_PEGA';
		}
		if(FLAG_NO==docCompleteFlag && DECISION_SERVICE_REJECTED!=rejectFlag){
			alertBox('ERROR_DOCUMENT_COMPLETE_FLAG');
		}else{
			if(diffRequestFlag == FLAG_YES)
			{
				//stay at same page and enable VERIFY_CUSTOMER_BTN
				alertBox('ERROR_KPL_REQUEST_AMT_DIFF',LoadRefreshFormAction,"",DEFAULT_ALERT_BOX_WIDTH);
			}else if(lowIncomeFlag == FLAG_YES)
			{
				//stay at same page and enable VERIFY_CUSTOMER_BTN
				console.log("lowIncomeFlage = Y");
				LoadRefreshFormAction("");
			}else{
				saveApplicationFormAction(formId,action,handleForm,validateForm);	
			}
		}
	}catch(exception){
		errorException(exception);
	}
}
function DECISION_DE2AfterDecisionActionJS(data,formId,action,handleForm,validateForm){
	try{
		saveApplicationFormAction(formId,action,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}

function DECISION_IAAfterDecisionActionJS(data,formId,action,handleForm,validateForm){
	try{
		var decisionApplication = $.parseJSON(data); 
		var docCompleteFlag = decisionApplication.docCompleteFlag;
		if(docCompleteFlag == FLAG_YES){
			saveApplicationFormAction(formId,action,handleForm,validateForm);
		}else{
			alertBox('ERROR_DOC_COMPLETE');
		}
	}catch(exception){
		errorException(exception);
	}
}

function DecisionServiceActionAfterBusinessExceptionDecisionActionJS(){
	var subformId = 'DOCUMENT_CHECK_LIST';
	refreshSubForm(subformId);
}