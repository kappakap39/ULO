function BTN_ADD_DOC_MAN_INCOMEActionJS(element, mode, name) {
	try {
		var manualFlag = "INCOME";
		$data = 'manualFlag=' + manualFlag;
		createRow('com.eaf.orig.ulo.app.view.util.ajax.AddDocumentCheckListManual', $data, refreshSubForm, 'DOCUMENT_CHECK_LIST_MANUAL_ADD', 'Y');
	} catch(exception) {
		errorException(exception);
	}
}

function BTN_ADD_DOC_MAN_INIT_INCOMEActionJS(element, mode, name) {
	try {
		var manualFlag = "INCOME";
		$data = 'manualFlag=' + manualFlag;
		createRow('com.eaf.orig.ulo.app.view.util.ajax.AddDocumentCheckListManual', $data, refreshSubForm, 'DOCUMENT_CHECK_LIST_MANUAL_ADD', 'N');
	} catch(exception) {
		errorException(exception);
	}
}

function BTN_DEL_DOC_MAN_INCOMEActionJS(element, mode, name) {
	try {
		var manualFlag = "INCOME";
		var docCheckListId = element.substring(element.lastIndexOf("_") + 1, element.length);
		$data = 'docCheckListId=' + docCheckListId + '&manualFlag=' + manualFlag;
		deleteRow('com.eaf.orig.ulo.app.view.util.ajax.DeleteDocumentCheckListManual', $data, refreshSubForm, 'DOCUMENT_CHECK_LIST_MANUAL_ADD', 'N');
	} catch(exception) {
		errorException(exception);
	}
}