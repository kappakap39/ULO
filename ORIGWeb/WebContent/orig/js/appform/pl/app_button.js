/**#SeptemWi*/
$(document).ready(function(){
	var server_flag = $('#server_flag').val();
	if(CANCEL == server_flag ||BLOCK == server_flag){
		LogicServerSite(server_flag);
	}
});
function LogicServerSite(server_flag){					 	
	switch(server_flag){
		case CANCEL:
			unblockScreen();
			alertMassageYesNoFuncExecute(CANCEL_MSG, SaveExecuteCancleApplication, null);
			break;
		case BLOCK:
			unblockScreen();
			alertMassageFuncExecute(BLOCK_MSG, SaveExecuteBlockApplication);
			break;
		default: unblockScreen();break;
	}
}
function auditButton(appRecId){
	var url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadAuditWebAction&appRecId="+appRecId;
	$('#appRecId').val(appRecId);
	$dialog = $dialogEmpty;
	var width = 950;
	var higth = $('.frame-module').height()-71;
	var title = 'Audit Trail';
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
   $dialog.dialog("open");
}
function loadHistoryAction(appRecId){
	var url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadHistoryActionWebAction&appRecId="+appRecId;
	$('#appRecId').val(appRecId);
	$dialog = $dialogEmpty;
	var width = 950;
	var higth = $('.frame-module').height()-71;
	var title = 'History Action';
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
   $dialog.dialog("open");
}
function cancelButton(userRole){
	openblockscreen = false;
	try{
		parent.leftFrame.openMenuFrame();
	}catch(e){
	}
	try{
		blockScreen();
//		appFormName.action.value="FristPLApp";
//		appFormName.handleForm.value = "N";
//		appFormName.submit();
		$('#action').val('FristPLApp');
		$('#handleForm').val('Y');
		$('#appFormName').submit();
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}
function validateSaveApp(){
	var $userrole = $('#role').val();
	openblockscreen = false;
	$('#submitType').val('Draft');
	$('#mandatoryType').val(MANDATORY_TYPE_DRAFT);	
	if(isMandatoryBlock($userrole)){
		ExecuteServiceApplication('button_7001',DEPLICATE_APPLICATION,MandatorySaveApplication);
	}else{
		if($('#code_1040').val() != undefined){
			LoadSaveNcbData(MandatorySaveApplication);
		}else{
			MandatorySaveApplication();	
		}
	}
}
function validateSubmitApp(userRole){
	var $userrole = $('#role').val();
	openblockscreen = false;
    $('#submitType').val('Send');
    var obj = $('input[name=decision_option]:checked');
    var mandatoryType = obj.attr('mondatory');
    if(mandatoryType == null || mandatoryType == '') 
  	  	mandatoryType = MANDATORY_TYPE_SUBMIT;      
	 $('#mandatoryType').val(mandatoryType);
	//execute block application #septemwi
	if(isMandatoryBlock($userrole) || isMandatoryBlockPO($userrole)){
		ExecuteServiceApplication('button_7001',DEPLICATE_APPLICATION,MandatorySubmitApplication);
	}else{		
		if($('#code_1040').val() != undefined){
			LoadSaveNcbData(MandatorySubmitApplication);
		}else{
			MandatorySubmitApplication();
		}		
	}	  
}

/**#SeptemWi ExecuteILOG Save Application*/
function ILOGExecution(){
	try{
		var $Module = '';
		var $userrole = $('#role').val();
		var $busclass = $('#busClass').val();
		var $jobstate = $('#jobState').val();
		if($userrole == ROLE_DC || $userrole == ROLE_DC_SUP){
			if($busclass == INCREASE_BUSSCLASS || $busclass == DECREASE_BUSSCLASS){
				$Module = MODULE_DCI_DECISION;
			}else{
				$Module = MODULE_DC_DECISION;
			}		
		}	
		if($userrole == ROLE_VC || $userrole == ROLE_VC_SUP){
			$Module = MODULE_VC_DECISION;
		}
		if ($userrole == ROLE_CA || $userrole == ROLE_CA_SUP){	
			if($jobstate == 'STI1511'){
				$Module = MODULE_DC_DECISION;
			}
		}
		if ($userrole == ROLE_DE_SUP){
			$Module = MODULE_DE_SUP_DECISION;
		}
		if($Module != ''){
			$('#action').val('ILOGExecution');
			$('#handleForm').val('Y');
			$('#currentTab').val('MAIN_TAB');
			var dataString = $('#avale-obj-form *').serialize()+"&ilog-module="+$Module;
			
			if(!openblockscreen){
				blockScreen();
			}
			$.post('FrontController',dataString
				,function(data,status,xhr){				
					if(data != null){
						DisplayILOGExecution(data);
					}
			   }).error(function(){
				   unblockScreen();
				   PushErrorNotifications('Network or Connection Error, please try again');
			   });
		}else{
			SaveApplication();
		}
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}
function DisplayILOGExecution(data){
	try{
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
						break;
					case 'SUMMARY_RULE':
						$('#'+item.ATTR_VALUE).html(item.VALUE);
						$('#'+item.ATTR_STYLE).removeClass();
						$('#'+item.ATTR_STYLE).addClass(item.STYLE_COLOR);
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
		$.map(data, function(item){
			if(item.TYPE == FINAL_EXE){							 	
				switch(item.RESULT_CODE){
					case REJECT: 
							unblockScreen();										
							alertMassageYesNoFunc(item.RESULT_DESC,ILOGRejectApplication,null);
							break;
					case 'ERROR':
							unblockScreen();
							PerpendNotifyID(item.RESULT_DESC,'error-message-ilog');
							OpenNotification();
							break;
					case 'DEFAULT': SaveApplication(); break;
					default: unblockScreen(); break;
				}
			}
		});
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}
function ILOGRejectApplication(){
	SaveApplication();
}
function LoadSaveNcbData(func){
	try{
		var $userrole = $('#role').val();
		var code_1040 = $('#code_1040').val();
		var ncbDecision = '';
		if(code_1040 != undefined && code_1040 == WAITING_NCB_DATA){
			StopSearchNcbData();
			var dataString = "className=SearchNcbData&packAge="+packageOrigRules+"&returnType=0";
			jQuery.ajax( {
				type :"POST",
				url :"AjaxDisplayServlet",
				data :dataString,
				async :false,	
				dataType: "json",
				success : function(data ,status ,xhr){
					if(data != null){
						$.map(data, function(item){
							$('#'+item.id).val(item.value);
							if(item.id == 'ncb-color'){
								$('#'+item.id).removeClass();
								$('#'+item.id).addClass(item.value);
							}else if(item.id == 'ncb-decision'){
								ncbDecision = item.value;
							}
						});												
					}	
					code_1040 = $('#code_1040').val();
				},
				error : function(response){
					unblockScreen();
					PushErrorNotifications('Network or Connection Error, please try again');
				}
			});
		}
		if(FAIL == ncbDecision && $userrole != ROLE_CA && $userrole != ROLE_CA_SUP){
			unblockScreen();
			alertMassageFunc(REJECT_MSG,RejectApplication);
		}else{
			new func();
		}
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}

function SaveApplication(){
	try{
//		var form = document.appFormName;
		var userRole = $('#role').val();
		if(!openblockscreen){
			blockScreen();
		}
		if(userRole == ROLE_DE || userRole == ROLE_DE_SUP){
			$('#action').val('DESaveApplication');
		}
		if(userRole == ROLE_DC || userRole == ROLE_DC_SUP){
			if($('#busClass').val() == INCREASE_BUSSCLASS || $('#busClass').val() == DECREASE_BUSSCLASS){
				$('#action').val('DCICDCSaveApplication');
			}else{
				$('#action').val('DCSaveApplication');
			}
		}
		if(userRole == ROLE_VC || userRole == ROLE_VC_SUP){
			$('#action').val('VCSaveApplication');
		}
		if (userRole == ROLE_CA || userRole == ROLE_CA_SUP){		
			// check job state ca increase sup  #Vikrom 20121218
			var jobState = $('#jobState').val();
			if(jobState == 'STI1511'){
				$('#action').val('DCSaveApplication');
			} else {
				$('#action').val('CASaveApplication');
			}
		}
		if (userRole == ROLE_FU || userRole == ROLE_FU_SUP){
			$('#action').val('FUSaveApplication');
		}
		if(userRole == ROLE_DF || userRole == ROLE_DF_SUP || userRole == DF_REJECT){
			$('#action').val('DFSaveApplication');
		}
		if(userRole == ROLE_PO){
			$('#action').val('SavePOWebAaction');
		}
		$('#handleForm').val('Y');
//		form.submit();
		$('#appFormName').submit();
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}

function ExecuteServiceApplication(buttonID,serviceID,func){
	try{
		$('#action').val('ExecuteXrulesManual');
		$('#handleForm').val('Y');
		$('#currentTab').val('MAIN_TAB');
		var dataString = $('#avale-obj-form *').serialize()+"&buttonID="+buttonID+"&serviceID="+serviceID+"&reset_service=Y";
		if(!openblockscreen){
			blockScreen();
		}
		$.post('FrontController',dataString ,function(data,status,xhr){				
			if(data != null){
				ShowExecuteApplication(data,func);
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
function ShowExecuteApplication(data,func){
	try{
		var userRole = $('#role').val();
		$.map(data, function(item){	
			try{
				switch(item.TYPE){
					case EXECUTE:
						if(item.EXE_CODE == 'S'){
							$("#code_"+item.SERVICE_ID).val(item.RESULT_CODE);
							$("#result_"+item.SERVICE_ID).val(item.RESULT_DESC);				
						}else{
							$("#code_"+item.SERVICE_ID).val('');
							$("#result_"+item.SERVICE_ID).val(item.EXE_DESC);
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
					default: 
						break;
				}				
			}catch(e){
				unblockScreen();
				var msg = ERROR_JS+e.message;
				PushErrorNotifications(msg);
			}
		});
		$.map(data, function(item){
			if(item.TYPE == FINAL_EXE){							 	
				switch(item.RESULT_CODE){
					case CANCEL:
						unblockScreen();
						alertMassageYesNoFuncExecute(CANCEL_MSG, SaveExecuteCancleApplication, null);
						break;
					case BLOCK:
						unblockScreen();
						alertMassageFuncExecute(BLOCK_MSG, SaveExecuteBlockApplication);
						break;
					case PASS:
						if(userRole ==  ROLE_PO){
							new func();
						}else{		
							if($('#code_1040').val() != undefined){
								LoadSaveNcbData(func);
							}else{
								new func();
							}		
						}					
						break;
					default: unblockScreen(); break;
				}
			}
		});
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}
function alertMassageFuncExecute(massage,func){
	$('.boxy-wrapper').remove();
	$('.boxy-modal-blackout').remove();
	 Boxy.alert(massage,function(){
		 			if(func != null) new func();
		 			$('.boxy-wrapper').remove();
					$('.boxy-modal-blackout').remove();
	 			},{title: 'Alert Message'});
}
function alertMassageYesNoFuncExecute(massage,yesFunc,noFunc){
	$('.boxy-wrapper').remove();
	$('.boxy-modal-blackout').remove();
	 Boxy.ask(massage, ["Yes", "No"], function(val) {		 	
		      if("Yes" == val){
		    	  if(yesFunc != null) new yesFunc();
		      }
		      if("No" == val){
		    	  if(noFunc != null) new noFunc();
		      }
		  	$('.boxy-wrapper').remove();
			$('.boxy-modal-blackout').remove();
	    }, {title:'Message'});
}
function SaveExecuteBlockApplication(){
	try{
		if(!openblockscreen){
			blockScreen();
		}
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
function SaveExecuteCancleApplication(){
	try{
		if(!openblockscreen){
			blockScreen();
		}
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
function isMandatoryBlock(userRole){
	var mandatory = false;
//	#septemwi not validate at javascript change to server side!!
//	if(userRole == ROLE_DE || userRole == ROLE_DE_SUP
//		|| userRole == ROLE_DC || userRole == ROLE_DC_SUP
//			|| userRole == ROLE_VC || userRole == ROLE_VC_SUP
//				||userRole == ROLE_FU || userRole == ROLE_FU_SUP){
//		if($('#identification_no').attr('id') != undefined && $('#identification_no').val() != ''){
//			mandatory = true;
//		}
//	}
	return mandatory;
}
function isMandatoryBlockPO(userRole){
	var mandatory = false;
	if(userRole == ROLE_PO){
		if($('#identification_no').attr('id') != undefined && $('#identification_no').val() != ''){
			mandatory = true;
		}
	}
	return mandatory;
}