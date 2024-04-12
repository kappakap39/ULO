/**
 * Rawi Songchaisin
 */
function MandatoryExecuteButton(buttonID){
	DestoryErrorField();
	$('#mandatoryType').val(MANDATORY_TYPE_EXECUTE);
	var dataString = "className=ApplicationMandatory&packAge="+packagePlMandatory+"&returnType=0&"
																+$("#avale-obj-form *").serialize()+"&buttonID="+buttonID;
	var error = false;
	jQuery.ajax( {
		type :"POST",
		url :'AjaxDisplayServlet',
		data :dataString,	
		async :false,	
		dataType: "json",
		success : function(data ,status ,xhr){
			if(data != null && data.length > 0){
				error =  true;
				DisplayErrorField(data); /**Fix For Used Notification Display Error #SeptemWi*/
				OpenNotification();
			}else{				
				error =  false;
			}
		},
		error : function(data){
			error =  true;
			PushErrorNotifications('Network or Connection Error, please try again');
		}
	});	
	return error;
}

function MandatorySaveApplication(){
	DestoryErrorField();
	var dataString = "className=ApplicationMandatory&packAge="+packagePlMandatory+"&returnType=0&"
								+$("#avale-obj-form *").serialize();
	jQuery.ajax( {
		type :"POST",
		url :'AjaxDisplayServlet',
		data :dataString,
		async :false,	
		dataType: "json",
		success : function(data ,status ,xhr){			
			if(data != null && data.length > 0){
				unblockScreen();
				DisplayErrorField(data); /**Fix For Used Notificatoin Display Error #SeptemWi*/
				OpenNotification();
			}else{ 
				SaveApplication();
			}
		},
		error : function(data){
			unblockScreen();
			PushErrorNotifications('Network or Connection Error, please try again');
		}
	});
}

function MandatorySubmitApplication(role){
	DestoryErrorField();
	var dataString = "className=ApplicationMandatory&packAge="+packagePlMandatory+"&returnType=0&"
																	+$("#avale-obj-form *").serialize();
	jQuery.ajax( {
		type :"POST",
		url :'AjaxDisplayServlet',
		data :dataString,	
		async :false,	
		dataType: "json",
		success : function(data ,status ,xhr){
			if(data != null && data.length > 0){
				unblockScreen();
				DisplayErrorField(data); /**Fix For Used Notificatoin Display Error #SeptemWi*/
				OpenNotification();
			}else{				
				/**#SeptemWi Execute ILOG Submit Application Logic*/
				ILOGExecution();
			}
		},
		error : function(data){
			unblockScreen();
			PushErrorNotifications('Network or Connection Error, please try again');
		}
	});
}

function mandatorySubmitAddressServlet(role){
	DestoryErrorField();
	var dataString = "className=ApplicationMandatory&packAge="+packagePlMandatory+"&returnType=0&"
																+$("#avale-obj-form *").serialize()+"&role="+role;
	var error = false;
	jQuery.ajax( {
		type :"POST",
		url :'AjaxDisplayServlet',
		data :dataString,	
		async :false,	
		dataType: "json",
		success : function(data ,status ,xhr){
			if(data != null && data.length > 0){
				error =  true;
				DisplayErrorField(data); /**Fix For Used Notificatoin Display Error #SeptemWi*/
				OpenNotification();
			}else{				
				error =  false;
				passSaveData();
			}
		},
		error : function(data){
			error =  true;
			PushErrorNotifications('Network or Connection Error, please try again');
		}
	});
	return error;
}