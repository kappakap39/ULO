/**
 * Rawi Songchaisin 
 */
$(document).ready(function (){
	DisplayMatrix();
});
function DisplayMatrix(){
	ajaxHtmlManualAsync('DisplayMatrix',packageOrigRules,'',MatrixEngine);
}
function DisplayMatrixHTML(){
	ajaxHtmlManual('DisplayMatrix',packageOrigRules,'',MatrixEngine);
}
var $intervalSearchNcb;
function MatrixEngine(response){
	$('#verification').html(response);	
	$('#popup_1003').click(function(e){	
		e.preventDefault();
		$('#action').val('FormHandlerAction');
		$('#handleForm').val('Y');
		$('#currentTab').val('MAIN_TAB');
		var dataString = $('#avale-obj-form *').serialize();
		$.post('FrontController',dataString
			,function(data,status,xhr){		
				openPopupDialogCloseButton('DUPLICATE_APPLICATION_POPUP','1000','450',DUPLICATE_APPLICATION);
		});
	});
	$('#popup_1004').click(function(e){	
		e.preventDefault();
		openPopupDialogCloseButton('EXISTING_KEC_POPUP','900','500',EXISTING_KEC);
	});
	$('#popup_1015').click(function(e){	
		e.preventDefault();
		openPopupDialogCloseButton('WATCHLIST_FRAUD_POPUP','800','420',WATCH_LIST_FRAUD);
	});
	$('#popup_1016').click(function(e){	
		e.preventDefault();
		openPopupDialogCloseButton('BSCORE_POPUP','1000','500',BSCORE);
	});
	$('#popup_1017').click(function(e){	
		e.preventDefault();
		openPopupDialogCloseButton('PAYROLL_POPUP','1200','500',PAY_ROLL);
	});
	$('#popup_1019').click(function(e){	
		e.preventDefault();
		openPopupDialogCloseButton('LIST_COMPANY_POPUP','1000','500',LIST_COMPANY);
	});
	$('#popup_1020').click(function(e){	
		e.preventDefault();
		OpenPopupDialogFraudCompary('FRAUD_COMPANY_POPUP','1000','500',FRAUD_COMPANY);
	});
	$('#popup_1007').click(function(e){	
		e.preventDefault();
		openPopupDialogCloseButton('NPL_POPUP','1200','500',NPL_LPM);
	});
	$('#edit_7001').click(function(e){	
		e.preventDefault();
		OpenPopupEditVerification('EDIT_VERIFICATION_POPUP','1000','500','Edit');
	});
	$('#button_1024').click(function(e){	
		e.preventDefault();
		$('#action').val('FormHandlerAction');
		$('#handleForm').val('Y');
		$('#currentTab').val('MAIN_TAB');
		var dataString = $('#avale-obj-form *').serialize();
		$.post('FrontController',dataString
			,function(data,status,xhr){		
				if(MandatoryOpenDocument()){
					OpenPopupDialogDocument('DOC_LIST_POPUP','1200','500',DOCUMENT_CHECK_LIST);
				}
		});		
	});	
	$('#popup_1023').click(function(e){	
		e.preventDefault();
		openPopupDialogCloseButton('NCB_RESULT_POPUP','1200',$('.frame-module').height()-71,NCB_RESULT);
	});	
	$('#button_1023').click(function(e){	
		e.preventDefault();
		OpenPopupDialogNcb('NCB_POPUP','1200',$('.frame-module').height()-71,REQUEST_NCB);
	});
	$('#button_1032').click(function(e){	
		e.preventDefault();
		try{
			$('#action').val('FormHandlerAction');
			$('#handleForm').val('Y');
			$('#currentTab').val('MAIN_TAB');
			var dataString = $('#avale-obj-form *').serialize();
			blockScreen();
			$.post('FrontController',dataString,function(data,status,xhr){					
					var result_1032 = $('#result_1032').val();
					if(result_1032 == ''){
						dataString = null;
						dataString = "className=VerifyCustomerWaivedResult&packAge=3&returnType=0";
						$.ajax({
							type :"POST",
							url :"AjaxDisplayServlet",
							data :dataString,
							async :true,	
							dataType: "json",
							success : function(data ,status ,xhr){
								unblockScreen();
								$.map(data, function(item){
									if(item.id == 'STYLE'){
										$('#result_1032').removeClass();
										$('#result_1032').addClass(item.value);
									}else{		
										if($('#'+item.id).attr('type') != undefined){
											switch ($('#'+item.id).attr('type')) {
												case 'text': $('#'+item.id).val(item.value); break;
												case 'textbox': $('#'+item.id).val(item.value); break;
												case "hidden": $('#'+item.id).val(item.value); break;
												default:break;
											}	
										}else{												
											$('#'+item.id).html(item.value);
										}
									}
									
								});
								var code_1032 = $('#code_1032').val();
								if(code_1032 != WEIVED){
									OpenPopupDialogVerifyCustomer('VERIFY_CUSTOMER_POPUP','1200','500',VERIFY_CUSTOMER);
								}							
							},
							error : function(data){
								unblockScreen();
								PushErrorNotifications('Network or Connection Error, please try again');
							}
						});	
					}else{
						unblockScreen();
						OpenPopupDialogVerifyCustomer('VERIFY_CUSTOMER_POPUP','1200','500',VERIFY_CUSTOMER);
					}
			   }).error(function(){
				   unblockScreen();
				   PushErrorNotifications('Network or Connection Error, please try again');
			   });
		}catch(e){
			unblockScreen();
			var msg = ERROR_JS+e.message;
			PushErrorNotifications(msg);
		}
	});	
	$('#button_1033').click(function(e){	
		e.preventDefault();	
		try{
			$('#action').val('FormHandlerAction');
			$('#handleForm').val('Y');
			$('#currentTab').val('MAIN_TAB');
			var dataString = $('#avale-obj-form *').serialize();
			blockScreen();
			$.post('FrontController',dataString,function(data,status,xhr){	
				var result_1033 = $('#result_1033').val();
				if(result_1033 == ''){
					dataString = null;
					dataString = "className=VerifyHRWaivedResult&packAge=3&returnType=0";
					$.ajax({
						type :"POST",
						url :"AjaxDisplayServlet",
						data :dataString,
						async :true,	
						dataType: "json",
						success : function(data ,status ,xhr){
							unblockScreen();
							$.map(data, function(item){
								if(item.id == 'STYLE'){
									$('#result_1033').removeClass();
									$('#result_1033').addClass(item.value);
								}else{		
									if($('#'+item.id).attr('type') != undefined){
										switch ($('#'+item.id).attr('type')) {
											case 'text': $('#'+item.id).val(item.value); break;
											case 'textbox': $('#'+item.id).val(item.value); break;
											case "hidden": $('#'+item.id).val(item.value); break;
											default:break;
										}	
									}else{												
										$('#'+item.id).html(item.value);
									}
								}
							});
							var code_1033 = $('#code_1033').val();
							if(code_1033 != WEIVED){
								OpenPopupDialogVerifyHR('VERIFY_HR_POPUP','1000','500',VERIFY_HR);
							}	
						},
						error : function(response){
							unblockScreen();
							PushErrorNotifications('Network or Connection Error, please try again');
						}
					});
				}else{
					unblockScreen();
					OpenPopupDialogVerifyHR('VERIFY_HR_POPUP','1000','500',VERIFY_HR);
				}
		   }).error(function(){
			   unblockScreen();
			   PushErrorNotifications('Network or Connection Error, please try again');
		   });
		}catch(e){
			unblockScreen();
			var msg = ERROR_JS+e.message;
			PushErrorNotifications(msg);
		}
	});	
	$('#follow_detail').click(function(e){	
		e.preventDefault();
		$('#action').val('FormHandlerAction');
		$('#handleForm').val('Y');
		$('#currentTab').val('MAIN_TAB');
		var dataString = $('#avale-obj-form *').serialize();
		$.post('FrontController',dataString
			,function(data,status,xhr){		
				OpenPopupDialogFollowDetail('FOLLOWDETAIL_POPUP','1200','500',FOLLOW_DETAIL);
		});		
	});	
	$('#button_7001').click(function(e){
		e.preventDefault();
		ExecuteButtonService('button_7001');
	});
	$('#button_7002').click(function(e){
		e.preventDefault();
		ExecuteButtonService('button_7002');
	});
	$('#popup_1040').click(function(e){
		e.preventDefault();
		openPopupDialogCloseButton('NCB_RESULT_POPUP','1200','500',NCB_RESULT);
	});
	$('#button_1040').click(function(e){
		e.preventDefault();
		RetrieveOldNcbData();
	});
	
	
	
	var verifyDisplayMode = $('#display-mode-verify').val();
	if(verifyDisplayMode == DISPLAY_MODE_EDIT){
		TextBoxCurrencyEngine('#salary');
		TextBoxCurrencyEngine('#loan-income');
		TextBoxCurrencyEngine('#other-income');
		TextBoxCurrencyEngine('#other-debt');
		TextBoxNumberEngine('#cheuqe-return');
		SensitiveEngine('#salary');
		SensitiveEngine('#loan-income');
		SensitiveEngine('#other-income');
		SensitiveEngine('#other-debt');
		SensitiveEngine('#cheuqe-return');
		SensitiveEngine('#other-income-remark');
		SensitiveEngine('#other-debt-remark');
		SensitiveEngine('#result_1031');
		SensitiveEngine('#result_1029');
		SensitiveEngine('#result_1030');
		$('#salary').blur(function(){
			CalculateIncome();
		});
		$('#loan-income').blur(function(){
			CalculateIncome();
		});
		$('#other-income').blur(function(){
			CalculateIncome();
		});
		$('#other-debt').blur(function(){
			CalculateDebt();
		});
		if($('#total-income').attr('id') != undefined){
			CalculateIncome();
		}
		if($('#total-debt').attr('id') != undefined){
			CalculateDebt();
		}
		
		$('#reset_execute1').click(function(e){
			e.preventDefault();
			ExecuteReset();
		});
		EnableLoanIncome();
		if($('#code_1040').val() != undefined && $('#code_1040').val()== WAITING_NCB_DATA){
			StopSearchNcbData();
			$intervalSearchNcb = setInterval(function (){SearchNcbData();},Number(RETRIEVE_NCB_DATA_TIMEOUT));
		}
	}
}
function ExecuteReset(){
	var dataString = "className=ExecuteReset&packAge=3&returnType=0";		
	jQuery.ajax( {
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,	
		dataType: "json",
		success : function(data ,status ,xhr){
			$.map(data, function(item){
				switch(item.type){
					case 'service':
						$("#code_"+item.id).val(item.code);
						if($("#result_"+item.id).attr('type') != undefined){
							switch ($("#result_"+item.id).attr('type')) {
								case 'text': $("#result_"+item.id).val(item.value); break;
								case 'textbox': $("#result_"+item.id).val(item.value); break;
								default:break;
							}	
						}else{												
							$("#result_"+item.id).html(item.value);
						}
						if(item.field01 != undefined && $(item.field01).attr('type') != undefined){
							$(item.field01).val('');
						}
						try{
							if(item.style != ''){
								$('#result_'+item.id).removeClass();
								$('#result_'+item.id).addClass(item.style);
							}
						}catch (e){
						}
						break;
					case 'button':
						$('#'+item.id).removeClass("button");
						$('#'+item.id).removeClass("button-red");								
						$('#'+item.id).addClass(item.style);
						break;
					case 'edit':
						$('#'+item.id).removeClass("button");
						$('#'+item.id).removeClass("button-red");								
						$('#'+item.id).addClass(item.style);
						break;	
					default:break;
				}
			});
		},
		error : function(response){
			 PushErrorNotifications('Network or Connection Error, please try again');
		}
	});
}
function SearchNcbData(){
	var $userrole = $('#role').val();
	var ncbDecision = '';
	if($('#code_1040').val() != undefined && $('#code_1040').val()== WAITING_NCB_DATA){
		var dataString = "className=SearchNcbData&packAge="+packageOrigRules+"&returnType=0";		
		jQuery.ajax( {
			type :"POST",
			url :"AjaxDisplayServlet",
			data :dataString,
			async :true,	
			dataType: "json",
			success : function(data ,status ,xhr){
				if(data != null){
					$.map(data, function(item){
						if(item.id == 'ncb-color'){
							$('#result_1040').removeClass();
							$('#result_1040').addClass(item.value);
						}else if(item.id == 'ncb-decision'){
							ncbDecision = item.value;
						}else{
							$('#'+item.id).val(item.value);
						}
					});			
		
					if($('#code_1040').val() != undefined && $('#code_1040').val() != ''
							&& $('#code_1040').val() != NA && $('#code_1040').val() != WAITING_NCB_DATA){
						
						CalculateDebt();
						SensitiveAttrNameEngine('total-debt');
						
						if( FAIL == ncbDecision && $userrole != ROLE_CA && $userrole != ROLE_CA_SUP){
							alertMassageFunc(REJECT_MSG,RejectApplication);							
						}	
						StopSearchNcbData();
					}					
				}
			},
			error : function(response){
				 PushErrorNotifications('Network or Connection Error, please try again');
			}
		});
		
	}
}
function StopSearchNcbData(){	
	clearInterval($intervalSearchNcb);	
}
function RetrieveOldNcbData(){
	if($('#code_1040').val() == WAITING_NCB_DATA){
		StopSearchNcbData();
		alertMassageYesNoFunc(MSG_RE_RETRIEVE_NCB_DATA,MandatoryRetrieve,doIntervalNcbData);	
	}else{
		MandatoryRetrieve();
	}
}
function doIntervalNcbData(){
	if($('#code_1040').val() == WAITING_NCB_DATA){
		StopSearchNcbData();
		$intervalSearchNcb = setInterval(function (){SearchNcbData();},Number(RETRIEVE_NCB_DATA_TIMEOUT));
	}
}
function MandatoryRetrieve(){
	var isError = true;
	DestoryErrorField();
	var dataString = "className=RetrieveOldNcbManadatory&packAge="+packagePlMandatory+"&returnType=0&"
										+$("#avale-obj-form *").serialize();	
	$.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,
		dataType: "json",
		success : function(response ,status ,xhr){			
			if(response != null && response.length > 0){
				isError = true; 
				DisplayErrorField(response);
				OpenNotification();
			}else{
				isError = false;
			}
		},
		error:function(){
			PushErrorNotifications('Network or Connection Error, please try again');
			isError = true;
		}
	});
	if(!isError){
		CallRetrieveOldNcb();
	}
}
function CallRetrieveOldNcb(){
	try{
		$('#action').val('RetrieveOldNcbData');
		$('#handleForm').val('Y');
		$('#currentTab').val('MAIN_TAB');
		var dataString = $('#avale-obj-form *').serialize();
		blockScreen();
		$.post('FrontController',dataString
			,function(data,status,xhr){	
				unblockScreen();
				if(data != null){
					$.map(data, function(item){
						if(item.id == 'ncb-color'){
							$('#result_1040').removeClass();
							$('#result_1040').addClass(item.value);
						}else{
							$('#'+item.id).val(item.value);
						}
					});
					
					CalculateDebt();
					SensitiveAttrNameEngine('total-debt');				
					
					if($('#code_1040').val() != undefined && $('#code_1040').val()== WAITING_NCB_DATA){
						StopSearchNcbData();
						$intervalSearchNcb = setInterval(function (){SearchNcbData();},Number(RETRIEVE_NCB_DATA_TIMEOUT));
					}
				}
		   }).error(function(){
			   unblockScreen();
			   PushErrorNotifications('Network or Connection Error, please try again');
		   });
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}
function ExecuteButtonService(buttonID){
	var $EAIMessage = '';
	if($('#'+buttonID).attr('id') != undefined && $('#edit_7001').attr('id') != undefined && 'button_7001' == buttonID){
		var dataString = "className=EAIServiceMessage&packAge="+packageOrigRules+"&returnType=1";	
			jQuery.ajax({
				type :"POST",
				url :"AjaxDisplayServlet",
				data :dataString,
				async :false,
				success : function(data ,status ,xhr){
					$EAIMessage = data;
				}
			});
	}	
	if($EAIMessage == EAITFB0137I01_ERROR){
		$('.boxy-wrapper').remove();
		$('.boxy-modal-blackout').remove();
		 Boxy.ask(CLEAR_DATA_EAI, ["Yes", "No"], function(val) {		 	
			      if("Yes" == val){	
				  		var dataString = "className=SetupEAIService&packAge="+packageOrigRules+"&returnType=0&eaiflag=Y";	
						jQuery.ajax({
							type :"POST",
							url :"AjaxDisplayServlet",
							data :dataString,
							async :false,
							success : function(data ,status ,xhr){
									$.map(data, function(item){
										try{
											switch(item.TYPE){
												case EXECUTE:
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
												case BUTTON_EDIT:
													$('#'+item.id).removeClass("button");
													$('#'+item.id).removeClass("button-red");								
													$('#'+item.id).addClass(item.value);
													break;
												default:
													break;
											}
										}catch(e){											
										}
									});
								if(MandatoryExecuteButton(buttonID))
									return false;	
					    		MandatoryXrulesService(buttonID);
							}
						});
			      }
			      if("No" == val){
			    	  var dataString = "className=SetupEAIService&packAge="+packageOrigRules+"&returnType=0&eaiflag=N";	
						jQuery.ajax({
							type :"POST",
							url :"AjaxDisplayServlet",
							data :dataString,
							async :false,
							success : function(response ,status ,xhr){
								if(MandatoryExecuteButton(buttonID))
									return false;	
					    		MandatoryXrulesService(buttonID);
							}
						});
			      }
			  	$('.boxy-wrapper').remove();
				$('.boxy-modal-blackout').remove();
		    }, {title:'Message'});
	}else{
		if(MandatoryExecuteButton(buttonID))
				return false;	
		MandatoryXrulesService(buttonID);
	}
}
function MandatoryXrulesService(buttonID){
	var isError = true;
	DestoryErrorField();
	var dataString = "className=XrulesMandatory&packAge="+packagePlMandatory+"&returnType=0&"
										+$("#avale-obj-form *").serialize()+"&buttonID="+buttonID;	
	jQuery.ajax({
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :false,
		dataType: "json",
		success : function(response ,status ,xhr){			
			if(response != null && response.length > 0){
				isError = true; 
				DisplayErrorField(response);	
				OpenNotification();
			}else{
				isError = false;
			}
		},
		error:function(){
			PushErrorNotifications('Network or Connection Error, please try again');
			isError = true;
		}
	});
	if(!isError){
		ExecuteService(buttonID);
	}
}
function ExecuteManualService(buttonID,serviceID){
	try{
		$('#action').val('ExecuteXrulesManual');
		$('#handleForm').val('Y');
		$('#currentTab').val('MAIN_TAB');
		var dataString = $('#avale-obj-form *').serialize()+"&buttonID="+buttonID+"&serviceID="+serviceID;
		if(!openblockscreen){
			blockScreen();
		}
		$.post('FrontController',dataString
			,function(data,status,xhr){				
				if(data != null){
					DisplayExecuteEngine(data);
				}
				unblockScreen();
		   }).error(function(){
			   unblockScreen();
			   PushErrorNotifications('Network or Connection Error, please try again');
		   });
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}
function ExecuteService(buttonID){
	try{
		$('#action').val('ExecuteXrulesModule');
		$('#handleForm').val('Y');
		$('#currentTab').val('MAIN_TAB');
		var dataString = $('#avale-obj-form *').serialize()+"&buttonID="+buttonID;
		blockScreen();
		$.post('FrontController',dataString
			,function(data,status,xhr){				
				if(data != null){
					DisplayExecuteEngine(data);
				}
				unblockScreen();
		   }).error(function(){
			   unblockScreen();
			   PushErrorNotifications('Network or Connection Error, please try again');
		   });
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}
function DisplayExecuteEngine(data){
	var notification = false;
	try{
		$.map(data, function(item){	
			try{
				switch(item.TYPE){
					case EXECUTE:
						if(item.EXE_CODE == SUCCESS){
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
						}else{
							$("#code_"+item.SERVICE_ID).val('');
							if($("#result_"+item.SERVICE_ID).attr('type') != undefined){
								switch ($("#result_"+item.SERVICE_ID).attr('type')) {
									case 'text': $("#result_"+item.SERVICE_ID).val(item.RESULT_DESC); break;	
									case 'textbox': $("#result_"+item.SERVICE_ID).val(item.RESULT_DESC); break;
									default:break;
								}
							}else{
								$("#result_"+item.SERVICE_ID).html(item.RESULT_DESC);
							}				    
						}
						try{
							if(item.STYLE != ''){
								$('#result_'+item.SERVICE_ID).removeClass();
								$('#result_'+item.SERVICE_ID).addClass(item.STYLE);
							}
						}catch (e){
						}
						DisplayServiceSubField(item);
						break;
					case BUTTON:
						$('#'+item.id).removeClass("button");
						$('#'+item.id).removeClass("button-red");								
						$('#'+item.id).addClass(item.value);
						break;
					case BUTTON_EDIT:
						$('#'+item.id).removeClass("button");
						$('#'+item.id).removeClass("button-red");								
						$('#'+item.id).addClass(item.value);
						break;
					case 'SUMMARY_RULE':
						$('#'+item.ATTR_VALUE).html(item.VALUE);
						$('#'+item.ATTR_STYLE).removeClass();
						$('#'+item.ATTR_STYLE).addClass(item.STYLE_COLOR);
						break;
					case 'MESSAGE':
						if(item.message != null && item.message != ''){
							PushNotifications(item.message);
							notification = true;
						}
						break;
					default:
						break;
				}				
			}catch(e){
				unblockScreen();
				var msg = ERROR_JS+e.message;
				PushErrorNotifications(msg);
			}
		});	
		if(notification){
			OpenNotification();
		}	
		$.map(data, function(item){
			if(item.TYPE == FINAL_EXE){							 	
				switch(item.RESULT_CODE){
					case REJECT: 
							unblockScreen();										
							alertMassageYesNoFunc(REJECT_MSG,RejectApplication,null);
							break;
					case CANCEL:
							unblockScreen();
							if(item.RESULT_DESC == DUP){
								alertMassageYesNoFunc(CANCEL_MSG,CancleApplication,null);	
							}
							if(item.RESULT_DESC == REC1){
								alertMassageYesNoFunc(CANCEL_REC1_MSG,CancleApplication,null);	
							}
							if(item.RESULT_DESC == REC2){
								alertMassageYesNoFunc(CANCEL_REC2_MSG,CancleApplication,null);	
							}
							break;
					case BLOCK:
							unblockScreen();
							alertMassageFunc(BLOCK_MSG,BlockApplication);	
							break;
					case PASS:
							break;
					case CODE_CHANGE_BUSCLASS:
							if(item.RESULT_DESC == FCP_KEC_CG){
								$('.boxy-wrapper').remove();
								$('.boxy-modal-blackout').remove();
								 Boxy.alert(MSG_CHANGE_CG
										 	,function(){
									 			try{
										 			blockScreen();
										 			$('#action').val('LoadSubformByBusClass');
										 			$('#handleForm').val('N');								 			
										 			$('#change-saletype').val('CG');								 			
													$('#appFormName').submit();
									 			}catch(e){
									 				unblockScreen();
									 				var msg = ERROR_JS+e.message;
									 				PushErrorNotifications(msg);
									 			}
								 			},{title: 'Alert Message'});
							}
							break;
					default:unblockScreen();
							break;
				}
			}
		});
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}

function DisplayServiceSubField(item){
	switch ((item.SERVICE_ID).toString()) {
		case EXISTING_KEC_SERVICE:
			$('#'+item.FIELD01).val(item.VALUE01);
			if($('#'+item.FIELD02).html() == null && item.VALUE02 == 'Y'){
				PerpendNotifyID(MSG_NOT_EQUAL_CARD_STATUS ,item.FIELD02);
				OpenNotification();
			}
			break;
		case FRAUD_COMPANY_SERVICE:		
			$('#'+item.FIELD01).val(item.VALUE01);
			break;	
		case PAY_ROLL_SERVICE:	
			if($('#salary').val() != undefined){
				$('#'+item.FIELD01).val(item.VALUE01);
//				#septemwi calculate income when add salary
				CalculateIncome();
				SensitiveAttrNameEngine('salary');
			}
			try{
				var data = $.parseJSON(item.OBJECT);
				if(data != undefined){
					displayJSON(data);
				}
			}catch(e){}
			break;
		case '1019':
			if($('#result_1019').val() == 'PASS(FOUND)'){
				SensitiveAttrNameEngine('code_1019');
			}
			break;
		default:
			break;
	}
}
function BlockApplication(){
	try{
		blockScreen();
		$('#action').val('DeplicateAppWebAction');
		$('#currentTab').val('MAIN_TAB');
		$('#handleForm').val('Y');										 			
		$('#appFormName').submit();
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}
function RejectApplication(){
	try{
		blockScreen();
		$('#action').val('RejectAppWebAction');
		$('#currentTab').val('MAIN_TAB');
		$('#handleForm').val('Y');										 			
		$('#appFormName').submit();
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}
function CancleApplication(){
	try{
		blockScreen();
		$('#action').val('CancleAppWebAction');
		$('#currentTab').val('MAIN_TAB');
		$('#handleForm').val('Y');										 			
		$('#appFormName').submit();
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}
function CalculateIncome(){
	var dataString = $("#table-incomedebt *").serialize();
//	septemwi comment change to sync ajax
//	ajaxDisplayElementJsonAsync('CalculateIncome',packageOrigRules,dataString);
	ajaxDisplayElementJson('CalculateIncome',packageOrigRules,dataString);
}
function CalculateDebt(){
	var dataString = $("#table-incomedebt *").serialize();
//	septemwi comment change to sync ajax
//	ajaxDisplayElementJsonAsync('CalculateDebt',packageOrigRules,dataString);
	ajaxDisplayElementJson('CalculateDebt',packageOrigRules,dataString);
}

function OpenPopupDialogDocument(action,width,higth,title){
	var dataStr = CONTEXT_PATH_ORIG+'/FrontController?action='+action+'&customertype='+$('#customertype').val();
	if($('input[name=applicant_radio]:checked').val() != undefined){
		dataStr = dataStr+'&applicant_radio='+$('input[name=applicant_radio]:checked').val();
	}
								
//	var busclassID = $('#busClass').val();
	var buttonArray;
	
	if(ROLE_FU == $('#current-role').val()
//			#septem comment for new logic FU
//			||(ROLE_CA == $('#current-role').val() && (BUS_CLASS_IC == busclassID || BUS_CLASS_DC == busclassID))
//				 || (ROLE_DC == $('#current-role').val() && (BUS_CLASS_IC == busclassID || BUS_CLASS_DC == busclassID))
			){
		var channel = $('#channel').val();
		if(channel == '01'){
			buttonArray =  { "Send Email to Branch" : function() { SendEmailToBranch(); },
  			  			 	 "Save"  : function() {saveDocList();},
  			  			 	 "Close" : function(){$dialog.dialog("close"); closeDialog(); }
  							};
		}else if(channel == '06'){
			buttonArray =  { "Save"  : function() {	saveDocList();},
 			  		 		 "Close" : function() { $dialog.dialog("close"); closeDialog(); }
						   };
		}else{
			if($('#sms-followup').val() == 'Y'){
				buttonArray =  { "Save"  : function() {	saveDocList();},
	  			  		 		 "Close" : function() { $dialog.dialog("close"); closeDialog();}
							 	};
			}else{
				buttonArray = { "Start Following" : function() { StartFollow(); },
			  			 	 	"Save"  : function() {saveDocList();},
			  			 	 	"Close" : function(){$dialog.dialog("close"); closeDialog(); }
							  };
			}
		}
	}else{
		buttonArray =  { "Save"  : function() {	saveDocList();},
	  			  		 "Close" : function() { $dialog.dialog("close"); closeDialog(); }
						};
	}
	
	var verifySearchType = $('#searchtype-verify').val();
	if(verifySearchType == SERACH_TYPE_ENQUIRY){
		buttonArray =  {"Close" : function(){ $dialog.dialog("close"); closeDialog(); }};
	}
	
	$dialog = $dialogEmpty;	
	$(".plan-dialog").remove();	
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(dataStr);
	$dialog.dialog({
		    resizable : false,
		    modal : true,
		    autoOpen: false,
		    draggable: true,
		    closeOnEscape:false,
		    width: width,
		    height: $('.frame-module').height()-71,
		    title:title,
		    position: { 
				        my: 'center',
				        at: 'center',
				        of: $('.frame-module')
		    		},
			buttons : buttonArray,
		    close: function(){
		    	closeDialog();
		    }
	    });
	$dialog.dialog('open');
}
function OpenPopupDialogVerifyCustomer(action,width,higth,title){
	var dataStr = CONTEXT_PATH_ORIG+'/FrontController?action='+action+'&name_eng='+encodeURI($('#name_eng').val())
							+'&surname_eng='+encodeURI($('#surname_eng').val())+'&identification_no='+encodeURI($('#identification_no').val())
								+'&birth_date='+encodeURI($('#birth_date').val());
	$dialog = $dialogEmpty;		
	var buttonArray;
	var verifyDisplayMode = $('#display-mode-verify').val();
	if(verifyDisplayMode == DISPLAY_MODE_VIEW){
		buttonArray =  {"Close" : function(){ $dialog.dialog("close"); closeDialog(); }};
	}else{
		buttonArray = { "Save" : function() {SaveVerifyCustomerAction(); }
						, "Close" : function() {$dialog.dialog("close");	closeDialog();}
					   };
	}	
	$(".plan-dialog").remove();	
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(dataStr);
	$dialog.dialog({
		    resizable : false,
		    modal : true,
		    autoOpen: false,
		    draggable: true,
		    width: width,
		    height: higth,
		    title:title,
		    position: { 
				        my: 'center',
				        at: 'center',
				        of: $('.frame-module')
		    		},
    		buttons : buttonArray,
		    close: function(){
		    	closeDialog();
		    }
	    });
	$dialog.dialog('open');
}
function OpenPopupDialogVerifyHR(action,width,higth,title) {
	var url = CONTEXT_PATH_ORIG+"/FrontController?action="+action;	
	$dialog = $dialogEmpty;	
	
	var buttonArray;
	var verifyDisplayMode = $('#display-mode-verify').val();
	if(verifyDisplayMode == DISPLAY_MODE_VIEW){
		buttonArray =  {"Close" : function(){ $dialog.dialog("close"); closeDialog(); }};
	}else{
		buttonArray = { "Save" : function() {SaveVerifyHR(); }
						, "Close" : function() {$dialog.dialog("close");	closeDialog();}
					   };
	}	
	
	$(".plan-dialog").remove();	
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(url);
	$dialog.dialog({
		    resizable : false,
		    modal : true,
		    autoOpen: false,
		    draggable: true,
		    width: width,
		    height: higth,
		    title:title,
		    position: { 
				        my: 'center',
				        at: 'center',
				        of: $('.frame-module')
		    		},
			buttons : buttonArray,
		    close: function(){
		    	closeDialog();
		    }
	    });
	$dialog.dialog('open');	
}
function OpenPopupDialogFollowDetail(action,width,higth,title) {
	var url = CONTEXT_PATH_ORIG+"/FrontController?action="+action;	
	$dialog = $dialogEmpty;		
	$(".plan-dialog").remove();	
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(url);
	$dialog.dialog({
		    resizable : false,
		    modal : true,
		    autoOpen: false,
		    draggable: true,
		    width: width,
		    height: higth,
		    title:title,
		    position: { 
				        my: 'center',
				        at: 'center',
				        of: $('.frame-module')
		    		},
			buttons : {
			        "Close" : function() {						        	
			        	$dialog.dialog("close");	
			        	closeDialog();
			        }
		    	},
		    close: function(){
		    	closeDialog();
		    }
	    });
	$dialog.dialog('open');	
}
function OpenPopupDialogNcb(action,width,higth,title){	
	var url = CONTEXT_PATH_ORIG+"/FrontController?action="+action;
	
	$dialog = $dialogEmpty;
	var buttonArray;
	var verifyDisplayMode = $('#display-mode-verify').val();	
	var code_1023 =  $('#code_1023').val();	
	if(code_1023 != '' && code_1023 != 'SB'){
		verifyDisplayMode = DISPLAY_MODE_VIEW;
	}
	if(verifyDisplayMode == DISPLAY_MODE_VIEW){
		buttonArray =  {"Close" : function(){ $dialog.dialog("close"); closeDialog(); }};
	}else{
		buttonArray = { "Save" : function() {SaveNcb(); }
						, "Close" : function() {$dialog.dialog("close");	closeDialog();}
					   };
	}
	$(".plan-dialog").remove();	
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(url);
	$dialog.dialog({
		    resizable : false,
		    modal : true,
		    autoOpen: false,
		    draggable: true,
		    width: width,
		    height: $('.frame-module').height()-71,
		    title:title,
		    position: { 
				        my: 'center',
				        at: 'center',
				        of: $('.frame-module')
		    		},
    		buttons : buttonArray,
		    close: function(){
		    	closeDialog();
		    }
	    });
	$dialog.dialog('open');
	
//	
//	openDialog(url,width,higth,scrollbars=0,setPrefs);
	
}
function OpenPopupDialogFraudCompary(action,width,higth,title){	
	var url = CONTEXT_PATH_ORIG+"/FrontController?action="+action;	
	$dialog = $dialogEmpty;		
	$(".plan-dialog").remove();	
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(url);
	var buttonArray;
	if($('#fraud-comp-searchtype').val() == SEARCH_TYPE_LIKE){
		buttonArray =  { "Save"  : function(){SaveFraudCompany();},
			 		 	 "Close" : function(){$dialog.dialog("close"); closeDialog();}
						};
	}else{
		buttonArray =  {"Close" : function(){$dialog.dialog("close"); closeDialog();}};
	}	
	var verifyDisplayMode = $('#display-mode-verify').val();
	if(verifyDisplayMode == DISPLAY_MODE_VIEW){
		buttonArray =  {"Close" : function(){ $dialog.dialog("close"); closeDialog(); }};
	}
	
	$dialog.dialog({
		    resizable : false,
		    modal : true,
		    autoOpen: false,
		    draggable: true,
		    width: width,
		    height: $('.frame-module').height()-71,
		    title:title,
		    position: { 
				        my: 'center',
				        at: 'center',
				        of: $('.frame-module')
		    		},
    		buttons : buttonArray,
		    close: function(){
		    	closeDialog();
		    }
	    });
	$dialog.dialog('open');
}
function OpenPopupEditVerification(action,width,higth,title){	
	var url = CONTEXT_PATH_ORIG+"/FrontController?action="+action;	
	$dialog = $dialogEmpty;	
	
	var buttonArray;
	var verifyDisplayMode = $('#display-mode-verify').val();
	if(verifyDisplayMode == DISPLAY_MODE_VIEW){
		buttonArray =  {"Close" : function(){ $dialog.dialog("close"); closeDialog(); }};
	}else{
		buttonArray = { "Save" : function() {SaveEditVerification(); }
						, "Close" : function() {$dialog.dialog("close");	closeDialog();}
					   };
	}
	
	$(".plan-dialog").remove();	
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(url);
	$dialog.dialog({
		    resizable : false,
		    modal : true,
		    autoOpen: false,
		    draggable: true,
		    width: width,
		    height: $('.frame-module').height()-71,
		    title:title,
		    position: { 
				        my: 'center',
				        at: 'center',
				        of: $('.frame-module')
		    		},
    		buttons : buttonArray,
		    close: function(){
		    	closeDialog();
		    }
	    });
	$dialog.dialog('open');
}
function EnableLoanIncome(){
	if($('#applicant_radio').attr('id') != undefined){
		var count = $('input[name=applicant_radio]:checked').length;
		if(count > 0 && $('input[name=applicant_radio]:checked').val() == '01'){
			$('#loan-income').unbind();
			$('#loan-income').removeClass('textbox-currency-view');
			$('#loan-income').attr('readOnly',false);
			$('#loan-income').addClass('textbox');
			$('#loan-income').blur(function(){
				CalculateIncome();
			});
			SensitiveEngine('#loan-income');
			TextBoxCurrencyEngine('#loan-income');	
		}else{
			DisableLoanIncome();
		}
	}
}
function DisableLoanIncome(){
	$('#loan-income').unbind();
	$('#loan-income').removeClass('textbox');
	$('#loan-income').attr('readOnly',true);
	$('#loan-income').addClass('textbox-currency-view');	
	$('#loan-income').val('0.00');
	CalculateIncome();
}
function MandatoryOpenDocument(){
	var verifySearchType = $('#searchtype-verify').val();
	if(verifySearchType == SERACH_TYPE_ENQUIRY){
		return true;
	}	
	if($('#applicant_radio').attr('id') != undefined && !$('#applicant_radio').attr('disabled')){
		if($('input[name=applicant_radio]:checked').length == 0){
			PushErrorNotifications(REQUIRE_INCOME_TYPE);
			return false;
		}else{
			DestoryNotification();
		}
	}
	return true;
}
function LogicColorStyleResult(resultCode , resultDesc){
	if(resultCode == 'P' || resultCode == 'POR' || resultCode == 'WV'){
		return 'verify-textbox-green';
	}
	if(resultCode == 'F'){
		return 'verify-textbox-red';
	}
	if(resultCode == 'OR' || resultCode == 'GOR'){
		return 'verify-textbox-yellow';
	}
	if(resultCode == 'WN' || resultCode == 'SETUP_DECISION' || resultCode == 'RQ' || resultCode == 'NA'){
		return 'verify-textbox-orange';
	}
	if(resultCode == '' || resultDesc != ''){
		return 'verify-textbox-error';
	}
	return 'verify-textbox-default';
}
