// naosformutil.js
// newFunction
function popupscript(action,field,cacheName,fieldDesc, dataValue) {
	var f = eval("document.appFormName." + field);
	if(f!=undefined){
		var value = f.value;
		}
	//alert("value= "+value);
	var campaignParameter = "";
	if (cacheName=="IntScheme" && document.appFormName.campaign!=undefined){
		campaignParameter = "&campaign="+document.appFormName.campaign.value;
	}
	var url = "/ORIGWeb/FrontController?action=" + action + "&" + field + "=" + value + "&cacheName=" + cacheName + "&textboxCode=" + field + "&textboxDesc=" + fieldDesc + campaignParameter + "&dataValue=" + dataValue;
	var childwindow = window.open(url,'','width=900,height=330,top=200,left=200,scrollbars=1');
	window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();};};	
	childwindow.onunload = function(){alert('closing');};
}
function popupscriptOneTextBox(action,field,cacheName,fieldDesc,dataValue) {
	var f = eval("document.appFormName." + fieldDesc);
	if(f!=undefined){
		var value = f.value;
		}
	var url = "/ORIGWeb/FrontController?action=" + action + "&" + fieldDesc + "=" + value + "&textboxCode=" + field + "&textboxDesc=" + fieldDesc + "&dataValue=" + dataValue;
	var childwindow = window.open(url,'','width=500,height=330,top=200,left=200,scrollbars=1');
	window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();};};	
	childwindow.onunload = function(){alert('closing')};
}

function popupscriptLoc(action,field,w,h,t,l) {
	var f = eval("document.appFormName." + field);
	if(f!=undefined){
		var value = f.value;
		}
	var url = "/ORIGWeb/FrontController?action=" + action + "&" + field + "=" + value + "&param=" + field + "&cacheName=" + cacheName;
	var childwindow = window.open(url,'','width=' + w + ',height=' + h + ',top=' + t + ',left=' + l + ',scrollbars=1');
	window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();};};	
	childwindow.onunload = function(){alert('closing')};
}
function popupscriptFields(action,fields,cacheName,fieldDesc, field, dataValue) {
//	alert('in script');
	var fs = fields;
	var strParam = "";
	if(fs != null){
		var params = "";
		for(var i = 0; i < fs.length; i++){
			if(i == 0){
				params = params + fs[i];
			}else{
				params = params + "," + fs[i];
			}
			strParam = strParam + "&" + fs[i] + "=" + eval("document.appFormName." + fs[i]).value;
		}
		strParam = strParam + "&param=" + params;
	}
	var url = "/ORIGWeb/FrontController?action=" + action + strParam + "&cacheName=" + cacheName + "&textboxCode=" + field + "&textboxDesc=" + fieldDesc + "&dataValue=" + dataValue;
	var childwindow = window.open(url,'','width=590,height=330,top=200,left=200,scrollbars=1');
	window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();};};	
	childwindow.onunload = function(){alert('closing');};
}

// call by displayPopUpTagScriptAction when have any fields and width, height, top, left
function popupscriptFieldsLoc(action,fields,w,h,t,l) {
	var fs = fields;
	var strParam = "";
	if(fs != null){
		var params = "";
		for(var i = 0; i < fs.length; i++){
			if(i == 0){
				params = params + fs[i];
			}else{
				params = params + "," + fs[i];
			}
			strParam = strParam + "&" + fs[i] + "=" + eval("document.appFormName." + fs[i]).value;
		}
		strParam = strParam + "&param=" + params;
	}
	var url = "/ORIGWeb/FrontController?action=" + action + strParam;
	var childwindow = window.open(url,'','width=' + w + ',height=' + h + ',top=' + t + ',left=' + l + ',scrollbars=1');
	window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();};};	
	childwindow.onunload = function(){alert('closing')};
}

function popupscriptBusClass(action,field,cacheName,fieldDesc,bus_class) {
	var f = eval("document.appFormName." + field);
	if(f!=undefined){
		var value = f.value;
		}
	var campaignParameter = "";
	if (cacheName=="IntScheme" && document.appFormName.campaign!=undefined){
		campaignParameter = "&campaign="+document.appFormName.campaign.value;
	}
	var url = "/ORIGWeb/FrontController?action=" + action + "&" + field + "=" + value + "&cacheName=" + cacheName + "&textboxCode=" + field + "&textboxDesc=" + fieldDesc + campaignParameter + "&bus_class=" + bus_class;
	var childwindow = window.open(url,'','width=500,height=330,top=200,left=200,scrollbars=1');
	window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();};};	
	childwindow.onunload = function(){alert('closing')};
}

function copySectionField(source,destination){
	var s = source;
	var d = destination;
	if(s.length == d.length){
		var sourceStr = "";
		var destinationStr = "";
		for(var i = 0; i < s.length; i++){
			sourceStr = s[i];
			destinationStr = d[i];
			var sourceObj = eval("document.appFormName." + sourceStr);
			var destinationObj = eval("document.appFormName." + destinationStr);
			if(sourceObj.type == destinationObj.type){
				destinationObj.value = sourceObj.value;
			}
		}
	}
}

// isIEObject(a)
// Internet Explorer holds references to objects that are not actually javascript objects. So if we use the objects in javascript it will give error. But the typeof operator identifies them as javascript objects( problem!!!). Here we can use the isIEObject() function to identify those objects.
function isIEObject(a) 
{
	return isObject(a) && typeof a.constructor != 'function';
}

// isArray(a)
// This function returns true if a is an array, meaning that it was produced by the Array constructor or by the [ ] array literal notation.
function isArray(a) 
{
  	return isObject(a) && a.constructor == Array;
}

// isBoolean(a)
// This function returns true if a is one of the Boolean values, true or false. 
function isBoolean(a) 
{
	return typeof a == 'boolean';
}

// isEmpty(a)
// This function returns true if a is an object or array or function containing no enumerable members.
function isEmpty(o) 
{
    if (isObject(o)) 
 	{
       for (var i in o) 
   	   {
          return false;
       }
    }
    return true;
}

// isFunction(a)
// This function returns true if a is a function. Beware that some native functions in IE were made to look like objects instead of functions. This function does not detect that.Netscape is better behaved in this regard.
function isFunction(a) 
{
    return typeof a == 'function';
}

// isNull(a)
// This function returns true if a is the null value.
function isNull(a) 
{
  	return typeof a == 'object' && !a;
}

// isObject(a)
// This function returns true if a is an object, array, or function. It returns false if a is a string, number, Boolean, null, or undefined.
function isNumber(a) 
{
   	return typeof a == 'number' && isFinite(a);
}

// isObject(a)
// This function returns true if a is an object, array, or function. It returns false if a is a string, number, Boolean, null, or undefined.
function isObject(a) 
{
    return (typeof a == 'object' && !!a) || isFunction(a);
}

// isString(a)
// This function returns true if a is a string.
function isString(a) 
{
  	return typeof a == 'string';
}

// isUndefined(a)
// This function returns true if a is the undefined value. You can get the undefined value from an uninitialized variable or from an object's missing member.
function isUndefined(a) 
{
  	return typeof a == 'undefined';
} 


function calculateAgeRetStr(dob){
	var strValue = dob ;
	var arrayDate = strValue.split('/');
	
	var intYear = parseInt(arrayDate[0],10);
	var intMonth = arrayDate[1];
	var intDate = arrayDate[2];
	
	var currentDate = new Date();
	var cYear  = currentDate.getFullYear();
	var cMonth = currentDate.getMonth() + 1;	
	var cDate = currentDate.getDate();
		
	var yearDiff = 0;
	
	//var inputDate = new Date(intYear+1500,intMonth-1,intDate);

	if(cMonth < intMonth || (cMonth == intMonth && cDate < intDate)){
		yearDiff = 1;
	}
	
	cYear = cYear - 1911;
	
	if(strValue != ''){
		var age =((cYear-intYear-yearDiff).toString());
		var age2 = 0;
		if(cDate < intDate){
			age2 = cDate + 30 - intDate;
		}else{
			age2 = cDate - intDate;
		}
		if(cMonth < intMonth){
			age2 = age2 + (cMonth + 12 - intMonth)*30;
		}else{
			age2 = age2 + (cMonth - intMonth)*30;
		}
		age2 = parseInt(age2/30*10/12);
		return age+"."+age2;
		
	}else{
		return '';
	}
}

function isNumber(input){
    if(input.value.length > 0 && !input.value.match(/^[0-9]+$/g)){
       input.value = input.value.replace(/[^0-9,^'.']*/g,'');
    }
}
  
function enableFields(form){
	var fields = form.elements;
	for(var i = 0; i < fields.length ; i++){
		var field = fields[i];
		if(!isUndefined(field)){
			field.disabled = false;
		}
	}
}

function disableFields(form){
	var fields = form.elements;
	for(var i = 0; i < fields.length ; i++){
		var field = fields[i];
		if(!isUndefined(field)){
			field.disabled = true;
		}
	}
}

function backToTop(){
	var appForm = window.document.appFormName;
	if(isObject(appForm)){
		var topText = appForm.topText;
		if(isObject(topText)){
			topText.focus();
		}
	}
}

function completeScriptPopup(name,action,fields){
	completeScriptPopups(name,action,fields,null);
}

function completeScriptPopups(name,action,fields,outputs){
	var fs = fields;
	if(fs != null && fs.length > 0){
		try{
			var req = new DataRequestor();
			var url = action;
			for(var i = 0; i < fs.length; i++){
				req.addArg(_POST, fs[i] , eval("document.appFormName." + fs[i]).value );
			}
			req.getURL(url, _RETURN_AS_DOM);
			
			req.onload = function (data, obj) {
				var root = data.documentElement;
				var matchResults = root.getElementsByTagName("Match_Flag");
				if(matchResults != null && matchResults.length > 0){
					var matchResult = matchResults[0].firstChild.nodeValue;
					if("T" == matchResult){
						if(outputs != null){
							var objOutput;
							var outputResults = root.getElementsByTagName("Output");
							for(var i = 0; i < outputs.length; i++){
								objOutput = eval("document.appFormName." + outputs[i]);
								if(isObject(objOutput) && outputResults.length >= i){
									objOutput.value = outputResults[i].firstChild.nodeValue;
								}
							}
						}
					}else{
						var objectChk = eval("document.appFormName." + fs[0]);
						if(outputs != null){
							var objOutput;
							for(var i = 0; i < outputs.length; i++){
								var sameReqFlg = false;
								for(var j = 0; j < fs.length; j++){
									if(outputs[i] == fs[j]){
										sameReqFlg = true;
									}
								}
								if(!sameReqFlg){
									objOutput = eval("document.appFormName." + outputs[i]);
									if(isObject(objOutput)){
										objOutput.value = "";
									}
								}
							}
						}
						if("F" == matchResult){
							alert("You key " + name + " does not match");
							objectChk.select();
							objectChk.focus();
						}else if("E" == matchResult){
							alert("Error, system can not check " + name);
						}
					}
				}
			}
		} catch(excompleteScriptPopups) {
			alert("Error excompleteScriptPopups : "+excompleteScriptPopups);
		}
	}
}

function loadPopup(name,action,width,height,top,left,seq,type) {
    var coborrowerFlag="";
	if(name == 'guarantor'){
		if(document.appFormName.type != null){
			type = document.appFormName.type.value;
			 
		}
		if(document.appFormName.coborrowerFlag !=null){
		 coborrowerFlag=document.appFormName.coborrowerFlag.value
		}
	}
	
	//alert(type);
	var url = "/ORIGWeb/FrontController?action=" + action + "&seq=" + seq + "&type=" + type+"&coborrowerFlag="+coborrowerFlag;
	var childwindow = window.open(url,name,'width='+width+',height='+height+',top='+top+',left='+left+',scrollbars=1,status=1');
	window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();};};	
	childwindow.onunload = function(){alert('closing')};
}
//new Parameter Load Poupup By Sankom 20080424
function loadPopup(name,action,width,height,top,left,seq,type,personalType) {
    var coborrowerFlag="";
	if(name == 'guarantor'){
		if(document.appFormName.type != null){
			type = document.appFormName.type.value;
		}
		if(document.appFormName.coborrowerFlag !=null){
		 coborrowerFlag=document.appFormName.coborrowerFlag.value
		}
	}	
//	alert(personalType);
	var url = "/ORIGWeb/FrontController?action=" + action + "&seq=" + seq + "&type=" + type+"&appPersonalType="+personalType+"&coborrowerFlag="+coborrowerFlag;
	var childwindow = window.open(url,name,'width='+width+',height='+height+',top='+top+',left='+left+',scrollbars=1,status=1');
	window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();};};	
	childwindow.onunload = function(){alert('closing')};
}
function loadAppScoreSummaryPopup(name,action,width,height,top,left,type) {

	var accept 	= document.appFormName.accept.value;
	var reject 	= document.appFormName.reject.value;
	var scConstant 	= document.appFormName.scConstant.value;
	var weight 	= document.appFormName.weight.value;
	var accept_used	= document.appFormName.accept_used.value;
	var reject_used	= document.appFormName.reject_used.value;
	
	if(name == 'guarantor'){
		type = document.appFormName.type.value;
	}
	//alert(type);
	var url = "/ORIGWeb/FrontController?action=" + action + "&accept=" + accept + "&reject=" + reject + "&scConstant=" + scConstant + "&weight=" + weight + "&accept_used=" + accept_used + "&reject_used=" + reject_used;
	var childwindow = window.open(url,'','width='+width+',height='+height+',top='+top+',left='+left+',scrollbars=1');
	window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();};};	
	childwindow.onunload = function(){alert('closing')};
}

function loadCorperatedPopup(name,action,width,height,top,left,year,seq,type) {
	if(name == 'guarantor'){
		type = document.appFormName.type.value;
	}
	//alert(type);
	var url = "/ORIGWeb/FrontController?action=" + action + "&year=" + year + "&seq=" + seq + "&type=" + type;
	var childwindow = window.open(url,'','width='+width+',height='+height+',top='+top+',left='+left+',scrollbars=1');
	window.onfocus = function(){if (childwindow.closed == false){childwindow.focus();};};	
	childwindow.onunload = function(){alert('closing')};
}

function deleteTableList(objSeqName, replaceID, servletName){
	var objSeq = eval("window.document.appFormName."+objSeqName);
	var strValue = "";
	var isValid = false;
	if (!isUndefined(objSeq)){
		if (isObject(objSeq) && !isUndefined(objSeq.length)) {
			if (objSeq.length > 0) {
				for (var i = 0; i < objSeq.length ; i++) {
					if (objSeq[i].checked) {
						isValid = true;
						if ("" == strValue) {
							strValue = strValue + objSeq[i].value;
						} else {
							strValue = strValue + "," + objSeq[i].value;
						}
					}
				}
			}		
		} else {
			if(objSeq != null){
				if (objSeq.checked) {
					strValue = strValue + objSeq.value;
					isValid = true;
				}
			}else{
				return true;
			}
		}
	}
	if(!isValid){
		alert("Please select at least one item.");
		return false;
	}
	var delConfirm = confirm("Are you sure to delete?");
	if (delConfirm) {
		var req = new DataRequestor();
		var url = servletName;
		req.addArg(_POST, objSeqName, strValue);
		req.getURL(url);
		
		req.onload = function (data, obj) {
			if(data != ""){
				var replaceObj =  document.getElementById(replaceID);
				replaceObj.innerHTML = data;
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
		//document.appFormName.pae.value = result;
	    return result;
	}
	
	var hexChars = "0123456789ABCDEF";

	function Dec2Hex (Dec) {
	var a = Dec % 16;
	var b = (Dec - a)/16;
	hex = "" + hexChars.charAt(B) + hexChars.charAt(a);
	return hex;
	}
	
	function thai(s){
		s2='';
		for(var i=0;i<s.length;i++){
			if(s.charCodeAt(i)>3423){
				n=s.charCodeAt(i)-3424;
				s2+='%'+Dec2Hex(n);
			}else s2+=s.charAt(i);
		}
		//document.appFormName.pae.value = s2;
		return s2;
	}
	
function mandatoryField(customerType, userRole, formId, closePopup) {
	try {
		setPosXY(45,40);
		var req = new DataRequestor();
		var url = "MandatoryServlet";
		var frm = document.appFormName;
		if (closePopup!=null || closePopup!=undefined){
			frm.closeGuarantorPop.value = closePopup;
		}
		if(frm.car_kilometers!=undefined){
  	      frm.car_kilometers.value=removeCommas( frm.car_kilometers.value);
  	    }
  	    if(frm.pre_score_share_capital!=undefined){
  	     frm.pre_score_share_capital.value=removeCommas( frm.pre_score_share_capital.value);
  	    }
		req.addArg(_POST, "cus_type", customerType);
		if (formId!=null || formId!=undefined){
			req.addArg(_POST, "FORM_ID", formId);
		}
		req.addArgsFromForm("appFormName");
		req.getURLForThai(url, _RETURN_AS_DOM);
		var span = document.getElementById("errorMessage");
		
		req.onload = function (data, obj) {
				if(frm.car_kilometers!=undefined){
	        	   addCommas("car_kilometers");
	        	}
	         if(frm.pre_score_share_capital!=undefined){
        	   addCommas( "pre_score_share_capital");
        	   }
        	   //alert('data='+data);
				if (data!=null) {
					// Display error message
					var datas = data.documentElement;
					if(datas != null){
						var error = datas.getElementsByTagName("error_message");
						var err = error[0].firstChild.nodeValue;
						//Set error field
						var lists = datas.getElementsByTagName("error_field");
						var list = lists[0];
						var childs = list.childNodes;
						for(i = 0; i < childs.length; i++){
							var field = childs[i].firstChild.nodeValue;
							var x=document.getElementById(field);
							if(x != null){
								if(x.type == 'text'){
									if(x.className == 'inputformnew' || x.className == 'inputformnewRed' || x.className == 'inputformnewCurrency') {
										x.className = "inputformnewRed";
									} else {
										x.className = "textboxRed";
									}
								}else{
									x.className = "ComboBoxRed";
								}
							}
						}
						
						//Set no error field
						var lists = datas.getElementsByTagName("no_error_field");
						var list = lists[0];
						var childs = list.childNodes;
						for(i = 0; i < childs.length; i++){
							var field = childs[i].firstChild.nodeValue;
							if(frm.field != null){
								var x=document.getElementById(field);
								if(x.type == 'text'){
									if(x.className == "inputformnew" || x.className == 'inputformnewRed' || x.className == 'inputformnewCurrency') {
										x.className = "inputformnew";
									} else {
										x.className = "textbox";
									}
								}else{
									x.className = "combobox";
								}
							}
						}
						
						span.innerHTML = err;
						span.style.display = "block";
						backToTop();
					}else{
						span.innerHTML = "";
						span.style.display = "none";
						//alert('If data=null1');
						submitApp(userRole);
						//alert('If data=null2');
					}
				} else {
					span.innerHTML = "";
					span.style.display = "none";
					submitApp(userRole);
				}

			} //end req.onload = function (data, obj) {
		
	} catch(er) {
		alert("Error MandatoryField : "+er);
	}
}
function mandatoryFieldGuarantor(customerType, userRole) {
	try {
		var frm = document.appFormName;
		frm.closeGuarantorPop.value = "Y";
		var req = new DataRequestor();
		var url = "MandatoryServlet";
		req.addArg(_POST, "cus_type", customerType);
		req.addArgsFromForm("appFormName");
		req.getURLForThai(url, _RETURN_AS_DOM);
		var span = document.getElementById("errorMessage");
		
		req.onload = function (data, obj) {
				if (data!=null) {
					// Display error message
					var datas = data.documentElement;
					if(datas != null){
						var error = datas.getElementsByTagName("error_message");
						var err = error[0].firstChild.nodeValue;
						//Set error field
						var lists = datas.getElementsByTagName("error_field");
						var list = lists[0];
						var childs = list.childNodes;
						for(i = 0; i < childs.length; i++){
							var field = childs[i].firstChild.nodeValue;
							var x=document.getElementById(field);
							if(x != null){
								if(x.type == 'text'){
									x.className = "textboxRed";
								}else{
									x.className = "ComboBoxRed";
								}
							}
						}
						
						//Set no error field
						var lists = datas.getElementsByTagName("no_error_field");
						var list = lists[0];
						var childs = list.childNodes;
						for(i = 0; i < childs.length; i++){
							var field = childs[i].firstChild.nodeValue;
							var x=document.getElementById(field);
							if(x.type == 'text'){
								x.className = "textbox";
							}else{
								x.className = "combobox";
							}
						}
						span.innerHTML = err;
						span.style.display = "block";
						backToTop();
					}else{
						span.innerHTML = "";
						span.style.display = "none";
						saveGuarantor(userRole);
					}
				} else {
					span.innerHTML = "";
					span.style.display = "none";
					saveGuarantor(userRole);
				}

			} //end req.onload = function (data, obj) {
		
	} catch(er) {
		alert("Error MandatoryField : "+er);
	}
}
function mandatoryPopupField(customerType, popupType) {
	try {
		var frm = document.appFormName;
		if(frm.car_kilometers!=undefined){
  	      frm.car_kilometers.value=removeCommas( frm.car_kilometers.value);
  	    }
		var req = new DataRequestor();
		var url = "MandatoryPopupServlet";
		req.addArg(_POST, "cus_type", customerType);
		req.addArg(_POST, "popupType", popupType);
		req.addArgsFromForm("appFormName");
		req.getURLForThai(url, _RETURN_AS_DOM);
		
		var span = document.getElementById("errorMessage");
		var valid = false;
		
		req.onload = function (data, obj) {
				if(frm.car_kilometers!=undefined){
	        	   addCommas("car_kilometers");
	        	}
				if (data!=null) {
					// Display error message
					var datas = data.documentElement;
					if(datas != null){
						var error = datas.getElementsByTagName("error_message");
						var err = error[0].firstChild.nodeValue;
						//Set error field
						var lists = datas.getElementsByTagName("error_field");
						var list = lists[0];
						var childs = list.childNodes;
						for(i = 0; i < childs.length; i++){
							var field = childs[i].firstChild.nodeValue;
							var x=document.getElementById(field);
							if(x != null){
								if(x.type == 'text'){
									x.className = "textboxRed";
								}else{
									x.className = "ComboBoxRed";
								}
							}
						}
						
						//Set no error field
						var lists = datas.getElementsByTagName("no_error_field");
						var list = lists[0];
						var childs = list.childNodes;
						for(i = 0; i < childs.length; i++){
							var field = childs[i].firstChild.nodeValue;
							var x=document.getElementById(field);
							if(x.type == 'text'){
								x.className = "textbox";
							}else{
								x.className = "combobox";
							}
						}
						span.innerHTML = err;
						span.style.display = "block";
						backToTop();
						return false;
					}else{
						span.innerHTML = "";
						span.style.display = "none";
						saveData(customerType,popupType);
					}
				} else {
					span.innerHTML = "";
					span.style.display = "none";
					saveData(customerType,popupType);
				}
				//valid = true;
				//return valid;
			} //end req.onload = function (data, obj) {
			//return valid;
	} catch(er) {
		alert("Error mandatoryPopupField : "+er);
	}
}
function mandatoryLoanPopupField(customerType, loanType) {
	try {
		var frm = document.appFormName;
		var haveVat = frm.vat.value;
		if(haveVat == 0){
			haveVat = "N";
		}else{
			haveVat = "Y";
		}
		var haveBalloon;
		if(frm.balloon_flag != null){
			haveBalloon = frm.balloon_flag.value;
		}
 	  if(frm.appraisal_price!=undefined){
  	      frm.appraisal_price.value=removeCommas( form.appraisal_price.value);
  	    }
  	    var hire_purchase_cost="";
  	   if(frm.hire_purchase_cost !=undefined){
  	   hire_purchase_cost=removeCommas(frm.hire_purchase_cost.value);
  	   }
  	   var hire_purchase_total="";
  	   if(frm.hire_purchase_total!=undefined){
  	    hire_purchase_total=removeCommas(frm.hire_purchase_total.value);
  	   }
		var req = new DataRequestor();
		var url = "MandatoryLoanPopupServlet";
		req.addArg(_POST, "cus_type", customerType);
		req.addArg(_POST, "loanType", loanType);
		req.addArg(_POST, "haveVat", haveVat);
		req.addArg(_POST, "haveBalloon", haveBalloon);
		req.addArg(_POST, "hire_purchase_total",hire_purchase_total);
		req.addArg(_POST, "hire_purchase_cost",hire_purchase_cost);
		//removeCommaAllFields(frm);
		
		req.addArgsFromForm("appFormName");
		req.getURLForThai(url, _RETURN_AS_DOM);
		var span = document.getElementById("errorMessage");
		var valid = false;
		req.onload = function (data, obj) {
		if(frm.appraisal_price!=undefined){
        	   addCommas( "appraisal_price");
        	   }
				if (data!=null) {
					// Display error message
					var datas = data.documentElement;
					if(datas != null){
						var error = datas.getElementsByTagName("error_message");
						var err = error[0].firstChild.nodeValue;
						//Set error field
						var lists = datas.getElementsByTagName("error_field");
						var list = lists[0];
						var childs = list.childNodes;
						for(i = 0; i < childs.length; i++){
							var field = childs[i].firstChild.nodeValue;
							var x=document.getElementById(field);
							if(x != null){
								if(x.type == 'text'){
									x.className = "textboxRed";
								}else{
									x.className = "ComboBoxRed";
								}
							}
						}
						
						//Set no error field
						var lists = datas.getElementsByTagName("no_error_field");
						var list = lists[0];
						var childs = list.childNodes;
						for(i = 0; i < childs.length; i++){
							var field = childs[i].firstChild.nodeValue;
							var x=document.getElementById(field);
							if(x.type == 'text'){
								if(frm.balloon_flag != null){
									if(!frm.balloon_flag.checked && field == "balloon_total"){
										x.className = "textboxCurrencyReadOnly";
									}else{
										x.className = "textbox";
									}
								}else{
									x.className = "textbox";
								}
							}else{
								x.className = "combobox";
							}
						}
						span.innerHTML = err;
						span.style.display = "block";
						backToTop();
						return false;
					}else{
						span.innerHTML = "";
						span.style.display = "none";
						calculateLoan("save");
					}
				} else {
					span.innerHTML = "";
					span.style.display = "none";
					calculateLoan("save");
				}
				//valid = true;
				//return valid;
			} //end req.onload = function (data, obj) {
			//return valid;
	} catch(er) {
		alert("Error mandatoryPopupField : "+er);
	}
}
function mandatoryPLoanPopupField(customerType, loanType) {
	try {
		var frm = document.appFormName;
	
		var req = new DataRequestor();
		var url = "MandatoryPLoanPopupServlet";
		req.addArg(_POST, "cus_type", customerType);
		req.addArg(_POST, "loanType", loanType);
		req.addArg(_POST, "calulateFlag", 'Y');		
		removeCommaAllFields(frm);
		req.addArgsFromForm("appFormName");		
		req.getURLForThai(url, _RETURN_AS_DOM);
		var span = document.getElementById("errorMessage");
		var valid = false;
		req.onload = function (data, obj) {		
				if (data!=null) {
					// Display error message
					var datas = data.documentElement;
					if(datas != null){
						var error = datas.getElementsByTagName("error_message");
						var err = error[0].firstChild.nodeValue;
						//Set error field
						var lists = datas.getElementsByTagName("error_field");
						var list = lists[0];
						var childs = list.childNodes;
						for(i = 0; i < childs.length; i++){
							var field = childs[i].firstChild.nodeValue;
							var x=document.getElementById(field);
							if(x != null){
								if(x.type == 'text'){
									x.className = "textboxRed";
								}else{
									x.className = "ComboBoxRed";
								}
							}
						}
						//Set no error field
						var lists = datas.getElementsByTagName("no_error_field");
						var list = lists[0];
						var childs = list.childNodes;
						for(i = 0; i < childs.length; i++){
							var field = childs[i].firstChild.nodeValue;
							var x=document.getElementById(field);
							if(x.type == 'text'){
								if(frm.balloon_flag != null){
									if(!frm.balloon_flag.checked && field == "balloon_total"){
										x.className = "textboxCurrencyReadOnly";
									}else{
										x.className = "textbox";
									}
								}else{
										x.className = "textbox";
								}
							}else{
								x.className = "combobox";
							}
						}
						span.innerHTML = err;
						span.style.display = "block";
						backToTop();
						return false;
					}else{
						span.innerHTML = "";
						span.style.display = "none";
						calculatePLoan("save");
					}
				} else {
					span.innerHTML = "";
					span.style.display = "none";
					calculatePLoan("save");
				}
				//valid = true;
				//return valid;
			} //end req.onload = function (data, obj) {
			//return valid;
	} catch(er) {
		alert("Error mandateLoanForCal : "+er);
	}
}
function backToTop(){
	window.location.href = "#";
}
function setFieldError(){
	setFieldError1();
}
function retrieveData(){
	//try{
		var form = document.appFormName;
		var personalId = form.identification_no.value;
		var customerType = form.customer_type.value;
		var appPersonalType=form.appPersonalType.value;
		//alert(personalId);
		var req = new DataRequestor();
		var url = "RetrievePersonalInfoServlet";
		req.addArg(_POST, "identification_no", personalId);
		req.addArg(_POST, "customer_type", customerType);
		req.addArg(_POST, "appPersonalType", appPersonalType);
		req.getURL(url, _RETURN_AS_DOM);
		req.onload = function (data, obj) {
			if (data!=null) {
				//if(data != 'Session Time Out'){
					var list = data.documentElement;
					var fields = list.childNodes;
					//alert(fields.length);
					for(var i=0; i<fields.length; i++){
						var field = fields[i];
						//alert("field ->["+i+"] " + field.nodeName);
						var values = "";
						if(field.firstChild != null){
							values = field.firstChild.nodeValue;
						}
						//alert("value -> "+ values);
						var fieldName = field.getAttribute('name');
						var positionValue;
						var m_positionValue;
						var pre_score_positionValue;
						//alert("fieldName -> ["+i+"]" + fieldName);
						if(fieldName != "foundAddress" && fieldName != "Finance" && fieldName != "ChangeName" && fieldName != "error" && fieldName != "Address"){
							if(values != null){
								var resultField = document.getElementById(fieldName);
								//alert("fieldName -> ["+i+"]" + fieldName+" resultField="+resultField+" values="+values);
								if(resultField != null){
									resultField.setAttribute('value', values);
								}
								if(form.position != null){
									if(fieldName == "occupation"){
										//alert(values+" >>>1 fiede name >>> "+ fieldName);
										parseOccupationToPosition(values,'PositionId','position');
									}
									if(fieldName == "position"){
										//alert(values);
										positionValue = values;
									}
								}
								if(form.m_position != null){
									if(fieldName == "m_occupation"){
										parseOccupationToPosition(values,'MPositionId','m_position');
									}
									if(fieldName == "m_position"){
										m_positionValue = values;
									}
								}
								if(form.pre_score_position != null){
									if(fieldName == "pre_score_occupation"){
										parseOccupationToPosition(values,'preScorePositionId','pre_score_position');
									}
									if(fieldName == "pre_score_position"){
										pre_score_positionValue = values;
									}
								}
							}
						}else if(fieldName == "error"){
							alert(values);
							return false;
						}else{
							var resultField = document.getElementById(fieldName);
							//if(fieldName == "foundAddress"){
							//	resultField.innerHTML = "Found "+values+" Records";
							//	resultField.className = "foundText";
							//	if(values == 0){
							//		form.viewAddressBnt.disabled = true;
							//	}else{
							//		form.viewAddressBnt.disabled = false;
							//	}
							//}else{
							//if(resultField!=null){
							//if(fieldName == "Address"){
							// alert("address value "+values);
							//}
							if (resultField!=null || resultField!=undefined){
								resultField.innerHTML = values;
							}
							//	}
							//}
						}
						if(document.appFormName.m_birth_date != null){
							checkBirthDate('m_birth_date','m_age');
						}
						checkBirthDate('birth_date','age');
						form.identification_no.readOnly = true;
						var iden = document.getElementById('identification_no');
						iden.className = "textboxReadOnly";
						if(form.position != null){
							form.position.value = positionValue;
						}
						if(form.m_position != null){
							form.m_position.value = m_positionValue;
						}
						if(form.pre_score_position != null){
							form.pre_score_position.value = pre_score_positionValue;
						}
					}
				//}else{
					//alert('Session Time Out');
				//}
			}
		}
	//} catch(er) {
	//	alert("Error Retieve Data : "+er);
	//}
}

function editPersonal(){
	var form = document.appFormName;
	form.identification_no.value = "";
	var req = new DataRequestor();
	var url = "EditPersonalInfoServlet";
	req.getURL(url, _RETURN_AS_DOM);
	req.onload = function (data, obj) {
		if (data!=null) {
			var list = data.documentElement;
			var fields = list.childNodes;
			//alert(fields.length);
			for(i=0; i<fields.length; i++){
				var field = fields[i];
				//alert("field ->" + field.nodeName);
				var values = "";
				if(field.firstChild != null){
					values = field.firstChild.nodeValue;
				}
				//alert("value -> "+ values);
				var fieldName = field.getAttribute('name');
				//alert("fieldName ->" + fieldName);
				if(fieldName != "Address" && fieldName != "foundAddress" && fieldName != "Finance" && fieldName != "ChangeName" && fieldName != "error"){
					if(values != null){
						var resultField = document.getElementById(fieldName);
						if(resultField != null){
							resultField.setAttribute('value', values);
						}
					}
				}else if(fieldName == "error"){
					alert(values);
					return false;
				}else{
					var resultField = document.getElementById(fieldName);
					///if(fieldName == "foundAddress"){
					//	resultField.innerHTML = "Found "+values+" Records";
					//	resultField.className = "foundText";
					//	if(values == 0){
					//		form.viewAddressBnt.disabled = true;
					//	}else{
					//		form.viewAddressBnt.disabled = false;
					//	}
					//}else{
					if(fieldName != "Address" && fieldName != "foundAddress" && fieldName != "Finance" && fieldName != "ChangeName" && fieldName != "error"){
						resultField.innerHTML = values;
					}
					//}
				}
				form.identification_no.readOnly = false;
				form.identification_no.value = "";
				var iden = document.getElementById('identification_no');
				iden.className = "textbox";
			}
		}
	}
}
function mandateLoanForCal(customerType, loanType){
	try {
		var frm = document.appFormName;
		var haveVat = frm.vat.value;
		if(haveVat == 0){
			haveVat = "N";
		}else{
			haveVat = "Y";
		}
		var haveBalloon;
		if(frm.balloon_flag != null){
			haveBalloon = frm.balloon_flag.value;
		}
	
		var req = new DataRequestor();
		var url = "MandatoryLoanPopupServlet";
		req.addArg(_POST, "cus_type", customerType);
		req.addArg(_POST, "loanType", loanType);
		req.addArg(_POST, "haveVat", haveVat);
		req.addArg(_POST, "haveBalloon", haveBalloon);
		req.addArg(_POST, "calulateFlag", 'Y');		
		removeCommaAllFields(frm);
		req.addArgsFromForm("appFormName");		
		req.getURLForThai(url, _RETURN_AS_DOM);
		var span = document.getElementById("errorMessage");
		var valid = false;
		req.onload = function (data, obj) {		
				if (data!=null) {
					// Display error message
					var datas = data.documentElement;
					if(datas != null){
						var error = datas.getElementsByTagName("error_message");
						var err = error[0].firstChild.nodeValue;
						//Set error field
						var lists = datas.getElementsByTagName("error_field");
						var list = lists[0];
						var childs = list.childNodes;
						for(i = 0; i < childs.length; i++){
							var field = childs[i].firstChild.nodeValue;
							var x=document.getElementById(field);
							if(x != null){
								if(x.type == 'text'){
									x.className = "textboxRed";
								}else{
									x.className = "ComboBoxRed";
								}
							}
						}
						//Set no error field
						var lists = datas.getElementsByTagName("no_error_field");
						var list = lists[0];
						var childs = list.childNodes;
						for(i = 0; i < childs.length; i++){
							var field = childs[i].firstChild.nodeValue;
							var x=document.getElementById(field);
							if(x.type == 'text'){
								if(frm.balloon_flag != null){
									if(!frm.balloon_flag.checked && field == "balloon_total"){
										x.className = "textboxCurrencyReadOnly";
									}else{
										x.className = "textbox";
									}
								}else{
										x.className = "textbox";
								}
							}else{
								x.className = "combobox";
							}
						}
						span.innerHTML = err;
						span.style.display = "block";
						backToTop();
						return false;
					}else{
						span.innerHTML = "";
						span.style.display = "none";
						calculateLoan();
					}
				} else {
					span.innerHTML = "";
					span.style.display = "none";
					calculateLoan();
				}
				//valid = true;
				//return valid;
			} //end req.onload = function (data, obj) {
			//return valid;
	} catch(er) {
		alert("Error mandateLoanForCal : "+er);
	}
}
function calculateLoan(type){
	var form = document.appFormName;
	//alert(personalId);
	if('save' == type){
		removeCommaAllFields(form);
	}
	//alert(form.car_price_total.value);
	var req = new DataRequestor();
	var url = "CalculateLoanServlet";
	req.addArgsFromForm("appFormName");
	
	req.getURLForThai(url, _RETURN_AS_DOM);
	//req.getURL(url, _RETURN_AS_DOM);
	req.onload = function (data, obj) {
		if (data!=null) {
			var list = data.documentElement;
			var fields = list.childNodes;
			//alert(fields.length);
			for(i=0; i<fields.length; i++){
				var field = fields[i];
				//alert("field ->" + field.nodeName);
				var values = "";
				if(field.firstChild != null){
					values = field.firstChild.nodeValue;
				}
				//alert("value -> "+ values);
				var fieldName = field.getAttribute('name');
				//alert("fieldName ->" + fieldName);
				if(values != null){
					var resultField = document.getElementById(fieldName);
					if(resultField != null){
						resultField.setAttribute('value', values);
					}
				}
			}
		}
		calculateAppraisalPercent();
		if(type == 'save'){
			saveData();
		}
	}
}

function mandatePLoanForCal(customerType, loanType){
	try {
		var frm = document.appFormName;
	
		var req = new DataRequestor();
		var url = "MandatoryPLoanPopupServlet";
		req.addArg(_POST, "cus_type", customerType);
		req.addArg(_POST, "loanType", loanType);
		req.addArg(_POST, "calulateFlag", 'Y');		
		removeCommaAllFields(frm);
		req.addArgsFromForm("appFormName");		
		req.getURLForThai(url, _RETURN_AS_DOM);
		var span = document.getElementById("errorMessage");
		var valid = false;
		req.onload = function (data, obj) {		
				if (data!=null) {
					// Display error message
					var datas = data.documentElement;
					if(datas != null){
						var error = datas.getElementsByTagName("error_message");
						var err = error[0].firstChild.nodeValue;
						//Set error field
						var lists = datas.getElementsByTagName("error_field");
						var list = lists[0];
						var childs = list.childNodes;
						for(i = 0; i < childs.length; i++){
							var field = childs[i].firstChild.nodeValue;
							var x=document.getElementById(field);
							if(x != null){
								if(x.type == 'text'){
									x.className = "textboxRed";
								}else{
									x.className = "ComboBoxRed";
								}
							}
						}
						//Set no error field
						var lists = datas.getElementsByTagName("no_error_field");
						var list = lists[0];
						var childs = list.childNodes;
						for(i = 0; i < childs.length; i++){
							var field = childs[i].firstChild.nodeValue;
							var x=document.getElementById(field);
							if(x.type == 'text'){
								if(frm.balloon_flag != null){
									if(!frm.balloon_flag.checked && field == "balloon_total"){
										x.className = "textboxCurrencyReadOnly";
									}else{
										x.className = "textbox";
									}
								}else{
										x.className = "textbox";
								}
							}else{
								x.className = "combobox";
							}
						}
						span.innerHTML = err;
						span.style.display = "block";
						backToTop();
						return false;
					}else{
						span.innerHTML = "";
						span.style.display = "none";
						calculatePLoan();
					}
				} else {
					span.innerHTML = "";
					span.style.display = "none";
					calculatePLoan();
				}
				//valid = true;
				//return valid;
			} //end req.onload = function (data, obj) {
			//return valid;
	} catch(er) {
		alert("Error mandateLoanForCal : "+er);
	}
}
function calculatePLoan(type){
	var form = document.appFormName;
	//alert(personalId);
	if('save' == type){
		removeCommaAllFields(form);
	}
	//alert(form.car_price_total.value);
	var req = new DataRequestor();
	var url = "CalculatePLoanServlet";
	req.addArgsFromForm("appFormName");
	
	req.getURLForThai(url, _RETURN_AS_DOM);
	//req.getURL(url, _RETURN_AS_DOM);
	req.onload = function (data, obj) {
		if (data!=null) {
			var list = data.documentElement;
			var fields = list.childNodes;
			//alert(fields.length);
			for(i=0; i<fields.length; i++){
				var field = fields[i];
				//alert("field ->" + field.nodeName);
				var values = "";
				if(field.firstChild != null){
					values = field.firstChild.nodeValue;
				}
				//alert("value -> "+ values);
				var fieldName = field.getAttribute('name');
				//alert("fieldName ->" + fieldName);
				if(values != null){
					var resultField = document.getElementById(fieldName);
					if(resultField != null){
						resultField.setAttribute('value', values);
					}
				}
			}
		}
		if(type == 'save'){
			saveData();
		}
	}
}

function mandatoryFieldSaveNewApp(customerType, userRole, saveType) {
	setPosXY(65,40);
	if(userRole == 'DE' || userRole == 'CMR' || userRole == 'UW'){
		try {
			var req = new DataRequestor();
			var url = "MandatoryServlet";
			req.addArg(_POST, "saveType", saveType);
			req.addArgsFromForm("appFormName");
			req.getURLForThai(url, _RETURN_AS_DOM);
			//req.getURLForThai(url, _RETURN_AS_DOM);
			var frm = document.appFormName;
			var span = document.getElementById("errorMessage");
			
			req.onload = function (data, obj) {
					if (data!=null) {
						// Display error message
						var datas = data.documentElement;
						if(datas != null){
							var error = datas.getElementsByTagName("error_message");
							var err = error[0].firstChild.nodeValue;
							//Set error field
							var lists = datas.getElementsByTagName("error_field");
							var list = lists[0];
							var childs = list.childNodes;
							for(i = 0; i < childs.length; i++){
								var field = childs[i].firstChild.nodeValue;
								var x=document.getElementById(field);
								if(x.type == 'text'){
									if(x.className == "inputformnew" || x.className == "inputformnewRed" || x.className == "inputformnewCurrency") {
										x.className = "inputformnewRed";
									} else {
										x.className = "textboxRed";
									}
								}else{
									x.className = "ComboBoxRed";
								}
							}
							
							//Set no error field
							var lists = datas.getElementsByTagName("no_error_field");
							var list = lists[0];
							var childs = list.childNodes;
							for(i = 0; i < childs.length; i++){
								var field = childs[i].firstChild.nodeValue;
								var x=document.getElementById(field);
								if(x.type == 'text'){
									if(x.className == "inputformnew" || x.className == 'inputformnewRed' || x.className == 'inputformnewCurrency') {
										x.className = "inputformnew";
									} else {
										x.className = "textbox";
									}
								}else{
									x.className = "combobox";
								}
							}
							span.innerHTML = err;
							span.style.display = "block";
							backToTop();
						}else{
							span.innerHTML = "";
							span.style.display = "none";
							saveApp(userRole);
						}
					} else {
						span.innerHTML = "";
						span.style.display = "none";
						saveApp(userRole);
					}
	
				} //end req.onload = function (data, obj) {
			} catch(er) {
				alert("Error MandatoryField : "+er);
			}
		}else{
				saveApp(userRole);
		}
}

function removeCommaAllFields(form){
	var fields = form.elements;
	var value;
	for(var i = 0; i < fields.length ; i++){
		var field = fields[i];
		if(!isUndefined(field)){
			//alert(field.value);
			if(!isUndefined(field.value)){
				value = removeCommas(field.value);
				//alert(value);
				field.value = value;
			}
		}
	}
}

function checkFieldBeforeDoAction(field, name){
	var fieldObj = eval("document.appFormName."+field);
	if(fieldObj != null){
		if(fieldObj.value == ""){
			alert("Please select "+name+".");
			return false;
		}
	}
	return true;
}
function clearDataWhenChangeField(fieldChange, fieldClear){
	var fieldChangeObj = eval("document.appFormName."+fieldChange);
	var fieldClearObj = eval("document.appFormName."+fieldClear);
	if(fieldClearObj != null){
		fieldClearObj.value = "";
	}
}
function setDescription(code, fieldDesc, cacheName, buttonName, bus_class){
	var form = document.appFormName;
	var req = new DataRequestor();
	var url = "LookupDescriptionServlet";
	var param1;
	if(cacheName == 'Branch'){
		param1 = form.bank.value;
	}else if(cacheName == 'CarModel'){
		param1 = form.car_brand.value;
	}
	if (cacheName=="IntScheme" && form.campaign!=undefined){
		req.addArg(_POST, "campaignCode", form.campaign.value);
	}
	if (cacheName=="Campaign" && form.campaign!=undefined){
		req.addArg(_POST, "bus_class", bus_class);
	}
	req.addArg(_POST, "code", code);
	req.addArg(_POST, "param1", param1);
	req.addArg(_POST, "cacheName", cacheName);
	req.getURL(url);
	//alert(cacheName);
	var buttonObj = eval("document.appFormName."+buttonName);
	req.onload = function (data, obj) {
		var fieldDescObj = eval("document.appFormName."+fieldDesc);
		if(data != null && data != ''){
			var datas = data.split(',');
			if (cacheName=="Campaign" && form.campaign!=undefined){
				if (fieldDescObj.value != datas[0]){
					fieldDescObj.value = datas[0];
					clearSchemeByCampaign();
				}
			}else{
				fieldDescObj.value = datas[0];
			}
			if(cacheName == 'CarModel'){
				if(datas[1] != null && datas[1] != ''){
					if(form != null){
						if(form.car_cc != null){
							form.car_cc.value = datas[2];
						}
						if(form.car_gear != null){
							if(datas[1] == 'A'){
								form.car_gear[0].checked = true;
								form.car_gear_tmp.value = 'A';
							}else{
								form.car_gear[1].checked = true;
								form.car_gear_tmp.value = 'M';
							}
						}
						if(form.car_gear_tmp != null){
							//form.car_gear_tmp.value = datas[1];
							form.car_weight.value = datas[3];
						}
					}
				}
			}
		}else{
			var fields = buttonName.split("Popup");
			var fieldName = fields[0];
			var fieldObj = eval("document.appFormName."+fieldName);
			fieldObj.value = ""; 
			fieldDescObj.value = "";
			if(code != ''){
				buttonObj.click();
			}
			if (cacheName=="Campaign" && form.campaign!=undefined){
				clearSchemeByCampaign();
			}
		}
	}
}
function setTitleDescription(code, fieldDesc, cacheName, buttonName, fieldrelate1, fieldrelate2){
	var form = document.appFormName;
	alert("NAOSFORMUTIL");
	if(code!=""){
		window.open("LookupTitleNameServlet?code="+code+"&cacheName="+cacheName+"&fieldDesc="+fieldDesc+"&fieldrelate1="+fieldrelate1+"&fieldrelate2="+fieldrelate2+"&buttonName="+buttonName,"loadTitleName","toolbar=no,status=no,resizable=no,height=0,width=0,top=2000,left=2000");
	}

}
function autoClickButton(buttonName){
	var buttonObj = eval("document.appFormName."+buttonName);
	buttonObj.click();
}
function setBranch(value, branchField, branchDescField){
	var form = document.appFormName;
	var branchObj = eval("document.appFormName."+branchField);
	var branchDescObj = eval("document.appFormName."+branchDescField);
	var branchBntObj = eval("document.appFormName."+branchField+"Popup");
	if(value != '' && value != null){
		branchObj.disabled = false;
		branchDescObj.disabled = false;
		branchBntObj.disabled = false;
	}else{
		branchObj.value = '';
		branchDescObj.value = '';
		branchObj.disabled = true;
		branchDescObj.disabled = true;
		branchBntObj.disabled = true;
	}
}
function setPosition(value, positionField){
	var form = document.appFormName;
	var positionObj = eval("document.appFormName."+positionField);
	if(value != '' && value != null){
		positionObj.disabled = false;
	}else{
		positionObj.value = '';
		positionObj.disabled = true;
	}
}
function parseOccupationToPosition(occupation,id,positionField, positionSelect){
	form = document.appFormName;
	if(occupation != null && occupation != ""){
		var req = new DataRequestor();
		var url = "ParseOccupationToPositionServlet";
		req.addArg(_POST, "occupation", occupation);
		req.addArg(_POST, "positionField", positionField);
		req.addArg(_POST, "positionSelect", positionSelect);
		req.getURL(url);
		req.onload = function (data, obj) {
			var positionObj = document.getElementById(id);
			positionObj.innerHTML = data;
		}
	}
}
function checkradio(fieldObj){
  if(fieldObj.getAttribute("wasChecked")=="true"){
    fieldObj.checked = false;
    fieldObj.setAttribute("wasChecked","false");
  }else {
    fieldObj.setAttribute("wasChecked","true");
  }
}
function mandatoryXRulesField(form,btnName,resName,service) {
	try {	
		var req = new DataRequestor();
		var url = "MandatoryXRulesServlet";
		req.addArg(_POST, "serviceId", service);
		//req.addArg(_POST, "popupType", popupType);
		req.addArgsFromForm("appFormName");
		req.getURLForThai(url, _RETURN_AS_DOM);
		var frm = document.appFormName;
		var span = document.getElementById("errorMessage");
		var valid = false;
		req.onload = function (data, obj) {
				if (data!=null) {
					// Display error message					 
					var datas = data.documentElement;
					if(datas != null){
						var sessionError = datas.getElementsByTagName("error");
						if(sessionError[0] != null){
							//if(sessionError[0] != null){
								var errSession = sessionError[0].firstChild.nodeValue;
								alert(errSession);
								return false;
							//}
						}else{
							var error = datas.getElementsByTagName("error_message");
							var err = error[0].firstChild.nodeValue;
							//Set error field
							var lists = datas.getElementsByTagName("error_field");
							var list = lists[0];
							var childs = list.childNodes;
							for(i = 0; i < childs.length; i++){
								var field = childs[i].firstChild.nodeValue;
								var x=document.getElementById(field);
								if(x != null){
									if(x.type == 'text'){
										if(x.className == 'inputformnew' || x.className == 'inputformnewRed' || x.className == 'inputformnewCurrency') {
											x.className = "inputformnewRed";
										} else {
											x.className = "textboxRed";
										}
									}else{
										x.className = "ComboBoxRed";
									}
								}
							}
							
							//Set no error field
							var lists = datas.getElementsByTagName("no_error_field");
							var list = lists[0];
							var childs = list.childNodes;
							for(i = 0; i < childs.length; i++){
								var field = childs[i].firstChild.nodeValue;
								var x=document.getElementById(field);
								if(x.type == 'text'){
									if(x.className == "inputformnew" || x.className == 'inputformnewRed' || x.className == 'inputformnewCurrency') {
										x.className = "inputformnew";
									} else {
										x.className = "textbox";
									}
								}else{
									x.className = "combobox";
								}
							}
							span.innerHTML = err;
							span.style.display = "block";
							backToTop();
							valid = false;
							return valid;
						}
					}else{
						span.innerHTML = "";
						span.style.display = "none";
						if(service=='0'){
						 executeVerificationAll();
						}else{
						 executeXrulesService(form,btnName,resName,service);						
						}
					}
					
				} else {
					span.innerHTML = "";
					span.style.display = "none";	
					if(service=='0'){
				 	  executeVerificationAll();
					}else{									
		            executeXrulesService(form,btnName,resName,service);
		            }
				}
				//valid = true;
				//return valid;
			} //end req.onload = function (data, obj) {
			//return valid;
	} catch(er) {
		alert("Error mandatoryPopupField : "+er);
	}
}
function textCounter(field,maxlimit) {
	if (field.value.length > maxlimit) // if too long...trim it!
		field.value = field.value.substring(0, maxlimit);
		// otherwise, update 'characters left' counter
	//else
		//cntfield.value = maxlimit - field.value.length;
}
function mandatoryPrescoreField(form) {
	try {
  	    //removeCommaAllFields(form);
  	    form.pre_score_car_price.value=removeCommas(form.pre_score_car_price.value);
  	    form.pre_score_InstallmentAmtVAT.value=removeCommas(form.pre_score_InstallmentAmtVAT.value);
  	    if(form.pre_score_share_capital!=undefined){
  	     form.pre_score_share_capital.value=removeCommas( form.pre_score_share_capital.value);
  	    }
		var req = new DataRequestor();
		var url = "MandatoryPreScoreServlet";
		//req.addArg(_POST, "serviceId", service);
		//req.addArg(_POST, "popupType", popupType);
		req.addArgsFromForm("appFormName");
		req.getURLForThai(url, _RETURN_AS_DOM);
		var frm = document.appFormName;
		var span = document.getElementById("errorMessage");
		var valid = false;
		req.onload = function (data, obj) {
		       addCommas( "pre_score_car_price" );
		       addDecimalFormat(form.pre_score_car_price);
        	   addCommas( "pre_score_InstallmentAmtVAT");
        	   addDecimalFormat(form.pre_score_InstallmentAmtVAT);
        	   if(form.pre_score_share_capital!=undefined){
        	   addCommas( "pre_score_share_capital");
        	   }
				if (data!=null) {
					// Display error message					 
					var datas = data.documentElement;
					if(datas != null){
						var sessionError = datas.getElementsByTagName("error");
						if(sessionError[0] != null){
							var errSession = sessionError[0].firstChild.nodeValue;
							alert(errSession);
							return false;
						}else{
							var error = datas.getElementsByTagName("error_message");
							var err = error[0].firstChild.nodeValue;
							//Set error field
							var lists = datas.getElementsByTagName("error_field");
							var list = lists[0];
							var childs = list.childNodes;
							for(i = 0; i < childs.length; i++){
								var field = childs[i].firstChild.nodeValue;
								var x=document.getElementById(field);
								if(x != null){
									if(x.type == 'text'){
										x.className = "textboxRed";
									}else{
										x.className = "ComboBoxRed";
									}
								}
							}
							
							//Set no error field
							var lists = datas.getElementsByTagName("no_error_field");
							var list = lists[0];
							var childs = list.childNodes;
							for(i = 0; i < childs.length; i++){
								var field = childs[i].firstChild.nodeValue;
								var x=document.getElementById(field);
								if(x.type == 'text'){
									x.className = "textbox";
								}else{
									x.className = "combobox";
								}
							}
							span.innerHTML = err;
							span.style.display = "block";
							backToTop();
							valid = false;
							return valid;
						}
					}else{
						span.innerHTML = "";
						span.style.display = "none";
						//executeXrulesService(form,btnName,resName,service)						
						//Call PreScore
						calculatePreScoring()
					}
				} else {
					span.innerHTML = "";
					span.style.display = "none";										
					//Call PreScore
					calculatePreScoring()		           
				}				 
			}  			 
	} catch(er) {
		alert("Error mandatoryPopupField : "+er);
	}
}	

	var gPosx = 0;
	var gPosy = 0;
	
	function setPosXY(x,y){
		var e = window.event;
		
		if (e.pageX || e.pageY) 	{
				gPosx = e.pageX - x;
				gPosy = e.pageY - y;
			}
		else if (e.clientX || e.clientY) 	{
			gPosx = e.clientX + document.body.scrollLeft
				+ document.documentElement.scrollLeft - x;
			gPosy = e.clientY + document.body.scrollTop
				+ document.documentElement.scrollTop - y;
		}
	}
	function displayLoading(message){
		hideDropdownList();
		var topOffset = 1;
		if (window.pageYOffset != null) { /* moz and safari */
			pos = window.pageYOffset;
			ph = document.documentElement.scrollHeight;
			pw = document.documentElement.scrollWidth;
			if (document.body.scrollHeight > document.documentElement.scrollHeight) {
				ph = document.body.scrollHeight;
				pw = document.body.scrollWidth;
			}
		} else if (document.documentElement.scrollTop > document.body.scrollTop) { /* ie, catch if Standards compliance mode */
			pos = document.documentElement.scrollTop;
			ph = document.documentElement.scrollHeight;
			pw = document.documentElement.scrollWidth;
			if (document.documentElement.clientHeight > document.documentElement.scrollHeight) {
				ph = document.documentElement.clientHeight;
			}
		} else if (document.body != null) { /* if IE 5.5 */
			pos = document.body.scrollTop;
			ph = document.body.scrollHeight;
			pw = document.body.scrollWidth;
			if (document.documentElement.scrollHeight > document.body.scrollHeight) {
				ph = document.documentElement.scrollHeight;
			}
	
			ph = ph + pos + topOffset; /* fix box model */
		}
		
		var posx = window.screen.width;
		var posy = window.screen.height;
		if (!e) var e = window.event;
		try{
			if (e.pageX || e.pageY) 	{
				posx = e.pageX;
				posy = e.pageY;
			}
			else if (e.clientX || e.clientY) 	{
				posx = e.clientX + document.body.scrollLeft
					+ document.documentElement.scrollLeft;
				posy = e.clientY + document.body.scrollTop
					+ document.documentElement.scrollTop;
			}
			if(gPosx != 0){
				posx = gPosx;
				posy = gPosy;
			}
		}catch(er){
			posx = gPosx;
			posy = gPosy;
		}
		if(message==null || "" == message){
			message = "Loading...";
		}
		var LR = '<DIV id="blockDiv" align="center" style="position:absolute; visibility:visible; width:'+ pw +'; height:'+ ph +'; background: green;"><table cellSpacing=0 cellPadding=0 width="100%" height="100%" align="center" border="0" ><tr align="center" valign="middle"><td>&nbsp;</td></tr></table></DIV>';
		var Loading = '<div align="justify" style="position:absolute; visibility:visible; left: '+posx+'px; top: '+posy+'px; background-color: white; border:1px; border-color: black; border-style: solid; vertical-align: middle;" ><IMG src="./en_US/images/loading1.gif" align="center">&nbsp;&nbsp;'+message+'&nbsp;&nbsp;</div>';
		document.body.insertAdjacentHTML("afterBegin",LR);
		var div = document.getElementById("blockDiv");
		div.style.setAttribute("filter", "alpha(opacity=20);");
		document.body.insertAdjacentHTML("beforeEnd",Loading);
	}
	function hideDropdownList(){
	
		var theForm = document.getElementById("appFormName");
		//alert(theForm);
		if(theForm == null){
			theForm = document.getElementById("resultForm");
		}
		for (var i=0; i < theForm.elements.length; i++) {
	            var theNode = theForm.elements[i];
				if (theNode.type!==undefined) {
					switch(theNode.type.toLowerCase()) {
						case "select-one":
						//alert("type = "+theNode.type+" : name = "+theNode.name+" : value = "+theNode.value);
						theNode.style.visibility = "hidden";
						break;
					}
				}
	        }
	}
	function checkTotalWorkingAndAge(year){
		var form = document.appFormName;
		var age = form.age.value;
		var yearObj = eval("document.appFormName."+year);
		var workingYear = yearObj.value;
		if((age == '' && workingYear != '' && parseFloat(workingYear) != 0) || parseInt(workingYear) > parseInt(age)){
			alert("Total Working must not greater than Age("+age+")");
			yearObj.focus();
		}
	}
	function checkTimeInAddressAndAge(time){
		var form = document.appFormName;
		var age = form.age.value;
		var timeObj = eval("document.appFormName."+time);
		var time = timeObj.value;
		if((age == '' && time != '') || parseInt(time) > parseInt(age)){
			alert("Time in current Address must not greater than Age("+age+")");
			timeObj.focus();
		}
	}
	
function mandatoryScoreField(form) {
	try {
	    if(form.pre_score_share_capital!=undefined){
  	      form.pre_score_share_capital.value=removeCommas( form.pre_score_share_capital.value);
  	    }
		var req = new DataRequestor();
		var url = "MandatoryScoreServlet"; 
		req.addArgsFromForm("appFormName");
		req.getURLForThai(url, _RETURN_AS_DOM);
		var frm = document.appFormName;
		var span = document.getElementById("errorMessage");
		var valid = false;
		req.onload = function (data, obj) {
		if(form.pre_score_share_capital!=undefined){
        	   addCommas( "pre_score_share_capital");
        	   }
				if (data!=null) {
					// Display error message					 
					var datas = data.documentElement;
					if(datas != null){
						var sessionError = datas.getElementsByTagName("error");
						if(sessionError[0] != null){
							var errSession = sessionError[0].firstChild.nodeValue;
							alert(errSession);
							return false;
						}else{
							var error = datas.getElementsByTagName("error_message");
							var err = error[0].firstChild.nodeValue;
							//Set error field
							var lists = datas.getElementsByTagName("error_field");
							var list = lists[0];
							var childs = list.childNodes;
							for(i = 0; i < childs.length; i++){
								var field = childs[i].firstChild.nodeValue;
								var x=document.getElementById(field);
								if(x != null){
									if(x.type == 'text'){
										x.className = "textboxRed";
									}else{
										x.className = "ComboBoxRed";
									}
								}
							}
							
							//Set no error field
							var lists = datas.getElementsByTagName("no_error_field");
							var list = lists[0];
							var childs = list.childNodes;
							for(i = 0; i < childs.length; i++){
								var field = childs[i].firstChild.nodeValue;
								var x=document.getElementById(field);
								if(x.type == 'text'){
									x.className = "textbox";
								}else{
									x.className = "combobox";
								}
							}
							span.innerHTML = err;
							span.style.display = "block";
							backToTop();
							valid = false;
							return valid;
						}
					}else{
						span.innerHTML = "";
						span.style.display = "none"; 
						calculateScoring();
					}
				} else {
					span.innerHTML = "";
					span.style.display = "none";										
					//Call  Score
					calculateScoring();		             
				}				 
			}  
	} catch(er) {
		alert("Error mandatoryPopupField : "+er);
	}
}	
function getInsuranceData(){
	var form = document.appFormName;
	var accIns = form.acc_insurence.value;
	//alert(accIns);
	if(accIns == '1'){
		var req = new DataRequestor();
		var url = "GetInsuaranceDataServlet";
		req.addArg(_POST, "car_license_type", form.car_license_type.value);
		req.addArg(_POST, "acc_insurence", accIns);
		req.getURL(url);
		req.onload = function (data, obj) {
			if(data != ''){
				var datas = data.split(",");
				form.acc_gross_amount.value = datas[0];
				addDecimalFormat(form.acc_gross_amount);
				
				form.acc_promiun_amount.value = datas[1];
				addDecimalFormat(form.acc_promiun_amount);
				
				form.acc_confirm_amount.value = datas[0];
				addDecimalFormat(form.acc_confirm_amount);
			}
		}
	}else{
		form.acc_gross_amount.value = "0.00";
		form.acc_promiun_amount.value = "0.00";
		form.acc_confirm_amount.value = "0.00";
	}
}

function checkMinDecimalValue(fieldObj){
	var value = fieldObj.value;
	if(parseFloat(value) < 15000){
		alert("The minimum value must 15,000");
		fieldObj.selected;
		fieldObj.focus();
	}
}

function chcekMaxDecimalValue(fieldObj){
	var value = fieldObj.value;
	if(parseFloat(value) > 999999999.99 || parseFloat(value) < 0){
		alert("The maximun value must between 0 - 999,999,999.99");
		fieldObj.selected;
		fieldObj.focus();
	}
}
function deisabledReason(reasonField, decisionField, proposal){
	var reason = eval("appFormName."+reasonField);
	var decision = eval("appFormName."+decisionField);
	if(!isUndefined(decision.length)){
		if (decision.length > 0) {
			//for (var i = 0; i < decision.length ; i++) {
				if(proposal == 'Y'){
					if(!decision[0].checked && !decision[1].checked && !decision[2].checked){
						if(reason!=null && !isUndefined(reason.length)){
							for(var i=0; i<reason.length; i++){
								reason[i].disabled = true;
							}
						}
					}else{
						if(reason!=null && !isUndefined(reason.length)){
							for(var i=0; i<reason.length; i++){
								reason[i].disabled = false;
							}
						}
					}
				}else{
					if(!decision[0].checked && !decision[1].checked && !decision[2].checked && !decision[3].checked && !decision[4].checked && !decision[5].checked){	
						if(reason!=null && !isUndefined(reason.length)){
							for(var i=0; i<reason.length; i++){
								reason[i].disabled = true;
							}
						}
					}else{
						if(reason!=null && !isUndefined(reason.length)){
							for(var i=0; i<reason.length; i++){
								reason[i].disabled = false;
							}
						}
					}
				}
			//}
		}
	}else{
		if(!decision.checked){
			if(reason!=null && !isUndefined(reason.length)){
				for(var i=0; i<reason.length; i++){
					reason[i].disabled = true;
				}
			}
		}else{
			if(reason!=null && !isUndefined(reason.length)){
				for(var i=0; i<reason.length; i++){
					reason[i].disabled = false;
				}
			}
		}
	}
}

//////////////////////////
function validateAddressReside(birthDate) {
	setPosXY(65,40);
	//if(userRole == 'DE' || userRole == 'CMR' || userRole == 'UW'){
		try {
			var req = new DataRequestor();
			var url = "ValidateAddressResideServlet";
			//req.addArg(_POST, "saveType", saveType);
			req.addArgsFromForm("appFormName");
			req.getURLForThai(url, _RETURN_AS_DOM);
			//req.getURLForThai(url, _RETURN_AS_DOM);
			var frm = document.appFormName;
			var span = document.getElementById("errorMessage");
			var valid = false;
			req.onload = function (data, obj) {
					if (data!=null) {
						// Display error message
						var datas = data.documentElement;						 
						if(datas != null){						 
						var sessionError = datas.getElementsByTagName("error");
						if(sessionError[0] != null){
							var errSession = sessionError[0].firstChild.nodeValue;
							alert(errSession);
							return false;
						}else{                           
							var error = datas.getElementsByTagName("error_message");
							var err = error[0].firstChild.nodeValue;
							//Set error field
							var lists = datas.getElementsByTagName("error_field");
							var list = lists[0];
							var childs = list.childNodes;
							for(i = 0; i < childs.length; i++){
								var field = childs[i].firstChild.nodeValue;
								var x=document.getElementById(field);
								if(x != null){
									if(x.type == 'text'){
										x.className = "textboxRed";
									}else{
										x.className = "ComboBoxRed";
									}
								}
							}
							
						  var bdField=eval("document.appFormName."+birthDate);
						  if(bdField){
						   bdField.value='';
						  }
						}							 
							//Set no error field
							var lists = datas.getElementsByTagName("no_error_field");
							var list = lists[0];
							var childs = list.childNodes;
							for(i = 0; i < childs.length; i++){
								var field = childs[i].firstChild.nodeValue;
								var x=document.getElementById(field);
								if(x.type == 'text'){
									x.className = "textbox";
								}else{
									x.className = "combobox";
								}
							}
							span.innerHTML = err;
							span.style.display = "block";
							backToTop();
						}else{//datas =null
							span.innerHTML = "";
							span.style.display = "none";
							 
						}
					} else {//data =null
						span.innerHTML = "";
						span.style.display = "none";
						 
					}
	
				} //end req.onload = function (data, obj) {
			} catch(er) {
				alert("Error MandatoryField : "+er);
			}
		//}else{
		//		saveApp(userRole);
		//}
}
function clearSchemeByCampaign(){
	var form = document.appFormName;
	var req = new DataRequestor();
	var url = "CheckMatchCampaignSchemeServlet";
	if (form.scheme_code!=undefined && form.campaign!=undefined){
		req.addArg(_POST, "campaign", form.campaign.value);
		req.addArg(_POST, "scheme_code", form.scheme_code.value);
	
		req.getURLForThai(url, _RETURN_AS_DOM);
		//req.getURL(url, _RETURN_AS_DOM);
		req.onload = function (data, obj) {
			if (data!=null) {
				var list = data.documentElement;
				var fields = list.childNodes;
				//alert(fields.length);
				for(i=0; i<fields.length; i++){
					var field = fields[i];
					//alert("field ->" + field.nodeName);
					var values = "";
					if(field.firstChild != null){
						values = field.firstChild.nodeValue;
					}
					//alert("value -> "+ values);
					var fieldName = field.getAttribute('name');
					//alert("fieldName ->" + fieldName);
					if (fieldName == "result" && values == "0"){
						if (form.scheme_code != undefined){
							form.scheme_code.value = "";
						}
						if (form.scheme_code_desc != undefined){
							form.scheme_code_desc.value = "";
						}
					}
				}
			}
		}
	}
}