var SUB_FORM_ID_COMMENT = "COMMENT_POPUP";
var SUB_FORM_ID = SUB_FORM_ID_COMMENT;
$(document).ready(function() {
	try{
		$('#SECTION_COMMENT_POPUP').find('.titlecontent').remove();
	}catch(exception){
		errorException(exception);
	}
});
function NOTE_PAD_DESCInitJS() {
	try{
		$('[name=NOTE_PAD_DESC]').focus();
	}catch(exception){
		errorException(exception);
	}
}
function COMMENT_POPUPInitJS() {
	try{
		NOTE_PAD_DESCInitJS();
	}catch(exception){
		errorException(exception);
	}
}
function ADD_COMMENTActionJS() {
	try{
		var NOTE_PAD_DESC = encodeURI($('[name=NOTE_PAD_DESC]').val().replace(/\n\r?/g, '<BR/>'));
		if(!isEmpty(NOTE_PAD_DESC)){
			var className = 'com.eaf.orig.ulo.app.view.form.popup.comment.manual.AddCommentPopup';
			var $data = '&NOTE_PAD_DESC=' + NOTE_PAD_DESC + "&subFormId=" + SUB_FORM_ID_COMMENT;
			createRow(className, $data, refreshSubForm, SUB_FORM_ID_COMMENT, "Y");
		}else{
			alertBox('WARNING_COMMENT', refreshSubForm,SUB_FORM_ID_COMMENT);
		}
	}catch(exception){
		errorException(exception);
	}	
}
function NOTE_PAD_DESCActionJS() {
}
function DELETE_COMMENTActionJS() {
	try{
		var seqVal = getValueCheckBox();
		if ("" != seqVal) {
			var $data = '&SEQ=' + seqVal + "&subFormId=" + SUB_FORM_ID_COMMENT;
			var className = 'com.eaf.orig.ulo.app.view.form.popup.comment.manual.DelCommentPopup';
			deleteRow(className, $data, refreshSubForm, SUB_FORM_ID_COMMENT, 'Y');
		}
	}catch(exception){
		errorException(exception);
	}
}

function getValueCheckBox() {
	var valList = "";
	try{
		$('[name="COMMENT_CH"]:checked').each(function(i) {
			valList += "," + $(this).val();
		});
	}catch(exception){
		errorException(exception);
	}
	return valList;
}