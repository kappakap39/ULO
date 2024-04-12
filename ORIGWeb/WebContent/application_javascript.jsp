<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.eaf.j2ee.pattern.util.ErrorUtil" %>
<%@ page import="com.eaf.orig.shared.constant.OrigConstant" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGDisplayFormatUtil" %>
<%@ page import="com.eaf.orig.shared.utility.ORIGUtility" %>
<jsp:useBean id="ORIGForm" scope="session" class="com.eaf.orig.formcontrol.view.form.ORIGFormHandler"/>
<%
	String defaultDate = ORIGUtility.displayEngToThaiDate(ORIGDisplayFormatUtil.parseDate(ORIGForm.getAppForm().getNullDate()));
%>
<SCRIPT language=JavaScript type=text/JavaScript>
var citizenErrorFlag = false;

function  checkBirthDate(field,field3){
	var obj=eval("document.appFormName."+field);
	var obj3=eval("document.appFormName."+field3);
	if(!validateUSDate(obj.value) && obj.value != '' ){
		obj.focus();
		obj.select();
	}else{
		var strValue = obj.value ;
		var arrayDate = strValue.split('/');
		var intYear = parseInt(arrayDate[2]);
		//alert(intYear);
		//var Max = intYear + 65 ;
		//var Min = intYear + 20 ;
		
		var Max = 65 ;
		var Min = 20 ;
		
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
			obj.value = "";
			obj.focus();
			obj.select();
			return false;
		}
		var age = cYear-intYear-yearDiff;
		
		if(strValue != ''){
			//if((strValue == '<%//=defaultDate%>' && field == 'm_birth_date')){
			//	obj3.value=(age.toString());
			//}else if((age <= Min || age >Max)){
			//	alert('<%//=ErrorUtil.getShortErrorMessage(request,"INVALID_AGE")%>');
			//	obj.focus();
			//	obj.select();
			//	return false;
			//}else{
			//	obj3.value=(age.toString());
			//}
			if((strValue == '<%=defaultDate%>' && field == 'm_birth_date')){
				obj3.value=(age.toString());
			}else if(((age <= Min || age > Max))){
				alert('<%=ErrorUtil.getShortErrorMessage(request,"INVALID_AGE")%>');
				obj.focus();
				obj.select();
				return false
			} else {
				obj3.value=(age.toString());
			}
		}else{
			obj3.value='';
		}
	}
}

function  setBirthDate(field,field3){
	var obj=eval("document.appFormName."+field);
	var obj3=eval("document.appFormName."+field3);
	if(!validateUSDate(obj.value) && obj.value != '' ){
		obj.focus();
		obj.select();
	}else{
		var strValue = obj.value ;
		var arrayDate = strValue.split('/');
		var intYear = parseInt(arrayDate[2]);
		//alert(intYear);
		//var Max = intYear + 65 ;
		//var Min = intYear + 20 ;
		
		var Max = 65 ;
		var Min = 20 ;
		
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
			obj.value = "";
			obj.focus();
			obj.select();
			return false;
		}
		var age = cYear-intYear-yearDiff;
		
		if(strValue != ''){
			obj3.value=(age.toString());			
		}else{
			obj3.value='';
		}
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

function checkDate(field){
	var obj=eval("document.appFormName."+field);
	if(!validateUSDate(obj.value) && obj.value != '' ){
	obj.value='';
	obj.focus();
	obj.select();
	}
}
function checkThaiFName(field){
var str = eval("document.appFormName."+field);
var reg1=/(^[A-Z,a-z,ก-ฮ,ะ-์,\-,\s,0-9,\.]*$)/ ; 
	if(!reg1.test(str.value) && str.value != ''){
		alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_VERIFY_THAI_FIRST_NAME")%>');
		str.focus();
		str.select();
	}
}
function checkThaiLName(field){
var str = eval("document.appFormName."+field);
var reg1=/(^[A-Z,a-z,ก-ฮ,ะ-์,\-,\s,0-9,\.]*$)/ ; 

	if(!reg1.test(str.value) && str.value != ''){
		alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_VERIFY_THAI_LAST_NAME")%>');
		str.focus();
		str.select();	
	}
}

function checkEngFName(field){
var str = eval("document.appFormName."+field);
var reg1=/(^[a-zA-Z\s\.\-\,\&0-9\(\)]*$)/ ;
	if(!reg1.test(str.value) && str.value != ''){	
		 alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_VERIFY_ENG_FIRST_NAME")%>');
         str.focus();
         str.select();
	}
}
function checkEngLName(field){
var str = eval("document.appFormName."+field);
var reg1=/(^[a-zA-Z\s\.\-\,\&0-9\(\)]*$)/ ;
	if(!reg1.test(str.value) && str.value != ''){	
		 alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_VERIFY_ENG_LAST_NAME")%>');
         str.focus();
         str.select();
	}
}
function checkNumber(field){
var number=eval("document.appFormName."+field);
var number1 = removeCommas(number.value);

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
function checkZipCode(field){
		var number = eval("document.appFormName."+field);
		var number1 = removeCommas(number.value);
		
		if (isNaN(number1)){			
			alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_KEY_ZIP_CODE")%>');
		number.value= "";	
		number.focus();
		number.select();
		return false ;
		}
		if ( (number.value != "") && (number.value.length < 5) ){	
			alert('<%=ErrorUtil.getShortErrorMessage(request,"PLEASE_KEY_ZIP_CODE")%>');
		number.value= "";	
		number.focus();
		number.select();
		return false ;
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
function checkCitizenID(field){
	var form = document.appFormName;
	var str = eval("document.appFormName."+field);
	var strObj = document.getElementById(field);
	var customerType = form.customertype.value;
	if('<%=OrigConstant.CUSTOMER_TYPE_FOREIGNER%>' != customerType){
		if(str.value != ''){
			if(!checkNumber(field)){
				//citizenErrorFlag = true;
				str.focus();
				str.select();
				return false;
			}else if(str.value.length != 13){
				alert('Invalid Identification No./Citizen ID');
				str.focus();
				str.select();
				return false;
			}else if(!checkCitizenIDDigit(str.value)){
				alert('Invalid Identification No./Citizen ID');
				str.focus();
				str.select();
				return false;
			}else{
				//citizenErrorFlag = false;
				//form.identification_no.value = form.identification_no.value;
				return true;
			}
		}else{
			
		}
	}
	//form.identification_no.value = form.identification_no.value;
	return true;
}
function checkCitizenIDDigit(dgt){
    var sum = 0;
    var sum1 = 0;
    var count = 13;  
    for (i = 0; i < 12; i++){
        sum = sum + (parseInt(dgt.charAt(i) + "") * count);
        count--;
    }
    sum1 = Math.floor((sum / 11)) * 11;
    var compare1 = (11 - (sum - sum1)) % 10;
    var compare2 = parseInt(dgt.charAt(12) + "");
    if (((11 - (sum - sum1)) % 10) == parseInt(dgt.charAt(12) + "")){
        return true;
    }else{
        return false;
    }
}
function checkCardType(cardIDType, cardID){
	var obj = eval("document.appFormName."+cardIDType);
	value = obj.value;
	if('<%=OrigConstant.CARD_TYPE_IDENTIFICATION%>' == value){
		checkCitizenID(cardID);
	}else{
		return true;
	}
}
function checkTaxID(field){
	var str = eval("document.appFormName."+field);
	if(str.value != ''){
		if(!checkNumber(field)){
			//citizenErrorFlag = true;
			str.focus();
			str.select();
			return false;
		}else if(str.value.length != 10 && str.value.length != 13){
			alert('Invalid Tax ID.');
			str.focus();
			str.select();
			return false;
		}else if(str.value.length == 10){ 
			if(!checkTaxIDDigit(str.value)){
				alert('Invalid Tax ID.');
				str.focus();
				str.select();
				return false;
			}
		}else if(str.value.length == 13){ 
			if(!checkCitizenIDDigit(str.value)){
				alert('Invalid Tax ID.');
				str.focus();
				str.select();
				return false;
			}
		}else{
			return true;
		}
	}
}
function checkTaxIDDigit(dgt){
    var sum = 0;
    var value = 0;  
    for (i = 0; i < 9; i++){
    	value = parseInt(dgt.charAt(i) + "");
    	if((i+1)%2 == 0){
    		sum = sum + (value*1);
    	}else{
    		sum = sum + (value*3);
    	}
    }
    var result = sum%10;
    result = 10 - result;
    if(result == 10){
    	result = 0;
    }
    if (result == parseInt(dgt.charAt(9) + "")){
        return true;
    }
    else{
        return false;
    }
}
function  checkCurrentDate(field){
	var obj=eval("document.appFormName."+field);
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
		if(inputTime > cTime){						
			alert('<%=ErrorUtil.getShortErrorMessage(request,"DATE_GREATER_THAN_CURRENT")%>');
			obj.value = "";
			obj.focus();
			obj.select();
			return false;
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
		if (number1 > 11 || number1<0){	
			alert('<%=ErrorUtil.getShortErrorMessage(request,"INVALID_MONTH_BETWEEN_0_12")%>');
			number.focus();
			number.select();	
		}
	}
}
 
function checkCMRworingDate(birthDate,workingYear,workingMonth,objFocus){ 
     var objBirthDate=eval("document.appFormName."+birthDate);
     var objWorkingYear=eval("document.appFormName."+workingYear);
     var objWorkingMonth=eval("document.appFormName."+workingMonth);      
     var strValue = objBirthDate.value ;
  	 var arrayDate = strValue.split('/');
 	 var intYear = parseInt(arrayDate[2]);
 	 var intMonth = arrayDate[1];
 	 var currentDate = new Date();
	 var cYear  = currentDate.getFullYear()+543;
   	 var cMonth = currentDate.getMonth()+1;
   	 var cDate=currentDate.getDate();
   	 var intDate=arrayDate[0];
   	 var ageYear=cYear-intYear;
 	 var ageMonth=cMonth-intMonth;
   	 //if(cMonth < intMonth || (cMonth == intMonth && cDate < intDate)){
 	 if(cMonth < intMonth  ){
			ageYear = ageYear-1;
			ageMonth=12+cMonth-intMonth;
		}   	 

   	 var tAgeYear=ageYear;
   	 var tWorkingYear=Number(objWorkingYear.value);
   	 var tWorkingMonth=Number(objWorkingMonth.value);
   	 // if(tWorkingMonth>0){
   	 // tWorkingYear=Number(tWorkingYear)+1;
   	// }   	    	  
     if(  Number(tWorkingYear)>Number(tAgeYear)||((Number(tWorkingYear)==Number(tAgeYear))&&(tWorkingMonth>ageMonth)) ){
        alert("Total Working (PreScore) must not greater than Age ("+ageYear+" Year "+ageMonth+" Month)"); 
        objFocus.value='';
        objFocus.focus();  
     }
 
}
function checkCMRAddressReside(birthDate,resideYear,resideMonth,objFocus){ 
     var objBirthDate=eval("document.appFormName."+birthDate);
     var objResideYear=eval("document.appFormName."+resideYear);
     var objResideMonth=eval("document.appFormName."+resideMonth);      
     var strValue = objBirthDate.value ;
  	 var arrayDate = strValue.split('/');
 	 var intYear = parseInt(arrayDate[2]);
 	 var intMonth = arrayDate[1];
 	 var currentDate = new Date();
	 var cYear  = currentDate.getFullYear()+543;
   	 var cMonth = currentDate.getMonth()+1;   	  
  	 var cDate=currentDate.getDate();
   	 var intDate=arrayDate[0];
   	 var ageYear=cYear-intYear;
	 var ageMonth=cMonth-intMonth;
   	 if(cMonth < intMonth ){
			ageYear = ageYear-1;
		}    
	 if(cMonth < intMonth ){
		ageYear = ageYear-1;
		ageMonth=12+cMonth-intMonth;
	  }   		 

   	// if(cMonth<intMonth){
   	//  agerYear=ageYear-1;
   	// }
   	 var tAgeYear=ageYear;
   	 var tWorkingYear=Number(objResideYear.value);
   	 var tWorkingMonth=Number(objResideMonth.value);
   	 // if(tWorkingMonth>0){
   	 // tWorkingYear=Number(tWorkingYear)+1;
   	// }   	    	  
     if(  Number(tWorkingYear)>Number(tAgeYear) ||((Number(tWorkingYear)==Number(tAgeYear))&&(tWorkingMonth>ageMonth))){
        alert("Time in Current Address (PreScore) must not greater than Age ("+ageYear+" Year "+ageMonth+" Month)"); 
        objFocus.value='';
        objFocus.focus();  
     }
 
}
function checkWoringDate(birthDate,workingYear,workingMonth,objFocus){ 
     var objBirthDate=eval("document.appFormName."+birthDate);
     var objWorkingYear=eval("document.appFormName."+workingYear);
     var objWorkingMonth=eval("document.appFormName."+workingMonth);      
     var strValue = objBirthDate.value ;
  	 var arrayDate = strValue.split('/');
 	 var intYear = parseInt(arrayDate[2]);
 	 var intMonth = arrayDate[1];
 	 var currentDate = new Date();
	 var cYear  = currentDate.getFullYear()+543;
   	 var cMonth = currentDate.getMonth()+1;
     var cDate=currentDate.getDate();
   	 var intDate=arrayDate[0];
   	 var ageYear=cYear-intYear;
  	 var ageMonth=cMonth-intMonth;
   	// if(cMonth < intMonth || (cMonth == intMonth && cDate < intDate)){
   	 if(cMonth < intMonth ){
			ageYear = ageYear-1;
			ageMonth=12+cMonth-intMonth;
		}   
   	if (objWorkingYear!=undefined && objWorkingMonth!=undefined){
	   	 var tAgeYear=ageYear;
	   	 var tWorkingYear=Number(objWorkingYear.value);
	   	 var tWorkingMonth=Number(objWorkingMonth.value);
	   	//  if(tWorkingMonth>0){
	   	//  tWorkingYear=Number(tWorkingYear)+1;
	   	// }   	    	  
	     if(  Number(tWorkingYear)>Number(tAgeYear) ||((Number(tWorkingYear)==Number(tAgeYear))&&(tWorkingMonth>ageMonth))){
	        alert("Total Working must not greater than Age ("+ageYear+" Year "+ageMonth+" Month)"); 
	        objFocus.value='';
	        objFocus.focus();  
	     }
    }
 
}
</script>