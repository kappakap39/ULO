/**
 * Sankom
 */
$(document).ready(function(){	
	$("Tr.ResultOdd").hover(
	    function(){$(this).addClass("ResultOdd-haver-non-pointer");},
	    function(){$(this).removeClass("ResultOdd-haver-non-pointer");}
	);
	$("Tr.ResultEven").hover(
	    function(){$(this).addClass("ResultEven-haver-non-pointer");},
	    function(){$(this).removeClass("ResultEven-haver-non-pointer");}
	);

	$('#btnAttach').click(function() {
		//LoadAttachmentPopup('add');
		uploadAttach();
	});
	$('#btnDelete').click(function() {
		deleteAttach();
	});
});

function LoadAttachmentPopup(val) {
	var url = CONTEXT_PATH_ORIG+"/orig/popup/pl/add_attachment.jsp";
	$dialog = $dialogEmpty;
	var width = 1250;
	var higth = $(window).height() - 250;
	var title = "Attachment";
	$(".plan-dialog").remove();
	$dialog = $('<div class="plan-dialog" id="plan-dialog"/>').load(url);
	var frameModule = (document.getElementById('frame-module')== null)? document.body :'.frame-module';
	$dialog.dialog({
		resizable : false,
		modal : true,
		autoOpen : false,
		draggable : true,
		width : width,
		height : higth,
		title : title,
		position : {
			my : 'center',
			at : 'center',
			of : $(frameModule)
		},
		close : function() {
			try{
	    		dialogWin.win.close();
	    	}catch(e){}
        	closeDialog();
		},
		buttons : {
			"Attach" : function(){upload();},
			"Close" : function(){$dialog.dialog("close"); closeDialog();}
		}

	});
	$dialog.dialog("open");
}

function checkAllBox(field) {
	if($('#' + field).is(':checked')){
		$('#' + field).attr('checked', true);
		$(':checkbox').attr('checked', true);
	}else{
		$('#' + field).removeAttr('checked', false);
		$(':checkbox').removeAttr('checked', false);
	}
}
function deleteAttachment(attachmentIds){
	var dataString = "attachmentIds=" + attachmentIds;
	jQuery.ajax({
		type : "POST",
		url : 'PLDeleteAttachmentServlet',
		data : dataString,
		async : false,
		dataType : "text",
		success : function(data, status, xhr) {
			var fields = document.getElementById("attachTable");
			fields.innerHTML = data;
		},
		error : function(data) {

		}
	});
}

function unCheck() {
	$('#checkbox').attr('checked', false);
}
function drawAttachTable(table) {
	var fields = document.getElementById("attachTable");
	fields.innerHTML = table;
}
function downLoadAttach(attachmentId){
	var form = document.reportForm;
	form.action = "PLDownLoadAttachmentServlet";
	var params = "<input type=\"hidden\" name=\"attachmentId\" value=\""+ attachmentId + "\">";
	document.getElementById("reportParam").innerHTML = params;
	form.submit();

}
function checkItemAll(item) {
	var itemValue = item.checked;
	var checkItems = document.getElementsByName("chkAttachmentId");
	for ( var i = 0; i < checkItems.length; i++) {
		checkItems[i].checked = itemValue;
	}
}
function checkAttachmentClick(obj) {
	if(obj.checked==false){		
		document.getElementById("attCheckAll").checked=false;
	}
}