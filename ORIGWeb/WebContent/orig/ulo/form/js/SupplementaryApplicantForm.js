function SUPPLEMENTARY_APPLICANT_FORMAfterSaveActionJS(){
	try{
		var $data = "";
		var className = 'com.eaf.orig.ulo.app.view.util.ajax.SaveChageStatusPersonal';
		ajax(className,$data,SUPPLEMENTARY_APPLICANT_FORMAfterSetTypePersonal);
	}catch(exception){
		errorException(exception);
	}
}

function SUPPLEMENTARY_APPLICANT_FORMAfterSetTypePersonal(){
	var handleForm = 'N';
	var $data = '';
	backLastTabAction($data,handleForm);
}