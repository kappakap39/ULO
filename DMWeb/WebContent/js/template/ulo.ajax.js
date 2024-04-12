/**
 * Rawi Songchaisin
 */
var packageDefault		 = 0;
var packageOrigShare	 = 1;
var packageOrigPl		 = 2;
var packageOrigRules	 = 3;
var packagePlMandatory	 = 4;
var packagePlProduct	 = 5;

function ajaxDisplayElementJson(className,packAge,data){
	var dataString = "className="+className+"&packAge="+packAge+"&returnType=0";
	if(data!= null && data.length >0) dataString = dataString+"&"+data;
	jQuery.ajax( {
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,	
		dataType: "json",
		success : function(response ,status ,xhr){
			jsonDisplayElementById(response);
		},
		error : function(response){
			
		}
	});
}
/**
 * Wichaya Ch.
 * 
 * Display Element and notification error
 */
function ajaxDisplayElementJsonWithError(className,packAge,data){
	var dataString = "className="+className+"&packAge="+packAge+"&returnType=0";
	if(data!= null && data.length >0) dataString = dataString+"&"+data;
	jQuery.ajax( {
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,	
		dataType: "json",
		success : function(response ,status ,xhr){
			jsonDisplayElementByIdWithError(response);
		},
		error : function(response){
		}
	});
}

/**
 * Praisan K.
 * Display Element and notification error
 */
function ajaxDisplayElementJsonValueWithError(className,packAge,data){
	var dataString = "className="+className+"&packAge="+packAge+"&returnType=0";
	if(data!= null && data.length >0) dataString = dataString+"&"+data;
	$.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,	
		dataType: "json",
		success : function(response ,status ,xhr){
			jsonDisplayElementByIdValueWithError(response);
		},
		error : function(response){
		}
	});
}

function ajaxDisplayElementJsonAsync(className,packAge,data){
	var dataString = "className="+className+"&packAge="+packAge+"&returnType=0";
	if(data!= null && data.length >0) dataString = dataString+"&"+data;
	jQuery.ajax( {
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :true,	
		dataType: "json",
		success : function(response ,status ,xhr){
			jsonDisplayElementById(response);
		},
		error : function(response){
		}
	});
}
function ajaxDisplayElementObjectJson(className,packAge,data){
	var dataString = "className="+className+"&packAge="+packAge+"&returnType=0";
	if(data != null && data.length >0) dataString = dataString+"&"+data;
	jQuery.ajax( {
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,	
		dataType: "json",
		success : function(response ,status ,xhr){
			displayJsonObjectElementById(response);
		},
		error : function(response){
		}
	});
}
function ajaxDisplayElementHtml(className,packAge,data,displayId){
	var dataString = "className="+className+"&packAge="+packAge+"&returnType=1";
	if(data!= null && data.length >0) dataString = dataString+"&"+data;
	jQuery.ajax( {
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,
		success : function(response ,status ,xhr){	
			htmlDisplayElementById(response,displayId);
		},
		error : function(response){
		}
	});
}
function ajaxDisplayElementHtmlAsync(className,packAge,data,displayId){
	var dataString = "className="+className+"&packAge="+packAge+"&returnType=1";
	if(data!= null && data.length >0) dataString = dataString+"&"+data;
	jQuery.ajax( {
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :true,
		success : function(response ,status ,xhr){			
			htmlDisplayElementById(response,displayId);
		},
		error : function(response){
		}
	});
}
function ajaxJsonManual(className,packAge,data,manualFunc){
	var dataString = "className="+className+"&packAge="+packAge+"&returnType=0";
	if(data!= null && data.length >0) dataString = dataString+"&"+data;
	jQuery.ajax( {
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,	
		dataType: "json",
		success : function(response ,status ,xhr){			
			new manualFunc(response);
		},
		error : function(response){
		}
	});
}
function ajaxJsonManualAsync(className,packAge,data,manualFunc){
	var dataString = "className="+className+"&packAge="+packAge+"&returnType=0";
	if(data!= null && data.length >0) dataString = dataString+"&"+data;
	jQuery.ajax( {
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :true,	
		dataType: "json",
		success : function(response ,status ,xhr){			
			new manualFunc(response);
		},
		error : function(response){
		}
	});
}
function ajaxHtmlManualAsync(className,packAge,data,manualFunc){
	var dataString = "className="+className+"&packAge="+packAge+"&returnType=1";
	if(data!= null && data.length >0) dataString = dataString+"&"+data;
	jQuery.ajax( {
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :true,
		success : function(response ,status ,xhr){			
			new manualFunc(response);
		},
		error : function(data){
		}
	});
}
function ajaxHtmlManual(className,packAge,data,manualFunc){
	var dataString = "className="+className+"&packAge="+packAge+"&returnType=1";
	if(data!= null && data.length >0) dataString = dataString+"&"+data;
	jQuery.ajax( {
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,
		success : function(response ,status ,xhr){			
			new manualFunc(response);
		},
		error : function(data){
		}
	});
}
function $AjaxFrontController(webAction,formHandler,formId,otherParam,handerFunc){
	$('#action').val(webAction);
	$('#handleForm').val(formHandler);
	$('#currentTab').val('MAIN_TAB');
	var dataString = '';
	if(formId != null && formId.length >0){
		if(document.getElementById(formId) == null) return;
		dataString = $('#'+formId+' *').serialize();
	}else{
		dataString = 'action='+webAction+'&handleForm='+formHandler;
	}
	if(otherParam != null && otherParam.length >0)
		dataString = dataString+'&'+otherParam;	
	$.ajax({
		type :"POST",
		url :"FrontController",
		data :dataString,
		async :false,
		success : function(data ,status ,xhr){			
			new handerFunc(data);
		},
		error : function(data){
		}
	});
}
function $FrontController(webAction,formHandler,formId,errorFunc){
	$('#action').val(webAction);
	$('#handleForm').val(formHandler);
	$('#currentTab').val('MAIN_TAB');
	var dataString = '';
	if(formId != null && formId.length >0){
		if(document.getElementById(formId) == null) return;
		dataString = $('#'+formId+' *').serialize();
	}
	blockScreen();
	$.post('FrontController',dataString
			,function(data,status,xhr){
				$.map(data, function(item){
					switch(item.message){
						case "page":
							unblockScreen();
							if(item.value != null){
								appFormName.action.value="RenderPageWebAction";
								appFormName.renderPage.value= item.value;
								appFormName.handleForm.value = "N";
								appFormName.submit();
							}
							break;
						case "status":
							unblockScreen();
							if($('#message-xrules-error').html() == null && item.value != ''){
								PerpendNotifyID(item.value ,'message-xrules-error');
							}
							if(errorFunc != null){								
								new errorFunc();
							}
							break;
						default:
							break;
					}
				});
				unblockScreen();
		   })
		   .error(function(){
			   unblockScreen();
		   });
}
function $FrontControllerMassage(webAction,formHandler,formId,otherParam){
	$('#action').val(webAction);
	$('#handleForm').val(formHandler);
	$('#currentTab').val('MAIN_TAB');
	var dataString = '';
	if(formId != null && formId.length >0){
		if(document.getElementById(formId) == null) return;
		dataString = $('#'+formId+' *').serialize();
	}else{
		dataString = 'action='+webAction+'&handleForm='+formHandler;
	}
	if(otherParam != null && otherParam.length >0)
		dataString = dataString+'&'+otherParam;
	blockScreen();
	$.post('FrontController',dataString
			,function(data,status,xhr){
				unblockScreen();
				$.map(data, function(item){
					switch(item.message){
						case "status":
							alertMassage(item.value);
							break;
						default:
							break;
					}
				});								
		   }).error(function(){
			   unblockScreen();
		   });
}
function ajaxLoad(elementId,className,paceAge,param,func){
	var dataString = "className="+className+"&packAge="+paceAge+"&returnType=1";
	if(param != null) dataString = dataString+"&"+param;
	$(document.getElementById(elementId)).load("AjaxDisplayServlet",dataString,
	function(response, status, xhr){
		if(func != null){
			new func();
		}else{
			htmlDisplayElementById(response,elementId);
		}	
	});	
}
/**
 * #SeptemWi
 * @deprecated
 * */
function getTitle(fieldName,fieldID,fielduse,scriptAction){
	var dataVal = $('#'+fieldName).val();
	var field_id = fieldID;
	var dataString = "ClassPackage=com.eaf.orig.ulo.pl.ajax&ClassName=GetTitleCodeByFieldID&title_val="+encodeURI(dataVal)+"&field_id="+field_id+"&field_use="+fielduse;
	var uri = "AjaxServlet";
	jQuery.ajax( {
		type :"POST",
		url :uri,
		data :dataString,
		async :false,
		
	success : function(data ,status ,xhr) {
		if(data=="NOT_Found"&&dataVal.length!=0){
			LoadTitlePopup(scriptAction,fieldName,fielduse);
			$('#'+fieldName).val("");
		} else{
			$('#'+fielduse).val(data);
//			#SeptemWi
//			if(fieldName=='titleThai'){ajaxDisplayElementObjectJson('GetGender','0',"CacheName=ListboxType"+"&title_thai="+data+"&fieldID="+fieldID);}
//			else if(fieldName=='titleEng'){ajaxDisplayElementObjectJson('GetGender','0',"CacheName=TMP"+"&title_thai="+data+"&fieldID="+fieldID);}
		}
		},
	error : function(data){
		alertMassage("error"+data);
	}
	});

}

function getDescByCode(fieldName,fielduse,title_table,scriptAction,fields){
//	alert(scriptAction);
	var dataVal = $('#'+fieldName).val();
	var dataString = "ClassPackage=com.eaf.orig.ulo.pl.ajax&ClassName=GetDescByCode&title_val="+encodeURI(dataVal)+"&title_table="+title_table;
	var uri = "AjaxServlet";
	jQuery.ajax({
		type :"POST",
		url :uri,
		data :dataString,
		async :false,		
		success : function(data ,status ,xhr) {
//			alert(data);
			if(data=="NOT_Found"&&dataVal.length!=0){
				if(title_table=='SaleInfo'){
					$('#'+fieldName).val(data);
					LoadSellPopup(fieldName, fielduse);
					}
				if(title_table=='BranchInfo'){
					$('#'+fieldName).val(data);
					LoadBranchPopup(fieldName, fielduse);
					}
				if(title_table=='FileCateGory'){ 
					$('#'+fieldName).val("");
					$('#'+fielduse).val("");
				}
			} else if(data!="NOT_Found"){
				$('#'+fielduse).val(data);
			}else{
				$('#'+fielduse).val("");
			}
		},
	error : function(data){
		alertMassage("error"+data);
	}
	});	
}
function ajaxManualHtmlWithNotfound(className,packAge,data,displayId,popupField){
	var dataString = "className="+className+"&packAge="+packAge+"&returnType=1";
	if(data!= null && data.length >0) dataString = dataString+"&"+data;
	jQuery.ajax( {
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,
		success : function(response ,status ,xhr){
			if(response.length!=0){
				htmlDisplayElementById(response,displayId);
			}else{
				$('#'+displayId).val('');
				$('#'+popupField).val('');
				openLayer2Popup(popupField);
			}
		},
		error : function(response){
		}
	});
}
function ajaxManualAddressWithNotfound(className,packAge,data,displayId,popupField){
	var dataString = "className="+className+"&packAge="+packAge+"&returnType=1";
	if(data!= null && data.length >0) dataString = dataString+"&"+data;
	jQuery.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,
		success : function(response ,status ,xhr){
			if(response == "0"){
				$('#'+popupField).val('');
				openLayer2Popup(popupField);
			}else if (response == "1"){
				openLayer2Popup(popupField);
			}else if (response.length!=0){
				htmlDisplayElementById(response,displayId);
				if(popupField == 'tambol'){getZipCode();}
			}
		},
		error : function(response){
		}
	});
}
