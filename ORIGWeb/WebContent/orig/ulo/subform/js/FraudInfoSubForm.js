function FRAUD_INFO_SUBFORMInitSubFormJS(){
	try{
		/*
		$("input[name=FRAUD_APP_FLAG]").attr("disabled",true);
		$("input[name=FRAUD_COMPANY_FLAG]").attr("disabled",true);
		*/
	}catch(exception){
		errorException(exception);
	}
}
function FINAL_DECISIONInitJS(){
	try{
		var FINAL_DECISION = $("input[name=FINAL_DECISION]:checked").val();
		console.log("FRAUD_FLAG : "+FRAUD_FLAG);
		console.log("FINAL_DECISION : "+FINAL_DECISION);
		/*if(FRAUD_FLAG==FINAL_DECISION){
			$("input[name=FRAUD_APP_FLAG]").prop("disabled",false);
			$("input[name=FRAUD_COMPANY_FLAG]").prop("disabled",false);
			$("input[name=FRAUD_COMPANY_FLAG]").prop("checked",false);
			$("input[name=FRAUD_APP_FLAG]").prop("checked",true);
		}else{
			$("input[name=FRAUD_APP_FLAG]").prop("checked",false);
			$("input[name=FRAUD_COMPANY_FLAG]").prop("checked",false);
			$("input[name=FRAUD_APP_FLAG]").prop("disabled",true);
			$("input[name=FRAUD_COMPANY_FLAG]").prop("disabled",true);
		}*/
	}catch(exception){
		errorException(exception);
	}
}
function FINAL_DECISIONActionJS(){
	try{
		var FINAL_DECISION = $("input[name=FINAL_DECISION]:checked").val();
		console.log("FRAUD_FLAG : "+FRAUD_FLAG);
		console.log("FINAL_DECISION : "+FINAL_DECISION);
		
		if(FRAUD_FLAG==FINAL_DECISION){
			$("input[name=FRAUD_APP_FLAG]").prop("disabled",false);
			$("input[name=FRAUD_COMPANY_FLAG]").prop("disabled",false);
			$("input[name=FRAUD_COMPANY_FLAG]").prop("checked",false);
			$("input[name=FRAUD_APP_FLAG]").prop("checked",true);
		}else{
			$("input[name=FRAUD_APP_FLAG]").prop("checked",false);
			$("input[name=FRAUD_COMPANY_FLAG]").prop("checked",false);
			$("input[name=FRAUD_APP_FLAG]").prop("disabled",true);
			$("input[name=FRAUD_COMPANY_FLAG]").prop("disabled",true);
		}
		
	}catch(exception){
		errorException(exception);
	}
}