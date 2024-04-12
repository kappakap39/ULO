// JavaScript Document
//Comment keypress.js Cannot Used Jquery Please New .js Under Jquey.js #SeptemWi 
//$(document).ready(function (){
//	$("input.textboxCurrency").key(function(event){
//		var strChar=String.fromCharCode(event.keyCode);
//		if(!validateInteger(strChar)){			
//			window.event.returnValue = false;
//		}		
//	});	
//	$("input.textboxCurrency").blur(function(){
//		$("input.textboxCurrency").formatCurrency();
//	});	
//});

function keyPressIntegerOnly(){
	var strChar=String.fromCharCode(event.keyCode);
	if(!validateInteger(strChar)){			
			window.event.returnValue = false;
	}	
}
function keyPressIntegerWithMinus(strValue){
	var strChar=String.fromCharCode(event.keyCode);
	var code = event.keyCode;
	
	if(!validateIntegerWithMinus(strChar) || !validateMinus(strValue,strChar)){		 	
			window.event.returnValue = false;
	}	
	 
}

function optionKey(code){
	//8=blackSpace 46=del 35=end 36=home 37=left 38=up 39=right 40=down
	if(code==8 || code==46 || code==35 || code==36 || code==37 || code==38 || code==39 || code==40){
		return true;
	}
	return false;
	
}
function deleteKey(code){
	//8=blackSpace 46=del
	if( code==8 || code==46){
		return true;
	}
	return false;
}
function keyPressFloat(strValue,strDecimal){
	var strChar=String.fromCharCode(event.keyCode);
	var code = event.keyCode;
	if(validateInteger(strChar) || strChar=="."){
			//first charecter
			if(strValue==""){
				if(strChar=="."){
					window.event.returnValue = false;
				}
			}
			var newArray=strValue.split(".");
			if(newArray.length==2){
				 if(strChar=="."){
					window.event.returnValue = false;
				}else{
					if(strDecimal!=0){
						if(newArray[1].length==strDecimal){
							window.event.returnValue = false;
						}
					}
				}
			}
	}else{			
		window.event.returnValue = false;
	}
}

function checkPrecesion(obj,precesion){
	var strValue = obj.value;
	var newArray=strValue.split(".");
	var str2 =getZero(precesion);
	if(newArray.length==2){
		var temp=newArray[1];
		if(temp.length==precesion){
			str2=temp;
		}else if(temp.length>precesion){
			str2=temp.substr(0,precesion);
		}else{
			str2=temp+getZero(parseInt(precesion)-temp.length);
		}
	}
	obj.value=newArray[0]+"."+str2;
}

function getZero(size){
	var temp='';
	for(i=0;i<size;i++){
		temp=temp+"0";
	}
	return temp;
}


function addComma(obj) {
	if(validateNumeric(obj.value) || obj.value == ''){
/**		var strChar=String.fromCharCode(event.keyCode);
		var code = event.keyCode;
	//	alert(event.keyCode);
		if( !( validateInteger(strChar) || deleteKey(code) ) ){
			return;
		}
*/ 
		var isNegative = false;
		strValue = obj.value;
		if(strValue.substring(0,1) == "-"){
			strValue = strValue.substring(1, strValue.length);
			isNegative = true;
		}
		var newArray=strValue.split(".");
		var suffix="";
		if(newArray.length>1){
			suffix="."+newArray[1];
		}
	
		number = Number(newArray[0])+'';
		//number = obj.value;
		number = '' + number;
		if (number.length > 3) {
	//	number = removeCommas(number);
			var mod = number.length % 3;
			var output = (mod > 0 ? (number.substring(0,mod)) : '');
			for (i=0 ; i < Math.floor(number.length / 3); i++) {
				if ((mod == 0) && (i == 0))
					output += number.substring(mod+ 3 * i, mod + 3 * i + 3);
				else
					output+= ',' + number.substring(mod + 3 * i, mod + 3 * i + 3);
			}
			//	return (output.concat(suffix));
			if(isNegative){
				obj.value= '-'+output.concat(suffix);
			}else{
				obj.value= output.concat(suffix);
			}	
		}else{ 
			if(isNegative){
				obj.value= '-'+number.concat(suffix);
			}else{
				obj.value=number.concat(suffix);
			}
		}
	}else{
		alert("Invalid Number");
		obj.select();
		obj.focus();
	}
}

function addCommaTwoDigit(obj) {
	if(obj.value != ''){
		if(validateNumeric(obj.value)){
			var isNegative = false;
			strValue = obj.value;
			if(strValue.substring(0,1) == "-"){
				strValue = strValue.substring(1, strValue.length);
				isNegative = true;
			}
			var newArray=strValue.split(".");
			var suffix=".00";
			if(newArray.length>1 && newArray[1] != ""){
				if(newArray[1].length == 1){
					suffix="."+newArray[1]+"0";
				}else if (newArray[1].length == 2){
					suffix="."+newArray[1];
				}
			}
		
			number = Number(newArray[0])+'';
			//number = obj.value;
			number = '' + number;
			if (number.length > 3) {
		//	number = removeCommas(number);
				var mod = number.length % 3;
				var output = (mod > 0 ? (number.substring(0,mod)) : '');
				for (var i=0 ; i < Math.floor(number.length / 3); i++) {
					if ((mod == 0) && (i == 0))
						output += number.substring(mod+ 3 * i, mod + 3 * i + 3);
					else
						output+= ',' + number.substring(mod + 3 * i, mod + 3 * i + 3);
				}
				//	return (output.concat(suffix));
				if(isNegative){
					obj.value= '-'+output.concat(suffix);
				}else{
					obj.value= output.concat(suffix);
				}	
			}else{ 
				if(isNegative){
					obj.value= '-'+number.concat(suffix);
				}else{
					obj.value=number.concat(suffix);
				}
			}
		}else{
			alert("Invalid Number");
			obj.select();
			obj.focus();
		}
	}
}

function addCommaString(strValue) {
	var result = "";
	if(validateNumeric(strValue)){
		var isNegative = false;
		if(strValue.substring(0,1) == "-"){
			strValue = strValue.substring(1, strValue.length);
			isNegative = true;
		}
		var newArray=strValue.split(".");
		var suffix=".00";
		if(newArray.length>1 && newArray[1] != ""){
			if(newArray[1].length == 1){
				suffix="."+newArray[1]+"0";
			}else if (newArray[1].length == 2){
				suffix="."+newArray[1];
			}
		}
	
		number = Number(newArray[0])+'';
		//number = obj.value;
		number = '' + number;
		if (number.length > 3) {
	//	number = removeCommas(number);
			var mod = number.length % 3;
			var output = (mod > 0 ? (number.substring(0,mod)) : '');
			for (var i=0 ; i < Math.floor(number.length / 3); i++) {
				if ((mod == 0) && (i == 0))
					output += number.substring(mod+ 3 * i, mod + 3 * i + 3);
				else
					output+= ',' + number.substring(mod + 3 * i, mod + 3 * i + 3);
			}
			//	return (output.concat(suffix));
			if(isNegative){
				result = '-'+output.concat(suffix);
			}else{
				result = output.concat(suffix);
			}	
		}else{ 
			if(isNegative){
				result = '-'+number.concat(suffix);
			}else{
				result =number.concat(suffix);
			}
		}
		return result;
	}
}

function removeCommas( strValue ) {
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
function removeCommas2(field) {
	var strValue = field.value;
	var end = false;
	while(!end){
		if(strValue.indexOf(",")<0){
			end =true;
		}else{
			strValue = strValue.replace(",","");
		}
	}
	field.value = strValue;
	field.focus();
	field.select();
}

function  validateNumeric( strValue ) {
/******************************************************************************
DESCRIPTION: Validates that a string contains only valid numbers.

PARAMETERS:
   strValue - String to be tested for validity

RETURNS:
   True if valid, otherwise false.
******************************************************************************/
  var objRegExp  =  /(^-?\d\d*\.\d*$)|(^-?\d\d*$)|(^-?\.\d\d*$)/;

  //check for numeric characters
  return objRegExp.test(strValue);
}
function validateCurrency( strValue ) {
	var objRegExp  =  /(^\d\d*\.\d*$)|(^\d\d*$)|(^\.\d\d*$)/;
	return objRegExp.test(strValue);
}
function validateInteger( strValue ) {
/************************************************
DESCRIPTION: Validates that a string contains only
    valid integer number.

PARAMETERS:
   strValue - String to be tested for validity

RETURNS:
   True if valid, otherwise false.
******************************************************************************/
  var objRegExp  = /(^-?\d\d*$)/;

  //check for integer characters
  return objRegExp.test(strValue); 
}
function validateIntegerWithMinus( strValue ) {
	var objRegExp  = /(^-?\d?\d*$)/;

  return objRegExp.test(strValue); 
}
function validateMinus(strValue,strChar){
	
	if(strValue !='' && strChar=='-'){
		return false;
	}else{
		 return true;
	}
	
}
 
function validateTime(strValue){
	var objRegExp  = /(^[0-2][0-9]:[0-5][0-9]$)/; 

  //check for time characters
  return objRegExp.test(strValue);
}

function keyPressDate(strValue){
	var strChar=String.fromCharCode(event.keyCode);
	var objRegExpFirstDigit  = /^\d{1}$/ ;
	var objRegExpSepDigit = /^\/$/;	
	if(strValue.length==0){
		if(!objRegExpFirstDigit.test(strChar) || strChar>3){			
			window.event.returnValue = false;
		}
	}else if(strValue.length==1){
		if(!objRegExpFirstDigit.test(strChar)){
			window.event.returnValue = false;
		}else{
			if(strValue==0){
				if(strChar==0){
					window.event.returnValue = false;
				}
			}else if(strValue==3){
				if(strChar>1){
					window.event.returnValue = false;
				}
			}
		//alert("false");
	  }
   }else if(strValue.length==2){
    	if(!objRegExpSepDigit.test(strChar)){
			window.event.returnValue = false;
		}
   }else if(strValue.length==3){
   		if(!objRegExpFirstDigit.test(strChar) || strChar>1){
			window.event.returnValue = false;
		}		
   }else if(strValue.length==4){
   		if(!objRegExpFirstDigit.test(strChar) || strChar==0){
			window.event.returnValue = false;
		}else{
			if(strValue.charAt(3)==1 && strChar>2){
				window.event.returnValue = false;
			}
		}		
   }else if(strValue.length==5){
    	if(!objRegExpSepDigit.test(strChar)){
			window.event.returnValue = false;
		}
   }else  if(strValue.length==6){
    	if(!objRegExpFirstDigit.test(strChar) || strChar==0){
			window.event.returnValue = false;
		}
   }else if(strValue.length>6 && strValue.length<10){
   		if(!objRegExpFirstDigit.test(strChar)){
			window.event.returnValue = false;
		}	
   }else{
   		window.event.returnValue = false;
   }
}

function keyPressTime(strValue){
	var strChar=String.fromCharCode(event.keyCode);
	var objRegExpFirstDigit  = /^\d{1}$/ ;
	var objRegExpSepDigit = /^:$/;
	if(strValue.length==0){
		if(!objRegExpFirstDigit.test(strChar) || strChar>2){			
			window.event.returnValue = false;
		}
	}else if(strValue.length==1){
		if(!objRegExpFirstDigit.test(strChar)){
			window.event.returnValue = false;
		}else{
			if(strValue==2){
				if(!objRegExpFirstDigit.test(strChar) || strChar>4){			
					window.event.returnValue = false;
				}
			}
		}
	}else if(strValue.length==2){
		if(!objRegExpSepDigit.test(strChar)){
			window.event.returnValue = false;
		}
	}else if(strValue.length==3){
		if(!objRegExpFirstDigit.test(strChar) || strChar>5){
			window.event.returnValue = false;
		}
	}else if(strValue.length==4){
		if(!objRegExpFirstDigit.test(strChar)){
			window.event.returnValue = false;
		}
	}else if(strValue.length==5){
		window.event.returnValue = false;
	}	
}

function checkFormat(type,valueBox,message){
	//Type constant meaning
	/*	DATE=1
		INTEGER=2
		FLOAT=3
		TIME=4
	*/
	if(type==1 && valueBox.value!=""){
		if(!validateTHDate(valueBox.value)){
			//alert(message);
			valueBox.value="";
			valueBox.focus();			
			return false;
		}
	}else if(type==2 && valueBox.value!=""){
		if(!validateInteger(valueBox.value)){
			alert(message);
			valueBox.value="";
			valueBox.focus();
			return false;
		}
	}else if(type==3 && valueBox.value!=""){
		if(!validateNumeric(valueBox.value)){
			alert(message);
			valueBox.value="";
			valueBox.focus();
			return false;
		}
	}else if(type==4 && valueBox.value!=""){
		if(!validateTime(valueBox.value)){
			alert(message);
			valueBox.value="";
			valueBox.focus();
			return false;
		}
	}
	return true;
	
}

function validateTHDate(strValue) {
/************************************************
DESCRIPTION: Validates that a string contains only
    valid dates with 2 digit month, 2 digit day,
    4 digit year. Date separator can be ., -, or /.
    Uses combination of regular expressions and
    string parsing to validate date.
    Ex. dd/mm/yyyy or dd-mm-yyyy or dd.mm.yyyy

PARAMETERS:
   strValue - String to be tested for validity

RETURNS:
   True if valid, otherwise false.

REMARKS:
   Avoids some of the limitations of the Date.parse()
   method such as the date separator character.
*************************************************/
  var objRegExp = /^\d{2}(\/)\d{2}(\/)\d{4}$/

  //check to see if in correct format
  if(!objRegExp.test(strValue)){
    return false; //doesn't match pattern, bad date
  }else{
    var strSeparator = strValue.substring(2,3) //find date separator
    var arrayDate = strValue.split(strSeparator); //split date into month, day, year
    //create a lookup for months not equal to Feb.
    var arrayLookup = { '01' : 31,'03' : 31, '04' : 30,'05' : 31,'06' : 30,'07' : 31,
                        '08' : 31,'09' : 30,'10' : 31,'11' : 30,'12' : 31}
    var intDay =arrayDate[0];

    //check if month value and day value agree
    if(arrayLookup[arrayDate[1]] != null) {
      if(intDay <= arrayLookup[arrayDate[1]] && intDay != 0)
        return true; //found in lookup table, good date
    }

    //check for February (bugfix 20050322)
    var intMonth = arrayDate[1];
    if (intMonth == 2) { 
       var intYear = arrayDate[2];
       if( ((intYear % 4 == 0 && intDay <= 29) || (intYear % 4 != 0 && intDay <=28)) && intDay !=0)
          return true; //Feb. had valid number of days
       }
  }
  return false; //any other values, bad date
}
/*************************************/
/****  	check charactor			******/
/*************************************/
function checkEngCharactors(field,mandate){
var str = eval("document.appFormName."+field);
var reg1=/(^[a-zA-Z]*$)/ ;
	if(!reg1.test(str.value) && str.value != ''){	
		 alert('PLEASE_KEY_ENG_ONLY');
		 str.focus();
		str.select();
	}else{
		if(mandate && str.value==''){
			alert('PLEASE_KEY_ENG_NAME');
			str.focus();
			str.select();
		}
	}
}
function isNumberOnKeyPress(input){
	var strChar=String.fromCharCode(event.keyCode);
	var inputs = input.value.split(".");
	if(validateInteger(strChar) || strChar=="."){
	    /*if(input.value != '' && !input.value.match(/^[0-9]+$/g)){
	       if(inputs.length > 1){
	       		//alert(inputs[1].length);
	       		if(inputs[1].length > 2){
	       			//input.value = input.value.substring(0, (input.value.length)-1);
	       			window.event.returnValue = false;
	       		}
	       }
	    }
	    return true;*/
	}else{
		window.event.returnValue = false;
	}
}
function isNumber2DigitOnKeyPress(input){
	var strChar=String.fromCharCode(event.keyCode);
	if(validateInteger(strChar) || strChar=="."){
		if(input.value.length > (input.maxLength - 4) && input.value.indexOf(".") == -1 && strChar != "."){
			window.event.returnValue = false;
		}
	}else{
		window.event.returnValue = false;
	}
}
function isNumberOnKeyPressNegative(input){
	var strChar=String.fromCharCode(event.keyCode);
	var inputs = input.value.split(".");
	if(validateInteger(strChar) || strChar=="." || strChar=="-"){
		/*if((input.value != '' || input.selected == false) && strChar=="-"){
			window.event.returnValue = false;
		}*/
	    /*if(input.value != '' && !input.value.match(/^[0-9]+$/g)){
	       if(inputs.length > 1){
	       		//alert(inputs[1].length);
	       		if(inputs[1].length > 2){
	       			//input.value = input.value.substring(0, (input.value.length)-1);
	       			window.event.returnValue = false;
	       		}
	       }
	    }
	    return true;*/
	}else{
		window.event.returnValue = false;
	}
}
function isNumberOnkeyUp(input){
	var strChar=String.fromCharCode(event.keyCode);
	var inputs = input.value.split(".");
	if(inputs.length > 1){
		//alert(inputs[1].length);
		if(inputs[1].length == 2){
			return true;
		}else if(inputs[1].length > 2){
   			input.value = input.value.substring(0, (input.value.length)-1);
   			//window.event.returnValue = false;
   		}
	}
}
function isNumberOnkeyDown(input){
	var strChar=String.fromCharCode(event.keyCode);
	var inputs = input.value.split(".");
	if(inputs.length > 1){
		//alert(inputs[1].length);
		if(inputs[1].length < 2){
			return true;
		}else if(inputs[1].length > 2){
   			//input.value = input.value.substring(0, (input.value.length)-1);
   			window.event.returnValue = false;
   		}
	}
}
function keyDecimal(strValue,strDecimal){
	var newArray=strValue.split(".");
	if(newArray.length==2){
		if(strDecimal!=0){
			if(newArray[1].length==strDecimal){
				window.event.returnValue = false;
			}
		}
	}
} 

function removeCommaField(fieldObj){
		var value = fieldObj.value;
		//fieldObj.value= removeCommas2(value);
		removeCommas2(fieldObj);
}

function addDecimalFormat(inputField){
	var strValue = inputField.value;
	var newArray = strValue.split(".");
	if(strValue == ""){
		return inputField.value = "0.00";
	}else if(newArray.length>1){
		if(newArray[1].length == 1){
			inputField.value = strValue+"0";
		}else if(newArray[1].length > 2){
			newArray[1] = newArray[1].substring(0,2);
			inputField.value = newArray[0]+"."+newArray[1];
		}
	}else{
		strValue = strValue+".00";
		inputField.value = strValue;
	} 
}

function addDecimalFormatNotDefaultZero(inputField){
	var strValue = inputField.value;
	var newArray = strValue.split(".");
	if(strValue == ""){
		return strValue;
	}else if(newArray.length>1){
		if(newArray[1].length == 1){
			inputField.value = strValue+"0";
		}else if(newArray[1].length > 2){
			newArray[1] = newArray[1].substring(0,2);
			inputField.value = newArray[0]+"."+newArray[1];
		}
	}else{
		strValue = strValue+".00";
		inputField.value = strValue;
	} 
}

function returnZero(inputField){
	var strValue = inputField.value;
	if(strValue == ""){
		return inputField.value = "0";
	}
}
function checkDecimalFormat(field, strDecimal){
	var strValue = field.value;
	var newArray=strValue.split(".");
	var decimal;
	if(newArray.length==2){
		 if(strDecimal!=0){
			if(newArray[1].length==strDecimal){
				//window.event.returnValue = false;
				decimal = newArray[1].substring(0,2);
				field.value = newArray[0] + "." + decimal;
				//return false;
			}
		}
	}
}	
function keyPressIntegerNCBData(obj){
    var strChar=String.fromCharCode(event.keyCode);
	var code = event.keyCode;
	var strValue=obj.value;    
    if(strChar=='-'){
       if(strValue!="" ){                      
    	 window.event.returnValue = false;    	     	
         }	  
    }else{	
       if(strValue!="" && strValue.substring(0,1)=='-' ){                      
    	 window.event.returnValue = false;    	     	
         }	  
	  if(!validateInteger(strChar)){			
			window.event.returnValue = false;
     	}	
	}
}
function keyPressEnter(){
	if(event.keyCode == 13){
		return false;
	}
	return true;
}
