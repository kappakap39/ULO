function IA_PERSONAL_APPLICANT_FORMAfterSaveActionJS(){
	try{
		var handleForm = 'N';
		
		try{
			Pace.block();
			var currentFormName = $("#FormHandlerManager [name='formName']").val();
			var currentFormId = $("#FormHandlerManager [name='formId']").val();
			var $data  = '&action=BackLastTab&ajaxAction=Y&handleForm='+handleForm;
				$data += '';
				$data += '&currentFormName'+currentFormName;
				$data += '&currentFormId'+currentFormId;
			var url = CONTEXT_PATH+'/FrontController';
			$.post(url,$data,function(data,status,xhr){
				try{			
					try{
						Pace.block();
						var url = CONTEXT_PATH+'/orig/ulo/template/FormTemplate.jsp';
						$.post(url,function(data,status,xhr){
							Pace.unblockFlag = true;
							Pace.unblock();
							try{
								$('#FormHandlerManager').html(data);
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
						})
						.done(function() {
							var isKPL = $("[name='isKPL']").val();
							//var DEL_BTN = $("#DELETE_PRODUCT_KPL");
							var KPL_PRODUCT = $('tr[class*="kpl product"]');
							//if(isKPL == 'true' && (DEL_BTN.length == 0))
							if(isKPL == 'true' && (KPL_PRODUCT.length == 0))
							{createRowKPLProductInfoActionJS();}
						})
						.fail(function(data,status,xhr){
							errorAjax(data,status,xhr);
						});
					}catch(exception){
						errorException(exception);
					}
				}catch(exception)
				{
					errorException(exception);
				}
			})
			.fail(function(data,status,xhr){
				errorAjax(data,status,xhr);
			});
		}catch(exception){
			errorException(exception);
		}
		
	}catch(exception){
		errorException(exception);
	}
}

function PASSPORT_EXPIRE_DATEActionJS(element, mode, name){
	var PASSPORT_EXPIRE_DATE = $("[name='"+element+"']").val();
	if(PASSPORT_EXPIRE_DATE){
		if(validateCurrentDateToDate(PASSPORT_EXPIRE_DATE,LOCAL_EN)){
			alertBox("ERROR_PASSPORT_EXPIRT_DATE",focusElementActionJS,element);
		}
	}
}