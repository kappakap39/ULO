function isNumber(value) {
	try{
		var reg = /(^-?\d\d*\.\d*$)|(^-?\d\d*$)|(^-?\.\d\d*$)/;
		return reg.test(value);
	}catch(exception){
		errorException(exception);
	}
	return false;
}
function isCurrency(value){
	try{
		var reg = /(^\d\d*\.\d*$)|(^\d\d*$)|(^\.\d\d*$)/;
		return reg.test(value);
	}catch(exception){
		errorException(exception);
	}
	return false;
}
function isInteger(value){
	try{
		var reg = /(^-?\d\d*$)/;
		return reg.test(value);
	}catch(exception){
		errorException(exception);
	}
	return false;
}
function isEmpty(str) {
    return (!str || 0 === str.length || str == '');
}
function validateNationalId(dgt){
	try{
	    var sum = 0;
	    var sum1 = 0;
	    var count = 13;
	    for (var i = 0; i < 12; i++){
	        sum = sum + (parseInt(dgt.charAt(i) + "") * count);
	        count--;
	    }
	    sum1 = Math.floor((sum / 11)) * 11;    
	    if (((11 - (sum - sum1)) % 10) == parseInt(dgt.charAt(12) + "")){
	        return true;
	    }else{
	        return false;
	    }
	}catch(exception){
		errorException(exception);
	}
	return false;
}

function validateCurrentDateToDate(date,local){
	if(local == undefined){
		local = LOCAL_TH;
	}
	var dateFrom = date;
	var dateTo = TH_CURRENT_DATE;
	if(local == LOCAL_EN){
		dateTo = EN_CURRENT_DATE;
	}
	return validateBetweenDateToDate(dateFrom, dateTo);
}

function validateBetweenDateToDate(dateFrom,dateTo){
	try{
		if(!isEmpty(dateFrom) && !isEmpty(dateTo)){
			var arrayForm = dateFrom.split('/');
			var arrayTo =  dateTo.split('/');		
			var _dateFrom = parseInt(arrayForm[2]+arrayForm[1]+arrayForm[0]); 
			var _dateTo = parseInt(arrayTo[2]+arrayTo[1]+arrayTo[0]); 
			if (_dateFrom >= _dateTo ){
				return false;
			}else{
				return true;
			}
		}
	}catch(exception){
		errorException(exception);
	}
	return true;
}

function validateBetweenDate(dateFrom,dateTo){
	try{
		if(!isEmpty(dateFrom) && !isEmpty(dateTo)){
			var arrayForm = dateFrom.split('/');
			var arrayTo =  dateTo.split('/');		
			var _dateFrom = parseInt(arrayForm[2]+arrayForm[1]+arrayForm[0]); 
			var _dateTo = parseInt(arrayTo[2]+arrayTo[1]+arrayTo[0]); 
			if (_dateFrom > _dateTo ){
				return false;
			}else{
				return true;
			}
		}
	}catch(exception){
		errorException(exception);
	}
	return true;
}
function validateCurrentDate(date,local){
	if(local == undefined){
		local = LOCAL_TH;
	}
	var dateFrom = date;
	var dateTo = TH_CURRENT_DATE;
	if(local == LOCAL_EN){
		dateTo = EN_CURRENT_DATE;
	}
	return validateBetweenDate(dateFrom, dateTo);
}

function accountValidation(element, mode, name, data) {
	try{	
		if(mode == MODE_VIEW){
			Pace.unblockFlag = true;
			Pace.unblock();
			return;
		}
		Pace.block();
		var accountNumber = $.trim($("[name='"+element+"']").val());
		if(accountNumber){	
			var $data  = '&className=com.eaf.orig.ulo.app.view.util.ajax.ValidateAccount';
			 	$data += '&elementID='+element+'&name='+name+'&acct_no='+accountNumber;
			if(data != null && data != "" && data != undefined) {
				$data = $data + data;
			}
			if(name == "CURRENT_ACC_NO") {
				var savingAcctFieldName = element.replace(name,"SAVING_ACC_NO");
				var elementValue = $.trim($("[name='"+savingAcctFieldName+"']").val());
				$data = $data+'&additionalAcct='+elementValue;
			} else if(name == "SAVING_ACC_NO") {
				var currentAcctFieldName = element.replace(name,"CURRENT_ACC_NO");
				var elementValue = $.trim($("[name='"+currentAcctFieldName+"']").val());
				$data = $data+'&additionalAcct='+elementValue;
			}
//			ajax('com.eaf.orig.ulo.app.view.util.ajax.ValidateAccount',$data, acctValidationResult, element, name);
			
		var url = CONTEXT_PATH+'/Ajax';
		$.post(url,$data,function(data,status,xhr){
			try{
				Pace.unblockFlag = true;
				Pace.unblock();
				acctValidationResult(data, status, xhr, element, name);
			}catch(exception){
				errorException(exception);
			}
		}).fail(function(data,status,xhr){
			errorAjax(data,status,xhr);
		});
 
		
		}else{
			var returnElement = "ACCOUNT_NAME";
			var returnError = "COMPLETE_DATA";
			if(name == "CURRENT_ACC_NO") {
				returnElement = 'CURRENT_ACC_NAME';
				returnError = 'COMPLETE_DATA_CURRENT';
			} else if(name == "SAVING_ACC_NO") {
				returnElement = 'SAVING_ACC_NAME';
				returnError = 'COMPLETE_DATA_SAVING';
			}
			var elementName = returnElement+element.replace(name,'');
			var errElementName = returnError+element.replace(name,'');
			$("#"+elementName).text('');
			$("[name='"+errElementName+"']").val('');
			Pace.unblockFlag = true;
			Pace.unblock();
		}
	}catch(exception){
		errorException(exception);
	}
}

function acctValidationResult(data, status, xhr, element, name) {
	try{
		if(data != null && data != "" && data != undefined) {
			var obj = $.parseJSON(data);
			var obj = $.parseJSON(obj.data);
			var error = obj.errorCode;
			var dataVal = obj.elementValue;
			var returnElement = obj.elementId;
			var returnError = obj.elementParam;
			var messageType = obj.messageType;
			//save error code to complete data
			var errElementName = "COMPLETE_DATA";
			if(name == "CURRENT_ACC_NO") {
				errElementName = 'COMPLETE_DATA_CURRENT';
			} else if(name == "SAVING_ACC_NO") {
				errElementName = 'COMPLETE_DATA_SAVING';
			}
			errElementName = errElementName+element.replace(name,'');
			$("[name='"+errElementName+"']").val(returnError);
			
			//show error
			if(error != null && error != "" && error != undefined) {
				if(messageType != null && messageType != "" && messageType != undefined && messageType == SYSTEM_EXCEPTION){
					errorForm(error);
				}else{
					alertBox(error,'',element,DEFAULT_ALERT_BOX_WIDTH);
				}
				
			}else if(messageType == SUCCESS_CODE){
				//save Account name
				if(dataVal == undefined) {
					dataVal = "";
				}
				if(returnElement == null || returnElement == "" || returnElement == undefined) {
					returnElement = "ACCOUNT_NAME";
				}
				var elementName = returnElement+element.replace(name,'');
				$("#"+elementName).text(dataVal);
				
				rewriteForCashTransfer(element, dataVal);
				rewriteForSavingCurrent(element, name, dataVal, returnError);	
			}
		}
		Pace.unblockFlag = true;
		Pace.unblock();
	}catch(exception){
		errorException(exception);
	}
}
function currentAccountValidation(accountNo){
	
	try{
		console.log("accountNo"+accountNo);
		var className = "com.eaf.orig.ulo.app.view.util.ajax.GetAccountName";
		var data = {
			ACCOUNT_NO:accountNo
		};
	 ajax(className, data, getAccountNameAfterActionJS);
	
	}catch(exception){
		errorException(exception);
	}
   
}

function getAccountNameAfterActionJS(data){
	// check many 20 year
	displayJSON(data);
	var FORM_ID = $("[name='formId']").val();
	var valueProp = 0 ;
	var $JSON = $.parseJSON(data);
	var propName = 'accountName';
	if($.isEmptyObject($JSON)){
		return;
	}
	$.map($JSON, function(item){
		var elementId = item.id;
		var elementValue = item.value;
		console.log("elementId:"+elementId);
		console.log("elementValue:"+elementValue);
		if(propName == elementId){
			if(elementValue != null && elementValue != "" && elementValue != undefined) {
				$("[name='HIDDEN_ACCOUNT_NAME").val(elementValue);
			}
		}
	});
	
}

function rewriteForSavingCurrent(element, name, data, error) {
	try{
		if(name == "CURRENT_ACC_NO" || name == "SAVING_ACC_NO") {
			var errElementName = 'COMPLETE_DATA_SAVING';
			if(name == "CURRENT_ACC_NO") {
				errElementName = 'COMPLETE_DATA_SAVING';
			} else if(name == "SAVING_ACC_NO") {
				errElementName = 'COMPLETE_DATA_CURRENT';
			}
			errElementName = errElementName+element.replace(name,'');
			var origVal = $("[name='"+errElementName+"']").val();
			if(error == ERR_CODE_ACCT_SAME_BRANCH) {
				if(origVal == '' || origVal == INFO_IS_CORRECT || origVal == null || origVal == undefined) {				
					$("[name='"+errElementName+"']").val(error);
				}
			} else if(error == null ||  error == "" && error == undefined) {
				if(origVal == ERR_CODE_ACCT_SAME_BRANCH) {
					$("[name='"+errElementName+"']").val('');
				}
			}
		}
	}catch(exception){
		errorException(exception);
	}
}
function rewriteForCashTransfer(element, data) {
	try{
		if(element.indexOf(ACCT_TYPE_CASH_TRANSFER) > -1){
			var CASH_TRANSFER_TYPE = $("[name='CASH_TRANSFER_TYPE_"+PRODUCT_K_EXPRESS_CASH+"_CASH_TRANSFER']").val();
			if(CASH_DAY_1 == CASH_TRANSFER_TYPE || CASH_1_HOUR == CASH_TRANSFER_TYPE ) {
				var accountNumber = $.trim($("[name='"+element+"']").val());
				var callForCashObject = $("[name='ACCOUNT_NO_"+PRODUCT_K_EXPRESS_CASH+"_"+CALL_FOR_CASH+"']");
				if(callForCashObject.val() == null || callForCashObject.val() == '') {
					callForCashObject.val(accountNumber);
					callForCashObject.focus();
				}
			}
		}
	}catch(exception){
		errorException(exception);
	}
}