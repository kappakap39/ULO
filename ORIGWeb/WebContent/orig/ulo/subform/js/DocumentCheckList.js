var SUB_FORM_ID_DOC_COMMENT = "DOCUMENT_CHECK_LIST";

function ADD_DOC_COMMENTActionJS() {
	try{
		var className = 'com.eaf.orig.ulo.app.view.form.popup.comment.manual.AddDocCommentPopup';
		var DOC_COMMENT = encodeURI($('[name=DOC_COMMENT]').val().replace(/\n\r?/g, '<BR/>'));
		var $data = '&DOC_COMMENT=' + DOC_COMMENT + "&subFormId=" + SUB_FORM_ID_DOC_COMMENT;
		createRow(className, $data, refreshSubForm, SUB_FORM_ID_DOC_COMMENT, "Y");
	}catch(exception){
		errorException(exception);
	}
}

function deleteComment(id) {
	try{
		if ("" != id || null != id) {
			var $data = '&docCommentId=' + id + "&subFormId=" + SUB_FORM_ID_DOC_COMMENT;
			var className = 'com.eaf.orig.ulo.app.view.form.popup.comment.manual.DelDocCommentPopup';
			deleteRow(className, $data, refreshSubForm, SUB_FORM_ID_COMMENT, 'Y');
		}
	}catch(exception){
		errorException(exception);
	}
}

function INTERNAL_INCOME_BTNActionJS() {
	try{
		loadPopupDialog("POPUP_INTERNAL_INCOME_FORM",'VIEW','0','');
	}catch(exception){
		errorException(exception);
	}
}
