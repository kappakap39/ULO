/**
 * Rawi Songchaisin
 */
var $warnIcon ="<img class=\"warnimg\" border=\"0\"src=\"images/warning.0.gif\" width=\"17\" height=\"17\" align=\"middle\">";

function DestoryButtonNormalEngine(entity){
	$(entity).unbind();
}
function DestoryButtonRedEngine(entity){
	$(entity).unbind();
}
function ButtonNormalEngine(entity){
	$(entity).hover(
		function(){$(this).addClass("button-hover");},
	 	function(){$(this).removeClass("button-hover");}
	);
}
function ButtonRedEngine(entity){
	$(entity).hover(
		function(){$(this).addClass("button-red-hover");},
		function(){$(this).removeClass("button-red-hover");}
	);	
}
function DisplayErrorField(data){
	if(data == null || data.length == 0){
		DestoryNotification();
		return;
	}	
	$.map(data, function(item){
		var field = document.getElementById(item.id);
		if(field != null){
			if (field.type!== undefined){			
				switch(field.type.toLowerCase()){
					case "input": break;
					case "hidden":break;
					case "select": $(field).removeClass("combobox").addClass("ComboBoxRed"); break;
					case "select-one": $(field).removeClass("combobox").addClass("ComboBoxRed"); break;
					case "textarea": break;
					case "text": $(field).addClass("textboxRed"); break;
					case "checkbox": break;
					case "radio": break;
				}
			}else{
				$("[id$="+item.id+"]").each(function () {			
				    switch(this.tagName.toLowerCase()) {
						case "table": break;
						case "div": $('#'+item.id).addClass("div-mandatory");
						case "td": break;
						case "tr": break;
						case "span": break;
				    }
				});
			}			
		}
		if(item.value != undefined && item.value != null && item.value != ''){
			PushNotifications(item.value);
		}
	});	
}
function DestoryErrorField(){
	RemoveErrorField();
	DestoryNotification();
}
function RemoveErrorField(){
	 $('.warnimg').remove();
//	 $(".textboxRed").removeClass("textboxRed").addClass("textbox");
	 $(".textboxRed").removeClass("textboxRed");
	 $('#div-decision-reason').removeClass("div-mandatory");
	 $(".ComboBoxRed").removeClass("ComboBoxRed").addClass("combobox");
}
function displayTextBoxReadOnly(fieldID){
	$(document.getElementById(fieldID)).attr('readonly', true);
	$(document.getElementById(fieldID)).addClass("textboxReadOnly");	
}
function displayTextBox(fieldID){
	$(document.getElementById(fieldID)).attr('readonly', false);
	$(document.getElementById(fieldID)).addClass("textbox");	
}
function displayJSON(data){
	jsonDisplayElementById(data);
}
function jsonDisplayElementById(data){
	if(data == null || data.length < 0) return;
	$.map(data, function(item){
		var elementID = document.getElementById(item.id);
		if(elementID != null){
			if (elementID.type!== undefined){
				switch(elementID.type.toLowerCase()) {
					case "input": break;
					case "hidden": $(elementID).val(item.value); break;
					case "select": $(elementID).val(item.value); break;
					case "select-one": $(elementID).val(item.value); break;
					case "textarea": break;
					case "text": $(elementID).val(item.value); break;
					case "checkbox": break;
					case "radio": break;
				}
			}else{
				$("[id$="+item.id+"]").each(function () {
				    switch(this.tagName.toLowerCase()) {
						case "table": $(elementID).append(item.value); break;
						case "div": $(elementID).html(item.value); break;
						case "td": $(elementID).html(item.value); break;
						case "tr": $(elementID).html(item.value); break;
						case "span": $(elementID).html(item.value); break;
				    }
				});
			}
		}
	});
}
/**
 * Wichaya Ch.
 * 
 * Display Element and notification error
 */
function jsonDisplayElementByIdWithError(data){
	var error = false;
	if(data == null || data.length < 0) return;
	$.map(data, function(item){
		if(item.id == 'ERROR'){
			if(item.value != null && item.value != ''){
				PushNotifications(item.value);
				OpenNotification();
			}
			
			error = true;
		}
		
	});
	
	if(!error){
		jsonDisplayElementById(data);
	}
	
}

/**
 * Praisan K.
 * Display Element and notification error
 */
function jsonDisplayElementByIdValueWithError(data){
	var error = false;
	if(data == null || data.length < 0) return;
	$.map(data, function(item){
		if(item.id == 'ERROR'){
			if(item.value != null && item.value != ''){
				PushNotifications(item.value);
				OpenNotification();
			}
			error = true;
		}
	});
	if(!error){
		jsonDisplayElementById(data);
	}
}

function displayJsonObjectElementById(data){
	if(data == null || data.length < 0) return;
	$.map(data, function(item){
		var elementID = document.getElementById(item.id);
		if(elementID != null){
			if (elementID.type !== undefined){			
				switch(elementID.type.toLowerCase()) {
					case "input": break;
					case "hidden": $(elementID).val(item.value); break;
					case "select": $(elementID).val(item.value); break;
					case "select-one": $(elementID).val(item.value); break;
					case "textarea": break;
					case "text": $(elementID).val(item.value); break;
					case "checkbox": break;
					case "radio": break;
				}
			}else{
				$("[id$="+item.id+"]").each(function () {			
				    switch(this.tagName.toLowerCase()) {
						case "table": $(elementID).append(item.value); break;
						case "div": $(elementID).html(item.value); break;
						case "td": $(elementID).html(item.value); break;
						case "tr": $(elementID).html(item.value); break;
						case "span": $(elementID).html(item.value); break;
				    }
				});
			}
		}
	});
}
function htmlDisplayElementById(data ,displayId){
	var elementID = document.getElementById(displayId);
	if(elementID != null){
		if (elementID.type !== undefined){			
			switch(elementID.type.toLowerCase()) {
				case "input": break;
				case "hidden": $(elementID).val(data); break;
				case "select": $(elementID).val(data); break;
				case "select-one": $(elementID).val(data); break;
				case "textarea": break;
				case "text": $(elementID).val(data); break;
				case "checkbox": break;
				case "radio": break;
			}
		}else{
			$("[id$="+displayId+"]").each(function(){			
			    switch(this.tagName.toLowerCase()) {
					case "table": $(elementID).append(data); break;
					case "div": $(elementID).html(data); break;
					case "td": $(elementID).html(data); break;
					case "tr": $(elementID).html(data); break;
					case "span": $(elementID).html(data); break;
			    }
			});
		}
	}
}
/**@deprecated
 * @author septemwi
 * Change To ulo.ajax
 * */
function ajaxDisplayHtmlById(servletName,dataStr){
	jQuery.ajax( {
		type :"POST",
		url :servletName,
		data :dataStr,
		async :false,	
		dataType: "json",
		success : function(data ,status ,xhr){			
			$.map(data, function(item){
				var elementID = document.getElementById(item.id);
				if(elementID != null){			
					$(elementID).html(item.value);
				}
			});
		},
		error : function(data ,status ,xhr){
		}
	});
}
/**@deprecated
 * @author septemwi
 * Change To ulo.ajax
 * */
function displayHtmlAppend(data ,removeId){
	if(data.length > 0) $(document.getElementById(removeId)).remove();
	jsonDisplayElementById(data);
}

function clearSellInfo(){
	var variable = document.getElementById("sell_information");
//	alert("variable= "+variable);
	if(variable!=null){
		$('#sell_information * :input:not(.buttonNew)').val("");
		$('#certification').attr('checked', false);
	}
}