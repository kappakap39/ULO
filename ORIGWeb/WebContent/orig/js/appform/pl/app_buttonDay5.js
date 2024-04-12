/**
 *#Sankom 
 */
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
	try{
		parent.leftFrame.openMenuFrame();
	}catch(e){		
	}
	try{
//		appFormName.action.value="FristPLApp";
//		appFormName.handleForm.value = "N";
//		appFormName.submit();	
		$('#action').val('FristPLApp');
		$('#handleForm').val('Y');
		$('#appFormName').submit();
	}catch(e){	
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}
function validateSaveApp(){
	$('#submitType').val('Draft');
	$('#mandatoryType').val('5');
	MandatorySaveApplication();
}
function SaveApplication(){
	try{
//		var form = document.appFormName;
//		var userRole = $('#role').val();
		blockScreen();		
		$('#action').val('SaveCashDay5Application');
		$('#handleForm').val('Y');
//		form.submit();
		$('#appFormName').submit();
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		PushErrorNotifications(msg);
	}
}
