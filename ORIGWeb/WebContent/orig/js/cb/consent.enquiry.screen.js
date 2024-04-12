/**
 * 
 */
var diffDate = 7776000000; //90day 60*60*24*1000*90

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
    	 function(){$(this).addClass("ResultOdd-haver-non-pointer");},
    	 function(){$(this).removeClass("ResultOdd-haver-non-pointer");}
    );
	$("Tr.ResultEven").hover(
	     function(){$(this).addClass("ResultEven-haver-non-pointer");},
	     function(){$(this).removeClass("ResultEven-haver-non-pointer");}
	);
	$('img.viewImg').click(function(e){
		e.preventDefault();
		var obj = $(this).attr('class').split(/\s+/);
		parent.leftFrame.closeMenuFrame();
		openNcbImage(obj[1]);
	});
	$('#dateFrom').blur(function(){
		var year = $('#dateFrom').val().substr(6,10);
		if(year < 2400 && year.length!=0){
			alertMassage(FORMAT_DATE_ERROR);
			$('#dateFrom').val('');
			$('#dateFrom').focus();
			$('#dateFrom').setCursorToTextEnd();
		}
	});
	$('#dateTo').blur(function(){
		var year = $('#dateTo').val().substr(6,10);
		if(year < 2400 && year.length!=0){
			alertMassage(FORMAT_DATE_ERROR);
			$('#dateTo').val('');
			$('#dateTo').focus();
			$('#dateTo').setCursorToTextEnd();
		}
	});
	
	//===========Enter Submit==================
	 var form =  document.appFormName;
	 if($(form).length > 0)
	 {
	     $(form).keypress(function (e){
	         code = e.keyCode ? e.keyCode : e.which;
	           if(code.toString() == 13) 
	           {
	        	   search();
	           }
	     });
	 }
	//=============================
//	 #septem focus screen
	 $('#button-search').focus();	 	 
});


function validate(){
	var dateFrom = appFormName.dateFrom.value;
	var dateTo= appFormName.dateTo.value;
	if(""==dateFrom){
//		alertMassage(CONSENT_REF_DATE_FROM);
//		appFormName.dateFrom.focus();
//		setCaretPosition(appFormName.dateFrom);
		alertMassageFuncParam1(CONSENT_REF_DATE_TO,setCaretPosition,appFormName.dateFrom);
		return false;
	}
	if(""==dateTo){
//		alertMassage(CONSENT_REF_DATE_TO);
//		appFormName.dateTo.focus();
//		setCaretPosition(appFormName.dateTo);
		alertMassageFuncParam1(CONSENT_REF_DATE_TO,setCaretPosition,appFormName.dateTo);
		return false;
	}
	
	var DateFrom = StringToDate(dateFrom);
	var DateTo = StringToDate(dateTo);
	
	if(DateTo < DateFrom){
//		alertMassage(ERROR_DATE);
//		appFormName.dateTo.focus();
//		setCaretPosition(appFormName.dateTo);
		alertMassageFuncParam1(ERROR_DATE,setCaretPosition,appFormName.dateTo);
		return false;
	}
	if((DateTo-DateFrom) > diffDate){
//		alertMassage(DAY_MORE_THAN_90);
//		appFormName.dateTo.focus();
//		setCaretPosition(appFormName.dateTo);
		alertMassageFuncParam1(DAY_MORE_THAN_90,setCaretPosition,appFormName.dateTo);
		return false;
	}
	return true;
}
function setCaretPosition(text){
	$('#'+$(text).attr('id')).focus();
	$('#'+$(text).attr('id')).setCursorToTextEnd();
}
function StringToDate(str){
	var dt  = parseInt(str.substring(0, 2), 10);
    var mon = parseInt(str.substring(3, 5), 10);
    var yr  = parseInt(str.substring(6, 10), 10);
    var date = new Date(yr, mon, dt);
    return date;
}
function exportExcel(){
	if(validate()){
		var dateFrom = appFormName.dateFrom.value;
		var dateTo= appFormName.dateTo.value;
	    var form=document.reportForm;
        var oldTargert = form.target;
    	form.action = "PLExcelServlet";
    	var params = "<input type=\"hidden\" name=\"dateFrom\" value=\""+dateFrom+"\"> <input type=\"hidden\" name=\"dateTo\" value=\""+dateTo+"\"> <input type=\"hidden\" name=\"requestType\" value=\"EXPORT_CONSENT\"> ";
    	
   	    params = params+"<iframe name=\"ExportXLSFormFrm\" src =\"\" width=\"0\" height=\"0\"></iframe>";
   	    document.getElementById("reportParam").innerHTML = params; 
   	    form.target = "ExportXLSFormFrm";
   	    form.submit();
   	    form.target = oldTargert;
	}
}
function search(){
	if(validate()){
		appFormName.action.value = "SearchCBConsentWebAction";
		appFormName.handleForm.value = "N";
		appFormName.submit();	
	}
}
function clickPageList(atPage){
	var form = window.document.appFormName;
	form.atPage.value = atPage;
	form.action.value = "ValueListWebAction";
	form.handleForm.value = "N";
	form.submit();
}
function clickItemPerPageList(atItem){
	var form = window.document.appFormName;
	form.itemsPerPage.value = atItem;
	form.action.value = "ValueListWebAction";
	form.handleForm.value = "N";
	form.submit();  
}
function openNcbImage(appRedId){
	var url = CONTEXT_PATH_ORIG+"/FrontController?action=NCB_IMAGES&appRecId="+appRedId;
	$dialog = $dialogEmpty;
	var width = 1200;
	var higth = $(window).height()-71;
	var title = 'NCB';
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
		        of: $(document.body)
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
//	openDialog(url,width,higth,scrollbars=0,setPrefs);
}