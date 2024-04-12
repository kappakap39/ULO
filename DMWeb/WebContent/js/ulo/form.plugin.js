var DEFAULT_ALERT_BOX_WIDTH = '600px';
(function($){
    $.fn.setCursorToTextEnd = function() {
        $initialVal = this.val();
        this.val($initialVal+' ');
        this.val($.trim($initialVal));
    };
})(jQuery);
(function($) {
	 $.datepicker.regional['th'] ={
		changeMonth: true,
		changeYear: true,
		buttonImageOnly: true,
//		buttonImage: 'images/calendar.png',
		dateFormat: 'dd/mm/yy',
		dayNames: ['\u0E2D\u0E32\u0E17\u0E34\u0E15\u0E22\u0E4C','\u0E08\u0E31\u0E19\u0E17\u0E23\u0E4C','\u0E2D\u0E31\u0E07\u0E04\u0E32\u0E23'
		           ,'\u0E1E\u0E38\u0E18','\u0E1E\u0E24\u0E2B\u0E31\u0E2A\u0E1A\u0E14\u0E35','\u0E28\u0E38\u0E01\u0E23\u0E4C','\u0E40\u0E2A\u0E32\u0E23\u0E4C'],
		dayNamesMin: ['\u0E2D\u0E32','\u0E08','\u0E2D','\u0E1E','\u0E1E\u0E24','\u0E28','\u0E2A'],
		monthNames: ['\u0E21\u0E01\u0E23\u0E32\u0E04\u0E21','\u0E01\u0E38\u0E21\u0E20\u0E32\u0E1E\u0E31\u0E19\u0E18\u0E4C','\u0E21\u0E35\u0E19\u0E32\u0E04\u0E21'
		             ,'\u0E40\u0E21\u0E29\u0E32\u0E22\u0E19','\u0E1E\u0E24\u0E29\u0E20\u0E32\u0E04\u0E21','\u0E21\u0E34\u0E16\u0E38\u0E19\u0E32\u0E22\u0E19'
		             ,'\u0E01\u0E23\u0E01\u0E0E\u0E32\u0E04\u0E21','\u0E2A\u0E34\u0E07\u0E2B\u0E32\u0E04\u0E21','\u0E01\u0E31\u0E19\u0E22\u0E32\u0E22\u0E19'
		             ,'\u0E15\u0E38\u0E25\u0E32\u0E04\u0E21','\u0E1E\u0E24\u0E28\u0E08\u0E34\u0E01\u0E32\u0E22\u0E19','\u0E18\u0E31\u0E19\u0E27\u0E32\u0E04\u0E21'],
		monthNamesShort: ['\u0E21\u002E\u0E04\u002E','\u0E01\u002E\u0E1E\u002E','\u0E21\u0E35\u002E\u0E04\u002E','\u0E40\u0E21\u002E\u0E22\u002E'
		                  ,'\u0E1E\u002E\u0E04\u002E','\u0E21\u0E34\u002E\u0E22\u002E','\u0E01\u002E\u0E04\u002E','\u0E2A\u002E\u0E04\u002E'
		                  ,'\u0E01\u002E\u0E22\u002E','\u0E15\u002E\u0E04\u002E','\u0E1E\u002E\u0E22\u002E','\u0E18\u002E\u0E04\u002E'],
		constrainInput: true,
		yearRange: '-100:+100',
		buttonText: '\u0E40\u0E25\u0E37\u0E2D\u0E01',
		yearOffSet: 543,
		showButtonPanel: true,
		prevText: '&laquo;&nbsp;\u0E22\u0E49\u0E2D\u0E19', prevStatus: '',
		prevJumpText: '&#x3c;&#x3c;', prevJumpStatus: '',
		nextText: '\u0E16\u0E31\u0E14\u0E44\u0E1B&nbsp;&raquo;', nextStatus: '',
		nextJumpText: '&#x3e;&#x3e;', nextJumpStatus: '',
		currentText: '\u0E27\u0E31\u0E19\u0E19\u0E35\u0E49', currentStatus: '',
		todayText: '\u0E27\u0E31\u0E19\u0E19\u0E35\u0E49', todayStatus: '',
		clearText: '-', clearStatus: '',
		closeText: '\u0E1B\u0E34\u0E14', closeStatus: '',
		yearStatus: '', monthStatus: '',
		weekText: 'Wk', weekStatus: '',
		//showOn: "button",
        //buttonImage: 'images/ulo/calendar.png',
		beforeShow: function(input, inst) {
			inst.dpDiv.css({'font-size':'12px'});
		},
		onSelect: function(dateText, inst) {
			$(this).focus();
		}
	};
	$.datepicker.regional['en'] = {
		changeMonth: true,
		changeYear: true,
		buttonImageOnly: true,
		dateFormat: 'dd/mm/yy',
		constrainInput: true,
		yearRange: '-100:+100',
		yearOffSet:0,
		showButtonPanel: true,
		//showOn: "button",
        //buttonImage: 'images/ulo/calendar.png',
        beforeShow: function(input, inst) {
			inst.dpDiv.css({'font-size':'12px'});
		},
		onSelect: function(dateText, inst) {
			$(this).focus();
		}
	};
})(jQuery);

function CALENDAROnblurAction(element,mode,name){
	if(mode == MODE_VIEW)return;
	var callBackAction = false;
	var errorFormat = false;
	var elementValue = $.trim($("[name='"+element+"']").val());
	if(elementValue == undefined){
		elementValue = '';
	}
	if(elementValue == null || elementValue == ''){
		callBackAction = true;
	}else{
		if(elementValue.length == 10){
			var objRegExp = /^\d{1,2}(\-|\/|\.)\d{1,2}\1\d{4}$/;
			if(!objRegExp.test(elementValue)){	
				errorFormat = true;
			}
			if(!errorFormat){
				var mDay =  parseInt(elementValue.substr(0,2),10);
				var mMonth =  parseInt(elementValue.substr(3,2),10);
				var mYear = parseInt(elementValue.substr(6,4));
				var yearValue = parseInt(elementValue.substr(6,4));
				var regional = $("[name='"+element+"']").attr('regional');
				if(regional == 'th'){
					mYear = mYear-543;
				}
				if(regional == 'th' && yearValue < 2400 && yearValue.length!=0){
					errorFormat = true;
				}else if(regional == 'en' && yearValue < 1900 && yearValue.length!=0){
					errorFormat = true;
				}
				if(!errorFormat){
					var allDays = [31,28,31,30,31,30,31,31,30,31,30,31];			
					var isLeapYear = (mYear % 4 == 0 && (mYear % 100 != 0 || mYear % 400 == 0));
						allDays[1] = (isLeapYear)? 29 : 28;	
					if (mMonth >= 1 && mMonth <= 12 && mDay <= allDays[mMonth-1] && mDay > 0){
						callBackAction = true;
					}else{
						errorFormat = true;
					}	
				}
			}
		}else{
			errorFormat = true;				
		}
	}
	if(callBackAction && !errorFormat){
		try{
			var ActionJS = eval(name+'ActionJS');
				ActionJS(element,mode,name);
		}catch(e){}
	}else if(errorFormat && !isEmpty(elementValue)){
		alertBox('ERROR_DATE_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
	}
}
function ERROR_DATE_FORMATAfterAlertActionJS(){
	
}
function emptyFocusElementActionJS(elementId){
	var	elementObject = $("[name='"+elementId+"']");
	var	elementProperty = (elementObject != undefined)?elementObject.attr('property'):'';
	displayHtmlElement(elementId,'',elementObject,elementProperty);
	try{
		switch(elementProperty){
			case "dropdown":break;
			default:elementObject.focus(); break;
		}
	}catch(e){}
}
function focusElementActionJS(elementId,elementValue){
	var	elementObject = $("[name='"+elementId+"']");
	var	elementProperty = (elementObject != undefined)?elementObject.attr('property'):'';
	displayHtmlElement(elementId,elementValue,elementObject,elementProperty);
	try{
		switch(elementProperty){
			case "dropdown":elementObject[0].selectize.focus();break;
			default:elementObject.focus();break;
		}
	}catch(e){}
}
function CALENDAROnKeyPressAction(element,mode,name,event){
	if(mode == MODE_VIEW)return;
	if(event.keyCode == 8 ||event.keyCode == 46){
		 return;
	}
	var elementObject = $("[name='"+element+"']");
	var value = String.fromCharCode(event.keyCode);
	if(value.match("[0-9]")||event.keyCode == 47){		
		if(elementObject.val().length == 2 || elementObject.val().length == 5){	
			event.preventDefault();
			elementObject.val((event.keyCode == 47)?elementObject.val()+'/': elementObject.val()+'/'+value);			
			elementObject.focus();
			return;
		}
		if(event.keyCode == 47 && elementObject.val().length == 1){
		   event.preventDefault();			   
		   elementObject.val('0'+elementObject.val()+'/');
		   elementObject.focus();
		   return;
		}
		if(event.keyCode == 47 && elementObject.val().length == 4){
		   event.preventDefault();			   
		   elementObject.val(elementObject.val().substr(0,3)+'0'+elementObject.val().substr(3,1)+'/');
		   elementObject.focus();
		   return;
		}
		if(event.keyCode == 47){
			event.preventDefault();
			elementObject.focus();
			return;
		}
		var $dateVal = elementObject.val()+value;
		if($dateVal.length > 9) return;
		var mDay = $dateVal.substr(0,2);
		var mMonth = $dateVal.substr(3,2);		
		var mSplit1 = $dateVal.substr(2,1);
		var mSplit2 = $dateVal.substr(5,1);			
		if(mSplit1 != null && mSplit1 != '' && mSplit1 != '/'){
			event.preventDefault();	
			elementObject.val('').focus();
			return;
		}			
		if(mSplit2 != null && mSplit2 != '' && mSplit2 != '/'){
			event.preventDefault();	
			elementObject.val('').focus();
			return;
		}
		if(mDay.length == 2 && (mDay == 0 || mDay > 31)){
			event.preventDefault();	
			elementObject.val('').focus();
			return;
		}
		if((mDay.length == 2 && mMonth.length == 2 && mMonth == 0)||mMonth > 12){
			event.preventDefault();	
			elementObject.val(mDay+'/');		
			elementObject.focus();
			return;
		}
	}else{
		event.preventDefault();
	}
}

function THOnblurAction(element,mode,name){
	if(mode == MODE_VIEW)return;
	var callBackAction = true;
	var errorFormat = false;
	var elementValue = $("[name='"+element+"']").val();
	if (elementValue == '' || elementValue.length == 1){
		var fistObjRegExp = /^[\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44]/;
		if(!fistObjRegExp.test(elementValue)){
			errorFormat = true;
			callBackAction = false;
		}		
	}else{
		var objRegExp  = /^([\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44])+([\u0E01-\u0E4F\u0E46\u002C\u002E\u002D\u005F\u002F\u0028\u0029\u0025\u002A\u0040\u0024\u0023\u0026\u002B\s])+$/;
		if(!objRegExp.test(elementValue)){
			errorFormat = true;
			callBackAction = false;
		}
	}
	if(callBackAction && !errorFormat){
		try{
			var ActionJS = eval(name+'ActionJS');
				ActionJS(element,mode,name);
		}catch(e){}
	}else if(errorFormat && !isEmpty(elementValue)){
		alertBox('ERROR_THAI_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
	}
}
function THOnKeyPressAction(element,mode,name,e){
	if(mode == MODE_VIEW)return;
	var strKeyCode = String.fromCharCode(e.keyCode);
	var elementValue = $("[name='"+element+"']").val();
	if (elementValue == '' || elementValue.length == 0){
		var fistObjRegExp = /^[\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44]/;
		if (!fistObjRegExp.test(strKeyCode)) {
			e.preventDefault();
			return false;
		}else{
			return true;
		}

	}else{
		var objRegExp = /^([\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44])+([\u0E01-\u0E4F\u0E46\u002C\u002E\u002D\u005F\u002F\u0028\u0029\u0025\u002A\u0040\u0024\u0023\u0026\u002B\s])+$/;
		if (!objRegExp.test(elementValue+strKeyCode)) {
			e.preventDefault();
			return false;
		}else{
			return true;
		}
	}
}

function ENOnblurAction(element,mode,name){
	if(mode == MODE_VIEW)return;
	var callBackAction = true;
	var errorFormat = false;
	var objRegExp = /(^[\w\s\d\.\-]*$)/;
	var element = $("[name='"+element+"']");
	var elementValue = element.val();
	elementValue = elementValue.toUpperCase();
	element.val(elementValue);
	
	if(!objRegExp.test(elementValue)){
		errorFormat = true;
		callBackAction = false;
	}
	if(/^[\-]$/.test(elementValue)){
		errorFormat = true;
	}
	if(callBackAction && !errorFormat){
		try{
			var ActionJS = eval(name+'ActionJS');
				ActionJS(element,mode,name);
		}catch(e){}
	}else if(errorFormat && !isEmpty(elementValue)){
		alertBox('ERROR_ENG_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
	}
}
function ENOnKeyPressAction(element,mode,name,e){
	if(mode == MODE_VIEW)return;
	var strKeyCode = String.fromCharCode(e.keyCode);
	var fistObjRegExp=/([\w\s\d\.\-])/;
	var elementValue = $("[name='"+element+"']").val();
	
	if (!fistObjRegExp.test(strKeyCode)) {
		e.preventDefault();
		return false;
	} else {
		return true;
	}

}

function CURRENCYOnblurAction(element,mode,name){
	if(mode == MODE_VIEW)return;
	var callBackAction = false;
	var errorFormat = false;
	var elementObject = $("[name='"+element+"']");
	var elementValue = $.trim(elementObject.val());
	var nullempty = elementObject.attr('nullempty');
	if(elementValue == '' && nullempty == 'Y'){
		callBackAction = true;
	}else{
		var format = elementObject.attr('format');
		if(format == '' || format == null) format = '########0.##';
		var formatArray = format.split('.');
		if(isCurrency(elementValue) || elementValue == ''){
			var newArray = elementValue.split(".");
			if(newArray[0]== undefined || newArray[0] == '') newArray[0] = '0';
			if(formatArray[1]!= undefined){
				if(newArray[1] == undefined || newArray[1] == '') newArray[1] = '00';
				if(newArray[1].length == 1) newArray[1] = newArray[1]+'0';
			}
			if(newArray[0].length > formatArray[0].length){
				errorFormat = true;
			}
			if(formatArray[1]!= undefined){
				if(newArray[1].length > formatArray[1].length){
					errorFormat = true;
				}
			}
			if(!errorFormat){
				var suffix = '';
				if(formatArray[1]!= undefined){
					suffix='.'+newArray[1];
				}			
				var number = Number(newArray[0])+'';
					number = ''+number;
				if (number.length > 3){
					var mod = number.length % 3;
					var output = (mod > 0 ? (number.substring(0,mod)) : '');
					for (var i=0; i < Math.floor(number.length / 3); i++) {
						if ((mod == 0) && (i == 0))
							output += number.substring(mod+ 3 * i, mod + 3 * i + 3);
						else
							output+= ',' + number.substring(mod + 3 * i, mod + 3 * i + 3);
					}
					elementObject.val(output.concat(suffix));
				}else{
					elementObject.val(number.concat(suffix));
				}
				callBackAction = true;
			}			
		}else{
			errorFormat = true;
		}
	}
	if(callBackAction && !errorFormat){
		try{
			var ActionJS = eval(name+'ActionJS');
				ActionJS(element,mode,name);
		}catch(e){}
	}else if(errorFormat && !isEmpty(elementValue)){
		alertBox('ERROR_CURRENCY_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
	}
}
function CURRENCYOnKeyPressAction(element,mode,name,e){
	if(mode == MODE_VIEW)return;
	var strChar = String.fromCharCode(e.keyCode);
	var elementValue = $("[name='"+element+"']").val();
	var newArray = elementValue.split(".");
	if(isCurrency(strChar) || (strChar=="." && newArray.length == 1)){
		return true;
	}else{
		e.preventDefault();
		return false;
	}
}
function CURRENCYOnKeyUpAction(element,mode,name,e){
	if(mode == MODE_VIEW)return;
	var keyVal = String.fromCharCode((96 <= e.keyCode && e.keyCode <= 105)? e.keyCode-48 : e.keyCode);
	var elementObject = $("[name='"+element+"']");
	var elementValue = elementObject.val();
	var newArray = elementValue.split(".");
	if(isCurrency(keyVal) || (keyVal=="." && newArray.length == 1)){			
		var format = elementObject.attr('format');			
		if(format == '' || format == null) format = '########0.##';
		var formatArray = format.split('.');
		var newArray1 = elementObject.val().split(".");
		if(newArray1[0] != undefined && (newArray1[0].length > formatArray[0].length)){
			if(keyVal != '.'){
				elementObject.val((newArray1[0].substring(0,formatArray[0].length)));
				return true;
			}
		}
		var newArray2 = elementObject.val().split(".");
		if(newArray2[1] != undefined && (newArray2[1].length > formatArray[1].length)){
			if(keyVal != '.'){
				elementObject.val((newArray2[0])+'.'+(newArray2[1].substring(0,formatArray[1].length)));
				return true;
			}
		}
	}
}
function CURRENCYOnFocusAction(element,mode,name){
	if(mode == MODE_VIEW)return;
	var elementObject = $("[name='"+element+"']");
	var elementValue = elementObject.val();
	var end = false;
	while(!end){
		if(elementValue.indexOf(",")<0){
			end =true;
		}else{
			elementValue = elementValue.replace(",","");
		}
	}
	elementObject.val(elementValue);
	try{
		elementObject.select(function(e){
			try{
				e.stopImmediatePropagation();
			}catch(e){}
		});
	}catch(e){}
}

function NUMBEROnblurAction(element,mode,name){
	if(mode == MODE_VIEW)return;
	var callBackAction = false;
	var errorFormat = false;
	var elementObject = $("[name='"+element+"']");	
	var elementValue = elementObject.val();
	var format = elementObject.attr('format');
	if(format == '' || format == null) format = '########0';
	if(isEmpty(elementValue)){
		var nullempty = elementObject.attr('nullempty');
		if(nullempty == 'N' && elementValue == ''){
			if(formatArray[1] != undefined){
				elementObject.val('0.'+formatArray[1]);
			}
			elementObject.val('0');
		}
		callBackAction = true;
	}else{
		if(isNumber(elementValue)){
			var formatArray = format.split('.');
			var minValue =  elementObject.attr('minvalue');
			var maxValue =  elementObject.attr('maxvalue');	
			var newArray = elementValue.split(".");	
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
				errorFormat = true;
			}
			if(formatArray[1] != undefined && newArray[1].length > formatArray[1].length){
				errorFormat = true;
			}
			if((minValue != '' && Number(elementValue) < Number(minValue))||(maxValue != '' && Number(elementValue) > Number(maxValue))){
				errorFormat = true;
			}
			if(!errorFormat){
				var numberElementValue = Number(newArray[0])+((newArray[1].length >0)?'.'+newArray[1]:'');
				elementObject.val(numberElementValue);
				callBackAction = true;
			}
		}else{
			errorFormat = true;
		}
	}	
	if(callBackAction && !errorFormat){
		try{
			var ActionJS = eval(name+'ActionJS');
				ActionJS(element,mode,name);
		}catch(e){}
	}else if(errorFormat && !isEmpty(elementValue)){
		alertBox('ERROR_NUMBER_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
	}
}
function NUMBEROnKeyPressAction(element,mode,name,e){
	if(mode == MODE_VIEW)return;
	var strChar = String.fromCharCode(e.keyCode);	
	var elementObject = $("[name='"+element+"']");
	var elementValue = elementObject.val();
	var newArray = elementValue.split(".");
	var format = elementObject.attr('format');
	if(format == '' || format == null) format = '########0';
	var formatArray = format.split('.');
	if(isNumber(strChar)||(strChar=="." && newArray.length == 1 && formatArray[1] != undefined)){
		return true;
	}else{
		e.preventDefault();
		return false;
	}
}
function NUMBEROnKeyUpAction(element,mode,name,e){
	if(mode == MODE_VIEW)return;
	var keyVal = String.fromCharCode((96 <= e.keyCode && e.keyCode <= 105)? e.keyCode-48 : e.keyCode);	
	var elementObject = $("[name='"+element+"']");
	var elementValue = elementObject.val();
	var newArray = elementValue.split(".");
	var format = elementObject.attr('format');
	if(format == '' || format == null) format = '########0';
	var formatArray = format.split('.');
	if(formatArray[1] == undefined){
		if(isNumber(keyVal)){
			var newArray1 = elementObject.val().split(".");
			if(newArray1[0] != undefined && (newArray1[0].length > formatArray[0].length)){
				elementObject.val((newArray1[0].substring(0,formatArray[0].length)));
				return true;
			}else{
				elementObject.val(newArray1[0]);
				return true;
			}
		}
	}else{
		if(isNumber(keyVal)||(keyVal=="." && newArray.length == 1)){
			var newArray1 = elementObject.val().split(".");
			if(newArray1[0] != undefined && (newArray1[0].length > formatArray[0].length)){
				if(keyVal != '.'){
					elementObject.val((newArray1[0].substring(0,formatArray[0].length)));
					return true;
				}
			}
			var newArray2 = elementObject.val().split(".");
			if(newArray2[1] != undefined && (newArray2[1].length > formatArray[1].length)){
				if(keyVal != '.'){
					elementObject.val((newArray2[0])+'.'+(newArray2[1].substring(0,formatArray[1].length)));
					return true;
				}
			}
		}
	}
}
function NUMBEROnFocusAction(element,mode,name){
	if(mode == MODE_VIEW)return;
	try{
		$("[name='"+element+"']").select(function(e){
			try{
				e.stopImmediatePropagation();
			}catch(e){}
		});
	}catch(e){}
}

function EMAILOnblurAction(element,mode,name){
	if(mode == MODE_VIEW)return;
	var errorFormat = false;
	var callBackAction = true;
	var elementValue = $("[name='"+element+"']").val();
	try{
		var objectEmails = elementValue.split(',');
		var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		for(var i=0;i<objectEmails.length;i++){
			var email = objectEmails[i];
			if(!filter.test(email)){
				errorFormat = true;
				callBackAction = false;
				break;
			}
		}
	}catch(e){}
	if(callBackAction && !errorFormat){
		try{
			var ActionJS = eval(name+'ActionJS');
				ActionJS(element,mode,name);
		}catch(e){}
	}else if(errorFormat && !isEmpty(elementValue)){
		alertBox('ERROR_EMAIL_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
	}
}

function TELOnblurAction(element,mode,name){
	if(mode == MODE_VIEW)return;
	var callBackAction = true;
	var errorFormat = false;
	var regEx = /[0]{1}-[1-7]{1}[0-9]{3}-[0-9]{4}/;
	var elementObject = $("[name='"+element+"']");
	var zero = elementObject.val().indexOf("00-0000");
	var elementValue = $.trim(elementObject.val());
	if(elementValue){
		if (zero != -1||!regEx.test(elementValue)) {
			errorFormat = true;
			callBackAction = false;
		}
	};
	if(callBackAction && !errorFormat){
		try{
			var ActionJS = eval(name+'ActionJS');
				ActionJS(element,mode,name);
		}catch(e){}
	}else if(errorFormat && !isEmpty(elementValue)){
		alertBox('ERROR_TEL_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
	}	
}
function TELOnFocusAction(element,mode,name){
	if(mode == MODE_VIEW)return;
	try{
		$("[name='"+element+"']").select(function(e){
			try{
				e.stopImmediatePropagation();
			}catch(e){}
		});
	}catch(e){}
}
function TELOnKeyPressAction(element,mode,name,e){
	if(mode == MODE_VIEW)return;
	var strKeyCode = String.fromCharCode(e.keyCode);
	var pattern = "9-9999-9999";
	var elementValue = $("[name='"+element+"']").val();
	var numcheck = /^[0-9-]+$/;
	if (!numcheck.test(elementValue+strKeyCode)) {
		e.returnValue = false;
	} else {
		e.returnValue = true;
	}
	$("[name='"+element+"']").mask(pattern);	
}

function MobileOnblurAction(element,mode,name){
	if(mode == MODE_VIEW)return;
	var callBackAction = true;
	var errorFormat = false;
	var regEx = /[0]{1}[6,8,9]{1}-[0-9]{4}-[0-9]{4}/; //0[6,8,9]-XXXX-XXXX
	var elementValue = $.trim($("[name='"+element+"']").val());
	if(elementValue){
		if (!regEx.test(elementValue)) {
			errorFormat = true;
			callBackAction = false;
		}
	};
	if(callBackAction && !errorFormat){
		try{
			var ActionJS = eval(name+'ActionJS');
				ActionJS(element,mode,name);
		}catch(e){}
	}else if(errorFormat && !isEmpty(elementValue)){
		alertBox('ERROR_MOBILE_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
	}
}
function MobileOnFocusAction(element,mode,name){
	if(mode == MODE_VIEW)return;
	try{
		$("[name='"+element+"']").select(function(e){
			try{
				e.stopImmediatePropagation();
			}catch(e){}
		});
	}catch(e){}
}
function MobileOnKeyPressAction(element,mode,name,e){
	if(mode == MODE_VIEW)return;
	var strKeyCode = String.fromCharCode(e.keyCode);
	var pattern = "99-9999-9999";
	var elementValue = $("[name='"+element+"']").val();
	var numcheck = /^[0-9-]+$/;
	if (!numcheck.test(elementValue+strKeyCode)) {
		e.returnValue = false;
	} else {
		e.returnValue = true;
	}
	$("[name='"+element+"']").mask(pattern);	
}


function ExtOnblurAction(element,mode,name){
	if(mode == MODE_VIEW)return;
	var elementValue = $("[name='"+element+"']").val();
	var callBackAction = true;
	var errorFormat = false;
	if(!isEmpty(elementValue)){
		if(!isNumber(elementValue)){
			errorFormat = true;
			callBackAction = false;
		}
	}
	if(callBackAction && !errorFormat){
		try{
			var ActionJS = eval(name+'ActionJS');
				ActionJS(element,mode,name);
		}catch(e){}
	}else if(errorFormat && !isEmpty(elementValue)){
		alertBox('ERROR_EXT_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
	}
}
function ExtOnFocusAction(element,mode,name){
	if(mode == MODE_VIEW)return;
	try{
		$("[name='"+element+"']").select(function(e){
			try{
				e.stopImmediatePropagation();
			}catch(e){}
		});
	}catch(e){}
}
function ExtOnKeyPressAction(element,mode,name,e){
	if(mode == MODE_VIEW)return;
	var strChar = String.fromCharCode(e.keyCode);
	if(!isEmpty(strChar)){
		if(isNumber(strChar)){
			return true;
		}else{
			e.preventDefault();
			return false;
		}
	}	
}

function createPopupDialog(formId,width,height){
//	#rawi comment change to bootstrap dialog , don't init dialog
//	if(width == undefined){
//		width = $(window).width()-50;
//	}
//	if(height == undefined){
//		height = 'auto';
//	}
//	var dialogId = 'form_'+formId+'_dialog';
//	var html = '<div id='+dialogId+' class=formDialog></div>';
//	$("body").append(html);
//	$('#'+dialogId).dialog({
//		width: width,
//		height: height,
//		autoOpen: false,
//		resizable : false,
//	    modal : true,
//	    show: 'slide',
//	    draggable: false,
//		closeOnEscape: false,
//		dialogClass:dialogId
//	});
//	$('#'+dialogId).bind('dialogclose',function(event){
//		
//	});
}
function loadPopupDialog(formId,mode,rowId,str,handleForm,width,height){
	var dialogId = 'form_'+formId+'_dialog';
	var $data  = 'formId='+formId;
		$data += '&mode='+mode;
		$data += '&rowId='+rowId;
		$data += '&action=LoadPopupForm&handleForm=N';
		$data += '&'+str;
		$data += '&dialogId='+dialogId;
	var url = CONTEXT_PATH+'/FrontController';
	if(width == undefined){
		width = '97%';
	}	
	$.post(url,$data,function(data,status,xhr){
//		$('#'+dialogId).html(data);
//		$('#'+dialogId).dialog('open');
		var dlgprop = {
				nl2br: false, // For disable auto <br/> from NL
				title: false, // Title bar
				closeByBackdrop: false,
				formId: formId,
				message: '<div id="'+dialogId+'" class="row formDialog"><div class="col-xs-12">'+data+'</div></div>', // Body
				onshow: function(dialogInstance) {
					try{
						var funcJS = eval(formId+'AfterLoadActionJS');
							funcJS();
					}catch(e){}
				},
				onshown: function(dialogInstance) {
					
				},
				onhide: function(dialogInstance) {
					
				},
				onhidden: function(dialogInstance) {

				}
			};
		openBootstrapDialog(dlgprop,'right',width, height);
	});
}
function closePopupDialog(formId,action){
	var dialogId = 'form_'+formId+'_dialog';
	var $data  = 'className=com.eaf.orig.ulo.app.view.util.manual.ControlFormHandle';
	var url = CONTEXT_PATH+'/Ajax';
	$.post(url,$data,function(data,status,xhr){
//		$('#'+dialogId).html('');
//		$('#'+dialogId).dialog('close');
		if (isResponseSuccess(data)) {
			var responseData = getResponseData(data);
			$('#formActionName').val(responseData);
			var modal = $('#'+dialogId).data('modal');
				modal.close();
			try{
				var funcJS = eval(formId+'AfterCloseActionJS');
					funcJS(action);
			}catch(e){}
		}
	});
}
function savePopupDialog(formId,str){
	validateFormDialog(formId,str);
}
function validateFormDialog(formId,str){
	var handleForm = 'Y';
	var	validateForm = 'Y';
	$('#'+formId+'ErrorFormHandlerManager').html('');
	var dialogId = 'form_'+formId+'_dialog';
	var $data  = $('#'+dialogId+' *').serialize();
		$data += '&className=com.eaf.orig.ulo.app.view.util.manual.MandatoryForm';
		$data += '&handleForm='+handleForm;
		$data += '&validateForm='+validateForm;
	var url = CONTEXT_PATH+'/Ajax';
	$.post(url,$data,function(data,status,xhr){
		try{
//			#rawi:After MandatoryForm set handleForm=N,validateForm=N
			handleForm = 'N';
			validateForm = 'N';
			if (isResponseSuccess(data)) {
				var responseData = getResponseData(data);
				if(responseData == '' || responseData == undefined){
					savePopupFormAction(formId,str,handleForm,validateForm);
				}else{
					var $error = $.parseJSON(responseData);
					if(validateFormDataObject($error,'ERROR')){
						displayMandatoryForm(formId,str,responseData);
					}else{
						savePopupFormAction(formId,str,handleForm,validateForm);
					}
				}
			}
		}catch(e){}
	});
	return true;
}

function validateFormAction(validateId,str,callBack){
	var $data  = '&className=com.eaf.orig.ulo.app.view.util.manual.ValidateFormAction';
		$data += '&validateId='+validateId;
		$data += '&'+str;
	var url = CONTEXT_PATH+'/Ajax';
	$.post(url,$data,function(data,status,xhr){
		if (isResponseSuccess(data)) {
			var responseData = getResponseData(data);
			if(responseData == '' || responseData == undefined){
				callBackActionJS(validateId,'AfterValidateFormActionJS',callBack,responseData);
			}else{			
				var $error = $.parseJSON(dataResponse);			
				if(validateFormDataObject($error,'ERROR')){				
					displayErrorValidateFormAction(responseData);
				}else{
					clearErrorValidateFormAction(responseData);
					callBackActionJS(validateId,'AfterValidateFormActionJS',callBack,responseData);
				}
			}
		}
	});
}

function callBackActionJS(callBackId,callBackJS,callBack,data){
	if(callBack != undefined){
		try{new callBack();}catch(e){}
	}else{
		try{
			var funcJS = eval(callBackId+callBackJS);
			funcJS(data);
		}catch(e){}
	}	
}
function validateFormDataObject($error,typeJson){
	var validateForm = false;
	if($error != undefined){
		$.map($error, function(item){
			var type = item.id;
			if(type == typeJson){
				if(item.value != undefined && item.value  != ''){
					validateForm = true;
				}
			}
		});
	}
	return validateForm;
}
function clearErrorValidateFormAction(data){
	try{
		var $error = $.parseJSON(data);	
		var elements = [];
		$.map($error, function(item){
			var type = item.id;
			if(type == 'SUCCESS'){
				try{
					elements = $.parseJSON(item.value);
					successElementValidateForm(elements);
				}catch(e){}
			}
		});
	}catch(e){}
}
function displayErrorValidateFormAction(data){
	var $error = $.parseJSON(data);	
	var elements = [];
	$.map($error, function(item){
		var type = item.id;
		if(type == 'ERROR'){
			alertBox(item.value);
		}else if(type == 'ELEMENT'){
			try{
				elements = $.parseJSON(item.value);
				errorElementValidateForm(elements);
			}catch(e){}
		}else if(type == 'SUCCESS'){
			try{
				elements = $.parseJSON(item.value);
				successElementValidateForm(elements);
			}catch(e){}
		}
	});
}
function errorElementValidateForm(elements){
	$.map(elements, function(item){
		var elementId = item.id;
		var formElement = $("[name='"+elementId+"']");
		if(formElement != undefined && formElement.attr('name') != undefined){
			formElement.closest('.form-group').addClass('has-error');
		}else{
			var labelElement = $("#LabelField_"+elementId);
			if(labelElement != undefined && labelElement.hasClass('LabelField')){
				labelElement.closest('.form-group').addClass('has-error');
			}
		}		
	});
}

function successElementValidateForm(elements){
	$.map(elements, function(item){
		var elementId = item.id;
		var formElement = $("[name='"+elementId+"']");
		if(formElement != undefined && formElement.attr('name') != undefined){
			formElement.closest('.form-group').removeClass('has-error');
		}else{
			var labelElement = $("#LabelField_"+elementId);
			if(labelElement != undefined && labelElement.hasClass('LabelField')){
				labelElement.closest('.form-group').removeClass('has-error');
			}
		}		
	});
}

function displayMandatoryForm(formId,str,data){
	try{
		var elements = [];
		$('#'+formId+'ErrorFormHandlerManager').html('');
		$('#'+formId+' .form-group').removeClass('has-error');
		var $error = $.parseJSON(data);		
		$.map($error, function(item){
			var type = item.id;
			if(type == 'ERROR'){
				var errorElement = "<div class='alert alert-danger alert-dismissible fade in' role='alert'>";
					errorElement += "<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>x</span></button>";
					errorElement += "<div>"+item.value+"</div>";
					errorElement += "</div>";
				$('#'+formId+'ErrorFormHandlerManager').append(errorElement);
			}else if(type == 'ELEMENT'){
				try{
					elements = $.parseJSON(item.value);
					errorElementForm(elements,formId);
				}catch(e){}
			}
		});
		var funcJS = eval(formId+'MandatoryActionJS');
			funcJS(elements,formId);
	}catch(e){}
}

function errorElementForm(elements,formId){
	$.map(elements, function(item){
		var elementId = item.id;
		var formElement = $("#"+formId+" [name='"+elementId+"']");
		if(formElement != undefined && formElement.attr('name') != undefined){
			if(formElement.closest('.form-group').attr('class') != undefined){
				formElement.closest('.form-group').addClass('has-error');
			}else{
				formElement.closest('.InputField').addClass('has-error');
			}
		}else{
			var labelElement = $("#"+formId+" #LabelField_"+elementId);
			if(labelElement != undefined && labelElement.hasClass('LabelField')){
				labelElement.closest('.form-group').addClass('has-error');
			}
		}		
	});
}
function clearValueElementForm(elements,formId){
	$.map(elements, function(item){
		try{
			var elementId = item.id;
			var element = $("#"+formId+" [name='"+elementId+"']");
			var tag = element.prop("tagName").toLowerCase();
			if(tag != undefined){
				if(tag == 'input'){
					if (element.attr('type') !== undefined){
						switch(element.attr('type').toLowerCase()) {
							case "hidden": element.val(''); break;
							case "text": element.val(''); break;
							case "checkbox": break;
							case "radio":  break;
						}
					}
				}else if(tag == 'select'){
					 element.val('');
				}else if(tag == 'textarea'){
					 element.html('');
				}
			}
		}catch(e){}
	});
}
function savePopupFormAction(formId,str,handleForm,validateForm){
	if(handleForm == undefined){
		handleForm = 'N';
	}
	if(validateForm == undefined){
		validateForm = 'N';
	}
	if(str == undefined){
		str = '';
	}
	var dialogId = 'form_'+formId+'_dialog';
	var $data  = $('#'+dialogId+' *').serialize();
		$data += '&dialogId='+dialogId;
		$data += '&action=SavePopupForm&handleForm='+handleForm;
		$data += '&'+str;
		$data += '&'+validateForm;
	var url = CONTEXT_PATH+'/FrontController';
	$.post(url,$data,function(data,status,xhr){
		try{
			var funcJS = eval(formId+'AfterSaveActionJS');
				funcJS(data,formId);
		}catch(e){}
	});
}

function saveApplicationForm(formId,action,handleForm,validateForm){
	if(handleForm == undefined){
		handleForm = 'N';
	}
	if(validateForm == undefined){
		validateForm = 'N';
	}
	validateSaveForm(formId,action,handleForm,validateForm);
}
function validateSaveForm(formId,action,handleForm,validateForm){
	$('#'+formId+'ErrorFormHandlerManager').html('');
	$('#'+formId+' .form-group').removeClass('has-error');
	var $data  = $('#appFormName').serialize();
		$data += '&formId='+formId;
		$data += '&className=com.eaf.orig.ulo.app.view.util.manual.MandatoryForm';
		$data += '&validateForm='+validateForm;
		$data += '&buttonAction='+action;
	var url = CONTEXT_PATH+'/Ajax';
	$.post(url,$data,function(data,status,xhr){
		try{
//			#rawi:After MandatoryForm set handleForm=N,validateForm=N
			handleForm = 'N';
			validateForm = 'N';
			if (isResponseSuccess(data)) {
				var responseData = getResponseData(data);
				if(responseData == '' || responseData == undefined){
					saveApplicationFormAction(formId,action,handleForm,validateForm);
				}else{
					var $error = $.parseJSON(responseData);
					if(validateFormDataObject($error,'ERROR')){
						var str = '';
						displayMandatoryForm(formId,str,responseData);
					}else{
						saveApplicationFormAction(formId,action,handleForm,validateForm);
					}
				}
			}
		}catch(e){}
	});	
}

function processApplicationFormAction(formId,action,handleForm,validateForm,callBack){
	$('#'+formId+'ErrorFormHandlerManager').html('');
	$('#'+formId+' .form-group').removeClass('has-error');
	var $data  = $('#appFormName').serialize();
		$data += '&formId='+formId;
		$data += '&className=com.eaf.orig.ulo.app.view.util.manual.MandatoryForm';
		$data += '&validateForm='+validateForm;
		$data += '&buttonAction='+action;
	var url = CONTEXT_PATH+'/Ajax';
	$.post(url,$data,function(data,status,xhr){
		try{
			if (isResponseSuccess(data)) {
				var responseData = getResponseData(data);
				if(responseData == '' || responseData == undefined){
					if(callBack != undefined){
						new callBack(responseData,status,xhr);
					}
				}else{
					var $error = $.parseJSON(responseData);
					if(validateFormDataObject($error,'ERROR')){
						var str = '';
						displayMandatoryForm(formId,str,responseData);
					}else{
						if(callBack != undefined){
							new callBack(responseData,status,xhr);
						}
					}
				}
			}
		}catch(e){}
	});	
}

function saveApplicationFormAction(formId,action,handleForm,validateForm){
//	#rawi comment
//	if(action == BUTTON_ACTION_DECISION){
//		decisionFormAction(formId,action,handleForm,validateForm);
//	}else{
//		$('#action').val('SaveApplication');
//		$('#handleForm').val(handleForm);	
//		$("[name='buttonAction']").val(action);
//		$('#appFormName').submit();
//	}
	$('#handleForm').val(handleForm);	
	$("[name='buttonAction']").val(action);
	$('#appFormName').submit();
}

function decisionFormAction(formId,action,handleForm,validateForm){
	var $data = '';
	$.post('orig/ulo/template/DecisionFormAction.jsp',$data,function(data,status,xhr){
		var dlgprop = {
				nl2br: false,
				title: 'Manual Decision Action',
				closable: false,
				draggable: true,
				message: '<div class="row formDialog"><div class="col-xs-12">'+data+'</div></div>',
				buttons: [
				   {
					   label: 'OK',
					   cssClass: 'btn-primary green',
					   action: function(dialog) {
						    var MANUAL_DECISION = $("[name='MANUAL_DECISION']").val();
							var formId = $("#FormHandlerManager [name='formId']").val();
							var action = BUTTON_ACTION_SUBMIT;
							var handleForm = 'N';
							var validateForm = 'N';
							$("[name='decisionAction']").val(MANUAL_DECISION);
							saveApplicationFormAction(formId,action,handleForm,validateForm);
						    dialog.close();
					   }
				   }
				],
				onhide: function(dialogInstance) {
				}
			};
		openBootstrapDialog(dlgprop,'auto','600px');
	});
}

function preSaveApplicationFormAction(formId,action,handleForm,validateForm){
//	#rawi comment
//	var processAction = BUTTON_ACTION_SUBMIT;
//	if(action == processAction && lookup(SENSITIVE_ROLE_ACTION,ROLE_ID)){
//		var $data  = 'className=com.eaf.orig.ulo.app.view.util.manual.SensitiveFieldAction';
//			$data += '&formId='+formId;
//		var url = CONTEXT_PATH+'/Ajax';
//		$.post(url,$data,function(data,status,xhr){
//			if("SUCCESS" == data) {
//				saveApplicationFormAction(formId,action,handleForm,validateForm);
//			} else {
//				loadPopupDialog(data,'INSERT','0','');
//			}
//		});
//	}else if(action == processAction && lookup(SUMMARY_SCREEN_ROLE_ACTION,ROLE_ID)){
//		var $data  = 'className=com.eaf.orig.ulo.app.view.util.manual.SummaryPageAction';
//		var url = CONTEXT_PATH+'/Ajax';
//		$.post(url,$data,function(data,status,xhr){
//		 loadPopupDialog(data,'INSERT','0','');
//		});
//	}else{
//		saveApplicationFormAction(formId,action,handleForm,validateForm);
//	}
}

function refreshSubForm(subformId,handleForm){
	if(handleForm != undefined && handleForm == 'Y'){
		var $data  = 'subformId='+subformId;
			$data += '&'+$('#SECTION_'+subformId+' *').serialize();
		ajax('com.eaf.orig.ulo.app.view.util.manual.HandleForm',$data,displaySubForm);
	}else{
		displaySubForm(subformId);
	}
}
function setPropertiesSubform(subformId,callBack){
	var $data  = 'subformId='+subformId;
		$data += '&'+$('#SECTION_'+subformId+' *').serialize();
	ajax('com.eaf.orig.ulo.app.view.util.manual.HandleForm',$data,callBack);
}
function displaySubForm(subformId){
	var url = CONTEXT_PATH+'/orig/ulo/template/SubForm.jsp?subformId='+subformId;
	$.post(url,function(data,status,xhr){
		try{	
			$('#SECTION_'+subformId).html(data);
		}catch(e){}
	});
}
function ajax(className,str,callBack){
	var $data  = '&className='+className;
		if(str != undefined){
			$data += '&'+str;
		}
	var url = CONTEXT_PATH+'/Ajax';
	$.post(url,$data,function(data,status,xhr){
		try{
			if (isResponseSuccess(data)) {
				var responseData = getResponseData(data);
				if(callBack != undefined){
					new callBack(responseData,status,xhr);
				}
			}
		}catch(e){}
	});
}

function confirmBox(msgId,callBack){
//	Type of dialog
//	BootstrapDialog.TYPE_DEFAULT
//	BootstrapDialog.TYPE_INFO
//	BootstrapDialog.TYPE_PRIMARY
//	BootstrapDialog.TYPE_SUCCESS
//	BootstrapDialog.TYPE_WARNING
//	BootstrapDialog.TYPE_DANGER	
	var constant = true;
	var msg = null;
	try{msg = eval(msgId);}catch(e){}
	if(msg == undefined || null == msg || msg == ''){
		constant = false;
		msg = msgId;
	}
	var dlgprop = {
			nl2br: false,
			type: BootstrapDialog.TYPE_WARNING,
			title: 'Confirmation',
			closable: false,
			draggable: true,
			message: '<div class="row formDialog"><div class="col-xs-12">'+msg+'</div></div>', // Body
			buttons: [
			   {
				   label: 'Yes',
				   cssClass: 'btn-default',
				   action: function(dialog) {
					    var CONFIRM_FLAG = 'Y';
					    dialog.close();
						try{
							if(callBack != undefined){
								new callBack(CONFIRM_FLAG);
							}else{
								if(constant){
									var funcJS = eval(msgId+'AfterConfirmActionJS');
										funcJS(CONFIRM_FLAG);
								}
							}		
						}catch(e){}
				   }
			   },
			   {
				   label: 'No',
				   cssClass: 'btn-danger',
				   action: function(dialog) {
					    var CONFIRM_FLAG = 'N';
					    dialog.close();
						try{
							if(callBack != undefined){
								new callBack(CONFIRM_FLAG);
							}else{
								if(constant){
									var funcJS = eval(msgId+'AfterConfirmActionJS');
										funcJS(CONFIRM_FLAG);
								}
							}		
						}catch(e){}
				   }
			   }
			]
		};
	openConfirmDialog(dlgprop,'auto',DEFAULT_ALERT_BOX_WIDTH);
}

function openConfirmDialog(obj, side, width, height) {	
//	Type of dialog
//	BootstrapDialog.TYPE_DEFAULT
//	BootstrapDialog.TYPE_INFO
//	BootstrapDialog.TYPE_PRIMARY
//	BootstrapDialog.TYPE_SUCCESS
//	BootstrapDialog.TYPE_WARNING
//	BootstrapDialog.TYPE_DANGER	
	if (!obj) return false;
	var dlg = new BootstrapDialog(obj);
	dlg.realize();	
//	Use Switch case for addition
	switch(side) {
		case 'right':side = 'right-side';
		break;
	}
	dlg.getModalDialog().css({
		'width': width
	}).attr('id','form_'+obj.formId+'_dialog').data('modal', dlg);
	if (obj.title == false) {
		dlg.getModalHeader().hide();
	}
	dlg.open();	
}

function confirmLink(msg,el) {
	confirmBox(msg,function() {
		if (el != undefined) {
			el = $(el);
			var url = el.attr('href');
			window.location = url;
		}
	});
	return false;
}

function alertBox(msgId,callBack,elementId,width,height){
	var constant = true;
	var msg = null;
	if(width == undefined){
		width = DEFAULT_ALERT_BOX_WIDTH;
	}
	try{
		msg = eval(msgId);
	}catch(e){}
	if(msg == undefined || null == msg || msg == ''){
		constant = false;
		msg = msgId;
	}
	if(elementId == undefined){
		elementId = '';
	}
	var dlgprop = {
		nl2br: false,
		title: 'Alert',
		closable: false,
		draggable: true,
		message: '<div class="row formDialog"><div class="col-xs-12">'+msg+'</div></div>', // Body
		buttons: [
		   {
			   label: 'OK',
			   cssClass: 'btn-primary',
			   action: function(dialog) {
				    dialog.close();
					try{
						if(callBack != undefined){
							new callBack(elementId);
						}else{
							if(constant){
								var funcJS = eval(msgId+'AfterAlertActionJS');
									funcJS(elementId);
							}
						}		
					}catch(e){}
			   }
		   }
		]
	};
	openAlertDialog(dlgprop,'auto',width, height);
}

function openAlertDialog(obj, side, width, height) {	
//	Type of dialog
//	BootstrapDialog.TYPE_DEFAULT
//	BootstrapDialog.TYPE_INFO
//	BootstrapDialog.TYPE_PRIMARY
//	BootstrapDialog.TYPE_SUCCESS
//	BootstrapDialog.TYPE_WARNING
//	BootstrapDialog.TYPE_DANGER	
	if (!obj) return false;
	var dlg = new BootstrapDialog(obj);
	dlg.realize();	
//	Use Switch case for addition
	switch(side) {
		case 'right':side = 'right-side';
		break;
	}
	dlg.getModalDialog().css({
		'width': width
	}).attr('id','form_'+obj.formId+'_dialog').data('modal', dlg);
	if (obj.title == false) {
		dlg.getModalHeader().hide();
	}
	dlg.open();	
}

function openBootstrapDialog(obj, side, width, height) {	
//	Type of dialog
//	BootstrapDialog.TYPE_DEFAULT
//	BootstrapDialog.TYPE_INFO
//	BootstrapDialog.TYPE_PRIMARY
//	BootstrapDialog.TYPE_SUCCESS
//	BootstrapDialog.TYPE_WARNING
//	BootstrapDialog.TYPE_DANGER	
	if (!obj) return false;
	var dlg = new BootstrapDialog(obj);
	dlg.realize();	
//	Use Switch case for addition
	switch(side) {
		case 'right':side = 'right-side';
		break;
	}
	dlg.getModalDialog().css({
		'width': width
	}).attr('id','form_'+obj.formId+'_dialog').data('modal', dlg)
	.find('.modal-body').css({'height': height})
	.find('.PopupFormHandlerWrapper').css('height', parseInt(height) - 63);
	
	modalreposition(dlg.getModal(), side);	
	if (obj.title == false) {
		dlg.getModalHeader().hide();
	}
	dlg.open();	
}

function loadNextTabAction(formId,mode,rowId,str,handleForm,validateForm){
//	// Trying to hide SmartDataEntry
//	try {
//		if (SmartDataEntry) {
//			SmartDataEntry.hide(false);
//		}
//	} catch(e) {}
	
	if(handleForm == undefined){
		handleForm = 'N';
	}
	if(validateForm == undefined){
		validateForm = 'N';
	}
	if(str == undefined){
		str = '';
	}
	var currentFormName = $("#FormHandlerManager [name='formName']").val();
	var currentFormId = $("#FormHandlerManager [name='formId']").val();
	var $data  = $('#'+currentFormId+' *').serialize();
		$data += '&formId='+formId;
		$data += '&mode='+mode;
		$data += '&rowId='+rowId;
		$data += '&currentFormName='+currentFormName;
		$data += '&currentFormId='+currentFormId;
		$data += '&action=LoadNextTab&handleForm='+handleForm;
		$data += '&'+str;
		$data += '&validateForm='+validateForm;
	var url = CONTEXT_PATH+'/FrontController';
//	Pace.restart(); // Restart loading
	$.post(url,$data,function(data,status,xhr){
		try{
			refreshMasterForm();
		}catch(e){}
	});
}
function validateTabForm(str,handleForm,validateForm){
	var formName = $("#FormHandlerManager [name='formName']").val();
	var formId = $("#FormHandlerManager [name='formId']").val();
	$('#'+formId+'ErrorFormHandlerManager').html('');
	if(validateForm == 'Y'){
		var $data  = $('#'+formId+' *').serialize();
			$data += '&formName='+formName;
			$data += '&formId='+formId;
			$data += '&handleForm='+handleForm;
			$data += '&validateForm='+validateForm;
			$data += '&className=com.eaf.orig.ulo.app.view.util.manual.MandatoryForm';
		var url = CONTEXT_PATH+'/Ajax';
		$.post(url,$data,function(data,status,xhr){
			try{
//				#rawi:After MandatoryForm set handleForm=N,validateForm=N
				handleForm = 'N';
				validateForm = 'N';
				if (isResponseSuccess(data)) {
					var responseData = getResponseData(data);
					if(responseData == '' || responseData == undefined){
						saveTabFormAction(str,handleForm,validateForm);
					}else{
						var $error = $.parseJSON(responseData);
						if(validateFormDataObject($error,'ERROR')){
							displayMandatoryForm(formId,str,responseData);
						}else{
							saveTabFormAction(str,handleForm,validateForm);
						}
					}
				}
			}catch(e){}
		});
	}else{
//		#rawi:After MandatoryForm set handleForm=N,validateForm=N
		handleForm = 'N';
		validateForm = 'N';
		saveTabFormAction(str,handleForm,validateForm);
	}
}

function saveTabForm(str,handleForm,validateForm){
	validateTabForm(str,handleForm,validateForm);
}
function saveTabFormAction(str,handleForm,validateForm){
	if(handleForm == undefined){
		handleForm = 'N';
	}
	if(validateForm == undefined){
		validateForm = 'N';
	}
	if(str == undefined){
		str = '';
	}
	var currentFormName = $("#FormHandlerManager [name='formName']").val();
	var currentFormId = $("#FormHandlerManager [name='formId']").val();
	var $data  = $('#'+currentFormId+' *').serialize();
		$data += '&currentFormName='+currentFormName;
		$data += '&currentFormId='+currentFormId;
		$data += '&action=SaveTabForm&handleForm='+handleForm;
		$data += '&'+str;
		$data += '&validateForm'+validateForm;
	var url = CONTEXT_PATH+'/FrontController';
	$.post(url,$data,function(data,status,xhr){
		try{
			var funcJS = eval(currentFormId+'AfterSaveActionJS');
				funcJS(data);
		}catch(e){}
	});
}
function backLastTabAction(str,handleForm){
	if(handleForm == undefined){
		handleForm = 'N';
	}
	if(str == undefined){
		str = '';
	}
	var currentFormName = $("#FormHandlerManager [name='formName']").val();
	var currentFormId = $("#FormHandlerManager [name='formId']").val();
	var $data  = '&action=BackLastTab&handleForm='+handleForm;
		$data += '&'+str;
		$data += '&currentFormName'+currentFormName;
		$data += '&currentFormId'+currentFormId;
	var url = CONTEXT_PATH+'/FrontController';
	$.post(url,$data,function(data,status,xhr){
		try{
			refreshMasterForm();
		}catch(e){}
	});
}
function refreshMasterForm(){
	var url = CONTEXT_PATH+'/orig/ulo/template/FormTemplate.jsp';
	$.post(url,function(data,status,xhr){
		try{
			$('#FormHandlerManager').html(data);
			$("html, body").animate({
		        scrollTop: 0
		    }, 200);
		}catch(e){}
	});
}
function refreshMasterPopupForm(){
	var url = CONTEXT_PATH+'/orig/ulo/template/PopupFormTemplate.jsp';
	$.post(url,function(data,status,xhr){
		try{
			$('#PopupFormHandlerManager').html(data);
		}catch(e){}
	});
}
function createRow(className,str,callBack,subformId,handleForm){
//	#rawi fix bug callBack function refreshSubform after createRow do not set handleForm
	if(subformId != undefined && handleForm != undefined && handleForm == 'Y'){
		var $data  = '&subformId='+subformId;
			$data += '&'+$('#SECTION_'+subformId+' *').serialize();
			$data += '&className=com.eaf.orig.ulo.app.view.util.manual.HandleForm';
		var url = CONTEXT_PATH+'/Ajax';
		$.post(url,$data,function(data,status,xhr){
			try{
				if (isResponseSuccess(data)) {
					var responseData = getResponseData(data);
					str += '&subformId='+subformId;
					str += '&handleForm='+handleForm;
					ajax(className,str,callBack);
				}
			}catch(e){}
		});	
	}else{
		str += '&subformId='+subformId;
		str += '&handleForm='+handleForm;
		ajax(className,str,callBack);
	}
}
function deleteRow(className,str,callBack,subformId,handleForm){
	var dlgprop = {
			nl2br: false,
			type: BootstrapDialog.TYPE_WARNING,
			title: 'Confirmation',
			closable: false,
			draggable: true,
			message: '<div class="row formDialog"><div class="col-xs-12">'+MSG_CONFIRM_DELETE_ROW+'</div></div>', // Body
			buttons: [
			   {
				   label: 'Yes',
				   cssClass: 'btn-primary',
				   action: function(dialog) {
					    dialog.close();
						try{
							MSG_CONFIRM_DELETE_ROWAfterConfirmActionJS(className,str,callBack,subformId,handleForm);
						}catch(e){}
				   }
			   },
			   {
				   label: 'No',
				   cssClass: 'btn-default',
				   action: function(dialog) {
					    dialog.close();
				   }
			   }
			]
		};
	openBootstrapDialog(dlgprop,'auto',DEFAULT_ALERT_BOX_WIDTH);
}
function MSG_CONFIRM_DELETE_ROWAfterConfirmActionJS(className,str,callBack,subformId,handleForm){
//	#rawi fix bug callBack function refreshSubform after deleteRow do not set handleForm
	if(subformId != undefined && handleForm != undefined && handleForm == 'Y'){
		var $data  = '&subformId='+subformId;
			$data += '&'+$('#SECTION_'+subformId+' *').serialize();
			$data += '&className=com.eaf.orig.ulo.app.view.util.manual.HandleForm';
		var url = CONTEXT_PATH+'/Ajax';
		$.post(url,$data,function(data,status,xhr){
			try{
				if (isResponseSuccess(data)) {
					var responseData = getResponseData(data);
					str += '&subformId='+subformId;
					str += '&handleForm='+handleForm;
					ajax(className,str,callBack);
				}
			}catch(e){}
		});
	}else{
		str += '&subformId='+subformId;
		str += '&handleForm='+handleForm;
		ajax(className,str,callBack);
	}
}
function clearForm(form) {
	var formEl = $(form);	
	// Clear Textboxes
	formEl.find('input[type=text]').val('');
	// Uncheck Checkboxes Radios
	formEl.find('input[type=radio], input[type=checkbox]').prop('checked', false);
	// Clear selected item from Dropdown (Selectize)
	formEl.find('select').each(function() {
		this.selectize.clear(true);
	});
}
function countFilledInput(form) {
	var formEl = $(form);
	var counter = 0;
	formEl.find('input[type=text]').each(function() {
		if ($(this).val() != '') {
			counter++;
		}
	});
	
	formEl.find('select').each(function() {
		if ($(this).val() != '') {
			counter++;
		}
	});
	return counter;
}

function getPopupFormId(){
	var formActionName = $("#formActionName").val();
	var formId = $("#PopupFormHandlerManager ."+formActionName+" [name='formId']").val();
	return formId;
}
function getPopupFormName(){
	var formActionName = $("#formActionName").val();
	var formName = $("#PopupFormHandlerManager ."+formActionName+" [name='formName']").val();
	return formName;
}

function AccountNoOnblurAction(element,mode,name){
	if(mode == MODE_VIEW)return;
	var callBackAction = true;
	var errorFormat = false;
	var regEx = /[0-9]{3}-[0-9]{1}-[0-9]{5}-[0-9]{1}/; //XXX-X-XXXXX-X
	var elementValue = $.trim($("[name='"+element+"']").val());
	if(elementValue){
		if (!regEx.test(elementValue)) {
			errorFormat = true;
		}
	};
	if(callBackAction && !errorFormat){
		try{
			var ActionJS = eval(name+'ActionJS');
				ActionJS(element,mode,name);
		}catch(e){}
	}else if(errorFormat && !isEmpty(elementValue)){
		alertBox('ERROR_ACCOUNT_NO_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
	}
}
function AccountNoOnFocusAction(element,mode,name){
	if(mode == MODE_VIEW)return;
	try{
		$("[name='"+element+"']").select(function(e){
			try{
				e.stopImmediatePropagation();
			}catch(e){}
		});
	}catch(e){}
}
function AccountNoOnKeyPressAction(element,mode,name,e){
	if(mode == MODE_VIEW)return;
	var strKeyCode = String.fromCharCode(e.keyCode);
	var pattern = "999-9-99999-9";
	var elementObject = $("[name='"+element+"']");
	var elementValue = elementObject.val();
	var numcheck = /^[0-9-]+$/;
	if (!numcheck.test(elementValue+strKeyCode)) {
		e.returnValue = false;
	} else {
		e.returnValue = true;
	}
	elementObject.mask(pattern);
}


function CardNoOnblurAction(element,mode,name){
	if(mode == MODE_VIEW)return;
	var callBackAction = false;
	var errorFormat = false;
	var regEx = /[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}/; //XXXX-XXXX-XXXX-XXXX
	var elementValue = $.trim($("[name='"+element+"']").val());
	if (elementValue) {
		if (!regEx.test(elementValue)) {
			errorFormat = true;
		}
	};
	if(callBackAction && !errorFormat){
		try{
			var ActionJS = eval(name+'ActionJS');
				ActionJS(element,mode,name);
		}catch(e){}
	}else if(errorFormat && !isEmpty(elementValue)){
		alertBox('ERROR_CARD_NO_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
	}
}
function CardNoOnFocusAction(element,mode,name){
	if(mode == MODE_VIEW)return;
	try{
		$("[name='"+element+"']").select(function(e){
			try{
				e.stopImmediatePropagation();
			}catch(e){}
		});
	}catch(e){}
}
function CardNoOnKeyPressAction(element,mode,name,e){
	if(mode == MODE_VIEW)return;
	var strKeyCode = String.fromCharCode(e.keyCode);
	var pattern = "9999-9999-9999-9999";
	var elementObject = $("[name='"+element+"']");
	var elementValue = elementObject.val();
	var numcheck = /^[0-9-]+$/;
	if (!numcheck.test(elementValue+strKeyCode)) {
		e.returnValue = false;
	} else {
		e.returnValue = true;
	}
	elementObject.mask(pattern);
}
function displayNotifyErrorMessage(errorMsg){
//	Pace.unblockFlag = true;
//	Pace.unblock();	
	$('.ErrorFormHandlerManager').html('');
	var errorElement = "<div class='alert alert-danger alert-dismissible fade in' role='alert'>";
		errorElement += "<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>x</span></button>";
		errorElement += "<div>"+errorMsg+"</div>";
		errorElement += "</div>";
	$('.ErrorFormHandlerManager').append(errorElement);
	$('.ErrorFormHandlerManager')[0].scrollIntoView(true);
}
function validateElementInputChars(element, need) {
	var elements = $(element);
	var isNotError = true;
	
	if (need == undefined) {
		need = SEARCH_FIELDS_REQUIRED_CHAR_MOREEQUAL_THAN;
	}
	
	getFilledFormElements(elements).each(function() {
		if (countInputChars($(this)) < need) {
			isNotError = false;
		}
	});
	return isNotError;
}
function getFilledFormElements(elements) {
	return $(elements).filter(function() { 
		return $(this).val().length > 0;
	});
}
function isResponseSuccess(data){
	var responseJSON = $.parseJSON(data);
	var resultCode = responseJSON.responseCode;
	if(resultCode == SUCCESS_CODE){
		return true;
	}else{
		errorResponse(data,status,xhr);
//		displayNotifyErrorMessage(resultDesc);
		return false;
	}
}
function errorResponse(data,status,xhr){
	var responseJSON = $.parseJSON(data);
	var resultDesc = responseJSON.responseDesc;
	errorForm(resultDesc);
}

function errorForm(errorMsg){
	Pace.unblockFlag = true;
	Pace.unblock();	
	$('.ErrorFormHandlerManager').html('');
	var errorElement = "<div class='alert alert-danger alert-dismissible fade in' role='alert'>";
		errorElement += "<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>x</span></button>";
		errorElement += "<div>"+errorMsg+"</div>";
		errorElement += "</div>";
	$('.ErrorFormHandlerManager').append(errorElement);
	$('.ErrorFormHandlerManager')[0].scrollIntoView(true);
}
function countInputChars(inputElement) {
	return $(inputElement).val().length;
}
function getResponseData(data){
	var responseJSON = $.parseJSON(data);
	var responseData = responseJSON.data;
	return responseData;
}