var DEFAULT_ALERT_BOX_WIDTH = '600px';
var CURRENT_POPUP_FIELD_NAME= "";
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
	try{
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
//					#p'eh validate year th=2443,en=1900
					if(yearValue.length!=0){
						var currentYear = (new Date()).getFullYear();
						if(regional == 'th'){
							var diffYear = parseInt(currentYear + 543) - parseInt(yearValue);
							if(diffYear < 0){
								if(CARLENDAR_VALIDATE_LIMIT_YEAR < (diffYear*(-1))){
									errorFormat = true;
								}
							}else{
								if(CARLENDAR_VALIDATE_LIMIT_YEAR < diffYear){
									errorFormat = true;
								}
							}
						}else if(regional == 'en'){
							var diffYear = parseInt(currentYear) - parseInt(yearValue);
								if(diffYear < 0){
									if(CARLENDAR_VALIDATE_LIMIT_YEAR < (diffYear*(-1))){
										errorFormat = true;
									}
								}else{
									if(CARLENDAR_VALIDATE_LIMIT_YEAR < diffYear){
										errorFormat = true;
									}
								}
						}
					}else{
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
			}catch(exception){}
		}else if(errorFormat && !isEmpty(elementValue)){
			alertBox('ERROR_DATE_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
		}
	}catch(exception){
		errorException(exception);
	}
}
function ERROR_DATE_FORMATAfterAlertActionJS(){
	
}
function emptyFocusElementActionJS(elementId){
	try{
		var	elementObject = $("[name='"+elementId+"']");
		var	elementProperty = (elementObject != undefined)?elementObject.attr('property'):'';
		displayHtmlElement(elementId,'',elementObject,elementProperty);
		switch(elementProperty){
			case "dropdown":break;
			default:elementObject.focus(); break;
		}
	}catch(exception){
		errorException(exception);
	}
}
function emptyAutoCompleteFocusElementActionJS(obj){
	try{
		switch(elementProperty){
			case "dropdown":obj.selectize.focus();break;
			default:obj.focus();break;
		}
	}catch(exception){
		errorException(exception);
	}
}
function focusElementActionJS(elementId,elementValue){
	try{
		var	elementObject = $("[name='"+elementId+"']");
		var	elementProperty = (elementObject != undefined)?elementObject.attr('property'):'';
		displayHtmlElement(elementId,elementValue,elementObject,elementProperty);
		switch(elementProperty){
			case "dropdown":elementObject[0].selectize.focus();break;
			default:elementObject.focus();break;
		}
	}catch(exception){
		errorException(exception);
	}
}

function focusSelectElementActionJS(elementId){
	try{
		var	elementObject = $("[name='"+elementId+"']");
		var	elementProperty = (elementObject != undefined)?elementObject.attr('property'):'';
		displayHtmlElement(elementId,'',elementObject,elementProperty);
		switch(elementProperty){
			case "dropdown":break;
			default:elementObject.focus(); break;
		}
	}catch(exception){
		errorException(exception);
	}
}

function CALENDAROnKeyPressAction(element,mode,name,event){
	try{
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
	}catch(exception){
		errorException(exception);
	}
}

function THOnblurAction(element,mode,name){
	try{
		var fistObjRegExpCondition = /^[\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44]/;
		var objRegExpCondition  = /^([\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44])+([\u0E01-\u0E4F\u0E46\u002C\u002E\u002D\u005F\u002F\u0028\u0029\u0025\u002A\u0040\u0024\u0023\u0026\u002B\s])+$/;
		var CIDTYPE = $("[name='CID_TYPE']").val();
		if(TH_LAST_NAME == element && CIDTYPE == CIDTYPE_PASSPORT){
			fistObjRegExpCondition = /^[\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44\-]/;
			objRegExpCondition = /^([\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44\-])+([\u0E01-\u0E4F\u0E46\u002C\u002E\u002D\u005F\u002F\u0028\u0029\u0025\u002A\u0040\u0024\u0023\u0026\u002B\s])+$/;
		}
		if(mode == MODE_VIEW)return;
		var callBackAction = true;
		var errorFormat = false;
		var elementValue = $("[name='"+element+"']").val();
		if (elementValue == '' || elementValue.length == 1){
			var fistObjRegExp = fistObjRegExpCondition;
			if(!fistObjRegExp.test(elementValue)){
				errorFormat = true;
				callBackAction = false;
			}		
		}else{
			var objRegExp  = objRegExpCondition;
			if(!objRegExp.test(elementValue)){
				errorFormat = true;
				callBackAction = false;
			}
		}
		if(callBackAction && !errorFormat){
			try{
				var ActionJS = eval(name+'ActionJS');
					ActionJS(element,mode,name);
			}catch(exception){}
		}else if(errorFormat && !isEmpty(elementValue)){
			alertBox('ERROR_THAI_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
		}
	}catch(exception){
		errorException(exception);
	}
}
function THOnblurAutoCompleteAction(element,mode,name){
	try{
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
			}catch(exception){}
		}else if(errorFormat && !isEmpty(elementValue)){
			alertBox('ERROR_THAI_NAME_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
		}
	}catch(exception){
		errorException(exception);
	}
}

function THOnTypeAutoCompleteAction(elementId,mode,name,obj){
	try{
		if(mode == MODE_VIEW)return;
		var callBackAction = true;
		var errorFormat = false;
		var elementValue =	obj.$control_input.val();
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
			}catch(exception){}
		}else if(errorFormat && !isEmpty(elementValue)){
			obj.$control_input.val('').triggerHandler('update');
			obj.refreshOptions();
		}
	}catch(exception){
		errorException(exception);
	}
}

function TextBoxOnblurAction(element,mode,name){
	try{
		if(mode == MODE_VIEW)return;
		var callBackAction = true;
		//var errorFormat = true;
		var elementValue = $("[name='"+element+"']").val();
		if (elementValue == '' || elementValue.length >= 1){
			var fistObjRegExp = /^[\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44\d\w\/]/;
			if(!fistObjRegExp.test(elementValue)){
				callBackAction = true;
			}		
		}else{
			var objRegExp  = /^([\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44\d\w\/])+([\u0E01-\u0E4F\u0E46\u002C\u002E\u002D\u005F\u002F\u0028\u0029\u0025\u002A\u0040\u0024\u0023\u0026\u002B\s\d\w\/])+$/;
			if(!objRegExp.test(elementValue)){
				callBackAction = true;
			}
		}
		if(callBackAction && !isEmpty(elementValue)){
			try{
				elementValue = removeFirstCharactor(elementValue);
				var strRepleaceTextBox = elementValue.replace(/[^\u0E01-\u0E4F\u0E46\u002C\u002E\u002D\u005F\u002F\u0028\u0029\u0025\u002A\u0040\u0024\u0023\u0026\u002B\s\d\w\/]/g,"");
				$("[name='"+element+"']").val(strRepleaceTextBox);
				var ActionJS = eval(name+'ActionJS');
					ActionJS(element,mode,name);
			}catch(exception){}
		}else{
			//#Change if null value in text box Not remove charator String -> call Function CallBack #932 #934 #935
			var ActionJS = eval(name+'ActionJS');
				ActionJS(element,mode,name);
		}
		//#Validate in callback
//		else if(errorFormat && !isEmpty(elementValue)){}
	}catch(exception){
		errorException(exception);
	}

}

function AutoCompleteOnblurActionJS(element,mode,name){
	var elementValue = $("[name='"+element+"']").val();
	var strRepleaceTextBox = removeFirstCharactor(elementValue);
	strRepleaceTextBox = strRepleaceTextBox.replace(/[^\u0E01-\u0E39\u0E40-\u0E4C\u0E4E\u0E46\u002C\u002E\u002D\u005F\u002F\u0028\u0029\u0025\u002A\u0040\u0024\u0023\u0026\u002B\s\d\w\/]/g,"");
	if(!(elementValue == strRepleaceTextBox)){
//		$("[name="+element+"]")[0].selectize.removeItem(elementValue);
//		$("[name="+element+"]")[0].selectize.removeOption(elementValue);
		$("[name='"+element+"']")[0].selectize.clearOptions();
		$("[name='"+element+"']")[0].selectize.addOption({'value':strRepleaceTextBox,'code':strRepleaceTextBox});
		$("[name='"+element+"']")[0].selectize.setValue(strRepleaceTextBox, true);
	}
}

function removeFirstCharactor(str){
	var fistObjRegExp = /^[\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44\d\w\@\$\%\(\)\.\#\&\*\_\-\,\+\/]/;
	if(!fistObjRegExp.test(str[0])){
		str = str.substr(1);
		return removeFirstCharactor(str);
	}else{
		return str;
	}
}

function TextBoxOnKeyPressAction(element,mode,name,e){
	try{
		if(mode == MODE_VIEW)return;
		var strKeyCode = String.fromCharCode(e.keyCode);
		var elementValue = $("[name='"+element+"']").val();
		if (elementValue == '' || elementValue.length == 0){
			var fistObjRegExp = /^[\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44\d\w\/]/;
			if (!fistObjRegExp.test(strKeyCode)) {
				e.preventDefault();
				return false;
			}else{
				return true;
			}

		}else{
			var objRegExp = /^([\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44\d\w\/])+([\u0E01-\u0E4F\u0E46\u002C\u002E\u002D\u005F\u002F\u0028\u0029\u0025\u002A\u0040\u0024\u0023\u0026\u002B\s\d\w\/])+$/;
			if (!objRegExp.test(elementValue+strKeyCode)) {
				e.preventDefault();
				return false;
			}else{
				return true;
			}
		}
	}catch(exception){
		errorException(exception);
	}

}

function TextBoxSpecialCharactorOnKeyPressAction(element,mode,name,e){
	try{
		if(mode == MODE_VIEW)return;
		var strKeyCode = String.fromCharCode(e.keyCode);
		var elementValue = $("[name='"+element+"']").val();
	
		var objRegExp = /[\u003A-\u0040\u005B-\u0060\u007B-\u007E\u0021-\u002F]/g;
		if (!objRegExp.test(elementValue+strKeyCode)) {
			return true;
		}else{
			e.preventDefault();
			return false;
		}
		
	}catch(exception){
		errorException(exception);
	}

}

function THOnKeyPressAction(element,mode,name,e){
	try{
		var fistObjRegExpCondition = /^[\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44]/;
		var objRegExpCondition = /^([\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44])+([\u0E01-\u0E4F\u0E46\u002C\u002E\u002D\u005F\u002F\u0028\u0029\u0025\u002A\u0040\u0024\u0023\u0026\u002B\s\d\w\@\#\$\%\&\*\_(\)\+\/\.\,])+$/;
		var CIDTYPE = $("[name='CID_TYPE']").val();
		if(TH_LAST_NAME == element && CIDTYPE == CIDTYPE_PASSPORT){
			fistObjRegExpCondition = /^[\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44\-]/;
			objRegExpCondition = /^([\u0E01-\u0E2E\u0E40\u0E41\u0E42\u0E43\u0E44\-])+([\u0E01-\u0E4F\u0E46\u002C\u002E\u002D\u005F\u002F\u0028\u0029\u0025\u002A\u0040\u0024\u0023\u0026\u002B\s\d\w\@\#\$\%\&\*\_(\)\+\-\/\.\,])+$/;
		}
		if(mode == MODE_VIEW)return;
		var strKeyCode = String.fromCharCode(e.keyCode);
		var elementValue = $("[name='"+element+"']").val();
		if (elementValue == '' || elementValue.length == 0){
			var fistObjRegExp = fistObjRegExpCondition;
			console.log(fistObjRegExp);
			if (!fistObjRegExp.test(strKeyCode)) {
				e.preventDefault();
				return false;
			}else{
				return true;
			}

		}else{
			var objRegExp = objRegExpCondition;
			console.log(objRegExpCondition);
			if (!objRegExp.test(elementValue+strKeyCode)) {
				e.preventDefault();
				return false;
			}else{
				return true;
			}
		}
	}catch(exception){
		errorException(exception);
	}
}

function NameENOnblurAction(element,mode,name){
	try{
		var objRegExpCondition = /(^[\w\s\d\.\,\@\#\$\%\&\*\_\(\)\+\-\/]*$)/;
		var regExpConditionDash = /^[\-]+$/;
		var regExpConditionCharOrNumBeg = /^[a-zA-Z0-9]+.?/; 
		var regExpConditionNumber = /^[0-9]*$/;
		var CIDTYPE = $("[name='CID_TYPE']").val();
	
		if(mode == MODE_VIEW)return;
		var callBackAction = true;
		var errorFormat = false;
		var objRegExp = objRegExpCondition;
		var elementObject = $("[name='"+element+"']");
		var elementValue = elementObject.val();
		elementValue = elementValue.toUpperCase();
		elementObject.val(elementValue);
		if(elementValue == ''){
			callBackAction = true;
		}else{
			if(!objRegExp.test(elementValue)){
				errorFormat = true;
				callBackAction = false;
			}
			if(TH_LAST_NAME == element && CIDTYPE == CIDTYPE_PASSPORT){
				if(!regExpConditionCharOrNumBeg.test(elementValue) && !regExpConditionDash.test(elementValue)){
					errorFormat = true;
				}
			}else{
				if(!regExpConditionCharOrNumBeg.test(elementValue) || regExpConditionDash.test(elementValue)){
					errorFormat = true;
				}
			}
			if(regExpConditionNumber.test(elementValue)){
				errorFormat = true;
			}
		}
		if(callBackAction && !errorFormat){
			try{
				var ActionJS = eval(name+'ActionJS');
					ActionJS(element,mode,name);
			}catch(exception){}
		}else if(errorFormat && !isEmpty(elementValue)){
			alertBox('ERROR_THAI_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
		}
	}catch(exception){
		errorException(exception);
	}
}

function ENOnblurAction(element,mode,name){
	try{
		var objRegExpCondition = /(^[\w\s\d\.\,\@\#\$\%\&\*\_\(\)\+\-\/]*$)/;
		var regExpConditionDash = /^[\-]+$/;
		var regExpConditionCharOrNumBeg = /^[a-zA-Z0-9]+.?/; 
		var regExpConditionNumber = /^[0-9]*$/;
		var CIDTYPE = $("[name='CID_TYPE']").val();
	
		if(mode == MODE_VIEW)return;
		var callBackAction = true;
		var errorFormat = false;
		var objRegExp = objRegExpCondition;
		var elementObject = $("[name='"+element+"']");
		var elementValue = elementObject.val();
		elementValue = elementValue.toUpperCase();
		elementObject.val(elementValue);
		if(elementValue == ''){
			callBackAction = true;
		}else{
			if(!objRegExp.test(elementValue)){
				errorFormat = true;
				callBackAction = false;
			}
			if(EN_LAST_NAME == element && CIDTYPE == CIDTYPE_PASSPORT){
				if(!regExpConditionCharOrNumBeg.test(elementValue) && !regExpConditionDash.test(elementValue)){
					errorFormat = true;
				}
			}else{
				if(!regExpConditionCharOrNumBeg.test(elementValue) || regExpConditionDash.test(elementValue)){
					errorFormat = true;
				}
			}
			if(regExpConditionNumber.test(elementValue)){
				errorFormat = true;
			}
		}
		if(callBackAction && !errorFormat){
			try{
				var ActionJS = eval(name+'ActionJS');
					ActionJS(element,mode,name);
			}catch(exception){}
		}else if(errorFormat && !isEmpty(elementValue)){
			alertBox('ERROR_ENG_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
		}
	}catch(exception){
		errorException(exception);
	}
}

function ENOnPasteAction(element,mode,name,event){
	try{
		var objRegExpCondition = /(^[\w\s\d\.\,\@\#\$\%\&\*\_\(\)\+\-\/]*$)/;
		var regExpConditionDash = /^[\-]+$/;
		var regExpConditionCharOrNumBeg = /^[a-zA-Z0-9]+.?/; 
		var regExpConditionNumber = /^[0-9]*$/;
		var CIDTYPE = $("[name='CID_TYPE']").val();
	
		if(mode == MODE_VIEW)return;
		var callBackAction = true;
		var errorFormat = false;
		var objRegExp = objRegExpCondition;
		var elementObject = $("[name='"+element+"']");
		var elementValue = elementObject.val();
		elementValue = elementValue.toUpperCase();
		elementObject.val(elementValue);
		if(elementValue == ''){
			callBackAction = true;
		}else{
			if(!objRegExp.test(elementValue)){
				errorFormat = true;
				callBackAction = false;
			}
			if(EN_LAST_NAME == element && CIDTYPE == CIDTYPE_PASSPORT){
				if(!regExpConditionCharOrNumBeg.test(elementValue) && !regExpConditionDash.test(elementValue)){
					errorFormat = true;
				}
			}else{
				if(!regExpConditionCharOrNumBeg.test(elementValue) || regExpConditionDash.test(elementValue)){
					errorFormat = true;
				}
			}
			if(regExpConditionNumber.test(elementValue)){
				errorFormat = true;
			}
		}
		if(callBackAction && !errorFormat){
			try{
				var ActionJS = eval(name+'ActionJS');
					ActionJS(element,mode,name);
			}catch(exception){}
		}else if(errorFormat && !isEmpty(elementValue)){
			alertBox('ERROR_ENG_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
		}
	}catch(exception){
		errorException(exception);
	}
}

function IDNOblurAction(element,mode,name){
	try{
		if(mode == MODE_VIEW)return;
		var callBackAction = true;
		var errorFormat = false;
		var objRegExp = /(^[\w\s\d\.\-]*$)/;
		var elementObject = $("[name='"+element+"']");
		var elementValue = elementObject.val();
		elementValue = elementValue.toUpperCase();
		elementObject.val(elementValue);
		if(elementValue == ''){
			callBackAction = true;
		}else{
			if(!objRegExp.test(elementValue)){
				errorFormat = true;
				callBackAction = false;
			}
			if(! /.*[\w]+.*/.test(elementValue)){
				errorFormat = true;
			}
		}
		if(callBackAction && !errorFormat){
			try{
				var ActionJS = eval(name+'ActionJS');
					ActionJS(element,mode,name);
			}catch(exception){}
		}else if(errorFormat && !isEmpty(elementValue)){
			var CID_TYPE = $("[name='CID_TYPE']").val();
			if(CID_TYPE == CIDTYPE_IDCARD){
				alertBox('ERROR_ID_NO_NATIONAL_ID_WRONG_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
			}else{
				alertBox('ERROR_ID_NO_FORMAT_DESC',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
			}
			
		}
	}catch(exception){
		errorException(exception);
	}
}
function IDNOKeyPressAction(element,mode,name,e){
	try{
		if(mode == MODE_VIEW)return;
		var strKeyCode = String.fromCharCode(e.keyCode);
		var fistObjRegExp=/([\w\s\d\.\-\,\@\#\$\%\&\*\_\(\)\+\-\/])/;
		if (!fistObjRegExp.test(strKeyCode)) {
			e.preventDefault();
			return false;
		} else {
			return true;
		}
	}catch(exception){
		errorException(exception);
	}
}
function ENOnblurAutoCompleteAction(local,element,mode,name){
	try{
		if(mode == MODE_VIEW)return;
		var callBackAction = true;
		var errorFormat = false;
		var objRegExp = /(^[\w\s\d\.\-\(\)]*$)/;
		var elementObject = $("[name='"+element+"']");
		var elementValue = elementObject.val();
		elementObject.val(elementValue);
		if(elementValue == ''){
			callBackAction = true;
		}else{
			if(!objRegExp.test(elementValue)){
				errorFormat = true;
				callBackAction = false;
			}
			if(! /.*[\w]+.*/.test(elementValue)){
				errorFormat = true;
			}
			if(!isNaN(elementValue)){
				errorFormat = true;
			}
		}
		if(callBackAction && !errorFormat){
			try{
				var ActionJS = eval(name+'ActionJS');
				ActionJS(element,mode,name);
			}catch(exception){}
		}else if(errorFormat && !isEmpty(elementValue)){
			if(NATIONALITY_TH==local){
				alertBox('ERROR_THAI_VISA_NAME_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
			}else{
				alertBox('ERROR_ENG_NAME_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
			}
			
		}
	}catch(exception){
		errorException(exception);
	}
}
function ENOnTypeAutoCompleteAction(local,element,mode,name,obj){
	try{
		if(mode == MODE_VIEW)return;
		var callBackAction = true;
		var errorFormat = false;
		var objRegExp = /(^[\w\s\d\.\-]*$)/;
		var elementValue =	obj.$control_input.val();
		if(elementValue == ''){
			callBackAction = true;
		}else{
			if(!objRegExp.test(elementValue)){
				errorFormat = true;
				callBackAction = false;
			}
			if(! /.*[\w]+.*/.test(elementValue)){
				errorFormat = true;
			}
			if(!isNaN(elementValue)){
				errorFormat = true;
			}
		}
		if(callBackAction && !errorFormat){ 
			try{
				var ActionJS = eval(name+'ActionJS');
				ActionJS(element,mode,name);
			}catch(exception){}
		}else if(errorFormat && !isEmpty(elementValue)){
			obj.$control_input.val('').triggerHandler('update');
			obj.refreshOptions();
			/*if(NATIONALITY_TH==local){
				alertBox('ERROR_THAI_VISA_NAME_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
			}else{
				alertBox('ERROR_ENG_NAME_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
			}*/
		}
	}catch(exception){
		errorException(exception);
	}
}

function ENOnKeyPressAction(element,mode,name,e){
	try{
		if(mode == MODE_VIEW)return;
		var strKeyCode = String.fromCharCode(e.keyCode);
		var fistObjRegExp=/([\w\s\d\.\-\,\@\#\$\%\&\*\_\(\)\+\-\/])/;
		if (!fistObjRegExp.test(strKeyCode)) {
			e.preventDefault();
			return false;
		} else {
			return true;
		}
	}catch(exception){
		errorException(exception);
	}
}

function NameENOnKeyPressAction(element,mode,name,e){
	try{
		if(mode == MODE_VIEW)return;
		var strKeyCode = String.fromCharCode(e.keyCode);
		var fistObjRegExp=/([\w\s\d\.\-])/;
		if (!fistObjRegExp.test(strKeyCode)) {
			e.preventDefault();
			return false;
		} else {
			return true;
		}
	}catch(exception){
		errorException(exception);
	}
}
function TextBoxIdnoblurAction(element,mode,name){
	try{
		if(mode == MODE_VIEW)return;
		var callBackAction = true;
		var errorFormat = false;
		var objRegExp = /(^[\d]*$)/;
		var elementObject = $("[name='"+element+"']");
		var elementValue = elementObject.val();
		elementValue = elementValue.toUpperCase();
		elementObject.val(elementValue);
		if(elementValue == ''){
			callBackAction = true;
		}else{
			if(!objRegExp.test(elementValue)){
				errorFormat = true;
				callBackAction = false;
			}
			if(! /.*[\w]+.*/.test(elementValue)){
				errorFormat = true;
			}
		}
		if(callBackAction && !errorFormat){
			try{
				var ActionJS = eval(name+'ActionJS');
					ActionJS(element,mode,name);
			}catch(exception){}
		}else if(errorFormat && !isEmpty(elementValue)){
			alertBox('ERROR_ID_NO_FORMAT_DESC',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
			
		}
	}catch(exception){
		errorException(exception);
	}
}
function TextBoxIdnoKeyPressAction(element,mode,name,e){
	try{
		if(mode == MODE_VIEW)return;
		var strKeyCode = String.fromCharCode(e.keyCode);
		var fistObjRegExp=/\d/;
		if (!fistObjRegExp.test(strKeyCode)) {
			e.preventDefault();
			return false;
		} else {
			return true;
		}
	}catch(exception){
		errorException(exception);
	}
}
function CURRENCYOnblurAction(element,mode,name){
	try{
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
			format = format.replace(/,/g,"");
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
			}catch(exception){}
		}else if(errorFormat && !isEmpty(elementValue)){
			alertBox('ERROR_CURRENCY_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
		}
	}catch(exception){
		errorException(exception);
	}
}
function CURRENCYOnKeyPressAction(element,mode,name,e){
	try{
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
	}catch(exception){
		errorException(exception);
	}
}
function CURRENCYOnKeyUpAction(element,mode,name,e){
	try{
		if(mode == MODE_VIEW)return;
		var keyVal = String.fromCharCode((96 <= e.keyCode && e.keyCode <= 105)? e.keyCode-48 : e.keyCode);
		var elementObject = $("[name='"+element+"']");
		var elementValue = elementObject.val();
		var newArray = elementValue.split(".");
		if(isCurrency(keyVal) || (keyVal=="." && newArray.length == 1)){			
			var format = elementObject.attr('format');			
			if(format == '' || format == null) format = '########0.##';
			   format = format.replace(/,/g,"");
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
	}catch(exception){
		errorException(exception);
	}
}
function CURRENCYOnFocusAction(element,mode,name){
	try{
		if(mode == MODE_VIEW)return;
		var elementObject = $("[name='"+element+"']");
		var elementValue = elementObject.val();
		var end = false;
		while(!end){
			if(elementValue.indexOf(",")<0){
				end =true;
			}else{
				elementValue = elementValue.replace(/,/g,"");
			}
		}
		elementObject.val(elementValue);
		elementObject.select(function(e){
			try{
				e.stopImmediatePropagation();
			}catch(exception){
				errorException(exception);
			}
		});
	}catch(exception){
		errorException(exception);
	}
}

function NUMBEROnblurAction(element,mode,name){
	try{
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
					if(elementValue.split(".").length==1){
						var numberElementValue = (newArray[0])+((newArray[1].length >0)?'.'+newArray[1]:'');
						elementObject.val(numberElementValue);
						callBackAction = true;
					}else{
						var numeralVal = Number(newArray[0]);
						if(newArray[0].length > 15){
							numeralVal = newArray[0];
						}
						var numberElementValue = numeralVal+((newArray[1].length >0)?'.'+newArray[1]:'');
						elementObject.val(numberElementValue);
						callBackAction = true;
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
			}catch(exception){}
		}else if(errorFormat && !isEmpty(elementValue)){
			alertBox('ERROR_NUMBER_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
		}
	}catch(exception){
		errorException(exception);
	}
}
function NUMBEROnKeyPressAction(element,mode,name,e){
	try{
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
	}catch(exception){
		errorException(exception);
	}
}
function NUMBEROnKeyUpAction(element,mode,name,e){
	try{
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
	}catch(exception){
		errorException(exception);
	}
}
function NUMBEROnFocusAction(element,mode,name){	
	try{
		if(mode == MODE_VIEW)return;
		$("[name='"+element+"']").select(function(e){
			try{
				e.stopImmediatePropagation();
			}catch(exception){
				errorException(exception);
			}
		});
	}catch(exception){
		errorException(exception);
	}
}

function EMAILOnblurAction(element,mode,name){
	try{
		if(mode == MODE_VIEW)return;
		var errorFormat = false;
		var callBackAction = true;
		var elementValue = $("[name='"+element+"']").val();
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
		if(callBackAction && !errorFormat){
			try{
				var ActionJS = eval(name+'ActionJS');
					ActionJS(element,mode,name);
			}catch(exception){}
		}else if(errorFormat && !isEmpty(elementValue)){
			alertBox('ERROR_EMAIL_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
		}
	}catch(exception){
		errorException(exception);
	}
}

function TELOnblurAction(element,mode,name){
	try{
		if(mode == MODE_VIEW)return;
		var callBackAction = true;
		var errorFormat = false;
		var pattern = "9-9999-9999";
		$("[name='"+element+"']").mask(pattern);
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
			}catch(exception){}
		}else if(errorFormat && !isEmpty(elementValue)){
			alertBox('ERROR_TEL_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
		}
	}catch(exception){
		errorException(exception);
	}	
}
function TELOnFocusAction(element,mode,name){	
	try{
		if(mode == MODE_VIEW)return;
		$("[name='"+element+"']").select(function(e){
			try{
				e.stopImmediatePropagation();
			}catch(exception){
				errorException(exception);
			}
		});
	}catch(exception){
		errorException(exception);
	}
}
function TELOnKeyPressAction(element,mode,name,e){
	try{
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
	}catch(exception){
		errorException(exception);
	}
}

function MobileOnblurAction(element,mode,name){
	try{
		if(mode == MODE_VIEW)return;
		var callBackAction = true;
		var errorFormat = false;
		var pattern = "99-9999-9999";
		$("[name='"+element+"']").mask(pattern);
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
			}catch(exception){}
		}else if(errorFormat && !isEmpty(elementValue)){
			alertBox('ERROR_MOBILE_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
		}
	}catch(exception){
		errorException(exception);
	}
}
function MobileOnFocusAction(element,mode,name){	
	try{
		if(mode == MODE_VIEW)return;
		$("[name='"+element+"']").select(function(e){
			try{
				e.stopImmediatePropagation();
			}catch(exception){
				errorException(exception);
			}
		});
	}catch(exception){
		errorException(exception);
	}
}
function MobileOnKeyPressAction(element,mode,name,e){
	try{
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
	}catch(exception){
		errorException(exception);
	}
}


function ExtOnblurAction(element,mode,name){
	try{
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
			}catch(exception){
				errorException(exception);
			}
		}else if(errorFormat && !isEmpty(elementValue)){
			alertBox('ERROR_EXT_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
		}
	}catch(exception){
		errorException(exception);
	}
}
function ExtOnFocusAction(element,mode,name){	
	try{
		if(mode == MODE_VIEW)return;
		$("[name='"+element+"']").select(function(e){
			try{
				e.stopImmediatePropagation();
			}catch(exception){
				errorException(exception);
			}
		});
	}catch(exception){
		errorException(exception);
	}
}
function ExtOnKeyPressAction(element,mode,name,e){
	try{
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
	}catch(exception){
		errorException(exception);
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

function loadIframeDialog(url,title){	
	try{
		var options = {
			    url: url,
			    title:title,
			    loading:true
			};
		eModal.iframe(options);
		Pace.unblockFlag = true;
		Pace.unblock();
	}catch(exception){
		errorException(exception);
	}
}

function loadIframeManualDialog(options,sectionId){	
	try{
		eModal.iframe(options,'',sectionId);
		Pace.unblockFlag = true;
		Pace.unblock();
	}catch(exception){
		errorException(exception);
	}
}

function loadPopupDialog(formId,mode,rowId,str,handleForm,width,height){
	var dialogInfo = new DialogInfo(formId,JS_FORM_TYPE_POPUP_DIALOG);
	try{
		if(formId == '' || formId == undefined){
			return false;
		}else if(lookupDialogInfos(dialogInfo)){
			return false;
		}
		console.log('loadPopupDialog >>> default : DIALOG_INFOS : '+JSON.stringify(DIALOG_INFOS));
		if(DIALOG_INFOS != undefined && DIALOG_INFOS.dialog != undefined){
			DIALOG_INFOS.dialog.push(dialogInfo);
		}
		console.log('loadPopupDialog >>> modified : DIALOG_INFOS : '+JSON.stringify(DIALOG_INFOS));
		Pace.block();
		if (handleForm == '' || handleForm == undefined) {
			handleForm = 'N';
		}
		var autoHightFlag=FLAG_YES;
		if(""!=height && null!=height && undefined !=height){
			autoHightFlag = FLAG_NO;
		}
		var dialogId = 'form_'+formId+'_dialog';
		var $data  = 'formId='+formId;
			$data += '&mode='+mode;
			$data += '&rowId='+rowId;
			$data += '&action=LoadPopupForm&ajaxAction=Y&handleForm=' + handleForm;
			$data += '&'+str;
			$data += '&dialogId='+dialogId;
			$data += '&autoHightFlag='+autoHightFlag;
		var url = CONTEXT_PATH+'/FrontController';
		if(width == undefined){
			width = '97%';
		}	
		$.post(url,$data,function(data,status,xhr){
			try{
//				$('#'+dialogId).html(data);
//				$('#'+dialogId).dialog('open');
				var dlgprop = {
						nl2br: false, // For disable auto <br/> from NL
						title: false, // Title bar
						closeByBackdrop: false,
						formId: formId,
						message: '<div id="'+dialogId+'" class="row formDialog"><div class="col-xs-12">'+data+'</div></div> <input type="hidden" name=autoHightFlag value="'+autoHightFlag+'"  property="hidden">', // Body
						onshow: function(dialogInstance) {
							try{
								var funcJS = eval(formId+'AfterLoadActionJS');
									funcJS();
							}catch(exception){
							}
						},
						onshown: function(dialogInstance) {
							try{
								var funcJS = eval(formId+'AfterLoadPopupFinishActionJS');
									funcJS();
							}catch(exception){
							}
						},
						onhide: function(dialogInstance) {
							
						},
						onhidden: function(dialogInstance) {

						},
						buttons: [{
			                id:"btnHotkeyCode",
			                hotkey: 27, // Keycode of keyup event of key 'A' is 65.
			                action: function() {
			                	var formName = getPopupFormName();
			            		var formId = getPopupFormId();
			            		closePopupDialog(formId,POPUP_ACTION_CLOSE);
			                }
			            }]
					};
				openBootstrapDialog(dlgprop,'right',width, height);
			}catch(exception){
				if(DIALOG_INFOS != undefined && DIALOG_INFOS.dialog != undefined){
					removeDialogInfos(dialogInfo);
				}
				errorException(exception);
			}
		}).fail(function(data,status,xhr){
			if(DIALOG_INFOS != undefined && DIALOG_INFOS.dialog != undefined){
				removeDialogInfos(dialogInfo);
			}
			errorAjax(data,status,xhr);
		});
	}catch(exception){
		if(DIALOG_INFOS != undefined && DIALOG_INFOS.dialog != undefined){
			removeDialogInfos(dialogInfo);
		}
		errorException(exception);
	}
}
function closePopupDialog(formId,action){
	try{
		Pace.block();
		var dialogId = 'form_'+formId+'_dialog';
		var $data  = 'className=com.eaf.orig.ulo.app.view.util.manual.ControlFormHandle';
		var url = CONTEXT_PATH+'/Ajax';
		$.post(url,$data,function(data,status,xhr){
			try{
				Pace.unblockFlag = true;
				Pace.unblock();
				if(responseSuccess(data,status,xhr)){
					var responseData = getResponseData(data);
					$('#formActionName').val(responseData);
					var modal = $('#'+dialogId).data('modal');
						modal.close();
					try{			
						var funcJS = eval(formId+'AfterCloseActionJS');
							funcJS(action);
//							## Graph Comment For open popup save & close data in masterform lose
//							refreshMasterForm();
					}catch(exception){}
					
					//focus next element when close popup	
					if(POPUP_ACTION_CLOSE!=action){ 
						autoTabNextElement(CURRENT_POPUP_FIELD_NAME);
					}
				}
			}catch(exception){
				errorException(exception);
			}
			var dialogInfo = new DialogInfo(formId,JS_FORM_TYPE_POPUP_DIALOG);
			if(DIALOG_INFOS != undefined && DIALOG_INFOS.dialog != undefined){
				removeDialogInfos(dialogInfo);
			}
		}).fail(function(data,status,xhr){
			errorAjax(data,status,xhr);
		});
	}catch(exception){
		errorException(exception);
	}
}

function autoTabNextElement(currentElement){
	focusElementActionJS(currentElement, $('[name="'+currentElement+'"]').val());
	var focussableElements = 'a:not([disabled]), button:not([disabled]), input[type=text]:not([disabled]), [tabindex]:not([disabled]):not([tabindex="-1"])';
   if (document.activeElement && document.activeElement.form) {
        var focussable = Array.prototype.filter.call(document.activeElement.form.querySelectorAll(focussableElements),
        function (element) {
            //check for visibility while always include the current activeElement 
            return element.offsetWidth > 0 || element.offsetHeight > 0 || element === document.activeElement;
        });
        var index = focussable.indexOf(document.activeElement); 
        focussable[index + 1].focus();
    }
}



function savePopupDialog(formId,str){
	try{
		Pace.block();
		validateFormDialog(formId,str);
	}catch(exception){
		errorException(exception);
	}
}
function validateFormDialog(formId,str){
	try{
		Pace.block();
		var handleForm = 'Y';
		var	validateForm = 'Y';
		$('#'+formId+'ErrorFormHandlerManager').html('');
		$('#'+formId).removeClass('danger alert-danger');
		var dialogId = 'form_'+formId+'_dialog';
		var $data  = $('#'+dialogId+' *').serialize();
			$data += '&className=com.eaf.orig.ulo.app.view.util.manual.MandatoryForm';
			$data += '&handleForm='+handleForm;
			$data += '&validateForm='+validateForm;
		var url = CONTEXT_PATH+'/Ajax';
		$.post(url,$data,function(data,status,xhr){
			try{
//				#rawi:After MandatoryForm set handleForm=N,validateForm=N
				handleForm = 'N';
				validateForm = 'N';
				if(responseSuccess(data,status,xhr)){
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
			}catch(exception){
				errorException(exception);
			}
		}).fail(function(data,status,xhr){
			errorAjax(data,status,xhr);
		});
	}catch(exception){
		errorException(exception);
	}
	return true;
}
//MOVE TO CONSTANT
//var DISPLAY_ERROR_SCREEN = 'SCREEN';
//var DISPLAY_ERROR_ALERT = 'ALERT';
//var DISPLAY_ERROR_POPUP = 'POPUP';

function validateFormAction(validateId,str,callBack,displayError){
	try{
		Pace.block();
		if(displayError == undefined){
			displayError = DISPLAY_ERROR_ALERT;
		}
		if(DISPLAY_ERROR_POPUP == displayError){
//          Chatmongkol Vong-aek :21/10/2016 Change for get lastPopupFormId
//			var popupFormId = $(".PopupFormHandlerManager input[name=formId]").val(); 
			var popupFormId = getPopupFormId();
			$('#'+popupFormId+'ErrorFormHandlerManager').html('');
			$('#'+popupFormId).find('.has-error').removeClass('has-error');			
			$('#'+popupFormId+'.form-group').removeClass('has-error');
			$('#'+popupFormId+'.InputField').removeClass('has-error');
		}else{
			$('.notifyHandlerManager').html('');
			$('#appFormName').removeClass('danger alert-danger');
			$('.form-group').removeClass('has-error');
			$('.InputField').removeClass('has-error');
		}
		var $data  = '&className=com.eaf.orig.ulo.app.view.util.manual.ValidateFormAction';
			$data += '&validateId='+validateId;
			$data += '&'+str;
		var url = CONTEXT_PATH+'/Ajax';
		$.post(url,$data,function(data,status,xhr){
			try{
				Pace.unblockFlag = true;
				Pace.unblock();
				if(responseSuccess(data,status,xhr)){
					var responseData = getResponseData(data);
					if(responseData == '' || responseData == undefined){			
						callBackActionJS(validateId,'AfterValidateFormActionJS',callBack,responseData);
					}else{			
						var $error = $.parseJSON(responseData);
						if(validateFormDataObject($error,'DISPLAY_ERROR_TYPE')){
							displayErrorMsg(getDataFormObject($error,'FORM_ID'),getDataFormObject($error,'ERROR'));
						}else if(validateFormDataObject($error,'ERROR')){				
							displayErrorValidateFormAction(responseData,displayError);
						}else{
							clearErrorValidateFormAction(responseData);
							callBackActionJS(validateId,'AfterValidateFormActionJS',callBack,responseData);
						}
					}
				}
			}catch(exception){
				errorException(exception);
			}
		}).fail(function(data,status,xhr){
			errorAjax(data,status,xhr);
		});
	}catch(exception){
		errorException(exception);
	}
}

function callBackActionJS(callBackId,callBackJS,callBack,data){
	if(callBack != undefined){
		try{
			new callBack();
		}catch(exception){
		}
	}else{
		try{
			var funcJS = eval(callBackId+callBackJS);
			funcJS(data);
		}catch(exception){
		}
	}	
}
function validateFormDataObject($error,typeJson){
	var validateForm = false;
	try{
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
	}catch(exception){
		errorException(exception);
	}
	return validateForm;
}

function getDataFormObject($error,typeJson){
	var dataObject = "";
	try{
		if($error != undefined){
			$.map($error, function(item){
				var type = item.id;
				if(type == typeJson){
					if(item.value != undefined && item.value  != ''){
						dataObject = item.value;
					}
				}
			});
		}
	}catch(exception){
		errorException(exception);
	}
	return dataObject;
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
				}catch(exception){
				}
			}
		});
	}catch(exception){
		errorException(exception);
	}
}
function displayErrorValidateFormAction(data,displayError){
	try{
		Pace.unblockFlag = true;
		Pace.unblock();
		var $error = $.parseJSON(data);	
		var elements = [];
		$.map($error, function(item){
			var type = item.id;
			if(type == 'ERROR'){
				if(displayError != undefined && (displayError == DISPLAY_ERROR_SCREEN || displayError == DISPLAY_ERROR_POPUP)){
					var errorElement = "<div class='alert alert-danger alert-dismissible fade in' role='alert'>";
						errorElement += "<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>x</span></button>";
						errorElement += "<div>"+item.value+"</div>";
						errorElement += "</div>";
					if(DISPLAY_ERROR_POPUP == displayError){
//						chage for get last popUpId
//						var popupFormId = $(".PopupFormHandlerManager input[name=formId]").val();
						var popupFormId = getPopupFormId();
						$('#'+popupFormId+'ErrorFormHandlerManager').append(errorElement);
						$('#'+popupFormId+'ErrorFormHandlerManager')[0].scrollIntoView(true);
					}else{
						$('.notifyHandlerManager').append(errorElement);
						$('.notifyHandlerManager')[0].scrollIntoView(true);
					}
				}else{
					alertBox(item.value);
				}
			}else if(type == 'ELEMENT'){
				try{
					elements = $.parseJSON(item.value);
					errorElementValidateForm(elements);
				}catch(exception){}
			}else if(type == 'SUCCESS'){
				try{
					elements = $.parseJSON(item.value);
					successElementValidateForm(elements);
				}catch(exception){}
			}
		});
	}catch(exception){
		errorException(exception);
	}
}

function errorElementValidateForm(elements){
	try{
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
	}catch(exception){
		errorException(exception);
	}
}

function successElementValidateForm(elements){
	try{
		$.map(elements, function(item){
			var elementId = item.id;
			var formElement = $("[name='"+elementId+"']");
			if(formElement != undefined && formElement.attr('name') != undefined){
				formElement.closest('.form-group').removeClass('has-error');
				formElement.closest('.InputField').removeClass('has-error');
			}else{
				var labelElement = $("#LabelField_"+elementId);
				if(labelElement != undefined && labelElement.hasClass('LabelField')){
					labelElement.closest('.form-group').removeClass('has-error');
					labelElement.closest('.InputField').removeClass('has-error');
				}
			}		
		});
	}catch(exception){
		errorException(exception);
	}
}

function displayErrorMsg(formId,errorMsg){
	try{
		Pace.unblockFlag = true;
		Pace.unblock();
		$('#'+formId+'ErrorFormHandlerManager').html('');
		$('#'+formId+' .form-group').removeClass('has-error');
		$('#'+formId+' .InputField').removeClass('has-error');
		var errorElement = "<div class='alert alert-danger alert-dismissible fade in' role='alert'>";
			errorElement += "<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>x</span></button>";
			errorElement += "<div>"+errorMsg+"</div>";
			errorElement += "</div>";
		$('#'+formId+'ErrorFormHandlerManager').append(errorElement);
		$('#'+formId+'ErrorFormHandlerManager')[0].scrollIntoView(true);
	}catch(exception){
		errorException(exception);
	}
}
function displayWarnMsg(formId,warnMsg){
	try{
		Pace.unblockFlag = true;
		Pace.unblock();
		$('#'+formId+'ErrorFormHandlerManager').html('');
		$('#'+formId+' .form-group').removeClass('has-error');
		$('#'+formId+' .InputField').removeClass('has-error');
		var warnElement = "<div class='alert alert-warning alert-dismissible fade in' role='alert'>";
			warnElement += "<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>x</span></button>";
			warnElement += "<div>"+warnMsg+"</div>";
			warnElement += "</div>";
		$('#'+formId+'ErrorFormHandlerManager').append(warnElement);
		$('#'+formId+'ErrorFormHandlerManager')[0].scrollIntoView(true);
	}catch(exception){
		errorException(exception);
	}
}
function displayMandatoryForm(formId,str,data){
	Pace.unblockFlag = true;
	Pace.unblock();
	try{
		var response = {};
		$('#'+formId+'ErrorFormHandlerManager').html('');
		$('#'+formId+' .form-group').removeClass('has-error');
		$('#'+formId+' .InputField').removeClass('has-error');
		var $error = $.parseJSON(data);		
		$.map($error, function(item){
			var type = item.id;
			if(type == 'ERROR'){
				var errorElement = "<div class='alert alert-danger alert-dismissible fade in' role='alert'>";
					errorElement += "<button type='button' class='close' data-dismiss='alert' aria-label='Close'><span aria-hidden='true'>x</span></button>";
					errorElement += "<div>"+item.value+"</div>";
					errorElement += "</div>";
				$('#'+formId+'ErrorFormHandlerManager').append(errorElement);
				$('#'+formId+'ErrorFormHandlerManager')[0].scrollIntoView(true);
			}else if(type == 'ELEMENT'){
				try{
					var elements = $.parseJSON(item.value);
					errorElementForm(elements,formId);
				}catch(exception){}
			}else if(type == 'CLEAR_ELEMENT'){
				try{
					var elements = $.parseJSON(item.value);
					clearValueElementForm(elements,formId);
				}catch(exception){}
			}else if(type == 'ERROR_HIGHLIGHT'){
				try{
					var elements = $.parseJSON(item.value);
					errorHighLightElementForm(elements,formId);
				}catch(exception){}
			}else if(type == 'RESPONSE'){
				try{
					response = item.value;
				}catch(exception){}
			}
		});
		var funcJS = eval(formId+'MandatoryActionJS');
			funcJS(response,formId);
	}catch(exception){
		errorException(exception);
	}
}
function errorHighLightElementForm(elements,formId){
	try{
		$.map(elements, function(item){
			var elementId = item.id;
			var formElement = $("#"+formId+" #"+elementId);
			if(formElement != undefined){
				var tagElement = formElement.prop("tagName").toLowerCase();
			    switch(tagElement) {
					case "table":formElement.addClass('danger'); break;
					case "div":formElement.addClass('alert-danger'); break;
					case "td":formElement.addClass('danger'); break;
					case "tr":formElement.addClass('danger'); break;
					case "span":break;
			    }
			}
		});
	}catch(exception){
		errorException(exception);
	}
}
function errorElementForm(elements,formId){
	try{
		$.map(elements, function(item){
			var elementId = item.id;
			var formElement = $("#"+formId+" [name='"+elementId+"']");
			if(formElement != undefined && formElement.attr('name') != undefined){
				try{
					var tagName = '';
					try{
						tagName = formElement.parents().parents().prop("tagName");
					}catch(exception){}
					if(tagName != undefined && tagName.toLowerCase() == 'td'){
						formElement.closest('.InputField').addClass('has-error');
					}else if(formElement.closest('.form-group').parents().attr('class') != undefined){
						formElement.closest('.form-group').addClass('has-error');
					}else{
						formElement.closest('.InputField').addClass('has-error');
					}
				}catch(exception){
					if(formElement.closest('.form-group').parents().attr('class') != undefined){
						formElement.closest('.form-group').addClass('has-error');
					}else{
						formElement.closest('.InputField').addClass('has-error');
					}
				}			
			}else{
				var labelElement = $("#"+formId+" #LabelField_"+elementId);
				if(labelElement != undefined && labelElement.hasClass('LabelField')){
					labelElement.closest('.form-group').addClass('has-error');
				}
			}		
		});
	}catch(exception){
		errorException(exception);
	}
}
function clearValueElementForm(elements,formId){
	try{
		$.map(elements, function(item){
			try{
				var elementId = item.id;
				var formElement = $("#"+formId+" [name='"+elementId+"']");
				if(formElement != undefined && formElement.attr('name') != undefined){
					if(formElement.closest('.form-group').attr('class') != undefined && "POPUP_OTH_SAVING_ACCT_FORM"!=formId){
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
				displayHtmlElement(elementId,'');
			}catch(exception){
				errorException(exception);
			}
		});
	}catch(exception){
		errorException(exception);
	}
}
function savePopupFormAction(formId,str,handleForm,validateForm){
	try{
		Pace.block();
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
			$data += '&action=SavePopupForm&ajaxAction=Y&handleForm='+handleForm;
			$data += '&'+str;
			$data += '&'+validateForm;
		var url = CONTEXT_PATH+'/FrontController';
		$.post(url,$data,function(data,status,xhr){
			try{
				Pace.unblockFlag = true;Pace.unblock();
				var funcJS = eval(formId+'AfterSaveActionJS');
				funcJS(data,formId);
			}catch(exception){
				closePopupDialog(formId,'');
				errorException(exception);
			}
		});
	}catch(exception){
		errorException(exception);
	}	
}

function saveApplicationForm(formId,action,handleForm,validateForm){
	try{
		Pace.block();
		if(handleForm == undefined){
			handleForm = 'N';
		}
		if(validateForm == undefined){
			validateForm = 'N';
		}
		validateSaveForm(formId,action,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}
function validateSaveForm(formId,action,handleForm,validateForm){
	try{
		Pace.block();
		$('#'+formId+'ErrorFormHandlerManager').html('');
		$('#'+formId+' .form-group').removeClass('has-error');
		$('#'+formId+' .InputField').removeClass('has-error');
		$("[name=buttonAction]").val(action);
		var $data  = $('#appFormName').serialize();
			$data += '&formId='+formId;
			$data += '&className=com.eaf.orig.ulo.app.view.util.manual.MandatoryForm';
			$data += '&validateForm='+validateForm;
//			$data += '&buttonAction='+action;
		var url = CONTEXT_PATH+'/Ajax';
		$.post(url,$data,function(data,status,xhr){
			try{
//				#rawi:After MandatoryForm set handleForm=N,validateForm=N
				handleForm = 'N';
				validateForm = 'N';
				if(responseSuccess(data,status,xhr)){
					var responseData = getResponseData(data);
					if(responseData == '' || responseData == undefined){
						processFormAction(formId,action,handleForm,validateForm);
					}else{
						var $error = $.parseJSON(responseData);
						if(validateFormDataObject($error,'ERROR')){
							var str = '';
							displayMandatoryForm(formId,str,responseData);
						}else{
							processFormAction(formId,action,handleForm,validateForm);
						}
					}
				}
			}catch(exception){
				errorException(exception);
			}
		}).fail(function(data,status,xhr){
			errorAjax(data,status,xhr);
		});
	}catch(exception){
		errorException(exception);
	}
}

function processApplicationFormAction(formId,action,handleForm,validateForm,callBack){
	try{
		Pace.block();
		$('#'+formId+'ErrorFormHandlerManager').html('');
		$('#'+formId+' .form-group').removeClass('has-error');	
		$('#'+formId+' .InputField').removeClass('has-error');
		var $data  = $('#'+formId+' *').serialize();
			$data += '&formId='+formId;
			$data += '&className=com.eaf.orig.ulo.app.view.util.manual.MandatoryForm';
			$data += '&validateForm='+validateForm;
			$data += '&buttonAction='+action;
		var url = CONTEXT_PATH+'/Ajax';
		$.post(url,$data,function(data,status,xhr){
			Pace.unblockFlag = true;
			Pace.unblock();
			try{
				if(responseSuccess(data,status,xhr)){
					var responseData = getResponseData(data);
					if(responseData == '' || responseData == undefined){
						if(callBack != undefined){
							new callBack(responseData,status,xhr,action);
						}
					}else{
						var $error = $.parseJSON(responseData);
						if(validateFormDataObject($error,'ERROR')){
							var str = '';
							displayMandatoryForm(formId,str,responseData);
						}else{
							if(callBack != undefined){
								new callBack(responseData,status,xhr,action);
							}
						}
					}
				}
			}catch(exception){
				errorException(exception);
			}
		}).fail(function(data,status,xhr){
			errorAjax(data,status,xhr);
		});
	}catch(exception){
		errorException(exception);
	}
}

function processHeaderSectionAction(formId,action,handleForm,validateForm,externalData,callBack){
	try{
		Pace.block();
		$('#'+formId+'ErrorFormHandlerManager').html('');
		$('#'+formId+' .form-group').removeClass('has-error');	
		$('#'+formId+' .InputField').removeClass('has-error');
		var $data  = $('#'+formId+' *').serialize();
			$data += '&formId='+formId;
			$data += '&className=com.eaf.orig.ulo.app.view.util.manual.MandatoryForm';
			$data += '&validateForm='+validateForm;
			$data += '&buttonAction='+action;
			$data += '&'+externalData;
		var url = CONTEXT_PATH+'/Ajax';
		$.post(url,$data,function(data,status,xhr){
			Pace.unblockFlag = true;
			Pace.unblock();
			try{
				if(responseSuccess(data,status,xhr)){
					var responseData = getResponseData(data);
					if(responseData == '' || responseData == undefined){
						if(callBack != undefined){
							new callBack(responseData,status,xhr,action);
						}
					}else{
						var $error = $.parseJSON(responseData);
						if(validateFormDataObject($error,'ERROR')){
							var str = '';
							displayMandatoryForm(formId,str,responseData);
						}else{
							if(callBack != undefined){
								new callBack(responseData,status,xhr,action);
							}
						}
					}
				}
			}catch(exception){
				errorException(exception);
			}
		}).fail(function(data,status,xhr){
			errorAjax(data,status,xhr);
		});
	}catch(exception){
		errorException(exception);
	}
}

function saveApplicationFormAction(formId,action,handleForm,validateForm){
	try{
		if(action == BUTTON_ACTION_DECISION){
			decisionFormAction(formId,action,handleForm,validateForm);
		}else{
			Pace.block();
			$('#action').val('SaveApplication');
			$('#handleForm').val(handleForm);	
			$("[name='buttonAction']").val(action);
			$('#appFormName').submit();
		}
	}catch(exception){
		errorException(exception);
	}
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
							if("CH"==MANUAL_DECISION){
								var $data ="";
								ajax('com.eaf.orig.ulo.app.view.util.ajax.DecisionProcess',$data,function(){
									 dialog.close();
									 LoadRefreshFormAction('');
								});
								
							}else{
								saveApplicationFormAction(formId,action,handleForm,validateForm);
							    dialog.close();
							}

					   }
				   }
				],
				onhide: function(dialogInstance) {
				}
			};
		openBootstrapDialog(dlgprop,'auto','600px');
	});
}
 
function processFormAction(formId,action,handleForm,validateForm){
	Pace.block();
	try{
		if(action == BUTTON_ACTION_SUBMIT && lookup(SENSITIVE_ROLE_ACTION,ROLE_ID) && lookup(SENSITIVE_FORM_ACTION,formId)){
			var $data  = 'className=com.eaf.orig.ulo.app.view.util.manual.SensitiveFieldAction';
				$data += '&formId='+formId;
			var url = CONTEXT_PATH+'/Ajax';
			$.post(url,$data,function(data,status,xhr){
				try{
					if(responseSuccess(data,status,xhr)){
						var responseData = getResponseData(data);
						if("SUCCESS" == responseData || responseData == '' || responseData == null || responseData == undefined) {
							if(!authorizedApplicationAction(formId,action,handleForm,validateForm)){
								applicationActionService(DECISION_SERVICE_ACTION,DecisionAfterActionJS,formId,action,handleForm,validateForm);
							}
						}else{
							if(PRODUCT_ELEMENT == responseData){
								confirmDangerBox(CONFIRM_DELETE_APPLICATION,confirmRekeyApplication);
							}else{
								loadPopupDialog(responseData,'INSERT','0','');	
							}
						}
					}
				}catch(exception){
					errorException(exception);
				}
			}).fail(function(data,status,xhr){
				errorAjax(data,status,xhr);
			});
		}else if(action == BUTTON_ACTION_SUBMIT && lookup(SUMMARY_SCREEN_ROLE_ACTION,ROLE_ID)){
			var $data  = 'className=com.eaf.orig.ulo.app.view.util.manual.SummaryPageAction';
				$data += '&callEscalateFlag='+FLAG_NO;
			var url = CONTEXT_PATH+'/Ajax';
			$.post(url,$data,function(data,status,xhr){ 
				try{
					if(responseSuccess(data,status,xhr)){
						var responseData = getResponseData(data);
						var summary = $.parseJSON(responseData);
						var popupFormId = summary.formId; 
						var callFico = summary.isCallFico;
						if(FLAG_YES ==callFico){
							if(!authorizedApplicationAction(formId,action,handleForm,validateForm)){
								applicationActionService(DECISION_SERVICE_ACTION,DecisionAfterActionJS,formId,action,handleForm,validateForm);
							}
						}else{
							if(""!=popupFormId && null!=popupFormId){
								loadPopupDialog(popupFormId,'INSERT','0','');
							}else {
								if(!authorizedApplicationAction(formId,action,handleForm,validateForm)){
									applicationActionService(DECISION_SERVICE_ACTION,DecisionAfterActionJS,formId,action,handleForm,validateForm);
								}
							}
						}
					}
				}catch(exception){
					errorException(exception);
				}
			}).fail(function(data,status,xhr){
				errorAjax(data,status,xhr);
			});
		}else if(action == BUTTON_ACTION_SUBMIT){
			if(!authorizedApplicationAction(formId,action,handleForm,validateForm)){
				applicationActionService(DECISION_SERVICE_ACTION,DecisionAfterActionJS,formId,action,handleForm,validateForm);
			}
		}else{
			if(!authorizedApplicationAction(formId,action,handleForm,validateForm)){
				saveApplicationFormAction(formId,action,handleForm,validateForm);
			}
		}
	}catch(exception){
		errorException(exception);
	}
}
function processFormActionIncome(formId,action,handleForm,validateForm){
	//Pace.block();
	try{
		decisionApplicationActionIncome(DECISION_SERVICE_ACTION,DecisionAfterActionJS,formId,action,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}
function confirmRekeyApplication(confirmFlag){//alert('confirmFlag : '+confirmFlag);
	if(confirmFlag == FLAG_YES){
		var className = 'com.eaf.orig.ulo.app.view.util.ajax.DeleteApplication';
		ajax(className,'',refreshMasterForm);
	}
}
function authorizedApplicationAction(formId,action,handleForm,validateForm){
	Pace.block();
	var authorizedApplication = false;
	try{
		var $data  = 'className=com.eaf.orig.ulo.control.util.AuthorizedApplicationAction';
			$data += '&buttonAction='+action;
		var url = CONTEXT_PATH+'/Ajax';
		$.ajax({
	      url:url,
	      data:$data,
	      async: false,  
	      success:function(data,status,xhr) {
	    	  try{
		    	  if(responseSuccess(data,status,xhr)){
						var responseData = getResponseData(data);
						var authorizedData = $.parseJSON(responseData);
				   		if(authorizedData.fraudFlag == 'Y'){
				    		authorizedApplication = true;
					    	alertBox('ERROR_APPLICATION_SEND_TO_FRAUD',null,action);
					    }else if(authorizedData.blockedFlag =='Y'){
				    		authorizedApplication = true;
				    		alertBox('ERROR_APPLICATION_IS_BLOCKED',null,action);
				    	}
		    	  }
	    	 }catch(exception){
	    		errorException(exception);
	    	 }
	      },
	      error:function(data,status,xhr){
	    	  errorAjax(data,status,xhr);
	      }
		});
	}catch(exception){
		errorException(exception);
	}
	return authorizedApplication;
}

function authorizedApplicationListAction(action){
	Pace.block();
	var authorizedApplication = false;
	try{
		var $data  = 'className=com.eaf.orig.ulo.control.util.CheckFraudApplicationAction';
			$data += '&buttonAction='+action;
		var url = CONTEXT_PATH+'/Ajax';
		$.ajax({
	      url:url,
	      data:$data,
	      async: false,  
	      success:function(data,status,xhr) {
	    	  try{
		    	  if(responseSuccess(data,status,xhr)){
						var responseData = getResponseData(data);
						var authorizedData = $.parseJSON(responseData);
				   		if('Y'==authorizedData.fruadFlag){
				    		authorizedApplication = true;
					    	alertBox(authorizedData.fraudAplicationText);
					    } 
		    	  }
	    	 }catch(exception){
	    		errorException(exception);
	    	 }
	      },
	      error:function(data,status,xhr){
	    	  errorAjax(data,status,xhr);
	      }
		});
	}catch(exception){
		errorException(exception);
	}
	return authorizedApplication;
}

function ERROR_APPLICATION_SEND_TO_FRAUDAfterAlertActionJS(action){
	try{
		var formId = $("#FormHandlerManager [name='formId']").val();
		var action = action; //BUTTON_ACTION_SAVE;
		var handleForm = 'N';
		var validateForm = 'N';
		saveApplicationFormAction(formId,action,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}
function ERROR_APPLICATION_IS_BLOCKEDAfterAlertActionJS(action){
	try{
		var formId = $("#FormHandlerManager [name='formId']").val();
		var action = action; //BUTTON_ACTION_SAVE;
		var handleForm = 'N';
		var validateForm = 'N';
		saveApplicationFormAction(formId,action,handleForm,validateForm);
	}catch(exception){
		errorException(exception);
	}
}

function applicationActionService(functionId,callBack,formId,action,handleForm,validateForm){
	if(CALL_DECISION_SERVICE_FLAG=="Y"){
		decisionApplicationAction(functionId,callBack,formId,action,handleForm,validateForm);
	 }else{
		 ficoApplicationAction(functionId,callBack,formId,action,handleForm,validateForm);
	 }
}
function decisionApplicationAction(functionId,callBack,formId,action,handleForm,validateForm){
	Pace.block();
	var $data = 'className=com.eaf.orig.ulo.app.view.util.decisionservice.DecisionApplicationAction';
		if(formId != undefined && formId != ''){
			$data += '&' + $('#'+formId+' *').serialize();
			$data += '&formId='+formId;
		}
		$data += '&validateForm='+validateForm;
		$data += '&buttonAction='+action;
		$data += '&functionId='+functionId;
	var url = CONTEXT_PATH+'/Ajax';
	$.post(url,$data,function(data,status,xhr){
		try{
			if (responseSuccess(data)) {
				var responseData = getResponseData(data);
				var decisionApplication = $.parseJSON(responseData); 
				if(decisionApplication != undefined){
					var resultCode = decisionApplication.resultCode;
					if(resultCode == SUCCESS_CODE || resultCode == NO_ACTION){
						if(decisionApplication.fraudFlag == 'Y'){
					    	alertBox('ERROR_APPLICATION_SEND_TO_FRAUD',null,action);
					    }else if(decisionApplication.blockedFlag =='Y'){
				    		alertBox('ERROR_APPLICATION_IS_BLOCKED',null,action);
				    	}else{
							if(callBack != undefined){
								new callBack(responseData,formId,action,handleForm,validateForm);				
							}else{
								DecisionAfterActionJS(responseData,formId,action,handleForm,validateForm);
							}
				    	}
					}else{
						var errorMsg = decisionApplication.errorMsg;
						displayErrorMsg(formId,errorMsg);
					}
				}
			}else{
				var decisionApplication = $.parseJSON(data); 
				var responseCode = decisionApplication.responseCode;
				if(responseCode == BUSINESS_EXCEPTION){
					var callBackJS = eval(functionId+'AfterBusinessExceptionDecisionActionJS');
					callBackJS(decisionApplication,formId,action,handleForm,validateForm);
				}
			}
		}catch(e){Pace.unblockFlag = true;Pace.unblock();}
	});
}
function decisionApplicationActionIncome(functionId,callBack,formId,action,handleForm,validateForm){
	Pace.block();
	var $data = 'className=com.eaf.orig.ulo.app.view.util.decisionservice.DecisionApplicationAction';
		if(formId != undefined && formId != ''){
			$data += '&' + $('#'+formId+' *').serialize();
			$data += '&formId='+formId;
		}
		$data += '&validateForm='+validateForm;
		$data += '&buttonAction='+action;
		$data += '&functionId='+functionId;
	var url = CONTEXT_PATH+'/Ajax';
	$.post(url,$data,function(data,status,xhr){
		try{
			if (responseSuccess(data)) {
				var responseData = getResponseData(data);
				var decisionApplication = $.parseJSON(responseData); 
				if(decisionApplication != undefined){
					var resultCode = decisionApplication.resultCode;
					var resultDesc = decisionApplication.resultDesc;
					if(resultCode == SUCCESS_CODE || resultCode == NO_ACTION){
						if(resultDesc == WARNING_DIFF_DV){
							confirmBox(CONFIRM_COMPARE_INCOME, AfterConfirmSAVE_TAB_INCOME_SCREEN_BTNActionJS);
						}else{
							AfterConfirmSAVE_TAB_INCOME_SCREEN_BTNActionJS('Y');
						}
					}
				}
			}else{
				var decisionApplication = $.parseJSON(data); 
				var responseCode = decisionApplication.responseCode;
				if(responseCode == BUSINESS_EXCEPTION){
					var callBackJS = eval(functionId+'AfterBusinessExceptionDecisionActionJS');
					callBackJS(decisionApplication,formId,action,handleForm,validateForm);
				}
			}
		}catch(e){Pace.unblockFlag = true;Pace.unblock();}
	});
}
function DecisionAfterActionJS(data,formId,action,handleForm,validateForm){
	var decisionApplication = $.parseJSON(data);
	if(decisionApplication != undefined){
		var resultCode = decisionApplication.resultCode;
		if(resultCode == '' || resultCode == NO_ACTION){
			saveApplicationFormAction(formId,action,handleForm,validateForm);
		}else{
			var decisionImplementActionId = decisionApplication.decisionImplementActionId;
			try{
				if(decisionImplementActionId != undefined){
					var callBackJS = eval(decisionImplementActionId+'AfterDecisionActionJS');
					callBackJS(data,formId,action,handleForm,validateForm);
				}
			}catch(e){}
		}
	}else{
		saveApplicationFormAction(formId,action,handleForm,validateForm);
	}
}


function ficoApplicationAction(functionId,callBack,formId,action,handleForm,validateForm){
	Pace.block();
	var $data  ='';
  	$data  = 'className=com.eaf.orig.ulo.app.view.util.fico.FicoApplicationAction';
		if(formId != undefined && formId != ''){
			$data += '&' + $('#'+formId+' *').serialize();
			$data += '&formId='+formId;
		}
		$data += '&validateForm='+validateForm;
		$data += '&buttonAction='+action;
		$data += '&functionId='+functionId;
	var url = CONTEXT_PATH+'/Ajax';
	$.post(url,$data,function(data,status,xhr){
		try{
			if (responseSuccess(data)) {
				var responseData = getResponseData(data);
				var ficoApplication = $.parseJSON(responseData);
				if(ficoApplication != undefined){
					var resultCode = ficoApplication.resultCode;
					if(resultCode == SUCCESS_CODE || resultCode == NO_ACTION){
						if(callBack != undefined){
							new callBack(responseData,formId,action,handleForm,validateForm);				
						}else{
							FicoDecisionAfterActionJS(responseData,formId,action,handleForm,validateForm);
						}
					}else{
						var errorMsg = ficoApplication.errorMsg;
						displayErrorMsg(formId,errorMsg);
					}
				}
			}
		}catch(e){Pace.unblockFlag = true;Pace.unblock();}
	});
}



function FicoDecisionAfterActionJS(data,formId,action,handleForm,validateForm){
	var ficoApplication = $.parseJSON(data);
	if(ficoApplication != undefined){
		var resultCode = ficoApplication.resultCode;
		if(resultCode == '' || resultCode == NO_ACTION){
			saveApplicationFormAction(formId,action,handleForm,validateForm);
		}else{
			var ficoImplementActionId = ficoApplication.ficoImplementActionId;
			try{
				if(ficoImplementActionId != undefined){
					var callBackJS = eval(ficoImplementActionId+'AfterFicoActionJS');
					callBackJS(data,formId,action,handleForm,validateForm);
				}
			}catch(e){}
		}
	}else{
		saveApplicationFormAction(formId,action,handleForm,validateForm);
	}
}

function buttonActionSubmit(action){
	return (action == BUTTON_ACTION_SUBMIT || action == BUTTON_ACTION_DECISION);
}

function refreshSubForm(subformId,handleForm){
	try{
		if(handleForm != undefined && handleForm == 'Y'){
			var $data  = 'subformId='+subformId;
				$data += '&'+$('#SECTION_'+subformId+' *').serialize();
			ajax('com.eaf.orig.ulo.app.view.util.manual.HandleForm',$data,displaySubForm);
		}else{
			displaySubForm(subformId);
		}
	}catch(exception){
		errorException(exception);
	}
}

function refreshFocusFiledNextTabSubForm(subformId,handleForm,currentField){
	try{
		if(handleForm != undefined && handleForm == 'Y'){
			var $data  = 'subformId='+subformId;
				$data += '&'+$('#SECTION_'+subformId+' *').serialize();
			ajax('com.eaf.orig.ulo.app.view.util.manual.HandleForm',$data,displaySubForm);
		}else{
			displaySubForm(subformId);
		}
		$('name="'+currentField+'"').focus();
		autoTabNextElement(currentField);
	}catch(exception){
		errorException(exception);
	}
}

function refreshSubFormNoBlockScreen(subformId,handleForm){
	try{
		if(handleForm != undefined && handleForm == 'Y'){
			var $data  = 'subformId='+subformId;
				$data += '&'+$('#SECTION_'+subformId+' *').serialize();
			ajax('com.eaf.orig.ulo.app.view.util.manual.HandleForm',$data,displaySubFormNoBlockScreen);
		}else{
			displaySubFormNoBlockScreen(subformId);
		}
	}catch(exception){
		errorException(exception);
	}
}

function manualRefreshSubForm(subformId,handleForm){
	try{
		if(handleForm != undefined && handleForm == 'Y'){
			var $data  = '&className=com.eaf.orig.ulo.app.view.util.manual.HandleForm';
				$data  += '&subformId='+subformId;
				$data += '&'+$('#SECTION_'+subformId+' *').serialize();
				var url = CONTEXT_PATH+'/Ajax';
				$.post(url,$data,function(data,status,xhr){
					try{
						if(responseSuccess(data,status,xhr)){
							manualDisplaySubForm(subformId);
							return true;
						}
						
					}catch(exception){
						errorException(exception);
					}
				}).fail(function(data,status,xhr){
					errorAjax(data,status,xhr);
				});
		}else{
			manualDisplaySubForm(subformId);
			return true;
		}
	}catch(exception){
		errorException(exception);
	}
	return false;
}

function setPropertiesSubform(subformId,callBack,elementId){
	try{
		var $data  = 'subformId='+subformId;
			$data += '&'+$('#SECTION_'+subformId+' *').serialize();
		ajax('com.eaf.orig.ulo.app.view.util.manual.HandleForm',$data,callBack,elementId);
	}catch(exception){
		errorException(exception);
	}
}
function displaySubForm(subformId){
	try{
		Pace.block();
		var url = CONTEXT_PATH+'/orig/ulo/template/SubForm.jsp?subformId='+subformId;
		$.post(url,function(data,status,xhr){
			try{	
				Pace.unblockFlag = true;
				Pace.unblock();
				$('#SECTION_'+subformId).html(data);
			}catch(exception){
				errorException(exception);
			}
		}).fail(function(data,status,xhr){
			errorAjax(data,status,xhr);
		});
	}catch(exception){
		errorException(exception);
	}
}

function displaySubFormNoBlockScreen(subformId){
	try{
		var url = CONTEXT_PATH+'/orig/ulo/template/SubForm.jsp?subformId='+subformId;
		$.post(url,function(data,status,xhr){
			try{	
				$('#SECTION_'+subformId).html(data);
			}catch(exception){
				errorException(exception);
			}
		}).fail(function(data,status,xhr){
			errorAjax(data,status,xhr);
		});
	}catch(exception){
		errorException(exception);
	}
}

function manualDisplaySubForm(subformId){
	try{
		var url = CONTEXT_PATH+'/orig/ulo/template/SubForm.jsp?subformId='+subformId;
		$.post(url,function(data,status,xhr){
			try{
				Pace.unblockFlag = true;
				Pace.unblock();
				$('#SECTION_'+subformId).html(data);
			}catch(exception){
				errorException(exception);
			}
		}).fail(function(data,status,xhr){
			errorAjax(data,status,xhr);
		});
	}catch(exception){
		errorException(exception);
	}
}
function ajax(className,str,callBack,elementId,name){
	try{
		var $data  = '&className='+className;
		if(str != undefined){
			// This can input as String or Object too.
			if(typeof str == "string") {
				$data += '&'+str;
			} else if(typeof str == "object") {
				$data += '&'+$.param(str);
			} else {
				console.log("ajax() : invalid type !");
			}
		}
		var url = CONTEXT_PATH+'/Ajax';
		$.post(url,$data,function(data,status,xhr){
			try{
				if(responseSuccess(data,status,xhr)){
					var responseData = getResponseData(data);
					try{
						if(callBack != undefined){
							new callBack(responseData,status,xhr,elementId,name);
						}
					}catch(exception){}
				}
			}catch(exception){
				errorException(exception);
			}
		}).fail(function(data,status,xhr){
			errorAjax(data,status,xhr);
		});
	}catch(exception){
		errorException(exception);
	}
}

function confirmBox(msgId,callBack){
	try{
		Pace.unblockFlag = true;
		Pace.unblock();
//		Type of dialog
//		BootstrapDialog.TYPE_DEFAULT
//		BootstrapDialog.TYPE_INFO
//		BootstrapDialog.TYPE_PRIMARY
//		BootstrapDialog.TYPE_SUCCESS
//		BootstrapDialog.TYPE_WARNING
//		BootstrapDialog.TYPE_DANGER	
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
	}catch(exception){
		errorException(exception);
	}
}

function confirmIncomeBox(msgId,callBack,data){
	try{
		Pace.unblockFlag = true;
		Pace.unblock();
//		Type of dialog
//		BootstrapDialog.TYPE_DEFAULT
//		BootstrapDialog.TYPE_INFO
//		BootstrapDialog.TYPE_PRIMARY
//		BootstrapDialog.TYPE_SUCCESS
//		BootstrapDialog.TYPE_WARNING
//		BootstrapDialog.TYPE_DANGER	
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
				   },
				   {
					   label: 'No',
					   cssClass: 'btn-danger',
					   action: function(dialog) {
						    var CONFIRM_FLAG = 'Y';
						    dialog.close();
							try{
								if(callBack != undefined){
									new callBack(CONFIRM_FLAG);
								}else{
									if(constant){
										var funcJS = eval(msgId+'AfterConfirmActionJS');
											funcJS(CONFIRM_FLAG,data);
									}
								}		
							}catch(e){}
					   }
				   }
				]
			};
		openConfirmDialog(dlgprop,'auto',DEFAULT_ALERT_BOX_WIDTH);
	}catch(exception){
		errorException(exception);
	}
}
function CONFIRM_COMPARE_INCOMEAfterConfirmActionJS(flag,ficoStatus){
	if(FLAG_YES==flag){
		AfterConfirmSaveAppActionJS(ficoStatus);
	}	
}
function confirmDangerBox(msgId,callBack){
	try{
		Pace.unblockFlag = true;
		Pace.unblock();
//		Type of dialog
//		BootstrapDialog.TYPE_DEFAULT
//		BootstrapDialog.TYPE_INFO
//		BootstrapDialog.TYPE_PRIMARY
//		BootstrapDialog.TYPE_SUCCESS
//		BootstrapDialog.TYPE_WARNING
//		BootstrapDialog.TYPE_DANGER	
		var constant = true;
		var msg = null;
		try{msg = eval(msgId);}catch(e){}
		if(msg == undefined || null == msg || msg == ''){
			constant = false;
			msg = msgId;
		}
		var dlgprop = {
				nl2br: false,
				type: BootstrapDialog.TYPE_DANGER,
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
	}catch(exception){
		errorException(exception);
	}
}

function openConfirmDialog(obj, side, width, height) {
	try{
//		Type of dialog
//		BootstrapDialog.TYPE_DEFAULT
//		BootstrapDialog.TYPE_INFO
//		BootstrapDialog.TYPE_PRIMARY
//		BootstrapDialog.TYPE_SUCCESS
//		BootstrapDialog.TYPE_WARNING
//		BootstrapDialog.TYPE_DANGER	
		if (!obj) return false;
		var dlg = new BootstrapDialog(obj);
		dlg.realize();	
//		Use Switch case for addition
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
	}catch(exception){
		errorException(exception);
	}
}

function confirmLink(msg,el) {
	confirmBox(msg,function(choice) {
		if (choice == "Y") {
			if (el != undefined) {
				el = $(el);
				var url = el.attr('href');
				window.location = url;
			}
		}
	});
	return false;
}

function alertBox(msgId,callBack,elementId,width,height){
	try{
		Pace.unblockFlag = true;
		Pace.unblock();
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
	}catch(exception){
		errorException(exception);
	}
}

function openAlertDialog(obj, side, width, height) {
	try{
//		Type of dialog
//		BootstrapDialog.TYPE_DEFAULT
//		BootstrapDialog.TYPE_INFO
//		BootstrapDialog.TYPE_PRIMARY
//		BootstrapDialog.TYPE_SUCCESS
//		BootstrapDialog.TYPE_WARNING
//		BootstrapDialog.TYPE_DANGER	
		if (!obj) return false;
		var dlg = new BootstrapDialog(obj);
		dlg.realize();	
//		Use Switch case for addition
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
	}catch(exception){
		errorException(exception);
	}
}

function openBootstrapDialog(obj, side, width, height) {	
	try{
//		Type of dialog
//		BootstrapDialog.TYPE_DEFAULT
//		BootstrapDialog.TYPE_INFO
//		BootstrapDialog.TYPE_PRIMARY
//		BootstrapDialog.TYPE_SUCCESS
//		BootstrapDialog.TYPE_WARNING
//		BootstrapDialog.TYPE_DANGER	
		if (!obj) return false;
		var dlg = new BootstrapDialog(obj);
		dlg.realize();	
//		Use Switch case for addition
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
		Pace.unblockFlag = true;
		Pace.unblock();
	}catch(exception){
		errorException(exception);
	}
}

function loadNextTabAction(formId,mode,rowId,str,handleForm,validateForm){
	try{
		Pace.block();
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
			$data += '&action=LoadNextTab&ajaxAction=Y&handleForm='+handleForm;
			$data += '&'+str;
			$data += '&validateForm='+validateForm;
		var url = CONTEXT_PATH+'/FrontController';
		$.post(url,$data,function(data,status,xhr){
			try{
				refreshMasterForm();
			}catch(exception){
				errorException(exception);
			}
		}).fail(function(data,status,xhr){
			Pace.unblockFlag = true;
			Pace.unblock();
			errorAjax(data,status,xhr);
		});
	}catch(exception){
		errorException(exception);
	}
}
function validateTabForm(str,handleForm,validateForm,callBack){
	try{
		Pace.block();
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
//					#rawi:After MandatoryForm set handleForm=N,validateForm=N
					handleForm = 'N';
					validateForm = 'N'; 
					if(responseSuccess(data,status,xhr)){
						var responseData = getResponseData(data);
						if(responseData == '' || responseData == undefined){
							if(callBack != undefined){
								new callBack(str,handleForm,validateForm);
							}else{
								saveTabFormAction(str,handleForm,validateForm);
							}
						}else{
							var $error = $.parseJSON(responseData);
							if(validateFormDataObject($error,'ERROR')){
								displayMandatoryForm(formId,str,responseData);
							}else{
								if(callBack != undefined){
									new callBack(str,handleForm,validateForm);
								}else{
									saveTabFormAction(str,handleForm,validateForm);
								}
							}
						}
					}
				}catch(exception){
					Pace.unblockFlag = true;
					Pace.unblock();
					errorException(exception);
				}
			}).fail(function(data,status,xhr){
				errorAjax(data,status,xhr);
			});
		}else{
//			#rawi:After MandatoryForm set handleForm=N,validateForm=N
			handleForm = 'N';
			validateForm = 'N';
			if(callBack != undefined){
				new callBack(str,handleForm,validateForm);
			}else{
				saveTabFormAction(str,handleForm,validateForm);
			}
		}
	}catch(exception){
		errorException(exception);
	}
}

function saveTabForm(str,handleForm,validateForm){
	validateTabForm(str,handleForm,validateForm);
}
function saveTabFormAction(str,handleForm,validateForm){
	try{
		Pace.block();
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
			$data += '&action=SaveTabForm&ajaxAction=Y&handleForm='+handleForm;
			$data += '&'+str;
			$data += '&validateForm'+validateForm;
		var url = CONTEXT_PATH+'/FrontController';
		$.post(url,$data,function(data,status,xhr){
			Pace.unblockFlag = true;
			Pace.unblock();
			try{
				var funcJS = eval(currentFormId+'AfterSaveActionJS');
				funcJS(data);
			}catch(exception){
				errorException(exception);
			}
		}).fail(function(data,status,xhr){
			errorAjax(data,status,xhr);
		});
	}catch(exception){
		errorException(exception);
	}
}
function backLastTabAction(str,handleForm,formActionId,callBack){
	try{
		Pace.block();
		if(handleForm == undefined){
			handleForm = 'N';
		}
		if(str == undefined){
			str = '';
		}
		var currentFormName = $("#FormHandlerManager [name='formName']").val();
		var currentFormId = $("#FormHandlerManager [name='formId']").val();
		var $data  = '&action=BackLastTab&ajaxAction=Y&handleForm='+handleForm;
			$data += '&'+str;
			$data += '&currentFormName'+currentFormName;
			$data += '&currentFormId'+currentFormId;
		var url = CONTEXT_PATH+'/FrontController';
		$.post(url,$data,function(data,status,xhr){
			try{			
				refreshMasterForm(formActionId,callBack);
			}catch(exception){
				errorException(exception);
			}
		}).fail(function(data,status,xhr){
			errorAjax(data,status,xhr);
		});
	}catch(exception){
		errorException(exception);
	}
}

function refreshCurrentForm(){
	try{
		Pace.block();
		var url = CONTEXT_PATH+'/orig/ulo/template/FormTemplate.jsp';
		$.post(url,function(data,status,xhr){
			Pace.unblockFlag = true;
			Pace.unblock();
			try{
				$('#FormHandlerManager').html(data);			
			}catch(exception){
				errorException(exception);
			}
		}).fail(function(data,status,xhr){
			errorAjax(data,status,xhr);
		});
	}catch(exception){
		errorException(exception);
	}
}

function refreshMasterForm(formActionId,callBack){
	try{
		Pace.block();
		var url = CONTEXT_PATH+'/orig/ulo/template/FormTemplate.jsp';
		$.post(url,function(data,status,xhr){
			Pace.unblockFlag = true;
			Pace.unblock();
			try{
				$('#FormHandlerManager').html(data);
//				scroll bar no move to top 			
//				$("html, body").animate({
//			        scrollTop: 0
//			    }, 200);
				$('.nopadding-right').stop().animate({scrollTop:0}, 0, 'swing');
			}catch(exception){
				errorException(exception);
			}
			try{			
				if(callBack != undefined){
					new callBack(formActionId,callBack);
				}else{
					if(formActionId != undefined){
						var funcJs = eval(formActionId+'ProcessActionJS');
						funcJs(formActionId);
					}
				}		
			}catch(exception){}
		}).fail(function(data,status,xhr){
			errorAjax(data,status,xhr);
		});
	}catch(exception){
		errorException(exception);
	}
}
function refreshMasterPopupForm(formActionId,callBack){
	try{
		Pace.block();
		var url = CONTEXT_PATH+'/orig/ulo/template/PopupFormTemplate.jsp';
		$.post(url,function(data,status,xhr){
			Pace.unblockFlag = true;
			Pace.unblock();
			try{
				$('#PopupFormHandlerManager').html(data);
			}catch(exception){
				errorException(exception);
			}
			try{
				if(callBack != undefined){
					new callBack(formActionId,callBack);
				}else{
					if(formActionId != undefined){
						var funcJs = eval(formActionId+'ProcessActionJS');
						funcJs(formActionId);
					}
				}		
			}catch(exception){
			}
		}).fail(function(data,status,xhr){
			errorAjax(data,status,xhr);
		});
	}catch(exception){
		errorException(exception);
	}
}
//rawi add function formHandleAction for set form properties
function formHandleAction(formId,str,callBack){
	try{
		var $data  = $('#'+formId+' *').serialize();
			$data += '&className=com.eaf.orig.ulo.app.view.util.manual.HandleForm';
			$data += '&functionId=formHandleAction';
			$data += '&formId='+formId;
			if(str == undefined){
				str = '';
			}
			$data += '&'+str;
		var url = CONTEXT_PATH+'/Ajax';
		$.post(url,$data,function(data,status,xhr){
			try{
				if(responseSuccess(data,status,xhr)){
					var responseData = getResponseData(data);
					try{
						if(callBack != undefined){
							new callBack(responseData,formId,str);
						}else{
							var funcJS = eval(formId+'AfterFormHandleActionJS');
								funcJS(responseData,formId,str);
						}
					}catch(exception){
					}
				}
			}catch(exception){
				errorException(exception);
			}
		}).fail(function(data,status,xhr){
			errorAjax(data,status,xhr);
		});
	}catch(exception){
		errorException(exception);
	}
}
function createRow(className,str,callBack,subformId,handleForm){
//	#rawi fix bug callBack function refreshSubform after createRow do not set handleForm
	try{
		if(subformId != undefined && handleForm != undefined && handleForm == 'Y'){
			var $data  = '&subformId='+subformId;
				$data += '&'+$('#SECTION_'+subformId+' *').serialize();
				$data += '&className=com.eaf.orig.ulo.app.view.util.manual.HandleForm';
			var url = CONTEXT_PATH+'/Ajax';
			$.post(url,$data,function(data,status,xhr){				
				try{	
					if(responseSuccess(data,status,xhr)){
						str += '&subformId='+subformId;
						str += '&handleForm='+handleForm;
						ajax(className,str,callBack);
					}
				}catch(exception){
					errorException(exception);
				}
			}).fail(function(data,status,xhr){
				errorAjax(data,status,xhr);
			});
		}else{
			str += '&subformId='+subformId;
			str += '&handleForm='+handleForm;
			ajax(className,str,callBack);
		}
	}catch(exception){
		errorException(exception);
	}
}

function deleteRow(className,str,callBack,subformId,handleForm,handleSubform){
	try{
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
								MSG_CONFIRM_DELETE_ROWAfterConfirmActionJS(className,str,callBack,subformId,handleForm,handleSubform);
							}catch(exception){
								errorException(exception);
							}
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
		openConfirmDialog(dlgprop,'auto',DEFAULT_ALERT_BOX_WIDTH);
	}catch(exception){
		errorException(exception);
	}
}
function MSG_CONFIRM_DELETE_ROWAfterConfirmActionJS(className,str,callBack,subformId,handleForm,handleSubform){
	try{
//		#rawi fix bug callBack function refreshSubform after deleteRow do not set handleForm
		if(subformId != undefined && handleForm != undefined && handleForm == 'Y'){
			var $data  = '&subformId='+subformId;
				$data += '&'+$('#SECTION_'+subformId+' *').serialize();
				$data += '&className=com.eaf.orig.ulo.app.view.util.manual.HandleForm';
				$data += '&handleSubform='+handleSubform;
			var url = CONTEXT_PATH+'/Ajax';
			$.post(url,$data,function(data,status,xhr){				
				try{
					if(responseSuccess(data,status,xhr)){
						str += '&subformId='+subformId;
						str += '&handleForm='+handleForm;
						ajax(className,str,callBack);
					}
				}catch(exception){
					errorException(exception);
				}
			}).fail(function(data,status,xhr){
				errorAjax(data,status,xhr);
			});
		}else{
			str += '&subformId='+subformId;
			str += '&handleForm='+handleForm;
			ajax(className,str,callBack);
		}
	}catch(exception){
		errorException(exception);
	}
}
function getFilledFormElements(elements) {
	return $(elements).filter(function() { 
		return $(this).val().length > 0;
	});
}
function clearForm(form){
	try{
		var formElement = $(form);	
		// Clear Textboxes
		formElement.find('input[type=text]').val('');
		// Uncheck Checkboxes Radios
		formElement.find('input[type=radio], input[type=checkbox]').prop('checked', false);
		// Clear selected item from Dropdown (Selectize)
		formElement.find('select').each(function() {
			this.selectize.clear(true);
		});
	}catch(exception){
		errorException(exception);
	}
}
function countFilledInput(form) {
	var countInput = 0;
	try{
		var formElement = $(form);
		formElement.find('input[type=text]').each(function() {
			if($(this).val() != ''){
				countInput++;
			}
		});	
		formElement.find('select').each(function() {
			if($(this).val() != ''){
				countInput++;
			}
		});
	}catch(exception){
		errorException(exception);
		countInput = 0;
	}
	return countInput;
}
function countInputChars(inputElement) {
	return $(inputElement).val().length;
}
function addFunctionOnFocus(form, callBack){
	try{
		var formElement = $(form);
		formElement.find('input[type=text]').each(function() {
			$(this).onfocus = function(){
				
			};
		});
	}catch(exception){
		errorException(exception);
	}
}
function validateFormInputsChars(form, need) {
	var validateForm = true;
	try{
		var formElement = $(form);
		if (need == undefined) {
			need = SEARCH_FIELDS_REQUIRED_CHAR_MOREEQUAL_THAN;
		}	
		getFilledFormElements(formElement.find('input[type=text]')).each(function() {
			if (countInputChars($(this)) < need) {
				validateForm = false;
			}
		});
	}catch(exception){
		errorException(exception);
		validateForm = false;
	}
	return validateForm;
}
function validateElementInputChars(element, need){	
	var validateElement = true;
	try{
		var elements = $(element);
		if (need == undefined) {
			need = SEARCH_FIELDS_REQUIRED_CHAR_MOREEQUAL_THAN;
		}	
		getFilledFormElements(elements).each(function() {
			if (countInputChars($(this)) < need) {
				validateElement = false;
			}
		});
	}catch(exception){
		errorException(exception);
		validateElement = false;
	}
	return validateElement;
}
function getPopupFormId(){
	var formId = "";
	try{
		var formActionName = $("#formActionName").val();
		formId = $("#PopupFormHandlerManager ."+formActionName+" [name='formId']").val();
	}catch(exception){
		errorException(exception);
	}
	return formId;
}
function getPopupFormName(){
	var formName = "";
	try{
		var formActionName = $("#formActionName").val();
		formName = $("#PopupFormHandlerManager ."+formActionName+" [name='formName']").val();
	}catch(exception){
		errorException(exception);
	}
	return formName;
}

function CALENDAROnFocusAction(element,mode,name){
	var ActionJs = eval(name+"OnFucusJS");
	ActionJs(element,mode,name);
}

function AccountNoOnblurAction(element,mode,name){
	try{
		if(mode == MODE_VIEW)return;
		var callBackAction = true;
		var errorFormat = false;
		var elementValue = $.trim($("[name='"+element+"']").val());
		str = elementValue.replace(/-/g,'');
		var pattern = "999-9-99999-9";
		var regEx = /[0-9]{3}-[0-9]{1}-[0-9]{5}-[0-9]{1}/;
		if(str.length == 14){
			pattern = "999-9-99999-9-99-99";
			regEx = /[0-9]{3}-[0-9]{1}-[0-9]{5}-[0-9]{1}-[0-9]{2}-[0-9]{2}/;
		}else if(str.length == 12){
			pattern = "999-9-99999-9-99";
			regEx = /[0-9]{3}-[0-9]{1}-[0-9]{5}-[0-9]{1}-[0-9]{2}/;
		}else{
			if(str.length > 10){
				errorFormat = true;
				callBackAction = false;
			}
		}
		var elementObject = $("[name='"+element+"']");
		elementObject.mask(pattern);
		if(elementValue && !regEx.test(elementValue)){
				errorFormat = true;
				callBackAction = false;
		}
		if(callBackAction && !errorFormat){
			try{
				var ActionJS = eval(name+'ActionJS');
					ActionJS(element,mode,name);
			}catch(exception){}
		}else if(errorFormat && !isEmpty(elementValue)){
			alertBox('ERROR_ACCOUNT_NO_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
		}
	}catch(exception){
		errorException(exception);
	}

}
function AccountNoOnFocusAction(element,mode,name){
	if(mode == MODE_VIEW)return;
	try{
		$("[name='"+element+"']").select(function(e){
			try{
				e.stopImmediatePropagation();
			}catch(exception){
				errorException(exception);
			}
		});
	}catch(exception){
		errorException(exception);
	}
}
function AccountNoOnKeyPressAction(element,mode,name,e){
	try{
		if(mode == MODE_VIEW)return;
		var maxLength = $("[name='"+element+"']").prop('maxlength');
		var pattern = "999-9-99999-9";
		if(maxLength && maxLength > 14){
			 pattern = "999-9-99999-9-99-99";
		}
		var strKeyCode = String.fromCharCode(e.keyCode);
		var elementObject = $("[name='"+element+"']");
		var elementValue = elementObject.val();
		var numcheck = /^[0-9-]+$/;
		if (!numcheck.test(elementValue+strKeyCode)) {
			e.returnValue = false;
		} else {
			e.returnValue = true;
		}
		elementObject.mask(pattern);
	}catch(exception){
		errorException(exception);
	}
}

function AccountNoOthBankOnblurAction(element,mode,name){
	try{
		
		if(mode == MODE_VIEW)return;
		var callBackAction = true;
		var errorFormat = false;
		var regEx = /^[0-9]+$/;
		var elementValue = $.trim($("[name='"+element+"']").val());
		if (!isEmpty(elementValue)) {
			if (!regEx.test(elementValue)) {
				errorFormat = true;
				callBackAction = false;
			}
		};
		if(callBackAction && !errorFormat){
			try{
				var ActionJS = eval(name+'ActionJS');
					ActionJS(element,mode,name);
			}catch(exception){}
		}else if(errorFormat && !isEmpty(elementValue)){
			alertBox('ERROR_ACCOUNT_NO_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
		}
		
		
	}catch(exception){
		errorException(exception);
	}
}
function AccountNoOthBankOnFocusAction(element,mode,name){
	if(mode == MODE_VIEW)return;
	try{
		$("[name='"+element+"']").select(function(e){
			try{
				e.stopImmediatePropagation();
			}catch(exception){
				errorException(exception);
			}
		});
	}catch(exception){
		errorException(exception);
	}
}
function AccountNoOthBankOnKeyPressAction(element,mode,name,e){
	try{
		
		var strKeyCode = String.fromCharCode(e.keyCode);
		var elementObject = $("[name='"+element+"']");
		var elementValue = elementObject.val();
		var numcheck = /^[0-9]+$/;
		if (!numcheck.test(elementValue+strKeyCode)) {
			e.returnValue = false;
		} else {
			e.returnValue = true;
		}
		
	}catch(exception){
		errorException(exception);
	}
}
function CardNoOnblurAction(element,mode,name){
	try{
		if(mode == MODE_VIEW)return;
		var callBackAction = true;
		var errorFormat = false;
		var pattern = "9999-9999-9999-9999";
		var elementObject = $("[name='"+element+"']");
			elementObject.mask(pattern);
		var regEx = /[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}/; //XXXX-XXXX-XXXX-XXXX
		var elementValue = $.trim($("[name='"+element+"']").val());
		if (!isEmpty(elementValue)) {
			if (!regEx.test(elementValue)) {
				errorFormat = true;
				callBackAction = false;
			}
		};
		if(callBackAction && !errorFormat){
			try{
				var ActionJS = eval(name+'ActionJS');
					ActionJS(element,mode,name);
			}catch(exception){}
		}else if(errorFormat && !isEmpty(elementValue)){
			alertBox('ERROR_CARD_NO_FORMAT',emptyFocusElementActionJS,element,DEFAULT_ALERT_BOX_WIDTH);
		}
	}catch(exception){
		errorException(exception);
	}
}
function CardNoOnFocusAction(element,mode,name){	
	try{
		if(mode == MODE_VIEW)return;
		$("[name='"+element+"']").select(function(e){
			try{
				e.stopImmediatePropagation();
			}catch(exception){
				errorException(exception);
			}
		});
	}catch(exception){
		errorException(exception);
	}
}
function CardNoOnKeyPressAction(element,mode,name,e){
	try{
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
	}catch(exception){
		errorException(exception);
	}
}
function LoadRefreshFormAction(requestData,callBack){
//	Pace.block();
	try{
		var	 className ='com.eaf.orig.ulo.app.view.util.ajax.LoadRefreshFormAction';
		if(callBack != undefined){
			ajax(className,requestData,callBack);
		}else{
			ajax(className,requestData,refreshMasterForm);
		}
	}catch(exception){
		errorException(exception);
	}
}
function refreshFormHeader(){
	try{
		Pace.block();
		var url = CONTEXT_PATH+'/orig/ulo/template/FormHeaderButton.jsp';
		$.post(url,function(data,status,xhr){
			try{
				Pace.unblockFlag = true;
				Pace.unblock();
				$('#FormHeaderButton').html(data);
			}catch(exception){
				errorException(exception);
			}
		}).fail(function(data,status,xhr){
			errorAjax(data,status,xhr);
		});
	}catch(exception){
		errorException(exception);
	}
}

function refreshHeaderFormTemplate(formId){
	try{
		Pace.block();
		var url = CONTEXT_PATH+'/orig/ulo/template/HeaderFormTemplate.jsp?formId='+formId;
		$.post(url,function(data,status,xhr){
			try{
				Pace.unblockFlag = true;
				Pace.unblock();
				$('#HEADER_SECTION_'+formId).html(data);
			}catch(exception){
				errorException(exception);
			}
		}).fail(function(data,status,xhr){
			errorAjax(data,status,xhr);
		});
	}catch(exception){
		errorException(exception);
	}
}

function refreshSearchControlActionJS(){
	try{
		Pace.block();
		$('#action').val('SearchControl');
		$('#handlerForm').val('N');
		$('#appFormName').submit();
	}catch(exception){
		errorException(exception);
	}
}
function genDateTH(value){
	var dateEN = "";
	try{
		if(!isEmpty(value)){
			var day = value.split("/")[0];
			var month = value.split("/")[1];
			var year = value.split("/")[2];
			dateEN = day + "/" + month + "/" + (parseInt(year) - 543).toString();
		}
	}catch(exception){
		errorException(exception);
	}
	return dateEN;
}
function genDateThFromEn(value){
	var dateEN = "";
	try{
		if(!isEmpty(value)){
			var day = value.split("/")[0];
			var month = value.split("/")[1];
			var year = value.split("/")[2];
			dateEN = day + "/" + month + "/" + (parseInt(year) + 543).toString();
		}
	}catch(exception){
		errorException(exception);
	}
	return dateEN;
}
function onloadNCBPopupBlackground(){
	try{
		var popupHeight = $('.PopupFormHandlerWrapper').find('.work_area').height();
		var htmlText = $('.ncb-background').html();
		$('.ncb-background').html('');
		for(var j=0;j<(popupHeight/250);j++){
			for(var i=0;i<2;i++){
				$('.ncb-background').append(htmlText);
			}
		}
	}catch(exception){
		errorException(exception);
	}
}
function OpenPopupListActionJS(element,actionJS){
	try{
		this.CURRENT_POPUP_FIELD_NAME=element;		
		eval(actionJS);
	}catch(exception){
		errorException(exception);
	}
	
}
function DialogInfo(formId,formType){
	this.formId = formId;
	this.formType = formType;
}
function lookupDialogInfos(dialogInfo){
	var found = false;
	if(DIALOG_INFOS!=undefined && DIALOG_INFOS.dialog!=undefined){
		if(dialogInfo!=undefined){
			$.each(DIALOG_INFOS.dialog,function(i,item){
				if(item!=undefined){
					if(item.formId==dialogInfo.formId && item.formType==dialogInfo.formType){
						found = true;
						return false; // if found,break loop
					}
				}
			});
		}
	}
	return found;
}
function removeDialogInfos(dialogInfo){
	if(DIALOG_INFOS!=undefined && DIALOG_INFOS.dialog!=undefined){
		if(dialogInfo!=undefined){
			var index = -1;
			$.each(DIALOG_INFOS.dialog,function(i,item){
				if(item!=undefined){
					if(item.formId==dialogInfo.formId && item.formType==dialogInfo.formType){
						index = i;
						return false; // if found,break loop
					}
				}
			});
			if(index>-1){
				DIALOG_INFOS.dialog.splice(index,1); // remove
			}
		}
	}
}