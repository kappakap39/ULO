/**
 * 
 */
$(document).ready(function (){
	var height = $('.PanelSecond').outerHeight();
	if(height < $(window).height()-60){
		$('.PanelSecond').css({
			'height': $(window).height()-60
		});
	}
	$('.nav-inbox').slimScroll({
		height: $(window).height()+'px'
	});	
	$("Tr.ResultOdd").hover(
    	 function(){$(this).addClass("ResultOdd-haver");},
    	 function(){$(this).removeClass("ResultOdd-haver");}
    );
	$("Tr.ResultEven").hover(
	    	 function(){$(this).addClass("ResultEven-haver");},
	    	 function(){$(this).removeClass("ResultEven-haver");}
	    );
	$("Tr.Result-Obj").click(function(e){	
		e.preventDefault();
		try{
			var obj = $(this).attr('id').split(/\s+/);
			blockScreen();
			var form=document.appFormName;
			form.formID.value="KEC_FORM";
			form.currentTab.value="MAIN_TAB";
			form.action.value="LoadPLAppForm";
			form.handleForm.value = "N";
			form.appRecordID.value = obj[0];
			form.roleElement.value="DF";
			form.submit();
		}catch(e){
			unblockScreen();
			var msg = ERROR_JS+e.message;
			alertMassageERROR(msg);
		}
	});
	
});

function loadApplication(appRecordID,appStatus,jobState){
	try{
		blockScreen();
	    var form=document.appFormName;
		//displayLoading('Loading...');
		form.formID.value="KEC_FORM";
		form.currentTab.value="MAIN_TAB";
		form.action.value="LoadPLAppForm";
		form.handleForm.value = "N";
		form.appRecordID.value = appRecordID;
		form.appStatus.value = appStatus;
		form.jobState.value = jobState;
		form.roleElement.value="DF";
		//window.parent.target = "_parent";
		form.submit();	
	}catch(e){
		unblockScreen();
		var msg = ERROR_JS+e.message;
		alertMassageERROR(msg);
	}
}

function buttonStatusOnJob(){
	var curStatusJob = $("#jobStatus").text();
	if("Off"==curStatusJob){
		curStatusJob = "Y";
	}else{
		curStatusJob = "N";
	}
	var dataString = "status="+curStatusJob;
	$FrontController('ButtonOnJobAction','N','',dataString,refershStatusJob);
	
}
function refershStatusJob(data){
	
}
function clickPageList(atPage){
	var form = window.document.appFormName;
	form.atPage.value = atPage;
	form.itemsPerPage.value = "";
	form.action.value = "DFPLInbox";
	form.handleForm.value = "N";
	form.submit();
}
function clickItemPerPageList(atItem){
	var form = window.document.appFormName;
	form.itemsPerPage.value = atItem;
	form.atPage.value = "";
	form.action.value = "DFPLInbox";
	form.handleForm.value = "N";
	form.submit();  
}