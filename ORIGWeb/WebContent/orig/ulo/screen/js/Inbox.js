$(document).ready(function(){
	try{
		$("#ON_OFF_BTN").bootstrapSwitch();
		setInboxFlagValue();
		$("#ON_OFF_BTN").on('switchChange.bootstrapSwitch', function(event,state){
			if(state==true){
				$('[name="INBOX_BTN"]').val('Y');
				INBOX_ActionJS('Y',"");
			}else{
				$('[name="INBOX_BTN"]').val('N');
				INBOX_ActionJS('N',"");
			}
		});
	}catch(exception){
		errorException(exception);
	}
}); 

function setInboxFlagValue(){
	try{
		var inbox_flag =$('[name="INBOX_BTN"]').val();
		if("Y"==inbox_flag){
			$("#ON_OFF_BTN").bootstrapSwitch('state', true, true);
		}else{
			$("#ON_OFF_BTN").bootstrapSwitch('state', false, false);
		}
	}catch(exception){
		errorException(exception);
	}
}

function INBOX_ActionJS(inboxFlag,AfterActionJS){
	try{
		var $data  ='INBOX_FLAG='+inboxFlag;
			$data += '&className=com.eaf.orig.ulo.app.view.util.manual.InboxAjax';
		var url = CONTEXT_PATH+'/Ajax';
		$.post(url,$data,function(data,status,xhr){
			try{		
				responseSuccess(data,status,xhr)
			}catch(e){}
		}).fail(function(data,status,xhr){
			errorAjax(data,status,xhr);
		});
		if(!isEmpty(AfterActionJS)){
			Pace.block();
			$('#action').val('Inbox');
			$('#handlerForm').val('N');
			$('#appFormName').submit();
		}
	}catch(exception){
		errorException(exception);
	}
}

function loadApplicationActionJS(applicationGroupId,taskId,applicationType,jobState,applicationTemplate,source){
	try{
		Pace.block();
		$('[name=APPLICATION_GROUP_ID]').val(applicationGroupId);
		$('[name=TASK_ID]').val(taskId);
		$('[name=APPLICATION_TYPE]').val(applicationType);
		$('[name=JOB_STATE]').val(jobState);
		$('[name=APPLICATION_TEMPLATE]').val(applicationTemplate);
		$('[name=SOURCE]').val(source);
		$('#action').val('LoadApplication');
		$('#handleForm').val('N');
		$('#appFormName').submit();
	}catch(exception){
		errorException(exception);
	}
}

function GET_JOBActionJS(){
	try{
		var CENTRAL_Q_NO =$('[name="CENTRAL_Q_NO"]').val();
		if (isEmpty(CENTRAL_Q_NO) || CENTRAL_Q_NO == '0') {
			alertBox('ERROR_GET_JOB');
		}else{
			$('[name="INBOX_BTN"]').val('Y');
			INBOX_ActionJS('Y',"refreshPage");
		}
	}catch(exception){
		errorException(exception);
	}
}
