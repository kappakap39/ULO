/**
 * Rawi Songchaisin
 */
(function($){
    $.fn.setCursorToTextEnd = function() {
        $initialVal = this.val();
        this.val($initialVal+' ');
        this.val($.trim($initialVal));
    };
})(jQuery);

function ValidateIdNo(field){
	var idNo 		 = document.getElementById(field);	
	var customerType = $('#customertype').val();
	if(CUST_FOREIGNER != customerType){
		if(idNo.value != ''){
			if(!ValidateNumberType(field)){
				alertMassageSelection(ERROR_ID_NO,field);
				return false;
			}else if(idNo.value.length != 13){
				alertMassageSelection(ERROR_ID_NO,field);
				return false;
			}else if(!ValidateIdNoDigit(idNo.value)){
				alertMassageSelection(ERROR_ID_NO,field);
				return false;
			}else{
				return true;
			}
		}else{
			return false;			
		}
	}	
	return true;
}
function ValidateIdNoDigit(dgt){
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
function ValidateNumberType(field){
	var number	= $("#"+field).val();
	var number1 =  RemoveCommaStr(number);
	if (isNaN(number1)){
		return false;
	}else{
		if(number1<0){
			return false;
		}
	}
	return true;
}
function RemoveCommaStr(strValue) {
	var end = false;
	while(!end){
		if(strValue.indexOf(",")<0){
			end =true;
		}else{
			strValue = strValue.replace(",","");
		}
	}
	return strValue;
}

function checkThaiFName(field,mandate){
	var str = eval("document.appFormName."+field);
	var reg1=/(^[A-Z,a-z,?-?,?-?,\-,\s,0-9,\.]*$)/;
	if(!reg1.test(str.value) && str.value != ''){
		alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_VERIFY_THAI_FIRST_NAME")%>');
		str.focus();
		str.select();
	}
}
function validateNumber(field){
	var number	= $(field).val();
	var number1 =  removeCommas(number);
	var fieldName = $(field).attr('id');
	if (isNaN(number1)){
		alertMassageSelection(ERROR_NUMBER, fieldName);
		return false;
	}else{
		if(number1<0){
			alertMassageSelection(ERROR_NUMBER,fieldName);
			return false;
		}
	}
	return true;
}

function keyPressInteger(){
	var keynum;
	var keychar;
	var numcheck;	
	if(window.event){
		keynum = event.keyCode;
	}else if(event.which){
		keynum = event.which;
	}
	keychar = String.fromCharCode(keynum);
	numcheck = /\d/;
	return numcheck.test(keychar);
}

function validateNumberValue(field){
	var value = $(field).val();
	numcheck = /\d/;
	if(!numcheck.test(value)){
		$(field).val("");
	}
	
}

function checkEmail(field) {
	var email = document.getElementById(field);
	if(email.value){
	var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (!filter.test(email.value)) {
		alertMassageSelection(ERROR_EMAIL,field);
		email.focus;
		return false;
		}
	}
}

function checkEmails(field) {
	var email = document.getElementById(field);
	if(email.value){
		var arrayEmail = email.value.split(',');
		var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		for (var i=0;i<arrayEmail.length;i++){
			var emailValue = arrayEmail[i];
			if (!filter.test(emailValue)) {
				alert(ERROR_EMAIL);
				email.select();
				return false;
			}
		}
	}
}

function getInternetExplorerVersion(){
	var rv = -1; // Return value assumes failure.
		if (navigator.appName == 'Microsoft Internet Explorer'){
		   var ua = navigator.userAgent;
		   var re  = new RegExp("MSIE ([0-9]{1,}[\.0-9]{0,})");
		   if (re.exec(ua) != null)
		      rv = parseFloat( RegExp.$1 );
		}
	return rv;
}
function keyPressMonth(field){
//	alert("in function");
	var value = $('#'+field).val();
	if((value>12 || value<1)&&value.length!=0){
		alertMassageSelection(CAR_INSURANCE, field);
	}
}
function keyPressYear(field){
	var value = $('#'+field).val();
	if((value>12 || value<1)&&value.length!=0){
		alertMassageFocus(YEAR_INPUT, field);
	}
}
function DestoryTextBoxTelEngine(entity){
	$(entity).unbind();
}
/**
 * This Function Used For Active Input Type Text To Textbox Tel Validate
 * #SeptemWi 
*/
function TextBoxTelEngine(entity){
	$(entity).focus(function(){
		$(this).select();
	});
	$(entity).keypress(function (e){
		var strChar = String.fromCharCode(e.keyCode);
		if(validateNumeric(strChar)){
			return true;
		}else{
			e.preventDefault();
			return false;
		}
	});	
}

function DestoryTextBoxNumberEngine(entity){
	$(entity).unbind();
}
/**
 * This Function Used For Active Input Type Text To TextBox Number
 * #SeptemWi
 * */
function TextBoxNumberEngine(entity){
	$(entity).focus(function (){
		$(this).select();
	});
	$(entity).blur(function (){
		var format = $(this).attr('format');
		if(format == '' || format == null) format = '########0'; /**Default Format ########0 #SeptemWi*/
		if(validateNumeric($(this).val())||$(this).val()==''){
			var formatArray = format.split('.');
			var nullempty = $(this).attr('nullempty');
			var minValue =  $(this).attr('minvalue');
			var maxValue =  $(this).attr('maxvalue');		
			var strValue = $(this).val();
			var newArray = strValue.split(".");	
			if(nullempty == 'N' && strValue == ''){
				if(formatArray[1] != undefined){
					$(this).val('0.'+formatArray[1]);
					return false;
				}
				$(this).val('0');
				return false;
			}else{
				if(strValue == ''){
					$(this).val('');
					return false;
				}				
			}
			if(newArray[0]== undefined || newArray[0] == ''){
				newArray[0] = '0';			
			}
			if(newArray[1] == undefined || newArray[1] == ''){
				newArray[1] = (formatArray[1] != undefined)?formatArray[1]:'';
			}
			if(newArray[0].substring(0,1) == "-"){
				var zeroVal = newArray[0].substring(1, newArray[0].length);
				if(Number(zeroVal) == 0){
					newArray[0] = '0';
				}
			}
			if(formatArray[1] != undefined && newArray[1].length < formatArray[1].length){
				newArray[1] = newArray[1]+formatArray[1].substr(newArray[1].length,formatArray[1].length-1);
			}
			if(formatArray[0] != undefined && newArray[0].length > formatArray[0].length){
				$(this).focus();
				return false;
			}
			if(formatArray[1] != undefined && newArray[1].length > formatArray[1].length){
				$(this).focus();
				return false;
			}
			if((minValue != '' && Number(strValue) < Number(minValue))||(maxValue != '' && Number(strValue) > Number(maxValue))){
				alertMassageSelection(NUMBER_ERROR_OVER_MAX+' '+maxValue,$(this).attr('id'));
				return false;
			}
			var displayVal = Number(newArray[0])+((newArray[1].length >0)?'.'+newArray[1]:'');
			$(this).val(displayVal);
			return true;
		}else{
			$(this).focus();
		}
	});
	$(entity).keypress(function (e){
		var strChar = String.fromCharCode(e.keyCode);	
		var strValue = $(this).val();
		var newArray = strValue.split(".");
		var format = $(this).attr('format');
		if(format == '' || format == null) format = '########0'; /**Default Format ########0 #SeptemWi*/
		var formatArray = format.split('.');
		if(validateNumeric(strChar)||(strChar=="." && newArray.length == 1 && formatArray[1] != undefined)){
			return true;
		}else{
			e.preventDefault();
			return false;
		}
	});	
	$(entity).keyup(function (e){
		var keyVal = String.fromCharCode((96 <= e.keyCode && e.keyCode <= 105)? e.keyCode-48 : e.keyCode);	
		var strValue = $(this).val();
		var newArray = strValue.split(".");
		var format = $(this).attr('format');
		if(format == '' || format == null) format = '########0'; /**Default Format ########0 #SeptemWi*/
		var formatArray = format.split('.');
		if(formatArray[1] == undefined){
			if(validateNumeric(keyVal)){
				var newArray1 = $(this).val().split(".");
				if(newArray1[0] != undefined && (newArray1[0].length > formatArray[0].length)){
					$(this).val((newArray1[0].substring(0,formatArray[0].length)));
					return true;
				}else{
					$(this).val(newArray1[0]);
					return true;
				}
			}
		}else{
			if(validateNumeric(keyVal)||(keyVal=="." && newArray.length == 1)){
				var newArray1 = $(this).val().split(".");
				if(newArray1[0] != undefined && (newArray1[0].length > formatArray[0].length)){
					if(keyVal != '.'){
						$(this).val((newArray1[0].substring(0,formatArray[0].length)));
						return true;
					}
				}
				var newArray2 = $(this).val().split(".");
				if(newArray2[1] != undefined && (newArray2[1].length > formatArray[1].length)){
					if(keyVal != '.'){
						$(this).val((newArray2[0])+'.'+(newArray2[1].substring(0,formatArray[1].length)));
						return true;
					}
				}
			}
		}
	});
}

function DestoryTextBoxCurrencyEngine(entity){
	$(entity).unbind();
}
/**
 * This Function Used For Active Input Type Text To TextBox Currentcy
 * #SeptemWi
 * */
function TextBoxCurrencyEngine(entity){
	$(entity).focus(function (){
		var strValue = $(this).val();
		var end = false;
		while(!end){
			if(strValue.indexOf(",")<0){
				end =true;
			}else{
				strValue = strValue.replace(",","");
			}
		}
		$(this).val(strValue);
		$(this).select();	
	});	
	$(entity).blur(function (){	
/**#SeptemWi Null Value Empty*/
		var nullempty = $(this).attr('nullempty');
		if($(this).val() == '' && nullempty == 'Y'){
			$(this).val('');
			return false;
		}		
		var format = $(this).attr('format');
		if(format == '' || format == null) format = '########0.##'; /**Default Format xxxxxxxx.xx #SeptemWi*/
		var formatArray = format.split('.');	
/**InputComma Field*/
		if(validateCurrency($(this).val()) || $(this).val() == ''){	
			var strValue = $(this).val();
			var newArray = strValue.split(".");
			if(newArray[0]== undefined || newArray[0] == '') newArray[0] = '0';
			
			if(formatArray[1]!= undefined){
				if(newArray[1] == undefined || newArray[1] == '') newArray[1] = '00';
				if(newArray[1].length == 1) newArray[1] = newArray[1]+'0';
			}
			
			if(newArray[0].length > formatArray[0].length){
				$(this).focus();
			}
			
			if(formatArray[1]!= undefined){
				if(newArray[1].length > formatArray[1].length){
					$(this).focus();
				}
			}
			var suffix = '';
			if(formatArray[1]!= undefined){
				suffix='.'+newArray[1];
			}			
			var number = Number(newArray[0])+'';
			number = '' + number;
			if (number.length > 3){
				var mod = number.length % 3;
				var output = (mod > 0 ? (number.substring(0,mod)) : '');
				for (var i=0; i < Math.floor(number.length / 3); i++) {
					if ((mod == 0) && (i == 0))
						output += number.substring(mod+ 3 * i, mod + 3 * i + 3);
					else
						output+= ',' + number.substring(mod + 3 * i, mod + 3 * i + 3);
				}
				$(this).val(output.concat(suffix));
			}else{
				$(this).val(number.concat(suffix));
			}
		}else{
			$(this).focus();
		}
	});
	$(entity).keypress(function (e){
		var strChar = String.fromCharCode(e.keyCode);
		var strValue = $(this).val();
		var newArray = strValue.split(".");
		if(validateCurrency(strChar) || (strChar=="." && newArray.length == 1)){
			return true;
		}else{
			e.preventDefault();
			return false;
		}
	});	
	$(entity).keyup(function (e){
		var keyVal = String.fromCharCode((96 <= e.keyCode && e.keyCode <= 105)? e.keyCode-48 : e.keyCode);
		var strValue = $(this).val();
		var newArray = strValue.split(".");
		if(validateCurrency(keyVal) || (keyVal=="." && newArray.length == 1)){			
			var format = $(this).attr('format');			
			if(format == '' || format == null) format = '########0.##'; /**Default Format xxxxxxxx.xx #SeptemWi*/
			var formatArray = format.split('.');
			var newArray1 = $(this).val().split(".");
			if(newArray1[0] != undefined && (newArray1[0].length > formatArray[0].length)){
				if(keyVal != '.'){
					$(this).val((newArray1[0].substring(0,formatArray[0].length)));
					return true;
				}
			}
			var newArray2 = $(this).val().split(".");
			if(newArray2[1] != undefined && (newArray2[1].length > formatArray[1].length)){
				if(keyVal != '.'){
					$(this).val((newArray2[0])+'.'+(newArray2[1].substring(0,formatArray[1].length)));
					return true;
				}
			}
		}
	});	
}


function keyPressThai(field) {
	var strKeyCode = String.fromCharCode(event.keyCode);
	var strValue = $(field).val();
	if (strValue == "" || strValue.length == 0) {
		var fistObjRegExp = /^[\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44]/;
		if (!fistObjRegExp.test(strKeyCode)) {
			window.event.returnValue = false;
		} else {
			window.event.returnValue = true;
		}

	} else {
		var objRegExp = /^([\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44])+([\u0E01-\u0E4F\u0E46\u002C\u002E\u002D\u005F\u002F\u0028\u0029\u0025\u002A\u0040\u0024\u0023\u0026\u002B\s])+$/;
	    //var objRegExp = /^([\u0030-\u0039\u0041-\u007A\u002E])+([\u0030-\u0039\u0041-\u007A\u002E\u002C\u002E\u002D\u005F\u002F\u0028\u0029\u0025\u002A\u0040\u0024\u0023\u0026\u002B\s])+$/; 
		// alert("Second "+strValue+strKeyCode+"
		// "+objRegExp.test(strValue+strKeyCode));
		if (!objRegExp.test(strValue + strKeyCode)) {
			window.event.returnValue = false;
		} else {
			window.event.returnValue = true;
		}
	}

}
function checkThai(field){/** Script for check Thai Char & Special Char*/
	/*** Thai Char, SpecialThaiChar & Special Symbol*/
	//var objRegExp  = /[\u0E01-\u0E4F\u0E46\u002C\u002E\u002D\u005F\u002F\u0028\u0029\u0025\u002A\u0040\u0024\u0023\u0026\u002B]/ ; 
	var objRegExp  = /^([\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44])+([\u0E01-\u0E4F\u0E46\u002C\u002E\u002D\u005F\u002F\u0028\u0029\u0025\u002A\u0040\u0024\u0023\u0026\u002B\s])+$/;
	var strValue = $(field).val();
	if(!objRegExp.test(strValue)){
		$(field).val("");
	}
}

function keyPressEng(field) {
    window.event.returnValue = true; 
	var strKeyCode = String.fromCharCode(event.keyCode);
	var fistObjRegExp=/(^[A-Z,a-z,?-?,?-?,\-,\s,0-9,\.]*$)/;  
	var strValue = $(field).val();
	if (strValue == "" || strValue.lenght) {
		//var fistObjRegExp = /^[\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44]/;
		// alert("First "+strKeyCode+" "+fistObjRegExp.test(strKeyCode));
		if (!fistObjRegExp.test(strKeyCode)) {
			window.event.returnValue = false;
		} else {
			window.event.returnValue = true;
		}

	} else {
		//var objRegExp = /^([\u0030-\u0039\u0041-\u007A\u002E])+([\u0030-\u0039\u0041-\u007A\u002E\u002C\u002E\u002D\u005F\u002F\u0028\u0029\u0025\u002A\u0040\u0024\u0023\u0026\u002B\s])+$/;
		// alert("Second "+strValue+strKeyCode+"
		// "+objRegExp.test(strValue+strKeyCode));
		if (!fistObjRegExp.test(strValue + strKeyCode)) {
			window.event.returnValue = false;
		} else {
			window.event.returnValue = true;
		}
	}
    
}

function checkEngWithInt(field){/** Script for check Eng & Integer Char & Special Char*/
//	var objRegExpEng  = /^[\u0041-\u007A\u002E\u0030\u0039]+$/;
//	var objRegExpInt  = /[0-9]/;
//	var objRegExpSpecChar = /[\u002C\u002E\u002D\u005F\u002F\u0028\u0029\u0025\u002A\u0040\u0024\u0023\u0026\u002B]/;
	var objRegExpEng  = /^([\u0030-\u0039\u0041-\u007A\u002E])+([\u0030-\u0039\u0041-\u007A\u002E\u002C\u002E\u002D\u005F\u002F\u0028\u0029\u0025\u002A\u0040\u0024\u0023\u0026\u002B\s])+$/;
	 
	var fieldID = $(field).attr('id');
	var strValue = $('#'+fieldID).val();
//	if(!objRegExpEng.test(strValue)&&!objRegExpInt.test(strValue)&&!objRegExpSpecChar.test(strValue)){
	if(!objRegExpEng.test(strValue)){
		$('#'+fieldID).val("");
	}
}
function addScript2Uppercase(form){
	var elements = form.elements;
	for(var i = 0; i < elements.length; i++){
		var element = elements[i];
		if(element.type == 'text'){
			var eventOnBlur = element.onblur;
			var newFunction = "";
			if(eventOnBlur != null){
				var eventOnBlurStr = eventOnBlur.toString();
				newFunction = "change2Uppercase('" + element.name + "'); " + eventOnBlurStr.substring(eventOnBlurStr.indexOf("{") + 1, eventOnBlurStr.lastIndexOf("}")); 
			}else{
				newFunction = "change2Uppercase('" + element.name + "'); ";
			}
			var func = new Function(newFunction);
			element.onblur = func;
		}
	}
}

function change2Uppercase(textFieldName){
	var textField = eval("window.document.appFormName." + textFieldName);
	if(!isUndefined(textField)){
		var values = textField.value;
		textField.value = values.toUpperCase();
	}
}

function keyPressCarMonth(field){
	var value = $('#'+field).val();
	if((value>12 || value<1)&&value.length!=0){
		alertMassageSelection(CAR_INSURANCE, field);
	}
}

function TextBoxThaiEngine(entityID){
	$(entityID).keypress(function(e) {
		var strKeyCode = String.fromCharCode(e.keyCode);
		var strValue = $(this).val();
		if (strValue == '' || strValue.length == 0){
			var fistObjRegExp = /^[\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44]/;
			if (!fistObjRegExp.test(strKeyCode)) {
				e.preventDefault();
				return false;
			}else{
				return true;
			}

		}else{
			var objRegExp = /^([\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44])+([\u0E01-\u0E4F\u0E46\u002C\u002E\u002D\u005F\u002F\u0028\u0029\u0025\u002A\u0040\u0024\u0023\u0026\u002B\s])+$/;
			if (!objRegExp.test(strValue + strKeyCode)) {
				e.preventDefault();
				return false;
			} else {
				return true;
			}
		}
	});
	$(entityID).blur(function (){
		var strValue = $(this).val();
		if (strValue == '' || strValue.length == 1){
			var fistObjRegExp = /^[\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44]/;
			if(!fistObjRegExp.test(strValue)){
				$(this).val("");
			}
		}else{
			var objRegExp  = /^([\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44])+([\u0E01-\u0E4F\u0E46\u002C\u002E\u002D\u005F\u002F\u0028\u0029\u0025\u002A\u0040\u0024\u0023\u0026\u002B\s])+$/;
			if(!objRegExp.test(strValue)){
				$(this).val("");
			}
		}
	});
}
function TextBoxEngEngine(entityID){
	$(entityID).keypress(function(e) {
		var strKeyCode = String.fromCharCode(e.keyCode);
		var fistObjRegExp=/(^[A-Z,a-z,?-?,?-?,\-,\s,0-9,\.]*$)/;
		var strValue = $(this).val();
		if (strValue == "" || strValue.length == 0){
			if (!fistObjRegExp.test(strKeyCode)) {
				e.preventDefault();
				return false;
			}else{
				return true;
			}
		}else{
			if (!fistObjRegExp.test(strKeyCode)) {
				e.preventDefault();
				return false;
			} else {
				return true;
			}
		}
	});
	$(entityID).blur(function (){
//		var objRegExp  = /^([\u0030-\u0039\u0041-\u007A\u002E])+([\u0030-\u0039\u0041-\u007A\u002E\u002C\u002E\u002D\u005F\u002F\u0028\u0029\u0025\u002A\u0040\u0024\u0023\u0026\u002B\s])+$/;
		var objRegExp = /(^[A-Z,a-z,?-?,?-?,\-,\s,0-9,\.]*$)/;
		var strValue = $(this).val();
		if(!objRegExp.test(strValue)){
			$(this).val("");
		}
	});
}
