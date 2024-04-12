/**
 * #SeptemWi
 */
/**This Function Used For SensitiveField For Validate Rule*/
function DestorySensitiveEngine(entity){
	$(entity).unbind();
}
function DocListSensitiveRadioButton(entity){
	$('input[name='+entity+']:radio').change(function (){
		var attrName = $(this).attr('name');
		var dataString = '&attrName=doccode&attrDocName='+attrName+'&'+$('#avale-obj-form *').serialize();
		if($(".plan-dialog") != undefined){
			dataString = dataString+'&'+$('.plan-dialog *').serialize();
		}
		$.ajax({
			type :"POST",
			url :"AjaxDisplayServlet",
			data :'className=SensitiveFieldEngine&packAge='+packageOrigRules+'&returnType=0'+dataString,
			async :true,
			dataType: "json",
			success : function(resp,status ,xhr){
				DisplaySensitiveFieldEngine(resp);
			},
			error : function(resp){
			}
		});
	});
}
function SensitiveRadioButton(entity){
	$('input[name='+entity+']:radio').change(function (){
		var attrName = $(this).attr('name');
		var dataString = '&attrName='+attrName+'&'+$('#avale-obj-form *').serialize();
		if($(".plan-dialog") != undefined){
			dataString = dataString+'&'+$('.plan-dialog *').serialize();
		}
		$.ajax({
			type :"POST",
			url :"AjaxDisplayServlet",
			data :'className=SensitiveFieldEngine&packAge='+packageOrigRules+'&returnType=0'+dataString,
			async :true,
			dataType: "json",
			success : function(resp,status ,xhr){
				DisplaySensitiveFieldEngine(resp);
			},
			error : function(resp){
			}
		});
	});
}
function SensitiveCheckBox(entity){
	$('input[name='+entity+']:checkbox').click(function (e){
		var attrName = $(this).attr('name');
		var dataString = '&attrName='+attrName+'&'+$('#avale-obj-form *').serialize();
		if($(".plan-dialog") != undefined){
			dataString = dataString+'&'+$('.plan-dialog *').serialize();
		}
		$.ajax({
			type :"POST",
			url :"AjaxDisplayServlet",
			data :'className=SensitiveFieldEngine&packAge='+packageOrigRules+'&returnType=0'+dataString,
			async :true,
			dataType: "json",
			success : function(resp,status ,xhr){
				DisplaySensitiveFieldEngine(resp);
			},
			error : function(resp){
			}
		});
	});
}
function SensitiveEngine(entity){	
	$(entity).blur(function(e){
		if($(this).is('[readonly]')){
			return;
		}
		var type = $(this).attr('type');
		if(type == undefined || type == 'radio'|| type == 'checkbox') return;
		var attrName = $(this).attr('name');
		var dataString = '&attrName='+attrName+'&'+$('#avale-obj-form *').serialize();
		if($(".plan-dialog") != undefined){
			dataString = dataString+'&'+$('.plan-dialog *').serialize();
		}
		$.ajax({
			type :"POST",
			url :"AjaxDisplayServlet",
			data :'className=SensitiveFieldEngine&packAge='+packageOrigRules+'&returnType=0'+dataString,
			async :true,
			dataType: "json",
			success : function(resp,status ,xhr){
				DisplaySensitiveFieldEngine(resp);
			},
			error : function(resp){
			}
		});
	});
	
	$(entity).change(function(e){
		try{
			var type = ($(this).get(0).tagName).toLowerCase();
			if(type == undefined || (type != 'select' && type != 'select-one')) return;
			var attrName = $(this).attr('name');
			var dataString = '&attrName='+attrName+'&'+$('#avale-obj-form *').serialize();
			if($(".plan-dialog") != undefined){
				dataString = dataString+'&'+$('.plan-dialog *').serialize();
			}
			$.ajax({
				type :"POST",
				url :"AjaxDisplayServlet",
				data :'className=SensitiveFieldEngine&packAge='+packageOrigRules+'&returnType=0'+dataString,
				async :true,
				dataType: "json",
				success : function(resp,status ,xhr){
					DisplaySensitiveFieldEngine(resp);
				},
				error : function(resp){
				}
			});	
		}catch (e){
		}
	});
	
}
function SensitiveFieldEngine(obj){
	var attrName = $(obj).attr('name');
	var dataString = '&attrName='+attrName+'&'+$('#avale-obj-form *').serialize();
	if($(".plan-dialog") != undefined){
		dataString = dataString+'&'+$('.plan-dialog *').serialize();
	}
	$.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :'className=SensitiveFieldEngine&packAge='+packageOrigRules+'&returnType=0'+dataString,
		async :false,
		dataType: "json",
		success : function(resp,status ,xhr){
			DisplaySensitiveFieldEngine(resp);
		},
		error : function(resp){
		}
	});
}
/**#septemwi add function for sensitive by attr name*/
function SensitiveAttrNameEngine(attrName){
	var dataString = '&attrName='+attrName+'&'+$('#avale-obj-form *').serialize();
	if($(".plan-dialog") != undefined){
		dataString = dataString+'&'+$('.plan-dialog *').serialize();
	}
	$.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :'className=SensitiveFieldEngine&packAge='+packageOrigRules+'&returnType=0'+dataString,
		async :false,
		dataType: "json",
		success : function(resp,status ,xhr){
			DisplaySensitiveFieldEngine(resp);
		},
		error : function(resp){
		}
	});
}
function DocListSensitiveAttrNameEngine(attrName){
	var dataString = '&attrName=doccode&attrDocName='+attrName+'&'+$('#avale-obj-form *').serialize();
	if($(".plan-dialog") != undefined){
		dataString = dataString+'&'+$('.plan-dialog *').serialize();
	}
	$.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :'className=SensitiveFieldEngine&packAge='+packageOrigRules+'&returnType=0'+dataString,
		async :false,
		dataType: "json",
		success : function(resp,status ,xhr){
			DisplaySensitiveFieldEngine(resp);
		},
		error : function(resp){
		}
	});
}
function DisplaySensitiveFieldEngine(resp){
	if(resp == null || resp.length == 0){
		return false;
	}
	$.map(resp, function(item){
		switch (item.TYPE) {
			case SERVICE:
				$("#code_"+item.SERVICE_ID).val(item.RESULT_CODE);
				if($("#result_"+item.SERVICE_ID).attr('type') != undefined){
					switch ($("#result_"+item.SERVICE_ID).attr('type')) {
						case 'text': $("#result_"+item.SERVICE_ID).val(item.RESULT_DESC); break;
						case 'textbox': $("#result_"+item.SERVICE_ID).val(item.RESULT_DESC); break;
						default:break;
					}	
				}else{
					$("#result_"+item.SERVICE_ID).html(item.RESULT_DESC);
				}
				if(item.SENSITIVE_TYPE == CLEAR_RULE){	
					if($('#message-sensitive').html() == null){
						PerpendNotifyID(SENSITIVE_ERROR ,'message-sensitive');
					}
				}
				try{
					if(item.STYLE != ''){
						$('#result_'+item.SERVICE_ID).removeClass();
						$('#result_'+item.SERVICE_ID).addClass(item.STYLE);
					}
				}catch (e){
				}
				break;
			case BUTTON:
				$('#'+item.id).removeClass("button");
				$('#'+item.id).removeClass("button-red");								
				$('#'+item.id).addClass(item.value);
				break;
			case MANUAL:
				if(item.SENSITIVE_TYPE == CLEAR_RULE){	
					if($('#message-sensitive').html() == null){
						PerpendNotifyID(SENSITIVE_ERROR ,'message-sensitive');
					}
				}
				break;
			default:
				break;
		}
	});
	
}