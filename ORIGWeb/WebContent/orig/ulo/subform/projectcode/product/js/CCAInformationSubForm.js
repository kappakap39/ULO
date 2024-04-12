function RESULTInitJS(element,mode,name){
	try{
		if(MODE_EDIT==mode){
			if($('[name="CCA_PRODUCT"]').val()=="" ||$('[name="CCA_PRODUCT"]').val()==null){
				targetDisplayHtml('RESULT', MODE_VIEW,'RESULT');
			}else{
				targetDisplayHtml('RESULT', MODE_EDIT,'RESULT');
			}
		}
	}catch(exception){
		errorException(exception);
	}

}