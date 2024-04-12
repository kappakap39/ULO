/** Remove DateFormat #SeptemWi
var isNav4 = false, isNav5 = false, isIE4 = false
var strSeperatorDate = "/"; 
var vDateType = 3; // Global value for type of date format
var vYearType = 4; //Set to 2 or 4 for number of digits in the year for Netscape
var vYearLength = 2; // Set to 4 if you want to force the user to enter 4 digits for the year before validating.
var err = 0; // Set the error code to a default of zero
if(navigator.appName == "Netscape") {
if (navigator.appVersion < "5") {
isNav4 = true; isNav5 = false;
}else if (navigator.appVersion > "4") {
isNav4 = false;isNav5 = true;
 }
}else {
isIE4 = true;
}
function DateFormat(vDateName, vDateValue, e, dateCheck, dateType) {
vDateType = dateType;
if (vDateValue == "~") {
alert("AppVersion = "+navigator.appVersion+" \nNav. 4 Version = "+isNav4+" \nNav. 5 Version = "+isNav5+" \nIE Version = "+isIE4+" \nYear Type = "+vYearType+" \nDate Type = "+vDateType+" \nSeparator = "+strSeperatorDate);
vDateName.value = "";vDateName.focus();return true;
}
var whichCode = (window.Event) ? e.which : e.keyCode;
if (vDateValue.length > 8 && isNav4) {
if ((vDateValue.indexOf("-") >= 1) || (vDateValue.indexOf("/") >= 1))return true;
}
var alphaCheck = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ/-";
var alphaCheck1 = "65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,32,191,189,109,111";
if (alphaCheck.indexOf(vDateValue) >= 1) {
if (isNav4) {
vDateName.value = "";vDateName.focus();vDateName.select();
return false;
}else {
var keyCheck = '37,38,39,40,112,113,114,115,116,117,118,119,120,121,122,123,45,36,35,34,33,19,145,13,16,17,18,93,92,20,144';
if (keyCheck.indexOf(e.keyCode)==-1) {
vDateName.value = vDateName.value.substr(0, (vDateValue.length-1));
}
return false;
   }
}
if (whichCode == 8) //Ignore the Netscape value for backspace. IE has no value
return false;
else {
//Create numeric string values for 0123456789/
//The codes provided include both keyboard and keypad values
var strCheck = '47,48,49,50,51,52,53,54,55,56,57,58,59,95,96,97,98,99,100,101,102,103,104,105';
if (strCheck.indexOf(whichCode) != -1) {
if (isNav4) {
if (((vDateValue.length < 6 && dateCheck) || (vDateValue.length == 7 && dateCheck)) && (vDateValue.length >=1)) {
alert("Invalid Date\nPlease Re-Enter");
vDateName.value = "";vDateName.focus();vDateName.select();
return false;
}
if (vDateValue.length == 6 && dateCheck) {
var mDay = vDateName.value.substr(2,2);
var mMonth = vDateName.value.substr(0,2);
var mYear = vDateName.value.substr(4,4)
if (mYear.length == 2 && vYearType == 4) {
var mToday = new Date();
var checkYear = mToday.getFullYear() + 30; 
var mCheckYear = '20' + mYear;
if (mCheckYear >= checkYear)mYear = '19' + mYear;
elsemYear = '20' + mYear;
}
var vDateValueCheck = mMonth+strSeperatorDate+mDay+strSeperatorDate+mYear;
if (!dateValid(vDateValueCheck)) {
alert("Invalid Date\nPlease Re-Enter");
vDateName.value = "";vDateName.focus();vDateName.select();
return false;
}
return true;
}else {
// Reformat the date for validation and set date type to a 1
if (vDateValue.length >= 8  && dateCheck) {
if (vDateType == 1) // mmddyyyy
{
var mDay = vDateName.value.substr(2,2);
var mMonth = vDateName.value.substr(0,2);
var mYear = vDateName.value.substr(4,4)
vDateName.value = mMonth+strSeperatorDate+mDay+strSeperatorDate+mYear;
}
if (vDateType == 2) // yyyymmdd
{
var mYear = vDateName.value.substr(0,4)
var mMonth = vDateName.value.substr(4,2);
var mDay = vDateName.value.substr(6,2);
vDateName.value = mYear+strSeperatorDate+mMonth+strSeperatorDate+mDay;
}
if (vDateType == 3) // ddmmyyyy
{
var mMonth = vDateName.value.substr(2,2);
var mDay = vDateName.value.substr(0,2);
var mYear = vDateName.value.substr(4,4)
vDateName.value = mDay+strSeperatorDate+mMonth+strSeperatorDate+mYear;
}
//Create a temporary variable for storing the DateType and change
//the DateType to a 1 for validation.
var vDateTypeTemp = vDateType;
vDateType = 1;
var vDateValueCheck = mMonth+strSeperatorDate+mDay+strSeperatorDate+mYear;
if (!dateValid(vDateValueCheck)) {
alert("Invalid Date\nPlease Re-Enter");
vDateType = vDateTypeTemp;
vDateName.value = "";
vDateName.focus();
vDateName.select();
return false;
}
vDateType = vDateTypeTemp;
return true;
}
else {
if (((vDateValue.length < 8 && dateCheck) || (vDateValue.length == 9 && dateCheck)) && (vDateValue.length >=1)) {
alert("Invalid Date\nPlease Re-Enter");
vDateName.value = "";vDateName.focus();vDateName.select();
return false;
         }
      }
   }
}else {
// Non isNav Check
if (((vDateValue.length < 8 && dateCheck) || (vDateValue.length == 9 && dateCheck)) && (vDateValue.length >=1)) {
alert("Invalid Date\nPlease Re-Enter");
vDateName.value = "";vDateName.focus();return true;
}
if (vDateValue.length >= 8 && dateCheck) {
if (vDateType == 1) // mm/dd/yyyy
{
var mMonth = vDateName.value.substr(0,2);
var mDay = vDateName.value.substr(3,2);
var mYear = vDateName.value.substr(6,4)
}
if (vDateType == 2) // yyyy/mm/dd
{
var mYear = vDateName.value.substr(0,4)
var mMonth = vDateName.value.substr(5,2);
var mDay = vDateName.value.substr(8,2);
}
if (vDateType == 3) // dd/mm/yyyy
{
var mDay = vDateName.value.substr(0,2);
var mMonth = vDateName.value.substr(3,2);
var mYear = vDateName.value.substr(6,4)
}
if (vYearLength == 4) {
if (mYear.length < 4) {
alert("Invalid Date\nPlease Re-Enter");
vDateName.value = "";vDateName.focus();return true;
   }
}
var vDateTypeTemp = vDateType;
vDateType = 1;
var vDateValueCheck = mMonth+strSeperatorDate+mDay+strSeperatorDate+mYear;
if (mYear.length == 2 && vYearType == 4 && dateCheck) {
var mToday = new Date();
var checkYear = mToday.getFullYear() + 30; 
var mCheckYear = '20' + mYear;
if (mCheckYear >= checkYear)mYear = '19' + mYear;
elsemYear = '20' + mYear;
vDateValueCheck = mMonth+strSeperatorDate+mDay+strSeperatorDate+mYear;
if (vDateTypeTemp == 1) // mm/dd/yyyy
vDateName.value = mMonth+strSeperatorDate+mDay+strSeperatorDate+mYear;
if (vDateTypeTemp == 3) // dd/mm/yyyy
vDateName.value = mDay+strSeperatorDate+mMonth+strSeperatorDate+mYear;
} 
if (!dateValid(vDateValueCheck)) {
alert("Invalid Date\nPlease Re-Enter");
vDateType = vDateTypeTemp;vDateName.value = "";vDateName.focus();
return true;
}
vDateType = vDateTypeTemp;
return true;
}else {
if (vDateType == 1) {
if (vDateValue.length == 2) {vDateName.value = vDateValue+strSeperatorDate;}
if (vDateValue.length == 5) {vDateName.value = vDateValue+strSeperatorDate;}
}
if (vDateType == 2) {
if (vDateValue.length == 4) {vDateName.value = vDateValue+strSeperatorDate;}
if (vDateValue.length == 7) {vDateName.value = vDateValue+strSeperatorDate;}
} 
if (vDateType == 3) {
if (vDateValue.length == 2) {vDateName.value = vDateValue+strSeperatorDate;}
if (vDateValue.length == 5) {vDateName.value = vDateValue+strSeperatorDate;}
}
return true;
   }
}
if (vDateValue.length == 10&& dateCheck) {
if (!dateValid(vDateName)) { 
alert("Invalid Date\nPlease Re-Enter");
vDateName.focus();vDateName.select();
   }
}
return false;
}else {
if (isNav4) {
vDateName.value = "";vDateName.focus();vDateName.select();
return false;
}else{
var keyCheck = '37,38,39,40,112,113,114,115,116,117,118,119,120,121,122,123,45,36,35,34,33,19,145,13,16,17,18,93,92,20,144';
if (keyCheck.indexOf(e.keyCode)==-1) {
vDateName.value = vDateName.value.substr(0, (vDateValue.length-1));
}
return false;
         }
      }
   }
}
*/
function removeCommasOnField( strValue , field) {
//	alert(field);
  var objRegExp = /,/g; //search for commas globally
//replace all matches with empty strings
  var obj = eval("document.appFormName."+field);
  alert(strValue.replace(objRegExp,''));
  obj.value = strValue.replace(objRegExp,'');  
}
function checkFraction(noFrac,value){
	var fracPos = value.indexOf(".");
	var l = value.length-1;
	var diff = l-fracPos;
	if(fracPos==-1)
		return true;
	else if(diff>noFrac){
//		alert("Please do not enter  more than "+noFrac+" decimals !!");
		return false;
	}	
	return true;
}
function checkFractionNoAlert(noFrac,value){
	var fracPos = value.indexOf(".");
	var l = value.length-1;
	var diff = l-fracPos;
	if(fracPos==-1)
		return true;
	else if(diff>noFrac){
//		alert("Please do not enter  more than "+noFrac+" decimals !!");
		return false;
	}	
	return true;
}
function addCommas( field ) {
	var no = eval("document.appFormName."+field);
	if(checkNumber(field)){		
		strVal =removeCommas(no.value) ;
		if(checkFraction(2,strVal)){
			var numVal = parseFloat(strVal);			
			if (!isNaN(numVal)){			 
				strValue = new String(numVal);				
			  	var objRegExp  = new RegExp('(-?[0-9]+)([0-9]{3})');
			    while(objRegExp.test(strValue)) {  strValue = strValue.replace(objRegExp, '$1,$2');   }			    
			    no.value = strValue ;
		    }else {  	no.value = "";    }
	    }else {
	    	no.focus();no.select();return false ;
	    }	  
	 }else {no.value = ""; }
}
function addCommaNotChkNumber( field ) {
	var no = eval("document.appFormName."+field);			
		strVal =removeCommas(no.value) ;
		if(checkFractionNoAlert(2,strVal)){
			var numVal = parseFloat(strVal);			
			if (!isNaN(numVal)){			 
				strValue = new String(numVal);				
			  	var objRegExp  = new RegExp('(-?[0-9]+)([0-9]{3})');
			    while(objRegExp.test(strValue)) {  strValue = strValue.replace(objRegExp, '$1,$2');   }			    
			    no.value = strValue ;
		    }else {  	no.value = "";    }
	    }else {
	    	no.focus();no.select();return false ;
	    }	  	 
}

function date2Number(str){
	var num = 0;
	try	{
		num = str.substring(6,10) + str.substring(3,5) + str.substring(0,2);
	}catch(error){
		num = 0;
	}
	return num;
}
function addCommasNCBData( inputField ) {	 
	if(inputField.value!='-'){
	//if(checkNumber(field)){		
		strVal =removeCommas(inputField.value) ;
		if(checkFraction(2,strVal)){
			var numVal = parseFloat(strVal);			
			if (!isNaN(numVal)){			 
				strValue = new String(numVal);				
			  	var objRegExp  = new RegExp('(-?[0-9]+)([0-9]{3})');
			    while(objRegExp.test(strValue)) {  strValue = strValue.replace(objRegExp, '$1,$2');   }			    
			    inputField.value = strValue ;
		    }else {  	inputField.value = "";    }
	    }else {
	    	inputField.focus();inputField.select();return false ;
	    }	  
	 //}else {inputField.value = ""; }
	 }
}