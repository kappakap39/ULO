function ADD_BTN_INCOMEInitJS() {
	// createPopupDialog('POPUP_PAYSLIP_FORM');
	// createPopupDialog('POPUP_YEARLY_TAWI_FORM');
	// createPopupDialog('POPUP_MONTHLY_TAWI_FORM');
	// createPopupDialog('POPUP_SALARY_CERT_FORM');
	// createPopupDialog('POPUP_BANK_STATEMENT_FORM');
	// createPopupDialog('POPUP_TAWEESAP_FORM');
	// createPopupDialog('POPUP_PAYROLL_FORM');
	// createPopupDialog('POPUP_FIXED_GUARANTEE_FORM');
	// createPopupDialog('POPUP_OTH_FIXED_ACCT_FORM');
	// createPopupDialog('POPUP_OTH_SAVING_ACCT_FORM');
	// createPopupDialog('POPUP_OTH_CLOSED_FUND_FORM');
	// createPopupDialog('POPUP_OTH_OPEN_FUND_FORM');
	// createPopupDialog('POPUP_FIN_INSTRUMENT_FORM');
	// createPopupDialog('POPUP_PREVIOUS_INCOME_FORM');
	// /*createPopupDialog('POPUP_KVI_FORM');*/
	// createPopupDialog('POPUP_BUNDLE_HL_FORM');
	// createPopupDialog('POPUP_BUNDLE_KL_FORM');
	// createPopupDialog('POPUP_BUNDLE_SME_FORM_KEC');
	// createPopupDialog('POPUP_BUNDLE_SME_FORM_CC');
}
function REVENUE_CATEGORYActionJS() {

}
function loadWindowForForm(formID) {
	try {
		var rowID = $("[name='INCOME_SEQ']").val();
		var income_type = $("[name='INCOME_TYPE']").val();
		var $data = '&incomeID=' + rowID + '&incomeType=' + income_type;
		if ("POPUP_KVI_FORM" == formID) {
			Pace.block();
			var className = 'com.eaf.orig.ulo.app.view.util.kvi.VerifyIncomeKVI';
			ajax(className, $data, KVIAfterActionJS);
		} else {
			if (INC_TYPE_BUNDLE_SME == income_type) {
				$("[name='INCOME_SEQ']").val('0000');
				wrapFormIdForBundleSME(formID);
			} else {
				loadPopupDialog(formID, 'INSERT', rowID, $data);
			}
		}
	} catch (exception) {
		errorException(exception);
	}
}
function KVIAfterActionJS(data) {
	try {
		var url = data;
		var style_suffix='defualt';
		var width = parseInt($('.smart-container').width());
		if(0!=width){
			style_suffix = 'KVI_VERIFY_INCOME_SECTION';
		}
		if (url != undefined) {
			var $data = ""; 
			var options = {
				    url: url,
				    title:KVI_VERIFY_INCOME_HEADER,
				    loading:false
				};
			loadIframeManualDialog(options,style_suffix);	
			Pace.unblock();
		} else {
			Pace.unblockFlag = true;
			Pace.unblock();
		}
	} catch (exception) {
		errorException(exception);
	}
}

 
 
function KVIOnMessageActionJS(KVIObjectData) {
	try {
		var verifiedIncome = KVIObjectData.verifiedIncome;
		var percentChequeReturn = KVIObjectData.percentChequeReturn;
		var $data = "&VERIFIED_INCOME=" + verifiedIncome + "&PERCENT_CHEQUE="
				+ percentChequeReturn + "&action=" + BUTTON_ACTION_SUBMIT;
		var className = 'com.eaf.orig.ulo.app.view.util.kvi.VerifyIncomeKVI';
		ajax(className, $data, KVICallbackAfterActionJS);
	} catch (exception) {
		errorException(exception);
	}
}

// var KVICallback = {callbackVerify :function(inObj){
// var income = inObj.verifiedIncome;
// var percent = inObj.percentChequeReturn;
// var $data =
// "&VERIFIED_INCOME="+income+"&PERCENT_CHEQUE="+percent+"&action="+BUTTON_ACTION_SUBMIT;
// var className = 'com.eaf.orig.ulo.app.view.util.kvi.VerifyIncomeKVI';
// ajax(className,$data,KVICallbackAfterActionJS);
// }
// };
// KVICallback.prototype.callbackVerify(inObj);

function KVICallbackAfterActionJS() {
	try {
		$('.modal-header').find('.close').click();
		refreshCurrentForm();
	} catch (exception) {
		errorException(exception);
	}
}

function ADD_BTN_INCOMEActionJS() {
	try {
		var income_type = $("[name='REVENUE_CATEGORY']").val();
		if (income_type == null || income_type == ""
				|| income_type == undefined) {
			alertBox('ERROR_PLEASE_SELECT_REVENUE_TYPE');
		} else {
			$("[name='INCOME_SEQ']").val('0');
			$("[name='INCOME_TYPE']").val(income_type);
			var $data = 'choiceNo=' + income_type + '&fieldID='
					+ FIELD_ID_REVENUE_CATEGORY;
			ajax('com.eaf.orig.ulo.app.view.util.ajax.GetSystemID1ByFieldID',
					$data, loadWindowForForm);
		}
	} catch (exception) {
		errorException(exception);
	}
}

function loadIncomePopup(obj, incomeSeq, incomeType) {
	try {
		/*
		 * var par = $(this).closest('TR'); var seqStr = par.attr('id'); var
		 * seqSplits = seqStr.split('_');
		 * $("[name='INCOME_SEQ']").val(seqSplits[2]);
		 * $("[name='INCOME_TYPE']").val(seqSplits[1]);
		 */
		var currentActionJs = $(obj).closest('TR').attr('onclick');
		$(obj).closest('TR').attr('onclick', '');
		setTimeout(function() {
			$(obj).closest('TR').attr('onclick', currentActionJs);
		}, 300);
		$("[name='INCOME_SEQ']").val(incomeSeq);
		$("[name='INCOME_TYPE']").val(incomeType);
		var $data = 'choiceNo=' + incomeType + '&fieldID='
				+ FIELD_ID_REVENUE_CATEGORY;
		ajax('com.eaf.orig.ulo.app.view.util.ajax.GetSystemID1ByFieldID',
				$data, loadWindowForForm);
	} catch (exception) {
		errorException(exception);
	}
}
function loadBundlePopup(obj, incomeSeq, incomeType) {
	try {
		var currentActionJs = $(obj).closest('TR').attr('onclick');
		$(obj).closest('TR').attr('onclick', '');
		setTimeout(function() {
			$(obj).closest('TR').attr('onclick', currentActionJs);
		}, 300);
		$("[name='INCOME_SEQ']").val(incomeSeq);
		$("[name='INCOME_TYPE']").val(incomeType);
		var $data = 'choiceNo=' + incomeType + '&fieldID='
				+ FIELD_ID_REVENUE_CATEGORY;
		ajax('com.eaf.orig.ulo.app.view.util.ajax.GetSystemID1ByFieldID',
				$data, wrapFormIdForBundleSME);
	} catch (exception) {
		errorException(exception);
	}
}
function wrapFormIdForBundleSME(formID) {
	try {
		var rowID = $("[name='INCOME_SEQ']").val();
		var income_type = $("[name='INCOME_TYPE']").val();
		var $data = '&appRecordID=' + rowID + '&type=' + income_type
				+ '&formName=' + formID;
		ajax('com.eaf.orig.ulo.app.view.util.ajax.WrapFormIdForBundleSME',
				$data, loadBundleForm);
	} catch (exception) {
		errorException(exception);
	}
}
function loadBundleForm(formID) {
	try {
		var rowID = $("[name='INCOME_SEQ']").val();
		var income_type = $("[name='INCOME_TYPE']").val();
		var $data = '&incomeID=' + rowID + '&incomeType=' + income_type;
		loadPopupDialog(formID, 'INSERT', rowID, $data);
	} catch (exception) {
		errorException(exception);
	}
}
function deleteIncomeType() {
	try {
		var par = $(this).closest('TR');
		var seqStr = par.attr('id');
		var seqSplits = seqStr.split('_');
		var $data = '&incomeType=' + seqSplits[1] + '&seq=' + seqSplits[2];
		deleteRow('com.eaf.orig.ulo.app.view.util.ajax.DeleteIncomeType',
				$data, refreshSubForm);
	} catch (exception) {
		errorException(exception);
	}
};
function closeIncomeCategoryPopup() {
	try {
		refreshSubForm("VERIFY_INCOME_SUBFORM");
		// #rawi change to used function closePopupDialog to close popup
		// eModal.close();
		var formId = getPopupFormId();
		closePopupDialog(formId, POPUP_ACTION_CLOSE);
	} catch (exception) {
		errorException(exception);
	}
}
function addIncomeCategoryPopup() {
	try {
		var formId = getPopupFormId();
		var $data = '';
		formHandleAction(formId, $data, cretaeRowIncomeCategory);
	} catch (exception) {
		errorException(exception);
	}
}
function cretaeRowIncomeCategory() {
	try {
		createRow('com.eaf.orig.ulo.app.view.util.ajax.AddIncomeCategoryList',
				'', refreshMasterPopupForm, '', 'N');
	} catch (exception) {
		errorException(exception);
	}
}
function deleteIncomeCategoryPopup() {
	try {
		var par = $(this).closest('TR');
		var $data = 'seq=' + par.attr('id');
		var formId = getPopupFormId();
		formHandleAction(formId, $data, deleteRowIncomeCategory);
	} catch (exception) {
		errorException(exception);
	}
}
function deleteRowIncomeCategory($data, formId, str) {
	try {
		deleteRow(
				'com.eaf.orig.ulo.app.view.util.ajax.DeleteIncomeCategoryList',
				str, refreshMasterPopupForm, '', 'N');
	} catch (exception) {
		errorException(exception);
	}
}

function delete50TawiIncomePopup() {
	try {
		var par = $(this).closest('TR');
		var $data = 'seq=' + par.attr('id');
		var formId = getPopupFormId();
		formHandleAction(formId, $data, deleteTheRow);
	} catch (exception) {
		errorException(exception);
	}
}
function deleteTheRow($data, formId, str) {
	try {
		deleteRow(
				'com.eaf.orig.ulo.app.view.util.ajax.DeleteIncomeCategoryList',
				str, refreshMasterPopupForm, '', 'N');
	} catch (exception) {
		errorException(exception);
	}
};
$(document).ready(function() {
	$("#VerifyIncome").on("click", ".BtnRemove", deleteIncomeType);
	// $("#VerifyIncome").on("click", ".loadPopup", loadIncomePopup);
	// $("#VerifyIncome .loadPopup").dblclick(function(e){e.preventDefault();})
	// $("#VerifyIncome").on("click", ".loadBundle", loadBundlePopup);
});
