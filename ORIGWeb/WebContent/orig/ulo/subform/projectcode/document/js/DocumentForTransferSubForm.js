function ADD_BTNActionJS(){
	try{
		var documentType=$('input:radio[name=DOCUMENT_TYPE]').filter(":checked").val();
		var rowLenght = $('button[name="DELETE_DOCUMENT_TRANS_BTN"]').length;
		if(documentType!=PRVLG_DOC_TYPE_FOR_TRANSFER){
			alertBox(PLEASE_SELECT_FILE_TRANSFER);
		}else{
			if(rowLenght==PRVLG_LIMIT_DOC_TRANS_RECORD){
				alertBox(ERROR_MAXIMUM_TRANSFER_RECORDS);
			}else{
				var $data ='&PRVLG_PROJECT_DOC_INDEX=0';	
				 $data +='&DOCUMENT_TYPE='+documentType;
				createRow('com.eaf.orig.ulo.app.view.util.ajax.AddDocumentForTransfer',$data,refreshSubForm,'VERIFY_PRIVILEGE_DOC_PROJECT_CODE_SUBFORM','Y');		
			}
		}
	}catch(exception){
		errorException(exception);
	}
}

function DELETE_DOCUMENT_TRANS_BTNActionJS(seq){ 
	try{
		var $data ='&PRVLG_PROJECT_DOC_INDEX=0';
		$data += '&SEQ='+seq;
		deleteRow('com.eaf.orig.ulo.app.view.util.ajax.DeleteDocumentForTransfer',$data,refreshSubForm,'VERIFY_PRIVILEGE_DOC_PROJECT_CODE_SUBFORM','Y');
	}catch(exception){
		errorException(exception);
	}
}