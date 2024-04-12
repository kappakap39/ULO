// Check browser version
var isNav4 = false, isNav5 = false, isIE4 = false
var strSeperator = "-"; 
// If you are using any Java validation on the back side you will want to use the / because 
// Java date validations do not recognize the dash as a valid date separator.
var err = 0; // Set the error code to a default of zero
if(navigator.appName == "Netscape"){
   if (navigator.appVersion < "5"){
      isNav4 = true;
      isNav5 = false;
	}else if (navigator.appVersion > "4"){
      isNav4 = false;   isNav5 = true;
	}
}else{  isIE4 = true; }
function trimString(mThis){
    //Get rid of leading spaces
    while(''+mThis.charAt(0)==' ')mThis=mThis.substring(1,mThis.length);    
    //Get rid of trailing spaces
    while(''+mThis.charAt(mThis.length-1)==' ') mThis=mThis.substring(0,mThis.length-1);    
    return mThis;
}
function trim(mThis){
    //Get rid of leading spaces
    while(''+mThis.value.charAt(0)==' ')mThis.value=mThis.value.substring(1,mThis.value.length);    
    //Get rid of trailing spaces
    while(''+mThis.value.charAt(mThis.value.length-1)==' ')mThis.value=mThis.value.substring(0,mThis.value.length-1);    
    return mThis.value;
}