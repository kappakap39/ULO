/**
 * @Pipe
 */
$(document).ready(function(){	
	if($('#projectcode_buttonmode').val() == DISPLAY_MODE_EDIT
			&& $('#project_code').val() != ''){
		ResetProjectCode();
	}		
	if($('#projectcode_displaymode').val() == DISPLAY_MODE_EDIT){
		TextboxProjectCode();
		ButtonProjectCode();
	}
});

function ButtonProjectCode(){
	$('#project_code_popup').click(function(e){
		OpenPopupProjectCode();
	});
}
function OpenPopupProjectCode(){
	var projectCode = $.trim($('#project_code').val());
	var exception_project = '';
	if($('#exception_project').attr('checked') == 'checked'){
		exception_project = 'Y';
	}	
	var url = CONTEXT_PATH_ORIG+"/FrontController?action=LoadProjectCode&project_code="+projectCode+'&product_feature='+$('#product_feature').val();
		url += "&exception_project="+exception_project;
	var frameModule = (document.getElementById('frame-module')== null)? document.body :'.frame-module';
	$dialog = $dialogEmpty;
	var width = 1000;
	var higth = $(window).height()-50;
	var title = PROJECT_CODE;
	$(".plan-dialog").remove();
	$('#popup-action').val('');
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(url);
	$dialog.dialog({
		    resizable : false,
		    modal : true,
		    autoOpen: false,
		    draggable: true,
		    width: width,
		    height: higth,
		    title:title,
		    buttons :{
		        "Close" : function() {						        	
		        	$dialog.dialog("close");	
		        	closeDialog();
		        }
	    	},
		    position: { 
		        my: 'center',
		        at: 'center',
		        of: $(frameModule)
    		},
		    close: function(){
		    	closeDialog();
		    	if($('#popup-action').val() != "SEARCH_CODE"){
		    		$('#project_code').focus();
		    		$('#project_code').setCursorToTextEnd();
		    		$('#popup-action').val('');
		    	}
		    }
	});
   $dialog.dialog("open");
}
function TextboxProjectCode(){
	$("#project_code").blur(function(){
		$(this).val($.trim($(this).val()));
		var projectCode = $(this).val();
		if(validateInteger(projectCode)){
			LoadProjectCodeInformation();
		}else{
			if(projectCode != ''){
				$('#project_code').val('');
				$('#project_code').focus();
				$('#project_code').setCursorToTextEnd();
			}
		}
	});
	$("#project_code").keypress(function(e){
		var strChar = String.fromCharCode(e.keyCode);
		if(validateInteger(strChar)){
			return true;
		}else{
			e.preventDefault();
			return false;
		}
	});
}

function LoadProjectCodeInformation(){
	var projectCode = $.trim($('#project_code').val());
	var exception_project = '';
	if($('#exception_project').attr('checked') == 'checked'){
		exception_project = 'Y';
	}
	if(projectCode == '') return;
	$('#projectCode').val(projectCode);
	var dataString = "className=GetProjectCode&packAge=5&returnType=0&project_code="+projectCode+'&product_feature='+$('#product_feature').val();
		dataString += "&exception_project="+exception_project;
	jQuery.ajax( {
		type :"POST",
		url :"AjaxDisplayServlet",
		data :dataString,
		async :true,	
		dataType: "json",
		success : function(data ,status ,xhr){
			if(data!= null && data.length >0){
				jsonDisplayElementById(data);
				setDefaultPaymentMethod();
				if($('#projectcode_buttonmode').val() == DISPLAY_MODE_EDIT
						&& $('#project_code').val() != ''){
					ResetProjectCode();
				}
			}else{
				OpenPopupProjectCode();
			}
		},
		error : function(response){
		}
	});
}

function ResetProjectCode(){ 
	$('#reset-projectcode').click(function(e){	
		alertMassageYesNoFunc(PROJECT_CODE_WARNING,ClearDataProjectCode,null);
	});	
}
function ClearDataProjectCode(){
	ajaxJsonManual('ClearDataProjectCode','5','',DisplayClearProjectCode);
}
function DisplayClearProjectCode(data){
	jsonDisplayElementById(data);
	TextboxProjectCode();
	ButtonProjectCode();
	SensitiveAttrNameEngine('project_code');
	SensitiveEngine('#project_code');
	$('#project_code').focus();
	if($('#projectcode_buttonmode').val() == DISPLAY_MODE_EDIT
			&& $('#project_code').val() != ''){
		ResetProjectCode();
	}
}
function setDefaultPaymentMethod(){
    var application_property=$("#application_property").html();
    if(application_property=="Payroll Group"){
    	$("#payment_method_pay").val("02");
    	$("#payment_method_ratio").removeAttr("disabled");
    	$("#payment_method_ratio").val("5");
    }
}
