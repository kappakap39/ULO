function POPUP_PAYROLL_FORMAfterSaveActionJS() {
	try {
		callDecisionService();
	} catch (exception) {
		errorException(exception);
	}
}

function callDecisionService() {
	try {
		var functionId = DECISION_IMPLEMENT_ACTION_PAYROLL;
		var formId = getPopupFormId();
		applicationActionService(functionId,
				POPUP_BUNDLE_SME_DECISION_SERVICEAfeterActionJS, formId);
	} catch (exception) {
		errorException(exception);
	}
}

function POPUP_BUNDLE_SME_DECISION_SERVICEAfeterActionJS(data) {
	try {
		var decisionApplication = $.parseJSON(data);
		if (decisionApplication != undefined) {
			// may do something
			closeIncomeCategoryPopup();
		} else {
			closeIncomeCategoryPopup();
		}
	} catch (exception) {
		errorException(exception);
	}
}