$('docuemnt').ready(function(){
	try{
		$('[name="REGISTRATION_DATE"]').attr('onblur',$('[name="REGISTRATION_DATE"]').attr('onblur')+";isMoreThanCurrenDate();");
	}catch(exception){
		errorException(exception);
	}
});


function isMoreThanCurrenDate(){
	try{
		if(!isValidDate($('[name="REGISTRATION_DATE"]').val())){
			alertBox(ERROE_DATE_MORE_THAN_CURRENT_DATE,emptyFocusElementActionJS,"REGISTRATION_DATE",DEFAULT_ALERT_BOX_WIDTH);
			
		}
	}catch(exception){
		errorException(exception);
	}
}

function isValidDate(dateVal) {
	try{
		var currentTime = new Date();
		var nowDate =currentTime.getDate();
		if(nowDate<10){
			nowDate="0"+nowDate;
			}
		var nowMonth =currentTime.getMonth()+1;
		if(nowMonth<10){
			nowMonth ="0"+nowMonth;
		}
		var nowYear = currentTime.getFullYear();
		if (dateVal != '')  {
			var toSprit = dateVal.split('/');		
			var inputInt = parseInt(toSprit [2]-543 + toSprit [1] + toSprit [0]); 
			var currentInt = parseInt(nowYear.toString() + nowMonth.toString() + nowDate.toString()); 
			if (inputInt > currentInt) {							
				return false;
			}
		}		
	}catch(exception){
		errorException(exception);
	}
	return true;
}

function REGISTRATION_NOInitJS(elementName,mode,displayName){
	try{
		$('[name="'+elementName+'"]').attr('onkeypress'
				,'NUMBEROnKeyPressAction("'+elementName+'","'+mode+'","'+displayName+'",event)');
	}catch(exception){
		errorException(exception);
	}
}
function REQUEST_NOInitJS(elementName,mode,displayName){
	try{
		$('[name="'+elementName+'"]').attr('onkeypress'
			,'NUMBEROnKeyPressAction("'+elementName+'","'+mode+'","'+displayName+'",event)');
	}catch(exception){
		errorException(exception);
	}	
}