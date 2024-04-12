// Check browser version
var isNav4 = false, isNav5 = false, isIE4 = false
var strSeperator = "-"; 
// If you are using any Java validation on the back side you will want to use the / because 
// Java date validations do not recognize the dash as a valid date separator.
var err = 0; // Set the error code to a default of zero
if(navigator.appName == "Netscape"){
   if (navigator.appVersion < "5"){
      isNav4 = true;      isNav5 = false;
	}else if (navigator.appVersion > "4"){
      isNav4 = false;    isNav5 = true;
	}
}else{
   isIE4 = true;
}
function creditFormat(vSSNName,vSSNValue, e, vSSNCheck){
mSSNName = vSSNName;
mSSNValue = vSSNValue;
// vSSNName = object name// vSSNValue = value in the field being checked// vSSNCheck = Perform validation as they leave the field
// e = event
   var whichCode = (window.Event) ? e.which : e.keyCode;
	// Remove any dashes that may be in the string being passed.
 	mSSNValue = trimString(replaceSubstring(vSSNValue,"-",""));
	if (mSSNValue.length >= 1){
		if (mSSNValue.length == 9 && vSSNCheck){
			// Parse the SSN to numeric values
			var mSSNArea = mSSNValue.substr(0,3);
			var mSSNGroup = mSSNValue.substr(3,2);
			var mSSNSerial = mSSNValue.substr(5,4);			
			// Set the default type of SSN to invalid
			var mSSNType = "INVALID";
			mSSNType = "Regular";
			// Determine type of SSN	
		}
	}
	if (mSSNValue.length >= 10 && vSSNCheck)	{
		alert("Too Many Numbers For SSN\nPlease Re-Enter");
		vSSNName.value = "";		vSSNName.focus();		vSSNName.select();
		return false;
	}
	// If there is a dash in the original and the length of the trimmed string is 9 and the user is using NS. Return true
   if ((vSSNValue.indexOf("-") >= 1) && vSSNValue.length == 11 && mSSNValue.length == 9 && isNav4){
         return true;
   }   
   //Eliminate all the ASCII codes that are not valid
   var alphaCheck = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ/-";
   if (alphaCheck.indexOf(vSSNValue) >= 1){   		
      if (isNav4){
         vSSNName.value = "";
         vSSNName.focus();
         vSSNName.select();
         return false;
      }else{
         vSSNName.value = vSSNName.value.substr(0, (vSSNValue.length-1));
         return false;
      } 
   }
   if (whichCode == 8) //Ignore the Netscape value for backspace. IE has no value
      return false;
   else{
      //Create numeric string values for 0123456789/
      //The codes provided include both keyboard and keypad values      
      var strCheck = '47,48,49,50,51,52,53,54,55,56,57,58,59,95,96,97,98,99,100,101,102,103,104,105';
      if (strCheck.indexOf(whichCode) != -1){
         if (isNav4){
            if ((mSSNValue.length < 9  && mSSNValue >= 1) && vSSNCheck){
               alert("Invalid SSN\nPlease Re-Enter");
               vSSNName.focus();
               vSSNName.select();
               return false;
            }else{
				if (vSSNCheck){
					if (IsNumeric(vSSNValue)){
			            var mS1 = mSSNName.value.substr(0,3)+strSeperator+mSSNName.value.substr(3,2)+strSeperator+mSSNName.value.substr(5,4);
			            vSSNName.value = mS1;
					}else{
		               alert("Invalid SSN\nPlease Re-Enter");
        		       vSSNName.focus();               vSSNName.select();
        		       return false;
					}
				}
			}
            return true;
         }else{
         // Non isNav Check
            if ((vSSNValue.length <= 10 && vSSNValue.length >=1) && vSSNCheck){
               alert("Invalid SSN\nPlease Re-Enter");
               vSSNName.value = "";          vSSNName.focus();
               return true;
            }            
            if (vSSNValue.length >14){
    	        return true;
            }else{               
	             if (vSSNValue.length == 4){
                     vSSNName.value = vSSNValue+strSeperator;
                  }
                  if (vSSNValue.length == 9){
                     vSSNName.value = vSSNValue+strSeperator;
                  }
				  if (vSSNValue.length == 14){
                     vSSNName.value = vSSNValue+strSeperator;
                  }
               return true;
            }
         }
         return false;
      }else{
         // If the value is not in the string return the string minus the last
         // key entered.
         if (isNav4){
            vSSNName.value = "";         vSSNName.focus();         vSSNName.select();
            return false;
         }else{
            vSSNName.value = vSSNName.value.substr(0, (vSSNValue.length-1));
            return false;
         }
		}
	}
}