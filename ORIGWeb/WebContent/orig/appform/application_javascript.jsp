<%@ page import="com.eaf.j2ee.pattern.util.ErrorUtil" %>
<%
%>
<SCRIPT language=JavaScript type=text/JavaScript>
var citizenErrorFlag = false;
function removeCommas( strValue ) {
	//search for commas globally
  	var objRegExp = /,/g; 
	//replace all matches with empty strings
  	return strValue.replace(objRegExp,'');
}
function checkNumber(field,mandate){
var number=eval("document.appFormName."+field);
var number1 = removeCommas(number.value);
if(mandate&&number.value==''){
			alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_KEY_NUMBER_ONLY")%>');		
			number.value= "";	
			number.focus();
			number.select();
			return false ;
		}
if (isNaN(number1)){
	alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_KEY_NUMBER_ONLY")%>');	
      number.focus();
      number.select();
      //  number.value="";
		return false;
}else{
	if(number1<0){
	  alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_KEY_NUMBER_MORE_THAN_ZERO")%>');	
      number.focus();
      number.select();
      return false;
	}
}
return true;
}
function checkPhone(field,mandate){
var str = eval("document.appFormName."+field);
var reg1=/(^[0-9]{1}\-[0-9]{4}\-[0-9]{4}$)/ ;
if(mandate&&str.value==''){
			alert('<%=ErrorUtil.getShortErrorMessage(request,"INVALID_PHONE_NUMBER")%>');		
			str.value= "";	
			str.focus();
			str.select();
			return false ;
		}
	if(!reg1.test(str.value)&&str.value != ''){
		alert('<%=ErrorUtil.getShortErrorMessage(request,"INVALID_PHONE_NUMBER")%>');	
		str.focus();
		str.select();
	return false;
	}
}
function checkFax(field){
var str = eval("document.appFormName."+field);
var reg1=/(^[0-9]{1}\-[0-9]{4}\-[0-9]{4}$)/ ;
	if(!reg1.test(str.value)&&str.value != ''){
	alert('<%=ErrorUtil.getShortErrorMessage(request,"INVALID_FAX_NUMBER")%>');	
		str.focus();
		str.select();
	return false;
	}
}

function checkThaiFName(field,mandate){
var str = eval("document.appFormName."+field);
var reg1=/(^[A-Z,a-z,?-?,?-?,\-,\s,0-9,\.]*$)/ ;

	if(!reg1.test(str.value) && str.value != ''){
		alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_VERIFY_THAI_FIRST_NAME")%>');
		str.focus();
		str.select();
	}
}
function checkThaiLName(field,mandate){
var str = eval("document.appFormName."+field);
var reg1=/(^[A-Z,a-z,?-?,?-?,\-,\s,0-9,\.]*$)/ ;

	if(!reg1.test(str.value) && str.value != ''){
		alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_VERIFY_THAI_LAST_NAME")%>');
		str.focus();
		str.select();	
	}else{
		if(mandate && str.value==''){
			alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_VERIFY_THAI_LAST_NAME")%>');
			str.focus();
			str.select();
		}
	}
}
function checkThaiMName(field,mandate){
var str = eval("document.appFormName."+field);
var reg1=/(^[A-Z,a-z,?-?,?-?,\-,\s,0-9,\.]*$)/ ;
	if(!reg1.test(str.value) && str.value != ''){
		alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_VERIFY_THAI_MIDDLE_NAME")%>');
		str.focus();
		str.select();	
	}else{
		if(mandate && str.value==''){
			alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_VERIFY_THAI_MIDDLE_NAME")%>');
			str.focus();
			str.select();
		}
	}
}
function checkEngFName(field,mandate){
var str = eval("document.appFormName."+field);
var reg1=/(^[a-zA-Z\s\.\-0-9]*$)/ ;
	if(!reg1.test(str.value) && str.value != ''){	
		 alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_VERIFY_ENG_FIRST_NAME")%>');
         str.focus();
         str.select();
	}else{
		if(mandate && str.value==''){
			alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_VERIFY_ENG_FIRST_NAME")%>');
			str.focus();
			str.select();
		}
	}
}
function checkEngLName(field,mandate){
var str = eval("document.appFormName."+field);
var reg1=/(^[a-zA-Z\s\.\-0-9]*$)/ ;
	if(!reg1.test(str.value) && str.value != ''){	
		 alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_VERIFY_ENG_LAST_NAME")%>');
         str.focus();
         str.select();
	}else{
		if(mandate && str.value==''){
				alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_VERIFY_ENG_LAST_NAME")%>');
				str.focus();
				str.select();
		}
  }
}

function checkEngMName(field,mandate){
var str = eval("document.appFormName."+field);
var reg1=/(^[a-zA-Z\s\.\-0-9]*$)/ ;
	if(!reg1.test(str.value) && str.value != ''){	
		 alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_VERIFY_ENG_MIDDLE_NAME")%>');
         str.focus();
         str.select();
	}else{
		if(mandate && str.value==''){
				alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_VERIFY_ENG_MIDDLE_NAME")%>');
				str.focus();
				str.select();
		}
  }
}
function checkNumber13(field){
var str = eval("document.appFormName."+field);
var reg1=/(^[0-9]{1}\-[0-9]{4}\-[0-9]{5}\-[0-9]{2}\-[0-9]{1}$)/ ;
var type =document.appFormName.identificationType;
if(type.value ==1 || type.value ==4){
	if(!reg1.test(str.value)){	
		alert('<%=ErrorUtil.getShortErrorMessage(request,"INVALID_CITIZEN_ID")%>');
	return false;
	}
	}
	return true;
}
function checkNationality(field,conditionField){
	var str = eval("document.appFormName."+field);
	var strCondition = eval("document.appFormName."+conditionField);
	if(strCondition.value != '' && strCondition.value == '176067'){
		if(str.value == 'TH'){
			alert('<%=ErrorUtil.getShortErrorMessage(request,"INVALID_FOR_INVOVLED_176067")%>');
			str.value = '';
			document.appFormName.nationalityDesc.value = "";
			str.focus();
			str.select();
			return false;
		}
	}else if(strCondition.value != '' && strCondition.value == '176001'){
		if(str.value != ''){
			if(str.value != 'TH'){
				alert('<%=ErrorUtil.getShortErrorMessage(request,"INVALID_FOR_INVOVLED_176001")%>');
				str.value = '';
				document.appFormName.nationalityDesc.value = "";
				str.focus();
				str.select();
				return false;
			}
		}
	}
	return true;
}
function checkCountry(field,conditionField){
	var str = eval("document.appFormName."+field);
	var strCondition = eval("document.appFormName."+conditionField);if(strCondition.value != '' && strCondition.value == '176001'){
		if(str.value != 'TH' && str.value != ''){
			alert('<%=ErrorUtil.getShortErrorMessage(request,"INVALID_FOR_INVOVLED_176001")%>');
			str.value = '';
			str.focus();
			str.select();
			return false;
		}
	}
	return true;
}
function checkPersonalNationality(field,conditionField){
	if(checkNationality(field,conditionField)){
		document.appFormName.nationalityBtn.click();
	}
}
function checkPersonalCountry(field,conditionField){
	if(checkCountry(field,conditionField)){
		document.appFormName.countryBtn.click();
	}
}
function checkCitizenIDByType(field,typeField){
	var str = eval("document.appFormName."+field);
	var strType = eval("document.appFormName."+typeField);
	if(strType.value != '' && strType.value =='324001'){
		if(str.value != ''){
			if(!checkNumber(field)){
				citizenErrorFlag = true;
				str.focus();
				str.select();
				return false;
			}else if(str.value.length != 13){
				alert('<%=ErrorUtil.getShortErrorMessage(request,"INVALID_CITIZEN_ID")%>');
				str.focus();
				str.select();
				return false;
			}else if(!checkCitizenIDDigit(str.value)){
				alert('<%=ErrorUtil.getShortErrorMessage(request,"INVALID_FORMAT_CITIZEN_ID")%>');
				str.focus();
				str.select();
				return false;
			}else{
				citizenErrorFlag = false;
			}
		}
	}else if(strType.value != '' && strType.value =='324002'){
		if(str.value != ''){
			checkEngCitizenID(field);
		}
	}
}
function checkZipCode(field,mandate){
		var number = eval("document.appFormName."+field);
		var number1 = removeCommas(number.value);
		if(mandate&&number.value==''){
			alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_KEY_POST_CODE")%>');
			number.value= "";	
			number.focus();
			number.select();
			return false ;
		}
		if (isNaN(number1)){			
			alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_KEY_POST_CODE")%>');
		number.value= "";	
		number.focus();
		number.select();
		return false ;
		}
		if ( (number.value != "") && (number.value.length < 5) ){	
			alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_KEY_POST_CODE")%>');
		number.value= "";	
		number.focus();
		number.select();
		return false ;
		}
}


function validateUSDate( strValue ) {
strValue = trimString(strValue);
  var objRegExp = /^\d{1,2}(\-|\/|\.)\d{1,2}\1\d{4}$/
  if(!objRegExp.test(strValue) && strValue != "" ){
		alert('<%=ErrorUtil.getShortErrorMessage(request,"INVALID_DATE")%>');
       return false;}
  else{
    var strSeparator = strValue.substring(2,3) //find date separator
    var arrayDate = strValue.split('/'); //split date into month, day, year
    //create a lookup for months not equal to Feb.
    var arrayLookup = { '01' : 31,'03' : 31, '04' : 30,'05' : 31,'06' : 30,'07' : 31,
                        '08' : 31,'09' : 30,'10' : 31,'11' : 30,'12' : 31}
    var intDay = parseInt(arrayDate[0],10);
	//var m = '09';
	var intMonth = parseInt(arrayDate[1],10);
	var intYear = parseInt(arrayDate[2]);
    if(arrayLookup[arrayDate[1]] != null) {
      if((intDay <= arrayLookup[arrayDate[1]]) && intDay != 0 && intMonth < 13)
    	return true; //found in lookup table, good date
    }
    //check for February   
     if( ((intYear % 4 == 3 && intDay <= 29) || (intYear % 4 != 3 && intDay <=28)) && intDay !=0 && intMonth <13 && intMonth >0  )
      return true; //Feb. had valid number of days 		
    if(strValue =='')
     return true;
		alert('<%=ErrorUtil.getShortErrorMessage(request,"INVALID_DATE")%>');
  return false;
  }
}
function  checkBirthDate(field,mandate,field3){
	var obj=eval("document.appFormName."+field);
	var obj3=eval("document.appFormName."+field3);
	if(mandate&&obj.value==''){
			alert('<%=ErrorUtil.getShortErrorMessage(request,"INVALID_DATE")%>');
			obj.value= "";	
			obj.focus();
			obj.select();
			return false ;
	}
	if(!validateUSDate(obj.value) && obj.value != '' ){
		obj.focus();
		obj.select();
	}else{
		var strValue = obj.value ;
		var arrayDate = strValue.split('/');
		var intYear = parseInt(arrayDate[2]);
		//alert(intYear);
		var Max = intYear + 65 ;
		var Min = intYear + 20 ;
		var currentDate = new Date();
		var cYear  = currentDate.getFullYear();
		
		var cMonth = currentDate.getMonth() + 1;	
		var cDate = currentDate.getDate();
			
		var yearDiff = 0;
		var intMonth = arrayDate[1];
		var intDate = arrayDate[0];
		
		var cTime = currentDate.getTime();
		var inputDate = new Date(intYear-543,intMonth-1,intDate);
		var inputTime = inputDate.getTime();
				
		if(cMonth < intMonth || (cMonth == intMonth && cDate < intDate)){
			yearDiff = 1;
		}
		//alert(cYear);
		cYear = cYear + 543 ;
		//alert(cYear);
		if(inputTime >= cTime || (cDate==intDate && cMonth == intMonth && cMonth == intMonth && cYear==intYear )){						
			alert('<%=ErrorUtil.getShortErrorMessage(request,"BIRTHDATE_LEAST_THAN_CURRENT")%>');
			obj.focus();
			obj.select();
			return false;
		}
		var age = cYear-intYear;
		if(strValue != ''){
			obj3.value=((cYear-intYear-yearDiff).toString());
		}else{
			obj3.value='';
		}
	}
}
function checkDate(field,mandate){
	var obj=eval("document.appFormName."+field);
	if(!validateUSDate(obj.value) && obj.value != '' ){
	obj.focus();
	obj.select();
	}else{
		if(mandate && obj.value==''){
			alert('<%=ErrorUtil.getShortErrorMessage(request,"INVALID_DATE")%>');
			obj.focus();
			obj.select();
		}
	}
}
function checkMonth(field){
var number = eval("document.appFormName."+field);
var number1 = removeCommas(number.value);
	if (isNaN(number1)){
			alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_KEY_NUMBER_ONLY")%>');
			number.focus();
	}else{
		if(!validateInteger(number1)){
			alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_KEY_INTEGER")%>');
			number.focus();
			number.select();
		}
		else if (number1 > 11 || number1<0){	
			alert('<%=ErrorUtil.getShortErrorMessage(request,"INVALID_MONTH_BETWEEN_0_12")%>');
			number.focus();
			number.select();	
		}
	}
}

function checkYear(field){
var number = eval("document.appFormName."+field);
var number1 = removeCommas(number.value);
	if (isNaN(number1)){
		alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_KEY_NUMBER_ONLY")%>');
		number.focus();
	}else{		
		if(!validateInteger(number1)){				
			alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_KEY_INTEGER")%>');
			number.focus();
			number.select();
		}else if (number1<0){
			alert('<%=ErrorUtil.getShortErrorMessage(request,"INVALID_YEAR")%>');
			number.focus();
			number.select();
		}
	
	}
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
  if(strValue==null || strValue=='')
  return true;
  //check for integer characters
  return objRegExp.test(strValue);
}
function checkCardExpireDate(field){
	var obj=eval("document.appFormName."+field);	
	if(!citizenErrorFlag){		
		if(!validateUSDate(obj.value) && obj.value != '' ){
			obj.focus();
			obj.select();
		}else{
			var strValue = obj.value ;
			var arrayDate = strValue.split('/');
			var intYear = parseInt(arrayDate[2],10);
	
			var intMonth = parseInt(arrayDate[1],10);
			var intDay = parseInt(arrayDate[0],10);	
			var inputDate = new Date(intYear-543,intMonth-1,intDay);
			
			var inputTime = inputDate.getTime();
			var currentDate = new Date();
			var cYear  = currentDate.getFullYear();
			var cMonth = currentDate.getMonth()+1;
			var cDay = currentDate.getDate();
			var cTime = currentDate.getTime();
			 cYear = cYear + 543 ;
			 if(intYear <= 2400) {
		    	alert('??? ????? ?? ????????????????????????????? (?????????? ?.?.)');
		    	obj.focus();
				obj.select();
		    }else if(inputTime < cTime){
				alert('<%=ErrorUtil.getShortErrorMessage(request,"EXPIRY_DATE_MORE_THAN_CURRENT")%>');
				obj.focus();
				obj.select();
			} 
		}
	}
}
function checkEmailAddress(field){
  var str = eval("document.appFormName."+field);
	if(str.value !="" ){ 
		var goodEmail = str.value.match(/\b(^(\S+@).+((\.com)|(\.net)|(\.edu)|(\.mil)|(\.gov)|(\.org)|(\..{2,2}))$)\b/gi);
		if (!goodEmail){
					alert('<%=ErrorUtil.getShortErrorMessage(request,"INVALID_EMAIL")%>');
   					str.focus();
   					str.select();
   		}
	}
}
function checkThaiLang(str){
	var reg1=/(^[?-?,?-?]*$)/ ;
	if(!reg1.test(str.value)){
		str.focus();
		str.select();
		return false;
	}else return true;
}
function subString(fieldName,sLength){
	var str = eval("document.appFormName."+fieldName);
	var strLength = str.value.length;
	if(strLength>20){
		str.value=str.value.substr(0,sLength);
	}
}
function upperCaseString(fieldName){
	var str = eval("document.appFormName."+fieldName);
	str.value=str.value.toUpperCase();
}
function copyValue(fieldName1,fieldName2){
	var str1 = eval("document.appFormName."+fieldName1);
	var str2 = eval("document.appFormName."+fieldName2);
	str2.value = str1.value;
}
function CheckBankAccountNo(vSSNName,vSSNValue){
  expiredDateName = vSSNName;
  expiredDateValue = vSSNValue;
  mSSNValue = trimString(replaceSubstring(vSSNValue,"-",""));  
   if(mSSNValue.length<10){
      <%   //err = error.getShortErrorMessage(request,"BankAccountNoFormatErr1") ;  %>
          //alert('<%//= err%> ');
   }else{
     if(mSSNValue.length==3){
         mSSNValue=mSSNValue+"-";
         vSSNName.value=mSSNValue;
     }
     if(mSSNValue.length==9){
       var bkaccno = mSSNValue.substr(0,3)+"-"+mSSNValue.substr(3,9)+"-"+mSSNValue.substr(9,10);
       vSSNName.value=bkaccno;
     }
   }
}
function CheckBankAccountNo(vSSNName,vSSNValue,e){
  expiredDateName = vSSNName;
  expiredDateValue = vSSNValue;
  var whichCode = (window.Event) ? e.which : e.keyCode;
  mSSNValue = trimString(replaceSubstring(vSSNValue,"-",""));  
      //Eliminate all the ASCII codes that are not valid
    var alphaCheck = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ/-";
     if (alphaCheck.indexOf(vSSNValue) >= 1)  // if alphaCheck
     {
      if (isNav4)
      {
         vSSNName.value = "";
         vSSNName.focus();
         vSSNName.select();
         return false;
      }
      else
      {
         vSSNName.value = vSSNName.value.substr(0, (vSSNValue.length-1));
         return false;
      } 
     } // end alphaCheck    
   if (whichCode == 8) //Ignore the Netscape value for backspace. IE has no value
      return false;
   else 
   {
      //Create numeric string values for 0123456789/
      //The codes provided include both keyboard and keypad values     
      var strCheck = '47,48,49,50,51,52,53,54,55,56,57,58,59,95,96,97,98,99,100,101,102,103,104,105';
      if (strCheck.indexOf(whichCode) != -1)  
      {
         if (isNav4)  
         {
            if ((mSSNValue.length < 10  && mSSNValue >= 1) && vSSNCheck)
            {
               alert('<%=ErrorUtil.getShortErrorMessage(request,"INVALID_BANK_ACCOUNTL")%>');
               vSSNName.focus();
               vSSNName.select();
               return false;
            }
			else
			{
					if (IsNumeric(vSSNValue))
					{
			            var mS1 = mSSNName.value.substr(0,3)+"-"+mSSNName.value.substr(4)+"-"+mSSNName.value.substr(5,10)+"-"+mSSNName.value.substr(10);
			            vSSNName.value = mS1;
					}
					else
					{
		               alert('<%=ErrorUtil.getShortErrorMessage(request,"INVALID_BANK_ACCOUNTL")%>');
        		       vSSNName.focus();
		               vSSNName.select();
        		       return false;
					}
			}
            return true;
         }
         else  
         { 
	             if (vSSNValue.length == 3)  
                  {
                     vSSNName.value = vSSNValue+"-";
                  }
                  if(vSSNValue.length == 5){
                     vSSNName.value = vSSNValue+"-";
                  }
                  if (vSSNValue.length == 11)  
                  {
                     vSSNName.value = vSSNValue+"-";
                  }
         }
         return false;
      }
      else  
      {
         // If the value is not in the string return the string minus the last
         // key entered.
         if (isNav4)
         {
            vSSNName.value = "";
            vSSNName.focus();
            vSSNName.select();
            return false;
         }
         else
         {
            vSSNName.value = vSSNName.value.substr(0, (vSSNValue.length-1));
            return false;
         }
		}
	}   
}
function	setZipCode(field1,field2,field3,zip,pro,dis){
	var field1 = eval("document.appFormName."+field1);
	var field2 = eval("document.appFormName."+field2);
	var field3 = eval("document.appFormName."+field3);
	field1.value = zip;
	field2.value = pro;
	field3.value = dis;
	field3.focus();
}
function getBankBranch1(windowOpen,field,bkaccno,bkcode,bankId){
   var str = eval("document.appFormName."+bankId);
   var strbkaccno=eval("document.appFormName."+bkaccno);
   if(strbkaccno.value!=''){
     if(bkcode!=""){
       bkacc = trimString(replaceSubstring(strbkaccno.value,"-",""));
       if(bkacc.length<10&&bkacc.length>0){
             strbkaccno.focus();
             strbkaccno.select();
        }else{
              var bkCode = trimString(bkcode) ;     
              window.open("/NaosMortgageWeb/servlet/com.ge.naos.mortgage.appform.popup.servlet.ViewBankBranch?action=viewBranchCode&bkCode="+bkCode+"&branchCode="+bkacc.substr(0,3)+"&fieldno="+field,"mywindow",'width=1,height=1,left=1050',status=1,toolbar=1);
        }
    }else{
     str.focus();
	 str.select()
   }
  }
}

function getCreditCardType(cardNo){
	 if(cardNo.length==19){
     	window.open("/NaosMortgageWeb/servlet/com.ge.naos.mortgage.appform.popup.servlet.ViewCreditCardType?cardNo="+cardNo,"mywindow",'width=1,height=1,left=1050');   
     }else if(cardNo.length>=1 && cardNo.length<19){
     	alert('<%=ErrorUtil.getShortErrorMessage(request,"INVALID_CREDIT_CARD")%>');
     	resetCardType();
     }
}
function checkCitizenIDDigit(dgt){
        var sum = 0;
        var sum1 = 0;
        var count = 13;  
        for (i = 0; i < 12; i++)
        {
            sum = sum + (parseInt(dgt.charAt(i) + "") * count);
            count--;
        }
        sum1 = Math.floor((sum / 11)) * 11;
        var compare1 = (11 - (sum - sum1)) % 10;
        var compare2 = parseInt(dgt.charAt(12) + "");
        if (((11 - (sum - sum1)) % 10) == parseInt(dgt.charAt(12) + "")){
            return true;
        }
        else{
            return false;
        }
}
function removeRowFromTable(tableName,row)
{	
	var tbl = document.getElementById(tableName);	
	//tbl.deleteRow(row);
	var row = tbl.getElementsByTagName("TR")[row];
	row.style.display="none";
}
function getRowIDFromTable(tableName,rowID)
{	
	var tbl = document.getElementById(tableName);
	var rowCount = 0;
	for(i = 1; i < tbl.rows.length; i++){
		var row = tbl.getElementsByTagName("TR")[i];
		if(row.style.display != 'none'){
			rowCount = rowCount+1;
			if(rowCount==rowID){
				return i;
			}
		}
	}
}
function hexnib(d) {
   if(d<10) 
	   return d; 
   else 
	   return String.fromCharCode(65+d-10);
}
function hexbyte(d) {		
		return "%"+hexnib((d&240)>>4)+""+hexnib(d&15);
}
function encode(url) {
     var result="";
    var hex="";
     for(var i=0;i<url.length; i++) {
             var cc=url.charCodeAt(i);
				 if (cc<128) {
					 result+=hexbyte(cc);
				 } else{
					result+=url.charAt(i);
				 }
	 }
    return result;
}	
function  checkMortgageDate(field,mandate){
	var obj=eval("document.appFormName."+field);
	if(mandate&&obj.value==''){
			alert('<%=ErrorUtil.getShortErrorMessage(request,"INVALID_DATE")%>');
			obj.value= "";	
			obj.focus();
			obj.select();
			return false ;
	}
	if(!validateUSDate(obj.value) && obj.value != '' ){
		obj.focus();
		obj.select();
	}else{
		var strValue = obj.value ;
		var arrayDate = strValue.split('/');
		var intYear = parseInt(arrayDate[2]);
		//alert(intYear);
		var Max = intYear + 65 ;
		var Min = intYear + 20 ;
		var currentDate = new Date();
		var cYear  = currentDate.getFullYear();
		
		var cMonth = currentDate.getMonth() + 1;	
		var cDate = currentDate.getDate();
			
		var yearDiff = 0;
		var intMonth = arrayDate[1];
		var intDate = arrayDate[0];
		
		var cTime = currentDate.getTime();
		var inputDate = new Date(intYear-543,intMonth-1,intDate);
		var inputTime = inputDate.getTime();
				
		if(cMonth < intMonth || (cMonth == intMonth && cDate < intDate)){
			yearDiff = 1;
		}
		
		cYear = cYear + 543 ;
		 if(intYear <= 2400) {
	    	alert('??? ????? ?? ????????????????????????????? (?????????? ?.?.)');
	    	obj.focus();
			obj.select();
			return false;
	    }else if(inputTime >= cTime || (cDate==intDate && cMonth == intMonth && cMonth == intMonth && cYear==intYear )){						
			alert('?????????????????????????????????????');
			obj.focus();
			obj.select();
			return false;
		}
	}
}
	function checkInsuranceExpDate(field){
		var obj=eval("document.appFormName."+field);
		
		if(!citizenErrorFlag){		
			if(!validateUSDate(obj.value) && obj.value != '' ){
			obj.focus();
			obj.select();
			}else{
				var strValue = obj.value ;
				var arrayDate = strValue.split('/');
				var intYear = parseInt(arrayDate[2],10);
				//alert(intYear);
				var intMonth = parseInt(arrayDate[1],10);
				var intDay = parseInt(arrayDate[0],10);	
				var inputDate = new Date(intYear-543,intMonth-1,intDay);
				
				var inputTime = inputDate.getTime();
				var currentDate = new Date();
				var cYear  = currentDate.getFullYear();
				var cMonth = currentDate.getMonth()+1;
				var cDay = currentDate.getDate();
				var cTime = currentDate.getTime();
				
				cYear = cYear + 543 ;
				if(inputTime < cTime){
					alert('???????????????????????????????? ? ??????????????');
					obj.focus();
					obj.select();
				} 
			}
		}
	}
	
	function viewZipCode(fieldZipcode,fieldProvince,fieldDistrict){
		var districtfield = eval("document.appFormName."+fieldDistrict);
		var provincefield = eval("document.appFormName."+fieldProvince);
		var zipfield = eval("document.appFormName."+fieldZipcode);	
		var zip = trimString(zipfield.value) ; 
		if ( (zipfield.value != "") && (zipfield.value.length < 5) ){
		return false;
		}	
		var number1 = removeCommas(zip);
		if (isNaN(number1)){
			return false;
		}
	    window.open("/NaosMortgageWeb/servlet/com.ge.naos.mortgage.appform.popup.servlet.ViewZipCode?zipCode="+zip+"&fieldZipcode="+fieldZipcode+"&fieldProvince="+fieldProvince+"&fieldDistrict="+fieldDistrict,"mywindow",'width=1,height=1,left=2000',status=1,toolbar=1);  
  }
  	function checkLegthString(field,condition,size,alertMessage){
		var str = eval("document.appFormName."+field);
		var ch= true;
		if(condition == 0){
			if(str.value.length != size){
				alert(alertMessage);
				str.focus();
				str.select();
				return false;
			}
		}else if(condition == 1){
			if(tmpField.value.length <= size){
				alert(alertMessage);
				str.focus();
				str.select();
				return false;
			}
		}else if(condition == 2){
			if(str.value.length < size){
				alert(alertMessage);
				str.focus();
				str.select();
				return false;
			}
		}else if(condition == 3){
			if(str.value.length >= size){
				alert(alertMessage);
				str.focus();
				str.select();
				return false;
			}
		}
		return true;
	}
	function checkTaxID(field,condition,size,alertMessage){
		var str = eval("document.appFormName."+field);
		if(str.value.length > 0){
			if(!checkLegthString(field,condition,size,alertMessage)){
				str.focus();
				str.select();
				return false;
			}
		}
		return true;
	}
	function lkuNationality(nationID, busID, field,effectField){
		lookupNationality(nationID, busID, field);
		var tmpField = eval("document.appFormName."+effectField);
		if(nationID == 'TH'){
			tmpField.value='324001';
			tmpField.disabled='disabled';
		}else{
			tmpField.value='';
			tmpField.disabled=null;
		}
	}
	function checkEngCitizenID(field){
	var str = eval("document.appFormName."+field);
	var reg1=/(^[a-zA-Z\s\.\-0-9]*$)/ ;
		if(!reg1.test(str.value) && str.value != ''){	
			 alert('???????????? ???????????????????');
	         str.focus();
	         str.select();
		}else{
			citizenErrorFlag = false;
		}
	}
	function  checkReceiveDate(field,mandate,seq){
	var obj=eval("document.appFormName."+field);
	if(mandate&&obj.value==''){
			alert('<%=ErrorUtil.getShortErrorMessage(request,"INVALID_DATE")%>');
			obj.value= "";	
			obj.focus();
			obj.select();
			return false ;
	}
	if(!validateUSDate(obj.value) && obj.value != '' ){
		obj.focus();
		obj.select();
	}else{
		var strValue = obj.value ;
		var arrayDate = strValue.split('/');
		var intYear = parseInt(arrayDate[2]);
		var Max = intYear + 65 ;
		var Min = intYear + 20 ;
		var currentDate = new Date();
		var cYear  = currentDate.getFullYear();
		
		var cMonth = currentDate.getMonth() + 1;	
		var cDate = currentDate.getDate();
			
		var yearDiff = 0;
		var intMonth = arrayDate[1];
		var intDate = arrayDate[0];
		
		var cTime = currentDate.getTime();
		var inputDate = new Date(intYear-543,intMonth-1,intDate);
		var inputTime = inputDate.getTime();
				
		if(cMonth < intMonth || (cMonth == intMonth && cDate < intDate)){
			yearDiff = 1;
		}
		cYear = cYear + 543 ;
		 if(intYear <= 2400) {
	    	alert('??? ????? ?? ????????????????????????????? (?????????? ?.?.)');
	    	obj.focus();
			obj.select();
			return false;
	    }else if(inputTime >= cTime || (cDate<intDate && cMonth == intMonth && cMonth == intMonth && cYear==intYear )){	
	    	if(seq==1){					
				alert('???????????????????????????');
			}else if(seq==2){					
				alert('??????????????????????????');
			}
			obj.focus();
			obj.select();
			return false;
		}
	}
}
function gotoNextTab(field){
	var field = eval("document.appFormName."+field);
	field.focus();
}

function checkInteger(field){
		var number = eval("document.appFormName."+field);
		var number1 = removeCommas(number.value);
		if (isNaN(number1)){
			alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_KEY_NUMBER_ONLY")%>');
			number.focus();
		}else{		
			if(!validateInteger(number1)){				
				//if (number1<0){
				alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_KEY_INTEGER")%>');
				number.focus();
				number.select();
			}
		}
}

function checkBankOfficerName(field,mandate){
var str = eval("document.appFormName."+field);
var reg1=/(^[A-Z,a-z,?-?,?-?,\-,\s,0-9,\.]*$)/ ;
	if(!reg1.test(str.value) && str.value != ''){	
		 alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_VERIFY_OFFICER_NAME")%>');
         str.focus();
         str.select();
	}else{
		if(mandate && str.value==''){
			alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_VERIFY_OFFICER_NAME")%>');
			str.focus();
			str.select();
		}
	}
}

function checkSubDistrict(field,mandate){
		var subdistrictcode = eval("document.appFormName."+field);
		var subdistrictcode1 = removeCommas(subdistrictcode.value);
		if(mandate&&subdistrictcode.value==''){
			alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_KEY_SUBDISTRICT_CODE")%>');
			subdistrictcode.value= "";	
			subdistrictcode.focus();
			subdistrictcode.select();
			return false ;
		}
		if (isNaN(subdistrictcode1)){			
			alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_KEY_SUBDISTRICT_CODE")%>');
		subdistrictcode.value= "";	
		subdistrictcode.focus();
		subdistrictcode.select();
		return false ;
		}
		if ( (subdistrictcode.value != "") && (subdistrictcode.value.length < 5) ){	
			alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_KEY_SUBDISTRICT_CODE")%>');
		subdistrictcode.value= "";	
		subdistrictcode.focus();
		subdistrictcode.select();
		return false ;
		}
}
</script>