$(document).ready(function(){
	try{
		$('#SECTION_DOCUMENT_CHECK_LIST_OTHERS').find('.titlecontent').remove();
	}catch(exception){
		errorException(exception);
	}
});

function ADD_CHECK_DOC_BTNActionJS(){
	try{
		if(""!=$('[name="DOCUMENT_CODE"]').val() && ""!=$('[name="APPLICANT_TYPE"]').val()){
			var $data  ='&DOCUMENT_CODE='+$('[name="DOCUMENT_CODE"]').val();
			$data+='&APPLICANT_TYPE='+$('[name="APPLICANT_TYPE"]').val();
			createRow('com.eaf.orig.ulo.app.view.util.manual.AddDocumentCheckListOther',$data,refreshSubForm);			
		}else{
			alertBox(PLEASE_SELECT_DOC_LIST_AND_PERSON);
		}
	}catch(exception){
		errorException(exception);
	}
}
function DELETE_CHECK_DOC_BTNActionJS(row_Seq){
	try{
		if(confirm(MSG_CONFIRM_DELETE)&& ""!=row_Seq){
			var $data  ='&ROW_SEQ='+row_Seq;
			deleteRow('com.eaf.orig.ulo.app.view.util.manual.DeleteDocumentCheckListOther',$data,refreshSubForm);
		}
	}catch(exception){
		errorException(exception);
	}
}