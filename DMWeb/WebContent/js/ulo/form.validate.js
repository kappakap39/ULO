function isNumber(value) {
	var reg = /(^-?\d\d*\.\d*$)|(^-?\d\d*$)|(^-?\.\d\d*$)/;
	return reg.test(value);
}
function isCurrency(value){
	var reg = /(^\d\d*\.\d*$)|(^\d\d*$)|(^\.\d\d*$)/;
	return reg.test(value);
}
function isInteger(value){
	var reg = /(^-?\d\d*$)/;
	return reg.test(value);
}
function isEmpty(str) {
    return (!str || 0 === str.length || str == '');
}
function validateNationalId(dgt){
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
}
function validateBetweenDate(dateFrom,dateTo){
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

