
function displayJSON(data){
	try{
		var $JSON = $.parseJSON(data);
		if($.isEmptyObject($JSON)){
			return;
		}
		$.map($JSON, function(item){
			var elementObject = $("[name='"+item.id+"']");
			var elementProperty = (elementObject != undefined)?elementObject.attr('property'):'';
			if(elementObject != undefined && elementProperty != undefined && elementProperty != ''){
				var elementId = item.id;
				var elementValue = item.value;
				displayHtmlElement(elementId,elementValue,elementObject,elementProperty);
			}else{
				displayHtmlElementJSON(item);
			}
		});
	}catch(e){		
	}
}
function displayHtmlElementJSON(item){
	try{
		var elementId = document.getElementById(item.id);
		if(elementId != null){
			if (elementId.type!== undefined){
				switch(elementId.type.toLowerCase()) {
					case "input": break;
					case "hidden": $(elementId).val(item.value); break;
					case "select": $(elementId).val(item.value); break;
					case "select-one": $(elementId).val(item.value); break;
					case "textarea": break;
					case "text": $(elementId).val(item.value); break;
					case "checkbox": break;
					case "radio": break;
					case "search": break;
				}
			}else{
				$("[id$="+item.id+"]").each(function () {
				    switch(this.tagName.toLowerCase()) {
						case "table": $(elementId).append(item.value); break;
						case "div": $(elementId).html(item.value); break;
						case "td": $(elementId).html(item.value); break;
						case "tr": $(elementId).html(item.value); break;
						case "span": $(elementId).html(item.value); break;
						case "textarea": $(elementId).html(item.value); break;
				    }
				});
			}
		}else{
			var element = $("[name='"+item.id+"']");
			var tag = element.prop("tagName").toLowerCase();
			if(tag != undefined){
				if(tag == 'input'){
					if (element.attr('type') !== undefined){
						switch(element.attr('type').toLowerCase()) {
							case "input": break;
							case "hidden": $("[name='"+item.id+"']").val(item.value); break;
							case "select": $("[name='"+item.id+"']").val(item.value); break;
							case "select-one": $("[name='"+item.id+"']").val(item.value); break;
							case "textarea": break;
							case "text": $("[name='"+item.id+"']").val(item.value); break;
							case "textbox": $("[name='"+item.id+"']").val(item.value); break;
							case "checkbox": break;
							case "radio": break;
						}
					}
				}else if(tag == 'select'){
					$("[name='"+item.id+"']").val(item.value);
				}else if(tag == 'textarea'){
					$("[name='"+item.id+"']").html(item.value);
				}
			}
		}
	}catch(e){}
}
function callAjaxReturnVal(className, dataString) {
	var $data = '&className='+className;
	var result = '';
	if(dataString != null && dataString != undefined && dataString.length >0) {
		$data += '&'+dataString;
	}
	var url = CONTEXT_PATH+'/Ajax';
	  $.ajax({
	      url:url,
	      data:$data,
	      async: false,  
	      success:function(data) {
	    	  if (isResponseSuccess(data)) {
	    		  result = getResponseData(data);
	    	  }
	      }
	   });
	
	return result;
}
function displayHtmlElement(elementId,elementValue,elementObject,elementProperty){
	if(elementObject == undefined){
		elementObject = $("[name='"+elementId+"']");
	}
	if(elementObject == undefined || elementObject.length == 0) {
		elementObject = $("#"+elementId);
	}
	if(elementProperty == undefined){
		elementProperty = (elementObject != undefined)?elementObject.attr('property'):'';
	}
	if(elementProperty == undefined) {
		elementProperty = (elementObject != undefined)?elementObject.prop('tagName').toLowerCase():'';
	}
	try{
		switch(elementProperty){
			case "textbox-text":
			case "textbox-email":
			case "textbox-tel":
			case "textbox-ext":
			case "textbox-mobile":
			case "textbox-calendar":
			case "textbox-lang":
			case "popup-list" :
			case "textbox-number":
			case "textbox-currency":
			case "textbox-accountno":
			case "textbox-cardno":
			case "hidden":
				elementObject.val(elementValue);break;
			case "textarea":
				elementObject.html(elementValue);break;
			case "dropdown":
				elementObject[0].selectize.setValue(elementValue,true);break;
			case "autocomplete":
				elementObject[0].selectize.addOption({"value":elementValue});
				elementObject[0].selectize.setValue(elementValue,true);
				break;
			case "checkbox":elementObject.prop('checked',true);break;
			case "radio":$("[name='"+elementId+"'][value='"+elementValue+"']").prop("checked",true);break;
			case "div":elementObject.text(elementValue);break;
			case "search":
				var searchConfigId = elementObject.attr('searchConfigId');
				searchValueAction(elementId, searchConfigId, elementValue);
				break;
			default:break;
		}
	}catch(e){}
}
function targetDisplayHtml(elementId,mode,elementName,clearFlag){
	console.log("targetDisplayHtml() : ", {
		elementId: elementId,
		mode: mode,
		elementName: elementName,
		clearFlag: clearFlag
	});
	var elementObject = $("[name='"+elementId+"']");
	var elementProperty = (elementObject != undefined)?elementObject.attr('property'):'';
	if(elementObject != undefined && elementProperty != ''){
		try{
			if(clearFlag == 'Y'){
				displayHtmlElement(elementId,"",elementObject,elementProperty);
			}
		}catch(e){}
		try{
			switch(elementProperty){
				case "textbox-text":
				case "textbox-email":
				case "textarea":
					if(mode == MODE_VIEW){
						elementObject.prop("readonly", true);
						elementObject.prop("tabindex", '-1');
					}else if(mode == MODE_EDIT){
						elementObject.prop("readonly", false);
						elementObject.removeAttr('tabindex');						
					}
					var onblurAction = elementObject.attr('onblur').replace('EDIT',mode).replace('VIEW',mode);
					elementObject.attr('onblur',onblurAction);
					break;
				case "textbox-tel":
				case "textbox-ext":
				case "textbox-mobile":
				case "textbox-accountno":
				case "textbox-cardno":
					if(mode == MODE_VIEW){
						elementObject.prop("readonly", true);
						elementObject.prop("tabindex", '-1');
					}else if(mode == MODE_EDIT){
						elementObject.prop("readonly", false);
						elementObject.removeAttr('tabindex');
					}
					var onblurAction = elementObject.attr('onblur').replace('EDIT',mode).replace('VIEW',mode);
					elementObject.attr('onblur',onblurAction);
					var onfocusAction = elementObject.attr('onfocus').replace('EDIT',mode).replace('VIEW',mode);
					elementObject.attr('onfocus',onfocusAction);
					var onkeypressAction = elementObject.attr('onkeypress').replace('EDIT',mode).replace('VIEW',mode);
					elementObject.attr('onkeypress',onkeypressAction);
					break;
				case "textbox-calendar":
					var regional = elementObject.attr('regional');
					if(mode == MODE_VIEW){
						elementObject.prop("readonly", true);
						elementObject.datepicker("destroy");
						elementObject.prop("tabindex", '-1');
					}else if(mode == MODE_EDIT){
						elementObject.prop("readonly", false);
						elementObject.datepicker($.datepicker.regional[regional]);
						elementObject.removeAttr('tabindex');
					}
					var onblurAction = elementObject.attr('onblur').replace('EDIT',mode).replace('VIEW',mode);
					elementObject.attr('onblur',onblurAction);
					var onkeypressAction = elementObject.attr('onkeypress').replace('EDIT',mode).replace('VIEW',mode);
					elementObject.attr('onkeypress',onkeypressAction);
					break;
				case "textbox-lang":
					if(mode == MODE_VIEW){
						elementObject.prop("readonly", true);
						elementObject.prop("tabindex", '-1');
					}else if(mode == MODE_EDIT){
						elementObject.prop("readonly", false);
						elementObject.removeAttr('tabindex');
					}
					var onblurAction = elementObject.attr('onblur').replace('EDIT',mode).replace('VIEW',mode);
					elementObject.attr('onblur',onblurAction);
					var onkeypressAction = elementObject.attr('onkeypress').replace('EDIT',mode).replace('VIEW',mode);
					elementObject.attr('onkeypress',onkeypressAction);
					break;
				case "textbox-number":
					if(mode == MODE_VIEW){
						elementObject.prop("readonly", true);
						elementObject.prop("tabindex", '-1');
					}else if(mode == MODE_EDIT){
						elementObject.prop("readonly", false);
						elementObject.removeAttr('tabindex');
					}
					var onblurAction = elementObject.attr('onblur').replace('EDIT',mode).replace('VIEW',mode);
					elementObject.attr('onblur',onblurAction);
					var onfocusAction = elementObject.attr('onfocus').replace('EDIT',mode).replace('VIEW',mode);
					elementObject.attr('onfocus',onfocusAction);
					var onkeypressAction = elementObject.attr('onkeypress').replace('EDIT',mode).replace('VIEW',mode);
					elementObject.attr('onkeypress',onkeypressAction);
					var onkeyupAction = elementObject.attr('onkeyup').replace('EDIT',mode).replace('VIEW',mode);
					elementObject.attr('onkeyup',onkeyupAction);
					break;
				case "textbox-currency":
					if(mode == MODE_VIEW){
						elementObject.prop("readonly", true);
						elementObject.prop("tabindex", '-1');
					}else if(mode == MODE_EDIT){
						elementObject.prop("readonly", false);
						elementObject.removeAttr('tabindex');
					}
					var onblurAction = elementObject.attr('onblur').replace('EDIT',mode).replace('VIEW',mode);
					elementObject.attr('onblur',onblurAction);
					var onfocusAction = elementObject.attr('onfocus').replace('EDIT',mode).replace('VIEW',mode);
					elementObject.attr('onfocus',onfocusAction);
					var onkeypressAction = elementObject.attr('onkeypress').replace('EDIT',mode).replace('VIEW',mode);
					elementObject.attr('onkeypress',onkeypressAction);
					var onkeyupAction = elementObject.attr('onkeyup').replace('EDIT',mode).replace('VIEW',mode);
					elementObject.attr('onkeyup',onkeyupAction);
					var onkeydownAction = elementObject.attr('onkeydown').replace('EDIT',mode).replace('VIEW',mode);
					elementObject.attr('onkeydown',onkeydownAction);					
					break;
				case "dropdown":
				case "autocomplete":
					if(mode == MODE_VIEW){
						elementObject[0].selectize.lock();
					}else if(mode == MODE_EDIT){
						elementObject[0].selectize.unlock();
					}
//					#rawi comment change action onchange to init selectize
//					var onchangeAction = elementObject.attr('onchange').replace('EDIT',mode).replace('VIEW',mode);
//					elementObject.attr('onchange',onchangeAction);
					break;
				case "checkbox":
					var objectValue = $("input:checkbox[name='"+elementId+"']:checked","#InputField_"+elementId).val();
					if(objectValue == undefined){
						objectValue = '';
					}
					if(mode == MODE_VIEW){
						elementObject.prop("disabled", true);		
						$("#InputField_"+elementId).append("<input type='hidden' name='"+elementId+"' property='hidden' value='"+objectValue+"'/>");
					}else if(mode == MODE_EDIT){
						elementObject.prop("disabled", false);
						$("input:hidden[name='"+elementId+"']","#InputField_"+elementId).remove();
					}
					var onclickAction = elementObject.attr('onclick').replace('EDIT',mode).replace('VIEW',mode);
					elementObject.attr('onclick',onclickAction);
					break;
				case "radio":
					var objectValue = $("input:radio[name='"+elementId+"']:checked","#InputField_"+elementId).val();
					if(objectValue == undefined){
						objectValue = '';
					}
					if(mode == MODE_VIEW){
						elementObject.prop("disabled", true);		
						$("#InputField_"+elementId).append("<input type='hidden' name='"+elementId+"' property='hidden' value='"+objectValue+"'/>");
					}else if(mode == MODE_EDIT){
						elementObject.prop("disabled", false);
						$("[name='"+elementId+"']").attr('checked',false);
//						$("input:hidden[name='"+elementId+"']","#InputField_"+elementId).remove();
					}
					var onclickAction = elementObject.attr('onclick').replace('EDIT',mode).replace('VIEW',mode);
					elementObject.attr('onclick',onclickAction);
					break;
				case "button":
					if(mode == MODE_VIEW){
						elementObject.prop("disabled", true);
					}else if(mode == MODE_EDIT){
						elementObject.prop("disabled", false);
					}
					break;
				case "icon":
					if(mode == MODE_VIEW){
						elementObject.prop("disabled", true);
					}else if(mode == MODE_EDIT){
						elementObject.prop("disabled", false);
					}
					break;
				case "popup-list":
					if(mode == MODE_VIEW){
						$('#InputField_'+elementId).css('pointer-events','none');
						elementObject.prop("tabindex", '-1');
					}else if(mode == MODE_EDIT){
						$('#InputField_'+elementId).css('pointer-events','auto');
						elementObject.removeAttr('tabindex');
					}
					break;
				case "search":
					if(mode == MODE_VIEW){
						elementObject[0].selectize.lock();
					}else if(mode == MODE_EDIT){
						elementObject[0].selectize.unlock();
					}
					break;
				default:break;
			}
		}catch(e){}
	}
}

function lookup(constantId,compare){
	try{return constantId.indexOf(compare)>-1;}catch(e){return false;}
}
function listBoxFilterAction(elementId,cacheId,typeId,listboxConfigId,str,callBack){
	var $data   = 'elementId='+elementId;
		$data  += '&cacheId='+cacheId;
		$data  += '&typeId='+typeId;
		$data  += '&configId='+listboxConfigId;
		$data  += '&className=com.eaf.orig.ulo.app.view.util.manual.ListBoxFilterAction';
		$data  += '&'+str;
	var url = CONTEXT_PATH+'/Ajax';
	$.post(url,$data,function(data,status,xhr){
		if (isResponseSuccess(data)) {
			var responseData = getResponseData(data);
			try{
				var elementObject = $("[name='"+elementId+"']");
					elementObject[0].selectize.clearOptions();
					elementObject[0].selectize.addOption($.parseJSON(responseData));			
			}catch(e){}
			try{
				if(callBack != undefined){
					new callBack(elementId);
				}else{
					var funcJS = eval(elementId+'AfterListBoxFilterActionJS');
						funcJS(elementId);
				}		
			}catch(e){}
		}
	});
}

function searchValueAction(elementId, searchConfigId, searchValue){
	var $data   = 'elementId='+elementId;
		$data  += '&searchConfigId='+searchConfigId;
		$data  += '&searchValue='+searchValue;
		$data  += '&searchValueFlag='+FLAG_YES;
		$data  += '&className=com.eaf.orig.ulo.app.view.util.manual.SearchFilterAction';
	var url = CONTEXT_PATH+'/Ajax';
	$.post(url,$data,function(data,status,xhr){alert(isResponseSuccess(data))
		if (isResponseSuccess(data)) {
			var responseData = getResponseData(data);
			var elementObject = $("[name='"+elementId+"']");
			var obj = $.parseJSON(responseData)&&$.parseJSON(responseData).length || [{}];
			elementObject[0].selectize.clearOptions();
			elementObject[0].selectize.addOption({'value' : obj[0].value,'code': obj[0].code });
			elementObject[0].selectize.setValue(searchValue, true);
		}
	});
}

function searchFilterAction(elementId,searchConfigId,str,callBack){
	var $data   = 'elementId='+elementId;
		$data  += '&searchConfigId='+searchConfigId;
		$data  += '&className=com.eaf.orig.ulo.app.view.util.manual.SearchFilterAction';
		$data  += '&'+str;
//		$data = encodeURIComponent($data);
	var url = CONTEXT_PATH+'/Ajax';
	$.post(url,$data,function(data,status,xhr){
		if (isResponseSuccess(data)) {
			var responseData = getResponseData(data);
			var elementObject = $("[name='"+elementId+"']");
			try{
				elementObject[0].selectize.clearOptions();
				elementObject[0].selectize.addOption($.parseJSON(responseData));
			}catch(e){
			}
			elementObject[0].selectize.refreshOptions();
			try{
				if(callBack != undefined){
					new callBack(elementId);
				}else{
					var funcJS = eval(elementId+'AfterSearchFilterActionJS');
						funcJS(elementId);
				}		
			}catch(e){}
		}
	});
}
/**
 * Make String date format "YYYY-mm-dd" from Date object
 * @param date Date input
 * @returns {String}
 * @author Norrapat Nimmanee
 */
function makeDate(date) {
	var splitDate = date.split("/");
	return splitDate[2] + '-' + splitDate[1] + '-' + splitDate[0];
}

/**
 * Format Date to String
 * @param date Date input
 * @param delimiter Delimiter for Date (Default is "/")
 * @returns formatted Date string
 * @author Norrapat Nimmanee
 */
function formatDate(date, delimiter) {
	if (delimiter == undefined) {
		// set Default
		delimiter = "/";
	}
	var dd = date.getDate().toString();
	var mm = (date.getMonth() + 1).toString(); // Month start from 0
	var yyyy = date.getFullYear().toString();
	
	return (dd[1]?dd:"0"+dd[0]) + delimiter + (mm[1]?mm:"0"+mm[0]) + delimiter + yyyy;
}

function reloadTooltip() {
	try {
	$('[tooltip]').tooltipster('destroy');
	} catch(e) {}
	
	$('[tooltip]').tooltipster({
		position: 'bottom',
		theme: 'tooltipster-shadow',
		delay: 20
	});
}